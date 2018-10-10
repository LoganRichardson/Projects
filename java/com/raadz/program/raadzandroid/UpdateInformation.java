package com.raadz.program.raadzandroid;

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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import static android.R.attr.id;
import static android.R.attr.value;
import static android.R.string.ok;

public class UpdateInformation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse, View.OnClickListener {

    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    java.lang.StringBuffer stringBuffer2 = new java.lang.StringBuffer();
    BufferedReader bufferedReader;
    BufferedReader bufferedReader2;
    NavigationView navigationView = null;
    HashMap interestsData = new HashMap();

    ImageButton ibProfile;

    Button bProfile;
    Button bSave;
    Button button3;
    Button bCancelRequest;
    Button bSubmitRequest;
    Button bRequest;

    Spinner sLocation;

    LinearLayout llGender;
    LinearLayout llAge;
    LinearLayout llLocation;
    LinearLayout llInterests;
    LinearLayout LLInterestCopy;
    LinearLayout LLInterestCopy2;

    LinearLayout LLRequest;

    TextView tvAgeGroup;
    TextView tvGenderGroup;
    TextView tvJSON;

    TextView tvGender;
    TextView tvAge;
    TextView tvInterests;
    TextView tvMoney;

    EditText etMessageRequest;

    RadioButton getRbMale, getRbFemale, getRbOther;
    RadioButton getRb1, getRb2, getRb3, getRb4, getRb5;

    RadioButton rbMale;
    RadioButton rbFemale;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;

    RadioGroup rgGender;
    RadioGroup rgAge;

    CheckBox checkBox;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    CheckBox checkBox10;
    CheckBox checkBox11;
    CheckBox checkBox12;
    CheckBox checkBox13;
    CheckBox checkBox14;
    CheckBox checkBox15;
    CheckBox checkBox16;

    CheckBox cbRGender;
    CheckBox cbRAge;
    CheckBox cbRLocation;
    CheckBox cbRInterests;

    String updateUserURL = "https://raadz.com/updateUserData.php";
    String userDataURL = "https://raadz.com/getUserData.php";
    String cityListURL = "https://raadz.com/getCityList.php";
    String RequestDataChangeURL = "https://raadz.com/requestUserDataChange.php";
    String InterestsListURL = "https://raadz.com/getInterestList.php";
    String buttonPlaceholder;
    String selectedGender;
    String selectAge;
    String selectInterest;
    String httpRequest;
    String raadz_user_id;
    String raadzToken;
    String gender;
    String fullName;
    String userEmail;
    String locationDefault;
    String location;
    String balance;
    String money;
    String selection;
    String info;

    int age;
    int ok;
    long interests = 0;
    int interestsValue = 0;
    int reload = 0;
    int test = 1;
    int request_change = 0;
    long int_track = 0;
    long int_value = 0;

    long[] interests_array_temp = new long[54];
    long[] interests_array = new long[54];
    long[] interests_add = new long[54];
    long[] interest_number_array = new long[54];
    long[] valueArray = new long[54];

    boolean layout_track = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        doEverything();
        bSave.setOnClickListener(this);
        bProfile.setOnClickListener(this);



/*
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test: ", String.valueOf(test));
                Log.d("Interests: ", String.valueOf(interests));
                //UpdateFunction(raadz_user_id, raadzToken, gender, String.valueOf(age), String.valueOf(interests));

                Intent in = new Intent(UpdateInformation.this, FragActivity.class);
                startActivity(in);
            }
        });
*/
        //button3.setOnClickListener(this);

    }


    public void doEverything() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        raadz_user_id = preferences.getString("raadz_user_id", "");
        raadzToken = preferences.getString("token", "");
        Log.d("UID ", raadz_user_id);
        Log.d("TID ", raadzToken);
        UpdateFunction(raadz_user_id, raadzToken);
        InterestsFunction();
        //Log.d("info main: ", info);

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

        sLocation = (Spinner) findViewById(R.id.sLocation);

        bProfile = (Button) findViewById(R.id.bProfile);
        bSave = (Button) findViewById(R.id.bSave);
        button3 = (Button) findViewById(R.id.button3);

        bRequest = (Button) findViewById(R.id.bRequest);
        bCancelRequest = (Button) findViewById(R.id.bCancelRequest);
        bSubmitRequest = (Button) findViewById(R.id.bSubmitRequest);

        llGender = (LinearLayout) findViewById(R.id.llGender);
        llAge = (LinearLayout) findViewById(R.id.llAge);
        llLocation = (LinearLayout) findViewById(R.id.llLocation);
        llInterests = (LinearLayout) findViewById(R.id.llInterests);
        LLInterestCopy = (LinearLayout) findViewById(R.id.LLInterestCopy);
        LLInterestCopy2 = (LinearLayout) findViewById(R.id.LLInterestCopy2);

        LLRequest = (LinearLayout) findViewById(R.id.LLRequest);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgAge = (RadioGroup) findViewById(R.id.rgAge);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);

        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
        checkBox9 = (CheckBox) findViewById(R.id.checkBox9);
        checkBox10 = (CheckBox) findViewById(R.id.checkBox10);
        checkBox11 = (CheckBox) findViewById(R.id.checkBox11);
        checkBox12 = (CheckBox) findViewById(R.id.checkBox12);
        checkBox13 = (CheckBox) findViewById(R.id.checkBox13);
        checkBox14 = (CheckBox) findViewById(R.id.checkBox14);
        checkBox15 = (CheckBox) findViewById(R.id.checkBox15);
        checkBox16 = (CheckBox) findViewById(R.id.checkBox16);

        cbRGender = (CheckBox) findViewById(R.id.cbRGender);
        cbRAge = (CheckBox) findViewById(R.id.cbRAge);
        cbRLocation = (CheckBox) findViewById(R.id.cbRLocation);
        cbRInterests = (CheckBox) findViewById(R.id.cbRInterests);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvGenderGroup = (TextView) findViewById(R.id.tvGenderGroup);
        tvAgeGroup = (TextView) findViewById(R.id.tvAgeGroup);

        etMessageRequest = (EditText) findViewById(R.id.etMessage);

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

        if (preferences.getString("from_profile", "").equals("true")) {
            bProfile.setVisibility(View.VISIBLE);
        }

        arrayValues();

        Log.d("testCities ", preferences.getString("city_list", ""));


        bRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bRequest.setVisibility(View.GONE);
                LLRequest.setVisibility(View.VISIBLE);
            }
        });

        bCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bRequest.setVisibility(View.VISIBLE);
                LLRequest.setVisibility(View.GONE);

                cbRGender.setChecked(false);
                cbRAge.setChecked(false);
                cbRLocation.setChecked(false);
                cbRInterests.setChecked(false);

                request_change = 0;
            }
        });

        bSubmitRequest.setOnClickListener(this);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UpdateInformation.this, AdsHistory.class);
                startActivity(in);
            }
        });


        cbRGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRGender.isChecked()) {
                    request_change = request_change + 1;
                } else {
                    request_change = request_change - 1;
                }
                Log.d("request_change ", String.valueOf(request_change));
            }
        });

        cbRAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRAge.isChecked()) {
                    request_change = request_change + 2;
                } else {
                    request_change = request_change - 2;
                }
                Log.d("request_change ", String.valueOf(request_change));
            }
        });

        cbRLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRLocation.isChecked()) {
                    request_change = request_change + 4;
                } else {
                    request_change = request_change - 4;
                }
                Log.d("request_change ", String.valueOf(request_change));
            }
        });

        cbRInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRInterests.isChecked()) {
                    request_change = request_change + 8;
                } else {
                    request_change = request_change - 8;
                }
                Log.d("request_change ", String.valueOf(request_change));
            }
        });


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rbMale:
                        if (rbMale.isChecked()) {
                            rbMale.setEnabled(false);
                            rbFemale.setEnabled(false);
                        }
                        gender = "M";
                        break;
                    case R.id.rbFemale:
                        if (rbFemale.isChecked()) {
                            rbFemale.setEnabled(false);
                            rbMale.setEnabled(false);
                        }
                        gender = "F";
                        break;
                }
            }
        });

        rgAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                switch (rb.getId()) {
                    case R.id.rb1:
                        age = 1;
                        break;
                    case R.id.rb2:
                        age = 2;
                        break;
                    case R.id.rb3:
                        age = 4;
                        break;
                    case R.id.rb4:
                        age = 8;
                        break;
                    case R.id.rb5:
                        age = 16;
                        break;
                }
            }
        });


        location = preferences.getString("city_list", "");
        String kept;
        String remainder;
        List<String> list = new ArrayList<String>();

        for (int j = 0; j < location.length(); j++) {
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


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateInformation.this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = adapter.getPosition(location);
        Log.d("position ", String.valueOf(location));
        Log.d("position String ", location);
        sLocation.setAdapter(adapter);
        sLocation.setSelection(position);


        sLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationDefault = sLocation.getSelectedItem().toString();
                Log.d("item selected ", locationDefault);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        interestsClick();
        Log.d("Interests - BSB: ", String.valueOf(interests));
    }

    public void ageDeselect() {
        rb1.setEnabled(false);
        rb1.setFocusable(false);
        rb2.setEnabled(false);
        rb2.setFocusable(false);
        rb3.setEnabled(false);
        rb3.setFocusable(false);
        rb4.setEnabled(false);
        rb4.setFocusable(false);
        rb5.setEnabled(false);
        rb5.setFocusable(false);
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
                        Intent in = new Intent(UpdateInformation.this, IndexActivity.class);
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
                    Intent in = new Intent(UpdateInformation.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateInformation.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateInformation.this);
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

    public void UpdateFunction(final String raadz_user_id, final String token) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);
                Log.d("TIDupdate ", token);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("raadz_user_id", raadz_user_id));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(userDataURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(userDataURL);
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
                        try {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();

                            JSONArray jArray = new JSONArray(preferences.getString("info", ""));
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                if (jObj.getString("interest_value").equals("gender")) {
                                    gender = jObj.getString("interest_text");
                                    if (gender.equals("M")) {
                                        rgGender.check(rbMale.getId());
                                        rgGender.setEnabled(false);
                                    }
                                    if (gender.equals("F")) {
                                        rgGender.check(rbFemale.getId());
                                        rgGender.setEnabled(false);
                                        //rbFemale.setChecked(true);
                                    }

                                    Log.d("gender: ", jObj.getString("interest_text"));
                                }

                                if (jObj.getString("interest_value").equals("age")) {
                                    age = jObj.getInt("interest_text");
                                    if (age == 1) {
                                        rgAge.check(rb1.getId());
                                        if (rb1.isChecked()) {
                                            ageDeselect();
                                        }
                                    }
                                    if (age == 2) {
                                        rgAge.check(rb2.getId());
                                        if (rb2.isChecked()) {
                                            ageDeselect();
                                        }
                                    }
                                    if (age == 4) {
                                        rgAge.check(rb3.getId());
                                        if (rb3.isChecked()) {
                                            ageDeselect();
                                        }
                                    }
                                    if (age == 8) {
                                        rgAge.check(rb4.getId());
                                        if (rb4.isChecked()) {
                                            ageDeselect();
                                        }
                                    }
                                    if (age == 16) {
                                        rgAge.check(rb5.getId());
                                        if (rb5.isChecked()) {
                                            ageDeselect();
                                        }
                                    }
                                    Log.d("age: ", jObj.getString("interest_text"));
                                }


                                if (jObj.getString("interest_value").equals("location")) {
                                    if (jObj.getString("interest_text").length() < 3) {
                                        locationDefault = jObj.getString("interest_text");
                                        Log.d("location ", locationDefault);
                                    }
                                    if (jObj.getString("interest_text").length() > 3) {
                                        locationDefault = jObj.getString("interest_text");
                                        Log.d("location ", locationDefault);
                                        Log.d("locationInterest ", jObj.getString("interest_text"));
                                        sLocation.setFocusable(false);
                                        sLocation.setEnabled(false);
                                        sLocation.setClickable(false);

                                    }
                                }

                                if (jObj.getString("interest_value").equals("location-list")) {

                                    location = jObj.getString("interest_text");
                                    String kept = location.substring(0, location.indexOf(","));
                                    String remainder = location.substring(location.indexOf(":") + 1, location.length());
                                    List<String> list = new ArrayList<String>();
                                    list.add(locationDefault);

                                    for (int j = 0; j < 100000; j++) {
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


                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateInformation.this, android.R.layout.simple_spinner_item, list);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    int position = adapter.getPosition(locationDefault);
                                    Log.d("position ", String.valueOf(locationDefault));
                                    Log.d("position String ", locationDefault);
                                    sLocation.setAdapter(adapter);
                                    sLocation.setSelection(position);
                                }

                                if (jObj.getString("interest_value").equals("userinterests")) {
                                    Log.d("i: ", jObj.getString("interest_text"));
                                    String interestsStr = jObj.getString("interest_text");
                                    Log.d("interests: ", interestsStr);

                                    String[] arr = interestsStr.split(":");
                                    int arrLength = arr.length;
                                    for (int j = 0; j < arrLength; j++) {

                                        if (!arr[j].isEmpty()) {
                                            long temp = Long.parseLong(arr[j]);
                                            Log.d("Interests Value: ", String.valueOf(interests));
                                            interests = interests + temp;
                                            Log.d("Interests - Loop: ", String.valueOf(interests));
                                            Log.d("Array: ", arr[j]);
                                            interestsData.put(arr[j], j);
                                            interests_add[j] = Long.parseLong(arr[j]);
                                        }
                                        Log.d("arrArray ", String.valueOf(arr[j]));
                                    }

                                    for (int j = 0; j < interests_add.length; j++) {
                                        int_track = int_track + interests_add[j];
                                    }


                                    Log.d("Intersts - OL: ", String.valueOf(interests));

                                    Log.d("Collection: ", String.valueOf(interestsData.values()));

//                                    for (int j = 0; j < interests_array.length; j++) {
//                                        if (interestsData.containsKey(String.valueOf(interests_array[j]))) {
////                                            checkBox16.setChecked(true);
////                                            checkBox16.setEnabled(false);
//                                            interestsValue = interestsValue + 1;
//                                        }
//                                    }
//                                    if (interestsData.containsKey("16384")) {
//                                        checkBox15.setChecked(true);
//                                        checkBox15.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("8192")) {
//                                        checkBox14.setChecked(true);
//                                        checkBox14.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("4096")) {
//                                        checkBox13.setChecked(true);
//                                        checkBox13.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("2048")) {
//                                        checkBox12.setChecked(true);
//                                        checkBox12.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("1024")) {
//                                        checkBox11.setChecked(true);
//                                        checkBox11.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("512")) {
//                                        checkBox10.setChecked(true);
//                                        checkBox10.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("256")) {
//                                        checkBox9.setChecked(true);
//                                        checkBox9.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("128")) {
//                                        checkBox8.setChecked(true);
//                                        checkBox8.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("64")) {
//                                        checkBox7.setChecked(true);
//                                        checkBox7.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("32")) {
//                                        checkBox6.setChecked(true);
//                                        checkBox6.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("16")) {
//                                        checkBox5.setChecked(true);
//                                        checkBox5.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("8")) {
//                                        checkBox4.setChecked(true);
//                                        checkBox4.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("4")) {
//                                        checkBox3.setChecked(true);
//                                        checkBox3.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("2")) {
//                                        checkBox2.setChecked(true);
//                                        checkBox2.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//                                    if (interestsData.containsKey("1")) {
//                                        checkBox.setChecked(true);
//                                        checkBox.setEnabled(false);
//                                        interestsValue = interestsValue + 1;
//                                    }
//
//                                    Log.d("interests: ", interestsStr);

                                }
                                Log.d("i: ", String.valueOf(i));
                            }
                        } catch (org.json.JSONException e) {
                            System.out.println();
                        }
                    }
                });
//                getInteger();
                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("ResultUpdateFxn ", result);
//                if (result.equals("user not found")) {
//                    Log.d("echo 1: ", "user not found");
//                }
//                if (result.equals("invalid post data")) {
//                    Log.d("echo 2: ", "invalid post data");
//                } else {
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("info", result);
//                    editor.commit();
//                    Log.d("ResultSend: ", preferences.getString("info", ""));
//
//                    Log.d("bNext1 Result ", result);
//                    LLInterestCopy.removeAllViews();
//                    LLInterestCopy2.removeAllViews();
//                    try {
//                        JSONArray jArray = new JSONArray(result);
//                        for (int i = 0; i < jArray.length(); i++) {
//                            JSONObject jObj = jArray.getJSONObject(i);
//                            final CheckBox cb = new CheckBox(UpdateInformation.this);
//                            //if(!jObj.getJSONObject("quest_text").equals("null")) {
//                            Log.d("jObj q ", jObj.getString("interest_text"));
//                            if (jObj.getString("interest_text").equals("null")) {
//                                Log.d("in the if ", jObj.getString("interest_text"));
//                            } else {
//                                Log.d("InsideFor ", jObj.getString("interest_text"));
//                                cb.setText(jObj.getString("interest_text"));
//                                cb.setId(i + jArray.length());
//                                if (layout_track == false) {
//                                    LLInterestCopy.addView(cb);
//                                    ok = jArray.length();
//                                    layout_track = true;
//                                } else if (layout_track == true) {
//                                    layout_track = false;
//                                    LLInterestCopy2.addView(cb);
//                                    ok = jArray.length();
//                                }
////                        cb.setText(jObj.getString("interest_text"));
////                        cb.setId(i + jArray.length());
////                        LLInterestCopy.addView(cb);
////                        ok = jArray.length();
//                            }
//
//                            Log.d("cb length ", String.valueOf(cb.length()));
//
//                            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
//                                    int s = (Integer.parseInt(String.valueOf(buttonView.getId())) - ok) + 1;
//                                    Log.d("ok ", String.valueOf(ok));
//                                    Log.d("string s ", String.valueOf(s));
//
//
//                                    if (s >= 1) {
//                                        for (int j = 0; j < interests_array.length; j++) {
//                                            if (interestsData.containsKey(String.valueOf(interests_array[j]))) {
//                                                cb.setChecked(true);
//                                                cb.setEnabled(false);
//                                                interestsValue = interestsValue + 1;
//                                            }
//                                        }
//                                        if (b == true) {
//                                            Log.d("array_value ", String.valueOf(interests_array[s - 1]));
//                                            interests = interests + (int) interests_array[s - 1];
//                                            interestsValue = interestsValue + 1;
//                                            Log.d("interestCounter ", String.valueOf(interests));
//                                            Log.d("interestsValue ", String.valueOf(interestsValue));
//                                        } else {
//                                            interests = interests - (int) interests_array[s - 1];
//                                            interestsValue = interestsValue - 1;
//                                            Log.d("interestCounter ", String.valueOf(interests));
//                                            Log.d("interestsValue ", String.valueOf(interestsValue));
//                                        }
//                                    }
//                                }
//                            });
//                            //}
//                        }
//                    } catch (org.json.JSONException e) {
//                        System.out.println();
//                    }
//
//                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(raadz_user_id, token);
    }

    public void InterestsFunction() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d("UIDupdate ", raadz_user_id);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                try {

                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(InterestsListURL);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(InterestsListURL);
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
                Log.d("ResultInterestsFxn ", result);
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
                    Log.d("ResultSend: ", preferences.getString("info", ""));

                    Log.d("bNext1 Result ", result);
                    LLInterestCopy.removeAllViews();
                    LLInterestCopy2.removeAllViews();
                    try {
                        JSONArray jArray = new JSONArray(result);

                        for (int a_i = 0; a_i < jArray.length(); a_i++) {
                            JSONObject jObj = jArray.getJSONObject(a_i);
                            try {
                                Log.d("a_i ", jObj.getString("interest_value"));
                                interest_number_array[a_i] = Long.parseLong(jObj.getString("interest_value"));
                            }catch (NumberFormatException e){

                            }
                        }

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            CheckBox cb = new CheckBox(UpdateInformation.this);
                            //if(!jObj.getJSONObject("quest_text").equals("null")) {
//                            Log.d("jObj q ", jObj.getString("interest_text"));
                            if (jObj.getString("interest_text").equals("null")) {
                                Log.d("in the if ", jObj.getString("interest_text"));
                            } else {

//                                Log.d("Interest_text ", jObj.getString("interest_text"));
//                                Log.d("Interest_value ", jObj.getString("interest_value"));

                                cb.setText(jObj.getString("interest_text"));
                                cb.setId(i);

                                Log.d(" - ", " - ");
                                Log.d("preCheckID ", String.valueOf(i));
                                Log.d("preCheckPlace ", String.valueOf(i));
                                Log.d("preCheckValue ", String.valueOf(interest_number_array[i]));
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
                                for (int y = 0; y < interests_add.length; y++) {
                                    for (int o = 0; o < interest_number_array.length; o++) {
                                        if (interest_number_array[o] == interests_add[y]) {
                                            int id = cb.getId();
                                            if(id == o){
                                                cb.setChecked(true);
                                                cb.setEnabled(false);
                                                interestsValue = interestsValue + 1;
                                            }
//                                            Log.d("intersts_array ", String.valueOf(interest_number_array[o]));
                                        }
                                    }
                                }
                            }


//                            Log.d("cb length ", String.valueOf(cb.length()));

                            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                    int s = (Integer.parseInt(String.valueOf(buttonView.getId()))) + 1;
                                    Log.d("ok ", String.valueOf(ok));
                                    Log.d("string s ", String.valueOf(s));
                                    Log.d("interests ", String.valueOf(interests));
                                    Log.d(" - ", " - ");
                                    Log.d("checkLength ", String.valueOf(buttonView.getId()));
                                    Log.d("checkLong ", String.valueOf(interest_number_array[buttonView.getId()]));
                                    Log.d(" - ", " - ");

                                    if (s >= 1) {
                                        if (b == true) {
//                                            Log.d("array_value ", String.valueOf(interests_array[s - 1]));
//                                            interests = interests + (int) interests_array[s - 1];
                                            interests = interests + interest_number_array[buttonView.getId()];
                                            interestsValue = interestsValue + 1;
                                            Log.d("interestCounter ", String.valueOf(interests));
                                            Log.d("interestsValue ", String.valueOf(interestsValue));
                                        } else {
//                                            interests = interests - (int) interests_array[s - 1];
                                            interests = interests - interest_number_array[buttonView.getId()];
                                            interestsValue = interestsValue - 1;
                                            Log.d("interestCounter ", String.valueOf(interests));
                                            Log.d("interestsValue ", String.valueOf(interestsValue));
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
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void processFinish(String s) {
        Log.d("DefaultProcess ", s);
        if (buttonPlaceholder.equals("bSubmitRequest")) {
            Log.d("bSubmitRequest ", s);
            if (s.equals("request updated")) {
                Toast.makeText(this, "Your request has been updated", Toast.LENGTH_SHORT).show();
                LLRequest.setVisibility(View.GONE);
                bRequest.setVisibility(View.VISIBLE);
            }
            if (s.equals("request added")) {
                Toast.makeText(this, "Your request has been added", Toast.LENGTH_SHORT).show();
                LLRequest.setVisibility(View.GONE);
                bRequest.setVisibility(View.VISIBLE);
            }
            if (s.equals("user not found")) {
                Toast.makeText(this, "User not found, try again later", Toast.LENGTH_SHORT).show();
                LLRequest.setVisibility(View.GONE);
                bRequest.setVisibility(View.VISIBLE);
            }
            if (s.equals("invalid data")) {
                Toast.makeText(this, "Invalid data.  Fill out all forms", Toast.LENGTH_SHORT).show();
                LLRequest.setVisibility(View.GONE);
                bRequest.setVisibility(View.VISIBLE);
            }
        }
        if (buttonPlaceholder.equals("bSave")) {
            if (s.contains("user info updated")){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(UpdateInformation.this, AdsHistory.class);
                startActivity(in);
            }else{
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }
        }
            else {

            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            Log.d("process ", String.valueOf(s));
            reload = 1;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("reload", reload);
            editor.commit();

            Toast.makeText(this, preferences.getString("raadz_user_id", ""), Toast.LENGTH_SHORT).show();
            Intent in = new Intent(this, AdsHistory.class);
            startActivity(in);
        }
    }

    @Override
    public void onClick(View v) {
        interestsValue = interestsValue;
        if (v == bSave) {
            buttonPlaceholder = "bSave";
            if (interestsValue > 20) {
                Log.d("interestsValue ", String.valueOf(interestsValue));
                Toast.makeText(this, "Maximum number of interests allowed: 20", Toast.LENGTH_SHORT).show();
            } else if (interestsValue < 1) {
                Log.d("interestsValue ", String.valueOf(interestsValue));
                Toast.makeText(this, "Minimum number of interests allowed: 1", Toast.LENGTH_SHORT).show();
            } else if (age < 1) {
                Toast.makeText(this, "Select an Age", Toast.LENGTH_SHORT).show();
            } else if (gender != "F" && gender != "M") {
                Toast.makeText(this, "Select a gender", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("interestsValue ", String.valueOf(interestsValue));
                HashMap postData = new HashMap();
                Log.d("UID ", raadz_user_id);
                Log.d("TID ", raadzToken);
                Log.d("age ", String.valueOf(age));
                Log.d("location ", String.valueOf(locationDefault));
                Log.d("interests ", String.valueOf(interests));
                Log.d("gender ", gender);

                postData.put("raadz_user_id", raadz_user_id);
                postData.put("token", raadzToken);
                postData.put("gender", gender);
                postData.put("age", String.valueOf(age));
                postData.put("location", String.valueOf(locationDefault));
                postData.put("interests", String.valueOf(interests));
                Log.d("Interests - Save ", String.valueOf(interests));
                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute(updateUserURL);
            }
        }
        if (v == bProfile) {
            Intent in = new Intent(UpdateInformation.this, AdsHistory.class);
            startActivity(in);
        }

        if (v == bSubmitRequest) {
            if (request_change == 0) {
                Toast.makeText(this, "Please select at least one checkbox to file request.", Toast.LENGTH_SHORT).show();
            }
            if (etMessageRequest.getText().toString().length() < 5) {
                Toast.makeText(this, "Please specify a message of at least 5 characters.", Toast.LENGTH_SHORT).show();
            }
            if (request_change > 0 && etMessageRequest.getText().toString().length() >= 5) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                buttonPlaceholder = "bSubmitRequest";
                Log.d("TAG ", "Inside the request_change if");
                Log.d("TAG ", preferences.getString("raadz_user_id", ""));
                Log.d("TAG ", preferences.getString("token", ""));
                Log.d("TAG ", String.valueOf(request_change));
                Log.d("TAG ", etMessageRequest.getText().toString());
                HashMap postData = new HashMap();
                postData.put("raadz_user_id", preferences.getString("raadz_user_id", ""));
                postData.put("token", preferences.getString("token", ""));
                postData.put("change_what", String.valueOf(request_change));
                postData.put("reason", etMessageRequest.getText().toString());
                PostResponseAsyncTask task = new PostResponseAsyncTask(UpdateInformation.this, postData);
                task.execute(RequestDataChangeURL);
            }
        }
    }
}
