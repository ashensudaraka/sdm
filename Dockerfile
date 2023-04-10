FROM bellsoft/liberica-openjdk-alpine:17.0.2 as builder
RUN apk update
RUN apk add maven
RUN mkdir -p /root/.m2
RUN touch /root/.m2/settings.xml
RUN echo $'<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n\
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">\n\
  <servers>\n\
  <server>\n\
  <id>salesdatam-releases</id>\n\
  <username>ashensudaraka</username>\n\
  <password>glpat-_36Ns2egFCe_bT-eYmBM</password>\n\
  </server>\n\
  <server>\n\
  <id>salesdatam-snapshots</id>\n\
  <username>ashensudaraka</username>\n\
  <password>glpat-_36Ns2egFCe_bT-eYmBM</password>\n\
  </server>\n\
  </servers>\n\
  </settings>' > /root/.m2/settings.xml
WORKDIR /app
COPY pom.xml .
COPY src ./src
ARG CACHEBUST=2
RUN --mount=type=cache,target=/root/.m2/repository mvn clean package -Dmaven.test.skip -U

FROM bellsoft/liberica-openjre-alpine:17.0.2 as extractor
WORKDIR /app
COPY --from=builder app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM bellsoft/liberica-openjre-alpine:17.0.2
RUN addgroup -S appuser && adduser -S appuser -G appuser
USER appuser:appuser
WORKDIR /app
COPY --from=extractor app/dependencies .
COPY --from=extractor app/snapshot-dependencies .
COPY --from=extractor app/spring-boot-loader .
COPY --from=extractor app/application .
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
