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

@Feature("Money Transfers")
@Test(groups = {"E2E - Remittance Fee Calculation - Unsuccessful - CRM-T2"})
public class RemittanceCalculationUnsuccessfulTest extends BaseTest {

    private CommonSteps commonSteps;
    private HomeSteps homeSteps;
    private MoneyTransfersSteps moneyTransfersSteps;

    @BeforeClass
    public void setup() {
        commonSteps = new CommonSteps(page);
        homeSteps = new HomeSteps(page);
        moneyTransfersSteps = new MoneyTransfersSteps(page);

        page.navigate(TBC_URL);
        homeSteps.declineCookies();
    }

    @Test(priority = 1, description = "Navigate to Personal Products")
    @Severity(SeverityLevel.NORMAL)
    public void navigatePersonalProducts() {
        commonSteps
                .hoverPersonalButton()
                .validatePersonalOverlay();
    }

    @Test(priority = 2, description = "Choose Money Transfers")
    @Severity(SeverityLevel.CRITICAL)
    public void navigateMoneyTransfers() {
        commonSteps
                .navigateToMoneyTransfers();

        moneyTransfersSteps
                .validateMoneyTransfersPageDisplayed();
    }

    @Test(priority = 3, description = "Checking the Remittance Fee Calculation to Greece")
    @Severity(SeverityLevel.CRITICAL)
    public void remittanceFeeForGreece() {
        moneyTransfersSteps
                .openRemittanceFeeCalculation()
                .selectCurrencyDropdown(GEL_STRING)
                .selectCurrency(EUR_STRING)
                .enterAmount(faker.getRandomNumber(50,150))
                .openCountryDropdown()
                .selectCountry(GREECE)
                .verifyProvidersDisplayed();
    }

    @Test(priority = 4, description = "Checking the Remittance Fee Calculation to Greece with incorrect format")
    @Severity(SeverityLevel.NORMAL)
    public void invalidAmountValidation() {
        moneyTransfersSteps
                .enterInvalidAmount(INVALID_INPUT)
                .verifyErrorMessage(ERROR);
    }
}
