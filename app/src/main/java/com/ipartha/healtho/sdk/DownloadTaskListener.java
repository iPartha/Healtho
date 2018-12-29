package com.ipartha.healtho.sdk;

public interface DownloadTaskListener {
    void onSuccess(Object object);
    void onFailure(String errMsg);
}
