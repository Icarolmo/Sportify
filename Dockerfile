FROM openjdk:20

RUN mkdir /app

COPY target/Ifootball-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Ifootball-0.0.1-SNAPSHOT.jar"]