package tests.ui;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.FakerSingleton;

import java.io.ByteArrayInputStream;
import java.util.List;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected FakerSingleton faker;

    @Parameters("browser")
    @BeforeClass
    public void setUp(String platform) {
        playwright = Playwright.create();
        boolean isCI = "true".equals(System.getenv("CI"));

        switch (platform.toLowerCase()) {
            case "firefox" -> {
                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(true);
                browser = playwright.firefox().launch(options);
            }
            case "webkit" -> {
                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(true);
                browser = playwright.webkit().launch(options);
            }
            default -> {
                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(true);

                if (isCI) {
                    options.setArgs(List.of(
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--disable-gpu"
                    ));
                }

                browser = playwright.chromium().launch(options);
            }
        }

        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1920, 1080)
        );
        page = browserContext.newPage();
        faker = FakerSingleton.getInstance();
    }

    @AfterMethod
    public void captureOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );

            Allure.addAttachment(
                    "Screenshot - " + result.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );

            Allure.addAttachment(
                    "Page HTML - " + result.getName(),
                    "text/html",
                    new ByteArrayInputStream(page.content().getBytes()),
                    "html"
            );

            Allure.addAttachment(
                    "Failed URL - " + result.getName(),
                    "text/plain",
                    new ByteArrayInputStream(page.url().getBytes()),
                    "txt"
            );
        }
    }

    @AfterClass
    public void tearDown() {
        if (page != null) page.close();
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
