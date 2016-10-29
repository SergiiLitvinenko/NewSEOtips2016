package com.litvinenko.newseotips2016.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.litvinenko.newseotips2016.R;

public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener, View.OnLongClickListener {
    private Button btnSeo, btnOptim, btnTools;

    private IOnMainSeoClickListener seoListener;

    AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        btnSeo = (Button) v.findViewById(R.id.btnMainSeo);
        btnOptim = (Button) v.findViewById(R.id.btnMainOptim);
        btnTools = (Button) v.findViewById(R.id.btnMainTools);

        btnSeo.setOnClickListener(this);
        btnOptim.setOnClickListener(this);
        btnTools.setOnClickListener(this);
        btnSeo.setOnLongClickListener(this);
        btnOptim.setOnLongClickListener(this);
        btnTools.setOnLongClickListener(this);

        /**
         * AdMob Banner Ads
         */

        //TODO: Find solution: app freezes on mAdView.loadAd(adRequest);

//                mAdView = new AdView(getActivity());
//                Log.v("MyLOG", "mAdView = new AdView");
//                mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id_main));
//                mAdView.setAdSize(AdSize.BANNER);
//                Log.v("MyLOG", "mAdView.setAdSize(AdSize.BANNER)");
//                LinearLayout layout = (LinearLayout) v.findViewById(R.id.llAdmobMain);
//                layout.addView(mAdView);
//                Log.v("MyLOG", "layout.addView(mAdView)");
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mAdView.loadAd(adRequest);

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

        }

    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainSeo:
                seoListener.onMainSeoInfoClick();
                break;

            case R.id.btnMainOptim:
                seoListener.onMainOptimInfoClick();
                break;

            case R.id.btnMainTools:
                seoListener.onMainToolsInfoClick();
                break;
        }

        return true;
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