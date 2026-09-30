# AI Stock Intelligence Platform

A full-stack portfolio management application built as part of a hackathon. Users can create and manage stock portfolios, track live holdings, and view sector-allocation insights. The project is under active development.

---

## Architecture Overview

```
Browser (Next.js 16 / React 19)
        │  HTTP + JWT Bearer (Axios)
        ▼
Spring Boot 4.1 REST API  ──► PostgreSQL (local)
        │
        ├──► Financial Modeling Prep API  (live stock quotes)
        ├──► Alpha Vantage API            (stock overview / details)
        └──► SEC EDGAR                    (bulk company ticker import)
```

| Layer | Technology |
|---|---|
| Frontend | Next.js 16 (App Router), React 19, TypeScript |
| UI Components | Material UI v9, `@mui/x-charts` |
| HTTP Client | Axios (frontend → backend) |
| Styling | MUI Emotion + Tailwind CSS v4 |
| Backend | Spring Boot 4.1, Java 21 |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL 15+ |
| Auth | Spring Security + JWT (jjwt 0.13) |
| External HTTP | Spring WebFlux `WebClient` |
| Build | Maven (backend), npm (frontend) |

---

## Repository Structure

```
/
├── backend/          Spring Boot application
│   ├── src/main/java/…/
│   │   ├── AuthConfig/       Security filter chain, JWT filter, CORS, WebClient config
│   │   ├── Constants/        Shared constants (BCrypt strength, token offsets)
│   │   ├── Controller/       REST controllers + external API HTTP clients
│   │   ├── Dto/              Request and response DTOs
│   │   ├── Models/           JPA entities
│   │   ├── Projections/      JPA interface projections
│   │   ├── Repository/       Spring Data JPA repositories
│   │   └── Service/          Business logic services
│   └── src/main/resources/
│       └── application.properties
├── frontend/         Next.js application
│   ├── app/          App Router pages (login, create, portfolio, portfolioDetails)
│   ├── components/   Reusable React components
│   ├── services/     Axios service wrappers per domain
│   ├── api/          Axios client instance
│   └── types/        TypeScript interfaces
└── README.md
```

---

## Data Model

Five JPA entities map to PostgreSQL tables:

| Table | Purpose |
|---|---|
| `users` | Registered accounts (email unique, BCrypt password) |
| `userportfolio` | Named portfolios owned by a user (soft-deleted via `deletedAt`) |
| `stocks` | Stock universe (ticker, company name, sector, industry, exchange) |
| `stockscurrentprices` | Live price cache per stock (price, change, previousClose, timestamps) |
| `portfolioholdings` | A stock held within a portfolio (shares, avgPrice; soft-deleted) |

---

## Authentication & Authorization

- **Registration / Login** → returns a JWT string (30-hour expiry).
- JWT is validated on every request by `JwtFilter`; `userId` is extracted from the token and stored in the `SecurityContext`.
- All endpoints except `POST /Create` and `POST /Login` require a valid Bearer token.
- No role-based authorization; all authenticated users have equal access.

> ⚠️ Known issue: The JWT secret key is regenerated on every server restart, which invalidates all active tokens. See [Known Issues](#known-issues--limitations).

---

## Implemented Functionality

### Authentication
- User registration (email + password, BCrypt hashed)
- User login returning a JWT
- JWT-protected API routes

### Portfolio Management
- Create, rename, redescribe, and soft-delete portfolios
- Batch-delete multiple portfolios
- List all portfolios for the authenticated user with holding count
- Keyword search across portfolio name and description

### Holdings Management
- Add one or multiple holdings to a portfolio (ticker, shares, avg purchase price)
- Update shares or average price of an existing holding
- Soft-delete individual holdings or a list of holdings
- Server-side paginated holdings table with keyword search (ticker, company, sector, industry, exchange)
- Expandable table rows showing detailed holding metrics

### Stock Data
- Paginated stock search that excludes stocks already held in the target portfolio
- Lazy-fetch of stock details (sector, industry, exchange) from Alpha Vantage on first access
- Live price, previous close, daily change, and change % fetched from Financial Modeling Prep
- Price refresh: stale prices (>5 minutes) are re-fetched on demand

### Portfolio Analytics
- Real-time portfolio value, total invested, gain/loss ($ and %)
- Per-holding: current value, invested amount, gain/loss, allocation %
- Sector allocation pie chart (MUI x-charts PieChart)
- Portfolio insights panel:
  - Warning if any single sector > 60% of portfolio
  - Warning if any single stock > 40% of portfolio
  - Best- and lowest-performing holding
  - Count of holdings above vs below average purchase price

---

## Planned / Future Functionality

The following items are **not yet implemented**:

- AI-powered natural-language portfolio narrative (IBM watsonx integration)
- Real-time price streaming (WebSocket / SSE)
- Logout button (button renders but has no handler)
- Redirect from root `/` to `/login`
- Post-registration redirect to portfolio page
- Sorting of holdings table columns
- Portfolio export (CSV / PDF)
- Docker / containerised deployment
- CI/CD pipeline
- Token refresh mechanism
- Role-based authorization (admin vs user)

---

## Prerequisites

- Java 21+
- Maven 3.9+ (or use the included `mvnw` wrapper)
- Node.js 20+ and npm
- PostgreSQL 15+ running locally

---

## Environment Variables

The backend requires the following environment variables. **Do not commit actual values.**

| Variable | Description |
|---|---|
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `FMP_API_KEY` | Financial Modeling Prep API key (https://financialmodelingprep.com) |
| `ALPHAVANTAGE_API_KEY` | Alpha Vantage API key (https://www.alphavantage.co) |
| `SEC_API_HEADER_VALUE` | User-Agent header string required by SEC EDGAR (e.g. `YourName yourname@email.com`) |

Export them in your shell before starting the backend:

```bash
export DB_USERNAME=your_pg_user
export DB_PASSWORD=your_pg_password
export FMP_API_KEY=your_fmp_key
export ALPHAVANTAGE_API_KEY=your_av_key
export SEC_API_HEADER_VALUE="Your Name your@email.com"
```

The database must already exist:

```sql
CREATE DATABASE aistockintelligenceprotfolio;
```

---

## Running Locally

### Backend

```bash
cd backend
./mvnw spring-boot:run
# API available at http://localhost:8080
```

> The first run will let Hibernate create the schema automatically if DDL auto is configured. Verify `spring.jpa.hibernate.ddl-auto` is set appropriately in `application.properties` for your environment (`create` for first run, `update` thereafter).

### Frontend

```bash
cd frontend
npm install
npm run dev
# App available at http://localhost:3000
```

> The frontend Axios client is hardcoded to `http://localhost:8080`. Ensure the backend is running before starting the frontend.

---

## Known Issues / Limitations

| # | Issue | Severity |
|---|---|---|
| 1 | JWT secret key is regenerated on every server restart — all sessions invalidated | 🔴 Critical |
| 2 | JWT token stored in `localStorage` — vulnerable to XSS attacks | 🔴 Critical |
| 3 | Root page `/` is blank — no redirect to `/login` | 🟠 High |
| 4 | After registration the app does not redirect to the portfolio page | 🟠 High |
| 5 | Portfolios with zero holdings may not appear in the list due to a JPQL `LEFT JOIN` filter bug | 🟠 High |
| 6 | HTTP Basic auth is enabled alongside JWT (left over from Postman testing) | 🟠 High |
| 7 | Price cache re-fetch logic in `getStockMoreDetails` is inverted — re-fetches on almost every call | 🟠 High |
| 8 | Logout button renders on the portfolio page but has no click handler | 🟡 Medium |
| 9 | No database migration tooling (Flyway / Liquibase) — schema managed manually | 🟡 Medium |
| 10 | `Stocks` entity fields `sector`/`industry`/`exchange` declared non-nullable but SEC bulk import leaves them empty | 🟡 Medium |

---

## API Reference (Backend)

### Public
| Method | Path | Description |
|---|---|---|
| `POST` | `/Create` | Register a new user |
| `POST` | `/Login` | Login, returns JWT string |

### Authenticated (Bearer token required)
| Method | Path | Description |
|---|---|---|
| `GET` | `/myPortfolios?keyword=` | List user portfolios |
| `POST` | `/createPortfolio` | Create a portfolio |
| `PATCH` | `/editPortfolioDetails` | Rename / redescribe portfolio |
| `DELETE` | `/deletePortfolio` | Soft-delete a portfolio |
| `DELETE` | `/deletePortfoliosList` | Soft-delete multiple portfolios |
| `POST` | `/getAllHoldings?keyword=&page=&size=` | Get portfolio summary + holdings |
| `POST` | `/getAllHoldings/refresh` | Same, forces price refresh |
| `POST` | `/addStocks` | Add holdings list to portfolio |
| `PATCH` | `/updateHoldings` | Update holding shares/avgPrice |
| `DELETE` | `/removeHoldings` | Soft-delete a holding |
| `DELETE` | `/removeListOfHoldings` | Soft-delete list of holdings |
| `GET` | `/getStocks?keyword=&portfolioId=&page=` | Paginated stock search |
| `GET` | `/getStockDetails?ticker=` | Stock metadata |
| `GET` | `/getStockMoreDetails?ticker=` | Stock metadata + live price |
