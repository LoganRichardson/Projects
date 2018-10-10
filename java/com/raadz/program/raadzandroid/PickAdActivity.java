package com.raadz.program.raadzandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.apache.http.HttpEntity;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class PickAdActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AsyncResponse {

    BufferedReader bufferedReader;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    NavigationView navigationView = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    ImageButton ibProfile;

    Button ytPlay;
    Button bYouTubeAd;
    Button bBannerAd;
    Button bChoose;
    Button bAudioAd;

    TextView tvTitle;
    TextView tvMoney;

    String balance;
    String money;

    int indexSize;
    int listCount = 0;

    String buttonPlaceholder = "";
    String adType;
    String UID;
    String raadz_ad_id;
    String ad_type;
    String yt_id;
    String ad_title;
    String ad_link;
    String raadz_pub_id;
    String company_name;
    String ad_cost;
    String gender;
    String age;
    String interests;
    String location;
    String questions;
    String ad_ref;

    String RegisterURL = "https://raadz.com/getNewAd.php";
    String httpRequest;
    String[] userInfo = new String[31];
    List<List<String>> list = new ArrayList<List<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_ad);
        // Inflate the layout for this fragment
        SharedPreferences preferencesU = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editU = preferencesU.edit();

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

        bYouTubeAd = (Button) findViewById(R.id.bYouTubeAd);
        bBannerAd = (Button) findViewById(R.id.bBannerAd);
        bAudioAd = (Button) findViewById(R.id.bAudioAd);
        ytPlay = (Button) findViewById(R.id.ytPlay);
        bChoose = (Button) findViewById(R.id.bChoose);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("funds", "");
//        Log.d("balance: ", balance);
//        try {
//            JSONArray jArray = new JSONArray(balance);
//            //Log.d("json: ", httpRequest);
//            for (int i = 0 ; i < jArray.length(); i++) {
//                JSONObject jObj = jArray.getJSONObject(i);
//                Log.d("money: ", jObj.getString("balance"));
//                money = jObj.getString("balance");
//            }
//        }catch(org.json.JSONException e){
//            System.out.println();
//        }
        tvMoney.setText("$" + balance);
        UID = preferences.getString("raadz_user_id", "");
        Log.d("uid: ", UID);

        Log.d("userInformation ", preferencesU.getString("user_details_complete", ""));

        if(preferencesU.getString("user_details_complete", "").equals("0")){
            Toast.makeText(this, "Please fill our your information before you Rate any Ads!", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, UpdateInformation.class);
            startActivity(in);
        }


        bYouTubeAd.setOnClickListener(this);

        bBannerAd.setOnClickListener(this);

        bAudioAd.setOnClickListener(this);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PickAdActivity.this, AdsHistory.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void processFinish(String s) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if(buttonPlaceholder.equals("bYouTube")) {
            if (s.equals("no new ads")) {
                Toast.makeText(this, "no new ads", Toast.LENGTH_SHORT).show();
                editor.putString("httpRequest", s);
                editor.commit();
            } else if (s.equals("invalid user id")) {
                Toast.makeText(this, "invalid user id", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Log.d("Inside try ", s);
                    editor.putString("httpRequest", s);
                    editor.commit();
                    JSONArray jArray = new JSONArray(s);

                    JSONObject jObj = jArray.getJSONObject(0);
                    //Log.d("the JSON???", s);
                    //Log.d("adid ", String.valueOf(jObj.getJSONObject("raadz_ad_id")));
                    //Log.d("i ", jArray.getString(i));
                    //Log.d("test ", ";lkajsd;lfkj");
                    raadz_ad_id = jObj.getString("raadz_ad_id");
                    Log.d("ad_id ", raadz_ad_id);
                    ad_type = jObj.getString("ad_type");
                    Log.d("ad_type ", ad_type);
                    ad_title = jObj.getString("ad_title");
                    Log.d("ad_title ", ad_title);
                    ad_link = jObj.getString("ad_link");
                    Log.d("ad_link ", ad_link);
                    raadz_pub_id = jObj.getString("raadz_pub_id");
                    Log.d("raadz_pub_id ", raadz_pub_id);
                    company_name = jObj.getString("company_name");
                    Log.d("company_name ", company_name);
                    yt_id = jObj.getString("ad_ref");
                    Log.d("ad_ref ", yt_id);
                    editor.putString("raadz_ad_id", raadz_ad_id);
                    editor.putString("ad_type", ad_type);
                    editor.putString("yt_id", yt_id);
                    editor.putString("ad_title", ad_title);
                    editor.putString("ad_link", ad_link);
                    editor.putString("raadz_ad_pub_id", raadz_pub_id);
                    editor.putString("company_name", company_name);
                    editor.commit();

                } catch (org.json.JSONException e) {
                    System.out.println();
                }

            }
            Intent in = new Intent(PickAdActivity.this, YouTubeActivity.class);
            startActivity(in);
            //JSONArray jsonArray = new JSONArray(s);
        }
        if(buttonPlaceholder.equals("bBannerAd")){
            if (s.equals("no new ads")) {
                editor.putString("result", s);
                editor.commit();
                Toast.makeText(this, "no new ads", Toast.LENGTH_SHORT).show();
            }
            if (s.equals("invalid user id")) {
                Toast.makeText(this, "invalid user id", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Log.d("s ", s);
                    JSONArray jArray = new JSONArray(s);

                    JSONObject jObj = jArray.getJSONObject(0);
                    raadz_ad_id = jObj.getString("raadz_ad_id");
                    Log.d("ad_id ", raadz_ad_id);
                    ad_type = jObj.getString("ad_type");
                    Log.d("ad_type ", ad_type);
                    ad_title = jObj.getString("ad_title");
                    Log.d("ad_title ", ad_title);
                    ad_link = jObj.getString("ad_link");
                    Log.d("ad_link ", ad_link);
                    raadz_pub_id = jObj.getString("raadz_pub_id");
                    Log.d("raadz_pub_id ", raadz_pub_id);
                    company_name = jObj.getString("company_name");
                    Log.d("company_name ", company_name);
                    ad_ref = jObj.getString("ad_ref");
                    Log.d("ad_ref ", ad_ref);

                    editor.putString("raadz_ad_id", raadz_ad_id);
                    editor.putString("ad_type", ad_type);
                    editor.putString("ad_title", ad_title);
                    editor.putString("ad_link", ad_link);
                    editor.putString("raadz_ad_pub_id", raadz_pub_id);
                    editor.putString("company_name", company_name);
                    editor.putString("result", s);
                    editor.putString("ad_ref", ad_ref);
                    editor.commit();

                } catch (org.json.JSONException e) {
                    System.out.println();
                }

            }
            Intent in = new Intent(PickAdActivity.this, BannerActivity.class);
            startActivity(in);
        }

        if(buttonPlaceholder.equals("bAudioAd")){
            Log.d("pickadfunction ", s);
            if (s.equals("no new ads")) {
                editor.putString("result", s);
                editor.commit();
                Toast.makeText(this, "no new ads", Toast.LENGTH_SHORT).show();
            }
            else if (s.equals("invalid user id")) {
                Toast.makeText(this, "invalid user id", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Log.d("s ", s);
                    JSONArray jArray = new JSONArray(s);

                    JSONObject jObj = jArray.getJSONObject(0);
                    raadz_ad_id = jObj.getString("raadz_ad_id");
                    Log.d("ad_id ", raadz_ad_id);
                    ad_type = jObj.getString("ad_type");
                    Log.d("ad_type ", ad_type);
                    ad_title = jObj.getString("ad_title");
                    Log.d("ad_title ", ad_title);
                    ad_link = jObj.getString("ad_link");
                    Log.d("ad_link ", ad_link);
                    raadz_pub_id = jObj.getString("raadz_pub_id");
                    Log.d("raadz_pub_id ", raadz_pub_id);
                    company_name = jObj.getString("company_name");
                    Log.d("company_name ", company_name);
                    ad_ref = jObj.getString("ad_ref");
                    Log.d("ad_ref ", ad_ref);


                    editor.putString("raadz_ad_id", raadz_ad_id);
                    editor.putString("ad_type", ad_type);
                    editor.putString("ad_title", ad_title);
                    editor.putString("ad_link", ad_link);
                    editor.putString("raadz_ad_pub_id", raadz_pub_id);
                    editor.putString("company_name", company_name);
                    editor.putString("result", s);
                    editor.putString("ad_ref", ad_ref);
                    editor.commit();

                } catch (org.json.JSONException e) {
                    System.out.println();
                }

            }

            Intent in = new Intent(PickAdActivity.this, AudioActivity.class);
            startActivity(in);
        }

    }


    @Override
    public void onClick(View view) {
        if (view == bYouTubeAd) {
            buttonPlaceholder = "bYouTube";
            adType = "1";
            Log.d("UID ", UID);
            Log.d("adType ", adType);
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", UID);
            postData.put("ad_type", adType);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(RegisterURL);

        }

        if(view == bBannerAd){
            buttonPlaceholder = "bBannerAd";
            adType = "2";
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("ad_type", adType);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(RegisterURL);
        }

        if(view == bAudioAd){
            buttonPlaceholder = "bAudioAd";
            adType = "3";
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            HashMap postData = new HashMap();
            postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
            postData.put("ad_type", adType);
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(RegisterURL);
        }

    }//End of onClick View


    private String readStream(String url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            String result = "";

            // convert response to string
            new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            InputStreamReader r = new InputStreamReader(is, "UTF-8");
            int intch;
            while ((intch = r.read()) != -1) {
                char ch = (char) intch;
                // Log.i("app", Character.toString(ch));
                String s = new String(Character.toString(ch).getBytes(), "UTF-8");
                sb.append(s);
            }
            is.close();
            result = sb.toString();
            Log.d("Result2: ", result);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());

        }
        return "ok";
    }


    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, FragActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
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
                        Intent in = new Intent(PickAdActivity.this, IndexActivity.class);
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
        }else if (id == R.id.user_history) {
            Intent in = new Intent(this, UserAdHistoryActivity.class);
            startActivity(in);
        }else if (id == R.id.user_leaderboards) {
            Intent in = new Intent(this, LeaderboardsUserActivity.class);
            startActivity(in);
        }else if (id == R.id.user_tutorial){
            Intent in = new Intent(this, TutorialActivity.class);
            startActivity(in);
        }else if (id == R.id.user_about) {
            Intent in = new Intent(this, AboutUsActivity.class);
            startActivity(in);
        }else if (id == R.id.user_contact) {
            Intent in = new Intent(this, ContactUsActivity.class);
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
                    Intent in = new Intent(PickAdActivity.this, IndexActivity.class);
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
        else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PickAdActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PickAdActivity.this);
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


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    public void SendDataToServer(final String ruid, final String QuickadType) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String QuickID = ruid;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", QuickID));
                nameValuePairs.add(new BasicNameValuePair("ad_type", QuickadType));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RegisterURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RegisterURL);
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
                //Log.d("SendRequest return: ", httpRequest);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });


                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(result);
                int i = 0;
                int t = 0;
                ArrayList<String> listarray = new ArrayList<String>(30);
                //Log.d("Result: ", result);


                if (result.contains("no new videos")) {

                    Log.d("if 1: ", "no new videos");
                }
                if (result.contains("invalid user id")) {
                    Log.d("if 2: ", "invalid user");
                } else
                    try {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        JSONArray jArray = new JSONArray(result);
                        JSONObject jObj = jArray.getJSONObject(0);
                        ad_type = jObj.getString("ad_type");
                        yt_id = jObj.getString("yt_id");
                        ad_title = jObj.getString("ad_title");
                        ad_link = jObj.getString("ad_link");
                        raadz_pub_id = jObj.getString("raadz_pub_id");
                        company_name = jObj.getString("company_name");
                        Log.d("1 ", jObj.getString("ad_type"));
                        Log.d(";jasdlkfklasdffasda ", jObj.getString("yt_id"));
                        Log.d("3 ", jObj.getString("ad_title"));
                        Log.d("4 ", jObj.getString("ad_link"));
                        Log.d("5 ", raadz_pub_id);
                        Log.d("6 ", company_name);
                        editor.putString("raadz_ad_id", raadz_ad_id);
                        editor.putString("ad_type", ad_type);
                        editor.putString("yt_id", yt_id);
                        editor.putString("ad_title", ad_title);
                        editor.putString("ad_link", ad_link);
                        editor.putString("raadz_pub_id_yt", raadz_pub_id);
                        editor.putString("company_name", company_name);
                        editor.putString("result", result);
                        editor.commit();

                    } catch (org.json.JSONException e) {
                        System.out.println();
                    }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(ruid, QuickadType);
    }
}