package pages;

import dto.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @FindBy(xpath = "//button[text()='Registration']")
    WebElement btnRegistrationBody;

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMassageLoginRegistration;


    public void typeLoginForm (User user){
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
        btnLoginBody.click();
//    public void typeLoginForm(String email, String password){
//        inputEmail.sendKeys(email);
//        inputPassword.sendKeys(password);
//        btnLoginBody.click();
    }

    public void typeRegistrationForm (User user){
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
        btnRegistrationBody.click();
    }

    public void closeAlert(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
    }

    public boolean isErrorMassagePresent(String message){
        return isTextInElementPresent(errorMassageLoginRegistration, message);
    }
}
