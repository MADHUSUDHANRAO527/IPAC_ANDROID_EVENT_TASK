package com.bosch.ipactask.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "event")
public class Event  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "event_name")
    private String eventName;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @ColumnInfo(name = "event_type")
    private String eventType;

    @ColumnInfo(name = "event_description")
    private String eventDescription;

    @ColumnInfo(name = "event_date")
    private String eventDate;

    @ColumnInfo(name = "event_venue")
    private String eventLocation;

    public String getEventUserStatus() {
        return eventUserStatus;
    }

    public void setEventUserStatus(String eventUserStatus) {
        this.eventUserStatus = eventUserStatus;
    }

    @ColumnInfo(name = "user_status")
    private String eventUserStatus;


    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
