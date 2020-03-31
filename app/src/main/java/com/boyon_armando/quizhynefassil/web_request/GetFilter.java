package com.boyon_armando.quizhynefassil.web_request;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import com.boyon_armando.quizhynefassil.fragments.FragmentFiltre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetFilter extends AsyncTask<Void, Void, Void> {

    // Variables servant à stocker les filtres de zone
    String AREA_URL = " https://www.themealdb.com/api/json/v1/1/list.php?a=list";
    ArrayList<String> listeArea = new ArrayList<>();

    // Variables servant à stocker les filtres de catégories
    String CATEGORY_URL = "https://www.themealdb.com/api/json/v1/1/list.php?c=list";
    ArrayList<String> listeCategory = new ArrayList<>();

    // Variables servant à stocker les filtres d'ingrédient
    String INGREDIENT_URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
    ArrayList<String> listeIngredients = new ArrayList<>();

    View rootView;
    ProgressDialog pDialog;

    public GetFilter(View view) {
        rootView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // On indique à l'utilisateur qu'on récupere les différents filtres
        pDialog = new ProgressDialog(rootView.getContext());
        pDialog.setMessage("Loading filters ...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // On récupere les différents filtres en fonction des différentes URL
        HttpHandler sh = new HttpHandler();

        String jsonAreaString = sh.makeServiceCall(AREA_URL);

        if (jsonAreaString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonAreaString);

                JSONArray areas = jsonObj.getJSONArray("meals");

                for (int i = 0; i < areas.length(); i++) {
                    JSONObject currentArea = areas.getJSONObject(i);
                    String area = currentArea.getString("strArea");
                    listeArea.add(area);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String jsonCategoryString = sh.makeServiceCall(CATEGORY_URL);

        if (jsonCategoryString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonCategoryString);

                JSONArray categories = jsonObj.getJSONArray("meals");

                for (int i = 0; i < categories.length(); i++) {
                    JSONObject currentCategory = categories.getJSONObject(i);
                    String category = currentCategory.getString("strCategory");
                    listeCategory.add(category);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String jsonIngredientString = sh.makeServiceCall(INGREDIENT_URL);

        if (jsonIngredientString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonIngredientString);

                JSONArray ingredients = jsonObj.getJSONArray("meals");

                for (int i = 0; i < ingredients.length(); i += 10) {
                    JSONObject currentIngredient = ingredients.getJSONObject(i);
                    String ingredient = currentIngredient.getString("strIngredient");
                    listeIngredients.add(ingredient);
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

        if (pDialog.isShowing()) pDialog.dismiss();

        // On lance la fonction addFilters à la classe FragmentFiltre
        FragmentFiltre.addFilters(listeArea, listeCategory, listeIngredients) ;
        }
}
