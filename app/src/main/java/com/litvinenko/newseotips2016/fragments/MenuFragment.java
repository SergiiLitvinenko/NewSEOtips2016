package com.litvinenko.newseotips2016.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.litvinenko.newseotips2016.R;
import com.litvinenko.newseotips2016.activities.Advice;
import com.litvinenko.newseotips2016.database.DBContract;
import com.litvinenko.newseotips2016.database.MyDatabaseAssetHelper;

import java.util.LinkedList;

public class MenuFragment extends android.support.v4.app.ListFragment {

    private int type;
    private Advice advice;

    private IOnMenuClickListener menuListener;

    private LinkedList<Advice> advicesDataList = new LinkedList<>();
    private LinkedList<String> filteredList = new LinkedList<>();

    private MyDatabaseAssetHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        if (getArguments() != null) {
            type = getArguments().getInt("Type");
        }
        else
            type = 0;

            initDataBase();
            initData();
            addAdvices();

        /**
         * Setting custom list layout
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list, filteredList);
        setListAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        menuListener = (IOnMenuClickListener) activity;
    }

    /**
     * Sending selected advice, its position and type to AdviceFragment. We need this data to
     * normally run ViewPager
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        String listItem = (String) getListAdapter().getItem(position);
        for (int i = 0; i < advicesDataList.size(); i++) {
            if (listItem.equals(advicesDataList.get(i).getName())) {
                advice = advicesDataList.get(i);
            }
        }
        menuListener = (IOnMenuClickListener) getActivity();
        menuListener.onMenuClick(advice, position, type);
    }

    public interface IOnMenuClickListener {
        void onMenuClick(Advice advice, int position, int type);
    }

    /**
     * Initializing and getting data from Database
     */
    private void initDataBase() {
        dbHelper = new MyDatabaseAssetHelper(getActivity());
    }

    private void initData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String columns[] = new String[]{DBContract.SeoAdviceTable.CATEGORY_COLUMN,
                DBContract.SeoAdviceTable.NUMBER_COLUMN, DBContract.SeoAdviceTable.NAME_COLUMN,
                DBContract.SeoAdviceTable.CONTENT_COLUMN};
        Cursor c = db.query(DBContract.Tables.SEO_ADVICE_TABLE, columns, null, null, null, null, null);
        int adviceCategoryIndex = c.getColumnIndex(DBContract.SeoAdviceTable.CATEGORY_COLUMN);
        int adviceNumberIndex = c.getColumnIndex(DBContract.SeoAdviceTable.NUMBER_COLUMN);
        int adviceNameIndex = c.getColumnIndex(DBContract.SeoAdviceTable.NAME_COLUMN);
        int adviceContentIndex = c.getColumnIndex(DBContract.SeoAdviceTable.CONTENT_COLUMN);


        while (c.moveToNext()) {
            Integer adviceCategory = c.getInt(adviceCategoryIndex);
            Integer adviceNumber = c.getInt(adviceNumberIndex);
            String adviceName = c.getString(adviceNameIndex);
            String adviceContent = c.getString(adviceContentIndex);
            advicesDataList.add(new Advice(adviceCategory, adviceNumber, adviceName, adviceContent));
        }
        db.close();
        for (int i = 0; i < advicesDataList.size(); i++);
    }

    /**
     * Add advice to filtered list
     */
    private void addAdvices() {
        if (filteredList.size() == 0) {
            for (int i = 0; i < advicesDataList.size(); i++) {
                if(advicesDataList.get(i).getCategory() == type) {
                    filteredList.add(advicesDataList.get(i).getNumber() + ". " +
                            advicesDataList.get(i).getName());
                }
            }
        }
    }
}