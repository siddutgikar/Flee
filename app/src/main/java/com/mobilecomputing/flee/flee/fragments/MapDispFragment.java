package com.mobilecomputing.flee.flee.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.mobilecomputing.flee.flee.R;
import com.mobilecomputing.flee.flee.data.EventBean;
import com.mobilecomputing.flee.flee.utils.Constants;

import java.util.ArrayList;

/**
 * Created by siddh on 4/22/2016.
 */
public class MapDispFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;

    ArrayList<EventBean> _eventList;


    CameraUpdate zoomLevel;

    CameraUpdate Animation;

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
    public void onMapReady(GoogleMap gMap) {
        if (gMap != null) {
            googleMap = gMap;
            plotMarkers();
        }
    }


    public void plotMarkers() {
        if (googleMap != null && _eventList != null && _eventList.size() > 0) {
            googleMap.clear();
            IconGenerator myFactory = new IconGenerator(getActivity());
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (int i = 0; i < _eventList.size(); i++) {
                //  if (i < 50) {
                double lat = _eventList.get(i).getLatitude();
                double lng = _eventList.get(i).getLongitude();
                if (lat != 0.0 && lng != 0.0) {
                    if (_eventList.get(i).getColorIndex().equals("#754C9A")) {
                        myFactory.setBackground(getResources().getDrawable(R.drawable.purple));
                    } else if (_eventList.get(i).getColorIndex().equals("#65C0AD")) {
                        myFactory.setBackground(getResources().getDrawable(R.drawable.green));
                    } else {
                        myFactory.setBackground(getResources().getDrawable(R.drawable.orange));
                    }
                    myFactory.setTextAppearance(getActivity(), R.style.markerStyle);
                    Bitmap icon = myFactory.makeIcon("" + (i + 1));
                    boundsBuilder.include(new LatLng(lat, lng));
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon)).position(new LatLng(lat, lng)).title(_eventList.get(i).getName()));

                }
            }

            LatLngBounds mapBounds = boundsBuilder.build();
            // begin new code:
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(mapBounds, width, height, padding);
            googleMap.animateCamera(cu);
            googleMap.moveCamera(cu);
        }


    }


    /**
     * @param eventBeanArrayList
     */
    public void setMarkers(ArrayList<EventBean> eventBeanArrayList) {
        _eventList = eventBeanArrayList;
        plotMarkers();

    }

    public interface MapFragmentInterface {
        public void sendFromMap();
    }


    public void setLocationFocus(EventBean selectedBean) {
        try {
            if (selectedBean != null && googleMap != null) {
                double lat = selectedBean.getLatitude();
                double lng = selectedBean.getLongitude();
                if (lat != 0.0 && lng != 0.0) {
                    LatLng latLng = new LatLng(lat, lng);
                    Animation = CameraUpdateFactory.newLatLng(latLng);
                    zoomLevel = CameraUpdateFactory.zoomTo(8);
                    googleMap.animateCamera(zoomLevel, 1000, new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            zoomLevel = CameraUpdateFactory.zoomTo(12);
                            googleMap.moveCamera(Animation);
                            googleMap.animateCamera(zoomLevel, 1000, null);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "Location not available.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_LONG).show();
            Log.e("Set Focus", ex.toString());
        }
    }

}
