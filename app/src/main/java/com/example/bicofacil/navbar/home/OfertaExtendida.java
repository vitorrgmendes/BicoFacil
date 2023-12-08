package com.example.bicofacil.navbar.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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


public class OfertaExtendida extends Fragment implements View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int idPulicacao;
    private OfertaExtendidaViewModel mViewModel;
    private AppDatabase db;
    private UsuarioDao usuarioDao;
    private UsuarioViewModel usuarioViewModel;
    private PublicacaoDao publicacaoDao;
    private FavoritosDao favoritosDao;
    private AvaliacaoDao avaliacaoDao;
    private TextView txtTituloOferta;
    private ImageView imgOferta;
    private TextView txtDescricao;
    private TextView txtValor;
    private TextView txtHorario;
    private TextView txtContato;
    private TextView semAvaliacao;
    private Button btnAvaliar;
    private Button btnVoltar;
    private RecyclerView recyclerView;
    private String chaveLista;
    private Button btnEditar;
    private Button btnExcluir;
    public OfertaExtendida() {
    }

    @SuppressWarnings("unused")
    public static OfertaExtendida newInstance(int columnCount) {
        OfertaExtendida fragment = new OfertaExtendida();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_avaliacoes, container, false);

        idPulicacao = getArguments().getInt("idPublicacao");
        chaveLista = getArguments().getString("chave");

        txtTituloOferta = view.findViewById(R.id.txtTituloOferta);
        imgOferta = view.findViewById(R.id.imagemOferta);
        txtDescricao = view.findViewById(R.id.txtDescricao);
        txtValor = view.findViewById(R.id.txtValor);
        txtHorario = view.findViewById(R.id.txtHorario);
        txtContato = view.findViewById(R.id.txtContato);
        btnAvaliar = view.findViewById(R.id.button_avaliar);
        semAvaliacao = view.findViewById(R.id.semAvaliacao);
        btnVoltar = view.findViewById(R.id.button_Voltar);
        btnEditar = view.findViewById(R.id.button_editar);
        btnExcluir = view.findViewById(R.id.button_excluir);

        btnAvaliar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnExcluir.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.list);

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
                (OfertaExtendidaViewModel.class);

        mViewModel.buscarDadosPublicacao(idPulicacao);

        mViewModel.buscarListaAvaliacoes(idPulicacao);

        mViewModel.getPublicacao().observe(getViewLifecycleOwner(), publicacao -> {
            if(publicacao!=null){

                if(publicacao.usuarioId==usuarioViewModel.getId().getValue()){
                    btnAvaliar.setVisibility(View.GONE);
                    btnEditar.setVisibility(View.VISIBLE);
                    btnExcluir.setVisibility(View.VISIBLE);
                }

                txtTituloOferta.setText(publicacao.titulo);
                if(publicacao.imagemPublicacao!=null && publicacao.imagemPublicacao.length>0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(publicacao.imagemPublicacao,
                            0, publicacao.imagemPublicacao.length);
                    imgOferta.setImageBitmap(bitmap);
                }
                txtDescricao.setText(publicacao.descricao);
                if(publicacao.valor!=null && publicacao.valor.length()>0){
                    txtValor.setText("R$"+publicacao.valor);
                }else{
                    txtValor.setText("Valor não informado");
                }
                if(publicacao.horario!=null && publicacao.horario.length()>0){
                    txtHorario.setText(publicacao.horario);
                }else{
                    txtHorario.setText("Horário não informado");
                }
                txtContato.setText(publicacao.contato);
            }
            });

        mViewModel.getListaAvaliacoes().observe(getViewLifecycleOwner(), avaliacoes -> {
            if (avaliacoes == null || avaliacoes.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                semAvaliacao.setVisibility(View.VISIBLE);
            }
            MyOfertaExtendidaRecyclerViewAdapter adapter = new MyOfertaExtendidaRecyclerViewAdapter
                    (avaliacoes, usuarioViewModel.getId().getValue(),getContext(),mViewModel);
            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(adapter);
            } else {
                ((MyOfertaExtendidaRecyclerViewAdapter) recyclerView.getAdapter()).updateData(avaliacoes);
            }

            adapter.setItemClickListener((position) -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPublicacao",idPulicacao);
                    bundle.putString("chave",chaveLista);
                    bundle.putInt("idAvaliacao",position);

                    FragmentAvaliacao fragmentAvaliacao = new FragmentAvaliacao();
                    fragmentAvaliacao.setArguments(bundle);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, fragmentAvaliacao);
                    transaction.addToBackStack(null);
                    transaction.commit();
            });
        });

        mViewModel.getFimDelet().observe(getViewLifecycleOwner(), fim -> {
            if(fim!=null && fim){
                Bundle bundle = new Bundle();
                bundle.putString("chave", chaveLista);

                OfertasFragment ofertasVagaFragment = new OfertasFragment();
                ofertasVagaFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, ofertasVagaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.buscarDadosPublicacao(idPulicacao);
    }

    @Override
    public void onClick(View v) {
        if(v==btnAvaliar){
            if(usuarioViewModel.getLogin().getValue()==true){
                Bundle bundle = new Bundle();
                bundle.putInt("idPublicacao",idPulicacao);
                bundle.putString("chave", chaveLista);
                FragmentAvaliacao fragmentAvaliacao = new FragmentAvaliacao();
                fragmentAvaliacao.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fragmentAvaliacao);
                transaction.addToBackStack(null);
                transaction.commit();
            } else{
                Toast.makeText(getActivity(), "É preciso estar logado para avaliar!",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(v==btnVoltar){
            Bundle bundle = new Bundle();
            bundle.putString("chave", chaveLista);

            OfertasFragment ofertasVagaFragment = new OfertasFragment();
            ofertasVagaFragment.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ofertasVagaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if(v==btnExcluir){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Confirmação");
            builder.setMessage("Tem certeza de que deseja excluir a sua publicação?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mViewModel.deletarPulicacao(idPulicacao);
                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if(v==btnEditar){
            Bundle bundle = new Bundle();
            bundle.putInt("idPublicacao",idPulicacao);
            bundle.putString("chave",chaveLista);
            EditarPublicacao editarPublicacao = new EditarPublicacao();
            editarPublicacao.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, editarPublicacao);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}