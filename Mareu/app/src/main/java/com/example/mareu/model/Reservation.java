package com.example.mareu.model;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

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
    private final String subject;

    //Constructor
    public Reservation(int roomId, Calendar meetingDate, ArrayList<String> participants, String name, int color, Calendar creationDate, String subject) {
        this.roomId = roomId;
        this.meetingCalendar = meetingDate;
        this.participants = participants;
        this.name = name;
        this.color = color;
        this.creationCalendar = creationDate;
        this.subject = subject;
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

    @Override
    public String toString() {
        return
                "Sujet de la réunion : '" + subject;
    }

    public String getParticipantsFormated() {
        String partListFormated = "";
        for (String part: this.participants
             ) {
            partListFormated = partListFormated + "\t" + part + "\n";
        }
        
        return partListFormated;
    }
    
    public String getName() {
        if(this.name.length() > 9){
            return this.name.substring(0,9)+"...";
        }
        return this.name;
    }
    public String getNameDetail() {
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
        String day = "";
        String month = "";
        if (isTwoDigitsMonth()) {
            month =  formatTwoDigitsMonth();
        } else {
            month =  formatOneDigitMonth();
        }
        if (isTwoDigitsDay()) {
            day =  formatTwoDigitsDay();
        } else {
            day =  formatOneDigitDay();
        }
        return day + "/" + month + "/" + meetingCalendar.get(Calendar.YEAR);
    }

    @NonNull
    private String formatTwoDigitsMonth() {
        return String.valueOf(this.meetingCalendar.get(Calendar.MONTH));
    }

   @NonNull
    private String formatTwoDigitsDay() {
        return String.valueOf(this.meetingCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @NonNull
    private String formatOneDigitMonth() {
        return "0" + this.meetingCalendar.get(Calendar.MONTH);
    }

   @NonNull
    private String formatOneDigitDay() {
        return "0" + this.meetingCalendar.get(Calendar.DAY_OF_MONTH);
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

    private boolean isTwoDigitsMonth() {
        return this.meetingCalendar.get(Calendar.MONTH) >= 10;
    }

    private boolean isTwoDigitsDay() {
        return this.meetingCalendar.get(Calendar.DAY_OF_MONTH) >= 10;
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

    public SpannableStringBuilder getMeetingCalendarFormated() {
        SpannableStringBuilder builder = new SpannableStringBuilder("La réunion est prévue pour le : " + formatWithDayOfMeeting() + " à " + formatCalendarForToday());
        builder.setSpan(new StyleSpan(Typeface.BOLD), 31, 31+formatWithDayOfMeeting().length(), 0);
        builder.setSpan(new StyleSpan(Typeface.BOLD), 34+formatWithDayOfMeeting().length(),34+formatWithDayOfMeeting().length() + formatCalendarForToday().length() , 0);
        return builder;
    }

    public String getSubject() {
        return "Description du sujet de la réunion :\n\t" + subject;
    }
}
