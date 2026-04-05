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
@Test(groups = {"E2E - Remittance Conversion - CRM-T1"})
public class RemittanceConversionTest extends BaseTest {

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

    @Test(priority = 3, description = "Currency conversion from GBP to USD Remittance Conversion")
    @Severity(SeverityLevel.BLOCKER)
    public void conversionGBPToUSD() {
        moneyTransfersSteps
                .enterAmount(faker.getRandomNumber(10,100))
                .selectCurrencyDropdown(USD_STRING)
                .selectCurrency(GBP_STRING)
                .selectCurrencyDropdown(GEL_STRING)
                .selectCurrency(USD_STRING)
                .verifyConversion();
    }

    @Test(priority = 4, description = "Conversion of the increased amount from GBP to USD Remittance Conversion")
    @Severity(SeverityLevel.CRITICAL)
    public void conversionOfLargeAmount() {
        moneyTransfersSteps
                .enterAmount(faker.getRandomNumber(200,300))
                .verifyConversion();
    }

    @Test(priority = 5, description = "Currency conversion for the same amount from USD to GBP Remittance Conversion")
    @Severity(SeverityLevel.CRITICAL)
    public void currencySwap() {
        moneyTransfersSteps
                .swapCurrencies()
                .verifyConversion();
    }
}
