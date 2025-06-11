package pages;

import dto.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddPage extends BasePage_PB{
    public AddPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputNameAddPage;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastNameAddPage;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhoneAddPage;

    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmailAddPage;

    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddressAddPage;

    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescriptionAddPage;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/button")
    WebElement btnSave;

    public void typeAddPageForm (Contact contact){
        inputNameAddPage.sendKeys(contact.getName());
        inputLastNameAddPage.sendKeys(contact.getLastname());
        inputPhoneAddPage.sendKeys(contact.getPhone());
        inputEmailAddPage.sendKeys(contact.getEmail());
        inputAddressAddPage.sendKeys(contact.getAddress());
        inputDescriptionAddPage.sendKeys(contact.getDescription());
        btnSave.click();
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
}
