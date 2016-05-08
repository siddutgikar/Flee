package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

//created by apyryt on 5/7/16

public class AddActivity extends Activity implements View.OnClickListener {

    private final static int SELECT_PHOTO = 12345;

    private EditText name, location, date, time, category, description;
    private TextView nameLabel, locationLabel, dateLabel, timeLabel, categoryLabel, descrLabel, addEventLabel;
    private ImageButton uploadImage, done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        //create the custom font
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/FedraSansStd-Bold.ttf");

        //initialize textViews
        addEventLabel = (TextView) findViewById(R.id.textViewAddEvent);
        nameLabel = (TextView) findViewById(R.id.nameTextField);
        locationLabel = (TextView) findViewById(R.id.locationTextField);
        dateLabel = (TextView) findViewById(R.id.dateTextField);
        timeLabel = (TextView) findViewById(R.id.timeTextField);
        categoryLabel = (TextView) findViewById(R.id.categoryTextField);
        descrLabel = (TextView) findViewById(R.id.descrTextField);

        //setting the font for the editTexts
        addEventLabel.setTypeface(font);
        nameLabel.setTypeface(font);
        locationLabel.setTypeface(font);
        dateLabel.setTypeface(font);
        timeLabel.setTypeface(font);
        categoryLabel.setTypeface(font);
        descrLabel.setTypeface(font);

        //Just initializes the TextViews and buttons to be used for creating an event
        name = (EditText) findViewById(R.id.nameTextField);
        location = (EditText) findViewById(R.id.dateTextField);
        date = (EditText) findViewById(R.id.timeTextField);
        time = (EditText) findViewById(R.id.timeTextField);
        category = (EditText) findViewById(R.id.categoryTextField);
        description = (EditText) findViewById(R.id.descrTextField);

        uploadImage = (ImageButton) findViewById(R.id.uploadImageButton);
        uploadImage.setOnClickListener(this);

        done = (ImageButton) findViewById(R.id.doneButton);
        done.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //select a photo from the android photo album
            case R.id.uploadImageButton:
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, SELECT_PHOTO);
                break;

            //create an event to add to the database
            case R.id.doneButton:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri image = data.getData();


        }
    }
}
