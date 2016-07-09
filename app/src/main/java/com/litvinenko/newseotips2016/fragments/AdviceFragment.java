package com.litvinenko.newseotips2016.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.activities.Advice;

public class AdviceFragment extends Fragment {
    TextView tvAdviceName, tvAdviceContent;

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

        if (getArguments() != null) {
            Advice advice = getArguments().getParcelable("Advice");
            setAdvice(advice);
        }

        return v;
    }

    /**
     * Note that we have support of html tags in tvAdviceContent. It is needed for Tool advices
     */
    private void setAdvice(Advice advice) {
        tvAdviceName.setText(advice.getName());
        if(advice.getContent() != null)
            tvAdviceContent.setText(Html.fromHtml(advice.getContent()));

    }
}