package com.example.bicofacil.navbar.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends Fragment implements View.OnClickListener{
    private TextView txtCadastrar;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private String email;
    private String senha;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private LoginViewModel mViewModel;
    public static Login newInstance() {return new Login();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_login, container, false);

        edtEmail = view.findViewById(R.id.edit_email);
        edtSenha = view.findViewById(R.id.edit_password);
        txtCadastrar = view.findViewById(R.id.txt_cadastro);
        btnEntrar = view.findViewById(R.id.button_entrar);

        txtCadastrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        mViewModel = new ViewModelProvider(this, new UsuarioDaoViewModelFactory(usuarioDao))
                .get(LoginViewModel.class);
    }
    @Override
    public void onClick(View v) {

        email = edtEmail.getText().toString();
        senha = edtSenha.getText().toString();

        if(v==btnEntrar){
            mViewModel.verificarCredenciais(email,senha);

            if(mViewModel.getCredenciaisCorretas().getValue() != null && mViewModel.
                    getCredenciaisCorretas().getValue()){
                Perfil perfilFragment = new Perfil();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, perfilFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }else{
                Toast.makeText(getActivity(), "E-mail e/ou senha não conferem!",
                        Toast.LENGTH_SHORT).show();
            }

        }

        if(v==txtCadastrar){
            Cadastro cadastroFragment = new Cadastro();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, cadastroFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
