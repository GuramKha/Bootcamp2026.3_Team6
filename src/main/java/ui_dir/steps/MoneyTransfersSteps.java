package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import ui_dir.pages.MoneyTransfersPage;
import utils.RateUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.ERROR;
import static org.testng.AssertJUnit.assertTrue;

public class MoneyTransfersSteps {

    private final MoneyTransfersPage moneyTransfersPage;
    private final Page page;

    public MoneyTransfersSteps(Page page) {
        this.moneyTransfersPage = new MoneyTransfersPage(page);
        this.page = page;
    }

    @Step("Verify Money Transfers page opened")
    public MoneyTransfersSteps verifyMoneyTransfersPageOpened() {
        assertThat(moneyTransfersPage.navbarMoneyTransfers).isVisible();

        return this;
    }

    @Step("Enter amount: {amount}")
    public MoneyTransfersSteps enterAmount(String amount) {
        moneyTransfersPage.moneyInput.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.moneyInput.first().fill(amount);

        return this;
    }

    @Step("Select input currency: {currency}")
    public MoneyTransfersSteps selectCurrencyInput(String currency) {
        moneyTransfersPage.currencyItem(currency).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.evaluate("window.scrollBy(0, 500)");
        moneyTransfersPage.currencyItem(currency).click();

        return this;
    }

    @Step("Open currency input dropdown")
    public MoneyTransfersSteps openCurrencyInputDropdown () {
        moneyTransfersPage.currencyDropDownInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownInput.click();

        return this;
    }

    @Step("Select output currency: {currency}")
    public MoneyTransfersSteps selectCurrencyOutput(String currency) {
        moneyTransfersPage.currencyItem(currency).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyItem(currency).click(new Locator.ClickOptions().setForce(true));

        return this;
    }

    @Step("Open currency output dropdown")
    public MoneyTransfersSteps openCurrencyOutputDropdown () {
        moneyTransfersPage.currencyDropDownOutput.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.currencyDropDownOutput.click(new Locator.ClickOptions().setForce(true));

        return this;
    }

    @Step("Verify conversion")
    public MoneyTransfersSteps verifyConversion() {
        double rate = RateUtils.extractRate(
                moneyTransfersPage.convertionRate.innerText()
        );

        double amount = RateUtils.parseAmount(
                moneyTransfersPage.moneyInput.first().inputValue()
        );

        double actual = RateUtils.parseAmount(
                moneyTransfersPage.moneyInput.last().inputValue()
        );

        double expected = RateUtils.calculateExpected(amount, rate);
        double roundedExpected = RateUtils.round(expected);

        assertTrue(RateUtils.isCloseEnough(actual, roundedExpected));

        return this;
    }

    @Step("Swap currencies")
    public MoneyTransfersSteps swapCurrencies() {
        moneyTransfersPage.swapButton.click();

        return this;
    }

    @Step("Open remittance fee calculation")
    public MoneyTransfersSteps openRemittanceFeeCalculation() {
        moneyTransfersPage.remittanceFeeCalculation.click();

        return this;
    }

    @Step("Select country Greece")
    public MoneyTransfersSteps selectCountry(String country) {
        moneyTransfersPage.countryItem.filter(new Locator.FilterOptions().setHasText(country)).click();

        return this;
    }

    @Step("Open country dropdown")
    public MoneyTransfersSteps openCountryDropdown(){
        moneyTransfersPage.countryDropDown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        moneyTransfersPage.countryDropDown.click();

        return this;
    }

    @Step("Verify providers displayed")
    public MoneyTransfersSteps verifyProvidersDisplayed() {
        moneyTransfersPage.cards.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertTrue(moneyTransfersPage.cards.count() > 0);

        return this;
    }

    @Step("Enter invalid amount: {value}")
    public MoneyTransfersSteps enterInvalidAmount(String value) {
        moneyTransfersPage.moneyInput.first().fill(value);

        return this;
    }

    @Step("Verify error message")
    public MoneyTransfersSteps verifyErrorMessage() {
        assertThat(moneyTransfersPage.errorMessage).containsText(ERROR);

        return this;
    }
}