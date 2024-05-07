ARG java_version=11

FROM openjdk:$java_version AS openjdk11

USER root

ENV activeProfile=prod
ENV logPath=/var/log/my-app/
ENV mysql_url=jdbc:mysql://mysql/test?serverTimezone=Asia/Shanghai

LABEL maintainer="13076894376@163.com"

WORKDIR /my-app

COPY . /my-app/

EXPOSE 9000

VOLUME /my-app/

CMD java -Dspring.profiles.active=prod -Dlog.path=/var/log/my-app/ -Dmysql_url=jdbc:mysql://192.169.0.104:3308/test?serverTimezone=Asia/Shanghai -jar target/my-app-1.0.0-SNAPSHOT.jar

