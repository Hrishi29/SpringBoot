FROM openjdk:11-jdk-alpine
VOLUME /tmp
COPY ./target/SpringBoot-1.0.0.jar mySpring.jar
ENTRYPOINT {"java","Djava.security.egd=file:/dev/./urandom","-jar","/api/jar"}