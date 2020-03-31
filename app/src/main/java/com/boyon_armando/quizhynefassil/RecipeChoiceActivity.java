package com.boyon_armando.quizhynefassil;

import android.os.Bundle;

import com.boyon_armando.quizhynefassil.fragments.FragmentFiltre;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_choice_activity);

        if (savedInstanceState == null) {
            // On lance le fragment qui gerera les différents filtres
            getSupportFragmentManager().beginTransaction().add(R.id.filter, new FragmentFiltre()).commit();
        }

    }
}
