package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
import android.view.MenuItem;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.colorSecondary;
import static com.raadz.program.raadzandroid.R.id.LLDrawingsHeader;
import static com.raadz.program.raadzandroid.R.id.LLDrawingsTable;
import static com.raadz.program.raadzandroid.R.id.LLHeader;
import static com.raadz.program.raadzandroid.R.id.LLLeaderboards;
import static com.raadz.program.raadzandroid.R.id.nc_nav_leaderboards;
import static com.raadz.program.raadzandroid.R.id.tvDrawingsEntriesValue;
import static com.raadz.program.raadzandroid.R.id.tvDrawingsEntriesValue2;
import static com.raadz.program.raadzandroid.R.id.tvDrawingsNextValue;
import static com.raadz.program.raadzandroid.R.id.tvDrawingsPrizeValue;

public class FragNotConfirmedActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {
    final String TAG = this.getClass().getName();

    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    Timer myTimer;

    final HashMap<String, String> drawingResults = new HashMap<String, String>();

    final MediaPlayer mp = new MediaPlayer();

    final int MINUTES_IN_AN_HOUR = 60;
    final int SECONDS_IN_A_MINUTE = 60;

    Button bMoreLeaderboards;

    LinearLayout LLLeaderboards;
    LinearLayout LLHeader;
    LinearLayout LLDrawingsHeader;
    LinearLayout LLDrawingsTable;

    TextView tvWelcome;
    TextView tvID;
    TextView tvName;
    TextView tvDrawingsPrizeValue;
    TextView tvDrawingsEntriesValue;
    TextView tvDrawingsEntriesValue2;
    TextView tvDrawingsNextValue;

    String getInfoURL = "https://raadz.com/getUserInfo.php";
    String getDataURL = "https://raadz.com/getUserData.php";
    String getMobileLoginURL = "https://raadz.com/mobilelogin.php";
    String getResendURL = "https://raadz.com/resendSignupEmail.php";
    String getDrawingsDetailsURL = "https://raadz.com/getDrawingDetails.php";
    String colorPrimary = "00cdFF";
    String colorSecondary = "#FFFFFF";
    String buttonPlaceholder = "";
    String color;
    String RID;
    String RT;
    String money;
    String balance;
    String httpRequest;
    String h_zero;
    String m_zero;
    String s_zero;
    String check = "true";
    String where = "bad";
    String result3;
    String drawing_date;

    int counter = 0;
    int currentTime;
    int totalTime;
    int i = 0;
    int total_seconds = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    int totalMinutes = 0;
    int colorChanger = 0;

    long value;
    long oTime;

    boolean bToggle = false;

    NavigationView navigationView = null;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_not_confirmed);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DrawingsFunction(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));

        String email = preferences.getString("NCEMAIL", "");
        String pass = preferences.getString("NCPASSWORD", "");
        check = "true";
        HashMap postData = new HashMap();
        PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) FragNotConfirmedActivity.this, postData);
        postData.put("email", email);
        postData.put("password", pass);
        task.execute(getMobileLoginURL);

        doEverything();

    }

    public void doEverything() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        RID = preferences.getString("raadz_user_id", "");
        RT = preferences.getString("token", "");

        if (preferences.getString("raadz_fb_user_id", "").length() > 3) {
            RID = preferences.getString("raadz_fb_user_id", "");
            RT = preferences.getString("token", "");
            edit.putString("raadz_pub_id", RID);
            edit.commit();
        }
        if (preferences.getString("raadz_pub_id", "").length() > 3) {
            RID = preferences.getString("raadz_pub_id", "");
            RT = preferences.getString("token", "");
        }


        Log.d("RID ", RID);
        Log.d("RT ", RT);

        Log.d("bf balance: ", preferences.getString("money", ""));
        Log.d("bf balance: ", preferences.getString("money", ""));
        Log.d("1", "1");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        bMoreLeaderboards = (Button) findViewById(R.id.bMoreLeaderboards);

        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHeader = (LinearLayout) findViewById(R.id.LLHeader);
        LLDrawingsHeader = (LinearLayout) findViewById(R.id.LLDrawingsHeader);
        LLDrawingsTable = (LinearLayout) findViewById(R.id.LLDrawingsTable);

        tvDrawingsPrizeValue = (TextView) findViewById(R.id.tvDrawingsPrizeValue);
        tvDrawingsEntriesValue = (TextView) findViewById(R.id.tvDrawingsEntriesValue);
        tvDrawingsEntriesValue2 = (TextView) findViewById(R.id.tvDrawingsEntriesValue2);
        tvDrawingsNextValue = (TextView) findViewById(R.id.tvDrawingsNextValue);


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

        Log.d("4", "4");
        Log.d("reload: ", "");

        Log.d("counter ", String.valueOf(counter));

        Log.d("Account UID ", RID);

        defaultLeaderboards();

        bMoreLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragNotConfirmedActivity.this, NCLeaderboardsActivity.class);
                startActivity(in);
            }
        });

    }

//    public void countdownTimer() {
//        new CountDownTimer(15000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                //Log.d("millis ", String.valueOf(millisUntilFinished));
//                value = millisUntilFinished / 1000;
//                //Log.d("Seconds remaining: ", String.valueOf(value));
//                tvTimer.setText(String.valueOf(value));
//            }
//
//            public void onFinish() {
//                taskCall(RID, RT, "https://raadz.com/mobilelogin.php");
//                countdownTimer();
//            }
//
//        }.start();
//
//    }

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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragNotConfirmedActivity.this);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == nc_nav_leaderboards) {
            Intent in = new Intent(this, NCLeaderboardsActivity.class);
            startActivity(in);
        } else if (id == R.id.nc_nav_about) {
            Intent in = new Intent(this, NCAboutUsActivity.class);
            startActivity(in);
        } else if (id == R.id.nc_nav_contact) {
            Intent in = new Intent(this, NCContactUsActivity.class);
            startActivity(in);
        } else if (id == R.id.nc_nav_logout) {
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
                    Intent in = new Intent(FragNotConfirmedActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragNotConfirmedActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragNotConfirmedActivity.this);
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

    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
        TextView tvName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvName = (TextView) findViewById(R.id.tvName);

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("token", params[1]));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getDataURL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpPost request = new HttpPost(getDataURL);
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

            return httpRequest;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("user not found")) {
                counter = 0;
            } else
                counter = 50;

        }
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
                            drawing_date = jObj.getString("drawing_date");
                            tvDrawingsNextValue.setText(drawing_date);
                            if (jObj.getString("reward").equals("1")) {
                                Log.d("reward_1 ", jObj.getString("reward"));
                                Log.d("1_t ", drawingResults.get("1_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("1_t"));
                            }
                            if (jObj.getString("reward").equals("2")) {
                                Log.d("reward_2 ", jObj.getString("reward"));
                                Log.d("2_t ", drawingResults.get("2_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("2_t"));
                            }
                            if (jObj.getString("reward").equals("3")) {
                                Log.d("reward_3 ", jObj.getString("reward"));
                                Log.d("3_t ", drawingResults.get("3_t"));
                                tvDrawingsPrizeValue.setText(drawingResults.get("3_t"));
                            }
                        } else {

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
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }

    @Override
    public void processFinish(String s) {

    }

}
