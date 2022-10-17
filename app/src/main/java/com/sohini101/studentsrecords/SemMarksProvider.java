package com.sohini101.studentsrecords;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SOHINI PAL on 10-01-2018.
 */

public class SemMarksProvider implements Parcelable{
    private String semid;
    private String semroll;
    private String firstsem1;
    private String firstsem2;
    private String firstYGPA;
    private String secondsem1;
    private String secondsem2;
    private String secondYGPA;
    private String thirdsem1;
    private String thirdsem2;
    private String thirdYGPA;
    private String fourthsem1;
    private String fourthsem2;
    private String fourthYGPA;
    private String finalDGPA;

    public SemMarksProvider(String semroll, String firstsem1, String firstsem2, String firstYGPA, String secondsem1, String secondsem2, String secondYGPA, String thirdsem1, String thirdsem2, String thirdYGPA, String fourthsem1, String fourthsem2, String fourthYGPA, String finalDGPA){
        this.semroll=semroll;
        this.firstsem1=firstsem1;
        this.firstsem2=firstsem2;
        this.firstYGPA=firstYGPA;
        this.secondsem1=secondsem1;
        this.secondsem2=secondsem2;
        this.secondYGPA=secondYGPA;
        this.thirdsem1=thirdsem1;
        this.thirdsem2=thirdsem2;
        this.thirdYGPA=thirdYGPA;
        this.fourthsem1=fourthsem1;
        this.fourthsem2=fourthsem2;
        this.fourthYGPA=fourthYGPA;
        this.finalDGPA=finalDGPA;


    }

    protected SemMarksProvider(Parcel in) {
        semid = in.readString();
        semroll = in.readString();
        firstsem1 = in.readString();
        firstsem2 = in.readString();
        firstYGPA = in.readString();
        secondsem1 = in.readString();
        secondsem2 = in.readString();
        secondYGPA = in.readString();
        thirdsem1 = in.readString();
        thirdsem2 = in.readString();
        thirdYGPA = in.readString();
        fourthsem1 = in.readString();
        fourthsem2 = in.readString();
        fourthYGPA = in.readString();
        finalDGPA = in.readString();
    }

    public static final Creator<SemMarksProvider> CREATOR = new Creator<SemMarksProvider>() {
        @Override
        public SemMarksProvider createFromParcel(Parcel in) {
            return new SemMarksProvider(in);
        }

        @Override
        public SemMarksProvider[] newArray(int size) {
            return new SemMarksProvider[size];
        }
    };

    public String getSemid(){
        return semid;
    }
    public void setSemid(String semid){
        this.semid=semid;
    }
    public String getSemroll(){
        return semroll;
    }
    public void setSemroll(String semroll){
        this.semroll=semroll;
    }
    public String getFirstsem1(){
        return firstsem1;
    }
    public void setFirstsem1(String firstsem1){

        this.firstsem1=firstsem1;
    }
    public String getFirstsem2(){
        return firstsem2;
    }
    public void setFirstsem2(String firstsem2){
        this.firstsem2=firstsem2;
    }
    public String getFirstYGPA(){

        return firstYGPA;
    }
    public void setFirstYGPA(String firstYGPA){
        this.firstYGPA=firstYGPA;
    }
    public String getSecondsem1(){
        return secondsem1;
    }
    public void setSecondsem1(String secondsem1){
        this.secondsem1=secondsem1;
    }
    public String getSecondsem2(){
        return secondsem2;
    }
    public void setSecondsem2(String secondsem2){
        this.secondsem2=secondsem2;
    }
    public String getSecondYGPA(){
        return secondYGPA;
    }
    public void setSecondYGPA(String secondYGPA){
        this.secondYGPA=secondYGPA;
    }
    public String getThirdsem1(){
        return thirdsem1;
    }
    public void setThirdsem1(String thirdsem1){
        this.thirdsem1=thirdsem1;
    }
    public String getThirdsem2(){
        return thirdsem2;
    }
    public void setThirdsem2(String thirdsem2){
        this.thirdsem2=thirdsem2;
    }
    public String getThirdYGPA(){
        return thirdYGPA;
    }
    public void setThirdYGPA(String thirdYGPA){
        this.thirdYGPA=thirdYGPA;
    }
    public String getFourthsem1(){
        return fourthsem1;
    }
    public void setFourthsem1(String fourthsem1){
        this.fourthsem1=fourthsem1;
    }
    public String getFourthsem2(){
        return fourthsem2;
    }
    public void setFourthsem2(String fourthsem2){
        this.fourthsem2=fourthsem2;
    }
    public String getFourthYGPA(){
        return fourthYGPA;
    }
    public void setFourthYGPA(String fourthYGPA){
        this.fourthYGPA=fourthYGPA;
    }
    public String getFinalDGPA(){
        return finalDGPA;
    }
    public void setFinalDGPA(String finalDGPA){
        this.finalDGPA=finalDGPA;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(semid);
        dest.writeString(semroll);
        dest.writeString(firstsem1);
        dest.writeString(firstsem2);
        dest.writeString(firstYGPA);
        dest.writeString(secondsem1);
        dest.writeString(secondsem2);
        dest.writeString(secondYGPA);
        dest.writeString(thirdsem1);
        dest.writeString(thirdsem2);
        dest.writeString(thirdYGPA);
        dest.writeString(fourthsem1);
        dest.writeString(fourthsem2);
        dest.writeString(fourthYGPA);
        dest.writeString(finalDGPA);
    }
}
