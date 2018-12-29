package com.ipartha.healtho.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipartha.healtho.R;
import com.ipartha.healtho.adapter.CategoryAdapter;
import com.ipartha.healtho.adapter.ProductAdapter;
import com.ipartha.healtho.sdk.DownloadTaskListener;
import com.ipartha.healtho.sdk.ProductMenuList;
import com.ipartha.healtho.sdk.DownloadTask;

public class ProductListFragment extends Fragment implements DownloadTaskListener {

    private Context mContext;
    private TextView mProductTitleView;
    private TextView mProductCountView;
    private RecyclerView mProductListView;
    private ProductAdapter mProductAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initViews(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    private void initViews(View view) {
        Bundle bundle =getArguments();
        mProductTitleView = view.findViewById(R.id.category_title);
        mProductCountView = view.findViewById(R.id.product_count);
        mProductListView = view.findViewById(R.id.product_list_recycler_view);

        mProductAdapter = new ProductAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mProductListView.setLayoutManager(layoutManager);
        mProductListView.setItemAnimator(new DefaultItemAnimator());
        mProductListView.setAdapter(mProductAdapter);

        CategoryItemDecoration decoration = new CategoryItemDecoration(1, 20, false);
        mProductListView.addItemDecoration(decoration);

        if (bundle != null) {
            String url = bundle.getString("CATEGORY_ID");
            DownloadTask downloadTask = new DownloadTask(url, this);
            downloadTask.execute();
        }
    }


    @Override
    public void onSuccess(Object object) {
        ProductMenuList productMenuList = (ProductMenuList)object;
        mProductAdapter.setProductMenuList(productMenuList.getProductMenuList());
        mProductTitleView.setText(productMenuList.getProductTitle());
        mProductCountView.setText(productMenuList.getProductMenuList().size()+ " products");
    }

    @Override
    public void onFailure(String errMsg) {

    }
}
