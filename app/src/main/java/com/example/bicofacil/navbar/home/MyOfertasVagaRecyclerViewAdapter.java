package com.example.bicofacil.navbar.home;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.databinding.FragmentOfertasBinding;

import java.util.List;


public class MyOfertasVagaRecyclerViewAdapter extends RecyclerView.Adapter<MyOfertasVagaRecyclerViewAdapter.ViewHolder> {

    private final List<Publicacao> mValues;

    public MyOfertasVagaRecyclerViewAdapter(List<Publicacao> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOfertasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Publicacao item = mValues.get(position);
        holder.mItem = item;
        holder.mItem = mValues.get(position);
        holder.txtDescricao.setText(String.valueOf(mValues.get(position).descricao));
        holder.txtTitulo.setText(mValues.get(position).titulo);
        holder.txtContato.setText(mValues.get(position).contato);
        if (item.favorito) {
            holder.btnFavorito.setColorFilter(Color.RED);}
        if (item.imagemPublicacao != null && item.imagemPublicacao.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.imagemPublicacao, 0, item.
                    imagemPublicacao.length);
            holder.imagemOferta.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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

}