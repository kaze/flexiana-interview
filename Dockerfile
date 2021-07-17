FROM openjdk:8-alpine

COPY target/uberjar/flexiana-interview.jar /flexiana-interview/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/flexiana-interview/app.jar"]
