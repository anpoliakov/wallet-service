FROM maven:3.8.3-openjdk-17-slim
WORKDIR /app
COPY . .
RUN mvn package
CMD mvn exec:java -Dexec.mainClass="by.anpoliakov.Runner"
