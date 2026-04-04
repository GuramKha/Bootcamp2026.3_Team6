package ui_dir.steps;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import ui_dir.pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.Constants.MONEY_TRANSFERS_URL;
import static constants.Constants.TBC_URL;

public class HomeSteps {

    private final HomePage homePage;
    private Page page;

    public HomeSteps(Page page) {
        this.homePage = new HomePage(page);
        this.page = page;
    }

    @Step("Accept cookies")
    public void acceptCookies() {
        assertThat(page).hasURL(TBC_URL);
        if (homePage.cookiesAcceptButton.isVisible()) {
            homePage.cookiesAcceptButton.click();
        }
    }
}