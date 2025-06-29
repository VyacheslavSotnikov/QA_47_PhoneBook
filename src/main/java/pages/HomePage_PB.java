package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static utils.PropertiesReader.getProperty;


public class HomePage_PB extends BasePage_PB {

    public HomePage_PB(WebDriver driver){
        setDriver(driver);
//        driver.get("https://telranedu.web.app/home");
        driver.get(getProperty("login.properties", "url"));

        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='LOGIN']")
    WebElement btnLoginHeader;

    public void clickBtnLoginHeader(){
        btnLoginHeader.click();
    }
}
