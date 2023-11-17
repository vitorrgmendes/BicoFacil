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

import com.example.bicofacil.R;

public class Home extends Fragment implements View.OnClickListener {
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