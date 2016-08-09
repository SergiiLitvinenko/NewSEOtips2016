package com.litvinenko.newseotips2016.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.activities.Advice;

public class AdviceFragment extends Fragment {
    TextView tvAdviceName, tvAdviceContent, tvAdviceExample;
    ImageView ivAdviceImage;

    AdView mAdView;

    public static AdviceFragment newInstance(Advice advice) {
        AdviceFragment fragmentAdvice = new AdviceFragment();
        Bundle args = new Bundle();
        args.putParcelable("Advice", advice);
        fragmentAdvice.setArguments(args);
        return fragmentAdvice;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advice, container, false);

        tvAdviceName = (TextView)v.findViewById(R.id.tvAdviceName);
        tvAdviceContent = (TextView)v.findViewById(R.id.tvAdvice);
        tvAdviceExample = (TextView)v.findViewById(R.id.tvAdviceExample);
        ivAdviceImage = (ImageView)v.findViewById(R.id.ivAdviceImage);

        if (getArguments() != null) {
            Advice advice = getArguments().getParcelable("Advice");
            setAdvice(advice);
        }

        mAdView = new AdView(getActivity());
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.rlAdmobAdvice);
        layout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adRequestBuilder.addTestDevice("04BE9085560FE8B1C252BDB22C5D8129");
        mAdView.loadAd(adRequest);

        return v;
    }

    /**
     * Set current advice. Note that we have support of html tags in some fields.
     */
    private void setAdvice(Advice advice) {
        tvAdviceName.setText(advice.getName());
        if(advice.getContent() != null)
            tvAdviceContent.setText(String.valueOf(advice.getContent()));
        if(advice.getExample() != null)
            tvAdviceExample.setText(String.valueOf(advice.getExample()));
        if(advice.getImage() != null)
        ivAdviceImage.setImageResource(getResourceID(advice.getImage(), "drawable", getContext()));

    }

    /**
     * Get resourse ID for images
     */
    protected static int getResourceID
            (final String resName, final String resType, final Context context)
    {
        final int ResourceID =
                context.getResources().getIdentifier(resName, resType,
                        context.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }
}