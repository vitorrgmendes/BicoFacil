package com.example.bicofacil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.navbar.home.EditarPublicacaoViewModel;
import com.example.bicofacil.navbar.home.FragmentAvaliacaoViewModel;
import com.example.bicofacil.navbar.home.FragmentPublicacaoViewModel;
import com.example.bicofacil.navbar.home.OfertaExtendidaViewModel;
import com.example.bicofacil.navbar.home.OfertasViewModel;
import com.example.bicofacil.navbar.perfil.AlterarSenhaViewModel;
import com.example.bicofacil.navbar.perfil.CadastroViewModel;
import com.example.bicofacil.navbar.perfil.EditarViewModel;
import com.example.bicofacil.navbar.perfil.LoginViewModel;
import com.example.bicofacil.navbar.perfil.PerfilViewModel;

public class ClassesViewModelFactory implements ViewModelProvider.Factory {
    private final UsuarioDao usuarioDao;
    private final UsuarioViewModel usuarioViewModel;
    private final PublicacaoDao publicacaoDao;
    private final FavoritosDao favoritosDao;
    private final AvaliacaoDao avaliacaoDao;

    public ClassesViewModelFactory(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = null;
        this.publicacaoDao = null;
        this.favoritosDao = null;
        this.avaliacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = null;
        this.favoritosDao = null;
        this.avaliacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel, 
                                   PublicacaoDao publicacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = null;
        this.avaliacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                   PublicacaoDao publicacaoDao, FavoritosDao favoritosDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = favoritosDao;
        this.avaliacaoDao = null;
    }

    public ClassesViewModelFactory(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                   PublicacaoDao publicacaoDao, FavoritosDao favoritosDao, AvaliacaoDao
                                           avaliacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = favoritosDao;
        this.avaliacaoDao = avaliacaoDao;
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
        }else if (modelClass.isAssignableFrom(OfertasViewModel.class)) {
            return (T) new OfertasViewModel(usuarioDao, usuarioViewModel, publicacaoDao, favoritosDao,
                    avaliacaoDao);
        }else if (modelClass.isAssignableFrom(OfertaExtendidaViewModel.class)) {
            return (T) new OfertaExtendidaViewModel(usuarioDao, usuarioViewModel, publicacaoDao,
                    favoritosDao, avaliacaoDao);
        }else if (modelClass.isAssignableFrom(FragmentAvaliacaoViewModel.class)) {
            return (T) new FragmentAvaliacaoViewModel(usuarioDao, usuarioViewModel, publicacaoDao,
                    favoritosDao, avaliacaoDao);
        }else if (modelClass.isAssignableFrom(EditarPublicacaoViewModel.class)) {
            return (T) new EditarPublicacaoViewModel(usuarioDao, usuarioViewModel, publicacaoDao,
                    favoritosDao, avaliacaoDao);
        }
        throw new IllegalArgumentException("Classe de ViewModel desconhecida");
    }
}


