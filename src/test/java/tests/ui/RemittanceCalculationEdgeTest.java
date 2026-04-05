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
    public void navigatePersonalProducts() {
        commonSteps
                .goToPersonal()
                .verifyPersonalProductsDisplayed();
    }

    @Test(priority = 2, description = "Choose Money Transfers")
    public void navigateMoneyTransfers() {
        commonSteps
                .goToMoneyTransfers();

        moneySteps
                .verifyMoneyTransfersPageOpened();
    }

    @Test(priority = 3, description = "Checking the Remittance Fee Calculation to United States for minimum amount")
    public void minAmountValidation() {
        moneySteps
                .openRemittanceFeeCalculation()
                .enterAmount(MIN_INPUT)
                .openCurrencyOutputDropdown()
                .selectCurrencyOutput(USD_STRING)
                .openCountryDropdown()
                .selectCountry(USA)
                .verifyProvidersDisplayed();
    }

    @Test(priority = 4, description = "Checking the Remittance Fee Calculation to United States for maximum amount")
    public void maxAmountValidation() {
        moneySteps
                .openRemittanceFeeCalculation()
                .enterAmount(MAX_AMOUNT)
                .verifyProvidersDisplayed();
    }
}