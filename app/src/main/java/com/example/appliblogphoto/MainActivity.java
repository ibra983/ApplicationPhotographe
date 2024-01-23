package com.example.appliblogphoto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil);


        // Récupérez la référence du BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        // Configurer la navigation avec le NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
