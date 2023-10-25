package com.example.bicofacil.BD.usuario;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "email", unique = true)})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nome;
    public String telefone;
    public String email;
    public String senhaHash;

    public void setSenha(String senhaPlana) {
        this.senhaHash = Criptografia.hashSenha(senhaPlana);
    }

    public boolean verificarSenha(String senhaPlana) {
        return Criptografia.verificarSenha(senhaPlana, this.senhaHash);
    }

}
