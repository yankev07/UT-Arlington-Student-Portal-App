package com.segroup9.uta_stud;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import android.os.Handler;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.segroup9.uta_stud.Helper.EventInformation;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by kevinyanogo on 11/12/18.
 */

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;

    private TextInputLayout textInputLayoutEventDetails;
    private TextInputEditText textInputEditTextEventDetails;
    private AppCompatButton appCompatButtonSubmit;
    private ProgressBar progressBar;

    TimePickerDialog timePickerDialog;
    ImageButton imageButton;
    EditText editTextEventTitle;
    EditText editTextEventDate;
    EditText editTextEventStart;
    EditText editTextEventEnd;
    EditText editTextEventDetails;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int year;
    int month;
    int day;
    int startH, startM;

    private Uri filePath;
    private StorageTask mUploadTask;

    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        imageButton = (ImageButton) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        editTextEventTitle = (EditText) findViewById(R.id.editTextEventTitle);
        editTextEventDate = (EditText) findViewById(R.id.editTextEventDate);
        editTextEventStart = (EditText) findViewById(R.id.editTextStartTime);
        editTextEventEnd = (EditText) findViewById(R.id.editTextEndTime);
        editTextEventDetails = (EditText) findViewById(R.id.editTextEventDetails);
        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(EventsActivity.this, "click", Toast.LENGTH_SHORT).show();
                openFileChooser();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("events");
        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        setListeners();
        appCompatButtonSubmit.setOnClickListener(this);
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageButton.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonSubmit:
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(EventsActivity.this, "Upload in progress...", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    editTextEventDate.setText((view.getMonth()+1) + "/" + view.getDayOfMonth() + "/" + view.getYear());
                }
            };



    public void setListeners(){

        editTextEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog newFragment = new DatePickerDialog(EventsActivity.this, R.style.TimePicker, dateSetListener, year, month, day);
                newFragment.show();
            }
        });


        editTextEventStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EventsActivity.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startH = hourOfDay;
                        startM = minutes;
                        String timeSet = "";
                        if (startH > 12) {
                            startH -= 12;
                            timeSet = "PM";
                        }
                        else if (startH == 0) {
                            startH += 12;
                            timeSet = "AM";
                        }
                        else if (startH == 12){
                            timeSet = "PM";
                        }
                        else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (startM < 10)
                            min = "0" + startM ;
                        else
                            min = String.valueOf(startM);

                        String timeAMPM = new StringBuilder().append(startH).append(':').append(min ).append(" ").append(timeSet).toString();
                        editTextEventStart.setText(timeAMPM);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


        editTextEventEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(EventsActivity.this, R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startH = hourOfDay;
                        startM = minutes;
                        String timeSet = "";
                        if (startH > 12) {
                            startH -= 12;
                            timeSet = "PM";
                        }
                        else if (startH == 0) {
                            startH += 12;
                            timeSet = "AM";
                        }
                        else if (startH == 12){
                            timeSet = "PM";
                        }
                        else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (startM < 10)
                            min = "0" + startM ;
                        else
                            min = String.valueOf(startM);

                        String timeAMPM = new StringBuilder().append(startH).append(':').append(min ).append(" ").append(timeSet).toString();
                        editTextEventEnd.setText(timeAMPM);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (filePath != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(filePath));

            mUploadTask = fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(EventsActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            EventInformation eventInformation = new EventInformation(taskSnapshot.getDownloadUrl().toString(), editTextEventTitle.getText().toString().trim(), editTextEventDate.getText().toString().trim(), editTextEventStart.getText().toString().trim(), editTextEventEnd.getText().toString().trim(), editTextEventDetails.getText().toString().trim());
                            databaseReference.child(editTextEventTitle.getText().toString().trim()).setValue(eventInformation);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EventsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });

            this.finish();
            startActivity(new Intent(EventsActivity.this, MainActivity.class));
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
