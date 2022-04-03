package ru.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

public class MainPage {

    private static MainPage mainPage;
    private int cartBubbleCount = 0;

    @FindBy (xpath = "//mvid-day-products-block")
    private SelenideElement dayProductBlock;
    @FindBy (xpath = "//mvid-day-product[contains(@class, 'main')]//button")
    private SelenideElement dayProductMainButton;
    @FindBy (xpath = "//a[contains(@class, 'c-cart-item__title')]")
    private SelenideElement inCartProductName;

    private static final String XPATH_TAB_CONTAINER = "//mvid-tap-bar//p[text() = '%s']/ancestor::mvid-header-icon";
    private static final String XPATH_CART_BUBBLE = "//div[contains(@class,'tab-cart')]//mvid-bubble";
    private static final String XPATH_DAY_PRODUCT_MAIN_PRICE = "preceding-sibling::mvid-price-2//span[@class = 'price__main-value']";
    private static final String XPATH_MOST_VIEWED_PRODUCTS_BLOCK = "//mvid-simple-product-collection-mp[.//h2[text()='Самые просматриваемые']]";
    private static final String XPATH_MOST_VIEWED_PRODUCT_NAME = "/.//div[contains(@class, 'product-mini-card__name')][%s]";

    private MainPage(){
    }

    public static MainPage getMainPage(){
        if (Objects.isNull(mainPage)) mainPage = Selenide.page(new MainPage());
        return mainPage;
    }

    public boolean isTabDisplayed(String tabName){
        return getTab(tabName).isDisplayed();
    }

    /**
     * Проверят, что элемент активен
     * @param tabName
     * @return
     */
    public boolean isTabActive(String tabName) {
        getTab(tabName).shouldNotHave(Condition.attribute("class", "disabled"));
        return getTabStatus(getTab(tabName));
    }

    /**
     * Проверяет, что элемент не активен
     * @param tabName
     * @return
     */
    public boolean isTabDisable(String tabName){
        return !getTabStatus(getTab(tabName));
    }

    public SelenideElement getTab(String tabName){
        return Selenide.$x(String.format(XPATH_TAB_CONTAINER, tabName));
    }

    /**
     * Возвращает статус элемента
     * @param element
     * @return
     */
    private boolean getTabStatus(SelenideElement element) {
        return !element.getAttribute("class").contains("disabled");
    }

    /**
     * Добавить товар дня в розину
     */
    public void dayProductButtonToCartClick(){
        dayProductMainButton.scrollIntoView(false).click();
    }

    public String getDayProductName(){
        return dayProductMainButton.$x("preceding-sibling::div").getText();
    }

    public int getDayProductPrice(){
        String price = dayProductMainButton.$x(XPATH_DAY_PRODUCT_MAIN_PRICE).getText();
        return Integer.parseInt(price.replaceAll("[^\\d]", ""));
    }

    /**
     * Получить количество добавленных товаров из бабла корзины
     * @return
     */
    public int getCartBubbleCount(){
        return Integer.parseInt(Selenide.$x(XPATH_CART_BUBBLE).getText());
    }


    public boolean dayProductBlockIsVisible(){
        dayProductBlock.shouldBe(Condition.visible);
        return dayProductBlock.isDisplayed();
    }

    //Периодически, при открытии в новом браузере, блок с самыми популярными товарами может быть не самым верхним, а
    //находиться в самом низу. Так как я не нашел за что можно зацепиться у не загруженных блоков, решил заскролиться на оба
    public void blockMostViewedIsVisible(){
        Selenide.$x("//mvid-simple-product-collection-mp").scrollIntoView(true);
        Selenide.$x("//mvid-simple-product-collection-mp[2]").scrollIntoView(true);
        Selenide.$x(XPATH_MOST_VIEWED_PRODUCTS_BLOCK).shouldBe(Condition.visible);
    }

    /**
     * Получить продукт из блока "Самые популярные" по порядковому номеру
     * @param index
     * @return
     */
    public SelenideElement getProductFromMostViewedBlock(int index){
        return Selenide.$x(String.format(XPATH_MOST_VIEWED_PRODUCTS_BLOCK + XPATH_MOST_VIEWED_PRODUCT_NAME, index));
    }

    /**
     * Добавляем товар в корзину и ждем, пока в бабле не обновится каунтер
     * @param index
     */
    public void addProductToCardFromMostViewedBlock(int index){
        getProductFromMostViewedBlock(index).$x("following::button").scrollIntoView(false).shouldBe(Condition.visible).click();
        cartBubbleCount += 1;
        Selenide.$x(XPATH_CART_BUBBLE).shouldHave(Condition.text(String.valueOf(cartBubbleCount)));
    }

    public String getProductNameFromMostViewedBlock(int index){
        return getProductFromMostViewedBlock(index).getText();
    }

    public int getProductPriceFromMostViewedBlock(int index){
        String price = getProductFromMostViewedBlock(index).$x("following::span[contains(@class, 'price__main-value')]").getText();
        return Integer.parseInt(price.replaceAll("[^\\d]", "")); //Берем из текста, только цифры
    }

    public void tabClick(String tabName){
        getTab(tabName).scrollIntoView(true).click();
    }

    public void searchFieldIsVisible(){
        Selenide.$x("//div[contains(@class, 'search-container')]").shouldBe(Condition.visible);
    }

    /**
     * После нажатия на поиск, ждем отображения строки с количествиенным резульатом поиска
     */
    public void buttonSearchClick(){
        Selenide.$x("//mvid-icon[contains(@class, 'search-icon')]").click();
        Selenide.$x("//p[contains(@class, 'srp-title')]").shouldBe(Condition.visible);
    }

    public void setTextFroSearch(String text){
        Selenide.$x("//input[contains(@class, 'input__field')]").setValue(text);
    }


}
