package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage_PB extends BasePage_PB {

    public LoginPage_PB(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(name = "email")
    WebElement inputEmail;

    @FindBy(name = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Login']")
    WebElement btnLoginBody;

    public void typeLoginForm(String email, String password){
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        btnLoginBody.click();
    }
}
