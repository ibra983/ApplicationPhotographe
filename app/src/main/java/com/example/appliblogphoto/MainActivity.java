package com.example.appliblogphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_open;
    Button btn_pageArticle;
    Button bt_openArticle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int userID = getIntent().getIntExtra("userID", -1);

        btn_open = findViewById(R.id.bt_article1);
        btn_pageArticle = findViewById(R.id.bt_pageArticle);
        bt_openArticle = findViewById(R.id.bt_openArticle);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer l'ID de l'utilisateur à Article1
                Intent intent = new Intent(MainActivity.this, Article1.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        btn_pageArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer l'ID de l'utilisateur à Article1
                Intent intent = new Intent(MainActivity.this, com.example.appliblogphoto.AddArticleActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        bt_openArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer les détails de l'article à ArticleDetailActivity
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                intent.putExtra("articleTitle", "Titre de l'article");
                intent.putExtra("articleImageUrl", "URL de l'image de l'article");
                intent.putExtra("articleContent", "Contenu de l'article");
                startActivity(intent);
            }
        });

    }
}
