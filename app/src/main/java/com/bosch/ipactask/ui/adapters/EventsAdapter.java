package com.bosch.ipactask.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bosch.ipactask.R;
import com.bosch.ipactask.db.entity.Event;
import com.bosch.ipactask.ui.activity.MainActivity;
import com.bosch.ipactask.ui.fragment.EventDetailFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Event> eventList;
    private Context context;
    private List<Event> itemsCopy = new ArrayList<>();

    public EventsAdapter(List<Event> all, Context mContext) {
        this.eventList = all;
        this.itemsCopy = all;
        this.context = mContext;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView txt_event_type, txt_event_name, txt_event_date, txt_event_location, txt_event_user_status;

        public EventsViewHolder(View v) {
            super(v);
            txt_event_type = (TextView) v.findViewById(R.id.txt_event_type);
            txt_event_name = (TextView) v.findViewById(R.id.txt_event_name);
            txt_event_date = (TextView) v.findViewById(R.id.txt_event_date);
            txt_event_location = (TextView) v.findViewById(R.id.txt_event_location);
            txt_event_user_status = (TextView) v.findViewById(R.id.txt_event_user_status);
            view = v;
        }
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_list_item, viewGroup, false);

        EventsViewHolder vh = new EventsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder eventsViewHolder, int i) {
        eventsViewHolder.txt_event_type.setText(eventList.get(i).getEventType());
        eventsViewHolder.txt_event_name.setText(eventList.get(i).getEventName());
        eventsViewHolder.txt_event_date.setText(eventList.get(i).getEventDate());
        eventsViewHolder.txt_event_location.setText(eventList.get(i).getEventLocation());
        if (eventList.get(i).getEventUserStatus() != null) {
            eventsViewHolder.txt_event_user_status.setText(eventList.get(i).getEventUserStatus());

            if (eventList.get(i).getEventUserStatus().equals("Attending"))
                eventsViewHolder.txt_event_user_status.setTextColor(Color.GREEN);
            else if (eventList.get(i).getEventUserStatus().equals("Not attending"))
                eventsViewHolder.txt_event_user_status.setTextColor(Color.RED);
            else
                eventsViewHolder.txt_event_user_status.setTextColor(Color.YELLOW);

        }

        eventsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", eventList.get(i));
                ((MainActivity) context).addFragment(new EventDetailFragment(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void filter(String text) {
        eventList = new ArrayList<>();
        if (text.isEmpty()) {
            eventList.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (Event item : itemsCopy) {
                if (item.getEventName().toLowerCase().contains(text) || item.getEventLocation().toLowerCase().contains(text)) {
                    eventList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterByCityOrDate(List<Event> evntList) {
        eventList = new ArrayList<>();
        eventList = evntList;
        notifyDataSetChanged();
    }
}
