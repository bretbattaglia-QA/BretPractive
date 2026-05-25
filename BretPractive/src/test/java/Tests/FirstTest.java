package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

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
        } catch (InterruptedException ignored) {
        }
    }

    @AfterClass
    public void tearDown() {
        // Do not close until you say so
        // driver.quit();
    }

    @Test(priority = 3)

    public void testHerokuAppFlow() {

        // Step 1: Go to homepage
        driver.get("https://the-internet.herokuapp.com");

        // Step 2: Basic Auth - valid
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        boolean validSuccess = driver.getPageSource().contains("Congratulations");
        Assert.assertTrue(validSuccess, "Admin/Admin should be authorized.");

        // Step 3: Basic Auth - invalid
        driver.get("https://bret:bret@the-internet.herokuapp.com/basic_auth");
        boolean invalidSuccess = driver.getPageSource().contains("Congratulations");
        Assert.assertFalse(invalidSuccess, "Bret/Bret should NOT be authorized.");

        // Step 4: Return to homepage
        driver.get("https://the-internet.herokuapp.com");

        // Step 5: Go to Checkboxes page
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement firstCheckbox = driver.findElements(By.cssSelector("#checkboxes input")).get(0);
        if (!firstCheckbox.isSelected()) firstCheckbox.click();
        Assert.assertTrue(firstCheckbox.isSelected(), "First checkbox should be selected.");

        // Step 6: Go to Dropdown page
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        dropdown.selectByValue("1");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Option 1");

        // Step 7: Go to Dynamic Loading (Example 1)
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!");

        // Step 8: Go to Drag and Drop
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        WebElement columnA = driver.findElement(By.id("column-a"));
        WebElement columnB = driver.findElement(By.id("column-b"));
        new Actions(driver).dragAndDrop(columnA, columnB).perform();
        Assert.assertEquals(columnA.getText(), "B", "Column A should now contain B.");

        // Step 9: Go to File Upload
        driver.get("https://the-internet.herokuapp.com/upload");
        WebElement fileInput = driver.findElement(By.id("file-upload"));
        fileInput.sendKeys("C:\\path\\to\\your\\file.txt"); // <-- change this path
        driver.findElement(By.id("file-submit")).click();
        Assert.assertTrue(driver.getPageSource().contains("File Uploaded!"));

        // Step 10: Go to Form Authentication
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Assert.assertTrue(driver.getPageSource().contains("You logged into a secure area!"));

        // Step 11: Logout
        driver.findElement(By.cssSelector("a[href='/logout']")).click();
        Assert.assertTrue(driver.getPageSource().contains("You logged out of the secure area!"));

        // Step 12: Final return to homepage
        driver.get("https://the-internet.herokuapp.com");
        sleep(3000);
    }
}