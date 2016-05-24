package com.example.eric.sdsdemo1;

import android.util.Log;

import com.loopj.android.http.*;

public class SdsRestClient {
    private static final String BASE_URL = "http://sephora-mobile-takehome-apple.herokuapp.com/api/v1";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("SdsRestClient", "final url is "+getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}