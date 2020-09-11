package com.example.mobilecbtexam;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;//5seconds
    SQLiteHelper sqLiteHelper;
    TextView tvLabel,textView,txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(this,null,null,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute();
            }
        }, SPLASH_TIME_OUT);

        tvLabel = (TextView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.textView);
        txt3 = (TextView) findViewById(R.id.textView2);
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in);
        // start the animation
        tvLabel.startAnimation(animFadeOut);
        textView.startAnimation(animFadeOut);
        txt3.startAnimation(animFadeOut);
        /**AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(tvLabel, "scaleX", 1.0f, 1.1f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(tvLabel, "scaleY", 1.0f, 1.1f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(textView, "scaleX", 1.0f, 1.1f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(textView, "scaleY", 1.0f, 1.1f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(textView, "scaleY", 1.1f, 1.0f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(textView, "scaleY", 1.1f, 1.0f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(tvLabel, "scaleY", 1.1f, 1.0f)
                        .setDuration(1000),
                ObjectAnimator.ofFloat(tvLabel, "scaleY", 1.1f, 1.0f)
                        .setDuration(1000)
        );
        set.start();**/
    }




    class ReadJSON extends AsyncTask<String, Integer, String> {
        String result;
        String[] data;

        protected String doInBackground(String... params) {

            //return readURL(address);
            try {
                    //save the Questions if not saved before !!!
//01
                sqLiteHelper.addNewQuestion("CHARLES BABBAGE MADE THE___________________", "BABBAGE MACHINE", "ELECTRONIC COMPUTER", "ANALYTICAL ENGINE", "CALCULATION ENGINE", "C", "O");
//02
                sqLiteHelper.addNewQuestion("FOLDERS THAT ARE CREATED INSIDE ANOTHER FOLDER ARE CALED _______________", "INSIDE FOLDERS", "SUB FOLDERS", "LAST FOLDERS", "END FOLDER", "B", "O");
//03
                sqLiteHelper.addNewQuestion("THIRD GENERATIONN COMPUTER USES", "INTEGRATED CIRCUIT", "TRANSISTORS", "PROCESSOR", "3RD GEN-VALVES", "A", "O");
//04
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT FOUND INSIDE THE SYSTEM UNIT ", "C.P.U", "RAM", "HARD DISK", "FLOPPY DISK", "D", "O");
//05
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS A WILD CARD CHARACTER", "#", "?", ")", "+", "B", "O");
//06
                sqLiteHelper.addNewQuestion("ALL THIS ICONS CAN BE SEEN IN THE CONTROL PANEL EXCEPT", "ALL PROGRAM", "DATE AND TIME", "SYSTEM", "USER ACCOUNT", "A", "O");
//07
                sqLiteHelper.addNewQuestion("DELETED FILES FROM THE COMPUTER CAN BE RESTORED BACK FROM WHERE ?", "MY COMPUTER", "MY DELETED DOCUMENTS", "RECYCLE BIN", "TRASH BIN", "C", "O");
//08
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT AN EXAMPLE OF ANTI VIRUS ?", "MICROSOFT VIRUS SLAYER ", "MC AFEE", "NORTON", "BITDEFENDER", "A", "O");
//09
                sqLiteHelper.addNewQuestion("HOW CAN YOU QUIT A PROGRAM THAT IS NOT RESPONDING ?", "ALT + SHIFT + CLOSE", "CTRL + ALT + DEL", "CTRL + SHIFT + F4", "CTRL + ALT +F4", "B", "O");
//10
                sqLiteHelper.addNewQuestion("MOZILLA IS AN EXAMPLE OF________________________", "SYSTEM SOFTWARE", "OPERATING SYSTEM", "APPLICATION SOFTWARE", "INTERNET EXPLORER", "C", "O");
//11
                sqLiteHelper.addNewQuestion("WHAT IS THE SHORTCUT FOR REDO", "CTRL + R", "CTRL + R + D", "CRL + R + Y", "CTRL + Y", "D", "O");
//12
                sqLiteHelper.addNewQuestion("WHAT IS THE SHORTCUT FOR QUITING A PROGRAM", "ALT + F4", "CTRL + F3", "SHIFT + F4", "TAB + F4 + C", "A", "O");
//13
                sqLiteHelper.addNewQuestion("WHAT IS THE LATEST MICROSOFT OPERATING SYSTEM", "WINDOWS 7", "WINDOWS ANDROID", "WINDOWS 8", "WINDOWS 2013", "C", "O");
//14
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS THE EXTENSION FOR AN MS-WORD 2007 FILE", ".DOC7", ".DOCX", ".DOC", ".DOCZ", "B", "O");
//15
                sqLiteHelper.addNewQuestion("WHICH OF THIS FUNCTION IS USED FOR WARM BOOTING ?", "RESTART", "REBOOT", "LOG OFF", "POWER BUTTON", "A", "O");
//16
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT EXAMPLE OF AN OPERATING SYSTEM ?", "LINUX OS", "UNIX OS", "ANDROID OS", "JAVA OS", "D", "O");
//17
                sqLiteHelper.addNewQuestion("THE ONLY LANGUAGE THE COMPUTER UNDERSTAND IS ______________", "JAVA LAMGUAGE", "MACHINE LANGUAGE", "MICROSOFT LAGUAGE", "WINDOWS LANGUAGE", "B", "O");
//18
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT AN INPUT DEVICE ?", "MONITOR", "SCANNER", "PLOTTER", "FLASH DRIVE", "B", "O");
//19
                sqLiteHelper.addNewQuestion("NIGERIA IS IN WHICH GENERATION OF COMPUTER ?", "5TH", "4TH", "3RD", "2ND", "B", "O");
//20
                sqLiteHelper.addNewQuestion("WHAT IS THE WORD TO SEARCH FOR POWERPOINT USING RUN COMMAND ", "POWER", "POWERPPT", "POWERPNT", "PPOINT", "C", "O");
//21
                sqLiteHelper.addNewQuestion("WHICH OF THE FOLLOWING IS NOT A WORD PROCESSOR", "OPEN OFFICE WORD", "MICROSOFT WORKS", "CORREL DRAW", "WORD PAD", "C", "O");
//22
                sqLiteHelper.addNewQuestion("WHICH OF THESE IS NOT A TAB IN MICROSOFT WORD", "HOME", "DATA", "MAILINGS", "REFERENCES", "B", "O");
//23
                sqLiteHelper.addNewQuestion("WHICH OF THE DOCUMENT VIEWS IS GOOD FOR VIEWING TWO PAGES AT ATIME ?", "PRINT LAYOUT", "WEB LAYOUT", "FULL SCREEN READING", "DRAFT", "C", "O");
//24
                sqLiteHelper.addNewQuestion("WHAT DO YOU USE TO COPY FORMATTING FROM ONE PORTION OF YOUR DOCUMENT TO ANOTHER?", "COPY", "SHADING", "TEXT HIGHLIGHT COLOUR", "FORMAT PAINTER", "D", "O");
//25
                sqLiteHelper.addNewQuestion("IN WHICH TAB IN MS WORD CAN YOU SET YOUR PAGE MARGIN ?", "INSERT", "PAGE LAYOUT", "PAGE SETUP", "REVIEW", "B", "O");
//26
                sqLiteHelper.addNewQuestion("___________ IS THE VISUAL REPRESENTATION OF A NUMERIC VALUE.", "TABLE", "HYPERLINK", "SMART ART", "CHART", "D", "O");
//27
                sqLiteHelper.addNewQuestion("AN ELECTRONIC DOCUMENT THAT STORES VARIOUS TYPES OF DATA IS ________", "SPREADSHEET", "DOCUMENT", "SLIDE", "WEB PAGE", "A", "O");
//28
                sqLiteHelper.addNewQuestion("_________ DISPLAYS THE CONTENT OF AN ACTIVE CELL", "CELL", "NAME BOX", "CELL ADDRESS", "FORMULA BAR", "D", "O");
//29
                sqLiteHelper.addNewQuestion("WHAT FUNCTION CAN BE USED TO CALCULATE THE POSITION OF A SET OF STUDENTS", "RANK", "COUNTIF", "POSITION", "AVERAGE", "A", "O");
//30
                sqLiteHelper.addNewQuestion("SPELLING AND GRAMMAR CHECK CAN BE FOUND IN WHICH TAB ?", "REVIEW", "VIEW", "HOME", "REFERENCES", "A", "O");
//31
                sqLiteHelper.addNewQuestion("HOW MANY DEFAULT WORKSHEEETS DOES A WORKBOOK HAVE", "2", "3", "4", "5", "B", "O");
//32
                sqLiteHelper.addNewQuestion("WHAT FUNCTION IS USED TO FIND THE NUMBER OF CELLS CONTAINING NUMERIC VALUE", "COUNTIF", "MAX", "COUNT", "SUM", "C", "O");
//33
                sqLiteHelper.addNewQuestion("A COLLECTION OF DATA AND INFORMATION THAT IS TO BE DELIVERED TO A SPECIFIC AUDIENCE IS _______", "SLIDE", "SPREADSHEET", "SPEECH", "PRESENTATION", "D", "O");
//34
                sqLiteHelper.addNewQuestion("IN WHICH TAB CAN YOU ADD TRANSITION TO YOUR SLIDES ?", "ANIMATIONS", "SLIDESHOW", "DESIGN", "REVIEW", "A", "O");
//35
                sqLiteHelper.addNewQuestion("WHICH OF THE SLIDE VIEWS DISPLAYS MULTIPLE SLIDES FOR EASY ARRANGEMENT ?", "NORMAL", "SLIDE MASTER", "SLIDE SORTER", "NOTES PAGE", "C", "O");
//36
                sqLiteHelper.addNewQuestion("ON WHICH TAB CAN YOU ADD AUDIO AND VIDEO CLIPS TO YOUR PRESENTATION?", "ANIMATION", "INSERT", "DESIGN", "HOME", "B", "O");
//37
                sqLiteHelper.addNewQuestion("THE EFFECTS THAT TAKE PLACE WHEN YOU SWITCH FROM ONE SLIDE TO THE NEXT IS_______", "SLIDE ANIMATION", "DESIGN", "SLIDE TRANSITION", "EFFECTS", "C", "O");
//38
                sqLiteHelper.addNewQuestion("HOW MANY PARAGRAPH ALIGNMENTS DO WE HAVE", "5", "3", "2", "4", "D", "O");
//39
                sqLiteHelper.addNewQuestion("WHAT IS THE RUN COMMAND FOR MICROSOFT WORD", "WINWORD", "MSWORD", "WORD", "WINWD", "A", "O");
//40
                sqLiteHelper.addNewQuestion("WHAT IS THE RUN COMMAND FOR MICROSOFT EXCEL", "XLSX", "EXCEL", "WINEXCEL", "MSEXCEL", "B", "O");
//41
                sqLiteHelper.addNewQuestion("MAN STANDS FOR_____________________", "METROPOLITAN AREA NETWORK", "METROPOLICE AREA NETWORK", "METROPOLITANT AREA NETWORK", "METROPOLLITANT AREA NETWORK", "A", "O");
//42
                sqLiteHelper.addNewQuestion("WHAT IS THE DEFAULT NUMBER OF SHEET(S) IN A NEWLY CREATED WORKBOOK ?", "1 SHEET(S)", "3 SHEET(S)", "2 SHEET(S)", "5 SHEET(S)", "B", "O");
//43
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS FOUND IN POWER POINT", "DATA SHEET", "SLIDE", "WORK SPACE", "FORMULA", "B", "O");
//44
                sqLiteHelper.addNewQuestion("WHAT IS THE SHORT CUT FOR DUPLICATING A SLIDE", "ALT + D", "CTRL + D", "SIFT + D", "CTRL + LEFT CLICK", "B", "O");
//45
                sqLiteHelper.addNewQuestion("WHICH OF THIS VIEW IS NOT IN POWER POINT", "SLIDE SORTER", "NORMAL", "WEB VIEW", "SLIDE SHOW VIEW", "C", "O");
//46
                sqLiteHelper.addNewQuestion("WHEN DOES A SLIDE PRESENTATION TAKE EFFECT ", "SLIDE SORTER", "NORMAL", "WEB VIEW", "SLIDE SHOW VIEW", "D", "O");
//47
                sqLiteHelper.addNewQuestion("WHICH VIEW ALLOWS YOU TO RE ARRANGE YOUR SLIDE", "SLIDE SORTER", "NORMAL", "WEB VIEW", "SLIDE SHOW VIEW", "A", "O");
//48
                sqLiteHelper.addNewQuestion("THE WAY IN WHICH COMPUTERS ARE ARRANGE IN A NETWORK IS CALLED?", "LOCAL AREA NETWORK", "NETWORK ARRANGEMENT", "NETWORK TOPOGRAPHY", "NETWORK TOPOLOGY", "D", "O");
//49
                sqLiteHelper.addNewQuestion("URL STANDS FOR______________________", "UNIFORM RESOURCE LOCATOR", "UNIVERSAL RESISITOR LOCATOR", "UNIFORM RESISTOR LOCATOR", "UNIVERSAL RESOURCE LOCATION", "A", "O");
//50
                sqLiteHelper.addNewQuestion("WHICH OF THIS SERVICES IS NOT FOUND ON THE INTERNET", "E-COMMERCE", "E-LEARNING", "E-MAILS", "E-COMPUTER", "D", "O");
//51
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT A SEARCH ENGINE ?", "GOOGLE", "YAHOO", "FACEBOOK", "E-HOW", "C", "O");
//52
                sqLiteHelper.addNewQuestion("A NEWS GROUPP CAN ALSO BE A_______________", "SEARCH ENGINE", "WEBSITE", "ONLINE FORUM", "EMAIL", "C", "O");
//53
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT A NETWORK TOPOLOGY", "LINEAR", "TREE", "MESH", "PARABOLLA", "D", "O");
//54
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS NOT A TYPE OF NETWORK", "WAN", "TAN", "PAN", "CAN", "B", "O");
//55
                sqLiteHelper.addNewQuestion("WHEN IN AN EMAIL TO START WRITING YOUR MESSAGE YOU CLICK ON______", "WRITE", "SEND MAIL", "COMPOSE ", "DRAFT", "C", "O");
//56
                sqLiteHelper.addNewQuestion("WHICH KIND OF APPLICATION SOFTWARE IS USED TO VIEW A WEB PAGE", "INTERNET SOFTWARE", "WEB BROWSER", "WEB CRAWLER", "WEB SOFTWARE", "B", "O");
//57
                sqLiteHelper.addNewQuestion("THE COMPUTER THAT IS VIEWING A PARTICULAR PAGE ON THE WEB IS CALLED___________", "CLIENT", "SERVER", "WEB SERVER", "WEB VIEWER", "A", "O");
//58
                sqLiteHelper.addNewQuestion("ISP STAND FOR ________________", "INTERNAL STRUCTURE PACKAGE", "INTERNET SERVICE PROVIDER", "INTERNET SERVICE PURCHASER", "INTERNET SERVICER ", "B", "O");
//59
                sqLiteHelper.addNewQuestion("WHAT IS THE FULL MEANING OF GUI_________", "GRAPHICAL USER INTERCOM", "GRAPHICS UNIFIED PROPERTIES", "GRAPHIC UNIFIED INTERFACE", "GRAPHICAL USER INTERFACE", "D", "O");
//60
                sqLiteHelper.addNewQuestion("5TH GENERATION OF COMPUTER IS BASED ON __________________", "ANDROID", "ROBOTICS", "BIOMETRIC ", "ARTIFOCIAL INTELLIGENCE", "D", "O");
//61
                sqLiteHelper.addNewQuestion("IN ORDER OF PRESCEDENCE WHICH ARITHMETIC OPERATOR WILL BE TREATED FIRST ?", "-", "+", "/", "^", "D", "O");
//62
                sqLiteHelper.addNewQuestion("WHICH OF THESE PROGRAMS IS USED TO CHECK AGAINST VIRUS ?", "ANTI-HACKER", "VIRUS DELETER", "CLEAN VIRUS", "ANTI-VIRUS", "D", "O");
//63
                sqLiteHelper.addNewQuestion("WHICH OF THESE IS NOT PART OF A COMPUTER SYSTEM ?", "PEOPLEWARE", "DATABASE", "HARDWARE", "SOFTWARE", "B", "O");
//64
                sqLiteHelper.addNewQuestion("A VIRUS PROGRAM IS ALSO CALLED A", "MARLWEAR", "HALWARE", "MALWARE", "HARDWARE", "C", "O");
//65
                sqLiteHelper.addNewQuestion("A COMPUTER IS USED IN ONE OF THE FOLLOWING EXCEPT", "BANKING", "SCIENTIFIC RESEARCH", "THINKING", "RECREATION", "C", "O");
//66
                sqLiteHelper.addNewQuestion("ONE OF THESE IS A CLASSIFICATION OF COMPUTERS BY PURPOSE", "PRIVATE", "GENERAL PURPOSE", "SIMPLE", "ALL-PURPOSE", "B", "O");
//67
                sqLiteHelper.addNewQuestion("WHICH OF THESE IS A BENEFIT OF CREATING FOLDERS ?", "FOR SAFETY", "FOR SORTING FILES", "FOR FUN", "FOR HELPING THE COMPUTER", "B", "O");
//68
                sqLiteHelper.addNewQuestion("COMPUTER STORES INFORMATION IN FORM OF _________?", "FOLDERS", "FILES", "APPLICATION", "BYTES", "B", "O");
//69
                sqLiteHelper.addNewQuestion("THOSE WHO GAIN ACCESS TO SOMEONE COMPUTER WITHOUT THEIR KNOWNLEDGE ARE CALLED?", "PROGRAMMERS", "COMPUETR VIRUS", "HACKERS", "ANALYST", "C", "O");
//70
                sqLiteHelper.addNewQuestion("WHEN THERE IS MISSING CHARACTER IN WORD, WHICH WILDCARD IS USED TO SEARCH?", "%", "#", "?", "@", "C", "O");
//71
                sqLiteHelper.addNewQuestion("WHAT DOES THE COMMAND .DOCX SEARCH FOR ?", "ALL FILES", "SOME FILES", "DOCUMENT FILES", "GAMES", "C", "O");
//72
                sqLiteHelper.addNewQuestion("WHAT IS THE EXTENSION OF A WORD DOCUMENT ?", ".DOCX", ".EXE", ".JPG", ".XLSX", "A", "O");
//73
                sqLiteHelper.addNewQuestion("WHICH OF THE FOLLOWING TEXT SELECTION TECHNIQUES IS USED SEVERAL WORDS IN A LINE.", "DOUBLE CLICK WITHIN THE WORD", "TRIPPLE CLICK THE LINE", "CLICK HOME TAB", "TETRA-CLICK OVER THE WORD", "B", "O");
//74
                sqLiteHelper.addNewQuestion("SPELLING AND GRAMMAR COMMAND BUTTON CAN BE ACCESS FROM WHICH OF THE FOLLOWING TAB", "HOME TAB", "VIEW TAB", "REVIEW TAB", "PAGE LAYOUT TAB", "C", "O");
//75
                sqLiteHelper.addNewQuestion("INFORMATION DELETED FROM COMPUTER ARE STORE IN ________________", "CLIPBOARD", "RECYCLE BIN", "TRASH BIN", "COMPUTER BIN", "B", "O");
//76
                sqLiteHelper.addNewQuestion("WHICH OF THE COMMAND BUTTON CAN WE USE TO COPY FORMATTING FROM PORTION OF THE DOCUMENT TO ANOTHER", "FONT", "TEXT HIGHLIGHTING COLOR", "FONT COLOR", "FORMAT PAINTER", "D", "O");
//77
                sqLiteHelper.addNewQuestion("WHICH OF THIS MODE CAN BE USE TO CONNECT TO INTERNET ?", "VSAAT", "DAAR SAT", "ISP", "VSAT", "B", "O");
//78
                sqLiteHelper.addNewQuestion("THE DEFAULT NUMBER OF PAGE(S) IN A NEWLY CREATED DOCUMENT IS ?", "1 PAGE", "2 PAGE(2)", "3 PAGE(S)", "4 PAGE(S)", "A", "O");
//79
                sqLiteHelper.addNewQuestion("WHICH OF THIS IS A TYPE VIRUS ?", "TROJAN HORSE", "TROYIAN PIG", "TROJAN FLY", "THROWJAM HORSE", "A", "O");
//80
                sqLiteHelper.addNewQuestion("WHAT IS THE FULL MEANING OF HTTP ?", "HYPERTEXT TRANSFER PROTOCOL", "HYPER TRANSFER PROCEDURE", "HYPERTEXT TRANSFER PROCEDURE", "HIGH TRANSMITTING TASK PACK", "A", "O");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String content) {

            try {

                //Inflate animation from XML
                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_anim);
                // Setup listeners (optional)
                animFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Fires when animation starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //call the home screen
                        Animation animFadeOut1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_out);
                        /**start the animation
                        tvLabel.startAnimation(animFadeOut1);
                        textView.startAnimation(animFadeOut1);
                        txt3.startAnimation(animFadeOut1);**/
                        txt3.setText("");textView.setText("");tvLabel.setText("");
                        Intent intent = new Intent (MainActivity.this,HomeScreen.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // ...
                    }
                });
                // start the animation
                tvLabel.startAnimation(animFadeOut);
                textView.startAnimation(animFadeOut);
                txt3.startAnimation(animFadeOut);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
