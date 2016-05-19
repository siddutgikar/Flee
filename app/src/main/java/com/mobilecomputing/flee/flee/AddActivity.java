package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

//created by apyryt on 5/7/16
// modified on 5/18/16

public class AddActivity extends Activity implements View.OnClickListener {

    private final static int SELECT_PHOTO = 12345;

    private EditText name, location, date, time, category, description;
    private TextView nameLabel, locationLabel, dateLabel, timeLabel, categoryLabel, descrLabel, addEventLabel;
    private ImageButton uploadImage, done, backButton;

    private Uri selectedImage;
    private WebView openURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_event);

            //create the custom font
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/FedraSansStd-Bold.ttf");
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
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

			//initializes buttons
            uploadImage = (ImageButton) findViewById(R.id.uploadImageButton);
            uploadImage.setOnClickListener(this);

            done = (ImageButton) findViewById(R.id.doneButton);
            done.setOnClickListener(this);

            backButton = (ImageButton) findViewById(R.id.backButton);
            backButton.setOnClickListener(this);

            //initializes the WebView - used for opening the URL for the PHP script
            openURL = (WebView) findViewById(R.id.web);
            openURL.getSettings().setJavaScriptEnabled(true);
            openURL.setWebViewClient(new WebViewClient());


        } catch (Exception e) {
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

                    //connect to the database and add the event
                    String newEvent = URLEncoder.encode("eventName", "UTF-8") + "=" + URLEncoder.encode(name.getText().toString(), "UTF-8") + "&";
                    newEvent += URLEncoder.encode("eventLocation", "UTF-8") + "=" + URLEncoder.encode(location.getText().toString(), "UTF-8") + "&";
                    newEvent += URLEncoder.encode("eventDate", "UTF-8") + "=" + URLEncoder.encode(date.getText().toString(), "UTF-8") + "&";
                    newEvent += URLEncoder.encode("eventTime", "UTF-8") + "=" + URLEncoder.encode(time.getText().toString(), "UTF-8") + "&";
                    newEvent += URLEncoder.encode("eventCategory", "UTF-8") + "=" + URLEncoder.encode(category.getText().toString(), "UTF-8") + "&";
                    newEvent += URLEncoder.encode("eventDescription", "UTF-8") + "=" + URLEncoder.encode(description.getText().toString(), "UTF-8");

                    //calls the PHP script with the user-entered fields in the WebView
                    openURL.loadUrl("http://mpss.csce.uark.edu/~team1/add_to_table.php?" + newEvent);
                    Toast messageDoneToast = Toast.makeText(AddActivity.this, "Event has been added!", Toast.LENGTH_LONG);
                    messageDoneToast.show();

					//waits a few seconds until the script has posted the events to the database
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    //starts the event Activity after the PHP field is finished
                                    Intent eventListing = new Intent(AddActivity.this, EventListActivity.class);
                                    startActivity(eventListing);
                                }
                            }
                            , 2000);
                    break;
            }
        } catch (Exception e) {
            Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
            notification.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //records the URI for the selected image
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
		
			try {

                selectedImage = data.getData();

                //set the imageButton as the image selected by the user
                //   uploadImage.setImageURI(null);
                //   uploadImage.setImageURI(selectedImage);
            
			} 
			catch (Exception e) {
				Toast notification = Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG);
				notification.show();
			}
		}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }

        return super.onOptionsItemSelected(item);

    }
}