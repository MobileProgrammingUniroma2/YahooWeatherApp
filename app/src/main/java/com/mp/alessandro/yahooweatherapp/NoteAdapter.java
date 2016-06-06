package com.mp.alessandro.yahooweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alessandro on 22/04/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    //Commit 
    public static class ViewHolder{
        TextView title;
        TextView note;
        ImageView noteIcon;

    }

    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context,0,notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        Note note = getItem(position);

        //create a new ViewHolder
        ViewHolder viewHolder;

        //Check if existing view is being reused, otherwise inflate a new view from custom row layout
        //Ogni view ha una certa row, si possono anche riutilizzare
        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);

            //set our views to our view holder so that we no longer have to go back and use find view by id
            //every time we have a new row
            viewHolder.title = (TextView) convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.note = (TextView) convertView.findViewById(R.id.listItemNoteBody);
            viewHolder.noteIcon = (ImageView) convertView.findViewById(R.id.listItemNoteImg);

            //use set tag to remember our view holder which is holding our references to our widgets
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Grab references of views so we can populate them with specific note row data

        //TextView noteTitle = (TextView) convertView.findViewById(R.id.listItemNoteTitle);
        //TextView noteText = (TextView) convertView.findViewById(R.id.listItemNoteBody);
        //ImageView noteIcon = (ImageView) convertView.findViewById(R.id.listItemNoteImg);


        //Fill each new referenced view with data associated with note it's referencing
        //noteTitle.setText(note.getTitle());
        //noteText.setText((note.getMessage()));
        //noteIcon.setImageResource(note.getAssociatedDrawable());

        viewHolder.title.setText(note.getTitle());
        viewHolder.note.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());

        //now that we modified the view to display appropriate data
        //return it so it will be displayed

        return convertView;

    }
}
