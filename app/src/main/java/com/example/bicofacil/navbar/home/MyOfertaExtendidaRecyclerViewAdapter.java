package com.example.bicofacil.navbar.home;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bicofacil.BD.avaliacao.Avaliacao;
import com.example.bicofacil.databinding.FragmentItemAvaliacaoBinding;

import java.util.List;

public class MyOfertaExtendidaRecyclerViewAdapter extends RecyclerView.Adapter<MyOfertaExtendidaRecyclerViewAdapter.ViewHolder> {

    private final List<Avaliacao> mValues;

    public MyOfertaExtendidaRecyclerViewAdapter(List<Avaliacao> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemAvaliacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Avaliacao avaliacaoAtual = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.textViewNome.setText(avaliacaoAtual.nomeUsuario);
        holder.txtViewDescricaoDaAvaliacao.setText(avaliacaoAtual.comentario);
        holder.nota.setRating((float) avaliacaoAtual.nota);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewNome;
        public final TextView txtViewDescricaoDaAvaliacao;
        public final RatingBar nota;
        public Avaliacao mItem;

        public ViewHolder(FragmentItemAvaliacaoBinding binding) {
            super(binding.getRoot());
            textViewNome = binding.textViewNome;
            txtViewDescricaoDaAvaliacao = binding.textViewDescricaoDaAvaliacao;
            nota = binding.nota;
        }


    }
}