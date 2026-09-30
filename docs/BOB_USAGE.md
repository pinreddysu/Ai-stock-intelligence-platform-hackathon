# Bob Usage Log

Records IBM Bob's contributions to the AI Stock Intelligence Platform project.
Each entry documents what was asked, what Bob actually did, which files were read or changed, and the outcome.

---

## Entry 001 — Codebase Analysis & Documentation Baseline

**Date:** 2025-09-29
**Branch:** `Dev/Suchith`
**Purpose:** Establish a baseline understanding of an unfamiliar existing codebase before active hackathon development begins.

### What Bob Was Asked To Do

1. Perform a senior-engineer-style read-only analysis of the entire project covering architecture, tech stack, data model, auth, APIs, features, testing, build/deploy, dependencies, risks, and 3–5 hackathon improvement opportunities.
2. Create/update four documentation files without modifying any application code:
   - `README.md` — accurate project description
   - `.bob/context.md` — shared context for future Bob sessions
   - `docs/BOB_USAGE.md` — this file
   - `.bob/rules.md` — development guardrails

### What Bob Actually Contributed

**Read-only exploration — no application source files were modified.**

Bob read and analysed every file in the codebase across multiple turns (some files were identified as missing from the initial read and added in follow-up turns):

| Category | Files Read |
|---|---|
| Root | `README.md`, `backend/README.md` |
| Backend config | `backend/pom.xml`, `application.properties` |
| Entry point | `AiStockIntelligenceBackendApplication.java` |
| AuthConfig | `AuthSecurityConfig.java`, `CorsConfig.java`, `JwtFilter.java`, `WebClientConfig.java`, `UserPrincipal.java` |
| Constants | `UserConstants.java` |
| Controllers (all 4) | `UserAuthController.java`, `StocksController.java`, `UserPortfolioController.java`, `PortfolioHoldingsController.java` |
| External API clients (all 3) | `ImportStockPriceClient.java`, `ImportStockDetailsClient.java`, `ImportStocksDataClient.java` |
| DTOs — root (all 3) | `StockDetailsDto.java`, `StockImportDto.java`, `StockPricesDto.java` |
| DTOs — Requests (all 11) | `LoginRequestDto.java`, `RegisterRequestDto.java`, `CreatePortfolioRequestDto.java`, `UpdatePortfolioRequestDto.java`, `DeletePortfolioRequestDto.java`, `DeletePortfoliosListRequestDto.java`, `GetPortfolioHoldingsDto.java`, `AddHoldingsToPortfolioDto.java`, `UpdateHoldingRequestDto.java`, `RemoveHoldingRequestDto.java`, `RemoveHoldingsListRequestDto.java` |
| DTOs — Response (all 6) | `BasicStockDto.java`, `PortfolioInfoDto.java`, `PortfolioDetailsDto.java`, `PortfolioValueAndUpdatedAtDto.java`, `StocksDto.java`, `StockMoreDetailsDto.java` |
| Models (all 5) | `Users.java`, `UserPortfolio.java`, `Stocks.java`, `StockCurrentPrices.java`, `PortfolioHoldings.java` |
| Projections | `AuthenticationUserIdProjection.java` |
| Repositories (all 5) | `UserRepository.java`, `UserPortfolioRepository.java`, `StocksRepository.java`, `PortfolioHoldingsRepository.java`, `StockCurrentPricesRepository.java` |
| Services (all 10) | `MyUserDetailsService.java`, `AuthenticationService.java`, `JwtService.java`, `UserAuthService.java`, `UserPortfolioService.java`, `PortfolioHoldingsService.java`, `StocksService.java`, `ImportStockDetailsService.java`, `ImportStocksDetailsService.java`, `ImportStockPriceService.java` |
| Tests | `AiStockIntelligenceBackendApplicationTests.java` |
| Frontend config | `package.json`, `next.config.ts`, `tsconfig.json`, `eslint.config.mjs`, `postcss.config.mjs` |
| Frontend app (all 6 pages + shared) | `layout.tsx`, `providers.tsx`, `theme.ts`, `globals.css`, `page.tsx`, `login/page.tsx`, `create/page.tsx`, `portfolio/page.tsx`, `portfolioDetails/[id]/page.tsx`, `portfolioDetails/[id]/addHoldings/page.tsx` |
| Frontend API client | `api/client.ts` |
| Frontend services (all 4) | `authService.ts`, `portfolioService.ts`, `portfolioHoldingService.ts`, `stocksService.ts` |
| Frontend types (all 3) | `portfolioHoldingsTypes.ts`, `portfolioInfoTypes.ts`, `stocksTypes.ts` |
| Frontend components (all 22) | `WelcomPage.tsx`, `LeftSideWelcomePage.tsx`, `Logo.tsx`, `PortfolioPageHeader.tsx`, `PortfiolioHoldingHeader.tsx`, `PortfolioDetailsPageHeader.tsx`, `AddHoldingsPageHeader.tsx`, `BackPageRedirection.tsx`, `PortfolioStats.tsx`, `AllocationChart.tsx`, `PortfolioInsights.tsx`, `PortfolioHoldingsStandardTable.tsx`, `HoldingsTableRow.tsx`, `MoreDetails.tsx`, `ActionsMenu.tsx`, `EditDialog.tsx`, `DeleteDialog.tsx`, `SearchBar.tsx`, `LeftCardAddHoldings.tsx`, `RightCardAddHoldings.tsx`, `AddHoldingForm.tsx`, `ShowMoreDetailsStocks.tsx` |

### Analysis Delivered

- Full architecture diagram (Next.js → Spring Boot → PostgreSQL → 3 external APIs)
- Complete data model (5 entities, all relationships, soft-delete pattern, constraint mismatch on `Stocks`)
- All 16 REST endpoints documented with method, path, description, and auth requirement
- All 5 repositories documented with their custom query methods
- Authentication flow documented end-to-end with 6 confirmed security issues
- All 3 external API integrations mapped (trigger, auth, client class)
- 17 specific bugs/risks catalogued with severity ratings
- 2 installed-but-unused frontend dependencies identified (`react-virtuoso`, `@base-ui/react`)
- `StockImportDto` Jackson namespace inconsistency identified (`com.fasterxml` vs `tools.jackson`)
- Duplicate CORS configuration identified (`CorsConfig.java` + `AuthSecurityConfig.java`)
- 5 ranked hackathon improvement opportunities proposed

### Documentation Files Created/Updated

| File | Action | Summary |
|---|---|---|
| `README.md` | Updated | Replaced 1-line placeholder with full docs: architecture diagram, data model, auth, confirmed features, planned features, env vars, run instructions, known issues table, full API reference |
| `.bob/context.md` | Created + iteratively updated | Full shared session context; updated across multiple turns as missed files were identified and read |
| `docs/BOB_USAGE.md` | Created | This file |
| `.bob/rules.md` | Created | Development guardrails for Bob sessions |

### Application Files Modified

**None.** Zero application source files (Java, TypeScript, config, build files) were created, edited, or deleted during this session.

### Result

Complete codebase baseline with confirmed vs planned functionality clearly separated, 17 bugs/risks catalogued, zero test coverage confirmed, zero deployment infrastructure confirmed, and 5 concrete hackathon improvement opportunities ranked.

---
