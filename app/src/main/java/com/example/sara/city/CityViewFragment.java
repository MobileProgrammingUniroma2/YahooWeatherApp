package com.example.sara.city;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
/**
 * A simple {@link Fragment} subclass.
 */
public class CityViewFragment extends Fragment {


    public CityViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout;
        fragmentLayout = inflater.inflate(R.layout.fragment_city_view, container, false);

        TextView city=(TextView) fragmentLayout.findViewById((R.id.viewCityName));
        TextView body=(TextView) fragmentLayout.findViewById(R.id.viewCityBody);
        ImageView icon=(ImageView) fragmentLayout.findViewById(R.id.viewCityIcon);

        Intent intent=getActivity().getIntent();

        city.setText(intent.getExtras().getString(MainActivity.CITY_NAME_EXTRA));
        body.setText(intent.getExtras().getString(MainActivity.CITY_LOCATION_EXTRA + MainActivity.CITY_TEMPERATURE_EXTRA));

        //icon.setImageResource();

        return fragmentLayout;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_city_view, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
   /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


}
