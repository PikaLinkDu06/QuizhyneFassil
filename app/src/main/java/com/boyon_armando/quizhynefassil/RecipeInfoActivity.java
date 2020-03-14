package com.boyon_armando.quizhynefassil;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyon_armando.quizhynefassil.classes.Recipe;
import com.boyon_armando.quizhynefassil.web_request.GetRecipeInfo;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeInfoActivity extends AppCompatActivity {

    GetRecipeInfo gri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_info);

        Recipe recipe = getIntent().getExtras().getParcelable("recipe");

        gri = new GetRecipeInfo(getWindow().getDecorView().getRootView(), recipe);
        gri.execute();

        final TextView tv = findViewById(R.id.nomRecette);
        final ImageView iv = findViewById(R.id.photoRecette);

        Picasso.get().load(recipe.getRecipePhotoURL()).into(iv);
        tv.setText(recipe.getRecipeName());

        final Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeInfoActivity.this, RecipeChoiceActivity.class);
                startActivity(intent);
            }
        });

        final Button sendSms = findViewById(R.id.sms_send);

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForNumber();
            }
        });
    }

    private void askForNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("To whom would you like to send this recipe ? ");

        final EditText input = new EditText(this);
        input.setHint("Enter a phone number");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.TEXT_ALIGNMENT_GRAVITY);
        input.setLayoutParams(lp);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String numbStr = input.getText().toString();
                if (!numbStr.isEmpty()) {
                    String number = String.format("smsto: %s", input.getText().toString());

                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse(number));
                    smsIntent.putExtra("sms_body", gri.getRecipeInfo());

                    // If package resolves (target app installed), send intent.
                    if (smsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(smsIntent);
                    } else {
                        Log.e("TEST", "Can't resolve app for ACTION_SENDTO Intent");
                    }
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

}
