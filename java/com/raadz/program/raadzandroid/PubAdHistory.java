package com.raadz.program.raadzandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.y;
import static android.R.id.edit;
import static android.R.id.list;
import static com.raadz.program.raadzandroid.R.id.LL2;
import static com.raadz.program.raadzandroid.R.id.LLCompleted;
import static com.raadz.program.raadzandroid.R.id.LLInterestCopy;
import static com.raadz.program.raadzandroid.R.id.LLInterestCopy2;
import static com.raadz.program.raadzandroid.R.id.LLMain;
import static com.raadz.program.raadzandroid.R.id.LLRecent;
import static com.raadz.program.raadzandroid.R.id.LLSeparate;
import static com.raadz.program.raadzandroid.R.id.LLStarted;
import static com.raadz.program.raadzandroid.R.id.LLSubmit;
import static com.raadz.program.raadzandroid.R.id.bOptResend;
import static com.raadz.program.raadzandroid.R.id.bResub;
import static com.raadz.program.raadzandroid.R.id.bResults;
import static com.raadz.program.raadzandroid.R.id.cbL1;
import static com.raadz.program.raadzandroid.R.id.etCompany;
import static com.raadz.program.raadzandroid.R.id.etVideoID;
import static com.raadz.program.raadzandroid.R.id.etVideoInfo;
import static com.raadz.program.raadzandroid.R.id.etVideoTitle;
import static com.raadz.program.raadzandroid.R.id.ivImage;
import static com.raadz.program.raadzandroid.R.id.sLocation;
import static com.raadz.program.raadzandroid.R.id.tvAge;
import static com.raadz.program.raadzandroid.R.id.tvBalance;
import static com.raadz.program.raadzandroid.R.id.tvPViews;
import static com.raadz.program.raadzandroid.R.id.tvQ1;
import static com.raadz.program.raadzandroid.R.id.tvQ2;
import static com.raadz.program.raadzandroid.R.id.tvQ3;
import static com.raadz.program.raadzandroid.R.id.tvQ4;
import static com.raadz.program.raadzandroid.R.id.tvStateOverall;
import static com.raadz.program.raadzandroid.R.id.tvStatus;
import static com.raadz.program.raadzandroid.R.id.tvTLNumber;
import static com.raadz.program.raadzandroid.R.id.tvTotal;
import static com.raadz.program.raadzandroid.R.id.view;

public class PubAdHistory extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {

    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    NavigationView navigationView = null;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    View vAlt;

    final MediaPlayer mp = new MediaPlayer();

    AlertDialog ads_dialog;

    ImageButton ibProfile;

    final HashMap<String, String> newResults = new HashMap<String, String>();

    LinearLayout LLHistory;
    LinearLayout LLHistoryHeader;
    LinearLayout LLHistoryActive;
    LinearLayout LLHistoryHeaderActive;
    LinearLayout LLHistoryCompleted;
    LinearLayout LLHistoryHeaderCompleted;
    LinearLayout LLCompletedAds;
    LinearLayout LL1;
    LinearLayout LL2;

    Spinner sCompletedAds;

    Button bEditActiveAds;

    TextView tvMoney;
    TextView tvNone;
    TextView tvNoPending;
    TextView tvNoActive;
    TextView tvNoCompleted;
    TextView tvNoAds;

    String getHistoryURL = "https://raadz.com/getPubAds.php";
    String ActiveAdsURL = "https://raadz.com/getActiveAds.php";
    String CityListURL = "https://raadz.com/getCityList.php";
    String InterestsListURL = "https://raadz.com/getInterestList.php";
    String UpdateActiveAdsURL = "https://raadz.com/updateActiveAd.php";
    String EmailResultsURL = "https://raadz.com/emailAdDetails.php";
    String OptInListURL = "https://raadz.com/buyCompletedOptinList.php";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String durationKey1 = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=";
    String durationKey2 = "&key=AIzaSyApjGaDZhoCC_SbLGzcDrZKARX2EGElG20";
    String durationKey;
    String colorPrimary = "#FFCECECE";
    String colorSecondary = "#FFCECECE";
    String[] s_ageArray = new String[5];
    String gGender = "";
    String gLocation = "";
    String httpRequest;
    String buttonPlaceholder;
    String result;
    String result2;
    String balance;
    String money;
    String color;
    String gAdID = "";
    String bait = "";
    String bothTime;
    String age_t;
    String defaultSelection;

    int currentTime;
    int totalTime;
    int ok;
    int gAge = 0;
    int i;
    int alt;
    int adComplete;
    int adNumber;
    int interestsNumber;
    int interestsSelected;

    long interestsCounter;
    long interestsCounterBackup;

    int[] ageArray = new int[5];
    int[] valueArray = new int[32];

    long[] interests_array_temp = new long[54];
    long[] interests_array = new long[54];
    long[] interests_number_array = new long[54];
    long[] product_array = new long[54];
    long[] final_product_array = new long[54];

    Boolean bLocation;
    boolean checkaudio = false;
    boolean adEdit = false;
    boolean layout_track = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_ad_history);

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

        LLHistory = (LinearLayout) findViewById(R.id.LLHistory);
        LLHistoryHeader = (LinearLayout) findViewById(R.id.LLHistoryHeader);
        LLHistoryActive = (LinearLayout) findViewById(R.id.LLHistoryActive);
        LLHistoryHeaderActive = (LinearLayout) findViewById(R.id.LLHistoryHeaderActive);
        LLHistoryCompleted = (LinearLayout) findViewById(R.id.LLHistoryCompleted);
        LLHistoryHeaderCompleted = (LinearLayout) findViewById(R.id.LLHistoryHeaderCompleted);
        LLCompletedAds = (LinearLayout) findViewById(R.id.LLCompleted);

        sCompletedAds = (Spinner) findViewById(R.id.sCompletedAds);

        bEditActiveAds = (Button) findViewById(R.id.bEditActiveAds);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvNone = (TextView) findViewById(R.id.tvNone);
        tvNoPending = (TextView) findViewById(R.id.tvNoPending);
        tvNoActive = (TextView) findViewById(R.id.tvNoActive);
        tvNoCompleted = (TextView) findViewById(R.id.tvNoCompleted);
        tvNoAds = (TextView) findViewById(R.id.tvNoAds);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("cash", "");

        tvMoney.setText("$" + balance);
        result = preferences.getString("result", "");

        arrayValue();

        AdvertiserFunction(preferences.getString("raadz_pub_id", ""));

        buttonPlaceholder = "other";
        onClick(vAlt);

        SendDataToServer(preferences.getString("raadz_pub_id", ""), preferences.getString("token", ""), 0);

        List<String> list = new ArrayList<String>();
        list.add("Most Recent");
        list.add("Best Overall");
        list.add("Funniest");
        list.add("Most Emotional");
        list.add("Most Effective");
        list.add("Attention-Grabbing");
        list.add("Best Explaination");
        list.add("Repeat Effectiveness");
        list.add("Viral Ability");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position2 = adapter2.getPosition("Most Recent");
        Log.d("position ", String.valueOf("Most Recent"));
        sCompletedAds.setAdapter(adapter2);
        sCompletedAds.setSelection(position2);
        adNumber = 3;
        Log.d("item 1 ", adapter2.getItem(0));
        Log.d("item 2 ", adapter2.getItem(1));
        Log.d("item 3 ", adapter2.getItem(2));
        Log.d("item 4 ", adapter2.getItem(3));
        Log.d("item 5 ", adapter2.getItem(4));
        Log.d("item 6 ", adapter2.getItem(5));
        Log.d("item 7 ", adapter2.getItem(6));
        Log.d("item 8 ", adapter2.getItem(7));
        Log.d("item 9 ", adapter2.getItem(8));

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PubAdHistory.this, PubProfileActivity.class);
                startActivity(in);
            }
        });

        sCompletedAds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defaultSelection = sCompletedAds.getSelectedItem().toString();
                Log.d("Item_Selected ", defaultSelection);

                if (defaultSelection.equals("Most Recent")) {
                    adNumber = 2;
                } else if (defaultSelection.equals("Best Overall")) {
                    adNumber = 3;
                } else if (defaultSelection.equals("Funniest")) {
                    adNumber = 4;
                } else if (defaultSelection.equals("Most Emotional")) {
                    adNumber = 5;
                } else if (defaultSelection.equals("Most Effective")) {
                    adNumber = 6;
                } else if (defaultSelection.equals("Attention-Grabbing")) {
                    adNumber = 7;
                } else if (defaultSelection.equals("Best Explaination")) {
                    adNumber = 8;
                } else if (defaultSelection.equals("Repeat Effectiveness")) {
                    adNumber = 9;
                } else if (defaultSelection.equals("Viral Ability")) {
                    adNumber = 10;
                }

                Log.d("SpinnerItem ", defaultSelection);
                Log.d("SpinnerCount ", String.valueOf(adNumber));

                SendDataToServer(preferences.getString("raadz_pub_id", ""), preferences.getString("token", ""), 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void arrayValue() {
        long t_value = (long) Math.pow(2, 53);
        int t2_temp = 0;
        while (t_value > 1) {
            Log.d("TAG_F ", String.valueOf(t2_temp) + " - " + String.valueOf(t_value));
            interests_array_temp[t2_temp] = t_value;
            t_value = t_value / 2;
            t2_temp++;
        }
        if (t_value >= 1) {
            Log.d("TAG_F ", String.valueOf(t2_temp) + " - " + String.valueOf(t_value));
            interests_array_temp[t2_temp] = t_value;
            t_value = t_value - 1;
        }

        int l_temp = interests_array.length - 1;

        for (int i = 0; i < interests_array_temp.length; i++) {
            Log.d("l_temp ", String.valueOf(l_temp));
            Log.d("i ", String.valueOf(i));
            interests_array[i] = interests_array_temp[l_temp];
            if (l_temp >= 1) {
                l_temp--;
            }
        }

        for (int i = 0; i < interests_array.length; i++) {
            Log.d("array_values ", String.valueOf(i) + " - " + String.valueOf(interests_array[i]));
        }
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

    public void setGender(View mView, String gender) {
        RadioButton rb1 = (RadioButton) mView.findViewById(R.id.rbMale);
        RadioButton rb2 = (RadioButton) mView.findViewById(R.id.rbFemale);
        RadioButton rb3 = (RadioButton) mView.findViewById(R.id.rbNo);

        if (gender.equals("M")) {
            rb1.setChecked(true);
            gGender = "M";
        } else if (gender.equals("F")) {
            rb2.setChecked(true);
            gGender = "F";
        } else if (gender.equals("N")) {
            rb3.setChecked(true);
            gGender = "N";
        }
    }

    public void getGender(View mView) {
        RadioGroup rg = (RadioGroup) mView.findViewById(R.id.rgGender);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbMale:
                        gGender = "M";
                        Log.d("gGender ", gGender);
                        break;
                    case R.id.rbFemale:
                        gGender = "F";
                        Log.d("gGender ", gGender);
                        break;
                    case R.id.rbNo:
                        gGender = "N";
                        Log.d("gGender ", gGender);
                        break;
                }
            }
        });
    }

    public void setAge(View mView) {
        CheckBox cbL1 = (CheckBox) mView.findViewById(R.id.cbL1);
        CheckBox cbL2 = (CheckBox) mView.findViewById(R.id.cbL2);
        CheckBox cbL3 = (CheckBox) mView.findViewById(R.id.cbL3);
        CheckBox cbL4 = (CheckBox) mView.findViewById(R.id.cbL4);
        CheckBox cbL5 = (CheckBox) mView.findViewById(R.id.cbL5);
        Log.d("ageArray[0] ", String.valueOf(ageArray[0]));
        Log.d("ageArray[1] ", String.valueOf(ageArray[1]));
        Log.d("ageArray[2] ", String.valueOf(ageArray[2]));
        Log.d("ageArray[3] ", String.valueOf(ageArray[3]));
        Log.d("ageArray[4] ", String.valueOf(ageArray[4]));
        Log.d("setAge Value ", String.valueOf(gAge));
        if (ageArray[0] != 0) {
            cbL1.setChecked(true);
            gAge = gAge + 1;
        }
        if (ageArray[1] != 0) {
            cbL2.setChecked(true);
            gAge = gAge + 2;
        }
        if (ageArray[2] != 0) {
            cbL3.setChecked(true);
            gAge = gAge + 4;
        }
        if (ageArray[3] != 0) {
            cbL4.setChecked(true);
            gAge = gAge + 8;
        }
        if (ageArray[4] != 0) {
            cbL5.setChecked(true);
            gAge = gAge + 16;
        }
    }

    public void getAge(View mView) {
        final CheckBox cbL1 = (CheckBox) mView.findViewById(R.id.cbL1);
        final CheckBox cbL2 = (CheckBox) mView.findViewById(R.id.cbL2);
        final CheckBox cbL3 = (CheckBox) mView.findViewById(R.id.cbL3);
        final CheckBox cbL4 = (CheckBox) mView.findViewById(R.id.cbL4);
        final CheckBox cbL5 = (CheckBox) mView.findViewById(R.id.cbL5);

        cbL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbL1.isChecked()) {
                    gAge = gAge + 1;
                    Log.d("Age Value ", String.valueOf(gAge));
                } else {
                    gAge = gAge - 1;
                    Log.d("Age Value ", String.valueOf(gAge));
                }
            }
        });

        cbL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbL2.isChecked()) {
                    gAge = gAge + 2;
                    Log.d("Age Value ", String.valueOf(gAge));
                } else {
                    gAge = gAge - 2;
                    Log.d("Age Value ", String.valueOf(gAge));
                }
            }
        });

        cbL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbL3.isChecked()) {
                    gAge = gAge + 4;
                    Log.d("Age Value ", String.valueOf(gAge));
                } else {
                    gAge = gAge - 4;
                    Log.d("Age Value ", String.valueOf(gAge));
                }
            }
        });

        cbL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbL4.isChecked()) {
                    gAge = gAge + 8;
                    Log.d("Age Value ", String.valueOf(gAge));
                } else {
                    gAge = gAge - 8;
                    Log.d("Age Value ", String.valueOf(gAge));
                }
            }
        });

        cbL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbL5.isChecked()) {
                    gAge = gAge + 16;
                    Log.d("Age Value ", String.valueOf(gAge));
                } else {
                    gAge = gAge - 16;
                    Log.d("Age Value ", String.valueOf(gAge));
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        mp.stop();
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
//        try {
//            mp.reset();
//            mp.setDataSource(path + fileName);
//            mp.prepare();
//            mp.start();
//
//            Log.d("file ", fileName);
//
//            audioTrack(mView);
//
//        } catch (Exception e) {
//            System.out.println();
//        }

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkaudio == false) {
                    checkaudio = true;
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
                    mp.start();
                    audioTrack(mView);
                } else if (checkaudio == true) {
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

    public void adDelay() {
        final ProgressDialog dialog = new ProgressDialog(PubAdHistory.this, R.style.AppCompatAlertDialogStyle);
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
                dialog.dismiss();
            }

        }.start();
    }

    public void totalAge() {
        gAge = 0;
        Log.d("gAge1/2 ", String.valueOf(gAge));
        for (int i = 0; i < ageArray.length; i++) {
            Log.d("-", "-");
            Log.d("ageArray" + String.valueOf(i), String.valueOf(ageArray[i]));
            Log.d("gAge ", String.valueOf(gAge));
            gAge = gAge + ageArray[i];
            Log.d("-", "-");
        }
        Log.d("gAge2/2 ", String.valueOf(gAge));
    }

    public void setLocation(View mView, final HashMap<String, String> adResults) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String location = preferences.getString("city_for_editing", "");

        Log.d("one ", "1");
        Log.d("setLocation city ", location);
        String kept;
        String remainder;
        List<String> list = new ArrayList<String>();
        list.add("No");

        final Spinner spinCity = (Spinner) mView.findViewById(R.id.spinLocation);

        if (adResults.get("location").equals("No")) {
            bLocation = true;
            gLocation = adResults.get("location");
        }

        if (bLocation = true) {
            for (int j = 0; j < location.length(); j++) {
                try {
                    kept = location.substring(0, location.indexOf(":"));
                    remainder = location.substring(location.indexOf(":") + 1, location.length());
                    location = remainder;
                    list.add(kept);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PubAdHistory.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    int position = adapter.getPosition(adResults.get("location"));
                    spinCity.setAdapter(adapter);
                    spinCity.setSelection(position);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }

            }
        } else if (bLocation = false) {
            location = adResults.get("location");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PubAdHistory.this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int position = adapter.getPosition(location);
            Log.d("position ", String.valueOf(location));
            Log.d("position String ", location);
            spinCity.setAdapter(adapter);
            spinCity.setSelection(adapter.getPosition(gLocation));
        }
        Log.d("locationList ", location);
        gLocation = location;


        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gLocation = spinCity.getSelectedItem().toString();
                Log.d("Current Location ", gLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setInterests(View mView, final HashMap<String, String> adResults) {
        buttonPlaceholder = "other2";
        onClick(vAlt);
    }

    public void getActiveInterests(View mView) {
        LL1 = (LinearLayout) mView.findViewById(R.id.LLInterests);
        LL2 = (LinearLayout) mView.findViewById(R.id.LLInterests2);
        LL1.removeAllViews();
        LL2.removeAllViews();
        interestsCounterBackup = interestsCounter;
        for (int j = 0; j < interests_array_temp.length; j++) {
            Log.d("active_i_temp ", String.valueOf(interests_array_temp[j]));
        }
        for (int j = 0; j < interests_array.length; j++) {
            if (interestsCounter >= interests_array_temp[j]) {
                Log.d(" - ", " - ");
                Log.d("j ", String.valueOf(j));
                Log.d("the_counter ", String.valueOf(interestsCounter));
                Log.d("the_value ", String.valueOf((int) interests_array_temp[j]));
                product_array[j] = interests_array_temp[j];
                interestsCounter = interestsCounter - interests_array_temp[j];
                Log.d("interestsCounter ", String.valueOf(interestsCounter));
                Log.d(" - ", " - ");
            }
        }

        for (int s = 0; s < product_array.length ; s++) {
            Log.d("product_array ", String.valueOf(product_array[s]));
        }

//        for (int y = 0; y < product_array.length; y++) {
//            if(product_array[y] != 0){
//                interestsCounter = interestsCounter + (int)product_array[y];
//            }
//        }

        interestsCounter = interestsCounterBackup;

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String i_result = preferences.getString("i_interest", "");

            JSONArray jArray = new JSONArray(i_result);

            for (int a_i = 0; a_i < jArray.length(); a_i++) {
                JSONObject jObj = jArray.getJSONObject(a_i);
                try {
                    Log.d("a_i ", jObj.getString("interest_value"));
                    interests_number_array[a_i] = Long.parseLong(jObj.getString("interest_value"));
                } catch (NumberFormatException e) {

                }
            }

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObj2 = jArray.getJSONObject(i);
                final CheckBox cb = new CheckBox(this);
                if (jObj2.getString("interest_text").equals("null")) {
                    Log.d("in the if ", jObj2.getString("interest_text"));
                } else {

                    cb.setText(jObj2.getString("interest_text"));
                    cb.setId(i);

                    if (layout_track == false) {
                        LL1.addView(cb);
                        ok = jArray.length();
                        layout_track = true;
                    } else if (layout_track == true) {
                        layout_track = false;
                        LL2.addView(cb);
                        ok = jArray.length();
                    }
                }
                for (int j = 0; j < product_array.length; j++) {
                    for (int k = 0; k < interests_number_array.length; k++) {
                        if (interests_number_array[k] == product_array[j]) {
                            int id = cb.getId();
                            if (id == k) {
                                cb.setChecked(true);
                                Log.d("check_id ", String.valueOf(id));
                            }
                        }
                    }
                }


                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                        int s = (Integer.parseInt(String.valueOf(buttonView.getId()))) + 1;
                        Log.d("ok ", String.valueOf(ok));
                        Log.d("string s ", String.valueOf(s));
                        Log.d("interests ", String.valueOf(interestsCounter));
                        Log.d("isChecked ", String.valueOf(cb.isChecked()));
                        if (s >= 1) {
                            if (b == true) {
//                                Log.d("array_value ", String.valueOf(interests_array[s - 1]));
                                interestsCounter = interestsCounter + interests_number_array[buttonView.getId()];
                                Log.d("interestCounter ", String.valueOf(interestsCounter));
                            } else {
                                interestsCounter = interestsCounter - interests_number_array[buttonView.getId()];
                                Log.d("interestCounter ", String.valueOf(interestsCounter));

                            }
                        }
                    }
                });

            }
        } catch (org.json.JSONException e) {
            System.out.println(e);
        }


    }

    public void totalInterests() {
        interestsCounter = 0;
        for (int i = 0; i < valueArray.length; i++) {
            Log.d("interestsArray" + String.valueOf(i), String.valueOf(valueArray[i]));
            Log.d("interestsCouter ", String.valueOf(interestsCounter));
            interestsCounter = interestsCounter + valueArray[i];

        }
    }

    public void matchAd(String ad_id, String result, View mView) {
        Log.d("matchAd", "insideMatchAd");
        try {
            JSONArray jArray = new JSONArray(result);
            Log.d("jArray ", String.valueOf(jArray.length()));

            for (int i = 0; i < jArray.getJSONArray(1).length(); i++) {
                final JSONObject jObj = jArray.getJSONArray(1).getJSONObject(i);

                if (ad_id == jObj.getString("raadz_ad_id")) {

                    adEdit = true;

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    Log.d("GenderTest ", jObj.getString("gender"));

                    newResults.put("age", jObj.getString("age"));
                    newResults.put("c_views", jObj.getString("current_views"));
                    newResults.put("gender", jObj.getString("gender"));
                    newResults.put("interests", jObj.getString("interests"));
                    newResults.put("location", jObj.getString("location"));
                    newResults.put("questions", jObj.getString("questions"));
                    newResults.put("questions_text", jObj.getString("questions_text"));

                    setGender(mView, newResults.get("gender"));
                    TextView gender = (TextView) mView.findViewById(R.id.tvGender);
                    gender.setText(newResults.get("gender"));

                    ageValue(Integer.parseInt(newResults.get("age")));
                    setAge(mView);
                    TextView age = (TextView) mView.findViewById(tvAge);
                    age.setText(newResults.get("age"));

                    setLocation(mView, newResults);
                    TextView location = (TextView) mView.findViewById(R.id.tvLocation);
                    location.setText(newResults.get("location"));

                    getValue(Integer.parseInt(newResults.get("interests")));
                    setInterests(mView, newResults);
                    TextView interests = (TextView) mView.findViewById(R.id.tvInterests);
                    interests.setText(newResults.get("interests"));

                }

            }

        } catch (org.json.JSONException e) {
            System.out.println();
        }

    }

    public int pendingDialog(final HashMap<String, String> adResults) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAdHistory.this);
        final View pView = getLayoutInflater().inflate(R.layout.completed_pending_info_dialog, null);
        Button mDone = (Button) pView.findViewById(R.id.bDone);

        TextView tvCompany = (TextView) pView.findViewById(R.id.tvCompany);
        TextView tvAd = (TextView) pView.findViewById(R.id.tvAd);
        TextView tvLink = (TextView) pView.findViewById(R.id.tvLink);
        TextView tvCost = (TextView) pView.findViewById(R.id.tvCost);
        TextView tvViews = (TextView) pView.findViewById(R.id.tvViews);
        TextView tvStatus = (TextView) pView.findViewById(R.id.tvStatus);

        TextView tvGender = (TextView) pView.findViewById(R.id.tvGender);
        TextView tvAge = (TextView) pView.findViewById(R.id.tvAge);
        TextView tvLocation = (TextView) pView.findViewById(R.id.tvLocation);
        TextView tvInterests = (TextView) pView.findViewById(R.id.tvInterests);

        TextView tvState1 = (TextView) pView.findViewById(R.id.tvState1);
        TextView tvState2 = (TextView) pView.findViewById(R.id.tvState2);
        TextView tvState3 = (TextView) pView.findViewById(R.id.tvState3);
        TextView tvState4 = (TextView) pView.findViewById(R.id.tvState4);

        LinearLayout mAudioLayout = pView.findViewById(R.id.LLAudio);
        ImageView ivImage = pView.findViewById(R.id.ivImage);

        final TextView mProgress = pView.findViewById(R.id.tvProgress);
        TextView mStatus = pView.findViewById(R.id.tvStatus);

        mStatus.setText("Pending");

        YouTubePlayerView yt = (YouTubePlayerView) pView.findViewById(R.id.newYTPlayer);

        Button mVideo = pView.findViewById(R.id.bVideo);
        Button mImage = pView.findViewById(R.id.bBanner);
        Button mAudio = pView.findViewById(R.id.bAudio);
        final Button mPlay = pView.findViewById(R.id.bPlay);
        final Button mPause = pView.findViewById(R.id.bPause);

        mBuilder.setView(pView);
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

            Log.d("pendingDialog ", adResults.get("ref"));

            mAudioLayout.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                public void run() {
                    audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, pView);
                }
            });
        }

        tvCompany.setText(adResults.get("company"));
        tvAd.setText(adResults.get("title"));
        tvCost.setText("$" + adResults.get("cost"));
        tvViews.setText("0" + " / " + adResults.get("t_views"));
        tvStatus.setText("Pending");

        SpannableString content1 = new SpannableString(adResults.get("link"));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvLink.setText(content1);

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

            age_t = "";
            for (int j = 0; j < ageArray.length; j++) {

                if (ageArray[j] == 1) {
                    age_t = "< 18,";
                }
                if (ageArray[j] == 2) {
                    age_t = age_t + "18-25";
                }
                if (ageArray[j] == 4) {
                    age_t = age_t + "26-40,";
                }
                if (ageArray[j] == 8) {
                    age_t = age_t + "41-60,";
                }
                if (ageArray[j] == 16) {
                    age_t = age_t + "61+,";
                }
                Log.d("age_t ", String.valueOf(age_t));
            }
            tvAge.setText(age_t);
        }

        if (adResults.get("location").equals("No")) {
            tvLocation.setText("Any");
        } else {
            tvLocation.setText(adResults.get("location"));
        }

        getValue(Integer.parseInt(adResults.get("interests")));
        if (interestsSelected == 0) {
            tvInterests.setText("Any");
        } else {
            tvInterests.setText(String.valueOf(interestsSelected) + " Interests Selected");
        }

        String quests = adResults.get("questions_text");
        String split[] = quests.split(":", 5);

        Log.d("q ", split[0]);
        Log.d("q ", split[1]);
        Log.d("q ", split[2]);
        Log.d("q ", split[3]);

        tvState1.setText(split[0]);
        tvState2.setText(split[1]);
        tvState3.setText(split[2]);
        tvState4.setText(split[3]);

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = adResults.get("link");
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("http://" + url));
                startActivity(in);
            }
        });

//        mBuilder.setView(pView);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearArray();
                interestsSelected = 0;
                mp.stop();
                dialog.dismiss();
            }
        });

        return 0;
    }

    public int activeDialog(final HashMap<String, String> adResults) {

        Log.d(" - ", " - ");
        Log.d("adEdit ", String.valueOf(adEdit));
        Log.d("interests ", adResults.get("interests"));
        Log.d("age ", adResults.get("age"));
        Log.d(" - ", " - ");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAdHistory.this);
        final View mView = getLayoutInflater().inflate(R.layout.completed_active_info_dialog, null);
        Button mDone = (Button) mView.findViewById(R.id.bDone);

        TextView tvCompany = (TextView) mView.findViewById(R.id.tvCompany);
        TextView tvAd = (TextView) mView.findViewById(R.id.tvAd);
        TextView tvLink = (TextView) mView.findViewById(R.id.tvLink);
        TextView tvCost = (TextView) mView.findViewById(R.id.tvCost);
        TextView tvViews = (TextView) mView.findViewById(R.id.tvViews);
        TextView tvStatus = (TextView) mView.findViewById(R.id.tvStatus);

        TextView tvGender = (TextView) mView.findViewById(R.id.tvGender);
        TextView tvAge = (TextView) mView.findViewById(R.id.tvAge);
        TextView tvLocation = (TextView) mView.findViewById(R.id.tvLocation);
        TextView tvInterests = (TextView) mView.findViewById(R.id.tvInterests);

        TextView tvState1 = (TextView) mView.findViewById(R.id.tvState1);
        TextView tvState2 = (TextView) mView.findViewById(R.id.tvState2);
        TextView tvState3 = (TextView) mView.findViewById(R.id.tvState3);
        TextView tvState4 = (TextView) mView.findViewById(R.id.tvState4);

        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);
        ImageView ivImage = mView.findViewById(R.id.ivImage);

        final TextView mProgress = mView.findViewById(R.id.tvProgress);
        TextView mStatus = mView.findViewById(R.id.tvStatus);

        mStatus.setText("Active");

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        Button mVideo = mView.findViewById(R.id.bVideo);
        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        final Button mPlay = mView.findViewById(R.id.bPlay);
        final Button mPause = mView.findViewById(R.id.bPause);

        mBuilder.setView(mView);
        ads_dialog = mBuilder.create();
        ads_dialog.show();

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

            Log.d("activeDialog ", adResults.get("ref"));

            mAudioLayout.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                public void run() {
                    audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, mView);
                }
            });
//            audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, mView);
        }

        Log.d("NewAgeTest ", adResults.get("age"));

        tvCompany.setText(adResults.get("company"));
        tvAd.setText(adResults.get("title"));
        tvCost.setText("$" + adResults.get("cost"));
        tvViews.setText(adResults.get("c_views") + " / " + adResults.get("t_views"));
        tvStatus.setText("Active");

        SpannableString content1 = new SpannableString(adResults.get("link"));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvLink.setText(content1);

        Log.d("NewGender ", adResults.get("gender"));


        if (adResults.get("gender").contains("N")) {
            tvGender.setText("Any");
        } else if (adResults.get("gender").contains("M")) {
            tvGender.setText("Male");
        } else if (adResults.get("gender").contains("F")) {
            tvGender.setText("Female");
        } else {
            tvGender.setText(adResults.get("gender"));
        }
//            tvAge.setText(age_t);

        if (adResults.get("location").equals("No")) {
            tvLocation.setText("Any");
        } else {
            tvLocation.setText(adResults.get("location"));
        }


        Log.d("AfterAgeValue ", String.valueOf(adResults.get("age")));

        String quests = adResults.get("questions_text");
        String split[] = quests.split(":", 5);

        Log.d("q ", split[0]);
        Log.d("q ", split[1]);
        Log.d("q ", split[2]);
        Log.d("q ", split[3]);

        tvState1.setText(split[0]);
        tvState2.setText(split[1]);
        tvState3.setText(split[2]);
        tvState4.setText(split[3]);

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = adResults.get("link");
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("http://" + url));
                startActivity(in);
            }
        });

        int age_set = Integer.parseInt(adResults.get("age"));
        ageValue(age_set);

        Log.d("NewAgeValue ", String.valueOf(age_set));

        clearArray();

        final Button bEdit = (Button) mView.findViewById(R.id.bEdit);
        Button bSave = (Button) mView.findViewById(R.id.bSave);
        Button bCancel = (Button) mView.findViewById(R.id.bCancel);
        final LinearLayout LLEditAd = (LinearLayout) mView.findViewById(R.id.LLEditAd);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setInterests(mView, adResults);

        Log.d("interests_test ", adResults.get("interests"));

        Log.d("wwwCounter ", String.valueOf(interestsCounter));
        try {
            interestsCounter = Long.parseLong(adResults.get("interests"));
        }catch (NumberFormatException e){
            System.out.println(e);
        }

        String age_value = adResults.get("age");
        ageValue(Integer.parseInt(age_value));
        setGender(mView, adResults.get("gender"));
        setLocation(mView, adResults);
        getActiveInterests(mView);

        age_t = "";
        for (int j = 0; j < ageArray.length; j++) {

            if (ageArray[j] == 1) {
                age_t = "< 18,";
            }
            if (ageArray[j] == 2) {
                age_t = age_t + "18-25,";
            }
            if (ageArray[j] == 4) {
                age_t = age_t + "26-40,";
            }
            if (ageArray[j] == 8) {
                age_t = age_t + "41-60,";
            }
            if (ageArray[j] == 16) {
                age_t = age_t + "61+,";
            }
            Log.d("age_t ", String.valueOf(age_t));
            tvAge.setText(age_t);
        }

        if (interestsSelected == 0) {
            tvInterests.setText("Any");
        } else {
            tvInterests.setText(String.valueOf(interestsSelected) + " Interests Selected");
        }

        gAdID = adResults.get("adid");

        setAge(mView);

        getAge(mView);
        totalAge();

        getGender(mView);

//        mBuilder.setView(mView);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LLEditAd.setVisibility(View.GONE);
                bEdit.setVisibility(View.VISIBLE);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("-", "-");
                Log.d("Final Gender ", String.valueOf(gGender));
                Log.d("Final Age ", String.valueOf(gAge));
                Log.d("Final Interests ", String.valueOf(interestsCounter));
                Log.d("Final Location ", String.valueOf(gLocation));

                Log.d("ageArray[0] ", String.valueOf(ageArray[0]));
                Log.d("ageArray[1] ", String.valueOf(ageArray[1]));
                Log.d("ageArray[2] ", String.valueOf(ageArray[2]));
                Log.d("ageArray[3] ", String.valueOf(ageArray[3]));
                Log.d("ageArray[4] ", String.valueOf(ageArray[4]));

                Log.d("valueArray[0] ", String.valueOf(valueArray[0]));
                Log.d("valueArray[1] ", String.valueOf(valueArray[1]));
                Log.d("valueArray[2] ", String.valueOf(valueArray[2]));
                Log.d("valueArray[3] ", String.valueOf(valueArray[3]));
                Log.d("valueArray[4] ", String.valueOf(valueArray[4]));
                Log.d("valueArray[0] ", String.valueOf(valueArray[5]));
                Log.d("valueArray[1] ", String.valueOf(valueArray[6]));
                Log.d("valueArray[2] ", String.valueOf(valueArray[7]));
                Log.d("valueArray[3] ", String.valueOf(valueArray[8]));
                Log.d("valueArray[4] ", String.valueOf(valueArray[9]));
                Log.d("valueArray[0] ", String.valueOf(valueArray[10]));
                Log.d("valueArray[1] ", String.valueOf(valueArray[11]));
                Log.d("valueArray[2] ", String.valueOf(valueArray[12]));
                Log.d("valueArray[3] ", String.valueOf(valueArray[13]));
                Log.d("valueArray[4] ", String.valueOf(valueArray[14]));
                Log.d("valueArray[4] ", String.valueOf(valueArray[15]));
                Log.d("-", "-");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Log.d("gender ", gGender);
                Log.d("age ", String.valueOf(gAge));
                Log.d("location ", gLocation);
                Log.d("interests ", String.valueOf(interestsCounter));

                SaveFunction(
                        preferences.getString("raadz_pub_id", ""),
                        preferences.getString("token", ""),
                        gAdID,
                        gGender,
                        String.valueOf(gAge),
                        gLocation,
                        String.valueOf(interestsCounter)

                );

                AdvertiserFunctionUpdate(mView, adResults.get("pub_id"), adResults.get("adid"));

                LLEditAd.setVisibility(View.GONE);
                bEdit.setVisibility(View.VISIBLE);

                matchAd(adResults.get("adid"), preferences.getString("edit_active", ""), mView);
                Toast.makeText(PubAdHistory.this, "Ad Details Saved!", Toast.LENGTH_SHORT).show();
                ads_dialog.dismiss();

                SendDataToServer(preferences.getString("raadz_pub_id", ""), preferences.getString("token", ""), 0);

            }
        });


        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LLEditAd.setVisibility(View.VISIBLE);
                bEdit.setVisibility(View.GONE);
            }
        });


//        mBuilder.setView(mView);
//        dialog.show();


        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArray();
                interestsSelected = 0;
                mp.stop();
                ads_dialog.dismiss();
                return;
            }
        });


        return 0;
    }

    public int resultsDialog(final HashMap<String, String> adResults) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("r1 ", adResults.get("q1"));
        Log.d("r2 ", adResults.get("q2"));
        Log.d("r3 ", adResults.get("q3"));
        Log.d("r4 ", adResults.get("q4"));
        Log.d("roverall ", adResults.get("overall"));
        Log.d("rdate ", adResults.get("date"));

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAdHistory.this);
        final View mView = getLayoutInflater().inflate(R.layout.completed_ad_info_dialog, null);
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

        TextView tvRemoved = (TextView) mView.findViewById(R.id.tvRemoved);

        LinearLayout mAudioLayout = mView.findViewById(R.id.LLAudio);
        ImageView ivImage = mView.findViewById(R.id.ivImage);

        final TextView mProgress = mView.findViewById(R.id.tvProgress);
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

        if (opt_bought.equals("0") && opt_count.equals("0")) {
            tvBoughtOptValue.setText("NO");
            tvUsersOptValue.setText(opt_count);
        }
        if (opt_bought.equals("0") && !opt_count.equals("0")) {
            tvBoughtOptValue.setText("YES");
            tvUsersOptValue.setText(opt_count);
            tvPurchase.setText("$" + balance);
            LLPurchase.setVisibility(View.VISIBLE);
            mOptBuy.setVisibility(View.VISIBLE);
        }
        if (opt_bought.equals("1") && opt_count.equals("0")) {
            //Never happens
        }
        if (opt_bought.equals("1") && !opt_count.equals("0")) {
            tvBoughtOptValue.setText("YES");
            tvUsersOptValue.setText(opt_count);
            mOptResend.setVisibility(View.VISIBLE);
        }

        if (d_balance < .50) {
            tvOptCost.setText("$.50");
            tvInsufficientBalanceValue.setText("$" + balance);
            mOptResend.setVisibility(View.GONE);
            LLInsufficient.setVisibility(View.VISIBLE);
        }

        Button mImage = mView.findViewById(R.id.bBanner);
        Button mAudio = mView.findViewById(R.id.bAudio);
        Button mVideo = mView.findViewById(R.id.bVideo);
        final Button mPlay = mView.findViewById(R.id.bPlay);
        final Button mPause = mView.findViewById(R.id.bPause);

        YouTubePlayerView yt = (YouTubePlayerView) mView.findViewById(R.id.newYTPlayer);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        final String t_ref = String.valueOf(adResults.get("ref"));

        Log.d("t_ref ", t_ref);

        if (t_ref != "null") {

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
                runOnUiThread(new Runnable() {
                    public void run() {
                        audioPlayer(azureAudio, adResults.get("ref"), mPlay, mPause, mProgress, mView);
                    }
                });
            }
        } else {
            tvRemoved.setVisibility(View.VISIBLE);
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
        tvCost.setText("$" + adResults.get("cost"));
        tvViews.setText(adResults.get("c_views") + " / " + adResults.get("t_views"));
        tvStatus.setText("Completed");

        SpannableString content1 = new SpannableString(adResults.get("link"));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvLink.setText(content1);

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

            age_t = "";
            for (int j = 0; j < ageArray.length; j++) {

                if (ageArray[j] == 1) {
                    age_t = "< 18,";
                }
                if (ageArray[j] == 2) {
                    age_t = age_t + "18-25";
                }
                if (ageArray[j] == 4) {
                    age_t = age_t + "26-40,";
                }
                if (ageArray[j] == 8) {
                    age_t = age_t + "41-60,";
                }
                if (ageArray[j] == 16) {
                    age_t = age_t + "61+,";
                }
                Log.d("age_t ", String.valueOf(age_t));
            }
            tvAge.setText(age_t);
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
                mOptResend.setVisibility(View.GONE);
                buttonPlaceholder = "mOptResend";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("cost", adResults.get("cost"));
                PostResponseAsyncTask task = new PostResponseAsyncTask(PubAdHistory.this, postData);
                task.execute(OptInListURL);
            }
        });

        mOptBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Opt_in_resend ", "do we get here ");
                mOptResend.setVisibility(View.GONE);
                buttonPlaceholder = "mOptBuy";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("cost", adResults.get("cost"));
                PostResponseAsyncTask task = new PostResponseAsyncTask(PubAdHistory.this, postData);
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
                Intent in = new Intent(PubAdHistory.this, PubSubmitActivity.class);
                startActivity(in);
            }
        });


//        mBuilder.setView(mView);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPlaceholder = "mShare";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                HashMap postData = new HashMap();
                postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("ad_id", adResults.get("adid"));
                postData.put("email", mEmail.getText().toString());
                PostResponseAsyncTask task = new PostResponseAsyncTask(PubAdHistory.this, postData);
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

    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, FragPubActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.  Dont need to use this comment section anymore
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
                        Intent in = new Intent(PubAdHistory.this, IndexActivity.class);
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
        } else if (id == R.id.adv_home) {
            Intent in = new Intent(this, FragPubActivity.class);
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
                    Intent in = new Intent(PubAdHistory.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAdHistory.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAdHistory.this);
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
        if (buttonPlaceholder.equals("other")) {
            HashMap postData = new HashMap();
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(CityListURL);
        }

        if (buttonPlaceholder.equals("other2")) {
            HashMap postData = new HashMap();
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(InterestsListURL);
        }
    }

    @Override
    public void processFinish(String s) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        if (buttonPlaceholder.equals("other")) {
            Log.d("FragPub cityList ", s);
            edit.putString("city_for_editing", s);
            edit.commit();
        }
        if (buttonPlaceholder.equals("other2")) {
            Log.d("InterestList ", s);
            edit.putString("i_interest", s);
            edit.commit();
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
            if (s.contains("success")) {
                Toast.makeText(this, "Opt-In list has been successfully emailed!", Toast.LENGTH_SHORT).show();
            } else if (s.contains("invalid data")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("invalid cost")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("internal error. please try again later.")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("ad not found")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("insufficient balance")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("Publisher not found")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
        if (buttonPlaceholder.equals("mOptBuy")) {
            Log.d("mOptBuy ", s);
            if (s.contains("success")) {
                Toast.makeText(this, "Opt-In list has been successfully emailed!", Toast.LENGTH_SHORT).show();
            } else if (s.contains("invalid data")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("invalid cost")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("internal error. please try again later.")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("ad not found")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("insufficient balance")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } else if (s.contains("Publisher not found")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }


        result2 = preferences.getString("UAHResult", "");
        Log.d("result2 ", result2);

        if (result2.contains("no completed ads")) {
            Log.d("1 ", "first if");
            tvNone.setVisibility(View.VISIBLE);
        }
    }

    public void SendDataToServer(final String RID, final String RT, final int adValue) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();
                result2 = "";

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                Log.d("pub id is what ", RID);
                nameValuePairs.add(new BasicNameValuePair("pub_id", RID));
                nameValuePairs.add(new BasicNameValuePair("token", preferences.getString("token", "")));
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
                    adComplete = adValue;

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (adComplete == 0) {
                    result2 = result;
                    Log.d("result ", result);
                    edit.putString("UAHResult", result);
                    edit.commit();
                } else if (adComplete == 1) {
                    result2 = result;
                    edit.putString("UAHResult", result);
                    edit.commit();
                    Log.d("RELAUNCH ", result2);
                }
//                runOnUiThread(new Runnable() {
//                    public void run() {
                result2 = preferences.getString("UAHResult", "");
                Log.d("result2 ", result2);


                if (result2.contains("no completed ads")) {
                    Log.d("1 ", "first if");
                    tvNone.setVisibility(View.VISIBLE);
                } else {
                    Log.d("2 ", "second if");
                    tvNone.setVisibility(View.GONE);
                    Log.d("try this 1 ", preferences.getString("FR1", ""));
                    Log.d("try this 2 ", result2);
                    edit.putString("pub_history", result2);
                    edit.commit();

                    if (adComplete == 0) {

                        if (result2.contains("no pending ads")) {
                            tvNoPending.setVisibility(View.VISIBLE);
                            tvNoPending.setText("No Pending Ads");
                        } else {
                            tvNoPending.setVisibility(View.GONE);
                            tvNoPending.setText("");
                            LLHistoryHeader.removeAllViews();
                            LinearLayout layout_g = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_header, null);
                            LLHistoryHeader.addView(layout_g);
                            LLHistory.removeAllViews();
                            try {
                                JSONArray jArray = new JSONArray(result2);
                                Log.d("jArray ", String.valueOf(jArray.length()));
                                Log.d("history result ", result2);

                                for (int i = 0; i < jArray.getJSONArray(0).length(); i++) {
                                    JSONObject jObj = jArray.getJSONArray(0).getJSONObject(i);
                                    Log.d("jObj2 ", String.valueOf(jObj.length()));
                                    Log.d("ad id ", jObj.getString("raadz_ad_id"));
                                    Log.d("ad title ", jObj.getString("ad_title"));
                                    Log.d("company ", jObj.getString("company_name"));
                                    Log.d("views ", jObj.getString("total_views"));
                                    Log.d("cost ", jObj.getString("ad_cost"));
                                }

                                for (int i = 0; i < jArray.getJSONArray(0).length(); i++) {
                                    final JSONObject jObj = jArray.getJSONArray(0).getJSONObject(i);

                                    Log.d("jObj1 ", String.valueOf(jObj.length()));
                                    Log.d("ad id ", jObj.getString("raadz_ad_id"));
                                    Log.d("ad title ", jObj.getString("ad_title"));
                                    Log.d("company ", jObj.getString("company_name"));
                                    Log.d("views ", jObj.getString("total_views"));
                                    Log.d("cost ", jObj.getString("ad_cost"));

                                    final HashMap<String, String> adResults = new HashMap<String, String>();
                                    final HashMap<String, String> resubResults = new HashMap<String, String>();

                                    adResults.put("cost", jObj.getString("ad_cost"));
                                    adResults.put("link", jObj.getString("ad_link"));
                                    adResults.put("ref", jObj.getString("ad_ref"));
                                    adResults.put("title", jObj.getString("ad_title"));
                                    adResults.put("type", jObj.getString("ad_type"));
                                    adResults.put("age", jObj.getString("age"));
                                    adResults.put("company", jObj.getString("company_name"));
                                    adResults.put("duration", jObj.getString("duration"));
                                    adResults.put("gender", jObj.getString("gender"));
                                    adResults.put("interests", jObj.getString("interests"));
                                    adResults.put("location", jObj.getString("location"));
                                    adResults.put("questions", jObj.getString("questions"));
                                    adResults.put("questions_text", jObj.getString("questions_text"));
                                    adResults.put("adid", jObj.getString("raadz_ad_id"));
                                    adResults.put("pub_id", jObj.getString("raadz_pub_id"));
                                    adResults.put("t_views", jObj.getString("total_views"));

                                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history, null);
                                    LinearLayout LLMain = (LinearLayout) layout.findViewById(R.id.LLMain);
                                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                                    TextView tvt1 = (TextView) layout.findViewById(R.id.tvTLUser);
                                    TextView tvt2 = (TextView) layout.findViewById(R.id.tvCityOther);
                                    TextView tvt3 = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                                    TextView tvt4 = (TextView) layout.findViewById(R.id.tvCompletedHistOther);

                                    tvNum.setVisibility(View.GONE);

                                    Log.d("ad_id ", jObj.getString("raadz_ad_id"));
                                    Log.d("ad_title ", jObj.getString("ad_title"));
                                    Log.d("company_name ", jObj.getString("company_name"));
                                    Log.d("total_views ", jObj.getString("total_views"));
                                    Log.d("overall ", jObj.getString("ad_cost"));

                                    LLMain.setId(i);
                                    tvt2.setId(i);
                                    tvt3.setId(i);
                                    tvt4.setId(i);

                                    LLMain.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            adDelay();
                                            pendingDialog(adResults);
                                        }
                                    });

                                    tvt2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            adDelay();
                                            pendingDialog(adResults);
                                        }
                                    });

                                    tvt3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            adDelay();
                                            pendingDialog(adResults);
                                        }
                                    });

                                    tvt4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            adDelay();
                                            pendingDialog(adResults);
                                        }
                                    });

                                    LLHistory.addView(layout);

                                    tvNum.setText((String.valueOf(i + 1)) + ")");
                                    tvt1.setText(jObj.getString("company_name"));
                                    tvt2.setText(jObj.getString("ad_title"));
                                    tvt3.setText(jObj.getString("total_views"));
                                    if (adResults.get("type").equals("1")) {
                                        tvt4.setText("Video");
                                    }
                                    if (adResults.get("type").equals("2")) {
                                        tvt4.setText("Image");
                                    }
                                    if (adResults.get("type").equals("3")) {
                                        tvt4.setText("Audio");
                                    }
                                }

                                Log.d("", "");

                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                        }

                        if (result2.contains("no active ads")) {
                            tvNoActive.setVisibility(View.VISIBLE);
                            tvNoActive.setText("No Active Ads");
                        } else {
                            tvNoActive.setVisibility(View.GONE);
                            tvNoActive.setText("");
                            tvNone.setVisibility(View.GONE);
                            LLHistoryHeaderActive.removeAllViews();
                            LinearLayout layout_f = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_active_header, null);
                            LLHistoryHeaderActive.addView(layout_f);
                            LLHistoryActive.removeAllViews();
                            try {
                                JSONArray jArray = new JSONArray(result2);
                                Log.d("jArray ", String.valueOf(jArray.length()));

                                for (int i = 0; i < jArray.getJSONArray(1).length(); i++) {
                                    final JSONObject jObj = jArray.getJSONArray(1).getJSONObject(i);

//                                Log.d("jObj2 ", String.valueOf(jObj.length()));
//                                Log.d("ad id ", jObj.getString("raadz_ad_id"));
//                                Log.d("ad title ", jObj.getString("ad_title"));
//                                Log.d("company ", jObj.getString("company_name"));
//                                Log.d("views ", jObj.getString("total_views"));
//                                Log.d("cost ", jObj.getString("ad_cost"));


                                    final HashMap<String, String> adResults = new HashMap<String, String>();
                                    final HashMap<String, String> resubResults = new HashMap<String, String>();

                                    adResults.put("cost", jObj.getString("ad_cost"));
                                    adResults.put("link", jObj.getString("ad_link"));
                                    adResults.put("ref", jObj.getString("ad_ref"));
                                    adResults.put("title", jObj.getString("ad_title"));
                                    adResults.put("type", jObj.getString("ad_type"));
                                    adResults.put("age", jObj.getString("age"));
                                    adResults.put("company", jObj.getString("company_name"));
                                    adResults.put("c_views", jObj.getString("current_views"));
                                    adResults.put("gender", jObj.getString("gender"));
                                    adResults.put("interests", jObj.getString("interests"));
                                    adResults.put("location", jObj.getString("location"));
                                    adResults.put("questions", jObj.getString("questions"));
                                    adResults.put("questions_text", jObj.getString("questions_text"));
                                    adResults.put("adid", jObj.getString("raadz_ad_id"));
                                    adResults.put("pub_id", jObj.getString("raadz_pub_id"));
                                    adResults.put("t_views", jObj.getString("total_views"));

                                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_active, null);
                                    LinearLayout LLMain = (LinearLayout) layout.findViewById(R.id.LLMain);
                                    TextView tvNum = (TextView) layout.findViewById(R.id.tvTLNumber);
                                    TextView tvt1 = (TextView) layout.findViewById(R.id.tvTLUser);
                                    TextView tvt2 = (TextView) layout.findViewById(R.id.tvCityOther);
                                    TextView tvt3 = (TextView) layout.findViewById(R.id.tvEarningsHistOther);
                                    TextView tvt4 = (TextView) layout.findViewById(R.id.tvCompletedHistOther);

                                    tvNum.setVisibility(View.GONE);

//                                Log.d("ad_id ", jObj.getString("raadz_ad_id"));
//                                Log.d("ad_title ", jObj.getString("ad_title"));
//                                Log.d("company_name ", jObj.getString("company_name"));
//                                Log.d("total_views ", jObj.getString("total_views"));
//                                Log.d("overall ", jObj.getString("ad_cost"));

                                    LLMain.setId(i);
                                    tvt2.setId(i);
                                    tvt3.setId(i);
                                    tvt4.setId(i);

                                    LLMain.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            activeDialog(adResults);
                                        }
                                    });

                                    tvt2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            activeDialog(adResults);
                                        }
                                    });

                                    tvt3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            activeDialog(adResults);
                                        }
                                    });

                                    tvt4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            activeDialog(adResults);
                                        }
                                    });

                                    LLHistoryActive.addView(layout);

                                    tvNum.setText((String.valueOf(i + 1)) + ")");
                                    tvt1.setText(jObj.getString("company_name"));
                                    tvt2.setText(jObj.getString("ad_title"));
                                    tvt3.setText(jObj.getString("current_views") + "/" + jObj.getString("total_views"));
                                    if (adResults.get("type").equals("1")) {
                                        tvt4.setText("Video");
                                    }
                                    if (adResults.get("type").equals("2")) {
                                        tvt4.setText("Image");
                                    }
                                    if (adResults.get("type").equals("3")) {
                                        tvt4.setText("Audio");
                                    }

                                }

                                Log.d("", "");

                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                        }

                    }

                    if (adComplete == 0 || adComplete == 1) {

                        if (result2.contains("no recently completed ads")) {
                            tvNoCompleted.setVisibility(View.VISIBLE);
                            tvNoCompleted.setText("No Recently Completed Ads");
                        } else {
                            tvNoCompleted.setVisibility(View.GONE);
                            tvNoCompleted.setText("");
                            tvNone.setVisibility(View.GONE);
                            LLHistoryHeaderCompleted.removeAllViews();
                            LinearLayout layout_f = (LinearLayout) getLayoutInflater().inflate(R.layout.pub_history_completed_header, null);
                            LLHistoryHeaderCompleted.addView(layout_f);
                            LLHistoryCompleted.removeAllViews();

                            char c1 = '[';
                            char c2 = '"';
                            char c3 = '"';
                            char c4 = ']';
                            String s = new StringBuilder().append(c1).append(c2).append(c3).append(c4).toString();
                            Log.d("NewAppendedString ", s);

                            try {
                                JSONArray jArray = new JSONArray(result2);
                                Log.d("EmptyFinal ", String.valueOf(adNumber));
                                Log.d("EmptyFinal ", String.valueOf(defaultSelection));
                                Log.d("jArray ", String.valueOf(jArray.getJSONArray(adNumber).length()));
                                Log.d("jArray2 ", String.valueOf(jArray.getString(adNumber).length()));
                                int t_length = jArray.getString(adNumber).length();
                                Log.d("t_length ", String.valueOf(t_length));

                                if (t_length == 4) {
                                    Log.d("TAG1 ", "in the first if");
                                    tvNoAds.setVisibility(View.VISIBLE);
                                    LLHistoryHeaderCompleted.setVisibility(View.GONE);
                                    LLHistoryCompleted.setVisibility(View.GONE);
                                } else {
                                    Log.d("TAG2 ", "in the second if");
                                    tvNoAds.setVisibility(View.GONE);
                                    LLHistoryHeaderCompleted.setVisibility(View.VISIBLE);
                                    LLHistoryCompleted.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < jArray.getJSONArray(adNumber).length(); i++) {
                                        final JSONObject jObj = jArray.getJSONArray(adNumber).getJSONObject(i);

                                        Log.d("ArrayEmpty2 ", jArray.getString(2));
                                        Log.d("ArrayEmpty3 ", jArray.getString(3));
                                        Log.d("ArrayEmpty4 ", jArray.getString(4));
                                        Log.d("ArrayEmpty5 ", jArray.getString(5));
                                        Log.d("ArrayEmpty6 ", jArray.getString(6));

                                        Log.d("EmptyTest1 ", String.valueOf(jArray.getString(1).length()));
                                        Log.d("EmptyTest2 ", String.valueOf(jArray.getString(2).length()));
                                        Log.d("EmptyTest3 ", String.valueOf(jArray.getString(3).length()));
                                        Log.d("EmptyTest4 ", String.valueOf(jArray.getString(4).length()));
                                        Log.d("EmptyTest5 ", String.valueOf(jArray.getString(5).length()));
                                        Log.d("EmptyTest6 ", String.valueOf(jArray.getString(6).length()));
                                        Log.d("EmptyTest7 ", String.valueOf(jArray.getString(7).length()));
                                        Log.d("EmptyTest_L ", String.valueOf(jArray.getString(adNumber).length()));
                                        Log.d("EmptyFinal_N ", String.valueOf(adNumber));
                                        Log.d("EmptyFinal_S ", String.valueOf(defaultSelection));


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

                                        LLHistoryCompleted.addView(layout);

                                        tvNum.setText((String.valueOf(i + 1)) + ")");
                                        tvt1.setText(jObj.getString("company_name"));
                                        tvt2.setText(jObj.getString("ad_title"));
                                        tvt3.setText(jObj.getString("current_views") + "/" + jObj.getString("total_views"));
                                        tvt4.setText("$" + jObj.getString("ad_cost"));

                                        tvNum.setText((String.valueOf(i + 1)) + ")");
                                        tvt1.setText(jObj.getString("company_name"));
                                        tvt2.setText(jObj.getString("ad_title"));
                                        tvt3.setText(jObj.getString("total_views"));
                                        if (adResults.get("type").equals("1")) {
                                            tvt4.setText("Video");
                                        }
                                        if (adResults.get("type").equals("2")) {
                                            tvt4.setText("Image");
                                        }
                                        if (adResults.get("type").equals("3")) {
                                            tvt4.setText("Audio");
                                        }
                                    }
                                }

                                Log.d("", "");

                            } catch (org.json.JSONException e) {
                                System.out.println();
                            }
                        }

                    }

                }
//                    }
//                });

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(RID, RT);
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

    public void AdvertiserFunction(final String raadz_user_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ActiveAdsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(ActiveAdsURL);
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
                Log.d("ActiveAds ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (!result.equals("")) {
                    Log.d("Result ", result);
                    edit.putString("edit_active", result);
                    edit.commit();
                } else if (result.equals("no active ads")) {
                    edit.putString("edit_active", result);
                    edit.commit();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id);
    }

    public void AdvertiserFunctionUpdate(final View mView, final String raadz_user_id, final String ad_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ActiveAdsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(ActiveAdsURL);
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
                Log.d("ActiveAds ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (!result.equals("")) {
                    Log.d("Result ", result);
                    edit.putString("edit_active", result);
                    edit.commit();
                } else if (result.equals("no active ads")) {
                    edit.putString("edit_active", result);
                    edit.commit();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, ad_id);
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

    public void SaveFunction(final String raadz_user_id, final String token, final String ad_id, final String gender, final String age, final String location, final String interests) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("pud_id ", raadz_user_id);
                        Log.d("token ", token);
                        Log.d("ad id ", ad_id);
                        Log.d("gender ", gender);
                        Log.d("age ", age);
                        Log.d("location ", location);
                        Log.d("interests ", interests);
                    }
                });

                nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("ad_id", ad_id));
                nameValuePairs.add(new BasicNameValuePair("gender", gender));
                nameValuePairs.add(new BasicNameValuePair("ageVal", age));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("interests", interests));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(UpdateActiveAdsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(UpdateActiveAdsURL);
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
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.contains("successfully")) {
                    Toast.makeText(PubAdHistory.this, "Ad Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                Log.d("Save Result ", result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token, ad_id, gender, age, location, interests);
    }


}
