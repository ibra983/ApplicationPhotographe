package com.example.appliblogphoto;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
                    startActivity(intent);
                }
            });
            articleLayout.addView(button);
        }
    }
}
