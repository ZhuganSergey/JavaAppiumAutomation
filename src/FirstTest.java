import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName","Android");
    capabilities.setCapability("deviceName","AndroidTestDevice");
    capabilities.setCapability("platformVersion","6.0");
    capabilities.setCapability("automationName","Appium");
    capabilities.setCapability("appPackage","org.wikipedia");
    capabilities.setCapability("appActivity",".main.MainActivity");
    capabilities.setCapability("app","C:\\Users\\Sergey\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown(){
    driver.quit();
  }

  @Test
  public void firstTest(){
    WebElement elementToInitSearch = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
    elementToInitSearch.click();

    WebElement elementToEnterSearchLine = waitForElementPresentByXpath(
            "//*[contains(@text, 'Search…')]",
            "Cannot find search input"
    );

    elementToEnterSearchLine.sendKeys("Java");
    waitForElementPresentByXpath(
            "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

  }

  private WebElement waitForElementPresentByXpath(String xpath, String errorMessage, long timeoutInSeconds){
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(errorMessage + "\n");
    By by = By.xpath(xpath);

    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private WebElement waitForElementPresentByXpath(String xpath, String errorMessage){
    return waitForElementPresentByXpath(xpath, errorMessage, 5);
  }
}
