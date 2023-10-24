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
    private final MutableLiveData<String> nome = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> telefone = new MutableLiveData<>();
    private final MutableLiveData<String> senha = new MutableLiveData<>();
    private UsuarioDao usuarioDao;
    private final MutableLiveData<Boolean> emailExistente = new MutableLiveData<>();

    public MutableLiveData<String> getNome() {
        return nome;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getTelefone() {
        return telefone;
    }

    public MutableLiveData<String> getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome.setValue(nome);
    }
    public void setEmail(String email) {
        this.email.setValue(email);
    }
    public void setTelefone(String telefone) {
        this.telefone.setValue(telefone);
    }
    public void setSenha(String senha) {
        this.senha.setValue(senha);
    }
    public boolean verificarSenhasIguais(String senha, String confirmSenha) {
        return senha.equals(confirmSenha);
    }

    public LiveData<Boolean> getEmailExistente() {
        return emailExistente;
    }
    public void verificarEmailExistente(String email) {
        new Thread(() -> {
            if(usuarioDao != null) {
                boolean existe = usuarioDao.emailExiste(email) > 0;
                emailExistente.postValue(existe);
            }
        }).start();
    }
}

