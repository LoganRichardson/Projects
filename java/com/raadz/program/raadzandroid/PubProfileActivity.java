package com.raadz.program.raadzandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.x;
import static com.raadz.program.raadzandroid.R.id.CardWidget;
import static com.raadz.program.raadzandroid.R.id.bExpand;
import static com.raadz.program.raadzandroid.R.id.bGoogleLogin;
import static com.raadz.program.raadzandroid.R.id.bGoogleSignIn;
import static com.raadz.program.raadzandroid.R.id.rbRecieve;


public class PubProfileActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    ProgressDialog progressDialog;
    View viewGlobal;
    LoginButton bFacebookLogin;
    CallbackManager callbackManager;

    GoogleApiClient googleApiClient;
    SignInButton bGoogleSignIn;

    ImageButton ibProfile;

    CardMultilineWidget CardWidget;

    AlertDialog stripe_dialog;

    LinearLayout LLCopy;
    LinearLayout LLLeaderboards;
    LinearLayout LLLinkedAccounts;
    LinearLayout LLHist1;
    LinearLayout LLHist2;
    LinearLayout LLHist3;
    LinearLayout LLHist4;
    LinearLayout LLHist5;

    RadioGroup rgPreferences;
    RadioButton rbReceive;
    RadioButton rbOptOut;

    Button bHistory;
    Button bExpand;
    Button bDeposit;
    Button bLinkedAccounts1;
    Button bLinkedAccounts2;
    Button bEdit;
    Button bUpdateAddress;
    Button bUpdateCompany;
    Button bLogout;
    Button bSaveEmail;

    EditText etCompany;
    EditText etWallet;

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
    TextView tvAccountBalance;
    TextView tvPending;
    TextView tvActiveAndComplete;

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

    String EmailPreferenceURL = "https://raadz.com/updatePubPrefs.php";
    String StripeDepositURL = "https://raadz.com/charge-deposit-stripe.php";
    String StripeChargeURL = "https://raadz.com/charge-pay-stripe.php";
    String RaadzIDURL = "https://raadz.com/getRaadzIDs.php";
    String getUserDataURL = "https://raadz.com/getUserData.php";
    String getFacebookLink = "https://raadz.com/fbpublink.php";
    String cityListURL = "https://raadz.com/getCityList.php";
    String getPubInfoURL = "https://raadz.com/getPubInfo.php";
    String updateWalletAddressURL = "https://raadz.com/updatePubWalletAddr.php";
    String updateCompanyNameURL = "https://raadz.com/updateCompanyName.php";
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
    String s_preference = "";
    String REQ_TOKEN = "";

    int JSONLength;
    int colorChanger = 0;

    double deposit = 0;

    boolean taskBool = false;
    boolean linkedClicked = false;
    boolean check = false;
    boolean paymentCheck = false;

    NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_profile);


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

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        rgPreferences = (RadioGroup) findViewById(R.id.rgPreferences);
        rbReceive = (RadioButton) findViewById(rbRecieve);
        rbOptOut = (RadioButton) findViewById(R.id.rbOptOut);

        LLCopy = (LinearLayout) findViewById(R.id.LLCopy);
        LLLinkedAccounts = (LinearLayout) findViewById(R.id.LLLinkedAccounts);
        LLLeaderboards = (LinearLayout) findViewById(R.id.LLLeaderboards);
        LLHist1 = (LinearLayout) findViewById(R.id.LLHist1);
        LLHist2 = (LinearLayout) findViewById(R.id.LLHist2);
        LLHist3 = (LinearLayout) findViewById(R.id.LLHist3);
        LLHist4 = (LinearLayout) findViewById(R.id.LLHist4);
        LLHist5 = (LinearLayout) findViewById(R.id.LLHist5);

        bHistory = (Button) findViewById(R.id.bHistory);
        bExpand = (Button) findViewById(R.id.bExpand);
        bDeposit = (Button) findViewById(R.id.bDeposit);
        bLinkedAccounts1 = (Button) findViewById(R.id.bLinkAccounts1);
        bLinkedAccounts2 = (Button) findViewById(R.id.bLinkAccounts2);
        bEdit = (Button) findViewById(R.id.bEdit);
        bUpdateAddress = (Button) findViewById(R.id.bUpdateAddress);
        bUpdateCompany = (Button) findViewById(R.id.bUpdateCompany);
        bLogout = (Button) findViewById(R.id.bLogout);
        bSaveEmail = (Button) findViewById(R.id.bSaveEmail);
        bGoogleSignIn = (SignInButton) findViewById(bGoogleLogin);

        tvNumber1 = (TextView) findViewById(R.id.tvNumber1);
        tvCompanyHist1 = (TextView) findViewById(R.id.tvCompanyHist1);
        tvTitleHist1 = (TextView) findViewById(R.id.tvTitleHist1);
        tvPercentileHist1 = (TextView) findViewById(R.id.tvPercentileHist1);
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
        tvAccountBalance = (TextView) findViewById(R.id.tvAccountBalance);
        tvPending = (TextView) findViewById(R.id.tvPending);
        tvActiveAndComplete = (TextView) findViewById(R.id.tvActiveAndComplete);
        tvFBSuccess = (TextView) findViewById(R.id.tvFBSuccess);
        tvFBSuccessEmail = (TextView) findViewById(R.id.tvFBSuccessEmail);
        tvGoogleSuccess = (TextView) findViewById(R.id.tvGoogleSuccess);
        tvGoogleSuccessEmail = (TextView) findViewById(R.id.tvGoogleSuccessEmail);

        etWallet = (EditText) findViewById(R.id.etWallet);
        etCompany = (EditText) findViewById(R.id.etCompany);

        bFacebookLogin = (LoginButton) findViewById(R.id.bFacebookLogin);
        bFacebookLogin.setReadPermissions("public_profile, email");
        callbackManager = CallbackManager.Factory.create();

        Log.d("funds ", preferences.getString("funds", ""));
        Log.d("cash ", preferences.getString("cash", ""));

        balance = preferences.getString("cash", "");
        tvMoney.setText("$" + balance);
        tvAccountBalance.setText("$" + balance);

        REQ_TOKEN = preferences.getString("google_oauth2", "");

        Log.d("test id ", preferences.getString("raadz_pub_id", ""));
        Log.d("test token ", preferences.getString("token", ""));


        String raadz_user_id = preferences.getString("raadz_pub_id", "");
        String token = preferences.getString("token", "");
        DataFunction(raadz_user_id, token);
        complete = preferences.getString("completed", "");
//        tvComplete.setTextSize(getResources().getDimension(R.dimen.title_size));
        tvNumber1.setTextSize(getResources().getDimension(R.dimen.text_size));
        tvNumber2.setTextSize(getResources().getDimension(R.dimen.text_size));
        tvNumber3.setTextSize(getResources().getDimension(R.dimen.text_size));
        tvNumber4.setTextSize(getResources().getDimension(R.dimen.text_size));
        tvNumber5.setTextSize(getResources().getDimension(R.dimen.text_size));
        Log.d("pending ", preferences.getString("pending", ""));
        Log.d("total ", preferences.getString("total_spent", ""));
        tvPending.setText(preferences.getString("pending", ""));
        tvActiveAndComplete.setText(preferences.getString("total_spent", ""));
        Log.d("echo: ", complete);

        String result = preferences.getString("accountResult", "");
        Log.d("result ", result);

        Log.d("fdID ", preferences.getString("fdID", ""));
        Log.d("email ", preferences.getString("email", ""));
        Log.d("first ", preferences.getString("fName", ""));
        Log.d("last ", preferences.getString("lName", ""));
        Log.d("fbToken ", preferences.getString("fbToken", ""));

        View view = this.getCurrentFocus();
        if (view != null) {
            hideKeyboard(view);
        }

        Log.d("email_frequency ", preferences.getString("email_frequency", ""));

        if(preferences.getString("email_frequency", "").equals("0")){
            rbReceive.setChecked(true);
        }
        else if(preferences.getString("email_frequency", "").equals("1")){
            rbOptOut.setChecked(true);
        }

        GoogleSignInOptions signInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(REQ_TOKEN).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOption).build();

        bUpdateAddress.setOnClickListener(this);

        bUpdateCompany.setOnClickListener(this);

        bDeposit.setOnClickListener(this);

        bGoogleSignIn.setOnClickListener(this);

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
                if (!preferences.getString("fb_email", "").equals("null")) {
                    bFacebookLogin.setVisibility(View.GONE);
                    tvFBSuccess.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setVisibility(View.VISIBLE);
                    tvFBSuccessEmail.setText(preferences.getString("fb_email", ""));
                }
                if (!preferences.getString("google_email", "").equals("null")) {
                    bGoogleSignIn.setVisibility(View.GONE);
                    tvGoogleSuccess.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setVisibility(View.VISIBLE);
                    tvGoogleSuccessEmail.setText(preferences.getString("google_email", ""));
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

        bHistory.setOnClickListener(this);

        bFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

                System.out.println("onSuccess");
                progressDialog = new ProgressDialog(PubProfileActivity.this);
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
                        Log.d(bFacebookData.getString("idFacebook", ""), "");
                        Log.d("", "");
                        Log.d("fbID ", idFB);
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
                                preferences.getString("raadz_pub_id", ""),
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
    }


    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void logoutMethod() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubProfileActivity.this);
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
                Intent in = new Intent(PubProfileActivity.this, IndexActivity.class);
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

        Intent in = new Intent(this, FragPubActivity.class);
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
                Intent intent1 = new Intent(this, PubAdHistory.class);
                startActivity(intent1);
                return true;
            case R.id.action_profile:

            case R.id.action_deposit:

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
                        Intent in = new Intent(PubProfileActivity.this, IndexActivity.class);
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
        if (id == R.id.adv_home) {
            Intent in = new Intent(this, FragPubActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_submit) {
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
                    Intent in = new Intent(PubProfileActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubProfileActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubProfileActivity.this);
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
                        Toast.makeText(PubProfileActivity.this, raadz_user_id, Toast.LENGTH_SHORT).show();
                    }
                });
                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

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
                Log.d("PubInfoResult: ", result);
                if (result.contains("user not found")) {
                    Log.d("echo 2:  ", "user not found");
                    //Toast.makeText(UpdateInformation.this, "ratings submitted", Toast.LENGTH_SHORT).show();
                    //Intent in = new Intent(PublisherActivity.this, ConfirmEmailActivity.class);
                    //startActivity(in);
                } else {
                    Log.d("else JSON: ", result);
//                    runOnUiThread(new Runnable() {
//                        public void run() {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    try {
                        JSONArray jArray = new JSONArray(result);
                        for (int i = 0; i < jArray.length(); i++) {
                            final JSONObject jObj = jArray.getJSONObject(i);
                            tvEmail.setText(jObj.getString("email"));
                            etCompany.setText(jObj.getString("company_name"));
                            etWallet.setText(jObj.getString("wallet_address"));
                            tvPending.setText("$" + jObj.getString("pending"));
                            tvMoney.setText(jObj.getString("balance"));
                            Log.d("new_balance ", jObj.getString("balance"));
                            Log.d("mail_freq_f ", jObj.getString("mail_freq"));
                            tvActiveAndComplete.setText("$" + jObj.getString("total_spent"));
                            {
                                edit.putString("fb_email", jObj.getString("fb_email"));
                                edit.putString("google_email", jObj.getString("google_email"));
                                edit.commit();
                            }
                            if (jObj.getString("mail_freq").equals("0")){
                                rbReceive.setChecked(true);
                                Log.d("Email_Frequency ", jObj.getString("interest_text"));
                                edit.putString("email_frequency", jObj.getString("mail_freq"));
                                edit.commit();
                            }
                            if (jObj.getString("mail_freq").equals("1")){
                                rbOptOut.setChecked(true);
                                Log.d("Email_Frequency ", jObj.getString("interest_text"));
                                edit.putString("email_frequency", jObj.getString("mail_freq"));
                                edit.commit();
                            }
                            if (jObj.getString("interest_value").equals("user_name")) {
                                Log.d("stuff ", preferences.getString("postResult", ""));
                                userName = jObj.getString("interest_text");
                                Log.d("jObj Name ", jObj.getString("interest_text"));
                                String[] arr = userName.split(" ");
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

                            if (jObj.getString("interest_value").equals("email")) {
                                userEmail = jObj.getString("interest_text");
                                Log.d("jObj Email ", jObj.getString("interest_text"));
                                Log.d("", "");
                                Log.d("Email ", userEmail);
                                tvEmail.setText(userEmail);
                                tvTitleEmail.setText(userEmail);

                            }

                            if (jObj.getString("interest_value").equals("fb_account")) {
                                edit.putString("fb_account", jObj.getString("interest_text"));
                                edit.commit();
                            }

                            if (jObj.getString("interest_value").equals("wallet_address")) {
                                edit.putString("wallet_address", jObj.getString("interest_value"));
                                edit.commit();
                                etWallet.setText(jObj.getString("interest_text"));
                            }

                            if (jObj.getString("interest_value").equals("company_name")) {
                                edit.putString("company_name", jObj.getString("interest_text"));
                                edit.commit();
                                etCompany.setText(jObj.getString("interest_text"));
                            }

                            if (jObj.getString("interest_value").equals("pending")) {
                                Log.d("pending ", jObj.getString("pending"));
                                edit.putString("pending", jObj.getString("interest_text"));
                                edit.commit();
                            }

                            if (jObj.getString("interest_value").equals("total_spent")) {
                                Log.d("total_spent ", jObj.getString("total_spent"));
                                edit.putString("total_spent", jObj.getString("interest_text"));
                                edit.commit();
                            }

                            Log.d("mail_freq ", jObj.getString("mail_freq"));

//                            if (jObj.getString("interest_value").equals("mail_freq")) {
//                                if (jObj.getString("interest_text").equals("0")){
//                                    rbReceive.setChecked(true);
//                                }
//                                if (jObj.getString("interest_text").equals("1")){
//                                    rbOptOut.setChecked(true);
//                                }
//                                Log.d("Email_Frequency ", jObj.getString("interest_text"));
//                                edit.putString("email_frequency", jObj.getString("interest_text"));
//                                edit.commit();
//                            }

                        }
                    } catch (org.json.JSONException e) {
                        System.out.println(e);
                    }
//                        }
//                    });
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (v == bSaveEmail) {
            buttonPlaceholder = "bSaveEmail";
            HashMap postData = new HashMap();
            postData.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("email_freq", s_preference);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(EmailPreferenceURL);
        }

        if (v == bHistory) {

            Intent in = new Intent(this, PubAdHistory.class);
            startActivity(in);
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

        if (v == bGoogleSignIn) {
            buttonPlaceholder = "bGoogleLogin";
            HashMap postData = new HashMap();
            postData.put("google_user_id", preferences.getString("google_id", ""));
            postData.put("google_access_token", preferences.getString("google_token", ""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(RaadzIDURL);
        }

        if (v == bUpdateAddress) {

            Log.d("pub_id ", preferences.getString("raadz_pub_id", ""));
            Log.d("token ", preferences.getString("token", ""));
            Log.d("address ", etWallet.getText().toString());

            buttonPlaceholder = "bUpdateAddress";
            HashMap postData = new HashMap();
            postData.put("pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("raad_address", etWallet.getText().toString());
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(updateWalletAddressURL);
        }

        if (v == bUpdateCompany) {

            Log.d("pub_id ", preferences.getString("raadz_pub_id", ""));
            Log.d("token ", preferences.getString("token", ""));
            Log.d("company ", etCompany.getText().toString());

            buttonPlaceholder = "bUpdateCompany";
            HashMap postData = new HashMap();
            postData.put("pub_id", preferences.getString("raadz_pub_id", ""));
            postData.put("token", preferences.getString("token", ""));
            postData.put("comp_name", etCompany.getText().toString());
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(updateCompanyNameURL);
        }

        if (v == bDeposit) {
            buttonPlaceholder = "bDeposit";
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubProfileActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.stripe_dialog, null);
            final EditText mAmount = (EditText) mView.findViewById(R.id.etAmount);
            final EditText mName = (EditText) mView.findViewById(R.id.etName);
            final EditText mZip = (EditText) mView.findViewById(R.id.etZip);
            final TextView mTotal = (TextView) mView.findViewById(R.id.tvTotal);
            CardWidget = (CardMultilineWidget) mView.findViewById(R.id.CardWidget);

            Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            Button mEnter = (Button) mView.findViewById(R.id.bEnter);
            mBuilder.setView(mView);
            stripe_dialog = mBuilder.create();
            stripe_dialog.show();
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stripe_dialog.dismiss();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            });

            mEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAmount.getText().equals("")) {
                        Toast.makeText(PubProfileActivity.this, "Enter a valid Amount", Toast.LENGTH_SHORT).show();
                    }
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
//                                    com.stripe.Stripe.apiKey = StripeConfig.getSecretKey();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    String s_email = preferences.getString("email", "");
                                    String s_pid = preferences.getString("raadz_pub_id", "");
                                    //Log.d("token ", token.getId());
                                    Log.d("amount ", mAmount.getText().toString());
                                    Log.d("email ", s_email);
                                    Log.d("pubID ", s_pid);
                                    Log.d("description ", "Deposit to account");
                                    Log.d("stripe_token ", preferences.getString("stripe_public_key", ""));

                                    Log.d("paymentCheck ", String.valueOf(paymentCheck));
                                    check = true;
                                    if (check == true) {
                                        deposit = Double.parseDouble(mAmount.getText().toString());
                                        Log.d("b_deposit ", String.valueOf(deposit));
                                        deposit = deposit * 100;
                                        if (deposit < 1000) {
                                            Log.d("Deposit Amount if 1 ", String.valueOf(deposit));
                                            Toast.makeText(PubProfileActivity.this, "Minimum deposit amount: $10.00", Toast.LENGTH_SHORT).show();
                                            mAmount.setText("");
                                        } else if (tempName.length() < 1) {
                                            Toast.makeText(PubProfileActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                                        } else if (tempZip.length() < 1) {
                                            Toast.makeText(PubProfileActivity.this, "Please enter your Zip", Toast.LENGTH_SHORT).show();
                                        } else if (deposit >= 1000) {
                                            int x;
                                            x = (int)deposit;
                                            StripeDetails(
                                                    token.getId(),
                                                    String.valueOf(x),
                                                    s_email, s_pid,
                                                    "Direct to Account",
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
                                            PostResponseAsyncTask task = new PostResponseAsyncTask(PubProfileActivity.this, postData);
                                            task.execute(getPubInfoURL);
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
        }

//        if(v == bGoogle){
//            Toast.makeText(this, "Function is not ready", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void processFinish(String s) {
        if (buttonPlaceholder.equals("bSaveEmail")){
            Log.d("bSaveEmail ", s);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        if (buttonPlaceholder.equals("bFacebook")) {
            Log.d("bFacebook Link", s);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
        if (buttonPlaceholder.equals("bGoogleLogin")) {
            Toast.makeText(PubProfileActivity.this, s, Toast.LENGTH_SHORT).show();
            bGoogleSignIn.setVisibility(View.GONE);
        }
        if (buttonPlaceholder.equals("bUserPref")) {
            Log.d("bUserPref ", s);
        }
        if (buttonPlaceholder.equals("bUpdateAddress")) {
            if(!s.equals("invalid data")){
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            }
            Log.d("bUpdateAddress ", s);
        }
        if (buttonPlaceholder.equals("bUpdateCompany")) {
            if(!s.equals("invalid data")){
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            }
            Log.d("bUpdateCompany ", s);
        }
        if (buttonPlaceholder.equals("bDeposit")) {
            Log.d("bDeposit ", s);
        }
    }


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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                Log.d("StripeFinish ", result);
                if (result.contains("success")) {
                    Toast.makeText(PubProfileActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(PubProfileActivity.this, PubProfileActivity.class);
                    startActivity(in);
                }
                else if(result.contains("Publisher not found")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("This card has been declined")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Too many requests")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Invalid parameters supplied")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Authentication with Stripe failed")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Network communication failed")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Generic error")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Error unrelated to Stripe")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Deposit amount below minimum")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
                else if(result.contains("Error: invalid call")){
                    Toast.makeText(PubProfileActivity.this, result, Toast.LENGTH_SHORT).show();
                    stripe_dialog.dismiss();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(token, amount, email, pubID, description);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

            nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("raadz_token", params[1]));
            nameValuePairs.add(new BasicNameValuePair("user_id", params[2]));
            nameValuePairs.add(new BasicNameValuePair("email", params[3]));
            nameValuePairs.add(new BasicNameValuePair("first_name", params[4]));
            nameValuePairs.add(new BasicNameValuePair("last_name", params[5]));
            nameValuePairs.add(new BasicNameValuePair("fb_access_token", params[6]));

            try {
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
            if (result.contains("facebook account exists")) {
                Toast.makeText(PubProfileActivity.this, "Facebook account already exists", Toast.LENGTH_SHORT).show();
            }
            if (result.contains("success")) {
                Toast.makeText(PubProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                bFBSuccess.setVisibility(View.VISIBLE);
//                bFBSuccess.setFocusable(false);
            }
        }
    }
}
