package com.bosch.ipactask.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bosch.ipactask.R;
import com.bosch.ipactask.databinding.FragmentEventsListBinding;
import com.bosch.ipactask.db.database.AppDatabase;
import com.bosch.ipactask.db.entity.Event;
import com.bosch.ipactask.db.utils.DatabaseInitializer;
import com.bosch.ipactask.db.utils.EventListViewModel;
import com.bosch.ipactask.events.DateTriggerEvent;
import com.bosch.ipactask.ui.adapters.EventsAdapter;
import com.bosch.ipactask.ui.dialog.ViewDialog;


import java.util.List;

public class EventsListFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private static final String TAG = EventsListFragment.class.getSimpleName();
    private View view;
    private LinearLayoutManager layoutManager;
    private EventsAdapter mAdapter;
    String[] cities = {"All Events", "Hyderabad", "Bangalore", "Vizag"};
    FragmentEventsListBinding fragmentEventsListBinding;
    EventListViewModel eventListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentEventsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_events_list, container, false);
        view = fragmentEventsListBinding.getRoot();
        eventListViewModel = ViewModelProviders.of(this).get(EventListViewModel.class);

        DatabaseInitializer.populateEventsAsync(AppDatabase.getAppDatabase(getActivity()));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        fragmentEventsListBinding.recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        fragmentEventsListBinding.recyclerView.setLayoutManager(layoutManager);

        //setting spiner
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        fragmentEventsListBinding.filterbySpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, cities);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEventsListBinding.filterbySpinner.setAdapter(aa);


        fragmentEventsListBinding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // mAdapter.filter(newText);
                return true;
            }
        });
        ImageView closeButton = (ImageView) fragmentEventsListBinding.searchBar.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage this event.
                fragmentEventsListBinding.searchBar.setQuery("", false);
                fragmentEventsListBinding.searchBar.clearFocus();
                mAdapter.filterByCityOrDate(AppDatabase.getAppDatabase(getActivity()).eventsDAO().getAllEvents());
            }
        });
        fragmentEventsListBinding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(getActivity());
            }
        });

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String eventDate) {
                // Update the UI, in this case, a TextView.
                List<Event> eventList = AppDatabase.getAppDatabase(getActivity()).eventsDAO().findByEventDate(eventDate);
                mAdapter.filterByCityOrDate(eventList);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        eventListViewModel.getSelectedDate().observe(this, nameObserver);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            mAdapter.filterByCityOrDate(AppDatabase.getAppDatabase(getActivity()).eventsDAO().getAllEvents());
            return;
        }
        List<Event> eventList = AppDatabase.getAppDatabase(getActivity()).eventsDAO().findByEventCity(cities[position]);
        Log.d(TAG, "Rows Count: " + eventList.size());
        mAdapter.filterByCityOrDate(eventList);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // specify an adapter (see also next example)
        mAdapter = new EventsAdapter(AppDatabase.getAppDatabase(getActivity()).eventsDAO().getAllEvents(), getActivity());
        fragmentEventsListBinding.recyclerView.setAdapter(mAdapter);
    }
}