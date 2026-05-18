package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BankPage {

    WebDriver driver;

    public BankPage(WebDriver driver) {
        this.driver = driver;
    }

    By usernameInput = By.id("username");

    By passwordInput = By.id("password");

    By loginButton = By.id("loginButton");

    By messageText = By.id("message");

    By balanceText = By.id("balance");

    By withdrawInput = By.id("withdrawAmount");

    By withdrawButton = By.id("withdrawButton");

    By withdrawMessage = By.id("withdrawMessage");

    By transactionItems = By.cssSelector("#transactionHistory li");

    public void enterUsername(String username) {

        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {

        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {

        driver.findElement(loginButton).click();
    }

    public String getMessageText() {

        return driver.findElement(messageText).getText();
    }

    public String getBalanceText() {

        return driver.findElement(balanceText).getText();
    }

    public void enterWithdrawAmount(String amount) {

        driver.findElement(withdrawInput).clear();

        driver.findElement(withdrawInput).sendKeys(amount);
    }

    public void clickWithdrawButton() {

        driver.findElement(withdrawButton).click();
    }

    public String getWithdrawMessage() {

        return driver.findElement(withdrawMessage).getText();
    }

    public List<String> getTransactionTexts() {

        return driver.findElements(transactionItems)
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}