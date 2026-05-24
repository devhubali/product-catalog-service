# ─────────────────────────────────────────────
# Stage 1: Build the application
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml first so Docker can cache the dependency
# download layer separately from the source code.
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this layer is cached as long as pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -q

# Copy source code and build the JAR, skipping tests
COPY src src
RUN ./mvnw package -DskipTests -q

# ─────────────────────────────────────────────
# Stage 2: Run the application
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user for security
RUN addgroup -S catalog && adduser -S catalog -G catalog

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Create the uploads directory and give ownership to our app user
RUN mkdir -p uploads && chown -R catalog:catalog /app

USER catalog

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
