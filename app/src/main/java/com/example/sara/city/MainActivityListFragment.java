package com.example.sara.city;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private  ArrayList<City> cities;
    private CityAdapter cityAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        /*
        String[] values= new String[]{};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);
        */

        CityDbAdapter dbAdapter= new CityDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        cities=dbAdapter.getAllCities();
        dbAdapter.close();

        //cities= new ArrayList<City>();
        //cities.add(new City("This is a new city!", "This is the body!", "", "" ));

        cityAdapter= new CityAdapter(getActivity(), cities);

        setListAdapter(cityAdapter);

        //getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        //getListView().setDividerHeight(1);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        launchCityDetailActivity(position);

    }

    private void launchCityDetailActivity(int position){

        //Grab the city information associated with whatever city item we clicked on
        City city=(City) getListAdapter().getItem(position);

        //Create a new intent that launches our cityDetailActivity
        Intent intent = new Intent(getActivity(), CityDetailActivity.class);

        //Pass along the information of the city we cliked on to our cityDetailActivity
        intent.putExtra(MainActivity.CITY_NAME_EXTRA, city.getCity());
        intent.putExtra(MainActivity.CITY_LOCATION_EXTRA, city.getLocation());
        intent.putExtra(MainActivity.CITY_TEMPERATURE_EXTRA, city.getTemperature());
        intent.putExtra(MainActivity.CITY_ID_EXTRA, city.getCityId());

        startActivity(intent);

    }

}

