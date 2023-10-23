package com.example.bicofacil;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Insert
    void insertUser(User user);
    // ... outros métodos conforme necessário
}
