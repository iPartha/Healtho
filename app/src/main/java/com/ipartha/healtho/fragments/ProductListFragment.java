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
import com.ipartha.healtho.sdk.OnItemClickListener;
import com.ipartha.healtho.sdk.ProductCart;
import com.ipartha.healtho.sdk.ProductMenu;
import com.ipartha.healtho.sdk.ProductMenuList;
import com.ipartha.healtho.sdk.DownloadTask;

import java.util.HashMap;
import java.util.Map;

public class ProductListFragment extends Fragment implements DownloadTaskListener, OnItemClickListener {

    private Context mContext;
    private TextView mProductTitleView;
    private TextView mProductCountView;
    private RecyclerView mProductListView;
    private ProductAdapter mProductAdapter;
    private Map<String, ProductCart> mProductCartMap = new HashMap<>();

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
        Bundle bundle = getArguments();
        String url = null;
        int imageId = 0;
        if (bundle != null) {
            url = bundle.getString("CATEGORY_ID");
            imageId = bundle.getInt("CATEGORY_IMG_ID");
        }
        mProductTitleView = view.findViewById(R.id.category_title);
        mProductCountView = view.findViewById(R.id.product_count);
        mProductListView = view.findViewById(R.id.product_list_recycler_view);

        mProductAdapter = new ProductAdapter(mContext, imageId, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mProductListView.setLayoutManager(layoutManager);
        mProductListView.setItemAnimator(new DefaultItemAnimator());
        mProductListView.setAdapter(mProductAdapter);

        CategoryItemDecoration decoration = new CategoryItemDecoration(1, 20, false);
        mProductListView.addItemDecoration(decoration);

       if (url != null){
            DownloadTask downloadTask = new DownloadTask(url, this);
            downloadTask.execute();
        }
    }


    @Override
    public void onSuccess(Object object) {
        ProductMenuList productMenuList = (ProductMenuList)object;
        mProductAdapter.setProductCartMap(mProductCartMap);
        mProductAdapter.setProductMenuList(productMenuList.getProductMenuList());
        mProductTitleView.setText(productMenuList.getProductTitle());
        mProductCountView.setText(productMenuList.getProductMenuList().size()+ " products");
    }

    @Override
    public void onFailure(String errMsg) {

    }

    @Override
    public void onItemClick(int position, boolean isAdd) {

        int count = 0;
        ProductCart cart;
        ProductMenu productMenu = mProductAdapter.getProductMenuList().get(position);

        if (isAdd) {
            if (!mProductCartMap.containsKey(productMenu.getProductName())) {
                cart = new ProductCart(0, productMenu);
                mProductCartMap.put(productMenu.getProductName(), cart);
            }
            cart = mProductCartMap.get(productMenu.getProductName());
            if (cart != null) {
                count = cart.getProductCount() + 1;
                cart.setProductCount(count);
            }
        } else {

            if (mProductCartMap.containsKey(productMenu.getProductName())) {
                cart = mProductCartMap.get(productMenu.getProductName());
                if (cart != null) {
                    count = cart.getProductCount() - 1;
                    cart.setProductCount(count);
                    if (count == 0) {
                        mProductCartMap.remove(productMenu.getProductName());
                    }
                }
            }

        }

    }
}
