package com.example.musica2.controller;

import com.example.musica2.model.Album;
import com.example.musica2.model.Musica;
import com.example.musica2.service.AlbumService;
import com.example.musica2.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albuns")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicaService musicaService;

    @GetMapping
    public List<Album> getAllAlbuns() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Optional<Album> album = albumService.findById(id);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.save(album);
    }

    @PutMapping("/{id}/addMusica/{musicaId}")
    public ResponseEntity<Album> addMusicaToAlbum(@PathVariable Long id, @PathVariable Long musicaId) {
        Optional<Album> albumOptional = albumService.findById(id);
        Optional<Musica> musicaOptional = musicaService.findById(musicaId);

        if (albumOptional.isPresent() && musicaOptional.isPresent()) {
            Album album = albumOptional.get();
            album.adicionarMusica(musicaOptional.get());
            albumService.save(album);
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/addNota")
    public ResponseEntity<Album> addNotaToAlbum(@PathVariable Long id, @RequestBody Double nota) {
        Optional<Album> albumOptional = albumService.findById(id);

        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            album.adicionarNota(nota);
            albumService.save(album);
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exibirMusicas")
    public ResponseEntity<String> exibirMusicasDoAlbum(@PathVariable Long id) {
        Optional<Album> albumOptional = albumService.findById(id);

        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            return ResponseEntity.ok(album.exibirMusicasDoAlbum());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/media")
    public ResponseEntity<Double> getMediaNota(@PathVariable Long id) {
        Optional<Album> albumOptional = albumService.findById(id);

        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            return ResponseEntity.ok(album.media());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        if (albumService.findById(id).isPresent()) {
            albumService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
