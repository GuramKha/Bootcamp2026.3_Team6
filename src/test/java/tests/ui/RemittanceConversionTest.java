package tests.ui;

import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui_dir.steps.CommonSteps;
import ui_dir.steps.HomeSteps;
import ui_dir.steps.MoneyTransfersSteps;

import static constants.Constants.*;

@Feature("Money Transfers")
@Test(groups = {"E2E - Remittance Conversion - CRM-T1"})
public class RemittanceConversionTest extends BaseTest {

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

    @Test(priority = 3, description = "Currency conversion from GBP to USD Remittance Conversion")
    public void conversionGBPToUSD() {
        moneySteps
                .enterAmount(faker.getRandomNumber(100,1000))
                .openCurrencyInputDropdown()
                .selectCurrencyInput(GBP_STRING)
                .openCurrencyOutputDropdown()
                .selectCurrencyOutput(USD_STRING)
                .verifyConversion();
    }

    @Test(priority = 4, description = "Conversion of the increased amount from GBP to USD Remittance Conversion")
    public void conversionOfLargeAmount() {
        moneySteps
                .enterAmount(faker.getRandomNumber(5000,9000))
                .verifyConversion();
    }

    @Test(priority = 5, description = "Currency conversion for the same amount from USD to GBP Remittance Conversion")
    public void currencySwap() {
        moneySteps
                .swapCurrencies()
                .verifyConversion();
    }
}