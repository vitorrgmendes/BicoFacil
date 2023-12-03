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
import android.widget.RatingBar;
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

public class FragmentAvaliacao extends Fragment implements View.OnClickListener{

    private FragmentAvaliacaoViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private AvaliacaoDao avaliacaoDao;
    private int idPulicacao;
    private String chaveVoltar;
    private RatingBar nota;
    private EditText comentario;
    private Button btnAvaliar;
    private Button btnCancelar;

    public static FragmentAvaliacao newInstance() {
        return new FragmentAvaliacao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avaliacao, container, false);

        idPulicacao = getArguments().getInt("idPublicacao");
        chaveVoltar = getArguments().getString("chave");

        nota = view.findViewById(R.id.nota);
        comentario = view.findViewById(R.id.editComentario);
        btnAvaliar = view.findViewById(R.id.button_Avaliar);
        btnCancelar = view.findViewById(R.id.button_Cacelar);

        btnAvaliar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);



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
                (FragmentAvaliacaoViewModel.class);

        mViewModel.getFim().observe(getViewLifecycleOwner(), fim -> {
            if (fim != null) {
                if(fim){
                    Toast.makeText(getActivity(), "Avaliação inserida com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPublicacao",idPulicacao);
                    bundle.putString("chave", chaveVoltar);
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
    public void onClick(View v) {

        if(v==btnCancelar){
            Bundle bundle = new Bundle();
            bundle.putInt("idPublicacao",idPulicacao);
            bundle.putString("chave", chaveVoltar);
            OfertaExtendida ofertaExtendida = new OfertaExtendida();
            ofertaExtendida.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ofertaExtendida);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnAvaliar){
            mViewModel.cadastrarAvaliacao(idPulicacao,usuarioViewModel.getId().getValue(),Math.
                    round(nota.getRating()),comentario.getText().toString(),usuarioViewModel.
                    getNome().getValue());
            }
        }
}