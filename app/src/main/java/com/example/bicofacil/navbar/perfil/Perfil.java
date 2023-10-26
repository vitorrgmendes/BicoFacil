package com.example.bicofacil.navbar.perfil;

import android.util.Log;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;

public class Perfil extends Fragment implements View.OnClickListener{

    private PerfilViewModel mViewModel;
    private TextView txtNome;
    private TextView txtEmail;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private Button btnSair;

    public static Perfil newInstance() {
        return new Perfil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tela_perfil, container, false);

        txtNome = view.findViewById(R.id.txtNome);
        txtEmail = view.findViewById(R.id.txtEmail);

        btnSair = view.findViewById(R.id.button_deslogar);

        btnSair.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel)).get(PerfilViewModel.class);
    }

    @Override
    public void onClick(View v) {
        mViewModel.atualizandoCampos(txtNome,txtEmail);
    }}