import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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

    waitForElementAndClick(
     By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
     "Cannot find Search Wikipedia input",
     5
    );

    waitForElementAndSendKeys(
     By.xpath("//*[contains(@text, 'Search…')]"),
     "Java",
     "Cannot find search input",
     5
    );

    waitForElementPresent(
      By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
      "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
      5
    );
  }

  @Test
  public void testCancelSearch(){
    waitForElementAndClick(
      By.id("org.wikipedia:id/search_container"),
     "Cannot find Search Wikipedia input",
     5
    );

    waitForElementAndClick(
      By.id("org.wikipedia:id/search_close_btn"),
     "Cannot find X to cancel search",
     5
    );

    waitForElementNotPresent(
      By.id("org.wikipedia:id/search_close_btn"),
     "X ids still present on the page",
     5
    );
  }

  @Test
  public void testClearField(){
    waitForElementAndClick(
      By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
     "Cannot find Search Wikipedia input",
     5
    );

    waitForElementAndSendKeys(
      By.xpath("//*[contains(@text, 'Search…')]"),
     "Java",
     "Cannot find search input",
     5
    );

    waitForElementAndClear(
     By.id("org.wikipedia:id/search_src_text"),
    "Cannot find search input",
    5
    );

    waitForElementAndClick(
      By.id("org.wikipedia:id/search_close_btn"),
     "Cannot find X to cancel search",
     5
    );

    waitForElementNotPresent(
      By.id("org.wikipedia:id/search_close_btn"),
     "X ids still present on the page",
     5
    );
  }

  @Test
  public void testCompareArticleTitle(){
    waitForElementAndClick(
      By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
     "Cannot find Search Wikipedia input",
     5
    );

    waitForElementAndSendKeys(
      By.xpath("//*[contains(@text, 'Search…')]"),
     "Java",
     "Cannot find search input",
     5
    );

    waitForElementAndClick(
      By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
     "Cannot find 'Object-oriented programming language'",
     5
    );

    WebElement titleElement = waitForElementPresent(
      By.id("org.wikipedia:id/view_page_title_text"),
     "Cannot find article title",
     15
    );

    String articleElement = titleElement.getAttribute("text");

    Assert.assertEquals(
     "We see unexpected title",
     "Java (programming language)",
      articleElement
    );

  }
  //org.wikipedia:id/search_src_text
  //Search…

  @Test
  public void testSearchTextPresent(){
    waitForElementAndClick(
      By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
     "Cannot find Search Wikipedia input",
     5
    );

    WebElement searchFieldElement = waitForElementPresent(
      By.id("org.wikipedia:id/search_src_text"),
     "Cannot find Search field",
     15
    );

    String searchFieldElementText = searchFieldElement.getAttribute("text");

    Assert.assertEquals(
     "We see unexpected text",
     "Search…",
      searchFieldElementText
    );

  }

  @Test
  public void testSearchList() throws Exception {
    waitForElementAndClick(
      By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
     "Cannot find Search Wikipedia input",
     5
    );

    waitForElementAndSendKeys(
      By.xpath("//*[contains(@text, 'Search…')]"),
     "Milk",
     "Cannot find search input",
     5
    );

    int articlesCount = waitForElementsAndCount(
      By.id( "org.wikipedia:id/page_list_item_container"),
     "Cannot find article element",
     15
    );

    assert articlesCount > 2 : "Search for the word 'milk'. We are waiting for several articles. Received a few articles.";

    waitForElementAndClick(
      By.id("org.wikipedia:id/search_close_btn"),
     "Cannot find X to cancel search",
     15
    );

    waitForElementNotPresent(
      By.id("org.wikipedia:id/page_list_item_container"),
     "Canceled the search. There was a lack of search result. The search result remained.",
     15
    );
  }

  private int waitForElementsAndCount(By by, String errorMessage, long timeoutInSeconds){
    waitForElementPresent(by, errorMessage, timeoutInSeconds);

    int count = driver.findElements(by).size();
    return count;
  }

  private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds){
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(errorMessage + "\n");

    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutSeconds){
    WebDriverWait wait = new WebDriverWait(driver,timeoutSeconds);
    wait.withMessage(errorMessage + "\n");

    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutSeconds){
    WebElement element = waitForElementPresent(by, errorMessage, timeoutSeconds);
    element.clear();
    return element;
  }
}