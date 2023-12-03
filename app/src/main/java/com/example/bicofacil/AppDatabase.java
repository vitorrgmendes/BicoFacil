package com.example.bicofacil;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bicofacil.BD.avaliacao.Avaliacao;
import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.Favoritos;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;


@Database(entities = {Publicacao.class, Usuario.class, Favoritos.class, Avaliacao.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PublicacaoDao publicacaoDao();
    public abstract UsuarioDao usuarioDao();
    public abstract FavoritosDao favoritosDao();
    public abstract AvaliacaoDao avaliacaoDao();
}
