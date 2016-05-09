package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.StateSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

//created by apyryt on 5/7/16

public class AddActivity extends Activity implements View.OnClickListener {

    private final static int SELECT_PHOTO = 12345;

    private EditText name, location, date, time, category, description;
    private TextView nameLabel, locationLabel, dateLabel, timeLabel, categoryLabel, descrLabel, addEventLabel;
    private ImageButton uploadImage, done, backButton;

    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
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

            backButton = (ImageButton) findViewById(R.id.backButton);
            backButton.setOnClickListener(this);
        }
        catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }
    }


    @Override
    public void onClick(View v) {

        try {

            switch (v.getId()) {

                //go back to the previous activity
                case R.id.backButton:
                    finish();
                    break;

                //select a photo from the android photo album
                case R.id.uploadImageButton:
                    Intent photoPicker = new Intent(Intent.ACTION_PICK);
                    photoPicker.setType("image/*");
                    startActivityForResult(photoPicker, SELECT_PHOTO);
                    break;

                //create an event to add to the database
                case R.id.doneButton:

                    //gets the information the user entered in
                    String retrievedName = name.getText().toString();
                    String retrievedLocation = location.getText().toString();
                    String retrievedDate = date.getText().toString();
                    String retrievedTime = time.getText().toString();
                    String retrievedCategory = category.getText().toString();
                    String retrievedDescr = description.getText().toString();

                    //add the event to the list of events...

                    Toast notification = Toast.makeText(this, "Event has been added", Toast.LENGTH_LONG);
                    notification.show();

                    //go to the EventList activity when the new event has been created
                    //Intent eventListIntent = new Intent(this, EventListActivity.class);
                    //eventListIntent.putExtra();
                    //startActivity(eventListIntent);
                    finish();
                    break;
            }
        }
        catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            super.onActivityResult(requestCode, resultCode, data);

            //records the URI for the selected image
            if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {

                selectedImage = data.getData();

                //set the imageButton as the image selected by the user
                //   uploadImage.setImageURI(null);
                //   uploadImage.setImageURI(selectedImage);
            }
        }
        catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }
    }
}