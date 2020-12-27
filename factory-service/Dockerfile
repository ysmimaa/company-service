FROM openjdk:11.0.9-buster

VOLUME /temp

COPY build/libs/factory-service*.jar /opt/factory-service_home/deployments/factory-service*.jar

CMD ["java" , "-jar" , "/opt/factory-service_home/deployments/factory-service*.jar"]

EXPOSE 8080
