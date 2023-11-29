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
import com.example.bicofacil.ClassesViewModelFactory;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.UsuarioViewModel;
import com.example.bicofacil.navBar;

public class AlterarSenha extends Fragment implements View.OnClickListener {

    private AlterarSenhaViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private EditText edtSenha;
    private EditText edtNovaSenha;
    private Button btnSalvar;
    private Button btnSair;
    private String senha;
    private String novaSenha;
    private int id;

    public static AlterarSenha newInstance() {
        return new AlterarSenha();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alterar_senha, container, false);

        edtSenha = view.findViewById(R.id.edit_senha);
        edtNovaSenha = view.findViewById(R.id.edit_novaSenha);
        btnSalvar = view.findViewById(R.id.button_salvar);
        btnSair = view.findViewById(R.id.button_sair);

        btnSair.setOnClickListener(this);
        btnSalvar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();
        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel)).get(AlterarSenhaViewModel.class);

        id=usuarioViewModel.getId().getValue();

        mViewModel.getFimOperacao().observe(getViewLifecycleOwner(), fim -> {
            if(fim && fim!=null){
                mViewModel.atualizarCache(id,getContext());
                Toast.makeText(getActivity(), "Senha atualizada com sucesso!",
                        Toast.LENGTH_SHORT).show();
                Perfil perfilFragment = new Perfil();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, perfilFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mViewModel.getReturnSenha().observe(getViewLifecycleOwner(), returnSenha -> {
            if(!returnSenha){
                Toast.makeText(getActivity(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getFimOperacao().observe(getViewLifecycleOwner(), fim -> {
            if(!fim){
                Toast.makeText(getActivity(), "Sua senha deve conter no mínimo 6 caracteres!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        senha = edtSenha.getText().toString();
        novaSenha = edtNovaSenha.getText().toString();

        if(v==btnSair){
            Perfil perfilFragment = new Perfil();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, perfilFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnSalvar){
            mViewModel.alterarSenha(id,senha,novaSenha);
        }

    }
}