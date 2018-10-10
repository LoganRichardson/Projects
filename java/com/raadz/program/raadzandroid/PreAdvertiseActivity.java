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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.raadz.program.raadzandroid.R.id.p_leaderboards;
import static com.raadz.program.raadzandroid.R.id.wvAbout1;
import static com.raadz.program.raadzandroid.R.id.wvAbout2;
import static com.raadz.program.raadzandroid.R.id.wvAbout3;

public class PreAdvertiseActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;

    WebView wvAdvertise;

    TextView tvMoney;

    String balance;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_advertise);

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

        wvAdvertise = (WebView) findViewById(R.id.wvAdvertise);

        wvAdvertise.loadUrl("https://raadz.com/why_advertise.html");

    }

    @Override
    public void onBackPressed() {

        Intent in = new Intent(this, IndexActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == p_leaderboards) {
            Intent in = new Intent(this, PreLeaderboardsActivity.class);
            startActivity(in);
        }else if (id == R.id.p_nav_about) {
            Intent in = new Intent(this, PreAdvertiseActivity.class);
            startActivity(in);
        }else if (id == R.id.p_nav_contact) {
            Intent in = new Intent(this, PreContactUsActivity.class);
            startActivity(in);
        }else if (id == R.id.p_home) {
            Intent in = new Intent(this, IndexActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PreAdvertiseActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PreAdvertiseActivity.this);
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
