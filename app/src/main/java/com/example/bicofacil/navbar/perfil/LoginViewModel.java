package com.example.bicofacil.navbar.perfil;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.Criptografia;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

public class LoginViewModel extends ViewModel {
    public LoginViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private Criptografia criptografia;
    private final MutableLiveData<Boolean> credenciaisCorretas = new MutableLiveData<>();
    public MutableLiveData<Boolean> getCredenciaisCorretas() {
        return credenciaisCorretas;
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

    public void adicionarDados(String email) {
        new Thread(() -> {
                    Usuario usuario = usuarioDao.buscaUsuarioPorEmail(email);

                    usuarioViewModel.setId(usuario.id);
                    usuarioViewModel.setNome(usuario.nome);
                    usuarioViewModel.setEmail(usuario.email);
                    usuarioViewModel.setTelefone(usuario.telefone);

        }).start();}

}
