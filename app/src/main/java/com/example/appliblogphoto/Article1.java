package com.example.appliblogphoto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Article1 extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article1);

        dbHelper = new DatabaseHelper(this);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Revenir à la page précédente
            }
        });


        // Initialisation du bouton publier
        Button btnPublier = findViewById(R.id.btnPublier);
        btnPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextContenu = findViewById(R.id.editTextContenu);
                String contenu = editTextContenu.getText().toString();
                // Ajouter le commentaire à la base de données
                ajouterCommentaire(contenu);
                // Effacer le texte de l'EditText
                editTextContenu.setText("");
            }
        });

    }

    // Méthode pour ajouter un commentaire à la base de données
    private void ajouterCommentaire (String contenuCommentaire){
        dbHelper.ajouterCommentaire(contenuCommentaire); // Appel de la méthode sans spécifier l'identifiant de l'utilisateur
    }
}

