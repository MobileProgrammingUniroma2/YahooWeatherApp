package com.example.sara.city;

import android.media.Image;

/**
 * Created by Sara on 15/06/16.
 */
public class City {

    private long cityId;
    private  String city;
    private  String location;
    private String temperature;
    private String image;

    public City(String city, String location, String temperature, String image) {
        this.city = city;
        this.location = location;
        this.temperature = temperature;
        this.image = image;
        this.cityId = 0;
    }

    public City(String city, String location, String temperature, String image, long cityId) {
        this.city = city;
        this.location = location;
        this.temperature = temperature;
        this.image = image;
        this.cityId = cityId;
    }

    public String getCity() {return city;}

    public String getLocation() {return location;}

    public String getTemperature() {return temperature;}

    public String getImage() {return image;}

    public long getCityId() {return cityId;}

    //public int getAssociatedDrawable(){return temperatureToDrawable(temperature)};
    //public static int temperatureToDrawable(String cityTemperaure){

        //switch (cityTemperaure){
            //return android.R.drawable
        //}

        //return "immagine";
    //}
}
