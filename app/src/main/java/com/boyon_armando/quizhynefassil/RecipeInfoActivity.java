package com.boyon_armando.quizhynefassil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyon_armando.quizhynefassil.classes.Recipe;
import com.boyon_armando.quizhynefassil.web_request.GetRecipeInfo;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeInfoActivity extends AppCompatActivity {

    GetRecipeInfo gri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_info) ;

        Recipe recipe = getIntent().getExtras().getParcelable("recipe") ;

        gri = new GetRecipeInfo(getWindow().getDecorView().getRootView(), recipe) ;
        gri.execute() ;

        final TextView tv = findViewById(R.id.nomRecette) ;
        final ImageView iv = findViewById(R.id.photoRecette) ;

        Picasso.get().load(recipe.getRecipePhotoURL()).into(iv) ;
        tv.setText(recipe.getRecipeName()) ;
    }

}
