package com.ashysystem.mbhq.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;


public class NewHelpFragment extends Fragment {


    public NewHelpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewHelpFragment newInstance(Integer param1) {
        NewHelpFragment fragment = new NewHelpFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_help, container, false);
    }
}