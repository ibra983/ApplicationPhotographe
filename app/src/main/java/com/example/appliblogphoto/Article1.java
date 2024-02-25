package com.example.appliblogphoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Article1 extends AppCompatActivity {

    private EditText editTextContenu;
    private Button btnPublier;
    private DatabaseHelper dbHelper;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article1);

        dbHelper = new DatabaseHelper(this);
        userID = getIntent().getIntExtra("userID", -1);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Revenir à la page précédente
            }
        });

        editTextContenu = findViewById(R.id.editTextContenu);
        btnPublier = findViewById(R.id.btnPublier);

        btnPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contenu = editTextContenu.getText().toString();
                // Ajouter le commentaire à la base de données avec l'identifiant de l'utilisateur
                ajouterCommentaire(contenu, userID);
                editTextContenu.setText("");
                Toast.makeText(Article1.this, "Commentaire publié avec succès", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ajouterCommentaire(String contenuCommentaire, int userID) {
        dbHelper.ajouterCommentaire(contenuCommentaire, userID);
    }
}

