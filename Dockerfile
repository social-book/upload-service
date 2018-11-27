FROM openjdk:8-jre-slim

RUN mkdir /app

WORKDIR /app

ADD ./api/target/upload-api-1.0-SNAPSHOT.jar /app

EXPOSE 8082

CMD java -jar upload-api-1.0-SNAPSHOT.jar