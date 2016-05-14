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
 * Last modified by asen on 5/9/16.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {

    ToggleButton tb11, tb12, tb13, tb14, tb15, tb16;
    ToggleButton tb21, tb22, tb23, tb24, tb25, tb26;
    ToggleButton tb31, tb32, tb33, tb34, tb35, tb36;
    TextView tvSelect, tvLocation, tvDistance, tvSeekbar;
    EditText etLocation;
    Button btnFinish;
    String progressString;          // Distance in miles

    Typeface fedraSansStdBook;
    SharedPreferences sharedPreferences;
    String categoriesID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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


        fedraSansStdBook = Typeface.createFromAsset(this.getAssets(), "fonts/FedraSansStd-Book.ttf");
        sharedPreferences = getSharedPreferences(Constants.CATEGORY_PREFS, Context.MODE_PRIVATE);

        btnFinish = (Button) findViewById(R.id.finishButtonCategory);
        btnFinish.setOnClickListener(this);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.parseColor("#754C9A"), PorterDuff.Mode.SRC));
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(4);
        final TextView seekBarValue = (TextView) findViewById(R.id.textViewSeekbar);
        seekBarValue.setTypeface(fedraSansStdBook);
        tvLocation.setTypeface(fedraSansStdBook);
        etLocation.setTypeface(fedraSansStdBook);
        tvSeekbar.setTypeface(fedraSansStdBook);
        tvDistance.setTypeface(fedraSansStdBook);
        tvSelect.setTypeface(fedraSansStdBook);
        seekBarValue.setText("10 miles");
        progressString = String.valueOf(seekBar.getProgress() * 5 + 5);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress >= 1 && progress <= seekBar.getMax()) {

                        progressString = String.valueOf(progress * 5 + 5);

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

                String[] categoryIDs = getResources().getStringArray(R.array.categoryIDArray);

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
                            // Toast.makeText(getApplicationContext(), latitude.toString(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), longitude.toString(), Toast.LENGTH_LONG).show();
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

                Intent eventActivityIntent = new Intent(this, EventListActivity.class);
                startActivity(eventActivityIntent);
                this.finish();
                break;
        }

    }
}
