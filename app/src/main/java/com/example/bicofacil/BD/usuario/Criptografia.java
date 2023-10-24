package com.example.bicofacil.BD.usuario;

import org.mindrot.jbcrypt.BCrypt;

public class Criptografia {
    public static String hashSenha(String senhaPlana) {
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt());
    }

    public static boolean verificarSenha(String senhaPlana, String senhaHash) {
        return BCrypt.checkpw(senhaPlana, senhaHash);
    }
}
