package com.ipartha.healtho.sdk;

public class ProductCart {
    private int productCount;
    private ProductMenu productMenu;

    public ProductCart(int productCount, ProductMenu productMenu) {
        this.productCount = productCount;
        this.productMenu = productMenu;
    }

    public int getProductCount() {
        return productCount;
    }

    public ProductMenu getProductMenu() {
        return productMenu;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
