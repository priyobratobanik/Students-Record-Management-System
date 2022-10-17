package com.sohini101.studentsrecords;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SOHINI PAL on 29-12-2017.
 */

public class StudentDataProvider implements Parcelable {
    private String id;
    private String streams;
    private String regYear;
    private String fname;
    private String lname;
    private String roll;
    private String email;
    private String phone;

    public StudentDataProvider(String streams, String regYear, String fname, String lname, String roll, String email, String phone){

        this.streams=streams;
        this.regYear=regYear;
        this.fname=fname;
        this.lname=lname;
        this.roll=roll;
        this.email=email;
        this.phone=phone;
    }

    protected StudentDataProvider(Parcel in) {
        id = in.readString();
        streams = in.readString();
        regYear = in.readString();
        fname = in.readString();
        lname = in.readString();
        roll = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    public static final Creator<StudentDataProvider> CREATOR = new Creator<StudentDataProvider>() {
        @Override
        public StudentDataProvider createFromParcel(Parcel in) {
            return new StudentDataProvider(in);
        }

        @Override
        public StudentDataProvider[] newArray(int size) {
            return new StudentDataProvider[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getStreams() {
        return streams;
    }

    public void setStreams(String streams) {
        this.streams =streams;
    }
    public String getRegYear() {
        return regYear;
    }

    public void setRegYear(String regYear) {
        this.regYear =regYear;
    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(streams);
        dest.writeString(regYear);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(roll);
        dest.writeString(email);
        dest.writeString(phone);
    }
    @Override
    public String toString() {
        return "StudentDataProvider{" +
                "id='" + id + '\'' +
                ", name='" + streams + '\'' +
                ", regYear='" + regYear + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", roll='" + roll + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
