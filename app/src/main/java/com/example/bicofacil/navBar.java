package com.example.bicofacil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

public class navBar extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnHome;
    private ImageButton btnFavoritos;
    private ImageButton btnPerfil;
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

        carregarCache(getBaseContext());
    }

    @Override
    public void onClick(View v) {
        if(v==btnHome) navController.navigate(R.id.navigation_home);

        if(v==btnFavoritos){
            Bundle bundle = new Bundle();
            bundle.putString("chave", "favoritos");

            Boolean login = usuarioViewModel != null ? usuarioViewModel.getLogin().getValue() : null;
            if (login == null || !login) {
                navController.navigate(R.id.navigation_login);
                Toast.makeText(navBar.this, "É necessário fazer o login para acessar " +
                                "suas publicações favoritas!", Toast.LENGTH_SHORT)
                        .show();}
            else{
                navController.navigate(R.id.navigation_favoritos,bundle);
            }
            }

        if(v==btnPerfil){
            Boolean login = usuarioViewModel != null ? usuarioViewModel.getLogin().getValue() : null;
            if (login == null || !login) {
            navController.navigate(R.id.navigation_login);}
            else{
                navController.navigate(R.id.navigation_perfil);
            }
            }
    }

    public UsuarioViewModel getUsuarioViewModel() {
        return usuarioViewModel;
    }


    public void carregarCache(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        usuarioViewModel.setId(sharedPreferences.getInt("id", 0));
        usuarioViewModel.setNome(sharedPreferences.getString("nome", ""));
        usuarioViewModel.setEmail(sharedPreferences.getString("email", ""));
        usuarioViewModel.setTelefone(sharedPreferences.getString("telefone", ""));
        usuarioViewModel.setSenha(sharedPreferences.getString("senha", ""));
        usuarioViewModel.setLogin(sharedPreferences.getBoolean("login", false));
    }
}
