package com.boyon_armando.quizhynefassil.web_request;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.adapter.RecipeAdapter;
import com.boyon_armando.quizhynefassil.classes.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetRecipe extends AsyncTask<Void, Void, Void> {

    String URL;
    View rootView;

    ArrayList<Recipe> arrayListRecipe;

    public GetRecipe(View v, String url) {
        this.URL = url;
        this.rootView = v;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // On récupere les différentes recettes en fonction de l'URL donnée
        HttpHandler sh = new HttpHandler();

        String jsonRecipeString = sh.makeServiceCall(URL);

        arrayListRecipe = new ArrayList<>();

        if (jsonRecipeString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonRecipeString);


                JSONArray recipes = jsonObj.getJSONArray("meals");


                for (int i = 0; i < recipes.length(); i++) {

                    JSONObject currentRecipe = recipes.getJSONObject(i);

                    String nom = currentRecipe.getString("strMeal");
                    int id = currentRecipe.getInt("idMeal");
                    String photoURL = currentRecipe.getString("strMealThumb");

                    Recipe re = new Recipe(id, nom, photoURL);

                    arrayListRecipe.add(re);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // On récupere la ListView de la rootView passée en paramètre de la classe GetRecipe et on lui applique un RecipeAdapter
        final ListView listeViewRecipes = rootView.findViewById(R.id.recipe_listview);

        RecipeAdapter adapter = new RecipeAdapter(rootView.getContext(), arrayListRecipe);
        listeViewRecipes.setAdapter(adapter);
    }

}
