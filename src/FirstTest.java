import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "6.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "C:\\Users\\Sergey\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {

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
  public void testCancelSearch() {
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
  public void testClearField() {
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
  public void testCompareArticleTitle() {
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

  @Test
  public void testSearchTextPresent() {
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
            By.id("org.wikipedia:id/page_list_item_container"),
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

  @Test
  public void testSwipeArticle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Appium",
            "Cannot find search input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Object-oriented programming language'",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    swipeUpToFindElement(
            By.xpath("//*[contains(@text, 'View page in browser')]"),
            "Cannot find the end of the article",
            20
    );
  }

  @Test
  public void saveFirstArticleToMyList(){
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

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (programming language)']"),
            "Cannot find article title",
            15
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find 'More options' button",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find 'Add to reading list' menu item",
            15
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'GOT IT' button",
            5
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            15
    );

    String nameOfFolder = "Test";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot put text into articles folder input",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press 'OK' button",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot press 'Navigate up' button",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to my list",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@text='" + nameOfFolder +"']"),
            "Cannot find created folder",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
           "Cannot fine saved article"
    );

    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );
  }

  private int waitForElementsAndCount(By by, String errorMessage, long timeoutInSeconds) {
    waitForElementPresent(by, errorMessage, timeoutInSeconds);

    int count = driver.findElements(by).size();
    return count;
  }

  private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(errorMessage + "\n");

    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
    wait.withMessage(errorMessage + "\n");

    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeoutSeconds);
    element.clear();
    return element;
  }

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int startY = (int) (size.height * 0.8);
    int endY = (int) (size.height * 0.2);


    action
            .press(x, startY)
            .waitAction(timeOfSwipe)
            .moveTo(x, endY)
            .release()
            .perform();
  }

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {

    int alreadySwiped = 0;
    while (driver.findElements(by).size() == 0) {
      if (alreadySwiped > maxSwipes) {
        waitForElementPresent(by, "Cannot find element by swiping. \n" + errorMessage, 0);
        return;
      }
      swipeUpQuick();
      ++alreadySwiped;
    }
  }

  protected void swipeElementToLeft(By by, String errorMessage){
    WebElement element = waitForElementPresent(
            by,
            errorMessage,
            10
    );

    int leftX = element.getLocation().getX();
    int rightX = leftX + element.getSize().getWidth();

    int upperY = element.getLocation().getY();
    int lowerY = upperY + element.getSize().getHeight();

    int middleY = (upperY + lowerY) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(rightX, middleY)
            .waitAction(500)
            .moveTo(leftX, middleY)
            .release()
            .perform();
  }
}