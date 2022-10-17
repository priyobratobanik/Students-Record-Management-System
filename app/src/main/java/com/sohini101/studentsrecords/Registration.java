package com.sohini101.studentsrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    private Button buserreg,bstudreg;
    private TextView tvsignasuser;
    Intent intent;
    // User Session Manager Class
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        buserreg = (Button) findViewById(R.id.buserreg);
        bstudreg=(Button)findViewById(R.id.bstudreg);
        tvsignasuser=(TextView)findViewById(R.id.tvsignasuser);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();
        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String pass = user.get(UserSessionManager.KEY_PASS);
        tvsignasuser.setText("Welcome user: signed in as-"+name);

        buserreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Registration.this,NewUser.class);
                startActivity(intent);
            }
        });
        bstudreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Registration.this,Studentreg.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.m_logout){
            // Clear the User session data
            // and redirect user to LoginActivity
            AlertDialog.Builder builder=new AlertDialog.Builder(Registration.this);
            builder.setTitle("Would you really like to log out?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    session.logoutUser();
                    Toast.makeText(getApplicationContext(),"logging out",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog1=builder.create();
            dialog1.show();
            Button bupt=dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
           // int color=getColor(R.color.yellow);

            int color = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            bupt.setBackgroundColor(color);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
