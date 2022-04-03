package ru.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_2 {

    private Steps steps;

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        steps = new Steps();
        steps.checkThatDayProductBlockIsVisible();
        steps.checkThatTabIsDisplayedAndDisable("Корзина");
        steps.dayProductButtonToCartClick();
        steps.checkThatTabIsDisplayedAndActive("Корзина");
        steps.checkCartBubbleCount(1);
    }
    @AfterClass
    public void aClass(){
        WebDriverRunner.closeWebDriver();
    }
}
