package com.example.bicofacil.navbar.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.UsuarioViewModel;

public class PerfilViewModel extends ViewModel {

    public PerfilViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }

    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private MutableLiveData<Boolean> confirmacao = new MutableLiveData<>();
    public LiveData<Boolean> getConfirmacao() {
        return confirmacao;
    }

    public void atualizandoCampos (TextView nome, TextView email, TextView telefone){
        telefone.setText(usuarioViewModel.getTelefone().getValue());
        nome.setText(usuarioViewModel.getNome().getValue());
        email.setText(usuarioViewModel.getEmail().getValue());
    }

    public void excluirConta(int id){
        new Thread(() -> {
            usuarioDao.deletarUsuarioPorId(id);
            confirmacao.postValue(true);
        }).start();
    }

    public void limparCache(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        usuarioViewModel.limparValores();
    }
}