FROM openjdk:20

RUN mkdir /app

COPY target/Sportify-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Sportify-0.0.1-SNAPSHOT.jar"]