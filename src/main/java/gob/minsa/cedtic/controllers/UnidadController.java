package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.UnidadRequestDto;
import gob.minsa.cedtic.models.Unidad;
import gob.minsa.cedtic.services.UnidadService;

@RestController
@RequestMapping("/api/unidad")
public class UnidadController {
    
    private UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Unidad>> all() {
        var unidades = unidadService.getAll();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("{id}")
    public ResponseEntity<Unidad> show(@PathVariable Long id) {
        var unidad = unidadService.getById(id);
        return ResponseEntity.ok(unidad);
    }

    @PostMapping
    public ResponseEntity<Unidad> store(@RequestBody UnidadRequestDto unidad, UriComponentsBuilder ucb) {
        var newUnidad = unidadService.create(unidad);
        URI uri = ucb.path("/api/unidad")
            .buildAndExpand(newUnidad.getId()).toUri();

        return ResponseEntity.created(uri).body(newUnidad);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
