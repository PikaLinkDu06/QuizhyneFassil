package com.boyon_armando.quizhynefassil.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.web_request.GetFilter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentFiltre extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    static View rootView ;

    static ArrayList<String> areaList = new ArrayList<>() ;
    static ArrayList<String> categoryList = new ArrayList<>() ;
    static ArrayList<String> ingredientList = new ArrayList<>() ;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_filtre, container, false);

        // On teste si l'utilisateur a du réseau internet et que la récupération des filtres a déjà été fait
        if (haveNetwork()) {
            if (areaList.isEmpty() && categoryList.isEmpty() && ingredientList.isEmpty())
                new GetFilter(rootView).execute();
            else
                afficherFiltres();

            final RadioGroup arg = rootView.findViewById(R.id.area_radiogroup);
            final RadioGroup crg = rootView.findViewById(R.id.category_radiogroup);
            final RadioGroup irg = rootView.findViewById(R.id.ingredient_radiogroup);

            // Les différents listener suivants serviront à vérifier que l'utilisateur choisisse bien qu'un seul filtre et affichera dans le fragment des recettes ...
            // ... les recettes correspondants  au filtre choisi
            arg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int selectedID = radioGroup.getCheckedRadioButtonId();

                    if (crg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        crg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (irg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        irg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (selectedID != -1) {
                        RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                        changeFragment("area", currentCheckedButton.getText().toString());
                    }
                }
            });

            crg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int selectedID = radioGroup.getCheckedRadioButtonId();

                    if (arg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        arg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (irg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        irg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (selectedID != -1) {
                        RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                        changeFragment("category", currentCheckedButton.getText().toString());
                    }
                }
            });

            irg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int selectedID = radioGroup.getCheckedRadioButtonId();

                    if (arg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        arg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (crg.getCheckedRadioButtonId() != -1 && selectedID != -1) {
                        crg.clearCheck();
                        radioGroup.check(selectedID);
                    } else if (selectedID != -1) {
                        RadioButton currentCheckedButton = rootView.findViewById(selectedID);
                        changeFragment("ingredient", currentCheckedButton.getText().toString());
                    }
                }
            });
        } else
            Toast.makeText(rootView.getContext(), "Please enable your internet connection", Toast.LENGTH_SHORT).show();
        return rootView;
    }

    // Fonction appelée dans la classe GetFilter et servant à remplir les différentes ArrayList
    public static void addFilters(ArrayList<String> listeArea, ArrayList<String> listeCategory, ArrayList<String> listeIngredients) {
        areaList = listeArea ;
        categoryList = listeCategory ;
        ingredientList = listeIngredients ;

        afficherFiltres() ;
    }

    // Fonction servant à afficher les différents filtres
    public static void afficherFiltres() {
        final RadioGroup arg = rootView.findViewById(R.id.area_radiogroup);
        final RadioGroup crg = rootView.findViewById(R.id.category_radiogroup);
        final RadioGroup irg = rootView.findViewById(R.id.ingredient_radiogroup);


        if (!areaList.isEmpty()) {
            for (String S : areaList) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setText(S);
                rb.setTextSize(13);
                rb.setId(View.generateViewId());
                arg.addView(rb);
            }
        }

        if (!categoryList.isEmpty()) {
            for (String S : categoryList) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setText(S);
                rb.setTextSize(13);
                rb.setId(View.generateViewId());
                crg.addView(rb);
            }
        }

        if (!ingredientList.isEmpty()) {
            for (String S : ingredientList) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setTextSize(13);
                rb.setText(S);
                rb.setId(View.generateViewId());
                irg.addView(rb);
            }
        }

    }

    // Fonction permettant de changer le fragment des recettes en fonction du filtre choisi
    public void changeFragment(String filterName, String filter) {
        frag = new FragmentRecette();
        Bundle args = new Bundle();
        args.putString("filter_name", filterName);
        args.putString("filter", filter);
        frag.setArguments(args);

        fragTransaction = getFragmentManager().beginTransaction().replace(R.id.recipes, frag);
        fragTransaction.commit();
    }

    // Fonction permettant de verifier que l'utilisateur possède bien une connexion internet
    private boolean haveNetwork() {
        ConnectivityManager cm = (ConnectivityManager)rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected ;
    }
}
