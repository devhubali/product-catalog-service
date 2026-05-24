# Product Catalog Service

A REST API service for managing and retrieving a product catalog, built with Spring Boot.

---

## General Approach

The codebase is organised around a feature-based package structure, each domain (auth, brand, category, product, variant, attribute, image) lives in its own package and owns its full vertical slice: entity, repository, DTOs, service interface, service implementation, and controller. This keeps related code together and makes each feature easy to navigate independently.

The design follows a strict separation of concerns. Controllers only handle HTTP concerns (request mapping, validation, response wrapping). All business logic lives in service implementations, which are hidden behind interfaces to keep consumers decoupled and testable. JPA repositories handle persistence, and a shared `BaseEntity` provides `id`, `createdAt`, and `updatedAt` to every entity via Spring Data auditing.

Security uses a stateless JWT filter chain. A custom `JwtAuthenticationFilter` intercepts every request, validates the token, and populates the `SecurityContext`. Route access is declared centrally in `SecurityConfig` using `permitAll`, `authenticated`, and `hasRole("ADMIN")` rules. Email verification adds a pre-login gate, users must confirm their address before a JWT is issued.

---

## Unsolved Problems

- **Email sender restriction** — Resend's `onboarding@resend.dev` address can only send to the account owner's email. A custom verified domain is needed to send to any address.
- **No refresh tokens** — JWT tokens expire after 24 hours and there is no refresh mechanism. Users must log in again after expiry.
- **Images are stored locally** — uploaded files are saved to the server's filesystem. This means images are lost if the container is restarted without a mounted volume, and does not scale across multiple instances.

---

## Tech Stack

- Java 17, Spring Boot 4.0.6
- PostgreSQL (AWS RDS)
- Spring Security 7 + JWT
- Lombok, Jakarta Validation
- Resend (transactional email)

---

## Setup

### Run locally (dev profile — port 8081)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Run with Docker Compose (port 5000)
```bash
docker-compose up --build
```
This starts the app and a PostgreSQL container. The app will be available at `http://localhost:5000`.

### Build JAR
```bash
mvn clean package -DskipTests
java -jar target/catalog-0.0.1-SNAPSHOT.jar
```

### Run tests
```bash
mvn test
```

### Default credentials (seeded on startup)
- **Email:** `admin@catalog.com`
- **Password:** `admin123`

### Note for testers — Resend API key

> The `resend.api-key` in `application-dev.properties` is set to the project owner's key.
> **Replace it with your own key before testing email features** (registration verification email, forgot password).
>
> 1. Create a free account at [resend.com](https://resend.com)
> 2. Generate an API key from the dashboard
> 3. Update `application-dev.properties`:
>    ```properties
>    resend.api-key=re_YOUR_KEY_HERE
>    resend.from-address=onboarding@resend.dev
>    ```
> 4. The `onboarding@resend.dev` sender only delivers to the email address registered on your Resend account.
>    To send to any address, add and verify a custom domain in the Resend dashboard.

---

## API Routes

### Auth

| Method | Route | Auth |
|---|---|---|
| POST | `/api/v1/auth/register` | Public |
| POST | `/api/v1/auth/login` | Public |
| GET | `/api/v1/auth/verify-email?token=` | Public |
| POST | `/api/v1/auth/forgot-password` | Public |
| POST | `/api/v1/auth/reset-password` | Public |
| GET | `/api/v1/auth/me` | JWT |
| POST | `/api/v1/auth/change-password` | JWT |

### Brands

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/brands` | Public |
| GET | `/api/v1/brands/{slug}` | Public |
| POST | `/api/v1/admin/brands` | ADMIN |
| PUT | `/api/v1/admin/brands/{id}` | ADMIN |
| DELETE | `/api/v1/admin/brands/{id}` | ADMIN |

### Categories

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/categories` | Public |
| GET | `/api/v1/categories/{slug}` | Public |
| POST | `/api/v1/admin/categories` | ADMIN |
| PUT | `/api/v1/admin/categories/{id}` | ADMIN |
| DELETE | `/api/v1/admin/categories/{id}` | ADMIN |

### Products

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/products` | Public |
| GET | `/api/v1/products/{slug}` | Public |
| POST | `/api/v1/admin/products` | ADMIN |
| PUT | `/api/v1/admin/products/{id}` | ADMIN |
| DELETE | `/api/v1/admin/products/{id}` | ADMIN |

### Variants

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/products/{productId}/variants` | Public |
| POST | `/api/v1/admin/products/{productId}/variants` | ADMIN |
| PUT | `/api/v1/admin/products/{productId}/variants/{variantId}` | ADMIN |
| DELETE | `/api/v1/admin/products/{productId}/variants/{variantId}` | ADMIN |

### Attributes

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/products/{productId}/attributes` | Public |
| POST | `/api/v1/admin/products/{productId}/attributes` | ADMIN |
| PUT | `/api/v1/admin/products/{productId}/attributes/{attributeId}` | ADMIN |
| DELETE | `/api/v1/admin/products/{productId}/attributes/{attributeId}` | ADMIN |

### Images

| Method | Route | Auth |
|---|---|---|
| GET | `/api/v1/images/{subfolder}/{filename}` | Public |
| POST | `/api/v1/admin/images/upload` | ADMIN |
| DELETE | `/api/v1/admin/images?path=...` | ADMIN |

---

## Documentation

- [User Stories](USER_STORIES.md)
- [Task Planning](todo.md)

---

## ERD
![ERD](images/ERD.png)
