package com.raadz.program.raadzandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;

import static com.raadz.program.raadzandroid.R.id.wvLoad;

public class AboutUsActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;

    ImageButton ibProfile;

    WebView wvAbout1;
    WebView wvAbout2;
    WebView wvAbout3;

    TextView tvMoney;

    String balance;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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

        wvAbout1.loadUrl("https://raadz.com/about_us_works.html");
        wvAbout2.loadUrl("https://raadz.com/about_us_story.html");
        wvAbout3.loadUrl("https://raadz.com/about_us_team.html");

        tvMoney.setText("$" + balance);

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AboutUsActivity.this, AdsHistory.class);
                startActivity(in);
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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutUsActivity.this);
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
                        Intent in = new Intent(AboutUsActivity.this, IndexActivity.class);
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
        }else if (id == R.id.user_rate) {
            Intent in = new Intent(this, PickAdActivity.class);
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

        }else if (id == R.id.user_contact) {
            Intent in = new Intent(this, ContactUsActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_logout){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutUsActivity.this);
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
                    Intent in = new Intent(AboutUsActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutUsActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutUsActivity.this);
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

    }
}
