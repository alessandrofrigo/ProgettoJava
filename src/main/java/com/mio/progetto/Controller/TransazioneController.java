package com.mio.progetto.Controller;

import com.mio.progetto.Model.TransazioneEntity;
import com.mio.progetto.Service.TransazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transazioni")
public class TransazioneController {

    private final TransazioneService transazioneService;

    public TransazioneController(TransazioneService transazioneService) {
        this.transazioneService = transazioneService;
    }

    @GetMapping
    public ResponseEntity<List<TransazioneEntity>> getAllTransazioni() {
        List<TransazioneEntity> transazioni = transazioneService.getAllTransazioni();
        return ResponseEntity.ok(transazioni);
    }

    @PostMapping
    public ResponseEntity<TransazioneEntity> insertTransazione(@RequestBody TransazioneEntity transazioneEntity) {
        transazioneService.insertTransazione(transazioneEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(transazioneEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransazioneById(@PathVariable int id) {
        int rows = transazioneService.deleteTransazioneById(id);
        if (rows > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/categoria/{categoria}")
    public ResponseEntity<Void> deleteTransazioniByCategoria(@PathVariable String categoria) {
        transazioneService.deleteTransazioniByCategoria(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/data/{data}")
    public ResponseEntity<Void> deleteTransazioniBeforeDate(@PathVariable String data) {
        transazioneService.deleteTransazioniBeforeDate(data);
        return ResponseEntity.noContent().build();
    }
}
