package com.example.eric.sdsdemo1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eric.sdsdemo1.entities.Product;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnProductSelectedListener}
 * interface.
 */
public class ProductListFragment extends Fragment {
    private static final String TAG = "ProductListFragment";
    public static final String ERR_DOWNLOAD = "Problem retrieving products from server. Please check your Internet connection and try again later";
    public static final String ERR_INTERPRET = "Problem interpreting the products received from server, please try again later or contact our support";
    private static final String KEY_PRODUCTS = "products";
    /*
    argument names
     */
    public static final String ARG_CATEGORY = "category";
    public static final String ARG_PAGE = "page";
    /*
    parameters
     */
    private String mCategory;
    private OnProductSelectedListener mListener;
    private RecyclerView mProductsView;
    private ProductsViewAdapter mProductsAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
    }

    @SuppressWarnings("unused")
    public static ProductListFragment newInstance(String category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestParams params = new RequestParams();
        if (getArguments() != null) {
            String category = getArguments().getString(ARG_CATEGORY, "");
            Log.d(TAG, "category is "+category);
            if (!category.isEmpty()) {
                mCategory = category;
                params.put(ARG_CATEGORY, category);
            }
            int page = getArguments().getInt(ARG_PAGE, 1);
            params.put(ARG_PAGE, page);
        }
        try {
            SdsRestClient.get("/products", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i(TAG, "success! products are "+response.toString());
                    ProductListFragment.this.populateProductList(response);
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

    /**
     * Populate the list with the product list retrieved
     *
     * @param response
     */
    private void populateProductList(JSONObject response) {
        // Log.i(TAG, "populateProductList");
        final List<Product> products = extractProducts(response);
        Log.i(TAG, "number of products "+ products.size());
        // Set the adapter
        if (this.getView() instanceof RecyclerView) {
            Context context = this.getView().getContext();
            mProductsView = (RecyclerView) this.getView();

            mProductsView.setLayoutManager(new GridLayoutManager(context, 2));
            mProductsAdapter = new ProductsViewAdapter(products, mListener);
            mProductsView.setAdapter(mProductsAdapter);
        }
    }
    /**
     * Get a list of Product objects from the JSON data retrieved
     *
     * @param response
     * @return
     */
    private List<Product> extractProducts(JSONObject response) {
        // Log.i(TAG, "extractProducts");
        List<Product> productList = new ArrayList<>();
        JSONObject o;
        Product p;
        try {

            JSONArray products = (JSONArray) response.get(KEY_PRODUCTS);
            for (int i = 0; i < products.length(); i++) {
                o = products.getJSONObject(i);
                p = new Product(o);
                productList.add(p);
            }

        } catch (JSONException e) {
            Log.e(TAG, "unexpected format received", e);
//            DialogUtils.displayError(this, ERR_INTERPRET);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Product attribute unexpected attributes", e);
        }
        return productList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductSelectedListener) {
            mListener = (OnProductSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProductSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnProductSelectedListener {
        // TODO: Update argument type and name
        void onProductSelected(Product item);
    }
}
