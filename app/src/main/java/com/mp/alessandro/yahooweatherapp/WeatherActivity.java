package com.mp.alessandro.yahooweatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mp.alessandro.yahooweatherapp.data.Channel;
import com.mp.alessandro.yahooweatherapp.data.Item;
import com.mp.alessandro.yahooweatherapp.service.WeatherServiceCallback;
import com.mp.alessandro.yahooweatherapp.service.YahooWeatherService;

import org.w3c.dom.Text;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback{

    public ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

        Intent intent = getIntent();

        service.refreshWeather(intent.getExtras().getString(MainActivity.EXTRA_LAT_LNG));

        //service.refreshWeather("Sydney, Australia");
    }

    @Override
    public void serviceSuccess(Channel channel){
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation") Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0 " + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception){
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        Log.d("SERVICE_FAILURE",exception.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
