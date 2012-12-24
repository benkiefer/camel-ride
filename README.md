##Camel Ride

**What is this?**

Some examples of how a error handling in Camel routes can be done using the Java DSL and Spring. Check out the following classes for more examples:

 - OnExceptionRoute: When a specific type of exception occurs during a route, perform specified behavior.
 - DoTryRoute: Mimics the java Try Catch Finally model.
 - DeadLetterChannel: Follows the Dead Letter Channel EIP

The app module is a complete WAR file that can be run from the command line using the `maven:jetty run' command. You will want to override the property values in the camel-ride-app-env.properties file to try it out.
