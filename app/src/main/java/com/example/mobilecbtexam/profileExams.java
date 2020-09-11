package com.example.mobilecbtexam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sherif146 on 10/06/2017.
 */
public class profileExams extends Fragment {
    private static int SPLASH_TIME_OUT = 1000;//5seconds
    SQLiteHelper sqLiteHelper;
    ArrayList<cbtExam> list;
    ListView lv;
    String username,all;
    TextView txtall;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profileexam, container, false);
        lv = (ListView) rootView.findViewById(R.id.list);
      //  txtall = (TextView) rootView.findViewById(R.id.txtall);
        list = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("USERNAME");

        sqLiteHelper = new SQLiteHelper(getActivity(),null,null,1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        },SPLASH_TIME_OUT);

        return rootView;
    }


    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                Cursor cursor = sqLiteHelper.getAllCbt(username);
                list.clear();
                //if(cursor.getCount()>=1) {
                //cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String username = cursor.getString(cursor.getColumnIndex("Username"));
                    String Date_cbt = "Date : " + cursor.getString(cursor.getColumnIndex("Date_cbt"));
                    String Score = "Score : " + cursor.getString(cursor.getColumnIndex("Score"));
                    String Percentage = "Percentage : " + cursor.getString(cursor.getColumnIndex("Percentage"));
                    String Time = "Time Left : " + cursor.getString(cursor.getColumnIndex("Time"));
                    list.add(new cbtExam(Date_cbt, Score, Percentage, Time, id));
                  //  all += ", " + username;
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
          //  pd.hide();
            //txtall.setText(all);
            ViewUserAdapter lva = new ViewUserAdapter(getActivity(), R.layout.user_item, list);
            lv.setAdapter(lva);
        }
    }
}
