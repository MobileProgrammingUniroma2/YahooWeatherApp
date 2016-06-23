package com.example.sara.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rvItem=(RecyclerView) findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);


        //Scroll Vertical or Horizontal
        //RecyclerView.LayoutManager manager= new LinearLayoutManager(getApplicationContext(),
        //        LinearLayoutManager.HORIZONTAL,
        //        false);

        //RecyclerView.LayoutManager manager=new GridLayoutManager(
        //        getApplicationContext(),
        //        2, //number of column
        //        GridLayoutManager.VERTICAL,
        //        false);

        RecyclerView.LayoutManager manager=new StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
        );

        rvItem.setLayoutManager(manager);

        ArrayList<Item> itemList=generateData();

        ItemAdapter adapter=new ItemAdapter(itemList,getApplicationContext());
        rvItem.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private ArrayList<Item> generateData(){
        ArrayList<Item> data=new ArrayList<>();
        for (int i=0; i<50;i++){
            Item item=new Item();
            item.id=i;
            if ( i % 4 == 0){
                item.desc="This is just a dummy text bla bla bla"+ Math.pow(i,i) + Math.pow(i,i);
            }
            item.desc="This is just a dummy text" +i;
            item.image="http://i1-news.softpedia-static.com/images/news2/Download-Yahoo-Weather-for-Android-2.jpg";
            data.add(item);


        }
        return data;
    }
}
