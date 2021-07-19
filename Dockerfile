FROM maven:3.8.1-adoptopenjdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip

FROM adoptopenjdk:11-jre-hotspot as extractor
WORKDIR /app
COPY --from=builder app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=extractor app/dependencies .
COPY --from=extractor app/snapshot-dependencies .
COPY --from=extractor app/spring-boot-loader .
COPY --from=extractor app/application .
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
