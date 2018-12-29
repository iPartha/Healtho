package com.ipartha.healtho.sdk;

public class CategoryMenu {
    private String categoryId;
    private String categoryName;
    private int categoryImageURI;
    private String productURL;


    public CategoryMenu(String categoryId, String categoryName, int categoryImageURI, String productURL) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImageURI = categoryImageURI;
        this.productURL = productURL;
    }

    public String getCategoryId() {
        return categoryId;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryImageURI() {
        return categoryImageURI;
    }

    public String getProductURL() {
        return productURL;
    }
}
