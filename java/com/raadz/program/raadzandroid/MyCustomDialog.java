package com.raadz.program.raadzandroid;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.raadz.program.raadzandroid.R.id.LLSeekbars;
import static com.raadz.program.raadzandroid.R.id.bPause;
import static com.raadz.program.raadzandroid.R.id.bPlay;
import static com.raadz.program.raadzandroid.R.id.bYTPlay;
import static com.raadz.program.raadzandroid.R.id.etVideoID;
import static com.raadz.program.raadzandroid.R.id.ivImage;
import static com.raadz.program.raadzandroid.R.id.newYTPlayer;
import static com.raadz.program.raadzandroid.R.id.tvStatus;
import static com.raadz.program.raadzandroid.R.id.view;

/**
 * Created by RaadzDesk1 on 4/20/2018.
 */

public class MyCustomDialog extends DialogFragment {
    private FragmentActivity myContext;

    YouTubePlayer player;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    final MediaPlayer mp = new MediaPlayer();

    private Button mDone;
    private Button mVideo;
    private Button mImage;
    private Button mAudio;
    private Button mPlay;
    private Button mPause;

    private TextView mAd;
    private TextView mLink;
    private TextView mCompany;
    private TextView mStatus;

    private LinearLayout mLayout;
    private LinearLayout mAudioLayout;

    ImageView imgImage;

    String azureAudio = "https://raadzcloud.blob.core.windows.net/audios/";
    String azureImages = "https://raadzcloud.blob.core.windows.net/images/";
    String bothTime;

    int currentTime;
    int totalTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_my_custom, container, false);
        final Bundle mArgs = getArguments();

        mAudioLayout = view.findViewById(R.id.LLAudio);

        imgImage = view.findViewById(R.id.imgImage);

        mAd = view.findViewById(R.id.tvAd);
        mLink = view.findViewById(R.id.tvLink);
        mCompany = view.findViewById(R.id.tvCompany);
        mStatus = view.findViewById(R.id.tvStatus);

        mDone = view.findViewById(R.id.bDone);
        mVideo = view.findViewById(R.id.bVideo);
        mImage = view.findViewById(R.id.bBanner);
        mAudio = view.findViewById(R.id.bAudio);
        mPlay = view.findViewById(R.id.bPlay);
        mPause = view.findViewById(R.id.bPause);

        mAd.setText(mArgs.getString("title"));
        mLink.setText(mArgs.getString("link"));
        mCompany.setText(mArgs.getString("company"));

        YouTubePlayerView yt = (YouTubePlayerView) view.findViewById(R.id.newYTPlayer);

        if (mArgs.getString("type").equals("1")) {
            mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                    Log.d("test", "inside initizedlistener");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    final String youtubeURL = mArgs.getString("ref");
                    player = youTubePlayer;
                    playVideo(youtubeURL);


                    player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {
                        }

                        @Override
                        public void onLoaded(String s) {
                            player.play();
                            Log.d("Duration ", String.valueOf(player.getDurationMillis()));
                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {
                            //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        }

                        @Override
                        public void onVideoEnded() {
                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {
                        }
                    });

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            };

            yt.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
            yt.setVisibility(View.VISIBLE);
            mVideo.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);
            mAudio.setVisibility(View.GONE);
        }

        if (mArgs.getString("type").equals("2")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);
            mAudio.setVisibility(View.GONE);

            Log.d("link ", azureImages);
            Log.d("path ", mArgs.getString("ref"));
            String imgPath = azureImages + mArgs.getString("ref");
            Picasso.with(getApplicationContext()).load(imgPath).into(imgImage);
            imgImage.setVisibility(View.VISIBLE);
        }

        if (mArgs.getString("type").equals("3")) {
            yt.setVisibility(View.GONE);
            mVideo.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mAudio.setVisibility(View.VISIBLE);

            mAudioLayout.setVisibility(View.VISIBLE);
            audioPlayer(azureAudio, mArgs.getString("ref"));
        }

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    public void playVideo(String url) {
        if (player != null) {
            player.cueVideo(url);
            Log.d("url ", url);
            //player.play();
        }
    }

    public void audioPlayer(final String path, final String fileName) {
        try {
            mp.reset();
            mp.setDataSource(path + fileName);
            mp.prepare();
            mp.start();

            Log.d("file ", fileName);

            Runnable runnable = new Runnable() {
                public void run() {
                    while (mp.isPlaying()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        totalTime = mp.getDuration() / 1000;
                        currentTime = mp.getCurrentPosition() / 1000;

                        Log.d("current ", String.valueOf(currentTime));
                        Log.d("total ", String.valueOf(totalTime));

                        bothTime = "0:" + String.valueOf(currentTime) + " | " + "0:" + String.valueOf(totalTime);

                        mStatus.setText("Status: " + bothTime);
                    }
                }
            };
            new Thread(runnable).start();


        } catch (Exception e) {
            System.out.println();
        }

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


}