package ru.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_4 {

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        Steps steps = new Steps();
        steps.blockMostViewedIsVisible();
        steps.addProductToCardFromMostViewedBlock(1);
        steps.addProductToCardFromMostViewedBlock(2);
        steps.tabClick("Корзина");
        steps.checkThatProductNameInCartIsCorrect(1);
        steps.checkThatProductNameInCartIsCorrect(2);
        steps.checkThatOrderCostInCartIsCorrect();
    }

    @AfterClass
    public void aClass(){
        WebDriverRunner.closeWebDriver();
    }
}
