# ATM Banking Web UI

Vite + React frontend for the Spring Boot API in ../backend.

## Local development
1. Start MySQL and configure the backend environment variables described in ../backend/README.md.
2. Start the API from ../backend with `mvn spring-boot:run` (default API origin http://localhost:8080).
3. Copy `.env.example` to `.env.local` and set `VITE_API_BASE_URL=http://localhost:8080`.
4. Run `npm install` then `npm run dev`.

## Vercel
Set the Root Directory to `web`. Add environment variable `VITE_API_BASE_URL` with the deployed backend base URL (no trailing slash), then deploy. The backend must allow the exact Vercel origin using its `FRONTEND_ORIGIN` setting. Deploy backend and provision MySQL separately.

This educational project is not suitable for real customer accounts, financial transactions, or production banking.
