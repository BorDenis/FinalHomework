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

    //Надо имея поменять на isTabVisible
    public boolean isTabDisplayed(String tabName){
        return mainPage.isTabDisplayed(tabName);
    }

    public boolean isTabActive(String tabName){
        return mainPage.isTabActive(tabName);
    }

    public boolean isTabDisable(String tabName) {
        return mainPage.isTabDisable(tabName);
    }

    /**
     * Проверяем, что кнопка отображается и активна
     * @param tabName
     */
    public void checkThatTabIsDisplayedAndActive(String tabName){
        Assert.assertTrue(isTabDisplayed(tabName) && isTabActive(tabName));
    }

    /**
     * Проверяем, что кнопка отображается и не активна
     * @param tabName
     */
    public void checkThatTabIsDisplayedAndDisable(String tabName){
        Assert.assertTrue(isTabDisplayed(tabName) && isTabDisable(tabName));
    }

    /**
     * Сохраняем имя продукта в лист, а цену плюсуем к сумме. Только потом добавляем товар
     */
    //Возможно, стоит поменять последовательность. Будет выглядеть логичней.
    public void dayProductButtonToCartClick(){
        productNamesList.add(mainPage.getDayProductName());
        sumProducts += mainPage.getDayProductPrice();
        mainPage.dayProductButtonToCartClick();
    }

    /**
     * Берем имя продукта по индексу в списке заказа и проверем, есть ли такое в нашем списке добавленных продуктов
     * @param index
     */
    public void checkThatProductNameInCartIsCorrect(int index){
        Assert.assertTrue(productNamesList.contains(cartPage.getInCartProductName(index)));
    }

    /**
     * Проверям что итоговая сумма заказа сходится с суммой цен наших добавленных товаров
     */
    public void checkThatOrderCostInCartIsCorrect(){
        Assert.assertEquals(cartPage.getInCartOrderPrice(), sumProducts);
    }

    public void checkThatDayProductBlockIsVisible(){
        Assert.assertTrue(mainPage.dayProductBlockIsVisible());
    }

    /**
     * Сравниваем ожиданеммое количество товаров в корзине, с фактическим
     * @param expected
     */
    //нужно будет убрать параметры, а тянуть из глобальной переменной, которая будет расти с кликами добавления в корзину
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

    //так же, можно тянуть из глобальной переменной ожидаемый результат
    public void checkThatInCartProductCountCorrect(int expectedCount){
        Assert.assertEquals(cartPage.getInCartProductCount(),expectedCount);
    }

    public void blockMostViewedIsVisible(){
        mainPage.blockMostViewedIsVisible();
    }

    //можно сделать общий метод и убрать dayProductButtonToCartClick()
    public void addProductToCardFromMostViewedBlock(int index){
        productNamesList.add(mainPage.getProductNameFromMostViewedBlock(index));
        sumProducts += mainPage.getProductPriceFromMostViewedBlock(index);
        mainPage.addProductToCardFromMostViewedBlock(index);
    }

    /**
     * Сравниваем два списка по размерам. Если в отфитрованном оказалось меньше элементов, значит нам попались продукты,
     * которые не подходят по параметрам поиска
     * @param filterKey
     */
    public void checkThatAllProductsHaveRightNameCriteria(String filterKey){
        productListPage.loadAllProductsInListing();
        int expected = productListPage.getListSize(productListPage.getElementsListProductNames()),
            actual = productListPage.getListSize(productListPage.getFilteredListProductNames(filterKey));
        Assert.assertEquals(actual, expected);

    }

    /**
     * Проверям сортировку "Сначала дороже". В листе со сценами, сравниваем каждую текущую, с последующей
     */
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

    /**
     * Проверяет, входит ли ожидаемая часть адресного пути, в фактический адрес страницы
     * @param expectedURL
     */
    public void checkCurrentURL(String expectedURL){
        String current = WebDriverRunner.getWebDriver().getCurrentUrl();
        Assert.assertTrue(current.contains(expectedURL));
    }

    /**
     * Перегруженный метод, также сравнивает запрошенный текст в поисковой строке и в адресе страницы
     * Пока что, только для текста без пробелов
     * @param expectedURL
     * @param searchParam
     */
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

    /**
     *Выставляет нужный тип сортировки
     * @param text
     */
    //добавить скролинг на кнопку, в начале метода
    public void changeSortType(String text){
        productListPage.sortDropdownIconClick();
        productListPage.sortDropdownOptionsIsVisible();
        productListPage.selectSortType(text);
    }
}
