FROM openjdk:17-oracle

WORKDIR /app
COPY . /app
COPY target/quarkus-app .
COPY  target/quarkus-app/quarkus-run.jar app.jar
CMD ["java", "-jar", "-Dquarkus.profile=dev", "app.jar"]


