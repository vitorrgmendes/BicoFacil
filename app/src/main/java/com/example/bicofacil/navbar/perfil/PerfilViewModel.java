package com.example.bicofacil.navbar.perfil;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.UsuarioDao;

public class PerfilViewModel extends ViewModel {

    public PerfilViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }

    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;

    public void atualizandoCampos (TextView nome, TextView email){
        nome.setText(usuarioViewModel.getNome().getValue());
        email.setText(usuarioViewModel.getNome().getValue());
    }
}