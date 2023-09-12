FROM gradle:8.3.0-jdk20-alpine AS build

WORKDIR /app
COPY . /app
RUN gradle clean bootJar
#
#RUN ls /app/build/
#RUN mv /app/build/libs/app-*.jar /app/steward-module.jar

#CMD bash

FROM openjdk:20-slim

WORKDIR /app

COPY --from=build /app/build/libs/app-*.jar /app/steward-module.jar
# COPY --from=build /app/steward-module.jar /app/steward-module.jar

ENV PORT=$PORT

CMD java -jar /app/steward-module.jar

