FROM openjdk:11-jdk-slim
LABEL maintainer="aydar@gumerbaev.ru"

ADD ./target/address-parser.jar /app/
WORKDIR /app
CMD ["java", "-jar", "address-parser.jar"]

EXPOSE 8080
