package com.boyon_armando.quizhynefassil.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.RecipeInfoActivity;
import com.boyon_armando.quizhynefassil.classes.Recipe;
import com.boyon_armando.quizhynefassil.web_request.GetRecipe;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRecette extends Fragment {

    View rootView;
    String INGREDIENT_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
    String CATEGORTY_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" ;
    String AREA_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?a=" ;

    String filterName ;
    GetRecipe gr ;

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
                gr = new GetRecipe(rootView, INGREDIENT_FILTER_URL) ;
                gr.execute() ;
                break ;
            case "category" :
                gr = new GetRecipe(rootView, CATEGORTY_FILTER_URL) ;
                gr.execute() ;
                break ;
            case "area" :
                gr = new GetRecipe(rootView, AREA_FILTER_URL) ;
                gr.execute() ;
                break ;
        }

        final ListView listeViewRecettes = rootView.findViewById(R.id.listeViewRecettes) ;

        listeViewRecettes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe r = (Recipe) listeViewRecettes.getAdapter().getItem(position) ;

                Intent intent = new Intent(rootView.getContext(), RecipeInfoActivity.class) ;
                intent.putExtra("recipe", r) ;
                startActivity(intent) ;
            }
        });

        return rootView;
    }

}
