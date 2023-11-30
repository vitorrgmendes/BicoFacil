package com.example.bicofacil.BD.publicacao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface PublicacaoDao {

    @Insert
    void inserirPublicacao(Publicacao publicacao);

    @Update
    void atualizarPublicacao(Publicacao publicacao);

    @Delete
    void deletarPublicacao(Publicacao publicacao);

    @Query("SELECT * FROM publicacao")
    List<Publicacao> obterTodasPublicacoes();

    @Query("SELECT * FROM publicacao WHERE tipoOferta = 1")
    List<Publicacao> obterPublicacoesVagas();

    @Query("SELECT * FROM publicacao WHERE tipoOferta = 2")
    List<Publicacao> obterPublicacoesServicos();

    @Query("SELECT * FROM publicacao WHERE usuarioId = :usuarioId")
    List<Publicacao> obterPublicacoesPorUsuarioId(int usuarioId);
}
