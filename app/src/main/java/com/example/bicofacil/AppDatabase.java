package com.example.bicofacil;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bicofacil.User;
import com.example.bicofacil.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
