ARG java_version=11-jdk-alpine

FROM openjdk:$java_version AS openjdk11

USER root

ENV active=prod
ENV logPath="/var/log/my-app/"

LABEL maintainer="13076894376@163.com"

WORKDIR /my-app

COPY . /my-app/

EXPOSE 9000

VOLUME /my-app/

CMD nohup java -jar target/my-app-1.0.0-SNAPSHOT.jar -Dspring.profiles.active=$active > /dev/null &2 > &1 &

