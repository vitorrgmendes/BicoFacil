package com.example.bicofacil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.navbar.perfil.UsuarioViewModel;

public class navBar extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnHome;
    private ImageButton btnFavoritos;
    private ImageButton btnPerfil;
    private ImageButton btnCadastroVaga;
    public NavController navController;

    private UsuarioViewModel usuarioViewModel;
    private AppDatabase db;
    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

        db = Conexao.getInstance(this);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        navController = navHostFragment.getNavController();

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnFavoritos.setOnClickListener(this);

        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);

        btnCadastroVaga = findViewById(R.id.btnCadastroVaga);
        btnCadastrarVaga.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnHome) navController.navigate(R.id.navigation_home);

        if(v==btnFavoritos) navController.navigate(R.id.navigation_favoritos);

        if(v==btnPerfil){
            Boolean isLogged = usuarioViewModel != null ? usuarioViewModel.getLogin().getValue() : null;
            if (isLogged == null || !isLogged) {
            navController.navigate(R.id.navigation_login);}
            else{
                navController.navigate(R.id.navigation_perfil);
            }
            }
    }

    public UsuarioViewModel getUsuarioViewModel() {
        return usuarioViewModel;
    }
}

