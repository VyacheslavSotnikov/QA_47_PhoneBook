package ui_tests;

import manager.ApplicationManager_PB;
import org.testng.annotations.Test;
import pages.HomePage_PB;
import utils.TestNGListener;

public class HomeTests_PB extends ApplicationManager_PB {

    @Test
    public void FirstTest(){
        HomePage_PB homePagePB = new HomePage_PB(getDriver())
;    }
}
