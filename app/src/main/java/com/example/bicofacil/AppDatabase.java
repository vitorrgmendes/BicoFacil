package com.example.bicofacil;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;


@Database(entities = {User.class, Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UsuarioDao usuarioDao();

}
