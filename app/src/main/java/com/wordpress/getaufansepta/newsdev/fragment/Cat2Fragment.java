package com.wordpress.getaufansepta.newsdev.fragment;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.getaufansepta.newsdev.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cat2Fragment extends Fragment {


    public Cat2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragView = inflater.inflate(R.layout.fragment_cat2,container,false);

        //return inflater.inflate(R.layout.fragment_cat2, container, false);
        return fragView;
    }

}
