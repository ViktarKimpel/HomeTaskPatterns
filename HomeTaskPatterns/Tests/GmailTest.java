package HomeTaskPatterns.Tests;




import HomeTaskPatterns.BusinessObjects.User;
import HomeTaskPatterns.Pages.GmailMainPage;
import HomeTaskPatterns.Pages.GmailPasswordPage;
import HomeTaskPatterns.Pages.LogInPage;
import HomeTaskPatterns.Pages.StartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import static HomeTaskPatterns.Utils.RandomNumber.DEFAULT_INTERVAL_INT;
import static HomeTaskPatterns.Utils.RandomNumber.getRandomInt;


public class GmailTest {

    private WebDriver driver;

    @BeforeTest(description = "start browser")

    public void StartBrowser() {
        System.setProperty("webdriver.chrome.driver", "d:\\_webdriver\\chromedriver\\chromedriver.exe");

        /*try {
            driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), DesiredCapabilities.chrome());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();


    }

    @Test(description = "mailCreation")
    public void CreateGmail() {

        StartPage startPage = new StartPage(driver);
        startPage.openGmail();
        LogInPage logInPage = startPage.signIn();
        logInPage.fillEmailInput(new User());
        GmailPasswordPage gmailPasswordPage = logInPage.pressNextButton();
        gmailPasswordPage.fillGmailPasswordInput(new User());
        GmailMainPage gmailMainPage = gmailPasswordPage.pressNextButton();
        /*gmailMainPage.waitForElementVisible(By.xpath("//div[text()='COMPOSE']"));*/
        Assert.assertTrue(driver.getTitle().contains("viktar.kimpel@gmail.com"),
                "You have not login to your account");
        gmailMainPage.doubleClick ();
        gmailMainPage.starredButton ();
        gmailMainPage.pressComposeButton();
        gmailMainPage.recipientInput("Imrahil1216@inbox.ru");
        gmailMainPage.subjectInput("Test");
        gmailMainPage.bodyInput("Hello, World!");
        gmailMainPage.saveAndCloseButton();
        gmailMainPage.draftMailButton();
        gmailMainPage.draftButton();
        gmailMainPage.bodyResult();
        Assert.assertTrue(driver.getTitle().contains("viktar.kimpel@gmail.com"),
                "You have invalid email");
        gmailMainPage.sendButton();
        gmailMainPage.sentMailButton();
        gmailMainPage.draftsButton();
        gmailMainPage.noDraftsButton();

        gmailMainPage.pressComposeButton();
        gmailMainPage.recipientInput("Imrahil1216@inbox.ru");
        gmailMainPage.subjectInput("Test");
        gmailMainPage.bodyInput(getRandomInt(DEFAULT_INTERVAL_INT));
        gmailMainPage.sendButton();
        gmailMainPage.imageButton();
        gmailMainPage.signOutButton();
    }

    @AfterClass(description = "Close browser")

    public void kill(){
        driver.quit();
    }
}
