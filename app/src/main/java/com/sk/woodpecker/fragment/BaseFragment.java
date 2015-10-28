package com.sk.woodpecker.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by luffy on 10/26/15.
 */
public class BaseFragment extends Fragment{
    public Activity mContext;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }
}
