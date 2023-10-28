package com.example.bicofacil.navbar.perfil;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsuarioViewModel extends ViewModel {

    private final MutableLiveData<Integer> id = new MutableLiveData<>();
    private final MutableLiveData<String> nome = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> telefone = new MutableLiveData<>();
    private final MutableLiveData<Boolean> login = new MutableLiveData<>();
    private final MutableLiveData<String> senha = new MutableLiveData<>();
    public MutableLiveData<String> getNome() {
        return nome;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }
    public MutableLiveData<Integer> getId() {
        return id;
    }
    public MutableLiveData<String> getTelefone() {
        return telefone;
    }
    public MutableLiveData<Boolean> getLogin() {
        return login;
    }
    public MutableLiveData<String> getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha.postValue(senha);
    }
    public void setLogin(Boolean login) {
        this.login.postValue(login);
    }
    public void setNome(String nome) {
        this.nome.postValue(nome);
    }
    public void setEmail(String email) {
        this.email.postValue(email);
    }
    public void setTelefone(String telefone) { this.telefone.postValue(telefone); }
    public void setId(int id) {
        this.id.postValue(id);}
}
