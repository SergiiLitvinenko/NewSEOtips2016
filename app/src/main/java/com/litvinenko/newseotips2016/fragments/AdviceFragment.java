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
import android.widget.TextView;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.activities.Advice;

public class AdviceFragment extends Fragment {
    TextView tvAdviceName, tvAdviceContent;
    ImageView ivAdviceImage;

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
        ivAdviceImage = (ImageView)v.findViewById(R.id.ivAdviceImage);

        if (getArguments() != null) {
            Advice advice = getArguments().getParcelable("Advice");
            setAdvice(advice);
        }

        return v;
    }

    //Res name from SQL db - testing

    String image = "googlepr";

    /**
     * Note that we have support of html tags in tvAdviceContent. It is needed for Tool advices
     */
    private void setAdvice(Advice advice) {
        tvAdviceName.setText(advice.getName());
        if(advice.getContent() != null)
            tvAdviceContent.setText(Html.fromHtml(advice.getContent()));
        ivAdviceImage.setImageResource(getResourceID(image, "drawable", getContext()));

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