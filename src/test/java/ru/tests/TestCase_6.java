package ru.tests;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import ru.steps.Steps;

import java.lang.ref.SoftReference;

public class TestCase_6 {

    @Test
    public void test(){
        Selenide.open("https://www.mvideo.ru/");
        Steps steps = new Steps();
        String searchText = "apple";
        steps.searchFieldIsVisible();
        steps.setSearchTextAndApply(searchText);
        steps.checkCurrentURL("/product-list-page", searchText);
        steps.checkThatAllProductsHaveRightNameCriteria(searchText);
        steps.sortDropdownIsVisible();
        steps.checkThatSortTypeIs("Сначала популярные");
        steps.changeSortType("Сначала дороже");
        steps.checkThatAllProductsHaveRightNameCriteria(searchText);
        steps.checkThatSortedPricesGoesFromBigToSmall();
        System.out.println();
    }
}
