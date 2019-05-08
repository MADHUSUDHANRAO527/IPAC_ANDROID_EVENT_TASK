package com.bosch.ipactask.ui.fragment;

import android.arch.persistence.room.Database;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bosch.ipactask.R;
import com.bosch.ipactask.databinding.FragmentEventDetailBinding;
import com.bosch.ipactask.db.database.AppDatabase;
import com.bosch.ipactask.db.entity.Event;
import com.bosch.ipactask.db.utils.DatabaseInitializer;

public class EventDetailFragment extends Fragment {

    private static final String TAG = EventDetailFragment.class.getSimpleName();
    private View view;

    FragmentEventDetailBinding fragmentEventDetailBinding;
    Event event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false);
        Bundle bundle = getArguments();
        event = (Event) bundle.getSerializable("data");
        Log.d(TAG, "onCreateView: " + event);
        view = fragmentEventDetailBinding.getRoot();

        fragmentEventDetailBinding.eventName.setText(event.getEventName());
        fragmentEventDetailBinding.eventDesc.setText(event.getEventDescription());
        fragmentEventDetailBinding.eventDate.setText(event.getEventDate());
        fragmentEventDetailBinding.eventLocation.setText(event.getEventLocation());
        fragmentEventDetailBinding.eventPrice.setText("$20");
        fragmentEventDetailBinding.attendingTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  event.setEventUserStatus("Attending");
                //  DatabaseInitializer.loadEvents(AppDatabase.getAppDatabase(getActivity()),event);
                AppDatabase.getAppDatabase(getActivity()).eventsDAO().UpdateColumnById("Attending", event.getUid());
                Toast.makeText(getActivity(), "Your attending this event :)", Toast.LENGTH_SHORT).show();
                Log.d(TAG, AppDatabase.getAppDatabase(getActivity()).eventsDAO().getAllEvents() + "");
            }
        });
        fragmentEventDetailBinding.notAttendingTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Your not attending this event ):", Toast.LENGTH_SHORT).show();
                AppDatabase.getAppDatabase(getActivity()).eventsDAO().UpdateColumnById("Not attending", event.getUid());
            }
        });
        fragmentEventDetailBinding.maybeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Your may attend this event <:", Toast.LENGTH_SHORT).show();
                AppDatabase.getAppDatabase(getActivity()).eventsDAO().UpdateColumnById("May be", event.getUid());
            }
        });
        return view;
    }


}