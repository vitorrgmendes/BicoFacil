package com.example.bicofacil.BD.usuario;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario")
    List<Usuario> todosUsuarios();

    @Insert
    long inserirUsuario(Usuario Usuario);

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    Usuario lerUsuarioPorId(int usuarioId);

    @Update
    void editarUsuario(Usuario usuario);

    @Query("DELETE FROM Usuario WHERE id = :usuarioId")
    void deletarUsuarioPorId(int usuarioId);

    @Query("SELECT COUNT(*) FROM Usuario WHERE email = :email")
    int emailExiste(String email);

    @Query("SELECT * FROM Usuario WHERE email = :email LIMIT 1")
    Usuario buscaUsuarioPorEmail(String email);

}
