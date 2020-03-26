package com.boyon_armando.quizhynefassil.web_request;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.classes.Ingredient;
import com.boyon_armando.quizhynefassil.classes.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetRecipeInfo extends AsyncTask<Void, Void, Void> {

    String URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

    Recipe RECIPE;
    View rootView;

    String needs = "";

    public GetRecipeInfo(View v, Recipe r) {
        RECIPE = r;
        URL += RECIPE.getRecipeId();
        rootView = v;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpHandler sh = new HttpHandler();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String jsonRecipeInfoString = sh.makeServiceCall(URL);

        if (jsonRecipeInfoString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonRecipeInfoString);

                JSONArray recipeInfo = jsonObj.getJSONArray("meals");

                JSONObject recipe = recipeInfo.getJSONObject(0);

                String step = recipe.getString("strInstructions");

                for (int i = 1; i <= 20; i++) {
                    String ingredientName = recipe.getString("strIngredient" + i);
                    String quantity = recipe.getString("strMeasure" + i);

                    if (!ingredientName.isEmpty() && !ingredientName.equals("null"))
                        ingredients.add(new Ingredient(ingredientName, quantity));
                }

                RECIPE.setSteps(step);
                RECIPE.setIngredients(ingredients);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        final TextView tvIngredients = rootView.findViewById(R.id.listeIngredients);
        final TextView tvPreparation = rootView.findViewById(R.id.steps);

        for (Ingredient I : RECIPE.getIngredients()) {
            needs += I.toString();
        }
        tvIngredients.setText(needs);
        tvPreparation.setText(RECIPE.getSteps());
    }

    public String getRecipeInfo() {
        return RECIPE.getRecipeName() + "\n\n" + rootView.getResources().getString(R.string.needs) + "\n" + needs + "\n" + rootView.getResources().getString(R.string.preparation) + "\n" + RECIPE.getSteps();
    }
}
