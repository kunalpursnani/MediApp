package com.example.pursnanikapil.myapplayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class CreateFragment extends Fragment {

    public CreateFragment() {
    }

    ImageView imageViewIcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create, container, false);
        imageViewIcon = (ImageView) rootView.findViewById(R.id.imageView);
        imageViewIcon.setImageResource(R.drawable.copy_icon);

        return rootView;
    }

}
