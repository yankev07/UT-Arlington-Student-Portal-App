package com.segroup9.uta_stud.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.segroup9.uta_stud.Adapters.CourseGridAdapter;
import com.segroup9.uta_stud.CoursesActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.RatingsActivity;
import com.segroup9.uta_stud.Utils.CourseItem;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinyanogo on 9/21/18.
 */

public class FragmentCourses extends android.support.v4.app.Fragment{

    public ArrayList<String> enrolledCourses;
    private RecyclerView recyclerView;
    private CourseGridAdapter mAdapter;
    private DatabaseSQL myDB;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_courses,null);

        myDB = new DatabaseSQL(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        enrolledCourses = myDB.displayEnrolledCourses();
        List<CourseItem> CoursesList = new ArrayList<CourseItem>();

        if(enrolledCourses.size() > 0){
            for(int i=0; i < enrolledCourses.size(); i++){
                CoursesList.add(new CourseItem(getContext().getResources().getIdentifier("icon_courses", "drawable", getContext().getPackageName()), enrolledCourses.get(i)));
            }
        }

        displayTutorials(CoursesList);

        return view;
    }

    private void displayTutorials(List<CourseItem> tutorialsList){
        mAdapter = new CourseGridAdapter(view.getContext(), tutorialsList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CourseGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CourseItem obj, int position) {
                Intent intent = new Intent(getActivity().getBaseContext(), CoursesActivity.class);
                intent.putExtra("course", obj.getName().trim());
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
