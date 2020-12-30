FROM openjdk:11.0.9-buster

VOLUME /temp

COPY build/libs/company-service*.jar /opt/company-service_home/deployments/company-service*.jar

CMD ["java","-jar","/opt/company-service_home/deployments/company-service*.jar"]

EXPOSE 8080
