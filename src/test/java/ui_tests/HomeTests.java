package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomeTests extends ApplicationManager {

    @Test
    public void FirstTest(){
        HomePage homePage = new HomePage(getDriver())
;    }
}
