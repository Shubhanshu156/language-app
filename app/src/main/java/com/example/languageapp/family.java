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

public class family extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener monAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
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
        setContentView(R.layout.activity_family);
        ArrayList<WORDS> num=new ArrayList<WORDS>();
        // num.add("zero");
        // WORDS W1=("")
        num.add(new WORDS("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        num.add(new WORDS("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        num.add(new WORDS("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        num.add(new WORDS("father","әpә",R.drawable.family_father,R.raw.family_father));
        num.add(new WORDS("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        num.add(new WORDS("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        num.add(new WORDS("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        num.add(new WORDS("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        num.add(new WORDS("son","angsi",R.drawable.family_son,R.raw.family_son));
        num.add(new WORDS("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
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
                    mMediaPlayer = MediaPlayer.create(family.this, cwords.getAudioid());
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