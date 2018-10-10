package com.raadz.program.raadzandroid;

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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.raadz.program.raadzandroid.R.id.LL1;
import static com.raadz.program.raadzandroid.R.id.adv_home;
import static com.raadz.program.raadzandroid.R.id.wvAbout1;

public class PubTutorialActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;

    WebView wvLoad;

    ImageButton ibProfile;

    LinearLayout LL2;
    LinearLayout LL3;
    LinearLayout LL4;
    LinearLayout LL5;
    LinearLayout LL6;

    TextView tvMoney;
    TextView tvStep;
    TextView tvVideo;
    TextView tvImage;
    TextView tvAudio;

    Button bNext2;
    Button bNext3;
    Button bNext4;
    Button bNext5;
    Button bNext6;

    Button bBack2;
    Button bBack3;
    Button bBack4;
    Button bBack5;

    String AdvertiserDataURL = "https://raadz.com/getPubInfo.php";
    String httpRequest;
    String info;
    String balance;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_tutorial);

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

        wvLoad = (WebView) findViewById(R.id.wvLoad);

        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvStep = (TextView) findViewById(R.id.tvStep);
        tvVideo = (TextView) findViewById(R.id.tvVideo);
        tvImage = (TextView) findViewById(R.id.tvImage);
        tvAudio = (TextView) findViewById(R.id.tvAudio);

        LL2 = (LinearLayout) findViewById(R.id.LL2);
        LL3 = (LinearLayout) findViewById(R.id.LL3);
        LL4 = (LinearLayout) findViewById(R.id.LL4);
        LL5 = (LinearLayout) findViewById(R.id.LL5);
        LL6 = (LinearLayout) findViewById(R.id.LL6);

        bNext2 = (Button) findViewById(R.id.bNext2);
        bNext3 = (Button) findViewById(R.id.bNext3);
        bNext4 = (Button) findViewById(R.id.bNext4);
        bNext5 = (Button) findViewById(R.id.bNext5);
        bNext6 = (Button) findViewById(R.id.bNext6);

        bBack2 = (Button) findViewById(R.id.bBack2);
        bBack3 = (Button) findViewById(R.id.bBack3);
        bBack4 = (Button) findViewById(R.id.bBack4);
        bBack5 = (Button) findViewById(R.id.bBack5);

        SpannableString content1 = new SpannableString("Video:");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvVideo.setText(content1);

        SpannableString content2 = new SpannableString("Image:");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvImage.setText(content2);

        SpannableString content3 = new SpannableString("Audio:");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tvAudio.setText(content3);

        wvLoad.loadUrl("https://raadz.com/user_tutorial.html");

        String t_temp = String.valueOf(wvLoad);

        Log.d("t_temp ", t_temp);

        bNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL2.setVisibility(View.GONE);
                LL3.setVisibility(View.VISIBLE);
                tvStep.setText("Step 2");
            }
        });

        bNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL3.setVisibility(View.GONE);
                LL4.setVisibility(View.VISIBLE);
                tvStep.setText("Step 3");
            }
        });

        bNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL4.setVisibility(View.GONE);
                LL5.setVisibility(View.VISIBLE);
                tvStep.setText("Step 4");
            }
        });

        bNext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL5.setVisibility(View.GONE);
                LL6.setVisibility(View.VISIBLE);
                tvStep.setText("Step 5");
            }
        });

        bNext6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PubTutorialActivity.this, FragActivity.class);
                startActivity(in);
            }
        });

        bBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL3.setVisibility(View.GONE);
                LL2.setVisibility(View.VISIBLE);
                tvStep.setText("Step 1");
            }
        });

        bBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL4.setVisibility(View.GONE);
                LL3.setVisibility(View.VISIBLE);
                tvStep.setText("Step 2");
            }
        });

        bBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL5.setVisibility(View.GONE);
                LL4.setVisibility(View.VISIBLE);
                tvStep.setText("Step 3");
            }
        });

        bBack5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL6.setVisibility(View.GONE);
                LL5.setVisibility(View.VISIBLE);
                tvStep.setText("Step 4");
            }
        });

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PubTutorialActivity.this, PubProfileActivity.class);
                startActivity(in);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        balance = preferences.getString("cash", "");
        tvMoney.setText("$" + balance);
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
        switch (item.getItemId()) {
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
                        Intent in = new Intent(PubTutorialActivity.this, IndexActivity.class);
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
        } else if (id == R.id.adv_about) {
            Intent in = new Intent(this, PubAboutUsActivity.class);
            startActivity(in);
        } else if (id == R.id.adv_contact) {
            Intent in = new Intent(this, PubContactUsActivity.class);
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
                    Intent in = new Intent(PubTutorialActivity.this, IndexActivity.class);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubTutorialActivity.this);
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PubTutorialActivity.this);
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
}