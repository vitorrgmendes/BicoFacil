package com.example.bicofacil.BD.avaliacao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.bicofacil.BD.publicacao.Publicacao;

@Entity(foreignKeys = @ForeignKey(entity = Publicacao.class,
        parentColumns = "id",
        childColumns = "publicacaoId"))
public class Avaliacao {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "publicacaoId")
    public int publicacaoId;
    public int nota;
    public String comentario;
    public String nomeUsuario;
}
