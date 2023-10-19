FROM openjdk:21
EXPOSE 8080
ADD target/beat-sheet-service-0.0.1.jar beat-sheet-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/beat-sheet-service-0.0.1.jar"]