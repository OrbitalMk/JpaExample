package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.ClasificacionEquipoRequestDto;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.services.ClasificacionEquipoService;

@RestController
@RequestMapping("/api/clasificacion-equipo")
public class ClasificacionEquipoController {

    private ClasificacionEquipoService clasificacionEquipoService;

    public ClasificacionEquipoController(ClasificacionEquipoService clasificacionEquipoService) {
        this.clasificacionEquipoService = clasificacionEquipoService;
    }
    
    @GetMapping
    public ResponseEntity<Iterable<ClasificacionEquipo>> all() {
        var marcas = clasificacionEquipoService.getAll();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClasificacionEquipo> show(@PathVariable Long id) {
        var clasificacion = clasificacionEquipoService.getById(id);
        return ResponseEntity.ok(clasificacion);
    }

    @PostMapping
    public ResponseEntity<ClasificacionEquipo> store(@RequestBody ClasificacionEquipoRequestDto clasificacion, UriComponentsBuilder ucb) {
        var newClasificacion = clasificacionEquipoService.create(clasificacion);
        URI uri = ucb.path("/api/clasificacion-equipo/{id}")
            .buildAndExpand(newClasificacion.getId()).toUri();

        return ResponseEntity.created(uri).body(newClasificacion);
    }
}
