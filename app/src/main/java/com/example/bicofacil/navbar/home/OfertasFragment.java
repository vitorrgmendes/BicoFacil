package com.example.bicofacil.navbar.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.bicofacil.navbar.perfil.Login;


public class OfertasFragment extends Fragment implements View.OnClickListener{

    private OfertasViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private TextView txtTitulo;
    private AvaliacaoDao avaliacaoDao;
    private RecyclerView recyclerView;
    private RelativeLayout semPublicacoes;
    private int id;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private String chaveLista;


    public OfertasFragment() {
    }

    @SuppressWarnings("unused")
    public static OfertasFragment newInstance(int columnCount) {
        OfertasFragment fragment = new OfertasFragment();
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
        }
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
        id = usuarioViewModel.getId().getValue();

        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel, publicacaoDao, favoritosDao, avaliacaoDao)).get(OfertasViewModel.class);

        mViewModel.carregarFavoritosEAtualizarPublicacoes(chaveLista, id);

        mViewModel.carregarValorNota();

        mViewModel.getListaMediaAvaliacoes().observe(getViewLifecycleOwner(), notas -> {
        mViewModel.getListaVagas().observe(getViewLifecycleOwner(), vagas -> {

            if (vagas == null || vagas.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                semPublicacoes.setVisibility(View.VISIBLE);
            }

            MyOfertasRecyclerViewAdapter adapter = new MyOfertasRecyclerViewAdapter(vagas, mViewModel,notas);
            adapter.setOnItemClickListener((publicacao, position) -> {
                Bundle bundle = new Bundle();
                bundle.putInt("idPublicacao",publicacao.id);
                bundle.putString("chave",chaveLista);
                OfertaExtendidaFragment ofertaExtendidaFragment = new OfertaExtendidaFragment();
                ofertaExtendidaFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, ofertaExtendidaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(adapter);
            } else {
                ((MyOfertasRecyclerViewAdapter) recyclerView.getAdapter()).updateData(vagas);
            }
        }); });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        semPublicacoes = view.findViewById(R.id.semPublicacoes);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        if (chaveLista == "servicos") {
            txtTitulo.setText("Ofertas de serviços");
        }

        if (chaveLista == "publicacoesPorId") {
            txtTitulo.setText("Minhas ofertas publicadas");
        }

        if (chaveLista == "favoritos") {
            txtTitulo.setText("Minhas ofertas favoritas");
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