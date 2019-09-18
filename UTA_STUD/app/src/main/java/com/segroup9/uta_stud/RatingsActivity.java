package com.segroup9.uta_stud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.segroup9.uta_stud.Adapters.RatingsAdapter;
import com.segroup9.uta_stud.Adapters.StarRatingsAdapter;
import com.segroup9.uta_stud.Helper.InputValidation;
import com.segroup9.uta_stud.Helper.RatingInformation;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;

/**
 * Created by kevinyanogo on 10/20/18.
 */

public class RatingsActivity extends AppCompatActivity implements View.OnClickListener {

    int[] images = {R.drawable.emoticon_happy, R.drawable.emoticon_sceptic, R.drawable.emoticon_crying};
    int[] stars = {R.drawable.stars5, R.drawable.stars4, R.drawable.stars3, R.drawable.stars2, R.drawable.star1};
    String[] names = {"Easy", "Medium", "Difficult"};
    String[] grade = {"5/5", "4/5", "3/5", "2/5", "1/5"};

    private DatabaseSQL myDB;
    public RatingsAdapter ratingsAdapter;
    public StarRatingsAdapter starRatingsAdapter;
    private Spinner spinnerCourse;
    private Spinner spinnerContent;
    private Spinner spinnerTeaching;
    private Spinner spinnerHomeworks;
    private Spinner spinnerExams;
    private Spinner spinnerOverall;
    private Spinner spinnerRecommendation;
    private ArrayList<String> coursesList;
    private ArrayList<String> professorsList;
    public ArrayAdapter spinnerCourseAdapter;
    public ArrayAdapter feedbackAdapter;
    private TextView professorName;
    public ArrayAdapter spinnerRecommendationAdapter;
    private TextInputLayout textInputLayoutDescription;
    private TextInputEditText textInputEditTextDescription;

    private AppCompatButton appCompatButtonSubmit;
    private InputValidation inputValidation;
    private FirebaseAuth firebaseAuth;

    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        myDB = new DatabaseSQL(RatingsActivity.this);
        //ratingsAdapter = new RatingsAdapter(this, names, images);
        starRatingsAdapter = new StarRatingsAdapter(this, grade, stars);
        coursesList = myDB.displayEnrolledCourses();
        professorsList = myDB.displayProfessorsNames();

        spinnerCourse = (Spinner) findViewById(R.id.spinner_category);
        professorName = (TextView) findViewById(R.id.textViewProfessorName);
        spinnerContent = (Spinner) findViewById(R.id.spinnerContent);
        spinnerTeaching = (Spinner) findViewById(R.id.spinnerTeaching);
        spinnerHomeworks = (Spinner) findViewById(R.id.spinnerHomeworks);
        spinnerExams = (Spinner) findViewById(R.id.spinnerExams);
        spinnerOverall = (Spinner) findViewById(R.id.spinnerOverall);
        spinnerRecommendation = (Spinner) findViewById(R.id.spinnerWouldRecommend);
        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);
        textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutDescription);
        textInputEditTextDescription = (TextInputEditText) findViewById(R.id.textInputEditTextDescription);

        spinnerRecommendationAdapter = ArrayAdapter.createFromResource(RatingsActivity.this, R.array.yesNo, android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coursesList);
        feedbackAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, names);

        spinnerCourse.setAdapter(spinnerCourseAdapter);
        spinnerContent.setAdapter(feedbackAdapter);
        spinnerTeaching.setAdapter(feedbackAdapter);
        spinnerHomeworks.setAdapter(feedbackAdapter);
        spinnerExams.setAdapter(feedbackAdapter);
        spinnerOverall.setAdapter(starRatingsAdapter);
        spinnerRecommendation.setAdapter(spinnerRecommendationAdapter);

        findViewById(R.id.relativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                professorName.setText(professorsList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        appCompatButtonSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonSubmit:
                submitFeedback();
                break;
        }
    }

    public int getRating(String rating){
        switch(rating){
            case "5/5":
                return 5;
            case "4/5":
                return 4;
            case "3/5":
                return 3;
            case "2/5":
                return 2;
            default:
                return 1;
        }
    }

    public boolean submitFeedback(){

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String courseName = spinnerCourse.getSelectedItem().toString().trim();
        String profName = professorName.getText().toString().trim();
        String contentRating = spinnerContent.getSelectedItem().toString().trim();
        String teachingRating = spinnerTeaching.getSelectedItem().toString().trim();
        String homeworksRating = spinnerHomeworks.getSelectedItem().toString().trim();
        String examsRating = spinnerExams.getSelectedItem().toString().trim();
        int overall = getRating(spinnerOverall.getSelectedItem().toString().trim());
        String wouldRecommend = spinnerRecommendation.getSelectedItem().toString().trim();
        String personalComments = textInputEditTextDescription.getText().toString().trim();
        String author = user.getUid().substring(22);

        inputValidation = new InputValidation(RatingsActivity.this);

        if(personalComments.isEmpty()){
            Toast.makeText(this, "Please add a comment...", Toast.LENGTH_LONG).show();
            return false;
        }

        RatingInformation ratingInformation = new RatingInformation(courseName, profName, contentRating, teachingRating, homeworksRating, examsRating, wouldRecommend, personalComments, overall, author);

        databaseReference = FirebaseDatabase.getInstance().getReference("ratings");
        databaseReference.child(courseName + " - " + author).setValue(ratingInformation);

        Toast.makeText(this, "Thanks for your feedback...", Toast.LENGTH_LONG).show();
        this.finish();
        startActivity(new Intent(RatingsActivity.this, MainActivity.class));

        return true;
    }
}
