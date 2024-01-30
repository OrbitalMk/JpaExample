package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.SolicitudRequestDto;
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

    @PostMapping
    public ResponseEntity<Solicitud> store(@RequestBody SolicitudRequestDto marca, UriComponentsBuilder ucb) {
        var newMarca = solicitudService.create(marca);
        URI uri = ucb.path("/api/solicitud/{id}")
            .buildAndExpand(newMarca.getId()).toUri();

        return ResponseEntity.created(uri).body(newMarca);
    }
}
