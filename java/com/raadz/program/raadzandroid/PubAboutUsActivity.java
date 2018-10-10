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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;

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
import java.util.List;

import static com.raadz.program.raadzandroid.R.id.adv_home;
import static com.raadz.program.raadzandroid.R.id.wvAbout1;
import static com.raadz.program.raadzandroid.R.id.wvAbout2;
import static com.raadz.program.raadzandroid.R.id.wvAbout3;

public class PubAboutUsActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    BufferedReader bufferedReader;

    ImageButton ibProfile;

    WebView wvAbout1;
    WebView wvAbout2;
    WebView wvAbout3;

    TextView tvMoney;
    TextView tvID;

    String httpRequest;
    String AdvertiserDataURL = "https://raadz.com/getPubInfo.php";
    String balance;
    String money;
    String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_about_us);

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

        tvMoney = (TextView)findViewById(R.id.tvMoney);

        wvAbout1 = (WebView) findViewById(R.id.wvAbout1);
        wvAbout2 = (WebView) findViewById(R.id.wvAbout2);
        wvAbout3 = (WebView) findViewById(R.id.wvAbout3);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("cash", "");

        wvAbout1.loadUrl("https://raadz.com/about_us_works.html");
        wvAbout2.loadUrl("https://raadz.com/about_us_story.html");
        wvAbout3.loadUrl("https://raadz.com/about_us_team.html");

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PubAboutUsActivity.this, PubProfileActivity.class);
                startActivity(in);
            }
        });

        AdvertiserFunction(preferences.getString("raadz_pub_id", ""));

    }

    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, FragPubActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
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
                        Intent in = new Intent(PubAboutUsActivity.this, IndexActivity.class);
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
        } else if (id == R.id.adv_submit) {
            Intent in = new Intent(this, PubSubmitActivity.class);
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
        }else if (id == R.id.adv_contact) {
            Intent in = new Intent(this, PubContactUsActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_logout){
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
                    Intent in = new Intent(PubAboutUsActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAboutUsActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubAboutUsActivity.this);
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
                        tvMoney.setText("$"+ ok);
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

    }

    @Override
    public void processFinish(String s) {

    }
}
