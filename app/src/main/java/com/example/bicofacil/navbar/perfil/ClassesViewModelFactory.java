package com.example.bicofacil.navbar.perfil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.BD.usuario.UsuarioDao;

public class ClassesViewModelFactory implements ViewModelProvider.Factory {
    private final UsuarioDao usuarioDao;
    private final UsuarioViewModel usuarioViewModel;

    public ClassesViewModelFactory(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CadastroViewModel.class)) {
            return (T) new CadastroViewModel(usuarioDao);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(usuarioDao, usuarioViewModel);
        }else if (modelClass.isAssignableFrom(PerfilViewModel.class)) {
            return (T) new PerfilViewModel(usuarioDao, usuarioViewModel);
        }
        throw new IllegalArgumentException("Classe de ViewModel desconhecida");
    }
}


