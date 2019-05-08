package com.bosch.ipactask.db.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bosch.ipactask.App;
import com.bosch.ipactask.R;
import com.bosch.ipactask.db.database.AppDatabase;
import com.bosch.ipactask.db.entity.Event;

import java.util.List;

public class DatabaseInitializer  {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateEventsAsync(@NonNull final AppDatabase db) {
        PopulateEventsDBAsync task = new PopulateEventsDBAsync(db);
        task.execute();
    }


    public static Event loadEvents(final AppDatabase db, Event event) {
        db.eventsDAO().insertAllEvents(event);
        return event;
    }



    private static void populateEventsData(AppDatabase db) {
        Event event = new Event();
        for (int i = 0; i < 3; i++) {
            event.setEventName("Outlife Summer Camp 2019");
            event.setEventType("EDUCATION");
            event.setEventDescription(App.getInstance().getResources().getString(R.string.desc));
            event.setEventDate("9-4-2019");
            event.setEventLocation("Hyderabad");

            loadEvents(db, event);

            event.setEventName("Workshop by Hobby");
            event.setEventType("Sports");
            event.setEventDescription(App.getInstance().getResources().getString(R.string.desc));
            event.setEventDate("10-4-2019");
            event.setEventLocation("Bangalore");

            loadEvents(db, event);

            event.setEventName("Block Printing");
            event.setEventType("Sports");
            event.setEventDescription(App.getInstance().getResources().getString(R.string.desc));
            event.setEventDate("11-4-2019");
            event.setEventLocation("Delhi");


            loadEvents(db, event);
        }

        List<Event> eventsList = db.eventsDAO().getAllEvents();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + eventsList.size());
    }

    private static class PopulateEventsDBAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateEventsDBAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateEventsData(mDb);
            return null;
        }
    }
}
