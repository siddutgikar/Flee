package com.mobilecomputing.flee.flee.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilecomputing.flee.flee.R;
import com.mobilecomputing.flee.flee.adapters.EventListAdapter;
import com.mobilecomputing.flee.flee.data.EventBean;

import java.util.ArrayList;

/**
 * Created by siddh on 4/22/2016.
 */
public class EventListFragment extends Fragment {


    ListView eventListView2;

    ArrayList<EventBean> _eventList;

    EventListAdapter eventListAdapter;

    Typeface fedraSansStdBold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventlist, container, false);

        initView(v);
        return v;
    }

    /**
     * @param v
     */
    private void initView(View v) {
        eventListView2 = (ListView) v.findViewById(R.id.lstEvents);
        _eventList = new ArrayList<EventBean>();
        eventListAdapter = new EventListAdapter(_eventList, getActivity());
        eventListView2.setAdapter(eventListAdapter);
        fedraSansStdBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FedraSansStd-Bold.ttf");
        TextView eventListTitle = (TextView) v.findViewById(R.id.eventListTitle);
        eventListTitle.setTypeface(fedraSansStdBold);
    }


    public void setEventData(ArrayList<EventBean> evenObjList) {
        _eventList.clear();
        _eventList.addAll(evenObjList);
        eventListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public interface EventListInterface {

        public void sendFromEventList();
    }

}
