package com.sohini101.studentsrecords;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SOHINI PAL on 09-01-2018.
 */

public class fragmentSem extends Fragment implements TextWatcher,TextView.OnEditorActionListener{
private View view;
private TextView etmrstrm,etmryear,etmrroll,tvres1ygpa,tvres2ygpa,tvres3ygpa,tvres4ygpa,tvfinaldgpa;
private EditText etmr1sm1,etmr1sm2,etmr2sm1,etmr2sm2,etmr3sm1,etmr3sm2,etmr4sm1,etmr4sm2;
private Button bsemadd,bsemupt,bsemdel;
private String getArgumentStringRoll,getArgumentStringStrm,getArgumentStringYr;
private String firstsem1,firstsem2,secondsem1,secondsem2,thirdsem1,thirdsem2,fourthsem1,fourthsem2;
private float i1sem1,i1sem2,i2sem1,i2sem2,i3sem1,i3sem2,i4sem1,i4sem2;
private String firstYgpa,secondYgpa,thirdYgpa,fourthYgpa,finalYgpa;
private SemMarksProvider semMarksProvider;
private UserDatbase mydbstudsemmarks;
private Cursor cursor;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmentsem, container, false);
        etmrstrm=(TextView)view.findViewById(R.id.etmrstrm);
        etmryear=(TextView)view.findViewById(R.id.etmryear);
        etmrroll=(TextView)view.findViewById(R.id.etmrroll);
        etmr1sm1=(EditText)view.findViewById(R.id.etmr1sm1);
        etmr1sm2=(EditText)view.findViewById(R.id.etmr1sm2);
        tvres1ygpa=(TextView)view.findViewById(R.id.tvres1ygpa);
        etmr2sm1=(EditText)view.findViewById(R.id.etmr2sm1);
        etmr2sm2=(EditText)view.findViewById(R.id.etmr2sm2);
        tvres2ygpa=(TextView)view.findViewById(R.id.tvres2ygpa);
        etmr3sm1=(EditText)view.findViewById(R.id.etmr3sm1);
        etmr3sm2=(EditText)view.findViewById(R.id.etmr3sm2);
        tvres3ygpa=(TextView)view.findViewById(R.id.tvres3ygpa);
        etmr4sm1=(EditText)view.findViewById(R.id.etmr4sm1);
        etmr4sm2=(EditText)view.findViewById(R.id.etmr4sm2);
        tvres4ygpa=(TextView)view.findViewById(R.id.tvres4ygpa);
        tvfinaldgpa=(TextView)view.findViewById(R.id.tvfinaldgpa);
        bsemadd=(Button)view.findViewById(R.id.bsemadd);
        bsemupt=(Button)view.findViewById(R.id.bsemupt);
        bsemdel=(Button)view.findViewById(R.id.bsemdel);
        getArgumentStringRoll = getArguments().getString("dataroll1");
        etmrroll.setText(getArgumentStringRoll);
        getArgumentStringStrm=getArguments().getString("datastrm");
        etmrstrm.setText(getArgumentStringStrm);
        getArgumentStringYr=getArguments().getString("datayr");
        etmryear.setText(getArgumentStringYr);
        etmr1sm1.addTextChangedListener(this);
        etmr1sm2.addTextChangedListener(this);
        etmr2sm1.addTextChangedListener(this);
        etmr2sm2.addTextChangedListener(this);
        etmr3sm1.addTextChangedListener(this);
        etmr3sm2.addTextChangedListener(this);
        etmr4sm1.addTextChangedListener(this);
        etmr4sm2.addTextChangedListener(this);
        etmr1sm1.setFocusableInTouchMode(true);
        etmr1sm1.requestFocus();
        etmr1sm2.setFocusableInTouchMode(true);
        etmr1sm2.requestFocus();
        etmr2sm1.setFocusableInTouchMode(true);
        etmr2sm2.requestFocus();
        etmr3sm1.setFocusableInTouchMode(true);
        etmr3sm2.requestFocus();
        etmr4sm1.setFocusableInTouchMode(true);
        etmr4sm2.requestFocus();
        etmr1sm2.setOnEditorActionListener(this);
        etmr2sm2.setOnEditorActionListener(this);
        etmr3sm2.setOnEditorActionListener(this);
        etmr4sm2.setOnEditorActionListener(this);
        mydbstudsemmarks=new UserDatbase(getActivity(),null,null,3);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String checksemroll=etmrroll.getText().toString();
                mydbstudsemmarks=new UserDatbase(getActivity(),null,null,3);
               cursor=mydbstudsemmarks.getStudSemRoll(checksemroll);
               if(cursor==null){
                   Toast.makeText(getActivity(),"no academic record found",Toast.LENGTH_SHORT).show();
               }
               else {
                   String showyr1sem1=cursor.getString(cursor.getColumnIndex(UserDatbase.COL5_STUDYR1SEM1));
                   etmr1sm1.setText(showyr1sem1);
                   String showyr1sem2=cursor.getString(cursor.getColumnIndex(UserDatbase.COL6_STUDYR1SEM2));
                   etmr1sm2.setText(showyr1sem2);
                   String show1stYGPA=cursor.getString(cursor.getColumnIndex(UserDatbase.COLYR1_YGPA));
                   tvres1ygpa.setText(show1stYGPA);
                   String showyr2sem1=cursor.getString(cursor.getColumnIndex(UserDatbase.COL7_STUDYR2SEM1));
                   etmr2sm1.setText(showyr2sem1);
                   String showyr2sem2=cursor.getString(cursor.getColumnIndex(UserDatbase.COL8_STUDYR2SEM2));
                   etmr2sm2.setText(showyr2sem2);
                   String show2ndYGPA=cursor.getString(cursor.getColumnIndex(UserDatbase.COLYR2_YGPA));
                   tvres2ygpa.setText(show2ndYGPA);
                   String showyr3sem1=cursor.getString(cursor.getColumnIndex(UserDatbase.COL9_STUDYR3SEM1));
                   etmr3sm1.setText(showyr3sem1);
                   String showyr3sem2=cursor.getString(cursor.getColumnIndex(UserDatbase.COL10_STUDYR3SEM2));
                   etmr3sm2.setText(showyr3sem2);
                   String show3rdYGPA=cursor.getString(cursor.getColumnIndex(UserDatbase.COLYR3_YGPA));
                   tvres3ygpa.setText(show3rdYGPA);
                   String showyr4sem1=cursor.getString(cursor.getColumnIndex(UserDatbase.COL11_STUDYR4SEM1));
                   etmr4sm1.setText(showyr4sem1);
                   String showyr4sem2=cursor.getString(cursor.getColumnIndex(UserDatbase.COL12_STUDYR4SEM2));
                   etmr4sm2.setText(showyr4sem2);
                   String show4thYGPA=cursor.getString(cursor.getColumnIndex(UserDatbase.COLYR4_YGPA));
                   tvres4ygpa.setText(show4thYGPA);
                   String showFinalDGPA=cursor.getString(cursor.getColumnIndex(UserDatbase.COLFINAL_DGPA));
                   tvfinaldgpa.setText(showFinalDGPA);
                   View v=view.findViewById(R.id.bsemadd);
                   v.setVisibility(View.GONE);
               }
            }
        });

        //custom toast in fragment

       Context context = getActivity();
        LayoutInflater toastinflater = getActivity().getLayoutInflater();

        View customToastroot = toastinflater.inflate(R.layout.mycustomsem_toast, null);

        Toast customtoast = new Toast(context);

        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.show();
        semMarksAdd();
        updateSemMarks();
        deleteSemMarks();
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable editable) {
        firstsem1=etmr1sm1.getText().toString();
        firstsem2=etmr1sm2.getText().toString();
        secondsem1=etmr2sm1.getText().toString();
        secondsem2=etmr2sm2.getText().toString();
        thirdsem1=etmr3sm1.getText().toString();
        thirdsem2=etmr3sm2.getText().toString();
        fourthsem1=etmr4sm1.getText().toString();
        fourthsem2=etmr4sm2.getText().toString();
        try {
            float no =Float.parseFloat(editable.toString());
            if(no>10){
                editable.replace(0,editable.length(),"10");
            }

        } catch (NumberFormatException e){
            Toast.makeText(getActivity(),"input can not be greater than 10",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_DONE){

                check();

            return true;
        }
        return false;
    }

    public void check(){
        String s1sem1=etmr1sm1.getText().toString();
        String s1sem2=etmr1sm2.getText().toString();
        String s2sem1=etmr2sm1.getText().toString();
        String s2sem2=etmr2sm2.getText().toString();
        String s3sem1=etmr3sm1.getText().toString();
        String s3sem2=etmr3sm2.getText().toString();
        String s4sem1=etmr4sm1.getText().toString();
        String s4sem2=etmr4sm2.getText().toString();
        try {

            i1sem1=Float.parseFloat(s1sem1);
            i1sem2=Float.parseFloat(s1sem2);
            i2sem1=Float.parseFloat(s2sem1);
            i2sem2=Float.parseFloat(s2sem2);
            i3sem1=Float.parseFloat(s3sem1);
            i3sem2=Float.parseFloat(s3sem2);
            i4sem1=Float.parseFloat(s4sem1);
            i4sem2=Float.parseFloat(s4sem2);
        }catch (NumberFormatException e){

        }


        firstYgpa = Float.toString((i1sem1 + i1sem2) / 2);
        secondYgpa=Float.toString((i2sem1+i2sem2)/2);
        thirdYgpa=String.valueOf((i3sem1+i3sem2)/2);
        fourthYgpa=String.valueOf((i4sem1+i4sem2)/2);
        finalYgpa=String.valueOf((i1sem1+i1sem2+i2sem1+i2sem2+i3sem1+i3sem2+i4sem1+i4sem2)/8);
        if(!s1sem2.isEmpty()) {
            tvres1ygpa.setText(firstYgpa);
        }
        if (!s2sem2.isEmpty()) {
            tvres2ygpa.setText(secondYgpa);
        }
        if(!s3sem2.isEmpty()) {
            tvres3ygpa.setText(thirdYgpa);
        }
        if(!s4sem2.isEmpty()) {
            tvres4ygpa.setText(fourthYgpa);
            tvfinaldgpa.setText(finalYgpa);
        }
    }

    public void semMarksAdd(){
        bsemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        String mrroll=etmrroll.getText().toString();
                        String s1sem1=etmr1sm1.getText().toString();
                        String s1sem2=etmr1sm2.getText().toString();
                        String firstYGPA=tvres1ygpa.getText().toString();
                        String s2sem1=etmr2sm1.getText().toString();
                        String s2sem2=etmr2sm2.getText().toString();
                        String secondYGPA=tvres2ygpa.getText().toString();
                        String s3sem1=etmr3sm1.getText().toString();
                        String s3sem2=etmr3sm2.getText().toString();
                        String thirdYGPA=tvres3ygpa.getText().toString();
                        String s4sem1=etmr4sm1.getText().toString();
                        String s4sem2=etmr4sm2.getText().toString();
                        String fourthYGPA=tvres4ygpa.getText().toString();
                        String finalDGPA=tvfinaldgpa.getText().toString();
                        if(s1sem1.isEmpty()&& s1sem2.isEmpty()&& s2sem1.isEmpty()&& s2sem2.isEmpty()&& s3sem1.isEmpty() && s3sem2.isEmpty() && s4sem1.isEmpty() && s4sem2.isEmpty()){
                            Toast.makeText(getActivity(),"Please enter atleast one sem marks to add into the database",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(mydbstudsemmarks.checkStudSemRoll(mrroll)){
                            Toast.makeText(getActivity(),"click on update",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        semMarksProvider=new SemMarksProvider(mrroll,s1sem1,s1sem2,firstYGPA,s2sem1,s2sem2,secondYGPA,s3sem1,s3sem2,thirdYGPA,s4sem1,s4sem2,fourthYGPA,finalDGPA);
                        semMarksProvider.setSemroll(mrroll);
                        semMarksProvider.setFirstsem1(s1sem1);
                        semMarksProvider.setFirstsem2(s1sem2);
                        semMarksProvider.setFirstYGPA(firstYGPA);
                        semMarksProvider.setSecondsem1(s2sem1);
                        semMarksProvider.setSecondsem2(s2sem2);
                        semMarksProvider.setSecondYGPA(secondYGPA);
                        semMarksProvider.setThirdsem1(s3sem1);
                        semMarksProvider.setThirdsem2(s3sem2);
                        semMarksProvider.setThirdYGPA(thirdYGPA);
                        semMarksProvider.setFourthsem1(s4sem1);
                        semMarksProvider.setFourthsem2(s4sem2);
                        semMarksProvider.setFourthYGPA(fourthYGPA);
                        semMarksProvider.setFinalDGPA(finalDGPA);
                        boolean ressemmarks=mydbstudsemmarks.addStudInfoSemMarks(semMarksProvider);
                        if(ressemmarks==true){
                            Toast.makeText(getActivity(),"successfully inserted sem marks of "+mrroll,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"not inserted",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void updateSemMarks(){
        bsemupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        String umrroll=etmrroll.getText().toString();
                        String us1sem1=etmr1sm1.getText().toString();
                        String us1sem2=etmr1sm2.getText().toString();
                        String ufirstYGPA=tvres1ygpa.getText().toString();
                        String us2sem1=etmr2sm1.getText().toString();
                        String us2sem2=etmr2sm2.getText().toString();
                        String usecondYGPA=tvres2ygpa.getText().toString();
                        String us3sem1=etmr3sm1.getText().toString();
                        String us3sem2=etmr3sm2.getText().toString();
                        String uthirdYGPA=tvres3ygpa.getText().toString();
                        String us4sem1=etmr4sm1.getText().toString();
                        String us4sem2=etmr4sm2.getText().toString();
                        String ufourthYGPA=tvres4ygpa.getText().toString();
                        String ufinalDGPA=tvfinaldgpa.getText().toString();
                        if(us1sem1.isEmpty()&& us1sem2.isEmpty()&& us2sem1.isEmpty()&& us2sem2.isEmpty()&& us3sem1.isEmpty() && us3sem2.isEmpty() && us4sem1.isEmpty() && us4sem2.isEmpty()){
                            Toast.makeText(getActivity(),"Please enter atleast one sem marks to update into the database",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!mydbstudsemmarks.checkStudSemRoll(umrroll)){
                            return;
                        }
                        boolean uptressemmarks=mydbstudsemmarks.updateStudentSemMarks(umrroll,us1sem1,us1sem2,ufirstYGPA,us2sem1,us2sem2,usecondYGPA,us3sem1,us3sem2,uthirdYGPA,us4sem1,us4sem2,ufourthYGPA,ufinalDGPA);
                        if(uptressemmarks==true){
                            Toast.makeText(getActivity(),"sem marks updated",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getActivity(),"data not updated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void deleteSemMarks(){
        bsemdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String delmrroll=etmrroll.getText().toString();
                if(!mydbstudsemmarks.checkStudSemRoll(delmrroll)){
                    return;
                }
                final AlertDialog.Builder builderRemove=new AlertDialog.Builder(getActivity());
                builderRemove.setTitle("Remove SEM MARKS OF? "+delmrroll);
                builderRemove.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final  UserDatbase mydbstudsemmarks = new UserDatbase(getActivity(), null, null, 3);
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Integer deletedStudSemMarks = mydbstudsemmarks.deleteStudSEMMarks(delmrroll);
                                if (deletedStudSemMarks> 0) {

                                    //refresh
                                    Toast.makeText(getActivity(), "marks of universtity roll" + delmrroll + " Deleted", Toast.LENGTH_SHORT).show();
                                    final AlertDialog.Builder alertdialog3=new AlertDialog.Builder(getActivity());
                                    alertdialog3.setTitle("INFO");
                                    alertdialog3.setCancelable(false);
                                    alertdialog3.setMessage("Record of student having university roll no: " +delmrroll+" has been deleted");
                                    alertdialog3.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                                      //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // FadingListView fadingListView = new FadingListView();
                                            etmr1sm1.setText("");
                                            etmr1sm2.setText("");
                                            tvres1ygpa.setText("");
                                            etmr2sm1.setText("");
                                            etmr2sm2.setText("");
                                            tvres2ygpa.setText("");
                                            etmr3sm1.setText("");
                                            etmr3sm2.setText("");
                                            tvres3ygpa.setText("");
                                            etmr4sm1.setText("");
                                            etmr4sm2.setText("");
                                            tvres4ygpa.setText("");
                                            tvfinaldgpa.setText("");


                                            dialog.dismiss();
                                        }
                                    });
                                    alertdialog3.create().show();


                                } else
                                    Toast.makeText(getActivity(), "marks not deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }
                });
                builderRemove.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderRemove.create().show();
            }
        });
    }

}

