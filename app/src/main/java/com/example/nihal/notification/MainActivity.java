package com.example.nihal.notification;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nihal.notification.Startup.ambulanceLogin_Activity;
import com.example.nihal.notification.Startup.userLogin_Activity;
import com.example.nihal.notification.model.Image;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar Toolbar;
    private ActionBar actionBar;
    private CoordinatorLayout mContainer;
    private ViewPager viewPager;
    LinearLayout layoutDots;
    public AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    private static int[] array_image_place = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
    };

    private static String[] array_brief_place = {
            "Foggy Hill",
            "The Backpacker",
            "River Forest"
    };


    //  private BroadcastReceiver broadcastReceiver;
    private static final String TAG = "MainActivity";
    private static final int Error_Dialog = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavigationMenu();
        initComponent();

        mContainer = findViewById(R.id.parent_view);

       /* final String TOKEN_Val=SharedPrefManager.getInstance(MainActivity.this).getDeviceToken();
         //   Log.d("TTTOOOKKEEEENNNN_value:",TOKEN_Val);

       if (SharedPrefManager.getInstance(this).getDeviceToken() !=null){
            textView.setText(TOKEN_Val);
            Log.d("FCM_TOKEN:" ,SharedPrefManager.getInstance(this).getDeviceToken());

       }*/


       /* registerReceiver(broadcastReceiver, new IntentFilter(FireBaseMessaging.TOKEN_BROADCAST));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (isServiceOK()) {
            init();
            getLocation();
        }*/

    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FireBaseMessaging.TOKEN_BROADCAST)) {
                Log.d("TTTOOOKKKKEENNNN_value:", SharedPrefManager.getInstance(MainActivity.this).getDeviceToken());
                Toast.makeText(context, SharedPrefManager.getInstance(MainActivity.this).getDeviceToken(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "There is no any message", Toast.LENGTH_LONG);
            }

        }
    };

    public void init() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }


    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOK: checking gooogle services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServiceOK: Google Play serivces is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, Error_Dialog);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //uploading section
    LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();

                String latitude = String.valueOf(latti);
                float lats = Float.valueOf(latitude);
                double longi = location.getLongitude();
                String longiutde = String.valueOf(longi);
                float longis = Float.valueOf(longiutde);
                BackgroundTask backgroundTask = new BackgroundTask(this);
                String type = "login";
                backgroundTask.execute(type, latitude, longiutde);
                //TokenBackgroundTask tokenBackgroundTask=new TokenBackgroundTask(this);
                //String token=SharedPrefManager.getInstance(this).getDeviceToken();
                //String type1="login1";
                //tokenBackgroundTask.execute(type1,token,"");
                store_Ambu_latitude(lats);
                store_Ambu_longitude(longis);

            } else {
            }


        }
    }

    private void store_Ambu_latitude(float latitude) {
        SharedPrefManager.getInstance(getApplicationContext()).save_Ambu_Latitude(latitude);
    }

    private void store_Ambu_longitude(float longitude) {
        SharedPrefManager.getInstance(getApplicationContext()).save_Ambu_Longitude(longitude);
    }


    //For Kathfest

    private void initToolbar() {

        Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("HOME");

    }

    private void initNavigationMenu() {
        DrawerLayout drawerLayout = findViewById(R.id.Drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                Toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.Home:
            case R.id.settings:
            case R.id.account:
            case R.id.logout:
            case R.id.more:
            case R.id.nav_help:
        }
        return true;
    }

    // For image slider

    private void initComponent() {
        layoutDots =findViewById(R.id.layout_dots);
        viewPager = findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(this, new ArrayList<Image>());

        final List<Image> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            Image obj = new Image();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.brief = array_brief_place[i];
            items.add(obj);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layoutDots, adapterImageSlider.getCount(), 0);
        ((TextView) findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                ((TextView) findViewById(R.id.brief)).setText(items.get(pos).brief);
                addBottomDots(layoutDots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 8000);
            }
        };
        handler.postDelayed(runnable, 8000);
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Image> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });

            ((ViewPager) container).addView(v);

            return v;
        }

        public static void displayImageOriginal(Context ctx, ImageView img, @DrawableRes int drawable) {
            try {
                Glide.with(ctx).load(drawable)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(img);
            } catch (Exception e) {
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }


}
