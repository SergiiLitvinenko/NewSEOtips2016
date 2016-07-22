package com.litvinenko.newseotips2016.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.activities.MainActivity;

public class InfoDialogFragment extends DialogFragment {

    public static InfoDialogFragment newInstance(int title, int message) {
        InfoDialogFragment infoFrag = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putInt("Title", title);
        args.putInt("Message", message);
        infoFrag.setArguments(args);
        return infoFrag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("Title");
        int message = getArguments().getInt("Message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(message)
                .setPositiveButton(R.string.alert_dialog_button_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }
                )
                .create();
    }
}