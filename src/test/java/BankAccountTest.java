import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.BankPage;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {

    WebDriver driver;
    BankPage bankPage;

    @BeforeEach
    void setUp() {

        driver = new ChromeDriver();

        String path = Paths.get("src/test/resources/app/bank.html")
                .toAbsolutePath()
                .toUri()
                .toString();

        driver.get(path);

        bankPage = new BankPage(driver);
    }

    @AfterEach
    void tearDown() throws InterruptedException {

        Thread.sleep(3000);

        driver.quit();
    }

    void loginAsTestUser() throws InterruptedException {

        Thread.sleep(1000);

        bankPage.enterUsername("test");

        Thread.sleep(1000);

        bankPage.enterPassword("test123");

        Thread.sleep(1000);

        bankPage.clickLoginButton();

        Thread.sleep(1000);
    }

    @Test
    void userCanLoginAndWithdrawMoney() throws InterruptedException {

        loginAsTestUser();

        assertEquals("Login successful", bankPage.getMessageText());

        assertEquals("100", bankPage.getBalanceText());

        bankPage.enterWithdrawAmount("30");

        Thread.sleep(1000);

        bankPage.clickWithdrawButton();

        Thread.sleep(1000);

        assertEquals("70", bankPage.getBalanceText());

        assertEquals("Withdrawal successful", bankPage.getWithdrawMessage());

        assertEquals(
                "Withdrawal: 30 PLN",
                bankPage.getTransactionTexts().get(0)
        );
    }

    @Test
    void userCannotWithdrawMoreMoneyThanBalance() throws InterruptedException {

        loginAsTestUser();

        bankPage.enterWithdrawAmount("150");

        Thread.sleep(1000);

        bankPage.clickWithdrawButton();

        Thread.sleep(1000);

        assertEquals("100", bankPage.getBalanceText());

        assertEquals("Insufficient funds", bankPage.getWithdrawMessage());
    }

    @Test
    void userCanAnalyzeTransactionsUsingStreams() throws InterruptedException {

        loginAsTestUser();

        int[] withdrawals = {10, 20, 30};

        for (int amount : withdrawals) {

            bankPage.enterWithdrawAmount(String.valueOf(amount));

            Thread.sleep(1000);

            bankPage.clickWithdrawButton();

            Thread.sleep(1000);
        }

        var transactionTexts = bankPage.getTransactionTexts();

        System.out.println(transactionTexts);

        long transactionCount = transactionTexts.stream().count();

        assertEquals(3, transactionCount);

        int totalWithdrawals = transactionTexts.stream()
                .map(text -> text.replace("Withdrawal: ", ""))
                .map(text -> text.replace(" PLN", ""))
                .mapToInt(Integer::parseInt)
                .sum();

        assertEquals(60, totalWithdrawals);
    }
}