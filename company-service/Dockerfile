FROM openjdk:11.0.3-brut

VOLUME /temp

COPY build/libs/company-service*.jar /opt/company-service_home/company-service*.jar

CMD ["java","-jar","/opt/company-service_home/company-service*.jar"]

EXPOSE 8080
