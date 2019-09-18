package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 11/16/18.
 */

public class EventInformation {

    public String imageUrl;
    public String eventTitle;
    public String eventDate;
    public String startTime;
    public String endTime;
    public String eventDetails;

    public EventInformation(){

    }

    public EventInformation(String imageUrl, String eventTitle, String eventDate, String startTime, String endTime, String eventDetails) {
        this.imageUrl = imageUrl;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDetails = eventDetails;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEventDetails() {
        return eventDetails;
    }
}
