package com.example.musica2.controller;

import com.example.musica2.model.Musica;
import com.example.musica2.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @GetMapping
    public List<Musica> getAllMusicas() {
        return musicaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> getMusicaById(@PathVariable Long id) {
        Optional<Musica> musica = musicaService.findById(id);
        return musica.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Musica createMusica(@RequestBody Musica musica) {
        return musicaService.save(musica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> updateMusica(@PathVariable Long id, @RequestBody Musica musicaDetails) {
        Optional<Musica> musica = musicaService.findById(id);
        if (musica.isPresent()) {
            Musica updatedMusica = musica.get();
            updatedMusica.setNome(musicaDetails.getNome());
            updatedMusica.setDuracao(musicaDetails.getDuracao());
            updatedMusica.setDescricaoResumida(musicaDetails.getDescricaoResumida());
            updatedMusica.setExibirFichaTecnica(musicaDetails.getExibirFichaTecnica());
            musicaService.save(updatedMusica);
            return ResponseEntity.ok(updatedMusica);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusica(@PathVariable Long id) {
        if (musicaService.findById(id).isPresent()) {
            musicaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
