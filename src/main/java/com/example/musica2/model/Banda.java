package com.example.musica2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bandas")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private List<Album> albuns = new ArrayList<>();

    private Double avaliacao;
    private String resumo;

    public void adicionarAlbum(Album album) {
        albuns.add(album);
    }

    public Double media() {
        return avaliacao; // Implementação pode ser ajustada conforme necessário
    }

    public void adicionarNota(Double nota) {
        this.avaliacao = nota;
    }

    public String exibirAlbuns() {
        StringBuilder sb = new StringBuilder();
        for (Album album : albuns) {
            sb.append(album.getNome()).append("\n");
        }
        return sb.toString();
    }
}
