package com.segroup9.uta_stud.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Adapters.AnouncementAdapter;
import com.segroup9.uta_stud.Adapters.CourseAdapter;
import com.segroup9.uta_stud.EventDetailsActivity;
import com.segroup9.uta_stud.Helper.UserInformation;
import com.segroup9.uta_stud.MainActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;

/**
 * Created by kevinyanogo on 9/21/18.
 */

public class FragmentHome extends android.support.v4.app.Fragment implements CourseAdapter.ItemClickListener, AnouncementAdapter.ItemClickListener {

    private View view;
    public ArrayList<String> coursesList;
    public ArrayList<String> eventsList;
    public RecyclerView recyclerView;
    public RecyclerView eventsRecyclerView;
    public ImageButton chatButton;
    public CourseAdapter adapter;
    public AnouncementAdapter eventAdapter;

    public TextView firstName;
    public TextView lastName;
    public TextView email;
    public TextView studentID;
    public TextView status;
    private DatabaseSQL myDB;

    DatabaseReference databaseReference;
    UserInformation userInformation;
    FirebaseUser user;
    String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,null);

        firstName = (TextView) view.findViewById(R.id.textViewFirstName);
        lastName = (TextView) view.findViewById(R.id.textViewLastName);
        email = (TextView) view.findViewById(R.id.textViewEmail);
        studentID = (TextView) view.findViewById(R.id.textViewStudentID);
        status = (TextView) view.findViewById(R.id.textViewStatus);
        chatButton = (ImageButton) view.findViewById(R.id.icon_chat);
        recyclerView = (RecyclerView) view.findViewById(R.id.courses_recyclerView);
        eventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recyclerView);
        coursesList = new ArrayList<String>();
        eventsList = new ArrayList<String>();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        myDB = new DatabaseSQL(getActivity());
        eventsList = myDB.displayEvents();
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapter = new AnouncementAdapter(getActivity(), eventsList);
        eventsRecyclerView.setAdapter(eventAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstNameString = dataSnapshot.child(uid).child("firstName").getValue(String.class);
                String lastNameString = dataSnapshot.child(uid).child("lastName").getValue(String.class);
                String idNumberString = dataSnapshot.child(uid).child("studentID").getValue(String.class);
                String emailString = dataSnapshot.child(uid).child("email").getValue(String.class);
                String course1 = dataSnapshot.child(uid).child("course1").getValue(String.class);
                String course2 = dataSnapshot.child(uid).child("course2").getValue(String.class);
                String course3 = dataSnapshot.child(uid).child("course3").getValue(String.class);
                String professor1 = dataSnapshot.child(uid).child("professor1").getValue(String.class);
                String professor2 = dataSnapshot.child(uid).child("professor2").getValue(String.class);
                String professor3 = dataSnapshot.child(uid).child("professor3").getValue(String.class);
                String userStatus = dataSnapshot.child(uid).child("status").getValue(String.class);

                myDB.saveUserInfo(uid, firstNameString, lastNameString, idNumberString, emailString, course1, course2, course3, professor1, professor2, professor3, userStatus);

                firstName.setText(firstNameString);
                lastName.setText(lastNameString);
                email.setText(emailString);
                studentID.setText(idNumberString);
                status.setText(userStatus);

                coursesList.add(course1);
                coursesList.add(course2);
                coursesList.add(course3);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CourseAdapter(getActivity(), coursesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Network ERROR. Please check your connection", Toast.LENGTH_LONG).show();
            }
        });

        eventAdapter.setClickListener(this);
        eventAdapter.setLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteDialog(eventsList.get(i), i);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position){
        //Toast.makeText(getActivity(), eventsList.get(position), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getBaseContext(), EventDetailsActivity.class);
        intent.putExtra("eventTitle", eventsList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position){
        //Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_LONG).show();
    }

    public void showDeleteDialog(final String eventName, final int position){

        final Dialog alertDialog = new Dialog(getActivity());
        final String thisEvent = eventName.toString();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_remove_announcement);
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
                myDB.deleteAnnouncement(eventName);
                eventsList.remove(position);
                eventAdapter.notifyItemRemoved(position);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
