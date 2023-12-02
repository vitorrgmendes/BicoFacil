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

    @Query("UPDATE publicacao SET favorito = :favorito WHERE id = :id")
    void atualizarFavorito(int id, boolean favorito);

    @Query("UPDATE publicacao SET favorito = 0")
    void atualizarTodosFavoritosParaFalse();

    @Query("UPDATE publicacao SET favorito = 1 WHERE id IN (:ids)")
    void atualizarFavoritosParaTrue(List<Integer> ids);

    @Query("SELECT * FROM publicacao WHERE favorito = 1")
    List<Publicacao> obterPublicacoesFavoritas();

    @Query("SELECT * FROM publicacao WHERE id = :publicacaoId")
    Publicacao obterPublicacoesPorId(int publicacaoId);
}
