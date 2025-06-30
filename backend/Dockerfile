# =========================
# Stage 1: Build stage
# =========================
FROM eclipse-temurin:21 AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew clean build -x test --no-daemon

COPY src ./src

RUN ./gradlew bootJar --no-daemon

# =========================
# Stage 2: Runtime stage
# =========================
FROM eclipse-temurin:21 AS jre-build

WORKDIR /app

# ✅ ใช้ stage ที่ตั้งชื่อไว้ว่า "build"
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
