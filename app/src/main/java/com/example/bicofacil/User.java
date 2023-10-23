package com.example.bicofacil;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    // ... outros campos conforme necessário
}
