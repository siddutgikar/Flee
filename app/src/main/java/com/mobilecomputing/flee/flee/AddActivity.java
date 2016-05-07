package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//created by apyryt on 5/7/16

public class AddActivity extends Activity implements View.OnClickListener {

    TextView name, location, date, time, category, description;
    Button uploadImage, done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Just initializes the TextViews and buttons to be used for creating an event
        name = (TextView) findViewById(R.id.nameTextField);
        location = (TextView) findViewById(R.id.dateTextField);
        date = (TextView) findViewById(R.id.timeTextField);
        time = (TextView) findViewById(R.id.timeTextField);
        category = (TextView) findViewById(R.id.categoryTextField);
        description = (TextView) findViewById(R.id.descriptionText);

        uploadImage = (Button) findViewById(R.id.uploadImageButton);
        uploadImage.setOnClickListener(this);

        done = (Button) findViewById(R.id.doneButton);
        done.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //go to a select picture screen
            case R.id.uploadImageButton:
                break;

            //create an event to add to the database
            case R.id.doneButton:
                break;
        }
    }
}
