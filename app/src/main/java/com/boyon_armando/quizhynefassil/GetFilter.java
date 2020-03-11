package com.boyon_armando.quizhynefassil;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetFilter extends AsyncTask<Void, Void, Void> {

    String AREA_URL = " https://www.themealdb.com/api/json/v1/1/list.php?a=list" ;
    ArrayList<String> listeArea = new ArrayList<>() ;

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler() ;

        String jsonAreaString = sh.makeServiceCall(AREA_URL) ;

        if(jsonAreaString != null) {
            Log.d("TEST", jsonAreaString) ;
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
        return null ;
        }

        public ArrayList<String> getArea() {
        return listeArea ;
        }

}
