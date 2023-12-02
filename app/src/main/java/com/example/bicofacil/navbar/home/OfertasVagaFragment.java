package com.example.bicofacil.navbar.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.favoritos.FavoritosDao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.ClassesViewModelFactory;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.UsuarioViewModel;
import com.example.bicofacil.navBar;


public class OfertasVagaFragment extends Fragment implements View.OnClickListener{

    private OfertasVagasViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private TextView txtTitulo;
    private RecyclerView recyclerView;
    private int id;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private String chaveLista;


    public OfertasVagaFragment() {
    }

    @SuppressWarnings("unused")
    public static OfertasVagaFragment newInstance(int columnCount) {
        OfertasVagaFragment fragment = new OfertasVagaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            chaveLista = getArguments().getString("chave");
            usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();
            id = usuarioViewModel.getId().getValue();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        publicacaoDao = db.publicacaoDao();
        favoritosDao = db.favoritosDao();

        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel, publicacaoDao, favoritosDao)).get(OfertasVagasViewModel.class);

        mViewModel.carregarFavoritosEAtualizarPublicacoes(chaveLista, id);

        mViewModel.getListaVagas().observe(getViewLifecycleOwner(), vagas -> {
            Log.i("debug","listaVagas:"+ vagas.toString());
                    if (recyclerView.getAdapter() == null) {
                        recyclerView.setAdapter(new MyOfertasVagaRecyclerViewAdapter(vagas, mViewModel));
                    } else {
                        ((MyOfertasVagaRecyclerViewAdapter) recyclerView.getAdapter()).updateData(vagas);
                    }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);

        recyclerView = view.findViewById(R.id.list);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        if (chaveLista == "servicos") {
            txtTitulo.setText("Ofertas de serviços");
        }

        if (chaveLista == "publicacoesPorId") {
            txtTitulo.setText("Minhas ofertas publicadas");
        }

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        return view;
    }

    @Override
    public void onClick(View v) {
    }
    @Override
    public void onDestroy() {
        mViewModel.salvarPublicacoesFavoritas(id);
        super.onDestroy();
    }
    @Override
    public void onPause() {
        mViewModel.salvarPublicacoesFavoritas(id);
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.carregarFavoritosEAtualizarPublicacoes(chaveLista, id);
    }

}