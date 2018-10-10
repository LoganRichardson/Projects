package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.raadz.program.raadzandroid.R.id.LLAllDetails;
import static com.raadz.program.raadzandroid.R.id.newYTPlayer;

public class YouTubeActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener, YouTubeThumbnailView.OnInitializedListener {

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    BufferedReader bufferedReader;
    BufferedReader bufferedReaderSubmit;
    BufferedReader bufferedReader2;
    OutputStream outputStream;
    HashMap<String, String> hashMap = new HashMap<>();
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    java.lang.StringBuffer stringBufferSubmit = new java.lang.StringBuffer();
    NavigationView navigationView = null;

    LinearLayout LLEverything;
    LinearLayout LLNoAds;
    LinearLayout LLMain;
    LinearLayout LLLast;
    LinearLayout LLAllDetails;

    Button bPlay;
    Button bSubmit;
    Button bNewVideo;
    Button bPlayAgain;
    Button bWPlay;
    Button bPause;
    Button bOtherNewVideo;
    Button bPostSubmitNewVideo;

    CheckBox cbOpt;
    CheckBox cbFavorite;

    SeekBar sbQ1;
    SeekBar sbQ2;
    SeekBar sbQ3;
    SeekBar sbQ4;

    RadioGroup rgLastGroup;

    RadioButton rbLast1;
    RadioButton rbLast2;
    RadioButton rbLast3;
    RadioButton rbLast4;
    RadioButton rbLast5;

    TextView tvNothing;
    TextView tvLast;

    TextView tvProgress1;
    TextView tvProgress2;
    TextView tvProgress3;
    TextView tvProgress4;

    TextView tvDifferentAd;
    TextView tvDifferentAd1;
    TextView tvQ1;
    TextView tvQ2;
    TextView tvQ3;
    TextView tvQ4;

    TextView tvAdTitle;
    TextView tvLink;
    TextView tvNoVideos;
    TextView tvCompany;

    String result;
    String raadz_ad_id;
    String ad_type;
    String yt_id;
    String ad_title;
    String ad_link;
    String raadz_pub_id;
    String company_name;
    String current_views;
    String total_views;
    String ad_cost;
    String gender;
    String age;
    String location;
    String interests;
    String questions;
    String buttonPlaceholder;

    String quest_value;
    String quest_text;
    String[] sArray = new String[12];
    String[] oArray = new String[6];

    //Alt Strings
    String submitURL = "https://raadz.com/submitRatings.php";
    String questionsURL = "https://raadz.com/getQuestionList.php";
    String completedAdsURL = "https://raadz.com/getUserCompletedAds.php";
    String newAdURL = "https://raadz.com/getNewAd.php";
    String httpRequest;
    String httpRequestSubmit;
    String ytVideoID;
    String indexArray;
    String vidPlaceholder;
    String s_radio_last;
    String question_last;
    String[] question_last_array = new String[5];

    int progress_value_1 = 50;
    int progress_value_2 = 50;
    int progress_value_3 = 50;
    int progress_value_4 = 50;

    //Alt integers
    int indexArray2;

    boolean newVid = false;
    boolean gSubmit = false;
    boolean feedChecked = false;
    boolean optChecked = false;
    boolean favChecked = false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu4);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mYouTubePlayerView = (YouTubePlayerView) findViewById(newYTPlayer);
        player = null;

        LLEverything = (LinearLayout) findViewById(R.id.LLEverything);
        LLNoAds = (LinearLayout) findViewById(R.id.LLNoAds);
        LLMain = (LinearLayout) findViewById(R.id.LLMain);
        LLLast = (LinearLayout) findViewById(R.id.LLLast);
        LLAllDetails = (LinearLayout) findViewById(R.id.LLAllDetails);

        cbOpt = (CheckBox) findViewById(R.id.cbOpt);
        cbFavorite = (CheckBox) findViewById(R.id.cbFavorite);

        rgLastGroup = (RadioGroup) findViewById(R.id.rgLastGroup);

        rbLast1 = (RadioButton) findViewById(R.id.rbLast1);
        rbLast2 = (RadioButton) findViewById(R.id.rbLast2);
        rbLast3 = (RadioButton) findViewById(R.id.rbLast3);
        rbLast4 = (RadioButton) findViewById(R.id.rbLast4);
        rbLast5 = (RadioButton) findViewById(R.id.rbLast5);

        bPlay = (Button) findViewById(R.id.bPlay);
        bSubmit = (Button) findViewById(R.id.bSubmit);
        bPlayAgain = (Button) findViewById(R.id.bPlayAgain);
        bWPlay = (Button) findViewById(R.id.bWPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bOtherNewVideo = (Button) findViewById(R.id.bOtherNewVideo);
        bPostSubmitNewVideo = (Button) findViewById(R.id.bPostSubmitNewVideo);

        sbQ1 = (SeekBar) findViewById(R.id.sbQ1);
        sbQ2 = (SeekBar) findViewById(R.id.sbQ2);
        sbQ3 = (SeekBar) findViewById(R.id.sbQ3);
        sbQ4 = (SeekBar) findViewById(R.id.sbQ4);

        tvProgress1 = (TextView) findViewById(R.id.tvProgress1);
        tvProgress2 = (TextView) findViewById(R.id.tvProgress2);
        tvProgress3 = (TextView) findViewById(R.id.tvProgress3);
        tvProgress4 = (TextView) findViewById(R.id.tvProgress4);

        tvQ1 = (TextView) findViewById(R.id.tvQ1);
        tvQ2 = (TextView) findViewById(R.id.tvQ2);
        tvQ3 = (TextView) findViewById(R.id.tvQ3);
        tvQ4 = (TextView) findViewById(R.id.tvOverall);

        tvDifferentAd = (TextView) findViewById(R.id.tvDiffererntAd);
        tvDifferentAd1 = (TextView) findViewById(R.id.tvDifferentAd1);
        tvAdTitle = (TextView) findViewById(R.id.tvTitle);
        tvLink = (TextView) findViewById(R.id.tvLink);
        tvNoVideos = (TextView) findViewById(R.id.tvNoVideos);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        tvNothing = (TextView) findViewById(R.id.tvNothing);
        tvLast = (TextView) findViewById(R.id.tvLast);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        gSubmit = true;
        result = preferences.getString("httpRequest", "");
        indexArray = preferences.getString("indexArray", "");
        indexArray2 = preferences.getInt("indexArray2", 0);
        Log.d("httpRequest: ", result);
        Log.d("result: ", result);
        Log.d("indexArray: ", String.valueOf(indexArray));
        final String raadzUID = preferences.getString("raadz_user_id", "");
        final String raadzToken = preferences.getString("token", "");
        if (result.contains("no new ads")) {

            mYouTubePlayerView.setVisibility(View.GONE);
            LLEverything.setVisibility(View.GONE);
            LLNoAds.setVisibility(View.VISIBLE);
            LLAllDetails.setVisibility(View.GONE);

            tvDifferentAd1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
                    startActivity(in);
                }
            });
        } else {
            mYouTubePlayerView.setVisibility(View.VISIBLE);
            LLEverything.setVisibility(View.VISIBLE);
            LLNoAds.setVisibility(View.GONE);

            raadz_ad_id = preferences.getString("raadz_ad_id", "");
            ad_type = preferences.getString("ad_type", "");
            yt_id = preferences.getString("yt_id", "");
            ad_title = preferences.getString("ad_title", "");
            ad_link = preferences.getString("ad_link", "");
            raadz_pub_id = preferences.getString("raadz_ad_pub_id", "");
            company_name = preferences.getString("company_name", "");

            //Log.d(";ljsdfl;kjaskfjsd", ad_title);
            tvAdTitle.setText(ad_title);
            tvLink.setText(ad_link);
            tvLink.setTextColor(Color.parseColor("#0000FF"));
            tvCompany.setText(company_name);
            tvDifferentAd.setVisibility(View.VISIBLE);
            Log.d("raadz_ad_id", preferences.getString("raadz_ad_id", ""));

            Log.d("adtype", ad_type);
            Log.d("ytjgjhj", yt_id);
            Log.d("t", ad_title);
            Log.d("l", ad_link);
            Log.d("c", company_name);

            vidPlaceholder = raadz_ad_id;

            tvDifferentAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
                    startActivity(in);
                }
            });

            tvDifferentAd1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
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


            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                    Log.d("test", "inside initizedlistener");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    final String youtubeURL = preferences.getString("yt_id", "");
                    player = youTubePlayer;
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

                            player.setFullscreen(false);
                            SendDataToServer(raadz_ad_id);
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


            bPostSubmitNewVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    videoInfo();
                    try {
                        Random rand = new Random();

                        JSONArray jArray = new JSONArray(result);
                        int t = rand.nextInt(jArray.length()) + 1;
                        JSONObject jObj = jArray.getJSONObject(t);
                        raadz_ad_id = jObj.getString("raadz_ad_id");
                        ad_type = jObj.getString("ad_type");
                        yt_id = jObj.getString("ad_ref");
                        ad_title = jObj.getString("ad_title");
                        ad_link = jObj.getString("ad_link");
                        raadz_pub_id = jObj.getString("raadz_pub_id");
                        company_name = jObj.getString("company_name");
                        current_views = jObj.getString("current_views");
                        total_views = jObj.getString("total_views");
                        ad_cost = jObj.getString("ad_cost");
                        gender = jObj.getString("gender");
                        age = jObj.getString("age");
                        location = jObj.getString("location");
                        interests = jObj.getString("interests");
                        questions = jObj.getString("questions");

                    } catch (org.json.JSONException e) {
                        System.out.println();
                    }
                    Log.d("title: ", ad_title);
                    Log.d("ad_link: ", ad_link);
                    Log.d("ad_type: ", ad_type);
                    Log.d("yt_id: ", yt_id);

                    tvAdTitle.setText(ad_title);
                    tvLink.setText(ad_link);
                    tvCompany.setText(company_name);

                    tvQ1.setVisibility(View.GONE);
                    tvQ2.setVisibility(View.GONE);
                    tvQ3.setVisibility(View.GONE);
                    tvQ4.setVisibility(View.GONE);

                    sbQ1.setVisibility(View.GONE);
                    sbQ2.setVisibility(View.GONE);
                    sbQ3.setVisibility(View.GONE);
                    sbQ4.setVisibility(View.GONE);

                    tvProgress1.setVisibility(View.GONE);
                    tvProgress2.setVisibility(View.GONE);
                    tvProgress3.setVisibility(View.GONE);
                    tvProgress4.setVisibility(View.GONE);

                    LLLast.setVisibility(View.GONE);

                    mYouTubePlayerView.setVisibility(View.VISIBLE);

                    bWPlay.setVisibility(View.VISIBLE);
                    bPause.setVisibility(View.VISIBLE);
                    bSubmit.setVisibility(View.GONE);
                    bPlayAgain.setVisibility(View.GONE);
                    bPostSubmitNewVideo.setVisibility(View.GONE);
                    playVideo(yt_id);
                }
            });

            bSubmit.setOnClickListener(this);

            bWPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newVid = false;
                    mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                    ytVideoID = "B7bqAsxee4I";
                    playVideo(ytVideoID);
                }
            });

            bOtherNewVideo.setOnClickListener(this);

            bPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newVid = false;
                    mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                    ytVideoID = "B7bqAsxee4I";
                    //Log.d("id: ", yt_id);
                    playVideo(ytVideoID);
                }
            });

            bPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            bPlayAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    tvQ1.setVisibility(View.GONE);
//                    tvQ2.setVisibility(View.GONE);
//                    tvQ3.setVisibility(View.GONE);
//                    tvQ4.setVisibility(View.GONE);
//                    tvQ5.setVisibility(View.GONE);
//                    tvQ6.setVisibility(View.GONE);
//                    tvQ7.setVisibility(View.GONE);
//                    tvQ8.setVisibility(View.GONE);
//                    tvQ9.setVisibility(View.GONE);
//                    tvQ10.setVisibility(View.GONE);
//                    tvQ11.setVisibility(View.GONE);
//                    tvQ12.setVisibility(View.GONE);
//
//                    sbQ1.setVisibility(View.GONE);
//                    sbQ2.setVisibility(View.GONE);
//                    sbQ3.setVisibility(View.GONE);
//                    sbQ4.setVisibility(View.GONE);
//                    sbQ5.setVisibility(View.GONE);
//                    sbQ6.setVisibility(View.GONE);
//                    sbQ7.setVisibility(View.GONE);
//                    sbQ8.setVisibility(View.GONE);
//                    sbQ9.setVisibility(View.GONE);
//                    sbQ10.setVisibility(View.GONE);
//                    sbQ11.setVisibility(View.GONE);
//                    sbQ12.setVisibility(View.GONE);
//
//                    tvProgress1.setVisibility(View.GONE);
//                    tvProgress2.setVisibility(View.GONE);
//                    tvProgress3.setVisibility(View.GONE);
//                    tvProgress4.setVisibility(View.GONE);
//                    tvProgress5.setVisibility(View.GONE);
//                    tvProgress6.setVisibility(View.GONE);
//                    tvProgress7.setVisibility(View.GONE);
//                    tvProgress8.setVisibility(View.GONE);
//                    tvProgress9.setVisibility(View.GONE);
//                    tvProgress10.setVisibility(View.GONE);
//                    tvProgress11.setVisibility(View.GONE);
//                    tvProgress12.setVisibility(View.GONE);

                    mYouTubePlayerView.setVisibility(View.VISIBLE);

                    bWPlay.setVisibility(View.VISIBLE);
                    bPause.setVisibility(View.VISIBLE);
                    tvAdTitle.setVisibility(View.VISIBLE);
                    tvLink.setVisibility(View.VISIBLE);
                    tvCompany.setVisibility(View.VISIBLE);
                    bOtherNewVideo.setVisibility(View.VISIBLE);
                    playVideo(yt_id);
                }
            });

            tvLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = ad_link;
                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setData(Uri.parse("http://" + url));
                    startActivity(in);

                }
            });

            cbOpt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(cbOpt.isChecked()){
                        optChecked = true;
                        Log.d("checked ", String.valueOf(optChecked));
                    }else {
                        optChecked = false;
                        Log.d("unchecked ", String.valueOf(optChecked));
                    }
                }
            });

            cbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(cbFavorite.isChecked()){
                        favChecked = true;
                        Log.d("checked ", String.valueOf(favChecked));
                    }else {
                        favChecked = false;
                        Log.d("unchecked ", String.valueOf(favChecked));
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

    }

    public void noNewVideos() {

    }

    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);


            bPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.pause();
                }
            });
            bWPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.play();

                    player.setShowFullscreenButton(false);
                }
            });


            //player.loadVideo(url);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Log ", "Landscape");
            player.setFullscreen(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d("Log ", "Portrait");
            player.setFullscreen(true);
        }
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
                yt_id = jObj.getString("ad_ref");
                editor.putString("ad_ref", yt_id);
                editor.commit();
                ad_title = jObj.getString("ad_title");
                ad_link = jObj.getString("ad_link");
                raadz_pub_id = jObj.getString("raadz_pub_id");
                company_name = jObj.getString("company_name");
                current_views = jObj.getString("current_views");
                total_views = jObj.getString("total_views");
                ad_cost = jObj.getString("ad_cost");
                gender = jObj.getString("gender");
                age = jObj.getString("age");
                location = jObj.getString("location");
                interests = jObj.getString("interests");
                questions = jObj.getString("questions");
                Log.d("i: ", String.valueOf(i));
                Log.d("Array Length: ", String.valueOf(jArray.length()));
            }


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }


    public void resetSeekbar() {
        sbQ1.setProgress(50);
        sbQ2.setProgress(50);
        sbQ3.setProgress(50);
        sbQ4.setProgress(50);
    }

    public void SendDataToServer(final String ad_id) {
        class taskSubmit extends AsyncTask<String, Void, String> {
            //taskSubmit mtask = new taskSubmit();
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.d("async value ", ad_id);
                nameValuePairs.add(new BasicNameValuePair("raadz_ad_id", ad_id));
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

                Log.d("result http ", httpRequest);
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (result.equals("no results")) {
                    Log.d("Result 1 ", result);
                } else {

                    String Q = result;

                    //QuestionsFunction(id);
                    Log.d("questionsJSON AM: ", Q);
                    try {
                        JSONArray jQArray = new JSONArray(Q);
                        Log.d("length: ", String.valueOf(jQArray.length()));
                        for (int i = 0; i < jQArray.length(); i++) {
                            if(i == jQArray.length() - 1){
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
                            }else {
                                JSONObject jObj = jQArray.getJSONObject(i);
                                quest_value = jObj.getString("quest_value");
                                quest_text = jObj.getString("quest_text");

                                sArray[i] = quest_text;

                                Log.d("", "");
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


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


                            if (sArray[4] != null) {
                                LLLast.setVisibility(View.VISIBLE);
                                Log.d("Array[4] ", String.valueOf(sArray[4]));
                            } else {
                                Log.d("Array[4] ", String.valueOf(sArray[4]));
                            }
                        }
                    });


                    mYouTubePlayerView.setVisibility(View.GONE);
                    bPlayAgain.setVisibility(View.VISIBLE);
                    bPlay.setVisibility(View.GONE);
                    bSubmit.setVisibility(View.VISIBLE);
                    bWPlay.setVisibility(View.GONE);
                    bPause.setVisibility(View.GONE);

                    tvAdTitle.setVisibility(View.VISIBLE);
                    tvLink.setVisibility(View.VISIBLE);
                    cbOpt.setVisibility(View.VISIBLE);
                    cbFavorite.setVisibility(View.VISIBLE);

                }

            }
        }


        taskSubmit methodSubmit = new taskSubmit();
        methodSubmit.execute(ad_id);

    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }


    @Override
    public void onClick(View v) {
        if (v == bOtherNewVideo) {
            buttonPlaceholder = "bOtherNewVideo";
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("ad_type", preferences.getString("ad_type", ""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("https://raadz.com/getNewAd.php");


        }

        if (v == bSubmit) {
            buttonPlaceholder = "bSubmit";
            gSubmit = true;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            String raadzADID = preferences.getString("raadz_ad_id", "");
            String raadzPID = preferences.getString("raadz_ad_pub_id", "");

            Log.d("Q1 value: ", String.valueOf(progress_value_1));
            Log.d("Q2 value: ", String.valueOf(progress_value_2));
            Log.d("Q3 value: ", String.valueOf(progress_value_3));
            Log.d("Q4 value: ", String.valueOf(progress_value_4));
            Log.d("raadz_user_id: ", preferences.getString("raadz_user_id", ""));
            Log.d("token: ", preferences.getString("token", ""));
            Log.d("raadz_pub_id: ", raadzPID);
            Log.d("ad_id: ", raadzADID);

            editor.putString("raadz_ad_pub_id", raadz_pub_id);
            editor.putString("q1", String.valueOf(progress_value_1));
            editor.putString("q2", String.valueOf(progress_value_2));
            editor.putString("q3", String.valueOf(progress_value_3));
            editor.putString("q4", String.valueOf(progress_value_4));
            editor.putString("q5", s_radio_last);
            editor.commit();


//            bPlayAgain.setVisibility(View.VISIBLE);
//
//            bPostSubmitNewVideo.setVisibility(View.VISIBLE);
//            bSubmit.setVisibility(View.INVISIBLE);
//            bOtherNewVideo.setVisibility(View.INVISIBLE);
//            tvAdTitle.setVisibility(View.INVISIBLE);
//            tvLink.setVisibility(View.INVISIBLE);
//
//            LLLast.setVisibility(View.GONE);
//
//            tvProgress1.setVisibility(View.GONE);
//            tvProgress2.setVisibility(View.GONE);
//            tvProgress3.setVisibility(View.GONE);
//            tvProgress4.setVisibility(View.GONE);
//
//            sbQ1.setVisibility(View.GONE);
//            sbQ2.setVisibility(View.GONE);
//            sbQ3.setVisibility(View.GONE);
//            sbQ4.setVisibility(View.GONE);
//
//            tvQ1.setVisibility(View.GONE);
//            tvQ2.setVisibility(View.GONE);
//            tvQ3.setVisibility(View.GONE);
//            tvQ4.setVisibility(View.GONE);
//
//            //onCreate(new Bundle());
//            //onCreate(new Bundle());
//
//            mYouTubePlayerView.setVisibility(View.GONE);
//            LLEverything.setVisibility(View.GONE);
//            LLNoAds.setVisibility(View.GONE);


            Log.d("id ", preferences.getString("raadz_user_id", ""));
            Log.d("token ", preferences.getString("token", ""));
            Log.d("pub ", preferences.getString("raadz_ad_pub_id", ""));
            Log.d("ad ", preferences.getString("raadz_ad_id", ""));
            Log.d("q1 ", String.valueOf(progress_value_1));
            Log.d("q2 ", String.valueOf(progress_value_2));
            Log.d("q3 ", String.valueOf(progress_value_3));
            Log.d("q4 ", String.valueOf(progress_value_4));


            HashMap postData2 = new HashMap();
            postData2.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData2.put("token", preferences.getString("token", ""));
            postData2.put("raadz_pub_id", preferences.getString("raadz_ad_pub_id", ""));
            postData2.put("ad_id", preferences.getString("raadz_ad_id", ""));
            postData2.put("q1", String.valueOf(progress_value_1));
            postData2.put("q2", String.valueOf(progress_value_2));
            postData2.put("q3", String.valueOf(progress_value_3));
            postData2.put("q4", String.valueOf(progress_value_4));
            postData2.put("q5", s_radio_last);
//            postData2.put("qlast", String.valueOf(progress_value_last));
            if(optChecked == false){
                postData2.put("optin", "0");
            }
            if(optChecked == true){
                postData2.put("optin", "1");
            }

            if(favChecked == false){
                postData2.put("save", "0");
            }
            if(favChecked == true){
                postData2.put("save", "1");
            }
            if (feedChecked == false) {
                Toast.makeText(this, "Please fill out all questions", Toast.LENGTH_SHORT).show();
            }if(feedChecked == true){
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData2);
                task.execute(submitURL);
            }

//            AlertDialog.Builder mBuilder = new AlertDialog.Builder(YouTubeActivity.this);
//            View mView = getLayoutInflater().inflate(R.layout.post_submit_dialog, null);
//            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
//            mBuilder.setView(mView);
//            final AlertDialog dialog = mBuilder.create();
//            dialog.show();
//            mEnter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
//                    startActivity(in);
//                }
//            });
        }


    }

    @Override
    public void processFinish(String s) {
        if (buttonPlaceholder.equals("bOtherNewVideo")) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            String adID = preferences.getString("raadz_ad_id", "");
            if (s.contains("no new ads")) {
                Toast.makeText(this, "No new video ads.  Try again later.", Toast.LENGTH_SHORT).show();
                mYouTubePlayerView.setVisibility(View.GONE);
                LLEverything.setVisibility(View.GONE);
                LLNoAds.setVisibility(View.VISIBLE);
                LLAllDetails.setVisibility(View.GONE);
            } else {
                try {
                    JSONArray jArray = new JSONArray(s);
                    Random rand = new Random();
                    if (jArray.length() > 1) {
                        Log.d("s ", s);
                        int k = rand.nextInt(jArray.length()) + 0;
                        JSONObject jObj = jArray.getJSONObject(k);
                        if (jObj.getString("raadz_ad_id").equals(adID)) {
                            Log.d("Original ", adID);
                            Log.d("New ", jObj.getString("raadz_ad_id"));
                            processFinish(s);
                        } else {
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
                            yt_id = jObj.getString("ad_ref");
                            Log.d("ad_ref ", yt_id);
                            Log.d("--- ", "---");
                            Log.d("jArray Length ", String.valueOf(jArray.length()));

                            editor.putString("raadz_ad_id", raadz_ad_id);
                            editor.putString("ad_type", ad_type);
                            editor.putString("yt_id", yt_id);
                            editor.putString("ad_title", ad_title);
                            editor.putString("ad_link", ad_link);
                            editor.putString("raadz_ad_pub_id", raadz_pub_id);
                            editor.putString("company_name", company_name);
                            editor.commit();
                            resetSeekbar();
                        }
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }
                Log.d("title: ", ad_title);
                Log.d("ad_link: ", ad_link);
                Log.d("ad_type: ", ad_type);
                Log.d("yt_id: ", yt_id);

                tvAdTitle.setText(ad_title);
                tvLink.setText(ad_link);
                tvCompany.setText(company_name);

                tvQ1.setVisibility(View.GONE);
                tvQ2.setVisibility(View.GONE);
                tvQ3.setVisibility(View.GONE);
                tvQ4.setVisibility(View.GONE);

                sbQ1.setVisibility(View.GONE);
                sbQ2.setVisibility(View.GONE);
                sbQ3.setVisibility(View.GONE);
                sbQ4.setVisibility(View.GONE);

                LLLast.setVisibility(View.GONE);

                tvProgress1.setVisibility(View.GONE);
                tvProgress2.setVisibility(View.GONE);
                tvProgress3.setVisibility(View.GONE);
                tvProgress4.setVisibility(View.GONE);

                mYouTubePlayerView.setVisibility(View.VISIBLE);

                bPostSubmitNewVideo.setVisibility(View.GONE);
                bWPlay.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.VISIBLE);
                bSubmit.setVisibility(View.GONE);
                bPlayAgain.setVisibility(View.GONE);

                cbOpt.setVisibility(View.GONE);
                cbFavorite.setVisibility(View.GONE);

                newVid = true;
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                ytVideoID = yt_id;
                //playVideo(ytVideoID);
                playVideo(yt_id);
            }
        }

        if (buttonPlaceholder.equals("bSubmit")) {
            LLLast.setVisibility(View.GONE);
            cbOpt.setVisibility(View.GONE);
            cbFavorite.setVisibility(View.GONE);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            Log.d("SubmitResult ", s);
            if(s.equals("invalid user")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
                startActivity(in);
            }
            if(s.equals("ad no longer active")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
                startActivity(in);
            }
            if(s.equals("ratings submitted")){
                Toast.makeText(this, "Ratings have sucessfully been submitted!", Toast.LENGTH_SHORT).show();
                onClick(bOtherNewVideo);
            }
            if(s.equals("ad complete")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                onClick(bOtherNewVideo);
            }
        }
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
                        Intent in = new Intent(YouTubeActivity.this, IndexActivity.class);
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
                Intent in = new Intent(YouTubeActivity.this, PickAdActivity.class);
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
                    Intent in = new Intent(YouTubeActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(YouTubeActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(YouTubeActivity.this);
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
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        youTubeThumbnailLoader = youTubeThumbnailLoader;
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String id = preferences.getString("yt_id", "");
        youTubeThumbnailLoader.setVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }
}

