package com.example.mobilecbtexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CbtWindow extends AppCompatActivity {
    //interface controls
    TextView question, time_left,no_left, time_leftd,no_leftd;
    Button previous, next, submit,submitD,previousD,nextD;
    RadioButton option_1, option_2,option_3,option_4;
    RadioGroup radioGroup;
    //questions movements and database
    SQLiteHelper sqLiteHelper;
    String choice="O";
    int currentQuestion;
    public static Cursor allQuestion;

    //timer variable
    private static final String FORMAT = "%02d:%02d:%02d";
    int seconds , minutes;
    String timing_exam = null;
    String time = null;
    //dialog boxes
    ProgressDialog pd;
    AlertDialog.Builder builder;

    //delaying process
    private static int SPLASH_TIME_OUT = 1000;//1seconds

    //intents variables
    String username, full_name,  password;
    byte [] image_data;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbt_window);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        initializeControls();

        //collect intent Values
        Intent intent = getIntent();
        full_name = intent.getStringExtra("FULL_NAME");
        username = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");
        image_data = intent.getByteArrayExtra("PROF_PICS");

        //event timing
        new CountDownTimer(1800000, 1000) { // 30 minutes (30 * 60 * 1000)adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timing_exam = String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                time_left.setText("Time Left : "+timing_exam);
                time_leftd.setText("Time Left : "+timing_exam);
                if(timing_exam.equals("00:10:59")){
                    Toast.makeText(getApplicationContext(), "You Have "+timing_exam+" Minute(s) Left ", Toast.LENGTH_LONG).show();
                }

                if(timing_exam.equals("00:05:59")){
                    Toast.makeText(getApplicationContext(), "You Have "+timing_exam+" Minute(s) Left ", Toast.LENGTH_LONG).show();
                }

                if(timing_exam.equals("00:1:59")){
                    Toast.makeText(getApplicationContext(), "You Have "+timing_exam+" Minute(s) Left ", Toast.LENGTH_LONG).show();
                }
                if(timing_exam.equals("00:0:59")){
                    Toast.makeText(getApplicationContext(), "You Have "+timing_exam+" Second(s) Left ", Toast.LENGTH_LONG).show();
                }
            }

            public void onFinish() {
                time = timing_exam;
                saveRecord();
            }
        }.start();

        //ends here

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        }, SPLASH_TIME_OUT);

        pd = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption();
                NextQuestion();
            }
        });
        nextD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption();
                NextQuestion();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption();
                previousQuestion();
            }
        });
        previousD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption();
                previousQuestion();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = timing_exam;
                confirm_submit();
            }
        });
        submitD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = timing_exam;
                confirm_submit();
            }
        });

        //option is selected

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            int rows;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case -1:
                       // radioGroup.clearCheck();
                        break;
                    case R.id.option_1:
                        choice = "A";
                        rows = sqLiteHelper.updateAnswer(choice,currentQuestion);
                        break;
                    case R.id.option_2:
                        choice = "B";
                        rows = sqLiteHelper.updateAnswer(choice,currentQuestion);
                        break;
                    case R.id.option_3:
                        choice = "C";
                        rows = sqLiteHelper.updateAnswer(choice,currentQuestion);
                        break;
                    case R.id.option_4:
                        choice = "D";
                        rows = sqLiteHelper.updateAnswer(choice,currentQuestion);
                        break;

                }
            }
        });

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
                Dialog d = new Dialog(CbtWindow.this);
                d.setTitle(full_name);
                d.setContentView(snackView);
                d.show();
            }
        });
    }

    //navigation
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            time = timing_exam;
            confirm_submit();
        }
    }
    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){

                    case R.id.prevN:
                        drawerLayout.closeDrawers();
                        selectedOption();
                        previousQuestion();
                        break;
                    case R.id.nextN:
                        drawerLayout.closeDrawers();
                        selectedOption();
                        NextQuestion();
                        break;
                    case R.id.subN:
                        drawerLayout.closeDrawers();
                        confirm_submit();
                        break;
                    case R.id.action_exitN:
                        drawerLayout.closeDrawers();
                        verify_close();
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


    //confirm submit
    public void confirm_submit(){
        builder.setMessage("Do You Really Want to Submit Exam ? \n You Still Have " + time + " Left !");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // dialogInterface.cancel();
                saveRecord();
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
    //submit and save exam
    public void saveRecord(){
        //intent to call for the window that display result
        Intent intent = new Intent(getApplicationContext(), CbtResult.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        intent.putExtra("TIMECOMPLETE", time);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
    public void initializeControls(){
        //textViews
        question = (TextView) findViewById(R.id.txtQuestion);
        time_left = (TextView) findViewById(R.id.timeLeft);
        no_left = (TextView) findViewById(R.id.noLeft);

        time_leftd = (TextView) findViewById(R.id.timeLeftd);
        no_leftd = (TextView) findViewById(R.id.noLeftd);

        //navigation buttons
        previous = (Button) findViewById(R.id.btnPrevious);
        previousD = (Button) findViewById(R.id.btnPreviousD);

        next = (Button) findViewById(R.id.btnNext);
        nextD = (Button) findViewById(R.id.btnNextD);

        submit = (Button) findViewById(R.id.btnSubmit);
        submitD = (Button) findViewById(R.id.btnSubmitD);

        //options
        option_1 = (RadioButton) findViewById(R.id.option_1);
        option_2 = (RadioButton) findViewById(R.id.option_2);
        option_3 = (RadioButton) findViewById(R.id.option_3);
        option_4 = (RadioButton) findViewById(R.id.option_4);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }
    //find selected option and do update
    public void selectedOption(){
       radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
      /**  int checkedRadioButtonID = radioGroup.getCheckedRadioButtonId();
        choice = "O";
        if(checkedRadioButtonID == R.id.option_1){
            choice = "A";
        }
        if(checkedRadioButtonID == R.id.option_2){
            choice = "B";
        }
        if(checkedRadioButtonID == R.id.option_3){
            choice = "C";
        }
        if(checkedRadioButtonID == R.id.option_4){
            choice = "D";
        }
        int rows = sqLiteHelper.updateAnswer(choice,currentQuestion);**/
    }

    //display the question and options
    public void displayValues(int currentQuestion_f){
        //allQuestion.close();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
       // radioGroup.clearCheck();
        allQuestion = sqLiteHelper.getQuestions(currentQuestion_f);
        String choice = null;
        allQuestion.moveToFirst();
           question.setText(allQuestion.getString(allQuestion.getColumnIndex("Question")));
           option_1.setText(allQuestion.getString(allQuestion.getColumnIndex("Option_1")));
           option_2.setText(allQuestion.getString(allQuestion.getColumnIndex("Option_2")));
           option_3.setText(allQuestion.getString(allQuestion.getColumnIndex("Option_3")));
           option_4.setText(allQuestion.getString(allQuestion.getColumnIndex("Option_4")));
           choice = allQuestion.getString(allQuestion.getColumnIndex("Choice"));
        radioGroup.clearCheck();
        String opt1 ="A";String opt2 ="B";String opt3 ="C";String opt4 ="D";String opt5 ="O";
        if(choice.equals(opt1)){
            option_1.setChecked(true);
            option_2.setChecked(false);
            option_3.setChecked(false);
            option_4.setChecked(false);
        }
        if(choice.equals(opt2)){
            option_1.setChecked(false);
            option_2.setChecked(true);
            option_3.setChecked(false);
            option_4.setChecked(false);
        }
        if(choice.equals(opt3)){
            option_1.setChecked(false);
            option_2.setChecked(false);
            option_3.setChecked(true);
            option_4.setChecked(false);
        }
        if(choice.equals(opt4)){
            option_1.setChecked(false);
            option_2.setChecked(false);
            option_3.setChecked(false);
            option_4.setChecked(true);
        }
        allQuestion.close();
    }


    //move previous
    public  void previousQuestion(){
        if(currentQuestion == 1){
            currentQuestion = 80;
        }else{
            currentQuestion = currentQuestion - 1;
        }
        no_left.setText(String.valueOf(currentQuestion) + " / 80");
        no_leftd.setText(String.valueOf(currentQuestion) + " / 80");
        displayValues(currentQuestion);
    }
    //move Next
    public  void NextQuestion(){
        if(currentQuestion == 80){
            currentQuestion = 1;
        }else{
            currentQuestion = currentQuestion + 1;
        }
        no_left.setText(String.valueOf(currentQuestion) + " / 80");
        no_leftd.setText(String.valueOf(currentQuestion) + " / 80");
        displayValues(currentQuestion);
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            currentQuestion = 1;
            displayValues(currentQuestion);
            no_left.setText(String.valueOf(currentQuestion) + " / 80");
            no_leftd.setText(String.valueOf(currentQuestion) + " / 80");
            super.onPostExecute(s);
        }
    }

    //menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cbt_options, menu);
        return true;
    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Exit C B T ? \n All Unsaved Data Wil be Lost. ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
               // System.exit(0);
                Intent intent = new Intent(CbtWindow.this, AccountHome.class);
                intent.putExtra("FULL_NAME", full_name);
                intent.putExtra("USERNAME", username);
                intent.putExtra("PASSWORD", password);
                intent.putExtra("PROF_PICS", image_data);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            selectedOption();
            NextQuestion();
            return true;
        }
        if (id == R.id.action_exit) {
            verify_close();
            return true;
        }
        if (id == R.id.prev) {
            selectedOption();
            previousQuestion();
            return true;
        }
        if (id == R.id.sub) {
            confirm_submit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
