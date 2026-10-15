# Web version deployment guide

The web app and API have been added on the `web-version` branch. They are not deployed automatically by these commits; hosting accounts and a provisioned MySQL database are still needed.

## 1. Create a managed MySQL database
Provision MySQL with your chosen provider. Create a database/schema and a dedicated user. Keep credentials private; enter them only in the hosting provider's secret/environment-variable settings.

## 2. Deploy the API
Deploy the `backend` directory as a Docker service using `backend/Dockerfile` (or build from `backend/pom.xml`). Set these environment variables:
- `DB_URL`: JDBC URL from the managed MySQL provider, beginning with `jdbc:mysql://`
- `DB_USERNAME`
- `DB_PASSWORD`
- `FRONTEND_ORIGIN`: exact HTTPS origin for the deployed Vercel website
- `COOKIE_SECURE=true`
- `PORT=8080` if your provider requires a fixed port

Once deployed, verify `https://YOUR-API-HOST/api/health` returns status ok.

## 3. Deploy the web UI to Vercel
Import this GitHub repository and select branch `web-version`. Set Root Directory to `web`. Add build environment variable:
- `VITE_API_BASE_URL=https://YOUR-API-HOST` (no trailing slash)

Deploy. Copy the final Vercel origin (scheme + host only) into the backend's `FRONTEND_ORIGIN`, then redeploy/restart the backend.

## 4. Smoke test
Create a demo account; securely note the generated 16-digit card number; sign out and sign back in; deposit a small test amount; confirm the balance and statement update; test a withdrawal larger than the balance is rejected.

## Safety note
This is a learning project. Do not use real customer data, actual banking credentials, or real money. It has not undergone a production security review. The legacy Swing application is separate and is not automatically migrated into the new web schema.
