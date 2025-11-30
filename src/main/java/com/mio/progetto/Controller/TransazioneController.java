package com.mio.progetto.Controller;

import com.mio.progetto.Model.TransazioneEntity;
import com.mio.progetto.Service.TransazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transazioni")
@CrossOrigin(origins = "*") // Allow all origins for now
public class TransazioneController {

    private final TransazioneService transazioneService;

    @Autowired
    public TransazioneController(TransazioneService transazioneService) {
        this.transazioneService = transazioneService;
    }

    @GetMapping
    public List<TransazioneEntity> getAllTransazioni() {
        return transazioneService.getAllTransazioni();
    }

    @PostMapping
    public void insertTransazione(@RequestBody TransazioneEntity transazioneEntity) {
        transazioneService.insertTransazione(transazioneEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteTransazioneById(@PathVariable int id) {
        transazioneService.deleteTransazioneById(id);
    }

    @DeleteMapping("/categoria/{categoria}")
    public void deleteTransazioniByCategoria(@PathVariable String categoria) {
        transazioneService.deleteTransazioniByCategoria(categoria);
    }

    @DeleteMapping("/data/{data}")
    public void deleteTransazioniBeforeDate(@PathVariable String data) {
        transazioneService.deleteTransazioniBeforeDate(data);
    }
}
