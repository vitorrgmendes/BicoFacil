package com.example.bicofacil.navbar.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bicofacil.BD.usuario.Criptografia;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

public class LoginViewModel extends ViewModel {
    public LoginViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private Criptografia criptografia;
    private Login login;
    private final MutableLiveData<Boolean> credenciaisCorretas = new MutableLiveData<>();
    public LiveData<Boolean> getCredenciaisCorretas() {
        return credenciaisCorretas;
    }
    public void verificarCredenciais(String email, String senha, Context context) {
        new Thread(() -> {
            if(usuarioDao != null) {
                boolean existe = usuarioDao.emailExiste(email) > 0;

                if(existe){

                    Usuario usuario = usuarioDao.buscaUsuarioPorEmail(email);
                    boolean senhaCorreta = criptografia.verificarSenha(senha,usuario.senhaHash);
                    credenciaisCorretas.postValue(senhaCorreta);

                    if(senhaCorreta){
                        SharedPreferences sharedPreferences = context.getSharedPreferences("Usuario"
                                , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", usuario.id);
                        editor.putString("nome", usuario.nome);
                        editor.putString("email", usuario.email);
                        editor.putString("telefone", usuario.telefone);
                        editor.putString("senha", usuario.senhaHash);
                        editor.putBoolean("login", true);
                        editor.apply();

                        usuarioViewModel.setId(usuario.id);
                        usuarioViewModel.setNome(usuario.nome);
                        usuarioViewModel.setEmail(usuario.email);
                        usuarioViewModel.setTelefone(usuario.telefone);
                        usuarioViewModel.setSenha(usuario.senhaHash);
                        usuarioViewModel.setLogin(true);
                    }
                }else{
                    credenciaisCorretas.postValue(false);
                }
            }
        }).start();}



}


