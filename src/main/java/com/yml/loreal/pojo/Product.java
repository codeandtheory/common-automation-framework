package com.yml.loreal.pojo;

import org.openqa.selenium.WebElement;

public class Product implements Comparable {

    private WebElement productName;
    private WebElement productCategoryName;
    private WebElement productBrandName;
    private WebElement productPromoText;
    private WebElement productPrice;
    private WebElement productRating;
    private WebElement productReviewCount;
    private WebElement productPricePrefix;
    private WebElement productStrikeThroughPrice;


    public WebElement getProductPricePrefix() {
        return productPricePrefix;
    }

    public void setProductPricePrefix(WebElement productPricePrefix) {
        this.productPricePrefix = productPricePrefix;
    }

    public WebElement getProductStrikeThroughPrice() {
        return productStrikeThroughPrice;
    }

    public void setProductStrikeThroughPrice(WebElement productStrikeThroughPrice) {
        this.productStrikeThroughPrice = productStrikeThroughPrice;
    }

    public WebElement getProductName() {
        return productName;
    }

    public void setProductName(WebElement productName) {
        this.productName = productName;
    }

    public WebElement getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(WebElement productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public WebElement getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(WebElement productBrandName) {
        this.productBrandName = productBrandName;
    }

    public WebElement getProductPromoText() {
        return productPromoText;
    }

    public void setProductPromoText(WebElement productPromoText) {
        this.productPromoText = productPromoText;
    }

    public WebElement getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(WebElement productPrice) {
        this.productPrice = productPrice;
    }

    public WebElement getProductRating() {
        return productRating;
    }

    public void setProductRating(WebElement productRating) {
        this.productRating = productRating;
    }

    public WebElement getProductReviewCount() {
        return productReviewCount;
    }

    public void setProductReviewCount(WebElement productReviewCount) {
        this.productReviewCount = productReviewCount;
    }

    public String toWebElement(){
        return "Product Name - "+this.productName.getText()+ "\nBrand Name-"+this.productBrandName.getText()+"\nProduct Price-"+productPrice.getText();
    }


    @Override
    public int compareTo(Object object) {
        Product product=(Product)object;
        if (this.equals(product))
            return 0;
        else if (this.getProductName().getText().length()>product.getProductName().getText().length())
            return 1;
        else
            return -1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Product product = (Product) object;
        return this.getProductName().getText().equalsIgnoreCase( product.getProductName().getText());
    }
}
