package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mobilecomputing.flee.flee.utils.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Created by siddh on 4/22/2016.
 * Last modified by asen on 5/17/16.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {

    ToggleButton tb11, tb12, tb13, tb14, tb15, tb16;
    ToggleButton tb21, tb22, tb23, tb24, tb25, tb26;
    ToggleButton tb31, tb32, tb33, tb34, tb35, tb36;
    TextView tvSelect, tvLocation, tvDistance, tvSeekbar;
    EditText etLocation;
    Button btnFinish;
    String progressString;          // Distance in miles

    Typeface fedraSansStdBook;      // Custom font
    SharedPreferences sharedPreferences;
    String categoriesID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // hide keyboard if visible on load
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tb11 = (ToggleButton) findViewById(R.id.toggleCustomized11);
        tb12 = (ToggleButton) findViewById(R.id.toggleCustomized12);
        tb13 = (ToggleButton) findViewById(R.id.toggleCustomized13);
        tb14 = (ToggleButton) findViewById(R.id.toggleCustomized14);
        tb15 = (ToggleButton) findViewById(R.id.toggleCustomized15);
        tb16 = (ToggleButton) findViewById(R.id.toggleCustomized16);
        tb21 = (ToggleButton) findViewById(R.id.toggleCustomized21);
        tb22 = (ToggleButton) findViewById(R.id.toggleCustomized22);
        tb23 = (ToggleButton) findViewById(R.id.toggleCustomized23);
        tb24 = (ToggleButton) findViewById(R.id.toggleCustomized24);
        tb25 = (ToggleButton) findViewById(R.id.toggleCustomized25);
        tb26 = (ToggleButton) findViewById(R.id.toggleCustomized26);
        tb31 = (ToggleButton) findViewById(R.id.toggleCustomized31);
        tb32 = (ToggleButton) findViewById(R.id.toggleCustomized32);
        tb33 = (ToggleButton) findViewById(R.id.toggleCustomized33);
        tb34 = (ToggleButton) findViewById(R.id.toggleCustomized34);
        tb35 = (ToggleButton) findViewById(R.id.toggleCustomized35);
        tb36 = (ToggleButton) findViewById(R.id.toggleCustomized36);

        etLocation = (EditText) findViewById(R.id.editTextLocation);

        tvSelect = (TextView) findViewById(R.id.textViewSelect);
        tvLocation = (TextView) findViewById(R.id.textViewLocation);
        tvDistance = (TextView) findViewById(R.id.textViewDistance);
        tvSeekbar = (TextView) findViewById(R.id.textViewSeekbar);

        // custom font for textView and buttons
        fedraSansStdBook = Typeface.createFromAsset(this.getAssets(), "fonts/FedraSansStd-Book.ttf");
        // shared preference file to save the user's favorite categories, location and distance
        sharedPreferences = getSharedPreferences(Constants.CATEGORY_PREFS, Context.MODE_PRIVATE);

        btnFinish = (Button) findViewById(R.id.finishButtonCategory);
        btnFinish.setOnClickListener(this);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        // change the default yellow color of the seek bar to purple
        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.parseColor("#754C9A"), PorterDuff.Mode.SRC));
        // set initial progress
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        // set the maximum possible value making it accept only 5 values
        seekBar.setMax(4);
        final TextView seekBarValue = (TextView) findViewById(R.id.textViewSeekbar);
        // apply the custom font
        seekBarValue.setTypeface(fedraSansStdBook);
        tvLocation.setTypeface(fedraSansStdBook);
        etLocation.setTypeface(fedraSansStdBook);
        tvSeekbar.setTypeface(fedraSansStdBook);
        tvDistance.setTypeface(fedraSansStdBook);
        tvSelect.setTypeface(fedraSansStdBook);
        // update textView associated with the seek bar
        seekBarValue.setText("10 miles");
        // seek bar can only be 0 - 4, but we want 5, 10, 15, 20 and 25 miles as values selected
        progressString = String.valueOf(seekBar.getProgress() * 5 + 5);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress >= 1 && progress <= seekBar.getMax()) {
                        // update value when seek bar is changed
                        progressString = String.valueOf(progress * 5 + 5);
                        // update textView accordingly
                        seekBarValue.setText(progressString + " miles"); // the TextView Reference
                        seekBar.setSecondaryProgress(progress);
                    } else {
                        seekBarValue.setText("5 miles"); // the TextView Reference
                        seekBar.setSecondaryProgress(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // if the user previously selected preferences
        // update the screen accordingly
        resetSelectedPreferences();

    }

    /**
     * ADDED BY SIDDHARTH
     */
    private void resetSelectedPreferences() {
        try {
            String resSet = sharedPreferences.getString(Constants.CATEGORY, "");

            if (resSet != null) {
                String arrCatID[] = resSet.split(",");
                for (int i = 0; i < arrCatID.length; i++) {
                    String catID = arrCatID[i];
                    if ("32".equals(catID)) {
                        tb11.setChecked(true);
                    } else if ("21".equals(catID)) {
                        tb12.setChecked(true);
                    } else if ("2".equals(catID)) {
                        tb13.setChecked(true);
                    } else if ("28".equals(catID)) {
                        tb14.setChecked(true);
                    } else if ("11".equals(catID)) {
                        tb15.setChecked(true);
                    } else if ("9".equals(catID)) {
                        tb16.setChecked(true);
                    } else if ("10".equals(catID)) {
                        tb21.setChecked(true);
                    } else if ("26".equals(catID)) {
                        tb22.setChecked(true);
                    } else if ("20".equals(catID)) {
                        tb23.setChecked(true);
                    } else if ("31".equals(catID)) {
                        tb24.setChecked(true);
                    } else if ("6".equals(catID)) {
                        tb25.setChecked(true);
                    } else if ("25".equals(catID)) {
                        tb26.setChecked(true);
                    } else if ("13".equals(catID)) {
                        tb31.setChecked(true);
                    } else if ("4".equals(catID)) {
                        tb32.setChecked(true);
                    } else if ("23".equals(catID)) {
                        tb33.setChecked(true);
                    } else if ("8".equals(catID)) {
                        tb34.setChecked(true);
                    } else if ("15".equals(catID)) {
                        tb35.setChecked(true);
                    } else if ("26".equals(catID)) {
                        tb36.setChecked(true);
                    }

                }

            }
            String locationString = sharedPreferences.getString(Constants.LOCATION, "");
            etLocation.setText(locationString);
            String seekValue = sharedPreferences.getString(Constants.DISTANCE, "");
            tvDistance.setText(seekValue);

        } catch (Exception ex) {
            Log.e("Reset prefs", ex.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.finishButtonCategory:

                categoriesID = "";

                if (tb11.isChecked()) {
                    categoriesID = categoriesID + ",32";
                }
                if (tb12.isChecked()) {
                    categoriesID = categoriesID + ",21";
                }
                if (tb13.isChecked()) {
                    categoriesID = categoriesID + ",2";
                }
                if (tb14.isChecked()) {
                    categoriesID = categoriesID + ",28";
                }
                if (tb15.isChecked()) {
                    categoriesID = categoriesID + ",11";
                }
                if (tb16.isChecked()) {
                    categoriesID = categoriesID + ",9";
                }
                if (tb21.isChecked()) {
                    categoriesID = categoriesID + ",10";
                }
                if (tb22.isChecked()) {
                    categoriesID = categoriesID + ",26";
                }
                if (tb23.isChecked()) {
                    categoriesID = categoriesID + ",20";
                }
                if (tb24.isChecked()) {
                    categoriesID = categoriesID + ",31";
                }
                if (tb25.isChecked()) {
                    categoriesID = categoriesID + ",6";
                }
                if (tb26.isChecked()) {
                    categoriesID = categoriesID + ",25";
                }
                if (tb31.isChecked()) {
                    categoriesID = categoriesID + ",13";
                }
                if (tb32.isChecked()) {
                    categoriesID = categoriesID + ",4";
                }
                if (tb33.isChecked()) {
                    categoriesID = categoriesID + ",23";
                }
                if (tb34.isChecked()) {
                    categoriesID = categoriesID + ",8";
                }
                if (tb35.isChecked()) {
                    categoriesID = categoriesID + ",15";
                }
                if (tb36.isChecked()) {
                    categoriesID = categoriesID + ",26";
                }

                // store the categories string in the preferences file
                SharedPreferences.Editor edit = sharedPreferences.edit();
                if (categoriesID != null && !categoriesID.isEmpty()) {
                    edit.putString(Constants.CATEGORY, categoriesID);
                    edit.commit();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select at least one Category.", Toast.LENGTH_LONG).show();
                    break;
                }

                String location = etLocation.getText().toString();

                if (location != null && !location.isEmpty()) {
                    Geocoder gc = new Geocoder(this);
                    List<Address> list = null;
                    try {
                        // Geocoder returns a list of addresses that match the text search.
                        // We only want the first one and we only store
                        // zip code, latitude and longitude from it.
                        list = gc.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (list != null && list.size() > 0) {
                        Address add = list.get(0);
                        if (add != null) {
                            edit.putString(Constants.LOCATION, location);
                            String postalCode = add.getPostalCode();
                            if (postalCode != null && !postalCode.isEmpty()) {
                                edit.putString(Constants.ZIPCODE, postalCode);
                            } else {
                                edit.putString(Constants.ZIPCODE, "");
                            }
                            Double latitude = add.getLatitude();
                            Double longitude = add.getLongitude();
                            edit.putString(Constants.LATITUDE, latitude.toString());
                            edit.putString(Constants.LONGITUDE, longitude.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Location not found. Please try again.", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Location not found. Please try again.", Toast.LENGTH_LONG).show();
                        break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter location.", Toast.LENGTH_LONG).show();
                    break;
                }

                edit.putString(Constants.DISTANCE, progressString);
                edit.commit();

                // if we have all information we need start the next activity
                Intent eventActivityIntent = new Intent(this, EventListActivity.class);
                startActivity(eventActivityIntent);
                this.finish();
                break;
        }
    }
}
