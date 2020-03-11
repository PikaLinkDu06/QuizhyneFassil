package com.boyon_armando.quizhynefassil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentFiltre extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    public ArrayList<String> listeFiltresArea = new ArrayList<>() ;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_filtre, container, false);

        new GetFilter(rootView).execute() ;

        final RadioGroup arg = rootView.findViewById(R.id.areaRadiogroup) ;
        final RadioGroup crg = rootView.findViewById(R.id.categoryRadiogroup) ;

        arg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(crg.getCheckedRadioButtonId() != -1 && arg.getCheckedRadioButtonId() != -1) {
                    crg.clearCheck();
                    arg.check(i) ;
                }
                else {
                    RadioButton currentCheckedButton = rootView.findViewById(arg.getCheckedRadioButtonId()) ;
                    changeFragment(currentCheckedButton.getText().toString()) ;
                }
            }
        });

        crg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(arg.getCheckedRadioButtonId() != -1 && crg.getCheckedRadioButtonId() != -1) {
                    arg.clearCheck() ;
                    crg.check(i) ;
                }
                else {
                    RadioButton currentCheckedButton = rootView.findViewById(crg.getCheckedRadioButtonId()) ;
                    changeFragment(currentCheckedButton.getText().toString()) ;
                }
            }
        }) ;
        return rootView ;
    }

    public void changeFragment(String filter) {
        frag = new FragmentRecette() ;
        Bundle args = new Bundle() ;
        args.putString("filtre", filter) ;
        frag.setArguments(args) ;

        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.recettes, frag) ;
        fragTransaction.commit() ;
    }


}
