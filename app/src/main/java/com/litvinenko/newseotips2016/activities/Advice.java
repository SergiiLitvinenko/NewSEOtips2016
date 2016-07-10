package com.litvinenko.newseotips2016.activities;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class Advice extends Activity implements Parcelable {
    private String Name, Content, Example, Image;
    private Integer Category, Number;

    public Advice(Integer category, String name, String content) {
        Name = name;
        Content = content;
        Category = category;
    }

    public Advice(Integer category, Integer number, String name, String content, String example, String image) {
        Category = category;
        Number = number;
        Name = name;
        Content = content;
        Example = example;
        Image = image;
    }

    public Integer getCategory() {
        return Category;
    }

    public void setCategory(Integer category) {
        Category = category;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
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

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

        dest.writeStringArray(new String[]{String.valueOf(this.Category), String.valueOf(this.Number),
                this.Name, this.Content, this.Example, this.Image});
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