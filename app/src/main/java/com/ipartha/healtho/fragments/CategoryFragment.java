package com.ipartha.healtho.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ipartha.healtho.sdk.CategoryMenu;
import com.ipartha.healtho.R;
import com.ipartha.healtho.adapter.CategoryAdapter;
import com.ipartha.healtho.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryFragment extends Fragment {

    private CategoryAdapter mCategoryAdapter;
    private Context mContext;
    private RecyclerView mCategoryRecyclerView;
    private ImageView mBannerImageView;
    private Timer mBannerSlideTimer;
    private int mBannerImgIdx = 0;
    private final int[] mBannerImageIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initViews(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onStart() {
        super.onStart();
        mBannerSlideTimer = new Timer();
        mCategoryAdapter = new CategoryAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mCategoryRecyclerView.setLayoutManager(layoutManager);
        mCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);

        CategoryItemDecoration decoration = new CategoryItemDecoration(2, 12, false);
        mCategoryRecyclerView.addItemDecoration(decoration);

        List<CategoryMenu> categoryMenus = new ArrayList<>();

        categoryMenus.add(new CategoryMenu("100", "Fruits & Vegetables", R.drawable.fruits,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("101", "Dairy & Eggs", R.drawable.dairy,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("102", "Personal Care", R.drawable.personal_care,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("103", "Beverages", R.drawable.beverages,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("104", "Food grains", R.drawable.foodgrain,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("105", "Masala & Spices", R.drawable.masala,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("106", "Cleaning & Household", R.drawable.cleaning,
                "/b/5c26d79f8c05c52ebad06649"));
        categoryMenus.add(new CategoryMenu("107", "Snacks", R.drawable.cake,
                "/b/5c26d79f8c05c52ebad06649"));

        mCategoryAdapter.setCategoryMenuList(categoryMenus);

        mCategoryAdapter.notifyDataSetChanged();

        mBannerSlideTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mBannerUpdateHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 7000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerSlideTimer.cancel();
        mBannerUpdateHandler.removeMessages(1);
    }

    private void initViews(View view) {
        mCategoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        mBannerImageView = view.findViewById(R.id.banner_image);
        GlideApp.with(mContext)
                .load(R.drawable.banner1)
                .override(mBannerImageView.getWidth(), mBannerImageView.getHeight())
                .centerCrop()
                .into(mBannerImageView);

    }

    public Handler mBannerUpdateHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            slidBannerView();
        }
    };

    private void slidBannerView() {

        if (mContext != null) {
            mBannerImgIdx++;
            if (mBannerImgIdx >= 3) {
                mBannerImgIdx = 0;
            }
            GlideApp.with(mContext)
                    .load(mBannerImageIds[mBannerImgIdx])
                    .override(mBannerImageView.getWidth(), mBannerImageView.getHeight())
                    .centerCrop()
                    .into(mBannerImageView);
        }

    }


}
