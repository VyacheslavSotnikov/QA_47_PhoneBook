package pages;

import dto.UserLombok;
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

    @FindBy(className = "contact-page_message__2qafk")
    WebElement messageNoContacts;

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOutHeader;

    @FindBy(xpath = "//div[text()='Login Failed with code 401']")
    WebElement messageErrorLogIn401;

    public void logOut(){
        btnSignOutHeader.click();
    }

    public void typeLoginForm (UserLombok userLombok){
        inputEmail.sendKeys(userLombok.getUsername());
        inputPassword.sendKeys(userLombok.getPassword());
        btnLoginBody.click();
//    public void typeLoginForm(String email, String password){
//        inputEmail.sendKeys(email);
//        inputPassword.sendKeys(password);
//        btnLoginBody.click();
    }

    public void typeRegistrationForm (UserLombok userLombok){
        inputEmail.sendKeys(userLombok.getUsername());
        inputPassword.sendKeys(userLombok.getPassword());
        btnRegistrationBody.click();
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

    public boolean isNoContactMessagePresent(String message){
        return isTextInElementPresent(messageNoContacts, message);
    }

    public boolean isErrorMessageLogIn401(){
        return isElementPresent(messageErrorLogIn401);
    }
}
