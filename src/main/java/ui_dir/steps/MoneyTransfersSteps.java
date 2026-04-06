package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui_dir.pages.MoneyTransfersPage;
import utils.RateUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.ERROR;
import static constants.Constants.MONEY_TRANSFERS_URL;
import static org.testng.AssertJUnit.assertTrue;

public class MoneyTransfersSteps {
    private final Page page;
    private final MoneyTransfersPage moneyTransfersPage;

    public MoneyTransfersSteps(Page page) {
        this.page = page;
        this.moneyTransfersPage = new MoneyTransfersPage(page);
    }

    @Step("Validate Money Transfers page is displayed")
    public MoneyTransfersSteps validateMoneyTransfersPageDisplayed() {
        assertThat(page).hasURL(MONEY_TRANSFERS_URL);
        assertThat(moneyTransfersPage.moneyTransfersNavbar).isVisible();

        return this;
    }

    @Step("Click desired input currency dropdown")
    public MoneyTransfersSteps selectCurrencyDropdown(String currencyDropdown) {
        moneyTransfersPage.currencyDropdownSelector(currencyDropdown)
                .evaluate("el => el.scrollIntoView({ behavior: 'instant', block: 'center' })");

        moneyTransfersPage.currencyDropdownSelector(currencyDropdown).click();

        return this;
    }

    @Step("Select desired input currency")
    public MoneyTransfersSteps selectCurrency(String currency) {
        moneyTransfersPage.dropdownList.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
        );

        moneyTransfersPage.currencySelector(currency).click();

        return this;
    }

    @Step("Input desired amount")
    public MoneyTransfersSteps enterAmount(String amount) {
        moneyTransfersPage.currencyInput.first().fill(amount);

        return this;
    }

    @Step("Verify conversion")
    public MoneyTransfersSteps verifyConversion() {
        double expected = getExpectedResult();

        page.waitForCondition(() -> {
            double actual = getActualResult();
            return RateUtils.isCloseEnough(actual, expected);
        }, new Page.WaitForConditionOptions().setTimeout(5000));

        assertTrue(RateUtils.isCloseEnough(getActualResult(), expected));

        return this;
    }

    @Step("Swap currencies")
    public MoneyTransfersSteps swapCurrencies() {
        moneyTransfersPage.swapCurrenciesButton.click();

        return this;
    }

    @Step("Open remittance fee calculation")
    public MoneyTransfersSteps openRemittanceFeeCalculation() {
        moneyTransfersPage.remittanceFeeCalculation.click();

        return this;
    }

    @Step("Open country dropdown")
    public MoneyTransfersSteps openCountryDropdown(){
        moneyTransfersPage.countryDropdown
                .evaluate("el => el.scrollIntoView({ behavior: 'instant', block: 'center' })");

        moneyTransfersPage.countryDropdown.click();

        return this;
    }

    @Step("Select country")
    public MoneyTransfersSteps selectCountry(String country) {
        moneyTransfersPage.dropdownList.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
        );

        moneyTransfersPage.countrySelector(country).click(new Locator.ClickOptions().setForce(true));

        moneyTransfersPage.dropdownList.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
                        .setTimeout(60000)
        );

        return this;
    }

    @Step("Verify providers and fees displayed")
    public MoneyTransfersSteps verifyProvidersDisplayed() {
        moneyTransfersPage.cards.first().waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(60000)
        );

        assertThat(moneyTransfersPage.cards.first()).isVisible();

        return this;
    }

    @Step("Enter invalid amount")
    public MoneyTransfersSteps enterInvalidAmount(String value) {
        moneyTransfersPage.currencyInput.first().fill(value);

        return this;
    }

    @Step("Verify error message")
    public MoneyTransfersSteps verifyErrorMessage(String error) {
        assertThat(moneyTransfersPage.errorMessage).containsText(error);

        return this;
    }

    private double getExpectedResult() {
        double amount = RateUtils.parseAmount(moneyTransfersPage.currencyInput.first().inputValue());
        double rate = RateUtils.extractRate(moneyTransfersPage.conversionRate.innerText());
        return RateUtils.round(RateUtils.calculateExpected(amount, rate));
    }

    private double getActualResult() {
        return RateUtils.parseAmount(moneyTransfersPage.currencyInput.last().inputValue());
    }
}
