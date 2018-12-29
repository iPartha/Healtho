package com.ipartha.healtho.sdk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetProductListInterface {
    @GET
    Call<ProductMenuList> getProductList(@Url String url);
}
