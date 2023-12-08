package com.example.bicofacil.navbar.home;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bicofacil.BD.avaliacao.Avaliacao;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.R;
import com.example.bicofacil.databinding.FragmentItemAvaliacaoBinding;

import java.util.List;

public class MyOfertaExtendidaRecyclerViewAdapter extends RecyclerView.Adapter<MyOfertaExtendidaRecyclerViewAdapter.ViewHolder> {

    private final List<Avaliacao> mValues;
    private final int idUsuario;
    private final Context context;
    private final OfertaExtendidaViewModel viewModel;
    private OnItemClickListener listener;

    public MyOfertaExtendidaRecyclerViewAdapter(List<Avaliacao> items, int idUsuario, Context context,
                                                OfertaExtendidaViewModel viewModel) {
        this.mValues = items;
        this.idUsuario = idUsuario;
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemAvaliacaoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Avaliacao avaliacaoAtual = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.textViewNome.setText(avaliacaoAtual.nomeUsuario);
        holder.txtViewDescricaoDaAvaliacao.setText(avaliacaoAtual.comentario);
        holder.nota.setRating((float) avaliacaoAtual.nota);

        if(idUsuario == avaliacaoAtual.usuarioId){
            holder.btnEditar.setVisibility(View.VISIBLE);
            holder.btnExcluir.setVisibility(View.VISIBLE);
        }

        holder.btnExcluir.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmação");
            builder.setMessage("Tem certeza de que deseja excluir a avaliação?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewModel.deletarAvaliacao(mValues.get(currentPosition).id, mValues.get
                            (currentPosition).publicacaoId);
                    Toast.makeText(context, "Avaliação excluída com sucesso!",
                            Toast.LENGTH_SHORT).show();

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
        });

        holder.btnEditar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(mValues.get(currentPosition).id);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewNome;
        public final TextView txtViewDescricaoDaAvaliacao;
        public final RatingBar nota;
        public final ImageButton btnEditar;
        public final ImageButton btnExcluir;

        public Avaliacao mItem;

        public ViewHolder(FragmentItemAvaliacaoBinding binding) {
            super(binding.getRoot());
            textViewNome = binding.textViewNome;
            txtViewDescricaoDaAvaliacao = binding.textViewDescricaoDaAvaliacao;
            nota = binding.nota;
            btnEditar = binding.imageButtonEditar;
            btnExcluir = binding.imageButtonDeletar;
        }
    }
    public void updateData(List<Avaliacao> novasAvaliacoes) {
        mValues.clear();
        mValues.addAll(novasAvaliacoes);
        notifyDataSetChanged();
    }
}