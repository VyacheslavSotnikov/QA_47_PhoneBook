package pages;

import lombok.Setter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HeaderMenuItem;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage_PB {
    @Setter
    static WebDriver driver;
    Logger logger = LoggerFactory.getLogger(BasePage_PB.class);

    public static void pause(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends  BasePage_PB> T clickButtonHeader(HeaderMenuItem headerMenuItem){
//        pause(3);
//        WebElement element = driver.findElement(By.xpath(headerMenuItem.getLocator()));
        //element.click();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator())));
        element.click();
        switch ((headerMenuItem)){
            case LOGIN -> {return(T) new LoginPage_PB(driver);}
            case ADD -> {return(T) new AddPage(driver);}
            case ABOUT -> {return(T) new AboutPage(driver);}
            case HOME -> {return(T) new HomePage_PB(driver);}
            case CONTACTS -> {return(T) new ContactsPage(driver);}
            case SIGNOUT -> {return(T) new HomePage_PB(driver);}
            default -> throw new IllegalArgumentException("Invalid parameter headerMenuItem");
        }
    }

    public boolean isElementPresent(WebElement element){
        return element.isDisplayed();
    }

    public boolean isTextInElementPresent(WebElement element, String text){
        return element.getText().contains(text);
    }

    public void closeAlert(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
    }

    public String closeAlertReturnText(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public  boolean validateURL(String str) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.urlContains(str));
    }

    public  boolean isURLNotContains(String str) {
        pause(5);
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains(str)));
    }
}
