package ru.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductListPage {

    private static ProductListPage productListPage;

    private static final String XPATH_PRODUCT_CARDS_ROW = "//div[contains(@class, 'product-cards-row')]";
    private static final String XPATH_SORT_DROPDOWN = "//mvid-dropdown[@icontype = 'sort']";
    private static final String XPATH_SORT_DROPDOWN_OPTIONS = "//div[contains(@class, 'dropdown__options')]";
    private ProductListPage(){
    }

    public static ProductListPage getProductListPage(){
        if (Objects.isNull(productListPage)) productListPage = Selenide.page(new ProductListPage());
        return productListPage;
    }

    public void loadAllProductsInListing(){
        for (int i = 1; i < 7; i++){
            getProductCardsRow(i).scrollIntoView(true).shouldBe(Condition.visible);
        }
    }

    public SelenideElement getProductCardsRow(int index){
        return Selenide.$x(String.format(XPATH_PRODUCT_CARDS_ROW+"[%s]", index));
    }

    public List<SelenideElement> getElementsListProductNames(){
        return Selenide.$$x(XPATH_PRODUCT_CARDS_ROW+"//a[contains(@class, 'product-title__text')]");
    }

    private List<SelenideElement> getElementsListProductPrices(){
        return Selenide.$$x(XPATH_PRODUCT_CARDS_ROW+"//span[contains(@class, 'price__main-value')]");
    }

    public List<Integer> getListProductPrices(){
        List<Integer> priceList = new ArrayList<>();
        for (SelenideElement elem : getElementsListProductPrices()){
            priceList.add(Integer.valueOf(elem.getText().replaceAll("[^\\d]", "")));
        }
        return priceList;

//        return getElementsListProductPrices().stream()                         //
//                .collect(Collectors.toList(item -> ));                        // Еще подумаю
//        item -> Integer.parseInt(item.getText().replaceAll("[^\\d]", ""))    //

    }

    public List<SelenideElement> getFilteredListProductNames(String filterKey){
        return getElementsListProductNames().stream().filter(item -> item.getText().toLowerCase(Locale.ROOT).contains(filterKey)).toList();
    }

    public int getListSize(List<SelenideElement> list){
        return list.size();
    }

    public void sortDropdownIsVisible(){
        Selenide.$x(XPATH_SORT_DROPDOWN).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}")
                .shouldBe(Condition.visible);
    }

    public String getTextSortDropdown(){
       return Selenide.$x(XPATH_SORT_DROPDOWN + "//div[contains(@class, 'dropdown__title')]").getText();
    }

    public void sortDropdownOptionsIsVisible(){
        Selenide.$x(XPATH_SORT_DROPDOWN + XPATH_SORT_DROPDOWN_OPTIONS).shouldBe(Condition.visible);
    }

    public void sortDropdownIconClick(){
        Selenide.$x(XPATH_SORT_DROPDOWN).click();
    }

    public void selectSortType(String text){
        Selenide.$x(String.format(XPATH_SORT_DROPDOWN + XPATH_SORT_DROPDOWN_OPTIONS + "//div[text()=' %s ']", text)).click();
    }



}
