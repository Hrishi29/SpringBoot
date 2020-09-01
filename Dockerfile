FROM openjdk:11-alpine

VOLUME /tmp

COPY ./target/SpringBoot-1.0.0.jar /mySpring.jar
COPY ./docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod 755 /docker-entrypoint.sh

RUN touch /mySpring.jar && mkdir -p /config

WORKDIR /

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD [ "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mySpring.jar" ]