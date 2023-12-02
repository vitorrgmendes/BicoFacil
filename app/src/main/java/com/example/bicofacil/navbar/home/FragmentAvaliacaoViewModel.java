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

}