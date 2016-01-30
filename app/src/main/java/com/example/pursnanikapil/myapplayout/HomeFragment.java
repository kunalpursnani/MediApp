package com.example.pursnanikapil.myapplayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


public class HomeFragment extends Fragment {
int w;
    Intent intent;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        int w = bundle.getInt("width",750 );


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity(), w));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position == 1) {
                    intent = new Intent(getActivity(),Medicines.class);
                    startActivity(intent);
                }
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();


            }
        });

        return rootView;
    }
}
