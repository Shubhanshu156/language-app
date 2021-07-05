package com.example.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.sax.TextElementListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class numberactivity extends AppCompatActivity {
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
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numberactivity);
       final ArrayList<WORDS> num=new ArrayList<WORDS>();
       mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE) ;

       // num.add("zero");
       // WORDS W1=("")
        num.add(new WORDS("one", "lutti", R.drawable.number_one, R.raw.number_one));
        num.add(new WORDS("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        num.add(new WORDS("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        num.add(new WORDS("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        num.add(new WORDS("five", "massokka", R.drawable.number_five, R.raw.number_five));
        num.add(new WORDS("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        num.add(new WORDS("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        num.add(new WORDS("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        num.add(new WORDS("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        num.add(new WORDS("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));
        LinearLayout root=(LinearLayout) findViewById(R.id.root);
        ListView list=(ListView)findViewById(R.id.list_item);
        wordadapter itemadapter=new wordadapter(this,num,R.color.purple_700);
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
                    mMediaPlayer = MediaPlayer.create(numberactivity.this, cwords.getAudioid());
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
       // AudioManager.abandonAudioFocus(monAudioFocusChangeListener);
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