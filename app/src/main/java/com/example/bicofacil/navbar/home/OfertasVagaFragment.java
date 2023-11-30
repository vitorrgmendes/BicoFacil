package com.example.bicofacil.navbar.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bicofacil.AppDatabase;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.publicacao.PublicacaoDao;
import com.example.bicofacil.BD.usuario.Usuario;
import com.example.bicofacil.BD.usuario.UsuarioDao;
import com.example.bicofacil.ClassesViewModelFactory;
import com.example.bicofacil.Conexao;
import com.example.bicofacil.R;
import com.example.bicofacil.UsuarioViewModel;
import com.example.bicofacil.navBar;
import com.example.bicofacil.navbar.home.placeholder.PlaceholderContent;

import java.util.List;

public class OfertasVagaFragment extends Fragment implements View.OnClickListener{

    private OfertasVagasViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private List<Publicacao> listaVagas;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;


    public OfertasVagaFragment() {
    }

    // TODO: Customize parameter initialization
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
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = Conexao.getInstance(getContext());
        usuarioDao = db.usuarioDao();
        publicacaoDao = db.publicacaoDao();
        usuarioViewModel = ((navBar) requireActivity()).getUsuarioViewModel();

        mViewModel = new ViewModelProvider(this, new ClassesViewModelFactory(usuarioDao,
                usuarioViewModel, publicacaoDao)).get(OfertasVagasViewModel.class);

        mViewModel.carregarListaVagas();

        mViewModel.getListaVagas().observe(getViewLifecycleOwner(), vagas -> {
            if (vagas != null) {
                RecyclerView recyclerView = getView().findViewById(R.id.list);
                recyclerView.setAdapter(new MyOfertasVagaRecyclerViewAdapter(vagas));
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);


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
}