# User Stories — Implemented & MVC Tested

The following user stories are implemented and verified by MockMvc unit tests in the `src/test` directory.

---

## Brands

### Public

**US-B01 — Browse active brands**
> As a visitor, I want to retrieve a list of all active brands so that I can browse available brands.

- `GET /api/v1/brands` returns `200 OK` with a list of active brands
- `GET /api/v1/brands` returns `200 OK` with an empty list when no active brands exist

**US-B02 — View a brand by slug**
> As a visitor, I want to retrieve a single brand by its slug so that I can view brand details.

- `GET /api/v1/brands/{slug}` returns `200 OK` with the brand when the slug exists
- `GET /api/v1/brands/{slug}` returns `404 Not Found` when no brand matches the slug

---

### Admin

**US-B03 — List all brands**
> As an admin, I want to retrieve all brands regardless of status so that I can manage the full brand catalogue.

- `GET /api/v1/admin/brands` returns `200 OK` with a list of all brands

**US-B04 — Create a brand**
> As an admin, I want to create a new brand so that it appears in the catalogue.

- `POST /api/v1/admin/brands` returns `201 Created` with the created brand when the request is valid
- `POST /api/v1/admin/brands` returns `400 Bad Request` when the brand name is blank
- `POST /api/v1/admin/brands` returns `409 Conflict` when a brand with the same name already exists

**US-B05 — Update a brand**
> As an admin, I want to update an existing brand so that catalogue information stays accurate.

- `PUT /api/v1/admin/brands/{id}` returns `200 OK` with the updated brand when the request is valid
- `PUT /api/v1/admin/brands/{id}` returns `404 Not Found` when the brand does not exist

**US-B06 — Delete a brand**
> As an admin, I want to delete a brand so that discontinued brands are removed from the catalogue.

- `DELETE /api/v1/admin/brands/{id}` returns `200 OK` when the brand is successfully deleted
- `DELETE /api/v1/admin/brands/{id}` returns `404 Not Found` when the brand does not exist

---

## Categories

### Public

**US-C01 — Browse active categories**
> As a visitor, I want to retrieve a list of all active categories so that I can explore product groupings.

- `GET /api/v1/categories` returns `200 OK` with a list of active categories
- `GET /api/v1/categories` returns `200 OK` with an empty list when no active categories exist

**US-C02 — View a category by slug**
> As a visitor, I want to retrieve a single category by its slug so that I can view category details.

- `GET /api/v1/categories/{slug}` returns `200 OK` with the category when the slug exists
- `GET /api/v1/categories/{slug}` returns `404 Not Found` when no category matches the slug

---

### Admin

**US-C03 — List all categories**
> As an admin, I want to retrieve all categories regardless of status so that I can manage the full category tree.

- `GET /api/v1/admin/categories` returns `200 OK` with a list of all categories

**US-C04 — Create a category**
> As an admin, I want to create a new category so that products can be organised under it.

- `POST /api/v1/admin/categories` returns `201 Created` with the created category when the request is valid
- `POST /api/v1/admin/categories` returns `400 Bad Request` when the category name is blank
- `POST /api/v1/admin/categories` returns `409 Conflict` when a category with the same name already exists

**US-C05 — Update a category**
> As an admin, I want to update an existing category so that catalogue information stays accurate.

- `PUT /api/v1/admin/categories/{id}` returns `200 OK` with the updated category when the request is valid
- `PUT /api/v1/admin/categories/{id}` returns `404 Not Found` when the category does not exist

**US-C06 — Delete a category**
> As an admin, I want to delete a category so that unused categories are removed from the catalogue.

- `DELETE /api/v1/admin/categories/{id}` returns `200 OK` when the category is successfully deleted
- `DELETE /api/v1/admin/categories/{id}` returns `404 Not Found` when the category does not exist
