FROM gradle:8.4.0-jdk17

WORKDIR /app
RUN apt-get update && apt-get install -y dos2unix
COPY . .
RUN find . -type f -name "*.gradle" -exec dos2unix {} \;
RUN find . -type f -name "gradlew" -exec dos2unix {} \;
RUN chmod +x ./gradlew
RUN ./gradlew build

CMD ["java", "-jar", "build/libs/diplomBackend-0.0.1-SNAPSHOT.jar"]