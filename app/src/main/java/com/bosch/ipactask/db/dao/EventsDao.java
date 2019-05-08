package com.bosch.ipactask.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bosch.ipactask.db.entity.Event;

import java.util.List;

@Dao
public interface EventsDao {
    @Query("SELECT * FROM event")
    List<Event> getAllEvents();

    @Query("SELECT * FROM event where event_venue LIKE  :eventLocation")
    List<Event> findByEventCity(String eventLocation);

    @Query("SELECT * FROM event where event_date LIKE  :eventDate")
    List<Event> findByEventDate(String eventDate);

    @Insert
    void insertAllEvents(Event... events);

    @Query("UPDATE event SET user_status= :eventUserStatus WHERE uid = :uid")
    void UpdateColumnById(String eventUserStatus, int uid);
}
