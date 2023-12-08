package com.example.bicofacil.navbar.home;

import static android.app.Activity.RESULT_OK;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.IOException;

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
    private ImageButton btnLimparFoto;
    private int tipoOferta;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int PICK_IMAGE_FROM_GALLERY = 2;
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
        btnLimparFoto = view.findViewById(R.id.imgButtonLimparImagem);

        imgBtnImagem.setOnClickListener(this);
        btnPublicar.setOnClickListener(this);
        btnLimparFoto.setOnClickListener(this);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Escolher uma opção");
            String[] options = {"Tirar uma foto", "Selecionar da galeria"};

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                            break;
                        case 1:
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALLERY);
                            break;
                    }
                }
            });

            builder.show();
        }

        if(v == btnLimparFoto){
            imgBtnImagem.setImageBitmap(null);
            imgBtnImagem.setImageResource(R.drawable.baseline_add_photo_alternate_24);
            imageBitmap = null;
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
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, 500, true);
                imagemPublicacao = mViewModel.converterImagemParaBytes(imageBitmap);                }

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
        } else if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                imgBtnImagem.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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