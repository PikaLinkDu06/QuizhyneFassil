package com.boyon_armando.quizhynefassil.web_request ;

import android.os.AsyncTask;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boyon_armando.quizhynefassil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetFilter extends AsyncTask<Void, Void, Void> {

    String AREA_URL = " https://www.themealdb.com/api/json/v1/1/list.php?a=list" ;
    ArrayList<String> listeArea = new ArrayList<>() ;

    String CATEGORY_URL = "https://www.themealdb.com/api/json/v1/1/list.php?c=list" ;
    ArrayList<String> listeCategory = new ArrayList<>() ;

    String INGREDIENT_URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list" ;
    ArrayList<String> listeIngredients = new ArrayList<>() ;

    View rootView;

    public GetFilter(View view) {
        rootView = view ;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler() ;

        String jsonAreaString = sh.makeServiceCall(AREA_URL) ;

        if(jsonAreaString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonAreaString) ;

                    JSONArray areas = jsonObj.getJSONArray("meals") ;

                    for(int i = 0; i < areas.length(); i++) {
                        JSONObject currentArea  = areas.getJSONObject(i);
                        String area = currentArea.getString("strArea") ;
                        listeArea.add(area) ;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        String jsonCategoryString = sh.makeServiceCall(CATEGORY_URL) ;

        if(jsonCategoryString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonCategoryString) ;

                JSONArray categories = jsonObj.getJSONArray("meals") ;

                for(int i = 0; i < categories.length(); i++) {
                    JSONObject currentCategory  = categories.getJSONObject(i);
                    String category = currentCategory.getString("strCategory") ;
                    listeCategory.add(category) ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String jsonIngredientString = sh.makeServiceCall(INGREDIENT_URL) ;

        if(jsonIngredientString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonIngredientString) ;

                JSONArray ingredients = jsonObj.getJSONArray("meals") ;

                for(int i = 0; i < ingredients.length(); i+=10) {
                    JSONObject currentIngredient  = ingredients.getJSONObject(i);
                    String ingredient = currentIngredient.getString("strIngredient") ;
                    listeIngredients.add(ingredient) ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null ;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        final RadioGroup arg = rootView.findViewById(R.id.areaRadiogroup) ;
        final RadioGroup crg = rootView.findViewById(R.id.categoryRadiogroup) ;
        final RadioGroup irg = rootView.findViewById(R.id.ingredientRadiogroup) ;

        if(!listeArea.isEmpty()) {
            for (String S : listeArea) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setText(S);
                rb.setId(View.generateViewId()) ;
                arg.addView(rb);
            }
        }

        if(!listeCategory.isEmpty()) {
            for (String S : listeCategory) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setText(S);
                rb.setId(View.generateViewId());
                crg.addView(rb);
            }
        }

        if(!listeIngredients.isEmpty()) {
            for (String S : listeIngredients) {
                RadioButton rb = new RadioButton(rootView.getContext());
                rb.setText(S);
                rb.setId(View.generateViewId());
                irg.addView(rb);
            }
        }
    }
}
