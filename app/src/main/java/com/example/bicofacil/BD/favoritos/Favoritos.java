package com.example.bicofacil.BD.favoritos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.bicofacil.BD.publicacao.Publicacao;
import com.example.bicofacil.BD.usuario.Usuario;

@Entity(tableName = "favoritos",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "usuarioId"),
                @ForeignKey(entity = Publicacao.class, parentColumns = "id", childColumns = "publicacaoId")
        },
        indices = {@Index(value = {"usuarioId", "publicacaoId"}, unique = true)})
public class Favoritos {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "usuarioId")
    public int usuarioId;

    @ColumnInfo(name = "publicacaoId")
    public int publicacaoId;
}
