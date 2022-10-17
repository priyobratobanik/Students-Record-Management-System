package com.sohini101.studentsrecords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOHINI PAL on 25-11-2017.
 */

class UserDatbase extends SQLiteOpenHelper {
    private StudentDataProvider studentDataProvider;
    public UserDatbase(Context context, Object name, Object factory, int version) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    String password;
    private static final String DB_NAME = "user.db";
    //table for users
    private static final String TB_NAME = "user_reg";
    public static final String COL1_USERID = "id";
    public static final String COL2_USERNAME = "userName";
    public static final String COL3_USERPASSWORD = "userPassword";
    public static final String COL4_USERQUES="userques";
    public static final String COL5_USERANS="userans";
    // public static final Integer ver=11;
    private static final int DATABASE_VERSION = 1;
    // public static final Integer ver2=2;

    //table for streams
    private static final String TB_STREAM = "streamsTable";
    public static final String COL1_STREAMID = "streamID";
    public static final String COL2_STREAMNAME = "StreamName";
    public String querystreamcreate = "create table " + TB_STREAM + "(streamID integer primary key autoincrement NOT NULL,StreamName TEXT NOT NULL)";
//table for studentsinfo
    private static final String TB_STUDS = "studentsTable";

   public static final String COL1_STUDID = "_id";
    public static final String COL2_STUDSTRM = "studsStrm";
    public static final String COL3_STUDYEAR= "studsYear";
    public static final String COL4_STUDFNAME= "studsFname";
    public static final String COL5_STUDLNAME = "studsLname";
    public static final String COL6_STUDROLL = "roll";
    public static final String COL7_STUDEMAIL = "studsEmail";
    public static final String COL8_STUDPHONE= "studsPhone";
    public String querystudinfocreate = "create table " + TB_STUDS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,studsStrm TEXT NOT NULL,studsYear TEXT NOT NULL,studsFname TEXT NOT NULL,studsLname TEXT NOT NULL,roll TEXT NOT NULL,studsEmail TEXT NOT NULL,studsPhone TEXT NOT NULL)";

    //create student image roll table
    private static final String TB_STUDSIMG = "studentsTableImage";
    public static final String COL1_STUDIMGID = "_id";
    public static final String COL2_STUDIMGROLL = "rollimg";
    public static final String COL3_STUDIMAGE="image";
    public String querystudinfocreateImage = "create table " + TB_STUDSIMG + "("+COL1_STUDIMGID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,rollimg TEXT NOT NULL,image BLOB)";

//create student marks table
    private static final String TB_STUDMARKS="studentsTableMarks";
    public static final String COL1_STUDSEMID="_id";
    public static final String COL4_STUDSEMROLL="semroll";
    public static final String COL5_STUDYR1SEM1="yr1sem1";
    public static final String COL6_STUDYR1SEM2="yr1sem2";
    public static final String COLYR1_YGPA="yr1ygpa";
    public static final String COL7_STUDYR2SEM1="yr2sem1";
    public static final String COL8_STUDYR2SEM2="yr2sem2";
    public static final String COLYR2_YGPA="yr2ygpa";
    public static final String COL9_STUDYR3SEM1="yr3sem1";
    public static final String COL10_STUDYR3SEM2="yr3sem2";
    public static final String COLYR3_YGPA="yr3ygpa";
    public static final String COL11_STUDYR4SEM1="yr4sem1";
    public static final String COL12_STUDYR4SEM2="yr4sem2";
    public static final String COLYR4_YGPA="yr4ygpa";
    public static final String COLFINAL_DGPA="finalmarks";

public String queryStudSemMarks= "create table " + TB_STUDMARKS + "("+COL1_STUDSEMID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,semroll TEXT NOT NULL,yr1sem1 TEXT,yr1sem2 TEXT,yr1ygpa TEXT,yr2sem1 Text,yr2sem2 TEXT,yr2ygpa TEXT,yr3sem1 TEXT,yr3sem2 TEXT,yr3ygpa TEXT,yr4sem1 TEXT,yr4sem2 TEXT,yr4ygpa TEXT,finalmarks TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TB_NAME + "(id integer primary key autoincrement,userName TEXT,userPassword TEXT,userques TEXT,userans TEXT)");
        db.execSQL(querystreamcreate);
        db.execSQL(querystudinfocreate);
        db.execSQL(querystudinfocreateImage);
        db.execSQL(queryStudSemMarks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TB_NAME);
        db.execSQL("drop table if exists " + TB_STREAM);
        db.execSQL("drop table if exists " + TB_STUDS);
        db.execSQL("drop table if exists " + TB_STUDSIMG);
        db.execSQL("drop table if exists " + TB_STUDMARKS);
        onCreate(db);
    }
//user registraion

    public Cursor viewAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TB_NAME, null);
        return res;
    }

    public boolean addUser(User userdb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_USERNAME, userdb.getName());
        contentValues.put(COL3_USERPASSWORD, userdb.getPassword());
        contentValues.put(COL4_USERQUES,userdb.getUserques());
        contentValues.put(COL5_USERANS,userdb.getUserans());
        long res = db.insert(TB_NAME, null, contentValues);
        db.close();
        if (res == -1)
            return false;
        else
            return true;
    }

    public boolean checkUser(String userdb) {
        String[] columns = {COL1_USERID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL2_USERNAME + "=?";
        String[] selectionArgs = {userdb};
        Cursor cursor = db.query(TB_NAME, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }



    String searchPass(String matchusername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME, null, "userName=?", new String[]{matchusername}, null, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex(COL3_USERPASSWORD));
            cursor.close();
        }
        return password;
    }

    String searchSecurityQues(String matchUsernameIfforgetpwd){
        String forgetques="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TB_NAME,null,"userName=?",new String[]{matchUsernameIfforgetpwd},null,null,null,null);
        if (cursor.getCount()<1){
            cursor.close();
            return "NOT EXIST";
        }
        else if (cursor.getCount()>=1 && cursor.moveToFirst()){
             forgetques=cursor.getString(cursor.getColumnIndex(COL4_USERQUES));
            cursor.close();

        }
        return forgetques;
    }

    String searchSecurityAns(String matchUserNameIfForgetPwd){
        String forgetans="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TB_NAME,null,"userName=?",new String[]{matchUserNameIfForgetPwd},null,null,null,null);
        if (cursor.getCount()<1){
            cursor.close();
            return "NOT EXIST";
        }
        else if (cursor.getCount()>=1 && cursor.moveToFirst()){
            forgetans=cursor.getString(cursor.getColumnIndex(COL5_USERANS));
            cursor.close();
        }
        return forgetans;
    }
//check existence of user when click forget pwd
    public boolean checkUserwiseIfForgetPwd(String UserExists) {
        String[] columns = {COL3_USERPASSWORD,COL4_USERQUES,COL5_USERANS};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL2_USERNAME + "=?";
        String[] selectionArgs = {UserExists};
        Cursor cursor = db.query(TB_NAME, columns, selection, selectionArgs, null, null, null);
        int cursorUserCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorUserCount > 0) {
            return true;//user exists
        }
        return false;// user don't exists
    }

    public boolean updateUser(String upname, String uppassword, String upid) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL1_USERID, upid);
        values.put(COL2_USERNAME, upname);
        values.put(COL3_USERPASSWORD, uppassword);

        // updating row
        int uptrows = db.update(TB_NAME, values, COL1_USERID + " = ?",
                new String[]{upid});
        db.close();
        //return true;

        return uptrows > 0;
    }


    public int deleteUser(String delid) {
        SQLiteDatabase db = this.getReadableDatabase();
        // delete user record by id
        int deleteditem = db.delete(TB_NAME, COL1_USERID + " = ?",
                new String[]{delid});
        db.close();
        return deleteditem;
    }

    //insert stream
    public void addStream(String streams) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentStream = new ContentValues();
        contentStream.put(COL2_STREAMNAME,streams);
        db.insert(TB_STREAM, null, contentStream);
        db.close();
    }

    /**
     * Getting all labels
     * returns list of labels
     */
    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>(10);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TB_STREAM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public boolean checkStream(String strmdb) {
        String[] columns = {COL2_STREAMNAME};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL2_STREAMNAME + "=?";
        String[] selectionArgs = {strmdb};
        Cursor cursor = db.query(TB_STREAM, columns, selection, selectionArgs, null, null, null);
         int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }

    public int deleteDBStream(String delstream) {
        SQLiteDatabase db = this.getReadableDatabase();
        // delete user record by id
        int deletedstrmitem = db.delete(TB_STREAM, COL2_STREAMNAME + " = ?",
                new String[]{delstream});
        db.close();
        return deletedstrmitem;
    }
    //delete student record if stream deleted
    public int deleteStudDataIfStrmDel(String studstrm) {
        SQLiteDatabase db=this.getReadableDatabase();
        int deletedstudentdelstrm= db.delete(TB_STUDS, COL2_STUDSTRM +" =?",new String[]{studstrm});
        db.close();
        return deletedstudentdelstrm;
    }
    //delete semmarks if sem deleted
    public int deleteSemIfdeleteStudDataIfStrmDel(String delstrm){
        SQLiteDatabase db=this.getReadableDatabase();
        String clause=COL4_STUDSEMROLL +" IN(SELECT roll FROM studentsTable where studsStrm=?)";
        int deletedsemdelstrm=db.delete(TB_STUDMARKS,clause,new String[]{delstrm});
        return deletedsemdelstrm;
    }
    public int deleteImageIfdeleteStudDataIfStrmDel(String delstrm){
        SQLiteDatabase db=this.getReadableDatabase();
        String clause=COL2_STUDIMGROLL +" IN(SELECT roll FROM studentsTable where studsStrm=?)";
        int deletedimagedelstrm=db.delete(TB_STUDSIMG,clause,new String[]{delstrm});
        return deletedimagedelstrm;
    }
//students records
public boolean addStudInfo(StudentDataProvider studdb) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL2_STUDSTRM, studdb.getStreams());
    contentValues.put(COL3_STUDYEAR, studdb.getRegYear());
    contentValues.put(COL4_STUDFNAME,studdb.getFname());
    contentValues.put(COL5_STUDLNAME,studdb.getLname());
    contentValues.put(COL6_STUDROLL,studdb.getRoll());
    contentValues.put(COL7_STUDEMAIL,studdb.getEmail());
    contentValues.put(COL8_STUDPHONE,studdb.getPhone());
    long resstud = db.insert(TB_STUDS, null, contentValues);
    db.close();
    if (resstud == -1)
        return false;
    else
        return true;
}
    public boolean checkStudRoll(String studroll) {
        String[] columns = {COL4_STUDFNAME};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL6_STUDROLL + "=?";
        String[] selectionArgs = {studroll};
        Cursor cursor = db.query(TB_STUDS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }
    public boolean checkStudEmail(String studemail) {
        String[] columns = {COL4_STUDFNAME};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL7_STUDEMAIL + "=?";
        String[] selectionArgs = {studemail};
        Cursor cursor = db.query(TB_STUDS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }
    public boolean checkStudPhone(String studphone) {
        String[] columns = {COL4_STUDFNAME};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL8_STUDPHONE + "=?";
        String[] selectionArgs = {studphone};
        Cursor cursor = db.query(TB_STUDS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }

    public Cursor  viewAll_List(SQLiteDatabase db){
       db=this.getReadableDatabase();
        Cursor res;

        String projection[]={COL1_STUDID,COL2_STUDSTRM,COL3_STUDYEAR,COL4_STUDFNAME,COL5_STUDLNAME,COL6_STUDROLL,COL7_STUDEMAIL,COL8_STUDPHONE};


        res=db.query(TB_STUDS,projection,null,null,null,null,null);

        return res;
    }
    //search fromlistview
    public Cursor  getStudentListByKeyword(String searchfname) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT _id as " +
                //KEY_ROWID + ","+
                COL1_STUDID + "," +
                COL2_STUDSTRM+ "," +
                COL3_STUDYEAR + "," +
                COL4_STUDFNAME + "," +
                COL5_STUDLNAME +","+
                COL6_STUDROLL+","+
                COL7_STUDEMAIL+","+
                COL8_STUDPHONE+
                " FROM " + TB_STUDS+
                " WHERE " +  COL4_STUDFNAME + "  LIKE  '%" +searchfname + "%' ";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    public Cursor  getStudentListByRoll(String searchRoll) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT _id as " +
                //KEY_ROWID + ","+
                COL1_STUDID + "," +
                COL2_STUDSTRM+ "," +
                COL3_STUDYEAR + "," +
                COL4_STUDFNAME + "," +
                COL5_STUDLNAME +","+
                COL6_STUDROLL+","+
                COL7_STUDEMAIL+","+
                COL8_STUDPHONE+
                " FROM " + TB_STUDS+
                " WHERE " +  COL6_STUDROLL + "  LIKE  '%" +searchRoll + "%' ";


        Cursor cursor1 = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor1 == null) {
            return null;
        } else if (!cursor1.moveToFirst()) {
            cursor1.close();
            return null;
        }
        return cursor1;

    }

    //update student table BY ID
    public boolean updateStudent(int id,String upststream, String upstyear, String upstfname,String upstlname,String upstroll,String upstemail,String upstphone) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL2_STUDSTRM, upststream);
        values.put(COL3_STUDYEAR, upstyear);
        values.put(COL4_STUDFNAME, upstfname);
        values.put(COL5_STUDLNAME,upstlname);
        values.put(COL6_STUDROLL,upstroll);
        values.put(COL7_STUDEMAIL,upstemail);
        values.put(COL8_STUDPHONE,upstphone);

       int upstrows=db.update(TB_STUDS,values,COL1_STUDID +" ="+id,null);
        db.close();


        return upstrows>0;
    }
    // Deleting record studdata from table by id
    public int deleteStudData(int studID) {
        SQLiteDatabase db=this.getReadableDatabase();
       int deletedstudent= db.delete(TB_STUDS, COL1_STUDID +" ="+studID,null);
        return deletedstudent;
    }

    //student image add
    public boolean addStudImg(StudentImageProvider studimgdb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_STUDIMGROLL,studimgdb.getImgroll());
        contentValues.put(COL3_STUDIMAGE,studimgdb.getImage());
        long resstudimg = db.insert(TB_STUDSIMG, null, contentValues);
        db.close();
        if (resstudimg == -1)
            return false;
        else
            return true;
    }

    public Cursor  viewAll_ListImage(SQLiteDatabase db){
        db=this.getReadableDatabase();
        Cursor resimg;

        String projection[]={COL1_STUDIMGID,COL2_STUDIMGROLL,COL3_STUDIMAGE};


        resimg=db.query(TB_STUDSIMG,projection,null,null,null,null,null);

        return resimg;
    }

    public Cursor getStudImg(String studimgroll){
        String columns[]={COL3_STUDIMAGE};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=COL2_STUDIMGROLL + "=?";
        String selectionargs[]={studimgroll};
        Cursor cursor=db.query(TB_STUDSIMG,columns,selection,selectionargs,null,null,null);
        if(cursor==null){
            return null;
        }
        else if(!cursor.moveToFirst()){
            cursor.close();
            return null;
        }
        return cursor;
    }
    public int deleteStudDataImage(String studimgRoll) {
        SQLiteDatabase db=this.getReadableDatabase();
        int deletedstudentimage= db.delete(TB_STUDSIMG, COL2_STUDIMGROLL +" ="+studimgRoll,null);
        return deletedstudentimage;
    }
    //checking image roll
    public boolean checkStudImageRoll(String studimgroll) {
        String[] columns = {COL3_STUDIMAGE};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL2_STUDIMGROLL + "=?";
        String[] selectionArgs = {studimgroll};
        Cursor cursor = db.query(TB_STUDSIMG, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }


public boolean addStudInfoSemMarks(SemMarksProvider semMarksProvider) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL4_STUDSEMROLL,semMarksProvider.getSemroll());
    contentValues.put(COL5_STUDYR1SEM1,semMarksProvider.getFirstsem1());
    contentValues.put(COL6_STUDYR1SEM2,semMarksProvider.getFirstsem2());
    contentValues.put(COLYR1_YGPA,semMarksProvider.getFirstYGPA());
    contentValues.put(COL7_STUDYR2SEM1,semMarksProvider.getSecondsem1());
    contentValues.put(COL8_STUDYR2SEM2, semMarksProvider.getSecondsem2());
    contentValues.put(COLYR2_YGPA, semMarksProvider.getSecondYGPA());
    contentValues.put(COL9_STUDYR3SEM1,semMarksProvider.getThirdsem1());
    contentValues.put(COL10_STUDYR3SEM2,semMarksProvider.getThirdsem2());
    contentValues.put(COLYR3_YGPA,semMarksProvider.getThirdYGPA());
    contentValues.put(COL11_STUDYR4SEM1,semMarksProvider.getFourthsem1());
    contentValues.put(COL12_STUDYR4SEM2,semMarksProvider.getFourthsem2());
    contentValues.put(COLYR4_YGPA,semMarksProvider.getFourthYGPA());
    contentValues.put(COLFINAL_DGPA,semMarksProvider.getFinalDGPA());
    long resstudmarks = db.insert(TB_STUDMARKS, null, contentValues);
    db.close();
    if (resstudmarks == -1)
        return false;
    else
        return true;
}
//update marks table by roll
public boolean updateStudentSemMarks(String upstSMroll,String upstSMyr1sem1,String upstSMyr1sem2,String upstSMyr1YGPA,String upstSMyr2sem1,String upstSMyr2sem2,String upstSMyr2YGPA,String upstSMyr3sem1,String upstSMyr3sem2,String upstSMyr3YGPA,String upstSMyr4sem1,String upstSMyr4sem2,String upstSMyr4YGPA,String upstSMfinalDGPA) {
    SQLiteDatabase db = this.getReadableDatabase();

    ContentValues values = new ContentValues();
    values.put(COL5_STUDYR1SEM1, upstSMyr1sem1);
    values.put(COL6_STUDYR1SEM2, upstSMyr1sem2);
    values.put(COLYR1_YGPA, upstSMyr1YGPA);
    values.put(COL7_STUDYR2SEM1,upstSMyr2sem1);
    values.put(COL8_STUDYR2SEM2,upstSMyr2sem2);
    values.put(COLYR2_YGPA,upstSMyr2YGPA);
    values.put(COL9_STUDYR3SEM1,upstSMyr3sem1);
    values.put(COL10_STUDYR3SEM2,upstSMyr3sem2);
    values.put(COLYR3_YGPA,upstSMyr3YGPA);
    values.put(COL11_STUDYR4SEM1,upstSMyr4sem1);
    values.put(COL12_STUDYR4SEM2,upstSMyr4sem2);
    values.put(COLYR4_YGPA,upstSMyr4YGPA);
    values.put(COLFINAL_DGPA,upstSMfinalDGPA);

    int upstSMrows=db.update(TB_STUDMARKS,values,COL4_STUDSEMROLL +" ="+upstSMroll,null);
    db.close();


    return upstSMrows>0;
}
//check sem stud roll wise
    public boolean checkStudSemRoll(String studSEMroll) {
        String[] columns = {COL5_STUDYR1SEM1,COL6_STUDYR1SEM2,COLYR1_YGPA,COL7_STUDYR2SEM1,COL8_STUDYR2SEM2,COLYR2_YGPA,COL9_STUDYR3SEM1,COL10_STUDYR3SEM2,COLYR3_YGPA,COL11_STUDYR4SEM1,COL12_STUDYR4SEM2,COLYR4_YGPA,COLFINAL_DGPA};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL4_STUDSEMROLL + "=?";
        String[] selectionArgs = {studSEMroll};
        Cursor cursor = db.query(TB_STUDMARKS, columns, selection, selectionArgs, null, null, null);
        int cursorSMCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorSMCount > 0) {
            return true;//duplicate
        }
        return false;// record will insert
    }
//delete sem marks by roll
    public int deleteStudSEMMarks(String studSEMRoll) {
        SQLiteDatabase db=this.getReadableDatabase();
        int deletedSemMarksOfstudent= db.delete(TB_STUDMARKS, COL4_STUDSEMROLL +" ="+studSEMRoll,null);
        return deletedSemMarksOfstudent;
    }
    //fetch roll exists of marks
    public Cursor getStudSemRoll(String studsemroll){
        String columns[]={COL5_STUDYR1SEM1,COL6_STUDYR1SEM2,COLYR1_YGPA,COL7_STUDYR2SEM1,COL8_STUDYR2SEM2,COLYR2_YGPA,COL9_STUDYR3SEM1,COL10_STUDYR3SEM2,COLYR3_YGPA,COL11_STUDYR4SEM1,COL12_STUDYR4SEM2,COLYR4_YGPA,COLFINAL_DGPA};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=COL4_STUDSEMROLL + "=?";
        String selectionargs[]={studsemroll};
        Cursor cursor=db.query(TB_STUDMARKS,columns,selection,selectionargs,null,null,null);
        if(cursor==null){
            return null;
        }
        else if(!cursor.moveToFirst()){
            cursor.close();
            return null;
        }
        return cursor;
    }


    public static String getDbName(){
      return DB_NAME;
    }

}
