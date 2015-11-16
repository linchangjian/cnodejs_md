package com.aniu.cnodejs_md.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment{

    private String BASETAG = BaseFragment.class.getSimpleName();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(BASETAG,"onCreateView ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
