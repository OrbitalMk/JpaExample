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

import gob.minsa.cedtic.dtos.request.ProcesoRequestDto;
import gob.minsa.cedtic.models.Proceso;
import gob.minsa.cedtic.services.ProcesoService;

@RestController
@RequestMapping("/api/proceso")
public class ProcesoController {
    
    private ProcesoService procesoService;

    public ProcesoController(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Proceso>> all() {
        var procesos = procesoService.getAll();
        return ResponseEntity.ok(procesos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Proceso> show(@PathVariable Long id) {
        var proceso = procesoService.getById(id);
        return ResponseEntity.ok(proceso);
    }

    @PostMapping
    public ResponseEntity<Proceso> store(@RequestBody ProcesoRequestDto proceso, UriComponentsBuilder ucb) {
        var newProceso = procesoService.create(proceso);
        URI uri = ucb.path("/api/proceso")
            .buildAndExpand(newProceso.getId()).toUri();

        return ResponseEntity.created(uri).body(newProceso);
    }
}
