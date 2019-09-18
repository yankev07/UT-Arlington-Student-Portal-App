package com.segroup9.uta_stud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Chat.MainDiscussionActivity;
import com.segroup9.uta_stud.Helper.CoursesInformation;
import com.segroup9.uta_stud.database.DatabaseSQL;

/**
 * Created by kevinyanogo on 11/15/18.
 */

public class CoursesActivity extends AppCompatActivity {

    private DatabaseSQL myDB;
    public TextView classroom;
    public TextView course_time;
    public TextView course_name;
    public TextView professor_name;
    public CoursesInformation course;
    public ImageButton syllabusButton;

    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        myDB = new DatabaseSQL(CoursesActivity.this);
        classroom = (TextView) findViewById(R.id.classroom);
        course_time = (TextView) findViewById(R.id.course_time);
        course_name = (TextView) findViewById(R.id.course_name);
        professor_name = (TextView) findViewById(R.id.professor_name);
        syllabusButton = (ImageButton) findViewById(R.id.syllabus_button);

        databaseReference = FirebaseDatabase.getInstance().getReference("courses");
        Query query = FirebaseDatabase.getInstance().getReference("courses").orderByChild("courseName").equalTo(getCourseName());
        query.addListenerForSingleValueEvent(valueEventListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myDB.adminPrivilleges()){
                Intent intent = new Intent(CoursesActivity.this, EditCourseActivity.class);
                startActivity(intent);
                }
                else{
                    Toast.makeText(CoursesActivity.this, "Only Admin allowed...", Toast.LENGTH_LONG).show();
                }
            }
        });

        syllabusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display Syllabus
                startActivity(new Intent(CoursesActivity.this, SyllabusActivity.class));
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    course = snapshot.getValue(CoursesInformation.class);
                }

                displayCourseDetails();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void displayCourseDetails(){

        classroom.setText(course.courseRoom);
        course_time.setText(course.courseTiming);
        course_name.setText(course.courseName);
        professor_name.setText(myDB.getProfessorName(course.courseName));
    }

    public String getCourseName(){
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("course");
        return courseName;
    }
}
