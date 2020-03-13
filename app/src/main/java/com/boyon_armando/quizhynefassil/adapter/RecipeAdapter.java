package com.boyon_armando.quizhynefassil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyon_armando.quizhynefassil.R;
import com.boyon_armando.quizhynefassil.classes.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;

public class RecipeAdapter extends BaseAdapter {
    private ArrayList<Recipe> recipeArrayList;
    private LayoutInflater mInflater; //Un mécanisme pour gérer l'affichage graphique depuis un layout XML

    /**
     * Constructeur normal
     *
     * @param context    le contexte de l'activité
     * @param recipeList la liste que l'on doit adapter
     */
    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        recipeArrayList = recipeList;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * surcharge obligatoire de BaseAdapter
     *
     * @return nb de diplome à adapter
     */
    public int getCount() {
        return recipeArrayList.size();
    }

    /**
     * surcharge obligatoire de BaseAdapter
     */
    public Recipe getItem(int position) {
        return recipeArrayList.get(position);
    }

    /**
     * surcharge obligatoire de BaseAdapter
     */
    public long getItemId(int position) {
        return position;
    }


//--------------------------------------------------------------


    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML ""
            layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.recipe_layout, parent, false);
        } else {
            layoutItem = (ConstraintLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView tvNomRecette = layoutItem.findViewById(R.id.texteRecette);
        ImageView imageRecette = layoutItem.findViewById(R.id.imageRecette);

        //(3) : Renseignement des valeurs
        tvNomRecette.setText(recipeArrayList.get(position).getRecipeName());
        //Loading image using Picasso
        Picasso.get().load(recipeArrayList.get(position).getRecipePhotoURL()).into(imageRecette);

        //(6)On retourne l'item créé.
        return layoutItem;
    }

}
