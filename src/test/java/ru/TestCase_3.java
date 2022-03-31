package ru;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_3 {

    private Steps steps;

    @BeforeClass
    public void bClass(){
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        steps = new Steps();
        steps.checkThatDayProductBlockIsDisplayed();
        steps.dayProductButtonToCartClick();
        steps.checkThatTabIsDisplayedAndActive("Корзина");
        steps.tabClick("Корзина");
        steps.isMyCartTitleVisible();
        steps.checkThatProductNameInCartIsSame();
    }
}
