package com.mobilecomputing.flee.flee;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;

/**
 * Created by siddh on 4/22/2016.
 * Last modified by asen on 5/8/16.
 */
public class SettingsActivity extends Activity implements View.OnClickListener{

    ToggleButton tb36 = null;

    Button btnFinish;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnFinish=(Button)findViewById(R.id.finishButtonCategory);
        btnFinish.setOnClickListener(this);
        tb36 = (ToggleButton) findViewById(R.id.toggleCustomized35);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(4);
        final TextView seekBarValue = (TextView) findViewById(R.id.textViewSeekbar);
        seekBarValue.setText("10 miles");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress >= 1 && progress <= seekBar.getMax()) {

                        String progressString = String.valueOf(progress * 5 + 5);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void search(View v) throws IOException {
        hideSoftKeyboard(v);



        Toast.makeText(getApplicationContext(), tb36.isChecked() + "", Toast.LENGTH_LONG).show();

        EditText etLocation = (EditText) findViewById(R.id.editTextLocation);
        String location = etLocation.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);

        Double latitude = add.getLatitude();
        Double longitude = add.getLongitude();

        Toast.makeText(getApplicationContext(), latitude + " ; " + longitude, Toast.LENGTH_LONG).show();
    }

    private void hideSoftKeyboard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.finishButtonCategory:
                Toast.makeText(SettingsActivity.this,tb36.isChecked()+"",Toast.LENGTH_LONG).show();
                break;
        }

    }
}
