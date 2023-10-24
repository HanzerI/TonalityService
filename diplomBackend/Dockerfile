FROM amazoncorretto:17

WORKDIR /app

COPY . .
RUN ./gradlew build

CMD ["java", "-jar", "build/libs/diplomBackend-0.0.1-SNAPSHOT.jar"]