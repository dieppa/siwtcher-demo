package io.cloudyrock.switcher.api.util;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class ServiceConfiguration {

    protected final static Logger LOG = LoggerFactory.getLogger(ServiceConfiguration.class);

    private final static String LOG_PREFIX = "[SERVICE-CONFIG] - ";
    private final static String REMOTE_MODE = "remote";
    private final static String HOST_PROP = "remoteHost";
    private final static String SERVICES_PREFIX_CONFIG_PROP = "services.";
    private final static String SERVICE_MODE_PROP_TEMPLATE = SERVICES_PREFIX_CONFIG_PROP + "%s.mode";
    private final static String SERVICE_HOST_PROP_TEMPLATE = SERVICES_PREFIX_CONFIG_PROP + "%s." + HOST_PROP;
    private final static String TEMPLATE_HOST_ERROR = HOST_PROP + " property for service %s cannot be null as remote mode is set";

    private final String defaultServiceName;
    private final Class defaultServiceClass;

    @Autowired
    protected Environment env;

    @Autowired
    protected ApplicationContext context;

    //Construction without default service
    protected ServiceConfiguration() {
        this.defaultServiceName = null;
        this.defaultServiceClass = null;
    }


    //Construction with default service
    protected ServiceConfiguration(String defaultServiceName, Class defaultServiceClass) {
        this.defaultServiceName = defaultServiceName;
        this.defaultServiceClass = defaultServiceClass;
    }

    @SuppressWarnings("SameParameterValue")
    protected Object getService(String serviceName, Class serviceClass) {
        return isRemote(serviceName)
                ? getRemoteInstance(serviceName, serviceClass)
                : getLocalInstance(serviceName, serviceClass);
    }

    protected Object getServiceDefault() {
        checkDefaultParameters();
        return isRemote(defaultServiceName)
                ? getRemoteInstance(defaultServiceName, defaultServiceClass)
                : getLocalInstance(defaultServiceName, defaultServiceClass);
    }

    private void checkDefaultParameters() {
        if(defaultServiceClass == null || StringUtils.isEmpty(defaultServiceName)) {
            throw new IllegalArgumentException("To use getServiceDefault() method, configuration needs to be " +
                    "initialised with default serviceName and serviceClass");
        }
    }

    private boolean isRemote(String serviceName) {
        final String serviceMode = String.format(SERVICE_MODE_PROP_TEMPLATE, serviceName);
        final String remoteHost = getRemoteHost(serviceName);
        return REMOTE_MODE.equals(serviceMode) || StringUtils.isEmpty(serviceMode) && !StringUtils.isEmpty(remoteHost);

    }

    @SuppressWarnings("unchecked")
    private Object getLocalInstance(String serviceName, Class serviceClass) {
        LOG.debug("{} Starting building local instance for {} ", LOG_PREFIX, serviceName);
        final Constructor constructor = serviceClass.getConstructors()[0];
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = context.getBean(parameterTypes[i]);
        }
        try {

            LOG.info("{} Built local instance for {} ", LOG_PREFIX, serviceName);
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getRemoteInstance(String serviceName, Class serviceClass) {
        LOG.debug("{} Starting building remote instance for {} ", LOG_PREFIX, serviceName);
        final String host = getRemoteHost(serviceName);
        if (StringUtils.isEmpty(host)) {
            throw new IllegalArgumentException(String.format(TEMPLATE_HOST_ERROR, serviceName));
        }
        final Class serviceApi = getServiceApi(serviceClass);
        Object service=  Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(serviceApi, host);
        LOG.info("{} Built remote instance for {} ", LOG_PREFIX, serviceName);
        return service;
    }

    private String getRemoteHost(String serviceName) {
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
