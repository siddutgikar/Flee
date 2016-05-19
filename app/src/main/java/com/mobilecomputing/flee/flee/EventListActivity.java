package com.mobilecomputing.flee.flee;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilecomputing.flee.flee.data.EventBean;
import com.mobilecomputing.flee.flee.fragments.EventDetailsFragment;
import com.mobilecomputing.flee.flee.fragments.EventListFragment;
import com.mobilecomputing.flee.flee.fragments.MapDispFragment;
import com.mobilecomputing.flee.flee.utils.Constants;
import com.mobilecomputing.flee.flee.utils.JsonResponseParser;
import com.mobilecomputing.flee.flee.utils.Utilities;
import com.mobilecomputing.flee.flee.utils.WebServiceHelper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

/**
 * Created by siddh on 4/22/2016.
 */
public class EventListActivity extends FragmentActivity implements View.OnClickListener, Animation.AnimationListener, AdapterView.OnItemSelectedListener, EventListFragment.EventListInterface, MapDispFragment.MapFragmentInterface {

    /**
     * Flag to decide when search panel can be seen
     */
    private boolean isSearchPanelVisible = false, isAnimating = false;
    /**
     * Animations for sliding of Search Panel and Fragments
     */
    TranslateAnimation transIn_Right;
    TranslateAnimation transOut_Right;

    TranslateAnimation transIn_Left;
    TranslateAnimation transOut_Left;

    /**
     * Search Panel Layout
     */
    LinearLayout lnSearchLayout;

    /**
     * Spinner for Category
     */
    Spinner spnCategory;

    /**
     * Edit text for search by Date
     */
    TextView edDate;

    /**
     * Edit text for search by Location
     */
    EditText edLocation;
    /**
     * Button Objects for Search and Clear for the Search Panel
     */
    Button btnSearch, btnClear;
    /**
     * Map Display Fragment to access the Map Fragment Methods
     */
    MapDispFragment mapDispFragment;

    /**
     * Event List Fragment to access the Fragment Method
     */
    EventListFragment eventListFragment;

    /**
     * Event Details Fragment to access the Fragment Method
     */
    EventDetailsFragment eventDetailsFragment;

    /**
     * URL String will be used to prepare URL at every request
     */
    String url = "";

    /**
     * THe response string will hold the Json
     */
    String responseString = "";

    /**
     * Response Code will be to show the
     */
    int responseCode = 0;

    /**
     * Frame Layout for loading the Detail Screen fragment
     */
    FrameLayout detailsLayout;

    /**
     * The layout
     */
    LinearLayout lnFrameContainer;

    /**
     *
     */
    ProgressBar progressBar;

    /**
     * Date picker Dialog for Search Date
     */
    DatePickerDialog.OnDateSetListener fromDatepickerdialog;

    /**
     *
     */
    private Calendar calendar = Calendar.getInstance();

    /**
     * Preferences object to access Shared Preference values
     */
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        initView();

        EventListTask getEventTask = new EventListTask();
        getEventTask.execute();


    }

    /**
     * Will initialise all the UI elements
     */
    private void initView() {

        sharedPreferences = getSharedPreferences(Constants.CATEGORY_PREFS, Context.MODE_PRIVATE);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        lnFrameContainer = (LinearLayout) findViewById(R.id.frame_Containers);
        detailsLayout = (FrameLayout) findViewById(R.id.frame_Details);
        lnSearchLayout = (LinearLayout) findViewById(R.id.searchPanelLayout);
        edLocation = (EditText) findViewById(R.id.srch_Location);
        edDate = (TextView) findViewById(R.id.srch_Date);
        spnCategory = (Spinner) findViewById(R.id.srch_Category);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        eventListFragment = new EventListFragment();
        mapDispFragment = new MapDispFragment();
        eventDetailsFragment = new EventDetailsFragment();
        edDate.setOnClickListener(this);
        progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);
        btnSearch.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        fromDatepickerdialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                edDate.setText(new StringBuilder().append((month + 1) + "/")
                        .append(day + "/").append(year));
            }
        };


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_eventList, eventListFragment);
        transaction.add(R.id.frame_Details, eventDetailsFragment);
        //transaction.replace(R.id.frame_Content, mapDispFragment);
        transaction.commit();
        FragmentManager fm = getSupportFragmentManager();
        mapDispFragment = new MapDispFragment();
        fm.beginTransaction().replace(R.id.frame_Content, mapDispFragment).commit();
        DisplayMetrics metrics = this.getResources()
                .getDisplayMetrics();
        int width = metrics.widthPixels;
        transIn_Right = new TranslateAnimation(width, 0, 0, 0);
        transIn_Right.setAnimationListener(this);
        transIn_Right.setDuration(500);
        transOut_Right = new TranslateAnimation(0, width, 0, 0);
        transOut_Right.setAnimationListener(this);
        transOut_Right.setDuration(500);

        transIn_Left = new TranslateAnimation(0, -width, 0, 0);
        transIn_Left.setAnimationListener(this);
        transIn_Left.setDuration(500);
        transOut_Left = new TranslateAnimation(-width, 0, 0, 0);
        transOut_Left.setAnimationListener(this);
        transOut_Left.setDuration(500);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eventlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * WIll animate the panel in and out
     *
     * @param direction will have 1 - for coming in 2 - for going out
     */
    public void animatePanel(int direction) {
        switch (direction) {
            case 1:
                if (detailsLayout.getVisibility() == View.VISIBLE) {
                    animatePanel(4);
                }
                lnSearchLayout.bringToFront();
                lnSearchLayout.clearAnimation();
                lnSearchLayout.setAnimation(transIn_Right);
                lnSearchLayout.startAnimation(transIn_Right);
                lnSearchLayout.setVisibility(View.VISIBLE);
                isSearchPanelVisible = true;
                break;
            case 2:
                lnSearchLayout.clearAnimation();
                lnSearchLayout.setAnimation(transOut_Right);
                lnSearchLayout.startAnimation(transOut_Right);
                lnSearchLayout.setVisibility(View.GONE);
                isSearchPanelVisible = false;
                break;
            case 3:

                //detailsLayout.bringToFront();
                detailsLayout.clearAnimation();
                detailsLayout.setAnimation(transIn_Right);
                detailsLayout.startAnimation(transIn_Right);
                detailsLayout.setVisibility(View.VISIBLE);

                // lnFrameContainer.bringToFront();
                lnFrameContainer.clearAnimation();
                lnFrameContainer.setAnimation(transIn_Left);
                lnFrameContainer.startAnimation(transIn_Left);
                lnFrameContainer.setVisibility(View.GONE);

                break;
            case 4:

                detailsLayout.clearAnimation();
                detailsLayout.setAnimation(transOut_Right);
                detailsLayout.startAnimation(transOut_Right);
                detailsLayout.setVisibility(View.GONE);

                lnFrameContainer.clearAnimation();
                lnFrameContainer.setAnimation(transOut_Left);
                lnFrameContainer.startAnimation(transOut_Left);
                lnFrameContainer.setVisibility(View.VISIBLE);

                break;
        }
    }

    /**
     * Options Menu Listener
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Search Menu Will be opened
            case R.id.event_menusearch:
                if (!isAnimating) {
                    if (!isSearchPanelVisible) {
                        animatePanel(1);
                    } else {
                        animatePanel(2);
                    }
                }
                break;
            // Add Event Activity will be called
            case R.id.event_menuadd:
                Intent addNewIntent = new Intent(EventListActivity.this, AddActivity.class);
                startActivity(addNewIntent);
                break;
            // Settings Activity will be called
            case R.id.event_menufilter:
                Intent settingsIntent = new Intent(EventListActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                this.finish();
                break;
            // Home button on Actions Menu will be called
            case android.R.id.home:
                if (!isAnimating) {
                    if (isSearchPanelVisible) {
                        animatePanel(2);
                    } else if (detailsLayout.getVisibility() == View.VISIBLE) {
                        animatePanel(4);
                    } else if (detailsLayout.getVisibility() == View.GONE && !isSearchPanelVisible) {
                        onBackPressed();
                    }

                }

                break;
            case R.id.event_menuLogOut:
                new AlertDialog.Builder(EventListActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                sharedPreferences.edit().clear().commit();
                                Intent logoutIntent = new Intent(EventListActivity.this, LoginActivity.class);
                                startActivity(logoutIntent);
                                EventListActivity.this.finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.flee_broken)
                        .show();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * On Click Listener for Buttons in the activity
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * Button Search In Search Panel will be calling this part of the code
             * This will start the search for Event List with the filters entered
             */
            case R.id.btnSearch:
                animatePanel(2);
                EventListTask getEventTask = new EventListTask();
                getEventTask.execute();
                break;
            /**
             *  Called when Date input box is clicked
             *  This opens a date picker dialog
             *  The Date picker di
             */
            case R.id.srch_Date:
                new DatePickerDialog(EventListActivity.this, fromDatepickerdialog,
                        calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
                break;
            /**
             *  Clear button implementation to clear the data fields
             */
            case R.id.btnClear:
                edDate.setText("");
                edLocation.setText("");
                spnCategory.setSelection(0);

                break;

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


    // Sets the isAnimating Flag true
    // This helps understanding if a current animation is in progress before starting a new one
    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating = true;
    }

    // Sets the isAnimating Flag False
    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimating = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * This fucntion has been implemented for the Map Fragment to communicate with the Activity
     * Implementation has not been required as of now
     */
    @Override
    public void sendFromMap() {

    }

    /**
     * The Onbackpressed event has been over ridden to handle the back events in case of various fragments
     */
    @Override
    public void onBackPressed() {
        if (isSearchPanelVisible) {
            if (!isAnimating) {
                animatePanel(2);
            }
        } else if (detailsLayout.getVisibility() == View.VISIBLE) {
            if (!isAnimating) {
                animatePanel(4);
            }

        } else {
            super.onBackPressed();
        }
    }

    /**
     * This function will be called from Event List Fragment
     * Its responsibility is to pass the event bean received to the Event Details Fragment
     * <p/>
     * This function will call the Events Details Fragment function which will fill the fragment
     * with passed data
     *
     * @param selectedBean
     */
    @Override
    public void eventListClick(EventBean selectedBean) {

        if (selectedBean != null & eventDetailsFragment != null) {
            eventDetailsFragment.setEventDetails(selectedBean);
            if (!isAnimating && detailsLayout.getVisibility() == View.GONE) {
                animatePanel(3);
            }
        }

    }

    /**
     * This fuction will be called from the EVent List Fragment
     * Its responsibilty is to pass the event bean to the Events Details fragment
     *
     * @param selectedBean
     */
    @Override
    public void eventListLongClick(EventBean selectedBean) {

        if (mapDispFragment != null) {
            mapDispFragment.setLocationFocus(selectedBean);

        }

    }


    /**
     * Event List Task is an Inner Class Extending Async Task.
     * This task is designed to Fetch the list of events from the Server
     * The Task has 3 methods Implementation
     * 1. OnPreExecute : Will Prepare the URL which needs to be accessed
     * 2. doInBackground : Will actually Ping the Server and fetch the data
     * 3. OnPostExecute : Will parse the Json Response. This will also call
     * event list fragment for displaying the list
     * Map Fragment for displaying the markers
     */
    public class EventListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prepareUrl();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                responseString = "";
                Log.d("URL", url);
                WebServiceHelper serviceHelper = new WebServiceHelper();
                Response response = serviceHelper.GET(url);
                if (response != null) {
                    responseCode = response.code();
                    Log.d("DoInBack Response Code", "" + responseCode);
                    if (responseCode == Constants.HTTP_OK_200) {
                        responseString = response.body().string();
                    }
                }
                return responseString;
            } catch (Exception ex) {
                Log.e("Async Task", ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            try {
                if (s != null && s.length() > 0) {

                    ArrayList<EventBean> eventBeanArrayList = JsonResponseParser.parseEventListResponse(s);
                    Log.d("PostExecute", "" + eventBeanArrayList.size());
                    if (eventBeanArrayList != null && eventBeanArrayList.size() > 0) {
                        eventListFragment.setEventData(eventBeanArrayList);
                        mapDispFragment.setMarkers(eventBeanArrayList);
                    } else {
                        Toast.makeText(EventListActivity.this, "No events found.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception ex) {
                Log.e("OnPostExecute", ex.toString());

            }
        }
    }


    /**
     * This Function will build the URL
     * In case of Search it will build the URL based on filters
     */
    private void prepareUrl() {

        url = Constants.BASE_EVENT_URL + Constants.QUERY_PAGE_SIZE + "50" + Constants.URL_AUTH + "&status=upcoming";
        /**
         *  THis is for spinner values of Category
         **/
        int spinner_pos = spnCategory.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.categoryIDArray);
        String categoryID = size_values[spinner_pos];

        if (!"0".equals(categoryID)) {
            url = url + Constants.QUERY_CATEGORY + categoryID;
        } else {
            categoryID = sharedPreferences.getString(Constants.CATEGORY, "");
            url = url + Constants.QUERY_CATEGORY + "0" + categoryID;
        }

        /**
         *  Date filter
         */

        String date = edDate.getText().toString();

        if (date != null && date.length() > 0) {
            long longDate = 0;
            longDate = Utilities.getCurrentTimeinEpoc(date);
            if (longDate != 0) {
                url = url + Constants.QUERY_TIME + Utilities.getCurrentTimeinEpoc(date) + "," + Utilities.getNextTimeinEpoc(date);
            }
        }

        try {
            String location = edLocation.getText().toString();
            if (location != null && location.length() > 0) {
                String encodedlocation = URLEncoder.encode(location, "UTF-8");

                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses = null;


                try {
                    // Getting a maximum of 3 Address that matches the input text
                    addresses = geocoder.getFromLocationName(location, 3);
                    if (addresses != null && addresses.size() > 0) {
                        url = url + Constants.QUERY_CITY + encodedlocation + Constants.QUERY_LAT + addresses.get(0).getLatitude() + Constants.QUERY_LON + addresses.get(0).getLongitude();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                url = url + Constants.QUERY_LAT + sharedPreferences.getString(Constants.LATITUDE, "") + Constants.QUERY_LON + sharedPreferences.getString(Constants.LONGITUDE, "");
            }

        } catch (Exception ex) {
            Log.e("URL ENCODE", ex.toString());
        }
        Log.d("Prepare URL", url);

    }

}
