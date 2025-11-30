package com.mio.progetto.Controller;

import com.mio.progetto.Model.Categoria;
import com.mio.progetto.Model.SottoCategoriaRegistry;
import com.mio.progetto.Model.Sottocategoria;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorie")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @GetMapping
    public List<String> getAllCategorie() {
        return Arrays.stream(Categoria.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/{categoria}/sottocategorie")
    public List<String> getSottoCategorie(@PathVariable String categoria) {
        try {
            Categoria cat = Categoria.valueOf(categoria.toUpperCase());
            return SottoCategoriaRegistry.getSottoCategorie(cat).stream()
                    .map(Sottocategoria::getNome)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }
}
