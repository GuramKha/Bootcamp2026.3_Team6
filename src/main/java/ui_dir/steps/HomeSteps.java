package ui_dir.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import ui_dir.pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.MONEY_TRANSFERS_URL;
import static constants.Constants.TBC_URL;

public class HomeSteps {
    private final HomePage homePage;

    public HomeSteps(Page page) {
        this.homePage = new HomePage(page);
    }

    @Step("Decline cookies")
    public HomeSteps declineCookies() {
        homePage.declineCookies.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));

        homePage.declineCookies.click();

        return this;
    }
}