FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE=../target/*.jar
COPY ${JAR_FILE} application.jar
CMD [ "ls -a" ]
ENTRYPOINT ["java", "-jar", "application.jar"]