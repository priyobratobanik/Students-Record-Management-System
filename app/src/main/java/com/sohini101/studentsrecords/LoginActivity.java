package com.sohini101.studentsrecords;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements MyDialogFragment.Communicator{
    private ProgressBar spinner;
    private EditText etloguser,etlogpass;
    private Button blogin;
    private CheckBox checkBox;
    private TextView tvregfromloginpg,tvforgetpwd;
    private String mainUser="sohini";
    private String mainpassword="sohini101";
    private UserDatbase mydblog;
    private Cursor cursor;

    SharedPreferences prefremind;
    final String KEY_SavedUser = "Saved user";
    // User Session Manager Class
    UserSessionManager session;
    String loginuser;
    String pass;
    private final int WAIT_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        initlog();
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


        etloguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etloguser.setFocusable(true);
                etloguser.setError("enter user");
                etloguser.requestFocus();
                etloguser.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (etloguser.getText().length() < 1) {
                            etloguser.setError("please enter user");
                            etloguser.requestFocus();
                        }
                        if (etloguser.getText().length() > 0) {
                            etloguser.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        etloguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etloguser.setFocusable(true);
                etloguser.setError("enter user");
                etloguser.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (etloguser.getText().length() < 1) {
                            etloguser.setError("please enter user");
                            etloguser.requestFocus();
                        }
                        if (etloguser.getText().length() > 0) {
                            etloguser.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        etlogpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etlogpass.setFocusable(true);
                etlogpass.setError("enter password");
                etlogpass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (etlogpass.getText().length() < 1) {
                            etlogpass.setError("please enter password");
                            etlogpass.requestFocus();
                        }
                        if (etlogpass.getText().length() > 0) {
                            etlogpass.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });


        visiblepassword();
        //text no account yet , to be shown or not
        mydblog = new UserDatbase(LoginActivity.this, null, null, 3);
        Cursor checkEntry=mydblog.viewAll();
        if (checkEntry.getCount()!=0){
            View tvnoaccount=findViewById(R.id.regfrmloginpg);
            tvnoaccount.setVisibility(View.GONE);
            View forgtpass=findViewById(R.id.forgetPwd);
            forgtpass.setVisibility(View.VISIBLE);
        }
        showDialogFrag();
    }
    public void pleaselogin(View v) {
        spinner.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                mydblog = new UserDatbase(LoginActivity.this, null, null, 3);
                loginuser = etloguser.getText().toString().trim();
                pass = etlogpass.getText().toString().trim();
                if (!isBlank()) {
                    return;
                }
                if (loginuser.equals(mainUser) && pass.equals(mainpassword)) {
                    // Creating user login session
                    session.createUserLoginSession(loginuser, pass);

                    Intent intent;
                    intent = new Intent(LoginActivity.this, Registration.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();//Call this when your activity is done and should be closed
                } else {
                    String matchpassword = mydblog.searchPass(loginuser);



                    if (pass.equals(matchpassword)) {
                        session.createUserLoginSession(loginuser, pass);

                        Intent intent;
                        intent = new Intent(LoginActivity.this, Registration.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();//
                    } else {
                        Context context = getApplicationContext();
                        LayoutInflater inflater = getLayoutInflater();

                        View customToastroot = inflater.inflate(R.layout.mycustom_toast, null);

                        Toast customtoast = new Toast(context);

                        customtoast.setView(customToastroot);
                        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                        customtoast.setDuration(Toast.LENGTH_LONG);
                        customtoast.show();
                        spinner.setVisibility(View.GONE);
                        clearlog();

                    }

                }
            }
        },WAIT_TIME);
    }

    private void initlog(){
        etloguser=(EditText)findViewById(R.id.etloguser);
        etlogpass=(EditText)findViewById(R.id.etlogpass);
        blogin=(Button)findViewById(R.id.blogin);
        checkBox=(CheckBox)findViewById(R.id.cb_show_pass);
        tvregfromloginpg=(TextView)findViewById(R.id.regfrmloginpg);
        tvforgetpwd=(TextView)findViewById(R.id.forgetPwd);
    }

    public void visiblepassword(){
        final EditText  etlogpass=(EditText)findViewById(R.id.etlogpass);
        final CheckBox  checkBox=(CheckBox)findViewById(R.id.cb_show_pass);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    etlogpass.setTransformationMethod(null);

                }
                else {

                    etlogpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void regfromlogin(View view){
        Intent intentnewreg=new Intent(LoginActivity.this,NewUser.class);
        startActivity(intentnewreg);

    }

    public boolean isBlank(){
        String user=etloguser.getText().toString().trim();
        String pass=etlogpass.getText().toString().trim();
        if(user.isEmpty()|| pass.isEmpty()) {
            if (user.isEmpty()) {
                etloguser.setError("blank");

            }
            if (pass.isEmpty()) {
                etlogpass.setError("blank");
            }
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }


    public void clearlog(){
        etloguser.setText(null);
        etlogpass.setText(null);
    }

    //forgetpwd
    public void showDialogFrag(){

        tvforgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etloguser.getText().toString().isEmpty()){
                    etloguser.setError("enter user to recover password");
                    return;
                }
                if (!mydblog.checkUserwiseIfForgetPwd(etloguser.getText().toString())){
                    etloguser.setError("no such user exists");
                    return;
                }
                String quesOfUser=mydblog.searchSecurityQues(etloguser.getText().toString());
                String ansOfUser=mydblog.searchSecurityAns(etloguser.getText().toString());
                String pwdOfUser=mydblog.searchPass(etloguser.getText().toString());
                FragmentManager fragmentManager=getFragmentManager();
                Bundle data=new Bundle();
                data.putString("userQues",quesOfUser);
                data.putString("userAns",ansOfUser);
                data.putString("userPass",pwdOfUser);
                MyDialogFragment myDialogFragment=new MyDialogFragment();
                myDialogFragment.setArguments(data);
                myDialogFragment.show(fragmentManager,"MyDialogFragment");


            }
        });
    }


    @Override
    public void onDialogMessage(String message) {
        Toast.makeText(this,"your password is successfully recovered!",Toast.LENGTH_SHORT).show();
        etlogpass.setText(message);
    }

}
