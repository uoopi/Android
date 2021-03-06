package com.example.skye.friendsup.Controllers;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.Models.Meetings;
import com.example.skye.friendsup.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.skye.friendsup.Controllers.MainActivity.model;


public class AddMeetingActivity extends AppCompatActivity {

    public static final String TAG = "AddMeeting status";


    private String title;
    private Calendar startTime = Calendar.getInstance();
    private boolean startPicked = false;
    private Calendar endTime = Calendar.getInstance();
    private boolean endPicked = false;
    private ArrayList<Friends> friendsMeeting = new ArrayList<>();
    private String location;


    // private int hour = thour;
    // private int minu = tminu;

    Meetings newMeeting;
    ////////
    private Button addMeetingBtn;
    private EditText startTimeText;
    private EditText endTimeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        Log.i(TAG, "onCreateAddMeeting");
        addMeetingBtn = (Button) findViewById(R.id.addMeeting);
        startTimeText = (EditText) findViewById(R.id.startText);
        endTimeText = (EditText) findViewById(R.id.endText);

        startTimeText.setOnClickListener(addMeetingActivityListener);
        endTimeText.setOnClickListener(addMeetingActivityListener);


        startTimeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickTime("startTime");
                }

            }
        });
        endTimeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickTime("endTime");
                }

            }
        });
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener addMeetingActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startText:
                    pickTime("startTime");
                    break;
                case R.id.endText:
                    pickTime("endTime");
                    break;
                case R.id.addMeeting:
                    addMeeting();
                    break;
                default:
                    break;
            }

        }
    };

    private void pickTime(String label) {
        DialogFragment mf = new MeetingDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeLabel", label);
        mf.setArguments(bundle);
        mf.show(getFragmentManager(), "datePickFragment");
    }


    private void addMeeting() {

    }


    public void addNewMeeting(View view) {

        friendsMeeting.add(model.getFriends().get(0));

        EditText etTitle = (EditText) findViewById(R.id.titleText);
        EditText etLocation = (EditText) findViewById(R.id.locationText);

        title = etTitle.getText().toString();
        location = etLocation.getText().toString();


        if (title != null && startPicked != false && endPicked != false && location != null) {

            newMeeting = new Meetings(title, startTime, endTime, friendsMeeting, location);
            model.addNewMeeting(newMeeting);

            Toast.makeText(AddMeetingActivity.this, "New meeting added", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(AddMeetingActivity.this, "You have to fill all the space.", Toast.LENGTH_LONG).show();
        }


    }


//    public void pickDate1(View view){
//
//
//        EditText dateText1 = (EditText)findViewById(R.id.startText);
//        dateText1.setText(thour+":"+tminu);
//
//        android.app.DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getFragmentManager(), "timePickFragment");
//
//        hour = thour;
//        minu = tminu;
//
//        dateText1.setText(hour+":"+minu);
//        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        startPicked = true;
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
//    }
//
//
//
//    public void refreshTime1(View view){
//        EditText dateText1 = (EditText)findViewById(R.id.startText);
//
//
//
//        hour = thour;
//        minu = tminu;
//
//        dateText1.setText(hour+":"+minu);
//        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        startPicked = true;
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
//
//    }
//
//
//
//    public void pickDate2(View view){
//
//
//        EditText dateText2 = (EditText)findViewById(R.id.endText);
//        dateText2.setText(thour+":"+tminu);
//
//        android.app.DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getFragmentManager(), "timePickFragment");
//
//        hour = thour;
//        minu = tminu;
//
//        dateText2.setText(hour+":"+minu);
//        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        endPicked = true;
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
//    }
//
//
//
//    public void refreshTime2(View view){
//        EditText dateText2 = (EditText)findViewById(R.id.endText);
//
//
//
//        hour = thour;
//        minu = tminu;
//
//        dateText2.setText(hour+":"+minu);
//        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        endPicked = true;
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResumeAddMeeting");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestartAddMeeting");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStartAddMeeting");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPauseAddMeeting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroyAddMeeting");
    }


}
