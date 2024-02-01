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

import gob.minsa.cedtic.dtos.request.SolicitudRequestDto;
import gob.minsa.cedtic.dtos.response.DisponibilidadResponseDto;
import gob.minsa.cedtic.models.Solicitud;
import gob.minsa.cedtic.services.SolicitudService;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {
    
    private SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Solicitud>> all() {
        var marcas = solicitudService.getAll();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("{id}")
    public ResponseEntity<Solicitud> show(@PathVariable Long id) {
        var solicitud = solicitudService.getById(id);
        return ResponseEntity.ok(solicitud);
    }

    @PostMapping
    public ResponseEntity<Solicitud> store(@RequestBody SolicitudRequestDto marca, UriComponentsBuilder ucb) {
        var newMarca = solicitudService.create(marca);
        URI uri = ucb.path("/api/solicitud/{id}")
            .buildAndExpand(newMarca.getId()).toUri();

        return ResponseEntity.created(uri).body(newMarca);
    }

    @GetMapping("disponibilidad/{id}")
    public ResponseEntity<Iterable<DisponibilidadResponseDto>> disponibilidad(@PathVariable Long id) {
        var disponibilidad = solicitudService.verificarDisponibilidad(id);
        return ResponseEntity.ok(disponibilidad);
    }
}
