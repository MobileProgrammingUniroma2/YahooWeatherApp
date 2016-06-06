package com.mp.alessandro.yahooweatherapp;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class MainActivity extends AppCompatActivity implements PlaceSelectionListener {// implements GoogleApiClient.ConnectionCallbacks,
    //GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;

    public static final String EXTRA_LAT_LNG = "com.mp.alessandro.yahooweatherapp.Extra_Lat_Lng";



    private GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds myBounds = new LatLngBounds(new LatLng(-0,0), new LatLng(0,0));

    private EditText myATView;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private ATAdapter mATAdapter;
    ImageView clearText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        // Retrieve the TextViews that will display details about the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {

        // Format the returned place's details and display them in the TextView.
        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), String.valueOf(place.getLatLng()),
                place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));

        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {

        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

        /*
        buildGoogleApiClient();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myATView = (EditText) findViewById(R.id.autocomplete_tv);
        clearText = (ImageView) findViewById(R.id.clear_text);
        mATAdapter = new ATAdapter(this, R.layout.search_row, mGoogleApiClient, myBounds, null);
        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myLinearLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLinearLayoutManager);
        myRecyclerView.setAdapter(mATAdapter);

        clearText.setOnClickListener(this);

        myATView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!s.toString().equals("") && mGoogleApiClient.isConnected()){
                    mATAdapter.getFilter().filter(s.toString());
                }
                else if(!mGoogleApiClient.isConnected()){
                    Toast.makeText(getApplicationContext(), "Google API Client not connected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myRecyclerView.addOnItemTouchListener(new RecyclerListener(this,new RecyclerListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position){
                final ATAdapter.ATPlace item = mATAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if(places.getCount() == 1){
                            //Do the things here on Click...
                            //Toast.makeText(getApplicationContext(),String.valueOf(places.get(0).getLatLng()), Toast.LENGTH_SHORT).show();
                            myATView.setText(places.get(0).getAddress());
                            Log.d("LAT_LNG", String.valueOf(places.get(0).getLatLng()));
                            myRecyclerView.setVisibility(View.GONE);

                            Intent intent = new Intent(getApplicationContext(),WeatherActivity.class);
                            intent.putExtra(MainActivity.EXTRA_LAT_LNG, String.valueOf(places.get(0).getLatLng()));
                            startActivity(intent);

                    }
                        else{

                        }
                    }
                });
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableStatement
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).addApi(Places.GEO_DATA_API).build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        if(v == clearText){
            myATView.setText("");
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if(!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }*/
}
