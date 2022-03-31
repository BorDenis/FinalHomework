package ru.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

public class MainPage {

    private static MainPage mainPage;
    @FindBy (xpath = "//mvid-day-products-block")
    private SelenideElement dayProductBlock;
    @FindBy (xpath = "//mvid-day-product[contains(@class, 'main')]//button")
    private SelenideElement dayProductMainButton;
    @FindBy (xpath = "//a[contains(@class, 'c-cart-item__title')]")
    private SelenideElement inCartProductName;

    private static final String XPATH_TAB_CONTAINER = "//mvid-tap-bar//p[text() = '%s']/ancestor::mvid-header-icon";
    private static final String XPATH_CART_BUBBLE = "//div[contains(@class,'tab-cart')]//mvid-bubble";
    private static final String XPATH_DAY_PRODUCT_MAIN_PRICE = "preceding-sibling::mvid-price-2//span[@class = 'price__main-value']";
    private static final String XPATH_IN_CART_PRODUCT_NAME = "//a[contains(@class, 'c-cart-item__title')]";
    private MainPage(){
    }

    public static MainPage getMainPage(){
        if (Objects.isNull(mainPage)) mainPage = Selenide.page(new MainPage());
        return mainPage;
    }

    public boolean isTabDisplayed(String tabName){
        return getTab(tabName).isDisplayed();
    }

    public boolean isTabActive(String tabName) {
        getTab(tabName).shouldNotHave(Condition.attribute("class", "disabled"));
        return getTabStatus(getTab(tabName));
    }

    public boolean isTabDisable(String tabName){
        return !getTabStatus(getTab(tabName));
    }

    public SelenideElement getTab(String tabName){
        return Selenide.$x(String.format(XPATH_TAB_CONTAINER, tabName));
    }

    private boolean getTabStatus(SelenideElement element) {
        return !element.getAttribute("class").contains("disabled");
    }

    public void dayProductButtonToCartClick(){
        dayProductMainButton.click();
    }

    public String getDayProductName(){
        return dayProductMainButton.$x("preceding-sibling::div").getText();
    }

    public int getDayProductPrice(){
        return Integer.parseInt((dayProductMainButton.$x(XPATH_DAY_PRODUCT_MAIN_PRICE).getText()).replace(" ", ""));

    }

    public int getCartBubbleCount(){
        return Integer.parseInt(Selenide.$x(XPATH_CART_BUBBLE).getText());
    }
    public String getInCartProductName(){
        return Selenide.$x(XPATH_IN_CART_PRODUCT_NAME).getText();
    }

    public boolean isDayProductBlockDisplayed(){
        dayProductBlock.shouldBe(Condition.visible);
        return dayProductBlock.isDisplayed();
    }

    public void tabClick(String tabName){
        getTab(tabName).click();
    }
    public void isMyCartTitleVisible(){
        Selenide.$x("//span[text() = 'Моя корзина']").shouldBe(Condition.visible);
    }

}
