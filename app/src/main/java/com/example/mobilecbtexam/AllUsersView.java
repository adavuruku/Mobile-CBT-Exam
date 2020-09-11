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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

//public class AllUsersView extends AppCompatActivity implements AdapterView.OnItemClickListener{
public class AllUsersView extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //GridView gridView;
    ArrayList<Food> list;
    ListView lv;
    GridView gv;
    ProgressDialog pd;
    TextView text;
    ViewStub lvt,gvt;
    int currentviewmode = 0;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    private static int SPLASH_TIME_OUT = 2000;//5seconds
    SQLiteHelper sqLiteHelper;
    AlertDialog.Builder builder;
    ImageView imv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_view);
        builder = new AlertDialog.Builder(this);
        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        list = new ArrayList<>();

        lvt = (ViewStub) findViewById(R.id.stub_list);
        gvt = (ViewStub) findViewById(R.id.stub_grid);

        lvt.inflate();
        gvt.inflate();
       // text = (TextView) findViewById(R.id.text);
        lv = (ListView) findViewById(R.id.list);
        gv = (GridView) findViewById(R.id.mygridView);

       lv.setOnItemClickListener((AdapterView.OnItemClickListener) this);
       gv.setOnItemClickListener((AdapterView.OnItemClickListener) this);


        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Preparing Request ...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        SharedPreferences SharedPreferences = getSharedPreferences("ViewMode",MODE_PRIVATE);
        currentviewmode = SharedPreferences.getInt("currentviewmode",VIEW_MODE_LISTVIEW);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        },SPLASH_TIME_OUT);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final  byte[] pics_pat = list.get(position).getImage();
        final  String new_title = list.get(position).getName();
        final  String new_user = list.get(position).getUsername();

        View snackView = getLayoutInflater().inflate(R.layout.dialogview, null);
        imv = (ImageView) snackView.findViewById(R.id.diaprofile_pic);
        Bitmap bitmap = BitmapFactory.decodeByteArray(pics_pat,0,pics_pat.length);
        imv.setImageBitmap(bitmap);
        TextView myvi = (TextView) snackView.findViewById(R.id.txtUser);
        myvi.setText(new_title);

        Dialog d = new Dialog(this);
        d.setTitle(new_title);
        d.setContentView(snackView);
        d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        d.show();
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AllUsersView.this,ProfileView.class);
                intent.putExtra("FULL_NAME", new_title);
                intent.putExtra("USERNAME", new_user);
                intent.putExtra("PROF_PICS", pics_pat);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
              //  Toast.makeText(getApplicationContext(),"Image Selected !!",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent (AllUsersView.this,HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    public void switchView(){
        if(VIEW_MODE_LISTVIEW == currentviewmode){
            lvt.setVisibility(View.VISIBLE);
            gvt.setVisibility(View.GONE);
            ListViewAdapter lva = new ListViewAdapter(getApplicationContext(), R.layout.list_item, list);
            lv.setAdapter(lva);
        }else{
            gvt.setVisibility(View.VISIBLE);
            lvt.setVisibility(View.GONE);
            GridViewAdapter gva = new GridViewAdapter(getApplicationContext(), R.layout.grid_item, list);
            gv.setAdapter(gva);
        }
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {
        String result;
        String[] data;
        String all = null;
        protected String doInBackground(String... params) {

            //return readURL(address);
            try{

                Cursor cursor = sqLiteHelper.getData();
                list.clear();
                while (cursor.moveToNext()){

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("accountName"));
                    String username = cursor.getString(cursor.getColumnIndex("accountId"));
                    byte[] image = cursor.getBlob(cursor.getColumnIndex("accountPics"));
                   //  all += name + ",";
                    list.add(new Food(name,username,image,id));
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
            return all;
        }
        @Override
        protected void onPostExecute(String content) {

            try {
              // text.setText(all);
                pd.hide();
                switchView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    // menu issued
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view_user, menu);
        return true;
    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Sign Out ? ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
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
    public void new_user(){
        Intent intent = new Intent (AllUsersView.this,CreateAccount.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void user_login(){
        Intent intent = new Intent (AllUsersView.this,LoginScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void view_user(){
        /**Intent intent = new Intent (AllUsersView.this,AllUsersView.class);
        startActivity(intent);
        finish();**/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            new_user();
            return true;
        }
        if (id == R.id.action_exit) {
            verify_close();
            return true;
        }
        if (id == R.id.action_login) {
            user_login();
            return true;
        }
        if (id == R.id.action_view) {
            view_user();
            return true;
        }

        if (id == R.id.switchView) {
            if(VIEW_MODE_LISTVIEW == currentviewmode){
                currentviewmode = VIEW_MODE_GRIDVIEW;
            }else{
                currentviewmode = VIEW_MODE_LISTVIEW;
            }
            SharedPreferences SharedPreferences = getSharedPreferences("ViewMode",MODE_PRIVATE);
            SharedPreferences.Editor editor = SharedPreferences.edit();
            editor.putInt("currentviewmode",currentviewmode);
            editor.commit();

            switchView();
        }

        return super.onOptionsItemSelected(item);
    }
}
