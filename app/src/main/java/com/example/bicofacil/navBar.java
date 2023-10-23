package com.example.bicofacil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class navBar extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnHome;
    private ImageButton btnFavoritos;
    private ImageButton btnPerfil;
    public NavController navController;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        db = Conexao.getInstance(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnFavoritos.setOnClickListener(this);

        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnHome) navController.navigate(R.id.navigation_home);

        if(v==btnFavoritos) navController.navigate(R.id.navigation_favoritos);

        if(v==btnPerfil) navController.navigate(R.id.navigation_perfil);
    }
}
