package com.ipartha.healtho.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipartha.healtho.R;
import com.ipartha.healtho.fragments.CategoryFragment;

public class StoreActivity extends AppCompatActivity {

    private TabLayout mStoreTabLayout;
    private int[] mStoreTabIcons = {
            R.drawable.category,
            R.drawable.search,
            R.drawable.offers,
            R.drawable.cart
    };

    private String[] mStoreTabString = {
            "Categories",
            "Search",
            "Offers",
            "Cart"
    };

    private int mCurrentTabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mStoreTabLayout = findViewById(R.id.store_tabs);
        mStoreTabLayout.addOnTabSelectedListener(mTabSelectedListener);

        setupTabIcons();
    }

    private void setupTabIcons() {
        short index = 0;
        for (int iconId : mStoreTabIcons) {
            mStoreTabLayout.addTab(mStoreTabLayout.newTab(), false);
            TabLayout.Tab tab = mStoreTabLayout.getTabAt(index);
            if (tab != null) {
                tab.setCustomView(R.layout.custom_tab);
                View tabView = tab.getCustomView();
                if (tabView != null) {
                    ImageView imageView = tabView.findViewById(R.id.tab_imageView);
                    imageView.setImageDrawable(getResources().getDrawable(iconId));
                    TextView textView = tabView.findViewById(R.id.tab_textView);
                    textView.setText(mStoreTabString[index]);
                    index++;
                }
            }
        }

        final TabLayout.Tab tab = mStoreTabLayout.getTabAt(mCurrentTabPosition);
        if (tab != null && tab.getCustomView() != null) {
            mStoreTabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tab.getCustomView().setSelected(true);
                    tab.select();
                }
            });
        }
    }

    private final TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            View tabTextView = tab.getCustomView();
            mCurrentTabPosition = tab.getPosition();
            if (tabTextView != null) {
                TextView textView = tabTextView.findViewById(R.id.tab_textView);
                textView.setTextColor(getResources().getColor(R.color.black));
                Fragment fragment = null;
                switch (mCurrentTabPosition) {
                    case 0:
                        fragment = new CategoryFragment();
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment);
                    transaction.commit();
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            View tabTextView = tab.getCustomView();
            if (tabTextView != null) {
                TextView textView = tabTextView.findViewById(R.id.tab_textView);
                textView.setTextColor(getResources().getColor(R.color.icon_disable));
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };



}
