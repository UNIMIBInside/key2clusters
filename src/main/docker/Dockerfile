FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    SLEEP=0
ENV JAVA_OPTS="-Xmx512m -Xmx256m"

ENV KEYWORD_CLUSTER_FILENAME=sample_keywords.csv

ENV SPRING_PROFILES_ACTIVE=MEMORY

ENV KEYWORD_CLUSTER_RECREATE_DATA_STRUCTURE=true

# add directly the jar
ADD *.jar /app.jar
RUN sh -c 'touch /app.jar'
VOLUME /files
EXPOSE 8080
CMD echo "The application will start in ${SLEEP}s..." && \
    sleep ${SLEEP} && \
    java  -Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom -jar /app.jar
