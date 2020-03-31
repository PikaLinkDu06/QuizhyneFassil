package com.boyon_armando.quizhynefassil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView logo = findViewById(R.id.logo);
        final TextView nomApp = findViewById(R.id.app_name);
        final TextView slogApp = findViewById(R.id.app_slogan);

        // Animation du logo qui glissera du haut de l'écran vers le centre
        final Animation translate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translation);
        // Animation de fondu du texte d'opacité 0.0 à opacité de 1.0
        final Animation fade = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade);

        // On démarre les animations
        logo.startAnimation(translate);
        nomApp.startAnimation(fade);
        slogApp.startAnimation(fade);

        // On ajoute un listener sur l'animation de translation
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Lorsque l'animation a terminé, on lance une nouvelle activité
                Intent intent = new Intent(MainActivity.this, RecipeChoiceActivity.class);
                startActivity(intent);
                // Le finish() servira a empeché à l'utilisateur de revenir sur l'écran principal
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
