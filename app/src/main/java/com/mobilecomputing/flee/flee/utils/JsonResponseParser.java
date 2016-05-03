package com.mobilecomputing.flee.flee.utils;

import android.util.Log;
import android.widget.Toast;

import com.mobilecomputing.flee.flee.data.EventBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by siddh on 5/2/2016.
 */
public class JsonResponseParser {


    public static ArrayList<EventBean> parseEventListResponse(String responseString) {
        ArrayList<EventBean> eventList = null;
        try {
            if (responseString != null&&responseString.length()>0)
            {
                JSONObject responseObject = new JSONObject(responseString);
                JSONArray resultsObject = responseObject.getJSONArray("results");
                for (int i = 0; i < resultsObject.length(); i++) {
                    JSONObject object = resultsObject.getJSONObject(i);
                    JSONObject groupObject = object.getJSONObject("group");
                    JSONObject venueObject = object.getJSONObject("venue");
                    EventBean eventBean = new EventBean();
                    eventBean.setName(object.getString("name"));
                    eventBean.setDescription(object.getString("description"));

                    if(venueObject!=null)
                    {
                        String latString=venueObject.getString("lat");
                        if(latString!=null) {
                          //  eventBean.setLatitude(Double.parseDouble(latString));
                        }
                    }


                    eventBean.setName(object.getString("name"));
                    eventBean.setName(object.getString("name"));
                    eventBean.setName(object.getString("name"));
                }

            }

        } catch (Exception ex) {
            Log.e("EventListParsing", ex.toString());
        }
        return eventList;


    }


}
