# Switcher demo

Proof of concept of a little framework which allows projects with multiple modules to switch between monolithic, micro-services or hybrid


## Running examples

### Running monolithic 

 ``
 java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar --services.mode=local 
                                                   --server.port=8080
 ``
 
 ### Running micro-services
 
 #### Client micro-service 
 
  ``
  java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar --services.client.mode=local
                                                    --services.product.mode=remote
                                                    --services.product.host=http://localhost:8082
                                                    --server.port=8081
  ``
  
#### Product micro-service 

``
  java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar --services.client.mode=remote
                                                    --services.client.host=http://localhost:8081
                                                    --services.product.mode=local
                                                    --server.port=8082
``