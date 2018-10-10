package com.raadz.program.raadzandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wseemann.media.FFmpegMediaMetadataRetriever;

import static android.R.attr.description;
import static android.R.attr.duration;
import static android.R.attr.min;
import static android.R.attr.preferenceCategoryStyle;
import static android.R.attr.width;
import static android.R.id.edit;
import static android.R.id.list;
import static android.os.Build.VERSION_CODES.N;
import static com.raadz.program.raadzandroid.R.id.CardWidget;
import static com.raadz.program.raadzandroid.R.id.LLLeaderboards;
import static com.raadz.program.raadzandroid.R.id.LLMain;
import static com.raadz.program.raadzandroid.R.id.adv_home;
import static com.raadz.program.raadzandroid.R.id.imageView;
import static com.raadz.program.raadzandroid.R.id.ivBanner;
import static com.raadz.program.raadzandroid.R.id.newYTPlayer;
import static com.raadz.program.raadzandroid.R.id.tvPQuestion;
import static com.raadz.program.raadzandroid.R.id.view;
import static java.lang.Double.parseDouble;

public class PubSubmitActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMS_REQUEST_CODE = 123;

    NavigationView navigationView = null;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;
    Uri videoUri;
    CardMultilineWidget CardWidget;


    ProgressDialog pd;
    ProgressDialog dialog;

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayerView NewYouTubePlayerView;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    List<String> allMatches = new ArrayList<String>();

    ImageView ivImage;

    Spinner spinnerDefault;

    RadioGroup rgGender;
    RadioGroup rgAge;
    RadioGroup rgLocation;

    RadioButton rbMale;
    RadioButton rbFemale;
    RadioButton rbAge1;
    RadioButton rbAge2;
    RadioButton rbAge3;
    RadioButton rbAge4;
    RadioButton rbAge5;
    RadioButton rbLoc1;
    RadioButton rbLoc2;
    RadioButton rbLoc3;

    CheckBox cbL1;
    CheckBox cbL2;
    CheckBox cbL3;
    CheckBox cbL4;
    CheckBox cbL5;

    CheckBox cbQ1;
    CheckBox cbQ2;
    CheckBox cbQ3;
    CheckBox cbQ4;
    CheckBox cbQ5;
    CheckBox cbQ6;

    CheckBox cbI1;
    CheckBox cbI2;
    CheckBox cbI3;
    CheckBox cbI4;
    CheckBox cbI5;
    CheckBox cbI6;
    CheckBox cbI7;
    CheckBox cbI8;
    CheckBox cbI9;
    CheckBox cbI10;
    CheckBox cbI11;
    CheckBox cbI12;
    CheckBox cbI13;
    CheckBox cbI14;
    CheckBox cbI15;
    CheckBox cbI16;

    CheckBox cbOpt;

    LinearLayout LLEnd;
    LinearLayout LLchoosead;
    LinearLayout LLyoutube;
    LinearLayout LLbanner;
    LinearLayout LLaudio;
    LinearLayout LLCheckCopy;
    LinearLayout LLInterestCopy;
    LinearLayout LLInterestCopy2;
    LinearLayout LLAgeCheckBoxes;
    LinearLayout LLlocationitems;
    LinearLayout LLInterestsCheckBoxes;
    LinearLayout LLstep1;
    LinearLayout LLstep2;
    LinearLayout LLstep3;
    LinearLayout LLstep4;
    LinearLayout LLstep5;
    LinearLayout LLopt;
    LinearLayout LL1;
    LinearLayout LL2;
    LinearLayout LL3;
    LinearLayout LL4;
    LinearLayout LL5;
    LinearLayout LL6;
    LinearLayout LLPreview;

    TextView tvComingSoon;
    TextView tvMax;
    TextView tvTotal;
    TextView tvDurationPrompt;
    TextView tvAltText;
    TextView tvOneOrTheOther;
    TextView tvLargeFile;
    TextView tvPickAd;
    TextView tvMoney;
    TextView tvTitle;
    TextView tvPromoDisplay;
    TextView tvPPubID;
    TextView tvPCompany;
    TextView tvPTitle;
    TextView tvPLink;
    TextView tvPYTID;
    TextView tvPGender;
    TextView tvPAge;
    TextView tvPLocation;
    TextView tvPQuestion;
    TextView tvPInterests;
    TextView tvPViews;
    TextView tvPCost;
    TextView tvPPromo;
    TextView tvPBalance;
    TextView tvOpt;


    EditText etCompany;
    EditText etVideoTitle;
    EditText etVideoInfo;
    EditText etVideoID;
    EditText etPromo;

    Button bDifferentYT;
    Button bDifferentB;
    Button bDifferentA;
    Button bYoutube;
    Button bBanner;
    Button bAudio;
    Button bChoose;
    Button bUpload;
    Button bGenderNo;
    Button bGenderYes;
    Button bAgeNo;
    Button bAgeYes;
    Button bLocationNo;
    Button bLocationYes;
    Button bInterestsNo;
    Button bInterestsYes;
    Button bViews1;
    Button bViews2;
    Button bViews3;
    Button bViews4;
    Button bViews5;
    Button bBack;
    Button bBack1;
    Button bBack2;
    Button bBack3;
    Button bBack4;
    Button bBack5;
    Button bNext1;
    Button bNext2;
    Button bNext3;
    Button bNext4;
    Button bApply;
    Button bAddFunds;
    Button bDirect;
    Button bEnter;
    Button bFinish;
    Button bPost;
    Button bYTPlay;
    Button bPlay;
    Button bPause;

    String StripeDepositURL = "https://raadz.com/charge-deposit-stripe.php";
    String StripeDepositVideoURL = "https://raadz.com/charge-pay-stripe.php";
    String StripeChargeImageURL = "https://raadz.com/charge-pay-img-stripe.php";
    String StripeChargeAudioURL = "https://raadz.com/charge-pay-aud-stripe.php";
    String ViewCostURL = "https://raadz.com/getPPVcosts.php";
    String AdvertiserDataURL = "https://raadz.com/getPubInfo.php";
    String VideoUploadURL = "https://raadz.com/videoupload.php";
    String BannerUploadURL = "https://raadz.com/imageupload.php";
    String ImageUploadURL = "https://raadz.com/addNewBanner.php";
    String AudioUploadURL = "https://raadz.com/addNewAudio.php";
    String NewVideoPublishURL = "https://raadz.com/addNewVideo.php";
    String CityListURL = "https://raadz.com/getCityList.php";
    String QuestionListURL = "https://raadz.com/getQuestionList.php";
    String InterestsListURL = "https://raadz.com/getInterestList.php";
    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String durationKey1 = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=";
    String durationKey2 = "&key=AIzaSyApjGaDZhoCC_SbLGzcDrZKARX2EGElG20";
    String durationKey;
    String u_URL;
    String gGender = "0";
    String f_price = "0";
    String locationCity = "0";
    String promoCode = "";
    String filePath = "";
    String fileDirectOrBalance = "1";
    String videoInternalOrExternal = "1";
    String imageInternalOrExternal = "1";
    String audioInternalOrExternal = "1";
    String seconds_f = "";
    String minutes_f = "";
    String youtubeLinkDuration;
    String altDuration;
    String audioLinkDuration;
    String balance;
    String money;
    String adType;
    String httpRequest;
    String info;
    String location;
    String fileNameG;
    String buttonPlaceholder;
    String viewPPV1;
    String viewPPV2;
    String viewPPV3;
    String viewPPV4;
    String viewPPV5;
    String costPPV1;
    String costPPV2;
    String costPPV3;
    String costPPV4;
    String costPPV5;
    String totalValueDisplay;
    String finalViews;
    String finalCost;
    String finalPromo;
    String finalGender;
    String finalAge;
    String finalLocation;
    String finalQuestions;
    String finalInterests;
    String pathReference;
    String fileDuration = "0";


    long[] interests_array_temp = new long[54];
    long[] interests_array = new long[54];
    long[] interests_number_array = new long[54];
    long[] interests_place = new long[54];

    long questionValue = 0;
    long interestsCounter = 0;

    int[] q_resub_value = new int[10];
    int[] q_resub_value_temp = new int[10];
    int[] q_values = new int[10];
    int[] q_values_temp = new int[10];
    int[] age_values = new int[5];
    int[] age_values_temp = new int[5];
    int[] interests_values = new int[54];
    int[] interests_values_temp = new int[54];

    int PICK_VIDEO = 100;
    int PICK_IMAGE = 100;
    int PICK_AUDIO = 100;
    int questionCounter = 0;
    int interestsNumber = 0;
    int ageValue = 0;
    int locationValue = 0;
    int serverResponseCode = 0;
    int numOfAges = 0;
    int numOfInterests = 0;
    int t_value;
    int age_power;
    int ok;
    int t_temp;
    int viewInt;
    int i_amount;

    double viewDisableValue = 0;
    double l_val1;
    double l_val2;
    double l_val3;
    double l_val4;
    double l_val5;
    double totalCost;
    double totalCostTemp;
    double optin;
    double double_amount;
    double deposit = 0;


    boolean layout_track = false;
    boolean boolGender = false;
    boolean boolAge = false;
    boolean boolAgeRG = false;
    boolean boolLocation = false;
    boolean boolLocationRG = false;
    boolean boolInterests = false;
    boolean boolInterestsCB = false;
    boolean boolGenderRG = false;
    boolean boolViews = false;
    boolean check = false;
    boolean paymentCheck = false;
    boolean previewCheck = false;
    boolean optChecked = false;
    boolean extraTime = false;


    private final int LOCATION_REQUEST_CODE = 2;
    private final int VIDEO_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile);
        toolbar.setOverflowIcon(drawable);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu4);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(newYTPlayer);
        player = null;

        ivImage = (ImageView) findViewById(R.id.ivImage);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgAge = (RadioGroup) findViewById(R.id.rgAge);
        rgLocation = (RadioGroup) findViewById(R.id.rgLocation);

        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        rbAge1 = (RadioButton) findViewById(R.id.rbAge1);
        rbAge2 = (RadioButton) findViewById(R.id.rbAge2);
        rbAge3 = (RadioButton) findViewById(R.id.rbAge3);
        rbAge4 = (RadioButton) findViewById(R.id.rbAge4);
        rbAge5 = (RadioButton) findViewById(R.id.rbAge5);
        rbLoc1 = (RadioButton) findViewById(R.id.rbLoc1);
        rbLoc2 = (RadioButton) findViewById(R.id.rbLoc2);
        rbLoc3 = (RadioButton) findViewById(R.id.rbLoc3);

        cbL1 = (CheckBox) findViewById(R.id.cbL1);
        cbL2 = (CheckBox) findViewById(R.id.cbL2);
        cbL3 = (CheckBox) findViewById(R.id.cbL3);
        cbL4 = (CheckBox) findViewById(R.id.cbL4);
        cbL5 = (CheckBox) findViewById(R.id.cbL5);

        cbQ1 = (CheckBox) findViewById(R.id.cbQ1);
        cbQ2 = (CheckBox) findViewById(R.id.cbQ2);
        cbQ3 = (CheckBox) findViewById(R.id.cbQ3);
        cbQ4 = (CheckBox) findViewById(R.id.cbQ4);
        cbQ5 = (CheckBox) findViewById(R.id.cbQ5);
        cbQ6 = (CheckBox) findViewById(R.id.cbQ6);

        cbI1 = (CheckBox) findViewById(R.id.cbI1);
        cbI2 = (CheckBox) findViewById(R.id.cbI2);
        cbI3 = (CheckBox) findViewById(R.id.cbI3);
        cbI4 = (CheckBox) findViewById(R.id.cbI4);
        cbI5 = (CheckBox) findViewById(R.id.cbI5);
        cbI6 = (CheckBox) findViewById(R.id.cbI6);
        cbI7 = (CheckBox) findViewById(R.id.cbI7);
        cbI8 = (CheckBox) findViewById(R.id.cbI8);
        cbI9 = (CheckBox) findViewById(R.id.cbI9);
        cbI10 = (CheckBox) findViewById(R.id.cbI10);
        cbI11 = (CheckBox) findViewById(R.id.cbI11);
        cbI12 = (CheckBox) findViewById(R.id.cbI12);
        cbI13 = (CheckBox) findViewById(R.id.cbI13);
        cbI14 = (CheckBox) findViewById(R.id.cbI14);
        cbI15 = (CheckBox) findViewById(R.id.cbI15);
        cbI16 = (CheckBox) findViewById(R.id.cbI16);

        cbOpt = (CheckBox) findViewById(R.id.cbOpt);

        LLchoosead = (LinearLayout) findViewById(R.id.LLchoosead);
        LLCheckCopy = (LinearLayout) findViewById(R.id.LLCheckCopy);
        LLInterestCopy = (LinearLayout) findViewById(R.id.LLInterestCopy);
        LLInterestCopy2 = (LinearLayout) findViewById(R.id.LLInterestCopy2);
        LLyoutube = (LinearLayout) findViewById(R.id.LLyoutube);
        LLbanner = (LinearLayout) findViewById(R.id.LLbanner);
        LLaudio = (LinearLayout) findViewById(R.id.LLaudio);
        LLAgeCheckBoxes = (LinearLayout) findViewById(R.id.LLAgeCheckBoxes);
        LLlocationitems = (LinearLayout) findViewById(R.id.LLlocationitems);
        LLInterestsCheckBoxes = (LinearLayout) findViewById(R.id.LLInterestsCheckBoxes);
        LLstep1 = (LinearLayout) findViewById(R.id.LLstep1);
        LLstep2 = (LinearLayout) findViewById(R.id.LLstep2);
        LLstep3 = (LinearLayout) findViewById(R.id.LLstep3);
        LLstep4 = (LinearLayout) findViewById(R.id.LLstep4);
        LLstep5 = (LinearLayout) findViewById(R.id.LLstep5);
        LLPreview = (LinearLayout) findViewById(R.id.LLPreview);
        LL1 = (LinearLayout) findViewById(R.id.LL1);
        LL2 = (LinearLayout) findViewById(R.id.LL2);
        LL3 = (LinearLayout) findViewById(R.id.LL3);
        LL4 = (LinearLayout) findViewById(R.id.LL4);
        LL5 = (LinearLayout) findViewById(R.id.LL5);
        LL6 = (LinearLayout) findViewById(R.id.LL6);
        LLopt = (LinearLayout) findViewById(R.id.LLopt);
        LLEnd = (LinearLayout) findViewById(R.id.LLEnd);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvComingSoon = (TextView) findViewById(R.id.tvComingSoon);
        tvMax = (TextView) findViewById(R.id.tvMaxLimit);
        tvPickAd = (TextView) findViewById(R.id.tvPickAd);
        tvAltText = (TextView) findViewById(R.id.tvAltText);
        tvOneOrTheOther = (TextView) findViewById(R.id.tvOneOrTheOther);
        tvLargeFile = (TextView) findViewById(R.id.tvLargeFile);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvDurationPrompt = (TextView) findViewById(R.id.tvDurationPrompt);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPromoDisplay = (TextView) findViewById(R.id.tvPromoDisplay);
        tvPPubID = (TextView) findViewById(R.id.tvPPubID);
        tvPCompany = (TextView) findViewById(R.id.tvPCompany);
        tvPTitle = (TextView) findViewById(R.id.tvPTitle);
        tvPLink = (TextView) findViewById(R.id.tvPLink);
        tvPYTID = (TextView) findViewById(R.id.tvPYTID);
        tvPGender = (TextView) findViewById(R.id.tvPGender);
        tvPAge = (TextView) findViewById(R.id.tvPAge);
        tvPLocation = (TextView) findViewById(R.id.tvPLocation);
        tvPQuestion = (TextView) findViewById(R.id.tvPQuestion);
        tvPInterests = (TextView) findViewById(R.id.tvPInterests);
        tvPViews = (TextView) findViewById(R.id.tvPViews);
        tvPCost = (TextView) findViewById(R.id.tvPCost);
        tvPPromo = (TextView) findViewById(R.id.tvPPromo);
        tvPBalance = (TextView) findViewById(R.id.tvPBalance);
        tvOpt = (TextView) findViewById(R.id.tvOpt);

        etCompany = (EditText) findViewById(R.id.etCompany);
        etVideoTitle = (EditText) findViewById(R.id.etVideoTitle);
        etVideoInfo = (EditText) findViewById(R.id.etVideoInfo);
        etVideoID = (EditText) findViewById(R.id.etVideoID);
        etPromo = (EditText) findViewById(R.id.etPromo);

        bDifferentYT = (Button) findViewById(R.id.bDifferentYT);
        bDifferentB = (Button) findViewById(R.id.bDifferentB);
        bDifferentA = (Button) findViewById(R.id.bDifferentA);
        bYoutube = (Button) findViewById(R.id.bYoutube);
        bBanner = (Button) findViewById(R.id.bBanner);
        bAudio = (Button) findViewById(R.id.bAudio);
        bChoose = (Button) findViewById(R.id.bChoose);
        bUpload = (Button) findViewById(R.id.bUpload);
        bGenderNo = (Button) findViewById(R.id.bGenderNo);
        bGenderYes = (Button) findViewById(R.id.bGenderYes);
        bAgeNo = (Button) findViewById(R.id.bAgeNo);
        bAgeYes = (Button) findViewById(R.id.bAgeYes);
        bLocationNo = (Button) findViewById(R.id.bLocationNo);
        bLocationYes = (Button) findViewById(R.id.bLocationYes);
        bInterestsNo = (Button) findViewById(R.id.bInterestsNo);
        bInterestsYes = (Button) findViewById(R.id.bInterestsYes);
        bViews1 = (Button) findViewById(R.id.bViews1);
        bViews2 = (Button) findViewById(R.id.bViews2);
        bViews3 = (Button) findViewById(R.id.bViews3);
        bViews4 = (Button) findViewById(R.id.bViews4);
        bViews5 = (Button) findViewById(R.id.bViews5);
        bBack = (Button) findViewById(R.id.bBack);
        bBack1 = (Button) findViewById(R.id.bBack1);
        bBack2 = (Button) findViewById(R.id.bBack2);
        bBack3 = (Button) findViewById(R.id.bBack3);
        bBack4 = (Button) findViewById(R.id.bBack4);
        bBack5 = (Button) findViewById(R.id.bBack5);
        bNext1 = (Button) findViewById(R.id.bNext1);
        bNext2 = (Button) findViewById(R.id.bNext2);
        bNext3 = (Button) findViewById(R.id.bNext3);
        bNext4 = (Button) findViewById(R.id.bNext4);
        bApply = (Button) findViewById(R.id.bApply);
        bAddFunds = (Button) findViewById(R.id.bAddFunds);
        bDirect = (Button) findViewById(R.id.bDirect);
        bFinish = (Button) findViewById(R.id.bFinish);
        bPost = (Button) findViewById(R.id.bPost);
        bYTPlay = (Button) findViewById(R.id.bYTPlay);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tvMoney.setText("$" + preferences.getString("cash", ""));
        etCompany.setText(preferences.getString("pub_company", ""));

        if (Double.parseDouble(preferences.getString("cash", "")) < 5.00) {
            Toast.makeText(this, "Warning:  Balance is below preferred threshold", Toast.LENGTH_LONG).show();
        }


        arrayValues();

        spinnerDefault = (Spinner) findViewById(R.id.spinnerDefault);
        spinnerDefault.setSelection(0);

        Log.d("resub ", preferences.getString("re_submit", ""));

        questionsClicked();

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Log.d("test", "inside initizedlistener");
                if (preferences.getString("ref", "").length() > 2) {
                    etVideoID.setText(preferences.getString("ref", ""));
                }
                Log.d("youtubeID ", etVideoID.getText().toString());
                final String youtubeURL = etVideoID.getText().toString();
                player = youTubePlayer;
                playVideo(youtubeURL);


                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                    }

                    @Override
                    public void onLoaded(String s) {
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

//        interestsClicked();

        if (preferences.getString("re_submit", "").equals("true")) {

            SharedPreferences.Editor edit = preferences.edit();
            edit.remove("re_submit");
            edit.putString("re_submit", "false");
            edit.commit();

            previewCheck = true;
            bBack5.setText("Edit Details");


            String AdID = preferences.getString("adid", "");
            String company = preferences.getString("company", "");
            String type = preferences.getString("type", "");
            String title = preferences.getString("title", "");
            String link = preferences.getString("link", "");
            String ref = preferences.getString("ref", "");
            String gender = preferences.getString("gender", "");
            String age = preferences.getString("age", "");
            String location = preferences.getString("location", "");
            String questions1 = preferences.getString("questions_value", "");
            String questions2 = preferences.getString("questions", "");
            String interests = preferences.getString("interests", "");

            Log.d("questions1 ", questions1);
            Log.d("questions2 ", questions2);

            int i_age = Integer.parseInt(age);

            tvPCompany.setText(preferences.getString("company", ""));
            tvPTitle.setText(preferences.getString("title", ""));
            tvPLink.setText(preferences.getString("link", ""));
            tvPGender.setText(preferences.getString("gender", ""));
            tvPAge.setText(preferences.getString("age", ""));
            tvPLocation.setText(preferences.getString("location", ""));
            tvPQuestion.setText(preferences.getString("questions_value", ""));
            tvPInterests.setText(preferences.getString("interests", ""));

            adType = type;
            videoInternalOrExternal = "1";

            Log.d("JIC_REF ", preferences.getString("ref", ""));
            Log.d("adType ", adType);

            edit.putString("adType", adType);
            edit.commit();

            Log.d("gender ", gender);
            Log.d("age ", String.valueOf(i_age));
            Log.d("location ", location);
            Log.d("interests", interests);

            preloadQuestions(Integer.parseInt(questions2));
            preloadGender(gender);
            preloadAge(i_age);
            preloadLocation(location);

            filePath = preferences.getString("ad_ref", "");
            fileNameG = "";
            View view;

            if (adType.equals("1")) {
                Log.d("inTypeOne ", " - ");
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                videoInternalOrExternal = "1";
                etVideoID.setText(ref);
            } else if (adType.equals("2")) {
                videoInternalOrExternal = "0";
                try {
                    String imgPath = azureImages + preferences.getString("ref", "");
                    Log.d("Full_Path ", imgPath);
                    Picasso.with(getApplicationContext()).load(imgPath).into(ivImage);
                    etVideoID.setText(ref);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (adType.equals("3")) {
                etVideoID.setText(ref);
                audioPlayer(azureAudio, preferences.getString("ref", ""));
            }

            view = bYoutube;
            onClick(view);
//            LLstep2.setVisibility(View.VISIBLE);

//            etVideoID.setText(preferences.getString("ad_ref", ""));
            etVideoTitle.setText(preferences.getString("title", ""));
            etVideoInfo.setText(preferences.getString("link", ""));

            edit.remove("promo");
            edit.remove("nvGender");
            edit.putString("nvGender", gender);
            edit.remove("nvAge");
            edit.putString("nvAge", age);
            edit.remove("nvLocation");
            edit.putString("nvLocation", location);
            edit.remove("nvInterests");
            edit.putString("nvInterests", interests);
            edit.remove("nvQuestions");
            edit.putString("nvQuestions", questions2);
            edit.commit();


            boolInterests = true;
            boolInterestsCB = true;
            interestsNumber = 1;

            Log.d("etVideoID ", etVideoID.getText().toString());

            View altV = bNext1;
            onClick(altV);


        } else if (previewCheck == false) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.remove("promo");
            edit.remove("nvGender");
            edit.remove("nvAge");
            edit.remove("nvLocation");
            edit.remove("nvInterests");
            edit.remove("nvQuestions");
            edit.remove("ad_ref");
            edit.remove("title");
            edit.remove("link");
            edit.remove("duration");
            edit.commit();
            bBack5.setText("Back");

        }

        bYoutube.setOnClickListener(this);

        bBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adType = "2";
                tvOneOrTheOther.setVisibility(View.GONE);
                videoInternalOrExternal = "0";
                bPlay.setVisibility(View.GONE);
                bPause.setVisibility(View.GONE);
                tvComingSoon.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                bUpload.setVisibility(View.GONE);
                LLchoosead.setVisibility(View.GONE);
                stepOneVisible();
                LLyoutube.setVisibility(View.VISIBLE);
                etVideoTitle.setHint("Image Title");
                etVideoID.setHint("Image URL");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("adType", adType);
                edit.commit();
                //LLbanner.setVisibility(View.VISIBLE);
            }
        });

        bAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adType = "3";
                tvOneOrTheOther.setVisibility(View.GONE);
                videoInternalOrExternal = "0";
                bPlay.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.VISIBLE);
                tvComingSoon.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                bUpload.setVisibility(View.GONE);
                LLchoosead.setVisibility(View.GONE);
                stepOneVisible();
                LLyoutube.setVisibility(View.VISIBLE);
                etVideoTitle.setHint("Audio Ad Title");
                etVideoID.setHint("Audio File URL");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("adType", adType);
                edit.commit();

            }
        });

        bUpload.setOnClickListener(this);
        bChoose.setOnClickListener(this);


        bYTPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                String tempURL = etVideoID.getText().toString();
                playVideo(tempURL);
            }
        });

        bNext1.setOnClickListener(this);

        bNext2.setOnClickListener(this);

        bNext3.setOnClickListener(this);

        bNext4.setOnClickListener(this);

        bPost.setOnClickListener(this);

        bBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepTwoGone();
                stepThreeGone();
                stepFourGone();
                stepFiveGone();
                stepOneVisible();
            }
        });
        bBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepOneGone();
                stepThreeGone();
                stepFourGone();
                stepFiveGone();
                stepTwoVisible();
            }
        });
        bBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepOneGone();
                stepTwoGone();
                stepFourGone();
                stepFiveGone();
                stepThreeVisible();
            }
        });
        bBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepOneGone();
                stepTwoGone();
                stepThreeGone();
                stepFiveGone();
                stepFourVisible();
            }
        });

        bBack5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interestsNumber = interestsNumber + 1;
                stepOneGone();
                stepTwoGone();
                stepThreeGone();
                stepFiveVisible();
                stepFourGone();
                stepPreviewGone();
                bDifferentYT.setVisibility(View.VISIBLE);
            }
        });


        bDifferentYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(PubSubmitActivity.this, PubSubmitActivity.class);
                startActivity(in);

//                clearValues();
//                tvTitle.setVisibility(View.VISIBLE);
//                etCompany.setText("");
//                etVideoTitle.setText("");
//                etVideoInfo.setText("");
//                etVideoID.setText("");
//                questionCounter = 0;
//
//                bGenderNo.setBackgroundResource(R.drawable.my_button_index);
//                bGenderNo.setTextColor(Color.WHITE);
//                bGenderYes.setBackgroundResource(R.drawable.my_button_index);
//                bGenderYes.setTextColor(Color.WHITE);
//
//                bAgeNo.setBackgroundResource(R.drawable.my_button_index);
//                bAgeNo.setTextColor(Color.WHITE);
//                bAgeYes.setBackgroundResource(R.drawable.my_button_index);
//                bAgeYes.setTextColor(Color.WHITE);
//
//                bLocationNo.setBackgroundResource(R.drawable.my_button_index);
//                bLocationNo.setTextColor(Color.WHITE);
//                bLocationYes.setBackgroundResource(R.drawable.my_button_index);
//                bLocationYes.setTextColor(Color.WHITE);
//
//                bInterestsNo.setBackgroundResource(R.drawable.my_button_index);
//                bInterestsNo.setTextColor(Color.WHITE);
//                bInterestsYes.setBackgroundResource(R.drawable.my_button_index);
//                bInterestsYes.setTextColor(Color.WHITE);
//
//                bViews1.setBackgroundResource(R.drawable.my_button_round);
//                bViews1.setTextColor(Color.WHITE);
//                bViews2.setBackgroundResource(R.drawable.my_button_round);
//                bViews2.setTextColor(Color.WHITE);
//                bViews3.setBackgroundResource(R.drawable.my_button_round);
//                bViews3.setTextColor(Color.WHITE);
//                bViews4.setBackgroundResource(R.drawable.my_button_round);
//                bViews4.setTextColor(Color.WHITE);
//                bViews5.setBackgroundResource(R.drawable.my_button_round);
//                bViews5.setTextColor(Color.WHITE);
//
//                bViews1.setVisibility(View.VISIBLE);
//                bViews2.setVisibility(View.VISIBLE);
//                bViews3.setVisibility(View.VISIBLE);
//                bViews4.setVisibility(View.VISIBLE);
//                bViews5.setVisibility(View.VISIBLE);
//
//                tvTotal.setText("");
//                stepOneGone();
//                stepTwoGone();
//                stepThreeGone();
//                stepFourGone();
//                stepFiveGone();
//                LLyoutube.setVisibility(View.GONE);
//                LLchoosead.setVisibility(View.VISIBLE);
            }
        });

        bDifferentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLbanner.setVisibility(View.GONE);
                LLstep1.setVisibility(View.GONE);
                LLstep2.setVisibility(View.GONE);
                LLstep3.setVisibility(View.GONE);
                LLstep4.setVisibility(View.GONE);
                LLchoosead.setVisibility(View.VISIBLE);
            }
        });

        bDifferentA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLaudio.setVisibility(View.GONE);
                LLstep1.setVisibility(View.GONE);
                LLstep2.setVisibility(View.GONE);
                LLstep3.setVisibility(View.GONE);
                LLstep4.setVisibility(View.GONE);
                LLchoosead.setVisibility(View.VISIBLE);
            }
        });


        bGenderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();
                boolGender = true;
                boolGenderRG = true;
                viewDisableValue = 0.00;
                bGenderNo.setBackgroundResource(R.drawable.my_button_clicked);
                bGenderNo.setTextColor(Color.GREEN);
                bGenderYes.setBackgroundResource(R.drawable.my_button_index);
                bGenderYes.setTextColor(Color.WHITE);
                rgGender.setVisibility(View.GONE);
                rbMale.setChecked(false);
                rbFemale.setChecked(false);
                gGender = "N";
                edit.putString("nvGender", "N");
                edit.commit();

                //rgGender.clearCheck();
            }
        });

        bGenderYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolGender = true;
                viewDisableValue = 1.00;
                bGenderYes.setBackgroundResource(R.drawable.my_button_clicked);
                bGenderYes.setTextColor(Color.GREEN);
                bGenderNo.setBackgroundResource(R.drawable.my_button_index);
                bGenderNo.setTextColor(Color.WHITE);
                rgGender.setVisibility(View.VISIBLE);
            }
        });

        bAgeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();
                boolAge = true;
                boolAgeRG = true;
                Log.d("boolage ", String.valueOf(boolAgeRG));
                ageValue = 0;
                numOfAges = 0;
                bAgeNo.setBackgroundResource(R.drawable.my_button_clicked);
                bAgeNo.setTextColor(Color.GREEN);
                bAgeYes.setBackgroundResource(R.drawable.my_button_index);
                bAgeYes.setTextColor(Color.WHITE);
                LLAgeCheckBoxes.setVisibility(View.GONE);
                cbL1.setChecked(false);
                cbL2.setChecked(false);
                cbL3.setChecked(false);
                cbL4.setChecked(false);
                cbL5.setChecked(false);
                ageValue = 0;
//                edit.putString("nvAge", "0");
//                edit.commit();

            }
        });

        bAgeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolAge = true;
                Log.d("boolage ", String.valueOf(boolAgeRG));
                bAgeYes.setBackgroundResource(R.drawable.my_button_clicked);
                bAgeYes.setTextColor(Color.GREEN);
                bAgeNo.setBackgroundResource(R.drawable.my_button_index);
                bAgeNo.setTextColor(Color.WHITE);
                LLAgeCheckBoxes.setVisibility(View.VISIBLE);
            }
        });

        bLocationNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("nvLocation", "No");
                edit.commit();
                locationCity = "0";
                boolLocation = true;
                boolLocationRG = true;
                bLocationNo.setBackgroundResource(R.drawable.my_button_clicked);
                bLocationNo.setTextColor(Color.GREEN);
                bLocationYes.setBackgroundResource(R.drawable.my_button_index);
                bLocationYes.setTextColor(Color.WHITE);
                LLlocationitems.setVisibility(View.GONE);
                rbLoc1.setChecked(false);
                rbLoc2.setChecked(false);
                rbLoc3.setChecked(false);
            }
        });

        bLocationYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolLocation = true;
                viewDisableValue += 2.00;
                bLocationYes.setBackgroundResource(R.drawable.my_button_clicked);
                bLocationYes.setTextColor(Color.GREEN);
                bLocationNo.setBackgroundResource(R.drawable.my_button_index);
                bLocationNo.setTextColor(Color.WHITE);
                LLlocationitems.setVisibility(View.VISIBLE);
            }
        });

        bInterestsNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolInterests = true;
                boolInterestsCB = true;
                interestsNumber = 1;
                interestsCounter = 0;
                bInterestsNo.setBackgroundResource(R.drawable.my_button_clicked);
                bInterestsNo.setTextColor(Color.GREEN);
                bInterestsYes.setBackgroundResource(R.drawable.my_button_index);
                bInterestsYes.setTextColor(Color.WHITE);
                LLInterestsCheckBoxes.setVisibility(View.GONE);
                cbI1.setChecked(false);
                cbI2.setChecked(false);
                cbI3.setChecked(false);
                cbI4.setChecked(false);
                cbI5.setChecked(false);
                cbI6.setChecked(false);
                cbI7.setChecked(false);
                cbI8.setChecked(false);
                cbI9.setChecked(false);
                cbI10.setChecked(false);
                cbI11.setChecked(false);
                cbI12.setChecked(false);
                cbI13.setChecked(false);
                cbI14.setChecked(false);
                cbI15.setChecked(false);
                cbI16.setChecked(false);
            }
        });

        bInterestsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolInterests = true;

                bInterestsYes.setBackgroundResource(R.drawable.my_button_clicked);
                bInterestsYes.setTextColor(Color.GREEN);
                bInterestsNo.setBackgroundResource(R.drawable.my_button_index);
                bInterestsNo.setTextColor(Color.WHITE);
                LLInterestsCheckBoxes.setVisibility(View.VISIBLE);
            }
        });

        bViews1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewInt = 1;
                LLopt.setVisibility(View.VISIBLE);
                tvOpt.setText("$" + preferences.getString("optin_cost", ""));

                boolViews = true;
                bViews1.setBackgroundResource(R.drawable.my_button_clicked);
                bViews1.setTextColor(Color.GREEN);

                bViews2.setBackgroundResource(R.drawable.my_button_round);
                bViews2.setTextColor(Color.WHITE);
                bViews3.setBackgroundResource(R.drawable.my_button_round);
                bViews3.setTextColor(Color.WHITE);
                bViews4.setBackgroundResource(R.drawable.my_button_round);
                bViews4.setTextColor(Color.WHITE);
                bViews5.setBackgroundResource(R.drawable.my_button_round);
                bViews5.setTextColor(Color.WHITE);

                if (optChecked == true) {
                    totalCost = optTotal(Double.parseDouble(costPPV1), Double.parseDouble(viewPPV1));
                } else {
                    totalCost = parseDouble(viewPPV1) * parseDouble(costPPV1);
                }
                totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));

                tvTotal.setText("$" + String.format("%.2f", totalCost));
                totalValueDisplay = (String.format("%.2f", totalCost));

                double t2 = Double.parseDouble(viewPPV2);
                double t3 = Double.parseDouble(viewPPV3);
                double t4 = Double.parseDouble(viewPPV4);
                double t5 = Double.parseDouble(viewPPV5);
                DecimalFormat df = new DecimalFormat("#.###");
                bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADedit = ADPreferences.edit();
                ADedit.putString("nvViews", bViews1.getText().toString());
                ADedit.putString("nvCost", String.valueOf(totalCost));
                ADedit.commit();
                finalCost = String.valueOf(totalCost);
                finalViews = df.format(Double.parseDouble(viewPPV1));
            }
        });
        bViews2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewInt = 2;
                LLopt.setVisibility(View.VISIBLE);
                tvOpt.setText("$" + preferences.getString("optin_cost", ""));

                boolViews = true;
                bViews2.setBackgroundResource(R.drawable.my_button_clicked);
                bViews2.setTextColor(Color.GREEN);

                bViews1.setBackgroundResource(R.drawable.my_button_round);
                bViews1.setTextColor(Color.WHITE);
                bViews3.setBackgroundResource(R.drawable.my_button_round);
                bViews3.setTextColor(Color.WHITE);
                bViews4.setBackgroundResource(R.drawable.my_button_round);
                bViews4.setTextColor(Color.WHITE);
                bViews5.setBackgroundResource(R.drawable.my_button_round);
                bViews5.setTextColor(Color.WHITE);

                if (optChecked == true) {
                    totalCost = optTotal(Double.parseDouble(costPPV2), Double.parseDouble(viewPPV2));
                } else {
                    totalCost = parseDouble(viewPPV2) * parseDouble(costPPV2);
                }

                totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));
                tvTotal.setText("$" + String.format("%.2f", totalCost));
                totalValueDisplay = (String.format("%.2f", totalCost));

                double t1 = Double.parseDouble(viewPPV1);
                double t3 = Double.parseDouble(viewPPV3);
                double t4 = Double.parseDouble(viewPPV4);
                double t5 = Double.parseDouble(viewPPV5);
                DecimalFormat df = new DecimalFormat("#.###");
                bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADedit = ADPreferences.edit();
                ADedit.putString("nvViews", bViews2.getText().toString());
                ADedit.putString("nvCost", String.valueOf(finalCost));
                ADedit.commit();
                finalCost = String.valueOf(totalCost);
                finalViews = df.format(Double.parseDouble(viewPPV2));
            }
        });
        bViews3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewInt = 3;
                LLopt.setVisibility(View.VISIBLE);
                tvOpt.setText("$" + preferences.getString("optin_cost", ""));

                boolViews = true;
                bViews3.setBackgroundResource(R.drawable.my_button_clicked);
                bViews3.setTextColor(Color.GREEN);

                bViews2.setBackgroundResource(R.drawable.my_button_round);
                bViews2.setTextColor(Color.WHITE);
                bViews1.setBackgroundResource(R.drawable.my_button_round);
                bViews1.setTextColor(Color.WHITE);
                bViews4.setBackgroundResource(R.drawable.my_button_round);
                bViews4.setTextColor(Color.WHITE);
                bViews5.setBackgroundResource(R.drawable.my_button_round);
                bViews5.setTextColor(Color.WHITE);

                if (optChecked == true) {
                    totalCost = optTotal(Double.parseDouble(costPPV3), Double.parseDouble(viewPPV3));
                } else {
                    totalCost = parseDouble(viewPPV3) * parseDouble(costPPV3);
                }

                totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));
                tvTotal.setText("$" + String.format("%.2f", totalCost));
                totalValueDisplay = (String.format("%.2f", totalCost));

                double t1 = Double.parseDouble(viewPPV1);
                double t2 = Double.parseDouble(viewPPV2);
                double t4 = Double.parseDouble(viewPPV4);
                double t5 = Double.parseDouble(viewPPV5);
                DecimalFormat df = new DecimalFormat("#.###");
                bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADedit = ADPreferences.edit();
                ADedit.putString("nvViews", bViews3.getText().toString());
                ADedit.putString("nvCost", String.valueOf(finalCost));
                ADedit.commit();
                finalCost = String.valueOf(totalCost);
                finalViews = df.format(Double.parseDouble(viewPPV3));
            }
        });
        bViews4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewInt = 4;
                LLopt.setVisibility(View.VISIBLE);
                tvOpt.setText("$" + preferences.getString("optin_cost", ""));

                boolViews = true;
                bViews4.setBackgroundResource(R.drawable.my_button_clicked);
                bViews4.setTextColor(Color.GREEN);

                bViews2.setBackgroundResource(R.drawable.my_button_round);
                bViews2.setTextColor(Color.WHITE);
                bViews3.setBackgroundResource(R.drawable.my_button_round);
                bViews3.setTextColor(Color.WHITE);
                bViews1.setBackgroundResource(R.drawable.my_button_round);
                bViews1.setTextColor(Color.WHITE);
                bViews5.setBackgroundResource(R.drawable.my_button_round);
                bViews5.setTextColor(Color.WHITE);

                if (optChecked == true) {
                    totalCost = optTotal(Double.parseDouble(costPPV4), Double.parseDouble(viewPPV4));
                } else {
                    totalCost = parseDouble(viewPPV4) * parseDouble(costPPV4);
                }

                totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));
                tvTotal.setText("$" + String.format("%.2f", totalCost));
                totalValueDisplay = (String.format("%.2f", totalCost));

                double t1 = Double.parseDouble(viewPPV1);
                double t2 = Double.parseDouble(viewPPV2);
                double t3 = Double.parseDouble(viewPPV3);
                double t5 = Double.parseDouble(viewPPV5);
                DecimalFormat df = new DecimalFormat("#.###");
                bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADedit = ADPreferences.edit();
                ADedit.putString("nvViews", bViews4.getText().toString());
                ADedit.putString("nvCost", String.valueOf(finalCost));
                ADedit.commit();
                finalCost = String.valueOf(totalCost);
                finalViews = df.format(Double.parseDouble(viewPPV4));
            }
        });
        bViews5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewInt = 5;
                LLopt.setVisibility(View.VISIBLE);
                tvOpt.setText("$" + preferences.getString("optin_cost", ""));

                boolViews = true;
                bViews5.setBackgroundResource(R.drawable.my_button_clicked);
                bViews5.setTextColor(Color.GREEN);

                bViews2.setBackgroundResource(R.drawable.my_button_round);
                bViews2.setTextColor(Color.WHITE);
                bViews3.setBackgroundResource(R.drawable.my_button_round);
                bViews3.setTextColor(Color.WHITE);
                bViews4.setBackgroundResource(R.drawable.my_button_round);
                bViews4.setTextColor(Color.WHITE);
                bViews1.setBackgroundResource(R.drawable.my_button_round);
                bViews1.setTextColor(Color.WHITE);

                if (optChecked == true) {
                    totalCost = optTotal(Double.parseDouble(costPPV5), Double.parseDouble(viewPPV5));
                } else {
                    totalCost = parseDouble(viewPPV5) * parseDouble(costPPV5);
                }

                totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));
                tvTotal.setText("$" + String.format("%.2f", totalCost));
                totalValueDisplay = (String.format("%.2f", totalCost));

                double t1 = Double.parseDouble(viewPPV1);
                double t2 = Double.parseDouble(viewPPV2);
                double t3 = Double.parseDouble(viewPPV3);
                double t4 = Double.parseDouble(viewPPV4);
                DecimalFormat df = new DecimalFormat("#.###");
                bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADedit = ADPreferences.edit();
                ADedit.putString("nvViews", bViews5.getText().toString());
                ADedit.putString("nvCost", String.valueOf(finalCost));
                ADedit.commit();
                finalCost = String.valueOf(totalCost);
                finalViews = df.format(Double.parseDouble(viewPPV5));
            }
        });


        bApply.setOnClickListener(this);
        bAddFunds.setOnClickListener(this);
        bDirect.setOnClickListener(this);
        bFinish.setOnClickListener(this);


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbMale:
                        boolGenderRG = true;
                        gGender = "M";
                        break;
                    case R.id.rbFemale:
                        boolGenderRG = true;
                        gGender = "F";
                        break;
                }
            }
        });

        rgAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbAge1:
                        numOfAges += 1;
                        boolAgeRG = true;
                        ageValue = 1;
                        break;
                    case R.id.rbAge2:
                        numOfAges += 1;
                        boolAgeRG = true;
                        ageValue = 2;
                        break;
                    case R.id.rbAge3:
                        numOfAges += 1;
                        boolAgeRG = true;
                        ageValue = 4;
                        break;
                    case R.id.rbAge4:
                        numOfAges += 1;
                        boolAgeRG = true;
                        ageValue = 8;
                        break;
                    case R.id.rbAge5:
                        numOfAges += 1;
                        boolAgeRG = true;
                        ageValue = 16;
                        break;
                }
            }
        });

        cbL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbL1.isChecked()) {
                    numOfAges += 1;
                    ageValue = ageValue + 1;
                    Log.d("Age Value ", String.valueOf(ageValue));
                } else {
                    numOfAges -= 1;
                    ageValue = ageValue - 1;
                    Log.d("Age Value ", String.valueOf(ageValue));
                }
            }
        });

        cbL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbL2.isChecked()) {
                    numOfAges += 1;
                    ageValue = ageValue + 2;
                    Log.d("Age Value ", String.valueOf(ageValue));
                } else {
                    numOfAges -= 1;
                    ageValue = ageValue - 2;
                    Log.d("Age Value ", String.valueOf(ageValue));
                }
            }
        });

        cbL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbL3.isChecked()) {
                    numOfAges += 1;
                    ageValue = ageValue + 4;
                    Log.d("Age Value ", String.valueOf(ageValue));
                } else {
                    numOfAges -= 1;
                    ageValue = ageValue - 4;
                    Log.d("Age Value ", String.valueOf(ageValue));
                }
            }
        });

        cbL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbL4.isChecked()) {
                    numOfAges += 1;
                    ageValue = ageValue + 8;
                    Log.d("Age Value ", String.valueOf(ageValue));
                } else {
                    numOfAges -= 1;
                    ageValue = ageValue - 8;
                    Log.d("Age Value ", String.valueOf(ageValue));
                }
            }
        });

        cbL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbL5.isChecked()) {
                    numOfAges += 1;
                    ageValue = ageValue + 16;
                    Log.d("Age Value ", String.valueOf(ageValue));
                } else {
                    numOfAges -= 1;
                    ageValue = ageValue - 16;
                    Log.d("Age Value ", String.valueOf(ageValue));
                }
            }
        });

        cbOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbOpt.isChecked()) {

                    optChecked = true;

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String opt = preferences.getString("optin_cost", "");

                    Log.d("opt in ", preferences.getString("optin_cost", ""));
                    Log.d("cost ", String.valueOf(costPPV1));
                    Log.d("view ", String.valueOf(viewPPV1));
                    Log.d("view2 ", String.valueOf(view));

                    if (viewInt == 1) {
                        Log.d("view ", String.valueOf(view));
                        totalCostTemp = optTotal(Double.parseDouble(costPPV1), Double.parseDouble(viewPPV1));
                    } else if (viewInt == 2) {
                        Log.d("view ", String.valueOf(view));
                        totalCostTemp = optTotal(Double.parseDouble(costPPV2), Double.parseDouble(viewPPV2));
                    } else if (viewInt == 3) {
                        Log.d("view ", String.valueOf(view));
                        totalCostTemp = optTotal(Double.parseDouble(costPPV3), Double.parseDouble(viewPPV3));
                    } else if (viewInt == 4) {
                        Log.d("view ", String.valueOf(view));
                        totalCostTemp = optTotal(Double.parseDouble(costPPV4), Double.parseDouble(viewPPV4));
                    } else if (viewInt == 5) {
                        Log.d("view ", String.valueOf(view));
                        totalCostTemp = optTotal(Double.parseDouble(costPPV5), Double.parseDouble(viewPPV5));
                    }

                    try {

                        Log.d("totalCostTemp ", String.valueOf(totalCostTemp));

                        tvTotal.setText("$" + String.format("%.2f", totalCostTemp));

                        finalCost = String.valueOf(totalCost);

                    } catch (NumberFormatException e) {
                        System.out.println(e);
                    }

                } else {
                    optChecked = false;


                    if (viewInt == 1) {
                        totalCost = optBack(Double.parseDouble(costPPV1), Double.parseDouble(viewPPV1));
                    } else if (viewInt == 2) {
                        totalCost = optBack(Double.parseDouble(costPPV2), Double.parseDouble(viewPPV2));
                    } else if (viewInt == 3) {
                        totalCost = optBack(Double.parseDouble(costPPV3), Double.parseDouble(viewPPV3));
                    } else if (viewInt == 4) {
                        totalCost = optBack(Double.parseDouble(costPPV4), Double.parseDouble(viewPPV4));
                    } else if (viewInt == 5) {
                        totalCost = optBack(Double.parseDouble(costPPV5), Double.parseDouble(viewPPV5));
                    }

                    tvTotal.setText("$" + String.format("%.2f", totalCost));
                    finalCost = String.valueOf(totalCost);
                }
            }
        });

        rgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbLoc1:
                        boolLocationRG = true;
                        locationValue = 1;
                        break;
                    case R.id.rbLoc2:
                        boolLocationRG = true;
                        locationValue = 2;
                        break;
                    case R.id.rbLoc3:
                        boolLocationRG = true;
                        locationValue = 4;
                        break;
                }
            }
        });

        spinnerDefault.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationCity = spinnerDefault.getSelectedItem().toString();
                boolLocationRG = true;
                Log.d("item selected ", locationCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }//End of onCreate


    public int localDuration(String file) {
        MediaPlayer mp = MediaPlayer.create(this, Uri.parse(file));
        int duration = mp.getDuration() / 100;
        Log.d("localDuration ", String.valueOf(duration));
        return duration;
    }

    public void valuesTotal(int q_value) {
        t_value = (int) Math.pow(2, 9);

        for (int t = 0; t < q_values_temp.length; t++) {
            q_values_temp[t] = t_value;
            t_value = t_value / 2;
        }
        int t_temp = q_values_temp.length - 1;
        for (int r = 0; r < q_values_temp.length; r++) {
            q_values[r] = q_values_temp[t_temp];
            if (t_temp >= 1) {
                t_temp--;
            }
        }

        for (int i = 0; i < q_values.length; i++) {
            Log.d("q_values ", String.valueOf(q_values[i]));
        }
    }

    public void preloadQuestions(int q_value) {

        valuesTotal(q_value);

        t_value = (int) Math.pow(2, 9);


        for (int i = 0; i < q_resub_value_temp.length; i++) {
            if (q_value >= t_value) {
                q_value = q_value - t_value;
                q_resub_value_temp[i] = t_value;
                t_value = t_value / 2;
                Log.d("q_value ", String.valueOf(q_value));
            } else if (i + 1 == q_resub_value_temp.length && q_value == 1) {
                q_resub_value_temp[i] = 1;
                q_value = q_value - 1;
            } else {
                Log.d("t_value ", String.valueOf(t_value));
                t_value = t_value / 2;
                q_resub_value_temp[i] = 0;
                Log.d("t_value_updated ", String.valueOf(t_value));
            }

        }

        for (int i = 0; i < q_resub_value_temp.length; i++) {
            Log.d("q_resub_value_temp ", String.valueOf(q_resub_value_temp[i]));
        }

        int l_temp = q_resub_value_temp.length - 1;
        for (int r = 0; r < q_resub_value_temp.length; r++) {
            Log.d("tempResub ", String.valueOf(q_resub_value[r]));
            Log.d("l_temp ", String.valueOf(l_temp));
            Log.d("r ", String.valueOf(r));
            q_resub_value[r] = q_resub_value_temp[l_temp];
            if (l_temp >= 1) {
                l_temp--;
            }
        }

        for (int i = 0; i < q_resub_value.length; i++) {
            Log.d("finalResub ", String.valueOf(q_resub_value[i]));
        }

    }

    public void preloadGender(String g_value) {
        boolGender = true;
        viewDisableValue = 1.00;
        bGenderYes.setBackgroundResource(R.drawable.my_button_clicked);
        bGenderYes.setTextColor(Color.GREEN);
        bGenderNo.setBackgroundResource(R.drawable.my_button_index);
        bGenderNo.setTextColor(Color.WHITE);
        rgGender.setVisibility(View.VISIBLE);
        if (g_value.equals("M")) {
            boolGenderRG = true;
            boolGender = true;
            gGender = "M";
            rbMale.setChecked(true);
        }
        if (g_value.equals("F")) {
            boolGenderRG = true;
            boolGender = true;
            gGender = "F";
            rbMale.setChecked(true);
        }
    }

    public void preloadAge(int a_value) {
        boolAge = true;
        boolAgeRG = true;
        ageValue = a_value;
        Log.d("boolage ", String.valueOf(boolAgeRG));
        bAgeYes.setBackgroundResource(R.drawable.my_button_clicked);
        bAgeYes.setTextColor(Color.GREEN);
        bAgeNo.setBackgroundResource(R.drawable.my_button_index);
        bAgeNo.setTextColor(Color.WHITE);
        LLAgeCheckBoxes.setVisibility(View.VISIBLE);
        age_power = (int) Math.pow(2, 4);

        for (int i = 0; i < age_values_temp.length; i++) {
            if (a_value > age_power) {
                numOfAges += 1;
                a_value = a_value - age_power;
                age_values_temp[i] = age_power;
                age_power = age_power / 2;
                Log.d("a_value ", String.valueOf(a_value));
            } else if (i + 1 == age_values_temp.length && a_value == 1) {
                numOfAges += 1;
                age_values_temp[i] = 1;
                a_value = a_value - 1;
            } else {
                Log.d("t_value ", String.valueOf(age_power));
                age_power = age_power / 2;
                age_values_temp[i] = 0;
                Log.d("age_power_updated ", String.valueOf(age_power));
            }

        }


        int a_temp = age_values_temp.length - 1;
        for (int r = 0; r < age_values_temp.length; r++) {
            age_values[r] = age_values_temp[a_temp];
            if (a_temp >= 1) {
                a_temp--;
            }
        }

        for (int i = 0; i < age_values.length; i++) {
            Log.d("age_values ", String.valueOf(age_values[i]));
        }
        if (age_values[0] > 0) {
            Log.d("agetag ", "1");
            cbL1.setChecked(true);
        }
        if (age_values[1] > 0) {
            Log.d("agetag ", "2");
            cbL2.setChecked(true);
        }
        if (age_values[2] > 0) {
            Log.d("agetag ", "3");
            cbL3.setChecked(true);
        }
        if (age_values[3] > 0) {
            cbL4.setChecked(true);
        }
        if (age_values[4] > 0) {
            cbL5.setChecked(true);
        }
    }

    public void preloadLocation(String l_value) {
        boolLocation = true;
        viewDisableValue += 2.00;
        bLocationYes.setBackgroundResource(R.drawable.my_button_clicked);
        bLocationYes.setTextColor(Color.GREEN);
        bLocationNo.setBackgroundResource(R.drawable.my_button_index);
        bLocationNo.setTextColor(Color.WHITE);
        LLlocationitems.setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PubSubmitActivity.this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = adapter.getPosition(l_value);
        Log.d("position ", String.valueOf(l_value));
        Log.d("position String ", l_value);
        int spinnerPosition = adapter.getPosition(l_value);
        spinnerDefault.setSelection(spinnerPosition);
        spinnerDefault.setAdapter(adapter);
    }

    public void preloadInterests(long i_value) {
        t_value = (int) Math.pow(2, 53);
        Log.d("INTERESTS ", String.valueOf(i_value));
        for (int i = 0; i < interests_values_temp.length; i++) {
            if (i_value > t_value) {
                i_value = i_value - t_value;
                interests_values_temp[i] = t_value;
                t_value = t_value / 2;
                Log.d("i_value ", String.valueOf(i_value));
            } else if (i + 1 == interests_values_temp.length && i_value == 1) {
                interests_values_temp[i] = 1;
                i_value = i_value - 1;
            } else {
                Log.d("t_value ", String.valueOf(t_value));
                t_value = t_value / 2;
                interests_values_temp[i] = 0;
                Log.d("t_value_updated ", String.valueOf(t_value));
            }

        }

        for (int i = 0; i < interests_values_temp.length; i++) {
            Log.d("interests_values_temp ", String.valueOf(interests_values_temp[i]));
        }

        int l_temp = interests_values_temp.length - 1;
        for (int r = 0; r < interests_values_temp.length; r++) {
            interests_values[r] = interests_values_temp[l_temp];
            if (l_temp >= 1) {
                l_temp--;
            }
        }

        for (int i = 0; i < interests_values.length; i++) {
            Log.d("finalResub ", String.valueOf(interests_values[i]));
        }
    }

    public void audioPlayer(final String path, final String fileName) {

        final MediaPlayer mp = new MediaPlayer();
        try {
            pathReference = path;
            mp.setDataSource(path + fileName);
            mp.prepare();
            mp.setVolume(0, 0);
            mp.start();
        } catch (Exception e) {
            System.out.println();
        }

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                bPlay.setText("Replay");
            }
        });

    }

    public void audioFromServer(final String path) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(path);
            player.prepare();
            player.start();
            audioLinkDuration = String.valueOf(player.getDuration());
            player.stop();
            long temp1 = Long.parseLong(audioLinkDuration);
            long temp2 = Long.parseLong(audioLinkDuration);
            audioLinkDuration = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(temp1));
            String altaudioLinkDuration = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(temp2));
            Log.d("audioLinkDuration ", audioLinkDuration);
            Log.d("altaudioLinkDuration ", altaudioLinkDuration);

            edit.putString("audioDuration", audioLinkDuration);
            edit.commit();

            Uri uri = Uri.parse(path);

            player.setDataSource(getApplicationContext(), uri);

            fileNameG = getFileNameByUri(getApplicationContext(), uri);
            filePath = path;

        } catch (Exception e) {
            // TODO: handle exception
        }
//        try{
//            MediaPlayer player = new MediaPlayer();
//            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            player.setDataSource(path);
//            player.start();
//            audioLinkDuration = String.valueOf(player.getDuration());
//            player.stop();
//            Log.d("audioLinkDuration ", audioLinkDuration);
//
//        }catch(Exception e){
//            System.out.println(e);
//        }
    }


    public double optTotal(double cost, double views) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String opt = preferences.getString("optin_cost", "");

        Log.d("cost ", String.valueOf(cost));
        Log.d("views ", String.valueOf(views));
        Log.d("opt ", String.valueOf(optin));

        double total = (cost + optin) * views;
        return total;
    }


    public double optBack(double cost, double views) {

        Log.d("cost ", String.valueOf(cost));
        Log.d("views ", String.valueOf(views));

        double total = cost * views;
        return total;
    }


    public static String fileFromServerURI(Context context, Uri uri) {
        String fileName = "unknown";
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
                Log.d("fileName ", fileName);
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            fileName = filePathUri.getLastPathSegment().toString();
            Log.d("fileName ", fileName);
        } else {
            fileName = fileName + "_" + filePathUri.getLastPathSegment();
            Log.d("fileName ", fileName);
        }
        return fileName;
    }


    private void openGallery() {
        if (adType.equals("1")) {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("video/*");
            startActivityForResult(pickIntent, PICK_VIDEO);
        }
        if (adType.equals("2")) {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, PICK_IMAGE);
        }
        if (adType.equals("3")) {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_PICK);
            intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_AUDIO);
        }
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        String fileName = "unknown";
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
                Log.d("fileName ", fileName);
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            fileName = filePathUri.getLastPathSegment().toString();
            Log.d("fileName ", fileName);
        } else {
            fileName = fileName + "_" + filePathUri.getLastPathSegment();
            Log.d("fileName ", fileName);
        }
        return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (resultCode == RESULT_OK) {

            Uri selectedVideoUri = data.getData();
            String path = selectedVideoUri.getPath();
            Log.d("NEWPATH ", path);


            if (hasPermissions()) {


                if (adType.equals("3")) {
                    audioInternalOrExternal = "0";
                    fileNameG = getFileNameByUri(getApplicationContext(), selectedVideoUri);
                    filePath = path;
                    etVideoID.setText(path);
                    filePath = getRealPathFromURI(selectedVideoUri);

                    MediaPlayer mp = MediaPlayer.create(this, Uri.parse(filePath));
                    int duration = mp.getDuration();
                    mp.release();
                    double temp = Double.parseDouble(String.format("%d",
                            TimeUnit.MILLISECONDS.toSeconds(duration)
                    ));

                    Log.d("temp ", String.valueOf(temp));
                    audioLinkDuration = String.valueOf(temp);
                    fileDuration = String.valueOf(temp);
                    if (temp > 300) {
                        Toast.makeText(this, "File too long.  Select another file.", Toast.LENGTH_SHORT).show();
                        etVideoID.setText("");
                    }

                    Log.d("AudioStringDuration ", String.valueOf(fileDuration));
                    Log.d("new_path ", filePath);
                }

                if (adType.equals("2")) {
                    imageInternalOrExternal = "0";
                    etVideoID.setText(filePath);
                    filePath = path;
                    filePath = getRealPathFromURI(selectedVideoUri);
                    Log.d("ImagePath ", filePath);
                    Log.d("new_path ", filePath);
                }

                if (adType.equals("1")) {
                    Log.d("TestPath ", "our test path");
                    videoInternalOrExternal = "0";
                    filePath = path;
                    filePath = getRealPathFromURI(selectedVideoUri);
                    etVideoID.setText(filePath);
                    fileDuration = String.valueOf(localDuration(filePath));

//                    MediaPlayer mp = MediaPlayer.create(this, Uri.parse(filePath));
//                    int duration = mp.getDuration() / 1000;
//                    int hours = duration / 3600;
//                    int minutes = (duration / 60) - (hours * 60);
//                    fileDuration = String.valueOf(duration - (hours * 3600) - (minutes * 60));

                    if (Integer.parseInt(fileDuration) > 300) {
                        Toast.makeText(this, "File too long.  Select another file.", Toast.LENGTH_SHORT).show();
                        etVideoID.setText("");
                    }

                    Log.d("seconds ", String.valueOf(fileDuration));
                }

                bChoose.setText("Replace File");

            } else {
                requestPerms();
            }
            Toast.makeText(this, "The file: " + filePath + " has been chosen.", Toast.LENGTH_SHORT).show();
            fileNameG = filePath.substring(filePath.lastIndexOf("/") + 1);

            Log.d("filePath ", filePath);
            Log.d("fileName ", fileNameG);
            etVideoID.setText(filePath);
            etVideoTitle.setTextColor(Color.DKGRAY);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("filePath", filePath);
            editor.commit();

        }

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getRealAudioPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void clearValues() {
        questionCounter = 0;
        boolGender = false;
        boolGenderRG = false;
        boolAge = false;
        boolAgeRG = false;
        boolLocation = false;
        boolLocationRG = false;
        boolInterests = false;

        cbQ1.setChecked(false);
        cbQ2.setChecked(false);
        cbQ3.setChecked(false);
        cbQ4.setChecked(false);
        cbQ5.setChecked(false);
        cbQ6.setChecked(false);
    }


    private boolean hasPermissions() {
        int res = 0;

        String[] permissions = new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;

    }

    private void requestPerms() {
        String[] permissions = new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMS_REQUEST_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }
        if (allowed) {
            //makeUpload();
            //new UploadFileAsync().execute(filePath, fileNameG);
            //uploadFile(filePath);
//            Log.d("requesting permissions", "");
//            new UploadFileAsync().execute("");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void submitReference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (adType.equals("1")) {
            HashMap postData = new HashMap();
            postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("title", preferences.getString("title", ""));
            postData.put("link", preferences.getString("link", ""));
            postData.put("ytID", preferences.getString("ref", ""));
            postData.put("duration", preferences.getString("duration", ""));
            postData.put("cost", finalCost);
            postData.put("totviews", tvPViews.getText().toString());
            postData.put("promo", preferences.getString("promo", ""));
            postData.put("gender", preferences.getString("gender", ""));
            postData.put("ageVal", preferences.getString("age", ""));
            postData.put("location", preferences.getString("location", ""));
            postData.put("interests", preferences.getString("interests", ""));
            postData.put("questVal", preferences.getString("questions", ""));
            if (optChecked == true) {
                postData.put("optin", "1");
            } else {
                postData.put("optin", "0");
            }
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(NewVideoPublishURL);
        }
        if (adType.equals("2")) {

            Log.d("pub id ", preferences.getString("raadz_pub_id", ""));
            Log.d("token ", preferences.getString("token", ""));
            Log.d("title ", preferences.getString("title", ""));
            Log.d("ytid ", preferences.getString("ref", ""));
            Log.d("link ", preferences.getString("link", ""));
            Log.d("views ", tvPViews.getText().toString());
            Log.d("cost ", finalCost);
            Log.d("promo ", preferences.getString("promo", ""));
            Log.d("gender ", preferences.getString("gender", ""));
            Log.d("age ", preferences.getString("age", ""));
            Log.d("location ", preferences.getString("location", ""));
            Log.d("questions ", preferences.getString("questions", ""));
            Log.d("interests ", preferences.getString("interests", ""));

            HashMap postData = new HashMap();
            postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("title", preferences.getString("title", ""));
            postData.put("link", preferences.getString("link", ""));
            postData.put("imgRef", preferences.getString("ref", ""));
            postData.put("cost", finalCost);
            postData.put("totviews", tvPViews.getText().toString());
            postData.put("promo", preferences.getString("promo", ""));
            postData.put("gender", preferences.getString("gender", ""));
            postData.put("ageVal", preferences.getString("age", ""));
            postData.put("location", preferences.getString("location", ""));
            postData.put("interests", preferences.getString("interests", ""));
            postData.put("questVal", preferences.getString("questions", ""));
            if (optChecked == true) {
                postData.put("optin", "1");
            } else {
                postData.put("optin", "0");
            }
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(ImageUploadURL);
        }
        if (adType.equals("3")) {

            HashMap postData = new HashMap();
            postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("title", preferences.getString("title", ""));
            postData.put("link", preferences.getString("link", ""));
            postData.put("audRef", preferences.getString("ref", ""));
            postData.put("duration", preferences.getString("duration", ""));
            postData.put("cost", finalCost);
            postData.put("totviews", tvPViews.getText().toString());
            postData.put("promo", preferences.getString("promo", ""));
            postData.put("gender", preferences.getString("gender", ""));
            postData.put("ageVal", preferences.getString("age", ""));
            postData.put("location", preferences.getString("location", ""));
            postData.put("interests", preferences.getString("interests", ""));
            postData.put("questVal", preferences.getString("questions", ""));
            if (optChecked == true) {
                postData.put("optin", "1");
            } else {
                postData.put("optin", "0");
            }
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(AudioUploadURL);
        }
    }

    public void submitFile() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (adType.equals("1")) {

            new UploadFileAsync().execute(
                    filePath,
                    fileNameG,
                    etVideoID.getText().toString(),
                    etVideoTitle.getText().toString(),
                    etVideoInfo.getText().toString(),
                    preferences.getString("raadz_pub_id", ""),
                    finalCost,
                    ADPreferences.getString("promo", ""),
                    finalViews,
                    ADPreferences.getString("nvGender", ""),
                    ADPreferences.getString("nvAge", ""),
                    ADPreferences.getString("nvLocation", ""),
                    ADPreferences.getString("nvInterests", ""),
                    ADPreferences.getString("nvQuestions", ""),
                    "",
                    "",
                    finalCost,
                    "Pay with Direct"
            );
        }
        if (adType.equals("2")) {

            new UploadFileAsync().execute(
                    filePath,
                    fileNameG,
                    etVideoID.getText().toString(),
                    etVideoTitle.getText().toString(),
                    etVideoInfo.getText().toString(),
                    preferences.getString("raadz_pub_id", ""),
                    finalCost,
                    ADPreferences.getString("promo", ""),
                    finalViews,
                    ADPreferences.getString("nvGender", ""),
                    ADPreferences.getString("nvAge", ""),
                    ADPreferences.getString("nvLocation", ""),
                    ADPreferences.getString("nvInterests", ""),
                    ADPreferences.getString("nvQuestions", ""),
                    "",
                    "",
                    finalCost,
                    "Pay with Direct"
            );

        }

        if (adType.equals("3")) {

            new UploadFileAsync().execute(
                    filePath,
                    fileNameG,
                    etVideoID.getText().toString(),
                    etVideoTitle.getText().toString(),
                    etVideoInfo.getText().toString(),
                    preferences.getString("raadz_pub_id", ""),
                    finalCost,
                    ADPreferences.getString("promo", ""),
                    finalViews,
                    ADPreferences.getString("nvGender", ""),
                    ADPreferences.getString("nvAge", ""),
                    ADPreferences.getString("nvLocation", ""),
                    ADPreferences.getString("nvInterests", ""),
                    ADPreferences.getString("nvQuestions", "")
            );
        }
    }

    private void makeUpload() {

        //uploadFile(filePath);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Test");

        if (!file.exists()) {
            Boolean ff = file.mkdir();
            if (ff) {
                Toast.makeText(this, "Folder created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to create folder", Toast.LENGTH_SHORT).show();
            }
            // file.mkdir();
        } else {
            Toast.makeText(this, "Folder already exists", Toast.LENGTH_SHORT).show();
        }

    }

    public void arrayValues() {
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


    @Override
    public void processFinish(String result) {

        if (buttonPlaceholder.equals("bNext1")) {
            if (previewCheck == false) {
                Log.d("bNext1 Result ", result);
                LLCheckCopy.removeAllViews();
                questionCounter = 0;
                questionValue = 0;
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        CheckBox cb = new CheckBox(this);
                        //if(!jObj.getJSONObject("quest_text").equals("null")) {
                        Log.d("jObj q ", jObj.getString("quest_text"));
                        if (jObj.getString("quest_text").equals("null")) {
                            Log.d("in the if ", jObj.getString("quest_text"));
                        } else {
                            cb.setText(jObj.getString("quest_text"));
                            cb.setId(i + jArray.length());
                            LLCheckCopy.addView(cb);
                            ok = jArray.length();
                        }
                        Log.d("cb length ", String.valueOf(cb.length()));

                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                int s = (Integer.parseInt(String.valueOf(buttonView.getId())) - ok) + 1;
                                Log.d("ok ", String.valueOf(ok));
                                Log.d("string s ", String.valueOf(s));
                                if (s == 1) {
                                    if (b == true) {
                                        questionValue = questionValue + 1;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 1;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }
                                if (s == 2) {
                                    if (b == true) {
                                        questionValue = questionValue + 2;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionsCounter ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 2;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionsCounter ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 3) {
                                    if (b == true) {
                                        questionValue = questionValue + 4;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 4;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 4) {
                                    if (b == true) {
                                        questionValue = questionValue + 8;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 8;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 5) {
                                    if (b == true) {
                                        questionValue = questionValue + 16;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 16;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 6) {
                                    if (b == true) {
                                        questionValue = questionValue + 32;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 32;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 7) {
                                    if (b == true) {
                                        questionValue = questionValue + 64;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 64;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 8) {
                                    if (b == true) {
                                        questionValue = questionValue + 128;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 128;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 9) {
                                    if (b == true) {
                                        questionValue = questionValue + 256;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 256;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 10) {
                                    if (b == true) {
                                        questionValue = questionValue + 512;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 512;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 11) {
                                    if (b == true) {
                                        questionValue = questionValue + 1024;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 1024;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }

                                if (s == 12) {
                                    if (b == true) {
                                        questionValue = questionValue + 2048;
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - 2048;
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValue ", String.valueOf(questionValue));
                                        Log.d("questionCoutner ", String.valueOf(questionCounter));
                                    }
                                }
                            }
                        });
                        //}
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }
            } else if (previewCheck == true) {
                LLCheckCopy.removeAllViews();
                questionCounter = 4;
                questionValue = 0;
                for (int i = 0; i < q_resub_value.length; i++) {
                    Log.d("resubArray ", String.valueOf(q_resub_value[i]));
                }
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        CheckBox cb = new CheckBox(this);
                        if (jObj.getString("quest_text").equals("null")) {

                        } else {
                            cb.setText(jObj.getString("quest_text"));
                            cb.setId(i);
                            LLCheckCopy.addView(cb);
                            ok = jArray.length();
                        }
                        for (int u = 0; u < q_resub_value.length; u++) {
                            Log.d("checkValue ", String.valueOf(cb.getId()));
                            Log.d("uValue ", String.valueOf(u));
                            if (q_resub_value[u] != 0) {
                                int id = cb.getId();
                                if (id == u) {
                                    questionValue = questionValue + q_resub_value[u];
                                    cb.setChecked(true);
                                }
                            }
                        }
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                int s = (Integer.parseInt(String.valueOf(buttonView.getId()))) + 1;
                                if (s >= 1) {
                                    if (b == true) {
                                        questionValue = questionValue + q_values[buttonView.getId()];
                                        questionCounter = questionCounter + 1;
                                        Log.d("questionValues ", String.valueOf(questionValue));
                                        Log.d("questionCounter ", String.valueOf(questionCounter));
                                    } else {
                                        questionValue = questionValue - q_values[buttonView.getId()];
                                        questionCounter = questionCounter - 1;
                                        Log.d("questionValues ", String.valueOf(questionValue));
                                        Log.d("questionCounter ", String.valueOf(questionCounter));
                                    }
                                }

                            }
                        });
                    }

                } catch (org.json.JSONException e) {

                }


            }
        }

        if (buttonPlaceholder.equals("bNext2")) {
            location = result;
            String kept;
            String remainder;
            List<String> list = new ArrayList<String>();
            list.add("-Select a City-");
            Log.d("bNext1 ", result);
            for (int j = 0; j < result.length(); j++) {
                try {
                    kept = location.substring(0, location.indexOf(":"));
                    remainder = location.substring(location.indexOf(":") + 1, location.length());
                    location = remainder;
                    list.add(kept);
                    Log.d("kept ", kept);
                    Log.d("remainder ", remainder);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }

            }
            Log.d("locationList ", location);

            if (previewCheck == true) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PubSubmitActivity.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                int position = adapter.getPosition(preferences.getString("location", ""));
                Log.d("position ", String.valueOf(preferences.getString("location", "")));
                Log.d("position String ", preferences.getString("location", ""));
                spinnerDefault.setAdapter(adapter);
                spinnerDefault.setSelection(position);

                boolLocation = true;
                boolLocationRG = true;


                int age = Integer.parseInt(preferences.getString("age", ""));
                preloadAge(age);

                preloadGender(preferences.getString("gender", ""));

            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PubSubmitActivity.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                int position = adapter.getPosition(location);
                Log.d("position ", String.valueOf(location));
                Log.d("position String ", location);
                spinnerDefault.setAdapter(adapter);
                spinnerDefault.setSelection(position);
            }


        }

        if (buttonPlaceholder.equals("bNext3")) {
            if (previewCheck == true) {
                boolInterests = true;
                boolInterestsCB = true;
                bInterestsYes.setBackgroundResource(R.drawable.my_button_clicked);
                bInterestsYes.setTextColor(Color.GREEN);
                bInterestsNo.setBackgroundResource(R.drawable.my_button_index);
                bInterestsNo.setTextColor(Color.WHITE);
                LLInterestsCheckBoxes.setVisibility(View.VISIBLE);
                Log.d("bNext3 Result ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                interestsCounter = Integer.parseInt(preferences.getString("interests", ""));
                preloadInterests(interestsCounter);
                LLInterestCopy.removeAllViews();
                LLInterestCopy2.removeAllViews();
                for (int j = 0; j < interests_array.length; j++) {
                    if (interestsCounter >= interests_array_temp[j]) {
                        Log.d(" - ", " - ");
                        Log.d("j ", String.valueOf(j));
                        Log.d("the_counter ", String.valueOf(interestsCounter));
                        Log.d("the_value ", String.valueOf((int) interests_array_temp[j]));
                        interests_place[j] = interests_array_temp[j];
                        interestsCounter = interestsCounter - (int) interests_array_temp[j];
                        Log.d("interestsCounter ", String.valueOf(interestsCounter));
                        Log.d(" - ", " - ");
                    }
                }
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int a_i = 0; a_i < jArray.length(); a_i++) {
                        JSONObject jObj = jArray.getJSONObject(a_i);
                        try {
                            Log.d("a_i ", jObj.getString("interest_value"));
                            interests_number_array[a_i] = Long.parseLong(jObj.getString("interest_value"));
                        } catch (NumberFormatException e) {

                        }
                    }

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        CheckBox cb = new CheckBox(this);
                        //if(!jObj.getJSONObject("quest_text").equals("null")) {
                        Log.d("jObj q ", jObj.getString("interest_text"));
                        if (jObj.getString("interest_text").equals("null")) {
                            Log.d("in the if ", jObj.getString("interest_text"));
                        } else {
                            cb.setText(jObj.getString("interest_text"));
                            cb.setId(i);

                            if (layout_track == false) {
                                LLInterestCopy.addView(cb);
                                ok = jArray.length();
                                layout_track = true;
                            } else if (layout_track == true) {
                                layout_track = false;
                                LLInterestCopy2.addView(cb);
                                ok = jArray.length();
                            }

                            for (int y = 0; y < interests_number_array.length; y++) {
                                Log.d("interests_number_array ", String.valueOf(interests_number_array[y]));
                            }
                            Log.d(" - ", " - ");
                            Log.d(" - ", " - ");
                            for (int w = 0; w < interests_place.length; w++) {
                                Log.d("interests_place ", String.valueOf(interests_place[w]));
                            }

                            for (int j = 0; j < interests_place.length; j++) {
                                for (int k = 0; k < interests_number_array.length; k++) {
                                    if (interests_number_array[k] == interests_place[j]) {
                                        int id = cb.getId();
                                        if (id == k) {
                                            cb.setChecked(true);
                                            Log.d("check_id ", String.valueOf(id));
                                        }
                                    }
                                }
                            }


                        }
                        Log.d("cb length ", String.valueOf(cb.length()));

                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                int s = (Integer.parseInt(String.valueOf(buttonView.getId()))) + 1;
                                Log.d("ok ", String.valueOf(ok));
                                Log.d("string s ", String.valueOf(s));


                                if (s >= 1) {
                                    if (b == true) {
                                        numOfInterests += 1;
                                        boolInterestsCB = true;
//                                    Log.d("array_value ", String.valueOf(interests_array[s - 1]));
                                        interestsCounter = interestsCounter + interests_number_array[buttonView.getId()];
                                        interestsNumber = interestsNumber + 1;
                                        Log.d("interestCounter ", String.valueOf(interestsCounter));
                                        Log.d("interestNumber ", String.valueOf(interestsNumber));
                                    } else {
                                        numOfInterests -= 1;
                                        interestsCounter = interestsCounter - interests_number_array[buttonView.getId()];
                                        interestsNumber = interestsNumber - 1;
                                        Log.d("interestCounter ", String.valueOf(interestsCounter));
                                        Log.d("interestNumber ", String.valueOf(interestsNumber));
                                    }
                                }
                            }
                        });
                        //}
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }
            } else {

                LLInterestCopy.removeAllViews();
                LLInterestCopy2.removeAllViews();
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int a_i = 0; a_i < jArray.length(); a_i++) {
                        JSONObject jObj = jArray.getJSONObject(a_i);
                        try {
                            Log.d("a_i ", jObj.getString("interest_value"));
                            Log.d("i ", String.valueOf(a_i));
                            interests_number_array[a_i] = Long.parseLong(jObj.getString("interest_value"));
                        } catch (NumberFormatException e) {

                        }
                    }
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        CheckBox cb = new CheckBox(this);
                        //if(!jObj.getJSONObject("quest_text").equals("null")) {
                        Log.d("jObj q ", jObj.getString("interest_text"));
                        if (jObj.getString("interest_text").equals("null")) {
                            Log.d("in the if ", jObj.getString("interest_text"));
                        } else {
                            cb.setText(jObj.getString("interest_text"));
                            cb.setId(i);

                            Log.d(" - ", " - ");
                            Log.d("preCheckID ", String.valueOf(i));
                            Log.d("preCheckValue ", String.valueOf(interests_number_array[i]));
                            Log.d(" - ", " - ");


                            if (layout_track == false) {
                                LLInterestCopy.addView(cb);
                                ok = jArray.length();
                                layout_track = true;
                            } else if (layout_track == true) {
                                layout_track = false;
                                LLInterestCopy2.addView(cb);
                                ok = jArray.length();
                            }
//                        cb.setText(jObj.getString("interest_text"));
//                        cb.setId(i + jArray.length());
//                        LLInterestCopy.addView(cb);
//                        ok = jArray.length();

                        }
                        Log.d("cb length ", String.valueOf(cb.length()));

                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                int s = (Integer.parseInt(String.valueOf(buttonView.getId()))) + 1;
                                Log.d("ok ", String.valueOf(ok));
                                Log.d("string s ", String.valueOf(s));


                                if (s >= 1) {
                                    if (b == true) {
                                        numOfInterests += 1;
                                        boolInterestsCB = true;
//                                    Log.d("array_value ", String.valueOf(interests_array[s - 1]));
                                        interestsCounter = interestsCounter + interests_number_array[buttonView.getId()];
                                        interestsNumber = interestsNumber + 1;
                                        Log.d("interestCounter ", String.valueOf(interestsCounter));
                                        Log.d("interestNumber ", String.valueOf(interestsNumber));
                                    } else {
                                        numOfInterests -= 1;
                                        interestsCounter = interestsCounter - interests_number_array[buttonView.getId()];
                                        interestsNumber = interestsNumber - 1;
                                        Log.d("interestCounter ", String.valueOf(interestsCounter));
                                        Log.d("interestNumber ", String.valueOf(interestsNumber));
                                    }
                                }
                            }
                        });
                        //}
                    }
                } catch (org.json.JSONException e) {
                    System.out.println();
                }
            }
        }


        if (buttonPlaceholder.equals("bNext4")) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    DataFunction(etVideoID.getText().toString());
                }
            };

            thread.start();
            try {
                if (videoInternalOrExternal.equals("1")) {
                    int s_temp = Integer.parseInt(allMatches.get(1));
                    int m_temp = Integer.parseInt(allMatches.get(0));
                    t_temp = (m_temp * 60) + s_temp;
                }
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                JSONArray jArray = new JSONArray(result);
                Log.d("json: ", result);
                for (int i = 0; i < jArray.length(); i++) {
                    Log.d("i: ", String.valueOf(i));
                    JSONObject jObj = jArray.getJSONObject(i);
                    if (jObj.getString("pub_id").equals("any")) {

                        Log.d("optin ", jObj.getString("optin_cost"));
                        optin = Double.parseDouble(jObj.getString("optin_cost"));
                        Log.d(" --- ", " --- ");
                        costPPV1 = jObj.getString("ppv_1");
                        costPPV2 = jObj.getString("ppv_2");
                        costPPV3 = jObj.getString("ppv_3");
                        costPPV4 = jObj.getString("ppv_4");
                        costPPV5 = jObj.getString("ppv_5");

                        Double v_one = Double.parseDouble(costPPV1);
                        Double v_two = Double.parseDouble(costPPV2);
                        Double v_three = Double.parseDouble(costPPV3);
                        Double v_four = Double.parseDouble(costPPV4);
                        Double v_five = Double.parseDouble(costPPV5);

                        Log.d("duration ", String.valueOf(fileDuration));
                        Log.d("price ", jObj.getString("extra_dur_cost4"));
                        Log.d(" - ", " - ");
                        Log.d("1 ", jObj.getString("extra_dur_cost1"));
                        Log.d("2 ", jObj.getString("extra_dur_cost2"));
                        Log.d("3 ", jObj.getString("extra_dur_cost3"));
                        Log.d("4 ", jObj.getString("extra_dur_cost4"));
                        Log.d("v_one ", String.valueOf(v_one));
                        Log.d("v_two ", String.valueOf(v_two));
                        Log.d("v_three ", String.valueOf(v_three));
                        Log.d("v_four ", String.valueOf(v_four));
                        Log.d("v_five ", String.valueOf(v_five));

                        if (adType.equals("1")) {
                            if (videoInternalOrExternal.equals("1")) {
                                DataFunction(etVideoID.getText().toString());
                                youtubeLinkDuration = String.valueOf(t_temp);
                            }
//                                int seconds = (Integer.parseInt(youtubeLinkDuration) / 1000) % 60;
                            youtubeLinkDuration = String.valueOf(t_temp);
                            Log.d("Duration_2 ", String.valueOf(youtubeLinkDuration));

                            Log.d("jObj ", String.valueOf(jObj.getString("extra_dur_cost4").replace(".", "")));
                            try {
                                if (Integer.parseInt(youtubeLinkDuration) > 121) {
                                    extraTime = true;
                                    tvDurationPrompt.setText("Additional cost per view: $0.020 (video longer than 0:30");
                                    tvLargeFile.setVisibility(View.VISIBLE);
                                    Log.d("TAG ", "first if");
                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                    Log.d("costAfter1 ", String.valueOf(l_val1));
                                    Log.d("costAfter2 ", String.valueOf(l_val2));
                                    Log.d("costAfter3 ", String.valueOf(l_val3));
                                    Log.d("costAfter4 ", String.valueOf(l_val4));
                                    Log.d("costAfter5 ", String.valueOf(l_val5));
                                } else if (Integer.parseInt(youtubeLinkDuration) > 91) {
                                    extraTime = true;
                                    tvDurationPrompt.setText("Additional cost per view: $0.020 (video longer than 0:30");
                                    tvLargeFile.setVisibility(View.VISIBLE);
                                    Log.d("TAG ", "second if");
                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                    Log.d("costAfter1 ", String.valueOf(l_val1));
                                    Log.d("costAfter2 ", String.valueOf(l_val2));
                                    Log.d("costAfter3 ", String.valueOf(l_val3));
                                    Log.d("costAfter4 ", String.valueOf(l_val4));
                                    Log.d("costAfter5 ", String.valueOf(l_val5));
                                } else if (Integer.parseInt(youtubeLinkDuration) > 61) {
                                    extraTime = true;
                                    tvDurationPrompt.setText("Additional cost per view: $0.020 (video longer than 0:30");
                                    tvLargeFile.setVisibility(View.VISIBLE);
                                    Log.d("TAG ", "third if");
                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                    Log.d("costAfter1 ", String.valueOf(l_val1));
                                    Log.d("costAfter2 ", String.valueOf(l_val2));
                                    Log.d("costAfter3 ", String.valueOf(l_val3));
                                    Log.d("costAfter4 ", String.valueOf(l_val4));
                                    Log.d("costAfter5 ", String.valueOf(l_val5));
                                } else if (Integer.parseInt(youtubeLinkDuration) > 31) {
                                    extraTime = true;
                                    tvDurationPrompt.setText("Additional cost per view: $0.020 (video longer than 0:30");
                                    tvLargeFile.setVisibility(View.VISIBLE);
                                    Log.d("TAG ", "fourth if");
                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                    Log.d("costAfter1 ", String.valueOf(l_val1));
                                    Log.d("costAfter2 ", String.valueOf(l_val2));
                                    Log.d("costAfter3 ", String.valueOf(l_val3));
                                    Log.d("costAfter4 ", String.valueOf(l_val4));
                                    Log.d("costAfter5 ", String.valueOf(l_val5));
                                } else {
                                    l_val1 = Double.parseDouble(jObj.getString("ppv_1"));
                                    l_val2 = Double.parseDouble(jObj.getString("ppv_2"));
                                    l_val3 = Double.parseDouble(jObj.getString("ppv_3"));
                                    l_val4 = Double.parseDouble(jObj.getString("ppv_4"));
                                    l_val5 = Double.parseDouble(jObj.getString("ppv_5"));
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(e);
                            }

                        }

//                        } else if (adType.equals("1") && videoInternalOrExternal.equals("0") || adType.equals("3") && audioInternalOrExternal.equals("0")) {
//                            double time = Double.parseDouble(fileDuration);
//
//                            Log.d("time ", String.valueOf(time));
//                            Log.d("jObj ", String.valueOf(jObj.getString("extra_dur_cost4").replace(".", "")));
//                            try {
//                                if (time > Double.parseDouble(jObj.getString("extra_dur_cost4").replace(".", ""))) {
//                                    tvLargeFile.setVisibility(View.VISIBLE);
//                                    Log.d("TAG ", "first if");
//                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost4"));
//                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost4"));
//                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost4"));
//                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost4"));
//                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost4"));
//                                    Log.d("costAfter1 ", String.valueOf(l_val1));
//                                    Log.d("costAfter2 ", String.valueOf(l_val2));
//                                    Log.d("costAfter3 ", String.valueOf(l_val3));
//                                    Log.d("costAfter4 ", String.valueOf(l_val4));
//                                    Log.d("costAfter5 ", String.valueOf(l_val5));
//                                } else if (time > Double.parseDouble(jObj.getString("extra_dur_cost3").replace(".", ""))) {
//                                    tvLargeFile.setVisibility(View.VISIBLE);
//                                    Log.d("TAG ", "second if");
//                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost3"));
//                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost3"));
//                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost3"));
//                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost3"));
//                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost3"));
//                                    Log.d("costAfter1 ", String.valueOf(l_val1));
//                                    Log.d("costAfter2 ", String.valueOf(l_val2));
//                                    Log.d("costAfter3 ", String.valueOf(l_val3));
//                                    Log.d("costAfter4 ", String.valueOf(l_val4));
//                                    Log.d("costAfter5 ", String.valueOf(l_val5));
//                                } else if (time > Double.parseDouble(jObj.getString("extra_dur_cost2").replace(".", ""))) {
//                                    tvLargeFile.setVisibility(View.VISIBLE);
//                                    Log.d("TAG ", "third if");
//                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost2"));
//                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost2"));
//                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost2"));
//                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost2"));
//                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost2"));
//                                    Log.d("costAfter1 ", String.valueOf(l_val1));
//                                    Log.d("costAfter2 ", String.valueOf(l_val2));
//                                    Log.d("costAfter3 ", String.valueOf(l_val3));
//                                    Log.d("costAfter4 ", String.valueOf(l_val4));
//                                    Log.d("costAfter5 ", String.valueOf(l_val5));
//                                } else if (time > Double.parseDouble(jObj.getString("extra_dur_cost1").replace(".", ""))) {
//                                    tvLargeFile.setVisibility(View.VISIBLE);
//                                    Log.d("TAG ", "fourth if");
//                                    l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost1"));
//                                    l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost1"));
//                                    l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost1"));
//                                    l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost1"));
//                                    l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost1"));
//                                    Log.d("costAfter1 ", String.valueOf(l_val1));
//                                    Log.d("costAfter2 ", String.valueOf(l_val2));
//                                    Log.d("costAfter3 ", String.valueOf(l_val3));
//                                    Log.d("costAfter4 ", String.valueOf(l_val4));
//                                    Log.d("costAfter5 ", String.valueOf(l_val5));
//                                } else {
//                                    l_val1 = Double.parseDouble(jObj.getString("ppv_1"));
//                                    l_val2 = Double.parseDouble(jObj.getString("ppv_2"));
//                                    l_val3 = Double.parseDouble(jObj.getString("ppv_3"));
//                                    l_val4 = Double.parseDouble(jObj.getString("ppv_4"));
//                                    l_val5 = Double.parseDouble(jObj.getString("ppv_5"));
//                                }
//                            } catch (NumberFormatException e) {
//                                System.out.println(e);
//                            }
//                        }
                        try {
                            if (adType.equals("3")) {
                                double time = Double.parseDouble(audioLinkDuration);

                                Log.d("time ", String.valueOf(time));
                                Log.d("jObj ", String.valueOf(jObj.getString("extra_dur_cost4").replace(".", "")));
                                try {
                                    if (time > 121) {
                                        extraTime = true;
                                        tvLargeFile.setVisibility(View.VISIBLE);
                                        Log.d("TAG ", "first if");
                                        l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                        l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                        l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                        l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                        l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost4"));
                                        Log.d("costAfter1 ", String.valueOf(l_val1));
                                        Log.d("costAfter2 ", String.valueOf(l_val2));
                                        Log.d("costAfter3 ", String.valueOf(l_val3));
                                        Log.d("costAfter4 ", String.valueOf(l_val4));
                                        Log.d("costAfter5 ", String.valueOf(l_val5));
                                    } else if (time > 91) {
                                        extraTime = true;
                                        tvLargeFile.setVisibility(View.VISIBLE);
                                        Log.d("TAG ", "second if");
                                        l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                        l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                        l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                        l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                        l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost3"));
                                        Log.d("costAfter1 ", String.valueOf(l_val1));
                                        Log.d("costAfter2 ", String.valueOf(l_val2));
                                        Log.d("costAfter3 ", String.valueOf(l_val3));
                                        Log.d("costAfter4 ", String.valueOf(l_val4));
                                        Log.d("costAfter5 ", String.valueOf(l_val5));
                                    } else if (time > 61) {
                                        extraTime = true;
                                        tvLargeFile.setVisibility(View.VISIBLE);
                                        Log.d("TAG ", "third if");
                                        l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                        l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                        l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                        l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                        l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost2"));
                                        Log.d("costAfter1 ", String.valueOf(l_val1));
                                        Log.d("costAfter2 ", String.valueOf(l_val2));
                                        Log.d("costAfter3 ", String.valueOf(l_val3));
                                        Log.d("costAfter4 ", String.valueOf(l_val4));
                                        Log.d("costAfter5 ", String.valueOf(l_val5));
                                    } else if (time > 31) {
                                        extraTime = true;
                                        tvLargeFile.setVisibility(View.VISIBLE);
                                        Log.d("TAG ", "fourth if");
                                        l_val1 = v_one + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                        l_val2 = v_two + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                        l_val3 = v_three + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                        l_val4 = v_four + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                        l_val5 = v_five + Double.parseDouble(jObj.getString("extra_dur_cost1"));
                                        Log.d("costAfter1 ", String.valueOf(l_val1));
                                        Log.d("costAfter2 ", String.valueOf(l_val2));
                                        Log.d("costAfter3 ", String.valueOf(l_val3));
                                        Log.d("costAfter4 ", String.valueOf(l_val4));
                                        Log.d("costAfter5 ", String.valueOf(l_val5));
                                    } else {
                                        l_val1 = Double.parseDouble(jObj.getString("ppv_1"));
                                        l_val2 = Double.parseDouble(jObj.getString("ppv_2"));
                                        l_val3 = Double.parseDouble(jObj.getString("ppv_3"));
                                        l_val4 = Double.parseDouble(jObj.getString("ppv_4"));
                                        l_val5 = Double.parseDouble(jObj.getString("ppv_5"));
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println(e);
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }

                        DecimalFormat df2 = new DecimalFormat("###.##");

                        l_val1 = Double.valueOf(df2.format(l_val1));
                        l_val2 = Double.valueOf(df2.format(l_val2));
                        l_val3 = Double.valueOf(df2.format(l_val3));
                        l_val4 = Double.valueOf(df2.format(l_val4));
                        l_val5 = Double.valueOf(df2.format(l_val5));

                        Log.d("one ", String.valueOf(l_val1));
                        Log.d("two ", String.valueOf(l_val2));
                        Log.d("three ", String.valueOf(l_val3));
                        Log.d("four ", String.valueOf(l_val4));
                        Log.d("five ", String.valueOf(l_val5));

                        if (!adType.equals("2")) {
                            costPPV1 = String.valueOf(l_val1);
                            costPPV2 = String.valueOf(l_val2);
                            costPPV3 = String.valueOf(l_val3);
                            costPPV4 = String.valueOf(l_val4);
                            costPPV5 = String.valueOf(l_val5);

                            Log.d("costPPV1", costPPV1);
                            Log.d("costPPV2", costPPV2);
                            Log.d("costPPV3", costPPV3);
                            Log.d("costPPV4", costPPV4);
                            Log.d("costPPV5", costPPV5);

                            edit.putString("costPPV1", "");
                            edit.putString("costPPV2", "");
                            edit.putString("costPPV3", "");
                            edit.putString("costPPV4", "");
                            edit.putString("costPPV5", "");
                            edit.commit();


                        }
                        if (adType.equals("2")) {

                            if (jObj.getString("optin_cost").equals("null")) {
                                Log.d("null ", "it equal'd null");
                            } else {
                                edit.putString("optin_cost", jObj.getString("optin_cost"));
                                edit.commit();
                            }

                            costPPV1 = String.valueOf(v_one);
                            costPPV2 = String.valueOf(v_two);
                            costPPV3 = String.valueOf(v_three);
                            costPPV4 = String.valueOf(v_four);
                            costPPV5 = String.valueOf(v_five);

                            Log.d("costPPV1", costPPV1);
                            Log.d("costPPV2", costPPV2);
                            Log.d("costPPV3", costPPV3);
                            Log.d("costPPV4", costPPV4);
                            Log.d("costPPV5", costPPV5);


                            edit.putString("costPPV1", "");
                            edit.putString("costPPV2", "");
                            edit.putString("costPPV3", "");
                            edit.putString("costPPV4", "");
                            edit.putString("costPPV5", "");
                            edit.commit();
                        }

//                        if(extraTime == false){
//                            costPPV1 = String.valueOf(v_one);
//                            costPPV2 = String.valueOf(v_two);
//                            costPPV3 = String.valueOf(v_three);
//                            costPPV4 = String.valueOf(v_four);
//                            costPPV5 = String.valueOf(v_five);
//
//                            Log.d("costPPV1", costPPV1);
//                            Log.d("costPPV2", costPPV2);
//                            Log.d("costPPV3", costPPV3);
//                            Log.d("costPPV4", costPPV4);
//                            Log.d("costPPV5", costPPV5);
//
//
//                            edit.putString("costPPV1", "");
//                            edit.putString("costPPV2", "");
//                            edit.putString("costPPV3", "");
//                            edit.putString("costPPV4", "");
//                            edit.putString("costPPV5", "");
//                            edit.commit();
//                        }

                        double t1 = Double.parseDouble(viewPPV1);
                        double t2 = Double.parseDouble(viewPPV2);
                        double t3 = Double.parseDouble(viewPPV3);
                        double t4 = Double.parseDouble(viewPPV4);
                        double t5 = Double.parseDouble(viewPPV5);
                        //double t6 = Double.parseDouble(costPPV1);
                        //Log.d("t6 ", String.valueOf(t6));
                        DecimalFormat df = new DecimalFormat("###.#");
                        bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                        bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                        bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                        bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                        bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                    } else {
                        Log.d(" --- ", " --- ");
                        viewPPV1 = jObj.getString("ppv_1");
                        viewPPV2 = jObj.getString("ppv_2");
                        viewPPV3 = jObj.getString("ppv_3");
                        viewPPV4 = jObj.getString("ppv_4");
                        viewPPV5 = jObj.getString("ppv_5");
                        edit.putString("viewPPV1", "");
                        edit.putString("viewPPV2", "");
                        edit.putString("viewPPV3", "");
                        edit.putString("viewPPV4", "");
                        edit.putString("viewPPV5", "");
                        edit.commit();

                        double t1 = Double.parseDouble(viewPPV1);
                        double t2 = Double.parseDouble(viewPPV2);
                        double t3 = Double.parseDouble(viewPPV3);
                        double t4 = Double.parseDouble(viewPPV4);
                        double t5 = Double.parseDouble(viewPPV5);
                        //double t6 = Double.parseDouble(costPPV1);
                        //Log.d("t6 ", String.valueOf(t6));
                        DecimalFormat df = new DecimalFormat("###.#");
                        //bViews1.setText(df.format(t6) + "\n");
                        Log.d("cost ", String.valueOf(costPPV1));
//                        bViews1.setText(df.format(t1) + "\n");
//                        bViews2.setText(df.format(t2) + "\n");
//                        bViews3.setText(df.format(t3) + "\n");
//                        bViews4.setText(df.format(t4) + "\n");
//                        bViews5.setText(df.format(t5) + "\n");
                        Log.d("vpv 1 ", viewPPV1);
                        Log.d("vpv 2 ", viewPPV2);
                        Log.d("vpv 3 ", viewPPV3);
                        Log.d("vpv 4 ", viewPPV4);
                        Log.d("vpv 5 ", viewPPV5);
                    }
                    Log.d("i: ", String.valueOf(i));
                    Log.d("Array Length: ", String.valueOf(jArray.length()));
                    Log.d(" --- ", " --- ");
                }


            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }

        }

        if (buttonPlaceholder == "bApply") {
            try {
                if (result.contains("invalid post")) {
                    Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                } else if (result.contains("error getting cost")) {
                    Toast.makeText(this, "Error getting the cost...", Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jArray = new JSONArray(result);
                    Log.d("json: ", result);
                    for (int i = 0; i < jArray.length(); i++) {
                        Log.d("i: ", String.valueOf(i));
                        JSONObject jObj = jArray.getJSONObject(i);
                        if (jObj.getString("promo_code").equals(etPromo.getText().toString())) {
                            Log.d(" --- ", " --- ");
                            costPPV1 = jObj.getString("ppv_1");
                            costPPV2 = jObj.getString("ppv_2");
                            costPPV3 = jObj.getString("ppv_3");
                            costPPV4 = jObj.getString("ppv_4");
                            costPPV5 = jObj.getString("ppv_5");
                            Log.d("cpv 1 ", String.valueOf(costPPV1));
                            Log.d("cpv 2 ", String.valueOf(costPPV2));
                            Log.d("cpv 3 ", String.valueOf(costPPV3));
                            Log.d("cpv 4 ", String.valueOf(costPPV4));
                            Log.d("cpv 5 ", String.valueOf(costPPV5));
                            Toast.makeText(this, "Promo code successfully applied!", Toast.LENGTH_SHORT).show();
                            tvTotal.setText("");
                            if (jObj.getString("promo_code").length() < 2) {
                                tvPromoDisplay.setText("");
                            }
                            tvPromoDisplay.setText(jObj.getString("promo_code"));

                            bViews1.setBackgroundResource(R.drawable.my_button_round);
                            bViews1.setTextColor(Color.WHITE);

                            bViews2.setBackgroundResource(R.drawable.my_button_round);
                            bViews2.setTextColor(Color.WHITE);

                            bViews3.setBackgroundResource(R.drawable.my_button_round);
                            bViews3.setTextColor(Color.WHITE);

                            bViews4.setBackgroundResource(R.drawable.my_button_round);
                            bViews4.setTextColor(Color.WHITE);

                            bViews5.setBackgroundResource(R.drawable.my_button_round);
                            bViews5.setTextColor(Color.WHITE);

                            //totalCost = parseDouble(viewPPV2) * parseDouble(costPPV2);
                            //totalCost = parseDouble(new DecimalFormat("##.###").format(totalCost));
                            //tvTotal.setText("$" + String.format("%.2f", totalCost));
                            totalValueDisplay = (String.format("%.2f", totalCost));
                            Log.d("totalCost ", String.valueOf(totalCost));

                            double t1 = Double.parseDouble(viewPPV1);
                            double t2 = Double.parseDouble(viewPPV2);
                            double t3 = Double.parseDouble(viewPPV3);
                            double t4 = Double.parseDouble(viewPPV4);
                            double t5 = Double.parseDouble(viewPPV5);
                            DecimalFormat df = new DecimalFormat("###.#");
                            bViews1.setText(df.format(t1) + "\n" + String.valueOf(costPPV1));
                            bViews2.setText(df.format(t2) + "\n" + String.valueOf(costPPV2));
                            bViews3.setText(df.format(t3) + "\n" + String.valueOf(costPPV3));
                            bViews4.setText(df.format(t4) + "\n" + String.valueOf(costPPV4));
                            bViews5.setText(df.format(t5) + "\n" + String.valueOf(costPPV5));
                        }

                    }
                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }

        }

        if (buttonPlaceholder == "bChoose") {
            Log.d("Other ", result);
            Log.d("bChoose fileChoose ", fileNameG);
        }

        if (buttonPlaceholder == "bUpload") {
            Log.d("result ", result);
            //
        }

        if (buttonPlaceholder == "bPost") {
            Log.d(" - ", " - ");
            Log.d("bPost Result ", result);
            Log.d(" - ", " - ");
            if (result.contains("Insufficient Account Balance")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("invalid promo code")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("Publisher not found")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("promo code expired")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("Video Title cannot be blank")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("invalid call")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("Invalid Video Length")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("You must select four (4) questions")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.contains("You may only select four (4) questions")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (result.equals("new video added")) {
                Toast.makeText(this, "Ad Successfully Uploaded!!!!", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            } else if (result.equals("new image added")) {
                Toast.makeText(this, "Ad Successfully Uploaded!!!!", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            } else if (result.equals("new audio added")) {
                Toast.makeText(this, "Ad Successfully Uploaded!!!!", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            }
        }

        if (buttonPlaceholder == "bAddFunds") {
            Log.d("AddFunds_Result ", result);
            info = result;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                    tvMoney.setText("$" + preferences.getString("cash", ""));
                    Log.d("tvMoney ", tvMoney.getText().toString());
                }
            });
        }

        if (buttonPlaceholder.equals("bDirect")) {
            if (result.contains("new video added")) {
                Toast.makeText(this, "Video Added!", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            }
            if (result.contains("invalid file type")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("This card has been declined")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("Too many requests")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("Invalid parameters supplied")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("Authentication with Stripe failed")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("promo code expired")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("invalid ad cost")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("invalid promo code")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("Internal Error")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("invalid input")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.contains("new image added")) {
                Log.d("i_tag ", "new image added");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            } else if (result.contains("new audio added")) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                startActivity(in);
            }
        }

        Log.d("PostAdType Logs ", result);
    }

    @Override
    public void onClick(final View view) {
        final ProgressDialog dialogStripe;
        //pd = new ProgressDialog(PubSubmitActivity.this);
        //pd.setMessage("Processing...");
        //pd.show();
        //pd.setCanceledOnTouchOutside(false);

        if (view == bYoutube) {
            Log.d(" - ", "Button_Youtube");
            if (previewCheck == false) {
                adType = "1";
            }
            bPlay.setVisibility(View.GONE);
            bPause.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvComingSoon.setVisibility(View.GONE);
            LLchoosead.setVisibility(View.GONE);
            etVideoID.setFocusable(true);
            etVideoID.setVisibility(View.VISIBLE);
            stepOneVisible();
            LLyoutube.setVisibility(View.VISIBLE);
            etVideoID.setHint("YouTube Video ID");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("adType", adType);
            edit.commit();
        }

        if (view == bNext1) {
            buttonPlaceholder = "bNext1";

            Log.d("title ", etVideoTitle.getText().toString());
            Log.d("link ", etVideoInfo.getText().toString());
            Log.d("company ", etCompany.getText().toString());
            Log.d("id ", etVideoID.getText().toString());

            if (etCompany.length() > 0 && etVideoTitle.length() > 0 && etVideoInfo.length() > 0 && etVideoID.length() > 0) {
                hideKeyboard(view);
                stepOneGone();
                stepThreeGone();
                stepFourGone();
                stepFiveGone();
                stepTwoVisible();

                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

                if (adType.equals("3")) {
                    audioFromServer((azureAudio + fileNameG));
                }

                if (videoInternalOrExternal.equals("1")) {
                    DataFunction(etVideoID.getText().toString());
                }

//                audioFromServer(etVideoID.getText().toString());
//                audioFromServer(etVideoID.getText().toString());


                PostResponseAsyncTask task = new PostResponseAsyncTask(this);
                HashMap postData = new HashMap();
                postData.put("user_id", "anything");
                task.execute(QuestionListURL);
            } else {
                if (etCompany.length() < 1) {
                    Toast.makeText(PubSubmitActivity.this, "Cannot leave Company name blank", Toast.LENGTH_SHORT).show();
                }
                if (etVideoTitle.length() < 1) {
                    if (adType.equals("1")) {
                        Toast.makeText(PubSubmitActivity.this, "Cannot leave video title blank", Toast.LENGTH_SHORT).show();
                    }
                    if (adType.equals("2")) {
                        Toast.makeText(PubSubmitActivity.this, "Cannot leave image title blank", Toast.LENGTH_SHORT).show();
                    }
                    if (adType.equals("3")) {
                        Toast.makeText(PubSubmitActivity.this, "Cannot leave audio title blank", Toast.LENGTH_SHORT).show();
                    }
                }
                if (etVideoInfo.length() < 1) {
                    Toast.makeText(PubSubmitActivity.this, "Cannot leave video info blank", Toast.LENGTH_SHORT).show();
                }
                if (etVideoID.length() < 1) {
                    Toast.makeText(PubSubmitActivity.this, "No file has been selected", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (view == bChoose) {
            buttonPlaceholder = "bChoose";
            pd = new ProgressDialog(PubSubmitActivity.this);
            pd.setMessage("Processing...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            openGallery();
            pd.dismiss();
        }

        if (view == bUpload) {

            dialog = ProgressDialog.show(PubSubmitActivity.this, "", "Uploading file...", true);

            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PubSubmitActivity.this, "upload is starting....", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (hasPermissions()) {
                        buttonPlaceholder = "bUpload";
                        new UploadFileAsync().execute(filePath, fileNameG, etVideoID.getText().toString(), etVideoTitle.getText().toString(), etVideoInfo.getText().toString());
                        Log.d("videoIDField ", etVideoID.getText().toString());
                    } else {
                        requestPerms();
                    }
                    //dialog.dismiss();
                    //First do the submit layout and then move on the the rest of the bug fixes, and lastly, do the ad leaderboard buttons for layout
                    //Next
                }
            }).start();

            dialog.dismiss();


        }


        if (view == bNext2) {
            hideKeyboard(view);
            buttonPlaceholder = "bNext2";
            if (questionCounter == 4) {
                finalQuestions = String.valueOf(questionValue);
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();
                edit.putString("nvQuestions", finalQuestions);

                PostResponseAsyncTask task = new PostResponseAsyncTask(this);
                HashMap postData = new HashMap();
                postData.put("user_id", "anything");
                task.execute(CityListURL);

                edit.commit();
                stepOneGone();
                stepTwoGone();
                stepFourGone();
                stepFiveGone();
                stepThreeVisible();
            } else if (questionCounter < 1) {
                Toast.makeText(PubSubmitActivity.this, "Select at least One Question", Toast.LENGTH_SHORT).show();
            } else if (questionCounter > 4) {
                Toast.makeText(PubSubmitActivity.this, "Cannot have more than 4 questions selected", Toast.LENGTH_SHORT).show();
            }
        }

        if (view == bNext3) {
            hideKeyboard(view);
            buttonPlaceholder = "bNext3";
            Log.d("in next3", "in next3");
            if (boolGender == true && boolAge == true && boolLocation == true && boolGenderRG == true && boolAgeRG == true && boolLocationRG == true) {
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();
                finalGender = gGender;
                finalAge = String.valueOf(ageValue);
                finalLocation = String.valueOf(locationValue);
                Log.d("gGender ", finalGender);
                Log.d("nvGender ", ADPreferences.getString("nvGender", ""));
                edit.putString("nvGender", finalGender);
                edit.putString("nvAge", finalAge);
                edit.putString("nvLocation", finalLocation);
                edit.commit();
                stepOneGone();
                stepTwoGone();
                stepThreeGone();
                stepFiveGone();
                stepFourVisible();

                HashMap postData = new HashMap();
//                postData.put("promo", promoCode);
//                postData.put("ad_type", adType);
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute(InterestsListURL);
            } else {
                if (boolGender == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select a Gender option", Toast.LENGTH_SHORT).show();
                }
                if (boolGenderRG == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select which Gender to Target", Toast.LENGTH_SHORT).show();
                }
                if (boolAge == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select an Age option", Toast.LENGTH_SHORT).show();
                }
                if (numOfAges < 1) {
                    Toast.makeText(PubSubmitActivity.this, "Select which Age range to Target", Toast.LENGTH_SHORT).show();
                }
                if (boolLocation == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select a Location option", Toast.LENGTH_SHORT).show();
                }
                if (boolLocationRG == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select which Location range to Target", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (view == bNext4) {
            Log.d("boolInterests ", String.valueOf(boolInterests));
            Log.d("boolInterestsCB ", String.valueOf(boolInterestsCB));
            Log.d("interestsNumber ", String.valueOf(interestsNumber));
            hideKeyboard(view);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            buttonPlaceholder = "bNext4";
            if (boolInterests == true && boolInterestsCB == true && interestsNumber > 0 && interestsNumber <= 20) {
                finalInterests = String.valueOf(interestsCounter);

                countValues();

                if (adType.equals("2")) {
                    tvDurationPrompt.setVisibility(View.GONE);
                }

                if (videoInternalOrExternal.equals("1") && preferences.getString("ref", "").length() > 2) {

                    Log.d("about ", "internal");
                    etVideoID.setText(preferences.getString("ref", ""));
                    Log.d("NewAssignment ", etVideoID.getText().toString());
                    DataFunction(etVideoID.getText().toString());
                }

                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();
                edit.putString("nvInterests", finalInterests);
                edit.commit();
                stepOneGone();
                stepTwoGone();
                stepThreeGone();
                stepFourGone();
                stepFiveVisible();
                HashMap postData = new HashMap();
                postData.put("pub_id", preferences.getString("raadz_pub_id", ""));
                postData.put("promo", promoCode);
                postData.put("ad_type", adType);
                Log.d("adType ", String.valueOf(adType));
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute(ViewCostURL);
            } else {
                if (boolInterests == false) {
                    Toast.makeText(PubSubmitActivity.this, "Select an Interest option", Toast.LENGTH_SHORT).show();
                }
                if (interestsNumber < 1) {
                    Toast.makeText(PubSubmitActivity.this, "Select at least 1 interest", Toast.LENGTH_SHORT).show();
                }
                if (interestsNumber > 20) {
                    Toast.makeText(PubSubmitActivity.this, "Maximum number of allowed interests is 20", Toast.LENGTH_SHORT).show();
                }
            }

        }//End of bNext if

        if (view == bApply) {
            buttonPlaceholder = "bApply";
            promoCode = etPromo.getText().toString();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = ADPreferences.edit();
            edit.putString("promo", "");
            edit.commit();
            finalPromo = etPromo.getText().toString();
            HashMap postData = new HashMap();
            postData.put("pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("promo", promoCode);
            postData.put("ad_type", adType);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(ViewCostURL);
        }//End of bApply if

        if (view == bDirect) {

//            String month = etExpMonth.getText().toString();
//            String year = etExpYear.getText().toString();
//            Card card = new Card("4242-4242-4242-4242", 12, 2018, "123");
//            Log.d("Number ", card.getNumber());
//            Log.d("Month ", String.valueOf(card.getExpMonth()));
//            Log.d("Number ", String.valueOf(card.getExpYear()));
//            Log.d("Number ", card.getCVC());
            // or make it another way around

            fileDirectOrBalance = "0";

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubSubmitActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
            final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
            final EditText mName = (EditText) mView.findViewById(R.id.etName);
            final EditText mZip = (EditText) mView.findViewById(R.id.etZip);
            final TextView mTotal = (TextView) mView.findViewById(R.id.tvTotal);
            CardWidget = (CardMultilineWidget) mView.findViewById(R.id.CardWidget);

            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            finalCost = tvTotal.getText().toString().replace(".", "");
            finalCost = finalCost.replace("$", "");
            //finalCost = String.valueOf(Integer.parseInt(finalCost) * 100);

            Log.d("finalCost1 ", finalCost);

            final SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = ADPreferences.edit();
            edit.putString("nvCost", finalCost + "0");
            edit.commit();

            mAmount.setText(tvTotal.getText().toString());

            mTotal.setText("Deposit Amount" + " " + (tvTotal.getText().toString()));

            Log.d("deposit ", String.valueOf(deposit));

            mAmount.setFocusable(false);

//            if (previewCheck == true) {
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                mAmount.setText(preferences.getString("cost", "").replace("$", ""));
//                mAmount.setFocusable(false);
//            }

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            });

            try {
                deposit = Integer.parseInt(finalCost);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }

            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CardWidget.getCard().getNumber().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Card Number", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getExpMonth().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Month", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getExpYear().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Year", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getCVC().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid CVC Number", Toast.LENGTH_SHORT).show();
                    } else if (mName.getText().toString().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Name/Email", Toast.LENGTH_SHORT).show();
                    } else if (mZip.getText().toString().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Zip Code", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            final Stripe stripe = new Stripe(getApplicationContext(), preferences.getString("stripe_public_key", ""));

                            final String tempName = mName.getText().toString();
                            final String tempZip = mZip.getText().toString();

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

                                    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    final SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    Log.d("pub id ", preferences.getString("raadz_pub_id", ""));
                                    Log.d("title ", etVideoTitle.getText().toString());
                                    Log.d("ytid ", etVideoID.getText().toString());
                                    Log.d("link ", etVideoInfo.getText().toString());
                                    Log.d("company ", etCompany.getText().toString());
                                    Log.d("views ", ADPreferences.getString("nvViews", ""));
                                    Log.d("cost ", ADPreferences.getString("nvCost", ""));
                                    Log.d("promo ", ADPreferences.getString("promo", ""));
                                    Log.d("gender ", ADPreferences.getString("nvGender", ""));
                                    Log.d("age ", ADPreferences.getString("nvAge", ""));
                                    Log.d("location ", ADPreferences.getString("nvLocation", ""));
                                    Log.d("questions ", ADPreferences.getString("nvQuestions", ""));
                                    Log.d("interests ", ADPreferences.getString("nvInterests", ""));
                                    Log.d("finalViews ", tvPViews.getText().toString());

                                    String s_email = preferences.getString("email", "");
                                    String s_pid = preferences.getString("raadz_pub_id", "");
                                    String s_pt = preferences.getString("token", "");
                                    String amount = mAmount.getText().toString().replace("$", "");
                                    double_amount = Double.parseDouble(amount) * 100;
                                    i_amount = (int) double_amount;
                                    amount = String.valueOf(i_amount);
//                                    amount = amount.replace(".", "");
                                    //Log.d("token ", token.getId());

                                    if (fileDuration.length() < 2) {
                                        fileDuration = preferences.getString("duration", "");
                                    }

                                    Log.d("amount ", mAmount.getText().toString());
                                    Log.d("amount_s ", String.valueOf(i_amount));
                                    Log.d("email ", s_email);
                                    Log.d("pubID ", s_pid);
                                    Log.d("description ", "12");

                                    Log.d("paymentCheck ", String.valueOf(paymentCheck));
                                    f_price = mAmount.getText().toString().replace("$", "");
                                    check = true;
                                    if (check == true) {
                                        if (adType.equals("1") && videoInternalOrExternal.equals("1")) {
                                            Log.d("1 ", String.valueOf(amount));
                                            Log.d("2 ", s_email);
                                            Log.d("3 ", "25");
                                            Log.d("4 ", preferences.getString("raadz_pub_id", ""));
                                            Log.d("5 ", preferences.getString("token", ""));
                                            Log.d("6 ", etVideoID.getText().toString());
                                            Log.d("7 ", etVideoTitle.getText().toString());
                                            Log.d("8 ", etVideoInfo.getText().toString());
                                            Log.d("9 ", ADPreferences.getString("promo", ""));
                                            Log.d("10 ", fileDuration);
                                            Log.d("11 ", finalViews);
                                            Log.d("12 ", ADPreferences.getString("nvGender", ""));
                                            Log.d("13 ", ADPreferences.getString("nvAge", ""));
                                            Log.d("14 ", ADPreferences.getString("nvLocation", ""));
                                            Log.d("15 ", ADPreferences.getString("nvInterests", ""));
                                            Log.d("16 ", ADPreferences.getString("nvQuestions", ""));

                                            Log.d("title ", etVideoTitle.getText().toString());
                                            Log.d("ytid ", etVideoID.getText().toString());
                                            Log.d("link ", etVideoInfo.getText().toString());
                                            Log.d("company ", etCompany.getText().toString());
                                            Log.d("views ", ADPreferences.getString("nvViews", ""));
                                            Log.d("cost ", ADPreferences.getString("nvCost", ""));
                                            Log.d("promo ", ADPreferences.getString("promo", ""));
                                            Log.d("gender ", ADPreferences.getString("nvGender", ""));
                                            Log.d("age ", ADPreferences.getString("nvAge", ""));
                                            Log.d("location ", ADPreferences.getString("nvLocation", ""));
                                            Log.d("questions ", ADPreferences.getString("nvQuestions", ""));
                                            Log.d("interests ", ADPreferences.getString("nvInterests", ""));
                                            Log.d("finalViews ", tvPViews.getText().toString());
                                            if (adType.equals("1")) {
                                                buttonPlaceholder = "bDirect";
                                                HashMap postData = new HashMap();
                                                postData.put("stripeToken", token.getId());
                                                postData.put("stripeAmountInCents", amount);
                                                postData.put("stripeEmail", s_email);
                                                postData.put("stripeDescription", "Deposit to Account");
                                                postData.put("pubID", preferences.getString("raadz_pub_id", ""));
                                                postData.put("pubToken", preferences.getString("token", ""));
                                                postData.put("ytID", etVideoID.getText().toString());
                                                postData.put("title", etVideoTitle.getText().toString());
                                                postData.put("link", etVideoInfo.getText().toString());
                                                if (ADPreferences.getString("promo", "").length() < 2) {
                                                    postData.put("promo", "");
                                                } else {
                                                    postData.put("promo", ADPreferences.getString("promo", ""));
                                                }
                                                postData.put("duration", fileDuration);
                                                postData.put("totviews", finalViews);
                                                postData.put("gender", ADPreferences.getString("nvGender", ""));
                                                postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                                                postData.put("location", ADPreferences.getString("nvLocation", ""));
                                                postData.put("interests", ADPreferences.getString("nvInterests", ""));
                                                postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                                                if (optChecked == true) {
                                                    postData.put("optin", "1");
                                                } else {
                                                    postData.put("optin", "0");
                                                }
                                                PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                                                task.execute(StripeDepositVideoURL);

                                            }

                                        } else {
                                            if (adType == "1" && videoInternalOrExternal.equals("0")) {
                                                new UploadFileAsync().execute(
                                                        filePath,
                                                        fileNameG,
                                                        etVideoID.getText().toString(),
                                                        etVideoTitle.getText().toString(),
                                                        etVideoInfo.getText().toString(),
                                                        preferences.getString("raadz_pub_id", ""),
                                                        totalValueDisplay,
                                                        ADPreferences.getString("promo", ""),
                                                        finalViews,
                                                        ADPreferences.getString("nvGender", ""),
                                                        ADPreferences.getString("nvAge", ""),
                                                        ADPreferences.getString("nvLocation", ""),
                                                        ADPreferences.getString("nvInterests", ""),
                                                        ADPreferences.getString("nvQuestions", ""),
                                                        token.getId(),
                                                        s_email,
                                                        f_price,
                                                        "Pay with Direct"
                                                );

                                            }
                                            if (adType == "2" && imageInternalOrExternal.equals("0")) {
                                                if (imageInternalOrExternal.equals("0")) {

                                                    Log.d("Test_Cost 1 ", finalCost);
                                                    Log.d("Test_Cost 2 ", finalCost + "0");
                                                    Log.d("Test_Cost 3 ", ADPreferences.getString("nvCost", ""));
                                                    Log.d("Test_Cost 4 ", totalValueDisplay);

                                                    new UploadFileAsync().execute(
                                                            filePath,
                                                            fileNameG,
                                                            etVideoID.getText().toString(),
                                                            etVideoTitle.getText().toString(),
                                                            etVideoInfo.getText().toString(),
                                                            preferences.getString("raadz_pub_id", ""),
                                                            totalValueDisplay,
                                                            ADPreferences.getString("promo", ""),
                                                            finalViews,
                                                            ADPreferences.getString("nvGender", ""),
                                                            ADPreferences.getString("nvAge", ""),
                                                            ADPreferences.getString("nvLocation", ""),
                                                            ADPreferences.getString("nvInterests", ""),
                                                            ADPreferences.getString("nvQuestions", ""),
                                                            token.getId(),
                                                            s_email,
                                                            f_price,
                                                            "Pay with Direct"
                                                    );
                                                }
                                            }
                                            if (adType.equals("2") && imageInternalOrExternal.equals("1")) {
                                                buttonPlaceholder = "bDirect";
                                                Log.d("f_price ", f_price);
                                                HashMap postData = new HashMap();
                                                postData.put("stripeToken", token.getId());
                                                postData.put("stripeAmountInCents", amount);
                                                postData.put("stripeEmail", s_email);
                                                postData.put("stripeDescription", "Deposit to Account");
                                                postData.put("pubID", preferences.getString("raadz_pub_id", ""));
                                                postData.put("pubToken", preferences.getString("token", ""));
                                                postData.put("imgRef", etVideoID.getText().toString());
                                                postData.put("title", etVideoTitle.getText().toString());
                                                postData.put("link", etVideoInfo.getText().toString());
                                                if (ADPreferences.getString("promo", "").length() < 2) {
                                                    postData.put("promo", "");
                                                } else {
                                                    postData.put("promo", ADPreferences.getString("promo", ""));
                                                }
                                                postData.put("totviews", finalViews);
                                                postData.put("gender", ADPreferences.getString("nvGender", ""));
                                                postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                                                postData.put("location", ADPreferences.getString("nvLocation", ""));
                                                postData.put("interests", ADPreferences.getString("nvInterests", ""));
                                                postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                                                if (optChecked == true) {
                                                    postData.put("optin", "1");
                                                } else {
                                                    postData.put("optin", "0");
                                                }
                                                PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                                                task.execute(StripeChargeImageURL);

                                            }

                                            if (adType == "3" && audioInternalOrExternal.equals("0")) {
                                                new UploadFileAsync().execute(
                                                        filePath,
                                                        fileNameG,
                                                        etVideoID.getText().toString(),
                                                        etVideoTitle.getText().toString(),
                                                        etVideoInfo.getText().toString(),
                                                        preferences.getString("raadz_pub_id", ""),
                                                        totalValueDisplay,
                                                        ADPreferences.getString("promo", ""),
                                                        finalViews,
                                                        ADPreferences.getString("nvGender", ""),
                                                        ADPreferences.getString("nvAge", ""),
                                                        ADPreferences.getString("nvLocation", ""),
                                                        ADPreferences.getString("nvInterests", ""),
                                                        ADPreferences.getString("nvQuestions", ""),
                                                        token.getId(),
                                                        s_email,
                                                        f_price,
                                                        "Pay with Direct"
                                                );

                                            }
                                            if (adType.equals("3") && audioInternalOrExternal.equals("1")) {
                                                Log.d("file_path ", etVideoID.getText().toString());
                                                if (adType.equals("3")) {
                                                    buttonPlaceholder = "bDirect";
                                                    HashMap postData = new HashMap();
                                                    postData.put("stripeToken", token.getId());
                                                    postData.put("stripeAmountInCents", amount);
                                                    postData.put("stripeEmail", s_email);
                                                    postData.put("stripeDescription", "Deposit to Account");
                                                    postData.put("pubID", preferences.getString("raadz_pub_id", ""));
                                                    postData.put("pubToken", preferences.getString("token", ""));
                                                    postData.put("audRef", etVideoID.getText().toString());
                                                    postData.put("title", etVideoTitle.getText().toString());
                                                    postData.put("link", etVideoInfo.getText().toString());
                                                    if (ADPreferences.getString("promo", "").length() < 2) {
                                                        postData.put("promo", "");
                                                    } else {
                                                        postData.put("promo", ADPreferences.getString("promo", ""));
                                                    }
                                                    postData.put("totviews", finalViews);
                                                    postData.put("gender", ADPreferences.getString("nvGender", ""));
                                                    postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                                                    postData.put("location", ADPreferences.getString("nvLocation", ""));
                                                    postData.put("interests", ADPreferences.getString("nvInterests", ""));
                                                    postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                                                    if (optChecked == true) {
                                                        postData.put("optin", "1");
                                                    } else {
                                                        postData.put("optin", "0");
                                                    }
                                                    PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                                                    task.execute(StripeChargeAudioURL);

                                                }

                                            }
                                        }
//                                        StripeDetails(
//                                                token.getId(),
//                                                mAmount.getText().toString(),
//                                                s_email,
//                                                s_pid,
//                                                s_pt,
//                                                "something something",
//                                                etVideoTitle.getText().toString(),
//                                                etVideoID.getText().toString(),
//                                                etVideoInfo.getText().toString(),
//                                                ADPreferences.getString("promo", ""),
//                                                ADPreferences.getString("nvViews", ""),
//                                                ADPreferences.getString("nvGender", ""),
//                                                ADPreferences.getString("nvAge", ""),
//                                                ADPreferences.getString("nvLocation", ""),
//                                                ADPreferences.getString("nvInterests", ""),
//                                                ADPreferences.getString("nvQuestions", ""),
//                                                etVideoID.getText().toString());


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


//            String month = etExpMonth.getText().toString();
//            String year = etExpYear.getText().toString();
//            Card card = new Card("4242-4242-4242-4242", 12, 2018, "123");
//            Log.d("Number ", card.getNumber());
//            Log.d("Month ", String.valueOf(card.getExpMonth()));
//            Log.d("Number ", String.valueOf(card.getExpYear()));
//            Log.d("Number ", card.getCVC());
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubSubmitActivity.this);
//            View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
//            final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
//            final EditText mNumber = (EditText) mView.findViewById(R.id.etNumber);
//            final EditText mMonth = (EditText) mView.findViewById(R.id.etMonth);
//            final EditText mYear = (EditText) mView.findViewById(R.id.etYear);
//            final EditText mCVC = (EditText) mView.findViewById(R.id.etCVC);
//
//            Button mCancel = (Button) mView.findViewById(R.id.bGo);
//            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
//            mBuilder.setView(mView);
//            final AlertDialog dialog = mBuilder.create();
//            dialog.show();
//            Log.d("test1 ", preferences.getString("finalCost", ""));
//            Log.d("test2 ", finalCost);
//            final String costOther = String.valueOf(totalCost);
//            final String cost = costOther.replace(".", "") + "0";
//            cost.replace(".", "");
//            Log.d("cost ", cost);
//            Log.d("costOther ", costOther);
//            mAmount.setText("$" + String.format("%.2f", totalCost));
//            mAmount.setFocusable(false);
//            mAmount.setTextColor(Color.BLACK);
//            mAmount.setBackgroundResource(R.color.lightGrey);
//            mCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                }
//            });
//
//            mEnter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mAmount.getText().equals("")) {
//                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Amount", Toast.LENGTH_SHORT).show();
//                    } else if (mNumber.getText().equals("")) {
//                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Card Number", Toast.LENGTH_SHORT).show();
//                    } else if (String.valueOf(mMonth).equals("")) {
//                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Month", Toast.LENGTH_SHORT).show();
//                    } else if (String.valueOf(mYear).equals("")) {
//                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Year", Toast.LENGTH_SHORT).show();
//                    } else if (mCVC.getText().equals("")) {
//                        Toast.makeText(PubSubmitActivity.this, "Enter a valid CVC Number", Toast.LENGTH_SHORT).show();
//                    } else {
//                        try {
//                            final Stripe stripe = new Stripe(getApplicationContext(), StripeConfig.getPubKey());
//
//                            //new Stripe(getApplicationContext()).setDefaultPublishableKey(StripeConfig.STRIPE_API_KEY);
//                            String month = mMonth.getText().toString();
//                            String year = mYear.getText().toString();
//                            //Card card = new Card(mNumber.getText().toString(), Integer.parseInt(month), Integer.parseInt(year), mCVC.getText().toString());
//                            Card card = new Card("4242424242424242", 12, 20, "123");
//                            Log.d("Number ", card.getNumber());
//                            Log.d("Month ", String.valueOf(card.getExpMonth()));
//                            Log.d("Year ", String.valueOf(card.getExpYear()));
//                            Log.d("CVC ", card.getCVC());
//
//                            stripe.createToken(card, StripeConfig.getPubKey(), new TokenCallback() {
//
//                                @Override
//                                public void onError(Exception error) {
//                                    Log.d("something ", "exception", error);
//                                }
//
//                                @Override
//                                public void onSuccess(Token token) {
//                                    Log.d("pointer 1", "1");
//                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    com.stripe.Stripe.apiKey = StripeConfig.getSecretKey();
//                                    String s_email = preferences.getString("email", "");
//                                    String s_pid = preferences.getString("raadz_pub_id", "");
//                                    //Log.d("token ", token.getId());
//                                    Log.d("amount ", mAmount.getText().toString());
//                                    Log.d("email ", s_email);
//                                    Log.d("pubID ", s_pid);
//                                    Log.d("description ", "12");
//                                    StripeDetails(token.getId(), cost, s_email, s_pid, "something something");
//                                    Log.d("paymentCheck ", String.valueOf(paymentCheck));
//                                   //AdvertiserFunction(s_pid);
//                                    try {
//                                        final String tempToken = token.getId();
//                                        final Map<String, Object> chargeParams = new HashMap<String, Object>();
//                                        chargeParams.put("amount", totalCost);
//                                        //chargeParams.put("amount", Integer.parseInt(mAmount.getText().toString()));
//                                        chargeParams.put("currency", "usd");
//                                        chargeParams.put("source", token.getId());
//                                        chargeParams.put("description", "Example charge");
//                                        new Thread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Log.d("pointer 2", "2");
//                                                Charge charge = null;
//                                                try {
//                                                    charge = Charge.create(chargeParams);
//                                                } catch (com.stripe.exception.APIConnectionException e) {
//                                                    e.printStackTrace();
//                                                } catch (com.stripe.exception.InvalidRequestException e) {
//                                                    e.printStackTrace();
//                                                } catch (com.stripe.exception.AuthenticationException e) {
//                                                    e.printStackTrace();
//                                                } catch (com.stripe.exception.APIException e) {
//                                                    e.printStackTrace();
//                                                } catch (com.stripe.exception.CardException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                Log.d("pointer 3", "3");
//                                                System.out.println("Charge Log :" + charge);
//                                                Log.d("Paid amount ", String.valueOf(mAmount.getText().toString()));
//                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                                String s_email = preferences.getString("email", "");
//                                                String s_pid = preferences.getString("raadz_pub_id", "");
//                                                Log.d("token ", tempToken);
//                                                Log.d("amount ", mAmount.getText().toString());
//                                                Log.d("email ", s_email);
//                                                Log.d("pubID ", s_pid);
//                                                Log.d("description ", "12");
//                                                buttonPlaceholder = "bDirect";
//                                                StripeDetails(tempToken, cost, s_email, s_pid, "something something");
//                                                //Toast.makeText(PubSubmitActivity.this, "Payment of " + String.valueOf(mAmount.getText().toString()) + " was successful!", Toast.LENGTH_SHORT).show();
//                                                dialog.dismiss();
//                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                                            }
//                                        }).start();
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            });
//                        } catch (AuthenticationException e) {
//                            System.out.println(e);
//                        }//End of catch
//                    }//End of else
//                }//End of onClick
//            });//End of mEnter Button
        }

        if (view == bAddFunds) {


//            String month = etExpMonth.getText().toString();
//            String year = etExpYear.getText().toString();
//            Card card = new Card("4242-4242-4242-4242", 12, 2018, "123");
//            Log.d("Number ", card.getNumber());
//            Log.d("Month ", String.valueOf(card.getExpMonth()));
//            Log.d("Number ", String.valueOf(card.getExpYear()));
//            Log.d("Number ", card.getCVC());
            //try to test the addFunds function to check if depositing or not


            fileDirectOrBalance = "1";

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubSubmitActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
            final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
            final EditText mName = (EditText) mView.findViewById(R.id.etName);
            final EditText mZip = (EditText) mView.findViewById(R.id.etZip);
            final TextView mTotal = (TextView) mView.findViewById(R.id.tvTotal);
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
                    DecimalFormat twoDecimals = new DecimalFormat("#.##");
                    deposit = Double.parseDouble(mAmount.getText().toString());
                    deposit = Double.valueOf(twoDecimals.format(deposit));
                    deposit = deposit * 100;
                    Log.d("b_deposit ", String.valueOf(deposit));
                    if (mAmount.getText().equals("") || deposit < 1000) {
                        Log.d("deposit ", String.valueOf(deposit));
                        Toast.makeText(PubSubmitActivity.this, "Enter a minimum of $10.00", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getNumber().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Card Number", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getExpMonth().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Month", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getExpYear().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid Expiration Year", Toast.LENGTH_SHORT).show();
                    } else if (CardWidget.getCard().getCVC().equals("")) {
                        Toast.makeText(PubSubmitActivity.this, "Enter a valid CVC Number", Toast.LENGTH_SHORT).show();
                    } else {
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
                            Log.d("Number ", String.valueOf(card.getExpYear()));
                            Log.d("Number ", card.getCVC());

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
                                    String s_ptoken = preferences.getString("token", "");
                                    //Log.d("token ", token.getId());
                                    Log.d("amount ", mAmount.getText().toString());
                                    Log.d("email ", s_email);
                                    Log.d("pubID ", s_pid);
                                    Log.d("description ", "Deposit to Account");

                                    Log.d("paymentCheck ", String.valueOf(paymentCheck));
                                    check = true;
                                    if (check == true) {
                                        if (deposit < 1000) {
                                            Log.d("Deposit Amount if 1 ", String.valueOf(deposit));
                                            Toast.makeText(PubSubmitActivity.this, "Minimum deposit amount: $10.00", Toast.LENGTH_SHORT).show();
                                            mAmount.setText("");
                                        } else if (tempName.length() < 1) {
                                            Toast.makeText(PubSubmitActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                                        } else if (tempZip.length() < 1) {
                                            Toast.makeText(PubSubmitActivity.this, "Please enter your Zip", Toast.LENGTH_SHORT).show();
                                        } else if (deposit >= 1000) {
                                            int x;
                                            x = (int) deposit;
                                            Log.d("x_value ", String.valueOf(x));
                                            StripeDetails(
                                                    token.getId(),
                                                    String.valueOf(x),
                                                    s_email,
                                                    s_pid,
                                                    s_ptoken,
                                                    "Direct to account",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    filePath);


                                            buttonPlaceholder = "bAddFunds";
                                            HashMap postData = new HashMap();
                                            postData.put("pub_id", s_pid);
                                            postData.put("token", preferences.getString("token", ""));
                                            PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                                            task.execute(AdvertiserDataURL);
                                            dialog.dismiss();
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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

        }//End of addFunds Button

        if (view == bFinish) {
            if (boolViews == true) {
                String[] prefArray = {"nvGender", "nvAge"};
                stepOneGone();
                stepTwoGone();
                stepFourGone();
                stepFiveGone();
                stepThreeGone();
                stepPreviewVisible();
                tvTitle.setText("Review your settings.");
                bDifferentYT.setVisibility(View.GONE);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = ADPreferences.edit();

                if (adType.equals("1")) {
                    if (videoInternalOrExternal.equals("0")) {
                        bYTPlay.setVisibility(View.GONE);
                        mYouTubePlayerView.setVisibility(View.GONE);
                    }
                    if (videoInternalOrExternal.equals("1")) {
                        bYTPlay.setVisibility(View.VISIBLE);
                        mYouTubePlayerView.setVisibility(View.VISIBLE);
                        playVideo(etVideoID.getText().toString());
                    }
                }

                if (adType.equals("2")) {
                    if (previewCheck == true) {
                        String imgPath = azureImages + preferences.getString("ref", "");
                        Picasso.with(getApplicationContext()).load(imgPath).into(ivImage);
                        ivImage.setVisibility(View.VISIBLE);
                    } else {
                        Bitmap bmp = BitmapFactory.decodeFile(filePath);
                        ivImage.setImageBitmap(bmp);
                        ivImage.setVisibility(View.VISIBLE);
                    }
                    Log.d("questionsTest ", preferences.getString("questions", ""));
                }

                if (adType.equals("3")) {
                    audioPlayer(azureAudio, fileNameG);
                }

                Log.d("array length ", String.valueOf(prefArray.length));
                for (int i = 0; i < prefArray.length; i++) {

                }

                if (ADPreferences.getString("nvGender", "").equals("0")) {
                    edit.remove("nvGender");
                    edit.putString("nvGender", "N");
                    edit.commit();
                }

                if (ADPreferences.getString("nvAge", "").equals("0")) {
                    edit.remove("nvAge");
                    edit.putString("nvAge", "0");
                    edit.commit();
                }

                if (promoCode.length() < 1) {
                    promoCode = "-";
                }

                if (locationCity.equals("0")) {
                    locationCity = "-";
                }

                Log.d("InterestsNumber ", String.valueOf(interestsNumber));
                interestsNumber = interestsNumber - 1;
                Log.d("InterestsNumber ", String.valueOf(interestsNumber));
                Log.d("title ", etVideoTitle.getText().toString());
                Log.d("newfinalcost ", finalCost);
                finalCost = tvTotal.getText().toString().replace(".", "");
                finalCost = finalCost.replace("$", "");
                Log.d("newfinalcost2 ", finalCost);
                Log.d("nvGender ", ADPreferences.getString("nvGender", ""));
                Log.d("gGender ", gGender);

                tvPPubID.setText(preferences.getString("raadz_pub_id", ""));
                tvPCompany.setText(etCompany.getText().toString());
                tvPTitle.setText(etVideoTitle.getText().toString());
                tvPLink.setText(etVideoInfo.getText().toString());
                tvPYTID.setText(etVideoID.getText().toString());
                tvPGender.setText(ADPreferences.getString("nvGender", ""));
                tvPAge.setText(ADPreferences.getString("nvAge", ""));
                tvPLocation.setText(ADPreferences.getString("nvLocation", ""));
                tvPInterests.setText(String.valueOf(interestsNumber));
                tvPViews.setText(String.valueOf(finalViews));

                String t_total = tvTotal.getText().toString().replace("$", "");
                Log.d("t_total", t_total.toString());
                tvPCost.setText("$" + t_total);
                tvPPromo.setText(promoCode);
                tvPBalance.setText("$" + preferences.getString("cash", ""));
                finalCost = t_total;

/*
                String[] values = {
                        "Advertisment ID: ", preferences.getString("raadz_pub_id", ""),
                        "Company Name: ", etCompany.getText().toString(),
                        "Video Title: ", etVideoTitle.getText().toString(),
                        "More Info Link: ", etVideoInfo.getText().toString(),
                        "YouTube Video ID: ", etVideoID.getText().toString(),
                        "Gender Targeted: ", ADPreferences.getString("nvGender", ""),
                        "Age Targeted: ", ADPreferences.getString("nvAge", ""),
                        "Location Targeted: ", locationCity,
                        "Questions Asked: ", ADPreferences.getString("nvQuestions", ""),
                        "Interests Targeted: ", ADPreferences.getString("nvInterests", ""),
                        "Number of Views: ", ADPreferences.getString("nvViews", ""),
                        "Total Cost: ", "$" + finalCost + "0",
                        "Promo Code: ", promoCode,
                        "Account Balance: ", preferences.getString("money", "")};
                for (int i = 0; i < values.length; i++) {
                    Log.d("values ", String.valueOf(values[i]));
                }
*/
                Log.d("test ", finalCost);
                edit.putString("finalCost", finalCost);
                //gvPreview.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values));
                //LLPreviewGrid.setVisibility(View.VISIBLE);
                //lvPreview.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values));

                //String yt = preferences.getString("ytid", "");
                //playVideo(yt);
            } else if (boolViews == false) {
                Toast.makeText(PubSubmitActivity.this, "Number of Views", Toast.LENGTH_SHORT).show();
            }
        }

        if (view == bEnter) {
            Log.d("view equals ", "Enter");
        }

        if (view == bPost) {

            SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("adType", adType);
            edit.commit();


            Log.d("TAG_Post ", "in b_post");
            Log.d("adType ", String.valueOf(adType));
            Log.d("int_ext ", String.valueOf(imageInternalOrExternal));
            Log.d("re_submit ", preferences.getString("re_submit", ""));
            Log.d("direct_balance ", fileDirectOrBalance);

            buttonPlaceholder = "bPost";

            Log.d("int_ext ", String.valueOf(imageInternalOrExternal));
//            if (adType.equals("2") || adType.equals("3")) {
//                videoInternalOrExternal = "0";
//            }
            if (adType.equals("1")) {
                int s_temp = Integer.parseInt(allMatches.get(1));
                int m_temp = Integer.parseInt(allMatches.get(0));
                int t_temp = (m_temp * 60) + s_temp;

                if (fileDuration.length() < 2) {
                    fileDuration = String.valueOf(t_temp);
                }
            }
            double t_balance = Double.parseDouble(preferences.getString("cash", ""));
            double a_cost = Double.parseDouble(finalCost);

            if (t_balance < a_cost) {
                Toast.makeText(this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
            } else if (t_balance >= a_cost) {

                if (videoInternalOrExternal.equals("0")) {
                    if (adType.equals("1")) {
                        Log.d("testLog ", "0 and 1");
                        new UploadFileAsync().execute(
                                filePath,
                                fileNameG,
                                etVideoID.getText().toString(),
                                etVideoTitle.getText().toString(),
                                etVideoInfo.getText().toString(),
                                preferences.getString("raadz_pub_id", ""),
                                finalCost,
                                ADPreferences.getString("promo", ""),
                                finalViews,
                                ADPreferences.getString("nvGender", ""),
                                ADPreferences.getString("nvAge", ""),
                                ADPreferences.getString("nvLocation", ""),
                                ADPreferences.getString("nvInterests", ""),
                                ADPreferences.getString("nvQuestions", ""),
                                "",
                                "",
                                finalCost,
                                "Pay for ad"
                        );
                    }
                }
                if (imageInternalOrExternal.equals("0")) {
                    if (adType.equals("2")) {
                        Log.d("testLog ", "0 and 2");
                        new UploadFileAsync().execute(
                                filePath,
                                fileNameG,
                                etVideoID.getText().toString(),
                                etVideoTitle.getText().toString(),
                                etVideoInfo.getText().toString(),
                                preferences.getString("raadz_pub_id", ""),
                                finalCost,
                                ADPreferences.getString("promo", ""),
                                finalViews,
                                ADPreferences.getString("nvGender", ""),
                                ADPreferences.getString("nvAge", ""),
                                ADPreferences.getString("nvLocation", ""),
                                ADPreferences.getString("nvInterests", ""),
                                ADPreferences.getString("nvQuestions", ""),
                                "",
                                "",
                                finalCost,
                                "Pay for ad"
                        );
                    }
                }

                if (audioInternalOrExternal.equals("0")) {
                    if (adType.equals("3")) {
                        Log.d("testLog ", "0 and 3");
                        new UploadFileAsync().execute(
                                filePath,
                                fileNameG,
                                etVideoID.getText().toString(),
                                etVideoTitle.getText().toString(),
                                etVideoInfo.getText().toString(),
                                preferences.getString("raadz_pub_id", ""),
                                finalCost,
                                ADPreferences.getString("promo", ""),
                                finalViews,
                                ADPreferences.getString("nvGender", ""),
                                ADPreferences.getString("nvAge", ""),
                                ADPreferences.getString("nvLocation", ""),
                                ADPreferences.getString("nvInterests", ""),
                                ADPreferences.getString("nvQuestions", ""),
                                "",
                                "",
                                finalCost,
                                "Pay for ad"

                        );
                    }
                }

                if (videoInternalOrExternal.equals("1")) {

                    if (adType.equals("1")) {
                        buttonPlaceholder = "bPost";
                        HashMap postData = new HashMap();
                        postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                        postData.put("token", preferences.getString("token", ""));
                        postData.put("ytID", etVideoID.getText().toString());
                        postData.put("title", etVideoTitle.getText().toString());
                        postData.put("link", etVideoInfo.getText().toString());
                        postData.put("promo", ADPreferences.getString("promo", ""));
                        postData.put("duration", fileDuration);
                        postData.put("cost", finalCost);
                        postData.put("totviews", finalViews);
                        postData.put("gender", ADPreferences.getString("nvGender", ""));
                        postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                        postData.put("location", ADPreferences.getString("nvLocation", ""));
                        postData.put("interests", ADPreferences.getString("nvInterests", ""));
                        postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                        if (optChecked == true) {
                            postData.put("optin", "1");
                        } else {
                            postData.put("optin", "0");
                        }
                        PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                        task.execute(NewVideoPublishURL);

                    }

                }


                if (imageInternalOrExternal.equals("1")) {
                    Log.d("file_path ", etVideoID.getText().toString());
                    if (adType.equals("2")) {
                        buttonPlaceholder = "bPost";
                        HashMap postData = new HashMap();
                        postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                        postData.put("token", preferences.getString("token", ""));
                        postData.put("title", etVideoTitle.getText().toString());
                        postData.put("link", etVideoInfo.getText().toString());
                        postData.put("promo", ADPreferences.getString("promo", ""));
                        postData.put("duration", fileDuration);
                        postData.put("cost", finalCost);
                        postData.put("totviews", finalViews);
                        postData.put("gender", ADPreferences.getString("nvGender", ""));
                        postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                        postData.put("location", ADPreferences.getString("nvLocation", ""));
                        postData.put("interests", ADPreferences.getString("nvInterests", ""));
                        postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                        postData.put("imgRef", etVideoID.getText().toString());
                        if (optChecked == true) {
                            postData.put("optin", "1");
                        } else {
                            postData.put("optin", "0");
                        }
                        PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                        task.execute(ImageUploadURL);

                    }

                }


                if (audioInternalOrExternal.equals("1")) {
                    Log.d("file_path ", etVideoID.getText().toString());
                    if (adType.equals("3")) {
                        buttonPlaceholder = "bPost";
                        HashMap postData = new HashMap();
                        postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                        postData.put("token", preferences.getString("token", ""));
                        postData.put("title", etVideoTitle.getText().toString());
                        postData.put("link", etVideoInfo.getText().toString());
                        postData.put("promo", ADPreferences.getString("promo", ""));
                        postData.put("duration", fileDuration);
                        postData.put("cost", finalCost);
                        postData.put("totviews", finalViews);
                        postData.put("gender", ADPreferences.getString("nvGender", ""));
                        postData.put("ageVal", ADPreferences.getString("nvAge", ""));
                        postData.put("location", ADPreferences.getString("nvLocation", ""));
                        postData.put("interests", ADPreferences.getString("nvInterests", ""));
                        postData.put("questVal", ADPreferences.getString("nvQuestions", ""));
                        postData.put("audRef", etVideoID.getText().toString());
                        if (optChecked == true) {
                            postData.put("optin", "1");
                        } else {
                            postData.put("optin", "0");
                        }
                        PostResponseAsyncTask task = new PostResponseAsyncTask(PubSubmitActivity.this, postData);
                        task.execute(AudioUploadURL);

                    }

                }


            }

        }
    }//End of onClick View


    public void playVideo(String url) {
        if (player == null) {
            Log.d("player_null", "the player is null");
//            NewYouTubePlayerView = (YouTubePlayerView) findViewById(newYTPlayer);
//            NewYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
//            initializePlayer();
        }
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
            //player.play();
            bYTPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.play();
                    player.setShowFullscreenButton(false);

                }
            });
        }

    }

    public void initializePlayer() {

        NewYouTubePlayerView = (YouTubePlayerView) findViewById(newYTPlayer);
        NewYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                NewYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();

                player = youTubePlayer;
                player.loadVideo("42FRgdjveGU");
                if (player == null) {
                    Log.d("null_player ", "is null");
                }
                Log.d("length ", String.valueOf(youTubePlayer.getCurrentTimeMillis()));

                Log.d("null_value ", "is null");


                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                        Log.d("True_duration ", String.valueOf(player.getDurationMillis()));
                    }

                    @Override
                    public void onLoaded(String s) {
                        Log.d("True_duration ", String.valueOf(player.getDurationMillis()));
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {
                        Log.d("True_duration ", String.valueOf(player.getDurationMillis()));
                    }

                    @Override
                    public void onVideoEnded() {
                        Log.d("True_duration ", String.valueOf(player.getDurationMillis()));
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
    }


    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    public void countValues() {
        if (numOfAges == 1) {
            viewDisableValue += 1.50;
        } else if (numOfAges == 2) {
            viewDisableValue += 1.00;
        } else if (numOfAges == 3) {
            viewDisableValue += .67;
        } else if (numOfAges == 4) {
            viewDisableValue += .34;
        }


        if (numOfInterests == 1) {
            viewDisableValue += 1.50;
        } else if (numOfInterests == 2) {
            viewDisableValue += 1.00;
        } else if (numOfInterests == 3) {
            viewDisableValue += .67;
        } else if (numOfInterests == 4) {
            viewDisableValue += .34;
        }

        if (viewDisableValue >= 1.00) {
            bViews5.setVisibility(View.GONE);
        }
        if (viewDisableValue >= 2.00) {
            bViews5.setVisibility(View.GONE);
            bViews4.setVisibility(View.GONE);
        }
        if (viewDisableValue >= 3.00) {
            bViews5.setVisibility(View.GONE);
            bViews4.setVisibility(View.GONE);
            bViews3.setVisibility(View.GONE);
        }
        if (viewDisableValue < 1.00) {
            bViews1.setVisibility(View.VISIBLE);
            bViews2.setVisibility(View.VISIBLE);
            bViews3.setVisibility(View.VISIBLE);
            bViews4.setVisibility(View.VISIBLE);
            bViews5.setVisibility(View.VISIBLE);
        }

        Log.d("viewDisableValue ", String.valueOf(viewDisableValue));

    }


    public void questionsClicked() {
        cbQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ1.isChecked()) {
                    questionValue = questionValue + 1;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 1;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });

        cbQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ2.isChecked()) {
                    questionValue = questionValue + 2;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 2;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });

        cbQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ3.isChecked()) {
                    questionValue = questionValue + 4;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 4;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });

        cbQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ4.isChecked()) {
                    questionValue = questionValue + 8;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 8;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });

        cbQ5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ5.isChecked()) {
                    questionValue = questionValue + 16;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 16;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });

        cbQ6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbQ6.isChecked()) {
                    questionValue = questionValue + 32;
                    questionCounter = questionCounter + 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                } else {
                    questionValue = questionValue - 32;
                    questionCounter = questionCounter - 1;
                    Log.d("questionsCounter ", String.valueOf(questionCounter));
                }
            }
        });
    }

    public void stepOneGone() {
        LL1.setVisibility(View.GONE);
        LLstep1.setVisibility(View.GONE);
    }

    public void stepOneVisible() {
        LLstep1.setVisibility(View.VISIBLE);
        LL1.setVisibility(View.VISIBLE);
    }

    public void stepTwoGone() {
        LLstep2.setVisibility(View.GONE);
        LL2.setVisibility(View.GONE);
    }

    public void stepTwoVisible() {
        LLstep2.setVisibility(View.VISIBLE);
        LL2.setVisibility(View.VISIBLE);
    }

    public void stepThreeGone() {
        LLstep3.setVisibility(View.GONE);
        LL3.setVisibility(View.GONE);
    }

    public void stepThreeVisible() {
        LLstep3.setVisibility(View.VISIBLE);
        LL3.setVisibility(View.VISIBLE);
    }

    public void stepFourGone() {
        LLstep4.setVisibility(View.GONE);
        LL4.setVisibility(View.GONE);
    }

    public void stepFourVisible() {
        LLstep4.setVisibility(View.VISIBLE);
        LL4.setVisibility(View.VISIBLE);
    }

    public void stepFiveVisible() {
        LLstep5.setVisibility(View.VISIBLE);
        LL5.setVisibility(View.VISIBLE);
    }

    public void stepFiveGone() {
        LLstep5.setVisibility(View.GONE);
        LL5.setVisibility(View.GONE);
    }

    public void stepPreviewVisible() {
        LLEnd.setVisibility(View.GONE);
        LLPreview.setVisibility(View.VISIBLE);
        LL6.setVisibility(View.VISIBLE);
    }

    public void skipToPreview() {
        LLchoosead.setVisibility(View.GONE);
        stepOneGone();
        LL5.setVisibility(View.VISIBLE);
    }

    public void stepPreviewGone() {
        LLEnd.setVisibility(View.VISIBLE);
        if (adType.equals("1")) {
            LLPreview.setVisibility(View.GONE);
            LL6.setVisibility(View.GONE);
            bYTPlay.setVisibility(View.GONE);
            mYouTubePlayerView.setVisibility(View.GONE);
        }
        if (adType.equals("2")) {
            LLPreview.setVisibility(View.GONE);
            LL6.setVisibility(View.GONE);
            ivImage.setVisibility(View.GONE);
        }

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
                Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
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
                        LogoutConfirmActivity logout = new LogoutConfirmActivity();
                        logout.logoutConfirm(getApplicationContext());
                        Intent in = new Intent(PubSubmitActivity.this, IndexActivity.class);
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
        if (id == adv_home) {
            Intent in = new Intent(this, FragPubActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_history) {
            Intent in = new Intent(this, PubAdHistory.class);
            startActivity(in);
        } else if (id == R.id.adv_leaderboards) {
            //This is the profile, just named as history
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
                    Intent in = new Intent(PubSubmitActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubSubmitActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubSubmitActivity.this);
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


    private class UploadFileAsync extends AsyncTask<String, Integer, String> {
        HttpURLConnection conn = null;

        ProgressDialog dialogLoad;
        String trueResult;

        int progress = 0;
        int s_temp;
        int m_temp;
        int t_temp;

        boolean boolCancel = false;

        DataOutputStream dos = null;

        private Context context;
        private String msg = "";
        private boolean running = true;

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String adType = preferences.getString("adType", "");
            Log.d("adType ", adType);
            Log.d("Direct ", fileDirectOrBalance);
            Log.d("params[2] ", params[2]);
            Log.d("params[3] ", params[3]);
            Log.d("params[4] ", params[4]);
            String pub_token = preferences.getString("token", "");

            if (adType.equals("1")) {
                s_temp = Integer.parseInt(allMatches.get(1));
                m_temp = Integer.parseInt(allMatches.get(0));
                t_temp = (m_temp * 60) + s_temp;
                Log.d("durationTest ", fileDuration);

            }

            if (fileDuration.length() < 2) {
                fileDuration = String.valueOf(t_temp);
            }


            final String title = params[3];
            String link = params[4];

            String rand = "random";
            String sourceFileUri = params[0];
            String filePath = params[0];
            final String fileNameG = params[1];

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            int totalBytesRead = 0;
            int totalBytesAvailable = 0;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);
            Log.d("sourceFile Length ", String.valueOf(sourceFileUri.length()));
            Log.d("Int or Ext ", String.valueOf(videoInternalOrExternal));


            try {
                if (adType.equals("1") && fileDirectOrBalance.equals("1")) {
                    Log.d("log5 ", "1 and 1");
                    Log.d("FileDirectis11 ", "both are 1");
                    Log.d("Duration ", fileDuration);
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(NewVideoPublishURL);
                    filePath = params[2];
                    String raadz_pub_id = params[5];
                    String cost = finalCost;
                    String promo = params[7];
                    String totViews = params[8];
                    String gender = params[9];
                    String age = params[10];
                    String location = params[11];
                    String interests = params[12];
                    String questVal = params[13];
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
//                        conn.setChunkedStreamingMode(1024);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    if (videoInternalOrExternal.equals("0")) {
                        conn.setRequestProperty("video", sourceFileUri);
                    }

                    if (videoInternalOrExternal.equals("1")) {
                        conn.setRequestProperty("ytID", sourceFileUri);
                        //change back to ytID
                    }


                    conn.setRequestProperty("title", "title");
                    conn.setRequestProperty("rand", "rand");
                    conn.setRequestProperty("link", "link");


                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(title);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(link);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"raadz_pub_id\"" + raadz_pub_id + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(raadz_pub_id);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"token\"" + pub_token + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(pub_token);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"duration\"" + fileDuration + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(fileDuration);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(cost);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(promo);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(totViews);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(gender);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(age);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(location);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    if (optChecked == true) {
                        dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes("1");
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                    } else {
                        dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes("0");
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                    }

                    dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(interests);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(questVal);
                    dos.writeBytes(lineEnd);

                    if (videoInternalOrExternal.equals("0")) {
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"video\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                    }

                    if (videoInternalOrExternal.equals("1")) {
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"ytID\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                    }

                    bytesAvailable = fileInputStream.available();
                    totalBytesAvailable = bytesAvailable;
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.d("-", "-");
                    Log.d("title ", title);
                    Log.d("link ", link);
                    Log.d("-", "-");
                    Log.d("bytesRead ", String.valueOf(bytesRead));
                    Log.d("maxbuffersize ", String.valueOf(maxBufferSize));
                    if (link.equals("") || title.equals("")) {
                        Toast.makeText(context, "Fill out the Title and Website link fields first", Toast.LENGTH_LONG).show();
                    } else {

                        try {
                            Log.d("Available ", String.valueOf(bytesAvailable));
                            Log.d("Read ", String.valueOf(bytesRead));
                            while (bytesRead > 0) {
                                try {
                                    totalBytesRead = totalBytesRead + bytesRead;
                                    dos.write(buffer, 0, bytesRead);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                    //progress = ((int) ((bytesRead * 100 / (float) bytesAvailable)));
                                    progress = (int) (((float) totalBytesRead / totalBytesAvailable) * 100);

                                    Log.d("", "");
                                    Log.d("progress ", String.valueOf(progress));
                                    Log.d("bytesRead ", String.valueOf(bytesRead));
                                    Log.d("Total Bytes Read ", String.valueOf(totalBytesRead / (1024 * 1024)));
                                    Log.d("Total Bytes Available ", String.valueOf(totalBytesAvailable / (1024 * 1024)));
                                    Log.d("", "");
                                } catch (OutOfMemoryError e) {
                                    System.out.println(e);
                                }


                                //progress += bytesRead;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //etVideoTitle.setText(title);
                                        dialogLoad.setProgress(progress);
                                        Log.d("progress ", String.valueOf(progress));
                                        if (progress == 100) {
                                            dialogLoad.setMessage("Finalizing...");
                                        }
                                    }
                                });
                                //Log.d("progress ", String.valueOf(progress));
                            }
//                                while (bytesRead > 0) {
//                                    dos.write(buffer, 0, bytesRead);
//                                    bytesAvailable = fileInputStream.available();
//                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                                    progress = ((bytesRead * 100)/ (bytesAvailable));
//                                    //progress = (((bytesRead / bytesAvailable) * 100));
//                                    Log.d("progress ", String.valueOf(progress));
//                                    //progress = (bufferSize * 100 / bytesAvailable);
//                                    //progress += bytesRead;
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            etVideoTitle.setText(title);
//                                            dialogLoad.setProgress(progress);
//                                            Log.d("progress ", String.valueOf(progress));
//                                            if (progress == 100) {
//                                                dialogLoad.dismiss();
//                                                Toast.makeText(PubSubmitActivity.this, "Processing upload...", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                    //Log.d("progress ", String.valueOf(progress));
//                                }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                        serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn.getResponseMessage();

                        Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                        if (serverResponseCode == 200) {
                            Log.e("uploadFile", "File Uploaded.");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        if (serverResponseCode == 500) {
                            Log.e("uploadFile", serverResponseMessage);
                        }

                        fileInputStream.close();
                        dos.flush();
                        dos.close();


                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            //dialog.dismiss();


                            try {
                                InputStream is = conn.getInputStream();
                                InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                BufferedReader reader = new BufferedReader(isReader);
                                String result = "";
                                String line = "";
                                while ((line = reader.readLine()) != null) {
                                    result += line;
                                }//End of while loop
                                Log.d("VideoUploadResult ", result);
                                trueResult = result;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }//End of catch
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etVideoID.setText(trueResult);
                                    //etVideoID.setFocusable(false);
                                    etVideoID.setTextColor(Color.DKGRAY);
                                }
                            });

                        }//End of if statement

                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }


            if (!sourceFile.isFile() && videoInternalOrExternal.equals("0")) {
                Log.e("1uploadFile", "Source File not exist :" + filePath + "" + fileNameG);
                return "0";
            } else {
                try {

                    if (adType.equals("1") && fileDirectOrBalance.equals("0")) {
                        Log.d("log4 ", "1 and 0");
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        Log.d("fileDirectStripe ", "Direct");
                        Log.d("params[0] ", params[0]);
                        Log.d("params[1] ", params[1]);
                        Log.d("params[2] ", params[2]);
                        Log.d("params[3] ", params[3]);
                        Log.d("params[4] ", params[4]);
                        Log.d("params[5] ", params[5]);
                        Log.d("params[6] ", params[6]);
                        Log.d("params[7] ", params[7]);
                        Log.d("params[8] ", params[8]);
                        Log.d("params[9] ", params[9]);
                        Log.d("params[10] ", params[10]);
                        Log.d("params[11] ", params[11]);
                        Log.d("params[12] ", params[12]);
                        Log.d("params[13] ", params[13]);
                        Log.d("params[14] ", params[14]);
                        Log.d("params[15] ", params[15]);
                        Log.d("params[16] ", params[16]);
                        Log.d("params[17] ", params[17]);
                        sourceFileUri = params[0];
                        //FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(StripeDepositVideoURL);
                        String raadz_pub_id = params[5];
                        String cost = finalCost;
                        String promo = params[7];
                        String totViews = params[8];
                        String gender = params[9];
                        String age = params[10];
                        String location = params[11];
                        String interests = params[12];
                        String questVal = params[13];
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
//                        conn.setChunkedStreamingMode(1024);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        conn.setRequestProperty("title", "title");
                        conn.setRequestProperty("rand", "rand");
                        conn.setRequestProperty("link", "link");

                        if (videoInternalOrExternal.equals("0")) {
                            conn.setRequestProperty("video", sourceFileUri);
                        }

                        if (videoInternalOrExternal.equals("1")) {
                            conn.setRequestProperty("ytID", sourceFileUri);
                        }

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(link);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubID\"" + raadz_pub_id + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(raadz_pub_id);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubToken\"" + pub_token + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(pub_token);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"duration\"" + fileDuration + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(fileDuration);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(promo);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(totViews);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(gender);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(age);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(location);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(interests);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeToken\"" + params[14] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[14]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeEmail\"" + params[15] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[15]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeAmountInCents\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeDescription\"" + params[17] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[17]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        if (optChecked == true) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("1");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("0");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        }

                        dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(questVal);
                        dos.writeBytes(lineEnd);

                        if (videoInternalOrExternal.equals("0")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"video\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        if (videoInternalOrExternal.equals("1")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"ytID\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        bytesAvailable = fileInputStream.available();
                        totalBytesAvailable = bytesAvailable;
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        Log.d("-", "-");
                        Log.d("title ", title);
                        Log.d("link ", link);
                        Log.d("-", "-");
                        Log.d("bytesRead ", String.valueOf(bytesRead));
                        Log.d("maxbuffersize ", String.valueOf(maxBufferSize));
                        if (link.equals("") || title.equals("")) {
                            Toast.makeText(context, "Fill out the Title and Website link fields first", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                Log.d("Available ", String.valueOf(bytesAvailable));
                                Log.d("Read ", String.valueOf(bytesRead));
                                while (bytesRead > 0) {
                                    try {
                                        totalBytesRead = totalBytesRead + bytesRead;
                                        dos.write(buffer, 0, bytesRead);
                                        bytesAvailable = fileInputStream.available();
                                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                        //progress = ((int) ((bytesRead * 100 / (float) bytesAvailable)));
                                        progress = (int) (((float) totalBytesRead / totalBytesAvailable) * 100);

                                        Log.d("", "");
                                        Log.d("progress ", String.valueOf(progress));
                                        Log.d("bytesRead ", String.valueOf(bytesRead));
                                        Log.d("Total Bytes Read ", String.valueOf(totalBytesRead / (1024 * 1024)));
                                        Log.d("Total Bytes Available ", String.valueOf(totalBytesAvailable / (1024 * 1024)));
                                        Log.d("", "");
                                    } catch (OutOfMemoryError e) {
                                        System.out.println(e);
                                    }


                                    //progress += bytesRead;
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            //etVideoTitle.setText(title);
                                            dialogLoad.setProgress(progress);
                                            Log.d("progress ", String.valueOf(progress));
                                            if (progress == 100) {
                                                dialogLoad.setMessage("Finalizing...");
                                            }
                                        }
                                    });
                                    //Log.d("progress ", String.valueOf(progress));
                                }
//                                while (bytesRead > 0) {
//                                    dos.write(buffer, 0, bytesRead);
//                                    bytesAvailable = fileInputStream.available();
//                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                                    progress = ((bytesRead * 100)/ (bytesAvailable));
//                                    //progress = (((bytesRead / bytesAvailable) * 100));
//                                    Log.d("progress ", String.valueOf(progress));
//                                    //progress = (bufferSize * 100 / bytesAvailable);
//                                    //progress += bytesRead;
//                                    runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            etVideoTitle.setText(title);
//                                            dialogLoad.setProgress(progress);
//                                            Log.d("progress ", String.valueOf(progress));
//                                            if (progress == 100) {
//                                                dialogLoad.dismiss();
//                                                Toast.makeText(PubSubmitActivity.this, "Processing upload...", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                    //Log.d("progress ", String.valueOf(progress));
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                            if (serverResponseCode == 200) {
                                Log.e("uploadFile", "File Uploaded.");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            if (serverResponseCode == 500) {
                                Log.e("uploadFile", serverResponseMessage);
                            }

                            fileInputStream.close();
                            dos.flush();
                            dos.close();


                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                //dialog.dismiss();


                                try {
                                    InputStream is = conn.getInputStream();
                                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                    BufferedReader reader = new BufferedReader(isReader);
                                    String result = "";
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        result += line;
                                    }//End of while loop
                                    Log.d("VideoUploadResult ", result);
                                    trueResult = result;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }//End of catch
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etVideoID.setText(trueResult);
                                        //etVideoID.setFocusable(false);
                                        etVideoID.setTextColor(Color.DKGRAY);
                                    }
                                });

                            }//End of if statement

                        }
                    }

                    Log.d("adType_u ", adType);
                    Log.d("Direct_balance ", fileDirectOrBalance);

                    if (adType.equals("2") && fileDirectOrBalance.equals("1")) {
                        Log.d("Inside_Upload ", "adtype 2 and filedirectorbalance 1");
                        Log.d("log2 ", "2 and 1");
                        Log.d("params[5] ", params[5]);
                        Log.d("params[6] ", params[6]);
                        Log.d("params[7] ", params[7]);
                        Log.d("params[8] ", params[8]);
                        Log.d("params[9] ", params[9]);
                        Log.d("params[10] ", params[10]);
                        Log.d("params[11] ", params[11]);
                        Log.d("params[12] ", params[12]);
                        Log.d("params[13] ", params[13]);
                        String raadz_pub_id = params[5];
                        String cost = params[6];
                        String promo = params[7];
                        String totViews = params[8];
                        String gender = params[9];
                        String age = params[10];
                        String location = params[11];
                        String interests = params[12];
                        String questVal = params[13];
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(ImageUploadURL);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        if (imageInternalOrExternal.equals("0")) {
                            conn.setRequestProperty("image", sourceFileUri);
                        }
                        if (imageInternalOrExternal.equals("1")) {
                            conn.setRequestProperty("imgRef", sourceFileUri);
                        }

                        conn.setRequestProperty("title", "title");
                        conn.setRequestProperty("rand", "rand");
                        conn.setRequestProperty("link", "link");


                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(link);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"raadz_pub_id\"" + raadz_pub_id + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(raadz_pub_id);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"token\"" + pub_token + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(pub_token);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(promo);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(totViews);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(gender);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(age);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(location);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(interests);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(questVal);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        if (optChecked == true) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("1");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("0");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        }

                        if (imageInternalOrExternal.equals("0")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        if (imageInternalOrExternal.equals("1")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"imgRef\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        if (link.equals("") || title.equals("")) {
                            Toast.makeText(context, "Fill out the Title and Webiste link fields first", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                while (bytesRead > 0) {
                                    dos.write(buffer, 0, bytesRead);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                    progress = (bytesRead * 100 / bytesAvailable);
                                    //progress = (bufferSize * 100 / bytesAvailable);
                                    //progress += bytesRead;
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            dialogLoad.setProgress(progress);
                                            Log.d("progress ", String.valueOf(progress));
                                            if (progress == 100) {
                                                dialogLoad.setMessage("Finalizing...");
                                            }
                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                            if (serverResponseCode == 200) {
                                Log.e("uploadFile", "File Uploaded.");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            fileInputStream.close();
                            dos.flush();
                            dos.close();


                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                //dialog.dismiss();


                                try {
                                    InputStream is = conn.getInputStream();
                                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                    BufferedReader reader = new BufferedReader(isReader);
                                    String result = "";
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        result += line;
                                    }//End of while loop
                                    Log.d("ImageUploadResult ", result);
                                    trueResult = result;
                                    if (trueResult.equals("new image added")) {
                                        dialog.dismiss();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }//End of catch
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etVideoID.setText(trueResult);
                                        etVideoID.setTextColor(Color.DKGRAY);
                                    }
                                });

                            }//End of if statement

                        }

                    }

                    if (adType.equals("2") && fileDirectOrBalance.equals("0")) {
                        Log.d("Inside_Upload ", "adtype 2 and filedirectorbalance 0");
                        Log.d("log3 ", "2 and 0");
                        Log.d("params[5] ", params[5]);
                        Log.d("params[6] ", params[6]);
                        Log.d("params[7] ", params[7]);
                        Log.d("params[8] ", params[8]);
                        Log.d("params[9] ", params[9]);
                        Log.d("params[10] ", params[10]);
                        Log.d("params[11] ", params[11]);
                        Log.d("params[12] ", params[12]);
                        Log.d("params[13] ", params[13]);
                        Log.d("params[14] ", params[14]);
                        Log.d("params[15] ", params[15]);
                        Log.d("params[16] ", params[16]);
                        Log.d("params[17] ", params[17]);
                        String raadz_pub_id = params[5];
                        String cost = finalCost;
                        String promo = params[7];
                        String totViews = params[8];
                        String gender = params[9];
                        String age = params[10];
                        String location = params[11];
                        String interests = params[12];
                        String questVal = params[13];
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(StripeChargeImageURL);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        if (imageInternalOrExternal.equals("0")) {
                            conn.setRequestProperty("image", sourceFileUri);
                        }
                        if (imageInternalOrExternal.equals("1")) {
                            conn.setRequestProperty("imgRef", sourceFileUri);
                        }

                        conn.setRequestProperty("title", "title");
                        conn.setRequestProperty("rand", "rand");
                        conn.setRequestProperty("link", "link");
                        conn.setRequestProperty("cost", "cost");
                        conn.setRequestProperty("ad_cost", "ad_cost");
                        conn.setRequestProperty("stripeAmountInCents", "stripeAmountInCents");


                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(link);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubID\"" + raadz_pub_id + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(raadz_pub_id);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubToken\"" + pub_token + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(pub_token);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(promo);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(totViews);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(gender);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(age);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(location);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(interests);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(questVal);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeToken\"" + params[14] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[14]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeEmail\"" + params[15] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[15]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeAmountInCents\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeDescription\"" + params[17] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[17]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        if (optChecked == true) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("1");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("0");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        }

                        if (imageInternalOrExternal.equals("0")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        if (imageInternalOrExternal.equals("1")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"imgRef\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        if (link.equals("") || title.equals("")) {
                            Toast.makeText(context, "Fill out the Title and Webiste link fields first", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                while (bytesRead > 0) {
                                    dos.write(buffer, 0, bytesRead);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                    progress = (bytesRead * 100 / bytesAvailable);
                                    //progress = (bufferSize * 100 / bytesAvailable);
                                    //progress += bytesRead;
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            dialogLoad.setProgress(progress);
                                            Log.d("progress ", String.valueOf(progress));
                                            if (progress == 100) {
                                                dialogLoad.setMessage("Finalizing...");
                                            }
                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                            if (serverResponseCode == 200) {
                                Log.e("uploadFile", "File Uploaded.");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            fileInputStream.close();
                            dos.flush();
                            dos.close();


                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                //dialog.dismiss();


                                try {
                                    InputStream is = conn.getInputStream();
                                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                    BufferedReader reader = new BufferedReader(isReader);
                                    String result = "";
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        result += line;
                                    }//End of while loop
                                    Log.d("ImageUploadResult ", result);
                                    trueResult = result;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }//End of catch
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etVideoID.setText(trueResult);
                                        etVideoID.setTextColor(Color.DKGRAY);
                                    }
                                });

                            }//End of if statement

                        }

                    }

                    if (adType.equals("3") && fileDirectOrBalance.equals("1")) {
                        Log.d("log2 ", "3 and 1");
                        Log.d("params[5] ", params[5]);
                        Log.d("params[6] ", params[6]);
                        Log.d("params[7] ", params[7]);
                        Log.d("params[8] ", params[8]);
                        Log.d("params[9] ", params[9]);
                        Log.d("params[10] ", params[10]);
                        Log.d("params[11] ", params[11]);
                        Log.d("params[12] ", params[12]);
                        Log.d("params[13] ", params[13]);
                        String raadz_pubz_id = params[5];
                        String cost = params[6];
                        String promo = params[7];
                        String totViews = params[8];
                        String gender = params[9];
                        String age = params[10];
                        String location = params[11];
                        String interests = params[12];
                        String questVal = params[13];
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(AudioUploadURL);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        if (audioInternalOrExternal.equals("0")) {
                            conn.setRequestProperty("audio", sourceFileUri);
                        }
                        if (audioInternalOrExternal.equals("1")) {
                            conn.setRequestProperty("audRef", sourceFileUri);
                        }

                        conn.setRequestProperty("title", "title");
                        conn.setRequestProperty("rand", "rand");
                        conn.setRequestProperty("link", "link");


                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(link);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"raadz_pub_id\"" + raadz_pubz_id + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(raadz_pubz_id);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"token\"" + pub_token + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(pub_token);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"duration\"" + fileDuration + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(fileDuration);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(promo);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(totViews);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(gender);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(age);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(location);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(interests);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(questVal);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        if (optChecked == true) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("1");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("0");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        }

                        if (audioInternalOrExternal.equals("0")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"audio\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        if (audioInternalOrExternal.equals("1")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"audRef\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }


                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        if (link.equals("") || title.equals("")) {
                            Toast.makeText(context, "Fill out the Title and Webiste link fields first", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                while (bytesRead > 0) {
                                    dos.write(buffer, 0, bytesRead);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                    progress = (bytesRead * 100 / bytesAvailable);
                                    //progress = (bufferSize * 100 / bytesAvailable);
                                    //progress += bytesRead;
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            dialogLoad.setProgress(progress);
                                            Log.d("progress ", String.valueOf(progress));
                                            if (progress == 100) {
                                                dialogLoad.setMessage("Finalizing...");
                                            }
                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.d("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                            Log.d("serverResponseCode ", String.valueOf(serverResponseCode));
                            Log.d("serverResponseMessage ", String.valueOf(serverResponseMessage));

                            if (serverResponseCode == 200) {
                                Log.e("uploadFile", "File Uploaded.");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            fileInputStream.close();
                            dos.flush();
                            dos.close();


                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                //dialog.dismiss();


                                try {
                                    InputStream is = conn.getInputStream();
                                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                    BufferedReader reader = new BufferedReader(isReader);
                                    String result = "";
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        result += line;
                                    }//End of while loop
                                    Log.d("result ", result);
                                    trueResult = result;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }//End of catch
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etVideoID.setText(trueResult);
                                        etVideoID.setTextColor(Color.DKGRAY);
                                    }
                                });

                            }//End of if statement

                        }

                    }

                    if (adType.equals("3") && fileDirectOrBalance.equals("0")) {
                        Log.d("log1 ", "3 and 0");
                        Log.d("params[5] ", params[5]);
                        Log.d("params[6] ", params[6]);
                        Log.d("params[7] ", params[7]);
                        Log.d("params[8] ", params[8]);
                        Log.d("params[9] ", params[9]);
                        Log.d("params[10] ", params[10]);
                        Log.d("params[11] ", params[11]);
                        Log.d("params[12] ", params[12]);
                        Log.d("params[13] ", params[13]);
                        String raadz_pub_id = params[5];
                        String cost = finalCost;
                        String promo = params[7];
                        String totViews = params[8];
                        String gender = params[9];
                        String age = params[10];
                        String location = params[11];
                        String interests = params[12];
                        String questVal = params[13];
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(StripeChargeAudioURL);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                        if (audioInternalOrExternal.equals("0")) {
                            conn.setRequestProperty("audio", sourceFileUri);
                        }
                        if (audioInternalOrExternal.equals("1")) {
                            conn.setRequestProperty("audRef", sourceFileUri);
                        }

                        conn.setRequestProperty("title", "title");
                        conn.setRequestProperty("rand", "rand");
                        conn.setRequestProperty("link", "link");


                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + title + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"link\"" + link + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(link);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubID\"" + raadz_pub_id + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(raadz_pub_id);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"pubToken\"" + pub_token + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(pub_token);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"cost\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"duration\"" + fileDuration + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(fileDuration);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"promo\"" + promo + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(promo);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"totviews\"" + totViews + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(totViews);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"gender\"" + gender + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(gender);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"ageVal\"" + age + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(age);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + location + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(location);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"interests\"" + interests + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(interests);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"questVal\"" + questVal + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(questVal);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeToken\"" + params[14] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[14]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeEmail\"" + params[15] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[15]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeAmountInCents\"" + cost + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(cost);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"stripeDescription\"" + params[17] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(params[17]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        if (optChecked == true) {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "1" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("1");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        } else {
                            dos.writeBytes("Content-Disposition: form-data; name=\"optin\"" + "0" + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            dos.writeBytes("0");
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                        }

                        if (audioInternalOrExternal.equals("0")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"audio\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }

                        if (audioInternalOrExternal.equals("1")) {
                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"audRef\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                        }


                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        if (link.equals("") || title.equals("")) {
                            Toast.makeText(context, "Fill out the Title and Webiste link fields first", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                while (bytesRead > 0) {
                                    dos.write(buffer, 0, bytesRead);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                    progress = (bytesRead * 100 / bytesAvailable);
                                    //progress = (bufferSize * 100 / bytesAvailable);
                                    //progress += bytesRead;
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            dialogLoad.setProgress(progress);
                                            Log.d("progress ", String.valueOf(progress));
                                            if (progress == 100) {
                                                dialogLoad.setMessage("Finalizing...");
                                            }
                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.d("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                            Log.d("serverResponseCode ", String.valueOf(serverResponseCode));
                            Log.d("serverResponseMessage ", String.valueOf(serverResponseMessage));

                            if (serverResponseCode == 200) {
                                Log.e("uploadFile", "File Uploaded.");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        tvAltText.setVisibility(View.VISIBLE);
//                                        Toast.makeText(context, fileNameG, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            fileInputStream.close();
                            dos.flush();
                            dos.close();


                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                //dialog.dismiss();


                                try {
                                    InputStream is = conn.getInputStream();
                                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                                    BufferedReader reader = new BufferedReader(isReader);
                                    String result = "";
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        result += line;
                                    }//End of while loop
                                    Log.d("result ", result);
                                    trueResult = result;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }//End of catch
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etVideoID.setText(trueResult);
                                        etVideoID.setTextColor(Color.DKGRAY);
                                    }
                                });

                            }//End of if statement

                        }

                    }

                } catch (Exception e) {
                    Log.e("Upload file exc", "Exception : " + e.getMessage(), e);
                }//End of Exception catch
                Log.d("SRC ", String.valueOf(serverResponseCode));
                return "Executed";
            } // End else block

        }

        @Override
        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    context = getApplicationContext();
                    Log.d("UploadAsyncReturn ", result);
                    dialogLoad.dismiss();
                    if (boolCancel == false && !result.equals("publisher not found")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (result.equals("Executed")) {
                                    Toast.makeText(context, "Ad Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                                    startActivity(in);
                                }

                                if (result.contains("Error: invalid data")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: invalid call")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: invalid file type")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: Authorization Error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: YouTube client error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: YouTube service error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains(" - Error: ACCESS TOKEN DOES NOT EXISTS - ")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("More Info Link cannot be blank")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Video Title cannot be blank")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: invalid call")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Publisher not found")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Insufficient Account Balance")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Internal Error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("invalid promo code")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("invalid ad cost")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("promo code expired")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("new video added")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                                    startActivity(in);
                                } else if (result.contains("new image added")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                                    startActivity(in);
                                } else if (result.contains("new audio added")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                                    startActivity(in);
                                } else if (result.contains("Error: invalid file type")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: Authorization Error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: YouTube client error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                } else if (result.contains("Error: YouTube service error")) {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                }

//                                if (fileDirectOrBalance.equals("1")) {
//
//
//
//
//                                    if (result.equals("Error: invalid data")) {
//                                        Toast.makeText(context, "File too big", Toast.LENGTH_SHORT).show();
//                                        etVideoID.setText("");
//                                    }
//                                    if (result.equals("new image added")) {
//                                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
////                                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
////                                    startActivity(in);
//                                    }
//                                    if (result.equals("Executed")) {
//                                        Toast.makeText(PubSubmitActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//                                        Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
//                                        startActivity(in);
//                                    }
//                                    Log.d("Post upload result ", result);
//                                } else if (fileDirectOrBalance.equals("0")) {
//                                    if (result.contains("Invalid Video Length")) {
//                                        Toast.makeText(context, "Invalid Video Length", Toast.LENGTH_SHORT).show();
//                                    }
//                                    if (adType.equals("1")) {
//                                        Toast.makeText(PubSubmitActivity.this, "Video Uploaded", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(PubSubmitActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//                                        Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
//                                        startActivity(in);
//                                    }
//
//                                    if (adType.equals("2")) {
//                                        Toast.makeText(PubSubmitActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(PubSubmitActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//                                        Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
//                                        startActivity(in);
//                                    }
//
//                                    if (adType.equals("3")) {
//                                        Toast.makeText(PubSubmitActivity.this, "Audio File Uploaded", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(PubSubmitActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//                                        Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
//                                        startActivity(in);
//                                    }
//                                }
                            }
                        });
                    }
                    if (boolCancel == true) {
                        Toast.makeText(PubSubmitActivity.this, "Upload Canceled", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialogLoad.setProgress(0);
//            dialogLoad.setMessage("Uploading...");
//            dialogLoad.setCancelable(false);
//            dialogLoad.setIndeterminate(false);
//            dialogLoad.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialogLoad.setMax(100);
//            dialogLoad.setProgress(0);
//            dialogLoad.show();

            runOnUiThread(new Runnable() {
                public void run() {
                    dialogLoad = new ProgressDialog(PubSubmitActivity.this);
                    dialogLoad.setMessage("Processing...");
                    dialogLoad.setIndeterminate(false);
                    dialogLoad.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialogLoad.setMax(100);
                    dialogLoad.setProgress(0);


                    dialogLoad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        // Set a click listener for progress dialog cancel button
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss the progress dialog
                            try {
                                conn.disconnect();
                                boolCancel = true;
                            } catch (NetworkOnMainThreadException e) {

                            }
                            dialogLoad.dismiss();

                            // Tell the system about cancellation
                            //isCanceled = true;
                        }
                    });


                    dialogLoad.show();
                    dialogLoad.setCanceledOnTouchOutside(false);

                }
            });

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            dialogLoad.setProgress(progress[0]);
            Log.d("ProgressU ", String.valueOf(progress[0]) + "%");
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    dialogLoad.setProgress(0);
//                }
//            });
        }
    }

    public void StripeDetails(
            final String token,
            final String amount,
            final String email,
            final String pubID,
            final String pubToken,
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
            final String questions,
            final String ref
    ) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            String updateURL;

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.d("adType ", adType);
                Log.d("stripeToken ", token);
                Log.d("stripeAmount ", amount);
                Log.d("stripeEmail ", email);
                Log.d("stripePubId ", pubID);
                Log.d("pubToken ", pubToken);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);
                Log.d("title ", title);

                nameValuePairs.add(new BasicNameValuePair("stripeToken", token));
                nameValuePairs.add(new BasicNameValuePair("stripeAmountInCents", amount));
                nameValuePairs.add(new BasicNameValuePair("stripeEmail", email));
                nameValuePairs.add(new BasicNameValuePair("stripePubID", pubID));
                nameValuePairs.add(new BasicNameValuePair("stripePubToken", pubToken));
                nameValuePairs.add(new BasicNameValuePair("stripeDescription", "Deposit to Account"));

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
                nameValuePairs.add(new BasicNameValuePair("ref", ref));

                if (adType == "2") {
                    nameValuePairs.add(new BasicNameValuePair("imgRef", ref));
                }
                if (adType == "3") {
                    nameValuePairs.add(new BasicNameValuePair("audRef", ref));
                }

                try {

                    updateURL = StripeDepositURL;
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
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                super.onPostExecute(result);
                Log.d("Stripe_Result ", result);
                if (result.equals("Invalid parameters supplied")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                }
                if (result.contains("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PubSubmitActivity.this, "Deposit was Successful!", Toast.LENGTH_SHORT).show();
                            paymentCheck = true;
                        }
                    });

                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(token, amount, email, pubID, description);
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

                    durationKey = durationKey1 + url + durationKey2;
                    //------------------>>
                    HttpGet httppost = new HttpGet(durationKey);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(httppost);

                    // StatusLine stat = response.getStatusLine();
                    int status = response.getStatusLine().getStatusCode();

                    Log.d("params ", params[0]);
                    Log.d("url ", url);
                    Log.d("Key1 ", durationKey1);
                    Log.d("Key2 ", durationKey2);
                    Log.d("Key3 ", durationKey);

                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        String data = EntityUtils.toString(entity);

                        JSONObject jObj = new JSONObject(data);

                        String tArray = jObj.getString("items");

                        Log.d("items ", jObj.getString("items"));

                        JSONArray jArray = new JSONArray(tArray);
                        JSONObject wObj = jArray.getJSONObject(0);

                        JSONObject tObj = new JSONObject(wObj.getString("contentDetails"));
                        String duration = tObj.getString("duration");
                        try {

                            Pattern p = Pattern.compile("-?\\d+");
                            Matcher m = p.matcher(duration);
                            while (m.find()) {
                                allMatches.add(m.group());
                            }

                            try {
                                Log.d("seconds_f ", allMatches.get(1));
                                Log.d("minutes_f ", allMatches.get(0));
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println(e);
                            }

//                            if (duration.contains("P") && duration.contains("T") && duration.contains("S")) {
//                                duration = duration.replace("P", "").replace("T", "").replace("S", "");
//                                Log.d("duration ", duration);
//                            }
//                            if (duration.contains("M")) {
//                                String min = String.valueOf(duration.substring(0, 1));
//                                String seconds = duration.replace(min, "").replace("M", "");
//
//                                Log.d("min ", min);
//                                Log.d("seconds ", seconds);
//
//                                int total = (Integer.parseInt(min) * 60) + Integer.parseInt(seconds);
//                                Log.d("totalTime ", String.valueOf(total));
//                                duration = String.valueOf(total);
//                            }
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
                Log.d("durationResult ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("duration", result);
                edit.commit();
                altDuration = result;
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(url);
    }

    public void DataFunction2(final String url) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            String g_duration;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(final String... params) {

                try {

                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();

                    durationKey = durationKey1 + url + durationKey2;
                    //------------------>>
                    HttpGet httppost = new HttpGet(durationKey);
                    HttpClient httpclient = new DefaultHttpClient();
                    final HttpResponse response = httpclient.execute(httppost);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int status = response.getStatusLine().getStatusCode();

                                Log.d("params ", params[0]);
                                Log.d("url ", url);
                                Log.d("Key1 ", durationKey1);
                                Log.d("Key2 ", durationKey2);
                                Log.d("Key3 ", durationKey);

                                if (status == 200) {
                                    HttpEntity entity = response.getEntity();
                                    String data = EntityUtils.toString(entity);

                                    JSONObject jObj = new JSONObject(data);

                                    String tArray = jObj.getString("items");

                                    Log.d("items ", jObj.getString("items"));

                                    JSONArray jArray = new JSONArray(tArray);
                                    JSONObject wObj = jArray.getJSONObject(0);

                                    JSONObject tObj = new JSONObject(wObj.getString("contentDetails"));
                                    String duration = tObj.getString("duration");
                                    try {

                                        Pattern p = Pattern.compile("-?\\d+");
                                        Matcher m = p.matcher(duration);
                                        while (m.find()) {
                                            allMatches.add(m.group());
                                        }

                                        Log.d("seconds_f ", allMatches.get(1));
                                        Log.d("minutes_f ", allMatches.get(0));

                                    } catch (NumberFormatException e) {
                                        System.out.println(e);
                                    }

                                    g_duration = duration;
                                }
                            } catch (JSONException e) {
                                System.out.println(e);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("durationResult ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("duration", result);
                edit.commit();
                altDuration = result;
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(url);
    }


    public void SubmitAd(final String url, final String raadz_user_id, final String token, final String title, final String info, final String duration, final String id, final String total, final String views, final String promo, final String gender, final String age, final String location, final String interests, final String questions) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                Log.d(" - ", " - ");
                Log.d("url ", url);
                Log.d("pub_id ", raadz_user_id);
                Log.d("token ", token);
                Log.d("title ", title);
                Log.d("info ", info);
                Log.d("id ", id);
                Log.d("total ", total);
                Log.d("views ", views);
                Log.d("promo ", promo);
                Log.d("gender ", gender);
                Log.d("age ", age);
                Log.d("location ", location);
                Log.d("interests ", interests);
                Log.d("questions ", questions);
                Log.d(" - ", " - ");

                nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("title", title));
                nameValuePairs.add(new BasicNameValuePair("link", info));

                if (adType.equals("1")) {
                    nameValuePairs.add(new BasicNameValuePair("ytID", id));
                    nameValuePairs.add(new BasicNameValuePair("duration", duration));

                }
                if (adType.equals("2")) {
                    nameValuePairs.add(new BasicNameValuePair("imgRef", id));

                }
                if (adType.equals("3")) {
                    nameValuePairs.add(new BasicNameValuePair("audRef", id));
                    nameValuePairs.add(new BasicNameValuePair("duration", duration));

                }
                nameValuePairs.add(new BasicNameValuePair("cost", total));
                nameValuePairs.add(new BasicNameValuePair("totviews", views));
                nameValuePairs.add(new BasicNameValuePair("promo", promo));
                nameValuePairs.add(new BasicNameValuePair("gender", gender));
                nameValuePairs.add(new BasicNameValuePair("ageVal", age));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("interests", interests));
                nameValuePairs.add(new BasicNameValuePair("questVal", questions));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(url);
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
                Log.d(" - ", " - ");
                Log.d("bPost Result ", result);
                Log.d(" - ", " - ");
                if (result.contains("Insufficient Account Balance")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.contains("invalid promo code")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.contains("Publisher not found")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.contains("promo code expired")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.contains("invalid call")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.contains("Invalid Video Length")) {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PubSubmitActivity.this, result, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(PubSubmitActivity.this, FragPubActivity.class);
                    startActivity(in);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(url, raadz_user_id, token, title, info, duration, id, total, views, promo, gender, age, location, interests, questions);
    }


    public void DirectReference(
            final String token,
            final String amount,
            final String email,
            final String pubID,
            final String pubToken,
            final String description,
            final String title,
            final String ytID,
            final String link,
            final String promo,
            final String duration,
            final String totviews,
            final String gender,
            final String age,
            final String location,
            final String interests,
            final String questions,
            final String ref) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

//                Log.d("stripeToken ", token);
                Log.d(" x ", " x ");
                Log.d("stripeAmount ", amount);
                Log.d("stripeEmail ", email);
                Log.d("stripePubId ", pubID);
                Log.d("pubToken ", pubToken);
                Log.d("description ", description);
                Log.d("title ", title);
                Log.d("id ", ytID);
                Log.d("link ", link);
                Log.d("promo ", promo);
                Log.d("duratation ", duration);
                Log.d("views ", totviews);
                Log.d("gender ", gender);
                Log.d("age ", age);
                Log.d("location ", location);
                Log.d("interests ", interests);
                Log.d("questions ", questions);
                Log.d("ref ", ref);
                Log.d(" x ", " x ");


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("stripeToken", token));
                nameValuePairs.add(new BasicNameValuePair("stripeEmail", email));
                nameValuePairs.add(new BasicNameValuePair("stripeAmountInCents", amount));
                nameValuePairs.add(new BasicNameValuePair("stripeDescription", "25"));
                nameValuePairs.add(new BasicNameValuePair("pubID", pubID));
                nameValuePairs.add(new BasicNameValuePair("pubToken", pubToken));

                if (adType.equals("1")) {
                    nameValuePairs.add(new BasicNameValuePair("ytID", ytID));
                }
                if (adType.equals("2")) {
                    nameValuePairs.add(new BasicNameValuePair("imgRef", ytID));
                }
                if (adType.equals("3")) {
                    nameValuePairs.add(new BasicNameValuePair("audRef", ytID));
                }
                nameValuePairs.add(new BasicNameValuePair("title", title));
                nameValuePairs.add(new BasicNameValuePair("link", link));
                nameValuePairs.add(new BasicNameValuePair("promo", promo));
                nameValuePairs.add(new BasicNameValuePair("duration", preferences.getString("duration", "")));
                nameValuePairs.add(new BasicNameValuePair("totviews", totviews));
                nameValuePairs.add(new BasicNameValuePair("gender", gender));
                nameValuePairs.add(new BasicNameValuePair("ageVal", age));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("interests", interests));
                nameValuePairs.add(new BasicNameValuePair("questVal", questions));
                nameValuePairs.add(new BasicNameValuePair("ref", ref));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    if (adType.equals("1")) {
                        u_URL = StripeDepositVideoURL;
                    }
                    if (adType.equals("2")) {
                        u_URL = StripeChargeImageURL;
                    }
                    if (adType.equals("3")) {
                        u_URL = StripeChargeAudioURL;
                    }
                    HttpPost httpPost = new HttpPost(u_URL);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(u_URL);
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
                Log.d("Result: ", result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(token, amount, email, pubID, pubToken, description, title, ytID, link, promo, duration, totviews, gender, age, location, interests, questions, ref);
    }


    public void AdvertiserFunction(final String raadz_user_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(AdvertiserDataURL);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(AdvertiserDataURL);
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
                info = httpRequest;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("info").commit();
                editor.putString("info", info);
                editor.commit();
                Log.d("info: ", preferences.getString("info", ""));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        //Log.d("httpRequestUI: ", httpRequest);
                        editor.putString("money", httpRequest);
                        editor.apply();
                        balance = preferences.getString("cash", "");
                        Log.d("balance: ", balance);
                        try {
                            JSONArray jArray = new JSONArray(balance);
                            //Log.d("json: ", httpRequest);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                Log.d("money: ", jObj.getString("balance"));
                                editor.putString("money", jObj.getString("balance"));
                                editor.commit();
                                money = jObj.getString("balance");
                            }
                        } catch (org.json.JSONException e) {
                            System.out.println();
                        }
                        String ok = preferences.getString("cash", "");
                        //Log.d("runUIThread ", money);
                        Log.d("httpUI ", httpRequest);
                        //String ok = preferences.getString("cash", "");
                        tvMoney.setText("$" + ok);
                    }
                });
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Log.d("Result: ", result);
                if (result.equals("user not found")) {
                    Log.d("echo 1: ", "user not found");
                }
                if (result.equals("invalid post data")) {
                    Log.d("echo 2: ", "invalid post data");
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("info", result);
                    editor.commit();
                    Log.d("Result: ", preferences.getString("info", ""));
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id);
    }


    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }
}


