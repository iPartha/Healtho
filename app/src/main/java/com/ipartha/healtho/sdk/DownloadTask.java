package com.ipartha.healtho.sdk;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class DownloadTask extends AsyncTask<Void, Void, Message> {


    private final static int API_SUCCESS = 1;
    private final static int API_FAILURE = 0;

    private DownloadTaskListener mDownloadTaskListener;
    private String mURL;


    public DownloadTask(String requestURL, DownloadTaskListener listener) {
        this.mURL = requestURL;
        this.mDownloadTaskListener = listener;
    }
    @Override
    protected Message doInBackground(Void... params) {

        Message message = Message.obtain();

        GetProductListInterface mProductList = RetrofitAPIClient.getClient().create(GetProductListInterface.class);

        Call<ProductMenuList> call = mProductList.getProductList(mURL);
        try {
            retrofit2.Response<ProductMenuList> response = call.execute();

            if (response != null) {
                if (response.isSuccessful()) {
                    message.what = API_SUCCESS;
                    message.obj = response.body();

                } else if (response.errorBody() != null) {
                    message.what = API_FAILURE;
                    message.obj = response.errorBody();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    protected void onPostExecute(Message message) {
        if (message != null && message.obj != null) {
            if (message.what == API_SUCCESS) {
                mDownloadTaskListener.onSuccess(message.obj);
            } else {
                mDownloadTaskListener.onFailure(message.obj.toString());
            }
        } else {
            mDownloadTaskListener.onFailure("Download Failure");
        }
    }
}
