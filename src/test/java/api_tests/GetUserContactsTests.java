package api_tests;

import dto.Contact;
import dto.ContactsDto;
import dto.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import manager.ContactController;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUserContactsTests extends ContactController {

    @Test
    public  void getAllUserContactsPositiveTest(){
        Response response = getAllUserContacts();
        System.out.println(response.getStatusLine());
        ContactsDto contactsDto = new ContactsDto();
        if(response.getStatusCode() == 200){
            contactsDto = response.body().as(ContactsDto.class);
        }
        System.out.println("-->" + contactsDto.getContacts().length);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getAllUserContactsUnauthorizedTest() {
        Response response = getAllUserContactsNoAuth();

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: '" + response.getBody().asString() + "'");

        Assert.assertEquals(statusCode, 403);

        Assert.assertTrue(response.getBody().asString().isEmpty(), "Response body should be empty");
    }
}
