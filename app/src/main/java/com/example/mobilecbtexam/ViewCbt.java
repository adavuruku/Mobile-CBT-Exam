package com.example.mobilecbtexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCbt extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;//5seconds
    SQLiteHelper sqLiteHelper;
    ArrayList<cbtExam> list;
    ListView lv;
    ImageView prof_pics;
    String userNamePref = null;
    ProgressDialog pd;
    AlertDialog.Builder builder;
    //intents variables
    String username, full_name,  password,all;
    byte [] image_data;
    TextView txtall;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cbt);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        builder = new AlertDialog.Builder(this);
        list = new ArrayList<>();

        prof_pics = (ImageView) findViewById(R.id.profile_pic);

        lv = (ListView) findViewById(R.id.list);
        //txtall = (TextView) findViewById(R.id.txtall);
        //collect intent Values
        Intent intent = getIntent();
        full_name = intent.getStringExtra("FULL_NAME");
        username = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");
        image_data = intent.getByteArrayExtra("PROF_PICS");

        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
        prof_pics.setImageBitmap(bitmap);

        SharedPreferences SharedPreferences = getSharedPreferences("STORES",MODE_PRIVATE);
        userNamePref = SharedPreferences.getString(userNamePref,username);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Preparing Request ...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        },SPLASH_TIME_OUT);


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
                Dialog d = new Dialog(ViewCbt.this);
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
            Intent intent = new Intent(ViewCbt.this, AccountHome.class);
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



    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                //Cursor cursor = sqLiteHelper.getAllCbt(username);
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
                    //    all += ", " + username;
                        list.add(new cbtExam(Date_cbt, Score, Percentage, Time, id));
                    }
                    /** }else{
                    all = username;
                }**/

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
            pd.hide();
            //txtall.setText(all);
            ViewUserAdapter lva = new ViewUserAdapter(getApplicationContext(), R.layout.user_item, list);
            lv.setAdapter(lva);
        }
    }

    // menu issued
    public void view_cbt(){
      //  Intent intent = new Intent (ViewCbt.this,ViewCbt.class);
      //  startActivity(intent);
    }
    public void start_cbt(){
        Intent intent = new Intent(ViewCbt.this, CbtWindow.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }
    public void view_Account(){
        Intent intent = new Intent(ViewCbt.this, AccountHome.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Sign Out ? ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
                Intent intent = new Intent(ViewCbt.this, HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
