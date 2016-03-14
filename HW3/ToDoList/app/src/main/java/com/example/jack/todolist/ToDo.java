package com.example.jack.todolist;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.CheckBox;

import java.util.Calendar;

/**
 * Created by Jack on 3/8/2016.
 */
public class ToDo implements Parcelable {
    private String title;
    private String description;
    private String date;
    private CheckBox cbox;

    // constructor
    public ToDo(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // getters and setters
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDate() {
        return this.date;
    }
    public void setDate(String newDate) {
        this.date = newDate;
    }

    // parcelling
    public ToDo (Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.title = data[0];
        this.description = data[1];
        this.date = data[2];
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title,
        this.description, this.date});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };
}
