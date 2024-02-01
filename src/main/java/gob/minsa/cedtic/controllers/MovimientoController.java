package gob.minsa.cedtic.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import gob.minsa.cedtic.dtos.request.MovimientoRequestDto;
import gob.minsa.cedtic.models.Movimiento;
import gob.minsa.cedtic.services.MovimientoService;

@RestController
@RequestMapping("/api/movimiento")
public class MovimientoController {
    
    private MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<Movimiento> create(@RequestBody MovimientoRequestDto movimiento, UriComponentsBuilder ucb) {
        var newMovimiento = movimientoService.create(movimiento);
        URI uri = ucb.path("/api/movimiento/{id}")
            .buildAndExpand(newMovimiento.getId()).toUri();
        
        return ResponseEntity.created(uri).body(newMovimiento);
    }
}
