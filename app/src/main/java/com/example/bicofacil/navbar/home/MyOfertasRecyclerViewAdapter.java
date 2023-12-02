package com.example.bicofacil.navbar.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.R;
import com.example.bicofacil.databinding.FragmentOfertasBinding;
import com.example.bicofacil.navbar.perfil.Perfil;

import java.util.List;


public class MyOfertasRecyclerViewAdapter extends RecyclerView.Adapter<MyOfertasRecyclerViewAdapter
        .ViewHolder> {

    private final List<Publicacao> listaPublicacoes;
    private OnItemClickListener listener;
    private final OfertasViewModel viewModel;

    public MyOfertasRecyclerViewAdapter(List<Publicacao> listaP, OfertasViewModel viewModel) {
        listaPublicacoes = listaP;
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOfertasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txtDescricao.setText("");
        holder.txtTitulo.setText("");
        holder.txtContato.setText("");
        holder.btnFavorito.clearColorFilter();
        holder.imagemOferta.setImageResource(R.drawable.baseline_no_photography_24);

        Publicacao item = listaPublicacoes.get(position);
        holder.mItem = item;
        holder.mItem = listaPublicacoes.get(position);
        holder.txtDescricao.setText(String.valueOf(listaPublicacoes.get(position).descricao));
        holder.txtTitulo.setText(listaPublicacoes.get(position).titulo);
        holder.txtContato.setText(listaPublicacoes.get(position).contato);

        if (listaPublicacoes.get(position).favorito) {
            holder.btnFavorito.setColorFilter(Color.RED);
        } else {
            holder.btnFavorito.clearColorFilter();
        }

        if (item.imagemPublicacao != null && item.imagemPublicacao.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.imagemPublicacao, 0, item.
                    imagemPublicacao.length);
            holder.imagemOferta.setImageBitmap(bitmap);
        } else{
            holder.imagemOferta.setImageResource(R.drawable.baseline_no_photography_24);
        }

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && listener != null) {
                Publicacao publicacao = listaPublicacoes.get(currentPosition);
                listener.onFavoritoClick(publicacao, currentPosition);
            }
        });


        holder.btnFavorito.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }
            Publicacao publicacao = listaPublicacoes.get(currentPosition);
            publicacao.favorito = !publicacao.favorito;

            viewModel.atualizarFavorito(publicacao.id, publicacao.favorito,
                    new FavoritoAtualizadoCallback() {
                @Override
                public void onFavoritoAtualizado() {
                    listaPublicacoes.set(currentPosition, publicacao);
                    if (publicacao.favorito) {
                        holder.btnFavorito.setColorFilter(Color.RED);
                    } else {
                        holder.btnFavorito.clearColorFilter();
                    }
                }
            });
        });
    }

    public interface OnItemClickListener {
        void onFavoritoClick(Publicacao publicacao, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaPublicacoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtDescricao;
        public final TextView txtTitulo;
        public final TextView txtContato;
        public final ImageButton btnFavorito;
        public final ImageView imagemOferta;
        public Publicacao mItem;
        public ViewHolder(FragmentOfertasBinding binding) {
            super(binding.getRoot());
            txtDescricao = binding.textViewDescricao;
            txtTitulo = binding.textViewTitulo;
            txtContato = binding.textViewContato;
            btnFavorito = binding.imageButtonFavorito;
            imagemOferta = binding.imageOferta;
        }


        @Override
        public String toString() {
            return super.toString() + " '" + txtTitulo.getText() + "'";
        }
    }

    public void updateData(List<Publicacao> novasPublicacoes) {
        listaPublicacoes.clear();
        listaPublicacoes.addAll(novasPublicacoes);
        notifyDataSetChanged();
    }

}