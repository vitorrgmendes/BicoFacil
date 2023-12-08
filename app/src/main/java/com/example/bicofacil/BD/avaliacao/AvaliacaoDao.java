package com.example.bicofacil.BD.avaliacao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.bicofacil.BD.publicacao.Publicacao;

import java.util.List;

@Dao
public interface AvaliacaoDao {
    @Insert
    void inserirAvaliacao(Avaliacao avaliacao);

    @Update
    void atualizarAvaliacao(Avaliacao avaliacao);

    @Delete
    void deletarAvaliacao(Avaliacao avaliacao);
    @Query("DELETE FROM avaliacao WHERE id = :avaliacaoId")
    void deletarAvaliacaoPorId(int avaliacaoId);

    @Query("SELECT * FROM avaliacao")
    List<Avaliacao> obterTodasAvaliacoes();

    @Query("SELECT * FROM avaliacao WHERE publicacaoId = :publicacaoId")
    List<Avaliacao> obterAvaliacoesPorPublicacaoId(int publicacaoId);

    @Query("SELECT publicacaoId, AVG(nota) as mediaNota FROM Avaliacao GROUP BY publicacaoId")
    List<AvaliacaoMedia> listaMediaNotasPorPublicacao();

    @Query("DELETE FROM avaliacao WHERE publicacaoId = :publicacaoId")
    void deletarAvaliacoesPorPublicacaoId(int publicacaoId);

    @Query("SELECT DISTINCT publicacao.* FROM publicacao JOIN avaliacao ON publicacao.id = avaliacao" +
            ".publicacaoId WHERE avaliacao.usuarioId = :idUsuario")
    List<Publicacao> obterPublicacoesAvaliadasPorUsuario(int idUsuario);

    @Query("SELECT * FROM avaliacao WHERE id = :avaliacaoId")
    Avaliacao obterAvaliacaoPorId(int avaliacaoId);
}
