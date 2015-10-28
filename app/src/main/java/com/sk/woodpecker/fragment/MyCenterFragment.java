package com.sk.woodpecker.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sk.woodpecker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCenterFragment extends BaseFragment {


    public MyCenterFragment() {
        // Required empty public constructor
    }


    private ImageView myAvatarImg;
    private TextView myNameTv;
    private TextView myTelTv;
    private LinearLayout myFavLayout;
    private LinearLayout myFeedbackLayout;
    private LinearLayout myPwdLayout;
    private LinearLayout myHelpLayout;
    private LinearLayout myServiceLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_center, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myAvatarImg = (ImageView) view.findViewById(R.id.my_avatar_img);
        myNameTv = (TextView) view.findViewById(R.id.my_name_tv);
        myTelTv = (TextView) view.findViewById(R.id.my_tel_tv);
        myFavLayout = (LinearLayout) view.findViewById(R.id.my_fav_layout);
        myFeedbackLayout = (LinearLayout) view.findViewById(R.id.my_feedback_layout);
        myPwdLayout = (LinearLayout) view.findViewById(R.id.my_pwd_layout);
        myHelpLayout = (LinearLayout) view.findViewById(R.id.my_help_layout);
        myServiceLayout = (LinearLayout) view.findViewById(R.id.my_service_layout);
    }


}
