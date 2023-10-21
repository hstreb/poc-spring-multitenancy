FROM eclipse-temurin:17.0.8_7-jdk

COPY build/libs/poc-spring-multitenancy-0.0.1.jar app.jar

CMD ["java", "-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]