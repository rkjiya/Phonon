

## README ###

loadtesting application

This application exposes REST APIs to create, update and fetch a request.
It also move request to target application for processing.

Steps to build the application:

1. Clone the application code from this location :
2. Build the Project from application directory using below command
   ./loadtesting> ./gradlew clean build -x test
   Above command will generated the JAR File at ./loatesting/build/libs directory.
3. Execute the JAR to run the application. Use beow command:
   java -jar ./loadtesting/build/libs/loadtesting-0.0.1-SNAPSHOT.jar

