package com.example.eric.sdsdemo1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.sdsdemo1.entities.Product;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    private static final String TAG = "ProductDetailsFragment";
    private static final String ARG_PRODUCT_ID = "id";
    private final String KEY_PRODUCT = "product";

    private long mProductId;
    private View mView;
    private TextView mNameView;
    private ImageView mImgView;
    private TextView mPriceView;
    private TextView mSaleView;
    private TextView mDescView;

    private OnFragmentInteractionListener mListener;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method
     *
     * @param productId
     * @return
     */
    public static ProductDetailsFragment newInstance(long productId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getLong(ARG_PRODUCT_ID);
        }
        try {
            SdsRestClient.get("/products/"+mProductId, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i(TAG, "success! products are "+response.toString());
                    ProductDetailsFragment.this.updateProductDetails(response);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, errorResponse.toString(), throwable);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e(TAG, errorResponse.toString(), throwable);
                }
            });
        } catch (Throwable t) {
            Log.e(TAG, "problem http request", t);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_details, container, false);

        mNameView = (TextView) mView.findViewById(R.id.TxtName);
        mImgView = (ImageView) mView.findViewById(R.id.ImgProduct);
        mPriceView = (TextView) mView.findViewById(R.id.TxtPrice);
        mSaleView = (TextView) mView.findViewById(R.id.TxtSale);
        mDescView = (TextView) mView.findViewById(R.id.TxtDesc);

        return mView;
    }

    private void updateProductDetails(JSONObject response) {
        try {
            JSONObject o = (JSONObject) response.get(KEY_PRODUCT);
            Product p = new Product(o);
            if (mNameView != null) mNameView.setText(p.getmName());
            DecimalFormat df = new DecimalFormat("#.00");
            String priceStr = "S$" + df.format(p.getmPrice()/100);
            if (mPriceView != null) mPriceView.setText(priceStr);
            if (mSaleView != null && p.getmSale()) mSaleView.setText(Product.LABEL_SALE);
            if (mDescView != null) mDescView.setText(p.getmDesc());
            if (mNameView != null) mNameView.setText(p.getmName());
            if (mImgView != null && !p.getmImg().isEmpty()) {
                Picasso.with(this.getContext())
                        .load(p.getmImg())
                        .into(mImgView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
