package com.segroup9.uta_stud.Fragments;

import android.app.Dialog;
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
import com.segroup9.uta_stud.Adapters.UserRatingsAdapter;
import com.segroup9.uta_stud.Helper.RatingInformation;
import com.segroup9.uta_stud.MainActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.RatingsActivity;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;
import java.util.List;

import static com.segroup9.uta_stud.R.id.recyclerView;

/**
 * Created by kevinyanogo on 10/19/18.
 */

public class FragmentRatings extends android.support.v4.app.Fragment implements UserRatingsAdapter.ItemClickListener {

    private View view;
    private RecyclerView recyclerView;
    private UserRatingsAdapter adapter;
    private List<RatingInformation> coursesList;
    public ArrayList<String> userInfo;
    private DatabaseSQL myDB;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ratings,null);

        recyclerView = (RecyclerView) view.findViewById(R.id.courses_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        coursesList = new ArrayList<>();
        adapter = new UserRatingsAdapter(getContext(), coursesList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), RatingsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        myDB = new DatabaseSQL(getActivity());
        userInfo = myDB.fetchUserInfo();

        databaseReference = FirebaseDatabase.getInstance().getReference("ratings");
        Query query1 = FirebaseDatabase.getInstance().getReference().child("ratings");
        query1.addListenerForSingleValueEvent(valueEventListener1);

        adapter.setClickListener(this);
        adapter.setLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myDB.adminPrivilleges()){
                showDeleteDialog(adapter.getItem(i).courseName, i);
                }
                else {
                    Toast.makeText(getActivity(), "Only Admins can delete...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        return view;
    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    RatingInformation course = snapshot.getValue(RatingInformation.class);
                    coursesList.add(course);
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
        //Toast.makeText(getActivity(), adapter.getItem(position).courseName, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(View view, int position){
        //Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_LONG).show();
    }

    public void showDeleteDialog(String courseName, final int position){

        final Dialog alertDialog = new Dialog(getActivity());
        final String thisCourse = courseName.toString();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_delete_comment);
        alertDialog.setTitle("My Custom Dialog");

        TextView courseTitle = (TextView)alertDialog.findViewById(R.id.courseName);
        Button Cancel = (Button)alertDialog.findViewById(R.id.button_cancel);
        Button Yes = (Button)alertDialog.findViewById(R.id.button_delete);

        courseTitle.setText(thisCourse);
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
                databaseReference = FirebaseDatabase.getInstance().getReference("ratings").child(adapter.getItem(position).courseName + " - " + adapter.getItem(position).author);
                databaseReference.removeValue();
                Toast.makeText(getActivity(), "Comment deleted...", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        alertDialog.show();
    }
}
