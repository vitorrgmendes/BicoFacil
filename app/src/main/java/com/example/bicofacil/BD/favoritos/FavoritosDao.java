package com.example.bicofacil.BD.favoritos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bicofacil.BD.publicacao.Publicacao;

import java.util.List;

@Dao
public interface FavoritosDao {
    @Query("SELECT * FROM favoritos")
    List<Favoritos> obterTodosFavoritos();

    @Insert
    void inserirFavorito(Favoritos favorito);

    @Query("SELECT * FROM favoritos WHERE usuarioId = :usuarioId")
    List<Favoritos> obterFavoritosPorUsuarioId(int usuarioId);

    @Query("SELECT * FROM favoritos WHERE usuarioId = :usuarioId AND publicacaoId = :publicacaoId")
    Favoritos obterFavoritoPorUsuarioEPublicacao(int usuarioId, int publicacaoId);

    @Delete
    void deletarFavorito(Favoritos favorito);


}
