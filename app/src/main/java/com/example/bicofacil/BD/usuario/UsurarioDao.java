package com.example.bicofacil.BD.usuario;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface UsurarioDao {
    @Query("SELECT * FROM Usuario")
    List<Usuario> todosUsuarios();

    @Insert
    long inserirUsuario(Usuario Usuario);

    @Query("SELECT * FROM Usuario WHERE id = Usuario.id")
    Usuario lerUsuarioPorId(int usuarioId);

    @Update
    void editarUsuario(Usuario usuario);

    @Delete
    void deletarUsuario(Usuario usuario);

}
