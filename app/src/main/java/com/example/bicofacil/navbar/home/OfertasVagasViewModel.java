package com.example.bicofacil.navbar.home;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.favoritos.Favoritos;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class OfertasVagasViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private MutableLiveData<List<Publicacao>> listaVagas;
    public OfertasVagasViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                 PublicacaoDao publicacaoDao, FavoritosDao favoritosDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = favoritosDao;
        this.listaVagas = new MutableLiveData<>();
    }
    public LiveData<List<Publicacao>> getListaVagas() {
        return listaVagas;
    }

    public void carregarLista(String chaveLista, int id) {
        new Thread(() -> {
            if (chaveLista == "vagas") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesVagas();
                listaVagas.postValue(publicacoes);
            }

            if (chaveLista == "servicos") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesServicos();
                listaVagas.postValue(publicacoes);
            }

            if (chaveLista == "publicacoesPorId") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesPorUsuarioId(id);
                listaVagas.postValue(publicacoes);
            }

        }).start();
    }

    public void atualizarFavorito(int idPublicacao, boolean favorito,FavoritoAtualizadoCallback callback){
        new Thread(() -> {
            publicacaoDao.atualizarFavorito(idPublicacao,favorito);
            callback.onFavoritoAtualizado();
        }).start();
    }



    public void salvarPublicacoesFavoritas(int usuarioId) {
        new Thread(() -> {
            List<Publicacao> publicacoesFavoritas = publicacaoDao.obterTodasPublicacoes();

            for (Publicacao publicacao : publicacoesFavoritas) {
                Favoritos existente = favoritosDao.obterFavoritoPorUsuarioEPublicacao(usuarioId, publicacao.id);
                    if (existente == null && publicacao.favorito) {
                        Favoritos novoFavorito = new Favoritos();
                        novoFavorito.usuarioId = usuarioId;
                        novoFavorito.publicacaoId = publicacao.id;
                        favoritosDao.inserirFavorito(novoFavorito);
                    }
                    if(!publicacao.favorito && existente!=null){
                        favoritosDao.deletarFavorito(existente);
                    }

            }
        }).start();
    }


    public void carregarFavoritosEAtualizarPublicacoes(String chaveLista, int usuarioId) {
        new Thread(() -> {
            List<Favoritos> favoritos = favoritosDao.obterFavoritosPorUsuarioId(usuarioId);

            List<Integer> idsPublicacoesFavoritas = new ArrayList<>();
            for (Favoritos favorito : favoritos) {
                idsPublicacoesFavoritas.add(favorito.publicacaoId);
            }

            publicacaoDao.atualizarTodosFavoritosParaFalse();

            publicacaoDao.atualizarFavoritosParaTrue(idsPublicacoesFavoritas);

            if (chaveLista == "vagas") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesVagas();
                listaVagas.postValue(publicacoes);
            }

            if (chaveLista == "servicos") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesServicos();
                listaVagas.postValue(publicacoes);
            }

            if (chaveLista == "publicacoesPorId") {
                List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesPorUsuarioId(usuarioId);
                listaVagas.postValue(publicacoes);
            }
        }).start();
    }



}
