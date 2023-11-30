package com.example.bicofacil.navbar.home;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.util.List;

public class OfertasVagasViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private Publicacao publicacao;
    private MutableLiveData<List<Publicacao>> listaVagas;
    public OfertasVagasViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                       PublicacaoDao publicacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
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

}
