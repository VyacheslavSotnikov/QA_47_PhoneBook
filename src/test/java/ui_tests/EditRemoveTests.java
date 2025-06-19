package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager_PB;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage_PB;
import pages.LoginPage_PB;
import utils.HeaderMenuItem;

import static pages.BasePage_PB.clickButtonHeader;
import static utils.RandomUtils.*;

public class EditRemoveTests extends ApplicationManager_PB {

    HomePage_PB homePagePb;
    LoginPage_PB loginPagePb;
    ContactsPage contactsPage;

    @BeforeMethod

    public void goToContactPage(){
        UserLombok user = new UserLombok("sotiga2007@mail.ru", "Sh12345!@");

        homePagePb = new HomePage_PB(getDriver());
        loginPagePb = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPagePb.typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
        contactsPage.clickButtonHeader(HeaderMenuItem.CONTACTS);
        contactsPage.clickRandomContact();
        contactsPage.btnEditContact.click();

    }

    @Test
    public void editContact() {

        String name = "Name" + generateString(3);
        String lastName = "Last" + generateString(3);
        String phone = generatePhone(10);
        String email = generateEmail(5);
        String address = "City " + generateString(4);
//      String description = "desc " + generateString(6);

        contactsPage.editInputByPlaceholder("Name", name);
        contactsPage.editInputByPlaceholder("Last Name", lastName);
        contactsPage.editInputByPlaceholder("Phone", phone);
        contactsPage.editInputByPlaceholder("email", email);
        contactsPage.editInputByPlaceholder("Address", address);
//      contactsPage.editInputByPlaceholder("desc", description);

        contactsPage.clickSaveButtonEditForm();
        contactsPage.waitUntilContactUpdatedInDetails(name);
        contactsPage.clickContactCardByName(name);

        boolean isAllDataValid = contactsPage.validateContactWithoutDesc(name, lastName, phone, email, address);

        Assert.assertTrue(isAllDataValid, "Data not valid after edit");
    }
}

