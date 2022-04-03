package ru.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_1 {

    private Steps steps;

//    @BeforeSuite
//    static void setupAllureReports() {
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
//
//        // либо для тонкой настройки:
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
//                .screenshots(false)
//                .savePageSource(true)
//        );
//    }


    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        steps = new Steps();
        steps.checkThatTabIsDisplayedAndActive("Статус заказа");
        steps.checkThatTabIsDisplayedAndActive("Войти");
        steps.checkThatTabIsDisplayedAndDisable("Сравнение");
        steps.checkThatTabIsDisplayedAndDisable("Избранное");
        steps.checkThatTabIsDisplayedAndDisable("Корзина");
    }
    @AfterClass
    public void aClass(){
        WebDriverRunner.closeWebDriver();
    }
}
