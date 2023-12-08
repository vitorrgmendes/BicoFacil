package com.example.bicofacil.navbar.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.avaliacao.Avaliacao;
import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.Favoritos;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.util.List;

public class FragmentAvaliacaoViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private AvaliacaoDao avaliacaoDao;
    private final MutableLiveData<Boolean> fim = new MutableLiveData<>();
    public LiveData<Boolean> getFim() {
        return fim;
    }
    private final MutableLiveData<Boolean> fimEdicao = new MutableLiveData<>();
    public LiveData<Boolean> getFimEdicao() {
        return fimEdicao;
    }
    private final MutableLiveData<Avaliacao> avaliacao = new MutableLiveData<>();
    public LiveData<Avaliacao> getAvaliacao() {
        return avaliacao;
    }
    public FragmentAvaliacaoViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                    PublicacaoDao publicacaoDao, FavoritosDao favoritosDao, AvaliacaoDao
                                            avaliacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
        this.favoritosDao = favoritosDao;
        this.avaliacaoDao = avaliacaoDao;
    }

    public void cadastrarAvaliacao(int idPublicacao, int idUsuario, int nota, String comentario,
                                   String nomeUsuario){
        new Thread(() -> {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.publicacaoId = idPublicacao;
            avaliacao.usuarioId = idUsuario;
            avaliacao.nota = nota;
            avaliacao.comentario = comentario;
            avaliacao.nomeUsuario = nomeUsuario;

            avaliacaoDao.inserirAvaliacao(avaliacao);

            fim.postValue(true);
        }).start();
    }

    public void atualizarCampos(int idAvaliacao){
        new Thread(() -> {
            if(idAvaliacao!=0){
                Avaliacao avaliacaoCarregada = avaliacaoDao.obterAvaliacaoPorId(idAvaliacao);
                avaliacao.postValue(avaliacaoCarregada);
            }
        }).start();
    }

    public void editarAvaliacao(int idAvaliacao, int nota, String comentario){
        new Thread(() -> {
            Avaliacao avaliacaoEditada = avaliacaoDao.obterAvaliacaoPorId(idAvaliacao);
            avaliacaoEditada.nota = nota;
            avaliacaoEditada.comentario = comentario;
            avaliacaoDao.atualizarAvaliacao(avaliacaoEditada);
            fimEdicao.postValue(true);
        }).start();
    }

}