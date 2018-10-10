package com.raadz.program.raadzandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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

import static com.raadz.program.raadzandroid.R.id.LLDrawingsHeader;
import static com.raadz.program.raadzandroid.R.id.LLDrawingsTable;
import static com.raadz.program.raadzandroid.R.id.p_leaderboards;

public class IndexActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient googleApiClient;
    NavigationView navigationView = null;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;
    CallbackManager callbackManager;

    TextView tvMoney;
    TextView tvVersion;

    Button bLogin;
    Button bUser;
    Button bPublisher;
    Button bAudio;
    Button bTest2;

    String getDataURL = "https://raadz.com/getUserData.php";
    String getDrawingsDetailsURL = "https://raadz.com/getDrawingDetails.php";
    String getInfoURL = "https://raadz.com/getUserInfo.php";
    String getAPIKeysURL = "https://raadz.com/getAPIKeys.php";
    String balance;
    String money;
    String httpRequest;

    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        API_Function();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile);
//        toolbar.setOverflowIcon(drawable);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu4);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        callbackManager = CallbackManager.Factory.create();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("emailPass");
        edit.commit();

        tvVersion = (TextView) findViewById(R.id.tvVersion);

        bLogin = (Button) findViewById(R.id.bSend);
        bUser = (Button) findViewById(R.id.bUser);
        bPublisher = (Button) findViewById(R.id.bPublisher);
        bAudio = (Button) findViewById(R.id.bAudio);
        bTest2 = (Button) findViewById(R.id.bTest2);

        String s_email = preferences.getString("email", "");
        String s_password = preferences.getString("password", "");
        String s_uid = preferences.getString("raadz_user_id", "");
        String fb_uid = preferences.getString("raadz_fb_user_id", "");
        String s_pid = preferences.getString("raadz_pub_id", "");
        String fb_pid = preferences.getString("raadz_fb_pub_id", "");
        String s_token = preferences.getString("token", "");
        Log.d("email: ", s_email);
        Log.d("password: ", s_password);
        Log.d("raadz_user_id: ", s_uid);
        Log.d("raadz_pub_id: ", s_pid);
        Log.d("token: ", s_token);

        Log.d("pid length ", String.valueOf(s_pid.length()));
        Log.d("password length: ", String.valueOf(s_password.length()));
        Log.d("token length: ", String.valueOf(s_token.length()));

        tvVersion.setText("Android version: 0.0.12");

        if (s_pid.length() > 5 && s_password.length() > 0 || s_pid.length() > 5 && s_token.length() > 0) {
            DataFunction(s_pid, s_token);
            try {
                JSONArray jArray = new JSONArray(preferences.getString("user_data", ""));
                //Log.d("json: ", httpRequest);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    Log.d("name other ", jObj.getString("interest_text"));
                    if (jObj.getString("interest_value").equals("user_name")) {
                        Log.d("name ", jObj.getString("interest_value"));
                        edit.putString("user_data_name", jObj.getString("interest_text"));
                        edit.commit();
                    }
                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
            Log.d("here", "are we here");
            HashMap postData2 = new HashMap();
            postData2.put("raadz_user_id", "");
            postData2.put("raadz_pub_id", s_pid);
            postData2.put("raadz_token", s_token);
            PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData2);
            task2.execute("https://raadz.com/getLeaderboards.php");
            edit.remove("raadz_user_id").commit();
            Intent in2 = new Intent(IndexActivity.this, FragPubActivity.class);
            startActivity(in2);
        } else if (s_email.length() > 0 && s_password.length() > 0 || s_uid.length() > 0 && s_token.length() > 0 || fb_uid.length() > 0 && s_token.length() > 0) {
            DataFunction(s_uid, s_token);
            DrawingsFunction(s_uid, s_token);
            try {
                JSONArray jArray = new JSONArray(preferences.getString("user_data", ""));
                //Log.d("json: ", httpRequest);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    Log.d("name other ", jObj.getString("interest_text"));
                    if (jObj.getString("interest_value").equals("user_name")) {
                        Log.d("name ", jObj.getString("interest_value"));
                        edit.putString("user_data_name", jObj.getString("interest_text"));
                        edit.commit();
                    }
                }
            } catch (org.json.JSONException e) {
                System.out.println();
            }
            Log.d("uid here ", s_uid);
            HashMap postData2 = new HashMap();
            postData2.put("raadz_user_id", s_uid);
            postData2.put("raadz_pub_id", "");
            postData2.put("raadz_token", s_token);
            PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData2);
            task2.execute("https://raadz.com/getLeaderboards.php");
            edit.remove("raadz_pub_id").commit();
            Intent in = new Intent(IndexActivity.this, FragActivity.class);
            startActivity(in);
        } else {
            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    Log.d("uid here ", "here at uidlogin");
                    HashMap postData2 = new HashMap();
                    postData2.put("raadz_user_id", "");
                    postData2.put("raadz_pub_id", "");
                    postData2.put("raadz_token", "");
                    PostResponseAsyncTask task2 = new PostResponseAsyncTask(IndexActivity.this, postData2);
                    task2.execute("https://raadz.com/getLeaderboards.php");
                    Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        bUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        bPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, PublisherActivity.class);
                startActivity(intent);
            }
        });

        bAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(IndexActivity.this, _TestActivity.class);
                startActivity(in);
            }
        });

        bTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(IndexActivity.this, _Test2.class);
                startActivity(in);
            }
        });


    }

    public void onButtonPressed(Uri uri) {

    }


    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleResult(result);
        }
    }

    public void signIn() {
        Intent in = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(in, REQ_CODE);
    }

    public void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final SharedPreferences.Editor edit = preferences.edit();
            final GoogleSignInAccount account = result.getSignInAccount();

            Log.d("LogoutTest ", preferences.getString("google_sign_out", ""));

            if (preferences.getString("google_sign_out", "").equals("1")) {

                Log.d("LogoutTest ", preferences.getString("google_sign_out", ""));

                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d("Logout", "are we logged out");
                        Toast.makeText(getApplicationContext(), "Successfully logged out of Google.", Toast.LENGTH_SHORT).show();
                        edit.clear();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Google Logout Failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == p_leaderboards) {
            Intent in = new Intent(this, PreLeaderboardsActivity.class);
            startActivity(in);
        } else if (id == R.id.p_nav_about) {
            Intent in = new Intent(this, PreAboutUsActivity.class);
            startActivity(in);
        } else if (id == R.id.p_nav_contact) {
            Intent in = new Intent(this, PreContactUsActivity.class);
            startActivity(in);
        }else if (id == R.id.p_advertise) {
            Intent in = new Intent(this, PreAdvertiseActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(IndexActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(IndexActivity.this);
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("IndexPass", s);
        edit.commit();
        Log.d("result 3 ", s);

    }

    public void DataFunction(final String raadz_user_id, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getDataURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getDataURL);
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

    public void DrawingsFunction(final String raadz_user_id, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("raadz_token", token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getDrawingsDetailsURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getDrawingsDetailsURL);
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
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                Log.d("post_exe ", result);
                Log.d("Index_Drawings", httpRequest);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("drawings_result", result);
                edit.commit();



            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token);
    }

    public void API_Function() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                httpRequest = "";
                bufferedReader = null;
                stringBuffer = new java.lang.StringBuffer();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("source", "2"));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getAPIKeysURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(getAPIKeysURL);
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
                Log.d("API_Result ", result);
                try{
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("API_KEYS", result);
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObj = jArray.getJSONObject(i);
                        edit.putString("stripe_public_key", jObj.getString("stripe_public_key"));
                        edit.putString("youtube_api_key", jObj.getString("youtube_api_key"));
                        edit.putString("recaptcha_public_key", jObj.getString("recaptcha_public_key"));
                        edit.putString("google_oauth2", jObj.getString("google_oauth2"));
                        edit.putString("facebook_app_id", jObj.getString("facebook_app_id"));
                        edit.commit();
                    }
                }catch (org.json.JSONException e){
                    System.out.println(e);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
