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

    // URL permettant la récuperation des recettes
    String INGREDIENT_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
    String CATEGORTY_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";
    String AREA_FILTER_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?a=";

    String filterName;
    GetRecipe gr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On récupere les arguments afin de remplir la bonne URL avec le bon filtre correspondant
        Bundle b = getArguments();
        if (b != null) {
            filterName = b.getString("filter_name").toLowerCase();
            String filter = b.getString("filter");

            switch (filterName) {
                case "ingredient":
                    INGREDIENT_FILTER_URL += filter;
                    break;
                case "category":
                    CATEGORTY_FILTER_URL += filter;
                    break;
                case "area":
                    AREA_FILTER_URL += filter;
                    break;
            }
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recettes, null);

        // On teste le nom du filtre pour creer notre GetRecipe puis on l'execute
        switch (filterName) {
            case "ingredient":
                gr = new GetRecipe(rootView, INGREDIENT_FILTER_URL);
                break;
            case "category":
                gr = new GetRecipe(rootView, CATEGORTY_FILTER_URL);
                break;
            case "area":
                gr = new GetRecipe(rootView, AREA_FILTER_URL);
                break;
        }
        gr.execute();

        final ListView listeViewRecettes = rootView.findViewById(R.id.recipe_listview);

        // Lorsque que l'on clique sur une recette, on démarre une nouvelle activité et on lui met comme argument la recette sélectionnée
        listeViewRecettes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe r = (Recipe) listeViewRecettes.getAdapter().getItem(position);

                Intent intent = new Intent(rootView.getContext(), RecipeInfoActivity.class);
                intent.putExtra("recipe", r);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
