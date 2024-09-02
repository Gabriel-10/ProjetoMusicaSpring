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
@Table(name = "albuns")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private List<Musica> musicas = new ArrayList<>();

    private Double avaliacao;
    private String duracaoTotal;

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
        calcularDuracaoTotal();
    }

    public String exibirMusicasDoAlbum() {
        StringBuilder sb = new StringBuilder();
        for (Musica musica : musicas) {
            sb.append(musica.getNome()).append("\n");
        }
        return sb.toString();
    }

    public void adicionarNota(Double nota) {
        this.avaliacao = nota;
    }

    public Double media() {
        return avaliacao;
    }

    private void calcularDuracaoTotal() {
        int totalDuracao = 0;
        for (Musica musica : musicas) {
            String[] parts = musica.getDuracao().split(":");
            int minutos = Integer.parseInt(parts[0]);
            int segundos = Integer.parseInt(parts[1]);
            totalDuracao += minutos * 60 + segundos;
        }
        int minutos = totalDuracao / 60;
        int segundos = totalDuracao % 60;
        this.duracaoTotal = String.format("%02d:%02d", minutos, segundos);
    }
}
