package com.example.bicofacil.navbar.perfil;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> nome = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> telefone = new MutableLiveData<>();
    private final MutableLiveData<String> senha = new MutableLiveData<>();
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
}
