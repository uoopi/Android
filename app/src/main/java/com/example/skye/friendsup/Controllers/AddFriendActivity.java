package com.example.skye.friendsup.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.Models.Model;
import com.example.skye.friendsup.R;
import java.util.Calendar;
import static com.example.skye.friendsup.Controllers.DatePickerFragment.tday;
import static com.example.skye.friendsup.Controllers.DatePickerFragment.tmonth;
import static com.example.skye.friendsup.Controllers.DatePickerFragment.tyear;
import static com.example.skye.friendsup.Controllers.MainActivity.model;

public class AddFriendActivity extends AppCompatActivity{
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0 ;
    public static final String TAG = "AddFriend status";


    private String friendName;
    private String friendEmail;
    private int friendImg = R.drawable.icon0;
    private int imageCounter = 0;

    private int year = tyear;
    private int month = tmonth+1;
    private int day = tday;
    private Calendar friendBD = Calendar.getInstance();

    private boolean datePicked = false;






    Friends newfriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Log.i(TAG,"onCreateAddFriend");


    }

    public void changeImage(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        if(imageCounter==0){
            image.setImageResource(R.drawable.icon1);
            friendImg = R.drawable.icon1;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else if (imageCounter==1){
            image.setImageResource(R.drawable.icon2);
            friendImg = R.drawable.icon2;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else if (imageCounter==2){
            image.setImageResource(R.drawable.icon3);
            friendImg = R.drawable.icon3;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else{
            image.setImageResource(R.drawable.icon0);
            friendImg = R.drawable.icon0;
            //model.setFriendImg(friendImg);
            imageCounter=0;
        }

    }

    public void addFromContact(View view){

        askForContactPermission();

    }

    public void getContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACTS);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG,"onActivityResult Called");
        if (requestCode == PICK_CONTACTS) {
            Log.i(TAG,"request code passed");
            if (resultCode == RESULT_OK) {
                Log.i(TAG,"ready to get results");
                ContactDataManager contactsManager = new ContactDataManager(this, data);
                String name = "";
                String email = "";
                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    friendName = name;
                    friendEmail = email;
                    EditText nameText = (EditText)findViewById(R.id.nameText);
                    EditText emailText = (EditText)findViewById(R.id.emailText);
                    nameText.setText(friendName);
                    emailText.setText(friendEmail);
                    //model.setFriendName(friendName);
                    //model.setFriendEmail(friendEmail);
                    Log.i(TAG,name);
                    Log.i(TAG,email);
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(TAG, e.getMessage());

                }
            }
        }

    }


    public void askForContactPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(AddFriendActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddFriendActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(AddFriendActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else getContact();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG,"Permission granted");

                    getContact();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(AddFriendActivity.this, "Permission denied to read Contact", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"Permission declined");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void addNewFriend(View view){
        if (friendName!=null && friendEmail!=null && friendBD!=null && datePicked!=false){
            //model.addNewFriend();

            newfriend = new Friends(friendName,friendEmail,friendBD,friendImg);
            Log.i(TAG,newfriend.getFriendID());
            Log.i(TAG,newfriend.getFriendName());
            Log.i(TAG,newfriend.getFriendEmail());
            Log.i(TAG,""+newfriend.getFriendImg());



            model.addNewFriend(newfriend);
            //Log.i(TAG,//model.getFriendID()+"\n"+//model.getFriendName()+"\n"+//model.getFriendEmail()+"\n"+//model.getFriendImg());
        }


    }

    public void pickDate(View view){


        EditText dateText = (EditText)findViewById(R.id.dobText);
        dateText.setText(day+"/"+month+"/"+year);

        android.app.DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePickFragment");

        year = tyear;
        month = tmonth+1;
        day = tday;

        dateText.setText(day+"/"+month+"/"+year);
        friendBD.set(year,month,day);
        //model.setFriendBD(friendBD);

        datePicked = true;
        Log.i(TAG,"The friend's BD is "+friendBD.get(Calendar.DAY_OF_MONTH)+"/"+friendBD.get(Calendar.MONTH)+"/"+friendBD.get(Calendar.YEAR));
    }

    public void refreshDate(View view){

        try{
            EditText dateText = (EditText)findViewById(R.id.dobText);
            year = tyear;
            month = tmonth+1;
            day = tday;

            dateText.setText(day+"/"+month+"/"+year);
            friendBD.set(year,month,day);
            Log.i(TAG,"The friend's BD is "+friendBD.get(Calendar.DAY_OF_MONTH)+"/"+friendBD.get(Calendar.MONTH)+"/"+friendBD.get(Calendar.YEAR));
            //model.setFriendBD(friendBD);
            datePicked = true;

        }catch (Exception e){
            Log.e(TAG,e.getMessage());

        }


    }





    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeAddFriend");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestartAddFriend");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStartAddFriend");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPauseAddFriend");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroyAddFriend");
    }
}
