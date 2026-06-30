package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class HomePage_Steps {
    public BrowserManager browserManager;

    public HomePage_Steps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Given("I navigate to the webdriveruniversity homepage")
    public void i_navigate_to_the_webdriveruniversity_homepage() {
        browserManager.page.navigate("https://www.webdriveruniversity.com/");
    }

    @When("I click on the contact us button")
    public void i_click_on_the_contact_us_button() {
        browserManager.page = browserManager.context.waitForPage(() -> {
            browserManager.page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CONTACT US Contact Us Form")).click();
        });

        browserManager.page.bringToFront();
        //mostRecentPage.pause();
    }


}
