package com.example.sara.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sara on 15/06/16.
 */
public class CityAdapter extends ArrayAdapter<City> {

    public static class ViewHolder{
        TextView city;
        TextView body;
        ImageView cityIcon;
    }
    public CityAdapter(Context context, ArrayList<City> cities){
        super(context,0,cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        City city=getItem(position);

        //create a new viewholder
        ViewHolder viewHolder;

        //Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null) {

            //If we don't have a view that is being used create one, and make sure you create
            //a view holder along with it to save our view references to
            viewHolder= new ViewHolder();

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent,false);

            //set our views to our view holder so that we no longer have to go back and use find view by id every time we have a new row
            viewHolder.city=(TextView) convertView.findViewById(R.id.listItemCityName);
            viewHolder.body=(TextView) convertView.findViewById(R.id.listItemCityBody);
            viewHolder.cityIcon=(ImageView) convertView.findViewById(R.id.listItemCityImg);

            //use set tag to remember our view holder which is holding our references to our widgets
            convertView.setTag(viewHolder);

        }else{

            //we already have a view so just go to our viewholder and grab the widgets from it
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //Grab references of view so we can populate them with specific city row data
        //TextView cityName=(TextView) convertView.findViewById(R.id.listItemCityName);
        //TextView cityText=(TextView) convertView.findViewById(R.id.listItemCityBody);
        //ImageView cityIcon=(ImageView) convertView.findViewById(R.id.listItemCityImg);

        //Fill each new referenced view with data associated with city it's referencing
        //cityName.setText(city.getCity());
        //cityText.setText(city.getLocation() + " " + city.getTemperature());
        //cityIcon.setImageResource(Integer.parseInt(city.getImage()));

        //Ppopulate the data into the template view using the data object
        viewHolder.city.setText(city.getCity());
        viewHolder.body.setText(city.getLocation() + city.getTemperature());
        //viewHolder.cityIcon.setImageResource(city.getImage());

        //Now that we modified the view to display appropriate data, return it so it will be displayed
        return convertView;

    }
}
