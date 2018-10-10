package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;

import static com.raadz.program.raadzandroid.R.id.tvDifferentAd1;


public class AudioActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener, MediaPlayer.OnPreparedListener {

    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    NavigationView navigationView = null;

    LinearLayout LLNoAds;
    LinearLayout LLSeekbars;
    LinearLayout LLLast;
    LinearLayout LLAllDetails;

    final MediaPlayer mp = new MediaPlayer();

    ScrollView svEverything;

    RadioGroup rgLastGroup;

    RadioButton rbLast1;
    RadioButton rbLast2;
    RadioButton rbLast3;
    RadioButton rbLast4;
    RadioButton rbLast5;

    CheckBox cbOpt;
    CheckBox cbFavorite;

    SeekBar sbQ1;
    SeekBar sbQ2;
    SeekBar sbQ3;
    SeekBar sbQ4;

    TextView tvDifferentAdBot;
    TextView tvDifferentAd1;
    TextView tvStatus;
    TextView tvMoney;
    TextView tvTitle;
    TextView tvLink;
    TextView tvCompany;
    TextView tvLast;

    TextView tvProgress1;
    TextView tvProgress2;
    TextView tvProgress3;
    TextView tvProgress4;

    TextView tvQ1;
    TextView tvQ2;
    TextView tvQ3;
    TextView tvQ4;

    Button bPause;
    Button bPlay;
    Button bNew;
    Button bSubmit;

    String submitURL = "https://raadz.com/submitRatings.php";
    String newBannerAdURL = "https://raadz.com/getNewAd.php";
    String azureLink = "https://raadzcloud.blob.core.windows.net/audios/";
    String questionsURL = "https://raadz.com/getQuestionList.php";
    String balance;
    String money;
    String buttonPlaceholder = "";
    String raadz_ad_id;
    String ad_type;
    String ad_title;
    String ad_link;
    String raadz_pub_id;
    String company_name;
    String ad_cost;
    String gender;
    String age;
    String interests;
    String questions;
    String location;
    String ad_ref;
    String quest_value;
    String quest_text;
    String result;
    String[] sArray = new String[12];
    String httpRequest;
    String s_radio_last;
    String bothTime;

    int progress_value_1 = 50;
    int progress_value_2 = 50;
    int progress_value_3 = 50;
    int progress_value_4 = 50;
    int totalTime;
    int currentTime;

    boolean opt = false;
    boolean playing = false;
    boolean feedChecked = false;
    boolean optChecked = false;
    boolean favChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

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

        LLSeekbars = (LinearLayout) findViewById(R.id.LLSeekbars);
        LLNoAds = (LinearLayout) findViewById(R.id.LLNoAds);
        LLLast = (LinearLayout) findViewById(R.id.LLLast);
        LLAllDetails = (LinearLayout) findViewById(R.id.LLAllDetails);

        cbOpt = (CheckBox) findViewById(R.id.cbOpt);
        cbFavorite = (CheckBox) findViewById(R.id.cbFavorite);

        sbQ1 = (SeekBar) findViewById(R.id.sbQ1);
        sbQ2 = (SeekBar) findViewById(R.id.sbQ2);
        sbQ3 = (SeekBar) findViewById(R.id.sbQ3);
        sbQ4 = (SeekBar) findViewById(R.id.sbQ4);

        svEverything = (ScrollView) findViewById(R.id.svEverything);

        rgLastGroup = (RadioGroup) findViewById(R.id.rgLastGroup);

        rbLast1 = (RadioButton) findViewById(R.id.rbLast1);
        rbLast2 = (RadioButton) findViewById(R.id.rbLast2);
        rbLast3 = (RadioButton) findViewById(R.id.rbLast3);
        rbLast4 = (RadioButton) findViewById(R.id.rbLast4);
        rbLast5 = (RadioButton) findViewById(R.id.rbLast5);

        bPause = (Button) findViewById(R.id.bPause);
        bPlay = (Button) findViewById(R.id.bPlay);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvDifferentAdBot = (TextView) findViewById(R.id.tvDifferentAdBot);
        tvDifferentAd1 = (TextView) findViewById(R.id.tvDifferentAd1);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvLink = (TextView) findViewById(R.id.tvLink);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        tvLast = (TextView) findViewById(R.id.tvLast);

        tvProgress1 = (TextView) findViewById(R.id.tvProgress1);
        tvProgress2 = (TextView) findViewById(R.id.tvProgress2);
        tvProgress3 = (TextView) findViewById(R.id.tvProgress3);
        tvProgress4 = (TextView) findViewById(R.id.tvProgress4);

        tvQ1 = (TextView) findViewById(R.id.tvQ1);
        tvQ2 = (TextView) findViewById(R.id.tvQ2);
        tvQ3 = (TextView) findViewById(R.id.tvQ3);
        tvQ4 = (TextView) findViewById(R.id.tvOverall);

        bNew = (Button) findViewById(R.id.bNew);
        bSubmit = (Button) findViewById(R.id.bSubmit);


        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        result = preferences.getString("result", "");
        balance = preferences.getString("funds", "");

        optInClicked();

        raadz_ad_id = preferences.getString("raadz_ad_id", "");
        ad_type = preferences.getString("ad_type", "");
        ad_title = preferences.getString("ad_title", "");
        ad_link = preferences.getString("ad_link", "");
        company_name = preferences.getString("company_name", "");
        ad_cost = preferences.getString("ad_cost", "");
        raadz_pub_id = preferences.getString("raadz_ad_pub_id", "");
        gender = preferences.getString("gender", "");
        age = preferences.getString("age", "");
        interests = preferences.getString("interests", "");
        questions = preferences.getString("questions", "");
        location = preferences.getString("location", "");
        ad_ref = preferences.getString("ad_ref", "");

        Log.d("adType ", preferences.getString("adType", ""));
        Log.d("azureLink ", azureLink);
        Log.d("Full link ", azureLink + preferences.getString("ad_ref", ""));
        tvTitle.setText(preferences.getString("ad_title", ""));
        tvLink.setText(preferences.getString("ad_link", ""));
        tvLink.setTextColor(Color.parseColor("#0000FF"));
        tvCompany.setText(preferences.getString("company_name", ""));
        Log.d("s_firstFinal ", result);
        if (result.contains("no new ads")) {
            svEverything.setVisibility(View.GONE);
            LLNoAds.setVisibility(View.VISIBLE);
            LLAllDetails.setVisibility(View.GONE);
        } else {
            svEverything.setVisibility(View.VISIBLE);
            LLNoAds.setVisibility(View.GONE);
        }

        cbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbFavorite.isChecked()) {
                    favChecked = true;
                } else
                    favChecked = false;
            }
        });

        cbOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbOpt.isChecked()) {
                    optChecked = true;
                } else
                    optChecked = false;
            }
        });

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = preferences.getString("ad_link", "");
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("http://" + url));
                startActivity(in);

            }
        });

        tvDifferentAdBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AudioActivity.this, PickAdActivity.class);
                startActivity(in);
            }
        });

        tvDifferentAd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AudioActivity.this, PickAdActivity.class);
                startActivity(in);
            }
        });

        rgLastGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbLast1:
                        s_radio_last = "1";
                        feedChecked = true;
                        Log.d("last ", s_radio_last);
                        break;
                    case R.id.rbLast2:
                        s_radio_last = "2";
                        feedChecked = true;
                        Log.d("last ", s_radio_last);
                        break;
                    case R.id.rbLast3:
                        s_radio_last = "3";
                        feedChecked = true;
                        Log.d("last ", s_radio_last);
                        break;
                    case R.id.rbLast4:
                        s_radio_last = "4";
                        feedChecked = true;
                        Log.d("last ", s_radio_last);
                        break;
                    case R.id.rbLast5:
                        s_radio_last = "5";
                        feedChecked = true;
                        Log.d("last ", s_radio_last);
                        break;
                }
            }
        });

        bNew.setOnClickListener(this);

        bSubmit.setOnClickListener(this);

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playing == false) {
                    Log.d("current ad_ref ", preferences.getString("ad_ref", ""));
                    audioPlayer(azureLink, preferences.getString("ad_ref", ""));
                    playing = true;
                }
            }
        });

        sbQ1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progress = progress + 50;
                progress_value_1 = progress;
                tvProgress1.setText(progress + " / " + sbQ1.getMax());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvProgress1.setText(progress_value_1 + " / " + sbQ1.getMax());
            }
        });
        sbQ2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value_2 = progress;
                tvProgress2.setText(progress + " / " + sbQ2.getMax());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvProgress2.setText(progress_value_2 + " / " + sbQ2.getMax());

            }
        });
        sbQ3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value_3 = progress;
                tvProgress3.setText(progress + " / " + sbQ3.getMax());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvProgress3.setText(progress_value_3 + " / " + sbQ3.getMax());

            }
        });
        sbQ4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value_4 = progress;
                tvProgress4.setText(progress + " / " + sbQ4.getMax());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvProgress4.setText(progress_value_4 + " / " + sbQ4.getMax());

            }
        });
    }


    public void audioTrack() {
        Runnable runnable = new Runnable() {
            public void run() {
                while (mp.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            totalTime = mp.getDuration() / 1000;
                            currentTime = mp.getCurrentPosition() / 1000;

                            Log.d("current ", String.valueOf(currentTime));
                            Log.d("total ", String.valueOf(totalTime));

                            bothTime = "0:" + String.valueOf(currentTime) + " | " + "0:" + String.valueOf(totalTime);

                            tvStatus.setText("Status: " + bothTime);
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


    public void audioPlayer(final String path, final String fileName) {
        try {
            mp.reset();
            mp.setDataSource(path + fileName);
            mp.prepare();
            mp.start();

            Log.d("testFile ", "lkjsdlfkj");
            Log.d("file ", fileName);

            audioTrack();


        } catch (Exception e) {
            System.out.println();
        }

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                audioTrack();
            }
        });
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
                Log.d("StatusTime ", bothTime);
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                LLSeekbars.setVisibility(View.VISIBLE);
                bPlay.setVisibility(View.GONE);
                bPause.setVisibility(View.GONE);
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                String id = preferences.getString("raadz_ad_id", "");
//                SendDataToServer(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""), raadz_pub_id, raadz_ad_id, "", "", "", "");
            }
        });

    }


    public void optInClicked() {

        cbOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbOpt.isChecked()) {
                    opt = true;
                } else
                    opt = false;
            }
        });
    }

    public void resetSeekbar() {
        sbQ1.setProgress(50);
        sbQ2.setProgress(50);
        sbQ3.setProgress(50);
        sbQ4.setProgress(50);
    }

    public void videoInfo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        //editor.clear();
        try {
            JSONArray jArray = new JSONArray(result);
            Random ran = new Random();
            int k = ran.nextInt(jArray.length()) + 1;

            Log.d("k: ", String.valueOf(k));
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                raadz_ad_id = jObj.getString("raadz_ad_id");
                editor.putString("raadz_ad_id", raadz_ad_id);
                editor.commit();
                ad_type = jObj.getString("ad_type");
                editor.commit();
                ad_title = jObj.getString("ad_title");
                ad_link = jObj.getString("ad_link");
                raadz_pub_id = jObj.getString("raadz_pub_id");
                company_name = jObj.getString("company_name");
                ad_cost = jObj.getString("ad_cost");
                gender = jObj.getString("gender");
                age = jObj.getString("age");
                location = jObj.getString("location");
                interests = jObj.getString("interests");
                questions = jObj.getString("questions");
                Log.d("i: ", String.valueOf(i));
                Log.d("Array Length: ", String.valueOf(jArray.length()));

                editor.putString("raadz_ad_id", raadz_ad_id);
                editor.putString("ad_type", ad_type);
                editor.putString("ad_title", ad_title);
                editor.putString("ad_link", ad_link);
                editor.putString("raadz_ad_pub_id", raadz_pub_id);
                editor.putString("company_name", company_name);
                editor.putString("result", result);
                editor.putString("ad_ref", ad_ref);
                editor.commit();
            }


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void SendDataToServer(final String raadzID, final String raadzToken, final String raadzPID, final String raadzAID, final String q1, final String q2, final String q3, final String q4) {
        class taskSubmit extends AsyncTask<String, Void, String> {
            //taskSubmit mtask = new taskSubmit();
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_ad_id", raadzAID));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(questionsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(questionsURL);
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
                    httpClient.getConnectionManager().shutdown();
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("qs result ", httpRequest);
                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {

                super.onPostExecute(result);
                Log.d("Submit = false: ", result);
                if (result.contains("no results")) {
                    Log.d("if 1: ", "no results");

                } else {
                    Log.d("raadzid ", raadzAID);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String Q = result;
                            Log.d("questionsJSON AM: ", Q);
                            try {
                                JSONArray jQArray = new JSONArray(Q);
                                Log.d("length: ", String.valueOf(jQArray.length()));
                                for (int i = 0; i < jQArray.length(); i++) {
                                    if (i == jQArray.length() - 1) {
                                        LLLast.setVisibility(View.VISIBLE);
                                        JSONObject jObj = jQArray.getJSONObject(i);

                                        Log.d("", "");
                                        Log.d("question_value ", jObj.getString("question"));
                                        Log.d("q1 ", jObj.getString("ans1"));
                                        Log.d("q2 ", jObj.getString("ans2"));
                                        Log.d("q3 ", jObj.getString("ans3"));
                                        Log.d("q4 ", jObj.getString("ans4"));
                                        Log.d("q5 ", jObj.getString("ans5"));
                                        Log.d("", "");
                                        tvLast.setText(jObj.getString("question"));
                                        rbLast1.setText(jObj.getString("ans1"));
                                        rbLast2.setText(jObj.getString("ans2"));
                                        rbLast3.setText(jObj.getString("ans3"));
                                        rbLast4.setText(jObj.getString("ans4"));
                                        rbLast5.setText(jObj.getString("ans5"));
                                    } else {
                                        JSONObject jObj = jQArray.getJSONObject(i);
                                        quest_value = jObj.getString("quest_value");
                                        quest_text = jObj.getString("quest_text");

                                        sArray[i] = quest_text;

                                        Log.d("", "");
                                        //make the next clas one that has another class within the original
                                        Log.d("i ", String.valueOf(i));
                                        Log.d("length ", String.valueOf(jQArray.length()));//create new discussion for the soon to be regional influence
                                        Log.d("Value: ", jObj.getString("quest_value"));
                                        Log.d("Text: ", jObj.getString("quest_text"));
                                        Log.d("", "");
                                    }

                                }
                            } catch (org.json.JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("", "");
                            for (int h = 0; h < sArray.length; h++) {
                                if (sArray[h] != null) {
                                    Log.d("Question: ", sArray[h]);
                                }
                            }
                            Log.d("", "");

                            if (sArray[7] != null && !sArray[7].isEmpty()) {
                                Log.d("is this the one: ", "lakjsd;lkfj");
                            }

                            if (sArray[0] != null) {
                                Log.d("sArray is not null", "");
                            }


                            if (sArray[0] != null) {
                                tvQ1.setText(sArray[0]);
                                tvQ1.setVisibility(View.VISIBLE);
                                tvProgress1.setText(String.valueOf(progress_value_1) + " / " + sbQ1.getMax());
                                tvProgress1.setVisibility(View.VISIBLE);
                                sbQ1.setVisibility(View.VISIBLE);
                            } else {
                                tvQ1.setText("");
                                tvQ1.setVisibility(View.GONE);
                                tvProgress1.setVisibility(View.GONE);
                                sbQ1.setVisibility(View.GONE);
                            }


                            if (sArray[1] != null) {
                                tvQ2.setText(sArray[1]);
                                tvQ2.setVisibility(View.VISIBLE);
                                tvProgress2.setText(String.valueOf(progress_value_2) + " / " + sbQ2.getMax());
                                tvProgress2.setVisibility(View.VISIBLE);
                                sbQ2.setVisibility(View.VISIBLE);
                            } else {
                                tvQ2.setText("");
                                tvQ2.setVisibility(View.GONE);
                                tvProgress2.setVisibility(View.GONE);
                                sbQ2.setVisibility(View.GONE);
                            }


                            if (sArray[2] != null) {
                                tvQ3.setText(sArray[2]);
                                tvQ3.setVisibility(View.VISIBLE);
                                tvProgress3.setText(String.valueOf(progress_value_3) + " / " + sbQ3.getMax());
                                tvProgress3.setVisibility(View.VISIBLE);
                                sbQ3.setVisibility(View.VISIBLE);
                            } else {
                                tvQ3.setText("");
                                tvQ3.setVisibility(View.GONE);
                                tvProgress3.setVisibility(View.GONE);
                                sbQ3.setVisibility(View.GONE);
                            }


                            if (sArray[3] != null) {
                                tvQ4.setText(sArray[3]);
                                tvQ4.setVisibility(View.VISIBLE);
                                tvProgress4.setText(String.valueOf(progress_value_4) + " / " + sbQ4.getMax());
                                tvProgress4.setVisibility(View.VISIBLE);
                                sbQ4.setVisibility(View.VISIBLE);
                            } else {
                                tvQ4.setText("");
                                tvQ4.setVisibility(View.GONE);
                                tvProgress4.setVisibility(View.GONE);
                                sbQ4.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        }
        taskSubmit methodSubmit = new taskSubmit();
        methodSubmit.execute(raadzID, raadzToken, raadzPID, raadzAID, q1, q2, q3, q4);
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.submit_confirmation_dialog, null);
        Button mCancel = (Button) mView.findViewById(R.id.bCancel);
        Button mEnter = (Button) mView.findViewById(R.id.bEnter);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AudioActivity.this, PickAdActivity.class);
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
                        LogoutConfirmActivity logout = new LogoutConfirmActivity();
                        logout.logoutConfirm(getApplicationContext());
                        Intent in = new Intent(AudioActivity.this, IndexActivity.class);
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
        if (id == R.id.user_home) {
            Intent in = new Intent(this, FragActivity.class);
            startActivity(in);
        } else if (id == R.id.user_rate) {
            Intent in = new Intent(this, PickAdActivity.class);
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
                    Intent in = new Intent(AudioActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AudioActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AudioActivity.this);
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

    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null) {
            mp.pause();
            if (isFinishing()) {
                mp.stop();
                mp.release();
            }
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View v) {
        if (v == bSubmit) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();

            Log.d("id ", preferences.getString("raadz_user_id", ""));
            Log.d("token ", preferences.getString("token", ""));
            Log.d("pub ", preferences.getString("raadz_ad_pub_id", ""));
            Log.d("ad ", preferences.getString("raadz_ad_id", ""));
            Log.d("q1 ", String.valueOf(progress_value_1));
            Log.d("q2 ", String.valueOf(progress_value_2));
            Log.d("q3 ", String.valueOf(progress_value_3));
            Log.d("q4 ", String.valueOf(progress_value_4));

            editor.putString("raadz_ad_pub_id", raadz_pub_id);
            editor.putString("q1", String.valueOf(progress_value_1));
            editor.putString("q2", String.valueOf(progress_value_2));
            editor.putString("q3", String.valueOf(progress_value_3));
            editor.putString("q4", String.valueOf(progress_value_4));
            editor.commit();


            buttonPlaceholder = "bSubmit";
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("raadz_pub_id", preferences.getString("raadz_ad_pub_id", ""));
            postData.put("ad_id", preferences.getString("raadz_ad_id", ""));
            postData.put("q1", String.valueOf(progress_value_1));
            postData.put("q2", String.valueOf(progress_value_2));
            postData.put("q3", String.valueOf(progress_value_3));
            postData.put("q4", String.valueOf(progress_value_4));
            postData.put("q5", s_radio_last);
//            postData.put("qlast", String.valueOf(progress_value_last));
            if (optChecked == false) {
                postData.put("optin", "0");
            }
            if (optChecked == true) {
                postData.put("optin", "1");
            }

            if (favChecked == false) {
                postData.put("save", "0");
            }
            if (favChecked == true) {
                postData.put("save", "1");
            }
            if (feedChecked == false) {
                Toast.makeText(this, "Please fill out all questions", Toast.LENGTH_SHORT).show();
            }
            if (feedChecked == true) {
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute(submitURL);
            }


//            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AudioActivity.this);
//            View mView = getLayoutInflater().inflate(R.layout.post_submit_dialog, null);
//            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
//            mBuilder.setView(mView);
//            final AlertDialog dialog = mBuilder.create();
//            dialog.show();
//            mEnter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(AudioActivity.this, PickAdActivity.class);
//                    startActivity(in);
//                }
//            });

        }
        if (v == bNew) {
            LLSeekbars.setVisibility(View.GONE);
            bPlay.setVisibility(View.VISIBLE);
            bPause.setVisibility(View.VISIBLE);

            playing = false;

            mp.seekTo(0);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            buttonPlaceholder = "bNew";
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("ad_type", preferences.getString("ad_type", ""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("https://raadz.com/getNewAd.php");
            resetSeekbar();
        }
    }

    @Override
    public void processFinish(String s) {
        if (buttonPlaceholder.equals("bNew")) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            String adID = preferences.getString("raadz_ad_id", "");
            Log.d("s_final ", s);
            Log.d("something ", "kjsdf");
            if (s.contains("no new ads")) {
                svEverything.setVisibility(View.GONE);
                Toast.makeText(this, "No new ads", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray jArray = new JSONArray(s);
                    Random rand = new Random();
                    if (jArray.length() > 1) {
                        //Log.d("s ", s);
                        int k = rand.nextInt(jArray.length()) + 0;
                        JSONObject jObj = jArray.getJSONObject(k);

                        if (jObj.getString("raadz_ad_id").equals(adID)) {
                            Log.d("Original ", adID);
                            Log.d("New ", jObj.getString("raadz_ad_id"));
                            processFinish(s);
                        } else {
                            Log.d("Original ", adID);
                            Log.d("New ", jObj.getString("raadz_ad_id"));
                            raadz_ad_id = jObj.getString("raadz_ad_id");
                            Log.d("ad_id ", raadz_ad_id);
                            ad_type = jObj.getString("ad_type");
                            Log.d("ad_type ", ad_type);
                            ad_title = jObj.getString("ad_title");
                            Log.d("ad_title ", ad_title);
                            ad_link = jObj.getString("ad_link");
                            Log.d("ad_link ", ad_link);
                            raadz_pub_id = jObj.getString("raadz_pub_id");
                            Log.d("raadz_pub_id ", raadz_pub_id);
                            company_name = jObj.getString("company_name");
                            Log.d("company_name ", company_name);
                            ad_ref = jObj.getString("ad_ref");
                            Log.d("ad_ref ", ad_ref);
                            Log.d("--- ", "---");
                            Log.d("jArray Length ", String.valueOf(jArray.length()));

                            editor.putString("raadz_ad_id", raadz_ad_id);
                            editor.putString("ad_type", ad_type);
                            editor.putString("ad_title", ad_title);
                            editor.putString("ad_link", ad_link);
                            editor.putString("raadz_ad_pub_id", raadz_pub_id);
                            editor.putString("company_name", company_name);
                            editor.putString("ad_ref", ad_ref);
                            editor.commit();
                        }

                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }

            }
            audioPlayer(azureLink, ad_ref);
        }

        if (buttonPlaceholder.equals("bSubmit")) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (s.equals("ratings submitted")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                onClick(bNew);
            } else if (s.equals("no new ads")) {
                LLNoAds.setVisibility(View.VISIBLE);
                svEverything.setVisibility(View.GONE);
            } else if (s.equals("invalid user id")) {
                Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Submit Result ", s);

                bNew.setVisibility(View.VISIBLE);
                //LLInfo.setVisibility(View.VISIBLE);
                tvTitle.setText(ad_title);
                tvLink.setText(ad_link);
                tvCompany.setText(company_name);
            }
        }
    }

    public void ResendAdValues(final String uid, final String adtype) {
        class taskSubmit extends AsyncTask<String, Void, String> {
            //taskSubmit mtask = new taskSubmit();
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", uid));
                nameValuePairs.add(new BasicNameValuePair("ad_type", adtype));
                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("https://raadz.com/getNewAd.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost("https://raadz.com/getNewAd.php");
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
                    httpClient.getConnectionManager().shutdown();
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("result http ", httpRequest);
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("s_final ", s);
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                if (s.contains("no new ads")) {
                    svEverything.setVisibility(View.GONE);
                    Toast.makeText(AudioActivity.this, "no new ads", Toast.LENGTH_SHORT).show();
                    editor.putString("httpRequest", s);
                    editor.commit();
                } else if (s.equals("invalid user id")) {
                    Toast.makeText(AudioActivity.this, "invalid user id", Toast.LENGTH_SHORT).show();
                }
                try {
                    Log.d("s ", s);
                    JSONArray jArray = new JSONArray(s);

                    JSONObject jObj = jArray.getJSONObject(0);
                    raadz_ad_id = jObj.getString("raadz_ad_id");
                    Log.d("ad_id ", raadz_ad_id);
                    ad_type = jObj.getString("ad_type");
                    Log.d("ad_type ", ad_type);
                    ad_title = jObj.getString("ad_title");
                    Log.d("ad_title ", ad_title);
                    ad_link = jObj.getString("ad_link");
                    Log.d("ad_link ", ad_link);
                    raadz_pub_id = jObj.getString("raadz_pub_id");
                    Log.d("raadz_pub_id ", raadz_pub_id);
                    company_name = jObj.getString("company_name");
                    Log.d("company_name ", company_name);
                    ad_ref = jObj.getString("ad_ref");
                    Log.d("ad_ref ", ad_ref);


                    editor.putString("raadz_ad_id", raadz_ad_id);
                    editor.putString("ad_type", ad_type);
                    editor.putString("ad_title", ad_title);
                    editor.putString("ad_link", ad_link);
                    editor.putString("raadz_ad_pub_id", raadz_pub_id);
                    editor.putString("company_name", company_name);
                    editor.putString("result", s);
                    editor.putString("ad_ref", ad_ref);
                    editor.commit();

                } catch (org.json.JSONException e) {
                    System.out.println();
                }

            }
        }
        Intent in = new Intent(AudioActivity.this, YouTubeActivity.class);
        startActivity(in);
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}