FROM openjdk:21
EXPOSE 8080
ADD target/BeatSheetService-0.0.1.jar BeatSheetService-0.0.1.jar
ENTRYPOINT ["java","-jar","/BeatSheetService-0.0.1.jar"]