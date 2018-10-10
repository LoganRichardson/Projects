package com.raadz.program.raadzandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
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
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.raadz.program.raadzandroid.R.id.CardWidget;
import static com.raadz.program.raadzandroid.R.id.bOptResend;
import static com.raadz.program.raadzandroid.R.id.bViews2;
import static com.raadz.program.raadzandroid.R.id.tvNoCompleted;
import static com.raadz.program.raadzandroid.R.id.tvPurchaseWithBalance;

public class FragPubActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {
    final String TAG = this.getClass().getName();

    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    final MediaPlayer mp = new MediaPlayer();

    View vAlt;

    CardMultilineWidget CardWidget;

    ImageButton ibProfile;

    LinearLayout LLSubmit;
    LinearLayout LLStarted;
    LinearLayout LLRecent;
    LinearLayout LLSeparate;
    LinearLayout LLLeaderboards;
    LinearLayout LLHistory;
    LinearLayout LLHistoryHeader;
    LinearLayout LLHeader;

    Button bRate;
    Button bMore;
    Button bMoreLeaderboards;
    Button bDeposit;

    TextView tvWelcome;
    TextView tvID;
    TextView tvName;
    TextView tvBalance;
    TextView tvNone;
    TextView tvMoney;
    TextView tvP2;
    TextView tvP4;

    String StripeDepositURL = "https://raadz.com/charge-deposit-stripe.php";
    String StripeChargeURL = "https://raadz.com/charge-pay-stripe.php";
    String CityListURL = "https://raadz.com/getCityList.php";
    String getLeaderboardsURL = "https://raadz.com/getLeaderboards.php";
    String EmailResultsURL = "https://raadz.com/emailAdDetails.php";
    String getPubInfoURL = "https://raadz.com/getPubInfo.php";
    String getPubURL = "https://raadz.com/getPubAds.php";
    String getDataURL = "https://raadz.com/getUserInfo.php";
    String getUserDataURL = "https://raadz.com/getUserData.php";
    String OptInListURL = "https://raadz.com/buyCompletedOptinList.php";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String durationKey1 = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=";
    String durationKey2 = "&key=AIzaSyApjGaDZhoCC_SbLGzcDrZKARX2EGElG20";
    String durationKey;
    String buttonPlaceholder = "";
    String bait = "";
    String RID;
    String RT;
    String money;
    String info;
    String company;
    String balance;
    String result1;
    String result2;
    String result3;
    String httpRequest;
    String bothTime;

    int currentTime;
    int totalTime;
    int i;
    int alt;
    int interestsCounter;
    int interestsNumber;
    int interestsSelected;

    double deposit = 0;

    boolean bToggle = false;
    boolean check = false;
    boolean paymentCheck = false;

    int[] ageArray = new int[5];
    int[] valueArray = new int[32];

    NavigationView navigationView = null;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frag_pub);
            doEverything();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }


    public void doEverything() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        if (preferences.getString("email_confirmation", "").equals("not confirmed - resend") || preferences.getString("email_confirmation", "").equals("not confirmed")) {
            Intent in = new Intent(this, FragNotConfirmedActivity.class);
            startActivity(in);
        }

        Log.d("fbid ", String.valueOf(preferences.getString("raadz_fb_user_id", "")));
        Log.d("id ", preferences.getString("raadz_fb_user_id", ""));
        Log.d("token ", preferences.getString("token", ""));
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

        if (preferences.getString("raadz_user_id", "").equals("user not found")) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
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
                    Intent in = new Intent(FragPubActivity.this, LoginActivity.class);
                    startActivity(in);
                }
            });
        }


        Log.d("RID main ", RID);
        Log.d("RT main", RT);

        Log.d("bf balance: ", preferences.getString("money", ""));

        DataFunction(RID, RT);
        Log.d("bf balance: ", preferences.getString("cash", ""));
        Log.d("bf F: ", preferences.getString("fName", ""));
        Log.d("bf L: ", preferences.getString("lName", ""));
        Log.d("1", "1");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        LLSubmit = (LinearLayout) findViewById(R.id.LLSubmit);
        LLStarted = (LinearLayout) findViewById(R.id.LLStarted);
        LLRecent = (LinearLayout) findViewById(R.id.LLRecent);
        LLSeparate = (LinearLayout) findViewById(R.id.LLSeparate);
        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHistory = (LinearLayout) findViewById(R.id.LLHistory);
        LLHistoryHeader = (LinearLayout) findViewById(R.id.LLHistoryHeader);
        LLHeader = (LinearLayout) findViewById(R.id.LLHeader);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvNone = (TextView) findViewById(R.id.tvNone);
        tvMoney = (TextView) findViewById(R.id.tvMoney);

        tvP2 = (TextView) findViewById(R.id.tvP2);
        tvP4 = (TextView) findViewById(R.id.tvP4);

        bRate = (Button) findViewById(R.id.bRate);
        bMore = (Button) findViewById(R.id.bMore);
        bMoreLeaderboards = (Button) findViewById(R.id.bMoreLeaderboards);
        bDeposit = (Button) findViewById(R.id.bDeposit);
        Log.d("new balance ", preferences.getString("cash", ""));
        onClick(vAlt);
        String info = preferences.getString("cash", "");
        Log.d("money info ", info);
        testMethod();
        Log.d("2", "2");
        //tvMoney.setText("$" + test);
        Log.d("3", "3");
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

        Log.d("id ", preferences.getString("raadz_pub_id", ""));

        {
            HashMap postData2 = new HashMap();
            postData2.put("pub_id", RID);
            postData2.put("token", preferences.getString("token", ""));
            PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData2);
            task2.execute(getPubURL);
        }

        result2 = preferences.getString("FR2", "");
        Log.d("result2 ", result2);


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                result3 = preferences.getString("IndexPass", "");
//                LLLeaderboards.removeAllViews();
//                LLHeader.removeAllViews();
//                LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout_header, null);
//                LLHeader.addView(layout_h);
//                Log.d("result3iswhat ", result3);
//                try {
//                    JSONObject jsonObj = new JSONObject(result3);
//                    Log.d("NewJSONOBJ ", jsonObj.getString("0"));
//                    Log.d("testJSON1 ", result3);
//                    Log.d("looper ", result3);
//                    String pass_result = jsonObj.getString("0");
//                    Log.d("ZERO ", pass_result);
//                    JSONArray jArray = new JSONArray(pass_result);
//
//                    JSONObject pass_Obj = jArray.getJSONObject(0);
//                    Log.d("", "");
//                    Log.d("pass_position ", String.valueOf(1));
//                    Log.d("pass_display ", pass_Obj.getString("display_name"));
//                    Log.d("pass_display ", pass_Obj.getString("all_earned"));
//                    Log.d("pass_display ", pass_Obj.getString("user_loc"));
//                    Log.d("", "");
//                    for (int i = 0; i < 5; i++) {
//                        JSONArray jsonArray = new JSONArray(jsonObj.getString("0"));
//                        JSONObject jObj = jsonArray.getJSONObject(0);
//                        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.table_layout, null);
//                        TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
//                        TextView tvName = (TextView) layout.findViewById(R.id.tvTLUser);
//                        TextView tvCity = (TextView) layout.findViewById(R.id.tvCityOther);
//                        TextView tvEarnings = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
//                        TextView tvDate = (TextView) layout.findViewById(R.id.tvCompletedHistOther);
//                        LinearLayout boardLayout = (LinearLayout) layout.findViewById(R.id.LLTLHistory);
//                        LLLeaderboards.addView(layout);
//
//                        tvNum.setText((String.valueOf(i + 1)) + ")");
//                        tvName.setText(jObj.getString("display_name"));
//                        tvCity.setText(jObj.getString("user_loc"));
//                        tvEarnings.setText("$" + jObj.getString("all_earned"));
////                tvDate.setText(jObj.getString("update_time"));
//
//                        TextView tv1 = (TextView) layout.findViewById(R.id.tvfUser);
//                        TextView tv2 = (TextView) layout.findViewById(R.id.tvfCity);
//                        TextView tv3 = (TextView) layout.findViewById(R.id.tvfEarningsHistOther);
//                        TextView tv4 = (TextView) layout.findViewById(R.id.tvfCompletedHistOther);
//
////                            tv1.setText(jObj.getString("Company"));
////                            tv2.setText(jObj.getString("Ad"));
////                            tv3.setText(jObj.getString("Percentile"));
////                            tv4.setText(jObj.getString("Earnings"));
//
//                    }
//                } catch (org.json.JSONException e) {
//                    System.out.println();
//                }
//            }
//        });

        bRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragPubActivity.this, PubSubmitActivity.class);
                startActivity(in);
            }
        });

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragPubActivity.this, PubProfileActivity.class);
                startActivity(in);
            }
        });

        bMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragPubActivity.this, PubAdHistory.class);
                startActivity(in);
            }
        });

        bMoreLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(FragPubActivity.this, PubLeaderboardsActivity.class);
                startActivity(in);
            }
        });

        bDeposit.setOnClickListener(this);
    }

//    public TextView getTextView(){
//        TextView txtView = (TextView)findViewById(R.id.tvMoney);
//        return txtView;
//    }

    public void StripeDetails(
            final String token,
            final String amount,
            final String email,
            final String pubID,
            final String description,
            final String title,
            final String ytID,
            final String link,
            final String promo,
            final String totviews,
            final String gender,
            final String age,
            final String location,
            final String interests,
            final String questions
    ) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            String updateURL;

            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("stripeToken", token));
                nameValuePairs.add(new BasicNameValuePair("stripeAmountInCents", amount));
                nameValuePairs.add(new BasicNameValuePair("stripeEmail", email));
                nameValuePairs.add(new BasicNameValuePair("stripePubID", pubID));
                nameValuePairs.add(new BasicNameValuePair("stripePubToken", preferences.getString("token", "")));
                nameValuePairs.add(new BasicNameValuePair("stripeDescription", "25"));

                nameValuePairs.add(new BasicNameValuePair("title", title));
                nameValuePairs.add(new BasicNameValuePair("ytID", ytID));
                nameValuePairs.add(new BasicNameValuePair("link", link));
                nameValuePairs.add(new BasicNameValuePair("promo", promo));
                nameValuePairs.add(new BasicNameValuePair("totviews", totviews));
                nameValuePairs.add(new BasicNameValuePair("gender", gender));
                nameValuePairs.add(new BasicNameValuePair("ageVal", age));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("interests", interests));
                nameValuePairs.add(new BasicNameValuePair("questVal", questions));

                try {
                    if (check == true) {
                        updateURL = StripeDepositURL;
                    }
                    if (check == false) {
                        updateURL = StripeChargeURL;
                    }
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(updateURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(updateURL);
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
                Log.d("StripeResponse ", result);
                if (result.equals("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            paymentCheck = true;
                        }
                    });
                    Intent in = new Intent(FragPubActivity.this, FragPubActivity.class);
                    startActivity(in);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(token, amount, email, pubID, description);
    }

    public int getValue(int value) {
        valueArray = new int[30];
        Log.d("valueArray[0] ", String.valueOf(valueArray[0]));
        Log.d("valueArray[1] ", String.valueOf(valueArray[1]));
        Log.d("valueArray[2] ", String.valueOf(valueArray[2]));
        Log.d("valueArray[3] ", String.valueOf(valueArray[3]));
        Log.d("valueArray[4] ", String.valueOf(valueArray[4]));
        Log.d("valueArray[5] ", String.valueOf(valueArray[5]));
        Log.d("valueArray[6] ", String.valueOf(valueArray[6]));
        Log.d("valueArray[7] ", String.valueOf(valueArray[7]));
        Log.d("valueArray[8] ", String.valueOf(valueArray[8]));
        Log.d("valueArray[9] ", String.valueOf(valueArray[9]));
        Log.d("valueArray[10] ", String.valueOf(valueArray[10]));
        Log.d("valueArray[11] ", String.valueOf(valueArray[11]));
        Log.d("valueArray[12] ", String.valueOf(valueArray[12]));
        Log.d("valueArray[13] ", String.valueOf(valueArray[13]));
        Log.d("valueArray[14] ", String.valueOf(valueArray[14]));
        Log.d("valueArray[15] ", String.valueOf(valueArray[15]));

        if (value >= 536870912) {
            value = value - 536870912;
            valueArray[29] = 536870912;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 268435456) {
            value = value - 268435456;
            valueArray[28] = 268435456;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 134217728) {
            value = value - 134217728;
            valueArray[27] = 134217728;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 67108864) {
            value = value - 67108864;
            valueArray[26] = 67108864;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 33554432) {
            value = value - 33554432;
            valueArray[25] = 33554432;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 16777216) {
            value = value - 16777216;
            valueArray[24] = 16777216;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 8388608) {
            value = value - 8388608;
            valueArray[23] = 8388608;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 4194304) {
            value = value - 4194304;
            valueArray[22] = 4194304;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 2097152) {
            value = value - 2097152;
            valueArray[21] = 2097152;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 1078576) {
            value = value - 1078576;
            valueArray[20] = 1078576;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 524288) {
            value = value - 524288;
            valueArray[19] = 524288;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 262144) {
            value = value - 262144;
            valueArray[18] = 262144;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 131072) {
            value = value - 131072;
            valueArray[17] = 131072;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 65536) {
            value = value - 65536;
            valueArray[16] = 65536;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 32768) {
            value = value - 32768;
            valueArray[15] = 32768;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 16384) {
            value = value - 16384;
            valueArray[14] = 16384;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 8192) {
            value = value - 8192;
            valueArray[13] = 8192;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 4096) {
            value = value - 4096;
            valueArray[12] = 4096;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 2048) {
            value = value - 2048;
            valueArray[11] = 2048;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 1024) {
            value = value - 1024;
            valueArray[10] = 1024;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 512) {
            value = value - 512;
            valueArray[9] = 512;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 256) {
            value = value - 256;
            valueArray[8] = 256;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 128) {
            value = value - 128;
            valueArray[7] = 128;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 64) {
            value = value - 64;
            valueArray[6] = 64;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 32) {
            value = value - 32;
            valueArray[5] = 32;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 16) {
            value = value - 16;
            valueArray[4] = 16;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 8) {
            value = value - 8;
            valueArray[3] = 8;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 4) {
            value = value - 4;
            valueArray[2] = 4;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 2) {
            value = value - 2;
            valueArray[1] = 2;
            interestsSelected = interestsSelected + 1;
        }
        if (value >= 1) {
            value = value - 1;
            valueArray[0] = 1;
            interestsSelected = interestsSelected + 1;
        }

        Log.d("end value ", String.valueOf(value));
        Log.d("interestsSelected ", String.valueOf(interestsSelected));

        return value;
    }

    public int ageValue(int value) {
        Log.d("valueArray[0] ", String.valueOf(valueArray[0]));
        Log.d("valueArray[1] ", String.valueOf(valueArray[1]));
        Log.d("valueArray[2] ", String.valueOf(valueArray[2]));
        Log.d("valueArray[3] ", String.valueOf(valueArray[3]));
        Log.d("valueArray[4] ", String.valueOf(valueArray[4]));
        Log.d("valueArray[5] ", String.valueOf(valueArray[5]));

        if (value >= 16) {
            value = value - 16;
            ageArray[4] = 16;
        }
        if (value >= 8) {
            value = value - 8;
            ageArray[3] = 8;
        }
        if (value >= 4) {
            value = value - 4;
            ageArray[2] = 4;
        }
        if (value >= 2) {
            value = value - 2;
            ageArray[1] = 2;
        }
        if (value >= 1) {
            value = value - 1;
            ageArray[0] = 1;
        }

        for (int j = 0; j < ageArray.length; j++) {
            Log.d("ageArray ", String.valueOf(j) + " | " + ageArray[j]);
        }

        Log.d("end value ", String.valueOf(value));

        return value;
    }

    public void depositFunds() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
        final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
        final EditText mName = (EditText) mView.findViewById(R.id.etName);
        final EditText mZip = (EditText) mView.findViewById(R.id.etZip);
        CardWidget = (CardMultilineWidget) mView.findViewById(R.id.CardWidget);

        Button mCancel = (Button) mView.findViewById(R.id.bCancel);
        Button mEnter = (Button) mView.findViewById(R.id.bEnter);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAmount.getText().equals("")) {
                    Toast.makeText(FragPubActivity.this, "Enter a valid Amount", Toast.LENGTH_SHORT).show();
                }
//                    else if (CardWidget.getCard().getNumber().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Card Number", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getExpMonth().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Expiration Month", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getExpYear().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Expiration Year", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getCVC().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid CVC Number", Toast.LENGTH_SHORT).show();
//                    }

                else {
                    try {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final Stripe stripe = new Stripe(getApplicationContext(), preferences.getString("stripe_public_key", ""));

                        final String tempName = mName.getText().toString();
                        final String tempZip = mZip.getText().toString();


                        //new Stripe(getApplicationContext()).setDefaultPublishableKey(StripeConfig.STRIPE_API_KEY);

                        //Card card = new Card(mNumber.getText().toString(), Integer.parseInt(month), Integer.parseInt(year), mCVC.getText().toString());
                        final Card card = new Card(CardWidget.getCard().getNumber(), CardWidget.getCard().getExpMonth(), CardWidget.getCard().getExpYear(), CardWidget.getCard().getCVC());

                        card.setName(tempName);
                        card.setAddressZip(tempZip);

                        Log.d("Number ", card.getNumber());
                        Log.d("Month ", String.valueOf(card.getExpMonth()));
                        Log.d("Year ", String.valueOf(card.getExpYear()));
                        Log.d("CVC ", card.getCVC());
                        Log.d("Name/Email ", tempName);
                        Log.d("Zip Code ", tempZip);
                        stripe.createToken(card, preferences.getString("stripe_public_key", ""), new TokenCallback() {

                            @Override
                            public void onError(Exception error) {
                                Log.d("something ", "exception", error);
                            }

                            @Override
                            public void onSuccess(Token token) {
                                Log.d("pointer 1", "1");
//                                com.stripe.Stripe.apiKey = StripeConfig.getSecretKey();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String s_email = preferences.getString("email", "");
                                String s_pid = preferences.getString("raadz_pub_id", "");
                                //Log.d("token ", token.getId());
                                Log.d("amount ", mAmount.getText().toString());
                                Log.d("email ", s_email);
                                Log.d("pubID ", s_pid);
                                Log.d("description ", "Deposit to account");

                                Log.d("paymentCheck ", String.valueOf(paymentCheck));
                                check = true;
                                if (check == true) {
                                    deposit = Double.parseDouble(mAmount.getText().toString());
                                    Log.d("b_deposit ", String.valueOf(deposit));
                                    deposit = deposit * 100;
                                    if (deposit < 1000) {
                                        Log.d("Deposit Amount if 1 ", String.valueOf(deposit));
                                        Toast.makeText(FragPubActivity.this, "Minimum deposit amount: $10.00", Toast.LENGTH_SHORT).show();
                                        mAmount.setText("");
                                    } else if (tempName.length() < 1) {
                                        Toast.makeText(FragPubActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                                    } else if (tempZip.length() < 1) {
                                        Toast.makeText(FragPubActivity.this, "Please enter your Zip", Toast.LENGTH_SHORT).show();
                                    } else if (deposit >= 1000) {
                                        int x;
                                        x = (int)deposit;
                                        StripeDetails(
                                                token.getId(),
                                                String.valueOf(deposit),
                                                s_email, s_pid,
                                                "something something",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "");
                                        Log.d("Deposit Amount if 2 ", String.valueOf(deposit));
                                        buttonPlaceholder = "bAddFunds";
                                        HashMap postData = new HashMap();
                                        postData.put("pub_id", s_pid);
                                        PostResponseAsyncTask task = new PostResponseAsyncTask(FragPubActivity.this, postData);
                                        task.execute(getPubInfoURL);
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                        Toast.makeText(FragPubActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(FragPubActivity.this, FragPubActivity.class);
                                        startActivity(in);
                                    }

                                }
                                //AdvertiserFunction(s_pid);

                                    /*
                                    try {
                                        final String tempToken = token.getId();
                                        final Map<String, Object> chargeParams = new HashMap<String, Object>();
                                        chargeParams.put("amount", Integer.parseInt(mAmount.getText().toString()));
                                        chargeParams.put("currency", "usd");
                                        chargeParams.put("source", token.getId());
                                        chargeParams.put("description", "Example charge");
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("pointer 2", "2");
                                                Charge charge = null;
                                                try {
                                                    charge = Charge.create(chargeParams);
                                                } catch (com.stripe.exception.APIConnectionException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.InvalidRequestException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.AuthenticationException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.APIException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.CardException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.d("pointer 3", "3");
                                                System.out.println("Charge Log :" + charge);
                                                Log.d("Paid amount ", String.valueOf(mAmount.getText().toString()));
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                String s_email = preferences.getString("email", "");
                                                String s_pid = preferences.getString("raadz_pub_id", "");
                                                Log.d("token ", tempToken);
                                                Log.d("amount ", mAmount.getText().toString());
                                                Log.d("email ", s_email);
                                                Log.d("pubID ", s_pid);
                                                Log.d("description ", "12");
                                                StripeDetails(tempToken, mAmount.getText().toString(), s_email, s_pid, "something something");
                                                //Toast.makeText(PubSubmitActivity.this, "Payment of " + String.valueOf(mAmount.getText().toString()) + " was successful!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                                            }
                                        }).start();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    */
                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e);
                    }//End of catch
                }//End of else
            }//End of onClick
        });//End of mEnter Button
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

    public void audioPlayer(final String path, final String fileName, Button mPlay, Button mPause, final TextView mStatus, final View mView) {
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

    public int resultsDialog(final HashMap<String, String> adResults) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("r1 ", adResults.get("q1"));
        Log.d("r2 ", adResults.get("q2"));
        Log.d("r3 ", adResults.get("q3"));
        Log.d("r4 ", adResults.get("q4"));
        Log.d("roverall ", adResults.get("overall"));
        Log.d("rdate ", adResults.get("date"));

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.completed_ad_info_dialog, null);
        Button mDone = (Button) mView.findViewById(R.id.bDone);
        Button mShare = (Button) mView.findViewById(R.id.bShareResults);
        final Button mOptResend = (Button) mView.findViewById(R.id.bOptResend);
        final Button mOptBuy = (Button) mView.findViewById(R.id.bOptBuy);
        final EditText mEmail = (EditText) mView.findViewById(R.id.etShareResults);

        LinearLayout LLInsufficient = (LinearLayout) mView.findViewById(R.id.LLInsufficient);
        LinearLayout LLPurchase = (LinearLayout) mView.findViewById(R.id.LLPurchaseLayout);

        TextView tvCompany = (TextView) mView.findViewById(R.id.tvCompany);
        TextView tvAd = (TextView) mView.findViewById(R.id.tvAd);
        TextView tvLink = (TextView) mView.findViewById(R.id.tvLink);
        TextView tvCost = (TextView) mView.findViewById(R.id.tvCost);
        TextView tvViews = (TextView) mView.findViewById(R.id.tvViews);
        TextView tvStatus = (TextView) mView.findViewById(R.id.tvStatus);
        TextView tvPurchase = (TextView) mView.findViewById(R.id.tvPurchaseWithBalance);
        TextView tvUsersOptValue = (TextView) mView.findViewById(R.id.tvUsersOptValue);
        TextView tvBoughtOptValue = (TextView) mView.findViewById(R.id.tvBoughtOptValue);
        TextView tvOptCost = (TextView) mView.findViewById(R.id.tvOptCost);
        TextView tvInsufficientBalanceValue = (TextView) mView.findViewById(R.id.tvInsufficientBalanceValue);

        TextView tvGender = (TextView) mView.findViewById(R.id.tvGender);
        TextView tvAge = (TextView) mView.findViewById(R.id.tvAge);
        TextView tvLocation = (TextView) mView.findViewById(R.id.tvLocation);
        TextView tvInterests = (TextView) mView.findViewById(R.id.tvInterests);

        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);
        ImageView ivImage = mView.findViewById(R.id.ivImage);

        TextView mProgress = mView.findViewById(R.id.tvProgress);
        TextView mStatus = mView.findViewById(R.id.tvStatus);

        mStatus.setText("Completed");

        tvUsersOptValue.setText(adResults.get("optcount"));

        String balance = preferences.getString("cash", "");
        balance = balance.replace("$", "");

        double d_balance = Double.parseDouble(balance);

        String opt_bought = adResults.get("optbought");
        String opt_count = adResults.get("optcount");
        double d_count = Double.parseDouble(opt_count);

        Log.d("opt_bought ", opt_bought);
        Log.d("opt_count ", opt_count);
        Log.d("d_count ", String.valueOf(d_count));
        Log.d("d_balance ", String.valueOf(d_balance));

        if(opt_bought.equals("0") && opt_count.equals("0")){
            tvBoughtOptValue.setText("NO");
            tvUsersOptValue.setText(opt_count);
        }
        if(opt_bought.equals("0") && !opt_count.equals("0")){
            tvBoughtOptValue.setText("YES");
            tvUsersOptValue.setText(opt_count);
            tvPurchase.setText("$" + balance);
            LLPurchase.setVisibility(View.VISIBLE);
            mOptBuy.setVisibility(View.VISIBLE);
        }
        if(opt_bought.equals("1") && opt_count.equals("0")){
            //Never happens
        }
        if(opt_bought.equals("1") && !opt_count.equals("0")){
            tvBoughtOptValue.setText("YES");
            tvUsersOptValue.setText(opt_count);
            mOptResend.setVisibility(View.VISIBLE);
        }

//        if (adResults.get("optbought").equals("0") && adResults.get("optcount").equals("0")) {
//            tvBoughtOptValue.setText("NO");
//            tvUsersOptValue.setText(adResults.get("optcount"));
//        } else if (adResults.get("optbought").equals("1") && !adResults.get("optcount").equals("0")) {
//            tvBoughtOptValue.setText("YES");
//            tvUsersOptValue.setText(adResults.get("optcount"));
//            mOptBuy.setVisibility(View.VISIBLE);
//        } else if (adResults.get("optbought").equals("0") && !adResults.get("optcount").equals("0")) {
//            tvBoughtOptValue.setText("NO");
//            tvUsersOptValue.setText(adResults.get("optcount"));
//            mOptBuy.setVisibility(View.VISIBLE);
//        } else if (adResults.get("optbought").equals("1") && adResults.get("optcount").equals("0")){
//            tvBoughtOptValue.setText("YES");
//            tvUsersOptValue.setText(adResults.get("optcount"));
//            mOptResend.setVisibility(View.VISIBLE);
//        }

        if (d_balance < .50) {
            tvOptCost.setText("$.50");
            tvInsufficientBalanceValue.setText("$" + balance);
            mOptResend.setVisibility(View.GONE);
            LLInsufficient.setVisibility(View.VISIBLE);
        }

        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        Button mVideo = mView.findViewById(R.id.bVideo);
        Button mPlay = mView.findViewById(R.id.bPlay);
        Button mPause = mView.findViewById(R.id.bPause);

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        if (adResults.get("type").equals("1")) {

            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                    Log.d("test", "inside initizedlistener");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    final String youtubeURL = adResults.get("ref");
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

            Log.d("resultsDialog ", adResults.get("ref"));

            mAudioLayout.setVisibility(View.VISIBLE);
            audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, mView);
        }

        TextView tvQ1 = (TextView) mView.findViewById(R.id.tvQ1);
        TextView tvQ2 = (TextView) mView.findViewById(R.id.tvQ2);
        TextView tvQ3 = (TextView) mView.findViewById(R.id.tvQ3);
        TextView tvQ4 = (TextView) mView.findViewById(R.id.tvQ4);

        TextView tvState1 = (TextView) mView.findViewById(R.id.tvState1);
        TextView tvState2 = (TextView) mView.findViewById(R.id.tvState2);
        TextView tvState3 = (TextView) mView.findViewById(R.id.tvState3);
        TextView tvState4 = (TextView) mView.findViewById(R.id.tvState4);
        TextView tvStateOverall = (TextView) mView.findViewById(R.id.tvStateOverall);

        TextView tvOverall = (TextView) mView.findViewById(R.id.tvOverall);

        tvCompany.setText(adResults.get("company"));
        tvAd.setText(adResults.get("title"));
        tvLink.setText(adResults.get("link"));
        tvCost.setText("$" + adResults.get("cost"));
        tvViews.setText(adResults.get("c_views") + " / " + adResults.get("t_views"));
        tvStatus.setText("Completed");

        if (adResults.get("gender").contains("N")) {
            tvGender.setText("Any");
        } else if (adResults.get("gender").contains("M")) {
            tvGender.setText("Male");
        } else if (adResults.get("gender").contains("F")) {
            tvGender.setText("Female");
        } else {
            tvGender.setText(adResults.get("gender"));
        }

        if (adResults.get("age").equals("0")) {
            tvAge.setText("Any");
        } else {

            int age_set = Integer.parseInt(adResults.get("age"));
            ageValue(age_set);

            for (int j = 0; j < ageArray.length; j++) {
                if (ageArray[j] > 0) {
                    if (j == 0) {
                        bait = "< 18,";
                    }
                    if (j == 1) {
                        bait = bait + "18-25,";
                    }
                    if (j == 2) {
                        bait = bait + "26-40,";
                    }
                    if (j == 3) {
                        bait = bait + "41-60,";
                    }
                    if (j == 4) {
                        bait = bait + "61+";
                    }
                }
            }

            Log.d("bait ", bait);
            tvAge.setText(bait);
        }

        getValue(Integer.parseInt(adResults.get("interests")));
        if (interestsSelected == 0) {
            tvInterests.setText("Any");
        } else {
            tvInterests.setText(String.valueOf(interestsSelected) + " Interests Selected");
        }

        if (adResults.get("location").equals("No")) {
            tvLocation.setText("Any");
        } else {
            tvLocation.setText(adResults.get("location"));
        }

        tvQ1.setText(adResults.get("q1"));
        tvQ2.setText(adResults.get("q2"));
        tvQ3.setText(adResults.get("q3"));
        tvQ4.setText(adResults.get("q4"));

        String quests = adResults.get("questions");
        String split[] = quests.split(":", 5);

        Log.d("q ", split[0]);
        Log.d("q ", split[1]);
        Log.d("q ", split[2]);
        Log.d("q ", split[3]);

        tvState1.setText(split[0]);
        tvState2.setText(split[1]);
        tvState3.setText(split[2]);
        tvState4.setText(split[3]);

        tvStateOverall.setText(adResults.get("overall"));

        LinearLayout LLResub = (LinearLayout) mView.findViewById(R.id.LLResub);
        Button bResub = (Button) mView.findViewById(R.id.bResub);

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = adResults.get("link");
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("http://" + url));
                startActivity(in);
            }
        });

        LLResub.setVisibility(View.VISIBLE);

        mOptResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Opt_in_resend ", "do we get here ");
                mOptResend.setVisibility(View.GONE);
                buttonPlaceholder = "mOptResend";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("cost", adResults.get("cost"));
                PostResponseAsyncTask task = new PostResponseAsyncTask(FragPubActivity.this, postData);
                task.execute(OptInListURL);
            }
        });

        mOptBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Opt_in_resend ", "do we get here ");
                mOptResend.setVisibility(View.GONE);
                buttonPlaceholder = "mOptResend";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("cost", adResults.get("cost"));
                PostResponseAsyncTask task = new PostResponseAsyncTask(FragPubActivity.this, postData);
                task.execute(OptInListURL);
            }
        });

        bResub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TTest1 ", adResults.get("questions_value"));
                Log.d("TTest2 ", adResults.get("questions"));
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("type", adResults.get("type"));
                edit.putString("adid", adResults.get("adid"));
                edit.putString("company", adResults.get("company"));
                edit.putString("title", adResults.get("title"));
                edit.putString("link", adResults.get("link"));
                edit.putString("ref", adResults.get("ref"));
                edit.putString("gender", adResults.get("gender"));
                edit.putString("age", adResults.get("age"));
                edit.putString("location", adResults.get("location"));
                edit.putString("interests", adResults.get("interests"));
                edit.putString("questions", adResults.get("questions_value"));
                edit.putString("questions_text", adResults.get("questions"));
                edit.putString("views", adResults.get("views"));
                edit.putString("cost", adResults.get("cost"));
                edit.putString("promo", adResults.get("promo"));
                edit.putString("re_submit", "true");
                edit.commit();
                Intent in = new Intent(FragPubActivity.this, PubSubmitActivity.class);
                startActivity(in);
            }
        });

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPlaceholder = "mShare";
                Log.d("TAG_Send ", "Share optin");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("email", mEmail.getText().toString());
                PostResponseAsyncTask task = new PostResponseAsyncTask(FragPubActivity.this, postData);
                task.execute(EmailResultsURL);
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArray();
                interestsSelected = 0;
                mp.stop();
                dialog.dismiss();
                return;
            }
        });

        return 0;
    }

    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
            //player.play();
        }
    }

    public void clearArray() {
        for (int j = 0; j < ageArray.length; j++) {
            ageArray[j] = 0;
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
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
        getMenuInflater().inflate(R.menu.navigationp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                Intent intent1 = new Intent(this, PubAdHistory.class);
                startActivity(intent1);
                return true;
            case R.id.action_profile:
                Intent intent2 = new Intent(this, PubProfileActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_deposit:
                depositFunds();
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
                        Intent in = new Intent(FragPubActivity.this, IndexActivity.class);
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
        if (id == R.id.adv_submit) {
            Intent in = new Intent(this, PubSubmitActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_history) {
            Intent in = new Intent(this, PubAdHistory.class);
            startActivity(in);
        } else if (id == R.id.adv_leaderboards) {
            Intent in = new Intent(this, PubLeaderboardsActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_tutorial) {
            Intent in = new Intent(this, PubTutorialActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_about) {
            Intent in = new Intent(this, PubAboutUsActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_contact) {
            Intent in = new Intent(this, PubContactUsActivity.class);
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
                    Intent in = new Intent(FragPubActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
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

    public void DataFunction(final String raadz_pub_id, final String token) {
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

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor edit = preferences.edit();
                Log.d("ID ", raadz_pub_id);
                Log.d("DFToken ", token);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_pub_id));
                nameValuePairs.add(new BasicNameValuePair("token", preferences.getString("token", "")));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getPubInfoURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getPubInfoURL);
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

                Log.d("see this one ", httpRequest);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        balance = httpRequest;
                        try {
                            JSONArray jArray = new JSONArray(balance);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                Log.d("Array Length ", String.valueOf(jArray.length()));
                                Log.d("money: ", jObj.getString("balance"));
                                money = jObj.getString("balance");
                                company = jObj.getString("company_name");
                                edit.remove("cash").commit();
                                edit.remove("pub_company").commit();
                                edit.putString("cash", money);
                                edit.putString("pub_company", company);
                                edit.commit();

                            }
                        } catch (org.json.JSONException e) {
                            System.out.println();
                        }
                        tvMoney.setText("$" + money);
                        tvBalance.setText("$" + money);
                        if (company.equals(null) || company.equals("null")) {
                            tvName.setText("Welcome");
                        } else {
                            tvName.setText("Welcome " + company);
                        }
                        try {
                            JSONArray jArray = new JSONArray(httpRequest);
                            for (int i = 0; i < jArray.getJSONObject(1).length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(1);
                                Log.d("Array_length ", String.valueOf(jArray.length()));
                                Log.d("Obj_test_1 ", String.valueOf(jObj.length()));
                                Log.d("Obj_test_2 ", jObj.getString("promo_code"));
                                Log.d("Obj_test_3 ", jObj.getString("code_description"));
                                tvP2.setText(jObj.getString("promo_code"));
                                tvP4.setText(jObj.getString("code_description"));
                            }
                        } catch (org.json.JSONException e) {
                            System.out.println();
                        }
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

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


                if (preferences.getString("getting_started", "").equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LLRecent.setVisibility(View.VISIBLE);
                            LLSubmit.setVisibility(View.VISIBLE);
                            LLSeparate.setVisibility(View.VISIBLE);
                            LLStarted.setVisibility(View.GONE);
                            SharedPreferences preferences_b = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor edit = preferences_b.edit();
                            try {
                                JSONArray jArray = new JSONArray(httpRequest);
                                Log.d("TAG 1 ", "inside class");
                                //Log.d("json: ", httpRequest);
                                for (int i = 0; i < jArray.length(); i++) {

                                    Log.d("TAG 2 ", "inside class");
                                    JSONObject jObj = jArray.getJSONObject(i);
                                    Log.d("name other ", jObj.getString("interest_text"));

                                    if (jObj.getString("interest_value").equals("user_name")) {
                                        Log.d("TAG 3 ", "inside class");
                                        Log.d("jObj ", jObj.getString("interest_text"));
                                        Log.d("name ", jObj.getString("interest_value"));
                                        tvName.setText("Welcome " + jObj.getString("interest_text"));
                                    }
                                }
                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }

                            balance = preferences.getString("money", "");

                            try {
                                JSONArray jArray = new JSONArray(balance);
                                //Log.d("json: ", httpRequest);
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject jObj = jArray.getJSONObject(i);
                                    Log.d("money: ", jObj.getString("balance"));
                                    money = jObj.getString("balance");
                                    edit.remove("cash").commit();
                                    edit.putString("cash", money);
                                    edit.commit();
                                }
                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                            tvMoney.setText("$" + money);
                            tvBalance.setText("$" + money);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            LLStarted.setVisibility(View.VISIBLE);
                        }
                    });
                }

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("Result: ", result);
                if (result.contains("user not found")) {
                    Log.d("echo 2:  ", "user not found");
                } else {
                    Log.d("else JSON: ", result);
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_pub_id, token);
    }

    public void DataFunction(final String url) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                try {

                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();

                    durationKey = durationKey1 + params[0] + durationKey2;

                    //------------------>>
                    HttpGet httppost = new HttpGet(durationKey);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(httppost);

                    // StatusLine stat = response.getStatusLine();
                    int status = response.getStatusLine().getStatusCode();

                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        String data = EntityUtils.toString(entity);

                        JSONObject jObj = new JSONObject(data);

                        String tArray = jObj.getString("items");

                        JSONArray jArray = new JSONArray(tArray);
                        JSONObject wObj = jArray.getJSONObject(0);

                        JSONObject tObj = new JSONObject(wObj.getString("contentDetails"));
                        String duration = tObj.getString("duration");
                        try {
                            if (duration.contains("P") && duration.contains("T") && duration.contains("S")) {
                                duration = duration.replace("P", "").replace("T", "").replace("S", "");
                                Log.d("duration ", duration);
                            }
                            if (duration.contains("M")) {
                                String min = String.valueOf(duration.substring(0, 1));
                                String seconds = duration.replace(min, "").replace("M", "");

                                Log.d("min ", min);
                                Log.d("seconds ", seconds);

                                int total = (Integer.parseInt(min) * 60) + Integer.parseInt(seconds);
                                Log.d("totalTime ", String.valueOf(total));
                                duration = String.valueOf(total);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }

                        return duration;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("duration", result);
                edit.commit();
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(url);
    }

    public void OptInFunction(final View mView, final String raadz_pub_id, final String token, final String ad_id, final String cost) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", raadz_pub_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("ad_id", ad_id));
                nameValuePairs.add(new BasicNameValuePair("cost", cost));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(OptInListURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(OptInListURL);
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

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("OptIn ", result);
                if (result.contains("insufficient balance")) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String balance = preferences.getString("cash", "");

                    LinearLayout LLInsufficient = (LinearLayout) mView.findViewById(R.id.LLInsufficient);
                    TextView tvOptCost = (TextView) mView.findViewById(R.id.tvOptCost);
                    TextView tvInsufficientBalanceValue = (TextView) mView.findViewById(R.id.tvInsufficientBalanceValue);
                    Button bOptResend = (Button) mView.findViewById(R.id.bOptResend);

                    tvOptCost.setText("$.50");
                    tvInsufficientBalanceValue.setText("$" + balance);

                    bOptResend.setVisibility(View.GONE);
                    LLInsufficient.setVisibility(View.VISIBLE);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_pub_id, token, ad_id, cost);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        if (view == bDeposit) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(FragPubActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
            final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
            final EditText mName = (EditText) mView.findViewById(R.id.etName);
            final EditText mZip = (EditText) mView.findViewById(R.id.etZip);
            CardWidget = (CardMultilineWidget) mView.findViewById(R.id.CardWidget);

            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            });

            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAmount.getText().equals("")) {
                        Toast.makeText(FragPubActivity.this, "Enter a valid Amount", Toast.LENGTH_SHORT).show();
                    }
//                    else if (CardWidget.getCard().getNumber().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Card Number", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getExpMonth().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Expiration Month", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getExpYear().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid Expiration Year", Toast.LENGTH_SHORT).show();
//                    } else if (CardWidget.getCard().getCVC().equals("")) {
//                        Toast.makeText(FragPubActivity.this, "Enter a valid CVC Number", Toast.LENGTH_SHORT).show();
//                    }

                    else {
                        try {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            final Stripe stripe = new Stripe(getApplicationContext(),preferences.getString("stripe_public_key", ""));

                            //new Stripe(getApplicationContext()).setDefaultPublishableKey(StripeConfig.STRIPE_API_KEY);

                            final String tempName = mName.getText().toString();
                            final String tempZip = mZip.getText().toString();

                            //Card card = new Card(mNumber.getText().toString(), Integer.parseInt(month), Integer.parseInt(year), mCVC.getText().toString());
                            final Card card = new Card(CardWidget.getCard().getNumber(), CardWidget.getCard().getExpMonth(), CardWidget.getCard().getExpYear(), CardWidget.getCard().getCVC());

                            card.setName(tempName);
                            card.setAddressZip(tempZip);

                            Log.d("Number ", card.getNumber());
                            Log.d("Month ", String.valueOf(card.getExpMonth()));
                            Log.d("Year ", String.valueOf(card.getExpYear()));
                            Log.d("CVC ", card.getCVC());
                            Log.d("Name/Email ", tempName);
                            Log.d("Zip Code ", tempZip);

                            stripe.createToken(card, preferences.getString("stripe_public_key", ""), new TokenCallback() {

                                @Override
                                public void onError(Exception error) {
                                    Log.d("something ", "exception", error);
                                }

                                @Override
                                public void onSuccess(Token token) {
                                    Log.d("pointer 1", "1");
//                                    com.stripe.Stripe.apiKey = StripeConfig.getSecretKey();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    String s_email = preferences.getString("email", "");
                                    String s_pid = preferences.getString("raadz_pub_id", "");
                                    //Log.d("token ", token.getId());
                                    Log.d("amount ", mAmount.getText().toString());
                                    Log.d("email ", s_email);
                                    Log.d("pubID ", s_pid);
                                    Log.d("description ", "Deposit to account");

                                    Log.d("paymentCheck ", String.valueOf(paymentCheck));
                                    check = true;
                                    if (check == true) {
                                        int deposit = Integer.parseInt(mAmount.getText().toString());
                                        Log.d("Deposit Amount ", String.valueOf(deposit));
                                        if (deposit < 1000) {
                                            Log.d("Deposit Amount if 1 ", String.valueOf(deposit));
                                            Toast.makeText(FragPubActivity.this, "Minimum deposit amount is: $10.00", Toast.LENGTH_SHORT).show();
                                            mAmount.setText("");
                                        } else if (tempName.length() < 1) {
                                            Toast.makeText(FragPubActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                                        } else if (tempZip.length() < 1) {
                                            Toast.makeText(FragPubActivity.this, "Please enter your Zip", Toast.LENGTH_SHORT).show();
                                        } else if (deposit >= 1000) {
                                            StripeDetails(
                                                    token.getId(),
                                                    String.valueOf(deposit),
                                                    s_email, s_pid,
                                                    "something something",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "");
                                            Log.d("Deposit Amount if 2 ", String.valueOf(deposit));
                                            buttonPlaceholder = "bAddFunds";
                                            HashMap postData = new HashMap();
                                            postData.put("pub_id", s_pid);
                                            PostResponseAsyncTask task = new PostResponseAsyncTask(FragPubActivity.this, postData);
                                            task.execute(getPubInfoURL);
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                            Toast.makeText(FragPubActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(FragPubActivity.this, FragPubActivity.class);
                                            startActivity(in);
                                        }

                                    }
                                    //AdvertiserFunction(s_pid);

                                    /*
                                    try {
                                        final String tempToken = token.getId();
                                        final Map<String, Object> chargeParams = new HashMap<String, Object>();
                                        chargeParams.put("amount", Integer.parseInt(mAmount.getText().toString()));
                                        chargeParams.put("currency", "usd");
                                        chargeParams.put("source", token.getId());
                                        chargeParams.put("description", "Example charge");
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("pointer 2", "2");
                                                Charge charge = null;
                                                try {
                                                    charge = Charge.create(chargeParams);
                                                } catch (com.stripe.exception.APIConnectionException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.InvalidRequestException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.AuthenticationException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.APIException e) {
                                                    e.printStackTrace();
                                                } catch (com.stripe.exception.CardException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.d("pointer 3", "3");
                                                System.out.println("Charge Log :" + charge);
                                                Log.d("Paid amount ", String.valueOf(mAmount.getText().toString()));
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                String s_email = preferences.getString("email", "");
                                                String s_pid = preferences.getString("raadz_pub_id", "");
                                                Log.d("token ", tempToken);
                                                Log.d("amount ", mAmount.getText().toString());
                                                Log.d("email ", s_email);
                                                Log.d("pubID ", s_pid);
                                                Log.d("description ", "12");
                                                StripeDetails(tempToken, mAmount.getText().toString(), s_email, s_pid, "something something");
                                                //Toast.makeText(PubSubmitActivity.this, "Payment of " + String.valueOf(mAmount.getText().toString()) + " was successful!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                                            }
                                        }).start();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    */
                                }
                            });
                        } catch (Exception e) {
                            System.out.println(e);
                        }//End of catch
                    }//End of else
                }//End of onClick
            });//End of mEnter Button
        } else {
            buttonPlaceholder = "other";
            HashMap postData = new HashMap();
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(CityListURL);
        }
    }

    @Override
    public void processFinish(String s) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        if (buttonPlaceholder.equals("other")) {
            Log.d("FragPub cityList ", s);
            edit.putString("city_for_editing", s);
            edit.commit();
        }

        if (buttonPlaceholder.equals("bAddFunds")) {
            Log.d("result ", s);
            info = s;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor = preferences.edit();
                    balance = info;
                    Log.d("balance: ", balance);
                    try {
                        JSONArray jArray = new JSONArray(balance);
                        //Log.d("json: ", httpRequest);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            Log.d("money: ", jObj.getString("balance"));
                            editor.putString("cash", jObj.getString("balance"));
                            editor.commit();
                            money = jObj.getString("balance");
                        }
                    } catch (org.json.JSONException e) {
                        System.out.println();
                    }
                    String ok = preferences.getString("cash", "");
                    //Log.d("runUIThread ", money);
                    Log.d("Updated balance ", String.valueOf(money));
                    Log.d("Preference balance ", preferences.getString("cash", ""));
                    tvMoney.setText("$" + ok);
                    tvBalance.setText("$" + ok);
                    Log.d("tvMoney ", tvMoney.getText().toString());
                }
            });
        }
        if (buttonPlaceholder.equals("mShare")) {
            Log.d("Send Email ", s);
            if (s.equals("invalid post data")) {
                Toast.makeText(this, "Could not send result to email at this time.", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("invalid post data")) {
                Toast.makeText(this, "Could not send result to email at this time.", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("invalid advertiser info")) {
                Toast.makeText(this, "Could not send result to email at this time.", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("ad not found")) {
                Toast.makeText(this, "Ad not found.", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("success")) {
                Toast.makeText(this, "Results sent!", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("please retry later")) {
                Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show();
            }
        }
        if (buttonPlaceholder.equals("mOptResend")) {
            Log.d("mOptResend ", s);
            if(s.contains("success")){
                Toast.makeText(this, "Opt-In list has been successfully emailed!", Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("invalid data")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("invalid cost")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("internal error. please try again later.")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("ad not found")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("insufficient balance")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("Publisher not found")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
        if (buttonPlaceholder.equals("mOptBuy")) {
            Log.d("mOptBuy ", s);
            if(s.contains("success")){
                Toast.makeText(this, "Opt-In list has been successfully emailed!", Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("invalid data")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("invalid cost")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("internal error. please try again later.")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("ad not found")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("insufficient balance")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
            else if (s.contains("Publisher not found")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        } else {

            result2 = s;
            Log.d("postValue ", preferences.getString("postSend2", ""));
            Log.d("post result  ", s);
            edit.putString("FR2", s);
            edit.commit();
            if (result2.contains("no recently completed ads")) {
                tvNone.setVisibility(View.VISIBLE);
            } else {
                //LLHistoryHeader.addView(layout_h);
                Log.d("2 ", "second if");
                tvNone.setVisibility(View.GONE);
                Log.d("try this 1 ", preferences.getString("FR1", ""));
                Log.d("try this 2 ", result2);
                edit.putString("pub_history", result2);
                edit.commit();
                LLHistoryHeader.removeAllViews();
                LinearLayout layout_g = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_header, null);
                LLHistoryHeader.addView(layout_g);
                LLHistory.removeAllViews();
//                runOnUiThread(new Runnable() {
//                    public void run() {
                try {
                    JSONArray jArray = new JSONArray(result2);
                    Log.d("jArray ", String.valueOf(jArray.length()));
                    for (int i = 0; i < 5; i++) {
                        final JSONObject jObj = jArray.getJSONArray(3).getJSONObject(i);

                        DataFunction(jObj.getString("ad_ref"));

                        Log.d("valuei ", String.valueOf(i));
                        Log.d("jObj2 ", String.valueOf(jObj.length()));
                        Log.d("ad id ", jObj.getString("raadz_ad_id"));
                        Log.d("ad title ", jObj.getString("ad_title"));
                        Log.d("ref ", jObj.getString("ad_ref"));
                        Log.d("company ", jObj.getString("company_name"));
                        Log.d("views ", jObj.getString("total_views"));
                        Log.d("cost ", jObj.getString("ad_cost"));
                        Log.d("duration ", preferences.getString("duration", ""));

                        alt = i;

                        final HashMap<String, String> adResults = new HashMap<String, String>();
                        final HashMap<String, String> resubResults = new HashMap<String, String>();

                        adResults.clear();
                        resubResults.clear();

                        resubResults.put("type", jObj.getString("ad_type"));
                        resubResults.put("adid", jObj.getString("raadz_ad_id"));
                        resubResults.put("company", jObj.getString("company_name"));
                        resubResults.put("title", jObj.getString("ad_title"));
                        resubResults.put("link", jObj.getString("ad_link"));
                        resubResults.put("ref", jObj.getString("ad_ref"));

                        resubResults.put("gender", jObj.getString("gender"));
                        resubResults.put("age", jObj.getString("age"));
                        resubResults.put("location", jObj.getString("location"));
                        resubResults.put("interests", jObj.getString("interests"));
                        resubResults.put("questions", jObj.getString("questions_text"));
                        resubResults.put("questions_value", jObj.getString("questions"));

                        resubResults.put("views", jObj.getString("total_views"));
                        resubResults.put("cost", jObj.getString("ad_cost"));
                        resubResults.put("promo", jObj.getString("promo_code"));

                        adResults.put("type", jObj.getString("ad_type"));
                        adResults.put("adid", jObj.getString("raadz_ad_id"));
                        adResults.put("ref", jObj.getString("ad_ref"));
                        adResults.put("company", jObj.getString("company_name"));
                        adResults.put("title", jObj.getString("ad_title"));
                        adResults.put("link", jObj.getString("ad_link"));
                        adResults.put("cost", jObj.getString("ad_cost"));
                        adResults.put("t_views", jObj.getString("total_views"));
                        adResults.put("c_views", jObj.getString("current_views"));
                        adResults.put("status", jObj.getString("update_time"));
                        adResults.put("gender", jObj.getString("gender"));
                        adResults.put("age", jObj.getString("age"));
                        adResults.put("location", jObj.getString("location"));
                        adResults.put("interests", jObj.getString("interests"));
                        adResults.put("optbought", jObj.getString("bought_optin"));
                        adResults.put("optcount", jObj.getString("optin_count"));
                        adResults.put("questions", jObj.getString("questions_text"));
                        adResults.put("questions_value", jObj.getString("questions"));
                        adResults.put("q1", jObj.getString("quest1"));
                        adResults.put("q2", jObj.getString("quest2"));
                        adResults.put("q3", jObj.getString("quest3"));
                        adResults.put("q4", jObj.getString("quest4"));
                        adResults.put("overall", jObj.getString("overall"));
                        adResults.put("date", jObj.getString("update_time"));

                        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_completed, null);
                        LinearLayout LLMain = (LinearLayout) layout.findViewById(R.id.LLMain);
                        TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                        TextView tvt1 = (TextView) layout.findViewById(R.id.tvTLUser);
                        TextView tvt2 = (TextView) layout.findViewById(R.id.tvCityOther);
                        TextView tvt3 = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                        TextView tvt4 = (TextView) layout.findViewById(R.id.tvCompletedHistOther);

                        tvNum.setVisibility(View.GONE);

                        Log.d("1 ", adResults.get("q1"));
                        Log.d("2 ", adResults.get("q2"));
                        Log.d("3 ", adResults.get("q3"));
                        Log.d("4 ", adResults.get("q4"));
                        Log.d("4andTest ", jObj.getString("questions"));
                        Log.d("overall ", adResults.get("overall"));
                        Log.d("date ", adResults.get("date"));

                        LinearLayout LLButtons = (LinearLayout) layout.findViewById(R.id.LLButtons);
                        Button bResults = (Button) layout.findViewById(R.id.bResults);
                        Button bResubmit = (Button) layout.findViewById(R.id.bResubmit);

                        LLButtons.setVisibility(View.VISIBLE);

                        bResults.setId(i);
                        bResubmit.setId(i);

                        LLMain.setId(i);
                        tvt2.setId(i);
                        tvt3.setId(i);
                        tvt4.setId(i);

                        LLMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resultsDialog(adResults);
                            }
                        });

                        tvt2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resultsDialog(adResults);
                            }
                        });

                        tvt3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resultsDialog(adResults);
                            }
                        });

                        tvt4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resultsDialog(adResults);
                            }
                        });


                        LLHistory.addView(layout);

                        tvNum.setText((String.valueOf(i + 1)) + ")");
                        tvt1.setText(jObj.getString("company_name"));
                        tvt2.setText(jObj.getString("ad_title"));
                        tvt3.setText(jObj.getString("total_views"));
                        if(adResults.get("type").equals("1")){
                            tvt4.setText("Video");
                        }
                        if(adResults.get("type").equals("2")){
                            tvt4.setText("Image");
                        }
                        if(adResults.get("type").equals("3")){
                            tvt4.setText("Audio");
                        }
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }

//                    }
//                });
            }
        }
    }


    public void testMethod() {
        DecimalFormat d1 = new DecimalFormat("###.#");
        DecimalFormat d2 = new DecimalFormat("##.#");
        DecimalFormat d3 = new DecimalFormat("#.#");
        DecimalFormat d4 = new DecimalFormat("#.##");
        DecimalFormat d5 = new DecimalFormat("#.###");
        DecimalFormat d6 = new DecimalFormat("##.#");
        DecimalFormat d7 = new DecimalFormat("##.##");

        DecimalFormat d8 = new DecimalFormat("##.###");
        DecimalFormat d9 = new DecimalFormat("##.####");
        DecimalFormat d10 = new DecimalFormat("###.#");

        Log.d("d1 ", String.valueOf(d1.format(12345.12345)));
        Log.d("d2 ", String.valueOf(d2.format(12345.12345)));
        Log.d("d3 ", String.valueOf(d3.format(12345.12345)));
        Log.d("d4 ", String.valueOf(d4.format(12345.12345)));
        Log.d("d5 ", String.valueOf(d5.format(12345.12345)));
        Log.d("d6 ", String.valueOf(d6.format(12345.12345)));
        Log.d("d7 ", String.valueOf(d7.format(12345.12345)));
        Log.d("d8 ", String.valueOf(d8.format(12345.12345)));
        Log.d("d9 ", String.valueOf(d9.format(12345.12345)));
        Log.d("d10 ", String.valueOf(d10.format(12345.12345)));
    }

}
