# Step 1: Java 17 image use करो
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Jar file build होके target folder में आती है
ARG JAR_FILE=target/*.jar

# Step 3: Jar को container में copy करो
COPY ${JAR_FILE} app.jar

# Step 4: Port expose करो (Render dynamic PORT देगा)
EXPOSE 8080

# Step 5: Application run command
ENTRYPOINT ["java","-jar","/app.jar"]
