package com.litvinenko.newseotips2016.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.database.DBContract;
import com.litvinenko.newseotips2016.database.MyDatabaseAssetHelper;
import com.litvinenko.newseotips2016.fragments.AdviceFragment;

import java.util.LinkedList;

public class AdviceActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private Integer currentPosition;
    private Integer type;

    private LinkedList<Advice> advicesDataList = new LinkedList<>();
    private LinkedList<Advice> advicesList = new LinkedList<>();
    private Advice[] filteredAdvicesList;
    private MyDatabaseAssetHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        /**
         * Show up icon in Action Bar
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        pager = (ViewPager) findViewById(R.id.vp_ViewPager);

        /**
         * Get position of advice clicked and its type
         */
        Bundle bundle = getIntent().getExtras();
        currentPosition = bundle.getInt("Position");
        type = bundle.getInt("Type");

        initDataBase();
        initData();
        addAdvices();

        /**
         * Set Pager Adapter and launching View Pager. Setting current item
         */
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(currentPosition);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Create Options Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_advice, menu);
        return true;
    }

    /**
     * Fill View Pager with our advices of specific type
     */
    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Совет " + (position + 1);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
                return AdviceFragment.newInstance(filteredAdvicesList[position]);
        }


        @Override
        public int getCount() {
            return advicesList.size();
        }

    }

    /**
     * Set options menu click result. Note: add advice and share advice have
     * different .setType attributes.
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
                    Toast.makeText(AdviceActivity.this, R.string.alert_dialog_no_email_client, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.miShare:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        filteredAdvicesList[currentPosition].getName());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        filteredAdvicesList[currentPosition].getContent());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_advice)));
                break;

            case R.id.miReport:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.alert_dialog_dev_email)});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.alert_dialog_report_subject) +
                        filteredAdvicesList[currentPosition].getName());
                intent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.alert_dialog_add_advice)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AdviceActivity.this, R.string.alert_dialog_no_email_client, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.miInfo:
                AlertDialog alertDialog = new AlertDialog.Builder(AdviceActivity.this).create();
                alertDialog.setTitle(getString(R.string.alert_dialog_info_title));
                alertDialog.setMessage(getString(R.string.alert_dialog_info_content));
                alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
        }
        return true;
    }

    /**
     * Initialize and get data from Database
     */
    private void initDataBase() {
        dbHelper = new MyDatabaseAssetHelper(this);
    }

    private void initData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String columns[] = new String[]{DBContract.SeoAdvicesTable.NAME_COLUMN,
                DBContract.SeoAdvicesTable.CONTENT_COLUMN, DBContract.SeoAdvicesTable.CATEGORY_COLUMN};
        Cursor c = db.query(DBContract.Tables.SEO_ADVICES_TABLE, columns, null, null, null, null, null);
        int adviceNameIndex = c.getColumnIndex(DBContract.SeoAdvicesTable.NAME_COLUMN);
        int adviceContentIndex = c.getColumnIndex(DBContract.SeoAdvicesTable.CONTENT_COLUMN);
        int adviceCategoryIndex = c.getColumnIndex(DBContract.SeoAdvicesTable.CATEGORY_COLUMN);

        while (c.moveToNext()) {
            String adviceName = c.getString(adviceNameIndex);
            String adviceContent = c.getString(adviceContentIndex);
            Integer adviceCategory = c.getInt(adviceCategoryIndex);
            advicesDataList.add(new Advice(adviceName, adviceContent, adviceCategory));
        }
        db.close();
    }

    /**
     * Add advice to filtered list according to selected type
     */
    private void addAdvices() {
        if (advicesList.size() == 0) {
            for (int i = 0; i < advicesDataList.size(); i++) {
                if(advicesDataList.get(i).getCategory() == type) {
                    advicesList.add(advicesDataList.get(i));
                }
                filteredAdvicesList = advicesList.toArray(new Advice[advicesList.size()]);
            }
        }
    }
}