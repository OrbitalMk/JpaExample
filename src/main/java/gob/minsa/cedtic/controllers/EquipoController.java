package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.EquipoRequestDto;
import gob.minsa.cedtic.models.Equipo;
import gob.minsa.cedtic.services.EquipoService;

@RestController
@RequestMapping("/api/equipo")
public class EquipoController {

    private EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Equipo>> all() {
        var equipos = equipoService.getAll();
        return ResponseEntity.ok(equipos);
    }

    @PostMapping
    public ResponseEntity<Equipo> store(@RequestBody EquipoRequestDto equipo, UriComponentsBuilder ucb) {
        var newEquipo = equipoService.create(equipo);
        URI uri = ucb.path("/api/equipo/{id}")
            .buildAndExpand(newEquipo).toUri();

        return ResponseEntity.created(uri).body(newEquipo);
    }
}
