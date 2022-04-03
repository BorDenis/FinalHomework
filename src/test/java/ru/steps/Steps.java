package ru.steps;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ru.pages.CartPage;
import ru.pages.MainPage;
import ru.pages.ProductListPage;

import java.util.ArrayList;
import java.util.List;

public class Steps {
    private MainPage mainPage;
    private CartPage cartPage;
    private ProductListPage productListPage;
    private int sumProducts = 0;
    private int dayProductPrice;
    private String dayProductName;
    private WebDriver webDriver;
    private ArrayList<String> productNamesList = new ArrayList<>();

    public Steps(){
        mainPage = MainPage.getMainPage();
        cartPage = CartPage.getCartPage();
        productListPage = ProductListPage.getProductListPage();
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
        productNamesList.add(mainPage.getDayProductName());
        sumProducts += mainPage.getDayProductPrice();
        mainPage.dayProductButtonToCartClick();
    }

    public void checkThatProductNameInCartIsCorrect(int index){
        Assert.assertTrue(productNamesList.contains(cartPage.getInCartProductName(index)));
    }
    public void checkThatOrderCostInCartIsCorrect(){
        Assert.assertEquals(cartPage.getInCartOrderPrice(), sumProducts);
    }

    public void checkThatDayProductBlockIsVisible(){
        Assert.assertTrue(mainPage.dayProductBlockIsVisible());
    }

    public void checkCartBubbleCount(int expected){
        Assert.assertEquals(mainPage.getCartBubbleCount(),expected);
    }

    public void tabClick(String tabName){
        mainPage.tabClick(tabName);
    }

    public void titleMyCartIsVisible(){
        cartPage.titleMyCartIsVisible();
    }

    public void checkThatIsInCartOrderSubmitButtonVisible(){
        cartPage.isInCartOrderSubmitButtonVisible();
    }

    public void checkThatInCartProductCountCorrect(int expectedCount){
        Assert.assertEquals(cartPage.getInCartProductCount(),expectedCount);
    }

    public void blockMostViewedIsVisible(){
        mainPage.blockMostViewedIsVisible();
    }

    public void addProductToCardFromMostViewedBlock(int index){
        productNamesList.add(mainPage.getProductNameFromMostViewedBlock(index));
        sumProducts += mainPage.getProductPriceFromMostViewedBlock(index);
        mainPage.addProductToCardFromMostViewedBlock(index);
    }

    public void checkThatAllProductsHaveRightNameCriteria(String filterKey){
        productListPage.loadAllProductsInListing();
        int expected = productListPage.getListSize(productListPage.getElementsListProductNames()),
            actual = productListPage.getListSize(productListPage.getFilteredListProductNames(filterKey));
        Assert.assertEquals(actual, expected);

    }

    public void checkThatSortedPricesGoesFromBigToSmall(){
        List<Integer> priceList = productListPage.getListProductPrices();
        productListPage.loadAllProductsInListing();
        for (int i = 0; i< priceList.size()-1; i++){
            Assert.assertTrue(priceList.get(i) >= priceList.get(i+1));
        }
    }

    public void setSearchTextAndApply(String text){
        mainPage.setTextFroSearch(text);
        mainPage.buttonSearchClick();
    }

    public void searchFieldIsVisible(){
        mainPage.searchFieldIsVisible();
    }

    public void checkCurrentURL(String expectedURL){
        String current = WebDriverRunner.getWebDriver().getCurrentUrl();
        Assert.assertTrue(current.contains(expectedURL));
    }

    public void checkCurrentURL(String expectedURL, String searchParam){
        String current = WebDriverRunner.getWebDriver().getCurrentUrl();
        Assert.assertTrue(current.contains(expectedURL) && current.contains("?q="+searchParam));
    }

    public void sortDropdownIsVisible(){
        productListPage.sortDropdownIsVisible();
    }

    public void checkThatSortTypeIs(String text){
        Assert.assertEquals(productListPage.getTextSortDropdown(), text);
    }

    public void changeSortType(String text){
        productListPage.sortDropdownIconClick();
        productListPage.sortDropdownOptionsIsVisible();
        productListPage.selectSortType(text);
    }
}
