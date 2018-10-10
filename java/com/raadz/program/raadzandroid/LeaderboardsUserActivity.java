package com.raadz.program.raadzandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;
import com.stripe.android.view.CardMultilineWidget;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.id.edit;
import static com.raadz.program.raadzandroid.R.id.LLMain;
import static com.raadz.program.raadzandroid.R.id.imgImage;
import static com.raadz.program.raadzandroid.R.id.tvCity;
import static com.raadz.program.raadzandroid.R.id.tvEarnings;
import static com.raadz.program.raadzandroid.R.id.tvName;
import static com.raadz.program.raadzandroid.R.id.view;

public class LeaderboardsUserActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {

    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    NavigationView navigationView = null;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    final MediaPlayer mp = new MediaPlayer();

    final HashMap<String, String> userDetails = new HashMap<String, String>();
    DecimalFormat df_g = new DecimalFormat("#.00");

    ImageButton ibProfile;

    ProgressBar pd;

    View viewGlobal;
    View altView;

    Spinner sTime;
    Spinner sLocation;
    Spinner sAdType;
    Spinner sUserType;

    LinearLayout LLLeaderboards;
    LinearLayout LLHeader;
    LinearLayout LLUser;
    LinearLayout LLRow2;
    LinearLayout LLRow3;
    LinearLayout LLCost;

    Button bViewAll;
    Button bAddResults;
    Button mPlay;
    Button mPause;

    TextView tvMoney;
    TextView tvUsers;
    TextView tvAds;
    TextView tvLocal;
    TextView tvGlobal;
    TextView tvTopEarners;
    TextView tvAccurate;
    TextView tvMostRatings;
    TextView tvFunny;
    TextView tvEmotional;
    TextView tvAttention;
    TextView tvExplain;
    TextView tvReWatch;
    TextView tvEffective;
    TextView tvViral;
    TextView tvResult;
    TextView mStatus;

    EditText etPHP;

    HashMap<String, String> userInfo = new HashMap<String, String>();
    HashMap<String, Double> userStats = new HashMap<String, Double>();

    String getLeaderboardsURL = "https://raadz.com/getLeaderboards.php";
    String buttonPlaceholder = "";
    String lastValueSelected = "";
    String lastTimeValueSelected = "";
    String lastLocationValueSelected = "";
    String j_value = "0";
    String balance;
    String money;
    String httpRequest;
    String sTimeVar;
    String sLocationVar;
    String result;
    String color;
    String colorPrimary = "00cdFF";
    String colorSecondary = "#FFFFFF";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String bothTime;

    int currentTime;
    int totalTime;

    int value = 5;
    int colorChanger = 0;
    int usersadsTracker = 0;
    int leaderboardEntries = 50;

    boolean PHPClicked = false;
    boolean checkAudio = false;
    boolean moreCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards_user);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile);
        toolbar.setOverflowIcon(drawable);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu4);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        pd = (ProgressBar) findViewById(R.id.progressBar2);

        sTime = (Spinner) findViewById(R.id.sTime);
        sAdType = (Spinner) findViewById(R.id.sAdType);
        sUserType = (Spinner) findViewById(R.id.sUserType);
        sLocation = (Spinner) findViewById(R.id.sLocation);

        bViewAll = (Button) findViewById(R.id.bViewAll);
        bAddResults = (Button) findViewById(R.id.bAddResults);

        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHeader = (LinearLayout) findViewById(R.id.LLHeader);
        LLUser = (LinearLayout) findViewById(R.id.LLUser);
        LLRow2 = (LinearLayout) findViewById(R.id.LLRow2);
        LLRow3 = (LinearLayout) findViewById(R.id.LLRow3);
        LLCost = (LinearLayout) findViewById(R.id.LLCost);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvUsers = (TextView) findViewById(R.id.tvUsers);
        tvAds = (TextView) findViewById(R.id.tvAds);
        tvTopEarners = (TextView) findViewById(R.id.tvTopEarners);
        tvLocal = (TextView) findViewById(R.id.tvLocal);
        tvGlobal = (TextView) findViewById(R.id.tvGlobal);
        tvAccurate = (TextView) findViewById(R.id.tvAccurate);
        tvMostRatings = (TextView) findViewById(R.id.tvMostRatings);
        tvFunny = (TextView) findViewById(R.id.tvFunny);
        tvEmotional = (TextView) findViewById(R.id.tvEmotional);
        tvEffective = (TextView) findViewById(R.id.tvEffective);
        tvAttention = (TextView) findViewById(R.id.tvAttention);
        tvExplain = (TextView) findViewById(R.id.tvExplain);
        tvReWatch = (TextView) findViewById(R.id.tvReWatch);
        tvViral = (TextView) findViewById(R.id.tvViral);
        tvResult = (TextView) findViewById(R.id.tvResult);

        etPHP = (EditText) findViewById(R.id.etPHP);

        balance = preferences.getString("funds", "");
//        Log.d("balance: ", balance);
//        try {
//            JSONArray jArray = new JSONArray(balance);
//            //Log.d("json: ", httpRequest);
//            for (int i = 0 ; i < jArray.length(); i++) {
//                JSONObject jObj = jArray.getJSONObject(i);
//                Log.d("money: ", jObj.getString("balance"));
//                money = jObj.getString("balance");
//            }
//        }catch(org.json.JSONException e){
//            System.out.println();
//        }
        tvMoney.setText("$" + balance);

        tvUsers.setClickable(false);
        tvUsers.setFocusable(false);

        //SendDataToServer(preferences.getString("raadz_user_id", ""), "", preferences.getString("token", ""));


        List<String> list = new ArrayList<String>();
        list.add("All Time");
        list.add("This Month");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = adapter.getPosition("All Time");
        Log.d("position ", String.valueOf("All Time"));
        Log.d("position String ", "All Time");
        sTime.setAdapter(adapter);
        sTime.setSelection(position);
        lastTimeValueSelected = adapter.getItem(0);
        Log.d("item 1 ", adapter.getItem(0));
        Log.d("item 2 ", adapter.getItem(1));


        List<String> Ulist = new ArrayList<String>();
        Ulist.add("Earners");
        Ulist.add("Accurate");
        Ulist.add("Rated");

        ArrayAdapter<String> boardsU = new ArrayAdapter<String>(LeaderboardsUserActivity.this, android.R.layout.simple_spinner_item, Ulist);
        boardsU.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int posU = boardsU.getPosition("Top Rated");
        sUserType.setAdapter(boardsU);
        sUserType.setSelection(posU);
        Log.d("item 1 ", boardsU.getItem(0));
        Log.d("item 2 ", boardsU.getItem(1));
        Log.d("item 3 ", boardsU.getItem(2));

        List<String> Llist = new ArrayList<String>();
        Llist.add("Top Rated");
        Llist.add("Funniest");
        Llist.add("Most Emotional");
        Llist.add("Most Effective");
        Llist.add("Attention-Grabbing");
        Llist.add("Best Explanation");
        Llist.add("Rewatch");
        Llist.add("Viral Ability");

        ArrayAdapter<String> boards = new ArrayAdapter<String>(LeaderboardsUserActivity.this, android.R.layout.simple_spinner_item, Llist);
        boards.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int pos = boards.getPosition("Top Rated");
        sAdType.setAdapter(boards);
        sAdType.setSelection(pos);
        Log.d("item 1 ", boards.getItem(0));
        Log.d("item 2 ", boards.getItem(1));
        Log.d("item 3 ", boards.getItem(2));
        Log.d("item 4 ", boards.getItem(3));
        Log.d("item 5 ", boards.getItem(4));
        Log.d("item 6 ", boards.getItem(5));
        Log.d("item 7 ", boards.getItem(6));

        SpannableString content1 = new SpannableString("Users");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvUsers.setText(content1);

        SpannableString content2 = new SpannableString("Ads");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvAds.setText(content2);

        SpannableString content3 = new SpannableString("Top Earners");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tvTopEarners.setText(content3);

        SpannableString content4 = new SpannableString("Most Accurate");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tvAccurate.setText(content4);

        SpannableString content5 = new SpannableString("Most Rated");
        content5.setSpan(new UnderlineSpan(), 0, content5.length(), 0);
        tvMostRatings.setText(content5);

        SpannableString content6 = new SpannableString("Funniest");
        content6.setSpan(new UnderlineSpan(), 0, content6.length(), 0);
        tvFunny.setText(content6);

        List<String> list2 = new ArrayList<String>();
        list2.add("Global");
        list2.add("Local");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position2 = adapter2.getPosition("Global");
        Log.d("position ", String.valueOf("Global"));
        Log.d("position String ", "Global");
        sLocation.setAdapter(adapter2);
        sLocation.setSelection(position2);
        lastLocationValueSelected = adapter2.getItem(0);
        Log.d("item 1 ", adapter2.getItem(0));
        Log.d("item 2 ", adapter2.getItem(1));

        userPosition();

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LeaderboardsUserActivity.this, AdsHistory.class);
                startActivity(in);
            }
        });

        sTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorChanger = 0;
                sTimeVar = sTime.getSelectedItem().toString();
                Log.d("item selected ", sTimeVar);
                lastTimeValueSelected = sTimeVar;
                onClick(viewGlobal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorChanger = 0;
                sLocationVar = sLocation.getSelectedItem().toString();
                Log.d("item selected ", sLocationVar);
                lastLocationValueSelected = sLocationVar;
                onClick(viewGlobal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sAdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorChanger = 0;
                sTime.setSelection(0);
                sLocation.setSelection(0);
                String locationSelect = sAdType.getSelectedItem().toString();
                if (locationSelect.equals("Top Rated")) {
                    altView = tvTopEarners;
                    lastTimeValueSelected = "All Time";
                    onClick(altView);
                }
                if (locationSelect.equals("Funniest")) {
                    altView = tvFunny;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Most Emotional")) {
                    altView = tvEmotional;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Most Effective")) {
                    altView = tvEffective;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Attention-Grabbing")) {
                    altView = tvAttention;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Explain")) {
                    altView = tvExplain;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Rewatch")) {
                    altView = tvReWatch;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Viral Ability")) {
                    altView = tvViral;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                Log.d("AdType ", locationSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTime.setSelection(0);
                sLocation.setSelection(0);
                String locationSelect = sUserType.getSelectedItem().toString();
                if (locationSelect.equals("Earners")) {
                    altView = tvTopEarners;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Accurate")) {
                    altView = tvAccurate;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                if (locationSelect.equals("Rated")) {
                    altView = tvMostRatings;
                    lastTimeValueSelected = "All Time";
                    lastLocationValueSelected = "Global";
                    onClick(altView);
                }
                Log.d("AdType ", locationSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        bViewAll.setOnClickListener(new View.OnClickListener() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            @Override
            public void onClick(View view) {
                if (PHPClicked == false) {
                    etPHP.setText(preferences.getString("l_result", ""));
                    bViewAll.setText("HIDE PHP");
                    PHPClicked = true;
                } else {
                    etPHP.setText("");
                    bViewAll.setText("VIEW PHP POST");
                    PHPClicked = false;
                }
            }
        });


        tvUsers.setOnClickListener(this);

        tvAds.setOnClickListener(this);

        tvTopEarners.setOnClickListener(this);

        tvAccurate.setOnClickListener(this);

        tvMostRatings.setOnClickListener(this);

        tvFunny.setOnClickListener(this);

        bAddResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ProgressDialog dialog = new ProgressDialog(LeaderboardsUserActivity.this, R.style.AppCompatAlertDialogStyle);
                dialog.setMessage("Please Wait...");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
                dialog.show();

                new CountDownTimer(800, 1000) {

                    public void onTick(long millisUntilFinished) {
                        long value = millisUntilFinished / 1000;
                        Log.d("Seconds remaining: ", String.valueOf(value));
                    }

                    public void onFinish() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                leaderboardEntries += 10;
                                moreCheck = true;
                                userPosition();
                            }
                        });

                    }

                }.start();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void userPosition() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (moreCheck == true) {
                    defaultStats();
                } else if (moreCheck == false) {
                    try {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Log.d("testDefault ", "inside position default");
                        result = preferences.getString("l_result", "");
//                    Log.d("Testresult ", result);
                        JSONObject LastJSON = new JSONObject(result);
//                    Log.d("LastJSON ", LastJSON.getString("99"));

                        String s_last_Obj = LastJSON.getString("99");
                        JSONArray LastjArray = new JSONArray(s_last_Obj);
                        JSONObject last_Obj = LastjArray.getJSONObject(0);

                        for (int i = 0; i < 28; i++) {
                            String temp = LastJSON.getString(String.valueOf(i));
                            Log.d("Leaderboard Array ", String.valueOf(i) + " | " + temp);
                        }

                        Log.d("Display Name ", last_Obj.getString("display_name"));
                        Log.d("RaadzUserID ", last_Obj.getString("raadz_user_id"));
                        Log.d("User Location ", last_Obj.getString("user_loc"));
                        Log.d("All Accurate ", last_Obj.getString("all_acc"));
                        Log.d("All Earned ", last_Obj.getString("all_earned"));
                        Log.d("All Rated ", last_Obj.getString("all_rated"));
                        Log.d("Month Accurate ", last_Obj.getString("month_acc"));
                        Log.d("Month Earned ", last_Obj.getString("month_earned"));
                        Log.d("Month Rated ", last_Obj.getString("month_rated"));
                        Log.d("JSON_Obj_99 ", s_last_Obj);

                        try {

                            if (Double.parseDouble(last_Obj.getString("all_acc")) < 0) {
                                String s_temp = String.valueOf(Double.parseDouble(last_Obj.getString("all_acc")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("all_acc", d_temp);
                            } else {
                                userStats.put("all_acc", Double.parseDouble(last_Obj.getString("all_acc")));
                            }


                            if (Double.parseDouble(last_Obj.getString("all_earned")) < 0) {
                                String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("all_earned")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("all_earned", d_temp);
                            } else {
                                userStats.put("all_earned", Double.parseDouble(last_Obj.getString("all_earned")));
                            }


                            if (Double.parseDouble(last_Obj.getString("all_rated")) < 0) {
                                String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("all_rated")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("all_rated", d_temp);
                            } else {
                                userStats.put("all_rated", Double.parseDouble(last_Obj.getString("all_rated")));
                            }


                            if (Double.parseDouble(last_Obj.getString("month_acc")) < 0) {
                                String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_acc")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("month_acc", d_temp);
                            } else {
                                userStats.put("month_acc", Double.parseDouble(last_Obj.getString("month_acc")));
                            }


                            if (Double.parseDouble(last_Obj.getString("month_earned")) < 0) {
                                String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_earned")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("month_earned", d_temp);
                            } else {
                                userStats.put("month_earned", Double.parseDouble(last_Obj.getString("month_earned")));
                            }


                            if (Double.parseDouble(last_Obj.getString("month_rated")) < 0) {
                                String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_rated")));
                                s_temp = s_temp.substring(0, 0);
                                double d_temp = Double.parseDouble(s_temp);
                                userStats.put("month_rated", d_temp);
                            } else {
                                userStats.put("month_rated", Double.parseDouble(last_Obj.getString("month_rated")));
                            }

                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }

                        userInfo.put("display_name", last_Obj.getString("display_name"));
                        userInfo.put("raadz_user_id", last_Obj.getString("raadz_user_id"));
                        userInfo.put("user_loc", last_Obj.getString("user_loc"));
                        defaultStats();
                    } catch (org.json.JSONException e) {
                        System.out.println(e);
                    }
//            defaultStats();
                }
            }
        });
    }

    public void defaultStats() {
        runOnUiThread(new Runnable() {
            public void run() {
                LLUser.removeAllViews();
                LLLeaderboards.removeAllViews();
                LLHeader.removeAllViews();
            }
        });
        placementMethod(1);
        buttonPlaceholder = "tvUsers";
        usersClicked();
        lastValueSelected = "users";
        earnersClicked();
        LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
        LLHeader.addView(layout_h);
        Log.d("OVG ", result);
        Log.d("OVG ", lastValueSelected);
        Log.d("OVG ", lastTimeValueSelected);
        Log.d("OVG ", lastLocationValueSelected);
        Log.d("OVG ", String.valueOf(viewGlobal));
        Log.d("defaultTest ", userInfo.get("display_name"));
        Log.d("defaultTest ", userInfo.get("raadz_user_id"));
        Log.d("defaultTest ", userInfo.get("user_loc"));
        colorChanger = 0;
        try {
            JSONObject jsonObj = new JSONObject(result);
            Log.d("NewJSONOBJ ", jsonObj.getString("0"));
            Log.d("testJSON1 ", result);
            Log.d("looper ", result);
            String pass_result = jsonObj.getString("0");
            Log.d("ZERO ", pass_result);
            JSONArray jArray = new JSONArray(pass_result);

            JSONObject pass_Obj = jArray.getJSONObject(0);
            Log.d("", "");
            Log.d("pass_position ", String.valueOf(1));
            Log.d("pass_display ", pass_Obj.getString("display_name"));
            Log.d("pass_display ", pass_Obj.getString("all_earned"));
            Log.d("pass_display ", pass_Obj.getString("user_loc"));
            Log.d("", "");
            for (int i = 0; i < leaderboardEntries; i++) {
                JSONArray jsonArray = new JSONArray(jsonObj.getString("0"));
                JSONObject jObj = jsonArray.getJSONObject(i);
                Log.d("", "");
                Log.d("position ", String.valueOf(i));
                Log.d("display ", jObj.getString("display_name"));
                Log.d("earned ", jObj.getString("all_earned"));
                Log.d("location", jObj.getString("user_loc"));
                Log.d("", "");
                final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);

                Log.d("DEFAULT_COLOR_VALUE ", String.valueOf(colorChanger));

                if (colorChanger == 0) {
                    layout.setBackgroundResource(R.drawable.table_gradient_1);
                } else if (colorChanger == 1) {
                    layout.setBackgroundResource(R.drawable.table_gradient_2);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        LLLeaderboards.addView(layout);
                    }
                });
                if (colorChanger == 0) {
                    color = colorPrimary;
                    colorChanger = 1;
                } else if (colorChanger == 1) {
                    color = colorSecondary;
                    colorChanger = 0;
                }
                //boardLayout.setBackgroundColor(Color.parseColor(color));
                Log.d("color ", color);
                Log.d("colorChanger ", String.valueOf(colorChanger));

                tvNum.setText((String.valueOf(i + 1)) + ")");
                tvName.setText(jObj.getString("display_name"));
                tvCity.setText(jObj.getString("user_loc"));

            }
        } catch (org.json.JSONException e) {
            System.out.println();
        }
    }

    public void usersClicked() {

        viewGlobal = tvUsers;
        lastTimeValueSelected = "All Time";
        sTime.setSelection(0);
        sUserType.setSelection(0);

        tvUsers.setClickable(false);
        tvUsers.setFocusable(false);
        tvAds.setClickable(true);
        tvAds.setFocusable(true);

        if (usersadsTracker == 1) {
            usersadsTracker = 0;
            LLRow2.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        if (usersadsTracker == 0) {
            usersadsTracker = 1;
//            LLRow2.setVisibility(View.VISIBLE);
            tvFunny.setVisibility(View.GONE);
            tvAccurate.setVisibility(View.VISIBLE);
            tvMostRatings.setVisibility(View.VISIBLE);
            sLocation.setVisibility(View.VISIBLE);
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
            LinearLayout usersLLCost = (LinearLayout) layout.findViewById(R.id.LLCost);
            usersLLCost.setVisibility(View.GONE);


        }
        LLLeaderboards.removeAllViews();
        colorTV1();
        decolorTV2();
        decolorTV3();
        decolorTV4();
        decolorTV5();
    }

    public void adsClicked() {

        viewGlobal = tvAds;
        lastTimeValueSelected = "All Time";
        sTime.setSelection(0);
        sAdType.setSelection(0);

        tvUsers.setClickable(true);
        tvUsers.setFocusable(true);
        tvAds.setClickable(false);
        tvAds.setFocusable(false);

        if (usersadsTracker == 1) {
            usersadsTracker = 0;
            LLRow2.setVisibility(View.GONE);
            LLRow3.setVisibility(View.GONE);
        }
        if (usersadsTracker == 0) {
            usersadsTracker = 1;
//            LLRow2.setVisibility(View.VISIBLE);
            tvFunny.setVisibility(View.VISIBLE);
            tvAccurate.setVisibility(View.GONE);
            tvMostRatings.setVisibility(View.GONE);
            tvFunny.setVisibility(View.VISIBLE);
            sLocation.setVisibility(View.GONE);
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
            LinearLayout adsLLCost = (LinearLayout) layout.findViewById(R.id.LLCost);
            adsLLCost.setVisibility(View.VISIBLE);
        }
        LLLeaderboards.removeAllViews();
        colorTV2();
        decolorTV1();
        decolorTV3();
        decolorTV4();
        decolorTV5();
    }

    public void earnersClicked() {
        usersadsTracker = 1;
        LLRow3.setVisibility(View.VISIBLE);
        if (lastValueSelected.equals("users")) {
            colorTV3();
            colorTV1();
            decolorTV2();
            decolorTV4();
            decolorTV5();
        }

        if (lastValueSelected.equals("ads")) {
            colorTV3();
            colorTV2();
            decolorTV1();
            decolorTV4();
            decolorTV5();
            decolorTV6();
        }
    }

    public void accurateClicked() {
        usersadsTracker = 1;
        LLRow3.setVisibility(View.VISIBLE);
        if (lastValueSelected.equals("users")) {
            colorTV4();
            colorTV1();
            decolorTV2();
            decolorTV3();
            decolorTV5();
        }

        if (lastValueSelected.equals("ads")) {
            colorTV4();
            colorTV2();
            decolorTV1();
            decolorTV3();
            decolorTV5();
            decolorTV6();
        }
    }

    public void ratingsClicked() {
        usersadsTracker = 1;
        LLRow3.setVisibility(View.VISIBLE);
        if (lastValueSelected.equals("users")) {
            colorTV5();
            colorTV1();
            decolorTV2();
            decolorTV3();
            decolorTV4();
        }

        if (lastValueSelected.equals("ads")) {
            colorTV5();
            colorTV2();
            decolorTV1();
            decolorTV3();
            decolorTV4();
            decolorTV6();
        }
    }

    public void funnyClicked() {
        usersadsTracker = 1;
        LLRow3.setVisibility(View.VISIBLE);
        if (lastValueSelected.equals("ads")) {
            colorTV6();
            colorTV2();
            decolorTV1();
            decolorTV3();
            decolorTV4();
            decolorTV5();
        }
    }

    public void colorTV1() {
        tvUsers.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV1() {
        tvUsers.setTextColor(Color.parseColor("#000000"));
    }


    public void colorTV2() {
        tvAds.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV2() {
        tvAds.setTextColor(Color.parseColor("#000000"));
    }


    public void colorTV3() {
        tvTopEarners.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV3() {
        tvTopEarners.setTextColor(Color.parseColor("#000000"));
    }


    public void colorTV4() {
        tvAccurate.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV4() {
        tvAccurate.setTextColor(Color.parseColor("#000000"));
    }


    public void colorTV5() {
        tvMostRatings.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV5() {
        tvMostRatings.setTextColor(Color.parseColor("#000000"));
    }

    public void colorTV6() {
        tvFunny.setTextColor(Color.parseColor("#0000FF"));
    }

    public void decolorTV6() {
        tvFunny.setTextColor(Color.parseColor("#000000"));
    }

    public void placementMethod(final int position) {
        runOnUiThread(new Runnable() {
            public void run() {
                LLUser.removeAllViews();
            }
        });

        Log.d("All Earned ", String.valueOf(userStats.get("all_earned")));
        Log.d("Month Earned ", String.valueOf(userStats.get("month_earned")));
        Log.d("All Accurate ", String.valueOf(userStats.get("all_acc")));
        Log.d("Month Accurate ", String.valueOf(userStats.get("month_acc")));
        Log.d("All Rated ", String.valueOf(userStats.get("all_rated")));
        Log.d("Month Rated ", String.valueOf(userStats.get("month_rated")));

        if (position == 1 &&
                userStats.get("all_earned") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            u_tvEarning.setText(String.valueOf("$" + userStats.get("all_earned")));
            LLUser.addView(user_layout);
        } else if (position == 2 && userStats.get("month_earned") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            u_tvEarning.setText(String.valueOf("$" + userStats.get("month_earned")));
            LLUser.addView(user_layout);
        } else if (position == 3 && userStats.get("all_acc") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            double first = userStats.get("all_acc");
            first = 100 - first;
            DecimalFormat df = new DecimalFormat("####0.00");
            double sum = Double.valueOf(df.format(first));
            u_tvEarning.setText(sum + "%");
            LLUser.addView(user_layout);
        } else if (position == 4 && userStats.get("month_acc") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            double first = userStats.get("month_acc");
            first = 100 - first;
            DecimalFormat df = new DecimalFormat("####0.00");
            double sum = Double.valueOf(df.format(first));
            u_tvEarning.setText(sum + "%");
            LLUser.addView(user_layout);
        } else if (position == 5 && userStats.get("all_rated") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            double value_d = userStats.get("all_rated");
            int value_i = (int) value_d;
            u_tvEarning.setText(String.valueOf(value_i));
            LLUser.addView(user_layout);
        } else if (position == 6 && userStats.get("month_rated") > 0) {
            LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
            TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
            TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
            TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
            u_tvName.setText(String.valueOf(userInfo.get("display_name")));
            u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
            double value_d = userStats.get("month_rated");
            int value_i = (int) value_d;
            u_tvEarning.setText(String.valueOf(value_i));
            LLUser.addView(user_layout);
        } else if (userInfo.get("user_loc").length() > 0) {
            if (position == 7 && userStats.get("all_earned") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                u_tvEarning.setText("$" + String.valueOf(userStats.get("all_earned")));
                LLUser.addView(user_layout);
            } else if (position == 8 && userStats.get("month_earned") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                u_tvEarning.setText("$" + String.valueOf(userStats.get("month_earned")));
                LLUser.addView(user_layout);
            } else if (position == 9 && userStats.get("all_acc") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                double first = userStats.get("all_acc");
                first = 100 - first;
                DecimalFormat df = new DecimalFormat("####0.00");
                double sum = Double.valueOf(df.format(first));
                u_tvEarning.setText(sum + "%");
                LLUser.addView(user_layout);
            } else if (position == 10 && userStats.get("month_acc") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                double first = userStats.get("month_acc");
                first = 100 - first;
                DecimalFormat df = new DecimalFormat("####0.00");
                double sum = Double.valueOf(df.format(first));
                u_tvEarning.setText(sum + "%");
                LLUser.addView(user_layout);
            } else if (position == 11 && userStats.get("all_rated") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                double value_d = userStats.get("all_rated");
                int value_i = (int) value_d;
                u_tvEarning.setText(String.valueOf(value_i));
                LLUser.addView(user_layout);
            } else if (position == 12 && userStats.get("month_rated") > 0) {
                LinearLayout user_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.user_position_layout, null);
                TextView u_tvName = (TextView) user_layout.findViewById(R.id.tvUser);
                TextView u_tvCity = (TextView) user_layout.findViewById(tvCity);
                TextView u_tvEarning = (TextView) user_layout.findViewById(R.id.tvEarning);
                u_tvName.setText(String.valueOf(userInfo.get("display_name")));
                u_tvCity.setText(String.valueOf(userInfo.get("user_loc")));
                double value_d = userStats.get("month_rated");
                int value_i = (int) value_d;
                u_tvEarning.setText(String.valueOf(value_i));
                LLUser.addView(user_layout);
            }
        }
    }

    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
            //player.play();
        }
    }

    public void audioTrack(final View mView) {
        Runnable runnable = new Runnable() {
            public void run() {
                while (mp.isPlaying()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {

                            TextView status = (TextView) mView.findViewById(R.id.tvProgress);

                            totalTime = mp.getDuration() / 1000;
                            currentTime = mp.getCurrentPosition() / 1000;

//                            Log.d("current ", String.valueOf(currentTime));
//                            Log.d("total ", String.valueOf(totalTime));

                            bothTime = "0:" + String.valueOf(currentTime) + " | " + "0:" + String.valueOf(totalTime);

                            status.setText("Status: " + bothTime);
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


    public void audioPlayer(final String path, final String fileName, final View mView) {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAudio == false) {
                    checkAudio = true;
                    try {
                        mp.reset();
                        mp.setDataSource(path + fileName);
                        mp.prepare();
                        mp.start();

                        Log.d("file ", fileName);
                        audioTrack(mView);

                    } catch (Exception e) {
                        System.out.println();
                    }
                } else if (checkAudio == true) {
                    audioTrack(mView);
                    mp.start();
                }
            }
        });
        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });

    }

    public void displaySelections(final HashMap<String, String> selectionResults) {
        Log.d("mainlayout ", "in main layout");

        Log.d("s_title ", selectionResults.get("title"));
        Log.d("s_link ", selectionResults.get("link"));
        Log.d("s_company ", selectionResults.get("company"));
        Log.d("s_type ", selectionResults.get("type"));
        Log.d("s_ref ", selectionResults.get("ref"));

//        Bundle args = new Bundle();
//        args.putString("company", selectionResults.get("company"));
//        args.putString("title", selectionResults.get("title"));
//        args.putString("link", selectionResults.get("link"));
//        args.putString("type", selectionResults.get("type"));
//        args.putString("ref", selectionResults.get("ref"));
//
//        MyCustomDialog dialog = new MyCustomDialog();
//        dialog.setArguments(args);
//        dialog.show(getFragmentManager(), "LeaderboardActivityUser");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LeaderboardsUserActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_my_custom, null);

        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);

        ImageView imgImage = mView.findViewById(R.id.imgImage);

        TextView mAd = mView.findViewById(R.id.tvAd);
        TextView mLink = mView.findViewById(R.id.tvLink);
        TextView mCompany = mView.findViewById(R.id.tvCompany);
        mStatus = mView.findViewById(R.id.tvStatus);

        Button mDone = mView.findViewById(R.id.bDone);
        Button mVideo = mView.findViewById(R.id.bVideo);
        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        Button mFacebook = (Button) mView.findViewById(R.id.bFacebook);
        Button mTwitter = (Button) mView.findViewById(R.id.bTwitter);
        mPlay = mView.findViewById(R.id.bPlay);
        mPause = mView.findViewById(R.id.bPause);

        SpannableString content1 = new SpannableString(selectionResults.get("link"));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        mLink.setText(content1);

        mAd.setText(selectionResults.get("title"));
//        mLink.setText(selectionResults.get("link"));
        mCompany.setText(selectionResults.get("company"));

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        if (selectionResults.get("type").equals("1")) {
            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                    Log.d("test", "inside initizedlistener");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    final String youtubeURL = selectionResults.get("ref");
                    player = youTubePlayer;
                    player.setFullscreen(false);
                    playVideo(youtubeURL);


                    player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {
                        }

                        @Override
                        public void onLoaded(String s) {
                            player.play();
                            Log.d("Duration ", String.valueOf(player.getDurationMillis()));
                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {
                            //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        }

                        @Override
                        public void onVideoEnded() {
                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {
                        }
                    });

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            };

            yt.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
            yt.setVisibility(View.VISIBLE);
            mVideo.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);
            mAudio.setVisibility(View.GONE);
        }

        if (selectionResults.get("type").equals("2")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);
            mAudio.setVisibility(View.GONE);

            Log.d("link ", azureImages);
            Log.d("path ", selectionResults.get("ref"));
            String imgPath = azureImages + selectionResults.get("ref");
            Picasso.with(getApplicationContext()).load(imgPath).into(imgImage);
            imgImage.setVisibility(View.VISIBLE);
        }

        if (selectionResults.get("type").equals("3")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mAudio.setVisibility(View.VISIBLE);

            mAudioLayout.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                public void run() {
                    audioPlayer(azureAudio, selectionResults.get("ref"), mView);
                }
            });
        }

//        mBuilder.setView(mView);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog = new ShareDialog(LeaderboardsUserActivity.this);
                String fbURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "") + selectionResults.get("adid");
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    String referral = preferences.getString("referral_id", "");
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setQuote("Check out the ad on Raadz.com! \n" + fbURL)
                            .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                            .setContentUrl(Uri.parse("https://beta.raadz.com/"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "") + selectionResults.get("adid");
                String tweetText = "Come check out this ad on Raadz.com!";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?url=" + tweetURL + "&text=" + tweetText));
                startActivity(browserIntent);
            }
        });


        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.release();
                dialog.dismiss();
            }
        });

        mLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = ("http://" + selectionResults.get("link"));
                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setData(Uri.parse(url));
                    startActivity(in);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, IndexActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Intent in = new Intent(LeaderboardsUserActivity.this, FragActivity.class);
//        startActivity(in);
        getMenuInflater().inflate(R.menu.navigationp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                Intent intent1 = new Intent(this, UserAdHistoryActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_profile:
                Intent intent2 = new Intent(this, AdsHistory.class);
                startActivity(intent2);
                return true;
            case R.id.action_logout:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                View mView = getLayoutInflater().inflate(R.layout.logout_dialog, null);
                Button mCancel = (Button) mView.findViewById(R.id.bCancel);
                Button mEnter = (Button) mView.findViewById(R.id.bEnter);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.clear();
                        edit.commit();
                        LogoutConfirmActivity logout = new LogoutConfirmActivity();
                        logout.logoutConfirm(getApplicationContext());
                        Intent in = new Intent(LeaderboardsUserActivity.this, IndexActivity.class);
                        startActivity(in);
                    }
                });
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.user_rate) {
            Intent in = new Intent(this, PickAdActivity.class);
            startActivity(in);
        } else if (id == R.id.user_home) {
            Intent in = new Intent(this, FragActivity.class);
            startActivity(in);
        } else if (id == R.id.user_history) {
            Intent in = new Intent(this, UserAdHistoryActivity.class);
            startActivity(in);
        } else if (id == R.id.user_tutorial) {
            Intent in = new Intent(this, TutorialActivity.class);
            startActivity(in);
        } else if (id == R.id.user_about) {
            Intent in = new Intent(this, AboutUsActivity.class);
            startActivity(in);
        } else if (id == R.id.user_contact) {
            Intent in = new Intent(this, ContactUsActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.logout_dialog, null);
            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogoutConfirmActivity logout = new LogoutConfirmActivity();
                    logout.logoutConfirm(getApplicationContext());
                    Intent in = new Intent(LeaderboardsUserActivity.this, IndexActivity.class);
                    startActivity(in);
                }
            });
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LeaderboardsUserActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.terms_layout, null);
            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mBack = (Button) mView.findViewById(R.id.bBack);
            WebView wvLoad = (WebView) mView.findViewById(R.id.wvLoad);
            wvLoad.loadUrl("https://raadz.com/terms.html");
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }else if (id == R.id.nav_privacy) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LeaderboardsUserActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.privacy_layout, null);
            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mBack = (Button) mView.findViewById(R.id.bBack);
            WebView wvLoad = (WebView) mView.findViewById(R.id.wvLoad);
            wvLoad.loadUrl("https://raadz.com/privacy.html");
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }


    @Override
    public void onClick(View v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String raadzid = preferences.getString("raadz_user_id", "");
        String raadzpubid = preferences.getString("raadz_pub_id", "");
        String token = preferences.getString("token", "");
        String result = preferences.getString("l_result", "");
        Log.d("FullResult ", result);
        Log.d("id ", raadzid);
        Log.d("token ", token);
        Log.d("U/A ", lastValueSelected);
        Log.d("Time ", lastTimeValueSelected);
        Log.d("Location ", lastLocationValueSelected);
        Log.d("ViewGlobal ", String.valueOf(viewGlobal));
        Log.d("View ", String.valueOf(v));
        colorChanger = 0;

        if (v == tvUsers) {

            sUserType.setVisibility(View.VISIBLE);
            sAdType.setVisibility(View.GONE);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            SpannableString content = new SpannableString("Top Earners");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvTopEarners.setText(content);
            LLHeader.removeAllViews();
            usersClicked();

            lastValueSelected = "users";


            viewGlobal = v;
            earnersClicked();

            result = preferences.getString("IndexPass", "");
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LLUser.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            buttonPlaceholder = "tvUsers";
            lastTimeValueSelected = "All Time";
            lastLocationValueSelected = "Global";
            v = tvTopEarners;
            colorChanger = 0;

//            try {
//                JSONObject jsonObj = new JSONObject(result);
//                Log.d("NewJSONOBJ ", jsonObj.getString("0"));
//                Log.d("testJSON1 ", result);
//                Log.d("looper ", result);
//                String pass_result = jsonObj.getString("0");
//                Log.d("ZERO ", pass_result);
//                JSONArray jArray = new JSONArray(pass_result);
//
//                JSONObject pass_Obj = jArray.getJSONObject(0);
//                Log.d("", "");
//                Log.d("pass_position ", String.valueOf(1));
//                Log.d("pass_display ", pass_Obj.getString("display_name"));
//                Log.d("pass_display ", pass_Obj.getString("all_earned"));
//                Log.d("pass_display ", pass_Obj.getString("user_loc"));
//                Log.d("", "");
//
//                for (int i = 0; i < leaderboardEntries; i++) {
//                    JSONArray jsonArray = new JSONArray(jsonObj.getString("0"));
//                    JSONObject jObj = jsonArray.getJSONObject(i);
//                    Log.d("", "");
//                    Log.d("position ", String.valueOf(i));
//                    Log.d("display ", jObj.getString("display_name"));
//                    Log.d("earned ", jObj.getString("all_earned"));
//                    Log.d("location", jObj.getString("user_loc"));
//                    Log.d("", "");
//
//                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
//
//                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
//                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
//                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
//                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
//                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
//
//                    if (colorChanger == 0) {
//                        layout.setBackgroundResource(R.drawable.table_gradient_1);
//                    } else if (colorChanger == 1) {
//                        layout.setBackgroundResource(R.drawable.table_gradient_2);
//                    }
//
//                    String t_obj = jObj.getString("all_earned");
//                    double d_obj = Double.parseDouble(t_obj);
//                    DecimalFormat df = new DecimalFormat("###.00");
//                    String formatted = df.format(d_obj);
//
//                    if (
//                            String.valueOf(df.format(userStats.get("all_earned"))).equals(formatted) &&
//                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
//                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
//                            ) {
//                        Log.d("Final ", "InTheLastOne");
//                        LLUser.removeAllViews();
//                        layout.setBackgroundResource(R.drawable.table_gradient);
////                        layout.setBackgroundResource(R.drawable.table_gradient);
//                    } else {
//                        layout.setBackgroundResource(R.drawable.table_gradient_1);
//                    }
//                    LLLeaderboards.addView(layout);
//
//                    if (colorChanger == 0) {
//                        color = colorPrimary;
//                        colorChanger = 1;
//                    } else if (colorChanger == 1) {
//                        color = colorSecondary;
//                        colorChanger = 0;
//                    }
//
//                    Log.d("USER_COLOR_VALUE ", String.valueOf(colorChanger));
//
//                    //boardLayout.setBackgroundColor(Color.parseColor(color));
//                    Log.d("color ", color);
//                    Log.d("colorChanger ", String.valueOf(colorChanger));
//
//                    tvNum.setText((String.valueOf(i + 1)) + ")");
//                    tvName.setText(jObj.getString("display_name"));
//                    tvCity.setText(jObj.getString("user_loc"));
//                    tvEarnings.setText("$" + jObj.getString("all_earned"));
//
//                }
//            } catch (org.json.JSONException e) {
//                System.out.println();
//            }
        }
        if (v == tvAds) {
            LLUser.removeAllViews();
            sUserType.setVisibility(View.GONE);
            sAdType.setVisibility(View.VISIBLE);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            SpannableString content = new SpannableString("Top Ads");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvTopEarners.setText(content);
            LLHeader.removeAllViews();
            lastValueSelected = "ads";
            adsClicked();
            buttonPlaceholder = "tvAds";
            v = tvTopEarners;
            lastValueSelected = "ads";
            lastTimeValueSelected = "All Time";
            viewGlobal = v;
            earnersClicked();
            bAddResults.setVisibility(View.GONE);

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners_header, null);
            LLHeader.addView(layout_h);
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);

            buttonPlaceholder = "tvAds";
            lastTimeValueSelected = "All Time";
            lastLocationValueSelected = "Global";

            colorChanger = 0;

            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("12"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("12");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("overall"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(pass_result);
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("jObj array ", String.valueOf(i));
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("overall"));
                    Log.d("lastTimeValueSelected ", lastTimeValueSelected);
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);

                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }

                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    Log.d("AD_COLOR_VALUE ", String.valueOf(colorChanger));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("overall"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    //Still need to display and and hide the layout for the Completed Layout

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            earnersClicked();
            placementMethod(1);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView tvChange = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            tvChange.setText("Total Earned");
            Log.d("OVG ", result);
            Log.d("OVG ", raadzid);
            Log.d("OVG ", token);
            Log.d("OVG ", lastValueSelected);
            Log.d("OVG ", lastTimeValueSelected);
            Log.d("OVG ", lastLocationValueSelected);
            Log.d("OVG ", String.valueOf(viewGlobal));
            Log.d("OVG ", String.valueOf(v));
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("0"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("0");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_earned"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("0"));
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_earned"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("all_earned");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_earned"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);


                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    Log.d("color ", color);
                    Log.d("colorChanger ", String.valueOf(colorChanger));

                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText("$" + jObj.getString("all_earned"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            earnersClicked();
            placementMethod(2);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView tvChange = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            tvChange.setText("Total Earned");
            Log.d("UMG ", result);
            Log.d("UMG ", raadzid);
            Log.d("UMG ", token);
            Log.d("UMG ", lastValueSelected);
            Log.d("UMG ", lastTimeValueSelected);
            Log.d("UMG ", lastLocationValueSelected);
            Log.d("UMG ", String.valueOf(viewGlobal));
            Log.d("UMG ", String.valueOf(v));
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("1"));
                String pass_result = jsonObj.getString("1");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_earned ", pass_Obj.getString("month_earned"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("1"));
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_earned"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_earned");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_earned"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText("$" + jObj.getString("month_earned"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAccurate && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            accurateClicked();
            placementMethod(3);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Percentile");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("2"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("2");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_acc"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("2"));
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_acc"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("all_acc");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_acc"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    double first = Double.parseDouble(jObj.getString("all_acc"));
                    first = 100 - first;
                    DecimalFormat dfv = new DecimalFormat("####0.00");
                    double sum = Double.valueOf(dfv.format(first));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(String.valueOf(sum) + "%");

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAccurate && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            accurateClicked();
            placementMethod(4);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Percentile");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("3"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("3");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("month_acc"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("3"));
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_acc"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_acc");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_acc"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    double first = Double.parseDouble(jObj.getString("month_acc"));
                    first = 100 - first;
                    DecimalFormat dfv = new DecimalFormat("####0.00");
                    double sum = Double.valueOf(dfv.format(first));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(String.valueOf(sum) + "%");

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvMostRatings && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            ratingsClicked();
            placementMethod(5);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Ads Rated");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("4"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("4");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_rated"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("4"));
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_rated"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("all_rated");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_rated"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(jObj.getString("all_rated"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvMostRatings && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Global")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            ratingsClicked();
            placementMethod(6);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Ads Rated");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("5"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("5");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("month_rated"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("5"));
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_rated"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_rated");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_rated"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(jObj.getString("month_rated"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            earnersClicked();
            placementMethod(7);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView tvChange = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            tvChange.setText("Total Earned");
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("6"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("6");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_earned"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("6"));
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    Log.d(" - ", " - ");
                    Log.d("u_all_earned", String.valueOf(userStats.get("all_earned")));
                    Log.d("earned ", jObj.getString("all_earned"));
                    Log.d("u_display_name", String.valueOf(userInfo.get("display_name")));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("u_user_loc", String.valueOf(userInfo.get("user_loc")));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d(" - ", " - ");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }

                    String t_obj = jObj.getString("all_earned");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_earned"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText("$" + jObj.getString("all_earned"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            accurateClicked();
            placementMethod(8);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView tvChange = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            tvChange.setText("Total Earned");
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("7"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("7");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("month_earned"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("7"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_earned"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_earned");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_earned"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    double first = Double.parseDouble(jObj.getString("all_acc"));
                    first = 100 - first;
                    DecimalFormat dfv = new DecimalFormat("####0.00");
                    double sum = Double.valueOf(dfv.format(first));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(String.valueOf(sum) + "%");

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAccurate && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            accurateClicked();
            placementMethod(9);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Percentile");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("8"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("8");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_acc"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("8"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_acc"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("all_acc");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_acc"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    double first = Double.parseDouble(jObj.getString("all_acc"));
                    first = 100 - first;
                    DecimalFormat dfv = new DecimalFormat("####0.00");
                    double sum = Double.valueOf(dfv.format(first));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(String.valueOf(sum) + "%");

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAccurate && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            accurateClicked();
            placementMethod(10);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Percentile");
            LLHeader.addView(layout_h);
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("9"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("9");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("month_acc"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("9"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_acc"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_acc");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_acc"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }

                    double first = Double.parseDouble(jObj.getString("all_acc"));
                    first = 100 - first;
                    DecimalFormat dfv = new DecimalFormat("####0.00");
                    double sum = Double.valueOf(dfv.format(first));

                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(String.valueOf(sum) + "%");

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvMostRatings && lastValueSelected.equals("users") && lastTimeValueSelected.equals("All Time") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            ratingsClicked();
            placementMethod(11);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Ads Rated");
            LLHeader.addView(layout_h);
            Log.d("placement 11 ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("10"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("10");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("all_rated"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("10"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_rated"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);

                    Log.d(" - ", " - ");
                    Log.d("u_all_rated", String.valueOf(userStats.get("all_rated")));
                    Log.d("u_display_name", String.valueOf(userInfo.get("display_name")));
                    Log.d("u_user_loc", String.valueOf(userInfo.get("user_loc")));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("all_rated"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d(" - ", " - ");

                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("all_rated");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("all_rated"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(jObj.getString("all_rated"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvMostRatings && lastValueSelected.equals("users") && lastTimeValueSelected.equals("This Month") && lastLocationValueSelected.equals("Local")) {
            Log.d("View ", String.valueOf(v));
            Log.d("Value ", lastValueSelected);
            Log.d("Time ", lastTimeValueSelected);
            Log.d("Location ", lastLocationValueSelected);
            buttonPlaceholder = "tvUsers";
            lastValueSelected = "users";
            viewGlobal = v;
            ratingsClicked();
            placementMethod(12);
            LLLeaderboards.removeAllViews();
            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
            TextView aHeader = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            aHeader.setText("Ads Rated");
            LLHeader.addView(layout_h);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("11"));
                Log.d("testJSON1 ", result);
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("11");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);

                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("display_name"));
                Log.d("pass_display ", pass_Obj.getString("month_rated"));
                Log.d("pass_display ", pass_Obj.getString("user_loc"));
                Log.d("", "");
                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("11"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("display ", jObj.getString("display_name"));
                    Log.d("earned ", jObj.getString("month_rated"));
                    Log.d("location", jObj.getString("user_loc"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    if (colorChanger == 0) {
                        layout.setBackgroundResource(R.drawable.table_gradient_1);
                    } else if (colorChanger == 1) {
                        layout.setBackgroundResource(R.drawable.table_gradient_2);
                    }
                    String t_obj = jObj.getString("month_rated");
                    double d_obj = Double.parseDouble(t_obj);
                    DecimalFormat df = new DecimalFormat("###.00");
                    String formatted = df.format(d_obj);

                    if (
                            String.valueOf(df.format(userStats.get("month_rated"))).equals(formatted) &&
                                    userInfo.get("display_name").equals(jObj.getString("display_name")) &&
                                    userInfo.get("user_loc").equals(jObj.getString("user_loc"))
                            ) {
                        Log.d("Final ", "InTheLastOne");
                        LLUser.removeAllViews();
                        layout.setBackgroundResource(R.drawable.table_gradient);
//                        layout.setBackgroundResource(R.drawable.table_gradient);
                    }
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("display_name"));
                    tvCity.setText(jObj.getString("user_loc"));
                    tvEarnings.setText(jObj.getString("month_rated"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            buttonPlaceholder = "tvAds";
            lastValueSelected = "ads";
            viewGlobal = v;
            earnersClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            TextView t_temp2 = (TextView) layout_h.findViewById(R.id.tvfCompletedHistOther);
            LLHeader.addView(layout_h);
            t_temp.setVisibility(View.GONE);
            t_temp2.setText("Overall Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("12"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("12");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("overall"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("12"));
                    final JSONObject jObj = jsonArray.getJSONObject(i);

                    Log.d("", "");
                    Log.d("jObj array ", String.valueOf(i));
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("overall"));
                    Log.d("lastTimeValueSelected ", lastTimeValueSelected);
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvCity = (TextView) layout.findViewById(R.id.tvTLUser);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));

                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvCompleted.setText(jObj.getString("overall"));
                    tvEarnings.setVisibility(View.GONE);

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    //Still need to display and and hide the layout for the Completed Layout

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvTopEarners && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            buttonPlaceholder = "tvAds";
            lastValueSelected = "ads";
            viewGlobal = v;
            earnersClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            TextView t_temp2 = (TextView) layout_h.findViewById(R.id.tvfCompletedHistOther);
            LLHeader.addView(layout_h);
            t_temp.setVisibility(View.GONE);
            t_temp2.setText("Overall Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("13"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("13");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("overall"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("13"));
                    JSONObject jObj = jsonArray.getJSONObject(i);

                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("overall"));
                    Log.d("", "");
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_earners, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvCity = (TextView) layout.findViewById(R.id.tvTLUser);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvEarnings.setVisibility(View.GONE);
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvCompleted.setText(jObj.getString("overall"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    //Still need to display and and hide the layout for the Completed Layout

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        //The method for the ads/funny section thatt is also all time.
        if (v == tvFunny && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Funny Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("14"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("14");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("funny"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("14"));
                    final JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("funny"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.setClickable(true);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("funny"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        //The method for the ads/funny section that is only for this month
        if (v == tvFunny && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Funny Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("15"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("15");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("funny"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("15"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("funny"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("funny"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvEmotional && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Emotional Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("16"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("16");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("emotional"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("16"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("emotional"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("emotional"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvEmotional && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Funny Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("17"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("17");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("emotional"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("17"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("emotional"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("emotional"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvEffective && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Effective Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("18"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("18");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("effective"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("18"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("effective"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("effective"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvEffective && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Effective Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("19"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("19");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("effective"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("19"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("effective"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("effective"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAttention && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Attention Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("20"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("20");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("attention"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("20"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("attention"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("attention"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvAttention && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Attention Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("21"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("21");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("attention"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("21"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("attention"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("attention"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvExplain && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Explanation Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("22"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("22");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("explain"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("22"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("explain"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("explain"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvExplain && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Explanation Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("23"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("23");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("explain"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("23"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("explain"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("explain"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvReWatch && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Rewatch Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("24"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("24");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("rewatch"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("24"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("rewatch"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("rewatch"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvReWatch && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Rewatch Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("25"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("25");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("rewatch"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("25"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("rewatch"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("rewatch"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvViral && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("All Time")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Viral Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("26"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("26");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("viral"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("26"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("viral"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("viral"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

        if (v == tvViral && lastValueSelected.equals("ads") && lastTimeValueSelected.equals("This Month")) {
            lastValueSelected = "ads";
            viewGlobal = v;
            funnyClicked();

            LLHeader.removeAllViews();
            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny_header, null);
            TextView t_temp = (TextView) layout_h.findViewById(R.id.tvfEarningsHistOther);
            LLHeader.addView(layout_h);
            t_temp.setText("Viral Rating");
            LLLeaderboards.removeAllViews();
            Log.d("result3iswhat ", result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                Log.d("NewJSONOBJ ", jsonObj.getString("27"));
                Log.d("looper ", result);
                String pass_result = jsonObj.getString("27");
                Log.d("ZERO ", pass_result);
                JSONArray jArray = new JSONArray(pass_result);
                JSONObject pass_Obj = jArray.getJSONObject(0);
                Log.d("", "");
                Log.d("pass_position ", String.valueOf(1));
                Log.d("pass_display ", pass_Obj.getString("company_name"));
                Log.d("pass_display ", pass_Obj.getString("ad_title"));
                Log.d("pass_display ", pass_Obj.getString("ad_link"));
                Log.d("pass_display ", pass_Obj.getString("viral"));
                Log.d("", "");

                for (int i = 0; i < leaderboardEntries; i++) {
                    JSONArray jsonArray = new JSONArray(jsonObj.getString("27"));
                    JSONObject jObj = jsonArray.getJSONObject(i);


                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("link", jObj.getString("ad_link"));
                    Log.d("score", jObj.getString("viral"));
                    Log.d("", "");

                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.ad_funny, null);
                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                    TextView tvfName = (TextView) layout.findViewById(R.id.tvfUser);
                    TextView tvfCity = (TextView) layout.findViewById(R.id.tvfCity);
                    TextView tvfEarnings = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                    TextView tvfCompleted = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);

                    final TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                    TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                    final TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                    TextView tvCompleted = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                    LinearLayout CostLayout = (LinearLayout) layout.findViewById(R.id.LLCost);
                    LinearLayout MainLayout = (LinearLayout) layout.findViewById(R.id.LLMain);
                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                    CostLayout.setVisibility(View.VISIBLE);
                    LLLeaderboards.addView(layout);
                    if (colorChanger == 0) {
                        color = colorPrimary;
                        colorChanger = 1;
                    } else if (colorChanger == 1) {
                        color = colorSecondary;
                        colorChanger = 0;
                    }
                    //boardLayout.setBackgroundColor(Color.parseColor(color));
                    tvNum.setText((String.valueOf(i + 1)) + ")");
                    tvName.setText(jObj.getString("company_name"));
                    tvCity.setText(jObj.getString("ad_title"));
                    tvEarnings.setText(jObj.getString("ad_link"));
                    tvCompleted.setText(jObj.getString("viral"));

                    final HashMap<String, String> selectionResults = new HashMap<String, String>();
                    selectionResults.put("title", jObj.getString("ad_title"));
                    selectionResults.put("link", jObj.getString("ad_link"));
                    selectionResults.put("company", jObj.getString("company_name"));
                    selectionResults.put("type", jObj.getString("ad_type"));
                    selectionResults.put("ref", jObj.getString("ad_ref"));

                    tvName.setTextColor(Color.parseColor("#0000FF"));

                    tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = tvEarnings.getText().toString();
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            in.setData(Uri.parse("http://" + url));
                            startActivity(in);
                        }
                    });

                    MainLayout.setId(i);
                    tvNum.setId(i);
                    tvCity.setId(i);
                    tvEarnings.setId(i);
                    tvCompleted.setId(i);

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            displaySelections(selectionResults);

                        }
                    });

                    MainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });


                    tvNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvEarnings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                    tvCompleted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displaySelections(selectionResults);
                        }
                    });

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }

//        if (v == tvAds) {
//            try {
//                JSONObject jsonObj = new JSONObject(result);
//                Log.d("LastJSON ", jsonObj.getString("99"));
//                String pass_result = jsonObj.getString("99");
//                JSONArray jArray = new JSONArray(pass_result);
//                JSONObject pass_Obj = jArray.getJSONObject(0);
//                Log.d("", "");
//                Log.d("pass_position ", String.valueOf(1));
//                Log.d("pass_display ", pass_Obj.getString("company_name"));
//                Log.d("pass_display ", pass_Obj.getString("ad_title"));
//                Log.d("pass_display ", pass_Obj.getString("ad_link"));
//                Log.d("pass_display ", pass_Obj.getString("viral"));
//                Log.d("", "");
//
//                for (int i = 0; i < leaderboardEntries; i++) {
//                    JSONArray jsonArray = new JSONArray(jsonObj.getString("27"));
//                    JSONObject jObj = jsonArray.getJSONObject(i);
//
//
//                    Log.d("", "");
//                    Log.d("position ", String.valueOf(i));
//                    Log.d("company ", jObj.getString("company_name"));
//                    Log.d("title ", jObj.getString("ad_title"));
//                    Log.d("link", jObj.getString("ad_link"));
//                    Log.d("score", jObj.getString("viral"));
//                    Log.d("", "");
//
//                }
//            } catch (org.json.JSONException e) {
//                System.out.println();
//            }
//        }

    }

    @Override
    public void processFinish(String s) {

    }


    public void SendDataToServer(final String raadzid, final String raadzpubid, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadzid));
                nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", raadzpubid));
                nameValuePairs.add(new BasicNameValuePair("raadz_token", token));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getLeaderboardsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getLeaderboardsURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    String line = "";
                    String LineSeparator = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator);
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("httpSendRequest ", httpRequest);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("result", httpRequest);
                        edit.commit();
                    }
                });


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                Log.d("SendToServerResult ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("result", result);
                edit.commit();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Log.d("testDefault ", "inside position default");
//                    Log.d("Testresult ", result);
                            JSONObject LastJSON = new JSONObject(result);
//                    Log.d("LastJSON ", LastJSON.getString("99"));
                            String s_last_Obj = LastJSON.getString("99");
                            JSONArray LastjArray = new JSONArray(s_last_Obj);
                            JSONObject last_Obj = LastjArray.getJSONObject(0);

                            for (int i = 0; i < 28; i++) {
                                String temp = LastJSON.getString(String.valueOf(i));
                                Log.d("Leaderboard Array ", String.valueOf(i) + " | " + temp);
                            }

                            Log.d("Display Name ", last_Obj.getString("display_name"));
                            Log.d("RaadzUserID ", last_Obj.getString("raadz_user_id"));
                            Log.d("User Location ", last_Obj.getString("user_loc"));
                            Log.d("All Accurate ", last_Obj.getString("all_acc"));
                            Log.d("All Earned ", last_Obj.getString("all_earned"));
                            Log.d("All Rated ", last_Obj.getString("all_rated"));
                            Log.d("Month Accurate ", last_Obj.getString("month_acc"));
                            Log.d("Month Earned ", last_Obj.getString("month_earned"));
                            Log.d("Month Rated ", last_Obj.getString("month_rated"));
                            Log.d("JSON_Obj_99 ", s_last_Obj);

                            try {

                                if (Double.parseDouble(last_Obj.getString("all_acc")) < 0) {
                                    String s_temp = String.valueOf(Double.parseDouble(last_Obj.getString("all_acc")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("all_acc", d_temp);
                                } else {
                                    userStats.put("all_acc", Double.parseDouble(last_Obj.getString("all_acc")));
                                }


                                if (Double.parseDouble(last_Obj.getString("all_earned")) < 0) {
                                    String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("all_earned")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("all_earned", d_temp);
                                } else {
                                    userStats.put("all_earned", Double.parseDouble(last_Obj.getString("all_earned")));
                                }


                                if (Double.parseDouble(last_Obj.getString("all_rated")) < 0) {
                                    String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("all_rated")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("all_rated", d_temp);
                                } else {
                                    userStats.put("all_rated", Double.parseDouble(last_Obj.getString("all_rated")));
                                }


                                if (Double.parseDouble(last_Obj.getString("month_acc")) < 0) {
                                    String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_acc")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("month_acc", d_temp);
                                } else {
                                    userStats.put("month_acc", Double.parseDouble(last_Obj.getString("month_acc")));
                                }


                                if (Double.parseDouble(last_Obj.getString("month_earned")) < 0) {
                                    String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_earned")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("month_earned", d_temp);
                                } else {
                                    userStats.put("month_earned", Double.parseDouble(last_Obj.getString("month_earned")));
                                }


                                if (Double.parseDouble(last_Obj.getString("month_rated")) < 0) {
                                    String s_temp = "0" + String.valueOf(Double.parseDouble(last_Obj.getString("month_rated")));
                                    s_temp = s_temp.substring(0, 0);
                                    double d_temp = Double.parseDouble(s_temp);
                                    userStats.put("month_rated", d_temp);
                                } else {
                                    userStats.put("month_rated", Double.parseDouble(last_Obj.getString("month_rated")));
                                }

                            } catch (NumberFormatException e) {
                                System.out.println(e);
                            }

                            userInfo.put("display_name", last_Obj.getString("display_name"));
                            userInfo.put("raadz_user_id", last_Obj.getString("raadz_user_id"));
                            userInfo.put("user_loc", last_Obj.getString("user_loc"));
                        } catch (org.json.JSONException e) {
                            System.out.println(e);
                        }
                    }
                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadzid, raadzpubid, token);
    }


}
