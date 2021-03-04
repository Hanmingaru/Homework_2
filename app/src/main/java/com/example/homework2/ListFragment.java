package com.example.homework2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    TextView listView;
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (TextView) view.findViewById(R.id.lapList);
        return view;
    }
    public void resetList(){
        if(listView != null) {
            listView.setText("");
        }
    }
    public void editText(ArrayList<String> list){
        StringBuilder sb = new StringBuilder();
        if(list == null || list.isEmpty()){
            return;
        }
        if(!list.isEmpty()) {
            sb.append("1. " + list.get(0));
            for (int i = 1; i < list.size(); i++) {
                sb.append("\n" + (i+1) +". " + list.get(i));
            }
            if(listView != null) {
                listView.setText(sb.toString());
            }
        }
    }
}