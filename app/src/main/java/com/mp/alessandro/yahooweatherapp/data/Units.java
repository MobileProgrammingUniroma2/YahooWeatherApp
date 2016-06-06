package com.mp.alessandro.yahooweatherapp.data;

import org.json.JSONObject;

/**
 * Created by Alessandro on 09/05/2016.
 */
public class Units implements JSONPopulator{
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
