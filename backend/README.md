# ATM Banking API

Spring Boot REST API for the web UI. This is an educational demo, not a production banking system.

## Required environment variables
- `DB_URL`: JDBC URL for a reachable MySQL database
- `DB_USERNAME`, `DB_PASSWORD`: database credentials
- `FRONTEND_ORIGIN`: exact deployed frontend origin, e.g. your Vercel URL
- `COOKIE_SECURE=true` when served over HTTPS
- `PORT`: hosting provider port (defaults to 8080)

JPA creates the `web_bank_accounts` and `web_bank_transactions` tables automatically. These are separate from the legacy Swing application's tables.

## Run locally
Install Java 21 and Maven, start MySQL, set the environment variables, then run:
`mvn spring-boot:run`

Health check: `GET /api/health`.

## API
- `POST /api/auth/signup` with `{ "name": "Your name", "pin": "1234" }` creates a demo account and returns its generated 16-digit card number.
- `POST /api/auth/login` with `{ "cardNumber": "16 digits", "pin": "4-6 digits" }`
- `POST /api/auth/logout`
- `GET /api/account`
- `GET /api/account/transactions`
- `POST /api/account/deposit` with `{ "amount": 100.00 }`
- `POST /api/account/withdraw` with `{ "amount": 100.00 }`
- `POST /api/account/pin` with `{ "currentPin": "1234", "newPin": "5678" }`

Session cookies are HTTP-only. Set the frontend origin explicitly and use HTTPS in hosting. Do not use real money or real customer data with this student demo. Production banking needs professional security review, audit controls, monitoring, and regulatory compliance.
