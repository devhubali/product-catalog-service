# todo

## Project Setup & Configuration
- [x] Create Git branch `dev`
- [x] Confirm PostgreSQL dependency exists in `pom.xml`
- [x] Confirm Spring Security dependency exists
- [x] Confirm JWT dependency exists
- [x] Confirm validation dependency exists
- [x] Confirm Lombok dependency exists if using Lombok
- [x] Create `application-dev.properties`
- [x] Configure database connection in `application-dev.properties`
- [x] Configure upload folder path
- [x] Use Spring Profiles for environment settings

## Common Foundation
- [x] Create `ApiResponse<T>`
- [x] Create `PageResponse<T>`
- [x] Create `ResourceNotFoundException`
- [x] Create `DuplicateResourceException`
- [x] Create `UnauthorizedException`
- [x] Create `ForbiddenException`
- [x] Create `BadRequestException`
- [x] Create `GlobalExceptionHandler`
- [x] Add validation error handling
- [x] Create `SlugUtil`
- [x] Create `FileStorageService`
- [x] Add base audit fields `createdAt` and `updatedAt`
- [x] Return clear error messages
- [x] Gracefully handle exceptions

## Authentication, Users & Roles
- [x] Create `User` entity
- [x] Create `Role` entity
- [x] Create `UserStatus` enum
- [x] Create `UserRepository`
- [x] Create `RoleRepository`
- [x] Create `RegisterRequest` DTO
- [x] Create `LoginRequest` DTO
- [x] Create `AuthResponse` DTO
- [x] Create `UserResponse` DTO
- [x] Create `ChangePasswordRequest` DTO
- [x] Create `AuthService` interface
- [x] Create `AuthServiceImpl`
- [x] Implement `register()`
- [x] Implement `login()`
- [x] Implement `getCurrentUser()`
- [x] Implement `changePassword()`
- [x] Create `AuthController`
- [x] Add `POST /api/v1/auth/register`
- [x] Add `POST /api/v1/auth/login`
- [x] Add `GET /api/v1/auth/me`
- [x] Add `POST /api/v1/auth/change-password`
- [x] Create startup seeder for `ROLE_USER` and `ROLE_ADMIN`
- [x] Seed one admin user

## Security & JWT
- [x] Create `JwtService`
- [x] Create `UserDetailsServiceImpl`
- [x] Create `JwtAuthenticationFilter`
- [x] Create `SecurityConfig`
- [x] Permit public auth routes
- [x] Protect routes with JWT except login/register
- [x] Protect `/api/v1/admin/**` with `ROLE_ADMIN`
- [x] Include at least two user roles: `ROLE_USER` and `ROLE_ADMIN`

## Brand API
- [x] Create `Brand` entity
- [x] Create `BrandRepository`
- [x] Create `BrandRequest` DTO
- [x] Create `BrandResponse` DTO
- [x] Create `BrandService` interface
- [x] Create `BrandServiceImpl`
- [x] Implement create brand
- [x] Implement update brand
- [x] Implement delete brand
- [x] Implement get all brands
- [x] Implement get brand by slug
- [x] Create `BrandController`
- [x] Add `GET /api/v1/brands`
- [x] Add `GET /api/v1/brands/{slug}`
- [x] Add `POST /api/v1/admin/brands`
- [x] Add `PUT /api/v1/admin/brands/{id}`
- [x] Add `DELETE /api/v1/admin/brands/{id}`

## Category API
- [x] Create `Category` entity
- [x] Create `CategoryRepository`
- [x] Create `CategoryRequest` DTO
- [x] Create `CategoryResponse` DTO
- [x] Create `CategoryService` interface
- [x] Create `CategoryServiceImpl`
- [x] Implement create category
- [x] Implement update category
- [x] Implement delete category
- [x] Implement get all categories
- [x] Implement get category by slug
- [x] Create `CategoryController`
- [x] Add `GET /api/v1/categories`
- [x] Add `GET /api/v1/categories/{slug}`
- [x] Add `POST /api/v1/admin/categories`
- [x] Add `PUT /api/v1/admin/categories/{id}`
- [x] Add `DELETE /api/v1/admin/categories/{id}`

## Product API
- [x] Create `Product` entity
- [x] Create `ProductStatus` enum
- [x] Add relation from product to brand
- [x] Add relation from product to category
- [x] Create `ProductRepository`
- [x] Create `ProductRequest` DTO
- [x] Create `ProductResponse` DTO
- [x] Create `ProductService` interface
- [x] Create `ProductServiceImpl`
- [x] Implement create product
- [x] Implement update product
- [x] Implement delete product
- [x] Implement get all active products
- [x] Implement get product by slug
- [x] Create `ProductController`
- [x] Add `GET /api/v1/products`
- [x] Add `GET /api/v1/products/{slug}`
- [x] Add `POST /api/v1/admin/products`
- [x] Add `PUT /api/v1/admin/products/{id}`
- [x] Add `DELETE /api/v1/admin/products/{id}`

## Product Variant API
- [x] Create `Variant` entity
- [x] Add relation from variant to product
- [x] Create `VariantRepository`
- [x] Create `VariantRequest` DTO
- [x] Create `VariantResponse` DTO
- [x] Create `VariantService` interface
- [x] Create `VariantServiceImpl`
- [x] Implement create variant
- [x] Implement update variant
- [x] Implement delete variant
- [x] Implement list variants by product
- [x] Create `VariantController`
- [x] Add `GET /api/v1/products/{productId}/variants`
- [x] Add `GET /api/v1/products/{productId}/variants/{id}`
- [x] Add `POST /api/v1/admin/variants`
- [x] Add `PUT /api/v1/admin/variants/{id}`
- [x] Add `DELETE /api/v1/admin/variants/{id}`

## Image Upload API
- [x] Create `ImageUploadResponse` DTO
- [x] Create `ImageService` interface
- [x] Create `ImageServiceImpl`
- [x] Create `ImageController`
- [x] Add `POST /api/v1/admin/images/upload`
- [x] Add `DELETE /api/v1/admin/images/{filename}`
- [x] Add `GET /api/v1/images/{filename}`

## Testing
- [x] Create `BrandControllerTest`
- [x] Test public brand list route
- [x] Test public brand get by slug route
- [x] Test brand not found returns 404
- [x] Test admin get all brands
- [x] Test admin create brand returns 201
- [x] Test admin create brand with blank name returns 400
- [x] Test admin create duplicate brand returns 409
- [x] Test admin update brand returns 200
- [x] Test admin update brand not found returns 404
- [x] Test admin delete brand returns 200
- [x] Test admin delete brand not found returns 404
- [x] Create `CategoryControllerTest`
- [x] Test public category list route
- [x] Test public category get by slug route
- [x] Test category not found returns 404
- [x] Test admin get all categories
- [x] Test admin create category returns 201
- [x] Test admin create category with blank name returns 400
- [x] Test admin create duplicate category returns 409
- [x] Test admin update category returns 200
- [x] Test admin update category not found returns 404
- [x] Test admin delete category returns 200
- [x] Test admin delete category not found returns 404

## Docker & Deployment
- [x] Create `Dockerfile`
- [x] Create `docker-compose.yml`
- [x] Add PostgreSQL service to Docker Compose
- [x] Add app service to Docker Compose
- [x] Configure app to use docker profile

## Documentation
- [x] Add routes table to README
- [x] Add installation instructions to README
- [x] Add Docker instructions to README
- [x] Add seeded admin credentials to README
