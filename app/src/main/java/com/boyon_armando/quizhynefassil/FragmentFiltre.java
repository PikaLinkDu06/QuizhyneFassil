package com.boyon_armando.quizhynefassil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentFiltre extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    ArrayList<String> listeFiltresArea ;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_filtre, container, false);

        final Button submit = rootView.findViewById(R.id.boutonSubmit) ;
        final RadioGroup rg = rootView.findViewById(R.id.radiogroup) ;

        GetFilter gf = new GetFilter() ;
        gf.execute() ;

        listeFiltresArea = gf.getArea() ;

            for(String S : listeFiltresArea) {
                Log.d("TEST", S) ;
                RadioButton rb = new RadioButton(getContext()) ;
                rb.setText(S) ;
                rb.setId(View.generateViewId()) ;
                rg.addView(rb) ;
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragmentRecette() ;
                Bundle args = new Bundle() ;
                args.putString("filtre", "TEST") ;
                frag.setArguments(args) ;

                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.recettes, frag) ;
                fragTransaction.commit() ;
            }
        });

        return rootView ;
    }

}
