package com.example.bicofacil.navbar.home;


import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

import java.io.ByteArrayOutputStream;

public class FragmentPublicacaoViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private Publicacao publicacao;
    public FragmentPublicacaoViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel,
                                       PublicacaoDao publicacaoDao) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
        this.publicacaoDao = publicacaoDao;
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

    public byte[] converterImagemParaBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void criarNovaPublicacao(int tipoOferta, byte[] imagemPublicacao, String titulo,
                                    String descricao, String valor, String horario,
                                    String contato) {
        new Thread(() -> {
            try {
                Log.i("Consulta imagem", "ImagemPublicação:: " + imagemPublicacao);
                Publicacao novaPublicacao = new Publicacao();
                novaPublicacao.usuarioId = usuarioViewModel.getId().getValue();
                novaPublicacao.tipoOferta = tipoOferta;
                novaPublicacao.imagemPublicacao = imagemPublicacao;
                novaPublicacao.titulo = titulo;
                novaPublicacao.descricao = descricao;
                novaPublicacao.valor = valor;
                novaPublicacao.horario = horario;
                novaPublicacao.contato = contato;
                publicacaoDao.inserirPublicacao(novaPublicacao);
                fim.postValue(true);

            } catch (android.database.sqlite.SQLiteConstraintException e) {

            }
        }).start();
    }


}