package com.raadz.program.raadzandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import static com.raadz.program.raadzandroid.R.id.bGoogleSignIn;

public class PublisherActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    SignInButton bGoogleSignUp;

    ProgressDialog progressDialog;

    Button register;
    Button login;
    Button fab;
    Button bBack;

    TextView tvAlready;
    TextView tvUser;

    EditText etEmail;
    EditText etPassword;
    EditText etPasswordRepeat;
    EditText etFirst;
    EditText etLast;

    HttpResponse response;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    LoginButton bFacebookLogin;
    CallbackManager callbackManager;

    Boolean CheckEditText;
    boolean accExists = false;

    String fbRegisterURL = "https://raadz.com/fbpubsignup.php";
    String RegisterURL = "https://raadz.com/pubsignup.php";
    String GooglePubSignUpURL = "https://raadz.com/googpubsignup.php";
    String buttonPlaceholder = "";
    String EmailHolder;
    String PasswordHolder;
    String PasswordRepeatHolder;
    String FirstHolder;
    String LastHolder;
    String httpRequest;
    String success = "1";
    String fbContent;
    String fName;
    String lName;
    String email;
    String username;
    String id;
    String token;

    private static final int REQ_CODE = 9001;
    String REQ_TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        register = (Button) findViewById(R.id.bSend);
        bBack = (Button) findViewById(R.id.bBack);

        tvAlready = (TextView) findViewById(R.id.tvAlready);
        tvUser = (TextView) findViewById(R.id.tvUser);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordRepeat = (EditText) findViewById(R.id.etPasswordRepeat);
        etFirst = (EditText) findViewById(R.id.etFirst);
        etLast = (EditText) findViewById(R.id.etLast);
        bFacebookLogin = (LoginButton) findViewById(R.id.bFacebookLogin);
        bFacebookLogin.setReadPermissions("public_profile, email");

        bGoogleSignUp = (SignInButton) findViewById(R.id.bGoogleSignUp);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        REQ_TOKEN = preferences.getString("google_oauth2", "");

        GoogleSignInOptions signInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(REQ_TOKEN).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOption).build();

        TextView textView = (TextView) bGoogleSignUp.getChildAt(0);
        textView.setText("Login");

        callbackManager = CallbackManager.Factory.create();

        tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PublisherActivity.this, RegisterActivity.class);
                startActivity(in);
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PublisherActivity.this, IndexActivity.class);
                startActivity(in);
            }
        });

        tvAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PublisherActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

        bGoogleSignUp.setOnClickListener(this);

        bFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {

                System.out.println("onSuccess");
                progressDialog = new ProgressDialog(PublisherActivity.this);
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.d("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("LoginActivity", response.toString());
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
                        FacebookLoginFunction(idFB, email, fName, lName, token);
                        if (accExists = false) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor edit = preferences.edit();
                            String credential = fbContent;
                            Log.d("FB stuff ", preferences.getString("result", ""));

                            String[] arr = credential.split(":");
                            int arrLength = arr.length;
                            Log.d("-", "");
                            Log.d("length of token", String.valueOf(arr[2].length()));
                            Log.d("-", "");
                            Log.d("result length ", String.valueOf(arrLength));
                            Log.d("value ", arr[0]);
                            //Log.d("value ", arr[1]);
                            Log.d("value ", arr[2]);

                            String Ttemp = arr[2];
                            String IDtemp = arr[0];

                            Log.d("fbid ", IDtemp);
                            Log.d("token ", Ttemp);

                            edit.putString("raadz_fb_pub_id", IDtemp);
                            edit.putString("token", Ttemp);
                            edit.commit();

                            edit.putString("adv_or_user", "adv");
                            edit.commit();

                            Intent in = new Intent(PublisherActivity.this, FragPubActivity.class);
                            startActivity(in);
                        }
                        if (accExists = true) {
                            Toast.makeText(PublisherActivity.this, "Sign In", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(PublisherActivity.this, LoginActivity.class);
                            startActivity(in);
                        }
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


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(PublisherActivity.this);
                if (!etPassword.getText().toString().equals(etPasswordRepeat.getText().toString())) {
                    Toast.makeText(PublisherActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    Log.d("pass 1 ", etPassword.getText().toString());
                    Log.d("pass 2 ", etPasswordRepeat.getText().toString());
                } else if (etPassword.length() < 8 && etPasswordRepeat.length() < 8) {
                    Toast.makeText(PublisherActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                    Log.d("pass 1 ", etPassword.getText().toString());
                    Log.d("pass 2 ", etPasswordRepeat.getText().toString());
                } else if (etFirst.length() < 1) {
                    Toast.makeText(PublisherActivity.this, "First name cannot be blank", Toast.LENGTH_SHORT).show();
                    Log.d("first ", etFirst.getText().toString());
                } else if (etLast.length() < 1) {
                    Toast.makeText(PublisherActivity.this, "Last name cannot be blank", Toast.LENGTH_SHORT).show();
                    Log.d("last ", etLast.getText().toString());
                } else {
                    GetCheckEditTextIsEmptyOrNot();
                    if (CheckEditText) {
                        register.setFocusable(false);
                        register.setText("Processing");
                        register.setBackgroundColor(Color.parseColor("#9e9e9e"));
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("fName", FirstHolder);
                        edit.putString("lName", LastHolder);
                        edit.commit();
                        SendDataToServer(EmailHolder, PasswordHolder, PasswordRepeatHolder, FirstHolder, LastHolder);
                    } else {
                        Toast.makeText(PublisherActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    }
                }
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
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }


    public void GetCheckEditTextIsEmptyOrNot() {
        EmailHolder = etEmail.getText().toString();
        PasswordHolder = etPassword.getText().toString();
        PasswordRepeatHolder = etPasswordRepeat.getText().toString();
        FirstHolder = etFirst.getText().toString();
        LastHolder = etLast.getText().toString();
        if (TextUtils.isEmpty(FirstHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
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


    public void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = preferences.edit();
            final GoogleSignInAccount account = result.getSignInAccount();

            fName = account.getGivenName();
            lName = account.getFamilyName();
            email = account.getEmail();
            username = account.getDisplayName();
            id = account.getId();
            token = result.getSignInAccount().getIdToken();

            Log.d("Googlefname ", fName);
            Log.d("Googlelname ", lName);
            Log.d("Googleemail ", email);
            Log.d("Googlefusername ", username);
            Log.d("Googleid ", id);
            Log.d("Googletoken ", token);

            GoogleSignUp(fName, lName, email, "", id, token);

        } else {
            Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        if (view == bGoogleSignUp) {
            if (googleApiClient.isConnected()) {
                signOut();
            }
            buttonPlaceholder = "bGoogleSignIn";
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void processFinish(String result) {
        if (buttonPlaceholder.equals("bGoogleSignIn")) {
            Log.d("GoogleResult ", result);
        }
    }//End of the process method. to the next.


    public void FacebookLoginFunction(final String raadz_user_id, final String email, final String firstname, final String lastname, final String token) {
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

                nameValuePairs.add(new BasicNameValuePair("user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("first_name", firstname));
                nameValuePairs.add(new BasicNameValuePair("last_name", lastname));
                nameValuePairs.add(new BasicNameValuePair("fb_access_token", token));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(fbRegisterURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(fbRegisterURL);
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
                Log.d("httpRequest: ", httpRequest);
                fbContent = httpRequest;
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.contains("invalid access token")) {
                    Log.d("result ", result);
                } else if (result.contains("Invalid email format")) {
                    Toast.makeText(PublisherActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    Log.d("Invalid_email_format ", result);
                } else if (result.contains("signup disabled")) {
                    Toast.makeText(PublisherActivity.this, "Signup Disabled", Toast.LENGTH_SHORT).show();
                    Log.d("Signup Disabled ", result);
                } else if (result.contains("user exists")) {
                    accExists = true;
                    Log.d("result ", result);
                } else
                    accExists = false;
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, email, firstname, lastname, token);
    }

    public void SendDataToServer(final String email, final String password, final String passwordre, final String first, final String last) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String passValue;
                String QuickEmail = email;
                String QuickPassword = password;
                String QuickPasswordRe = passwordre;
                String QuickFirst = first;
                String QuickLast = last;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.clear();
                nameValuePairs.add(new BasicNameValuePair("email", QuickEmail));
                nameValuePairs.add(new BasicNameValuePair("psw", QuickPassword));
                nameValuePairs.add(new BasicNameValuePair("psw-repeat", QuickPasswordRe));
                nameValuePairs.add(new BasicNameValuePair("first_name", QuickFirst));
                nameValuePairs.add(new BasicNameValuePair("last_name", QuickLast));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RegisterURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    //HttpEntity entity = response.getEntity();
                    //Log.d("entity: ", entity.toString());
                    HttpPost request = new HttpPost(RegisterURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);

                    //Send off to server
                    //HttpResponse response = httpClient.execute(request);

                    //Reads response and gets content
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    httpRequest = "";
                    String line = "";
                    String LineSeparator = System.getProperty("line.separator");
                    //Read back server output
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
                Log.d("Result: ", result);
                if (result.contains("Message could not be sent")) {
                    success = "0";
                    Toast.makeText(PublisherActivity.this, "Message could not be sent", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(PublisherActivity.this);
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
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        }
                    });
                    mToEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin"));
                            startActivity(browserIntent);
                        }
                    });

                } else if (result.contains("Message has been sent")) {
                    success = "0";
                    Toast.makeText(PublisherActivity.this, "Message has been sent", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(PublisherActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);

                    Button mBack = (Button) mView.findViewById(R.id.bBack);
                    Button mToEmail = (Button) mView.findViewById(R.id.bToEmail);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    mBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent browserIntent = new Intent(PublisherActivity.this, LoginActivity.class);
                            startActivity(browserIntent);
                        }
                    });
                    mToEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin"));
                            startActivity(browserIntent);
                        }
                    });

                } else if (result.contains("passwords do not match")) {
                    success = "0";
                    Toast.makeText(PublisherActivity.this, "passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (result.contains("user exists")) {
                    success = "0";
                    Toast.makeText(PublisherActivity.this, "user already exists...Sign in", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(PublisherActivity.this, LoginActivity.class);
                    startActivity(in);
                    result = "";
                } else if (result.contains("password error")) {
                    success = "0";
                    Toast.makeText(PublisherActivity.this, "password error", Toast.LENGTH_SHORT).show();
                }
                if (success.contains("1")) {
                    Toast.makeText(PublisherActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(PublisherActivity.this, FragPubActivity.class);
                    startActivity(in);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(first, last, email, password, passwordre);
    }

    public void GoogleSignUp(final String first, final String last, final String email, final String referrer, final String google_id, final String google_token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("first_name", first));
                nameValuePairs.add(new BasicNameValuePair("last_name", last));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("referrer", referrer));
                nameValuePairs.add(new BasicNameValuePair("user_id", google_id));
                nameValuePairs.add(new BasicNameValuePair("goog_access_token", google_token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(GooglePubSignUpURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(GooglePubSignUpURL);
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
                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("google_information", httpRequest);
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
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                Log.d("PostResultGoogle ", result);
                if (result.contains("Message has been sent")) {
                    Toast.makeText(PublisherActivity.this, "User already exists. Please sign in.", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(PublisherActivity.this, LoginActivity.class);
                    startActivity(in);
                } else if (result.contains("Invalid email format")) {
                    Toast.makeText(PublisherActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    Log.d("Invalid_email_format ", result);
                } else if (result.contains("signup disabled")) {
                    Toast.makeText(PublisherActivity.this, "Signup Disabled", Toast.LENGTH_SHORT).show();
                    Log.d("Signup_disabled ", result);
                } else if (result.contains("publisher exists")) {
                    Toast.makeText(PublisherActivity.this, result + "Account already exists, please sign in.", Toast.LENGTH_SHORT).show();
                    signOut();
                    Intent in = new Intent(PublisherActivity.this, LoginActivity.class);
                    startActivity(in);
                } else if (result.contains("invalid access token")) {
                    Toast.makeText(PublisherActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                    Log.d("token_error ", result);
                } else if (result.contains("invalid data")) {
                    Toast.makeText(PublisherActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                    Log.d("invalid data ", result);
                } else {
                    Log.d("Other ", result);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(google_id, google_token);
    }


}

















/*


_________________________________________________________________



public class MainActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    EditText etUsername, etPassword;
    Button bLogin;
    String userID;
    String token;
    String responseText;
    String url3 = "http://beta.raadz.com/login.php";

    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        textView = (TextView) findViewById(R.id.textView);
        callbackManager = CallbackManager.Factory.create();


        //String url = "https://raadz.000webhostapp.com/index.php";
        //String url = "https://raadzinc.visualstudio.com/_git/RaadzWebsite/api.php";
        StringRequest stringRequest1 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading google", Toast.LENGTH_SHORT).show();
            }
        });

        //String url2 = "https://raadzinc.visualstudio.com/_git/RaadzWebsite/api.php";
        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading google", Toast.LENGTH_SHORT).show();
            }
        });


        //MySingleton.getInstance(this).addToRequestQueue(stringRequest3);
        //MySingleton.getInstance(this).addToRequestQueue(stringRequest2);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute(url3);

                StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        if (response.contains("logged in successfully")) {
                            responseText = response;
                            Toast.makeText(getApplicationContext(), "We are in", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(MainActivity.this, SubActivity.class);
                            startActivity(in);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String temp = responseText;
                        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", etUsername.getText().toString());
                        params.put("password", etPassword.getText().toString());
                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textView.setText("Login Success");
                userID = loginResult.getAccessToken().getUserId();
                token = loginResult.getAccessToken().getToken();
            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
*/