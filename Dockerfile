FROM openjdk:11
ADD target/maat-tcs-houses-1.0.jar maat-tcs-houses-1.0.jar
ENTRYPOINT ["java", "-jar","maat-tcs-houses-1.0.jar"]
EXPOSE 8080