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

        driver.navigate().back();
        sleep(5000);
    }

    @Test(priority = 2)
    public void testAddRemoveElements() {
        driver.findElement(By.linkText("Add/Remove Elements")).click();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("add_remove_elements"),
                "Add/Remove Elements page did not open."
        );

        // Add one element
        driver.findElement(By.xpath("//button[text()='Add Element']")).click();

        // Delete the element
        driver.findElement(By.xpath("//button[text()='Delete']")).click();

        // Add 3 elements
        for (int i = 0; i < 3; i++) {
            driver.findElement(By.xpath("//button[text()='Add Element']")).click();
        }

        // Delete all 3 elements
        for (int i = 0; i < 3; i++) {
            driver.findElement(By.xpath("//button[text()='Delete']")).click();
        }

        // Go back to homepage
        driver.navigate().back();
        sleep(5000);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    @AfterClass
    public void tearDown() {
        // Do not close until you say so
        // driver.quit();
    }
    @Test(priority = 3)
    public void testBasicAuth() {

        // Step 1: Click Basic Auth link
        driver.findElement(By.linkText("Basic Auth")).click();

        // Step 2: Login with admin/admin using URL authentication
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        // Step 3: Verify authorized
        String successText = driver.findElement(By.tagName("p")).getText();
        Assert.assertTrue(
                successText.contains("Congratulations"),
                "Admin/Admin login did not authorize correctly."
        );

        // Step 4: Go back to the main homepage
        driver.get("https://the-internet.herokuapp.com/");
        sleep(5000);

        // Step 5: Click Basic Auth again
        driver.findElement(By.linkText("Basic Auth")).click();

        // Step 6: Login with bret/bret (expected to FAIL)
        driver.get("https://bret:bret@the-internet.herokuapp.com/basic_auth");

        // Step 7: Verify NOT authorized (this is a PASS condition)
        boolean unauthorized =
                driver.getPageSource().toLowerCase().contains("not authorized")
                        || driver.getPageSource().toLowerCase().contains("unauthorized")
                        || driver.getTitle().contains("401")
                        || driver.getCurrentUrl().contains("basic_auth") == false;

        Assert.assertTrue(
                unauthorized,
                "Expected Bret/Bret to be unauthorized, but the page did not show an unauthorized state."
        );

        // Step 8: Return to homepage
        driver.get("https://the-internet.herokuapp.com/");
        sleep(5000);
    }
}