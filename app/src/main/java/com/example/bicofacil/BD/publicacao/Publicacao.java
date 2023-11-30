package com.example.bicofacil.BD.publicacao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.bicofacil.BD.usuario.Usuario;
@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "id",
        childColumns = "usuarioId"))
public class Publicacao {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int tipoOferta;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] imagemPublicacao;
    public String titulo;
    public String descricao;
    public String valor;
    public String horario;
    public String contato;
    public boolean favorito;
    @ColumnInfo(name = "usuarioId")
    public int usuarioId;

}



