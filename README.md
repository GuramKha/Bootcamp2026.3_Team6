# Bootcamp2025.3_Team6
# 🏦 TBC Bank – Other products Automation Project

---

## 1. Project Description

**Bootcamp2025.3 – Team 6** Our team developed comprehensive test coverage for the **Money Transfer**  feature of [tbcbank.ge](https://www.tbcbank.ge). The QA workflow included:

- Test Design: Full test planning specifically for the Money Transfer page.
- UI Test Coverage: End-to-end automated UI tests ensuring functionality and user experience.
- API Validation: Thorough API-level testing to verify correctness and reliability of endpoints.
- Performance Testing: Benchmarking response times to ensure system stability under load.
- AI-assisted Test Engineering: Using AI tools to optimize test generation and maintenance.
- CI/CD Pipeline Integration: Automated test execution in a stable, parallelized environment, including cross-browser testing.
- Critical Bug Discovery & Analysis: Identified and analyzed critical defects impacting end users.
- Agile Approach: All work was conducted following Agile methodology, allowing iterative improvements and rapid feedback.

## 2. Team Members

| Name                 | Role                              |
|----------------------|-----------------------------------|
| Milena Oganiani      | API Testing (Rest Assured)        |
| Luka Vashakidze      | Performance Testing (K6)          |
| Mamuka Lomjaria      | UI Automation (Playwright)        |
| Guram Khakhutashvili | Reporting & CI/CD                 |
| Mariami Tukhashvili  | Test Scenarios & Bug Report       |
| Tamar Kilasonia      | AI-Assisted Quality Engineering   |

---

## 3. Team Captain

Milena Oganiani

---

## 4. Features
The TBC Bank Money Transfers page provides two core calculator tools for customers
sending or receiving remittances through the bank's supported transfer systems
(FastTransfer, IntelExpress, MoneyGram, Ria, RicoGram, Western Union, ZolotayaKorona).

### Remittance Conversion

A real-time currency converter that lets users calculate equivalent amounts
across supported currencies (GEL, USD, EUR, etc.) using live exchange rates.

- Two-way conversion between any supported currency pair
- Live exchange rate displayed below the input (e.g. `1 USD = 2.688 GEL`)
- Swap button to reverse the conversion direction instantly
- Currency selection dropdowns on both sides

### Remittance Fee Calculation

A commission estimator that calculates the expected fee for sending a specified
amount to a chosen destination country.

- Amount input with GEL currency selector
- Country dropdown to select the destination
- Validation message when required fields are missing
---

## 5. Tech Stack

| Layer           | Technology    | Version  | Purpose                                          |
|-----------------|---------------|----------|--------------------------------------------------|
| UI Automation   | Playwright    | 1.44+    | Browser automation and UI test execution         |
| UI Automation   | Java          | Latest   | Test scripting language                          |
| UI Automation   | Node.js       | 20.x LTS | Runtime environment                              |
| API Testing     | Rest Assured  | 5.x      | API test automation (Java)                       |
| API Testing     | Testng        | 5.x      | Test runner and assertions                       |
| API Testing     | Maven         | 3.9+     | Build and dependency management                  |
| Performance     | K6            | Latest   | Load and stress testing                          |
| Reporting       | Allure Report | Latest   | Test reports with screenshots and logs           |
| CI/CD           | Gitlab        | —        | Pipeline automation                              |
| CI/CD           | Gitlab Pages  | —        | Allure report hosting                            |
| Test Management | Zephyr Scale  | —        | Test case management and scenario documentation  |
| Project Mgmt    | Jira          | —        | Agile board: To Do / In Progress / Done          |

---

## 6. Scenarios


| Key    | Name                                                                  | Type        |
|--------|-----------------------------------------------------------------------|-------------|
| CRM-T1 | Remittance Conversion                                                 | SCENARIO    |
| CRM-T2 | Remittance Fee Calculation – Unsuccessful                             | SCENARIO    |
| CRM-T3 | Remittance Fee Calculation for minimum and maximum amounts – Edge case | SCENARIO   |
| CRM-T4 | Money Transfer – API Testing                                          | SCENARIO    |
| CRM-T5 | Remittance Fee Calculation With Random Data (K6)                      | PERFORMANCE |
| CRM-T6 | Rate Conversion With Random Data & Currencies (K6)                    | PERFORMANCE |

**CRM-T1 · Remittance Conversion**
Verifies that entering an amount and selecting currencies returns the correct
converted value based on the displayed rate. Includes swap direction check.

**CRM-T2 · Remittance Fee Calculation – Unsuccessful**
Verifies that submitting the fee calculator without an amount or country
shows the validation message: *"Please enter the amount and select the country."*

**CRM-T3 · Remittance Fee Calculation – Edge Case**
Verifies correct fee calculation at the minimum and maximum allowed send amounts,
and graceful handling of out-of-range values.

**CRM-T4 · Money Transfer – API Testing**
Validates the rate conversion and fee calculation API endpoints for correct
responses on valid inputs and appropriate error codes on invalid ones.

**CRM-T5 · Remittance Fee Calculation With Random Data (K6)**
Load test for the fee calculation API with randomised amounts and countries.
Threshold: p95 response time within limit, error rate < 1%.

**CRM-T6 · Rate Conversion With Random Data & Currencies (K6)**
Load test for the conversion API with random currency pairs and amounts.
Threshold: p95 response time within limit, error rate < 1%.

---
## 7. Run Instructions

**Prerequisites:** Node.js 20.x · Java 17+ · Maven 3.9+ · K6 · Allure CLI 

```bash
## 1. Clone Repository

```bash
git clone https://github.com/your-repo/project.git
cd project
```

## 2. Install Dependencies

**Maven:**
```bash
mvn clean install
```

**Gradle:**
```bash
gradle build
```

## 3. Run Tests

**UI Tests:**
```bash
mvn test -Dtest=UiTests
```

**API Tests:**
```bash
mvn test -Dtest=ApiTests
```

**Run All Tests:**
```bash
mvn clean test
```

## 4. Generate Report (Allure)

```bash
allure serve target/allure-results
```

## 8. CI/CD Link

| Resource                  | Link                                                                          |
|---------------------------|-------------------------------------------------------------------------------|
| 🔧 GitLab Pipeline | [View Workflows](https://gitlab.com/final-project-tbc/bootcamp2026.3_team6/-/pipelines/2430981121)  |


## 9. AI Summary

AI reviewed the Money Transfers automation suite (Playwright + Java + TestNG)
with a focus on stability and CI determinism — no business logic was changed.

**Key issues identified:**
- `@Test(priority=...)` chaining caused cascade failures when an early test failed
- Shared class-level `Page` state leaked between tests, causing intermittent failures
- Missing explicit waits after navigation and dropdown interactions exposed race conditions in CI
- Some locators were too generic, leading to ambiguous element matches
- Rate parsing and conversion verification logic was scattered across Steps and tests
- `Faker` was instantiated repeatedly with no standardized pattern

**Recommendations applied:**
- Made each test self-contained with its own navigation and preconditions
- Replaced priority chaining with independent test flows
- Added explicit readiness waits after navigation and dropdown actions
- Hardened locators using `getByRole` and `.filter(hasText(...))` scoping
- Extracted rate parsing and calculation into a `RateUtils` helper class
- Standardized `Faker` as a singleton for consistent, low-overhead test data

**Outcome:** Reduced flakiness, fewer cascade failures, more reproducible CI runs,
and a cleaner separation between UI interaction logic and verification logic.

---

## 10. Bug Summary

Total bugs found: 5

| Category              | Description                                                                                      | Priority          |
|-----------------------|--------------------------------------------------------------------------------------------------|-------------------|
| Financial / Logic     | Incorrect currency conversion: for large amounts, the result does not match the displayed rate.  | Highest (Critical)|
| Functional            | Non-responsive button: the "Open Account" button on the accounts page does not respond to clicks.| High              |
| Data Validation       | Character overflow: entering more than 8 digits causes the calculator to stop producing results; no input limit is enforced. | Medium |
| UX / Error Handling   | NaN display: pressing Enter on an empty input field causes the system to show a "Not a Number" (NaN) error. | Low         |
| UX / Sorting          | Unsorted country list: the country dropdown is not sorted alphabetically, making it difficult to search. | Low          |
