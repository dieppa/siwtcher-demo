package io.cloudyrock.switcher.api.util;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class ServiceConfiguration {


    private final static String REMOTE_MODE = "remote";
    private final static String HOST_PROP = "remoteHost";
    private final static String SERVICES_PREFIX_CONFIG_PROP = "services.";
    private final static String SERVICE_MODE_PROP_TEMPLATE = SERVICES_PREFIX_CONFIG_PROP + "%s.mode";
    private final static String SERVICE_HOST_PROP_TEMPLATE = SERVICES_PREFIX_CONFIG_PROP + "%s." + HOST_PROP;
    private final static String TEMPLATE_HOST_ERROR = HOST_PROP + " property for service %s cannot be null as remote mode is set";

    private final String serviceName;

    @Autowired
    protected Environment env;

    @Autowired
    protected ApplicationContext context;

    protected ServiceConfiguration(String serviceName) {
        this.serviceName = serviceName;
    }

    protected Object getService(Class serviceClass) {
        return isRemote() ? getRemoteInstance(serviceClass) : getLocalInstance(serviceClass);
    }

    private boolean isRemote() {
        final String serviceMode = String.format(SERVICE_MODE_PROP_TEMPLATE, serviceName);
        final String remoteHost = getRemoteHost();
        return REMOTE_MODE.equals(serviceMode) || StringUtils.isEmpty(serviceMode) && !StringUtils.isEmpty(remoteHost);

    }

    @SuppressWarnings("unchecked")
    private Object getLocalInstance(Class serviceClass) {
        final Constructor constructor = serviceClass.getConstructors()[0];
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = context.getBean(parameterTypes[i]);
        }
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getRemoteInstance(Class serviceClass) {
        final String host = getRemoteHost();
        if (StringUtils.isEmpty(host)) {
            throw new IllegalArgumentException(String.format(TEMPLATE_HOST_ERROR, serviceName));
        }
        final Class serviceApi = getServiceApi(serviceClass);
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(serviceApi, host);
    }

    private String getRemoteHost() {
        return env.getProperty(String.format(SERVICE_HOST_PROP_TEMPLATE, serviceName));
    }

    private Class getServiceApi(Class serviceClass) {
        Class[] interfaces = serviceClass.getInterfaces();
        if (interfaces == null || interfaces.length < 1) {
            throw new RuntimeException("Service class " + serviceClass.getName() + " needs to implement a service Api interface");
        }

        if (interfaces.length == 1) {
            return interfaces[0];
        }

        return Stream.of(interfaces)
                .filter(anInterface -> anInterface.isAnnotationPresent(ServiceApi.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service class " + serviceClass.getName() + " implements more " +
                        "than one interface, but none of them is annotated with @ServiceApi"));
    }

}
