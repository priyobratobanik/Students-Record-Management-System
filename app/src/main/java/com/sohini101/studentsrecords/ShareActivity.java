package com.sohini101.studentsrecords;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    private TextView rstrm,ryr,rfnm,rlnm,rroll,remail,rphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Bundle bundle = getIntent().getExtras();
        StudentDataProvider studentDataIntent=(StudentDataProvider)bundle.getParcelable("student");

        rstrm=(TextView) findViewById(R.id.rstudstream);
        rstrm.setText(studentDataIntent.getStreams());
        ryr=(TextView) findViewById(R.id.rstudyear);
        ryr.setText(studentDataIntent.getRegYear());
        rfnm=(TextView) findViewById(R.id.rfname);
        rfnm.setText(studentDataIntent.getFname());
        rlnm=(TextView) findViewById(R.id.rlname);
        rlnm.setText(studentDataIntent.getLname());
        rroll=(TextView) findViewById(R.id.rroll);
        rroll.setText(studentDataIntent.getRoll());
        remail=(TextView) findViewById(R.id.remail);
        remail.setText(studentDataIntent.getEmail());
        rphone=(TextView) findViewById(R.id.rphone);
        rphone.setText(studentDataIntent.getPhone());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.m_takepic:
                Toast.makeText(getApplicationContext(),"first fragment",Toast.LENGTH_SHORT).show();
                loadFragment(new CameraFragment());
                break;
            case R.id.m_semmarks:
                Toast.makeText(getApplicationContext(),"second fragment",Toast.LENGTH_SHORT).show();

                loadFragment1(new fragmentSem());
                break;
            case R.id.m_home:
                Intent i=new Intent(ShareActivity.this,Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadFragment(Fragment fragment) {
        String tvroll=rroll.getText().toString();
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
        Fragment argumentFragment=new CameraFragment();//Get Fragment Instance

        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("dataroll", tvroll);//put string, int, etc in bundle with a key value
        argumentFragment.setArguments(data);//Finally set argument bundle to fragment

        fm.beginTransaction().replace(R.id.frameLayout, argumentFragment).commit();//now replace the argument fragment

/* for fragment without bundle
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
       fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes*/
    }
    private void loadFragment1(Fragment fragment) {
        String tvroll=rroll.getText().toString();
        String tvstream=rstrm.getText().toString();
        String tvyear=ryr.getText().toString();
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
        Fragment argumentFragment2=new fragmentSem();
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("dataroll1", tvroll);//put string, int, etc in bundle with a key value
        data.putString("datastrm",tvstream);
        data.putString("datayr",tvyear);
        argumentFragment2.setArguments(data);
        fm.beginTransaction().replace(R.id.frameLayout, argumentFragment2).commit();//now replace the argument fragment

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

    }
}
