package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.raadz.program.raadzandroid.R.id.nc_nav_leaderboards;

public class NCContactUsActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    Button bContact;

    EditText etName;
    EditText etEmail;
    EditText etMessage;

    TextView tvID;

    String httpRequest;
    String contactUsURL = "https://raadz.com/ContactUs.php";
    String AdvertiserDataURL = "https://raadz.com/getPubInfo.php";
    String balance;
    String money;
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccontact_us);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMessage = (EditText)findViewById(R.id.etMessage);

        bContact = (Button)findViewById(R.id.bContact);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("money", "");

        AdvertiserFunction(preferences.getString("raadz_pub_id", ""));

        bContact.setOnClickListener(this);

    }





    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, FragNotConfirmedActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == nc_nav_leaderboards) {
            Intent in = new Intent(this, NCLeaderboardsActivity.class);
            startActivity(in);
        }else if (id == R.id.nc_nav_about) {
            Intent in = new Intent(this, NCAboutUsActivity.class);
            startActivity(in);
        }else if (id == R.id.nc_nav_home) {
            Intent in = new Intent(this, FragNotConfirmedActivity.class);
            startActivity(in);
        }else if(id == R.id.nc_nav_logout){
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
                    Intent in = new Intent(NCContactUsActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NCContactUsActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NCContactUsActivity.this);
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


    public void AdvertiserFunction(final String raadz_user_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pub_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", preferences.getString("token", "")));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(AdvertiserDataURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
                        balance = preferences.getString("money", "");
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
                        String ok = preferences.getString("money", "");
                        //Log.d("runUIThread ", money);
                        Log.d("httpUI ", httpRequest);
                        //String ok = preferences.getString("money", "");
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

    @Override
    public void onClick(View v) {
        if(v == bContact){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Log.d("Name ", etName.getText().toString());
            Log.d("Email ", etEmail.getText().toString());
            Log.d("Message ", etMessage.getText().toString());
            Log.d("ID ", preferences.getString("raadz_user_id", ""));

            if(etEmail.getText().toString().length() < 2){
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
            }
            else if(etMessage.getText().toString().length() < 10){
                Toast.makeText(this, "Message too short", Toast.LENGTH_SHORT).show();
            }
            else if(etName.getText().toString().length() < 3){
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
            else{
                HashMap postData2 = new HashMap();
                postData2.put("name", etName.getText().toString());
                postData2.put("email", etEmail.getText().toString());
                postData2.put("message", etMessage.getText().toString());
                postData2.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
                postData2.put("raadz_pub_id", preferences.getString("raadz_pub_id", ""));
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(this, postData2);
                task2.execute(contactUsURL);
            }
        }
    }

    @Override
    public void processFinish(String s) {
        Log.d("ConactResult ", s);
        Toast.makeText(this, "Message Sucessfully ", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(NCContactUsActivity.this, FragNotConfirmedActivity.class);
        startActivity(in);
    }
}
