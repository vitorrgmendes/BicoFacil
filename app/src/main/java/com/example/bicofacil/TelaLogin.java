package com.example.bicofacil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TelaLogin extends AppCompatActivity
{
    private TextView txtCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        inicializarComponentes();

        txtCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent telaCadastro = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(telaCadastro);
            }
        });
    }

    private void inicializarComponentes()
    {
        txtCadastrar = findViewById(R.id.txt_cadastro);
    }
}