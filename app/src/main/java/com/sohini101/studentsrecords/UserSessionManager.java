package com.sohini101.studentsrecords;

/**
 * Created by SOHINI PAL on 13-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences pref;//SharedPreferences is a singleton class means this class will only have a single object throughout the application lifecycle

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";
    /*
    A preference file is actually a xml file saved in internal memory of device. Every application have
    some data stored in memory in a directory data/data/application package name
     i.e data/data/com.abhiandroid.abhiapp so whenever getSharedPreferences(String name,int mode) function
     get called it validates the file that if it exists or it doesn’t then a new xml is created with passed name.

   */

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "sessionname";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASS = "sessionpass";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context; //context is obtaining access to preferences
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);//Used in cases when more than one preference file required in Activity
        // it can be called from anywhere in the application with reference of Context.
        editor = pref.edit();//To save data in SharedPreferences developer need to called edit() function of SharedPreferences class which returns Editor class object.
    }

    //Create login session
    public void createUserLoginSession(String sessionname, String sessionpass){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);//Editor class provide different function to save primitive time data.

        // Storing name in pref
        editor.putString(KEY_NAME, sessionname);

        // Storing password in pref
        editor.putString(KEY_PASS, sessionpass);

        // commit changes
        editor.commit();//Editor class provide different function to save primitive time data.
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}

