package com.example.pas;

import android.os.Parcel;
import android.os.Parcelable;

public class kucingModel implements Parcelable {
    private String image;
    private String id;
    private String width;
    private String height;

    protected kucingModel(Parcel in) {
        image = in.readString();
        id = in.readString();
        width = in.readString();
        height = in.readString();
    }

    public static final Creator<kucingModel> CREATOR = new Creator<kucingModel>() {
        @Override
        public kucingModel createFromParcel(Parcel in) {
            return new kucingModel(in);
        }

        @Override
        public kucingModel[] newArray(int size) {
            return new kucingModel[size];
        }
    };
    public kucingModel() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(id);
        dest.writeString(width);
        dest.writeString(height);
    }
}
