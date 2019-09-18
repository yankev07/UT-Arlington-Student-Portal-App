package com.segroup9.uta_stud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by kevinyanogo on 11/16/18.
 */

public class SyllabusActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        imageView = (ImageView)findViewById(R.id.imageView);

        PhotoViewAttacher photoView = new PhotoViewAttacher(imageView);
        imageView.setImageResource(R.drawable.syllabus);

        photoView.update();
    }
}
