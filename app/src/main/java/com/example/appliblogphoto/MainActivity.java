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
    TextView tv_inscription;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int userID = getIntent().getIntExtra("userID", -1);

        btn_open = findViewById(R.id.bt_article1);
        tv_inscription = findViewById(R.id.inscription);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer l'ID de l'utilisateur à Article1
                Intent intent = new Intent(MainActivity.this, Article1.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
