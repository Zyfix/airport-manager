FROM gradle:8.3.0-jdk20-alpine AS build

WORKDIR /app
COPY . /app
RUN gradle clean bootJar

# RUN mv /app/build/libs/*.jar /app/airplane-module.jar

FROM openjdk:20-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/airplane-module.jar

ENV PORT=$PORT

CMD java -jar /app/airplane-module.jar

