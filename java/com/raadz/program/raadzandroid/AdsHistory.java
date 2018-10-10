package com.raadz.program.raadzandroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.value;
import static android.R.id.edit;
import static com.raadz.program.raadzandroid.R.id.LLHeader;
import static com.raadz.program.raadzandroid.R.id.LLStarted;
import static com.raadz.program.raadzandroid.R.id.LLSubmit;
import static com.raadz.program.raadzandroid.R.id.bGoogleLogin;
import static com.raadz.program.raadzandroid.R.id.bHistory;
import static com.raadz.program.raadzandroid.R.id.bShare;
import static com.raadz.program.raadzandroid.R.id.bWithdraw;
import static com.raadz.program.raadzandroid.R.id.rbFemale;
import static com.raadz.program.raadzandroid.R.id.rbMale;
import static com.raadz.program.raadzandroid.R.id.rbOptOut;
import static com.raadz.program.raadzandroid.R.id.rbRecieve;
import static com.raadz.program.raadzandroid.R.id.rgGender;
import static com.raadz.program.raadzandroid.R.id.tvBalance;

public class AdsHistory extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    GoogleApiClient googleApiClient;
    SignInButton bGoogleSignIn;

    ShareDialog shareDialog;

    ImageButton ibProfile;

    ProgressDialog progressDialog;
    View viewGlobal;
    LoginButton bFacebookLogin;
    CallbackManager callbackManager;

    RadioGroup rgPreferences;
    RadioButton rbReceive;
    RadioButton rbOptOut;

    LinearLayout LLCopy;
    LinearLayout LLLeaderboards;
    LinearLayout LLLinkedAccounts;
    LinearLayout LLShareButtons;
    LinearLayout LLPendingHeader;
    LinearLayout LLPending;
    LinearLayout LLHist1;
    LinearLayout LLHist2;
    LinearLayout LLHist3;
    LinearLayout LLHist4;
    LinearLayout LLHist5;

    Button bUserPref;
    Button bExpand;
    Button bLinkedAccounts1;
    Button bLinkedAccounts2;
    Button bWithdrawRDZ;
    Button bWithdrawUSD;
    Button bShareFacebook;
    Button bShareDisplay1;
    Button bShareDisplay2;
    Button bShareGoogle;
    Button bShareTwitter;
    Button bListFacebook;
    Button bListGoogle;
    Button bLogout;
    Button bSaveEmail;

    TextView tvComplete;
    TextView tvFBSuccess;
    TextView tvFBSuccessEmail;
    TextView tvGoogleSuccess;
    TextView tvGoogleSuccessEmail;
    TextView tvMoney;
    TextView tvFirst;
    TextView tvLast;
    TextView tvEmail;
    TextView tvTitleName;
    TextView tvTitleEmail;
    TextView tvEarnings;
    TextView tvAdEarnings;
    TextView tvDrawingEarnings;
    TextView tvEarningsReferrals;
    TextView tvReferralsNumber;
    TextView tvReferral;
    TextView tvCrypto;
    TextView tvBalance;
    TextView tvBonus;

    TextView tvNumber1;
    TextView tvCompanyHist1;
    TextView tvTitleHist1;
    TextView tvPercentileHist1;
    TextView tvEarningsHist1;
    TextView tvCompletedHist1;

    TextView tvNumber2;
    TextView tvCompanyHist2;
    TextView tvTitleHist2;
    TextView tvPercentileHist2;
    TextView tvEarningsHist2;
    TextView tvCompletedHist2;

    TextView tvNumber3;
    TextView tvCompanyHist3;
    TextView tvTitleHist3;
    TextView tvPercentileHist3;
    TextView tvEarningsHist3;
    TextView tvCompletedHist3;

    TextView tvNumber4;
    TextView tvCompanyHist4;
    TextView tvTitleHist4;
    TextView tvPercentileHist4;
    TextView tvEarningsHist4;
    TextView tvCompletedHist4;

    TextView tvNumber5;
    TextView tvCompanyHist5;
    TextView tvTitleHist5;
    TextView tvPercentileHist5;
    TextView tvEarningsHist5;
    TextView tvCompletedHist5;

    TextView tvfCompanyHist1;
    TextView tvfTitleHist1;
    TextView tvfEarningsHist1;
    TextView tvfPercentileHist1;

    TextView tvfCompanyHist2;
    TextView tvfTitleHist2;
    TextView tvfEarningsHist2;
    TextView tvfPercentileHist2;

    TextView tvfCompanyHist3;
    TextView tvfTitleHist3;
    TextView tvfEarningsHist3;
    TextView tvfPercentileHist3;

    TextView tvfCompanyHist4;
    TextView tvfTitleHist4;
    TextView tvfEarningsHist4;
    TextView tvfPercentileHist4;

    TextView tvfCompanyHist5;
    TextView tvfTitleHist5;
    TextView tvfEarningsHist5;
    TextView tvfPercentileHist5;

    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    String EmailPreferenceURL = "https://raadz.com/updateUserPrefs.php";
    String ServerLoginURL = "https://raadz.com/mobilelogin.php";
    String getUserDataURL = "https://raadz.com/getUserData.php";
    String getFacebookLink = "https://raadz.com/fbuserlink.php";
    String cityListURL = "https://raadz.com/getCityList.php";
    String RaadzIDURL = "https://raadz.com/getRaadzIDs.php";
    String fbRegisterURL = "https://raadz.com/fbusersignup.php";
    String RaadzTokenURL = "https://raadz.com/getTokenValue.php";
    String WithdrawCryptoURL = "https://raadz.com/withdrawCrypto.php";
    String twitter_consumer = "E11yDuBsmv8WwFobZE8hDGXJ5";
    String twitter_secret = "ky8mnrSWefmCuQZxIi1dpNpL1NRJWrdN7WwDo5qQZvS46o4Do3";
    String fb_id_pass;
    String fb_pub_pass;
    String fb_token_pass;
    String buttonPlaceholder;
    String userName;
    String userFirst;
    String userLast;
    String userEmail;
    String httpRequest;
    String complete;
    String balance;
    String money;
    String color;
    String company_name;
    String ad_title;
    String fbContent;
    String s_preference = "";

    int JSONLength;
    int colorChanger = 0;

    double i_ad;
    double i_drawing;
    double i_refer;

    String REQ_TOKEN = "";
    private static final int REQ_CODE = 9001;

    boolean taskBool = false;
    boolean linkedClicked = false;
    boolean shareClicked = false;

    NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Twitter.initialize(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                twitter_consumer,
                twitter_secret);

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_ads_history);

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

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        REQ_TOKEN = preferences.getString("google_oauth2", "");

//        listView = (ExpandableListView)findViewById(R.id.evList);
//        initData();
//        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
//        listView.setAdapter(listAdapter);

        Log.d("list_button ", preferences.getString("list_button", ""));

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        rgPreferences = (RadioGroup) findViewById(R.id.rgPreferences);
        rbReceive = (RadioButton) findViewById(rbRecieve);
        rbOptOut = (RadioButton) findViewById(R.id.rbOptOut);

        LLCopy = (LinearLayout) findViewById(R.id.LLCopy);
        LLLinkedAccounts = (LinearLayout) findViewById(R.id.LLLinkedAccounts);
        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLShareButtons = (LinearLayout) findViewById(R.id.LLShareButtons);
        LLPendingHeader = (LinearLayout) findViewById(R.id.LLPendingHeader);
        LLPending = (LinearLayout) findViewById(R.id.LLPending);
        LLHist1 = (LinearLayout) findViewById(R.id.LLHist1);
        LLHist2 = (LinearLayout) findViewById(R.id.LLHist2);
        LLHist3 = (LinearLayout) findViewById(R.id.LLHist3);
        LLHist4 = (LinearLayout) findViewById(R.id.LLHist4);
        LLHist5 = (LinearLayout) findViewById(R.id.LLHist5);

        bUserPref = (Button) findViewById(R.id.bUserPref);
        bExpand = (Button) findViewById(R.id.bExpand);
        bLinkedAccounts1 = (Button) findViewById(R.id.bLinkAccounts1);
        bLinkedAccounts2 = (Button) findViewById(R.id.bLinkAccounts2);
        bWithdrawRDZ = (Button) findViewById(R.id.bWithdrawRDZ);
        bWithdrawUSD = (Button) findViewById(R.id.bWithdrawUSD);
        bShareFacebook = (Button) findViewById(R.id.bShareFacebook);
        bShareTwitter = (Button) findViewById(R.id.bShareTwitter);
        bListGoogle = (Button) findViewById(R.id.bListGoogle);
        bListFacebook = (Button) findViewById(R.id.bListFacebook);
        bLogout = (Button) findViewById(R.id.bLogout);
        bSaveEmail = (Button) findViewById(R.id.bSaveEmail);
        bShareDisplay1 = (Button) findViewById(R.id.bShareDisplay1);
        bShareDisplay2 = (Button) findViewById(R.id.bShareDisplay2);

        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        tvCompanyHist1 = (TextView) findViewById(R.id.tvCompanyHist1);
        tvTitleHist1 = (TextView) findViewById(R.id.tvTitleHist1);
        tvPercentileHist1 = (TextView) findViewById(R.id.tvPercentileHist1);
        tvDrawingEarnings = (TextView) findViewById(R.id.tvDrawingEarnings);
        tvEarningsHist1 = (TextView) findViewById(R.id.tvEarningsHist1);
        tvCompletedHist1 = (TextView) findViewById(R.id.tvCompletedHist1);
        tvTitleEmail = (TextView) findViewById(R.id.tvTitleEmail);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);

        tvNumber2 = (TextView) findViewById(R.id.tvNumber2);
        tvCompanyHist2 = (TextView) findViewById(R.id.tvCompanyHist2);
        tvTitleHist2 = (TextView) findViewById(R.id.tvTitleHist2);
        tvPercentileHist2 = (TextView) findViewById(R.id.tvPercentileHist2);
        tvEarningsHist2 = (TextView) findViewById(R.id.tvEarningsHist2);
        tvCompletedHist2 = (TextView) findViewById(R.id.tvCompletedHist2);

        tvNumber3 = (TextView) findViewById(R.id.tvNumber3);
        tvCompanyHist3 = (TextView) findViewById(R.id.tvCompanyHist3);
        tvTitleHist3 = (TextView) findViewById(R.id.tvTitleHist3);
        tvPercentileHist3 = (TextView) findViewById(R.id.tvPercentileHist3);
        tvEarningsHist3 = (TextView) findViewById(R.id.tvEarningsHist3);
        tvCompletedHist3 = (TextView) findViewById(R.id.tvCompletedHist3);

        tvNumber4 = (TextView) findViewById(R.id.tvNumber4);
        tvCompanyHist4 = (TextView) findViewById(R.id.tvCompanyHist4);
        tvTitleHist4 = (TextView) findViewById(R.id.tvTitleHist4);
        tvPercentileHist4 = (TextView) findViewById(R.id.tvPercentileHist4);
        tvEarningsHist4 = (TextView) findViewById(R.id.tvEarningsHist4);
        tvCompletedHist4 = (TextView) findViewById(R.id.tvCompletedHist4);

        tvNumber5 = (TextView) findViewById(R.id.tvNumber5);
        tvCompanyHist5 = (TextView) findViewById(R.id.tvCompanyHist5);
        tvTitleHist5 = (TextView) findViewById(R.id.tvTitleHist5);
        tvPercentileHist5 = (TextView) findViewById(R.id.tvPercentileHist5);
        tvEarningsHist5 = (TextView) findViewById(R.id.tvEarningsHist5);
        tvCompletedHist5 = (TextView) findViewById(R.id.tvCompletedHist5);

        tvfCompanyHist1 = (TextView) findViewById(R.id.tvfCompanyHist1);
        tvfTitleHist1 = (TextView) findViewById(R.id.tvfTitleHist1);
        tvfEarningsHist1 = (TextView) findViewById(R.id.tvfEarningsHist1);
        tvfPercentileHist1 = (TextView) findViewById(R.id.tvfPercentileHist1);

        tvfCompanyHist2 = (TextView) findViewById(R.id.tvfCompanyHist2);
        tvfTitleHist2 = (TextView) findViewById(R.id.tvfTitleHist2);
        tvfEarningsHist2 = (TextView) findViewById(R.id.tvfEarningsHist2);
        tvfPercentileHist2 = (TextView) findViewById(R.id.tvfPercentileHist2);

        tvfCompanyHist3 = (TextView) findViewById(R.id.tvfCompanyHist3);
        tvfTitleHist3 = (TextView) findViewById(R.id.tvfTitleHist3);
        tvfEarningsHist3 = (TextView) findViewById(R.id.tvfEarningsHist3);
        tvfPercentileHist3 = (TextView) findViewById(R.id.tvfPercentileHist3);

        tvfCompanyHist4 = (TextView) findViewById(R.id.tvfCompanyHist4);
        tvfTitleHist4 = (TextView) findViewById(R.id.tvfTitleHist4);
        tvfEarningsHist4 = (TextView) findViewById(R.id.tvfEarningsHist4);
        tvfPercentileHist4 = (TextView) findViewById(R.id.tvfPercentileHist4);

        tvfCompanyHist5 = (TextView) findViewById(R.id.tvfCompanyHist5);
        tvfTitleHist5 = (TextView) findViewById(R.id.tvfTitleHist5);
        tvfEarningsHist5 = (TextView) findViewById(R.id.tvfEarningsHist5);
        tvfPercentileHist5 = (TextView) findViewById(R.id.tvfPercentileHist5);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvFirst = (TextView) findViewById(R.id.tvFirst);
        tvLast = (TextView) findViewById(R.id.tvLast);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvEarnings = (TextView) findViewById(R.id.tvEarnings);
        tvEarningsReferrals = (TextView) findViewById(R.id.tvEarningsReferrals);
        tvReferralsNumber = (TextView) findViewById(R.id.tvReferralsNumber);
        tvReferral = (TextView) findViewById(R.id.tvReferral);
        tvCrypto = (TextView) findViewById(R.id.tvCrypto);
        tvAdEarnings = (TextView) findViewById(R.id.tvAdEarnings);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBonus = (TextView) findViewById(R.id.tvBonus);
        tvFBSuccess = (TextView) findViewById(R.id.tvFBSuccess);
        tvFBSuccessEmail = (TextView) findViewById(R.id.tvFBSuccessEmail);
        tvGoogleSuccess = (TextView) findViewById(R.id.tvGoogleSuccess);
        tvGoogleSuccessEmail = (TextView) findViewById(R.id.tvGoogleSuccessEmail);

        bFacebookLogin = (LoginButton) findViewById(R.id.bFacebookLogin);
        bFacebookLogin.setReadPermissions("public_profile, email");
        callbackManager = CallbackManager.Factory.create();
        bGoogleSignIn = (SignInButton) findViewById(bGoogleLogin);

        balance = preferences.getString("funds", "");
        tvMoney.setText("$" + balance);
        tvBalance.setText("$" + balance);
//        tvAdEarnings.setText("$" + balance);

        Log.d("test id ", preferences.getString("raadz_user_id", ""));
        Log.d("test token ", preferences.getString("token", ""));

        String raadz_user_id = preferences.getString("raadz_user_id", "");
        String token = preferences.getString("token", "");
        DataFunction(raadz_user_id, token);
        complete = preferences.getString("completed", "");
//        tvNumber1.setTextSize(getResources().getDimension(R.dimen.text_size));
//        tvNumber2.setTextSize(getResources().getDimension(R.dimen.text_size));
//        tvNumber3.setTextSize(getResources().getDimension(R.dimen.text_size));
//        tvNumber4.setTextSize(getResources().getDimension(R.dimen.text_size));
//        tvNumber5.setTextSize(getResources().getDimension(R.dimen.text_size));

        Log.d("echo: ", complete);

        String result = preferences.getString("accountResult", "");
        Log.d("result ", "here");
        Log.d("first ", preferences.getString("fName", ""));
        Log.d("last ", preferences.getString("lName", ""));


        CryptoFunction();
        bShareFacebook = (Button) findViewById(R.id.bShareFacebook);
        GoogleSignInOptions signInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(REQ_TOKEN).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOption).build();

        FacebookSdk.sdkInitialize(getApplicationContext());

        Log.d("email_frequency_main ", preferences.getString("email_frequency", ""));

        if (preferences.getString("email_frequency", "").equals("0")) {
            rbReceive.setChecked(true);
        } else if (preferences.getString("email_frequency", "").equals("1")) {
            rbOptOut.setChecked(true);
        }

//        evList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bListFacebook.setVisibility(View.VISIBLE);
//                bListGoogle.setVisibility(View.VISIBLE);
//            }
//        });

        bUserPref.setOnClickListener(this);

        bGoogleSignIn.setOnClickListener(this);

        bWithdrawRDZ.setOnClickListener(this);

        bWithdrawUSD.setOnClickListener(this);

        bListFacebook.setOnClickListener(this);

        bListGoogle.setOnClickListener(this);

        bShareFacebook.setOnClickListener(this);

        bShareTwitter.setOnClickListener(this);

        bShareDisplay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bShareDisplay2.setVisibility(View.VISIBLE);
                bShareDisplay1.setVisibility(View.GONE);
                LLShareButtons.setVisibility(View.VISIBLE);
            }
        });

        bShareDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bShareDisplay1.setVisibility(View.VISIBLE);
                bShareDisplay2.setVisibility(View.GONE);
                LLShareButtons.setVisibility(View.GONE);
            }
        });

        bLinkedAccounts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bLinkedAccounts1.setVisibility(View.GONE);
                bLinkedAccounts2.setVisibility(View.VISIBLE);
                LLLinkedAccounts.setVisibility(View.VISIBLE);
                if (!preferences.getString("fb_account", "").equals("null")) {
                    bFacebookLogin.setVisibility(View.GONE);
                    tvFBSuccess.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setText(preferences.getString("fb_account", ""));
                }
                if (!preferences.getString("google_account", "").equals("null")) {
                    bGoogleSignIn.setVisibility(View.GONE);
                    tvGoogleSuccess.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setText(preferences.getString("google_account", ""));
                }
            }
        });

        bLinkedAccounts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bLinkedAccounts2.setVisibility(View.GONE);
                bLinkedAccounts1.setVisibility(View.VISIBLE);
                LLLinkedAccounts.setVisibility(View.GONE);
                if (!preferences.getString("fb_account", "").equals("null")) {
                    bFacebookLogin.setVisibility(View.GONE);
                    tvFBSuccess.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setText(preferences.getString("fb_account", ""));
                }
                if (!preferences.getString("google_account", "").equals("null")) {
                    bGoogleSignIn.setVisibility(View.GONE);
                    tvGoogleSuccess.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setText(preferences.getString("google_account", ""));
                }
            }
        });


        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        rgPreferences.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbRecieve:
                        if (rbReceive.isChecked()) {
                            s_preference = "0";
                        }
                        break;
                    case R.id.rbOptOut:
                        if (rbOptOut.isChecked()) {
                            s_preference = "1";
                        }
                        break;
                }
            }
        });

        bSaveEmail.setOnClickListener(this);


//        bShareFacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//                    @Override
//                    public void onSuccess(Sharer.Result result) {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//
//                    }
//                });
//                ShareLinkContent linkContent = new ShareLinkContent.Builder().setQuote("setting the quote").build();
//                if(ShareDialog.canShow(ShareLinkContent.class)){
//                    shareDialog.show(linkContent);
//                }
//            }
//        });


//        edit.putString("fdID", idFB);
//        edit.putString("email", email);
//        edit.putString("fName", fName);
//        edit.putString("mName", mName);
//        edit.putString("lName", lName);
//        edit.putString("fbToken", token);
//        edit.commit();


        bFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

                System.out.println("onSuccess");
                progressDialog = new ProgressDialog(AdsHistory.this);
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.d("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("LoginActivity", response.toString());
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        //String id = bFacebookData.getString("id", "");
                        String idFB = bFacebookData.getString("idFacebook", "");
                        String email = bFacebookData.getString("email", "");
                        String fName = bFacebookData.getString("first_name", "");
                        String lName = bFacebookData.getString("last_name", "");
                        String token = loginResult.getAccessToken().getToken();
                        //Log.d("token ", token);
                        Log.d(bFacebookData.getString("idFacebook", ""), "");
                        Log.d("", "");
                        Log.d("fbID ", idFB);
                        //Log.d("id ", id);
                        Log.d("token ", token);
                        Log.d("email ", email);
                        Log.d("fName ", fName);
                        Log.d("lName ", lName);
                        Log.d("", "");

                        edit.putString("fdID", idFB);
                        edit.putString("email", email);
                        edit.putString("fName", fName);
                        edit.putString("lName", lName);
                        edit.putString("fbToken", token);
                        edit.commit();


                        new SendPostReqAsyncTask().execute(
                                preferences.getString("raadz_user_id", ""),
                                preferences.getString("token", ""),
                                idFB,
                                email,
                                fName,
                                lName,
                                token);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
                progressDialog.dismiss();
            }

            @Override
            public void onCancel() {
                Log.d("facebook - onCancel", "cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("facebook - onError", error.getMessage());
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutMethod();
            }
        });

    }


    public void login(TwitterSession session) {

    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d("TAG", "Error parsing JSON");
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    public void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            final GoogleSignInAccount account = result.getSignInAccount();

            String fname = account.getGivenName();
            String lname = account.getFamilyName();
            String email = account.getEmail();
            String username = account.getDisplayName();
            String id = account.getId();
            String temp = result.getSignInAccount().getIdToken();

            edit.putString("google_fname", fname);
            edit.putString("google_lname", lname);
            edit.putString("google_email", email);
            edit.putString("google_username", username);
            edit.putString("google_id", id);
            edit.putString("google_token", temp);
            edit.commit();

            Log.d("Googlefname ", fname);
            Log.d("Googlelname ", lname);
            Log.d("Googleemail ", email);
            Log.d("Googlefusername ", username);
            Log.d("Googleid ", id);
            Log.d("Googletoken ", temp);
        } else {
            Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void fbShare() {
        ShareDialog shareDialog = new ShareDialog(AdsHistory.this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String referral = preferences.getString("referral_id", "");
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setQuote("Check out Raadz!  Use my Referral code to earn extra cash! \n" + "Referral Code: " + referral)
                    .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                    .setContentUrl(Uri.parse("https://beta.raadz.com/"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

//    public void initData(){
//        listDataHeader = new ArrayList<>();
//        listHash = new HashMap<>();
//
//        listDataHeader.add("Link Accounts");
//        listDataHeader.add("Alternate");
//
//        List<String> accLink = new ArrayList<>();
//        accLink.add("Facebook");
//        accLink.add("Google");
//
//        List<String> altList = new ArrayList<>();
//        altList.add("Something1");
//        altList.add("Something2");
//
//        listHash.put(listDataHeader.get(0), accLink);
//        listHash.put(listDataHeader.get(1), altList);
//
//
//
//    }

    public void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Link Accounts");

        List<String> expandList = new ArrayList<>();
        expandList.add("Facebook");
        expandList.add("Twitter");

        listHash.put(listDataHeader.get(0), expandList);

    }


    public void logoutMethod() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdsHistory.this);
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
                Intent in = new Intent(AdsHistory.this, IndexActivity.class);
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
    public void onBackPressed() {

        Intent in = new Intent(this, FragActivity.class);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                        Intent in = new Intent(AdsHistory.this, IndexActivity.class);
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
                    Intent in = new Intent(AdsHistory.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdsHistory.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdsHistory.this);
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

    public void CryptoFunction() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RaadzTokenURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RaadzTokenURL);
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
                Log.d("Set stuff httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                Log.d("Crypto_Result: ", result);
                String[] temp = result.split(":");
                Log.d("text ", temp[0]);
                Log.d("num1 ", temp[1]);
                Log.d("num2 ", temp[2]);
                Log.d("balance ", balance);

                double value1 = Double.parseDouble(balance);
                double value2 = Double.parseDouble(temp[1]);
                double value3 = value1 * value2;

                String limit = temp[1].substring(0, 2);
                Log.d("limit ", limit);
                edit.putString("discount", limit);
                edit.commit();

                tvBonus.setText("(" + limit + "% BONUS!)");

                int value_i = (int) value3;

                tvCrypto.setText(String.valueOf(value_i));
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


    public void DataFunction(final String raadz_user_id, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                Log.d("async id ", raadz_user_id);
                Log.d("async token ", token);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AdsHistory.this, raadz_user_id, Toast.LENGTH_SHORT).show();
                    }
                });
                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getUserDataURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getUserDataURL);
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
                Log.d("Set stuff httpRequest ", httpRequest);


                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                //Log.d("Result: ", result);
                if (result.contains("user not found")) {
                    Log.d("echo 2:  ", "user not found");
                    //Toast.makeText(UpdateInformation.this, "ratings submitted", Toast.LENGTH_SHORT).show();
                    //Intent in = new Intent(PublisherActivity.this, ConfirmEmailActivity.class);
                    //startActivity(in);
                } else {
                    Log.d("else JSON: ", result);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("current_result", result);
                            edit.commit();
                            try {
                                JSONArray jArray = new JSONArray(result);
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject jObj = jArray.getJSONObject(i);
                                    if (jObj.getString("interest_value").equals("user_name")) {
                                        Log.d("stuff ", preferences.getString("postResult", ""));
                                        userName = jObj.getString("interest_text");
                                        Log.d("jObj Name ", jObj.getString("interest_text"));
                                        String[] arr = userName.split(":");
                                        userFirst = arr[0];
                                        userLast = arr[1];
                                        Log.d("", "");
                                        Log.d("Name ", userName);
                                        Log.d("First ", userFirst);
                                        Log.d("Last ", userLast);
                                        Log.d("", "");
                                        tvFirst.setText(userFirst);
                                        tvLast.setText(userLast);
                                        tvTitleName.setText(userFirst + " " + userLast);
                                    }

                                    if (jObj.getString("interest_value").equals("user_email")) {
                                        userEmail = jObj.getString("interest_text");
                                        Log.d("jObj Email ", jObj.getString("interest_text"));
                                        Log.d("", "");
                                        Log.d("Email ", userEmail);
                                        tvEmail.setText(userEmail);
                                        tvTitleEmail.setText(userEmail);

                                    }

//                                    if (jObj.getString("interest_value").equals("total_earned")) {
//                                        Log.d("Total Earned ", jObj.getString("interest_text"));
//                                        tvEarnings.setText("$" + jObj.getString("interest_text"));
//                                    }

                                    if (jObj.getString("interest_value").equals("total_earned")) {
                                        Log.d("Total Earned ", jObj.getString("interest_text"));
                                        tvAdEarnings.setText("$" + jObj.getString("interest_text"));
                                        i_ad = Double.parseDouble(jObj.getString("interest_text"));
                                    }

                                    if (jObj.getString("interest_value").equals("referral_earned")) {
                                        Log.d("Referral Earnings ", jObj.getString("interest_text"));
                                        tvEarningsReferrals.setText("$" + jObj.getString("interest_text"));
                                        i_refer = Double.parseDouble(jObj.getString("interest_text"));
                                    }

                                    if (jObj.getString("interest_value").equals("num_of_referrals")) {
                                        Log.d("Number of Referrals ", jObj.getString("interest_text"));
                                        tvReferralsNumber.setText(jObj.getString("interest_text"));
                                    }

                                    if (jObj.getString("interest_value").equals("referral_id")) {
                                        Log.d("Referral Code ", jObj.getString("interest_text"));
                                        tvReferral.setText(jObj.getString("interest_text"));
                                        edit.putString("referral_id", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    if (jObj.getString("interest_value").equals("fb_account")) {
                                        edit.putString("fb_account", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    if (jObj.getString("interest_value").equals("allow_update")) {
                                        edit.putString("update", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    if (jObj.getString("interest_value").equals("google_account")) {
                                        edit.putString("google_account", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    if (jObj.getString("interest_value").equals("fb_account")) {
                                        edit.putString("fb_account", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    if (jObj.getString("interest_value").equals("reward_earned")) {
                                        Log.d("Reward Earned ", jObj.getString("interest_text"));
                                        tvDrawingEarnings.setText("$" + jObj.getString("interest_text"));
                                        i_drawing = Double.parseDouble(jObj.getString("interest_text"));
                                    }

                                    if (jObj.getString("interest_value").equals("email_frequency")) {
                                        if (jObj.getString("interest_text").equals("0")) {
                                            rbReceive.setChecked(true);
                                        }
                                        if (jObj.getString("interest_text").equals("1")) {
                                            rbOptOut.setChecked(true);
                                        }
                                        Log.d("Email_Frequency ", jObj.getString("interest_text"));
                                        edit.putString("email_frequency", jObj.getString("interest_text"));
                                        edit.commit();
                                    }

                                    Log.d("i_ad", String.valueOf(i_ad));
                                    Log.d("i_drawing", String.valueOf(i_drawing));
                                    Log.d("i_refer", String.valueOf(i_refer));

                                    DecimalFormat twoDecimals = new DecimalFormat("#.##");
                                    double d_temp = i_ad + i_drawing + i_refer;
                                    d_temp = Double.valueOf(twoDecimals.format(d_temp));

                                    tvEarnings.setText("$" + (d_temp));

                                    if (jObj.getString("interest_value").equals("pending_withdrawals")) {
                                        if (jObj.getString("interest_text").length() > 5) {
                                            try {
                                                LLPendingHeader.removeAllViews();
                                                LLPending.removeAllViews();
                                                LinearLayout layout_h = (LinearLayout) getLayoutInflater().inflate(R.layout.pending_layout_header, null);
                                                LLPendingHeader.addView(layout_h);
                                                JSONArray pArray = new JSONArray(result);
                                                for (int p = 0; p < pArray.length(); p++) {
                                                    JSONObject pObj = pArray.getJSONObject(p);
                                                    if (pObj.getString("interest_value").equals("pending_withdrawals")) {
                                                        if (pObj.getString("interest_text").length() > 5) {

                                                            String[] temp = jObj.getString("interest_text").split(":");
                                                            Log.d("temp1 ", temp[0]);
                                                            Log.d("temp2 ", temp[1]);
                                                            Log.d("temp3 ", temp[2]);

                                                            try {
                                                                Double value1 = Double.parseDouble(balance);
                                                                Double value2 = Double.parseDouble(temp[1]);
                                                                Double value3 = value1 * value2;
                                                                tvCrypto.setText(String.valueOf(value3));
                                                            } catch (NumberFormatException e) {
                                                                System.out.println(e);
                                                            }

                                                            Log.d("stuff ", preferences.getString("postResult", ""));
                                                            Log.d("pObj Name ", pObj.getString("interest_text"));

                                                            LinearLayout layout_p = (LinearLayout) getLayoutInflater().inflate(R.layout.pending_layout, null);

                                                            TextView mAmount = (TextView) layout_p.findViewById(R.id.tvLAmount);
                                                            TextView mFrom = (TextView) layout_p.findViewById(R.id.tvLFrom);
                                                            TextView mWithdraw = (TextView) layout_p.findViewById(R.id.tvLWithdraw);

                                                            LLPending.addView(layout_p);

                                                            mAmount.setText(temp[0] + " RDZ");
                                                            mFrom.setText("$" + temp[2]);
                                                            mWithdraw.setText(temp[1]);

                                                        }
                                                    }
                                                }
                                            } catch (org.json.JSONException e) {
                                                System.out.println(e);
                                            }
                                        }
                                    }

                                }
                            } catch (org.json.JSONException e) {
                                System.out.println(e);
                            }
                        }
                    });
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

    @Override
    public void onClick(View v) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (v == bSaveEmail) {
            buttonPlaceholder = "bSaveEmail";
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("email_freq", s_preference);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(EmailPreferenceURL);
        }

        if (v == bFacebookLogin) {
            buttonPlaceholder = "bFacebook";
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("raadz_token", preferences.getString("token", ""));
            postData.put("user_id", preferences.getString("fdID", ""));
            postData.put("email", preferences.getString("email", ""));
            postData.put("first_name", preferences.getString("fName", ""));
            postData.put("last_name", preferences.getString("lName", ""));
            postData.put("fb_access_token", preferences.getString("fbToken", ""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(getFacebookLink);

        }

        if (v == bShareTwitter) {
            buttonPlaceholder = "bShareTwitter";

            String tweetURL = "https://raadz.com?rfid=" + preferences.getString("referral_id", "");
            String tweetText = "Come check out Raadz.com and start earning money";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?url=" + tweetURL + "&text=" + tweetText));
            startActivity(browserIntent);
        }

        if (v == bShareFacebook) {
            ShareDialog shareDialog = new ShareDialog(AdsHistory.this);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                String referral = preferences.getString("referral_id", "");
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Check out Raadz!  Use my Referral code to earn extra cash! \n" + "Referral Code: " + referral)
                        .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                        .setContentUrl(Uri.parse("https://beta.raadz.com/"))
                        .build();

                shareDialog.show(linkContent);
            }
        }

        if (v == bUserPref) {
            if (preferences.getString("update", "").equals("1")) {
                buttonPlaceholder = "bUserPref";
                HashMap postData = new HashMap();
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute(cityListURL);
            } else {
                Toast.makeText(this, "Cannot update information at this time.", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == bGoogleSignIn) {
            buttonPlaceholder = "bGoogleLogin";
            HashMap postData = new HashMap();
            postData.put("google_user_id", preferences.getString("google_id", ""));
            postData.put("google_access_token", preferences.getString("google_token", ""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(RaadzIDURL);
        }

        if (v == bListFacebook) {
            ShareDialog shareDialog = new ShareDialog(AdsHistory.this);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                String referral = preferences.getString("referral_id", "");
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Check out Raadz!  Use my Referral code to earn extra cash! \n" + "Referral Code: " + referral)
                        .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                        .setContentUrl(Uri.parse("https://beta.raadz.com/"))
                        .build();

                shareDialog.show(linkContent);
            }
        }

        if (v == bListGoogle) {
            Log.d("bListGoogle ", "Share Google");
        }

        if (v == bWithdrawRDZ) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.token_withdrawl_layout, null);
            final EditText tokens = (EditText) mView.findViewById(R.id.etAmount);
            final EditText address = (EditText) mView.findViewById(R.id.etAddress);
            final TextView tvTokensWithdraw = (TextView) mView.findViewById(R.id.tvTokensWithdraw);
            final TextView tvTokensBonus = (TextView) mView.findViewById(R.id.tvTokensBonus);
            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mEnter = (Button) mView.findViewById(R.id.bEnter);


            double value_d = Double.parseDouble(tvCrypto.getText().toString());
            Log.d("double_value ", String.valueOf(value_d));

            int value_i = (int) value_d;
            Log.d("int_value ", String.valueOf(value_i));

            tokens.setText(String.valueOf(value_i));
            tvTokensWithdraw.setText(" (" + String.valueOf(value_i) + " available)");

            double fBonus = (value_d * .20) + value_d;
            int iBonus = (int) fBonus;
            tvTokensBonus.setText("Tokens Received: " + String.valueOf(iBonus) + " (" + preferences.getString("discount", "") + "% bonus!)");


            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (address.getText().toString().length() < 42) {
                        Toast.makeText(AdsHistory.this, "Wallet Address length too short", Toast.LENGTH_SHORT).show();
                    }
                    if (address.getText().toString().length() > 42) {
                        Toast.makeText(AdsHistory.this, "Wallet Address length too large", Toast.LENGTH_SHORT).show();
                    }
                    if (address.getText().toString().length() == 42) {
                        CryptoFunctionWithdraw(
                                preferences.getString("raadz_user_id", ""),
                                preferences.getString("token", ""),
                                address.getText().toString(),
                                tokens.getText().toString());
                    }
                }
            });
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (v == bWithdrawUSD) {
            Log.d("bWithdrawUSD ", "you have clicked to withdraw in USD");
        }
//        if(v == bGoogle){
//            Toast.makeText(this, "Function is not ready", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void processFinish(String s) {
        if (buttonPlaceholder.equals("bSaveEmail")) {
            Log.d("bSaveEmail ", s);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        if (buttonPlaceholder.equals("bFacebook")) {
            Log.d("bFacebook Link", s);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        if (buttonPlaceholder.equals("bUserPref")) {
            Log.d("the cities ", s);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("city_list", s);
            edit.putString("from_profile", "true");
            edit.commit();
            Intent in = new Intent(AdsHistory.this, UpdateInformation.class);
            startActivity(in);
        }
        if (buttonPlaceholder.equals("bGoogleLogin")) {
            Toast.makeText(AdsHistory.this, s, Toast.LENGTH_SHORT).show();
//            bGoogleSuccess.setVisibility(View.VISIBLE);
            bGoogleSignIn.setVisibility(View.GONE);
//            bGoogleSuccess.setFocusable(false);
        }
        if (buttonPlaceholder.equals("bWithdrawRDZ")) {
            Log.d("Withdraw Post ", s);
        }
        if (buttonPlaceholder.equals("bWithdrawUSD")) {
            Log.d("Withdraw Post ", s);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
        TextView tvMoney;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvMoney = (TextView) findViewById(R.id.tvMoney);

        }

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("raadz_user_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("raadz_token", params[1]));
            nameValuePairs.add(new BasicNameValuePair("user_id", params[2]));
            nameValuePairs.add(new BasicNameValuePair("email", params[3]));
            nameValuePairs.add(new BasicNameValuePair("first_name", params[4]));
            nameValuePairs.add(new BasicNameValuePair("last_name", params[5]));
            nameValuePairs.add(new BasicNameValuePair("fb_access_token", params[6]));

            try {
                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getFacebookLink);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpPost request = new HttpPost(getFacebookLink);
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
            Log.d("FB Link result ", result);
            if (result.equals("facebook account exists")) {
                Toast.makeText(AdsHistory.this, result, Toast.LENGTH_SHORT).show();
            }
            if (result.equals("success")) {
                Toast.makeText(AdsHistory.this, result, Toast.LENGTH_SHORT).show();
//                bFBSuccess.setVisibility(View.VISIBLE);
//                bFBSuccess.setFocusable(false);
            }
        }
    }


    public void CryptoFunctionWithdraw(final String raadz_user_id, final String token, final String wallet, final String amount) {
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
                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("raadz_token", token));
                nameValuePairs.add(new BasicNameValuePair("wallet_address", wallet));
                nameValuePairs.add(new BasicNameValuePair("withdraw_amount", amount));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(WithdrawCryptoURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(WithdrawCryptoURL);
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

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("pending", "true");
                edit.commit();

                String JSONResult = preferences.getString("current_result", "");

                Log.d("post for withdraw ", result);
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token, wallet, amount);
    }


}
