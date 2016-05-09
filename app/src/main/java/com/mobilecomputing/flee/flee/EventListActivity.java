package com.mobilecomputing.flee.flee;

import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.mobilecomputing.flee.flee.data.EventBean;
import com.mobilecomputing.flee.flee.fragments.EventDetailsFragment;
import com.mobilecomputing.flee.flee.fragments.EventListFragment;
import com.mobilecomputing.flee.flee.fragments.MapDispFragment;
import com.mobilecomputing.flee.flee.utils.Constants;
import com.mobilecomputing.flee.flee.utils.JsonResponseParser;
import com.mobilecomputing.flee.flee.utils.Utilities;
import com.mobilecomputing.flee.flee.utils.WebServiceHelper;

import java.util.ArrayList;

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
     * Animations for sliding of Search Panel
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
    EditText edDate;

    /**
     * Edit text for search by Location
     */
    EditText edLocation;

    MapDispFragment mapDispFragment;

    EventListFragment eventListFragment;

    EventDetailsFragment eventDetailsFragment;

    String url = "";

    String responseString = "";

    int responseCode = 0;

    /**
     * Frame Layout for loading the Detail Screen fragment
     */
    FrameLayout detailsLayout;

    LinearLayout lnFrameContainer;

    ProgressBar progressBar;

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
        lnFrameContainer = (LinearLayout) findViewById(R.id.frame_Containers);
        detailsLayout = (FrameLayout) findViewById(R.id.frame_Details);
        lnSearchLayout = (LinearLayout) findViewById(R.id.searchPanelLayout);
        edLocation = (EditText) findViewById(R.id.srch_Location);
        edDate = (EditText) findViewById(R.id.srch_Date);
        spnCategory = (Spinner) findViewById(R.id.srch_Category);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        eventListFragment = new EventListFragment();
        mapDispFragment = new MapDispFragment();
        eventDetailsFragment = new EventDetailsFragment();

        progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.event_menusearch:
                if (!isAnimating) {
                    if (!isSearchPanelVisible) {
                        animatePanel(1);
                    } else {
                        animatePanel(2);
                    }
                }
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
            case R.id.btnSearch:
                animatePanel(2);
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


    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating = true;
    }

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


    @Override
    public void sendFromMap() {

    }

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

    @Override
    public void eventListClick(EventBean selectedBean) {

        if (selectedBean != null & eventDetailsFragment != null) {
            eventDetailsFragment.setEventDetails(selectedBean);
            if (!isAnimating && detailsLayout.getVisibility() == View.GONE) {
                animatePanel(3);
            }
        }

    }

    @Override
    public void eventListLongClick(EventBean selectedBean) {

        if (mapDispFragment != null) {
            mapDispFragment.setLocationFocus(selectedBean);

        }

    }


    /**
     *
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
                    eventListFragment.setEventData(eventBeanArrayList);
                    mapDispFragment.setMarkers(eventBeanArrayList);
                }
            } catch (Exception ex) {
                Log.e("OnPostExecute", ex.toString());

            }
        }
    }


    /**
     * This Function will build the URL
     */
    private void prepareUrl() {
        url = Constants.BASE_EVENT_URL + Constants.QUERY_ZIP + "21227" + Constants.QUERY_PAGE_SIZE + "50" + Constants.QUERY_TIME + Utilities.getCurrentTimeinEpoc(null) + "," + Utilities.getNextTimeinEpoc(null) + Constants.URL_AUTH;
    }


}
