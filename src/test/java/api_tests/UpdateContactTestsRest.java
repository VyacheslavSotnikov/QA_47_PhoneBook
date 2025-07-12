package api_tests;

import dto.*;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;
import static org.hamcrest.Matchers.*;

public class UpdateContactTestsRest extends ContactController {

    Contact contact;

    @BeforeClass(alwaysRun = true)
    public void createContact(){
        contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        if(response.getStatusCode() != 200){
            System.out.println("Contact doesn't created");
        } else
            responseMessageDto = response.body().as(ResponseMessageDto.class);
        //Contact was added! ID: 1ebfbba2-b59f-45c9-8540-d2b0c07f1649)
        contact.setId(responseMessageDto.getMessage().split("ID: ")[1]);
    }

    @Test(groups = "smoke")
    public void  updateContactPositiveTest(){
        System.out.println(contact.toString());
        contact.setName("New_name");
        Response response = updateContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        response
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
                ;
        ResponseMessageDto responseMessageDto = response.body().as(ResponseMessageDto.class);
        Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was updated"));
    }

    @Test
    public void updateContactInvalidEmailTest() {
        contact.setEmail("invalid_email"); // без @
        Response response = updateContactRequest(contact, tokenDto);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
    }

    @Test
    public void updateContactEmptyNameTest() {
        contact.setName("");
        Response response = updateContactRequest(contact, tokenDto);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
    }

    @Test
    public void updateContactInvalidPhoneTest() {
        contact.setPhone("12345abc"); // невалидный номер
        Response response = updateContactRequest(contact, tokenDto);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
    }

    @Test
    public void updateContactMissingIdTest() {
        contact.setId(null);
        Response response = updateContactRequest(contact, tokenDto);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
    }

    @Test
    public void updateContactMissingRequiredFieldsTest() {
        Contact invalidContact = Contact.builder()
                .id(contact.getId())
                .build(); // только id
        Response response = updateContactRequest(invalidContact, tokenDto);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));
    }

    @Test
    public void updateContactUnauthorizedTest() {
        contact.setName("Unauthorized update");

        Response response = updateContactRequestNoAuth(contact);

        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Body length: " + response.getBody().asString().length());

        response
                .then()
                .log().all()
                .statusCode(anyOf(equalTo(401), equalTo(403)));

        String bodyAsString = response.getBody().asString();
        if (!bodyAsString.isEmpty()) {
            response.then().body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));

            ErrorMessageDto error = response.body().as(ErrorMessageDto.class);
            Assert.assertTrue(error.getStatus() == 401 || error.getStatus() == 403);
            Assert.assertTrue(error.getError().toLowerCase().contains("unauthorized")
                    || error.getError().toLowerCase().contains("forbidden"));
        } else {
            System.out.println("Warning: No response body received for 401/403 status");
        }
    }

    @Test
    public void updateContactNotFoundTest() {
        contact.setId("00000000-0000-0000-0000-000000000000");
        Response response = updateContactRequest(contact, tokenDto);

        response
                .then()
                .log().all()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"));

        ErrorMessageDto error = response.body().as(ErrorMessageDto.class);
        Assert.assertEquals(error.getStatus(), 400);

        Object messageObj = error.getMessage();
        String messageStr = (messageObj == null) ? "" : messageObj.toString();

        String expectedMessage = "Contact with id: 00000000-0000-0000-0000-000000000000 not found in your contacts!";
        Assert.assertTrue(messageStr.contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage + ", but was: " + messageStr);
    }
}
