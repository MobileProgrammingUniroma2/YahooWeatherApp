package com.mp.alessandro.yahooweatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
/**
 * Created by Alessandro on 10/05/2016.
 */
public class ATAdapter extends RecyclerView.Adapter<ATAdapter.PredictionHolder> implements Filterable{

    private ArrayList<ATPlace> myResultList;
    private GoogleApiClient myApiClient;
    private LatLngBounds myBounds;
    AutocompleteFilter myACFilter;

    private Context myContext;
    private int layout;

    public ATAdapter(Context context, int resource, GoogleApiClient googleApiClient, LatLngBounds bounds, AutocompleteFilter filter){

        myContext = context;
        layout = resource;
        myApiClient = googleApiClient;
        myBounds = bounds;
        myACFilter = filter;
    }

    public void setMyBounds(LatLngBounds bounds){ myBounds = bounds;}

    @Override
    public Filter getFilter(){
        Filter filter = new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint){
                FilterResults results = new FilterResults();
                if(constraint != null){
                    myResultList = getAutocomplete(constraint);
                    if(myResultList != null){
                        results.values = myResultList;
                        results.count = myResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results){
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else{

                }
            }
        };
        return filter;
    }

    private ArrayList<ATPlace> getAutocomplete(CharSequence constraint){
        if(myApiClient.isConnected()){
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi.getAutocompletePredictions(myApiClient,constraint.toString(), myBounds, myACFilter);

            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            final Status status = autocompletePredictions.getStatus();
            if(!status.isSuccess()){
                Toast.makeText(myContext, "Error contacting API: " + status.toString(), Toast.LENGTH_SHORT).show();
                Log.e("","Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i("", "Query completed. Received " + autocompletePredictions.getCount() + " predictions.");

            Iterator<AutocompletePrediction>  iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());

            while(iterator.hasNext()){
                AutocompletePrediction prediction = iterator.next();
                //noinspection deprecation
                resultList.add(new ATPlace(prediction.getPlaceId(), prediction.getDescription()));
            }

            autocompletePredictions.release();

            return resultList;
        }

        Log.e("","Google API client is not connected for autocomplete query.");
        return null;
    }


    @Override
    public ATAdapter.PredictionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(layout, viewGroup, false);
        PredictionHolder mPredictionHolder = new PredictionHolder(convertView);
        return mPredictionHolder;
    }

    @Override
    public void onBindViewHolder(ATAdapter.PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.myPrediction.setText(myResultList.get(i).description);
    }

    @Override
    public int getItemCount() {
        return myResultList!=null ? myResultList.size() : 0;
    }

    public ATPlace getItem(int position){ return myResultList.get(position);}

    public class PredictionHolder extends RecyclerView.ViewHolder{
        private TextView myPrediction;
        private RelativeLayout myRow;

        public PredictionHolder(View itemView){
            super(itemView);
            myPrediction = (TextView) itemView.findViewById(R.id.address);
            myRow = (RelativeLayout) itemView.findViewById(R.id.autocomplete_row);
        }
    }

    public class ATPlace{
        public CharSequence placeId;
        public CharSequence description;

        ATPlace(CharSequence placeId, CharSequence description){
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString(){return description.toString();}
    }

}
