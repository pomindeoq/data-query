FROM gradle:7.2.0-jdk17 as builder
WORKDIR /var/lib/build
ADD .. ./

RUN gradle build

CMD pwd && ls -ltr

FROM openjdk:17-alpine

RUN apk add --no-cache bash

CMD ls -all
EXPOSE 8080

COPY --from=builder /var/lib/build/build/libs/data-query-0.0.1.jar /data-query.jar

ENTRYPOINT ["java", "-jar","/data-query.jar"]