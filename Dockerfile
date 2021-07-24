FROM azul/zulu-openjdk-alpine:11.0.12-11.50.19 as builder
RUN apk update
RUN apk add maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip

FROM azul/zulu-openjdk-alpine:11.0.12-11.50.19-jre-headless as extractor
WORKDIR /app
COPY --from=builder app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:11.0.12-11.50.19-jre-headless
WORKDIR /app
COPY --from=extractor app/dependencies .
COPY --from=extractor app/snapshot-dependencies .
COPY --from=extractor app/spring-boot-loader .
COPY --from=extractor app/application .
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
