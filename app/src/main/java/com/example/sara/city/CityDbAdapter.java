package com.example.sara.city;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sara on 12/06/16.
 */
public class CityDbAdapter {

    private static final String DATABASE_NAME="city.db";
    private static final int DATABASE_VERSION=1;

    public static final String CITY_TABLE="city";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_CITY= "city";
    public static final String COLUMN_LOCATION="location";
    public static final String COLUMN_TEMPERATURE="temperature";
    public static final String COLUMN_IMAGE="image";

    private String[] allColumns={COLUMN_ID, COLUMN_CITY, COLUMN_LOCATION, COLUMN_TEMPERATURE, COLUMN_IMAGE};

    public static final String CREATE_TABLE_CITY="create table "+ CITY_TABLE +" ( "
            + COLUMN_ID + " integer primar key autoincrement, "
            + COLUMN_CITY + " text not null, "
            + COLUMN_LOCATION + " text not null, "
            + COLUMN_TEMPERATURE + " text not null, "
            + COLUMN_IMAGE + " );";

    private SQLiteDatabase sqlDB;
    private Context context;

    private CityDbHelper cityDbHelper;

    public CityDbAdapter(Context ctx){
        context = ctx;
    }

    public CityDbAdapter open () throws android.database.SQLException{
        cityDbHelper= new CityDbHelper(context);
        sqlDB = cityDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){cityDbHelper.close();}

    public ArrayList<City> getAllCities(){
        ArrayList<City> cities = new ArrayList<City>();

        //grab all of the information in our database for the cities in it
        Cursor cursor =sqlDB.query(CITY_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            City city = cursorToCity(cursor);
            cities.add(city);
        }

        //close our cursor required
        cursor.close();

        //return arrayList now filled with database city or cities in our database
        return cities;

    }

    private City cursorToCity(Cursor cursor) {
        City newCity=cursorToCity(cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4), cursor.getLong(0));
        return newCity;
    }


    private City cursorToCity(String string, String string1, String string2, String string3, long aLong) {
        return new  City(string, string1, string2, string3);
    }


    private static class CityDbHelper extends SQLiteOpenHelper{

        CityDbHelper(Context ctx) {
            super(ctx,DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //create city table
            db.execSQL(CREATE_TABLE_CITY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(CityDbHelper.class.getName(),
                    "Upgrading database from version "+ oldVersion + " to" + newVersion + ", which will destroy all old data");
            //destroys data
            db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE);
            onCreate(db);
        }
    }
}
