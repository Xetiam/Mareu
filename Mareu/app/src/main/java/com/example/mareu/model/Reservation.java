package com.example.mareu.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Reservation implements Serializable {
    private final String name;
    //Attributes
    private final int roomId;
    private final Calendar meetingCalendar;
    private final ArrayList<String> participants;
    private final int color;
    private final Calendar creationCalendar;

    //Constructor
    public Reservation(int roomId, Calendar meetingDate, ArrayList<String> participants, String name, int color, Calendar creationDate) {
        this.roomId = roomId;
        this.meetingCalendar = meetingDate;
        this.participants = participants;
        this.name = name;
        this.color = color;
        this.creationCalendar = creationDate;
    }

    //Getter
    public int getRoomId() {
        return this.roomId;
    }

    public Calendar getMeetingCalendar() {
        return this.meetingCalendar;
    }

    public ArrayList<String> getParticipants() {
        return this.participants;
    }

    public String getName() {
        if(this.name.length() > 20){
            return this.name.substring(0,19)+"...";
        }
        return this.name;
    }

    public int getColor() {
        return this.color;
    }

    public Calendar getEndMeetingDate() {
        Calendar date = this.getMeetingCalendar();
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        if (minute >= 14) {
            hour = hour + 1;
            minute = minute - 15;
        } else {
            minute = minute + 45;
        }

        return getCalendar(date, hour, minute);
    }

    public Calendar getBeforeMeetingDate() {
        Calendar date = this.getMeetingCalendar();
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        if (minute <= 15) {
            hour = hour - 1;
            minute = minute + 15;
        } else {
            minute = minute - 45;
        }

        return getCalendar(date, hour, minute);
    }

    @NonNull
    private Calendar getCalendar(Calendar date, int hour, int minute) {
        Calendar beforeMeetingDate = Calendar.getInstance();
        beforeMeetingDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), hour, minute);
        return beforeMeetingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return roomId == that.roomId && color == that.color && Objects.equals(meetingCalendar, that.meetingCalendar) && Objects.equals(participants, that.participants) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, meetingCalendar, participants, name, color);
    }

    public String getMeetingDateCorrectlyFormatted() {
        Calendar today = Calendar.getInstance();

        if (isSameDay(today) &&
                isSameMonth(today) &&
                isSameYear(today)) {
            return formatCalendarForToday();
        } else {
            return formatWithDayOfMeeting();
        }
    }

    @NonNull
    private String formatCalendarForToday() {
        if (isTwoDigitsMinutes()) {
            return formatTwoDigitsMinutes();
        } else {
            return formatOneDigitMinutes();
        }
    }

    @NonNull
    private String formatWithDayOfMeeting() {
        return getMeetingCalendar().get(Calendar.DAY_OF_MONTH) + "/" + getMeetingCalendar().get(Calendar.MONTH) + "/" + getMeetingCalendar().get(Calendar.YEAR);
    }

    @NonNull
    private String formatOneDigitMinutes() {
        return this.meetingCalendar.get(Calendar.HOUR_OF_DAY) + "h0" + getMeetingCalendar().get(Calendar.MINUTE);
    }

    @NonNull
    private String formatTwoDigitsMinutes() {
        return this.meetingCalendar.get(Calendar.HOUR_OF_DAY) + "h" + getMeetingCalendar().get(Calendar.MINUTE);
    }

    private boolean isTwoDigitsMinutes() {
        return this.meetingCalendar.get(Calendar.MINUTE) >= 10;
    }

    private boolean isSameYear(Calendar today) {
        return this.meetingCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR);
    }

    private boolean isSameMonth(Calendar today) {
        return meetingCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH);
    }

    private boolean isSameDay(Calendar today) {
        return meetingCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getCreationCalendar() {
        return creationCalendar;
    }
}
