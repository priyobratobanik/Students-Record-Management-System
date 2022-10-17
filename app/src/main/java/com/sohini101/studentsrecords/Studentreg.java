package com.sohini101.studentsrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Studentreg extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText et1,etfname,etlname,etregroll,etemail,etphone;
    private TextView tv,tvfname,tvlname,tvregroll,tvemail,tvphone;
    private Button btn,bStud,bdelstrm,bshowstud;
    private   String namestrm;
    private UserDatbase dbstrm;
    private UserDatbase mydbstud;
    private EmailPhoneValidator emailPhoneValidator;
    SharedPreferences sharedPreferences;



    ArrayAdapter<String> dataAdapterstrm;
    List<String> lablesstrm;
    Spinner spin;
    Spinner spinyr;
    ListView listView;
    StudentDataProvider dataProvider;

    private String year;
    private String stream;
    public static final String STORAGE_NAME = "MYSharedPreferences";
    protected void setUpYear(){
        Resources resource = getResources();
        String[] deptArray = resource.getStringArray(R.array.student_class);
        List<String> list2 = new ArrayList<String>(Arrays.asList(deptArray));
        for (int i=0;i<list2.size();i++){
            if(list2.get(i).equals(year)){
                ((Spinner)findViewById(R.id.spinyr)).setSelection(i);
            }
        }

    }
    protected void setUpStream(){

        UserDatbase dbstrm=new UserDatbase(Studentreg.this,null,null,3);
        List<String> lablesstrm = dbstrm.getAllLabels();
        for (int j=0;j<lablesstrm.size();j++){
            if(lablesstrm.get(j).equals(stream)){
                ((Spinner)findViewById(R.id.spin)).setSelection(j);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentreg);
        year="";
        stream="";
        et1=(EditText)findViewById(R.id.et1);
        tv=(TextView)findViewById(R.id.tv);
        btn=(Button)findViewById(R.id.btn);
        bdelstrm=(Button)findViewById(R.id.bdelstrm);
        spin=(Spinner)findViewById(R.id.spin);

        spinyr=(Spinner)findViewById(R.id.spinyr);

        etfname=(EditText)findViewById(R.id.etfname);
        etlname=(EditText)findViewById(R.id.etlname);
        etregroll=(EditText)findViewById(R.id.etregroll);
        etemail=(EditText)findViewById(R.id.etemail);
        etphone=(EditText)findViewById(R.id.etphone);
        tvfname=(TextView)findViewById(R.id.tvfname);
        tvlname=(TextView)findViewById(R.id.tvlname);
        tvregroll=(TextView)findViewById(R.id.tvregroll);
        tvemail=(TextView)findViewById(R.id.tvemail);
        tvphone=(TextView)findViewById(R.id.tvphone);
        bStud=(Button) findViewById(R.id.bStud);
        bshowstud=(Button)findViewById(R.id.bshowstud);
        listView=(ListView)findViewById(R.id.listview);
        customYear();
        spinyr.setOnItemSelectedListener(Studentreg.this);


        loadSpinnerData();
        spin.setOnItemSelectedListener(Studentreg.this);
//--


       /* */
        //loading saved data

        sharedPreferences = getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        year = sharedPreferences.getString("studyear", "");
        stream=sharedPreferences.getString("studstream","");
        setUpYear();

        setUpStream();

        deletestream();
        emailPhoneValidator=new EmailPhoneValidator();

        etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (! emailPhoneValidator.validateEmail(etemail.getText().toString()) ) {
                    etemail.setError("invalid");

                }
                else { etemail.setError(null);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //--
        etphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (! emailPhoneValidator.validatePhone(etphone.getText().toString()) ) {
                    etphone.setError("invalid");

                }
                else { etphone.setError(null);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mydbstud=new UserDatbase(Studentreg.this,null,null,3);
    }

    public void populate(View v){

        namestrm=et1.getText().toString();
        if(namestrm.trim().length()>0){
            et1.setError(null);
            UserDatbase dbstrm=new UserDatbase(Studentreg.this,null,null,3);
            if(!dbstrm.checkStream(namestrm)) {
                dbstrm.addStream(namestrm);
                Toast.makeText(Studentreg.this, namestrm + " added", Toast.LENGTH_SHORT).show();
                et1.setText("");


                // loading spinner with newly added data
                loadSpinnerData();
            }
            else {
                Toast.makeText(getApplicationContext(), "duplicate found",
                        Toast.LENGTH_SHORT).show();
                et1.setError("duplicate");
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter label name",
                    Toast.LENGTH_SHORT).show();
            et1.setError("blank");
        }
    }
    public void loadSpinnerData(){
        UserDatbase dbstrm=new UserDatbase(Studentreg.this,null,null,3);
        // Spinner Drop down elements
        lablesstrm = dbstrm.getAllLabels();
        // Creating adapter for spinner
        dataAdapterstrm = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lablesstrm);
        // Drop down layout style - list view with radio button
        dataAdapterstrm
                .setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        //refresh;
        dataAdapterstrm.notifyDataSetChanged();
        // attaching data adapter to spinner
        spin.setAdapter(dataAdapterstrm);


    }
    public void customYear(){
        spinyr=(Spinner)findViewById(R.id.spinyr);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.student_class, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinyr.setAdapter(adapter);

    }

    //
    public void deletestream(){
        bdelstrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   int pos=spin.getSelectedItemPosition();

                if(pos>-1) {
                    showConfirmDialog();
                }
                else {
                    Toast.makeText(Studentreg.this,"no data found",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showConfirmDialog(){
        final   int pos=spin.getSelectedItemPosition();
        final String delstrm=lablesstrm.get(pos);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Studentreg.this);
        alertdialog.setIcon(R.mipmap.questionmark);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Would you really like to delete the stream? if stream is deleted,then all the students records will be deleted having stream "+delstrm);
        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //loadSpinnerData();
                UserDatbase dbstrm=new UserDatbase(Studentreg.this,null,null,3);
                Integer deletedStrms = dbstrm.deleteDBStream(delstrm);
                if(deletedStrms>0){

                    dataAdapterstrm.remove(lablesstrm.get(pos));
                    //refresh
                    dataAdapterstrm.notifyDataSetChanged();

                    Toast.makeText(Studentreg.this,delstrm+" Deleted",Toast.LENGTH_SHORT).show();
                    //delete sem marks of stud roll where strm=delstrm
                    Integer delsemofdelstrm=dbstrm.deleteSemIfdeleteStudDataIfStrmDel(delstrm);
                    if (delsemofdelstrm>0){
                        Toast.makeText(Studentreg.this,String.valueOf(delsemofdelstrm)+" Students marks having stream "+delstrm+" Deleted",Toast.LENGTH_SHORT).show();
                    }
                    //delete image of stud roll where strm=delstrm
                    Integer delimageofdelstrm=dbstrm.deleteImageIfdeleteStudDataIfStrmDel(delstrm);
                    if (delimageofdelstrm>0){
                        Toast.makeText(Studentreg.this,String.valueOf(delimageofdelstrm)+" Students images having stream "+delstrm+" Deleted",Toast.LENGTH_SHORT).show();
                    }
                    //delete student of stud roll where strm=delstrm
                    Integer delstudofdelstrm=dbstrm.deleteStudDataIfStrmDel(delstrm);
                    if (delstudofdelstrm>0){
                        Toast.makeText(Studentreg.this,String.valueOf(delstudofdelstrm)+" Students having stream "+delstrm+" Deleted",Toast.LENGTH_SHORT).show();
                    }


                    deleted(delstrm);}

                else
                {
                    Toast.makeText(getApplicationContext(),"not deleted",Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdialog.create().show();



    }
    private void deleted(String delstrm){
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(Studentreg.this);
        alertdialog.setTitle("INFO");
        alertdialog.setMessage("Stream " +delstrm+" has been deleted");
        alertdialog.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertdialog.create().show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        adapterView.getItemAtPosition(position);
        switch (adapterView.getId()){
            case R.id.spin:
                stream=(String) spin.getItemAtPosition(position);

                break;
            case R.id.spinyr:

                year=(String)spinyr.getItemAtPosition(position);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //saving stud record
    public void afterSelection(View v){

        Resources resource = getResources();
        String[] deptArray = resource.getStringArray(R.array.student_class);
        int posyr=spinyr.getSelectedItemPosition();
        String studentyr=deptArray[posyr];
        String fname=etfname.getText().toString().trim();
        String lname=etlname.getText().toString().trim();
        String regroll=etregroll.getText().toString().trim();
        String email=etemail.getText().toString().trim();
        String phone=etphone.getText().toString().trim();
        int posstrm=spin.getSelectedItemPosition();
        if (!isBlankStud(fname,lname,regroll,email,phone)) { //if false ,return stops execution of the method.
            return;
        }
        if(!emailPhoneValidator.validateEmail(email)){
            Toast.makeText(Studentreg.this,"enter valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!emailPhoneValidator.validatePhone(phone)){
            Toast.makeText(Studentreg.this,"enter valid Indian mobile number",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String studentstrm = lablesstrm.get(posstrm);


            dataProvider = new StudentDataProvider(studentstrm, studentyr, fname, lname, regroll, email, phone);



            if (!mydbstud.checkStudRoll(regroll) && !mydbstud.checkStudEmail(email) && !mydbstud.checkStudPhone(phone)) {
                dataProvider.setStreams(studentstrm);
                dataProvider.setRegYear(studentyr);
                dataProvider.setFname(fname);
                dataProvider.setLname(lname);
                dataProvider.setRoll(regroll);
                dataProvider.setEmail(email);
                dataProvider.setPhone(phone);
                boolean resstud = mydbstud.addStudInfo(dataProvider);

                if (resstud == true) {
                    SharedPreferences sharedPreferences = getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("studyear", year);
                    editor.putString("studstream", stream);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "choice of stream and year have been saved in SharedPreferences!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Studentreg.this, "Successfully inserted", Toast.LENGTH_SHORT).show();

                }
                clear();
            }



            else{
                Toast.makeText(Studentreg.this, "Duplicate record", Toast.LENGTH_SHORT).show();
                if (mydbstud.checkStudRoll(regroll)) {
                    etregroll.setError("duplicate");
                }
                if (mydbstud.checkStudEmail(email)) {
                    etemail.setError("duplicate");
                }
                if (mydbstud.checkStudPhone(phone)) {
                    etphone.setError("duplicate");
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(Studentreg.this,"First add Stream",Toast.LENGTH_SHORT).show();
            et1.setError("blank");
        }

    }

    public void viewList(View vw){

        Intent intent=new Intent(Studentreg.this,StudentDataListActivity.class);

        startActivity(intent);

    }
    public boolean isBlankStud(String fname,String lname,String regroll,String email,String phone){
        fname=etfname.getText().toString().trim();
        lname=etlname.getText().toString().trim();
        regroll=etregroll.getText().toString().trim();
        email=etemail.getText().toString().trim();
        phone=etphone.getText().toString().trim();
        if(fname.isEmpty()|| lname.isEmpty()||regroll.isEmpty()||email.isEmpty()||phone.isEmpty()) {
            if (fname.isEmpty()) {
                etfname.setError("blank");

            }
            if (lname.isEmpty()) {
                etlname.setError("blank");
            }
            if (regroll.isEmpty()) {
                etregroll.setError("blank");
            }
            if (email.isEmpty()) {
                etemail.setError("blank");
            }
            if (phone.isEmpty()) {
                etphone.setError("blank");
            }
            return false;
        }
        return true;
    }
    private void clear() {
        etfname.setText(null);
        etlname.setText(null);
        etregroll.setText(null);
        etemail.setText(null);
        etphone.setText(null);
        etemail.setError(null);
        etphone.setError(null);
    }

}
