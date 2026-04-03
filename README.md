# Bootcamp2025.3_Team6
# 🏦 TBC Bank – Money Transfer Automation Project

---

## 1. Project Description

**Bootcamp2025.3 – Team 6** delivers a full QA automation workflow targeting the **Money Transfer** page of [tbcbank.ge](https://www.tbcbank.ge).

The **Money Transfer** feature enables TBC Bank customers to send funds domestically and internationally. Our team owns end-to-end QA coverage including UI test flows, API-level validation, performance benchmarking, AI-assisted test engineering, and real bug identification.

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

| #  | Test Case                                       | Type        | Tool         | Status      |
|----|-------------------------------------------------|-------------|--------------|-------------|
| 1  | Money Transfer page loads correctly             | UI          | Playwright   | ✅ Complete |
| 2  | Successful transfer with valid inputs           | UI          | Playwright   | ✅ Complete |
| 3  | Error shown when required fields are empty      | UI          | Playwright   | ✅ Complete |
| 4  | Invalid IBAN format triggers validation error   | UI          | Playwright   | ✅ Complete |
| 5  | Currency selection updates amount field         | UI          | Playwright   | ✅ Complete |
| 6  | Transfer API returns 200 for valid payload      | API         | Rest Assured | ✅ Complete |
| 7  | Load test: 50 concurrent users on transfer page | Performance | K6           | ✅ Complete |

---

## 5. Tech Stack

| Layer           | Technology      | Version  | Purpose                                          |
|-----------------|-----------------|----------|--------------------------------------------------|
| UI Automation   | Playwright      | 1.44+    | Browser automation and UI test execution         |
| UI Automation   | TypeScript      | Latest   | Test scripting language                          |
| UI Automation   | Node.js         | 20.x LTS | Runtime environment                              |
| API Testing     | Rest Assured    | 5.x      | API test automation (Java)                       |
| API Testing     | JUnit 5         | 5.x      | Test runner and assertions                       |
| API Testing     | Maven           | 3.9+     | Build and dependency management                  |
| Performance     | K6              | Latest   | Load and stress testing                          |
| Reporting       | Allure Report   | Latest   | Test reports with screenshots and logs           |
| CI/CD           | GitHub Actions  | —        | Pipeline automation                              |
| CI/CD           | GitHub Pages    | —        | Allure report hosting                            |
| Test Management | Zephyr Scale    | —        | Test case management and scenario documentation  |
| Project Mgmt    | GitHub Projects | —        | Agile board: To Do / In Progress / Done          |

---

## 6. Scenarios

All scenarios target the **Money Transfer** page on [tbcbank.ge](https://www.tbcbank.ge).

---

### Scenario 1 (Positive) – Successful Domestic Money Transfer

**Type:** Positive | **Actor:** Authenticated bank customer | **Automated:** 

**Preconditions:** User is logged in; sufficient balance exists in source account

**Steps:**
1. Navigate to the Money Transfer page
2. Select transfer type: **Domestic**
3. Enter a valid Georgian IBAN (`GExx TBC xxx...`)
4. Enter recipient full name
5. Enter amount: `50.00 GEL`
6. Enter transfer description/note
7. Click **Continue**, review summary, confirm transfer

**Expected Result:** Transfer confirmation screen with transaction ID and success message

---

### Scenario 2 (Negative) – Transfer Rejected with Invalid IBAN

**Type:** Negative | **Actor:** Authenticated bank customer | **Automated:**

**Steps:**
1. Navigate to the Money Transfer page
2. Enter an invalid IBAN: `GE00 0000 0000`
3. Click outside the field to trigger validation
4. Attempt to click **Continue**

**Expected Result:** Inline validation error shown on the IBAN field; form cannot be submitted

---

### Scenario 3 (Negative) – Submission Blocked When Required Fields Are Empty

**Type:** Negative | **Actor:** Authenticated bank customer | **Automated:** 

**Steps:**
1. Navigate to the Money Transfer page
2. Leave all fields empty
3. Click **Continue**

**Expected Result:** All required fields highlighted with error messages; form not submitted

---

### Scenario 4 (Edge Case) – Transfer Amount of Zero is Rejected

**Type:** Edge Case | **Actor:** Authenticated bank customer | **Automated:** 

**Steps:**
1. Navigate to the Money Transfer page
2. Enter a valid IBAN
3. Enter transfer amount: `0.00`
4. Click **Continue**

**Expected Result:** Error shown — amount must be greater than zero; form does not proceed

---

### Scenario 5 (Positive) – Currency Selection Updates Amount Field

**Type:** Positive | **Actor:** Authenticated bank customer | **Automated:** 

**Steps:**
1. Navigate to the Money Transfer page
2. Select transfer type: **International**
3. Click the currency dropdown and select **USD**
4. Enter a valid USD amount
5. Proceed with the form

**Expected Result:** Currency switches to USD; form accepts USD-denominated input correctly

---

### Scenario 6 (API) – Transfer Endpoint Returns 200 for Valid Payload

**Type:** API Positive | **Actor:** System / API client | **Automated:** 

**Steps:**
1. Construct a valid transfer payload (IBAN, amount, currency, description)
2. Add `Authorization: Bearer <token>` header
3. Send `POST /api/transfers`
4. Inspect response status code and body

**Expected Result:** `HTTP 200 OK` with `transactionId` and `status: "PENDING"` in response body

---

### Scenario 7 (Performance) – 50 Concurrent Users on Money Transfer Page

**Type:** Performance Load Test | **Actor:** Simulated virtual users (K6) | **Automated:** ✅ K6

**Steps:**
1. Ramp up from 0 → 50 virtual users over 30 seconds
2. Each VU navigates to the Money Transfer page and fills the form
3. Sustain 50 VUs for 1 minute, then ramp down

**Expected Result:** p95 response time < 3 seconds; error rate < 1%

---

## 7. Run Instructions

**Prerequisites:** Node.js 20.x · Java 17+ · Maven 3.9+ · K6 · Allure CLI (`npm install -g allure-commandline`)

```bash
# 1. Clone the repository
git clone https://github.com/your-org/Bootcamp2025.3_Team6.git
cd Bootcamp2025.3_Team6

# 2. Set up environment
cp .env.example .env
# Edit .env: set BASE_URL, API_BASE_URL, AUTH_TOKEN

# 3. Run UI Tests (Playwright)
cd ui-tests && npm ci
npx playwright install --with-deps
npx playwright test --reporter=allure-playwright

# 4. Run API Tests (Rest Assured)
cd ../api-tests && mvn test -Pallure

# 5. Run Performance Tests (K6)
k6 run performance-tests/scripts/money-transfer-load.js --vus 50 --duration 90s

# 6. Generate & open Allure Report
allure generate allure-results --clean -o allure-report
allure open allure-report
```

---

## 8. CI/CD Link

| Resource                  | Link                                                                          |
|---------------------------|-------------------------------------------------------------------------------|
| 🔧 GitHub Actions Pipeline | [View Workflows](https://github.com/your-org/Bootcamp2025.3_Team6/actions)  |


## 9. AI Summary


---

## 10. Bug Summary

