package com.example.bicofacil;

import android.content.Context;

import androidx.room.Room;

public class Conexao {

    private static AppDatabase appDatabase;

    private Conexao() {
        // Construtor privado para evitar instanciação
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "BICOFACILBD"
            ).build();

        }
        return appDatabase;
    }
}