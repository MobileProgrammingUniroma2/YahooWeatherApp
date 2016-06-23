package com.example.sara.city;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CityDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

    }

    private void createAndFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        CityViewFragment cityViewFragment= new CityViewFragment();
        fragmentTransaction.add(R.id.city_container, cityViewFragment, "CITY_VIEW_FRAGMENT");

        fragmentTransaction.commit();

    }
}
