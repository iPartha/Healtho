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
import com.ipartha.healtho.sdk.ProductMenu;
import com.ipartha.healtho.utils.GlideApp;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductMenuViewHolder> {

    private List<ProductMenu> mProductMenuList;
    private Context mContext;
    private int mDefaultImageId;
    private static DecimalFormat priceFormat = new DecimalFormat();
    private static DecimalFormat quantityFormat = new DecimalFormat();

    public ProductAdapter(Context context, int imageId) {
        mContext  = context;
        mDefaultImageId = imageId;
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMinimumIntegerDigits(2);
        quantityFormat.setMaximumFractionDigits(2);
    }

    @NonNull
    @Override
    public ProductMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_view, viewGroup, false);
        return new ProductMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMenuViewHolder productMenuViewHolder, int position) {

        productMenuViewHolder.mProductName.setText(mProductMenuList.get(position).getProductName());

        productMenuViewHolder.mProductQuantity.setText(String.valueOf(
                quantityFormat.format(mProductMenuList.get(position).getProductQuantity()))
                +" "+ mProductMenuList.get(position).getProductMeasure());

        productMenuViewHolder.mProductPrice.setText("Rs. "+String.valueOf(
                priceFormat.format(mProductMenuList.get(position).getProductPrice())));

        GlideApp.with(mContext)
                .load(mProductMenuList.get(position).getImageURL())
                .error(mDefaultImageId)
                .override(productMenuViewHolder.mProductImage.getWidth(), productMenuViewHolder.mProductImage.getHeight())
                .into(productMenuViewHolder.mProductImage);
    }



    public void setProductMenuList(List<ProductMenu> categoryAdapter) {
        this.mProductMenuList = categoryAdapter;
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

        public ProductMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductImage = itemView.findViewById(R.id.product_image);
            mProductQuantity = itemView.findViewById(R.id.product_quantity);
            mProductPrice = itemView.findViewById(R.id.product_price);
        }


    }
}

