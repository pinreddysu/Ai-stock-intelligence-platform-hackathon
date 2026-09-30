# AI Stock Intelligence Platform — Shared Project Context

> Authoritative context for IBM Bob sessions on this project.
> Keep current after every meaningful session.
> Legend: ✅ Confirmed | ⚠️ Partially Implemented | 🔲 Planned/Future | ❓ Unknown

---

## Project Purpose

A full-stack portfolio tracking application. Users register, create named portfolios, add stock holdings (ticker, shares, avg purchase price), and view live analytics (portfolio value, gain/loss, sector allocation, performance insights). The name "AI Stock Intelligence Platform" anticipates an IBM watsonx integration that is **not yet implemented**. Built during a hackathon; under active development.

---

## Repository Structure

```
/
├── README.md
├── backend/                        Spring Boot 4.1 (Java 21)
│   ├── pom.xml
│   └── src/main/java/com/ai_stock_intelligience/aiStockIntelligenceBackend/
│       ├── AiStockIntelligenceBackendApplication.java      Entry point
│       ├── AuthConfig/
│       │   ├── AuthSecurityConfig.java     Security filter chain, BCrypt bean, JWT wiring, CORS
│       │   ├── CorsConfig.java             WebMvcConfigurer CORS (⚠️ duplicates SecurityConfig CORS)
│       │   ├── JwtFilter.java              OncePerRequestFilter — validates Bearer, populates SecurityContext
│       │   ├── WebClientConfig.java         Bean: WebClient.Builder (15 MB in-memory buffer)
│       │   └── SecurityPrincipals/
│       │       └── UserPrincipal.java       implements UserDetails; adds getUserId() for JWT claims
│       ├── Constants/
│       │   └── UserConstants.java           encryptionStrength=12, extractTokenStart=7
│       ├── Controller/
│       │   ├── UserAuthController.java          POST /Create, POST /Login, GET /Greet (dead)
│       │   ├── StocksController.java             GET /getStocks, /getStockDetails, /getStockMoreDetails
│       │   ├── UserPortfolioController.java      Portfolio CRUD endpoints
│       │   ├── PortfolioHoldingsController.java  Holdings CRUD + portfolio summary endpoints
│       │   ├── ImportStockPriceClient.java        WebClient → FMP stable/quote (⚠️ misplaced in Controller/)
│       │   ├── ImportStockDetailsClient.java      WebClient → Alpha Vantage OVERVIEW (⚠️ misplaced)
│       │   └── ImportStocksDataClient.java        WebClient → SEC EDGAR company_tickers.json (⚠️ misplaced)
│       ├── Dto/
│       │   ├── StockDetailsDto.java        Alpha Vantage OVERVIEW response (PascalCase fields)
│       │   ├── StockImportDto.java          SEC EDGAR entry (⚠️ uses com.fasterxml.jackson — inconsistent with tools.jackson)
│       │   ├── StockPricesDto.java          FMP quote response (price, change, changePercentage, previousClose, timestamp as Long epoch)
│       │   ├── Requests/
│       │   │   ├── LoginRequestDto.java                  email (@Email @NotBlank), password (@NotBlank @Size 8-30)
│       │   │   ├── RegisterRequestDto.java                firstName/lastName (@NotBlank @Size 2-24), email, password
│       │   │   ├── CreatePortfolioRequestDto.java         portfolioName (@NotBlank @Size 2-50), description (@Size max 255)
│       │   │   ├── UpdatePortfolioRequestDto.java         id (@NotNull @Positive), portfolioName/description (optional, sized)
│       │   │   ├── DeletePortfolioRequestDto.java         id (@NotNull @Positive)
│       │   │   ├── DeletePortfoliosListRequestDto.java    ids List<Long> (@NotEmpty)
│       │   │   ├── GetPortfolioHoldingsDto.java           portfolioId (@NotNull @Positive)
│       │   │   ├── AddHoldingsToPortfolioDto.java         portfolioId, stockId, ticker (@NotEmpty), shares/avgPrice (@PositiveOrZero)
│       │   │   ├── UpdateHoldingRequestDto.java           id (@NotNull), shares/avgPrice (@Positive, both optional)
│       │   │   ├── RemoveHoldingRequestDto.java           id (@NotNull @Positive)
│       │   │   └── RemoveHoldingsListRequestDto.java      ids List<Long> (@NotEmpty)
│       │   └── Response/
│       │       ├── BasicStockDto.java              totalElements (Long) + List<StockList{id,ticker,companyName}>
│       │       ├── PortfolioInfoDto.java            Interface projection: id, portfolioName, description, portfolioHoldings
│       │       ├── PortfolioDetailsDto.java         Full portfolio response — nested HoldingsDto, SectorAllocationDto, MaxStockAllocation
│       │       ├── PortfolioValueAndUpdatedAtDto.java   Internal DTO: prices, updatedAt, marketTimeStamp (used in portfolioStats)
│       │       ├── StocksDto.java                  Interface projection: id, ticker, companyName, sector, exchange, industry
│       │       └── StockMoreDetailsDto.java          Full stock + live price fields → /getStockMoreDetails
│       ├── Models/
│       │   ├── Users.java              users table
│       │   ├── UserPortfolio.java      userportfolio table (soft-delete)
│       │   ├── Stocks.java             stocks table
│       │   ├── StockCurrentPrices.java stockscurrentprices table (price cache)
│       │   └── PortfolioHoldings.java  portfolioholdings table (soft-delete)
│       ├── Projections/
│       │   └── AuthenticationUserIdProjection.java   getId() only — passed to JwtService.generateToken()
│       ├── Repository/
│       │   ├── UserRepository.java                  findByEmail, findProjectedByEmail
│       │   ├── UserPortfolioRepository.java          CRUD + JPQL keyword search + holding count
│       │   ├── StocksRepository.java                 findByTicker + paginated JPQL search
│       │   ├── PortfolioHoldingsRepository.java      CRUD + 2x JPQL getPortfolioHoldings + window-fn allocation queries
│       │   └── StockCurrentPricesRepository.java     findByStocksId
│       └── Service/
│           ├── MyUserDetailsService.java     UserDetailsService impl; loadUserByUsername → UserPrincipal
│           ├── AuthenticationService.java    getCurrentUserId() from SecurityContext
│           ├── JwtService.java               Generate/validate JWT (⚠️ random secret on restart)
│           ├── UserAuthService.java          register + login → JWT
│           ├── UserPortfolioService.java      Portfolio CRUD
│           ├── PortfolioHoldingsService.java  Holdings CRUD + portfolioSummary aggregation
│           ├── StocksService.java             Search + detail + lazy import trigger
│           ├── ImportStockDetailsService.java SEC bulk import (admin, commented-out endpoint)
│           ├── ImportStocksDetailsService.java Alpha Vantage lazy single-ticker detail fetch
│           └── ImportStockPriceService.java   FMP quote fetch → upserts StockCurrentPrices
│   └── src/main/resources/
│       └── application.properties           DB url + 5 env-var placeholders (no DDL auto set)
│   └── src/test/…/
│       └── AiStockIntelligenceBackendApplicationTests.java   context load only — no business logic tests
├── frontend/                               Next.js 16 App Router (TypeScript)
│   ├── package.json
│   ├── next.config.ts                      Empty — no custom config
│   ├── tsconfig.json                       strict mode, @/* path alias → ./*, ES2017 target
│   ├── eslint.config.mjs                   eslint-config-next (core-web-vitals + typescript)
│   ├── postcss.config.mjs                  @tailwindcss/postcss plugin
│   ├── app/
│   │   ├── layout.tsx                      Root layout — wraps children in <Providers>
│   │   ├── providers.tsx                   MUI ThemeProvider + CssBaseline
│   │   ├── theme.ts                        MUI createTheme with light+dark colorSchemes
│   │   ├── globals.css                     Tailwind import + CSS vars for bg/fg, dark-mode media query
│   │   ├── favicon.ico
│   │   ├── page.tsx                        Root "/" — empty component (⚠️ no redirect to /login)
│   │   ├── login/page.tsx                  Renders WelcomePage with login:true props
│   │   ├── create/page.tsx                 Renders WelcomePage with register:true props
│   │   ├── portfolio/page.tsx              Portfolio list, search, create/edit/delete dialogs
│   │   └── portfolioDetails/[id]/
│   │       ├── page.tsx                    Portfolio detail: stats, charts, insights, holdings table
│   │       └── addHoldings/page.tsx        Stock search, queue holdings, submit batch
│   ├── components/                         22 components (all confirmed read)
│   │   ├── WelcomPage.tsx                  Login + register form (shared, driven by props)
│   │   ├── LeftSideWelcomePage.tsx         Left panel of auth page — hero image + feature list
│   │   ├── Logo.tsx                        "PIE / AI Portfolio Intelligence Engine" wordmark
│   │   ├── PortfolioPageHeader.tsx          "My Portfolios" heading + subtitle
│   │   ├── PortfiolioHoldingHeader.tsx      Portfolio name/description/holdings count + Edit/Delete buttons
│   │   ├── PortfolioDetailsPageHeader.tsx   Back button + Refresh Portfolio button (calls /refresh endpoint)
│   │   ├── AddHoldingsPageHeader.tsx        "Add Holdings" heading + subtitle
│   │   ├── BackPageRedirection.tsx          Generic back-button (supports portfolioHoldings and addHoldings pages)
│   │   ├── PortfolioStats.tsx               Value / Gain-Loss / Invested / Holdings count bar
│   │   ├── AllocationChart.tsx              MUI PieChart — sector allocation %
│   │   ├── PortfolioInsights.tsx            Rules-based insight alerts (concentration, performers, diversity)
│   │   ├── PortfolioHoldingsStandardTable.tsx  MUI Table + server-side TablePagination (1/5/10/25/50 rows)
│   │   ├── HoldingsTableRow.tsx             Collapsible table row → MoreDetails panel
│   │   ├── MoreDetails.tsx                  Expanded row: company, exchange, sector, industry, invested, daily change
│   │   ├── ActionsMenu.tsx                  MUI Menu per row: "Edit Holding" → EditDialog, "Delete Holding" → DeleteDialog
│   │   ├── EditDialog.tsx                   Shared edit dialog: handles both holding (shares/avgPrice) and portfolio (name/desc)
│   │   ├── DeleteDialog.tsx                 Shared confirm-delete dialog: handles both holdings and portfolios
│   │   ├── SearchBar.tsx                    Controlled TextField with Search icon; placeholder adapts to page context
│   │   ├── LeftCardAddHoldings.tsx          Search + paginated stock list + AddHoldingForm inline
│   │   ├── RightCardAddHoldings.tsx         Staged holdings queue — shows pending list, remove button per item
│   │   ├── AddHoldingForm.tsx               Inline form for shares + avgPrice once a stock is selected
│   │   └── ShowMoreDetailsStocks.tsx        MUI Dialog showing full stock details + live price
│   ├── api/
│   │   └── client.ts                        Axios instance — baseURL hardcoded to http://localhost:8080
│   ├── services/
│   │   ├── authService.ts                   login (POST /Login), create (POST /Create)
│   │   ├── portfolioService.ts              getAllUserPortfolio, createPortfolio, editUserPortfolio, deleteUserPortfolioById
│   │   ├── portfolioHoldingService.ts       getHoldingsByPortfolioId, refreshPortfolioHoldingsData, updatePortfolioHoldings, deletePortfolioHoldings, addHoldingsList
│   │   └── stocksService.ts                 getBasicStocksInfo, getStockMoreDetails
│   ├── types/
│   │   ├── portfolioHoldingsTypes.ts        Holdings, PortfolioDetailsAndHoldings, MaxStockAllocationData, PortfolioAllocationData, ColumnData, columns[], EditHoldingRequest, EditHoldingData
│   │   ├── portfolioInfoTypes.ts            PortfolioInfo, PortfolioStatsData, DeletePortfolioInfo, EditPortfolioInfo
│   │   └── stocksTypes.ts                   StockLists, StockListData, StockMoreDetails, AddHoldingsData
│   └── public/
│       ├── leftSide.png                     Hero background image for auth page left panel
│       └── *.svg                            Default Next.js SVG assets (file, globe, next, vercel, window)
├── .bob/
│   ├── context.md                           This file
│   └── rules.md                             Bob development guardrails
└── docs/
    └── BOB_USAGE.md                         Bob session activity log
```

> ⚠️ `target/` at repo root is committed by mistake (Maven build artifacts). Should be in `.gitignore`.

---

## Frontend Architecture

- **Framework:** Next.js 16, App Router; all interactive pages marked `"use client"`
- **UI Library:** Material UI v9 with Emotion CSS-in-JS
- **Charts:** `@mui/x-charts` — PieChart used for sector allocation
- **Styling:** MUI Emotion is primary; Tailwind CSS v4 configured (`globals.css`, `postcss.config.mjs`) but minimally used (no Tailwind class found in components)
- **Theme:** MUI `createTheme` with `colorSchemes: { light: true, dark: true }` in `app/theme.ts` — dark mode is available but no toggle is exposed in the UI
- **HTTP:** Single Axios instance `api/client.ts`; `baseURL` hardcoded to `http://localhost:8080` — no env-based config
- **Auth state:** JWT stored in `localStorage` (XSS risk). No global state — each page calls `localStorage.getItem("token")` and attaches it as `Authorization: Bearer` manually in every service call
- **Auth guard:** No Next.js middleware guard. Each page handles its own 401/network-error redirect to `/login`
- **State management:** Local `useState`/`useEffect` only. No Redux, Zustand, or React Context

### Installed but Unused Dependencies
- `react-virtuoso` v4 — in `package.json`, not imported anywhere in the codebase
- `@base-ui/react` v1 — in `package.json`, not imported anywhere in the codebase

---

## Backend Architecture

- **Framework:** Spring Boot 4.1, Java 21
- **Pattern:** Layered MVC — Controller → Service → Repository → Model
- **External HTTP clients:** Spring WebFlux `WebClient` used synchronously (`.block()`). Three client classes are placed in `Controller/` — this is an architectural misplacement (should be `client/` or `infrastructure/`)
- **ORM:** Spring Data JPA / Hibernate. Entities use verbose manual getters/setters — Lombok is declared in `pom.xml` but not used on entity classes
- **Jackson namespace:** `tools.jackson` (Spring Boot 4.x changed namespace from `com.fasterxml.jackson`). Exception: `StockImportDto` still uses `com.fasterxml.jackson.annotation.JsonProperty` — ⚠️ inconsistency
- **Validation:** Jakarta Bean Validation (`@Valid` on all controller methods, constraints on all request DTOs)
- **Soft deletes:** `deletedAt` timestamp pattern on `UserPortfolio` and `PortfolioHoldings`; all queries add `AND deletedAt IS NULL`
- **Database migrations:** None — no Flyway or Liquibase. `application.properties` does not set `spring.jpa.hibernate.ddl-auto`

### Repositories (all 5 confirmed)

| Repository | Entity | Key Custom Methods |
|---|---|---|
| `UserRepository` | `Users` | `findByEmail(String)` — Spring Security UserDetailsService; `findProjectedByEmail(String)` → `AuthenticationUserIdProjection` for JWT userId claim |
| `UserPortfolioRepository` | `UserPortfolio` | `findByIdAndUserIdAndDeletedAtIsNull`, `existsByPortfolioNameAndUserIdAndDeletedAtIsNull`, `findAllByIdInAndUserIdAndDeletedAtIsNull`, `getPortfolioDetailsWithTotalHoldings` (JPQL LEFT JOIN — ⚠️ has filter bug B5) |
| `StocksRepository` | `Stocks` | `findByTicker(String)`, `findStockDetailsByTicker` (JPQL → `StocksDto` projection), `getStocksList` (paginated JPQL — excludes already-held stocks, 5-field keyword search) |
| `PortfolioHoldingsRepository` | `PortfolioHoldings` | `existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull`, `findByIdAndUserPortfolioUserIdAndDeletedAtIsNull`, `findAllByIdInAndUserPortfolioUserIdAndDeletedAtIsNull`, `getPortfolioHoldings` (2 overloads: paginated+keyword / full list), `getSectorAllocation` (JPQL window fn), `getMaxStockAllocation` (JPQL window fn) |
| `StockCurrentPricesRepository` | `StockCurrentPrices` | `findByStocksId(Long)` — price cache lookup by stock FK |

---

## Database / Data Model

**Database name:** `aistockintelligenceprotfolio` (misspelled — preserve as-is)
**Dialect:** PostgreSQL

```
USERS (1) ──< USERPORTFOLIO (1) ──< PORTFOLIOHOLDINGS >── STOCKS (1) ──── STOCKSCURRENTPRICES
```

| Entity | Table | Key Columns | Notes |
|---|---|---|---|
| `Users` | `users` | `id`, `email` (unique), `passwordhash`, `firstname`, `lastname`, `createdat`, `updatedat` | Hibernate audit timestamps |
| `UserPortfolio` | `userportfolio` | `id`, `portfolioname`, `description`, `userid` (FK→users), `createdat`, `updatedat`, `deletedat` | Soft-deleted |
| `Stocks` | `stocks` | `id`, `ticker` (unique), `companyname`, `sector`, `industry`, `exchange`, `createdat`, `updatedat` | ⚠️ `sector`/`industry`/`exchange` declared `nullable=false` but SEC import leaves them blank |
| `StockCurrentPrices` | `stockscurrentprices` | `id`, `stockid` (FK unique), `price`, `previousclose`, `change`, `changepercent`, `markettimestamp`, `updatedat` | One-to-one with Stocks; price cache |
| `PortfolioHoldings` | `portfolioholdings` | `id`, `portfolioid` (FK), `stockid` (FK), `shares`, `avgprice`, `createdat`, `updatedat`, `deletedat` | Soft-deleted |

---

## Authentication & Authorization

### ✅ Confirmed
- BCrypt password hashing (strength 12 from `UserConstants.encryptionStrength`)
- JWT issued on register and login — contains email (subject) + userId (claim), 30-hour expiry
- `JwtFilter` validates Bearer token on every request, extracts userId, stores `UserPrincipal` in `SecurityContext`
- `AuthenticationService.getCurrentUserId()` reads userId from context — called in every service
- CORS: `localhost:3000` only, credentials allowed, all headers allowed (set in both `AuthSecurityConfig` and `CorsConfig` — duplicate)
- Only `/Create` and `/Login` are public; all other routes require auth
- Single granted authority: `"User"` hardcoded in `UserPrincipal.getAuthorities()`

### ⚠️ Known Security Issues
1. **JWT secret regenerated on every restart** — `JwtService` constructor generates a new random `HmacSHA256` key; all active tokens become invalid. Static key is commented out.
2. **JWT stored in `localStorage`** — XSS vulnerable. Should use HttpOnly cookies.
3. **HTTP Basic auth enabled** alongside JWT in `AuthSecurityConfig` (`.httpBasic(Customizer.withDefaults())`) — Postman testing artifact.
4. No token refresh mechanism.
5. No role-based authorization.
6. JWT response body is a raw plain-text string (not JSON) — frontend receives it as `response.data`.

---

## External APIs

| API | Client | Endpoint | Trigger | Auth |
|---|---|---|---|---|
| SEC EDGAR | `ImportStocksDataClient` | `GET /files/company_tickers.json` | Admin-only, commented-out endpoint | `User-Agent` header = `SEC_API_HEADER_VALUE` env var |
| Alpha Vantage | `ImportStockDetailsClient` | `GET /query?function=OVERVIEW&symbol=` | Lazy — first time a stock is accessed with null sector/exchange | `ALPHAVANTAGE_API_KEY` env var |
| Financial Modeling Prep | `ImportStockPriceClient` | `GET stable/quote?symbol=` | On portfolio load if price null or stale (>5 min) | `FMP_API_KEY` env var |

All use `.block()` — synchronous reactive calls. SEC import is **not publicly accessible** in the current build.

---

## Confirmed Current Functionality

### ✅ Authentication
- Register (`POST /Create`), login (`POST /Login`) — both return JWT string
- Login/register pages with form validation, show/hide password, error alert on 401

### ✅ Portfolio Management
- Create / edit (name + description) / soft-delete / batch soft-delete portfolios
- List portfolios for authenticated user with total holding count
- Debounced keyword search (500ms) on name + description

### ✅ Holdings Management
- Add multiple holdings in one submission (staged queue UI → `POST /addStocks`)
- Update shares or avgPrice (`PATCH /updateHoldings`)
- Soft-delete individual or batch holdings
- Server-side paginated holdings table (rows per page: 1/5/10/25/50)
- Keyword search on ticker / company / exchange / sector / industry

### ✅ Stock Search & Data
- Paginated stock search that excludes already-held stocks in target portfolio
- Stock more-details modal: full metadata + live price from FMP
- Lazy detail fetch: Alpha Vantage called on first access when sector/exchange are null

### ✅ Portfolio Analytics
- Per-holding: current value, invested, gain/loss ($+%), daily change, allocation %
- Portfolio totals: value, invested, gain/loss ($+%)
- Sector allocation pie chart (MUI x-charts PieChart)
- Rules-based insights panel: sector concentration >60%, stock concentration >40%, best/worst performers, holdings above/below avg price
- Refresh button: forces FMP re-fetch if price data >5 min stale

---

## Known Bugs / Risks

| ID | Description | Severity | File |
|---|---|---|---|
| B1 | JWT secret regenerated on restart — all sessions invalidated | 🔴 Critical | `JwtService.java` |
| B2 | JWT in `localStorage` — XSS risk | 🔴 Critical | All frontend service files |
| B3 | Root `/` is blank — no redirect to `/login` | 🟠 High | `app/page.tsx` |
| B4 | Register flow has no post-success redirect | 🟠 High | `WelcomPage.tsx:66-72` |
| B5 | JPQL LEFT JOIN filter hides portfolios with zero holdings | 🟠 High | `UserPortfolioRepository.java:31` |
| B6 | HTTP Basic auth enabled alongside JWT | 🟠 High | `AuthSecurityConfig.java:46` |
| B7 | Price cache re-fetch condition inverted in `getStockMoreDetails` | 🟠 High | `StocksService.java:76-78` |
| B8 | Logout button renders but has no click handler | 🟡 Medium | `portfolio/page.tsx:213-222` |
| B9 | No DB migration tooling — schema managed manually | 🟡 Medium | `application.properties` |
| B10 | `Stocks` non-null columns left empty by SEC bulk import | 🟡 Medium | `Stocks.java`, `ImportStockDetailsService.java` |
| B11 | `getStocksListWithSearch` returns `null` when keyword is empty | 🟡 Medium | `StocksController.java:25-26` |
| B12 | NPE risk in `portfolioStats` if FMP returns null | 🟡 Medium | `PortfolioHoldingsService.java:193-196` |
| B13 | `StockImportDto` uses `com.fasterxml.jackson` — inconsistent with `tools.jackson` | 🟢 Low | `StockImportDto.java` |
| B14 | Import client classes placed in `Controller/` package | 🟢 Low | `Controller/Import*Client.java` |
| B15 | `CorsConfig.java` and `AuthSecurityConfig` both register CORS — duplicate | 🟢 Low | `CorsConfig.java`, `AuthSecurityConfig.java` |
| B16 | `console.log` debug statements in production page code | 🟢 Low | `portfolio/page.tsx:147,149` |
| B17 | `target/` build artifacts committed at repo root | 🟢 Low | `/target` |

---

## Planned / Future Functionality

🔲 IBM watsonx AI natural-language portfolio narrative
🔲 Real-time price streaming (WebSocket / SSE)
🔲 Functional logout button
🔲 Root `/` redirect to `/login`
🔲 Post-registration redirect to portfolio page
🔲 Holdings table column sorting
🔲 Portfolio data export (CSV / PDF)
🔲 Docker / docker-compose
🔲 CI/CD pipeline
🔲 JWT token refresh
🔲 Role-based authorization (admin vs user)
🔲 Database migration tooling (Flyway / Liquibase)
🔲 Dark mode toggle (theme supports it; no UI toggle exists)

---

## Testing Status

- **Backend:** One test class — `AiStockIntelligenceBackendApplicationTests.java` — context load only. Zero business logic tests. H2 and Spring test starters are in `pom.xml` and ready to use.
- **Frontend:** No test files. No Jest/Vitest config. No testing libraries in `package.json`.
- **Overall: Effectively zero test coverage.**

---

## Deployment Status

No deployment infrastructure exists — no Dockerfile, docker-compose, CI/CD pipeline, or cloud config. Both services run locally only:
- Backend: `localhost:8080`
- Frontend: `localhost:3000`
- CORS locked to `localhost:3000`

---

## Technical Constraints

1. Frontend `baseURL` is hardcoded — any non-local deployment requires a code change or Next.js `rewrites` proxy config.
2. Spring Boot 4.x uses `tools.jackson` namespace — do not use `com.fasterxml.jackson` in new backend code.
3. No Flyway/Liquibase — schema changes require manual SQL or careful Hibernate DDL auto configuration.
4. All five env vars (`DB_USERNAME`, `DB_PASSWORD`, `FMP_API_KEY`, `ALPHAVANTAGE_API_KEY`, `SEC_API_HEADER_VALUE`) must be set for the backend to start.
5. PostgreSQL database `aistockintelligenceprotfolio` must exist before first run.
6. JWT response is a raw string body (not JSON) — backend/frontend must stay in sync on this contract.
7. `StockImportDto` uses `com.fasterxml.jackson` — if this DTO is ever touched, migrate to `tools.jackson`.

---

## Hackathon Context

- **Branch:** `Dev/Suchith`
- **Phase:** Active hackathon sprint
- **Top priorities identified:**
  1. IBM watsonx AI portfolio narrative (core hackathon feature)
  2. Security hardening (JWT secret persistence, localStorage → HttpOnly cookie)
  3. Critical bug fixes (B3 blank root, B4 register redirect, B7 cache logic, B8 logout)
  4. Containerisation (Docker + docker-compose)
  5. Meaningful test coverage (at least one service layer)

---

## Development Rules for Bob Sessions

See `.bob/rules.md` for full guardrails. Short version:
- Read every relevant file before making claims about it.
- Distinguish ✅ confirmed / ⚠️ partial / 🔲 planned in all output.
- Do not modify application source files unless explicitly instructed.
- Do not install or remove dependencies without explicit instruction.
- Propose improvements; never silently implement them.
- Update this file and `docs/BOB_USAGE.md` after every meaningful session.
