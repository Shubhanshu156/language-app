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

public class phrases extends AppCompatActivity {

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
        setContentView(R.layout.activity_phrases);
        setContentView(R.layout.activity_numberactivity);
        ArrayList<WORDS> num=new ArrayList<WORDS>();
        // num.add("zero");
        // WORDS W1=("")
       // num.add(new WORDS("how","hello",123)))
        num.add(new WORDS("What is your name.","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        num.add(new WORDS("my name is","oyaaset...",R.raw.phrase_my_name_is));
        num.add(new WORDS("are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        num.add(new WORDS("yes i am coming","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        num.add(new WORDS("come here","әnni'nem",R.raw.phrase_come_here));
        num.add(new WORDS("i am coming","әәnәm",R.raw.phrase_im_coming));
        num.add(new WORDS("how are you feeling","michәksәs?’.",R.raw.phrase_how_are_you_feeling));
        num.add(new WORDS("I am feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        num.add(new WORDS("lets go","yoowutis",R.raw.phrase_lets_go));
        num.add(new WORDS("Where are you going","minto wuksus",R.raw.phrase_where_are_you_going));
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
                    mMediaPlayer = MediaPlayer.create(phrases.this, cwords.getAudioid());
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