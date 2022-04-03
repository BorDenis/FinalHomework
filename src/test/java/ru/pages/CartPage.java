package ru.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;


import java.util.Objects;

public class CartPage {

    private static CartPage cartPage;
    private static final String XPATH_CART_ORDER_PRODUCTS = "//div[@class = 'c-cart__order'][%s]";
    private static final String XPATH_IN_CART_PRODUCT_NAME = ".//a[contains(@class, 'c-cart-item__title')]";
    private static final String XPATH_ORDER_LIST_CONTENT ="//div[@class='c-orders-list__content'][div[@class = 'u-mb-20']]";


    private CartPage(){
    }

    public static CartPage getCartPage(){
        if (Objects.isNull(cartPage)) cartPage = Selenide.page(new CartPage());
        return cartPage;
    }

    /**
     * Получить карточку товара по индексу из списка заказа
     * @param index
     * @return
     */
    public SelenideElement getCartOrderProductCard(int index){
        return Selenide.$x(String.format(XPATH_CART_ORDER_PRODUCTS, index));
    }

    public void titleMyCartIsVisible(){
        Selenide.$x("//span[text() = 'Моя корзина']").shouldBe(Condition.visible);
    }

    /**
     * Получить сумму всего заказа
     * @return
     */
    public int getInCartOrderPrice(){
        String price = Selenide.$x(XPATH_ORDER_LIST_CONTENT).$x(".//span[@class = 'c-cost-line__text']").getText();
        return Integer.parseInt(price.replaceAll("[^\\d]", ""));
    }

    public String getInCartProductName(int index){
        return getCartOrderProductCard(index).$x(XPATH_IN_CART_PRODUCT_NAME).getText();
    }

    public void isInCartOrderSubmitButtonVisible (){
        Selenide.$x(XPATH_ORDER_LIST_CONTENT).$x(".//input[contains(@class, 'c-btn')]").shouldBe(Condition.visible);
    }

    public int getInCartProductCount(){
        String count = Selenide.$x(XPATH_ORDER_LIST_CONTENT).$x(".//span[contains(@class, 'c-cost-lin')]").getText();
        return Integer.parseInt(count.replaceAll("[^\\d]", ""));
    }
}
