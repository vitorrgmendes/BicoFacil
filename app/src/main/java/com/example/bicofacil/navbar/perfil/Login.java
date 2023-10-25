package com.example.bicofacil.navbar.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bicofacil.R;

public class Login extends Fragment implements View.OnClickListener{
    private TextView txtCadastrar;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
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
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onClick(View v) {

        if(v==txtCadastrar){
            Cadastro cadastroFragment = new Cadastro();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, cadastroFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
