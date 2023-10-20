package com.example.bicofacil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class navBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        ImageButton btnPerfil;
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent telaPerfil = new Intent(navBar.this, TelaPerfil.class);
                startActivity(telaPerfil);
            }
        });
    }}

