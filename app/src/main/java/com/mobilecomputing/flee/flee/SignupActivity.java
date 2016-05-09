package com.mobilecomputing.flee.flee;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends Activity {

    private TextView mfirstName;
    private TextView mLastName;
    private TextView mUsername;
    private TextView mPassword;
    private Button mFinishButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/FedraSansStd-Book.ttf");

        mfirstName = (TextView) findViewById(R.id.firstNameText);
        mLastName = (TextView) findViewById(R.id.lastNameText);
        mUsername = (TextView) findViewById(R.id.userText);
        mPassword = (TextView) findViewById(R.id.passwordText);
        mFinishButton = (Button) findViewById(R.id.finishButton);

        mfirstName.setTypeface(font);
        mLastName.setTypeface(font);
        mUsername.setTypeface(font);
        mFinishButton.setTypeface(font);
        mPassword.setTypeface(font);

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupActivity.this, SettingsActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
