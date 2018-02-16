package com.example.sarthak.todo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sarthak on 11/2/18.
 */

public class Information implements Serializable {
    String content;
    String date;
    String time;
    int position;

    public Information() {
    }

    public Information(String content, String time, String date) {
        this.content = content;
        this.time = time;
        this.date = date;
    }

    public Information setInformationFromJSON(String json, int i) throws JSONException {
        this.position = i;
        if (json.equals("{}")) {
            return null;
        }
        JSONObject obj = new JSONObject(json);
        this.content = obj.getString("content");
        this.date = obj.getString("date");
        this.time = obj.getString("time");
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
