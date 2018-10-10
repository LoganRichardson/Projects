package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.x;
import static com.raadz.program.raadzandroid.R.id.tvResult;
import static com.raadz.program.raadzandroid.R.id.tvStatus;

public class UserDrawingsActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {
    final String TAG = this.getClass().getName();


    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    Timer myTimer;

    ImageButton ibProfile;

    final HashMap<String, String> drawingResults = new HashMap<String, String>();

    final int MINUTES_IN_AN_HOUR = 60;
    final int SECONDS_IN_A_MINUTE = 60;

    LinearLayout LLSubmit;
    LinearLayout LLLeaderboards;
    LinearLayout LLHistory;
    LinearLayout LLHistoryHeader;
    LinearLayout LLHeader;
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

    String getDrawingsDetailsURL = "https://raadz.com/getDrawingDetails.php";
    String buttonPlaceholder = "";
    String money = "";
    String balance;
    String result2;
    String result3;
    String resultDrawings;
    String httpRequest;
    String setter = "";
    String drawing_date;
    String h_zero;
    String m_zero;
    String s_zero;

    int i = 0;
    int total_seconds = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    int totalMinutes = 0;

    NavigationView navigationView = null;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_drawings);
            doEverything();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }


    public void doEverything() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        balance = preferences.getString("balance", "");
        try {
            JSONArray jArray = new JSONArray(balance);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                Log.d("money: ", jObj.getString("balance"));
//                money = jObj.getString("balance");
//                edit.remove("funds").commit();
//                edit.putString("funds", money);
//                edit.commit();
            }
        } catch (org.json.JSONException e) {
            System.out.println();
        }
        Log.d("bf balance: ", preferences.getString("money", ""));
        Log.d("bf F: ", preferences.getString("fName", ""));
        Log.d("bf L: ", preferences.getString("lName", ""));
        Log.d("1", "1");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        LLSubmit = (LinearLayout) findViewById(R.id.LLSubmit);
        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHistory = (LinearLayout) findViewById(R.id.LLHistory);
        LLHistoryHeader = (LinearLayout) findViewById(R.id.LLHistoryHeader);
        LLHeader = (LinearLayout) findViewById(R.id.LLHeader);
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

        balance = preferences.getString("funds", "");
        tvMoney.setText("$" + balance);
        tvBalance.setText("$" + preferences.getString("cash", ""));
        Log.d("new balance ", preferences.getString("cash", ""));

        Log.d("setterHome ", preferences.getString("user_details_complete", ""));

        String info = preferences.getString("cash", "");
        Log.d("money info ", info);

        Log.d("2", "2");
        //tvMoney.setText("$" + test);
        Log.d("3", "3");

//        edit.putString("funds", info);
//        edit.commit();

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
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

        result2 = preferences.getString("FR2", "");
        resultDrawings = preferences.getString("drawings_result", "");
        Log.d("result2 ", result2);
        Log.d("resultRequest ", resultDrawings);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserDrawingsActivity.this, AdsHistory.class);
                startActivity(in);
            }
        });

        DrawingsFunction(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
    }

//    public void defaultDrawings() {
//
//        result3 = resultDrawings;
//        LLDrawingsTable.removeAllViews();
//        LLDrawingsHeader.removeAllViews();
//        LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.drawings_history_header, null);
//        LLDrawingsHeader.addView(layout_h);
//        Log.d("defaultResult ", result3);
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
//            for (int i = 0; i < jArray.getJSONArray(0).length(); i++) {
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
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            LLDrawingsTable.addView(layout);
//                        }
//                    });
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
//    }

    public void TimerMethod(){

        if(minutes == 0){
            hours = hours - 1;
            minutes = 60;
        }

        if(seconds == 0){
            minutes = minutes - 1;
            seconds = 60;
        }

        seconds = seconds - 1;

        if(seconds < 10){
            s_zero = "0";
        }else if(seconds > 10){
            s_zero = "";
        }

        if(minutes < 10){
            m_zero = "0";
        }else if(minutes > 10){
            m_zero = "";
        }

        if(hours < 10){
            h_zero = "0";
        }else if(hours > 10){
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
        myTimer.cancel();
        myTimer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        myTimer.cancel();
        myTimer = null;
    }

    @Override
    protected void onStop() {
        Log.w(TAG, "Application stopped");

        super.onStop();
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
//            String test = String.valueOf(jArray.getJSONObject(2));
//            Log.d("ohTest ", test);


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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDrawingsActivity.this);
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
                        Intent in = new Intent(UserDrawingsActivity.this, IndexActivity.class);
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
        } else if (id == R.id.user_leaderboards) {
            Intent in = new Intent(this, LeaderboardsUserActivity.class);
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
                    Intent in = new Intent(UserDrawingsActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDrawingsActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDrawingsActivity.this);
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
    public void onClick(View view) {

    }

    @Override
    public void processFinish(String s) {

    }

}
