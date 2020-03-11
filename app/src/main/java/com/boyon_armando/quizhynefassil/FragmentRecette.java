package com.boyon_armando.quizhynefassil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRecette extends Fragment {

    View rootView;
    String URL = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments() ;
        if(b!=null)
        {
            URL = getArguments().getString("filtre") ;
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recettes, null);

        final TextView et = rootView.findViewById(R.id.test) ;

        et.setText(URL) ;

        return rootView;
    }

}
