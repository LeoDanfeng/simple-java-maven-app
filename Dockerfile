FROM maven:3.6
USER root
LABEL maintainer="13076894376@163.com"
WORKDIR /my-app
COPY . /my-app/
EXPOSE 8080
VOLUME /my-app/
CMD java -jar target/my-app-1.0.0-SNAPSHOT.jar

