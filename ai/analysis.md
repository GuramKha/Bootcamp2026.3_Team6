# AI-Assisted QA Automation Analysis — Playwright + Java + TestNG Stabilization
**Project:** Bootcamp2026.3_Team6 (tbcbank.ge UI automation)
**Date:** 2026-04-04
**Role:** Senior QA Automation Engineer (Playwright + Java + TestNG)
**Scope:** Stability/structure improvements only (business logic unchanged)


---


## 1) What I asked AI to do
I asked AI to review the Money Transfers conversion flow and recommend changes that reduce UI flakiness and improve CI determinism without changing business coverage.


**Targets reviewed:**
- `RemittanceConversionTest`
- `BaseTest`
- `MoneyTransfersSteps`
- `MoneyTransfersPage`


**Requested focus (stability-first):**
- Order dependency (`priority` / chained test flow)
- Shared state risks (`@BeforeClass`, reused `Page`)
- Synchronization after navigation and dropdown interactions
- Locator robustness (avoid ambiguous selectors / selector drift)
- Verification reliability (rate parsing, rounding, tolerance)
- Test data strategy (standardized Faker usage; memory/determinism)


---


## 2) Baseline (before changes)
Before stabilization work:
- Tests reused the same Playwright `Page` state across multiple test methods (class-level setup).
- Some scenarios implicitly relied on earlier navigation/currency selection due to `priority` chaining.
- Locator strategy was inconsistent: some locators were too generic and not uniquely scoped.
- Parsing/calculation logic for conversion verification was not centralized.
- Faker/test data generation pattern was not standardized.


This increased CI flakiness due to state leakage, timing variance, ambiguous element matches, and mixed responsibilities.


---


## 3) What AI flagged (key risks)
| Finding | Severity | Why it matters |
|---|---|---|
| `@Test(priority=...)` order coupling | High | Failure/skip early can invalidate later tests due to missing preconditions |
| Shared mutable state via class-level setup | High | UI/session state can leak between tests and create intermittent failures |
| Weak synchronization after navigation/dropdowns | Medium | CI exposes race conditions when UI is slower or inconsistent |
| Ambiguous/under-scoped locators | Medium | Multiple matches cause unstable click/verify behavior |
| Parsing/verification logic mixed into Steps/tests | Medium | Harder debugging; duplicated logic; inconsistent rounding/tolerance |
| Faker not standardized | Low/Medium | Extra allocations; harder control over deterministic CI data |


---


## 4) AI recommendations (what to change)
1) **Remove order coupling**
- Make each test self-contained and set navigation + preconditions inside the test flow.


2) **Reduce shared-state risk**
- Prefer per-test deterministic start state and limit reliance on shared Page state.


3) **Add deterministic readiness contracts**
- Add explicit waits after navigation and dropdown open/selection actions to avoid implicit timing assumptions.


4) **Harden locators (examples from MoneyTransfersPage)**
- Prefer semantic locators where possible:
    - `remittanceFeeCalculation` should use `getByRole(AriaRole.BUTTON, name=REMITTANCE_FEE)` to avoid brittle CSS/XPath and improve clarity.
- Scope locators to the intended element to avoid ambiguous matches:
    - `navbarMoneyTransfers` should be filtered by text:
      `locator(".tbcx-pw-breadcrumbs__item").filter(hasText(MONEY_TRANSFERS))`
      This prevents selecting the wrong breadcrumb item when multiple exist.


5) **Extract verification helpers**
- Move rate parsing + expected conversion calculation into `RateUtils` to centralize rounding/tolerance and keep UI steps focused on interactions.


6) **Standardize Faker usage (singleton)**
- Use a singleton `Faker` instance to reduce repeated allocations and keep data generation consistent.
- Optionally seed Faker or switch to fixed values for CI determinism.


---


## 5) Why these recommendations were useful (impact)
- **Less cascade failure:** removing priority chains prevents “one failure breaks multiple tests.”
- **More reproducible runs:** reducing shared Page state leakage makes reruns and isolated execution more reliable.
- **Fewer CI race conditions:** explicit readiness waits improve stability in slow/variable CI.
- **More reliable element targeting:** `getByRole` + `filter/scoping` reduces ambiguous locator matches and stabilizes clicks/assertions.
- **Cleaner architecture:** `RateUtils` centralizes parsing/math; Faker singleton reduces object churn and supports deterministic data strategy.



