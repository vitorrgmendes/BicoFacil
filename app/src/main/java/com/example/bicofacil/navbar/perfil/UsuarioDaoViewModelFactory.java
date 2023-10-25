package com.example.bicofacil.navbar.perfil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.BD.usuario.UsuarioDao;

public class UsuarioDaoViewModelFactory implements ViewModelProvider.Factory {
    private final UsuarioDao usuarioDao;

    public UsuarioDaoViewModelFactory(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CadastroViewModel.class)) {
            return (T) new CadastroViewModel(usuarioDao);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(usuarioDao);
        }
        throw new IllegalArgumentException("Classe de ViewModel desconhecida");
    }
}


