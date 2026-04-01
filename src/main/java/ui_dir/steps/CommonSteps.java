package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import ui_dir.pages.CommonPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonSteps {

    private final CommonPage commonPage;

    public CommonSteps(Page page) {
        this.commonPage = new CommonPage(page);
    }

    @Step("Go to Personal")
    public CommonSteps goToPersonal() {
        commonPage.personal.hover();

        return this;
    }

    @Step("Verify Personal products displayed")
    public CommonSteps verifyPersonalProductsDisplayed() {
        commonPage.moneyTransfers.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(commonPage.moneyTransfers).isVisible();

        return this;
    }

    @Step("Go to Money Transfers")
    public CommonSteps goToMoneyTransfers() {
        commonPage.moneyTransfers.click();

        return this;
    }
}