package com.example.mobilecbtexam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    Button saveRecord;
    EditText fullName, userName, password;
    ProgressDialog pd;
    AlertDialog.Builder builder;
    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        pd = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        getInitials();
        //login users

        saveRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //check for empty boxes
                //String fullname = fullName.getText().toString();
                String username = userName.getText().toString();
                String userPassword = password.getText().toString();

                String email = "No Email";


                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Processing Details ...");
                pd.setIndeterminate(true);
                pd.setCancelable(true);
                pd.show();

                //verify if all details are provided
                if(username.isEmpty() || userPassword.isEmpty()){
                    //dont save
                    pd.hide();
                    builder.setMessage("Error: Some Field are Empty Verify!");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    alert.show();
                }
                else
                {
                    pd.show();
                    //save
                    Cursor cursor = sqLiteHelper.userLogin(username,userPassword);
                    String UserName = "", PassWord = "", FullName = "";
                    byte[] imageP=null;
                    if(cursor.getCount() <= 0 ){

                        pd.hide();

                        builder.setMessage("Error: Invalid User Name or Password !");
                        builder.setCancelable(false);
                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                        alert.show();
                    }else {
                        cursor.moveToFirst();

                        FullName = cursor.getString(1);
                        UserName = cursor.getString(3);
                        PassWord = cursor.getString(4);
                        imageP = cursor.getBlob(5);

                        Toast.makeText(getApplicationContext(), "Login Successfully !!", Toast.LENGTH_LONG).show();
                        //move to home Screen
                        Intent intent = new Intent(getApplicationContext(), AccountHome.class);
                        intent.putExtra("FULL_NAME", FullName);
                        intent.putExtra("USERNAME", UserName);
                        intent.putExtra("PASSWORD", PassWord);
                        intent.putExtra("PROF_PICS", imageP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    }
                }
            }
        });
    }
    public void getInitials(){
        saveRecord = (Button) findViewById(R.id.btnSave);
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (LoginScreen.this,HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Close PCNL Cbt ? ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
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
        Intent intent = new Intent (LoginScreen.this,CreateAccount.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
      //  finish();
    }
    public void user_login(){
      /**  Intent intent = new Intent (LoginScreen.this,LoginScreen.class);
        startActivity(intent);
        finish();**/
    }
    public void view_user(){
        Intent intent = new Intent (LoginScreen.this,AllUsersView.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
       // finish();
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

        return super.onOptionsItemSelected(item);
    }
}
