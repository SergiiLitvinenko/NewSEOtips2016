package com.litvinenko.newseotips2016.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

//    public static final String APP_PREFERENCES_INFO
//    public static final String APP_PREFERENCES_RANDOM_ADVICE

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
        showInfoDialog(R.string.info_seo_title, R.string.info_seo_content);
    }

    @Override
    public void onMainOptimInfoClick() {
        showInfoDialog(R.string.info_optim_title, R.string.info_optim_content);
    }

    @Override
    public void onMainToolsInfoClick() {
        showInfoDialog(R.string.info_tools_title, R.string.info_tools_content);
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
                showInfoDialog(R.string.alert_dialog_info_title, R.string.alert_dialog_info_content);
        }
        return true;
    }

    void showInfoDialog(int title, int content) {
        InfoDialogFragment newFragment = InfoDialogFragment.newInstance(title, content);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        Log.i("FragmentAlertDialog", "Negative click!");
    }

}