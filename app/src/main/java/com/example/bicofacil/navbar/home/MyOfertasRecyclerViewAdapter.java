package com.example.bicofacil.navbar.home;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bicofacil.BD.avaliacao.AvaliacaoMedia;
import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.R;
import com.example.bicofacil.databinding.FragmentOfertasBinding;

import java.util.List;


public class MyOfertasRecyclerViewAdapter extends RecyclerView.Adapter<MyOfertasRecyclerViewAdapter
        .ViewHolder> {

    private final List<Publicacao> listaPublicacoes;
    private final List<AvaliacaoMedia> listaNotas;
    public final int idUsuario;
    private OnItemClickListener listener;
    private final OfertasViewModel viewModel;
    private final Context context;
    private final String chaveLista;

    public MyOfertasRecyclerViewAdapter(List<Publicacao> listaPublicacoes, OfertasViewModel viewModel,
                                        List<AvaliacaoMedia> listaNotas, int idUsuario, Context context
            ,String chaveLista) {
        this.listaPublicacoes = listaPublicacoes;
        this.listaNotas = listaNotas;
        this.viewModel = viewModel;
        this.idUsuario = idUsuario;
        this.context = context;
        this.chaveLista = chaveLista;
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

        if(idUsuario==item.usuarioId){
            holder.btnEditar.setVisibility(View.VISIBLE);
            holder.btnDeletar.setVisibility(View.VISIBLE);
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
                listener.onFavoritoClick(publicacao, currentPosition, "publicacaoExtendida");
            }
        });

        holder.btnEditar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && listener != null) {
                Publicacao publicacao = listaPublicacoes.get(currentPosition);
                listener.onFavoritoClick(publicacao, currentPosition, "editar");
            }
        });

        holder.btnDeletar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmação");
            builder.setMessage("Tem certeza de que deseja excluir a sua publicação?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    viewModel.deletarPulicacao(chaveLista, idUsuario, listaPublicacoes.get(position)
                            .id);
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

            AvaliacaoMedia notaCorrespondente = encontrarNotaPorPublicacao(listaPublicacoes.get(position).id);
            if (notaCorrespondente != null) {
                holder.nota.setRating(notaCorrespondente.mediaNota);
            } else {
                holder.nota.setVisibility(View.GONE);
            }

    }

    public interface OnItemClickListener {
        void onFavoritoClick(Publicacao publicacao, int position, String chaveTela);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private AvaliacaoMedia encontrarNotaPorPublicacao(int publicacaoId) {
        for (AvaliacaoMedia nota : listaNotas) {
            if (nota.publicacaoId == publicacaoId) {
                return nota;
            }
        }
        return null;
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
        public final ImageButton btnDeletar;
        public final ImageButton btnEditar;
        public final ImageView imagemOferta;
        private final RatingBar nota;
        public Publicacao mItem;
        public ViewHolder(FragmentOfertasBinding binding) {
            super(binding.getRoot());
            txtDescricao = binding.textViewDescricao;
            txtTitulo = binding.textViewTitulo;
            txtContato = binding.textViewContato;
            btnFavorito = binding.imageButtonFavorito;
            imagemOferta = binding.imageOferta;
            btnDeletar = binding.imageButtonDeletar;
            btnEditar = binding.imageButtonEditar;
            nota = binding.nota;
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