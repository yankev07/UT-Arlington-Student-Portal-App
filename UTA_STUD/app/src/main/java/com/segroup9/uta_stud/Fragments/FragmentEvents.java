package com.segroup9.uta_stud.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Adapters.CourseGridAdapter;
import com.segroup9.uta_stud.Adapters.EventAdapter;
import com.segroup9.uta_stud.Adapters.LocationAdapter;
import com.segroup9.uta_stud.Adapters.UserRatingsAdapter;
import com.segroup9.uta_stud.EventDetailsActivity;
import com.segroup9.uta_stud.EventsActivity;
import com.segroup9.uta_stud.Helper.EventInformation;
import com.segroup9.uta_stud.Helper.RatingInformation;
import com.segroup9.uta_stud.LocationsActivity;
import com.segroup9.uta_stud.MainActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.RatingsActivity;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinyanogo on 9/21/18.
 */

public class FragmentEvents extends android.support.v4.app.Fragment implements EventAdapter.ItemClickListener {

    private View view;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<EventInformation> eventsList;

    private DatabaseSQL myDB;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events,null);

        myDB = new DatabaseSQL(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventsList = new ArrayList<>();
        adapter = new EventAdapter(getContext(), eventsList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myDB.adminPrivilleges()){
                Intent intent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                getActivity().startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Only reserved for admins", Toast.LENGTH_LONG).show();
                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        Query query = FirebaseDatabase.getInstance().getReference().child("events");
        query.addListenerForSingleValueEvent(valueEventListener);

        adapter.setClickListener(this);
        adapter.setLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myDB.adminPrivilleges()){
                showDeleteDialog(adapter.getItem(i).eventTitle, i);
                }
                else {
                    Toast.makeText(getActivity(), "Only Admins can delete...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    EventInformation event = snapshot.getValue(EventInformation.class);
                    eventsList.add(event);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onItemClick(View view, int position){
        // Show Event Details
        Intent intent = new Intent(getActivity().getBaseContext(), EventDetailsActivity.class);
        intent.putExtra("eventTitle", adapter.getItem(position).eventTitle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position){
        //Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_LONG).show();
    }

    public void showDeleteDialog(String eventName, final int position){

        final Dialog alertDialog = new Dialog(getActivity());
        final String thisEvent = eventName.toString();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_delete_event);
        alertDialog.setTitle("My Custom Dialog");

        TextView eventTitle = (TextView)alertDialog.findViewById(R.id.eventTitle);
        Button Cancel = (Button)alertDialog.findViewById(R.id.button_cancel);
        Button Yes = (Button)alertDialog.findViewById(R.id.button_delete);

        eventTitle.setText(thisEvent);
        Cancel.setEnabled(true);
        Yes.setEnabled(true);

        Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                databaseReference = FirebaseDatabase.getInstance().getReference("events").child(adapter.getItem(position).eventTitle);
                databaseReference.removeValue();
                Toast.makeText(getActivity(), "Event removed...", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        alertDialog.show();
    }

    /*public void refreshFragment(){
        Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.);
        if (currentFragment instanceof FragmentEvents) {
            FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();}
        }
    }*/
}
