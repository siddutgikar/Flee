
package ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import data.EventBean;

/**
 * Created by siddh on 4/22/2016.
 */
public class EventListAdapter extends BaseAdapter {

    // Event List which needs to be populated
    private ArrayList<EventBean> _eventList;
    //Context Used for updating the UI
    private Context _context;

    public EventListAdapter(ArrayList<EventBean> eventList, Context context)
    {
        _eventList=eventList;
        _context=context;
    }

    @Override
    public int getCount() {
        return _eventList.size();
    }

    @Override
    public Object getItem(int position) {
      return  _eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return  _eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class holder{
        TextView txtName,txtTime,txtAddress;
        ImageView imgCateogory;
    }
}
