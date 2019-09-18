package com.segroup9.uta_stud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.segroup9.uta_stud.LoginRegister.UserRegister;

/**
 * Created by kevinyanogo on 11/26/18.
 */

public class EditCourseActivity extends AppCompatActivity implements View.OnClickListener{

    public Spinner courseName;
    public EditText courseTime;
    public EditText courseLocation;
    private ArrayAdapter coursesList;
    public DatabaseReference databaseReference;
    private AppCompatButton appCompatButtonValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_edit);

        courseName = (Spinner) findViewById(R.id.spinner_course_name);
        courseTime = (EditText) findViewById(R.id.editTextTimings);
        courseLocation = (EditText) findViewById(R.id.editTextClassroom);
        appCompatButtonValidate = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);

        coursesList = ArrayAdapter.createFromResource(EditCourseActivity.this, R.array.courses, android.R.layout.simple_spinner_dropdown_item);
        courseName.setAdapter(coursesList);

        appCompatButtonValidate.setOnClickListener(this);

    }

    public void updateCourse(){

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonSubmit:
                updateCourse();
                break;
        }
    }
}
