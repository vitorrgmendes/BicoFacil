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
        if (listaVagas.getValue() == null) {
            carregarListaVagas();
        }
        return listaVagas;
    }

    private final MutableLiveData<Boolean> fimQueryVagas = new MutableLiveData<>();
    public LiveData<Boolean> getfimQueryVagas() {
        return fimQueryVagas;
    }

    public void carregarListaVagas() {
        new Thread(() -> {
            List<Publicacao> publicacoes = publicacaoDao.obterPublicacoesVagas();
            listaVagas.postValue(publicacoes);
        }).start();
    }

}
