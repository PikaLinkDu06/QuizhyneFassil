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

        final ImageView logo = findViewById(R.id.logo) ;
        final TextView nomApp = findViewById(R.id.nomApp) ;
        final TextView slogApp = findViewById(R.id.sloganApp) ;

        final Animation translate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translation) ;
        final Animation fade = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade) ;

        logo.startAnimation(translate) ;
        nomApp.startAnimation(fade) ;
        slogApp.startAnimation(fade) ;

        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, RecipeChoiceActivity.class) ;
                startActivity(intent) ;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
