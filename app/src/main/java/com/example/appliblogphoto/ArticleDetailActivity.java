package com.example.appliblogphoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class ArticleDetailActivity extends AppCompatActivity {
    private int userID;
    private int articleID;

    private DatabaseHelper dbHelper;
    private EditText editTextContenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Récupération de la référence de l'EditText pour le contenu du commentaire
        editTextContenu = findViewById(R.id.editTextContenu);

        // Récupération des données de l'article depuis l'intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("articleTitle");
        String imageUrl = intent.getStringExtra("articleImageUrl");
        String content = intent.getStringExtra("articleContent");
        articleID = intent.getIntExtra("articleID", -1); // Récupération de l'ID de l'article

        // Récupération du commentaire associé à l'article depuis l'intent
        String commentaire = intent.getStringExtra("articleCommentaire");

        // Trouver les vues par leur identifiant et les peupler avec les données de l'article
        TextView titleTextView = findViewById(R.id.detailArticleTitleTextView);
        ImageView imageView = findViewById(R.id.detailArticleImageView);
        TextView contentTextView = findViewById(R.id.detailArticleContentTextView);

        // Affichage des données de l'article dans les vues correspondantes
        titleTextView.setText(title);
        contentTextView.setText(content);

        // Utilisation de Glide pour charger l'image depuis l'URL dans l'ImageView
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().error(R.drawable.image_error)) // Utilisation d'une image d'erreur en cas de chargement échoué
                .into(imageView);

        // Affichage des commentaires associés à l'article
        LinearLayout commentaireContainer = findViewById(R.id.commentaireContainer);
        commentaireContainer.removeAllViews(); // Suppression des vues précédentes pour éviter les duplications

        // Récupération des commentaires associés à l'article depuis la base de données
        List<Commentaire> commentaires = dbHelper.getAllCommentaires(articleID);

        // Ajout dynamique d'un TextView pour chaque commentaire dans le LinearLayout
        for (Commentaire comment : commentaires) {
            TextView textView = new TextView(this);
            textView.setText(comment.getCommentaire()); // Utilisation de la méthode getCommentaire() de la classe Commentaire
            textView.setTextSize(16); // Taille du texte
            commentaireContainer.addView(textView); // Ajout du TextView au conteneur
        }

        // Définition du listener de clic sur le bouton "Publier"
        Button btnPublier = findViewById(R.id.btnPublier);
        btnPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération du contenu du commentaire saisi par l'utilisateur
                String contenuCommentaire = editTextContenu.getText().toString();
                // Récupération de l'ID de l'utilisateur et de l'ID de l'article associé à cet article
                userID = getIntent().getIntExtra("userID", -1);
                articleID = getIntent().getIntExtra("articleID", -1);
                // Ajout du commentaire à la base de données en utilisant le DatabaseHelper
                ajouterCommentaire(contenuCommentaire, userID, articleID);
                // Effacement du contenu de l'EditText après l'ajout du commentaire
                editTextContenu.setText("");
                // Affichage d'un message pour indiquer que le commentaire a été ajouté avec succès
                Toast.makeText(ArticleDetailActivity.this, "Commentaire publié avec succès", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Méthode pour ajouter un commentaire à la base de données
    private void ajouterCommentaire(String contenuCommentaire, int userID, int articles_id) {
        dbHelper.ajouterCommentaire(contenuCommentaire, userID, articles_id);
    }
}
