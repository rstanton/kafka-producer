FROM openjdk:15-alpine
RUN java -version
RUN apk add git
RUN git clone https://github.com/rstanton/kafka-producer.git
WORKDIR /kafka-producer

ENTRYPOINT ./gradlew run --args stock