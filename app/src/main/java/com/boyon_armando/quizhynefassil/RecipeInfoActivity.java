package com.boyon_armando.quizhynefassil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.boyon_armando.quizhynefassil.classes.Contact;
import com.boyon_armando.quizhynefassil.classes.Recipe;
import com.boyon_armando.quizhynefassil.web_request.GetRecipeInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class RecipeInfoActivity extends AppCompatActivity {

    // Variable servant à stocker les informations de la recette
    GetRecipeInfo gri;

    // Variable servant aux autorisations d'accès aux contacts
    static final int REQUEST_CONTACT_PERMISSION = 1;
    boolean canAccessContacts = false ;

    // Variables servant au stockage et à l'affichage des contacts
    ArrayList<Contact> storeContacts ;
    ArrayAdapter<Contact> adapter ;
    Cursor cursor ;

    // Variable servant de boîte de dialogue pour demander à l'utilisateur à qui il souhaite envoyer le message
    AlertDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_info);

        storeContacts = new ArrayList<>() ;
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

    public void askForNumber() {

        EnableRuntimePermission() ;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send");
        builder.setMessage("To whom would you like to send this recipe ?") ;

        builder.setView(R.layout.send_to_contact) ;

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendToContact();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });

        dialog = builder.create() ;
        dialog.show();

        final Button selectContact = dialog.findViewById(R.id.contact_button) ;

        selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(canAccessContacts) {
                    GetContactsIntoArrayList() ;
                    AlertDialog.Builder builderContacts = new AlertDialog.Builder(view.getContext()) ;
                    builderContacts.setView(R.layout.select_contact) ;
                    builderContacts.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel() ;
                            storeContacts.clear() ;
                        }
                    }) ;
                    final AlertDialog dialogContacts = builderContacts.create() ;
                    dialogContacts.show() ;
                    ListView listeViewContacts = dialogContacts.findViewById(R.id.contact_listview) ;
                    adapter = new ArrayAdapter<Contact>(view.getContext(), android.R.layout.simple_list_item_1, storeContacts) ;
                    listeViewContacts.setAdapter(adapter) ;

                    listeViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Contact selectContact = storeContacts.get(i) ;
                            EditText input = dialog.findViewById(R.id.input_phone_number) ;

                            input.setText(selectContact.getPhoneNumber()) ;
                            dialogContacts.cancel() ;
                        }
                    });
                }
            }
        });
    }

    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(RecipeInfoActivity.this, Manifest.permission.READ_CONTACTS))  {
            canAccessContacts = true ;
        } else {
            ActivityCompat.requestPermissions(RecipeInfoActivity.this,new String[]{ Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT_PERMISSION) ;
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String[] per, int[] PResult) {
        switch (RC) {
            case REQUEST_CONTACT_PERMISSION:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    canAccessContacts = true ;
                } else {
                    Toast.makeText(RecipeInfoActivity.this,"You must accept the application to access your contacts if you want to select a contact", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void GetContactsIntoArrayList(){
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null) ;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            storeContacts.add(new Contact(name, phonenumber)) ;
        }
        cursor.close();
    }

    public void sendToContact() {

        final EditText input = dialog.findViewById(R.id.input_phone_number) ;
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
            Toast.makeText(getApplicationContext(), "Merci d'entrer un numéro de téléphone", Toast.LENGTH_SHORT).show() ;
        }
    }
}
