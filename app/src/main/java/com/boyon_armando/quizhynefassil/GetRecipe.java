package com.boyon_armando.quizhynefassil;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetRecipe extends AsyncTask<Void, Void, Void> {

    String URL ;
    View rootView ;

    ArrayList<Recipe> arrayListRecipe ;

    public GetRecipe(View v, String url) {
        this.URL = url ;
        this.rootView = v ;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpHandler sh = new HttpHandler();

        String jsonRecipeString = sh.makeServiceCall(URL);

        arrayListRecipe = new ArrayList<>() ;

        if (jsonRecipeString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonRecipeString);


                JSONArray recipes = jsonObj.getJSONArray("meals");


                for (int i = 0; i < recipes.length(); i++) {

                    JSONObject currentRecipe = recipes.getJSONObject(i);

                    String nom = currentRecipe.getString("strMeal");
                    int id = currentRecipe.getInt("idMeal") ;

                    Recipe re = new Recipe(id, nom) ;

                    arrayListRecipe.add(re) ;
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

        final ListView listeViewRecipes = rootView.findViewById(R.id.listeViewRecettes) ;

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayListRecipe) ;
        listeViewRecipes.setAdapter(adapter) ;
    }
}
