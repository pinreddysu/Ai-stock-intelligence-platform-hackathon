# Bob Development Rules & Guardrails

Rules that IBM Bob must follow throughout this project. Apply these in every session without exception.

---

## 1. Read Before You Claim

- **Never speculate about code you have not opened.** If making a claim about a specific file, class, method, or behaviour, read that file first.
- If a file has been read in a previous session that is still in context, you may reference it. If context was compacted or the session is new, re-read before claiming.
- Use `GetSymbolsOverview`, `FindSymbol`, `grep`, or `read_file` to ground every factual statement about the codebase.

---

## 2. Distinguish Confirmation Status in All Output

Always clearly label:
- ✅ **Confirmed** — you have read the code that implements this
- ⚠️ **Partially implemented** — code exists but is incomplete, broken, or blocked
- 🔲 **Planned / future** — discussed or named but not yet coded
- ❓ **Unknown** — not yet investigated

Never present a planned feature as implemented. Never present a bug as fixed unless you have read the fixed code.

---

## 3. Do Not Modify Application Source Files Unless Explicitly Instructed

- **Default mode is read-only.** Do not edit, create, or delete any file under `backend/src/`, `frontend/app/`, `frontend/components/`, `frontend/services/`, `frontend/api/`, `frontend/types/`, or any other application source path unless the user explicitly asks for a code change.
- Analysis, documentation, and planning tasks do **not** imply permission to change application code or perform any terminal scripts.
- If you identify a bug or improvement, **propose it**; do not silently fix it.

---

## 4. Do Not Install or Remove Dependencies

- Do not run `npm install <package>`, `npm remove <package>`, `mvn` dependency changes, or any equivalent that modifies `package.json`, `package-lock.json`, or `pom.xml` unless the user explicitly instructs it with a specific package name.
- Do not suggest adding a dependency and then add it in the same turn without user confirmation.

---

## 5. Do Not Invent Features

- Do not add functionality, endpoints, components, or configuration that was not explicitly requested — even if it seems like an obvious improvement.
- Scope every code change to the minimum that satisfies the stated requirement.
- If a request is ambiguous, ask a clarifying question rather than guessing and implementing.

---

## 6. Do Not Describe Proposals as Completed

- When suggesting an improvement, use future-tense or conditional language: "This could be fixed by…", "A possible approach is…"
- Only use past-tense or present-tense completion language ("has been fixed", "is now implemented") after you have actually made the change and it is in the file.
- Never mark a task complete in the todo list until the work is verified in the file.

---

## 7. Preserve Accurate Existing Documentation

- When updating `README.md`, `.bob/context.md`, or any documentation file, do not remove content that is still accurate.
- Only remove content that is factually wrong, superseded, or explicitly flagged by the user for removal.
- The existing known-issues table, API reference, and confirmed-functionality sections must be preserved and extended, not replaced wholesale, unless a full rewrite is explicitly requested.

---

## 8. Keep Context Files Current

After every session that involves meaningful analysis or code changes:
- Update `.bob/context.md` to reflect any new confirmed functionality, new bugs found, or resolved bugs.
- Add an entry to `docs/BOB_USAGE.md` recording what was asked, what was done, which files were changed, and the result.
- Do not exaggerate Bob's contribution in the usage log. Record only what actually happened.

---

## 9. Technical Constraints to Respect

These must not be violated in any generated code:

| Constraint | Rule |
|---|---|
| Jackson namespace | Use `tools.jackson` — never `com.fasterxml.jackson` in new backend code |
| JWT contract | JWT response body is a raw string (not JSON) — do not wrap it in a JSON object without updating both sides |
| Database name | `aistockintelligenceprotfolio` — misspelled by design; do not rename |
| CORS origin | `localhost:3000` only — do not widen without explicit instruction |
| Soft deletes | Always filter `AND deletedAt IS NULL` in any query touching `userportfolio` or `portfolioholdings` |
| Auth scoping | Every service that touches user data must call `AuthenticationService.getCurrentUserId()` to scope to the authenticated user |
| No DDL auto | Do not set `spring.jpa.hibernate.ddl-auto=create` or `drop-create` without explicit confirmation — it will destroy production data |

---

## 10. Code Style — Follow Existing Conventions

- **Backend:** Verbose manual getters/setters (no Lombok on entities even though Lombok is available). Constructor injection. `@Valid` on all controller `@RequestBody` params. Soft-delete via `deletedAt` timestamp.
- **Frontend:** `"use client"` directive on all interactive pages. Per-page auth checks (no global middleware). Manual `Authorization: Bearer` header in every service call. `useState`/`useEffect` only — no global state library.
- **Naming:** Follow existing naming (e.g., `UserPortfolio` not `Portfolio`, `portfolioHoldings` not `holdings`). Do not rename existing symbols.

---

## 11. Hackathon Scope

During the hackathon sprint, prioritise in this order:
1. IBM watsonx integration (highest impact for the hackathon theme)
2. Critical security fixes (B1 JWT secret, B2 localStorage)
3. High-severity bug fixes (B3 blank root, B4 register redirect, B7 cache logic, B8 logout)
4. Containerisation (Docker + docker-compose)
5. Test coverage (at minimum one service layer with `@DataJpaTest` or `@SpringBootTest`)

Do not pursue low-severity cosmetic cleanups, refactors, or architectural improvements unless the user specifically requests them and there is sprint capacity.
