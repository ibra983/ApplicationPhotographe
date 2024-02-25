package com.example.appliblogphoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Inscription extends AppCompatActivity {

    private EditText etUsernameInscription;
    private EditText etMailInscription;
    private EditText etPasswordInscription;
    private DatabaseHelper dbHelper; // référence à DataBaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        dbHelper = new DatabaseHelper(this); //Instancier DataBaseHelper

        etUsernameInscription = findViewById(R.id.etUsernameInscription);
        etMailInscription = findViewById(R.id.etMailInscription);
        etPasswordInscription = findViewById(R.id.etPasswordInscription);
        Button btnInscription = findViewById(R.id.btnLoginInscription);

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs de saisie
                String username = etUsernameInscription.getText().toString().trim();
                String mail = etMailInscription.getText().toString().trim();
                String password = etPasswordInscription.getText().toString().trim();

                // Vérifier si les champs ne sont pas vides
                if (!username.isEmpty() && !mail.isEmpty() && !password.isEmpty()) {
                    // Ajouter l'utilisateur seulement si les champs ne sont pas vides
                    dbHelper.ajouterNouvelUtilisateur(username, mail, password);

                    // Afficher un message de confirmation
                    Toast.makeText(Inscription.this, "Inscription réussie pour " + username, Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    // Afficher un message d'erreur si des champs sont vides
                    Toast.makeText(Inscription.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
