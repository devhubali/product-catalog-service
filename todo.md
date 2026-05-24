# todo

## Project Setup & Configuration
- [x] Create Git branch `dev`
- [ ] Confirm Spring Boot project runs locally
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
- [ ] Create `Brand` entity
- [ ] Create `BrandRepository`
- [ ] Create `BrandRequest` DTO
- [ ] Create `BrandResponse` DTO
- [ ] Create `BrandService` interface
- [ ] Create `BrandServiceImpl`
- [ ] Implement create brand
- [ ] Implement update brand
- [ ] Implement delete brand
- [ ] Implement get all brands
- [ ] Implement get brand by slug
- [ ] Create `BrandController`
- [ ] Add `GET /api/v1/brands`
- [ ] Add `GET /api/v1/brands/{slug}`
- [ ] Add `POST /api/v1/admin/brands`
- [ ] Add `PUT /api/v1/admin/brands/{id}`
- [ ] Add `DELETE /api/v1/admin/brands/{id}`

## Category API
- [ ] Create `Category` entity
- [ ] Add self-reference field `parentCategory`
- [ ] Create `CategoryRepository`
- [ ] Create `CategoryRequest` DTO
- [ ] Create `CategoryResponse` DTO
- [ ] Create `CategoryService` interface
- [ ] Create `CategoryServiceImpl`
- [ ] Implement create category
- [ ] Implement update category
- [ ] Implement delete category
- [ ] Implement get all categories
- [ ] Implement get category by slug
- [ ] Create `CategoryController`
- [ ] Add `GET /api/v1/categories`
- [ ] Add `GET /api/v1/categories/{slug}`
- [ ] Add `POST /api/v1/admin/categories`
- [ ] Add `PUT /api/v1/admin/categories/{id}`
- [ ] Add `DELETE /api/v1/admin/categories/{id}`

## Product API
- [ ] Create `Product` entity
- [ ] Create `ProductStatus` enum
- [ ] Add relation from product to brand
- [ ] Add relation from product to category
- [ ] Create `ProductRepository`
- [ ] Create `ProductRequest` DTO
- [ ] Create `ProductResponse` DTO
- [ ] Create `UpdateProductPriceRequest` DTO only if needed
- [ ] Create `ProductService` interface
- [ ] Create `ProductServiceImpl`
- [ ] Implement create product
- [ ] Implement update product
- [ ] Implement delete product
- [ ] Implement get all active products
- [ ] Implement get product by slug
- [ ] Implement basic product search by name or SKU
- [ ] Create `ProductController`
- [ ] Add `GET /api/v1/products`
- [ ] Add `GET /api/v1/products/{slug}`
- [ ] Add `GET /api/v1/products/search?keyword=`
- [ ] Add `POST /api/v1/admin/products`
- [ ] Add `PUT /api/v1/admin/products/{id}`
- [ ] Add `DELETE /api/v1/admin/products/{id}`

## Product Variant API
- [ ] Create `ProductVariant` entity
- [ ] Add relation from variant to product
- [ ] Create `ProductVariantRepository`
- [ ] Create `ProductVariantRequest` DTO
- [ ] Create `ProductVariantResponse` DTO
- [ ] Create `ProductVariantService` interface
- [ ] Create `ProductVariantServiceImpl`
- [ ] Implement create variant
- [ ] Implement update variant
- [ ] Implement delete variant
- [ ] Implement list variants by product
- [ ] Create `ProductVariantController`
- [ ] Add `GET /api/v1/products/{productId}/variants`
- [ ] Add `POST /api/v1/admin/products/{productId}/variants`
- [ ] Add `PUT /api/v1/admin/products/{productId}/variants/{variantId}`
- [ ] Add `DELETE /api/v1/admin/products/{productId}/variants/{variantId}`

## Product Image Upload API
- [ ] Create `ProductImage` entity
- [ ] Add relation from image to product
- [ ] Create `ProductImageRepository`
- [ ] Create `ProductImageResponse` DTO
- [ ] Create `ProductImageService` interface
- [ ] Create `ProductImageServiceImpl`
- [ ] Implement local file upload
- [ ] Save uploaded files under `/uploads/products/`
- [ ] Save image metadata in database
- [ ] Implement list images by product
- [ ] Implement delete product image
- [ ] Create `ProductImageController`
- [ ] Add `GET /api/v1/products/{productId}/images`
- [ ] Add `POST /api/v1/admin/products/{productId}/images/upload`
- [ ] Add `DELETE /api/v1/admin/products/{productId}/images/{imageId}`

## Testing
- [ ] Create `AuthControllerTest`
- [ ] Test register returns success
- [ ] Test login returns JWT
- [ ] Test `/me` without token returns unauthorized
- [ ] Create `BrandControllerTest`
- [ ] Test public brand list route
- [ ] Test admin create brand route
- [ ] Create `CategoryControllerTest`
- [ ] Test public category list route
- [ ] Test admin create category route
- [ ] Create `ProductControllerTest`
- [ ] Test public product list route
- [ ] Test product search route
- [ ] Test admin create product route
- [ ] Create `ProductImageControllerTest`
- [ ] Test image upload route with mock multipart file

## Docker & Deployment
- [ ] Create `Dockerfile`
- [ ] Create `docker-compose.yml`
- [x] Add PostgreSQL service to Docker Compose
- [x] Add app service to Docker Compose
- [x] Configure app to use docker profile
- [ ] Test app starts locally without Docker
- [ ] Test app starts using Docker Compose

## Manual Verification
- [ ] Test seeded admin user works
- [ ] Test public routes using Postman
- [ ] Test admin routes using JWT
- [ ] Confirm users can register and login
- [ ] Confirm login returns JWT
- [ ] Confirm admin routes are protected
- [ ] Confirm public users can browse products, brands, categories, variants, and images
- [ ] Confirm local image upload works
- [ ] Confirm PostgreSQL persistence works

## Documentation
- [ ] Add ERD image to repository
- [ ] Add route documentation
- [ ] Add routes table to README
- [ ] Add installation instructions to README
- [ ] Add Docker instructions to README
- [ ] Add seeded admin credentials to README
- [ ] Add known limitations to README
- [ ] Add future improvements section to README
- [ ] Add credits/references section to README


## Skipped for MVP / Future Improvements
- [ ] Attribute definitions
- [ ] Product price history
- [ ] Product audit logs
- [ ] Import jobs
- [ ] Import job errors
- [ ] Outbox events
- [ ] Advanced search filters
- [ ] Facets
- [ ] Autocomplete
- [ ] Admin dashboard
- [ ] Cloud image storage
