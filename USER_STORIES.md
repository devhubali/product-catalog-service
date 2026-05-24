# User Stories — Implemented & MVC Tested

The following user stories are implemented. Email verification and password reset are implemented but not covered by MockMvc tests (noted where applicable).

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

---

## Authentication

**US-A01 — Register an account**
> As a visitor, I want to register a new account so that I can access the platform.

- `POST /api/v1/auth/register` returns `201 Created` with a JWT token when the request is valid
- `POST /api/v1/auth/register` returns `409 Conflict` when the email is already taken
- A verification email is sent to the registered address on successful registration
- The user cannot log in until their email is verified

**US-A02 — Verify email address** _(implemented, no MockMvc test)_
> As a newly registered user, I want to verify my email address so that I can activate my account and log in.

- `GET /api/v1/auth/verify-email?token=<token>` returns `200 OK` and activates the account when the token is valid
- `GET /api/v1/auth/verify-email?token=<token>` returns `400 Bad Request` when the token is invalid or already used

**US-A03 — Log in**
> As a verified user, I want to log in with my email and password so that I receive a JWT token to access protected routes.

- `POST /api/v1/auth/login` returns `200 OK` with a JWT token when credentials are valid
- `POST /api/v1/auth/login` returns `400 Bad Request` when the email is not yet verified
- `POST /api/v1/auth/login` returns `401 Unauthorized` when credentials are wrong

**US-A04 — Forgot password** _(implemented, no MockMvc test)_
> As a user who forgot their password, I want to receive a password reset link by email so that I can recover my account.

- `POST /api/v1/auth/forgot-password` returns `200 OK` regardless of whether the email exists (to prevent account enumeration)
- A reset link containing a token is sent if the email matches an account
- The reset token expires after 15 minutes

**US-A05 — Reset password** _(implemented, no MockMvc test)_
> As a user with a reset token, I want to set a new password so that I can regain access to my account.

- `POST /api/v1/auth/reset-password` returns `200 OK` when the token is valid and not expired
- `POST /api/v1/auth/reset-password` returns `400 Bad Request` when the token is invalid or expired

**US-A06 — Change password**
> As an authenticated user, I want to change my password so that I can keep my account secure.

- `POST /api/v1/auth/change-password` returns `200 OK` when the current password is correct
- `POST /api/v1/auth/change-password` returns `400 Bad Request` when the current password is wrong
