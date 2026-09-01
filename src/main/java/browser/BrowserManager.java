package browser;

import com.microsoft.playwright.*;

import java.awt.*;

public class BrowserManager {
    public Playwright playwright; //used to create an instance of the Chromium, Firefox browser etc.
    public Page page; //is the single tab or window in the browser.
    public BrowserContext context; //is the isolated browser session.
    public Browser browser; //represents the browser instance.
    public byte[] takeScreenshot() {
        if (page != null) {
            return page.screenshot();
        }
        return new byte[0];
    }


    public void setUp() {
        System.out.println("Setting up Playwright...");
        // Get viewport size of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        System.out.println("Playwright setup complete!");
    }

    public void tearDown() {
        System.out.println("Tearing down Playwright...");
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("Playwright teardown complete!");
    }
}
