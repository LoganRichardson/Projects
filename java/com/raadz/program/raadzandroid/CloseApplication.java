package com.raadz.program.raadzandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.raadz.program.raadzandroid.R.id.bCancel;
import static com.raadz.program.raadzandroid.R.id.bConfirmLogout;

/**
 * Created by RaadzDesk1 on 1/9/2018.
 */

public class CloseApplication extends AppCompatActivity {
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_confirm);
        con = this;
    }

    public void forceClose(Context context) {

        Intent in = new Intent(Intent.ACTION_MAIN);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(in);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
