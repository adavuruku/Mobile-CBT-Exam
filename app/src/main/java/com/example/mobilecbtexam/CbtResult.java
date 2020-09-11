package com.example.mobilecbtexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CbtResult extends AppCompatActivity {
    //interface controls
    TextView txtName, txtUsername,txtPercentage,txtScore,time_left;
    ImageView profile_pic;
    double Cbtscore;
    TextView txtall;
    double percentage;
    String username, full_name,  password,score, time_com, date_r,all;
    byte [] image_data;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    //delaying process
    private static int SPLASH_TIME_OUT = 1000;//1seconds
    SQLiteHelper sqLiteHelper;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbt_result);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this,null,null,1);

        initializeControls();
        //collect intent Values
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        full_name = intent.getStringExtra("FULL_NAME");
        username = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");
        time_com = intent.getStringExtra("TIMECOMPLETE");
        image_data = intent.getByteArrayExtra("PROF_PICS");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        }, SPLASH_TIME_OUT);
        //set the values
     //   setValues();

        initNavigationDrawer();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        ImageView imageV = (ImageView) header.findViewById(R.id.profile_image);
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View snackView = getLayoutInflater().inflate(R.layout.dialogview, null);
                ImageView imv = (ImageView) snackView.findViewById(R.id.diaprofile_pic);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
                imv.setImageBitmap(bitmap);
                TextView myvi = (TextView) snackView.findViewById(R.id.txtUser);
                myvi.setText(full_name);
                Dialog d = new Dialog(CbtResult.this);
                d.setTitle(full_name);
                d.setContentView(snackView);
                d.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            Intent intent = new Intent(CbtResult.this, AccountHome.class);
            intent.putExtra("FULL_NAME", full_name);
            intent.putExtra("USERNAME", username);
            intent.putExtra("PASSWORD", password);
            intent.putExtra("PROF_PICS", image_data);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){

                    case R.id.action_start1:
                        drawerLayout.closeDrawers();
                        start_cbt();
                        break;
                    case R.id.action_exit1:
                        drawerLayout.closeDrawers();
                        verify_close();
                        break;
                    case R.id.action_view_cbt1:
                        drawerLayout.closeDrawers();
                        view_cbt();
                        break;
                    case R.id.action_home1:
                        drawerLayout.closeDrawers();
                        view_Account();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_email.setText(full_name);
        ImageView imageV = (ImageView)header.findViewById(R.id.profile_image);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
        imageV.setImageBitmap(bitmap);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }






    public void setValues(){
        txtUsername.setText("User Name : " + username);
        txtName.setText("Name : " + full_name);
        txtPercentage.setText("Percentage Score : " + Double.toString(percentage) + "%" );
        txtScore.setText("Score : " + String.valueOf(Cbtscore));
        time_left.setText("Time Left : " + time_com);
        txtall.setText("Date : "+date_r);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
        profile_pic.setImageBitmap(bitmap);
    }
    public void initializeControls(){
        //textViews
        txtName = (TextView) findViewById(R.id.txtName);
        time_left = (TextView) findViewById(R.id.timeLeft);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtUsername = (TextView) findViewById(R.id.txtUsername);

        profile_pic =(ImageView) findViewById(R.id.profile_pic);

        txtall = (TextView) findViewById(R.id.txtall);
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            //mark exam and display result
            String Cbtanswer=null, CbtChoice = null;
            Cbtscore = 0;
            Cursor cbtResult = sqLiteHelper.getAllQuestions();
            if(cbtResult.getCount()>=1) {
                cbtResult.moveToFirst();
                while (cbtResult.moveToNext()) {
                    Cbtanswer = cbtResult.getString(cbtResult.getColumnIndex("Answer"));
                    CbtChoice = cbtResult.getString(cbtResult.getColumnIndex("Choice"));
                    all = all + "," + CbtChoice;
                    if (Cbtanswer.equals(CbtChoice)) {
                        Cbtscore = Cbtscore + 1;
                    }
                }
                percentage = Cbtscore > 0 ? ((double) (Cbtscore / 80)) * 100 : 0;

                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                date_r = df.format(Calendar.getInstance().getTime());
                //  DateUtils.
                //save the record to exam record
                sqLiteHelper.addNewExamRecord(username, String.valueOf(date_r), String.valueOf(Cbtscore),Double.toString(percentage), time_com);
                sqLiteHelper.updateAnswer_Default();
            }else{
                all = "No Records !!";
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
           // setValues();
        }

        @Override
        protected void onPostExecute(String s) {
            setValues();
        }
    }

    // menu issued
    public void view_cbt(){
        Intent intent = new Intent (CbtResult.this,ViewCbt.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        //finish();
    }
    public void start_cbt(){
        Intent intent = new Intent(CbtResult.this, CbtWindow.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void view_Account(){
        Intent intent = new Intent(CbtResult.this, AccountHome.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Close PCNL Cbt ? ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
                System.exit(0);
                finish();
                //System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_start) {
            start_cbt();
            return true;
        }
        if (id == R.id.action_exit) {
            verify_close();
            return true;
        }
        if (id == R.id.action_view_cbt) {
            view_cbt();
            return true;
        }
        if (id == R.id.action_home) {
            view_Account();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
