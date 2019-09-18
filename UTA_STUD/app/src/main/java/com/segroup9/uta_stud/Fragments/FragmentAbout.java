package com.segroup9.uta_stud.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.segroup9.uta_stud.R;

/**
 * Created by kevinyanogo on 9/21/18.
 */

public class FragmentAbout extends android.support.v4.app.Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about,null);

        return view;
    }
}
