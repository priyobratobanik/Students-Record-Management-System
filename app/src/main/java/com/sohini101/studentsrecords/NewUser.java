package com.sohini101.studentsrecords;

import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NewUser extends AppCompatActivity implements View.OnClickListener{
    private EditText etregpass,etreguser;
    private TextView tv1,tv2;
    private Button breg,bview,bedit,bupdate,bdelete;
    private UserDatbase mydbreg;
    private RadioGroup rgsecurity;
    private RadioButton rb;
    private   EditText etsurepass,et4,etanswr;
    private UserSessionManager checkloginstatussession;
    private    User user;
    private String selectedrbques;
    private String rbanswr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        initreg();
        et4.setEnabled(false);
        bupdate.setEnabled(false);
        bdelete.setEnabled(false);
        initListeners();
        initObjects();
        confirmpasstextwatcher();
        rollEnable();
        checkloginstatussession=new UserSessionManager(getApplicationContext());
    }
    private void initObjects(){
        user=new User();
        mydbreg=new UserDatbase(NewUser.this, null, null, 3);
    }
    private void initreg(){
        etregpass=(EditText)findViewById(R.id.etregpass);
        etreguser=(EditText)findViewById(R.id.etreguser);
        etsurepass=(EditText)findViewById(R.id.etsurepass);
        et4=(EditText)findViewById(R.id.et4);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        breg=(Button)findViewById(R.id.breg);
        bview=(Button)findViewById(R.id.bview);
        bedit=(Button)findViewById(R.id.bedit);
        bupdate=(Button)findViewById(R.id.bupdate);
        bdelete=(Button)findViewById(R.id.bdelete);
        rgsecurity=(RadioGroup)findViewById(R.id.rgsecurity);
        etanswr=(EditText)findViewById(R.id.etanswr);

    }
    private void initListeners(){
        breg.setOnClickListener(this);
    }
    public void viewuser(View view){
        Cursor res=mydbreg.viewAll();
        if(res.getCount()==0){
            showMsg("Error","No Record");
            return;
        }else{
            StringBuilder b=new StringBuilder();
            while(res.moveToNext()){
                b.append("id "+res.getString(0)+"\n");
                b.append("userName "+res.getString(1)+"\n");
                b.append("userPassword "+res.getString(2)+"\n");

            }
            showMsg("Record",b.toString());
        }
    }
    public void showMsg(String title, String msg){
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(title);
        b.setMessage(msg);
        b.show();
    }
    public boolean isConfirmPassword(){
        String regpass=etregpass.getText().toString().trim();
        String confirmpass=etsurepass.getText().toString().trim();
        if(regpass.isEmpty()){
            etsurepass.setError("!put password");
            return false;
        }
        if(!regpass.contentEquals(confirmpass)){
            etsurepass.setError("!wrong");
            return false;
        }



        else { if (regpass.contentEquals(confirmpass)) {
            etsurepass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right, 0, 0, 0);
        }
        }

        return true;
    }
    public void confirmpasstextwatcher() {
        etsurepass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isConfirmPassword();
                boolean flag=isConfirmPassword();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void rbclick(View view){
        int rb_id=rgsecurity.getCheckedRadioButtonId();
        rb=(RadioButton)findViewById(rb_id);
        selectedrbques=rb.getText().toString();
        View dispanswr=findViewById(R.id.etanswr);
        dispanswr.setVisibility(View.VISIBLE);

    }
    public boolean isBlank(){
        String user=etreguser.getText().toString().trim();
        String pass=etregpass.getText().toString().trim();
        if(user.isEmpty()|| pass.isEmpty()) {
            if (user.isEmpty()) {
                etreguser.setError("blank");

            }
            if (pass.isEmpty()) {
                etregpass.setError("blank");
            }
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.breg:
                postDataToSqlite();
                break;
        }
    }
    public void postDataToSqlite() {
        if (!isBlank()) { //if false ,return stops execution of the method.
            return;
        }
        if (!isConfirmPassword()) {
            return;
        }

        rgsecurity=(RadioGroup)findViewById(R.id.rgsecurity);
        int rb_id = rgsecurity.getCheckedRadioButtonId();
        if (rb_id==-1){
            Toast.makeText(NewUser.this, "select question", Toast.LENGTH_SHORT).show();
            return;
        }
        rb = (RadioButton) findViewById(rb_id);
        selectedrbques = rb.getText().toString();
        rbanswr = etanswr.getText().toString();

        if (selectedrbques.isEmpty()) {
            Toast.makeText(NewUser.this, "select question", Toast.LENGTH_SHORT).show();
            return;
        }


        if (rbanswr.isEmpty()) {
            etanswr.setError("blank");
            return;
        }



        if (!mydbreg.checkUser(etreguser.getText().toString().trim())) {
            user.setName(etreguser.getText().toString().trim());
            user.setPassword(etregpass.getText().toString().trim());
            user.setUserques(selectedrbques);
            user.setUserans(rbanswr);
            boolean res = mydbreg.addUser(user);
            if (res == true) {
                Toast.makeText(NewUser.this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                //if first time login,then after registration user will redirect to login page
                if(checkloginstatussession.checkLogin())
                    finish();

            }
            clear();
        }else {
            Toast.makeText(NewUser.this, "Duplicate record", Toast.LENGTH_SHORT).show();
            etreguser.setError("duplicate");
        }

    }
    public void rollEnable(){
        bedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewshowUpt=findViewById(R.id.bupdate);
                viewshowUpt.setVisibility(View.VISIBLE);
                final View viewshowDel=findViewById(R.id.bdelete);
                viewshowDel.setVisibility(View.VISIBLE);
                final View viewId=findViewById(R.id.et4);
                viewId.setVisibility(View.VISIBLE);
                final View vradiogrp=findViewById(R.id.rgsecurity);
                vradiogrp.setVisibility(View.GONE);
                final View vetanswr=findViewById(R.id.etanswr);
                vetanswr.setVisibility(View.GONE);
                final View tvques=findViewById(R.id.security);
                tvques.setVisibility(View.GONE);

                et4.setEnabled(true);
                breg.setEnabled(false);
                bedit.setEnabled(false);
                bupdate.setEnabled(true);
                bdelete.setEnabled(true);
                update();
                delete();

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        et4.setEnabled(false);
                        breg.setEnabled(true);
                        bedit.setEnabled(true);
                        bupdate.setEnabled(false);
                        bdelete.setEnabled(false);
                        viewshowUpt.setVisibility(View.GONE);
                        viewshowDel.setVisibility(View.GONE);
                        viewId.setVisibility(View.GONE);
                        vradiogrp.setVisibility(View.VISIBLE);
                        vetanswr.setVisibility(View.VISIBLE);
                        tvques.setVisibility(View.VISIBLE);
                    }
                },20000);
                //
            }
        });
    }
    public void  update(){
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mydbreg.checkUser(etreguser.getText().toString())) {
                    boolean isUpdate = mydbreg.updateUser(etreguser.getText().toString(), etregpass.getText().toString(), et4.getText().toString());
                    if (isUpdate == true)
                        Toast.makeText(NewUser.this, "Data Update", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(NewUser.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(NewUser.this, "duplicate user exists..first delete the previous user record to update password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void delete(){
        bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydbreg.deleteUser(et4.getText().toString());
                if(deletedRows > 0)
                    Toast.makeText(NewUser.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(NewUser.this,"no match found",Toast.LENGTH_LONG).show();
            }
        });


    }
    private void clear(){
        etreguser.setText(null);
        etregpass.setText(null);
        etsurepass.setText(null);
        etsurepass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        etsurepass.setError(null);

    }
}
