package com.example.mobilecbtexam;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAccount extends AppCompatActivity {
    TextView browse, useCamera,btnClose;
    Button saveRecord;
    EditText fullName, userName, password;
    ImageView profilePic;
    SQLiteHelper sqLiteHelper;
    String imageSelected = "No";
    ProgressDialog pd;
    AlertDialog.Builder builder;
    Uri FileUri;
    final int REQUEST_CODE_GALLERY=999;
    final int REQUEST_CODE_CAMERA=777;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        pd = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        getInitials();
        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        //browse picture
       /** browse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        CreateAccount.this,new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },REQUEST_CODE_GALLERY
                );
               /**Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(galleryintent,RESULT_LOAD_IMAGE);
            }
        });

        //snap picture
        useCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              /**  if(isDeviceSupportCamera()){ //check if system support cammera
                   Intent galleryintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(galleryintent,REQUEST_CODE_GALLERY);**/

                    /**ActivityCompat.requestPermissions(
                            CreateAccount.this,new String[]{
                                    Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },REQUEST_CODE_CAMERA
                    );
                   // captureImage();
                }
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
            }
        });**/

        /**LinearLayout lin = (LinearLayout) findViewById(R.id.scroll);
        lin.setOnHoverListener(new View.OnHoverListener(){
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {
                snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                textView.setVisibility(View.INVISIBLE);
                return false;
            }
        });**/

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
                                CreateAccount.this,new String[]{
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

        //save record
        saveRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //check for empty boxes
                String fullname = fullName.getText().toString();
                String username = userName.getText().toString();
                String userPassword = password.getText().toString();

                String email = "No Email";

                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Processing Details ...");
                pd.setIndeterminate(true);
                pd.setCancelable(true);
                pd.show();

                //verify if all details are provided
                if(fullname.isEmpty() || username.isEmpty() || userPassword.isEmpty() || imageSelected.equals("No")){
                    //dont save
                    pd.hide();
                    builder.setMessage("Error: Some Field are Empty !");
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
                }else{
                    //save
                    sqLiteHelper.insertData(
                            fullname,email,username,userPassword,imageViewToByte(profilePic)
                    );
                    Toast.makeText(getApplicationContext(),"Account Saved Successfully !!",Toast.LENGTH_LONG).show();

                    //move to home Screen
                    byte [] imagesPics = imageViewToByte(profilePic);

                    Intent intent = new Intent(getApplicationContext(), AccountHome.class);
                    intent.putExtra("FULL_NAME", fullname);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("PASSWORD", userPassword);
                    intent.putExtra("PROF_PICS", imagesPics);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }
            }
        });
    }

    //check if device use camera
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    //capturing the image with camera
    /*
 * Capturing Camera Image will lauch camera app requrest image capture
 */

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
        //browse = (TextView) findViewById(R.id.btnChoose);
       // useCamera = (TextView) findViewById(R.id.btnSnap_pics);
        saveRecord = (Button) findViewById(R.id.btnSave);

        fullName = (EditText) findViewById(R.id.fullname);
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
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
    public void onStart(){
        LinearLayout lin = (LinearLayout) findViewById(R.id.scroll);
        lin.setOnHoverListener(new View.OnHoverListener(){
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        super.onStart();
    }
    @Override
    public void onBackPressed() {

     /**   if(snackbar.isShown()){
            snackbar.dismiss();
        }else {**/
            Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
       // }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //when browse to gallery
        snackbar.dismiss();
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profilePic.setImageBitmap(bitmap);
                imageSelected = "Yes";
                Toast.makeText(getApplicationContext(),"Image Selected !!",Toast.LENGTH_LONG).show();

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        //when use device camera

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);
            imageSelected = "Yes";
            Toast.makeText(getApplicationContext(),"Image Selected !!",Toast.LENGTH_LONG).show();

        }

        /**if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try{
                /**BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(FileUri.getPath());
                profilePic.setImageBitmap(bitmap);

                imageSelected = "Yes";
                Toast.makeText(getApplicationContext(),"Image Selected !!",Toast.LENGTH_LONG).show();**/
              /**  InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profilePic.setImageBitmap(bitmap);
                imageSelected = "Yes";
                Toast.makeText(getApplicationContext(),"Image Selected !!",Toast.LENGTH_LONG).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }**/
        super.onActivityResult(requestCode, resultCode, data);
    }

    // menu issued
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
      /**  Intent intent = new Intent (CreateAccount.this,CreateAccount.class);
        startActivity(intent);
        finish();**/
    }
    public void user_login(){
        Intent intent = new Intent (CreateAccount.this,LoginScreen.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
    public void view_user(){
        Intent intent = new Intent (CreateAccount.this,AllUsersView.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

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
