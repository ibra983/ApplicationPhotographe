package com.example.appliblogphoto;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG; // Importer la constante TAG pour les logs

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.Log; // Importer la classe Log pour les logs
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_pageArticle; // Déclaration du bouton pour accéder à la page des articles
    private DatabaseHelper dbHelper; // Référence à l'instance de DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Affichage de la mise en page principale de l'application

        dbHelper = new DatabaseHelper(this); // Instanciation de DatabaseHelper pour gérer les opérations sur la base de données

        int userID = getIntent().getIntExtra("userID", -1); // Récupération de l'ID de l'utilisateur connecté depuis l'intent

        // Récupération du jeton FCM (Firebase Cloud Messaging) pour les notifications push
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // Récupération du jeton FCM avec succès
                            String token = task.getResult();
                            // Log et affichage du jeton
                            Log.d(TAG, "FCM Token: " + token);
                            Toast.makeText(MainActivity.this, "FCM Token: " + token, Toast.LENGTH_SHORT).show();
                        } else {
                            // Échec de la récupération du jeton FCM
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                        }
                    }
                });

        btn_pageArticle = findViewById(R.id.btn_pageArticle); // Liaison du bouton pour accéder à la page des articles défini dans le fichier de mise en page XML

        // Définition de l'écouteur de clic pour le bouton d'accès à la page des articles
        btn_pageArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passage de l'ID de l'utilisateur à AddArticleActivity pour ajouter un nouvel article
                Intent intent = new Intent(MainActivity.this, com.example.appliblogphoto.AddArticleActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // Chargement des articles depuis la base de données
        List<Article> articlesList = dbHelper.getAllArticles();

        // Création et affichage des boutons pour chaque article
        createArticleButtons(articlesList, userID);
    }

    // Méthode pour créer et afficher les boutons pour chaque article
    private void createArticleButtons(List<Article> articlesList, int userID) {
        LinearLayout articleLayout = findViewById(R.id.articleContainer); // LinearLayout dans le layout principal de l'application
        articleLayout.removeAllViews(); // Suppression des vues précédentes pour éviter les duplications

        // Création d'un bouton pour chaque article
        for (Article article : articlesList) {
            Button button = new Button(this);
            button.setText(article.getTitle()); // Définition du texte du bouton comme le titre de l'article
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Passage des détails de l'article à ArticleDetailActivity
                    Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                    intent.putExtra("userID", userID); // Transmission de l'ID de l'utilisateur à ArticleDetailActivity
                    intent.putExtra("articleID", article.getId()); // Transmission de l'ID de l'article sélectionné
                    intent.putExtra("articleTitle", article.getTitle()); // Transmission du titre de l'article
                    intent.putExtra("articleImageUrl", article.getImageUrl()); // Transmission de l'URL de l'image de l'article
                    intent.putExtra("articleContent", article.getContent()); // Transmission du contenu de l'article

                    // Chargement des commentaires associés à cet article depuis la base de données
                    List<Commentaire> commentaires = dbHelper.getAllCommentaires(article.getId());

                    // Conversion de la liste de commentaires en une chaîne de caractères pour transmission
                    StringBuilder commentaireStringBuilder = new StringBuilder();
                    for (Commentaire commentaire : commentaires) {
                        commentaireStringBuilder.append(commentaire.getCommentaire()).append("\n");
                    }
                    intent.putExtra("articleCommentaire", commentaireStringBuilder.toString()); // Transmission des commentaires de l'article
                    startActivity(intent); // Démarrage de l'activité ArticleDetailActivity
                }
            });
            articleLayout.addView(button); // Ajout du bouton au layout
        }
    }
}
