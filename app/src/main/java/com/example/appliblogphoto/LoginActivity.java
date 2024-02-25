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

    private EditText etUsername;
    private EditText etPassword;
    public Button btnLogin;
    public TextView inscription; // Changer le nom de tvSignUpPrompt à inscription
    private DatabaseHelper dbHelper;
    private int userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        inscription = findViewById(R.id.inscription);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                // Vérifier si l'utilisateur existe et récupérer son login
                userID = dbHelper.getUserID(login, password);

                if (userID != -1) {
                    Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                    // Création de l'Intent pour démarrer Article1
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userID", userID); // Transmettre l'ID de l'utilisateur à Article1
                    startActivity(intent);
                    finish();  // Pour fermer LoginActivity après la connexion réussie
                } else {
                    Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Ajouter un écouteur de clic pour rediriger vers la page d'inscription
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
        return userID;
    }
}
