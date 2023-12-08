package com.example.bicofacil.navbar.home;

import static android.app.Activity.RESULT_OK;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
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
import android.widget.Toast;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.avaliacao.AvaliacaoDao;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.ClassesViewModelFactory;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.UsuarioViewModel;
import com.example.bicofacil.navBar;

import java.io.IOException;

public class EditarPublicacao extends Fragment implements View.OnClickListener{

    private EditarPublicacaoViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private AvaliacaoDao avaliacaoDao;
    private FavoritosDao favoritosDao;
    private ImageButton btnLimparFoto;
    private int idPulicacao;
    private String chaveLista;
    private ImageButton btnImagem;
    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtValor;
    private EditText edtHorario;
    private EditText edtTelefone;
    private Button btnEditar;
    private Bitmap imageBitmap;
    private byte[] imagemPublicacao;
    private Button btnVoltar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int PICK_IMAGE_FROM_GALLERY = 2;

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
        btnLimparFoto = view.findViewById(R.id.imgButtonLimparImagem);

        btnEditar.setOnClickListener(this);
        btnImagem.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnLimparFoto.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        publicacaoDao = db.publicacaoDao();
        favoritosDao = db.favoritosDao();
        avaliacaoDao = db.avaliacaoDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();

        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel, publicacaoDao, favoritosDao, avaliacaoDao)).get
                (EditarPublicacaoViewModel.class);

        mViewModel.buscarDadosPublicacao(idPulicacao);

        mViewModel.getPublicacao().observe(getViewLifecycleOwner(), publicacao -> {
            imagemPublicacao = publicacao.imagemPublicacao;
            if(publicacao!=null){
                if(publicacao.imagemPublicacao!=null && publicacao.imagemPublicacao.length>0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(publicacao.imagemPublicacao,
                            0, publicacao.imagemPublicacao.length);
                    btnImagem.setImageBitmap(bitmap);
                }}
            edtTitulo.setText(publicacao.titulo);
            edtDescricao.setText(publicacao.descricao);
            edtValor.setText(publicacao.valor);
            edtHorario.setText(publicacao.horario);
            edtTelefone.setText(publicacao.contato);
        });

        mViewModel.getFim().observe(getViewLifecycleOwner(), fim -> {
            if (fim != null) {
                if(fim){
                    Toast.makeText(getActivity(), "Edição concluída com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPublicacao",idPulicacao);
                    bundle.putString("chave",chaveLista);
                    OfertaExtendida ofertaExtendida = new OfertaExtendida();
                    ofertaExtendida.setArguments(bundle);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, ofertaExtendida);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            btnImagem.setImageBitmap(imageBitmap);
        } else if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                btnImagem.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btnImagem){
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
            btnImagem.setImageBitmap(null);
            btnImagem.setImageResource(R.drawable.baseline_add_photo_alternate_24);
            imagemPublicacao = null;
        }

        if(v==btnVoltar){
            Bundle bundle = new Bundle();
            bundle.putInt("idPublicacao",idPulicacao);
            bundle.putString("chave",chaveLista);
            OfertaExtendida ofertaExtendida = new OfertaExtendida();
            ofertaExtendida.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ofertaExtendida);
            transaction.addToBackStack(null);
            transaction.commit();}

        if(v==btnEditar){
            if(imageBitmap!=null){
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, 500, true);
                imagemPublicacao = mViewModel.converterImagemParaBytes(imageBitmap);}

            if(mViewModel.validarTitulo(edtTitulo.getText().toString()) && mViewModel.validarTelefone
                    (edtTelefone.getText().toString()) && mViewModel.validarDescricao(edtDescricao
                    .getText().toString())){
                        mViewModel.atualizarPublicacao(idPulicacao,imagemPublicacao,edtTitulo.getText()
                                .toString(), edtDescricao.getText().toString(),edtValor.
                                getText().toString(),edtHorario.getText().toString(),edtTelefone.
                                getText().toString());
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





    }
