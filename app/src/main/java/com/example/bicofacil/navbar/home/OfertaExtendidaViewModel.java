package com.example.bicofacil.navbar.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.util.List;

public class OfertaExtendidaViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private AvaliacaoDao avaliacaoDao;
    private MutableLiveData<Publicacao> publicacao;
    public LiveData<Publicacao> getPublicacao() {
        return publicacao;
    }
    public OfertaExtendidaViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                            PublicacaoDao publicacaoDao, FavoritosDao favoritosDao, AvaliacaoDao
                                            avaliacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = favoritosDao;
        this.avaliacaoDao = avaliacaoDao;
        this.publicacao = new MutableLiveData<>();
    }

    public void buscarDadosPublicacao(int idPublicacao){
        new Thread(() -> {
            Publicacao novaPublicacao = publicacaoDao.obterPublicacoesPorId(idPublicacao);
            publicacao.postValue(novaPublicacao);
        }).start();
    }

}
