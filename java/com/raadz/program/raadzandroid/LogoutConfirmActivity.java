package com.raadz.program.raadzandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static android.R.id.edit;


public class LogoutConfirmActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    SignInButton bGoogleSignIn;

    LoginButton bFacebookLogin;
    CallbackManager callbackManager;
    Button bCancel;
    Button bConfirmLogout;

    private static final int REQ_CODE = 9001;
    private static final String REQ_TOKEN = GoogleConfig.getApiKey();

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public interface LogoutFacebookListener {

        void onLoggedOutFromFacebook();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_confirm);
        bCancel = (Button) findViewById(R.id.bCancel);
        bConfirmLogout = (Button) findViewById(R.id.bConfirmLogout);
        callbackManager = CallbackManager.Factory.create();


        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (preferences.getString("raadz_pub_id", "").length() > 5) {
                    Intent in = new Intent(LogoutConfirmActivity.this, FragPubActivity.class);
                    startActivity(in);
                } else if (preferences.getString("raadz_pub_id", "").length() < 5) {
                    Intent in = new Intent(LogoutConfirmActivity.this, FragActivity.class);
                    startActivity(in);
                }
            }
        });

        bConfirmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences1.edit();
                SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ADeditor = ADPreferences.edit();
                SharedPreferences i_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor i_edit = i_preferences.edit();
                preferences.edit().remove("email").commit();
                preferences.edit().remove("password").commit();
                preferences.edit().remove("raadz_user_id").commit();
                preferences.edit().remove("raadz_pub_id").commit();
                preferences.edit().remove("token").commit();
                preferences.edit().remove("result");
                preferences.edit().remove("httpRequest").commit();
                preferences.edit().remove("money").commit();
                preferences.edit().remove("balance").commit();
                ADPreferences.edit().clear();
                preferences.edit().clear();
                preferences1.edit().clear();
                i_preferences.edit().clear();

//                onStart();
//                signOut();

                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor e = p.edit();

                e.putString("google_sign_out", "1");
                e.commit();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(LogoutConfirmActivity.this, IndexActivity.class);
                startActivity(in);
            }
        });
    }


    public void logoutConfirm(Context context) {

        //disconnectFromFacebook();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences ADPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ADeditor = ADPreferences.edit();
        SharedPreferences preferencesU = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editU = preferencesU.edit();
        preferences.edit().remove("email").commit();
        preferences.edit().remove("password").commit();
        preferences.edit().remove("raadz_user_id").commit();
        preferences.edit().remove("raadz_pub_id").commit();
        preferences.edit().remove("token").commit();
        preferences.edit().remove("result");
        preferences.edit().remove("httpRequest").commit();
        preferences.edit().remove("money").commit();
        preferences.edit().remove("balance").commit();
        ADPreferences.edit().clear();
        preferences.edit().clear();
        preferences.edit().clear().commit();
        ADPreferences.edit().clear().commit();
        editU.clear().commit();

//        onStart();
//        signOut();

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = p.edit();

        e.putString("google_sign_out", "1");
        e.commit();

        LoginManager.getInstance().logOut();

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(REQ_TOKEN)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();
        super.onStart();
    }


    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),LogoutConfirmActivity.class);
                        startActivity(i);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        }
    }

    public void signIn() {
        Intent in = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(in, REQ_CODE);
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

    public void cancelConfirm() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (preferences.getString("raadz_pub_id", "").length() > 5) {
            Intent in = new Intent(LogoutConfirmActivity.this, FragPubActivity.class);
            startActivity(in);
        } else if (preferences.getString("raadz_pub_id", "").length() < 5) {
            Intent in = new Intent(LogoutConfirmActivity.this, FragActivity.class);
            startActivity(in);
        }
    }

}
