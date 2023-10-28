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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.navBar;

public class Perfil extends Fragment implements View.OnClickListener{

    private PerfilViewModel mViewModel;
    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtTelefone;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private Button btnSair;
    private Button btnEditar;
    private Button btnAltSenha;

    public static Perfil newInstance() {
        return new Perfil();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_perfil, container, false);

        txtNome = view.findViewById(R.id.txtNome);
        txtTelefone = view.findViewById(R.id.txtTelefone);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnSair = view.findViewById(R.id.button_deslogar);
        btnEditar = view.findViewById(R.id.button_editar);
        btnAltSenha = view.findViewById(R.id.button_alterar_senha);

        btnSair.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnAltSenha.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();
        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel)).get(PerfilViewModel.class);
        mViewModel.atualizandoCampos(txtNome,txtEmail,txtTelefone);
    }

    @Override
    public void onClick(View v) {
        if (v==btnSair){
            usuarioViewModel.setLogin(false);
            Login loginFragment = new Login();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, loginFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnEditar){
            Editar editarFragment = new Editar();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, editarFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnAltSenha){
            AlterarSenha alterarSenhaFragment = new AlterarSenha();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, alterarSenhaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }}