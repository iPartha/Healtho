package com.ipartha.healtho.sdk;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuList {

    @SerializedName("productTitle")
    String productTitle;

    @SerializedName("productList")
    private List<ProductMenu> productMenuList = new ArrayList<>();

    public String getProductTitle() {
        return productTitle;
    }

    public List<ProductMenu> getProductMenuList() {
        return productMenuList;
    }
}
