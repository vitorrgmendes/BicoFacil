package com.example.bicofacil.navbar.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bicofacil.R;

public class FragmentPublicacao extends Fragment implements View.OnClickListener{

    private FragmentPublicacaoViewModel mViewModel;
    private RadioGroup tipoDeOferta;
    private RadioButton opcaoVaga;
    private RadioButton opcaoServico;

    public static FragmentPublicacao newInstance() {
        return new FragmentPublicacao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publicacao, container, false);

        tipoDeOferta = view.findViewById(R.id.radioGroupTipoDeOfertas);
        opcaoVaga = view.findViewById(R.id.opcaoVaga);
        opcaoServico = view.findViewById(R.id.opcaoServico);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentPublicacaoViewModel.class);

    }

    @Override
    public void onClick(View v) {

    }
}