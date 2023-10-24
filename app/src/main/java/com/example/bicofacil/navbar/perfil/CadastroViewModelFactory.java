package com.example.bicofacil.navbar.perfil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.navbar.perfil.CadastroViewModel;

public class CadastroViewModelFactory implements ViewModelProvider.Factory {
    private final UsuarioDao usuarioDao;

    public CadastroViewModelFactory(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CadastroViewModel.class)) {
            return (T) new CadastroViewModel(usuarioDao);
        }
        throw new IllegalArgumentException("Classe de ViewModel desconhecida");
    }
}

