package com.example.appliblogphoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername; // Champ de saisie pour le nom d'utilisateur
    private EditText etPassword; // Champ de saisie pour le mot de passe
    public Button btnLogin; // Bouton de connexion
    public TextView inscription; // Texte pour rediriger vers la page d'inscription
    private DatabaseHelper dbHelper; // Référence à l'instance de DatabaseHelper
    private int userID; // ID de l'utilisateur connecté

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Affichage de la mise en page de connexion définie dans le fichier XML

        dbHelper = new DatabaseHelper(this); // Instanciation de DatabaseHelper pour gérer les opérations sur la base de données

        etUsername = findViewById(R.id.etUsername); // Liaison du champ de saisie pour le nom d'utilisateur défini dans le fichier de mise en page XML
        etPassword = findViewById(R.id.etPassword); // Liaison du champ de saisie pour le mot de passe défini dans le fichier de mise en page XML
        btnLogin = findViewById(R.id.btnLogin); // Liaison du bouton de connexion défini dans le fichier de mise en page XML
        inscription = findViewById(R.id.inscription); // Liaison du texte pour rediriger vers la page d'inscription défini dans le fichier de mise en page XML

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etUsername.getText().toString(); // Récupération du nom d'utilisateur saisi
                String password = etPassword.getText().toString(); // Récupération du mot de passe saisi
                // Vérification si l'utilisateur existe et récupération de son ID
                userID = dbHelper.getUserID(login, password);

                if (userID != -1) { // Si l'ID de l'utilisateur est valide
                    Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                    // Création de l'Intent pour démarrer MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userID", userID); // Transmission de l'ID de l'utilisateur à MainActivity
                    startActivity(intent);
                    finish(); // Fermeture de LoginActivity après la connexion réussie
                } else {
                    Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Ajout d'un écouteur de clic pour rediriger vers la page d'inscription
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'écran d'inscription
                Intent intent = new Intent(LoginActivity.this, Inscription.class);
                startActivity(intent);
            }
        });

    }
    public int getUserID() {
        return userID; // Méthode pour obtenir l'ID de l'utilisateur connecté
    }
}
