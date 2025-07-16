package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.WDListener;

import java.time.Duration;
import org.openqa.selenium.safari.SafariDriver;

public class ApplicationManager {
    static String browser = System.getProperty("browser", "chrome");
    private WebDriver driver;

    public WebDriver getDriver(){
        return driver;
    }

    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        // logger.info("Start test --> "+ LocalDate.now());
        // driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        switch(browser.toLowerCase()){
            case "safari":
                driver  = new SafariDriver();
                logger.info("Start test in Safari");
                break;
            case "chrome":
                driver = new ChromeDriver(chromeOptions);
                logger.info("Start test in Chrome");
                break;
            default:
                driver = new ChromeDriver(chromeOptions);
                logger.info("Start test in Chrome");
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);
    }

    @AfterMethod(enabled = true, alwaysRun = true)
    public void tearDown(){
        // logger.info("Stop test -----------");
        if (driver != null)
            driver.quit();
    }
}