package com.example.appliblogphoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appliblogphoto.DatabaseHelper;
import com.example.appliblogphoto.R;

public class AddArticleActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextImageUrl, editTextContent;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        // Récupération des références des vues à partir de leurs identifiants
        editTextTitle = findViewById(R.id.editTextArticleTitle);
        editTextImageUrl = findViewById(R.id.editTextArticleImageUrl);
        editTextContent = findViewById(R.id.editTextArticleContent);
        buttonSubmit = findViewById(R.id.buttonSubmitArticle);

        // Définition du listener de clic sur le bouton de soumission
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitArticle(); // Appel de la méthode pour soumettre l'article
            }
        });

        // Récupération de la référence du bouton de retour et définition du listener de clic
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Revenir à la page précédente en fermant cette activité
            }
        });
    }

    // Méthode pour soumettre un article
    private void submitArticle() {
        // Récupération du texte entré par l'utilisateur dans les champs
        String title = editTextTitle.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        // Vérification que les champs ne sont pas vides
        if (!title.isEmpty() && !imageUrl.isEmpty() && !content.isEmpty()) {
            // Enregistrement de l'article dans la base de données
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            dbHelper.addArticle(title, imageUrl, content); // Ajout de l'article dans la base de données

            // Affichage d'un message de succès à l'utilisateur
            Toast.makeText(this, "Article ajouté", Toast.LENGTH_SHORT).show();
            finish(); // Fermeture de l'activité et retour à la liste des articles
        } else {
            // Affichage d'un message d'erreur si des champs sont vides
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
