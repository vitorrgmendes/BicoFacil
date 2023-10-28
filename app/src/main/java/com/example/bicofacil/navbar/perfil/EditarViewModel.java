package com.example.bicofacil.navbar.perfil;


import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;

import java.util.concurrent.Executors;


public class EditarViewModel extends ViewModel {
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private AppDatabase db;
    private final MutableLiveData<Boolean> fimAtualizacao = new MutableLiveData<>();
    public LiveData<Boolean> getFimAtualizacao() {
        return fimAtualizacao;
    }
    public EditarViewModel(UsuarioDao usuarioDao, UsuarioViewModel usuarioViewModel) {
        this.usuarioDao = usuarioDao;
        this.usuarioViewModel = usuarioViewModel;
    }

    public void atualizarBancodeDados(int id, EditText nome, EditText telefone, EditText email){
        Executors.newSingleThreadExecutor().execute(() -> {

        Usuario usuarioAtualizado = usuarioDao.lerUsuarioPorId(id);

        if(usuarioAtualizado!=null){

            usuarioAtualizado.telefone=telefone.getText().toString();
            usuarioAtualizado.email=email.getText().toString();
            usuarioAtualizado.nome=nome.getText().toString();

            usuarioDao.buscaUsuarioPorEmail(usuarioAtualizado.email.toString());
            Usuario usuarioAtualizado2 = usuarioDao.lerUsuarioPorId(id);

            boolean existe = usuarioDao.emailExiste(email.getText().toString()) > 0;

            if(!existe || usuarioAtualizado2.email.toString().equals(email.getText().toString())){
            usuarioDao.editarUsuario(usuarioAtualizado);


            if(usuarioAtualizado.email.toString().equals(usuarioAtualizado2.email.toString()) && usuarioAtualizado.nome.
                    equals(usuarioAtualizado2.nome.toString()) && usuarioAtualizado.telefone.equals
                    (usuarioAtualizado2.telefone.toString())){

                usuarioViewModel.setNome(nome.getText().toString());
                usuarioViewModel.setTelefone(telefone.getText().toString());
                usuarioViewModel.setEmail(email.getText().toString());

                fimAtualizacao.postValue(true);}
        }else{
                fimAtualizacao.postValue(false);
            }
        }

        });}

}