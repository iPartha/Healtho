package com.ipartha.healtho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.ipartha.healtho.R;
import com.ipartha.healtho.sdk.CategoryMenu;
import com.ipartha.healtho.sdk.OnItemClickListener;
import com.ipartha.healtho.sdk.ProductCart;
import com.ipartha.healtho.sdk.ProductMenu;
import com.ipartha.healtho.sdk.ProductMenuList;
import com.ipartha.healtho.utils.GlideApp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductMenuViewHolder> {

    private List<ProductMenu> mProductMenuList;
    private Context mContext;
    private int mDefaultImageId;
    private static DecimalFormat priceFormat = new DecimalFormat();
    private static DecimalFormat quantityFormat = new DecimalFormat();
    private OnItemClickListener mOnItemClickListener;
    private Map<String, ProductCart> mProductCartMap;

    public ProductAdapter(Context context, int imageId, OnItemClickListener listener) {
        mContext  = context;
        mDefaultImageId = imageId;
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMinimumIntegerDigits(2);
        quantityFormat.setMaximumFractionDigits(2);
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public ProductMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_view, viewGroup, false);
        return new ProductMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMenuViewHolder productMenuViewHolder, int position) {

        GlideApp.with(mContext)
                .load(mProductMenuList.get(position).getImageURL())
                .error(mDefaultImageId)
                .override(productMenuViewHolder.mProductImage.getWidth(), productMenuViewHolder.mProductImage.getHeight())
                .into(productMenuViewHolder.mProductImage);

        productMenuViewHolder.mProductName.setText(mProductMenuList.get(position).getProductName());

        productMenuViewHolder.mProductQuantity.setText(String.valueOf(
                quantityFormat.format(mProductMenuList.get(position).getProductQuantity()))
                +" "+ mProductMenuList.get(position).getProductMeasure());

        productMenuViewHolder.mProductPrice.setText("Rs. "+String.valueOf(
                priceFormat.format(mProductMenuList.get(position).getProductPrice())));

        int count = 0;
        if (mProductCartMap.containsKey(mProductMenuList.get(position).getProductName())) {
            ProductCart cart = mProductCartMap.get(mProductMenuList.get(position).getProductName());
            count = cart.getProductCount();
        }

        productMenuViewHolder.mProductCount.setText(String.valueOf(count));

    }



    public void setProductMenuList(List<ProductMenu> productMenuList) {
        this.mProductMenuList = productMenuList;
    }

    public List<ProductMenu> getProductMenuList() {
        return this.mProductMenuList;
    }

    public void setProductCartMap(Map<String, ProductCart> productCartMap) {
        this.mProductCartMap = productCartMap;
    }

    @Override
    public int getItemCount() {
        if (mProductMenuList != null) {
            return mProductMenuList.size();
        } else {
            return 0;
        }
    }

    class ProductMenuViewHolder extends  RecyclerView.ViewHolder {

        TextView mProductName, mProductQuantity, mProductPrice;
        CircleImageView mProductImage;
        TextView mProductAdd, mProductRemove, mProductCount;

        public ProductMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductImage = itemView.findViewById(R.id.product_image);
            mProductQuantity = itemView.findViewById(R.id.product_quantity);
            mProductPrice = itemView.findViewById(R.id.product_price);
            mProductAdd = itemView.findViewById(R.id.add_product);
            mProductRemove = itemView.findViewById(R.id.remove_product);
            mProductCount = itemView.findViewById(R.id.purchase_count);

            mProductAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), true);
                    int count = 0;
                    if (mProductCartMap.containsKey(mProductMenuList.get(getAdapterPosition()).getProductName())) {
                        ProductCart cart = mProductCartMap.get(mProductMenuList.get(getAdapterPosition()).getProductName());
                        count = cart.getProductCount();
                    }

                    mProductCount.setText(String.valueOf(count));
                }
            });

            mProductRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), false);
                    int count = 0;
                    if (mProductCartMap.containsKey(mProductMenuList.get(getAdapterPosition()).getProductName())) {
                        ProductCart cart = mProductCartMap.get(mProductMenuList.get(getAdapterPosition()).getProductName());
                        count = cart.getProductCount();
                    }

                    mProductCount.setText(String.valueOf(count));
                }

            });
        }


    }
}

