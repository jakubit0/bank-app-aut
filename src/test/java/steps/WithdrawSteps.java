package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BankPage;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawSteps {

    WebDriver driver;
    BankPage bankPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();

        String path = Paths.get("src/test/resources/app/bank.html")
                .toAbsolutePath()
                .toUri()
                .toString();

        driver.get(path);

        bankPage = new BankPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("user is logged in")
    public void userIsLoggedIn(){

        bankPage.enterUsername("test");

        bankPage.enterPassword("test123");

        bankPage.clickLoginButton();

        bankPage.waitForAccountSection();

        assertEquals("Login successful", bankPage.getMessageText());
    }

    @When("user withdraws {int} PLN")
    public void userWithdrawsPLN(int amount){

        bankPage.enterWithdrawAmount(String.valueOf(amount));
        bankPage.clickWithdrawButton();

    }

    @Then("balance should be {int} PLN")
    public void balanceShouldBePLN(int expectedBalance) {
        assertEquals(String.valueOf(expectedBalance), bankPage.getBalanceText());
    }

    @Then("transaction history should contain {string}")
    public void transactionHistoryShouldContain(String expectedTransaction) {
        assertEquals(expectedTransaction, bankPage.getTransactionTexts().get(0));
    }
    @Then("error message should be {string}")
    public void errorMessageShouldBe(String expectedMessage) {
        assertEquals(expectedMessage, bankPage.getWithdrawMessage());
    }
}