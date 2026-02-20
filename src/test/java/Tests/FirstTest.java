package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");
    }

    @Test(priority = 1)
    public void testABTesting() {
        driver.findElement(By.linkText("A/B Testing")).click();
        Assert.assertTrue(
                driver.findElement(By.tagName("h3")).getText().contains("A/B Test"),
                "A/B Testing page did not open."
        );

        // Go back to homepage
        driver.navigate().back();

        // 5-second wait for future homepage logic
        sleep(5000);
    }

    @Test(priority = 2)
    public void testAddRemoveElements() {
        driver.findElement(By.linkText("Add/Remove Elements")).click();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("add_remove_elements"),
                "Add/Remove Elements page did not open."
        );

        // Go back to homepage
        driver.navigate().back();

        // 5-second wait again
        sleep(5000);
    }

    // Helper method for clean sleeps
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    @AfterClass
    public void tearDown() {
        // DO NOT CLOSE until you say so
        // driver.quit();
    }
}