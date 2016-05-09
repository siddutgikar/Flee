package com.mobilecomputing.flee.flee.utils;

import android.util.Log;

import com.mobilecomputing.flee.flee.data.EventBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by siddh on 5/2/2016.
 */
public class JsonResponseParser {


    public static ArrayList<EventBean> parseEventListResponse(String responseString) {
        ArrayList<EventBean> eventList = null;
        try {
            if (responseString != null && responseString.length() > 0) {
                JSONObject responseObject = new JSONObject(responseString);
                if (responseObject.has("results")) {
                    eventList = new ArrayList<EventBean>();
                    JSONArray resultsObject = responseObject.getJSONArray("results");

                    for (int i = 0; i < resultsObject.length(); i++) {
                        JSONObject object = resultsObject.getJSONObject(i);
                        EventBean eventBean = new EventBean();
                        eventBean.setColorIndex(getRandom());
                        if (object.has("name")) {
                            eventBean.setName(object.getString("name"));
                        }
                        if (object.has("description")) {
                            eventBean.setDescription(object.getString("description"));
                        }
                        if(object.has("time"))
                        {
                            eventBean.setTime(object.getLong("time"));
                        }
                        if (object.has("distance")) {
                            eventBean.setDistance(object.getString("distance"));
                        }


                        if (object.has("group")) {
                            JSONObject groupObject = object.getJSONObject("group");
                        }

                        if (object.has("venue")) {
                            JSONObject venueObject = object.getJSONObject("venue");
                            if (venueObject != null) {
                                String latString = venueObject.getString("lat");
                                if (latString != null) {
                                    eventBean.setLatitude(Double.parseDouble(latString));
                                }
                                String lngString = venueObject.getString("lon");
                                if (lngString != null) {
                                    eventBean.setLongitude(Double.parseDouble(lngString));
                                }
                                eventBean.setAddress(venueObject.getString("name") + " " + venueObject.getString("address_1"));
                            }
                        }
                        eventList.add(eventBean);
                    }
                }

            }

        } catch (JSONException ex) {
            Log.e("EventListParsing", ex.toString());
        }
        return eventList;


    }

    /**
     * Get the Color Value
     *
     * @return
     */
    public static String getRandom() {
        String[] array = {"#65C0AD", "#E37245", "#754C9A"};
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


}
