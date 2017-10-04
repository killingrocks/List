package com.example.meoryou.myapplication.ToDoList;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meoryou.myapplication.AlarmReceiver;
import com.example.meoryou.myapplication.ConnectionReceiver;
import com.example.meoryou.myapplication.DataBase.CRUD;
import com.example.meoryou.myapplication.DataBase.Item;
import com.example.meoryou.myapplication.DataBase.ItemsCollection;
import com.example.meoryou.myapplication.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by me or you on 9/27/2017.
 */

public class ToDo extends AppCompatActivity {
    // source----
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();// structures the date and the time
    TextView calendartext;
    Calendar cal =Calendar.getInstance(); // calender
    EditText descEditText,nameEditText; // the dialog ill put up to change the description
    String desc,dateformat, name; // get intent
    int position; // keep position of the item
    CRUD crud; // use method
    int AlamCode = 7;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    CheckBox checkBox;
    boolean statuscheck;
   private BroadcastReceiver bm;
    // savebtn is going to change the description and time

    //onCREATE
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.need_to_do_layout); // this is my layout
        descEditText= (EditText)findViewById(R.id.description_text);
        nameEditText = (EditText) findViewById(R.id.to_do);
        calendartext = (TextView) findViewById(R.id.date_time_cal); // image
        checkBox = (CheckBox) findViewById(R.id.check_box);
        crud = new CRUD(ItemsCollection.getItem()); // CRUD
        // intent
        Intent intent = this.getIntent();       // collect from main activity
        name = intent.getExtras().getString("NAM");
        desc = intent.getExtras().getString("DESC");        // description
        position = intent.getExtras().getInt("POS");       // position
        //bind
        descEditText.setText(desc);
        nameEditText.setText(name);

       // updateText();
        checkBox.setChecked(crud.getItems().get(position).getFlag());
        bm= new ConnectionReceiver();

    }
    //onRESUME
    @Override
    protected void onResume() {
        super.onResume();
        calendartext.setText(crud.getItems().get(position).getDateFormat());
    }

    //CHECK BOX
    public void onClickCheckbox (View  view){
        if(isNetworkAvailable()) // if it is available
            statuscheck = checkBox.isChecked();
        else {// if there is no
            checkBox.setChecked(crud.getItems().get(position).getFlag());
        }

    }
    // CHECK INTERNET
    private boolean isNetworkAvailable() { // returns true if we have internet return false other wise
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
   // UPDATE BUTTON
    public void updateOnClick (View view) {
        Item item = new Item(); // store new item
        // get updated item
        desc = descEditText.getText().toString(); // current description
        name = nameEditText.getText().toString();
        dateformat = (String) calendartext.getText(); // get the text from the calendertext

        item.setDescription(desc); // place new description
        item.setName(name); // restore name
        item.setDateFormat(dateformat); // set the date
        item.setFlag(statuscheck);

        Calendar calInitial = Calendar.getInstance(); // initial calender

            if (crud.update(position, item)) { // remove last item and gives it a new one
                nameEditText.setText(name);
                descEditText.setText(desc);
                calendartext.setText(dateformat);
                Toast.makeText(this, "UPDATE ", Toast.LENGTH_SHORT).show();
            }

        if(cal.compareTo(calInitial) <= 0) { // compare current to pick
            Toast.makeText(getApplicationContext(),
                    "You didn't set an appropriate date/time",
                    Toast.LENGTH_LONG).
                    show();
        }else{
            setAlarm(cal);
            Toast.makeText(getApplicationContext(),
                    "alarm has been set",
                    Toast.LENGTH_LONG).
                    show();
        }


    }
    // DELETE BUTTON
    public void deleteOnClick (View view) {
        if(crud.delete(position)){
            ToDo.this.finish();//throws away cards
            Toast.makeText(this,"DELETE", Toast.LENGTH_SHORT).show();
        }
        if(alarmManager != null) // you can only cancel something that is there
            alarmManager.cancel(pendingIntent);

    }


    //  SET ALARM
    private void setAlarm(Calendar date){
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("DESCR",desc);
        pendingIntent = PendingIntent.getBroadcast(this, AlamCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }
    // selecting on calender
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            cal.set(Calendar.YEAR, y);
            cal.set(Calendar.MONTH, m);
            cal.set(Calendar.DAY_OF_MONTH, d);
           updateText(); // update date
        }
    };
    // selecting on time
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker timePicker, int h, int min) {
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, min);
            cal.set(Calendar.SECOND, 0);
            updateText();    // update time
        }
    };
    public void calenderOnClick(View view) {
        // shows the calender dialog
        new DatePickerDialog(this,date,cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void timeOnClick(View view) {
        // show the time dialog
        new TimePickerDialog(this, time, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
    }

    private void updateText(){
        calendartext.setText(formatDateTime.format(cal.getTime()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(bm);  // disable broadcast reviever
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter =new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
           registerReceiver(bm, intentFilter);

    }
}
