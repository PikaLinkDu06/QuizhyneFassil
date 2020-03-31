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

        // On déclare un nouveau GetRecipeInfo qui servira à récuperer les infos de la recette passée en argument
        gri = new GetRecipeInfo(getWindow().getDecorView().getRootView(), recipe);
        gri.execute();

        final TextView tv = findViewById(R.id.recipe_name);
        final ImageView iv = findViewById(R.id.recipe_photo);

        // Cette fonction (Picasso) sert à afficher une image d'après une URL internet
        Picasso.get().load(recipe.getRecipePhotoURL()).into(iv);
        tv.setText(recipe.getRecipeName());

        final Button back = findViewById(R.id.back);

        // Bouton de retour pour retourner aux choix de filtres
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeInfoActivity.this, RecipeChoiceActivity.class);
                startActivity(intent);
            }
        });

        final Button sendSms = findViewById(R.id.sms_send);

        // Bouton qui servira à l'envoie de message de la recette à un ami
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForNumber();
            }
        });
    }

    public void askForNumber() {

        // On demande la permission à l'utilisateur d'acceder à ses contacts
        EnableRuntimePermission() ;

        // On construit un AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send");
        builder.setMessage("To whom would you like to send this recipe ?") ;

        builder.setView(R.layout.send_to_contact) ;

        // On crée les deux boutons, le "send" servira à envoyer le message et le "cancel" servira à annuler l'AlertDialog
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

        // On crée l'AlertDialog et on l'affiche
        dialog = builder.create() ;
        dialog.show();

        final Button selectContact = dialog.findViewById(R.id.contact_button) ;

        // Ce bouton là servira pour acceder à la liste de contacts et a affiché les différents contacts du destinataire
        selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Si l'utilisateur a accepté que l'application puisse acceder à ses contacts
                if(canAccessContacts) {

                    // Function servant à récuperer tous les contacts de l'utilisateur seulement si l'ArrayList qui stocke les contacts est vide
                    if(storeContacts.isEmpty())
                        GetContactsIntoArrayList() ;

                    // On reconstruit un AlertDialog
                    AlertDialog.Builder builderContacts = new AlertDialog.Builder(view.getContext()) ;
                    builderContacts.setView(R.layout.select_contact) ;
                    // Le bouton "cancel" fermera cet AlertDialog
                    builderContacts.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel() ;
                        }
                    }) ;

                    // On crée l'AlertDialog et on l'affiche
                    final AlertDialog dialogContacts = builderContacts.create() ;
                    dialogContacts.show() ;

                    // On ajoute l'adapter contenant les contacts dans la ListView
                    ListView listeViewContacts = dialogContacts.findViewById(R.id.contact_listview) ;
                    adapter = new ArrayAdapter<Contact>(view.getContext(), android.R.layout.simple_list_item_1, storeContacts) ;
                    listeViewContacts.setAdapter(adapter) ;


                    listeViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            // Lorsque l'on clique sur un des contacts, on ajoute son numéro dans l'EditText qui contient les contacts
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

    // Fonction servant à demander à l'utilisateur d'acceder à ses contacts
    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(RecipeInfoActivity.this, Manifest.permission.READ_CONTACTS))  {
            // Si l'utilisateur à déjà accepté l'application
            canAccessContacts = true ;
        } else {
            // Sinon on lui demande la permission
            ActivityCompat.requestPermissions(RecipeInfoActivity.this,new String[]{ Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT_PERMISSION) ;
        }
    }

    // Fonction qui se lance lorsque l'utilisateur a choisi si oui ou non il accepte la permission
    @Override
    public void onRequestPermissionsResult(int RC, String[] per, int[] PResult) {
        switch (RC) {
            case REQUEST_CONTACT_PERMISSION:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    // Si l'utilisateur a accepté on met la variable canAccessContacts à true
                    canAccessContacts = true ;
                } else {
                    // Sinon on lui affiche qu'il est obligé d'accepter s'il veut pouvoir acceder à ses contacts
                    Toast.makeText(RecipeInfoActivity.this,"You must accept the application to access your contacts if you want to select a contact", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // Fonction servant à récuperer les contacts de l'utilisateur dans une ArrayList
    public void GetContactsIntoArrayList(){
        // On déclare une curseur qui accedera au contacts de l'utilisateur
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null) ;
        while (cursor.moveToNext()) {
            // On récupere le nom ainsi que le numéro du contact puis on crée un nouveau Contact qu'on stockera dans la variable storeContacts
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            storeContacts.add(new Contact(name, phonenumber)) ;
        }
        cursor.close();
    }

    public void sendToContact() {

        final EditText input = dialog.findViewById(R.id.input_phone_number) ;
        // On récupere le String du numéro de téléphone et on teste si le numéro entré n'est pas vide
        String numbStr = input.getText().toString();

        if (!numbStr.isEmpty()) {
            String number = String.format("smsto: %s", input.getText().toString());

            // On ouvre l'activité SMS et on met comme corps du message les infos de la recette
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
            // Si le String du numéro de téléphone est vide on annule l'AlertDialog et on affiche à l'utilisateur qu'il est obligé d'entrer un numéro de téléphone
            dialog.cancel();
            Toast.makeText(getApplicationContext(), "Please enter a phone number", Toast.LENGTH_SHORT).show() ;
        }
    }
}
