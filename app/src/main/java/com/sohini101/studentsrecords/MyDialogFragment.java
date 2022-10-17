package com.sohini101.studentsrecords;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SOHINI PAL on 13-01-2018.
 */

public class MyDialogFragment extends DialogFragment implements View.OnClickListener{
    private View view;
    private Button submit,cancel;
    private EditText etdiaans;
    private TextView tvdiaques;
   private Communicator communicator;//creating ref
    private String getargumentUserques;
    private String getargumentUserans;
    private String getargumentUserpass;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        //to assign the object of activity to communicator with appropriate typecasting
        communicator=(Communicator)activity; //this will not work until you implement communicator interface in your activity


    }
   public MyDialogFragment(){//default constructor of DialogFragment

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       getDialog().setTitle("RECOVER PASSWORD!");
       view=inflater.inflate(R.layout.forgetpwd_dialogfrag,null);//to open fragment in a separate window we pass null in the viewgroup container
        submit=view.findViewById(R.id.bdiasubmit);
        cancel=view.findViewById(R.id.bdiacancel);
        tvdiaques=view.findViewById(R.id.tvdiaques);
        etdiaans=view.findViewById(R.id.etdiaans);
        Bundle args=getArguments();
        getargumentUserques=args.getString("userQues");
        getargumentUserans=args.getString("userAns");
        getargumentUserpass=args.getString("userPass");
        tvdiaques.setText(getargumentUserques);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bdiasubmit:
               if(!etdiaans.getText().toString().equals(getargumentUserans)){
                   etdiaans.setError("Sorry! wrong answer");
                   return;
               }

                communicator.onDialogMessage(getargumentUserpass);//whenever i launch this it will go to the activity's onDialogMessage
                dismiss();
                break;
            case R.id.bdiacancel:

               Toast.makeText(getActivity(),"cancel",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    interface Communicator{
        public void onDialogMessage(String message);
    }
}
