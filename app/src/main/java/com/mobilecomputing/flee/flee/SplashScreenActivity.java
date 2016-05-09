package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by siddh on 4/22/2016.
 * Edited by apyryt on 5/4/16
 */
public class SplashScreenActivity extends Activity implements MediaPlayer.OnCompletionListener {

    VideoView splash;

    @Override
    public void onCreate(Bundle savedInstanceState){

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            //loads the video file for the splashscreen
            splash = (VideoView) findViewById(R.id.videoView);
            Uri splashPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.flee1);
            splash.setVideoURI(splashPath);
            splash.start();
            splash.setOnCompletionListener(this);
        }
        catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        try {
            //When the splash screen finishes playing, start the next activity (login activity)
            Intent startAppIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(startAppIntent);

            finish();
        }
        catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }
    }
}
