package com.boyon_armando.quizhynefassil.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.web_request.GetFilter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentFiltre extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_filtre, container, false);

        new GetFilter(rootView).execute() ;

        final RadioGroup arg = rootView.findViewById(R.id.areaRadiogroup) ;
        final RadioGroup crg = rootView.findViewById(R.id.categoryRadiogroup) ;
        final RadioGroup irg = rootView.findViewById(R.id.ingredientRadiogroup) ;

        arg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = radioGroup.getCheckedRadioButtonId() ;

                if (crg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    crg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(irg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    irg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(selectedID != -1) {
                    RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                    changeFragment("area", currentCheckedButton.getText().toString());
                }
            }
        });

        crg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = radioGroup.getCheckedRadioButtonId() ;

                if(arg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    arg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(irg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    irg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(selectedID != -1) {
                    RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                    changeFragment("category", currentCheckedButton.getText().toString()) ;
                }
            }
        }) ;

        irg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = radioGroup.getCheckedRadioButtonId() ;

                if(arg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    arg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(crg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                    crg.clearCheck() ;
                    radioGroup.check(selectedID) ;
                }
                else if(selectedID != -1) {
                    RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                    changeFragment("ingredient", currentCheckedButton.getText().toString()) ;
                }
            }
        });


        return rootView ;
    }

    public void changeFragment(String filterName, String filter) {
        frag = new FragmentRecette() ;
        Bundle args = new Bundle() ;
        args.putString("filter_name", filterName) ;
        args.putString("filter", filter) ;
        frag.setArguments(args) ;

        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.recettes, frag) ;
        fragTransaction.commit() ;
    }


}
