package ru.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.steps.Steps;

public class TestCase_5 {

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        Steps steps = new Steps();
        String searchText = "apple";
        steps.searchFieldIsVisible();
        steps.setSearchTextAndApply(searchText);
        steps.checkCurrentURL("/product-list-page", searchText);
        steps.checkThatAllProductsHaveRightNameCriteria(searchText);
    }

    @AfterClass
    public void aClass(){
        WebDriverRunner.closeWebDriver();
    }

}
