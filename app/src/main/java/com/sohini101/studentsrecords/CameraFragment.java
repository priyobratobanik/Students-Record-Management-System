package com.sohini101.studentsrecords;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SOHINI PAL on 08-01-2018.
 */

public class CameraFragment extends Fragment {
   private View view;
  private   StudentImageProvider imageProvider;
    private UserDatbase mydbstudimg;
    private Button bCapImg,bshowstud,bdelstudimg;
    private ImageView imageView;
    private Cursor cursor;
    private SQLiteDatabase db;
    private String getArgumentStringImgRoll;
    private static final int CAMERA_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmentcamera, container, false);
         bCapImg=(Button)view.findViewById(R.id.bCapImg);
         imageView=(ImageView)view.findViewById(R.id.ivImage);
         bshowstud=(Button)view.findViewById(R.id.bshowstud);
        bdelstudimg=(Button)view.findViewById(R.id.bdelstudimg);
        //Get Argument that passed from activity in "data" key value
         getArgumentStringImgRoll = getArguments().getString("dataroll");
        Toast.makeText(getActivity(),getArgumentStringImgRoll,Toast.LENGTH_SHORT).show();
        //create UserDataBase object
        mydbstudimg=new UserDatbase(getActivity(),null,null,3);


        final String[] option = new String[] { "Take from Camera", "Cancel" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                if (which == 0) {
                    callCamera();
                }
                if(which==1){
                    dialog.dismiss();
                }

            }
        });
        final AlertDialog dialog = builder.create();
        bCapImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

       if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

          // bCapImg.setEnabled(false);

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        }
        viewListImg();
        deleteimg();
        //to set profile pic when fragment is opening
        if(mydbstudimg.checkStudImageRoll(getArgumentStringImgRoll)){
          //  bCapImg.setEnabled(false);
            View vcam=view.findViewById(R.id.bCapImg);
            vcam.setVisibility(View.GONE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    db=mydbstudimg.getReadableDatabase();
                    cursor=mydbstudimg.getStudImg(getArgumentStringImgRoll);
                    if(cursor==null){
                        Toast.makeText(getActivity(),"NO IMAGE FOUND",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        byte[] outputstudimg=cursor.getBlob(cursor.getColumnIndex(UserDatbase.COL3_STUDIMAGE));
                        //convert byte to bitmap
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outputstudimg);
                        Bitmap theImageBitmap = BitmapFactory.decodeStream(imageStream);
                        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(theImageBitmap, 100);
                        imageView.setImageBitmap(circularBitmap);

                    }

                }
            });
        }
        return view;

    }

   @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == 0) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                bCapImg.setEnabled(true);

            }

        }

    }

    /**
     * On activity result
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    Log.e("outputbeforeconversion", imageInByte.toString());
                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                    imageProvider = new StudentImageProvider(getArgumentStringImgRoll, imageInByte);
                    imageProvider.setImgroll(getArgumentStringImgRoll);
                    imageProvider.setImage(imageInByte);
                    boolean resultimg = mydbstudimg.addStudImg(imageProvider);
                    if (resultimg == true) {
                        View vcam=view.findViewById(R.id.bCapImg);
                        vcam.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                        cursor=mydbstudimg.getStudImg(getArgumentStringImgRoll);
                        byte[] outputstudimg=cursor.getBlob(cursor.getColumnIndex(UserDatbase.COL3_STUDIMAGE));
                        //convert byte to bitmap
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outputstudimg);
                        Bitmap theImageBitmap = BitmapFactory.decodeStream(imageStream);
                        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(theImageBitmap, 100);
                        imageView.setImageBitmap(circularBitmap);

                    }
                }
                break;

                  /*  */
                    }

       }


    public void callCamera() {
        //using implicit intent to open camera or gallery
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        Fragment fragment=this;
        fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void viewListImg(){

        bshowstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        db=mydbstudimg.getReadableDatabase();
                        cursor=mydbstudimg.getStudImg(getArgumentStringImgRoll);
                        if(cursor==null){
                            Toast.makeText(getActivity(),"NO IMAGE FOUND",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                          byte[] outputstudimg=cursor.getBlob(cursor.getColumnIndex(UserDatbase.COL3_STUDIMAGE));
                            //convert byte to bitmap
                            ByteArrayInputStream imageStream = new ByteArrayInputStream(outputstudimg);
                            Bitmap theImageBitmap = BitmapFactory.decodeStream(imageStream);
                            Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(theImageBitmap, 100);
                              imageView.setImageBitmap(circularBitmap);

                       }

                    }
                });

            }
        });


    }


    public void deleteimg(){
        bdelstudimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builderRemove=new AlertDialog.Builder(getActivity());
                builderRemove.setTitle("Remove profile photo?");
                builderRemove.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final  UserDatbase mydbstudimg = new UserDatbase(getActivity(), null, null, 3);
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Integer deletedStudimg = mydbstudimg.deleteStudDataImage(getArgumentStringImgRoll);
                                if (deletedStudimg > 0) {

                                    //refresh

                                    Toast.makeText(getActivity(), "image of universtity roll" + getArgumentStringImgRoll + " Deleted", Toast.LENGTH_SHORT).show();
                               bCapImg.setEnabled(true);
                                    View vcamimg=view.findViewById(R.id.bCapImg);
                                    vcamimg.setVisibility(View.VISIBLE);
                                } else
                                    Toast.makeText(getActivity(), "img not deleted", Toast.LENGTH_SHORT).show();
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
