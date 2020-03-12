package com.boyon_armando.quizhynefassil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRecette extends Fragment {

    View rootView;
    String INGREDIENT_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
    String CATEGORTY_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" ;
    String AREA_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?a=" ;

    String filterName ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments() ;
        if(b!=null)
        {
            filterName = b.getString("filter_name") ;
            String filter = b.getString("filter") ;

            switch (filterName) {
                case "ingredient" :
                    INGREDIENT_FILTER_URL += filter ;
                    break ;
                case "category" :
                    CATEGORTY_FILTER_URL += filter ;
                    break ;
                case "area" :
                    AREA_FILTER_URL += filter ;
                    break ;
            }
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recettes, null);

        switch(filterName) {
            case "ingredient" :
                new GetRecipe(rootView, INGREDIENT_FILTER_URL).execute() ;
                break ;
            case "category" :
                new GetRecipe(rootView, CATEGORTY_FILTER_URL).execute() ;
                break ;
            case "area" :
                new GetRecipe(rootView, AREA_FILTER_URL).execute() ;
                break ;
        }

        return rootView;
    }

}
