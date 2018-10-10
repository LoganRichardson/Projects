package com.raadz.program.raadzandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.dial;
import static android.R.attr.id;
import static android.R.id.edit;
import static com.raadz.program.raadzandroid.R.id.bGoogleLogin;
import static com.raadz.program.raadzandroid.R.id.bResend;
import static com.raadz.program.raadzandroid.R.id.bSend;
import static com.raadz.program.raadzandroid.R.id.button;
import static com.raadz.program.raadzandroid.R.id.etVideoTitle;
import static com.raadz.program.raadzandroid.R.id.tvAltText;
import static junit.runner.Version.id;

public class LoginActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.common.api.Result, GoogleApiClient.ConnectionCallbacks {

    GoogleApiClient googleApiClient;

    GoogleApiClient mGoogleApiClient;

    View view;

    Button bLogin;
    Button bBack;
    SignInButton bGoogleSignIn;

    AlertDialog dialog_f;

    ProgressDialog progressDialog;
    ProgressDialog progressDialogG;
    ProgressDialog progressDialogEmail;

    EditText etEmail;
    EditText etPassword;

    TextView tvLogin;
    TextView textView;
    TextView tvLink;
    TextView tvSignUP;
    TextView tvForgotPassword;
    TextView tvPlaceholder;

    LoginButton bFacebookLogin;
    CallbackManager callbackManager;
    BufferedWriter bufferedWriter;
    LoginParseClass loginParseClass = new LoginParseClass();
    BufferedReader bufferedReader;
    OutputStream outputStream;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();

    int count = 0;

    String ServerLoginURL = "https://raadz.com/mobilelogin.php";
    String RaddzIDURL = "https://raadz.com/getRaadzIDs.php";
    String forgotPasswordURL = "https://raadz.com/forgotPassword.php";
    String reload = "111";
    String buttonPlaceholder = "no";
    String captchaToken;
    String raadzUID;
    String raadzPID;
    String raadzToken;
    String UID;
    String PID;
    String UserToken;
    String PubToken;
    String fName;
    String lName;
    String email;
    String username;
    String id;
    String token;
    String fbUID;
    String fbToken;
    String fbEmail;
    String gEmail;
    String gPassword;
    String gRaadzUID;
    String gRaadzToken;
    String EmailHolder;
    String PasswordHolder;
    String fbContent;
    String fb_id_pass;
    String fb_pub_pass;
    String fb_token_pass;

    StringBuilder stringBuilder = new StringBuilder();
    String Result;
    String finalResult;
    String FinalHttpData = "";
    String httpRequest;

    int google_b = 0;
    int forgot_val = 0;

    boolean success = false;
    boolean CheckEditText;
    boolean captchaPass;

    String TAG = "TAG";
    public static final String UserEmail = "";
    public static final String data = "{\"user_id\":{\"put_id\":\"token\",\"salary\":56000}}";
    String SITE_KEY = "";
    String REQ_TOKEN = "";
    public static final String SECRET_KEY = "6LcVrUwUAAAAADmA4tnNYUmPu8rcOcOBF1IxTt0d";
    private static final int REQ_CODE = 9001;

    HashMap<String, String> hashMap = new HashMap<>();
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("https://raadz.com/mobilelogin.php");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("facebookLogin", "false");
        editor.commit();
        SITE_KEY = preferences.getString("recaptcha_public_key", "");
        REQ_TOKEN = preferences.getString("google_oauth2", "");
        Log.d("REQ_TOKEN ", REQ_TOKEN);
        tvLink = (TextView) findViewById(R.id.tvLink);
        textView = (TextView) findViewById(R.id.textView);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvSignUP = (TextView) findViewById(R.id.tvSignUP);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvPlaceholder = (TextView) findViewById(R.id.tvPlaceholder);

        bLogin = (Button) findViewById(bSend);
        bBack = (Button) findViewById(R.id.bBack);
        bGoogleSignIn = (SignInButton) findViewById(R.id.bGoogleSignIn);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bFacebookLogin = (LoginButton) findViewById(R.id.bFacebookLogin);
        bFacebookLogin.setReadPermissions("public_profile, email");

        final String fb_uid = preferences.getString("raadz_fb_user_id", "");
        final String fb_pid = preferences.getString("raadz_fb_pub_id", "");

        TextView textView = (TextView) bGoogleSignIn.getChildAt(0);
        textView.setText("Login");

        Log.d("fb_uid ", fb_uid);
        Log.d("fb_pid ", fb_pid);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();

        Log.d("lets see ", preferences.getString("rEmail", ""));

        if (preferences.getString("emailPass", "").length() > 0) {
            etEmail.setText(preferences.getString("emailPass", ""));
        } else if (preferences.getString("rEmail", "").length() > 0) {
            etEmail.setText(preferences.getString("rEmail", ""));
        }

        etEmail.clearFocus();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(LoginActivity.this)
                .build();

        mGoogleApiClient.connect();

        GoogleSignInOptions signInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(REQ_TOKEN).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOption).build();

        if (googleApiClient.isConnected()) {
            signOut();
        }

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, IndexActivity.class);
                startActivity(in);
            }
        });

        tvForgotPassword.setOnClickListener(this);

        bGoogleSignIn.setOnClickListener(this);


        bFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

                System.out.println("onSuccess");
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Processing...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                String accessToken = loginResult.getAccessToken().getToken();
                Log.d("accessToken", accessToken);

                final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final SharedPreferences.Editor edit = preferences.edit();
                        Log.d("LoginActivity", response.toString());
                        Bundle bFacebookData = getFacebookData(object);
                        String idFB = bFacebookData.getString("idFacebook", "");
                        final String email = bFacebookData.getString("email", "");
                        String fName = bFacebookData.getString("first_name", "");
                        String lName = bFacebookData.getString("last_name", "");
                        String token = loginResult.getAccessToken().getToken();

                        edit.putString("fdID", idFB);
                        edit.putString("email", email);
                        edit.putString("fName", fName);
                        edit.putString("lName", lName);
                        edit.putString("fbToken", token);
                        edit.commit();

                        edit.remove("fb_content").commit();
                        FacebookLoginFunction(idFB, token);

                        //Log.d("2 ", arr[1]);
                        //Log.d("3 ", arr[2]);
//                        if(counter < 3){
//                            FacebookLoginFunction(idFB, token);
//                            credential = "";
//                            credential = preferences.getString("fb_content", "");
//                            Log.d("cred ", credential);
//                            Log.d("FB stuff ", preferences.getString("fb_content", ""));
//                            arr = credential.split(":");
//                            arrLength = arr.length;
//                            Log.d("length ", String.valueOf(arrLength));
//                        }


                        //int arrLength = arr.length;

//                        Log.d("-", "");
//                        //Log.d("length of token", String.valueOf(arr[2].length()));
//                        Log.d("-", "");
//                        //Log.d("result length ", String.valueOf(arrLength));
//                        Log.d("value ", arr[0]);
//                        //Log.d("value ", arr[1]);
//                        //Log.d("value ", arr[2]);
//
//                        String IDtemp = arr[0];
//                        //String PIDtemp = arr[1];
//                        //String Tokentemp = arr[2];
//                        //.replaceAll("\\s+", "");
//
//                        Log.d("idtemp ", IDtemp);
//                        //Log.d("tokentemp ", Tokentemp);
//
//                        edit.putString("fdID", idFB);
//                        edit.commit();
//
//                        edit.remove("raadz_user_id").apply();
//                        edit.putString("raadz_fb_user_id", IDtemp);
//                        edit.apply();
//
//                        edit.remove("token").apply();
//                        //edit.putString("token", Tokentemp);
//                        edit.apply();
//
//
//                        Log.d("ID ", preferences.getString("raadz_user_id", ""));
//                        Log.d("token ", preferences.getString("token", ""));
//
//
//                        Log.d(bFacebookData.getString("idFacebook", ""), "");
//                        Log.d("", "");
//                        Log.d("fbID ", idFB);
//                        Log.d("token ", token);
//                        Log.d("", "");
//
//                        credential = preferences.getString("fb_content", "");
//                        String[] arr2 = credential.split(":");
//                        Log.d("ArraySize ", String.valueOf(arr2));


//                        Intent in = new Intent(LoginActivity.this, FragActivity.class);
//                        startActivity(in);

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {

                            }
                        }, 4000);

                        fb_id_pass = preferences.getString("fb_content_id", "");
                        fb_token_pass = preferences.getString("fb_content_token", "");

                        Log.d("id ", fb_id_pass);
                        Log.d("token ", fb_token_pass);

                        edit.putString("raadz_fb_user_id", fb_id_pass);
                        edit.putString("token", fb_token_pass);
                        edit.commit();

                        new CountDownTimer(3000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                long value = millisUntilFinished / 1000;
                                Log.d("Seconds remaining: ", String.valueOf(value));
                            }

                            public void onFinish() {
                                buttonPlaceholder = "temp";
                                Log.d("adv or user ", preferences.getString("adv_or_user", ""));
                                Log.d("Seconds remaining: ", "Done");
                                Toast.makeText(LoginActivity.this, preferences.getString("adv_or_user", ""), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                if (preferences.getString("adv_or_user", "").equals("adv")) {

                                    Log.d("captcha Token 2 ", preferences.getString("captchaToken", ""));

                                    HashMap postData = new HashMap();
                                    postData.put("raadz_pub_id", preferences.getString("fb_content_pub", ""));
                                    postData.put("token", preferences.getString("token", ""));
                                    postData.put("captcha_token", preferences.getString("captchaToken", ""));
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData);
                                    task.execute(ServerLoginURL);

                                    if (preferences.getString("email_confirmation", "").equals("not confirmed - resend") || preferences.getString("email_confirmation", "").equals("not confirmed")) {
                                        Intent in = new Intent(LoginActivity.this, FragNotConfirmedActivity.class);
                                        startActivity(in);
                                    } else {
                                        Log.d("First res ", "adv");
                                        edit.putString("raadz_fb_user_id", preferences.getString("fb_content_pub", ""));
                                        edit.commit();
                                        Intent in = new Intent(LoginActivity.this, FragPubActivity.class);
                                        startActivity(in);
                                    }
                                }
                                if (preferences.getString("adv_or_user", "").equals("user")) {

                                    Log.d("captcha Token 2 ", preferences.getString("captchaToken", ""));

                                    HashMap postData = new HashMap();
                                    postData.put("raadz_user_id", preferences.getString("fb_content_pub", ""));
                                    postData.put("token", preferences.getString("token", ""));
                                    postData.put("captcha_token", preferences.getString("captchaToken", ""));
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData);
                                    task.execute(ServerLoginURL);

                                    if (preferences.getString("email_confirmation", "").equals("not confirmed - resend") || preferences.getString("email_confirmation", "").equals("not confirmed")) {
                                        Intent in = new Intent(LoginActivity.this, FragNotConfirmedActivity.class);
                                        startActivity(in);
                                    } else {

                                        Log.d("First res ", "user");
                                        edit.putString("raadz_fb_user_id", preferences.getString("raadz_fb_user_id", ""));
                                        edit.commit();
                                        Intent in = new Intent(LoginActivity.this, FragActivity.class);
                                        startActivity(in);
                                    }
                                }

                            }

                        }.start();


                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

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


        tvSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(in);
            }
        });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCheckEditTextIsEmptyOrNot();
                recaptchaMethod(view);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void GetCheckEditTextIsEmptyOrNot() {
        EmailHolder = etEmail.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("fpEmail", EmailHolder);
        edit.commit();
        PasswordHolder = etPassword.getText().toString();
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
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

    public void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d("Logout ", "now signed out");
                    }
                });
    }

    public void signIn() {
        Intent in = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(in, REQ_CODE);

    }


    public void handleResult(final GoogleSignInResult handleResult) {
        if (handleResult.isSuccess()) {
            progressDialogG = new ProgressDialog(LoginActivity.this);
            progressDialogG.setMessage("Logging you in...");
            progressDialogG.setCancelable(false);
            progressDialogG.show();
            progressDialogG.setCanceledOnTouchOutside(false);
            Log.d("InHandleResult ", "1");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            final GoogleSignInAccount account = handleResult.getSignInAccount();

            fName = account.getGivenName();
            lName = account.getFamilyName();
            email = account.getEmail();
            username = account.getDisplayName();
            id = account.getId();
            token = handleResult.getSignInAccount().getIdToken();

            Log.d("Googlefname ", fName);
            Log.d("Googlelname ", lName);
            Log.d("Googleemail ", email);
            Log.d("Googlefusername ", username);
            Log.d("Googleid ", id);
            Log.d("Googletoken ", token);

            GoogleFunction(id, token);

            Log.d("Login" + " - ", "Success");
        } else

        {
            Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void recaptchaMethod(View view) {
        SafetyNet.SafetyNetApi.verifyWithRecaptcha(mGoogleApiClient, SITE_KEY)
                .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                    @Override
                    public void onResult(SafetyNetApi.RecaptchaTokenResult result) {
                        Status status = result.getStatus();

                        Log.d("status ", String.valueOf(status));

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();

                        if ((status != null) && status.isSuccess()) {

                            if (!result.getTokenResult().isEmpty()) {
                                captchaPass = true;
                                Log.d(TAG, String.valueOf(captchaPass));
                                if (CheckEditText && captchaPass == true) {
                                    captchaToken = result.getTokenResult();
                                    Log.d("captcha Token 1 ", captchaToken);
                                    edit.putString("captchaToken", captchaToken);
                                    edit.commit();
                                    LoginFunction(EmailHolder, PasswordHolder);
                                    LoginUIDFunction(gRaadzUID, gRaadzToken);
                                    String uid = preferences.getString("raadz_user_id", "");
                                    Log.d("LoginActivity?: ", uid);
                                    if (success == true) {
                                        LoginUIDFunction(gRaadzUID, gRaadzToken);
                                        //Intent in = new Intent(LoginActivity.this, FragActivity.class);
                                        //startActivity(in);
                                    }
                                    //LoginUIDFunction(UIDHolder, TokenHolder);
                                    //LoginPublisherFunction(PIDHolder, TokenHolder);
                                }
                            }
                        } else {
                            captchaPass = false;
                            Log.d(TAG, "result is empty");
                            Log.e(TAG, "error happened!");
//                            mPresenter.handleCaptchaError(status);
                        }
                    }
                });
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private Bundle getFacebookData(JSONObject object) {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging you in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
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
        progressDialog.dismiss();
        return null;
    }


    //Login function for checking just username and password
    public void LoginFunction(final String email, final String password) {
        class LoginFunctionClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);
                progressDialog.setCanceledOnTouchOutside(false);
            }


            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Log.i("WHATEVER", httpResponseMsg);
                if (httpResponseMsg.contains("invalid data supplied")) {
                    httpResponseMsg = "";
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Unhandled Error!");
                    alertDialog.setMessage("An Unexpected Error has occured, please reenter login and password to continue.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivityForResult(intent, 0);
                                }
                            });
                    alertDialog.show();

                }
                if (httpResponseMsg.contains("not confirmed")) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("NCEMAIL", gEmail);
                    edit.putString("NCPASSWORD", gPassword);
                    edit.commit();
                    Intent in = new Intent(LoginActivity.this, FragNotConfirmedActivity.class);
                    startActivity(in);
/*                    httpResponseMsg = "";
                    Log.d("Account Info: ", httpResponseMsg);

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);

                    Button mBack = (Button) mView.findViewById(R.id.bBack);
                    Button mToEmail = (Button) mView.findViewById(R.id.bToEmail);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    mBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            progressDialog.dismiss();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        }
                    });
                    mToEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressDialogEmail = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin"));
                            startActivity(browserIntent);
                            progressDialogEmail.dismiss();
                        }
                    });*/
                }
                if (httpResponseMsg.contains("email or password invalid")) {
                    httpResponseMsg = "";
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Invalid email or password");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor edit = preferences.edit();
                                    edit.putString("emailPass", gEmail);
                                    edit.commit();
                                    dialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivityForResult(intent, 0);
                                }
                            });
                    alertDialog.show();

                }
                if (httpResponseMsg.contains("success:")) {
                    success = true;
                    try {
                        //Gets pairs of everything inbetween quotes and stores them in an array
                        String[] userInfo = new String[6];
                        Pattern p = Pattern.compile("\"([^\"]*)\"");
                        Matcher m = p.matcher(httpResponseMsg);
                        count = 0;
                        while (m.find()) {
                            userInfo[count] = m.group(1);
                            count++;
                        }

                        raadzUID = userInfo[1];
                        raadzPID = userInfo[3];
                        raadzToken = userInfo[5];

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", gEmail);
                        editor.putString("password", gPassword);
                        editor.putString("raadz_user_id", gRaadzUID);
                        editor.putString("raadz_pub_id", raadzPID);
                        editor.putString("token", gRaadzToken);
                        editor.commit();
                        httpResponseMsg = "";
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                Log.d("captchaToken 3 ", captchaToken);
                hashMap.put("email", params[0]);
                hashMap.put("password", params[1]);
                hashMap.put("captcha_token", captchaToken);
                finalResult = loginParseClass.postRequest(hashMap);
                gEmail = email;
                gPassword = password;
                return finalResult;
            }
        }
        LoginFunctionClass loginFunctionClass = new LoginFunctionClass();
        loginFunctionClass.execute(email, password);
        //LoginUIDFunction(gRaadzUID, gRaadzToken);
    }


    //Login function for checking the UID and the Token
    public void LoginUIDFunction(final String raadz_user_id, final String token) {
        class LoginUIDClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                Log.i("WHATEVER2", httpResponseMsg);


                String tokenID = httpResponseMsg;
                //tvLogin.setText(httpResponseMsg);
                if (httpResponseMsg.contains("invalid data supplied")) {
                    httpResponseMsg = "";
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Exception Caught.");
                    alertDialog.setMessage("Did not recieve values in POST. Resubmit data.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }


                if (httpResponseMsg.contains("userID or token invalid")) {
                    httpResponseMsg = "";
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("user ID and token values wipped...try again");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                if (httpResponseMsg.contains("success:")) {
                    Log.d("WHATEVER3", httpResponseMsg);
                    success = true;
                    try {
                        Log.d("WHATEVER4", httpResponseMsg);
                        //Gets pairs of everything inbetween quotes and stores them in an array
                        String[] userInfo = new String[6];
                        Pattern p = Pattern.compile("\"([^\"]*)\"");
                        Matcher m = p.matcher(httpResponseMsg);
                        count = 0;
                        while (m.find()) {
                            userInfo[count] = m.group(1);
                            count++;
                        }

                        Log.d("WHATEVER5", httpResponseMsg);
                        String UItemp = userInfo[1];
                        String PIDtemp = userInfo[3];
                        String TIDtemp = userInfo[5];
                        Log.d("UID", UItemp);
                        Log.d("UID", userInfo[1]);
                        Log.d("---", "");
                        Log.d("PID", PIDtemp);
                        Log.d("PID", userInfo[3]);
                        Log.d("---", "");
                        Log.d("token", TIDtemp);
                        Log.d("token", userInfo[5]);

                        if (UItemp.equals(raadzUID) && TIDtemp.equals(raadzToken) && PIDtemp.equals("x") && !UItemp.equals("x")) {
                            gRaadzUID = UItemp;
                            gRaadzToken = TIDtemp;
                            Log.d("WHATEVER6", httpResponseMsg);
                            Log.d("Successful Login", UItemp);
                            Log.d("1 ", UItemp);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("raadz_user_id", UItemp);
                            editor.putString("token", gRaadzToken);
                            editor.commit();

                            // make into the another null pointer excpetion, code a try cathc if possible

                            Log.d("result user ", preferences.getString("result", ""));
                            Log.d("httpRequest: ", preferences.getString("httpRequest", ""));
                            editor.putString("LOAD", reload);
                            editor.putString("raadz_user_id", gRaadzUID);
                            editor.putString("token", gRaadzToken);

                            editor.commit();

                            Log.d("login result ", gRaadzUID);
                            DataFunction(gRaadzUID, gRaadzToken);

                            if (preferences.getString("login_deny", "").contains("account locked")) {
                                Toast.makeText(LoginActivity.this, "Your account has been locked", Toast.LENGTH_SHORT).show();
                            } else if (preferences.getString("login_deny", "").contains("retry in:")) {
                                Toast.makeText(LoginActivity.this, preferences.getString("login_deny", ""), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent in = new Intent(LoginActivity.this, FragActivity.class);
                                startActivity(in);
                            }
                        }


                        if (PIDtemp.equals(raadzPID) && TIDtemp.equals(raadzToken) && UItemp.equals("x") && !PIDtemp.equals("x")) {
                            Log.d("WHATEVER7", httpResponseMsg);
                            Log.d("Successful Login", UItemp);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("raadz_pub_id", PIDtemp);
                            editor.putString("token", raadzToken);
                            editor.commit();
                            Log.d("result pub ", preferences.getString("result", ""));
                            Log.d("httpRequest: ", preferences.getString("httpRequest", ""));
                            editor.putString("LOAD", reload);
                            editor.commit();

                            Intent in = new Intent(LoginActivity.this, FragPubActivity.class);
                            startActivity(in);
                        }


                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("raadz_user_id", params[0]);
                hashMap.put("token", params[1]);
                finalResult = loginParseClass.postRequest(hashMap);
                gRaadzUID = raadz_user_id;
                gRaadzToken = token;
                return finalResult;
            }
        }
        LoginUIDClass loginUIDClass = new LoginUIDClass();
        loginUIDClass.execute(raadz_user_id, token);
    }


    @Override
    public void processFinish(final String result) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        SharedPreferences.Editor editor = preferences.edit();
        if (buttonPlaceholder.equals("temp")) {
            if (result.equals("not confirmed - resend") || result.equals("not confirmed")) {
                Log.d("Confirmation ", result);
                edit.putString("email_confirmation", result);
                edit.commit();
                Intent in = new Intent(LoginActivity.this, FragNotConfirmedActivity.class);
                startActivity(in);
            }
        }
        if (buttonPlaceholder.equals("fbLogin")) {
            fbContent = result;
            if (result.contains("invalid access token")) {
                Log.d("result ", result);
                fbContent = result;
            } else {
                fbContent = result;
                edit.putString("fb_credentials", fbContent);
                edit.commit();
                tvPlaceholder.setText(fbContent);

                tvPlaceholder.setText(fbContent);
                Log.d("result ", result);
            }
        }
        if (buttonPlaceholder.equals("tvForgotPassword")) {
            Log.d("result ", result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
        if (buttonPlaceholder.equals("google0")) {
            Log.d("google_b ", result);
            google_b = 1;
            onClick(bGoogleSignIn);
        }
        if (buttonPlaceholder.equals("handle_method")) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("handleMethodResult ", result);
                }
            });
        }

        if (buttonPlaceholder.equals("view")) {
            Log.d("view ", result);
        }

        if (buttonPlaceholder.equals("bForgotPassword")) {
            if (forgot_val == 3 && result.contains("email successfully sent")) {
                Log.d("Resend_password ", result);
                Toast.makeText(this, "Email Successfully Sent!", Toast.LENGTH_SHORT).show();
                etEmail.setText(preferences.getString("f_email", ""));
                dialog_f.dismiss();
            } else if (result.contains("resend reset email allowed")) {
                forgot_val = 2;
                Log.d("first_fp ", result);
                dialog_f.dismiss();
                onClick(tvForgotPassword);
            } else if (result.contains("check email to reset")) {
                forgot_val = 0;
                Log.d("second_fp ", result);
                Toast.makeText(this, "Check email to reset", Toast.LENGTH_SHORT).show();
                dialog_f.dismiss();
            } else if (result.contains("email successfully sent")) {
                forgot_val = 0;
                Toast.makeText(this, "Email Successfully Sent!", Toast.LENGTH_SHORT).show();
                etEmail.setText(preferences.getString("f_email", ""));
                dialog_f.dismiss();
            }
        }

        Log.d("result fb ", result);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View v) {
        if (v == bGoogleSignIn) {
            if (googleApiClient.isConnected()) {
                signOut();
            }
            signIn();
            buttonPlaceholder = "handle_method";
        }
        if (v == tvForgotPassword) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.forgot_password_dialog, null);
            final LinearLayout LLResend = (LinearLayout) mView.findViewById(R.id.LLResend);
            final Button mSend = (Button) mView.findViewById(R.id.bEnter);
            final TextView mResend = (TextView) mView.findViewById(R.id.tvResend);
            final Button mCancel = (Button) mView.findViewById(R.id.bCancel);
            final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);

            mEmail.setText(etEmail.getText().toString());

            Log.d("forgot_val ", String.valueOf(forgot_val));

            if (forgot_val == 0) {
                mBuilder.setView(mView);
                dialog_f = mBuilder.create();
                dialog_f.show();
                mSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("forgot_val_0 ", "1");
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("f_email", mEmail.getText().toString());
                        edit.commit();
                        buttonPlaceholder = "bForgotPassword";
                        HashMap postData = new HashMap();
                        postData.put("email", mEmail.getText().toString());
                        postData.put("resend", "0");
                        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData);
                        task.execute(forgotPasswordURL);
                    }
                });
            } else if (forgot_val == 2) {
                forgot_val = 3;
                mBuilder.setView(mView);
                dialog_f = mBuilder.create();
                dialog_f.show();
                mEmail.setText(preferences.getString("f_email", ""));
                LLResend.setVisibility(View.VISIBLE);
                mResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonPlaceholder = "bForgotPassword";
                        HashMap postData = new HashMap();
                        postData.put("email", mEmail.getText().toString());
                        postData.put("resend", "1");
                        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData);
                        task.execute(forgotPasswordURL);
                    }
                });
                Log.d("forgot_val_2 ", "3");
            } else if (forgot_val == 3) {
                mBuilder.setView(mView);
                dialog_f = mBuilder.create();
                dialog_f.show();
            }

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_f.dismiss();
                }
            });
        }
    }


    public void DataFunction(final String raadz_user_id, final String token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("https://raadz.com/getUserData.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost("https://raadz.com/getUserData.php");
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
                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("user_data", httpRequest);
                        edit.commit();
                    }
                });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("user_data").apply();
                edit.putString("user_data", httpRequest);
                edit.commit();
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("UserData: ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("user_data").apply();
                edit.putString("user_data", result);
                edit.commit();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token);
    }


    public void GoogleFunction(final String google_id, final String google_token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("google_user_id", google_id));
                nameValuePairs.add(new BasicNameValuePair("google_access_token", google_token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RaddzIDURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RaddzIDURL);
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

                Log.d("httpRequest Google ", httpRequest);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("user_data").apply();
                edit.putString("user_data", httpRequest);
                edit.commit();
                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                Log.d("LoginResult ", result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (result.length() < 10) {
                    Toast.makeText(LoginActivity.this, "User doesnt exist.  Please create an account.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!result.equals("invalid access token") && !result.equals("invalid data")) {
                        Log.d("GoogleResult ", result);
                        Log.d("GoogleID ", id);
                        Log.d("GoogleToken ", token);
                        String[] parts = result.split(":");
                        for (int i = 0; i < parts.length; i++) {
                            Log.d("-", "-");
                            Log.d("parts ", parts[i]);
                            Log.d("count ", String.valueOf(i));
                            Log.d("-", "-");
                            if (i == 0 && parts[0].length() > 1) {
                                UID = parts[0];
                            } else if (i == 0 && parts[0].length() < 8) {
                                UID = "x";
                            }

                            if (i == 1 && parts[1].length() > 1) {
                                PID = parts[1];
                            } else if (i == 1 && parts[1].length() < 8) {
                                PID = "x";
                            }

                            if (i == 2 && parts[2].length() > 1) {
                                UserToken = parts[2];
                            } else if (i == 2 && parts[2].length() < 8) {
                                UserToken = "x";
                            }

                            if (i == 3 && parts[3].length() > 8) {
                                PubToken = parts[3];
                            } else if (i == 3 && parts[3].length() < 8) {
                                PubToken = "x";
                            }
                        }

                        Log.d("-", "-");
                        Log.d("UID", UID);
                        Log.d("PID", PID);
                        Log.d("UserToken", UserToken);
                        Log.d("PubToken", PubToken);
                        Log.d("FullResult ", result);
                        Log.d("-", "-");

                        //DataFunction(UID, UserToken);

                        if (!UID.equals("x") && !UserToken.equals("x") && PID.equals("x")) {
                            Log.d("WHATEVER6", result);
                            Log.d("Successful Login", UID);
                            edit.putString("raadz_user_id", UID);
                            edit.putString("token", UserToken);
                            edit.commit();
                            Log.d("result user ", preferences.getString("result", ""));
                            Log.d("httpRequest: ", preferences.getString("httpRequest", ""));
                            edit.putString("LOAD", reload);
                            edit.putString("pub_or_user", "user");
                            edit.commit();

                            GoogleMobileFunction(UID, UserToken);

                            if (preferences.getString("login_deny", "").contains("account locked")) {
                                Toast.makeText(LoginActivity.this, "Your account has been locked", Toast.LENGTH_SHORT).show();
                            } else if (preferences.getString("login_deny", "").contains("retry in:")) {
                                Toast.makeText(LoginActivity.this, preferences.getString("login_deny", ""), Toast.LENGTH_SHORT).show();
                            } else {
//                                Intent in = new Intent(LoginActivity.this, FragActivity.class);
//                                startActivity(in);
                            }
                        }


                        if (!PID.equals("x") && UID.equals("x")) {
                            Log.d("WHATEVER7", result);
                            Log.d("Successful Login", PID);
                            edit.putString("raadz_pub_id", PID);
                            edit.putString("token", UserToken);
                            edit.commit();
                            Log.d("result pub ", preferences.getString("result", ""));
                            Log.d("httpRequest: ", preferences.getString("httpRequest", ""));
                            edit.putString("LOAD", reload);
                            edit.putString("pub_or_user", "pub");
                            edit.commit();

                            GoogleMobileFunction(PID, UserToken);

                            if (preferences.getString("login_deny", "").contains("account locked")) {
                                Toast.makeText(LoginActivity.this, "Your account has been locked", Toast.LENGTH_SHORT).show();
                            } else if (preferences.getString("login_deny", "").contains("retry in:")) {
                                Toast.makeText(LoginActivity.this, preferences.getString("login_deny", ""), Toast.LENGTH_SHORT).show();
                            } else {
//                                Intent in = new Intent(LoginActivity.this, FragActivity.class);
//                                startActivity(in);
                            }

//                            Intent in = new Intent(LoginActivity.this, FragPubActivity.class);
//                            startActivity(in);
                        }


                    }//After this one

                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(google_id, google_token);
    }


    public void GoogleMobileFunction(final String raadz_id, final String token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Log.d("ID ", raadz_id);
                Log.d("Token ", token);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                if (preferences.getString("pub_or_user", "").equals("pub")) {
                    nameValuePairs.add(new BasicNameValuePair("raadz_pub_id", raadz_id));
                    nameValuePairs.add(new BasicNameValuePair("token", token));
                }
                if (preferences.getString("pub_or_user", "").equals("user")) {
                    nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_id));
                    nameValuePairs.add(new BasicNameValuePair("token", token));
                }
                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(ServerLoginURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(ServerLoginURL);
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

                Log.d("httpRequest Google ", httpRequest);

                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Log.d("Double up ", result);
                if (result.equals("not confirmed - resend") || result.contains("not confirmed")) {
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(LoginActivity.this, FragNotConfirmedActivity.class);
                    startActivity(in);
                } else if (result.equals("userID or token invalid")) {
                    Toast.makeText(LoginActivity.this, "An unexpected error has occurred", Toast.LENGTH_SHORT).show();
                } else {
                    if (preferences.getString("pub_or_user", "").equals("pub")) {
                        progressDialogG.dismiss();
                        Log.d("GoogleIsPub ", result);
                        Intent in = new Intent(LoginActivity.this, FragPubActivity.class);
                        startActivity(in);
                    }
                    if (preferences.getString("pub_or_user", "").equals("user")) {
                        progressDialogG.dismiss();
                        Log.d("GoogleIsUser ", result);
                        Intent in = new Intent(LoginActivity.this, FragActivity.class);
                        startActivity(in);
                    }
                }
                Log.d("LoginResult ", result);

            }//After this one
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_id, token);
    }


    public void FacebookLoginFunction(final String raadz_user_id, final String token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            TextView tvMoney;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                tvMoney = (TextView) findViewById(R.id.tvMoney);

            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("fb_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("fb_access_token", token));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RaddzIDURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RaddzIDURL);
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

                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("fb_content").commit();
                        editor.putString("fb_content", httpRequest);
                        editor.commit();

                        String[] arr2 = httpRequest.split(":");
                        Log.d("1 ", arr2[0]);
                        Log.d("2 ", arr2[1]);
                        Log.d("3 ", arr2[2]);

                        if (arr2[0].length() > 1) {
                            editor.putString("adv_or_user", "user");
                            editor.commit();
                        } else {
                            editor.putString("adv_or_user", "adv");
                            editor.commit();
                        }
                        Log.d("This is result ", httpRequest);
                        editor.putString("raadz_fb_user_id", arr2[0]);
                        editor.putString("fb_content_pub", arr2[1]);
                        editor.putString("token", arr2[2]);
                        editor.putString("token", arr2[2].replaceAll("\\s+", ""));
                        editor.commit();
                    }
                });

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                super.onPostExecute(result);
                if (result.equals("invalid access token")) {
                    Log.d("result ", result);
                } else if (result.equals("account locked")) {
                    edit.putString("login_deny", result);
                    edit.commit();
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                } else if (result.equals("retry in:")) {
                    edit.putString("login_deny", result);
                    edit.commit();
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("result ", result);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, String.valueOf(connectionResult), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Toast.makeText(this, String.valueOf(hasCapture), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public class LoginParseClass {

        public String postRequest(HashMap<String, String> Data) {

            try {
                url = new URL(ServerLoginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(12000);
                httpURLConnection.setConnectTimeout(12000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(FinalDataParse(Data));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    httpURLConnection.getInputStream()
                            )
                    );
                    FinalHttpData = bufferedReader.readLine();
                } else {
                    FinalHttpData = "Something Went Wrong";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FinalHttpData;
        }

        public String FinalDataParse(HashMap<String, String> hashMap2) throws UnsupportedEncodingException {
            for (Map.Entry<String, String> map_entry : hashMap2.entrySet()) {
                stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));
            }
            Result = stringBuilder.toString();
            return Result;
        }
    }

}