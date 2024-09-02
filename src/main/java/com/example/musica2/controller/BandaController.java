package com.example.musica2.controller;

import com.example.musica2.model.Album;
import com.example.musica2.model.Banda;
import com.example.musica2.service.AlbumService;
import com.example.musica2.service.BandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bandas")
public class BandaController {

    @Autowired
    private BandaService bandaService;

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public List<Banda> getAllBandas() {
        return bandaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banda> getBandaById(@PathVariable Long id) {
        Optional<Banda> banda = bandaService.findById(id);
        return banda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Banda createBanda(@RequestBody Banda banda) {
        return bandaService.save(banda);
    }

    @PutMapping("/{id}/addAlbum/{albumId}")
    public ResponseEntity<Banda> addAlbumToBanda(@PathVariable Long id, @PathVariable Long albumId) {
        Optional<Banda> bandaOptional = bandaService.findById(id);
        Optional<Album> albumOptional = albumService.findById(albumId);

        if (bandaOptional.isPresent() && albumOptional.isPresent()) {
            Banda banda = bandaOptional.get();
            banda.adicionarAlbum(albumOptional.get());
            bandaService.save(banda);
            return ResponseEntity.ok(banda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/addNota")
    public ResponseEntity<Banda> addNotaToBanda(@PathVariable Long id, @RequestBody Double nota) {
        Optional<Banda> bandaOptional = bandaService.findById(id);

        if (bandaOptional.isPresent()) {
            Banda banda = bandaOptional.get();
            banda.adicionarNota(nota);
            bandaService.save(banda);
            return ResponseEntity.ok(banda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exibirAlbuns")
    public ResponseEntity<String> exibirAlbunsDaBanda(@PathVariable Long id) {
        Optional<Banda> bandaOptional = bandaService.findById(id);

        if (bandaOptional.isPresent()) {
            Banda banda = bandaOptional.get();
            return ResponseEntity.ok(banda.exibirAlbuns());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/media")
    public ResponseEntity<Double> getMediaNota(@PathVariable Long id) {
        Optional<Banda> bandaOptional = bandaService.findById(id);

        if (bandaOptional.isPresent()) {
            Banda banda = bandaOptional.get();
            return ResponseEntity.ok(banda.media());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanda(@PathVariable Long id) {
        if (bandaService.findById(id).isPresent()) {
            bandaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
