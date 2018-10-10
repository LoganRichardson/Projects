package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.colorSecondary;
import static android.R.attr.x;
import static com.raadz.program.raadzandroid.R.id.LLMain;
import static com.raadz.program.raadzandroid.R.id.LLUser;
import static com.raadz.program.raadzandroid.R.id.ibProfile;
import static com.raadz.program.raadzandroid.R.id.tvDate;
import static com.raadz.program.raadzandroid.R.id.tvResult;
import static com.raadz.program.raadzandroid.R.id.tvStatus;

public class FragActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {
    final String TAG = this.getClass().getName();

    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    Timer myTimer;

    YouTubePlayer player;

    final MediaPlayer mp = new MediaPlayer();

    final HashMap<String, String> drawingResults = new HashMap<String, String>();

    final int MINUTES_IN_AN_HOUR = 60;
    final int SECONDS_IN_A_MINUTE = 60;

    ImageButton ibProfile;

    LinearLayout LLSubmit;
    LinearLayout LLLeaderboards;
    LinearLayout LLHeader;
    LinearLayout LLHistory;
    LinearLayout LLHistoryHeader;
    LinearLayout LLSeperater;
    LinearLayout LLRecent;
    LinearLayout LLStarted;
    LinearLayout LLDrawingsHeader;
    LinearLayout LLDrawingsTable;

    Button bRate;
    Button bMore;
    Button bMoreLeaderboards;
    Button bWithdraw;
    Button bUpdateInfo;
    Button bMoreDrawings;
    Button bFacebook;
    Button bTwitter;

    EditText etTest;

    TextView tvWelcome;
    TextView tvID;
    TextView tvName;
    TextView tvBalance;
    TextView tvNone;
    TextView tvTest;
    TextView tvMoney;
    TextView tvDrawingsPrizeValue;
    TextView tvDrawingsEntriesValue;
    TextView tvDrawingsEntriesValue2;
    TextView tvDrawingsNextValue;

    String getLeaderboardsURL = "https://raadz.com/getLeaderboards.php";
    String getHistoryURL = "https://raadz.com/getUserCompletedAds.php";
    String getDataURL = "https://raadz.com/getUserInfo.php";
    String cityListURL = "https://raadz.com/getCityList.php";
    String getUserDataURL = "https://raadz.com/getUserData.php";
    String getUserConfirmedURL = "https://raadz.com/userconfirmed.php";
    String getDrawingsDetailsURL = "https://raadz.com/getDrawingDetails.php";
    String ToggleFavoriteURL = "https://raadz.com/toggleSavedAd.php";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String colorPrimary = "00cdFF";
    String colorSecondary = "#FFFFFF";
    String color;
    String buttonPlaceholder = "";
    String RID;
    String RT;
    String money = "";
    String balance;
    String result2;
    String result3;
    String httpRequest;
    String userDetails = "";
    String drawing_date;
    String h_zero;
    String m_zero;
    String s_zero;
    String bothTime;

    int currentTime;
    int totalTime;
    int i = 0;
    int total_seconds = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    int totalMinutes = 0;
    int colorChanger = 0;

    NavigationView navigationView = null;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frag);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            DrawingsFunction(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
            doEverything();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }


    public void doEverything() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        ConfirmedFunction(preferences.getString("raadz_user_id", ""));

        if (preferences.getString("email_confirmation", "").equals("not confirmed - resend") || preferences.getString("email_confirmation", "").equals("not confirmed")) {
            Intent in = new Intent(this, FragNotConfirmedActivity.class);
            startActivity(in);
        }

        if (preferences.getString("google_user", "").equals("yes")) {

        }

        Log.d("fbid ", String.valueOf(preferences.getString("raadz_fb_user_id", "")));
        Log.d("id ", preferences.getString("raadz_fb_user_id", ""));
        Log.d("token ", preferences.getString("token", ""));
        if (preferences.getString("raadz_fb_user_id", "").length() > 0) {
            RID = preferences.getString("raadz_fb_user_id", "");
            RT = preferences.getString("token", "");
            edit.putString("raadz_user_id", RID);
            edit.commit();

        }
        if (preferences.getString("raadz_user_id", "").length() > 0) {
            RID = preferences.getString("raadz_user_id", "");
            RT = preferences.getString("token", "");
            Log.d("RID main ", RID);
            Log.d("RT main", RT);
        }

        if (preferences.getString("raadz_user_id", "").equals("user not found")) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.error_dialog, null);
            Button mOk = (Button) mView.findViewById(R.id.bOK);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    edit.commit();
                    Intent in = new Intent(FragActivity.this, LoginActivity.class);
                    startActivity(in);
                }
            });
        }

        Log.d("userdata ", preferences.getString("balance", ""));

        Log.d("RID main ", RID);
        Log.d("RT main", RT);

        Log.d("bf balance: ", preferences.getString("money", ""));

        DataFunction(RID, RT, getDataURL, "user_id", "1");
        balance = preferences.getString("balance", "");
        try {
            JSONArray jArray = new JSONArray(balance);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                Log.d("money: ", jObj.getString("balance"));
                money = jObj.getString("balance");
                edit.remove("funds").commit();
                edit.putString("funds", money);
                edit.commit();
            }
        } catch (org.json.JSONException e) {
            System.out.println();
        }

        DataFunction(RID, RT, getUserDataURL, "raadz_user_id", "2");

        Log.d("bf balance: ", preferences.getString("money", ""));
        Log.d("bf F: ", preferences.getString("fName", ""));
        Log.d("bf L: ", preferences.getString("lName", ""));
        Log.d("1", "1");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        LLSubmit = (LinearLayout) findViewById(R.id.LLSubmit);
        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHeader = (LinearLayout) findViewById(R.id.LLHeader);
        LLHistory = (LinearLayout) findViewById(R.id.LLHistory);
        LLHistoryHeader = (LinearLayout) findViewById(R.id.LLHistoryHeader);
        LLSeperater = (LinearLayout) findViewById(R.id.LLSeperater);
        LLRecent = (LinearLayout) findViewById(R.id.LLRecent);
        LLStarted = (LinearLayout) findViewById(R.id.LLStarted);
        LLDrawingsHeader = (LinearLayout) findViewById(R.id.LLDrawingsHeader);
        LLDrawingsTable = (LinearLayout) findViewById(R.id.LLDrawingsTable);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvNone = (TextView) findViewById(R.id.tvNone);
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvMoney = (TextView) findViewById(R.id.tvMoney);

        tvDrawingsPrizeValue = (TextView) findViewById(R.id.tvDrawingsPrizeValue);
        tvDrawingsEntriesValue = (TextView) findViewById(R.id.tvDrawingsEntriesValue);
        tvDrawingsEntriesValue2 = (TextView) findViewById(R.id.tvDrawingsEntriesValue2);
        tvDrawingsNextValue = (TextView) findViewById(R.id.tvDrawingsNextValue);

        etTest = (EditText) findViewById(R.id.etTest);

        bUpdateInfo = (Button) findViewById(R.id.bUpdateInfo);
        bRate = (Button) findViewById(R.id.bRate);
        bMore = (Button) findViewById(R.id.bMore);
        bMoreLeaderboards = (Button) findViewById(R.id.bMoreLeaderboards);
        bWithdraw = (Button) findViewById(R.id.bWithdraw);
        bMoreDrawings = (Button) findViewById(R.id.bMoreDrawings);
        bFacebook = (Button) findViewById(R.id.bFacebook);
        bTwitter = (Button) findViewById(R.id.bTwitter);

        Log.d("canwe ", preferences.getString("cash", ""));
        tvMoney.setText("$" + preferences.getString("cash", ""));
        tvBalance.setText("$" + preferences.getString("cash", ""));
        Log.d("new balance ", preferences.getString("cash", ""));

        Log.d("setterHome ", preferences.getString("user_details_complete", ""));

        String info = preferences.getString("cash", "");
        Log.d("money info ", info);

        Log.d("2", "2");
        //tvMoney.setText("$" + test);
        Log.d("3", "3");

        edit.putString("funds", info);
        edit.commit();

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (preferences.getString("fbID", "").length() > 1) {
            //Bitmap bitmap = getFacebookProfilePicture(preferences.getString("fbID", ""));
            //Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), bitmap);

        }
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile);
        toolbar.setOverflowIcon(drawable);
        drawer.setDrawerListener(toggle);
        //toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu4);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

        Log.d("id ", preferences.getString("raadz_user_id", ""));

//        DrawingsFunction(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));

        {
            HashMap postData2 = new HashMap();
            postData2.put("raadz_user_id", RID);
            postData2.put("user_id", RID);
            postData2.put("token", RT);
            PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData2);
            task2.execute(getHistoryURL);
        }

        result2 = preferences.getString("FR2", "");
        Log.d("drawingsResult ", preferences.getString("drawings_result", ""));
        Log.d("result2 ", result2);

        defaultLeaderboards();

//        defaultDrawings();

        bRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragActivity.this, PickAdActivity.class);
                startActivity(in);
            }
        });

        bUpdateInfo.setOnClickListener(this);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragActivity.this, AdsHistory.class);
                startActivity(in);
            }
        });

        bMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragActivity.this, UserAdHistoryActivity.class);
                startActivity(in);
            }
        });

        bMoreDrawings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragActivity.this, UserDrawingsActivity.class);
                startActivity(in);
            }
        });

        bMoreLeaderboards.setOnClickListener(this);
    }

    public void defaultLeaderboards() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                result3 = preferences.getString("IndexPass", "");
                LLLeaderboards.removeAllViews();
                LLHeader.removeAllViews();
                LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
                LLHeader.addView(layout_h);
                Log.d("result3iswhat ", result3);
                edit.putString("l_result", preferences.getString("IndexPass", ""));
                edit.commit();
                try {
                    JSONObject jsonObj = new JSONObject(result3);
                    Log.d("NewJSONOBJ ", jsonObj.getString("0"));
                    Log.d("testJSON1 ", result3);
                    Log.d("looper ", result3);
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
                    for (int i = 0; i < 5; i++) {
                        JSONArray jsonArray = new JSONArray(jsonObj.getString("0"));
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
                        TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                        TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
                        TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
                        TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                        TextView tvDate = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
                        LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                        if (colorChanger == 0) {
                            layout.setBackgroundResource(R.drawable.table_gradient_1);
                        } else if (colorChanger == 1) {
                            layout.setBackgroundResource(R.drawable.table_gradient_2);
                        }
//                        String t_obj = jObj.getString("all_acc");
//                        double d_obj = Double.parseDouble(t_obj);
//                        DecimalFormat df = new DecimalFormat("###.00");
//                        String formatted = df.format(d_obj);
//

                        runOnUiThread(new Runnable() {
                            @Override
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
                        Log.d("here3 ", "here3");
                        tvNum.setText((String.valueOf(i + 1)) + ")");
                        tvName.setText(jObj.getString("display_name"));
                        tvCity.setText(jObj.getString("user_loc"));
                        tvEarnings.setText("$" + jObj.getString("all_earned"));

                        TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
                        TextView tv2 = (TextView) layout.findViewById(R.id.tvfCity);
                        TextView tv3 = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                        TextView tv4 = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }
            }
        };

        thread.start();
    }

//    public void defaultDrawings() {
//        LLDrawingsTable.removeAllViews();
//        LLDrawingsHeader.removeAllViews();
//        LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.drawings_history_header, null);
//        LLDrawingsHeader.addView(layout_h);
//        Log.d("defaultResult ", resultDrawings);
//        Log.d("defaultResult2 ", resultDrawings);
//        try {
//            JSONArray jArray = new JSONArray(resultDrawings);
//            Log.d("jArray ", String.valueOf(jArray.length()));
//            Log.d("history result ", resultDrawings);
////            String test = String.valueOf(jArray.getJSONObject(2));
////            Log.d("ohTest ", test);
//
//
//            {
//                JSONArray jArray3 = new JSONArray(resultDrawings);
//                Log.d("TAG_jArray3 ", jArray3.getString(2));
//                Log.d("TAG_jArray4 ", jArray3.getString(3));
//                Log.d("TAG_jArray5 ", String.valueOf(jArray3.getInt(4)));
//                Log.d("TAG_Before ", "Before");
//
//                tvDrawingsEntriesValue.setText(jArray3.getString(2));
//                tvDrawingsEntriesValue2.setText(jArray3.getString(3));
//                total_seconds = jArray3.getInt(4);
//                Log.d("totalSeconds ", String.valueOf(total_seconds));
//
//                //Log.d("TAG2 ", jObj.getString("3");
//
//            }
//
////            drawingResults.put("current_p", jObj.getString("reward_val"));
//
//
//            JSONArray jArray2 = new JSONArray(resultDrawings);
//            for (int i = 0; i < jArray2.getJSONArray(1).length(); i++) {
//                JSONObject jObj = jArray2.getJSONArray(1).getJSONObject(i);
//                Log.d("RewardVal ", jObj.getString("reward_val"));
//                Log.d("Bonus ", jObj.getString("reward_text"));
//                Log.d("ToBalance ", jObj.getString("add_to_balance"));
//
//                drawingResults.put(String.valueOf(i) + "_v", jObj.getString("reward_val"));
//                drawingResults.put(String.valueOf(i) + "_t", jObj.getString("reward_text"));
//                drawingResults.put(String.valueOf(i) + "_a", jObj.getString("add_to_balance"));
//
//            }
//
//            for (int i = 0; i < 6; i++) {
//                JSONObject jObj = jArray.getJSONArray(0).getJSONObject(i);
//                Log.d(" - ", " - ");
////                Log.d("Length2 ", String.valueOf(jArray.getJSONObject(0).length()));
//                Log.d("Length ", String.valueOf(jObj.length()));
//                Log.d("Length2 ", String.valueOf(jArray.length()));
//                Log.d("Date ", jObj.getString("drawing_date"));
//                Log.d("Winner ", jObj.getString("user_name"));
//                Log.d("Reward ", jObj.getString("reward"));
//                Log.d(" - ", " - ");
//                final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.drawings_history, null);
//                TextView tvDate = (TextView) layout.findViewById(R.id.tvDateValue);
//                TextView tvWinner = (TextView) layout.findViewById(R.id.tvWinnerValue);
//                TextView tvPrize = (TextView) layout.findViewById(R.id.tvPrizeValue);
//
//                if (i == 0) {
//                    drawing_date = jObj.getString("drawing_date");
//                    tvDrawingsNextValue.setText(drawing_date);
//                    if (jObj.getString("reward").equals("1")) {
//                        Log.d("reward_1 ", jObj.getString("reward"));
//                        Log.d("1_t ", drawingResults.get("1_t"));
//                        tvDrawingsPrizeValue.setText(drawingResults.get("1_t"));
//                    }
//                    if (jObj.getString("reward").equals("2")) {
//                        Log.d("reward_2 ", jObj.getString("reward"));
//                        Log.d("2_t ", drawingResults.get("2_t"));
//                        tvDrawingsPrizeValue.setText(drawingResults.get("2_t"));
//                    }
//                    if (jObj.getString("reward").equals("3")) {
//                        Log.d("reward_3 ", jObj.getString("reward"));
//                        Log.d("3_t ", drawingResults.get("3_t"));
//                        tvDrawingsPrizeValue.setText(drawingResults.get("3_t"));
//                    }
//                } else {
//
//                    LLDrawingsTable.addView(layout);
//
//                    String d_date = jObj.getString("drawing_date");
//                    d_date = d_date.substring(0, 10);
//                    tvDate.setText(d_date);
//                    tvWinner.setText(jObj.getString("user_name"));
//
//                    if (jObj.getString("reward").equals("1")) {
//                        tvPrize.setText(drawingResults.get("0_t"));
//                    }
//                    if (jObj.getString("reward").equals("2")) {
//                        tvPrize.setText(drawingResults.get("1_t"));
//                    }
//                    if (jObj.getString("reward").equals("3")) {
//                        tvPrize.setText(drawingResults.get("2_t"));
//                    }
//
//                }
//            }
//
////                    JSONObject jsonObj2 = new JSONObject(resultDrawings);
////                    Log.d("NextJSON ", jsonObj.getString("1"));
////                    Log.d("AsyncReturn ", resultDrawings);
////                    for (int i = 0; i < 3; i++) {
////                        JSONArray jsonArray = new JSONArray(jsonObj2.getString("1"));
////                        JSONObject jObj = jsonArray.getJSONObject(i);
////
////
////                        Log.d("jObj ", jObj.getString("reward_val"));
////                        Log.d("jObj ", jObj.getString("reward_text"));
////                        Log.d("jObj ", jObj.getString("add_to_balance"));
////                    }
//
//
//        } catch (org.json.JSONException e) {
//            System.out.println();
//        }
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        seconds = total_seconds % SECONDS_IN_A_MINUTE;
//        totalMinutes = total_seconds / SECONDS_IN_A_MINUTE;
//        minutes = totalMinutes % MINUTES_IN_AN_HOUR;
//        hours = totalMinutes / MINUTES_IN_AN_HOUR;
//
//        myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                TimerMethod();
//            }
//
//        }, 0, 1000);
//
//    }

    public void TimerMethod() {

        if (minutes == 0) {
            if ((hours - 1) == -1) {
                hours = 0;
            } else {
                hours = hours - 1;
            }

            minutes = 60;
        }

        if (seconds == 0) {
            if ((minutes - 1) == -1) {
                minutes = 0;
            } else {
                minutes = minutes - 1;
            }
            seconds = 60;
        }

        seconds = seconds - 1;

        if (seconds < 10) {
            s_zero = "0";
        } else if (seconds > 10) {
            s_zero = "";
        }

        if (minutes < 10) {
            m_zero = "0";
        } else if (minutes > 10) {
            m_zero = "";
        }

        if (hours < 10) {
            h_zero = "0";
        } else if (hours > 10) {
            h_zero = "";
        }

        Log.d("seconds ", String.valueOf(seconds));
        Log.d("minutes ", String.valueOf(minutes));
        Log.d("hours ", String.valueOf(hours));
        this.runOnUiThread(Timer_Tick);
    }

    public Runnable Timer_Tick = new Runnable() {
        @Override
        public void run() {
            tvDrawingsNextValue.setText(h_zero + hours + ":" + m_zero + minutes + ":" + s_zero + seconds);
        }
    };

    public void onDestroy() {
        super.onDestroy();
        if(myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
        mp.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
        mp.stop();
    }

    @Override
    protected void onStop() {
        Log.w(TAG, "Application has stopped");

        super.onStop();
    }

    public void recentDetails(final HashMap<String, String> adResults) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.uh_recent_dialog, null);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final CheckBox mFav = (CheckBox) mView.findViewById(R.id.cbFav);
        Button mDone = (Button) mView.findViewById(R.id.bDone);
        TextView tvCompany = (TextView) mView.findViewById(R.id.tvCompany);
        TextView tvAd = (TextView) mView.findViewById(R.id.tvAd);
        TextView tvLink = (TextView) mView.findViewById(R.id.tvLink);
        TextView tvCost = (TextView) mView.findViewById(R.id.tvCost);
        TextView tvViews = (TextView) mView.findViewById(R.id.tvViews);
        TextView tvDate = (TextView) mView.findViewById(R.id.tvDate);

        Button mFacebook = (Button) mView.findViewById(R.id.bFacebook);
        Button mTwitter = (Button) mView.findViewById(R.id.bTwitter);


        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);
        ImageView ivImage = mView.findViewById(R.id.ivImage);

        TextView mStatus = mView.findViewById(R.id.tvStatus);

        Button mVideo = mView.findViewById(R.id.bVideo);
        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        Button mPlay = mView.findViewById(R.id.bPlay);
        Button mPause = mView.findViewById(R.id.bPause);

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        if (adResults.get("type").equals("1")) {
            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                    Log.d("test", "inside initizedlistener");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    final String youtubeURL = adResults.get("ref");
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
                            //player.setFullscreen(true);
                            //player.setShowFullscreenButton(false);
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

        if (adResults.get("type").equals("2")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);
            mAudio.setVisibility(View.GONE);

            Log.d("link ", azureImages);
            Log.d("path ", adResults.get("ref"));
            String imgPath = azureImages + adResults.get("ref");
            Picasso.with(getApplicationContext()).load(imgPath).into(ivImage);
            ivImage.setVisibility(View.VISIBLE);
        }

        if (adResults.get("type").equals("3")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mAudio.setVisibility(View.VISIBLE);

            mAudioLayout.setVisibility(View.VISIBLE);
            audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mStatus, mView);
        }


        tvCompany.setText(adResults.get("company"));
        tvAd.setText(adResults.get("title"));
        tvLink.setText(adResults.get("link"));
        if (adResults.get("percent").equals("null")) {
            tvCost.setText("N/A");
        } else {
            double value_d = 100 - Double.parseDouble(adResults.get("percent"));

            tvCost.setText(String.valueOf(value_d) + "%");
        }
        if (adResults.get("earnings").equals("null")) {
            tvViews.setText("N/A");
        } else {
            tvViews.setText("$" + adResults.get("earnings"));
        }
        tvDate.setText(adResults.get("date").substring(0, 10));

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog = new ShareDialog(FragActivity.this);
                String fbURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "") + adResults.get("adid");
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
                buttonPlaceholder = "bShareTwitter";
                String tweetURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "") + adResults.get("adid");
                String tweetText = "Come check out this ad on Raadz.com!";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?url=" + tweetURL + "&text=" + tweetText));
                startActivity(browserIntent);
            }
        });

        mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFav.isChecked()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    HashMap postData = new HashMap();
                    postData.put("user_id", preferences.getString("raadz_user_id", ""));
                    postData.put("token", preferences.getString("token", ""));
                    postData.put("ad_id", adResults.get("adid"));
                    PostResponseAsyncTask task = new PostResponseAsyncTask(FragActivity.this, postData);
                    task.execute(ToggleFavoriteURL);
                }
                if (!mFav.isChecked()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    HashMap postData = new HashMap();
                    postData.put("user_id", preferences.getString("raadz_user_id", ""));
                    postData.put("token", preferences.getString("token", ""));
                    postData.put("ad_id", adResults.get("adid"));
                    PostResponseAsyncTask task = new PostResponseAsyncTask(FragActivity.this, postData);
                    task.execute(ToggleFavoriteURL);
                }
            }
        });


        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = adResults.get("link");
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("http://" + url));
                startActivity(in);
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adResults.get("type").equals("1")) {
                    player.release();
                }
                if (adResults.get("type").equals("3")) {
                    mp.stop();
                }
                dialog.dismiss();
                return;
            }
        });
    }

    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
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

                            TextView status = (TextView) mView.findViewById(R.id.tvStatus);

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

    public void audioPlayer(final String path, final String fileName, Button mPlay, Button mPause, final TextView mStatus, final View mView) {
        try {
            mp.reset();
            mp.setDataSource(path + fileName);
            mp.prepare();

            Log.d("file ", fileName);

            audioTrack(mView);


        } catch (Exception e) {
            System.out.println();
        }

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                audioTrack(mView);
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        Button mCancel = (Button) mView.findViewById(R.id.bCancel);
        Button mEnter = (Button) mView.findViewById(R.id.bClose);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseApplication close = new CloseApplication();
                close.forceClose(getApplicationContext());
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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
                        Intent in = new Intent(FragActivity.this, IndexActivity.class);
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
        } else if (id == R.id.user_history) {
            Intent in = new Intent(this, UserAdHistoryActivity.class);
            startActivity(in);
        } else if (id == R.id.user_leaderboards) {
            onClick(bMoreLeaderboards);
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
                    Intent in = new Intent(FragActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragActivity.this);
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

    public void DataFunction(final String raadz_user_id, final String token, final String URL, final String post, final String count) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            TextView tvMoney;
            TextView tvName;
            TextView tvfName;
            TextView tvlName;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                tvMoney = (TextView) findViewById(R.id.tvMoney);
                tvName = (TextView) findViewById(R.id.tvName);

            }

            @Override
            protected String doInBackground(String... params) {

                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor edit = preferences.edit();
                Log.d("ID ", raadz_user_id);
                Log.d("DFToken ", token);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                //nameValuePairs.add(new BasicNameValuePair("user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair(post, raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(URL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);

                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line = "";
                    String LineSeparator = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator);
                        //Log.d("stringBuffer: ", stringBuffer.toString());
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("see this one ", httpRequest);

                if (count.equals("1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            balance = httpRequest;
                            Log.d("httpRequest Balance ", balance);
                            try {
                                JSONArray jArray = new JSONArray(balance);
                                //Log.d("json: ", httpRequest);
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject jObj = jArray.getJSONObject(i);
                                    Log.d("money: ", jObj.getString("balance"));
                                    money = jObj.getString("balance");
                                    edit.remove("funds").commit();
                                    edit.putString("funds", money);
                                    edit.commit();
                                }
                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                            tvMoney.setText("$" + money);
                            tvBalance.setText("$" + money);
                            Log.d("balance funds ", tvBalance.getText().toString());
                        }
                    });
                }

                if (count.equals("2")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray jArray = new JSONArray(httpRequest);
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject jObj = jArray.getJSONObject(i);
                                    if (jObj.getString("interest_value").equals("user_details_complete")) {
                                        if (jObj.getString("interest_text").equals("1")) {
                                            Log.d("true ", "it is 1");
                                            edit.putString("getting_started", "true");
                                            edit.commit();
                                        } else {
                                            edit.putString("getting_started", "false");
                                            edit.commit();
                                            Log.d("false ", "it is 0");
                                        }
                                    }
                                }
                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }

                        }
                    });
                }
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (count.equals("1")) {
                    Log.d("Result: ", result);
                    if (result.contains("user not found")) {
                        Log.d("echo 2:  ", "user not found");
                        //Toast.makeText(UpdateInformation.this, "ratings submitted", Toast.LENGTH_SHORT).show();
                        //Intent in = new Intent(PublisherActivity.this, ConfirmEmailActivity.class);
                        //startActivity(in);
                    } else {

                        balance = result;

                        Log.d("httpRequest Balance ", balance);
                        try {
                            JSONArray jArray = new JSONArray(balance);
                            //Log.d("json: ", httpRequest);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                Log.d("money: ", jObj.getString("balance"));
                                money = jObj.getString("balance");
                                edit.remove("funds").commit();
                                edit.putString("funds", money);
                                edit.commit();
                            }
                        } catch (org.json.JSONException e) {
                            System.out.println();
                        }
                        tvMoney.setText("$" + money);
                        tvBalance.setText("$" + money);
                        Log.d("balance funds ", tvBalance.getText().toString());


                        Log.d("else JSON: ", result);
                        balance = result;
                        edit.putString("money", result);
                        edit.apply();
                        Log.d("Balance else: ", preferences.getString("money", ""));

                    }
                }
                if (count.equals("2")) {
                    Log.d("User Data ", result);
                    try {
                        SharedPreferences preferencesU = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editU = preferencesU.edit();
                        JSONArray jArray = new JSONArray(result);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            if (jObj.getString("interest_value").equals("user_details_complete")) {
                                if (jObj.getString("interest_text").equals("1")) {
                                    Log.d("userDetails ", userDetails);
                                    LLSubmit.setVisibility(View.VISIBLE);
                                    LLStarted.setVisibility(View.GONE);
                                    editU.putString("user_details_complete", jObj.getString("interest_text"));
                                    editU.commit();

                                }
                                if (jObj.getString("interest_text").equals("0")) {
                                    LLSubmit.setVisibility(View.GONE);
                                    LLStarted.setVisibility(View.VISIBLE);
                                    editU.putString("user_details_complete", jObj.getString("interest_text"));
                                    editU.commit();
                                }
                            }
                            if (jObj.getString("interest_value").equals("user_name")) {
                                String t_name = jObj.getString("interest_text");
                                String f_name;
                                String l_name;
                                Log.d("jObj Name ", jObj.getString("interest_text"));
                                String[] arr = t_name.split(":");
                                f_name = arr[0];
                                l_name = arr[1];
                                tvName.setText("Welcome " + f_name + " " + l_name);
                            }
                        }
                    } catch (org.json.JSONException e) {
                        System.out.println();
                    }
                }

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token, URL, post, count);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (view == bUpdateInfo) {
            buttonPlaceholder = "bUserPref";
            HashMap postData = new HashMap();
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(cityListURL);
        }
        if (view == bMoreLeaderboards) {
            buttonPlaceholder = "bMoreLeaderboards";
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", RID);
            postData.put("raadz_pub_id", "");
            postData.put("raadz_token", RT);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(getLeaderboardsURL);
        }

    }

    @Override
    public void processFinish(String s) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        String percent;
        String earnings;

        result2 = s;
        Log.d("postValue ", preferences.getString("postSend2", ""));
        Log.d("post result  ", s);
        edit.putString("FR2", s);
        edit.commit();
        if (buttonPlaceholder.equals("bMoreLeaderboards")) {
            Log.d("More Leaderboards ", s);
            edit.putString("l_result", s);
            edit.commit();
            Intent in = new Intent(FragActivity.this, LeaderboardsUserActivity.class);
            startActivity(in);
        }
        if (buttonPlaceholder.equals("bUserPref")) {
            Log.d("the cities ", s);
            edit.putString("city_list", s);
            edit.commit();
            Intent in = new Intent(FragActivity.this, UpdateInformation.class);
            startActivity(in);
        }
        if (result2.contains("no completed ads")) {
            Log.d("1 ", "first if");
            tvNone.setVisibility(View.VISIBLE);
        } else {
            LLHistoryHeader.removeAllViews();
            final LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout_header, null);
            LLHistoryHeader.addView(layout_h);
            LLHistory.removeAllViews();
            Log.d("2 ", "second if");
            tvNone.setVisibility(View.GONE);
            Log.d("try this 1 ", preferences.getString("FR1", ""));
            Log.d("try this 2 ", result2);
            try {
                Log.d("3 ", "inside try");
                JSONArray jArray = new JSONArray(result2);
                Log.d("3/2 ", String.valueOf(jArray.length()));
                for (i = 0; i < 5; i++) {
                    Log.d("4 ", "inside for");
                    final JSONObject jObj = jArray.getJSONObject(i);
                    Log.d("", "");
                    Log.d("position ", String.valueOf(i));
                    Log.d("company ", jObj.getString("company_name"));
                    Log.d("title ", jObj.getString("ad_title"));
                    Log.d("percent", jObj.getString("percentile"));
                    Log.d("money", jObj.getString("earnings"));
                    Log.d("", "");
                    final HashMap<String, String> adResults = new HashMap<String, String>();
                    adResults.put("adid", jObj.getString("raadz_ad_id"));
                    adResults.put("type", jObj.getString("ad_type"));
                    adResults.put("company", jObj.getString("company_name"));
                    adResults.put("title", jObj.getString("ad_title"));
                    adResults.put("link", jObj.getString("ad_link"));
                    adResults.put("percent", jObj.getString("percentile"));
                    adResults.put("earnings", jObj.getString("earnings"));
                    adResults.put("date", jObj.getString("update_time"));
                    adResults.put("ref", jObj.getString("ad_ref"));
                    runOnUiThread(new Runnable() {
                        public void run() {

                            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout, null);
                            LinearLayout LLCost = (LinearLayout) layout.findViewById(R.id.LLCost);
                            LinearLayout LLCostH = (LinearLayout) layout_h.findViewById(R.id.LLCost);

                            LinearLayout LLMain = (LinearLayout) layout.findViewById(R.id.LLMain);

                            TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                            TextView tvt1 = (TextView) layout.findViewById(R.id.tvTLUser);
                            TextView tvt2 = (TextView) layout.findViewById(R.id.tvCityOther);
                            TextView tvt3 = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                            TextView tvt4 = (TextView) layout.findViewById(R.id.tvCompletedHistOther);

                            tvt1.setTextColor(Color.parseColor("#0000FF"));

                            final TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
                            TextView tv2 = (TextView) layout.findViewById(R.id.tvfCity);
                            TextView tv3 = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
                            TextView tv4 = (TextView) layout_h.findViewById(R.id.tvfCompletedHistOther);

                            LLMain.setId(i);
                            tvNum.setId(i);
                            tvt2.setId(i);
                            tvt3.setId(i);
                            tvt4.setId(i);

                            LLMain.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recentDetails(adResults);
                                }
                            });


                            tvNum.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recentDetails(adResults);
                                }
                            });

                            tvt2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recentDetails(adResults);
                                }
                            });

                            tvt3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recentDetails(adResults);
                                }
                            });

                            tvt4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recentDetails(adResults);
                                }
                            });

                            LLCost.setVisibility(View.VISIBLE);
                            LLCostH.setVisibility(View.VISIBLE);
                            tv4.setText("Earnings");

                            LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                            LLHistory.addView(layout);

                            try {
                                if (jObj.getString("percentile").equals("null")) {
                                    tvt4.setText("-");
                                } else {
                                    double sum = Double.parseDouble(jObj.getString("percentile"));
                                    sum = 100 - sum;
                                    tvt4.setText(sum + "%");
                                }
                                if (jObj.getString("earnings").equals("null")) {
                                    tvt3.setText("Still Active");
                                } else
                                    tvt3.setText("$" + jObj.getString("earnings"));
                                tvNum.setText((String.valueOf(i + 1)) + ")");
                                tvt1.setText(jObj.getString("company_name"));
                                tvt2.setText(jObj.getString("ad_title"));
                                //tvt3.setText(jObj.getString("percentile") + "%");

                            } catch (org.json.JSONException e) {
                                System.out.println(e);
                            }

                            tvt1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        String url = ("http://" + jObj.getString("ad_link"));
                                        Intent in = new Intent(Intent.ACTION_VIEW);
                                        in.setData(Uri.parse(url));
                                        startActivity(in);
                                    } catch (org.json.JSONException e) {
                                        System.out.println(e);
                                    }
                                }
                            });


                        }
                    });


//                            tv1.setText(jObj.getString("Company"));
//                            tv2.setText(jObj.getString("Ad"));
//                            tv3.setText(jObj.getString("Percentile"));
//                            tv4.setText(jObj.getString("Earnings"));

                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
        }
    }


    public void ConfirmedFunction(final String raadz_user_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

//                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getUserConfirmedURL);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getUserConfirmedURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line = "";
                    String LineSeparator2 = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator2);
                        //Log.d("stringBuffer: ", stringBuffer.toString());
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
                Log.d("httpRequestUpdate: ", httpRequest);
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("userconfirmed result ", result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id);
    }


    public void DrawingsFunction(final String raadz_user_id, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("raadz_token", token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getDrawingsDetailsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getDrawingsDetailsURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line = "";
                    String LineSeparator2 = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator2);
                        //Log.d("stringBuffer: ", stringBuffer.toString());
                        //Youtube player stand alone player otkthe ofor that noe that doe shta.
                        //standalone player
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String resultDrawings) {
                super.onPostExecute(resultDrawings);
                Log.d("post_exe ", resultDrawings);
                Log.d("Index_Drawings", httpRequest);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("drawings_result", resultDrawings);
                edit.commit();


                LLDrawingsTable.removeAllViews();
                LLDrawingsHeader.removeAllViews();
                LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.drawings_history_header, null);
                LLDrawingsHeader.addView(layout_h);
                Log.d("defaultResult ", resultDrawings);
                Log.d("defaultResult2 ", resultDrawings);
                try {
                    JSONArray jArray = new JSONArray(resultDrawings);
                    Log.d("jArray ", String.valueOf(jArray.length()));
                    Log.d("history result ", resultDrawings);
                    {
                        JSONArray jArray3 = new JSONArray(resultDrawings);
                        Log.d("TAG_jArray3 ", jArray3.getString(2));
                        Log.d("TAG_jArray4 ", jArray3.getString(3));
                        Log.d("TAG_jArray5 ", String.valueOf(jArray3.getInt(4)));
                        Log.d("TAG_Before ", "Before");

                        tvDrawingsEntriesValue.setText(jArray3.getString(2));
                        tvDrawingsEntriesValue2.setText(jArray3.getString(3));
                        total_seconds = jArray3.getInt(4);
                        Log.d("totalSeconds ", String.valueOf(total_seconds));

                        //Log.d("TAG2 ", jObj.getString("3");

                    }

//            drawingResults.put("current_p", jObj.getString("reward_val"));


                    JSONArray jArray2 = new JSONArray(resultDrawings);
                    for (int i = 0; i < jArray2.getJSONArray(1).length(); i++) {
                        JSONObject jObj = jArray2.getJSONArray(1).getJSONObject(i);
                        Log.d("RewardVal ", jObj.getString("reward_val"));
                        Log.d("Bonus ", jObj.getString("reward_text"));
                        Log.d("ToBalance ", jObj.getString("add_to_balance"));

                        drawingResults.put(String.valueOf(i) + "_v", jObj.getString("reward_val"));
                        drawingResults.put(String.valueOf(i) + "_t", jObj.getString("reward_text"));
                        drawingResults.put(String.valueOf(i) + "_a", jObj.getString("add_to_balance"));

                    }

                    for (int i = 0; i < 6; i++) {
                        JSONObject jObj = jArray.getJSONArray(0).getJSONObject(i);
                        Log.d(" - ", " - ");
//                Log.d("Length2 ", String.valueOf(jArray.getJSONObject(0).length()));
                        Log.d("Length ", String.valueOf(jObj.length()));
                        Log.d("Length2 ", String.valueOf(jArray.length()));
                        Log.d("Date ", jObj.getString("drawing_date"));
                        Log.d("Winner ", jObj.getString("user_name"));
                        Log.d("Reward ", jObj.getString("reward"));
                        Log.d(" - ", " - ");
                        final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.drawings_history, null);
                        TextView tvDate = (TextView) layout.findViewById(R.id.tvDateValue);
                        TextView tvWinner = (TextView) layout.findViewById(R.id.tvWinnerValue);
                        TextView tvPrize = (TextView) layout.findViewById(R.id.tvPrizeValue);

                        if (i == 0) {
                            Log.d("drawing_reward_value ", jObj.getString("reward"));
                            drawing_date = jObj.getString("drawing_date");
                            tvDrawingsNextValue.setText(drawing_date);
                            if (jObj.getString("reward").equals("1")) {
                                Log.d("reward_1 ", jObj.getString("reward"));
                                Log.d("0_t ", drawingResults.get("0_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("0_t"));
                            }
                            else if (jObj.getString("reward").equals("2")) {
                                Log.d("reward_2 ", jObj.getString("reward"));
                                Log.d("1_t ", drawingResults.get("1_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("1_t"));
                            }
                            else if (jObj.getString("reward").equals("3")) {
                                Log.d("reward_3 ", jObj.getString("reward"));
                                Log.d("2_t ", drawingResults.get("2_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("2_t"));
                            }
                        } else {
                            if(jObj.getString("user_name").length() >= 2) {
                                LLDrawingsTable.addView(layout);

                                String d_date = jObj.getString("drawing_date");
                                d_date = d_date.substring(0, 10);
                                tvDate.setText(d_date);
                                tvWinner.setText(jObj.getString("user_name"));

                                if (jObj.getString("reward").equals("1")) {
                                    tvPrize.setText(drawingResults.get("0_t"));
                                }
                                if (jObj.getString("reward").equals("2")) {
                                    tvPrize.setText(drawingResults.get("1_t"));
                                }
                                if (jObj.getString("reward").equals("3")) {
                                    tvPrize.setText(drawingResults.get("2_t"));
                                }
                            }
                        }
                    }

//                    JSONObject jsonObj2 = new JSONObject(resultDrawings);
//                    Log.d("NextJSON ", jsonObj.getString("1"));
//                    Log.d("AsyncReturn ", resultDrawings);
//                    for (int i = 0; i < 3; i++) {
//                        JSONArray jsonArray = new JSONArray(jsonObj2.getString("1"));
//                        JSONObject jObj = jsonArray.getJSONObject(i);
//
//
//                        Log.d("jObj ", jObj.getString("reward_val"));
//                        Log.d("jObj ", jObj.getString("reward_text"));
//                        Log.d("jObj ", jObj.getString("add_to_balance"));
//                    }


                } catch (org.json.JSONException e) {
                    System.out.println();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                seconds = total_seconds % SECONDS_IN_A_MINUTE;
                totalMinutes = total_seconds / SECONDS_IN_A_MINUTE;
                minutes = totalMinutes % MINUTES_IN_AN_HOUR;
                hours = totalMinutes / MINUTES_IN_AN_HOUR;

                myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        TimerMethod();
                    }

                }, 0, 1000);


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token);
    }


}
