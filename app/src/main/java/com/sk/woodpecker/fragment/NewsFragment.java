package com.sk.woodpecker.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sk.woodpecker.R;
import com.sk.woodpecker.adapter.NewsItemLayoutAdapter;
import com.sk.woodpecker.adapter.ServiceItemLayoutAdapter;
import com.sk.woodpecker.bean.News;
import com.sk.woodpecker.bean.Service;
import com.sk.woodpecker.util.Config;
import com.sk.woodpecker.util.image.ImageLoadUtil;
import com.sk.woodpecker.widget.ViewPagerBanners;
import com.tuesda.walker.circlerefresh.CListView;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {


    private ViewPagerBanners newsViewPager;
    private ArrayList<News> list;
    private CListView listview;
    private NewsItemLayoutAdapter adapter;
    ArrayList<String> imageUrls = new ArrayList<String>();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        imageUrls.add(Config.url1);
        imageUrls.add(Config.url2);
        imageUrls.add(Config.url3);
        int adH = getResources().getDimensionPixelSize(R.dimen.news_ad_h);

        newsViewPager = new ViewPagerBanners(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, adH);
        newsViewPager.setLayoutParams(params);

        listview = (CListView) view.findViewById(R.id.news_listview);
        listview.addHeadView(newsViewPager);
        listview.setDivider(new ColorDrawable(Color.parseColor("#50818283")));
        listview.setDividerHeight(20);
        newsViewPager.setImageResources(imageUrls, new ViewPagerBanners.PagerBannersListener() {

            @Override
            public void onImageClick(int position, View imageView) {
            }

            @Override
            public void ShowImage(String imageURL, ImageView imageView) {
                ImageLoadUtil.getCommonImage(imageView, imageURL);
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new News());
        }
        adapter = new NewsItemLayoutAdapter(mContext, list);
        listview.setAdapter(adapter);
        listview.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                listview.finishRefreshing();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
