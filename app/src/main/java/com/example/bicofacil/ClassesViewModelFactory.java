package com.example.bicofacil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.navbar.home.FragmentPublicacaoViewModel;
import com.example.bicofacil.navbar.home.OfertasVagasViewModel;
import com.example.bicofacil.navbar.perfil.AlterarSenhaViewModel;
import com.example.bicofacil.navbar.perfil.CadastroViewModel;
import com.example.bicofacil.navbar.perfil.EditarViewModel;
import com.example.bicofacil.navbar.perfil.LoginViewModel;
import com.example.bicofacil.navbar.perfil.PerfilViewModel;

public class ClassesViewModelFactory implements ViewModelProvider.Factory {
    private final UsuarioDao usuarioDao;
    private final UsuarioViewModel usuarioViewModel;
    private final PublicacaoDao publicacaoDao;

    public ClassesViewModelFactory(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = null;
        this.publicacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel, 
                                   PublicacaoDao publicacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
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
        }else if (modelClass.isAssignableFrom(EditarViewModel.class)) {
            return (T) new EditarViewModel(usuarioDao, usuarioViewModel);
        }else if (modelClass.isAssignableFrom(AlterarSenhaViewModel.class)) {
            return (T) new AlterarSenhaViewModel(usuarioDao, usuarioViewModel);
        }else if (modelClass.isAssignableFrom(FragmentPublicacaoViewModel.class)) {
            return (T) new FragmentPublicacaoViewModel(usuarioDao, usuarioViewModel, publicacaoDao);
        }else if (modelClass.isAssignableFrom(OfertasVagasViewModel.class)) {
            return (T) new OfertasVagasViewModel(usuarioDao, usuarioViewModel, publicacaoDao);
        }
        throw new IllegalArgumentException("Classe de ViewModel desconhecida");
    }
}


