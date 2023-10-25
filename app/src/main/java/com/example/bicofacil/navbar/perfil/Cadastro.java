package com.example.bicofacil.navbar.perfil;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;


public class Cadastro extends Fragment implements View.OnClickListener {
    private CadastroViewModel mViewModel;
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtTelefone;
    private EditText edtSenha;
    private EditText edtConfirmSenha;
    private Button btnCadastrar;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String confirmSenha;


    public static Cadastro newInstance() {
        return new Cadastro();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_cadastro, container, false);

        edtNome = view.findViewById(R.id.edit_nome);
        edtEmail = view.findViewById(R.id.edit_email);
        edtTelefone = view.findViewById(R.id.edit_telefone);
        edtSenha = view.findViewById(R.id.edit_password);
        edtConfirmSenha = view.findViewById(R.id.confirm_password);
        btnCadastrar = view.findViewById(R.id.button_cadastrar);

        btnCadastrar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        mViewModel = new ViewModelProvider(this, new UsuarioDaoViewModelFactory(usuarioDao))
                .get(CadastroViewModel.class);
    }

    @Override
    public void onClick(View v) {

        nome = edtNome.getText().toString();
        telefone = edtTelefone.getText().toString();
        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();
        confirmSenha = edtConfirmSenha.getText().toString();

        if(v==btnCadastrar){
            if(mViewModel.verificarSenhasIguais(senha, confirmSenha)==true && mViewModel.
                    validarSenha(senha)==true && mViewModel.validarEmail(email)==true){

                mViewModel.verificarEmailExistente(email);

                mViewModel.getEmailExistente().observe(getViewLifecycleOwner(), existe -> {
                    if (existe != null) {

                        if (existe==false) {mViewModel.criarNovoUsuario(nome, telefone, email,
                                senha);

                            Toast.makeText(getActivity(), "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                            Login loginFragment = new Login();
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentContainerView, loginFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        if (existe==true) {
                            Toast.makeText(getActivity(), "Email já cadastrado!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
            if(mViewModel.verificarSenhasIguais(senha, confirmSenha)==false){
                Toast.makeText(getActivity(), "Senhas não conferem!",
                        Toast.LENGTH_SHORT).show();}
            if(mViewModel.validarSenha(senha)==false){
                Toast.makeText(getActivity(), "A senha deve conter no mínimo 6 caracteres!",
                        Toast.LENGTH_SHORT).show();}
            }
            if(mViewModel.validarEmail(email)==false){
                Toast.makeText(getActivity(), "E-mail inválido!",
                        Toast.LENGTH_SHORT).show();}
            }
        }


