package com.mp.alessandro.yahooweatherapp;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Un fragment è un comportamento o una porzione di UI in un'Activity
 * L'activity può essere composta da più fragment, un fragment può essere riutilizzato in più activity
 * Un fragment ha una vita propria, ha un suo input e può essere aggiunto o rimosso mentre l'Acitivity è in esecuzione
 * E' un po come una subActivity
 * Questo è molto utile nel caso di applicazione smartphone vs tablet perché
 * magari in uno smartphone riesco a tenere un solo fragment nell'activity, mentre in uno schermo tablet riesco a tenerne 2
 * La cosa più utile dei fragment è che puoi riutilizzare un sacco di codice
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;



    /*
    public MainActivityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //Callback eseguita

        /*
        String[] values = new String[]{"Android","iPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows", "MAC OS X", "Linux"
        "OS/2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                values);
        1) Contesto in cui siamo
        2) Layout in cui ci deve stare una textView per visualizzare le stringhe (ne abbiamo usato uno già pronto se no potevamo crearlo)
        3) Array di roba da visualizzare

        setListAdapter(adapter);*/

        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(new Note("Note Title","Body of a note", Note.Category.PERSONAL));

        noteAdapter = new NoteAdapter(getActivity(),notes);

        setListAdapter(noteAdapter);

        //getListView().setDivider(ContextCompat.getDrawable(getActivity(),android.R.color.black));
        //getListView().setDividerHeight(1);

        registerForContextMenu(getListView());
        //Registro una certa view a un menu in modo tale che quando clicco sulla lista mi si attiva il codice onCreateContextMenu
    }

    @Override
    public void onListItemClick(ListView l,View v,int position, long id){
        super.onListItemClick(l, v, position, id);

        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        //give me the position of whatever note i long pressed on
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;


        //quale item è stato cliccato dal menu
        switch (item.getItemId()){
            case R.id.edit :
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Menu Clicks", "We press edit!");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position){

        //grab the note information associated with whatever note item we clicked on
        Note note = (Note) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);

        //pass along the information of the note we clicked on to our noteDetailActivity
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getNoteId());
        //unique keys

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
        }


        startActivity(intent);
    }

}
