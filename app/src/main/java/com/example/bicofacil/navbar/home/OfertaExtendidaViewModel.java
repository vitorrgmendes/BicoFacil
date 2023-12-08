package com.example.bicofacil.navbar.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.avaliacao.Avaliacao;
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
    private MutableLiveData<Boolean> fimDelet;
    public LiveData<Boolean> getFimDelet() {
        return fimDelet;
    }
    private MutableLiveData<List<Avaliacao>> listaAvaliacoes;
    public LiveData<List<Avaliacao>> getListaAvaliacoes() {
        return listaAvaliacoes;
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
        this.listaAvaliacoes = new MutableLiveData<>();
        this.fimDelet = new MutableLiveData<>();
    }

    public void buscarDadosPublicacao(int idPublicacao){
        new Thread(() -> {
            Publicacao novaPublicacao = publicacaoDao.obterPublicacoesPorId(idPublicacao);
            publicacao.postValue(novaPublicacao);
        }).start();
    }

    public void buscarListaAvaliacoes(int idPulicacao){
        new Thread(() -> {
            List<Avaliacao> novaListaAvaliacoes = avaliacaoDao.obterAvaliacoesPorPublicacaoId(idPulicacao);
            listaAvaliacoes.postValue(novaListaAvaliacoes);
        }).start();
    }

    public void deletarPulicacao(int publicacaoId){
        new Thread(() -> {
            List<Avaliacao> listaAvaliacao = avaliacaoDao.obterAvaliacoesPorPublicacaoId(publicacaoId);

            if (listaAvaliacao != null || !listaAvaliacao.isEmpty()){
                avaliacaoDao.deletarAvaliacoesPorPublicacaoId(publicacaoId);
            }

            Publicacao publicacao = publicacaoDao.obterPublicacoesPorId(publicacaoId);
            publicacaoDao.deletarPublicacao(publicacao);

            fimDelet.postValue(true);
        }).start();
    }

    public void deletarAvaliacao(int idAvaliacao, int idPublicacao){
        new Thread(() -> {
            avaliacaoDao.deletarAvaliacaoPorId(idAvaliacao);
            buscarListaAvaliacoes(idPublicacao);
        }).start();
    }

}
