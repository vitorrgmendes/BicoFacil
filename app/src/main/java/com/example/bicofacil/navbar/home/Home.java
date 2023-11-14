package com.example.bicofacil.navbar.home;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bicofacil.R;
import com.example.bicofacil.navbar.perfil.Perfil;

public class Home extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    Button addPublicacao;

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addPublicacao = view.findViewById(R.id.buttonAddPublicacao);

        addPublicacao.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        if(v == addPublicacao){
            FragmentPublicacao publicacaoFragment = new FragmentPublicacao();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, publicacaoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}