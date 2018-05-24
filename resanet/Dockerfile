FROM maven:3.5-jdk-8 as BUILD
 
COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp
RUN mvn -f /usr/src/myapp/pom.xml clean package

#FROM openjdk:8
#COPY --from=BUILD /usr/src/myapp/target/*-SNAPSHOT.jar /resanet.jar
#ENTRYPOINT java -jar /resanet.jar


FROM openjdk:8-alpine
WORKDIR /app
COPY --from=BUILD /usr/src/myapp/target/*-SNAPSHOT.jar resanet.jar
EXPOSE 8080
ENTRYPOINT  [ "sh", "-c", "java $JAVA_OPTS -jar /app/resanet.jar"]
