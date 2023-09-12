FROM gradle:8.3.0-jdk20-alpine AS build

WORKDIR /app
COPY . /app
RUN gradle clean bootJar

FROM openjdk:20-slim

WORKDIR /app

COPY --from=build /app/build/libs/app-*.jar /app/airport.jar

ENV PORT=$PORT

CMD java -jar /app/airport.jar

