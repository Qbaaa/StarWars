FROM eclipse-temurin:11-jdk-alpine AS builder
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY /src /src
RUN ./mvnw clean install

FROM eclipse-temurin:11-jre-alpine
WORKDIR /opt/app
COPY --from=builder /target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/opt/app/*.jar"]