package ui_dir.steps;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import ui_dir.pages.HomePage;

public class HomeSteps {

    private final HomePage homePage;

    public HomeSteps(Page page) {
        this.homePage = new HomePage(page);
    }

    @Step("Accept cookies")
    public void acceptCookies() {
        if (homePage.cookiesAcceptButton.isVisible()) {
            homePage.cookiesAcceptButton.click();
        }
    }
}