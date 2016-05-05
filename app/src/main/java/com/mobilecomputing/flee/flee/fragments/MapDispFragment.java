package com.mobilecomputing.flee.flee.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobilecomputing.flee.flee.R;

/**
 * Created by siddh on 4/22/2016.
 * modified by apyryt on 5/4/2016
 */
public class MapDispFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;

    /*
     * Defining the custom Info Window for markers on the map. The window will display information
     * about each event on the map and will appear when the marker of the event is clicked on
     *
     * TODO: make methods to modify the text of each of the fields in the info window
     */
    class EventInfoWindow implements GoogleMap.InfoWindowAdapter {

        private View windowView;

        //defines the InfoWindow with the event_popuop.xml file
        public EventInfoWindow() {
            windowView = getActivity().getLayoutInflater().inflate(R.layout.event_popup, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            //defining the TextViews
            TextView title = (TextView) windowView.findViewById(R.id.titleTextPopup);
            TextView location = (TextView) windowView.findViewById(R.id.locationText);
            TextView dateTime = (TextView) windowView.findViewById(R.id.dateText);
            TextView descr = (TextView) windowView.findViewById(R.id.descriptionText);

            //For each marker, retrieve the details of the event and set the values of the popup window
            //TODO: here we will pass in the event details specific to each event
            title.setText("Sample Event");
            location.setText("UMBC");
            dateTime.setText("Tomorrow");
            descr.setText("This is a test event");

            return windowView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return windowView;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapDispFragment.this);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {

        }
        this.googleMap = googleMap;

        //allows the current user's location to be displayed on the map
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        //sets custom event info window
        googleMap.setInfoWindowAdapter(new EventInfoWindow());

        //for testing purposes
        //googleMap.addMarker(new MarkerOptions()
        //       .position(new LatLng(39.2551, -76.7112)));
    }

    public interface MapFragmentInterface {
        public void sendFromMap();
    }
}
