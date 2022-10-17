package com.sohini101.studentsrecords;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * Created by SOHINI PAL on 29-12-2017.
 */

public class ListDataAdapter extends CursorAdapter {
    private StudentDataListActivity studentDataListActivity;
    Context context;
    LayoutInflater inflater;
StudentDataProvider dataProvider;
UserDatbase mydbstud;
private EmailPhoneValidator emailPhoneValidator;
    List<String> lablesstuduptstrm;
    ArrayAdapter<String>dataAdapterupdatedstudstrm;
ArrayAdapter<CharSequence> dataAdapterupdatedstudyear;

   List list=new ArrayList();

    public ListDataAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    static class ViewHolder{
        TextView tvid,streams,regYear,fname,lname,roll,email,phone;
        TextView titlestreams,titleregyear,titlefname,titlelname,titleroll,titleemail,titlephone;
        Button btnstedit;

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable



@Override

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View view=inflater.inflate(R.layout.studentrow_layout,parent,false);
        ViewHolder holder  =   new ViewHolder();
        view.setBackground( ContextCompat.getDrawable(context,R.drawable.listviewitemborder));
        holder.tvid=(TextView)view.findViewById(R.id.studid);
        holder.titlestreams=(TextView)view.findViewById(R.id.tvtitlestdept);
        holder.streams=(TextView)view.findViewById(R.id.studstream);
        holder.btnstedit=(Button) view.findViewById(R.id.bstedit);
        holder.titleregyear=(TextView)view.findViewById(R.id.tvtitlestyear);
        holder.regYear=(TextView)view.findViewById(R.id.studyear);
        holder.titlefname=(TextView)view.findViewById(R.id.tvtitlestfnanme);
        holder.fname=(TextView)view.findViewById(R.id.fname);
        holder.titlelname=(TextView)view.findViewById(R.id.tvtitlestlname);
        holder.lname=(TextView)view.findViewById(R.id.lname);
        holder.titleroll=(TextView)view.findViewById(R.id.tvtitlestroll);
        holder.roll=(TextView)view.findViewById(R.id.roll);
        holder.titleemail=(TextView)view.findViewById(R.id.tvtitlestemail);
        holder.email=(TextView)view.findViewById(R.id.email);
        holder.titlephone=(TextView)view.findViewById(R.id.tvtitlestphone);
        holder.phone=(TextView)view.findViewById(R.id.phone);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

      final ViewHolder holder=(ViewHolder)view.getTag();
       holder.tvid.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL1_STUDID)));
        holder.streams.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL2_STUDSTRM)));
        holder.regYear.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL3_STUDYEAR)));
        holder.fname.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL4_STUDFNAME)));
        holder.lname.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL5_STUDLNAME)));
        holder.roll.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL6_STUDROLL)));
        holder.email.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL7_STUDEMAIL)));
        holder.phone.setText(cursor.getString(cursor.getColumnIndex(UserDatbase.COL8_STUDPHONE)));
        int position=cursor.getPosition();
        final int id=cursor.getInt(cursor.getColumnIndex(UserDatbase.COL1_STUDID));
        int streamindex=cursor.getColumnIndex(UserDatbase.COL2_STUDSTRM);
        final String istream=cursor.getString(streamindex);
        int yearindex=cursor.getColumnIndex(UserDatbase.COL3_STUDYEAR);
        final String iyear=cursor.getString(yearindex);
        int fnameindex=cursor.getColumnIndex(UserDatbase.COL4_STUDFNAME);
        final String ifname=cursor.getString(fnameindex);
        int lnameindex=cursor.getColumnIndex(UserDatbase.COL5_STUDLNAME);
        final String ilname=cursor.getString(lnameindex);
        int rollindex=cursor.getColumnIndex(UserDatbase.COL6_STUDROLL);
       final String iroll=cursor.getString(rollindex);
        int emailindex=cursor.getColumnIndex(UserDatbase.COL7_STUDEMAIL);
        final String iemail=cursor.getString(emailindex);
        int phoneindex=cursor.getColumnIndex(UserDatbase.COL8_STUDPHONE);
        final String iphone=cursor.getString(phoneindex);
        holder.btnstedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Roll is: "+iroll,Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("university roll: "+iroll);
                builder.setIcon(R.mipmap.editpencil);
                builder.setNeutralButton("DETAILS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentstud;
                        intentstud=new Intent(context,ShareActivity.class);
                        intentstud.putExtra("student",new StudentDataProvider(istream,iyear,ifname,ilname,iroll,iemail,iphone));

                        context.startActivity(intentstud);
                    }
                });
                builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View alertlayout=inflater.inflate(R.layout.update_student_dialog,null);

                        final TextView tvupstudstrm=alertlayout.findViewById(R.id.uprtvtitlestdept);

                        final Spinner spinuptstudstrm=alertlayout.findViewById(R.id.spinuptstrm);
                        final TextView tvupstudregyear=alertlayout.findViewById(R.id.uprtvtitlestyear);

                        final Spinner spinuptstyr=alertlayout.findViewById(R.id.spinuptstudyr);
                        final TextView tvupstudfname=alertlayout.findViewById(R.id.uprtvtitlestfnanme);
                        final EditText etupstudfname=alertlayout.findViewById(R.id.uprfname);
                        final TextView tvupstudlname=alertlayout.findViewById(R.id.uprtvtitlestlname);
                        final EditText etupstudlname=alertlayout.findViewById(R.id.uprlname);
                        final TextView tvupstudroll=alertlayout.findViewById(R.id.uprtvtitlestroll);
                        final TextView etupstudroll=alertlayout.findViewById(R.id.uprroll);
                        final TextView tvupstudemail=alertlayout.findViewById(R.id.uprtvtitlestemail);
                        final EditText etupstudemail=alertlayout.findViewById(R.id.upremail);
                        final TextView tvupstudphone=alertlayout.findViewById(R.id.uprtvtitlestphone);
                        final EditText etupstudphone=alertlayout.findViewById(R.id.uprphone);
                        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context);
                        UserDatbase dbupdatedstudstrm=new UserDatbase(context,null,null,3);
                        lablesstuduptstrm=dbupdatedstudstrm.getAllLabels();
                        dataAdapterupdatedstudstrm = new ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, lablesstuduptstrm);
                        // Drop down layout style - list view with radio button
                        dataAdapterupdatedstudstrm
                                .setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        //refresh;
                        dataAdapterupdatedstudstrm.notifyDataSetChanged();
                        // attaching data adapter to spinner
                        spinuptstudstrm.setAdapter(dataAdapterupdatedstudstrm);

                        int spinnerPositionstrm = dataAdapterupdatedstudstrm.getPosition(istream);
                        spinuptstudstrm.setSelection(spinnerPositionstrm);

                        dataAdapterupdatedstudyear = ArrayAdapter.createFromResource(context,
                                R.array.student_class, android.R.layout.simple_spinner_item);
                        dataAdapterupdatedstudyear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinuptstyr.setAdapter(dataAdapterupdatedstudyear);
                        int spinnerPositionyr=dataAdapterupdatedstudyear.getPosition(iyear);
                        spinuptstyr.setSelection(spinnerPositionyr);

                        etupstudfname.setText(ifname);
                        etupstudlname.setText(ilname);
                        etupstudroll.setText(iroll);
                        etupstudemail.setText(iemail);
                        etupstudphone.setText(iphone);

                        alertdialog.setTitle("Student Record Update");
                        alertdialog.setView(alertlayout);

                        alertdialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int posstrm = spinuptstudstrm.getSelectedItemPosition();
                            final String upststrm = lablesstuduptstrm.get(posstrm);

                                Resources resource = context.getResources();
                                String[] uptYrArray = resource.getStringArray(R.array.student_class);
                                int posyr = spinuptstyr.getSelectedItemPosition();
                           final String upstyear = uptYrArray[posyr];

                                EditText upresfnm = alertlayout.findViewById(R.id.uprfname);
                               final String upstfname = upresfnm.getText().toString();
                                EditText upreslnm = alertlayout.findViewById(R.id.uprlname);
                               final String upstlname = upreslnm.getText().toString();
                                TextView upresroll = alertlayout.findViewById(R.id.uprroll);
                               final String upstroll = upresroll.getText().toString();
                                EditText upresemail = alertlayout.findViewById(R.id.upremail);
                              final  String upstemail = upresemail.getText().toString();
                                EditText upresphone = alertlayout.findViewById(R.id.uprphone);
                             final  String upstphone = upresphone.getText().toString();

                                mydbstud = new UserDatbase(context, null, null, 3);
                                Studentreg studentreg = new Studentreg();
                                emailPhoneValidator=new EmailPhoneValidator();

                                if(upstfname.isEmpty() && upstlname.isEmpty() && upstemail.isEmpty() && upstphone.isEmpty()){
                                    Toast.makeText(context,"fields cannot be blank",Toast.LENGTH_SHORT).show();
                                     return;
                                   }
                                if (!emailPhoneValidator.validatePhone(upstphone)){
                                    upresphone.setError("invalid");
                                    Toast.makeText(context,"invalid mobile number",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (!emailPhoneValidator.validateEmail(upstemail)){
                                    upresemail.setError("invalid");
                                    Toast.makeText(context,"invalid email",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                    boolean studupdate = mydbstud.updateStudent(id, upststrm, upstyear, upstfname, upstlname, upstroll, upstemail, upstphone);
                                    if (studupdate == true) {
                                        Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show();
                                        final AlertDialog.Builder disbuilder=new AlertDialog.Builder(context);
                                        disbuilder.setTitle("info");
                                        disbuilder.setMessage("record of roll "+upstroll+" has been updated");
                                        disbuilder.setCancelable(false);
                                        disbuilder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                holder.streams.setText(upststrm);
                                                holder.regYear.setText(upstyear);
                                                holder.fname.setText(upstfname);
                                                holder.lname.setText(upstlname);
                                                holder.roll.setText(upstroll);
                                                holder.email.setText(upstemail);
                                                holder.phone.setText(upstphone);
                                            }
                                        });
                                        disbuilder.create().show();

                                        Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(context, "Data not Updated", Toast.LENGTH_LONG).show();
                                    }

                                dialog.dismiss();


                            }
                        });
                        alertdialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog1=alertdialog.create();
                        dialog1.show();
                        Button bupt=dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                        int color=getColor(context,R.color.yellow);
                        bupt.setBackgroundColor(color);

                    }
                });
                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final AlertDialog.Builder alertdialog2=new AlertDialog.Builder(context);
                        alertdialog2.setIcon(R.mipmap.questionmark);
                        alertdialog2.setTitle("Warning");
                        alertdialog2.setMessage("Would you really like to delete the student record? "+"\n"+istream+"\n"+iyear+"\n"+ifname+"\n"+ilname+"\n"+iphone);
                        alertdialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //loadSpinnerData();
                                UserDatbase mydbstud=new UserDatbase(context,null,null,3);
                                Integer deletedStud = mydbstud.deleteStudData(id);
                                if(deletedStud>0){

                                    //refresh
                                 Integer deletedStudSemMarks=mydbstud.deleteStudSEMMarks(iroll);
                                 if (deletedStudSemMarks>0){
                                     Toast.makeText(context,"Sem marks of "+iroll+" deleted",Toast.LENGTH_SHORT).show();
                                 }

                                    Toast.makeText(context,"Record of universtity roll"+iroll+" Deleted",Toast.LENGTH_SHORT).show();

                                    final AlertDialog.Builder alertdialog3=new AlertDialog.Builder(context);
                                    alertdialog3.setTitle("INFO");
                                    alertdialog3.setCancelable(false);
                                    alertdialog3.setMessage("Record of student having university roll no: " +iroll+" has been deleted");
                                    alertdialog3.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            holder.tvid.setText(" ");
                                            holder.streams.setText(" ");
                                            holder.regYear.setText(" ");
                                            holder.fname.setText(" ");
                                            holder.lname.setText(" ");
                                            holder.roll.setText(" ");
                                            holder.email.setText(" ");
                                            holder.phone.setText(" ");
                                            holder.btnstedit.setEnabled(false);
                                          view.setBackgroundColor(Color.GRAY);

                                            dialog.dismiss();
                                        }
                                    });
                                    alertdialog3.create().show();
                                }

                                else
                                {
                                    Toast.makeText(context,"not deleted",Toast.LENGTH_SHORT).show();
                                }

                                dialog.dismiss();
                            }
                        });
                        alertdialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertdialog2.create().show();

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();

             Button bPos=dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                int color1=getColor(context,R.color.pinkish);
                int color2=getColor(context,R.color.material_blue_grey_80);
             bPos.setTextColor(color2);
             bPos.setBackgroundColor(color1);
             int color3=getColor(context,R.color.violet);
             Button bUp=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
             bUp.setBackgroundColor(color3);
             bPos.setTextColor(color2);
             int color4=getColor(context,R.color.blue);
             Button bdel=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
             bdel.setTextColor(color2);
             bdel.setBackgroundColor(color4);



            }
        });

    }


    @Override
    protected void onContentChanged(){
    super.onContentChanged();
    notifyDataSetChanged();
    }


}
