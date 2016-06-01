package com.litvinenko.newseotips2016.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.litvinenko.newseotips2016.R;

public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Button btnSeo, btnOptim, btnTools, btnSeoInfo, btnOptimInfo, btnToolsInfo;

    private IOnMainSeoClickListener seoListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        btnSeo = (Button) v.findViewById(R.id.btnMainSeo);
        btnOptim = (Button) v.findViewById(R.id.btnMainOptim);
        btnTools = (Button) v.findViewById(R.id.btnMainTools);
        btnSeoInfo = (Button) v.findViewById(R.id.btnMainSeoInfo);
        btnOptimInfo = (Button) v.findViewById(R.id.btnMainOptimInfo);
        btnToolsInfo = (Button) v.findViewById(R.id.btnMainToolsInfo);

        btnSeo.setOnClickListener(this);
        btnOptim.setOnClickListener(this);
        btnTools.setOnClickListener(this);
        btnSeoInfo.setOnClickListener(this);
        btnOptimInfo.setOnClickListener(this);
        btnToolsInfo.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        seoListener = (IOnMainSeoClickListener) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainSeo:
                seoListener.onMainSeoClick();
                break;

            case R.id.btnMainOptim:
                seoListener.onMainOptimClick();
                break;

            case R.id.btnMainTools:
                seoListener.onMainToolsClick();
                break;

            case R.id.btnMainSeoInfo:
                seoListener.onMainSeoInfoClick();
                break;

            case R.id.btnMainOptimInfo:
                seoListener.onMainOptimInfoClick();
                break;

            case R.id.btnMainToolsInfo:
                seoListener.onMainToolsInfoClick();
                break;
        }
    }

    public interface IOnMainSeoClickListener {
        void onMainSeoClick();
        void onMainOptimClick();
        void onMainToolsClick();
        void onMainSeoInfoClick();
        void onMainOptimInfoClick();
        void onMainToolsInfoClick();
    }
}