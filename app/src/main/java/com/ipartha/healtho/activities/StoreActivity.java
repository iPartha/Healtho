package com.ipartha.healtho.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ipartha.healtho.R;
import com.ipartha.healtho.fragments.CategoryFragment;
import com.ipartha.healtho.utils.GlideApp;
import com.ipartha.healtho.utils.Utils;

import java.util.List;
import java.util.Locale;

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
    private FusedLocationProviderClient mFusedLocationClient;

    private final int REQUEST_LOCATION_PERMISSION = 100;
    private final static String TAG = StoreActivity.class.getSimpleName();
    private ImageView mUserImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mUserImageView = findViewById(R.id.user_profile);

        mStoreTabLayout = findViewById(R.id.store_tabs);
        mStoreTabLayout.addOnTabSelectedListener(mTabSelectedListener);

        setupTabIcons();
        getIntentData();
        registerForContextMenu(mUserImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLocationPermissionGranted();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.signout) {
            Bundle bundle = getIntent().getBundleExtra("Key_Bundle");
            if (bundle != null) {
                String accountType = bundle.getString("Account_Type");
                if (!TextUtils.isEmpty(accountType)) {
                    if (accountType.equalsIgnoreCase("Google")) {
                        googleSignOut();
                    } else if (accountType.equalsIgnoreCase("Facebook")) {
                        LoginManager.getInstance().logOut();
                        finish();
                    }
                }

            }
        }
        return true;
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

    public  boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    showLocationUpdate(location);
                                }
                            }
                        });
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            // Got last known location. In some rare situations this can be null.
                                            if (location != null) {
                                                // Logic to handle location object
                                                showLocationUpdate(location);
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;

        }
    }

    private void showLocationUpdate(final Location location) {

       Thread thread = new Thread() {
           @Override
           public void run() {
               getLocationValues(location);
           }
       };

       thread.start();
    }

    private void getLocationValues(Location location) {
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address> addresses;
        String currentLocation="";
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String locality = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if (address != null) {
                    currentLocation = knownName+",";
                }
                if (subLocality != null) {
                    currentLocation += subLocality+",";
                }

                currentLocation += locality;
                final String curAddress = currentLocation;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView addressView = findViewById(R.id.delivery_address);
                        addressView.setText(curAddress);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(StoreActivity.this, "No Location Update", Toast.LENGTH_SHORT).show();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("Key_Bundle");
            if (bundle != null) {
                String accountType = bundle.getString("Account_Type");
                if (!TextUtils.isEmpty(accountType)) {
                    if (accountType.equalsIgnoreCase("Google")) {
                        Uri photoUri = bundle.getParcelable("Photo_Uri");
                        if (photoUri != null) {
                            GlideApp.with(this)
                                    .load(photoUri)
                                    .placeholder(R.drawable.user)
                                    .override(mUserImageView.getWidth(), mUserImageView.getHeight())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(mUserImageView);
                        }
                    } else if (accountType.equalsIgnoreCase("Facebook")) {
                        String userID = bundle.getString("FB_USERID");
                        if (!TextUtils.isEmpty(userID)) {
                            GlideApp.with(this)
                                    .load("https://graph.facebook.com/" + userID + "/picture?type=large")
                                    .placeholder(R.drawable.user)
                                    .override(mUserImageView.getWidth(), mUserImageView.getHeight())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(mUserImageView);
                        }
                    }
                }

            }
        }

    }

    private void googleSignOut() {
        GoogleSignInClient signInAccount = Utils.getInstance().getGoogleSignInClient();

        if (signInAccount != null) {
            signInAccount.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                        }
                    });
        }
    }

}
