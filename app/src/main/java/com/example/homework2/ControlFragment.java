package com.example.homework2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ControlFragment extends Fragment implements View.OnClickListener{
    Button start;
    Button lap;
    Button reset;
    TextView timer;
    int hours;
    int minutes;
    int seconds;
    private OnFragmentListener mListener;

    public ControlFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        start = (Button) view.findViewById(R.id.startButton);
        lap = (Button) view.findViewById(R.id.lapButton);
        reset = (Button) view.findViewById(R.id.resetButton);
        timer = (TextView) view.findViewById(R.id.timer);
        hours = 0;
        minutes  = 0;
        seconds = 0;

        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentListener){
            this.mListener= (OnFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View view){
        if(view.getId() == start.getId()){
            mListener.onButtonClicked(0);
        }
        else if(view.getId() == lap.getId()){
            mListener.onButtonClicked(1);
        }
        else if(view.getId() == reset.getId()) {
            mListener.onButtonClicked(2);
        }

    }
    //sets time to 0
    public void resetTime(){
        timer.setText(String.format("%02d:%02d:%02d", 0, 0, 0));
    }
    //increments time by one second and updates text
    public void incTimer(int counter){
        seconds = (counter) % 60;
        if(seconds == 0) {
            minutes = (minutes + 1) % 60;
            if(minutes == 0){
                hours =  (hours + 1);
            }
        }
        Log.i("time", counter + "");
        timer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

    }

    //checks for onClick events
    public interface OnFragmentListener{
        void onButtonClicked(int infoID);
    }
}