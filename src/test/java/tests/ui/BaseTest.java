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
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected FakerSingleton faker;
    protected String browserNameLower;

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browserName) {
        playwright = Playwright.create();

        browserNameLower = browserName.toLowerCase();

        boolean isCI = "true".equals(System.getenv("CI"));
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(isCI);

        if (isCI && !browserNameLower.equals("webkit")) {
            launchOptions.setArgs(List.of(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            ));
        }

        browser = switch (browserNameLower) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> playwright.chromium().launch(launchOptions);
        };

        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1920, 1080)
        );

        page = browserContext.newPage();

        faker = FakerSingleton.getInstance();
    }

    @AfterMethod(alwaysRun = true)
    public void captureOnFailure(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE || page == null) {
            return;
        }

        String testName = result.getName();

        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(
                "Screenshot - " + testName,
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
        );

        Allure.addAttachment(
                "Page HTML - " + testName,
                "text/html",
                new ByteArrayInputStream(page.content().getBytes(StandardCharsets.UTF_8)),
                "html"
        );

        Allure.addAttachment(
                "Failed URL - " + testName,
                "text/plain",
                new ByteArrayInputStream(page.url().getBytes(StandardCharsets.UTF_8)),
                "txt"
        );
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browserContext != null) {
            browserContext.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
