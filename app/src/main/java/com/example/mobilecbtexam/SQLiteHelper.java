package com.example.mobilecbtexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by sherif146 on 12/04/2017.
 */
public class SQLiteHelper extends SQLiteOpenHelper{
    // Database Info
    private static final String DATABASE_NAME = "newWork";
    private static final int DATABASE_VERSION = 1;
    Context context;
    // Table Names
    private static final String TABLE_QUESTION = "cbtQuestions";
    private static final String TABLE_USERS = "usersInfo";
    private static final String TABLE_RECORDS = "examRecords";

    // usersInfo Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_USER_NAME = "accountName";
    private static final String KEY_USER_EMAIL = "accountEmail";
    private static final String KEY_USER_ID = "accountId";
    private static final String KEY_USER_PASSWORD = "accountPassword";
    private static final String KEY_USER_PICS= "accountPics";

    // cbtQuestions Table Columns
    private static final String KEY_Q_ID = "id";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_OPTION_1 = "Option_1";
    private static final String KEY_OPTION_2 = "Option_2";
    private static final String KEY_OPTION_3 = "Option_3";
    private static final String KEY_OPTION_4= "Option_4";
    private static final String KEY_ANSWER= "Answer";
    private static final String KEY_CHOICE= "Choice";

    // examRecords Table Columns
    private static final String KEY_R_ID = "id";
    private static final String KEY_RECORD_USERNAME = "Username";
    private static final String KEY_RECORD_DATE = "Date_cbt";
    private static final String KEY_RECORD_SCORE = "Score";
    private static final String KEY_RECORD_TIME = "Time";
    private static final String KEY_RECORD_PERCENTAGE = "Percentage";

    private static SQLiteHelper sInstance;

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION +
                "(" +
                KEY_Q_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                KEY_QUESTION + " TEXT, " +
                KEY_OPTION_1 + " VARCHAR, " +
                KEY_OPTION_2 + " VARCHAR, " +
                KEY_OPTION_3 + " VARCHAR, " +
                KEY_OPTION_4 + " VARCHAR, " +
                KEY_ANSWER + " VARCHAR, " +
                KEY_CHOICE + " VARCHAR" +
                ")";

        String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                KEY_USER_NAME + " VARCHAR, " +
                KEY_USER_EMAIL + " VARCHAR, " +
                KEY_USER_ID + " VARCHAR, " +
                KEY_USER_PASSWORD + " VARCHAR, " +
                KEY_USER_PICS + " BLOG" +
                ")";


        String CREATE_TABLE_RECORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_RECORDS +
                "(" +
                KEY_R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                KEY_RECORD_USERNAME + " VARCHAR, " +
                KEY_RECORD_DATE + " VARCHAR, " +
                KEY_RECORD_SCORE + " VARCHAR, " +
                KEY_RECORD_TIME + " VARCHAR," +
                KEY_RECORD_PERCENTAGE + " VARCHAR" +
                ")";
     //   sqLiteDatabase.openOrCreateDatabase(DATABASE_NAME,null);

        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_RECORDS);
    }

    //when database is upgraded - version changed to higher version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            //recreate the tables
            onCreate(sqLiteDatabase);
        }
    }

    //when database is downgraded - version changed to lower version
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            //recreate the tables
            onCreate(db);
        }
    }

    public int updateAnswer(String choice,int id){
        SQLiteDatabase database = getWritableDatabase();
       /** String sql = "UPDATE "+TABLE_QUESTION + " SET " + KEY_CHOICE + " = '" + choice +"' WHERE " + KEY_Q_ID +" = '" + id +"' Limit 1";
        database.update(sql, null);**/
        ContentValues values = new ContentValues();
        values.put(KEY_CHOICE, choice);
        int rows = database.update(TABLE_QUESTION, values, KEY_Q_ID + "= ?", new String[]{String.valueOf(id)});
        return rows;
    }


    //update all questions choice to O
    public void updateProfilePhoto(byte[] image, String username){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE "+ TABLE_USERS + " SET " + KEY_USER_PICS + " = ? WHERE " + KEY_USER_ID + " = ? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1, image);
        statement.bindString(2, username);
        statement.execute();
    }


    //update all questions choice to O
    public void updateAnswer_Default(){
        SQLiteDatabase database = getWritableDatabase();
        String choice = "O";
        String sql = "UPDATE "+ TABLE_QUESTION + " SET " + KEY_CHOICE + " = ? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, choice);
        statement.execute();
     /**   ContentValues values = new ContentValues();
        values.put(KEY_CHOICE, choice);
        int rows = database.update(TABLE_QUESTION, values, null, null);
        return rows;**/
    }
//add exam record
    public void addNewExamRecord(String username, String date_r,
                           String score, String percentage,String time_s) {
        // Create and/or open the database for writing
        SQLiteDatabase database = getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_RECORDS+" VALUES (NULL,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, username);
        statement.bindString(2, date_r);
        statement.bindString(3, score);
        statement.bindString(4, time_s);
        statement.bindString(5, percentage);
        statement.executeInsert();
    }
//ADD A QUESTION
    public Cursor verifyQuestionExist(String question){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_QUESTION + " WHERE "+KEY_QUESTION +"= '" + question +"' Limit 1";
        return database.rawQuery(sql, null);
    }

    public void addNewQuestion(String question, String option_1,
                               String option_2, String option_3,String option_4, String answer, String choice){
        //verify if same user exist
        Cursor cursor = verifyQuestionExist(question);
        SQLiteDatabase database = getWritableDatabase();
        if(cursor.getCount() >= 1){
            //update
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION, question);
            values.put(KEY_OPTION_1, option_1);
            values.put(KEY_OPTION_2, option_2);
            values.put(KEY_OPTION_3, option_3);
            values.put(KEY_OPTION_4, option_4);
            values.put(KEY_ANSWER, answer);
            values.put(KEY_CHOICE, choice);

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = database.update(TABLE_QUESTION, values, KEY_QUESTION + "= ?", new String[]{question});
        }else {
            //insert
            String sql = "INSERT INTO "+TABLE_QUESTION+" VALUES (NULL,?,?,?,?,?,?,?)";
            SQLiteStatement statement =database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, question);
            statement.bindString(2, option_1);
            statement.bindString(3, option_2);
            statement.bindString(4, option_3);
            statement.bindString(5, option_4);
            statement.bindString(6, answer);
            statement.bindString(7, choice);
            statement.executeInsert();
        }
    }

//CREATE AN ACCOUNT
public Cursor verifyUserExist(String username){
    SQLiteDatabase database = getReadableDatabase();
    String sql = "SELECT * FROM "+TABLE_USERS + " WHERE "+KEY_USER_ID +"= '" + username +"' Limit 1";
    return database.rawQuery(sql, null);
}
    public void insertData(String fullname, String email,String username, String userPassword, byte[] image){
        //verify if same user exist
        Cursor cursor = verifyUserExist(username);
        SQLiteDatabase database = getWritableDatabase();
        if(cursor.getCount() >= 1){
            //update
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, fullname);
            values.put(KEY_USER_EMAIL, email);
            values.put(KEY_USER_ID, username);
            values.put(KEY_USER_PASSWORD, userPassword);
            values.put(KEY_USER_PICS, image);

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = database.update(TABLE_USERS, values, KEY_USER_ID + "= ?", new String[]{username});
        }else {
            //insert
            String sql = "INSERT INTO "+TABLE_USERS+" VALUES (NULL,?,?,?,?,?)";
            SQLiteStatement statement =database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, fullname);
            statement.bindString(2, email);
            statement.bindString(3, username);
            statement.bindString(4, userPassword);
            statement.bindBlob(5, image);
            statement.executeInsert();
        }
    }

    //HELP LOGIN
    public Cursor userLogin(String USERNAME, String PASSWORD){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_USERS+" WHERE " + KEY_USER_PASSWORD + " = '"+PASSWORD+"' AND " + KEY_USER_ID  +" = '"+USERNAME+"' LIMIT 1";
        return database.rawQuery(sql, null);
    }

    //LIST OF USERS
    public Cursor getData(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_USERS ;
        return database.rawQuery(sql, null);
    }

    //GET ALL QUESTIONS
    public Cursor getAllQuestions(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_QUESTION ;
        return database.rawQuery(sql, null);
    }

    public Cursor getQuestions(int currentQuestion){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + KEY_Q_ID + " = '" + currentQuestion + "'";
        return database.rawQuery(sql, null);
    }

    //GET ALL CBT EXAM
    public Cursor getAllCbt(String username){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_RECORDS + " WHERE " + KEY_RECORD_USERNAME + " = '" + username + "'";
        return database.rawQuery(sql, null);
    }

}
