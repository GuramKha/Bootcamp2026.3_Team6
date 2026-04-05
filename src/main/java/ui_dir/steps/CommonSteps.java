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

    @Step("Hover personal button")
    public CommonSteps hoverPersonalButton() {
        commonPage.personalButton.hover();

        return this;
    }

    @Step("Validate personal products list is displayed")
    public CommonSteps validatePersonalOverlay() {
        assertThat(commonPage.personalButtonOverlay).isVisible();

        return this;
    }

    @Step("Navigate to Money Transfers")
    public CommonSteps navigateToMoneyTransfers() {
        commonPage.moneyTransfersLink.click();

        return this;
    }
}
