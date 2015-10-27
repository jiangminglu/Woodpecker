package com.sk.woodpecker.business;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sk.woodpecker.R;
import com.sk.woodpecker.fragment.BaseFragment;
import com.sk.woodpecker.fragment.HomeFragment;
import com.sk.woodpecker.fragment.MyCenterFragment;
import com.sk.woodpecker.fragment.NewsFragment;
import com.sk.woodpecker.fragment.ServiceFragment;

public class MainActivity extends FragmentActivity {

    private HomeFragment homeFragment;
    private ServiceFragment serviceFragment;
    private NewsFragment newsFragment;
    private MyCenterFragment myFragment;

    private FrameLayout mainContent;
    private LinearLayout mainHomeLayout;
    private ImageView mainHomeImg;
    private TextView mainHomeTv;
    private LinearLayout mainServiceLayout;
    private ImageView mainServiceImg;
    private TextView mainServiceTv;
    private LinearLayout mainNewsLayout;
    private ImageView mainNewsImg;
    private TextView mainNewsTv;
    private LinearLayout mainMyLayout;
    private ImageView mainMyImg;
    private TextView mainMyTv;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        init();
    }

    private void init() {
        mainContent = (FrameLayout) findViewById(R.id.main_content);
        mainHomeLayout = (LinearLayout) findViewById(R.id.main_home_layout);
        mainHomeImg = (ImageView) findViewById(R.id.main_home_img);
        mainHomeTv = (TextView) findViewById(R.id.main_home_tv);
        mainServiceLayout = (LinearLayout) findViewById(R.id.main_service_layout);
        mainServiceImg = (ImageView) findViewById(R.id.main_service_img);
        mainServiceTv = (TextView) findViewById(R.id.main_service_tv);
        mainNewsLayout = (LinearLayout) findViewById(R.id.main_news_layout);
        mainNewsImg = (ImageView) findViewById(R.id.main_news_img);
        mainNewsTv = (TextView) findViewById(R.id.main_news_tv);
        mainMyLayout = (LinearLayout) findViewById(R.id.main_my_layout);
        mainMyImg = (ImageView) findViewById(R.id.main_my_img);
        mainMyTv = (TextView) findViewById(R.id.main_my_tv);

        mainHomeLayout.setOnClickListener(onClickListener);
        mainNewsLayout.setOnClickListener(onClickListener);
        mainServiceLayout.setOnClickListener(onClickListener);
        mainMyLayout.setOnClickListener(onClickListener);

        switchFragment(mainHomeLayout);
    }

    private void switchFragment(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


        BaseFragment baseFragment = null;
        if (view == mainHomeLayout) {
            if (homeFragment == null)
                homeFragment = new HomeFragment();
            baseFragment = homeFragment;
        } else if (view == mainServiceLayout) {
            if (serviceFragment == null)
                serviceFragment = new ServiceFragment();
            baseFragment = serviceFragment;
        } else if (view == mainNewsLayout) {
            if (newsFragment == null)
                newsFragment = new NewsFragment();
            baseFragment = newsFragment;
        } else if (view == mainMyLayout) {
            if (myFragment == null)
                myFragment = new MyCenterFragment();
            baseFragment = myFragment;
        }

        if (currentFragment != null && currentFragment.isVisible() && currentFragment != baseFragment) {
            ft.hide(currentFragment);
        }
        if (baseFragment.isAdded()) {
            ft.show(baseFragment);
        } else {
            ft.add(R.id.main_content, baseFragment);
        }
        currentFragment = baseFragment;
        ft.commit();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchFragment(view);
        }
    };
}
