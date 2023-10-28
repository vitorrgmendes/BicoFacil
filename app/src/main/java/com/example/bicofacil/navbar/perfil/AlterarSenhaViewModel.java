package com.example.bicofacil.navbar.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

import java.util.concurrent.Executors;

public class AlterarSenhaViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    public AlterarSenhaViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }
    private static final String SENHA_REGEX = "^.{6,}$";
    public LiveData<Boolean> getFimOperacao() {
        return fimOperacao;
    }
    private final MutableLiveData<Boolean> fimOperacao = new MutableLiveData<>();
    public LiveData<Boolean> getReturnSenha() {
        return returnSenha;
    }
    private final MutableLiveData<Boolean> returnSenha = new MutableLiveData<>();
    public boolean validarSenha(String senha) {
        return senha.matches(SENHA_REGEX);
    }

    public void alterarSenha(int id,String senha, String novaSenha){
        Executors.newSingleThreadExecutor().execute(() -> {

            Usuario usuario = usuarioDao.lerUsuarioPorId(id);

            Boolean senhaCorreta = usuario.verificarSenha(senha);

            if(senhaCorreta){
                returnSenha.postValue(true);
                if (validarSenha(novaSenha)) {
                    usuario.setSenha(novaSenha);
                    usuarioDao.editarUsuario(usuario);
                    fimOperacao.postValue(true);
                }else {
                    fimOperacao.postValue(false);
                }
            }else{
                returnSenha.postValue(false);
            }
        });

    }
}