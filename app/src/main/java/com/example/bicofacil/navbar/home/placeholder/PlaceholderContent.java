package com.example.bicofacil.navbar.home.placeholder;

import com.example.bicofacil.BD.publicacao.Publicacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Publicacao> ITEMS = new ArrayList<Publicacao>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, Publicacao> ITEM_MAP = new HashMap<String, Publicacao>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Publicacao item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }

    private static Publicacao createPlaceholderItem(int position) {
        Publicacao novaPublicacao = new Publicacao();
        novaPublicacao.id = position;
        novaPublicacao.tipoOferta=1;
        novaPublicacao.imagemPublicacao = new byte[0];
        novaPublicacao.titulo ="Título da Publicação " + position;
        novaPublicacao.descricao = "Descrição da Publicação " + position;
        novaPublicacao.valor ="Valor " + position ;
        novaPublicacao.horario ="Horário " + position ;
        novaPublicacao.contato ="Contato " + position ;
        novaPublicacao.favorito = true;
        novaPublicacao.usuarioId = 0;

        return novaPublicacao;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final int id;
        public final int tipoOferta;
        public final byte[] imagemPublicacao;
        public final String titulo;
        public final String descricao;
        public final String valor;
        public final String horario;
        public final String contato;
        public final boolean favorito;
        public final int usuarioId;


        public PlaceholderItem(int id, int tipoOferta, byte[] imagemPublicacao, String titulo, String descricao,
                               String valor, String horario, String contato, boolean favorito, int usuarioId) {
            this.id = id;
            this.tipoOferta = tipoOferta;
            this.imagemPublicacao = imagemPublicacao;
            this.titulo = titulo;
            this.descricao = descricao;
            this.valor = valor;
            this.horario = horario;
            this.contato = contato;
            this.favorito = favorito;
            this.usuarioId = usuarioId;
        }

    }
}