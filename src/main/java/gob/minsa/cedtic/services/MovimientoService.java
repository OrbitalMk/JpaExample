package gob.minsa.cedtic.services;

import gob.minsa.cedtic.dtos.request.MovimientoRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.Equipo;
import gob.minsa.cedtic.models.Movimiento;
import gob.minsa.cedtic.models.Proceso;
import gob.minsa.cedtic.repositories.EquipoJpaRepository;
import gob.minsa.cedtic.repositories.MovimientoJpaRepository;
import gob.minsa.cedtic.repositories.ProcesoJpaRepository;

public class MovimientoService {
    
    private MovimientoJpaRepository movimientoJpaRepository;
    private ProcesoJpaRepository procesoJpaRepository;
    private EquipoJpaRepository equipoJpaRepository;

    public MovimientoService(MovimientoJpaRepository movimientoJpaRepository,
        ProcesoJpaRepository procesoJpaRepository,
        EquipoJpaRepository equipoJpaRepository) {
        this.movimientoJpaRepository = movimientoJpaRepository;
        this.procesoJpaRepository = procesoJpaRepository;
        this.equipoJpaRepository = equipoJpaRepository;
    }

    public Iterable<Movimiento> getAll() {
        return movimientoJpaRepository.findAll();
    }

    public Movimiento getById(Long id) {
        return movimientoJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Movimiento #%d no encontrado".formatted(id)));
    }

    public Movimiento create(MovimientoRequestDto movimiento) {
        Proceso proceso = procesoJpaRepository.findById(movimiento.procesoId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Proceso #%d no encontrado".formatted(movimiento.procesoId())));

        Equipo equipo = equipoJpaRepository.findById(movimiento.equipoId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Equipo #%d no encontrado".formatted(movimiento.equipoId())));

        Movimiento newMovimiento = new Movimiento();
        newMovimiento.setStockActual(movimiento.stockActual());
        newMovimiento.setStockAnterior(movimiento.stockAnterior());
        newMovimiento.setProceso(proceso);
        newMovimiento.setEquipo(equipo);

        return movimientoJpaRepository.save(newMovimiento);
    }
}
