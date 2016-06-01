package com.litvinenko.newseotips2016.activities;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class Advice extends Activity implements Parcelable {
    private String Name, Content;
    private Integer Category;

    public Advice(String name, String content, Integer category) {
        Name = name;
        Content = content;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Integer getCategory() {
        return Category;
    }

    public void setCategory(Integer category) {
        Category = category;
    }

    public Advice(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.Name = data[0];
        this.Content = data[1];
        this.Category = Integer.parseInt(data[2]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.Name, this.Content, String.valueOf(this.Category)});
    }

    public static final Parcelable.Creator<Advice> CREATOR= new Parcelable.Creator<Advice>() {

        @Override
        public Advice createFromParcel(Parcel source) {
            return new Advice(source);
        }

        @Override
        public Advice[] newArray(int size) {
            return new Advice[size];
        }
    };
}

