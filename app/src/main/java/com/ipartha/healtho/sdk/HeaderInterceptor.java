package com.ipartha.healtho.sdk;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request r = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                // authorization token here
                .addHeader("secret-key", "$2a$10$8p3c28dGs8JfuN86.WuP1OjeNnxRxfXeyXolOig4rnE9tRLBPNUyu")
                .build();

        return chain.proceed(r);
    }
}
