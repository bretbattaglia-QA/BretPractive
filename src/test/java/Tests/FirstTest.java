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
}