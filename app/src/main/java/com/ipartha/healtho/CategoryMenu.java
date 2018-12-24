package com.ipartha.healtho;

public class CategoryMenu {
    String categoryId;
    String categoryName;
    int categoryImageURI;

    public CategoryMenu(String categoryId, String categoryName, int categoryImageURI) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImageURI = categoryImageURI;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImageURI() {
        return categoryImageURI;
    }

    public void setCategoryImageURI(int categoryImageURI) {
        this.categoryImageURI = categoryImageURI;
    }


}
