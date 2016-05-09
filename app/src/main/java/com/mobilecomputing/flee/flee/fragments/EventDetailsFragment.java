package com.mobilecomputing.flee.flee.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilecomputing.flee.flee.R;
import com.mobilecomputing.flee.flee.data.EventBean;
import com.mobilecomputing.flee.flee.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by siddh on 5/7/2016.
 */
public class EventDetailsFragment extends Fragment implements View.OnClickListener, LocationListener {

    /**
     * Text view objects to be used for displaying the data
     */
    TextView tv_Title, tv_Address_Label, tv_Address_value, tv_Desc_Label, tv_Desc_value, tv_Distance, tv_Time;

    /**
     * Image view object for displaying the map
     */
    ImageView imgView;

    /**
     * Image button for the share option
     */
    ImageButton btnShare;

    int width = 401;
    int height = 592;
    double currentLat = 0.0;
    double currentLng = 0.0;
    double eventLat = 0.0;
    double eventLng = 0.0;

    LocationManager locationManager_;

    /**
     * Event Bean for the received object
     */
    EventBean _eventBean;

    

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_details, container, false);

        initView(v);
        return v;
    }

    /**
     * Initialise the view Elements
     *
     * @param v
     */

    private void initView(View v) {
        try {
            tv_Title = (TextView) v.findViewById(R.id.tv_title);
            tv_Address_Label = (TextView) v.findViewById(R.id.tv_address_header);
            tv_Address_value = (TextView) v.findViewById(R.id.tv_address_details);
            tv_Desc_Label = (TextView) v.findViewById(R.id.tv_desc_header);
            tv_Desc_value = (TextView) v.findViewById(R.id.tv_desc_details);
            tv_Distance = (TextView) v.findViewById(R.id.tv_Distance_details);
            tv_Time = (TextView) v.findViewById(R.id.tv_Time_details);
            imgView = (ImageView) v.findViewById(R.id.imgMap);
            btnShare = (ImageButton) v.findViewById(R.id.btnShare);
            Typeface fedraSansStdBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FedraSansStd-Bold.ttf");
            Typeface fedraSansStdBook = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FedraSansStd-Book.ttf");
            tv_Title.setTypeface(fedraSansStdBold);
            tv_Address_Label.setTypeface(fedraSansStdBold);
            tv_Address_value.setTypeface(fedraSansStdBook);
            tv_Desc_Label.setTypeface(fedraSansStdBold);
            tv_Desc_value.setTypeface(fedraSansStdBook);
            tv_Distance.setTypeface(fedraSansStdBook);
            tv_Time.setTypeface(fedraSansStdBook);
            imgView.setOnClickListener(this);
            btnShare.setOnClickListener(this);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            Log.e("Event Details Init View", ex.toString());
        }
    }

    /**
     * The method will Set All the details of the event on the screen
     *
     * @param eventBean
     */
    public void setEventDetails(EventBean eventBean) {
        try {
            _eventBean = eventBean;
            if (eventBean != null) {

                if (imgView.getWidth() != 0) {
                    width = imgView.getWidth();
                }
                if (imgView.getHeight() != 0) {
                    height = imgView.getHeight();
                }
                String distance = eventBean.getDistance();
                tv_Distance.setText(distance.substring(0, distance.indexOf('.')) + " mi");
                long time = eventBean.getTime();
                if (time > 0) {
                    String date = new SimpleDateFormat("dd MMM hh:mm a")
                            .format(new java.util.Date(time * 1000));
                    tv_Time.setText(date);
                } else {
                    tv_Time.setText("soon...");
                }
                tv_Title.setText(eventBean.getName());
                tv_Address_value.setText(eventBean.getAddress());
                if (eventBean.getDescription() != null) {
                    tv_Desc_value.setText(Html.fromHtml(eventBean.getDescription()));
                }
                double lat = eventBean.getLatitude();
                double lng = eventBean.getLongitude();

                if (lat != 0.0 && lng != 0.0) {
                    eventLat = lat;
                    eventLng = lng;
                    String image_url = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&markers=icon:http://tinyurl.com/2ftvtt6|" + lat + "," + lng + "&zoom=12&size=" + width + "x" + height + "&sensor=false";
                    Log.d("MapsImage UR", image_url);
                    Picasso.with(getActivity()).load(image_url).fit().into(imgView);
                }
            } else {
                Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            Log.e("EVENT Details Error", ex.toString());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMap:
                startMapApp();
                break;
            case R.id.btnShare:
                try {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/html");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<h1>" + _eventBean.getName() + "</h1><div>" + _eventBean.getAddress() + "</div><div>" + _eventBean.getUrl() + "</div>"));
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));
                } catch (Exception ex) {

                }
                break;
        }
    }

    /**
     * THis Method will find the current location of the
     */
    private void startMapApp() {
        try {
            locationManager_ = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager_ != null) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (eventLat != 0.0 && eventLng != 0.0) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.827500,-122.481670"));
                        startActivity(i);
                    }
                } else {
                    locationManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, EventDetailsFragment.this);
                }
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + eventLat + "," + eventLng));
                startActivity(i);
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            Log.e("Event Details Fragment", ex.toString());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
            if (currentLat != 0.0 && currentLng != 0.0 && eventLat != 0.0 && eventLng != 0.0) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + currentLat + "," + currentLng + "&daddr=" + eventLat + "," + eventLng));
                startActivity(intent);
            } else if (eventLat != 0.0 && eventLng != 0.0) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + eventLat + "," + eventLng));
                startActivity(i);
            } else {
                Toast.makeText(getActivity(), "Location cannot be determined", Toast.LENGTH_LONG).show();
            }
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager_.removeUpdates(EventDetailsFragment.this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
