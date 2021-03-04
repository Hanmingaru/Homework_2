package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListFragment listFragment;
    Button viewCtrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);

        Bundle b1 = getIntent().getExtras();
        ArrayList<String> getList = b1.getStringArrayList("lapstrings");
        listFragment.editText(getList);
        viewCtrl = (Button) findViewById(R.id.controlButton);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("listView",listFragment.listView.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        listFragment.listView.setText(inState.getString("listView"));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void viewControl(View view){
        finish();
    }

}