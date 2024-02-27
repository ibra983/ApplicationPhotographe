package com.example.appliblogphoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Inscription extends AppCompatActivity {

    private EditText etUsernameInscription; // Champ de saisie pour le nom d'utilisateur
    private EditText etMailInscription; // Champ de saisie pour l'adresse e-mail
    private EditText etPasswordInscription; // Champ de saisie pour le mot de passe
    private DatabaseHelper dbHelper; // Référence à l'instance de DatabaseHelper pour interagir avec la base de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription); // Affichage de la mise en page d'inscription définie dans le fichier XML

        dbHelper = new DatabaseHelper(this); // Instanciation de DatabaseHelper pour gérer les opérations sur la base de données

        etUsernameInscription = findViewById(R.id.etUsernameInscription); // Liaison du champ de saisie pour le nom d'utilisateur défini dans le fichier de mise en page XML
        etMailInscription = findViewById(R.id.etMailInscription); // Liaison du champ de saisie pour l'adresse e-mail défini dans le fichier de mise en page XML
        etPasswordInscription = findViewById(R.id.etPasswordInscription); // Liaison du champ de saisie pour le mot de passe défini dans le fichier de mise en page XML
        Button btnInscription = findViewById(R.id.btnLoginInscription); // Liaison du bouton d'inscription défini dans le fichier de mise en page XML

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs des champs de saisie
                String username = etUsernameInscription.getText().toString().trim();
                String mail = etMailInscription.getText().toString().trim();
                String password = etPasswordInscription.getText().toString().trim();

                // Validation de l'email et du mot de passe avant l'ajout dans la base de données
                if (isValidEmail(mail) && isValidPassword(password)) {
                    // Ajout de l'utilisateur dans la base de données seulement si les champs ne sont pas vides
                    dbHelper.ajouterNouvelUtilisateur(username, mail, password);
                    // Affichage d'un message de confirmation
                    Toast.makeText(Inscription.this, "Inscription réussie pour " + username, Toast.LENGTH_SHORT).show();
                    finish(); // Fermeture de l'activité d'inscription après l'inscription réussie
                } else {
                    // Affichage d'un message d'erreur si la validation échoue
                    if (!isValidEmail(mail)) {
                        Toast.makeText(Inscription.this, "L'email n'est pas valide.", Toast.LENGTH_SHORT).show();
                    }
                    if (!isValidPassword(password)) {
                        Toast.makeText(Inscription.this, "Le mot de passe doit contenir au moins 8 caractères, dont au moins une majuscule, une minuscule et un chiffre.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // Vérifie la validité de l'email avec une expression régulière
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Vérifie la validité du mot de passe avec une expression régulière
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordRegex);
    }
}
