package com.example.bicofacil.navbar.perfil;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.Criptografia;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

public class LoginViewModel extends ViewModel {
    public LoginViewModel(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    private UsuarioDao usuarioDao;
    private Criptografia criptografia;
    private final MutableLiveData<String> nome = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> telefone = new MutableLiveData<>();
    private final MutableLiveData<String> senha = new MutableLiveData<>();
    public MutableLiveData<String> getTelefone() {
        return telefone;
    }
    private final MutableLiveData<Boolean> credenciaisCorretas = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCredenciaisCorretas() {
        return credenciaisCorretas;
    }

    public MutableLiveData<String> getSenha() {
        return senha;
    }
    public MutableLiveData<String> getNome() {
        return nome;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }
    private final MutableLiveData<Boolean> emailExistente = new MutableLiveData<>();

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
    public void verificarCredenciais(String email, String senha) {
        new Thread(() -> {
            if(usuarioDao != null) {
                boolean existe = usuarioDao.emailExiste(email) > 0;
                if(existe==true){
                    Usuario usuario = usuarioDao.buscaUsuarioPorEmail(email);
                    boolean senhaCorreta = criptografia.verificarSenha(senha,usuario.senhaHash);
                    credenciaisCorretas.postValue(senhaCorreta);
                }else{
                    credenciaisCorretas.postValue(false);
                }
            }
        }).start();}
}
