package com.example.bicofacil.navbar.perfil;

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
import android.widget.Toast;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.navBar;

public class Editar extends Fragment implements View.OnClickListener {

    private EditarViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtTelefone;
    private Button btnSalvar;
    private Button btnSair;
    private int id;
    public static Editar newInstance() {
        return new Editar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar, container, false);

        edtEmail = view.findViewById(R.id.edit_email);
        edtNome = view.findViewById(R.id.edit_nome);
        edtTelefone = view.findViewById(R.id.edit_telefone);
        btnSalvar = view.findViewById(R.id.button_salvar);
        btnSair = view.findViewById(R.id.button_sair);

        btnSalvar.setOnClickListener(this);
        btnSair.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();
        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel)).get(EditarViewModel.class);

        edtNome.setText(usuarioViewModel.getNome().getValue());
        edtEmail.setText(usuarioViewModel.getEmail().getValue());
        edtTelefone.setText(usuarioViewModel.getTelefone().getValue());
        id=usuarioViewModel.getId().getValue();


        mViewModel.getFimAtualizacao().observe(getViewLifecycleOwner(), fim -> {
            if (fim != null && fim) {
                mViewModel.atualizarCache(id,getContext());
                Toast.makeText(getActivity(), "Dados atualizados com sucesso!",
                        Toast.LENGTH_SHORT).show();
                Perfil perfilFragment = new Perfil();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, perfilFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else if (fim != null) {
                Toast.makeText(getActivity(), "E-mail já cadastrado no sistema!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v==btnSair){
            Perfil perfilFragment = new Perfil();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, perfilFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnSalvar){
            if(mViewModel.validarNome(edtNome.getText().toString()) && mViewModel.validarEmail
                    (edtEmail.getText().toString()) && mViewModel.validarTelefone(edtTelefone.
                    getText().toString())){
            mViewModel.atualizarBancodeDados(id, edtNome, edtTelefone, edtEmail);}

            if(!mViewModel.validarNome(edtNome.getText().toString())){
                Toast.makeText(getActivity(), "Nome deve conter no mínimo 3 caracteres!",
                        Toast.LENGTH_SHORT).show();
            }
            if(!mViewModel.validarEmail(edtEmail.getText().toString())){
                Toast.makeText(getActivity(), "E-mail inválido!",
                        Toast.LENGTH_SHORT).show();
            }
            if(!mViewModel.validarTelefone(edtTelefone.getText().toString())){
                Toast.makeText(getActivity(), "Telefone inválido!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}