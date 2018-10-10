package com.raadz.program.raadzandroid;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.kosalgeek.asynctask.AsyncResponse;
import com.stripe.android.view.CardMultilineWidget;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import static android.R.attr.tag;
import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;
import static com.raadz.program.raadzandroid.PubSubmitActivity.getFileNameByUri;
import static com.raadz.program.raadzandroid.R.id.LLMain;
import static com.raadz.program.raadzandroid.R.id.bYTPlay;
import static com.raadz.program.raadzandroid.R.id.etVideoID;
import static com.raadz.program.raadzandroid.R.id.newYTPlayer;
import static com.raadz.program.raadzandroid.R.id.videoView;
import static com.raadz.program.raadzandroid.R.id.wvLoad;

public class _TestActivity extends YouTubeBaseActivity implements AsyncResponse, View.OnClickListener, DialogInterface, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "_TestActivity";
    private Button mOpenDialog;


    BufferedReader bufferedReader;
    OutputStream outputStream;
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
    String httpRequest;

    GoogleApiClient googleApiClient;

    View view;

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayerView NewYouTubePlayerView;
    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    YouTubePlayerFragment myYouTubePlayerFragment;

    WebView wvWeb;

    VideoView vvVideo;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    ImageView ivImage;

    LinearLayout LLButtons;
    LinearLayout LLLogout;
    LinearLayout LLShare;

    Button bPlay;
    Button bTest;
    Button bLogout;
    Button bShare;

    SignInButton bGoogleSignIn;
    SignInButton bGoogleSignUp;

    String ServerLoginURL = "https://raadz.com/mobilelogin.php";
    String RaddzIDURL = "https://raadz.com/getRaadzIDs.php";
    String GoogleUserSignUpURL = "https://raadz.com/googusersignup.php";
    String buttonPlaceholder;
    String userResponseToken;
    String UID;
    String PID;
    String UserToken;
    String PubToken;
    String fName;
    String lName;
    String email;
    String username;
    String id;
    String token;
    String InOut;
    String str;

    int PICK_VIDEO = 100;
    int PICK_IMAGE = 100;
    int PICK_AUDIO = 100;

    boolean updateUI;

    public static final String data = "{\"user_id\":{\"put_id\":\"token\",\"salary\":56000}}";
    public static final String SITE_KEY = "6LcVrUwUAAAAAL2J1zasONEiiru3Lgg4z9bMxxLJ";
    public static final String SECRET_KEY = "6LcVrUwUAAAAADmA4tnNYUmPu8rcOcOBF1IxTt0d";
    private static final int REQ_CODE = 9001;
    private static final String REQ_TOKEN = "1088961401873-969414qur5dh7ied7ailbajgcst9t5ou.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___test);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(newYTPlayer);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        LLButtons = (LinearLayout) findViewById(R.id.LLButtons);
        LLLogout = (LinearLayout) findViewById(R.id.LLLogout);
        LLShare = (LinearLayout) findViewById(R.id.LLShare);

        bTest = (Button) findViewById(R.id.bTest);
        bPlay = (Button) findViewById(R.id.bPlay);
        bLogout = (Button) findViewById(R.id.bLogout);
        bShare = (Button) findViewById(R.id.bShare);

        bGoogleSignIn = (SignInButton) findViewById(R.id.bGoogleSignIn);
        bGoogleSignUp = (SignInButton) findViewById(R.id.bGoogleSignUp);
        mOpenDialog = findViewById(R.id.bShare);



        GoogleSignInOptions signInOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(REQ_TOKEN).build();

        ImageView img = (ImageView) findViewById(R.id.ivImage);
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/sample-1.jpg");
        img.setImageBitmap(bitmap);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomDialog dialog = new MyCustomDialog();
                dialog.show(getFragmentManager(), "LeaderboardActivityUser");
            }
        });

//        bShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = YouTubeStandalonePlayer.createVideoIntent(_TestActivity.this, YouTubeConfig.getApiKey(), "yVqFow3sNOY");
//                startActivity(intent);
//            }
//        });

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(_TestActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.terms_layout, null);
                Button mCancel = (Button) mView.findViewById(R.id.bCancel);
                WebView wvLoad = (WebView) mView.findViewById(R.id.wvLoad);
                wvLoad.loadUrl("https://raadz.com/privacy.html");
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        bGoogleSignIn.setOnClickListener(this);

        bGoogleSignUp.setOnClickListener(this);

        bLogout.setOnClickListener(this);

        bTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

//        bPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_PICK);
//                intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_AUDIO);
//            }
//        });


}




    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
            //player.play();
        }
    }

    public String fetchContent(String http) {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost postalcall = new HttpPost(http);
            HttpResponse response = client.execute(postalcall);


            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                str = EntityUtils.toString(response.getEntity());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return str;

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Log ", "Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("Log ", "Portrait");
        }
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(_TestActivity.this, "We've signed out", Toast.LENGTH_SHORT).show();
                        updateUI(false);
                    }
                });
    }

    public void signIn() {
        Intent in = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(in, REQ_CODE);

    }

    public void updateUI(boolean SI) {
        if (SI == true) {
            Log.d("updateUI ", String.valueOf(SI));
            LLButtons.setVisibility(View.GONE);
            LLLogout.setVisibility(View.VISIBLE);
        }
        if (SI == false) {
            Log.d("updateUI ", String.valueOf(SI));
            LLLogout.setVisibility(View.GONE);
            LLButtons.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void handleResult(final GoogleSignInResult handleResult) {

        if (InOut.equals("in")) {
            if (handleResult.isSuccess()) {
                Log.d("InHandleResult ", "1");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                final GoogleSignInAccount account = handleResult.getSignInAccount();

                fName = account.getGivenName();
                lName = account.getFamilyName();
                email = account.getEmail();
                username = account.getDisplayName();
                id = account.getId();
                token = handleResult.getSignInAccount().getIdToken();

                Log.d("Googlefname ", fName);
                Log.d("Googlelname ", lName);
                Log.d("Googleemail ", email);
                Log.d("Googlefusername ", username);
                Log.d("Googleid ", id);
                Log.d("Googletoken ", token);
                updateUI(true);
                Log.d("Login" + " - ", "Success");

                GoogleFunction(id, token);

            } else {
                updateUI(false);
                Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (InOut.equals("out")) {
            if (handleResult.isSuccess()) {
                Log.d("InHandleResult ", "1");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                final GoogleSignInAccount account = handleResult.getSignInAccount();

                fName = account.getGivenName();
                lName = account.getFamilyName();
                email = account.getEmail();
                username = account.getDisplayName();
                id = account.getId();
                token = handleResult.getSignInAccount().getIdToken();

                Log.d("Googlefname ", fName);
                Log.d("Googlelname ", lName);
                Log.d("Googleemail ", email);
                Log.d("Googlefusername ", username);
                Log.d("Googleid ", id);
                Log.d("Googletoken ", token);
                updateUI(true);
                Log.d("Login" + " - ", "Success");

                GoogleSignUp(fName, lName, email, "", id, token);

            } else {
                updateUI(false);
                Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View view) {
        if (view == bGoogleSignIn) {
            InOut = "in";
            buttonPlaceholder = "bGoogleSignIn";
            signIn();
        }
        if (view == bGoogleSignUp) {
            InOut = "out";
            buttonPlaceholder = "bGoogleSignUp";
            signIn();
        }
        if (view == bLogout) {
            signOut();
        }
    }

    @Override
    public void processFinish(String s) {
        Log.d("GoogleOutput ", s);
    }


    public void GoogleSignUp(final String first, final String last, final String email, final String referrer, final String google_id, final String google_token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("first_name", first));
                nameValuePairs.add(new BasicNameValuePair("last_name", last));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("referrer", referrer));
                nameValuePairs.add(new BasicNameValuePair("user_id", google_id));
                nameValuePairs.add(new BasicNameValuePair("goog_access_token", google_token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RaddzIDURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RaddzIDURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);

                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line = "";
                    String LineSeparator = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator);
                        //Log.d("stringBuffer: ", stringBuffer.toString());
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("httpRequest Google ", httpRequest);
                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("google_information", httpRequest);
                        edit.commit();
                    }
                });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("user_data").apply();
                edit.putString("user_data", httpRequest);
                edit.commit();
                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                Log.d("PostResult ", result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(google_id, google_token);
    }


    public void GoogleFunction(final String google_id, final String google_token) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("google_user_id", google_id));
                nameValuePairs.add(new BasicNameValuePair("google_access_token", google_token));

                try {
                    httpRequest = "";
                    bufferedReader = null;
                    stringBuffer = new java.lang.StringBuffer();
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(RaddzIDURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpPost request = new HttpPost(RaddzIDURL);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    request.setEntity(entity);

                    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line = "";
                    String LineSeparator = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line + LineSeparator);
                        //Log.d("stringBuffer: ", stringBuffer.toString());
                    }
                    httpRequest = stringBuffer.toString();
                    bufferedReader.close();

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                Log.d("httpRequest Google ", httpRequest);
                runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("google_information", httpRequest);
                        edit.commit();
                    }
                });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("user_data").apply();
                edit.putString("user_data", httpRequest);
                edit.commit();
                return httpRequest;
            }

            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = preferences.edit();
                if (!result.equals("invalid access token") && !result.equals("invalid data")) {
                    Log.d("GoogleResult ", result);
                    Log.d("GoogleID ", preferences.getString("google_id", ""));
                    Log.d("GoogleToken ", preferences.getString("google_token", ""));
                    String[] parts = result.split(":");
                    for (int i = 0; i < parts.length; i++) {
                        Log.d("-", "-");
                        Log.d("parts ", parts[i]);
                        Log.d("count ", String.valueOf(i));
                        Log.d("-", "-");
                        if (i == 0 && parts[0].length() > 1) {
                            UID = parts[0];
                        } else if (i == 0 && parts[0].length() < 8) {
                            UID = "x";
                        }

                        if (i == 1 && parts[1].length() > 1) {
                            PID = parts[1];
                        } else if (i == 1 && parts[1].length() < 8) {
                            PID = "x";
                        }

                        if (i == 2 && parts[2].length() > 1) {
                            UserToken = parts[2];
                        } else if (i == 2 && parts[2].length() < 8) {
                            UserToken = "x";
                        }

                        if (i == 3 && parts[3].length() > 8) {
                            PubToken = parts[3];
                        } else if (i == 3 && parts[3].length() < 8) {
                            PubToken = "x";
                        }
                    }

                    Log.d("-", "-");
                    Log.d("UID", UID);
                    Log.d("PID", PID);
                    Log.d("UserToken", UserToken);
                    Log.d("PubToken", PubToken);
                    Log.d("FullResult ", result);
                    Log.d("-", "-");

                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(google_id, google_token);
    }

    public void HTMLFunction(final String http) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                try
                {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost postalcall = new HttpPost(http);
                    HttpResponse response = client.execute(postalcall);


                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                    {
                        str = EntityUtils.toString(response.getEntity());
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }

                return httpRequest;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("ActiveAds ", result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(http);
    }
//
//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

//
//    public static String getFileNameByUri(Context context, Uri uri)
//    {
//        String fileName="unknown";//default fileName
//        Uri filePathUri = uri;
//        if (uri.getScheme().toString().compareTo("content")==0)
//        {
//            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
//            if (cursor.moveToFirst())
//            {
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
//                filePathUri = Uri.parse(cursor.getString(column_index));
//                fileName = filePathUri.getLastPathSegment().toString();
//                Log.d("fileName ", fileName);
//            }
//        }
//        else if (uri.getScheme().compareTo("file")==0)
//        {
//            fileName = filePathUri.getLastPathSegment().toString();
//            Log.d("fileName ", fileName);
//        }
//        else
//        {
//            fileName = fileName+"_"+filePathUri.getLastPathSegment();
//            Log.d("fileName ", fileName);
//        }
//        return fileName;
//    }


//    public String getRealPathFromURI(Uri uri) {
//        String[] projection = {MediaStore.Video.Media.DATA};
//        @SuppressWarnings("deprecation")
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//
//    private String getVideoIdFromFilePath(String filePath,
//                                        ContentResolver contentResolver) {
//        long videoId;
//        Log.d(TAG, "Loading file " + filePath);
//        Uri videosUri = MediaStore.Audio.Media.getContentUri("external");
//        Log.d(TAG, "videosUri = " + videosUri.toString());
//        String[] projection = {MediaStore.Video.VideoColumns._ID};
//        // TODO This will break if we have no matching item in the MediaStore.
//        String newID = videosUri.getPath();
//        Log.d(TAG, "Video ID is " + newID);
//        return newID;
//    }


}

