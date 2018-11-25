# Switcher demo

Proof of concept of a little framework which allows projects with multiple modules to switch between monolithic, micro-services or hybrid


## Running examples

Default service mode is local, so in case of local instance, mode is not needed

### Running monolithic 

  ``
  java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar --spring.config.name=all-services-local
  ``
  
 or just 
 
  ``
  java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar
  ``
  
 ### Running micro-services
 
 #### Client micro-service 
 
  ``
  java -jar MainApp/target/MainApp-1.0-SNAPSHOT.jar --spring.config.name=client-local-and-product-remote
  ``
  