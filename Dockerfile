ARG java_version=11

FROM openjdk:$java_version AS openjdk11

USER root

ENV active_profile=prod
ENV log_path=/var/log/my-app/
ENV mysql_url="jdbc:mysql://172.31.163.102:3308/test?serverTimezone=Asia/Shanghai"
ENV mysql_user=developer
ENV mysql_passwd=DevPasswd123.
ENV redis_host=172.31.163.102
ENV redis_port=6379
ENV redis_db=0

LABEL maintainer="13076894376@163.com"

WORKDIR /my-app

COPY . /my-app/

EXPOSE 9000

VOLUME /my-app/

CMD java -jar target/my-app-1.0.0-SNAPSHOT.jar

