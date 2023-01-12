FROM openjdk:17
COPY target/Amortization.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]

#COPY .mvn/ ./mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#CMD ["./mvnw", "spring-boot:run"]
#COPY *.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]