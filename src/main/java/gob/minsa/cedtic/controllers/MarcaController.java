package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.MarcaRequestDto;
import gob.minsa.cedtic.models.Marca;
import gob.minsa.cedtic.services.MarcaService;

@RestController
@RequestMapping("/api/marca")
public class MarcaController {

    private MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Marca>> all() {
        var marcas = marcaService.getAll();
        return ResponseEntity.ok(marcas);
    }

    @PostMapping
    public ResponseEntity<Marca> store(@RequestBody MarcaRequestDto marca, UriComponentsBuilder ucb) {
        var newMarca = marcaService.create(marca);
        URI uri = ucb.path("/api/marca/{id}")
            .buildAndExpand(newMarca.getId()).toUri();

        return ResponseEntity.created(uri).body(newMarca);
    }
}
