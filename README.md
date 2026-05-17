# Product Catalog Service

## Project Overview
A service for maintianing and retreiving products.

---

# Routes Table

## Public Routes

| Method | Route | Purpose |
|---|---|---|
| GET | `/api/v1/brands` | List all brands |
| GET | `/api/v1/brands/{slug}` | Get brand by slug |
| GET | `/api/v1/categories` | List all categories |
| GET | `/api/v1/categories/tree` | Get category hierarchy/tree |
| GET | `/api/v1/categories/{slug}` | Get category by slug |
| GET | `/api/v1/products` | List active products |
| GET | `/api/v1/products/{slug}` | Get product details by slug |
| GET | `/api/v1/products/search` | Search products |
| GET | `/api/v1/products/hot` | Get hot/popular products |
| GET | `/api/v1/products/{productId}/variants` | Get product variants |
| GET | `/api/v1/products/{productId}/images` | Get product images |
| GET | `/api/v1/search` | General product search |
| GET | `/api/v1/search/autocomplete` | Search autocomplete |
| GET | `/api/v1/search/facets` | Search facets/filters |

## Admin Routes

### Brand Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/brands` | Create brand |
| PUT | `/api/v1/admin/brands/{id}` | Update brand |
| DELETE | `/api/v1/admin/brands/{id}` | Delete brand |

### Category Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/categories` | Create category |
| PUT | `/api/v1/admin/categories/{id}` | Update category |
| DELETE | `/api/v1/admin/categories/{id}` | Delete category |

### Product Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/products` | Create product |
| PUT | `/api/v1/admin/products/{id}` | Update product |
| PATCH | `/api/v1/admin/products/{id}/price` | Update product price |
| PATCH | `/api/v1/admin/products/{id}/status` | Update product status |
| DELETE | `/api/v1/admin/products/{id}` | Delete product |

### Product Variant Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/products/{productId}/variants` | Create product variant |
| PUT | `/api/v1/admin/products/{productId}/variants/{variantId}` | Update product variant |
| PATCH | `/api/v1/admin/products/{productId}/variants/{variantId}/price` | Update variant price |
| PATCH | `/api/v1/admin/products/{productId}/variants/{variantId}/stock` | Update variant stock |
| DELETE | `/api/v1/admin/products/{productId}/variants/{variantId}` | Delete product variant |

### Product Image Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/products/{productId}/images` | Add product image metadata |
| POST | `/api/v1/admin/products/{productId}/images/upload-url` | Generate pre-signed upload URL |
| POST | `/api/v1/admin/products/{productId}/images/confirm` | Confirm image upload |
| PATCH | `/api/v1/admin/products/{productId}/images/{imageId}/main` | Set image as main image |
| DELETE | `/api/v1/admin/products/{productId}/images/{imageId}` | Delete product image |

### Attribute Definition Management

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/categories/{categoryId}/attributes` | Create category attribute definition |
| GET | `/api/v1/admin/categories/{categoryId}/attributes` | List category attribute definitions |
| PUT | `/api/v1/admin/attributes/{attributeId}` | Update attribute definition |
| DELETE | `/api/v1/admin/attributes/{attributeId}` | Delete attribute definition |

### Bulk Import

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/admin/products/import` | Start product import |
| GET | `/api/v1/admin/import-jobs/{jobId}` | Get import job status |
| GET | `/api/v1/admin/import-jobs/{jobId}/errors` | Get import job row errors |

### Audit Logs

| Method | Route | Purpose |
|---|---|---|
| GET | `/api/v1/admin/audit-logs` | View all audit logs |
| GET | `/api/v1/admin/products/{productId}/audit-logs` | View product-specific audit logs |

## Auth Routes

| Method | Route | Purpose |
|---|---|---|
| POST | `/api/v1/auth/register` | Register user/admin |
| POST | `/api/v1/auth/login` | Login and receive JWT |
| GET | `/api/v1/auth/me` | Get current authenticated user |

---

# User Stories

## 1. Public User / Customer Stories

| ID | User Story |
|---|---|
| US-001 | As a public user, I want to view all available brands so that I can browse products by brand. |
| US-002 | As a public user, I want to view a brand by its slug so that I can see details about a specific brand. |
| US-003 | As a public user, I want to view all categories so that I can browse products by category. |
| US-004 | As a public user, I want to view categories as a tree so that I can understand the category hierarchy. |
| US-005 | As a public user, I want to view a category by its slug so that I can see products under that category. |
| US-006 | As a public user, I want to view active products so that I can browse the catalog. |
| US-007 | As a public user, I want to view product details by slug so that I can understand the product before buying. |
| US-008 | As a public user, I want to view products by category so that I can find items in a specific section. |
| US-009 | As a public user, I want to view products by brand so that I can find products from a specific brand. |
| US-010 | As a public user, I want to view hot products so that I can discover popular items. |
| US-011 | As a public user, I want to view product variants so that I can choose between options like size, color, storage, or RAM. |
| US-012 | As a public user, I want to see variant price and stock availability so that I know which option is available. |
| US-013 | As a public user, I want to view product images so that I can visually inspect the product. |
| US-014 | As a public user, I want to see the main product image first so that I can quickly recognize the product. |
| US-015 | As a public user, I want to search products by keyword so that I can quickly find what I need. |
| US-016 | As a public user, I want autocomplete suggestions so that I can search faster. |
| US-017 | As a public user, I want to filter search results by brand, category, price, and attributes so that I can narrow down results. |
| US-018 | As a public user, I want fuzzy search so that I can still find products even if I mistype the name. |

---

## 2. Admin / Catalog Manager Stories

### Brand Management

| ID | User Story |
|---|---|
| US-019 | As an admin, I want to create a brand so that products can be linked to it. |
| US-020 | As an admin, I want to update a brand so that I can correct or improve brand information. |
| US-021 | As an admin, I want to delete a brand so that unused or incorrect brands can be removed. |
| US-022 | As an admin, I want brand names and slugs to be unique so that the catalog remains clean and consistent. |

### Category Management

| ID | User Story |
|---|---|
| US-023 | As an admin, I want to create a category so that products can be organized. |
| US-024 | As an admin, I want to create parent-child category relationships so that the catalog supports hierarchy. |
| US-025 | As an admin, I want to update a category so that I can change its name, slug, description, or parent. |
| US-026 | As an admin, I want to delete a category so that unused categories can be removed. |
| US-027 | As an admin, I want category slugs to be unique so that categories can be safely accessed by URL. |

### Product Management

| ID | User Story |
|---|---|
| US-028 | As an admin, I want to create a product so that it becomes part of the catalog. |
| US-029 | As an admin, I want to assign a product to a brand and category so that it is properly organized. |
| US-030 | As an admin, I want to update product details so that product information stays accurate. |
| US-031 | As an admin, I want to update product price separately so that price changes are easy to manage. |
| US-032 | As an admin, I want to update product status so that I can control whether a product is draft, active, inactive, or archived. |
| US-033 | As an admin, I want to soft-delete products so that product history is preserved. |
| US-034 | As an admin, I want product SKUs to be unique so that each product can be identified clearly. |
| US-035 | As an admin, I want product slugs to be unique so that each product has a stable public URL. |
| US-036 | As an admin, I want to store flexible product attributes using JSONB so that different categories can have different attributes. |

### Product Variant Management

| ID | User Story |
|---|---|
| US-037 | As an admin, I want to create product variants so that one product can have multiple options. |
| US-038 | As an admin, I want to update product variants so that variant details stay correct. |
| US-039 | As an admin, I want to update variant prices separately so that each option can have its own price. |
| US-040 | As an admin, I want to update variant stock separately so that availability is accurate. |
| US-041 | As an admin, I want to delete product variants so that invalid options are removed. |
| US-042 | As an admin, I want variant SKUs to be unique so that every sellable option can be identified. |

### Product Image Management

| ID | User Story |
|---|---|
| US-043 | As an admin, I want to add product image metadata so that products can display images. |
| US-044 | As an admin, I want to generate a pre-signed upload URL so that images can be uploaded directly to S3 or MinIO. |
| US-045 | As an admin, I want to confirm image upload so that only successfully uploaded images are linked to products. |
| US-046 | As an admin, I want to set one image as the main image so that the product has a primary display image. |
| US-047 | As an admin, I want to delete product images so that outdated or wrong images can be removed. |
| US-048 | As an admin, I want to store image metadata in PostgreSQL but not image binary data so that the database stays lightweight. |

### Dynamic Attribute Management

| ID | User Story |
|---|---|
| US-049 | As an admin, I want to define valid attributes for each category so that product data is consistent. |
| US-050 | As an admin, I want to define attribute data types so that values can be validated correctly. |
| US-051 | As an admin, I want to mark some attributes as required so that important product data is always provided. |
| US-052 | As an admin, I want to define allowed values for attributes so that invalid values are rejected. |
| US-053 | As an admin, I want product and variant attributes to be validated against category definitions so that the catalog stays clean. |

---

## 3. Authentication and Authorization Stories

| ID | User Story |
|---|---|
| US-054 | As a user, I want to register an account so that I can access protected features. |
| US-055 | As a user, I want to log in using my email and password so that I can receive a JWT token. |
| US-056 | As an authenticated user, I want to view my profile so that I can confirm my account information. |
| US-057 | As a system, I want passwords to be stored as hashes so that user credentials are protected. |
| US-058 | As a system, I want public endpoints to be accessible without authentication so that customers can browse the catalog. |
| US-059 | As a system, I want admin endpoints to require JWT authentication so that only authenticated users can modify catalog data. |
| US-060 | As a system, I want admin endpoints to require `ROLE_ADMIN` or `ROLE_CATALOG_MANAGER` so that only authorized users can manage catalog data. |

---

## 4. Search and Filtering Stories

| ID | User Story |
|---|---|
| US-061 | As a public user, I want basic PostgreSQL search in the MVP so that I can search products early in the project. |
| US-062 | As a public user, I want advanced search using Elasticsearch or OpenSearch so that search results are faster and more relevant. |
| US-063 | As a public user, I want search filters by brand so that I can narrow results to a specific brand. |
| US-064 | As a public user, I want search filters by category so that I can narrow results to a specific category. |
| US-065 | As a public user, I want search filters by price range so that I can find products within my budget. |
| US-066 | As a public user, I want search filters by dynamic attributes so that I can filter based on category-specific properties. |
| US-067 | As a system, I want to fall back to PostgreSQL search if Elasticsearch/OpenSearch is unavailable so that search still works. |

---

## 5. Caching Stories

| ID | User Story |
|---|---|
| US-068 | As a system, I want to cache product details by ID so that repeated product requests are faster. |
| US-069 | As a system, I want to cache product details by slug so that public product pages load faster. |
| US-070 | As a system, I want to cache hot products so that popular product lists are served quickly. |
| US-071 | As a system, I want to evict product cache after updates so that users do not see stale data. |
| US-072 | As a system, I want to evict product cache after deletion so that removed products are not shown. |

---

## 6. Event and Integration Stories

| ID | User Story |
|---|---|
| US-073 | As a system, I want to publish a product created event so that other parts of the system can react asynchronously. |
| US-074 | As a system, I want to publish a product updated event so that search indexes and caches can be synchronized. |
| US-075 | As a system, I want to publish a product deleted event so that dependent systems can remove or update product data. |
| US-076 | As a system, I want Kafka to handle product events so that processing is asynchronous and scalable. |
| US-077 | As a system, I want product events to update the search index so that search results stay current. |
| US-078 | As a system, I want product events to evict Redis cache so that cached data stays accurate. |

---

## 7. Outbox Pattern Stories

| ID | User Story |
|---|---|
| US-079 | As a system, I want to save events in an outbox table inside the same database transaction as product changes so that events are not lost. |
| US-080 | As a system, I want a scheduled outbox publisher so that pending events are published to Kafka reliably. |
| US-081 | As a system, I want to track outbox event status so that I know whether events are pending, published, or failed. |
| US-082 | As a system, I want retry handling for failed outbox events so that temporary failures can be recovered. |
| US-083 | As a system, I want to store event payloads as JSONB so that event data is flexible and easy to publish. |

---

## 8. Audit Logging Stories

| ID | User Story |
|---|---|
| US-084 | As an admin, I want product changes to be logged so that I can track who changed what. |
| US-085 | As an admin, I want price changes to be logged so that pricing history is traceable. |
| US-086 | As an admin, I want status changes to be logged so that product lifecycle changes are auditable. |
| US-087 | As an admin, I want old and new values to be stored so that I can compare changes. |
| US-088 | As an admin, I want to view audit logs for all products so that I can monitor catalog activity. |
| US-089 | As an admin, I want to view audit logs for a specific product so that I can investigate its history. |

---

## 9. Bulk Import Stories

| ID | User Story |
|---|---|
| US-090 | As an admin, I want to import products from CSV so that I can add many products at once. |
| US-091 | As an admin, I want to import products from JSON so that I can support structured import files. |
| US-092 | As an admin, I want large import files to be processed without loading the entire file into memory so that the system remains stable. |
| US-093 | As an admin, I want to view import job status so that I know whether an import is running, completed, or failed. |
| US-094 | As an admin, I want to see total, successful, and failed row counts so that I can evaluate import results. |
| US-095 | As an admin, I want to view row-level import errors so that I can fix failed records. |

---

## 10. Reliability and Operations Stories

| ID | User Story |
|---|---|
| US-096 | As a system, I want circuit breakers for external dependencies so that failures do not cascade. |
| US-097 | As a system, I want rate limiting so that APIs are protected from abuse. |
| US-098 | As a developer, I want health checks through Spring Boot Actuator so that I can monitor the application. |
| US-099 | As a developer, I want consistent error responses so that API errors are easy to understand. |
| US-100 | As a developer, I want request validation so that invalid input is rejected early. |
| US-101 | As a developer, I want pagination for list endpoints so that large result sets are handled efficiently. |

---

## 11. Developer / Engineering Stories

| ID | User Story |
|---|---|
| US-102 | As a developer, I want a clean package structure so that the codebase is easy to navigate. |
| US-103 | As a developer, I want controllers to stay thin so that HTTP handling is separated from business logic. |
| US-104 | As a developer, I want business logic inside services so that behavior is reusable and testable. |
| US-105 | As a developer, I want repositories to handle database access so that persistence logic is isolated. |
| US-106 | As a developer, I want DTOs instead of exposing entities directly so that API contracts are stable. |
| US-107 | As a developer, I want mappers to convert between DTOs, entities, events, and search documents so that transformation logic is centralized. |
| US-108 | As a developer, I want Flyway migrations so that database changes are version-controlled. |
| US-109 | As a developer, I want Docker Compose for local dependencies so that the project is easy to run locally. |
| US-110 | As a developer, I want unit tests so that business logic is verified. |
| US-111 | As a developer, I want integration tests with Testcontainers so that database and infrastructure behavior is tested realistically. |

---

# MVP User Stories

| ID | User Story |
|---|---|
| MVP-001 | As an admin, I want to create, update, delete, and list brands. |
| MVP-002 | As an admin, I want to create, update, delete, and list categories. |
| MVP-003 | As an admin, I want to create, update, delete, and list products. |
| MVP-004 | As an admin, I want to assign products to brands and categories. |
| MVP-005 | As an admin, I want to create and manage product variants. |
| MVP-006 | As an admin, I want to add and manage product image metadata. |
| MVP-007 | As a public user, I want to list active products. |
| MVP-008 | As a public user, I want to view product details. |
| MVP-009 | As a public user, I want to search products using basic PostgreSQL search. |
| MVP-010 | As a developer, I want PostgreSQL and Flyway configured before building the core modules. |
| MVP-011 | As a developer, I want DTOs, services, repositories, and mappers for each core module. |
| MVP-012 | As a developer, I want pagination and validation in the MVP APIs. |


# ERD
![ERD](images/ERD.png)
