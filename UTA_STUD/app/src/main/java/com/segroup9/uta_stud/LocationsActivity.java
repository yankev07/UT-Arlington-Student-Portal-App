package com.segroup9.uta_stud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Helper.CoursesInformation;
import com.segroup9.uta_stud.Helper.ProfessorsInformation;
import com.segroup9.uta_stud.directionhelpers.FetchURL;
import com.segroup9.uta_stud.directionhelpers.TaskLoadedCallback;

/**
 * Created by kevinyanogo on 11/17/18.
 */

public class LocationsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    public TextView instructorName;
    public TextView instructorOffice;
    public TextView instructorEmail;
    public TextView instructorPhone;
    public TextView instructorHours;

    private GoogleMap mMap;
    private String[] instructor;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    private ImageButton getDirection;

    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    public ProfessorsInformation professorsInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        getDirection = findViewById(R.id.btnGetDirection);
        instructorName = (TextView) findViewById(R.id.instructorName);
        instructorOffice = (TextView) findViewById(R.id.instructorOffice);
        instructorEmail = (TextView) findViewById(R.id.instructorEmail);
        instructorPhone = (TextView) findViewById(R.id.instructorPhone);
        instructorHours = (TextView) findViewById(R.id.instructorHours);

        instructor = getInstructorName().split("-");

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LocationsActivity.this, instructor[0], Toast.LENGTH_LONG).show();
                //new FetchURL(LocationsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("professors");
        Query query = FirebaseDatabase.getInstance().getReference("professors").orderByChild(instructor[0]).equalTo(instructor[1]);
        query.addListenerForSingleValueEvent(valueEventListener);

        //32.733299,-97.113407 ERB
        //32.729012,-97.107986
        place1 = new MarkerOptions().position(new LatLng(32.733299, -97.113407)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(32.733299, -97.107986)).title("Location 2");
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    professorsInformation = snapshot.getValue(ProfessorsInformation.class);
                }

                displayInstructorInformation();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    public void displayInstructorInformation(){
        if(instructor[0].equals("professorName")){
            instructorName.setText(professorsInformation.professorName);
            instructorOffice.setText(professorsInformation.professorOffice);
            instructorEmail.setText(professorsInformation.professorEmail);
            instructorPhone.setText(professorsInformation.professorPhone);
            instructorHours.setText(professorsInformation.professorHours);
        }
        else{
            instructorName.setText(professorsInformation.assistantName);
            instructorOffice.setText(professorsInformation.assistantOffice);
            instructorEmail.setText(professorsInformation.assistantEmail);
            instructorPhone.setText(professorsInformation.assistantPhone);
            instructorHours.setText(professorsInformation.assistantHours);
        }
    }

    public String getInstructorName(){
        Intent intent = getIntent();
        String instructorName = intent.getStringExtra("instructorName");
        return instructorName;
    }
}
