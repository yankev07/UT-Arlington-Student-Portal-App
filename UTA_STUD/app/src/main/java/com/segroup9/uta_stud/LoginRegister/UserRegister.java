package com.segroup9.uta_stud.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.segroup9.uta_stud.Helper.InputValidation;
import com.segroup9.uta_stud.Helper.UserInformation;
import com.segroup9.uta_stud.R;

/**
 * Created by kevinyanogo on 9/28/18.
 */

public class UserRegister extends AppCompatActivity implements View.OnClickListener{


    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutStudentID;
    private TextInputLayout textInputLayoutNetID;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextStudentID;
    private TextInputEditText textInputEditTextNetID;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private Spinner spinnerCourse1;
    private Spinner spinnerCourse2;
    private Spinner spinnerCourse3;
    private Spinner spinnerProfessor1;
    private Spinner spinnerProfessor2;
    private Spinner spinnerProfessor3;

    private ProgressBar progressBar;
    private ArrayAdapter coursesList;
    private ArrayAdapter professorsList;
    private InputValidation inputValidation;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
        textInputLayoutStudentID = (TextInputLayout) findViewById(R.id.textInputLayoutStudentID);
        textInputLayoutNetID = (TextInputLayout) findViewById(R.id.textInputLayoutNetID);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (TextInputEditText) findViewById(R.id.textInputEditTextLastName);
        textInputEditTextStudentID = (TextInputEditText) findViewById(R.id.textInputEditTextStudentID);
        textInputEditTextNetID = (TextInputEditText) findViewById(R.id.textInputEditTextNetID);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        spinnerCourse1 = (Spinner) findViewById(R.id.spinner_course1);
        spinnerCourse2 = (Spinner) findViewById(R.id.spinner_course2);
        spinnerCourse3 = (Spinner) findViewById(R.id.spinner_course3);
        spinnerProfessor1 = (Spinner) findViewById(R.id.spinner_professor1);
        spinnerProfessor2 = (Spinner) findViewById(R.id.spinner_professor2);
        spinnerProfessor3 = (Spinner) findViewById(R.id.spinner_professor3);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        coursesList = ArrayAdapter.createFromResource(UserRegister.this, R.array.courses, android.R.layout.simple_spinner_dropdown_item);
        professorsList = ArrayAdapter.createFromResource(UserRegister.this, R.array.professors, android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse1.setAdapter(coursesList);
        spinnerCourse2.setAdapter(coursesList);
        spinnerCourse3.setAdapter(coursesList);
        spinnerProfessor1.setAdapter(professorsList);
        spinnerProfessor2.setAdapter(professorsList);
        spinnerProfessor3.setAdapter(professorsList);

        findViewById(R.id.relativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        initListeners();

    }

    @Override
    public void onResume(){
        super.onResume();
    }


    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }


    private void userRegistration(){

        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        inputValidation = new InputValidation(UserRegister.this);

        if(!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, getString(R.string.error_message_name))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextLastName, textInputLayoutLastName, getString(R.string.error_message_name))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextStudentID, textInputLayoutStudentID, getString(R.string.error_message_studentid))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextNetID, textInputLayoutNetID, getString(R.string.error_message_netid))){
            return;
        }
        if(!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if(spinnerCourse1.getSelectedItem().toString().trim().equals("-")){
            Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!spinnerCourse1.getSelectedItem().toString().trim().equals("-")){
            if(spinnerProfessor1.getSelectedItem().toString().trim().equals("-")){
                Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(!spinnerCourse2.getSelectedItem().toString().trim().equals("-")){
            if(spinnerProfessor2.getSelectedItem().toString().trim().equals("-")){
                Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(spinnerCourse2.getSelectedItem().toString().trim().equals("-")){
            if(!spinnerProfessor2.getSelectedItem().toString().trim().equals("-")){
                Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(!spinnerCourse3.getSelectedItem().toString().trim().equals("-")){
            if(spinnerProfessor3.getSelectedItem().toString().trim().equals("-")){
                Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(spinnerCourse3.getSelectedItem().toString().trim().equals("-")){
            if(!spinnerProfessor3.getSelectedItem().toString().trim().equals("-")){
                Toast.makeText(UserRegister.this, "Course information missing!", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        // If validations are ok, we'll first show a progress bar.
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    // User is successfully registered and logged in.
                    // we will start the profile activity from here
                    // right now let's display a toast only
                    Toast.makeText(UserRegister.this, "Successful! Please login", Toast.LENGTH_SHORT).show();
                    saveUserInformation();
                    Intent intent = new Intent(UserRegister.this, UserLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    if(task.getException()instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(UserRegister.this, "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(UserRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void saveUserInformation(){

        String firstName = textInputEditTextFirstName.getText().toString().trim();
        String lastName = textInputEditTextLastName.getText().toString().trim();
        String studentID = textInputEditTextStudentID.getText().toString().trim();
        String netID = textInputEditTextNetID.getText().toString().trim();
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        String course1 = spinnerCourse1.getSelectedItem().toString().trim();
        String course2 = spinnerCourse2.getSelectedItem().toString().trim();
        String course3 = spinnerCourse3.getSelectedItem().toString().trim();
        String professor1 = spinnerProfessor1.getSelectedItem().toString().trim();
        String professor2 = spinnerProfessor2.getSelectedItem().toString().trim();
        String professor3 = spinnerProfessor3.getSelectedItem().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        UserInformation userInformation = new UserInformation(user.getUid(), firstName, lastName, studentID, netID, email, password, course1, course2, course3, professor1, professor2, professor3, "");

        databaseReference.child(user.getUid()).setValue(userInformation);

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
                userRegistration();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                startActivity(new Intent(this, UserLogin.class));
                break;
        }
    }
}
