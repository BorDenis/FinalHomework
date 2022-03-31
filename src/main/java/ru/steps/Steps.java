package ru.steps;

import org.testng.Assert;
import ru.pages.MainPage;

public class Steps {
    private MainPage mainPage;
    private int dayProductPrice;
    private String dayProductName;

    public Steps(){
        mainPage = MainPage.getMainPage();
    }

    public boolean isTabDisplayed(String tabName){
        return mainPage.isTabDisplayed(tabName);
    }

    public boolean isTabActive(String tabName){
        return mainPage.isTabActive(tabName);
    }
    public boolean isTabDisable(String tabName) {
        return mainPage.isTabDisable(tabName);
    }

    public void checkThatTabIsDisplayedAndActive(String tabName){
        Assert.assertTrue(isTabDisplayed(tabName) && isTabActive(tabName));
    }
    public void checkThatTabIsDisplayedAndDisable(String tabName){
        Assert.assertTrue(isTabDisplayed(tabName) && isTabDisable(tabName));
    }
    public void dayProductButtonToCartClick(){
        dayProductName = mainPage.getDayProductName();
        dayProductPrice = mainPage.getDayProductPrice();
        mainPage.dayProductButtonToCartClick();
    }

    public void checkThatProductNameInCartIsSame(){
        Assert.assertEquals(mainPage.getInCartProductName(),dayProductName);
    }

    public void checkThatDayProductBlockIsDisplayed(){
        Assert.assertTrue(mainPage.isDayProductBlockDisplayed());
    }

    public void checkCartBubbleCount(int expected){
        Assert.assertEquals(mainPage.getCartBubbleCount(),expected);
    }
    public void tabClick(String tabName){
        mainPage.tabClick(tabName);
    }
    public void isMyCartTitleVisible(){
        mainPage.isMyCartTitleVisible();
    }

}
