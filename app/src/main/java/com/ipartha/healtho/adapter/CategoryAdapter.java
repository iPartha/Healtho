package com.ipartha.healtho.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipartha.healtho.R;


import com.ipartha.healtho.sdk.CategoryMenu;
import com.ipartha.healtho.fragments.ProductListFragment;
import com.ipartha.healtho.utils.GlideApp;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryMenuViewHolder> {

    List<CategoryMenu> mCategoryList;
    Context mContext;

    public CategoryAdapter(Context context) {
        mContext  = context;
    }

    @NonNull
    @Override
    public CategoryMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_menu, viewGroup, false);
        return new CategoryMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMenuViewHolder categoryMenuViewHolder, int position) {

        categoryMenuViewHolder.mCategoryName.setText(mCategoryList.get(position).getCategoryName());
        GlideApp.with(mContext)
                .load(mCategoryList.get(position).getCategoryImageURI())
                .override(categoryMenuViewHolder.mImageViewW, categoryMenuViewHolder.mImageViewW)
                .centerCrop()
                .into(categoryMenuViewHolder.mCategoryImage);
        //categoryMenuViewHolder.mCategoryImage.setImageDrawable(mContext.getResources().getDrawable(mCategoryList.get(position).getCategoryImageURI()));

    }

    public void setCategoryMenuList(List<CategoryMenu> categoryAdapter) {
        this.mCategoryList = categoryAdapter;
    }

    @Override
    public int getItemCount() {
        if (mCategoryList != null) {
            return mCategoryList.size();
        } else {
            return 0;
        }
    }

    class CategoryMenuViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mCategoryName;
        ImageView mCategoryImage;
        int mImageViewW, mImageViewH;

        public CategoryMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryName = itemView.findViewById(R.id.category_name);
            mCategoryImage = itemView.findViewById(R.id.category_image);
            mImageViewW = mCategoryImage.getWidth();
            mImageViewH = mCategoryImage.getHeight();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new ProductListFragment();
            Bundle bundle = new Bundle();
            int pos = getAdapterPosition();
            bundle.putString("CATEGORY_ID", mCategoryList.get(pos).getProductURL());
            bundle.putInt("CATEGORY_IMG_ID", mCategoryList.get(pos).getCategoryImageURI());
            fragment.setArguments(bundle);
            FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}

