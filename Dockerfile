FROM adoptopenjdk/openjdk15
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]