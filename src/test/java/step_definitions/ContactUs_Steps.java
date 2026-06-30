package step_definitions;

import browser.BrowserManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class ContactUs_Steps {
    public BrowserManager browserManager;

    public ContactUs_Steps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @And("I type a first name")
    public void i_type_a_first_name() {
        browserManager.page.getByPlaceholder("First Name").fill("Joe");
    }

    @And("I type a last name")
    public void i_type_a_last_name() {
        browserManager.page.getByPlaceholder("Last Name").fill("Blogs");
    }

    @And("I enter an email address")
    public void i_enter_an_email_address() {
        browserManager.page.getByPlaceholder("Email Address").fill("joe_blogs@example.com");
    }

    @And("I type a comment")
    public void i_type_a_comment() {
        browserManager.page.getByPlaceholder("Comments").fill("Hello World!!");
    }

    @And("I click on the submit button")
    public void i_click_on_the_submit_button() {
        //Set a custom timeout of 10 seconds
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions().setTimeout(10000); //10 seconds

        //wait for the button to load
        browserManager.page.waitForSelector("input[value='SUBMIT']", options);

        //Once loaded, click on button
        browserManager.page.click("input[value='SUBMIT']");
    }

    @Then("I should be presented with a successful contact us submission message")
    public void i_should_be_presented_with_a_successful_contact_us_submission_message() {
        browserManager.page.waitForSelector("#contact_reply h1", new Page.WaitForSelectorOptions().setTimeout(10000)); 	//Set a custom timeout (10 seconds)

        Locator locator = browserManager.page.locator("#contact_reply h1");
        assertThat(locator).isVisible();
        assertThat(locator).hasText("Thank You for your Message!");
    }
    @Then("I should be presented with a unsuccessful contact us submission message")
    public void i_should_be_presented_with_a_unsuccessful_contact_us_submission_message() {
        //wait for the <body> element
        browserManager.page.waitForSelector("body");

        //Locator of the body element
        Locator bodyElement = browserManager.page.locator("body");

        // Extract text from the element
        String bodyText = bodyElement.textContent();

        // Assert that the body text matches the expected pattern
        Pattern pattern = Pattern.compile("Error: (all fields are required|Invalid email address)");
        Matcher matcher = pattern.matcher(bodyText);
        Assert.assertTrue(matcher.find(), "The body text does not match the expected error message. Found Text: " + bodyText);
    }


}
