package com.litvinenko.newseotips2016.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.fragments.InfoDialogFragment;
import com.litvinenko.newseotips2016.fragments.MainFragment;
import com.litvinenko.newseotips2016.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.IOnMainSeoClickListener,
        MenuFragment.IOnMenuClickListener {
    private FrameLayout flFragmentContainer;

    int menuType;
    boolean menuActive;

    android.support.v4.app.FragmentTransaction fTrans;

    private android.support.v4.app.Fragment fMain;
    private android.support.v4.app.Fragment fMenu;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(menuActive) {
            Log.v("MyLOG", "menuActive");
            outState.putInt("SavedMenu", menuType);
            outState.putBoolean("InMenu", menuActive);
            super.onSaveInstanceState(outState);
        }
        else
            super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flFragmentContainer = (FrameLayout)findViewById(R.id.flFragmentContainer);

        fragmentManager = getSupportFragmentManager();

        /**
         * Check fragmentManager state, if no fragments in backstack = add new, else popBackStack
         */
        if(fragmentManager.getBackStackEntryCount() == 0) {
            fMain = new MainFragment();
            fTrans = fragmentManager.beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, fMain, "MainFragment");
            fTrans.commit();
            menuActive = false;
        }

        else {
            menuActive = savedInstanceState.getBoolean("InMenu");
            fragmentManager.popBackStack();
        }

        /**
         * If menu was active - replace MainFragment with MenuFragment
         */
        if(menuActive) {
            Log.v("MyLOG", "menuActive, launch MenuFragment");
            fMenu = new MenuFragment();
            menuType = savedInstanceState.getInt("SavedMenu");
            menuActive = true;
            Bundle bundle = new Bundle();
            bundle.putInt("Type", menuType);
            bundle.putBoolean("InMenu", menuActive);
            fMenu.setArguments(bundle);
            fTrans = fragmentManager.beginTransaction();
            fTrans.setCustomAnimations(0, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fTrans.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
            fTrans.addToBackStack(null);
            fTrans.commit();
        }

        /**
         * Ads initialize
         */
        AdsInit adTask = new AdsInit();
        adTask.execute();
    }

    class AdsInit extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-6834005874422488~8947206651");
            return null;
        }
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
        menuType = 1;
        menuActive = true;
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        fTrans = fragmentManager.beginTransaction();
        fTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fTrans.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void onMainOptimClick() {
        fMenu = new MenuFragment();
        Bundle bundle = new Bundle();
        menuType = 2;
        menuActive = true;
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        fTrans = fragmentManager.beginTransaction();
        fTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fTrans.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void onMainToolsClick() {
        fMenu = new MenuFragment();
        Bundle bundle = new Bundle();
        menuType = 3;
        menuActive = true;
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        fTrans = fragmentManager.beginTransaction();
        fTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fTrans.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        fTrans.addToBackStack(null);
        fTrans.commit();
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
     * Set results for advice menu click. Open Advice activity and send there
     * type and position of advice that was clicked. It is needed for proper work of
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
                showInfoDialog(R.string.alert_dialog_menu_info_title, R.string.alert_dialog_menu_info_content);
                break;

            case R.id.miAppInfo:
                showInfoDialog(R.string.alert_dialog_info_title, R.string.alert_dialog_info_content);
                break;
        }
        return true;
    }

    void showInfoDialog(int title, int content) {
        InfoDialogFragment newFragment = InfoDialogFragment.newInstance(title, content);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onBackPressed() {
        menuActive = false;
            super.onBackPressed();
    }

    public void doPositiveClick() {
        Log.i("FragmentAlertDialog", "Positive click!");
    }

}

