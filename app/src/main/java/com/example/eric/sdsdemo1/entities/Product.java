package com.example.eric.sdsdemo1.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Product {
    private static final String TAG = "Product";
    public static final String KEY_ID = "id";
    public final static  String LABEL_SALE = "Sale";
    public final static  String KEY_NAME = "name";
    public final static String KEY_CATEGORY = "category";
    public final static  String KEY_IMG = "img_url";
    public final static String KEY_PRICE = "price";
    public final static String KEY_DESC = "description";
    public final static String KEY_SALE = "under_sale";

    private long mId;
    private String mName;
    private String mCat;
    private String mImg;
    private double mPrice;
    private String mDesc;
    private Boolean mSale;

    /*
     * Construct a Product object from a JSON Object of the expect format
     */
    public Product(JSONObject o) throws JSONException {
        if (!o.optString(KEY_ID).isEmpty()) mId = Long.parseLong(o.optString(KEY_ID));
        mName = o.optString(KEY_NAME);
        mCat = o.optString(KEY_CATEGORY);
        mImg = o.optString(KEY_IMG);
        if (!o.optString(KEY_PRICE).isEmpty()) mPrice = Double.parseDouble(o.optString(KEY_PRICE));
        mDesc = o.optString(KEY_DESC);
        if (!o.optString(KEY_SALE).isEmpty()) mSale = Boolean.parseBoolean(o.optString(KEY_SALE));
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCat() {
        return mCat;
    }

    public void setmCat(String mCat) {
        this.mCat = mCat;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public Boolean getmSale() {
        return mSale;
    }

    public void setmSale(Boolean mSale) {
        this.mSale = mSale;
    }
}