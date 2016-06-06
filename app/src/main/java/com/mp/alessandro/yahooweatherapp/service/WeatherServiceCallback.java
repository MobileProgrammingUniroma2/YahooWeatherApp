package com.mp.alessandro.yahooweatherapp.service;

import com.mp.alessandro.yahooweatherapp.data.Channel;

/**
 * Created by Alessandro on 09/05/2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
