package com.example.appliblogphoto;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import android.util.Log;



import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_pageArticle;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        int userID = getIntent().getIntExtra("userID", -1);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // Récupérer le jeton FCM avec succès
                            String token = task.getResult();
                            // Log et afficher le jeton
                            Log.d(TAG, "FCM Token: " + token);
                            Toast.makeText(MainActivity.this, "FCM Token: " + token, Toast.LENGTH_SHORT).show();
                        } else {
                            // Échec de la récupération du jeton FCM
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                        }
                    }
                });

        btn_pageArticle = findViewById(R.id.btn_pageArticle);


        btn_pageArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer l'ID de l'utilisateur à Article
                Intent intent = new Intent(MainActivity.this, com.example.appliblogphoto.AddArticleActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });



        // Charger les articles depuis la base de données
        List<Article> articlesList = dbHelper.getAllArticles();

        // Créer et afficher les boutons pour chaque article
        createArticleButtons(articlesList, userID);
    }

    private void createArticleButtons(List<Article> articlesList, int userID) {
        LinearLayout articleLayout = findViewById(R.id.articleContainer); // LinearLayout dans votre layout principal
        articleLayout.removeAllViews(); // Supprime les vues précédentes pour éviter les duplications

        // Créer un bouton pour chaque article
        for (Article article : articlesList) {
            Button button = new Button(this);
            button.setText(article.getTitle());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Passer les détails de l'article à ArticleDetailActivity
                    Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("articleID", article.getId());
                    intent.putExtra("articleTitle", article.getTitle());
                    intent.putExtra("articleImageUrl", article.getImageUrl());
                    intent.putExtra("articleContent", article.getContent());
                    // Charger les commentaires associés à cet article

                    List<Commentaire> commentaires = dbHelper.getAllCommentaires(article.getId());

                    // Convertir la liste de commentaires en une chaîne de caractères et la passer à ArticleDetailActivity
                    StringBuilder commentaireStringBuilder = new StringBuilder();
                    for (Commentaire commentaire : commentaires) {
                        commentaireStringBuilder.append(commentaire.getCommentaire()).append("\n");
                    }
                    intent.putExtra("articleCommentaire", commentaireStringBuilder.toString());
                    startActivity(intent);
                }
            });
            articleLayout.addView(button);

        }
    }
}
