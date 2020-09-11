package com.example.mobilecbtexam;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AccountHome extends AppCompatActivity {
    TextView userName, fullName,btnSave,btnSaveTwo,btnClose;
    ImageView prof_pics;
    String username, full_name,  password;
    byte [] image_data;
    AlertDialog.Builder builder;
    ImageView profilePic,profile_image;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    Uri FileUri;
    final int REQUEST_CODE_GALLERY=999;
    final int REQUEST_CODE_CAMERA=777;
    Snackbar snackbar;
    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);

        sqLiteHelper = new SQLiteHelper(this,null,null,1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        userName = (TextView) findViewById(R.id.username);
        fullName = (TextView) findViewById(R.id.fullname);
        prof_pics = (ImageView) findViewById(R.id.profile_pic);
        btnSave = (TextView) findViewById(R.id.btnSave);
        btnSaveTwo = (TextView) findViewById(R.id.btnSave_two);

      //  btnClose = (TextView) findViewById(R.id.btnClose);
        //collect intent Values
        full_name = intent.getStringExtra("FULL_NAME");
        username = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");
        image_data = intent.getByteArrayExtra("PROF_PICS");

        //set the values
        userName.setText("User ID : "+username);
        fullName.setText("Name : " + full_name);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
        prof_pics.setImageBitmap(bitmap);


        SharedPreferences SharedPreferences = getSharedPreferences("STORES",MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPreferences.edit();
        editor.putString("userNamePref",username);
        editor.commit();


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
                Dialog d = new Dialog(AccountHome.this);
                d.setTitle(full_name);
                d.setContentView(snackView);
                d.show();
            }
        });


        // Toast.makeText(getApplicationContext(), "" + new_title, Toast.LENGTH_LONG).show();**/



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_cbt();
            }
        });
        btnSaveTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_cbt();
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.browse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                textView.setVisibility(View.INVISIBLE);
                View snackView = getLayoutInflater().inflate(R.layout.browsefile, null);
                layout.addView(snackView, 0);
                snackbar.show();

                TextView fab = (TextView) snackView.findViewById(R.id.btnChoose);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(
                                AccountHome.this,new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },REQUEST_CODE_GALLERY
                        );
                    }
                });

                TextView fab1 = (TextView) snackView.findViewById(R.id.btnCamera);
                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
                    }
                });

                btnClose = (TextView) snackView.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
            }
        });

    }

    private void captureImage() {
        String imagename = "urPics.jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + imagename);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        FileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
        // start the image capture Intent
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    //convert image to byte
    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void getInitials(){
        profilePic = (ImageView) findViewById(R.id.profile_pic);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(),"You don't have permission to acces Gallery",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if(requestCode == REQUEST_CODE_CAMERA){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                captureImage();
            }else{
                Toast.makeText(getApplicationContext(),"You don't have permission to acces Camera ",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //when browse to gallery
        getInitials();
        snackbar.dismiss();
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profilePic.setImageBitmap(bitmap);
                image_data =  imageViewToByte(profilePic);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        //when use device camera

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);
            image_data = imageViewToByte(profilePic);
        }
        initNavigationDrawer();
       sqLiteHelper.updateProfilePhoto(image_data, username);

        super.onActivityResult(requestCode, resultCode, data);
    }

    //
    @Override
    public void onBackPressed() {
        /**   if(snackbar.isShown()){
         snackbar.dismiss();
         }else {**/
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                verify_close();
            }
      //  }
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


    // menu issued
    public void view_cbt(){
        Intent intent = new Intent (AccountHome.this,ViewCbt.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }
    public void start_cbt(){
        Intent intent = new Intent(AccountHome.this, CbtWindow.class);
        intent.putExtra("FULL_NAME", full_name);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("PROF_PICS", image_data);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }
    public void view_Account(){
        //Intent intent = new Intent (AccountHome.this,AllUsersView.class);
        //startActivity(intent);
    }
    public void verify_close(){

        builder.setMessage("Do You Really Want to Sign Out ? ");
        builder.setTitle("P C N L CBT");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dialogInterface.cancel();
                Intent intent = new Intent(AccountHome.this, HomeScreen.class);
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

