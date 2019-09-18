package com.segroup9.uta_stud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Helper.EventInformation;
import com.segroup9.uta_stud.LoginRegister.UserLogin;
import com.segroup9.uta_stud.database.DatabaseSQL;
import com.squareup.picasso.Picasso;

/**
 * Created by kevinyanogo on 11/26/18.
 */

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView eventImage;
    private TextView eventTitle;
    private TextView eventDate;
    private TextView eventStartTime;
    private TextView eventEndTime;
    private TextView eventDetails;
    private DatabaseSQL myDB;
    DatabaseReference databaseReference;
    private AppCompatButton appCompatButtonValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);

        myDB = new DatabaseSQL(EventDetailsActivity.this);

        eventImage = (ImageView) findViewById(R.id.eventImage);
        eventTitle = (TextView) findViewById(R.id.editTextTitle);
        eventDate = (TextView) findViewById(R.id.editTextDate);
        eventStartTime = (TextView) findViewById(R.id.editTextStartTime);
        eventEndTime = (TextView) findViewById(R.id.editTextEndTime);
        eventDetails = (TextView) findViewById(R.id.editTextDescription);
        appCompatButtonValidate = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);

        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        Query query = FirebaseDatabase.getInstance().getReference().child("events").orderByChild("eventTitle").equalTo(getEventTitle());
        query.addListenerForSingleValueEvent(valueEventListener);
        appCompatButtonValidate.setOnClickListener(this);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    EventInformation event = snapshot.getValue(EventInformation.class);
                    Picasso.get()
                            .load(event.getImageUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(eventImage);
                    eventTitle.setText(event.eventTitle);
                    eventDate.setText(event.eventDate);
                    eventStartTime.setText(event.startTime);
                    eventEndTime.setText(event.endTime);
                    eventDetails.setText(event.eventDetails);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonSubmit:
                myDB.saveEventInfo(eventTitle.getText().toString().trim(), eventDate.getText().toString().trim(), eventStartTime.getText().toString().trim(), eventEndTime.getText().toString().trim());
                Toast.makeText(EventDetailsActivity.this, "Event added to announcements...", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    public String getEventTitle(){
        Intent intent = getIntent();
        String eventTitle = intent.getStringExtra("eventTitle");
        return eventTitle;
    }
}
