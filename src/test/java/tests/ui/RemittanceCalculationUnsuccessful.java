package tests.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui_dir.steps.CommonSteps;
import ui_dir.steps.HomeSteps;
import ui_dir.steps.MoneyTransfersSteps;

import static constants.Constants.*;

@Feature("Remittance Fee Calculation - Unsuccessful")
@Test(groups = {"E2E - Remittance Fee Calculation - Unsuccessful - CRM-T2"})
public class RemittanceCalculationUnsuccessful extends BaseTest {

    private HomeSteps homeSteps;
    private CommonSteps commonSteps;
    private MoneyTransfersSteps moneySteps;

    @BeforeClass
    public void setup() {
        homeSteps = new HomeSteps(page);
        commonSteps = new CommonSteps(page);
        moneySteps = new MoneyTransfersSteps(page);

        page.navigate(TBC_URL);
        homeSteps.acceptCookies();
    }

    @Test(priority = 1, description = "Navigate to Personal Products")
    @Severity(SeverityLevel.NORMAL)
    public void navigatePersonalProducts() {
        commonSteps
                .goToPersonal()
                .verifyPersonalProductsDisplayed();
    }

    @Test(priority = 2, description = "Choose Money Transfers")
    @Severity(SeverityLevel.CRITICAL)
    public void navigateMoneyTransfers() {
        commonSteps
                .goToMoneyTransfers();

        moneySteps
                .verifyMoneyTransfersPageOpened();
    }

    @Test(priority = 3, description = "Checking the Remittance Fee Calculation to Greece")
    @Severity(SeverityLevel.CRITICAL)
    public void remittanceFeeForGreece() {
        moneySteps
                .openRemittanceFeeCalculation()
                .openCurrencyOutputDropdown()
                .selectCurrencyOutput(EUR_STRING)
                .enterAmount(faker.getRandomNumber(200,500))
                .openCountryDropdown()
                .selectCountry(GREECE)
                .verifyProvidersDisplayed();
    }

    @Test(priority = 4, description = "Checking the Remittance Fee Calculation to Greece with incorrect format")
    @Severity(SeverityLevel.NORMAL)
    public void invalidAmountValidation() {
        moneySteps
                .enterInvalidAmount(INVALID_INPUT)
                .verifyErrorMessage();
    }
}