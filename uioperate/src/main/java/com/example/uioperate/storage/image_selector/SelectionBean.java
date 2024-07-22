package com.example.uioperate.storage.image_selector;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class SelectionBean implements Parcelable {
    Uri uri;
    String[] projection;
    String selection;
    String[] selectionArgs;
    String sortOrder;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri, flags);
        dest.writeStringArray(this.projection);
        dest.writeString(this.selection);
        dest.writeStringArray(this.selectionArgs);
        dest.writeString(this.sortOrder);
    }

    public void readFromParcel(Parcel source) {
        this.uri = source.readParcelable(Uri.class.getClassLoader());
        this.projection = source.createStringArray();
        this.selection = source.readString();
        this.selectionArgs = source.createStringArray();
        this.sortOrder = source.readString();
    }

    public SelectionBean() {
    }

    public SelectionBean(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        this.uri = uri;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
    }

    protected SelectionBean(Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.projection = in.createStringArray();
        this.selection = in.readString();
        this.selectionArgs = in.createStringArray();
        this.sortOrder = in.readString();
    }

    public static final Parcelable.Creator<SelectionBean> CREATOR = new Parcelable.Creator<SelectionBean>() {
        @Override
        public SelectionBean createFromParcel(Parcel source) {
            return new SelectionBean(source);
        }

        @Override
        public SelectionBean[] newArray(int size) {
            return new SelectionBean[size];
        }
    };
}
