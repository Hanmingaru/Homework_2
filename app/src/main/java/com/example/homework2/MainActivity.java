package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ControlFragment.OnFragmentListener {
    ControlFragment controlFragment;
    ListFragment listFragment;
    boolean isStopped;
    boolean isReset;
    MyAsyncTask myAsyncTask;
    int counter;
    ArrayList<String> lapList = new ArrayList<String>();
    public static final String LAP_STRINGS = "lapstrings";
    Button lapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlFragment = (ControlFragment) getSupportFragmentManager().findFragmentById(R.id.controlFrag);
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        lapList = new ArrayList<String>();
        myAsyncTask = new MyAsyncTask();
        counter = 0;

        isStopped = true;
        isReset = false;
        lapButton = findViewById(R.id.viewLaps);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",counter);
        outState.putBoolean("isStopped", isStopped);
        outState.putBoolean("isReset", isReset);
        outState.putStringArrayList("lapList", lapList);
        outState.putString("timer", controlFragment.timer.getText().toString());
        outState.putString("startText", controlFragment.start.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        this.counter = inState.getInt("counter");
        this.isStopped = inState.getBoolean("isStopped");
        this.isReset = inState.getBoolean("isReset");
        this.lapList = inState.getStringArrayList("lapList");

        if(listFragment != null) {
            listFragment.editText(lapList);
        }
        controlFragment.start.setText(inState.getString("startText"));
        controlFragment.timer.setText(inState.getString("timer"));
        if(myAsyncTask.getStatus()!= AsyncTask.Status.RUNNING && !isStopped) {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }
    }
    //destroys async task if still running
    @Override
    protected void onDestroy() {
        if(myAsyncTask!=null && myAsyncTask.getStatus()== AsyncTask.Status.RUNNING){
            myAsyncTask.cancel(true);
            myAsyncTask= null;
        }
        super.onDestroy();
    }

    public void onButtonClicked(int infoID){
        if(controlFragment != null){
            //Controls start button
            if(infoID == 0){
                start();
            }
            if(infoID == 1){
                lap();
            }
            if(infoID == 2){
                reset();
            }
        }
    }
    //Starts timer and changes text to stop while running and start while stopped.
    private void start(){
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        isStopped = !isStopped;
        String start_stop = isStopped ? "Start" : "Stop";
        controlFragment.start.setText(start_stop);

        isReset = false;
        if(myAsyncTask.getStatus()!= AsyncTask.Status.RUNNING && !isStopped) {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }
    }
    //adds current time to list.
    private void lap(){
        Toast.makeText(this, "Lap", Toast.LENGTH_SHORT).show();
        //always add timer to list view when lap is clicked
        lapList.add(controlFragment.timer.getText().toString());
        //landscape mode
        if(listFragment != null && listFragment.isInLayout()){
            Log.i("","Landscape");
            listFragment.editText(lapList);
        }
        //portrait mode
        else if(listFragment != null && !listFragment.isInLayout()){
            Log.i("","Portrait");
        }
        else{
            Log.i("Error","Error");
        }
    }
    //resets timer, resets button text, and clears list.
    private void reset(){
        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
        isReset = true;
        isStopped = true;
        counter = 0;
        lapList.clear();
        if(listFragment != null) {
            listFragment.resetList();
        }
        controlFragment.start.setText("Start");
    }
    //onClick method for viewing laps in portrait mode
    public void viewLaps(View view){
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra(LAP_STRINGS, lapList);
        startActivity(intent);
    }

    //Async class to run timer. Pulled from lab5 code.
    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while(!isReset){
                try{
                    //checking if the asynctask has been cancelled, end loop if so
                    if(isCancelled() || isReset) {
                        break;
                    }
                    Thread.sleep(1000);
                    //send count to onProgressUpdate to update UI
                    publishProgress(counter);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            controlFragment.resetTime();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(!isStopped && !isReset){
                Log.i("isStopped is" ,isStopped + "");
                counter++;
                super.onProgressUpdate(counter);
                controlFragment.incTimer(counter);
            }
        }
    }

}