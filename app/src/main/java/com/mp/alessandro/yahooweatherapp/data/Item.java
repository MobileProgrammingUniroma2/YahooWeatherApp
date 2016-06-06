package com.mp.alessandro.yahooweatherapp.data;

import org.json.JSONObject;

/**
 * Created by Alessandro on 09/05/2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
