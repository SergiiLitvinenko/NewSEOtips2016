package com.litvinenko.newseotips2016.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.fragments.InfoDialogFragment;
import com.litvinenko.newseotips2016.fragments.MainFragment;
import com.litvinenko.newseotips2016.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.IOnMainSeoClickListener,
        MenuFragment.IOnMenuClickListener {
    private FrameLayout flFragmentContainer;

    private android.support.v4.app.Fragment fMmain;
    private android.support.v4.app.Fragment fMenu;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flFragmentContainer = (FrameLayout)findViewById(R.id.flFragmentContainer);

        fragmentManager = getSupportFragmentManager();
        fMmain = new MainFragment();
        android.support.v4.app.FragmentTransaction fTrans = fragmentManager.beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, fMmain, "MainFragment");
        fTrans.commit();
    }

    /**
     * Create Options Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Set results for main menu buttons clicks. Open menu fragment and send
     * there type of advices we need via Bundle
     */
    @Override
    public void onMainSeoClick() {
            fMenu = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Type", 1);
            fMenu.setArguments(bundle);
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
            ft.addToBackStack(null);
            ft.commit();
    }

    @Override
    public void onMainOptimClick() {
            fMenu = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Type", 2);
            fMenu.setArguments(bundle);
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
            ft.addToBackStack(null);
            ft.commit();
    }

    @Override
    public void onMainToolsClick() {
        fMenu = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Type", 3);
        fMenu.setArguments(bundle);
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Set results for main menu info buttons clicks. All dialogs are dynamically generated
     */
    @Override
    public void onMainSeoInfoClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.info_seo_title));
        alertDialog.setMessage(getString(R.string.info_seo_content));
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onMainOptimInfoClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.info_optim_title));
        alertDialog.setMessage(getString(R.string.info_optim_content));
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onMainToolsInfoClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.info_tools_title));
        alertDialog.setMessage(getString(R.string.info_tools_content));
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    /**
     * Set results for advices menu click. We are open Advice activity and send there
     * type and position of advice that was clicked because it is needed for proper work of
     * View Pager
     */
    @Override
    public void onMenuClick(Advice advice, int position, int type) {
        Intent intent = new Intent(this, AdviceActivity.class);
        intent.putExtra("Position", position);
        intent.putExtra("Type", type);
        startActivity(intent);
    }


    /**
     * Set options menu click result. Note: add advice and share have different types.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddAdvice:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.alert_dialog_dev_email)});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.alert_dialog_dev_subject));
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.alert_dialog_add_advice)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, R.string.alert_dialog_no_email_client, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.miInfo:
                showDialog();
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setTitle(getString(R.string.alert_dialog_info_title));
//                alertDialog.setMessage(getString(R.string.alert_dialog_info_content));
//                alertDialog.setIcon(android.R.drawable.ic_dialog_info);
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
        }
        return true;
    }

    void showDialog() {

        InfoDialogFragment newFragment = InfoDialogFragment.newInstance(R.string.alert_dialog_info_title,
                R.string.alert_dialog_info_content);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }
}