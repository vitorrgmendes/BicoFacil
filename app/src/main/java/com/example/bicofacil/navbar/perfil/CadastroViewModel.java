package com.example.bicofacil.navbar.perfil;

import static kotlinx.coroutines.BuildersKt.withContext;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

public class CadastroViewModel extends ViewModel {
    public CadastroViewModel(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    private UsuarioDao usuarioDao;
    public LiveData<Boolean> getEmailExistente() {
        return emailExistente;
    }
    private final MutableLiveData<Boolean> emailExistente = new MutableLiveData<>();
    private static final String SENHA_REGEX = "^.{6,}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public boolean verificarSenhasIguais(String senha, String confirmSenha) {
        return senha.equals(confirmSenha);
    }

    public boolean validarSenha(String senha) {
        return senha.matches(SENHA_REGEX);
    }

    public boolean validarEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
    
    public void verificarEmailExistente(String email) {
        new Thread(() -> {
            if(usuarioDao != null) {
                boolean existe = usuarioDao.emailExiste(email) > 0;
                emailExistente.postValue(existe);
            }
        }).start();
    }

    public void criarNovoUsuario(String nome, String telefone, String email, String senha) {
        new Thread(() -> {
            try {
                Usuario novoUsuario = new Usuario();
                novoUsuario.nome = nome;
                novoUsuario.telefone = telefone;
                novoUsuario.email = email;
                novoUsuario.setSenha(senha);
                usuarioDao.inserirUsuario(novoUsuario);
            } catch (android.database.sqlite.SQLiteConstraintException e) {

            }
        }).start();
    }
}

