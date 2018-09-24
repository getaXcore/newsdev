package com.wordpress.getaufansepta.newsdev.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.getaufansepta.newsdev.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cat1Fragment extends Fragment {


    public Cat1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_cat1,container,false);

        TextView text = fragView.findViewById(R.id.texttes);

        Toast.makeText(getActivity(),"Bisnis menu",Toast.LENGTH_SHORT).show();

        return fragView;
    }

}
