package com.example.bicofacil.navbar.perfil;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bicofacil.R;
import com.example.bicofacil.TelaCadastro;
import com.example.bicofacil.TelaLogin;

public class Perfil extends Fragment implements View.OnClickListener{

    private PerfilViewModel mViewModel;
    private TextView txtCadastrar;

    public static Perfil newInstance() {
        return new Perfil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tela_login, container, false);

        txtCadastrar = view.findViewById(R.id.txt_cadastro);
        txtCadastrar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        if(v==txtCadastrar){
            Cadastro cadastroFragment = new Cadastro();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, cadastroFragment);
            transaction.addToBackStack(null);
            transaction.commit();}
    }

    }