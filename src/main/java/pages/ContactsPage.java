package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ContactsPage extends BasePage_PB {

    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[@href='/contacts']")
    WebElement btnContactsHeader;

    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    List<WebElement> contactsList;

    @FindBy(xpath = "//div[@class='contact-page_rightdiv__1NgZC']")
    List<WebElement> detailContactCard;

    @FindBy(xpath = "//button[text()='Edit']")
    public WebElement btnEditContact;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/button[text()='Save']")
    public WebElement btnEditFormSave;

    public boolean isContactsPresent() {
        return isElementPresent(btnContactsHeader);
    }

    public Integer getContactListSize() {
        System.out.println("contactsList.size()-->"+contactsList.size());
        return contactsList.size();
    }

    public Integer getContactsListSizeUseFindElement(){
        pause(2);
        List<WebElement> listContactsFindElement = driver.findElements(
                By.xpath("//div[@class='contact-item_card__2SOIM']"));
        System.out.println("contactsList.size()-->"+listContactsFindElement.size());
        return listContactsFindElement.size();
    }

    public boolean validateContactNamePhone(String name, String phone) {
        for (WebElement element: contactsList){
            //System.out.println(element.getText());
            if(element.getText().contains(name) && element.getText().contains(phone))
                return true;
        }
        return false;
    }

    public boolean validateContactAll(String name, String lastName, String phone, String email
                                            , String address, String description) {
        for (WebElement element : detailContactCard) {
            if (element.getText().contains(name) && element.getText().contains(lastName)
                    && element.getText().contains(phone) && element.getText().contains(email)
                    && element.getText().contains(address)
                    && element.getText().contains(description))
                return true;
        }
        return false;
    }

    public boolean validateContactWithoutDesc(String name, String lastName, String phone, String email
            , String address) {
        for (WebElement element : detailContactCard) {
            if (element.getText().contains(name) && element.getText().contains(lastName)
                    && element.getText().contains(phone) && element.getText().contains(email)
                    && element.getText().contains(address))
                return true;
        }
        return false;
    }

    public void clickContactCardByName(String name) {
        for (WebElement element : contactsList) {
            String contactName = element.findElement(By.tagName("h2")).getText();
            if (contactName.equals(name)) {
                element.click();
                return;
            }
        }
    }

    public String getPhoneFromList() {
        if(!contactsList.isEmpty()) {
            System.out.println(contactsList.get(0).getText().split("\n")[1]);
            return contactsList.get(0).getText();
        }
        System.out.println("contact list is empty");
        return null;
    }

    public void clickRandomContact() {
        if (!contactsList.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(contactsList.size());
            WebElement randomContact = contactsList.get(index);
            System.out.println("Clicking on contact: " + randomContact.getText());
            randomContact.click();
        } else {
            System.out.println("Contact list is empty. No contact to click.");
        }
    }

    public void editInputByPlaceholder(String placeholder, String value) {
        String xpath = String.format("//div[@class='form_form__FOqHs']//*[self::input or self::textarea][@placeholder='%s']", placeholder);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        input.clear();
        input.sendKeys(value);
    }

    public void clickSaveButtonEditRemove() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(btnEditFormSave))
                .click();
    }

    public void waitUntilContactUpdatedInDetails(String newName) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='contact-page_rightdiv__1NgZC']//h2[contains(text(),'" + newName + "')]")
                )
        );
    }
}