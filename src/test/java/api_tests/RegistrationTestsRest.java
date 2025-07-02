package api_tests;

import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseAPI;

import static utils.RandomUtils.*;

public class RegistrationTestsRest extends AuthenticationController implements BaseAPI {

    @Test
    public void registrationPositiveTest_200(){
        User user = new User(generateEmail(10), "Password123!");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void registrationPositiveTest_getBody(){
        User user = new User(generateEmail(10), "Password123!");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.body().print());
    }

    @Test
    public void registrationNegativeTest_wrongEmail_400(){
        User user = new User(generateString(10), "Password123!");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void LoginPositiveTest_200(){
        User user = new User("sotiga2015@gmail.com", "Sh12345!@");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void LoginNegativeTest_wrongEmail_401(){
        User user = new User("sotiga2015gmail.com", "Sh12345!@");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    @Test
    public void LoginNegativeTest_wrongPassword_401(){
        User user = new User("sotiga2015@gmail.com", "Sh12345!!");
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 401);
    }
}
