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

        dbHelper = new DatabaseHelper(this);
        editTextContenu = findViewById(R.id.editTextContenu);

        // Récupération des données de l'article(titre, image, content) avec l'intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("articleTitle");
        String imageUrl = intent.getStringExtra("articleImageUrl");
        String content = intent.getStringExtra("articleContent");

        // Récupération de l'ID de l'article à partir de l'intent
        articleID = intent.getIntExtra("articleID", -1);

        // Récupération des données de l'article (commentaire) avec l'intent
        String commentaire = intent.getStringExtra("articleCommentaire");

        // Trouver les vues par leur id et les peupler avec les données de l'article
        TextView titleTextView = findViewById(R.id.detailArticleTitleTextView);
        ImageView imageView = findViewById(R.id.detailArticleImageView);
        TextView contentTextView = findViewById(R.id.detailArticleContentTextView);

        LinearLayout commentaireContainer = findViewById(R.id.commentaireContainer); // Ajouté

        titleTextView.setText(title);
        contentTextView.setText(content);

        // Utilisez Glide pour charger l'image depuis imageUrl dans imageView
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().error(R.drawable.image_error)) // Utilisez une image d'erreur en cas d'échec du chargement
                .into(imageView);

        // Affichage des commentaires
        commentaireContainer.removeAllViews(); // Supprime les vues précédentes pour éviter les duplications

        // Charger les commentaires associés à l'article depuis la base de données
        List<Commentaire> commentaires = dbHelper.getAllCommentaires(articleID);

        // Ajouter dynamiquement un TextView pour chaque commentaire dans le LinearLayout
        for (Commentaire comment : commentaires) {
            TextView textView = new TextView(this);
            textView.setText(comment.getCommentaire()); // Utilisez la méthode getCommentaire() de la classe Commentaire
            textView.setTextSize(16); // Taille du texte
            // Autres paramètres de mise en forme du TextView si nécessaire
            commentaireContainer.addView(textView); // Ajoutez le TextView au conteneur
        }


        Button btnPublier = findViewById(R.id.btnPublier);
        btnPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenez le contenu du commentaire de l'utilisateur
                String contenuCommentaire = editTextContenu.getText().toString();
                // obtenir l'ID de l'utilisateur et l'ID de l'article associé à cet article
                userID = getIntent().getIntExtra("userID", -1); // Obtenir l'ID de l'utilisateur à partir de l'intent
                articleID = getIntent().getIntExtra("articleID", -1); // Obtenir l'ID de l'article à partir de l'intent
                // Ajouter le commentaire à la base de données en utilisant votre DatabaseHelper
                dbHelper.ajouterCommentaire(contenuCommentaire, userID, articleID);
                // Effacez le contenu de l'EditText après avoir ajouté le commentaire
                editTextContenu.setText("");
                // Affichez un message pour indiquer que le commentaire a été ajouté avec succès
                Toast.makeText(ArticleDetailActivity.this, "Commentaire publié avec succès", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ajouterCommentaire(String contenuCommentaire, int userID, int articles_id) {
        dbHelper.ajouterCommentaire(contenuCommentaire, userID, articles_id);
    }
}
