package com.sk.woodpecker.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContact;
import com.easemob.exceptions.EaseMobException;
import com.sk.woodpecker.R;
import com.sk.woodpecker.bean.RobotUser;
import com.sk.woodpecker.business.ChatActivity;
import com.sk.woodpecker.db.UserDao;
import com.sk.woodpecker.util.Config;
import com.sk.woodpecker.util.DemoHelper;
import com.sk.woodpecker.util.image.ImageLoadUtil;
import com.sk.woodpecker.widget.ViewPagerBanners;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment implements View.OnClickListener {


    public HomeFragment() {
    }

    private ViewPagerBanners homeViewpager;
    private RelativeLayout homeConsultLayout;
    private ImageView homeCoverTwo;
    private RelativeLayout homeAskLayout;
    private ImageView homeCoverOne;
    private TextView myTelTv;
    private List<RobotUser> robotList = new ArrayList<RobotUser>();

    ArrayList<String> imageUrls = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageUrls.add(Config.url1);
        imageUrls.add(Config.url2);
        imageUrls.add(Config.url3);

        homeViewpager = (ViewPagerBanners) view.findViewById(R.id.home_viewpager);
        homeConsultLayout = (RelativeLayout) view.findViewById(R.id.home_consult_layout);
        homeCoverTwo = (ImageView) view.findViewById(R.id.home_cover_two);
        homeAskLayout = (RelativeLayout) view.findViewById(R.id.home_ask_layout);
        homeCoverOne = (ImageView) view.findViewById(R.id.home_cover_one);
        myTelTv = (TextView) view.findViewById(R.id.my_tel_tv);
        homeConsultLayout.setOnClickListener(this);
        homeAskLayout.setOnClickListener(this);
        homeViewpager.setImageResources(imageUrls, new ViewPagerBanners.PagerBannersListener() {

            @Override
            public void onImageClick(int position, View imageView) {
            }

            @Override
            public void ShowImage(String imageURL, ImageView imageView) {
                ImageLoadUtil.getCommonImage(imageView, imageURL);
            }
        });
        Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
        if (robotMap != null) {
            robotList.addAll(robotMap.values());
        } else {
            getRobotNamesFromServer();
        }
    }


    @Override
    public void onClick(View view) {
        if (view == homeConsultLayout) {
            if(robotList.size()>0){
                Intent intent = new Intent();
                intent.setClass(mContext, ChatActivity.class);
                intent.putExtra("userId", robotList.get(0).getUsername());
                startActivity(intent);
            }

        }
    }

    private void getRobotNamesFromServer() {
        asyncGetRobotNamesFromServer(new EMValueCallBack<List<EMContact>>() {

            @Override
            public void onSuccess(final List<EMContact> value) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, RobotUser> mMap = new HashMap<String, RobotUser>();
                        for (EMContact item : value) {
                            RobotUser user = new RobotUser(item.getUsername());
                            user.setNick(item.getNick());
                            user.setInitialLetter("#");
                            mMap.put(item.getUsername(), user);
                        }
                        robotList.clear();
                        robotList.addAll(mMap.values());
                        // 存入内存
                        DemoHelper.getInstance().setRobotList(mMap);
                        // 存入db
                        UserDao dao = new UserDao(mContext);
                        dao.saveRobotUser(robotList);
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }
    private void asyncGetRobotNamesFromServer(final EMValueCallBack<List<EMContact>> callback) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<EMContact> mList = EMChatManager.getInstance().getRobotsFromServer();
                    callback.onSuccess(mList);
                } catch (EaseMobException e) {
                    e.printStackTrace();
                    callback.onError(e.getErrorCode(), e.toString());
                }
            }
        }).start();
    }
}
