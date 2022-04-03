package ru.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_3 {

    private Steps steps;

    @BeforeClass
    public void bClass(){
        Configuration.pageLoadStrategy = "normal";
        Configuration.holdBrowserOpen = false;
    }

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        steps = new Steps();
        steps.checkThatDayProductBlockIsVisible();
        steps.dayProductButtonToCartClick();
        steps.checkThatTabIsDisplayedAndActive("Корзина");
        steps.tabClick("Корзина");
        steps.titleMyCartIsVisible();
        steps.checkThatProductNameInCartIsCorrect(1);
        steps.checkThatIsInCartOrderSubmitButtonVisible();
        steps.checkThatInCartProductCountCorrect(1);
        steps.checkThatOrderCostInCartIsCorrect();
    }
    @AfterClass
    public void aClass(){
        WebDriverRunner.closeWebDriver();
    }
}
