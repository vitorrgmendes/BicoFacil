package com.example.bicofacil.navbar.home;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.io.ByteArrayOutputStream;

public class EditarPublicacaoViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private AvaliacaoDao avaliacaoDao;
    private MutableLiveData<Publicacao> publicacao;
    public LiveData<Publicacao> getPublicacao() {
        return publicacao;
    }
    private final MutableLiveData<Boolean> fim = new MutableLiveData<>();
    public LiveData<Boolean> getFim() {
        return fim;
    }
    private static final String TITUTLO_REGEX = "^.{3,}$";
    private static final String DESCRICAO_REGEX = "^.{15,}$";
    private static final String TELEFONE_REGEX = "^.{8,}$";
    public boolean validarDescricao(String descricao){ return descricao.matches(DESCRICAO_REGEX); };
    public boolean validarTitulo(String titulo){ return titulo.matches(TITUTLO_REGEX); };
    public boolean validarTelefone(String telefone) {
        return telefone.matches(TELEFONE_REGEX);
    }
    public EditarPublicacaoViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
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

    public byte[] converterImagemParaBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void atualizarPublicacao(int idPublicacao, byte[] imagemPublicacao, String titulo,
                                    String descricao, String valor, String horario,
                                    String contato) {
        new Thread(() -> {
            try {
                Publicacao novaPublicacao = publicacaoDao.obterPublicacoesPorId(idPublicacao);
                novaPublicacao.imagemPublicacao = imagemPublicacao;
                novaPublicacao.titulo = titulo;
                novaPublicacao.descricao = descricao;
                novaPublicacao.valor = valor;
                novaPublicacao.horario = horario;
                novaPublicacao.contato = contato;
                publicacaoDao.atualizarPublicacao(novaPublicacao);
                fim.postValue(true);

            } catch (android.database.sqlite.SQLiteConstraintException e) {

            }
        }).start();
    }

}