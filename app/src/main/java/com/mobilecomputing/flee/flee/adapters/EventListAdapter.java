
package com.mobilecomputing.flee.flee.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilecomputing.flee.flee.R;
import com.mobilecomputing.flee.flee.data.EventBean;
import com.mobilecomputing.flee.flee.utils.TextDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by siddh on 4/22/2016.
 */
public class EventListAdapter extends BaseAdapter {

    // Event List which needs to be populated
    private ArrayList<EventBean> _eventList;
    //Context Used for updating the UI
    private Context _context;
    // Inflator Object to inflate the Row View
    LayoutInflater layoutInflater;

    Typeface fedraSansStdBold, fedraSansStdBook;

    public EventListAdapter(ArrayList<EventBean> eventList, Context context) {
        _eventList = eventList;
        _context = context;
        layoutInflater = LayoutInflater.from(context);
        initFonts();
    }

    /**
     * FEDRA SANS
     */
    private void initFonts() {
        fedraSansStdBold = Typeface.createFromAsset(_context.getAssets(), "fonts/FedraSansStd-Bold.ttf");
        fedraSansStdBook = Typeface.createFromAsset(_context.getAssets(), "fonts/FedraSansStd-Book.ttf");
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return _eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return _eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        holder hold = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.event_row, null);
            hold = new holder();
            hold.txtName = (TextView) convertView.findViewById(R.id.tv_EventName);
            hold.txtDistance = (TextView) convertView.findViewById(R.id.tv_Distance);
            hold.txtTime = (TextView) convertView.findViewById(R.id.tv_Time);
            hold.imgCateogory = (ImageView) convertView.findViewById(R.id.img_count);
            convertView.setTag(hold);

        } else {
            hold = (holder) convertView.getTag();
        }
        hold.txtName.setText(_eventList.get(position).getName());
        hold.txtName.setTypeface(fedraSansStdBold);
        String distance = _eventList.get(position).getDistance();
        hold.txtDistance.setText(distance.substring(0, distance.indexOf('.')) + " mi");
        hold.txtDistance.setTypeface(fedraSansStdBook);

        long time = _eventList.get(position).getTime();
        if (time > 0) {

            String date = new SimpleDateFormat("dd MMM hh:mm a")
                    .format(new java.util.Date(_eventList.get(position).getTime() * 1000));
            hold.txtTime.setText(date);
        } else {
            hold.txtTime.setText("soon...");
        }
        hold.txtTime.setTypeface(fedraSansStdBook);
        TextDrawable txtDrawable = TextDrawable.builder().buildRound(
                position + 1 + "", Color.parseColor(_eventList.get(position).getColorIndex()));
        hold.imgCateogory.setImageDrawable(txtDrawable);
        return convertView;
    }

    static class holder {
        TextView txtName, txtTime, txtDistance;
        ImageView imgCateogory;

    }
}
