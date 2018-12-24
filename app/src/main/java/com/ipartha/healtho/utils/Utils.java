package com.ipartha.healtho.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class Utils {
    private static Utils mInstance;
    GoogleSignInClient mGoogleSignInClient;
    private Utils() {

    }
    public static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }
        return mInstance;
    }

    public void  setGoogleSignInClient(GoogleSignInClient client) {
        mGoogleSignInClient = client;
    }

    public GoogleSignInClient  getGoogleSignInClient() {
        return mGoogleSignInClient;
    }
}
