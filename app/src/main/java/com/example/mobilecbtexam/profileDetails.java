package com.example.mobilecbtexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sherif146 on 10/06/2017.
 */

public class profileDetails extends Fragment {
    TextView txtName, txtUsername, txtDate;
    String full_name, username,date_r;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profileview, container, false);

        txtName = (TextView) rootView.findViewById(R.id.txtName);
        txtUsername = (TextView) rootView.findViewById(R.id.txtUsername);
        txtDate = (TextView) rootView.findViewById(R.id.timeLeft);

        Intent intent = getActivity().getIntent();
        full_name = intent.getStringExtra("FULL_NAME");
        username = intent.getStringExtra("USERNAME");

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        date_r = df.format(Calendar.getInstance().getTime());

        txtName.setText(full_name);
        txtUsername.setText(username);
        txtDate.setText(date_r);

        return rootView;
    }
}
