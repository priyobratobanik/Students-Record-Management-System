package com.sohini101.studentsrecords;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SOHINI PAL on 08-01-2018.
 */

public class StudentImageProvider implements Parcelable{
    private String imgid;
    private String imgroll;
    private byte[] image;
    public StudentImageProvider(String imgroll, byte[] image) {

        this.imgroll =imgroll;
        this.image = image;
    }

    protected StudentImageProvider(Parcel in) {
        imgid = in.readString();
        imgroll = in.readString();
        image = in.createByteArray();
    }

    public static final Creator<StudentImageProvider> CREATOR = new Creator<StudentImageProvider>() {
        @Override
        public StudentImageProvider createFromParcel(Parcel in) {
            return new StudentImageProvider(in);
        }

        @Override
        public StudentImageProvider[] newArray(int size) {
            return new StudentImageProvider[size];
        }
    };

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getImgroll() {
        return imgroll;
    }

    public void setImgroll(String roll) {
        this.imgroll = imgroll;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgid);
        dest.writeString(imgroll);
        dest.writeByteArray(image);
    }
}
