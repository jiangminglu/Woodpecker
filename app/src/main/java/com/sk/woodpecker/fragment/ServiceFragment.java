package com.sk.woodpecker.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sk.woodpecker.R;
import com.sk.woodpecker.adapter.ServiceItemLayoutAdapter;
import com.sk.woodpecker.bean.Service;
import com.tuesda.walker.circlerefresh.CListView;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFragment {


    private ArrayList<Service> list;
    private ServiceItemLayoutAdapter adapter;
    private CListView listview;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = (CListView) view.findViewById(R.id.service_listview);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Service());
        }
        adapter = new ServiceItemLayoutAdapter(mContext,list);
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
    }
}
