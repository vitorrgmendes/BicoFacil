package com.example.bicofacil.navbar.home;

import static android.app.Activity.RESULT_OK;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.ClassesViewModelFactory;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.UsuarioViewModel;
import com.example.bicofacil.navBar;
import com.example.bicofacil.navbar.perfil.LoginViewModel;

public class FragmentPublicacao extends Fragment implements View.OnClickListener{

    private FragmentPublicacaoViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private RadioGroup tipoDeOferta;
    private RadioButton opcaoVaga;
    private RadioButton opcaoServico;
    private ImageButton imgBtnImagem;
    private int tipoOferta;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private Bitmap imageBitmap;
    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtValor;
    private EditText edtHorario;
    private EditText edtTelefone;
    private Button btnPublicar;
    private byte[] imagemPublicacao;

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
        imgBtnImagem = view.findViewById(R.id.imageButtonImagem);
        edtTitulo = view.findViewById(R.id.editTitulo);
        edtDescricao = view.findViewById(R.id.editDescricao);
        edtValor = view.findViewById(R.id.editValor);
        edtHorario = view.findViewById(R.id.editHorario);
        edtTelefone = view.findViewById(R.id.editTelefone);
        btnPublicar = view.findViewById(R.id.button_Publicar);

        imgBtnImagem.setOnClickListener(this);
        btnPublicar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        publicacaoDao = db.publicacaoDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();

        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel, publicacaoDao)).get(FragmentPublicacaoViewModel.class);

        edtTelefone.setText(usuarioViewModel.getTelefone().getValue());

        mViewModel.getFim().observe(getViewLifecycleOwner(), fim -> {
            if (fim != null) {
                if(fim){
                    Toast.makeText(getActivity(), "Publicação inserida com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    Home HomeFragment = new Home();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, HomeFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == imgBtnImagem){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                        .CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);}
            }
        }

        if(v == btnPublicar){

            int idRadioButton = tipoDeOferta.getCheckedRadioButtonId();

            if (idRadioButton == R.id.opcaoVaga) {
                tipoOferta=1;
            } else if (idRadioButton == R.id.opcaoServico) {
                tipoOferta=2;
            } else {
                Toast.makeText(getActivity(), "É necessário definir o tipo de oferta!",
                        Toast.LENGTH_SHORT).show();
            }
            if(imageBitmap!=null){
            imagemPublicacao = mViewModel.converterImagemParaBytes(imageBitmap);}

            if((tipoOferta==1 || tipoOferta==2) && mViewModel.validarTitulo(edtTitulo.getText()
                    .toString()) && mViewModel.validarTelefone(edtTelefone.getText().toString()) &&
                    mViewModel.validarDescricao(edtDescricao.getText().toString())){

            mViewModel.criarNovaPublicacao(tipoOferta,imagemPublicacao,edtTitulo.getText().toString(),
                    edtDescricao.getText().toString(),edtValor.getText().toString(),
                    edtHorario.getText().toString(), edtTelefone.getText().toString());
            }

            if(!mViewModel.validarTitulo(edtTitulo.getText().toString())){
                Toast.makeText(getActivity(), "O título deve ter no mínimo 3 caracteres!",
                        Toast.LENGTH_SHORT).show();
            }

            if(!mViewModel.validarTelefone(edtTelefone.getText().toString())){
                Toast.makeText(getActivity(), "O telefone deve ter no mínimo 8 dígitos!",
                        Toast.LENGTH_SHORT).show();
            }

            if(!mViewModel.validarDescricao(edtDescricao.getText().toString())){
                Toast.makeText(getActivity(), "A descrição deve ter no mínimo 15 caracteres!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgBtnImagem.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);}
            } else {
                Toast.makeText(getActivity(), "Sem permissão para acessar a câmera!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}