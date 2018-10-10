package com.raadz.program.raadzandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class ForgotPasswordActivity extends AppCompatActivity implements AsyncResponse, View.OnClickListener, DialogInterface {

    View view;

    Button bSend;
    Button bResend;
    Button bBack;

    EditText etEmail;

    TextView tvLogin;
    TextView textView;

    BufferedReader bufferedReader;
    OutputStream outputStream;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();

    int count = 0;

    String forgotPasswordURL = "https://raadz.com/forgotPassword.php";

    String Result;
    String httpRequest;
    String buttonPlaceholder;

    int place = 0;

    public static final String UserEmail = "";
    public static final String data = "{\"user_id\":{\"put_id\":\"token\",\"salary\":56000}}";

    HashMap<String, String> hashMap = new HashMap<>();
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("https://raadz.com/mobilelogin.php");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("facebookLogin", "false");
        editor.commit();
        textView = (TextView) findViewById(R.id.textView);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        bResend = (Button) findViewById(R.id.bResend);
        bSend = (Button) findViewById(R.id.bSend);
        bBack = (Button) findViewById(R.id.bBack);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setText(preferences.getString("fpEmail", ""));
        if (preferences.getString("fpEmail", "").length() > 0) {
            etEmail.setText(preferences.getString("fpEmail", ""));
        }

        if (preferences.getString("fpResult", "").equals("resend reset email allowed")) {
            bSend.setText("Resend Email");
        }
        if (preferences.getString("fpResult", "").equals("check email to reset")) {
            bSend.setBackgroundColor(Color.parseColor("#a5a5a5"));
            bSend.setFocusable(false);
        }


        etEmail.clearFocus();

        bSend.setOnClickListener(this);

        bResend.setOnClickListener(this);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("rEmail", etEmail.getText().toString());
        edit.commit();
        hideKeyboard(view);
        if (view == bSend) {
            place = 1;
            buttonPlaceholder = "bLogin";
            HashMap postData = new HashMap();
            postData.put("email", etEmail.getText().toString());
            postData.put("resend", "0");
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute(forgotPasswordURL);
        }
        if(view == bResend){
            Toast.makeText(this, "Email has been resent", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(in);
        }
    }

    @Override
    public void processFinish(String s) {
        if(place == 1) {
            if (s.equals("resend reset email allowed")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                bResend.setVisibility(View.VISIBLE);
                bSend.setVisibility(View.GONE);
            } else if (s.equals("check email to reset")) {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                bSend.setBackgroundColor(Color.parseColor("#a5a5a5"));
                bSend.setFocusable(false);
            } else {
                s = s.replace("\n", "").replace("\r", "");
                Log.d("result ", s);
                Toast.makeText(ForgotPasswordActivity.this, s, Toast.LENGTH_LONG).show();
                Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(in);
            }
        }
    }

    public void UpdateFunction(final String email, final String resend) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("resend", resend));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(forgotPasswordURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(forgotPasswordURL);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        String temp = preferences.getString("fpEmail", "");
                    }
                });

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("fpResult", result);
                edit.commit();
                super.onPostExecute(result);
                if (result.equals("resend reset email allowed")) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            place = 1;
                        }
                    });
                    place = 1;
                } else if (result.equals("check email to reset")) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            place = 2;
                        }
                    });
                    place = 2;
                } else {
                    result = result.replace("\n", "").replace("\r", "");
                    Log.d("result ", result);
                    Toast.makeText(ForgotPasswordActivity.this, result, Toast.LENGTH_LONG).show();
                    Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    startActivity(in);
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(email, resend);
    }
}