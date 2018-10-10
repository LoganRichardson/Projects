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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.raadz.program.raadzandroid.R.id.p_leaderboards;

public class PreContactUsActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface, NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;

    Button bContact;

    EditText etName;
    EditText etEmail;
    EditText etMessage;

    String contactUsURL = "https://raadz.com/ContactUs.php";
    String balance;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_contact_us);

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

        bContact.setOnClickListener(this);

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
            Intent in = new Intent(this, PreAboutUsActivity.class);
            startActivity(in);
        }else if (id == R.id.p_home) {
            Intent in = new Intent(this, IndexActivity.class);
            startActivity(in);
        }else if (id == R.id.p_advertise) {
            Intent in = new Intent(this, PreAdvertiseActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_terms) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PreContactUsActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PreContactUsActivity.this);
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
        Intent in = new Intent(PreContactUsActivity.this, FragPubActivity.class);
        startActivity(in);
    }
}
