package com.example.bicofacil.BD.avaliacao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.usuario.Usuario;

@Entity(foreignKeys = {
        @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "usuarioId"),
        @ForeignKey(entity = Publicacao.class, parentColumns = "id", childColumns = "publicacaoId")
},
        indices = {@Index(value = {"usuarioId", "publicacaoId"})})
public class Avaliacao {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "publicacaoId")
    public int publicacaoId;
    @ColumnInfo(name = "usuarioId")
    public int usuarioId;
    public int nota;
    public String comentario;
    public String nomeUsuario;
}
