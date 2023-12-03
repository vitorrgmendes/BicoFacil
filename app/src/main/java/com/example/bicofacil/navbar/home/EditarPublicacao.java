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
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.bicofacil.R;

public class EditarPublicacao extends Fragment implements View.OnClickListener{

    private EditarPublicacaoViewModel mViewModel;
    private int idPulicacao;
    private String chaveLista;
    private ImageButton btnImagem;
    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtValor;
    private EditText edtHorario;
    private EditText edtTelefone;
    private Button btnEditar;
    private Button btnVoltar;

    public static EditarPublicacao newInstance() {
        return new EditarPublicacao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editar_publicacao, container, false);

        idPulicacao = getArguments().getInt("idPublicacao");
        chaveLista = getArguments().getString("chave");

        btnImagem = view.findViewById(R.id.imageButtonImagem);
        edtTitulo = view.findViewById(R.id.editTitulo);
        edtDescricao = view.findViewById(R.id.editDescricao);
        edtValor = view.findViewById(R.id.editValor);
        edtHorario = view.findViewById(R.id.editHorario);
        edtTelefone = view.findViewById(R.id.editTelefone);
        btnEditar = view.findViewById(R.id.button_editar);
        btnVoltar = view.findViewById(R.id.button_voltar);

        btnEditar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarPublicacaoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        if(v==btnVoltar){
            Bundle bundle = new Bundle();
            bundle.putString("chave", chaveLista);

            OfertasFragment ofertasVagaFragment = new OfertasFragment();
            ofertasVagaFragment.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ofertasVagaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}