package com.example.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class colors extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener monAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                        // mediaController.getTransportControls().pause();
                        // Wait 30 seconds before stopping playback
                        //   handler.postDelayed(delayedStopRunnable,
                        //       TimeUnit.SECONDS.toMillis(30));
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                        // Pause playback
                        // media
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                        // Lower the volume, keep playing
                    }

                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        setContentView(R.layout.activity_numberactivity);
        ArrayList<WORDS> num=new ArrayList<WORDS>();
        // num.add("zero");
        // WORDS W1=("")
        num.add(new WORDS("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        num.add(new WORDS("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        num.add(new WORDS("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        num.add(new WORDS("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        num.add(new WORDS("black", "kululli", R.drawable.color_black, R.raw.color_black));
        num.add(new WORDS("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        num.add(new WORDS("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        num.add(new WORDS("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
       LinearLayout root=(LinearLayout) findViewById(R.id.root);
        ListView list=(ListView)findViewById(R.id.list_item);
        wordadapter itemadapter=new wordadapter(this,num,R.color.purple_200);
        list.setAdapter(itemadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WORDS cwords=num.get(position);
                int result = mAudioManager.requestAudioFocus(monAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    // mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);

                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(colors.this, cwords.getAudioid());
                    mMediaPlayer.start(); // no need to call prepare(); create() does that for you
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(monAudioFocusChangeListener);
        }
    }
}