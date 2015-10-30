package com.sk.woodpecker.business;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sk.woodpecker.DemoApplication;
import com.sk.woodpecker.R;
import com.sk.woodpecker.fragment.BaseFragment;
import com.sk.woodpecker.fragment.HomeFragment;
import com.sk.woodpecker.fragment.MyCenterFragment;
import com.sk.woodpecker.fragment.NewsFragment;
import com.sk.woodpecker.fragment.ServiceFragment;
import com.sk.woodpecker.util.DemoHelper;
import com.sk.woodpecker.util.SystemBarTintManager;

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
    private boolean autoLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitileBar();
        initImageLoader(this);
        init();
        // 如果登录成功过，直接进入主页面
        if (DemoHelper.getInstance().isLoggedIn()) {
            autoLogin = true;

        }else{
            loginServer();
        }
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
    /**
     * 初始化沉浸式菜单栏
     */
    public void initTitileBar(){
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(Color.TRANSPARENT);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchFragment(view);
        }
    };

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config.memoryCacheSize(30 * 1014 * 1024);/*手机内存设置*/
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
    private void loginServer(){
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login("jiangminglu", "123456", new EMCallBack() {

            @Override
            public void onSuccess() {
                // 登陆成功，保存用户名
                DemoHelper.getInstance().setCurrentUserName("jiangminglu");
                // 注册群组和联系人监听
                DemoHelper.getInstance().registerGroupAndContactListener();

                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                EMChatManager.getInstance().loadAllConversations();
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {

            }
        });
    }

}
