package com.litvinenko.newseotips2016.activities;

import android.content.Intent;
import android.os.Bundle;
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

    int menuType;

    private android.support.v4.app.Fragment fMmain;
    private android.support.v4.app.Fragment fMenu;
    private android.support.v4.app.FragmentManager fragmentManager;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_INFO = "SHOW_INFO";
    private boolean infoStatus;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(fragmentManager.findFragmentByTag("MenuFragment") != null) {
            outState.putBoolean("InMenu", true);
            outState.putInt("SavedMenu", menuType);
            super.onSaveInstanceState(outState);
        }
        else
            super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        /**
//         * Check settings for dialog status
//         */
//        SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        if(mySharedPreferences.getBoolean(APP_PREFERENCES_INFO, infoStatus)) {
//            nicknameText.setText(mSettings.getString(APP_PREFERENCES_NAME, ""));
//        }



        flFragmentContainer = (FrameLayout)findViewById(R.id.flFragmentContainer);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            fMmain = new MainFragment();
            android.support.v4.app.FragmentTransaction fTrans = fragmentManager.beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, fMmain, "MainFragment");
            fTrans.commit();
        }
        else {
            fMenu = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Type", savedInstanceState.getInt("SavedMenu"));
            fMenu.setArguments(bundle);
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
            ft.commit();
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
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onMainOptimClick() {
        fMenu = new MenuFragment();
        Bundle bundle = new Bundle();
        menuType = 2;
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.flFragmentContainer, fMenu, "MenuFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onMainToolsClick() {
        fMenu = new MenuFragment();
        Bundle bundle = new Bundle();
        menuType = 3;
        bundle.putInt("Type", menuType);
        fMenu.setArguments(bundle);
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
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

    public void doPositiveClick() {
        Log.i("FragmentAlertDialog", "Positive click!");
    }

}