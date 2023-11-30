package com.example.bicofacil.navbar.home;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bicofacil.R;
import com.example.bicofacil.navBar;
import com.example.bicofacil.navbar.perfil.Login;
import com.example.bicofacil.UsuarioViewModel;

public class Home extends Fragment implements View.OnClickListener {
    Button addPublicacao;
    ImageButton btnVagas;
    UsuarioViewModel usuarioViewModel;

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addPublicacao = view.findViewById(R.id.buttonAddPublicacao);
        btnVagas = view.findViewById(R.id.btnVagas);

        addPublicacao.setOnClickListener(this);
        btnVagas.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();
    }

    @Override
    public void onClick(View v) {

        if(v == addPublicacao){
            if (usuarioViewModel.getLogin().getValue() == true) {
                FragmentPublicacao publicacaoFragment = new FragmentPublicacao();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, publicacaoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }else{
                Toast.makeText(getActivity(), "É necessário fazer o login!", Toast.LENGTH_SHORT)
                        .show();
                Login loginFragment = new Login();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }

        if(v == btnVagas){
            OfertasVagaFragment ofertasVagaFragment = new OfertasVagaFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ofertasVagaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}