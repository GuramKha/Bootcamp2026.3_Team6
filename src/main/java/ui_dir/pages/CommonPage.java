package ui_dir.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CommonPage {
    public Locator personal
            ,moneyTransfers
            ,activePersonal;

    public CommonPage(Page page) {
        this.personal = page.locator("//div[contains(text(), 'Personal')]").first();
        this.moneyTransfers = page.locator("//button[.//span[text()='Money Transfers']]");
        this.activePersonal = page.locator("tbcx-pw-navigation-item tbcx-pw-navigation-item--active");
    }
}