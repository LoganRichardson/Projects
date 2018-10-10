package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.raadz.program.raadzandroid.R.id.imgImage;
import static com.raadz.program.raadzandroid.R.id.ivImage;
import static com.raadz.program.raadzandroid.R.id.spinnerDefault;
import static com.raadz.program.raadzandroid.R.id.tvEarnings;
import static com.raadz.program.raadzandroid.R.id.tvName;
import static com.raadz.program.raadzandroid.R.id.view;

public class UserAdHistoryActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {

    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    NavigationView navigationView = null;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    final MediaPlayer mp = new MediaPlayer();

    ImageButton ibProfile;

    Spinner sAdTable;

    LinearLayout LLHistory;
    LinearLayout LLHistoryHeader;

    TextView tvMoney;
    TextView tvNone;

    String getHistoryURL = "https://raadz.com/getUserCompletedAds.php";
    String ToggleFavoriteURL = "https://raadz.com/toggleSavedAd.php";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String httpRequest;
    String result;
    String result2;
    String balance;
    String money;
    String color;
    String colorPrimary = "#FFCECECE";
    String colorSecondary = "#FFCECECE";
    String bothTime;

    int currentTime;
    int totalTime;
    int i;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;

    boolean checkAudio = false;
    boolean checkAdRef = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ad_history);

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

        sAdTable = (Spinner) findViewById(R.id.sAdTable);

        LLHistory = (LinearLayout) findViewById(R.id.LLHistory);
        LLHistoryHeader = (LinearLayout) findViewById(R.id.LLHistoryHeader);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvNone = (TextView) findViewById(R.id.tvNone);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("funds", "");
        tvMoney.setText("$" + balance);
        result = preferences.getString("result", "");

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Recent");
        spinnerArray.add("Favorites");
        spinnerArray.add("Complete");
        spinnerArray.add("Earnings");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAdTable.setAdapter(adapter);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserAdHistoryActivity.this, AdsHistory.class);
                startActivity(in);
            }
        });

        sAdTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = sAdTable.getSelectedItem().toString();
                Log.d("Spinner item ", item);

                if (item.equals("Recent")) {
                    RecentAds(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
                } else if (item.equals("Favorites")) {
                    FavoriteAds(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
                } else if (item.equals("Complete")) {
                    CompleteAds(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
                } else if (item.equals("Earnings")) {
                    EarningsAds(preferences.getString("raadz_user_id", ""), preferences.getString("token", ""));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }


    public void recentDetails(final HashMap<String, String> adResults) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserAdHistoryActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.uh_recent_dialog, null);

        final CheckBox mFav = (CheckBox) mView.findViewById(R.id.cbFav);
        Button mDone = (Button) mView.findViewById(R.id.bDone);
        TextView tvCompany = (TextView) mView.findViewById(R.id.tvCompany);
        TextView tvAd = (TextView) mView.findViewById(R.id.tvAd);
        TextView tvLink = (TextView) mView.findViewById(R.id.tvLink);
        TextView tvCost = (TextView) mView.findViewById(R.id.tvCost);
        TextView tvViews = (TextView) mView.findViewById(R.id.tvViews);
        TextView tvDate2 = (TextView) mView.findViewById(R.id.tvDate_n);
        TextView tvDate = (TextView) mView.findViewById(R.id.tvDate);
        TextView tvRemoved = (TextView) mView.findViewById(R.id.tvRemoved);
        final TextView mProgress = mView.findViewById(R.id.tvProgress);

        Button mFacebook = (Button) mView.findViewById(R.id.bFacebook);
        Button mTwitter = (Button) mView.findViewById(R.id.bTwitter);

        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);
        ImageView ivImage = mView.findViewById(R.id.ivImage);

        TextView mStatus = mView.findViewById(R.id.tvStatus);

        Button mVideo = mView.findViewById(R.id.bVideo);
        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        final Button mPlay = mView.findViewById(R.id.bPlay);
        final Button mPause = mView.findViewById(R.id.bPause);

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        Log.d("USER ", "USERADHISTORY");

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Log.d("Ad_reference ", adResults.get("ref"));

        final String t_ref = String.valueOf(adResults.get("ref"));

        Log.d("t_ref ", t_ref);

        if (t_ref != "null") {
            checkAdRef = true;
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
                mAudioLayout.setVisibility(View.VISIBLE);
                runOnUiThread(new Runnable() {
                    public void run() {
                        audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, mView);
                    }
                });
            }

        } else {
            tvRemoved.setVisibility(View.VISIBLE);
        }


        tvCompany.setText(adResults.get("company"));
        tvAd.setText(adResults.get("title"));
        tvLink.setText(adResults.get("link"));

        SpannableString content1 = new SpannableString(adResults.get("title"));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvLink.setText(content1);

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

        if (adResults.get("earnings").equals("null")) {
            tvDate.setText("Ad still active, check back later for your stading and earnings!");
            tvDate2.setText("Ad Rated on: ");
        } else {
            tvDate.setText(adResults.get("date").substring(0, 10));
            tvDate2.setText("Ad Completed on: ");
        }

        mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFav.isChecked()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    HashMap postData = new HashMap();
                    postData.put("user_id", preferences.getString("raadz_user_id", ""));
                    postData.put("token", preferences.getString("token", ""));
                    postData.put("ad_id", adResults.get("adid"));
                    PostResponseAsyncTask task = new PostResponseAsyncTask(UserAdHistoryActivity.this, postData);
                    task.execute(ToggleFavoriteURL);
                }
                if (!mFav.isChecked()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    HashMap postData = new HashMap();
                    postData.put("user_id", preferences.getString("raadz_user_id", ""));
                    postData.put("token", preferences.getString("token", ""));
                    postData.put("ad_id", adResults.get("adid"));
                    PostResponseAsyncTask task = new PostResponseAsyncTask(UserAdHistoryActivity.this, postData);
                    task.execute(ToggleFavoriteURL);
                }
            }
        });

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog = new ShareDialog(UserAdHistoryActivity.this);
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
                String tweetURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "") + adResults.get("adid");
                String tweetText = "Come check out this ad on Raadz.com!";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?url=" + tweetURL + "&text=" + tweetText));
                startActivity(browserIntent);
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
                Log.d("checkAdRef ", String.valueOf(t_ref));
                if (t_ref == "null"){
                    dialog.dismiss();
                    return;
                } else {
                    if (adResults.get("type").equals("1")) {
                        player.release();
                    }
                    if (adResults.get("type").equals("3")) {
                        mp.stop();
                    }
                    dialog.dismiss();
                    return;
                }
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
//        try {
//            mp.reset();
//            mp.setDataSource(path + fileName);
//            mp.prepare();
//
//            Log.d("file ", fileName);
//
//            audioTrack(mView);
//
//
//        } catch (Exception e) {
//            System.out.println();
//        }


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAudio == false) {
                    checkAudio = true;
                    try {
                        mp.reset();
                        mp.setDataSource(path + fileName);
                        mp.prepare();

                        Log.d("file ", fileName);

                        audioTrack(mView);


                    } catch (Exception e) {
                        System.out.println();
                    }
                    mp.start();
                    audioTrack(mView);
                } else if (checkAudio == true) {
                    mp.start();
                    audioTrack(mView);
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


    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, FragActivity.class);
        startActivity(in);

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
                        Intent in = new Intent(UserAdHistoryActivity.this, IndexActivity.class);
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
                    Intent in = new Intent(UserAdHistoryActivity.this, IndexActivity.class);
                    startActivity(in);
                }
            });
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserAdHistoryActivity.this);
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

        } else if (id == R.id.nav_privacy) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserAdHistoryActivity.this);
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

    }

    @Override
    public void processFinish(String s) {
        Log.d("ToggleResult ", result);
    }

    public void RecentAds(final String RID, final String RT) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user_id", RID));
                nameValuePairs.add(new BasicNameValuePair("token", RT));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getHistoryURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getHistoryURL);
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

                Log.d("httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                result2 = result;
                Log.d("result ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("UAHResult", result);
                edit.commit();

                runOnUiThread(new Runnable() {
                    public void run() {
                        i1 = 0;
                        i2 = 0;
                        i3 = 0;
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        result2 = preferences.getString("UAHResult", "");
                        Log.d("result2 ", result2);

                        if (result2.contains("no completed ads")) {
                            Log.d("1 ", "first if");
                            tvNone.setVisibility(View.VISIBLE);
                        } else {
                            LLHistoryHeader.removeAllViews();
                            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout_header, null);
                            LLHistoryHeader.addView(layout_h);
                            LLHistory.removeAllViews();
                            Log.d("2 ", "second if");
                            tvNone.setVisibility(View.GONE);
                            Log.d("try this 1 ", preferences.getString("FR1", ""));
                            Log.d("try this 2 ", result2);
                            try {
                                Log.d("3 ", "inside try");
                                JSONArray jArray = new JSONArray(result2);
                                for (i = 0; i < jArray.length(); i++) {
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

                                    TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
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
                                    tv4.setText("Gain");

                                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                                    LLHistory.addView(layout);

                                    try {
                                        if (jObj.getString("percentile").equals("null")) {
                                            tvt3.setText("-");
                                        } else {
                                            double sum = Double.parseDouble(jObj.getString("percentile"));
                                            sum = 100 - sum;
                                            tvt3.setText(sum + "%");
                                        }
                                        if (jObj.getString("earnings").equals("null")) {
                                            tvt4.setText("-");
                                        } else
                                            tvt4.setText("$" + jObj.getString("earnings"));
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

                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                        }
                    }
                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(RID, RT);
    }

    public void FavoriteAds(final String RID, final String RT) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user_id", RID));
                nameValuePairs.add(new BasicNameValuePair("token", RT));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getHistoryURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getHistoryURL);
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

                Log.d("httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                result2 = result;
                Log.d("result ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("UAHResult", result);
                edit.commit();

                runOnUiThread(new Runnable() {
                    public void run() {
                        i2 = 0;
                        i3 = 0;
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        result2 = preferences.getString("UAHResult", "");
                        Log.d("Favorite Results ", result2);

                        if (result2.contains("no favorited ads")) {
                            Log.d("1 ", "first if");
                            tvNone.setVisibility(View.VISIBLE);
                        } else {
                            LLHistoryHeader.removeAllViews();
                            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout_header, null);
                            LLHistoryHeader.addView(layout_h);
                            LLHistory.removeAllViews();
                            tvNone.setVisibility(View.GONE);
                            Log.d("try this 1 ", preferences.getString("FR1", ""));
                            Log.d("try this 2 ", result2);
                            try {
                                JSONArray jArray = new JSONArray(result2);
                                Log.d("Favorite ", String.valueOf(jArray.length()));
                                for (i = 101; i < jArray.length(); i++) {
                                    Log.d("Favorite ", "inside for");
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

                                    TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
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
                                    tv4.setText("Gain");

                                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                                    LLHistory.addView(layout);

                                    try {
                                        if (jObj.getString("percentile").equals("null")) {
                                            tvt3.setText("-");
                                        } else {
                                            double sum = Double.parseDouble(jObj.getString("percentile"));
                                            sum = 100 - sum;
                                            tvt3.setText(sum + "%");
                                        }
                                        if (jObj.getString("earnings").equals("null")) {
                                            tvt4.setText("Pending");
                                        } else
                                            tvt4.setText("$" + jObj.getString("earnings"));
                                        tvNum.setText((String.valueOf(i1 + 1)) + ")");
                                        tvt1.setText(jObj.getString("company_name"));
                                        tvt2.setText(jObj.getString("ad_title"));
                                        i1++;
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
                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(RID, RT);
    }

    public void CompleteAds(final String RID, final String RT) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user_id", RID));
                nameValuePairs.add(new BasicNameValuePair("token", RT));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getHistoryURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getHistoryURL);
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

                Log.d("httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                result2 = result;
                Log.d("result ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("UAHResult", result);
                edit.commit();

                runOnUiThread(new Runnable() {
                    public void run() {
                        i1 = 0;
                        i3 = 0;
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        result2 = preferences.getString("UAHResult", "");
                        Log.d("result2 ", result2);

                        if (result2.contains("no completed ads")) {
                            Log.d("1 ", "first if");
                            tvNone.setVisibility(View.VISIBLE);
                        } else {
                            LLHistoryHeader.removeAllViews();
                            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout_header, null);
                            LLHistoryHeader.addView(layout_h);
                            LLHistory.removeAllViews();
                            Log.d("2 ", "second if");
                            tvNone.setVisibility(View.GONE);
                            Log.d("try this 1 ", preferences.getString("FR1", ""));
                            Log.d("Complete Results ", result2);
                            try {
                                JSONArray jArray = new JSONArray(result2);

                                for (i = 201; i < jArray.length(); i++) {
                                    Log.d("4 ", "inside for");
                                    final JSONObject jObj = jArray.getJSONObject(i);
                                    Log.d("", "");
                                    Log.d("adid ", jObj.getString("raadz_ad_id"));
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

                                    TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
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
                                    tv4.setText("Gain");

                                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                                    LLHistory.addView(layout);

                                    try {
                                        if (jObj.getString("percentile").equals("null")) {
                                            tvt3.setText("-");
                                        } else {
                                            double sum = Double.parseDouble(jObj.getString("percentile"));
                                            sum = 100 - sum;
                                            tvt3.setText(sum + "%");
                                        }
                                        if (jObj.getString("earnings").equals("null")) {
                                            tvt4.setText("Pending");
                                        } else
                                            tvt4.setText("$" + jObj.getString("earnings"));
                                        tvNum.setText((String.valueOf(i2 + 1)) + ")");
                                        tvt1.setText(jObj.getString("company_name"));
                                        tvt2.setText(jObj.getString("ad_title"));
                                        i2++;
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
                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(RID, RT);
    }

    public void EarningsAds(final String RID, final String RT) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user_id", RID));
                nameValuePairs.add(new BasicNameValuePair("token", RT));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getHistoryURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getHistoryURL);
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

                Log.d("httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                result2 = result;
                Log.d("result ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("UAHResult", result);
                edit.commit();

                runOnUiThread(new Runnable() {
                    public void run() {
                        i3 = 0;
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        result2 = preferences.getString("UAHResult", "");
                        Log.d("result2 ", result2);

                        if (result2.contains("no completed ads")) {
                            Log.d("1 ", "first if");
                            tvNone.setVisibility(View.VISIBLE);
                        } else {
                            LLHistoryHeader.removeAllViews();
                            LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.history_layout_header, null);
                            LLHistoryHeader.addView(layout_h);
                            LLHistory.removeAllViews();
                            Log.d("2 ", "second if");
                            tvNone.setVisibility(View.GONE);
                            Log.d("try this 1 ", preferences.getString("FR1", ""));
                            Log.d("Earnings Results ", result2);
                            try {
                                JSONArray jArray = new JSONArray(result2);
                                for (i = 301; i < jArray.length(); i++) {
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

                                    TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
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
                                    tv4.setText("Gain");

                                    LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
                                    LLHistory.addView(layout);

                                    try {
                                        if (jObj.getString("percentile").equals("null")) {
                                            tvt3.setText("-");
                                        } else {
                                            double sum = Double.parseDouble(jObj.getString("percentile"));
                                            sum = 100 - sum;
                                            tvt3.setText(sum + "%");
                                        }
                                        if (jObj.getString("earnings").equals("null")) {
                                            tvt4.setText("Pending");
                                        } else
                                            tvt4.setText("$" + jObj.getString("earnings"));
                                        tvNum.setText((String.valueOf(i3 + 1)) + ")");
                                        tvt1.setText(jObj.getString("company_name"));
                                        tvt2.setText(jObj.getString("ad_title"));
                                        i3++;
                                        //tvt3.setText(jObj.getString("percentile") + "%");

                                    } catch (org.json.JSONException e) {
                                        System.out.println(e);
                                    }

                                    tvt1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                String url = ("http://www." + jObj.getString("ad_link"));
                                                Intent in = new Intent(Intent.ACTION_VIEW);
                                                in.setData(Uri.parse(url));
                                                startActivity(in);
                                            } catch (org.json.JSONException e) {
                                                System.out.println(e);
                                            }
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
                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(RID, RT);
    }


}
