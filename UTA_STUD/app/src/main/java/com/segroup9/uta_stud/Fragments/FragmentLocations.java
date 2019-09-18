package com.segroup9.uta_stud.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Adapters.CourseDiscussionAdapter;
import com.segroup9.uta_stud.Adapters.LocationAdapter;
import com.segroup9.uta_stud.Adapters.UserRatingsAdapter;
import com.segroup9.uta_stud.Chat.ChatActivity;
import com.segroup9.uta_stud.CoursesActivity;
import com.segroup9.uta_stud.Helper.ContactInformation;
import com.segroup9.uta_stud.Helper.CoursesInformation;
import com.segroup9.uta_stud.Helper.ProfessorsInformation;
import com.segroup9.uta_stud.LocationsActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinyanogo on 10/19/18.
 */

public class FragmentLocations extends android.support.v4.app.Fragment implements LocationAdapter.ItemClickListener{

    private View view;

    private LocationAdapter instructorAdapter;
    private ProfessorsInformation professorInformation;
    public RecyclerView instructorsRecyclerView;
    private List<String> professorsList;
    private List<String> instructorsList;

    private DatabaseSQL myDB;
    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_locations,null);

        myDB = new DatabaseSQL(getActivity());

        instructorsRecyclerView = (RecyclerView) view.findViewById(R.id.instructors_recyclerView);

        instructorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        professorsList = new ArrayList<>();
        instructorsList = new ArrayList<>();

        instructorsList = myDB.displayProfessorsNames();
        professorsList = myDB.displayProfessorsNames();

        instructorAdapter = new LocationAdapter(getContext(), instructorsList);

        instructorsRecyclerView.setAdapter(instructorAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("professors");
        Query query1 = FirebaseDatabase.getInstance().getReference("professors").orderByChild("professorName").equalTo(professorsList.get(0));
        Query query2 = FirebaseDatabase.getInstance().getReference("professors").orderByChild("professorName").equalTo(professorsList.get(1));
        Query query3 = FirebaseDatabase.getInstance().getReference("professors").orderByChild("professorName").equalTo(professorsList.get(2));
        query1.addListenerForSingleValueEvent(valueEventListener1);
        query2.addListenerForSingleValueEvent(valueEventListener2);
        query3.addListenerForSingleValueEvent(valueEventListener3);

        instructorAdapter.setClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(View view, int position){
        if(isProfessor(instructorAdapter.getItem(position).trim())){
            Intent intent = new Intent(getActivity().getBaseContext(), LocationsActivity.class);
            intent.putExtra("instructorName", "professorName-" + instructorAdapter.getItem(position).trim());
            getActivity().startActivity(intent);
        }
        else{
            Intent intent = new Intent(getActivity().getBaseContext(), LocationsActivity.class);
            intent.putExtra("instructorName", "assistantName-" + instructorAdapter.getItem(position).trim());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onItemLongClick(View view, int position){
        //Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_LONG).show();
    }

    public boolean isProfessor(String instructorName){

        for(int i = 0; i < professorsList.size(); i++){
            if(professorsList.get(i).equals(instructorName)){
                return true;
            }
        }
        return false;
    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    professorInformation = snapshot.getValue(ProfessorsInformation.class);
                    instructorsList.add(professorInformation.assistantName);
                }
                instructorAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    professorInformation = snapshot.getValue(ProfessorsInformation.class);
                    instructorsList.add(professorInformation.assistantName);
                }
                instructorAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener3 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    professorInformation = snapshot.getValue(ProfessorsInformation.class);
                    instructorsList.add(professorInformation.assistantName);
                }
                instructorAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
