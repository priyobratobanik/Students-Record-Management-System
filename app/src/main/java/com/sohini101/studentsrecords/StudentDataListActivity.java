package com.sohini101.studentsrecords;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class StudentDataListActivity extends AppCompatActivity {
    ListView listView;

    private SQLiteDatabase db;
    SwipeRefreshLayout swipeRefreshLayout;
    private UserDatbase mydbstud;

    private Cursor cursor;

    private Cursor cursor1;

    private ListDataAdapter listDataAdapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data_list);
        context=StudentDataListActivity.this;

        listView = (ListView) findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);



        mydbstud = new UserDatbase(StudentDataListActivity.this, null, null, 3);

        db = mydbstud.getReadableDatabase();
        cursor = mydbstud.viewAll_List(db);
        cursor1=mydbstud.viewAll_List(db);
        int count=cursor.getCount();
        Toast.makeText(StudentDataListActivity.this,"Number of students: "+String.valueOf(count),Toast.LENGTH_LONG).show();


        new Handler().post(new Runnable() {
            @Override
            public void run() {

                listDataAdapter = new ListDataAdapter(StudentDataListActivity.this, cursor, 0);
                listView.setAdapter(listDataAdapter);
                listDataAdapter.notifyDataSetChanged();


            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // refresh();
                Intent intent=new Intent(StudentDataListActivity.this,Studentreg.class);
                Toast.makeText(getApplicationContext(),"Click on view to see the refreshed listview item",Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setQueryHint("fname/roll");

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(final String s) {

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mydbstud = new UserDatbase(StudentDataListActivity.this, null, null, 3);
                            cursor = mydbstud.getStudentListByKeyword(s);
                            if (cursor == null) {
                                cursor1=mydbstud.getStudentListByRoll(s);
                                if(cursor1==null) {
                                    Toast.makeText(StudentDataListActivity.this, "No records found!", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(StudentDataListActivity.this, cursor1.getCount() + " records found!", Toast.LENGTH_LONG).show();
                                }
                                listDataAdapter.swapCursor(cursor1);
                            } else {
                                Toast.makeText(StudentDataListActivity.this, cursor.getCount() + " records found!", Toast.LENGTH_LONG).show();
                                listDataAdapter.swapCursor(cursor);
                            }
                        }
                    });



                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    mydbstud = new UserDatbase(StudentDataListActivity.this, null, null, 3);
                    cursor = mydbstud.getStudentListByKeyword(s);
                    cursor1=mydbstud.getStudentListByRoll(s);
                    if (cursor != null) {
                        listDataAdapter.swapCursor(cursor);
                    }
                    else {
                        if (cursor1!=null){
                            listDataAdapter.swapCursor(cursor1);
                        }
                    }
                    return true;
                }

            });

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.m_home:
                Intent i=new Intent(StudentDataListActivity.this,Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
