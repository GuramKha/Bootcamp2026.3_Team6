package tests.ui;


import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui_dir.steps.CommonSteps;
import ui_dir.steps.HomeSteps;
import ui_dir.steps.MoneyTransfersSteps;

import static constants.Constants.*;

@Feature("Money Transfers")
@Test(groups = {"E2E - Remittance Fee Calculation for minimum and maximum amounts -  Edge case - CRM-T3"})
public class RemittanceCalculationEdgeTest extends BaseTest {

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
    public void navigatePersonalProducts() {
        commonSteps
                .hoverPersonalButton()
                .validatePersonalOverlay();
    }

    @Test(priority = 2, description = "Choose Money Transfers")
    public void navigateMoneyTransfers() {
        commonSteps
                .navigateToMoneyTransfers();

        moneyTransfersSteps
                .validateMoneyTransfersPageDisplayed();
    }

    @Test(priority = 3, description = "Checking the Remittance Fee Calculation to United States for minimum amount")
    public void minAmountValidation() {
        moneyTransfersSteps
                .openRemittanceFeeCalculation()
                .enterAmount(MIN_INPUT)
                .selectCurrencyDropdown(GEL_STRING)
                .selectCurrency(USD_STRING)
                .openCountryDropdown()
                .selectCountry(USA)
                .verifyProvidersDisplayed();
    }

    @Test(priority = 4, description = "Checking the Remittance Fee Calculation to United States for maximum amount")
    public void maxAmountValidation() {
        moneyTransfersSteps
                .openRemittanceFeeCalculation()
                .enterAmount(MAX_AMOUNT)
                .verifyProvidersDisplayed();
    }
}
