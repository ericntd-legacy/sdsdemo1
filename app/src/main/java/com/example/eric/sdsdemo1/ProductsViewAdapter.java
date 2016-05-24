package com.example.eric.sdsdemo1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.sdsdemo1.ProductListFragment.OnProductSelectedListener;
import com.example.eric.sdsdemo1.entities.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product} and makes a call to the
 * specified {@link OnProductSelectedListener}.
 */
public class ProductsViewAdapter extends RecyclerView.Adapter<ProductsViewAdapter.ViewHolder> {
    private final String TAG = "ProductsViewAdapter";

    private final List<Product> mProducts;
    private final OnProductSelectedListener mListener;

    public ProductsViewAdapter(List<Product> items, OnProductSelectedListener listener) {
        mProducts = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder");
        holder.mItem = mProducts.get(position);
        if (holder.mItem != null) {
            Log.i(TAG, "product name is " + holder.mItem.getmName());
            if (holder.mNameView != null) holder.mNameView.setText(holder.mItem.getmName());
            DecimalFormat df = new DecimalFormat("#.00");
            String priceStr = "S$" + df.format(holder.mItem.getmPrice()/100);
            if (holder.mPriceView != null) holder.mPriceView.setText(priceStr);
            if (holder.mSaleView != null && holder.mItem.getmSale())
                holder.mSaleView.setText(Product.LABEL_SALE);
            if (holder.mImgView != null && !holder.mItem.getmImg().isEmpty()) {
                Log.i(TAG, "image width is " + holder.mImgView.getWidth());
                Picasso.with(holder.mImgView.getContext())
                        .load(holder.mItem.getmImg())
                        .into(holder.mImgView);
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG, "onClick");
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onProductSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImgView;
        public final TextView mNameView;
        public final TextView mPriceView;
        public final TextView mSaleView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImgView = (ImageView) view.findViewById(R.id.ImgProduct);
            mNameView = (TextView) view.findViewById(R.id.TxtName);
            mPriceView = (TextView) view.findViewById(R.id.TxtPrice);
            mSaleView = (TextView) view.findViewById(R.id.TxtSale);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "product name" + "'";
        }
    }
}
