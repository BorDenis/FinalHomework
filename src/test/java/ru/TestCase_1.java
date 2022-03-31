package ru;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_1 {

    private Steps steps;

    @BeforeClass
    public void bClass(){
        Configuration.pageLoadStrategy = "eager";
    }

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
}
