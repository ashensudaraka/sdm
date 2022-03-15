FROM bellsoft/liberica-openjdk-alpine:17.0.2 as builder
RUN apk update
RUN apk add maven
RUN mkdir -p /root/.m2
RUN touch /root/.m2/settings.xml
RUN echo $'<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n\
xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">\n\
<servers>\n\
  <server>\n\
    <id>jfrog-releases</id>\n\
    <username>gihan.k@salesreckon.com</username>\n\
    <password>eyJ2ZXIiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJraWQiOiJjYjNrTG1yc2tQNnZETGk0UElWZ00wXzJISEFGa0hfdjBiSU0xSlAzd2wwIn0.eyJleHQiOiJ7XCJyZXZvY2FibGVcIjpcInRydWVcIn0iLCJzdWIiOiJqZmFjQDAxZndrZmRrbWprNzZqMW41MzA1c2gxbnZ6XC91c2Vyc1wvZ2loYW4ua0BzYWxlc3JlY2tvbi5jb20iLCJzY3AiOiJhcHBsaWVkLXBlcm1pc3Npb25zXC91c2VyIiwiYXVkIjpbImpmcnRAKiIsImpmYWNAKiIsImpmZXZ0QCoiLCJqZm1kQCoiLCJqZmNvbkAqIl0sImlzcyI6ImpmZmVAMDAwIiwiZXhwIjoxNjc4NjI5ODkxLCJpYXQiOjE2NDcwOTM4OTEsImp0aSI6Ijk3OTQwODkwLTIzYmEtNDM0MC04NDE4LTQxNmZlYTgxY2IxNSJ9.MLRox-LmeYVtydmIhS1R1JjnP1UEuISpdJfOOVNcoBLRwG7G17e6Z_eM8qWFbssd2P2OquE_gidde9kY3AMqymlO5SW-JSmOefrMCzUKyhb6UWiVksTqkjp7TlnmwCwH2nPhKbhFTAtQgqrDCxBfUMNbUL46Y0d9lMq_jhEG0HXvEE8cdGkFacVhug0s3JNG2Q83ShrzBjRtnxWFs606382lQoT8owgawqhI3SEeTCpwspY2W9KFHanG42QJndttUkm6Rp0X2hAm__-mLnga00mQ-1YqWQ9cfGbl38zHU0AKo_1wavnL9xrnp7hdJkvTXOw3TQW9h542Hgy4eMzSgA</password>\n\
  </server>\n\
  <server>\n\
    <id>jfrog-snapshots</id>\n\
    <username>gihan.k@salesreckon.com</username>\n\
    <password>eyJ2ZXIiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJraWQiOiJjYjNrTG1yc2tQNnZETGk0UElWZ00wXzJISEFGa0hfdjBiSU0xSlAzd2wwIn0.eyJleHQiOiJ7XCJyZXZvY2FibGVcIjpcInRydWVcIn0iLCJzdWIiOiJqZmFjQDAxZndrZmRrbWprNzZqMW41MzA1c2gxbnZ6XC91c2Vyc1wvZ2loYW4ua0BzYWxlc3JlY2tvbi5jb20iLCJzY3AiOiJhcHBsaWVkLXBlcm1pc3Npb25zXC91c2VyIiwiYXVkIjpbImpmcnRAKiIsImpmYWNAKiIsImpmZXZ0QCoiLCJqZm1kQCoiLCJqZmNvbkAqIl0sImlzcyI6ImpmZmVAMDAwIiwiZXhwIjoxNjc4NjI5ODkxLCJpYXQiOjE2NDcwOTM4OTEsImp0aSI6Ijk3OTQwODkwLTIzYmEtNDM0MC04NDE4LTQxNmZlYTgxY2IxNSJ9.MLRox-LmeYVtydmIhS1R1JjnP1UEuISpdJfOOVNcoBLRwG7G17e6Z_eM8qWFbssd2P2OquE_gidde9kY3AMqymlO5SW-JSmOefrMCzUKyhb6UWiVksTqkjp7TlnmwCwH2nPhKbhFTAtQgqrDCxBfUMNbUL46Y0d9lMq_jhEG0HXvEE8cdGkFacVhug0s3JNG2Q83ShrzBjRtnxWFs606382lQoT8owgawqhI3SEeTCpwspY2W9KFHanG42QJndttUkm6Rp0X2hAm__-mLnga00mQ-1YqWQ9cfGbl38zHU0AKo_1wavnL9xrnp7hdJkvTXOw3TQW9h542Hgy4eMzSgA</password>\n\
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
