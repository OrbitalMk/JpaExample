package gob.minsa.cedtic.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.AtenderSolicitudRequestDto;
import gob.minsa.cedtic.dtos.request.SolicitudRequestDto;
import gob.minsa.cedtic.dtos.response.DisponibilidadEquipoResponseDto;
import gob.minsa.cedtic.dtos.response.DisponibilidadResponseDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.models.DetalleSolicitud;
import gob.minsa.cedtic.models.Equipo;
import gob.minsa.cedtic.models.Movimiento;
import gob.minsa.cedtic.models.Proceso;
import gob.minsa.cedtic.models.Solicitud;
import gob.minsa.cedtic.models.Unidad;
import gob.minsa.cedtic.repositories.ClasificacionEquipoJpaRepository;
import gob.minsa.cedtic.repositories.EquipoJpaRepository;
import gob.minsa.cedtic.repositories.MovimientoJpaRepository;
import gob.minsa.cedtic.repositories.ProcesoJpaRepository;
import gob.minsa.cedtic.repositories.SolicitudJpaRepository;
import gob.minsa.cedtic.repositories.UnidadJpaRepository;

@Service
public class SolicitudService {

    private SolicitudJpaRepository solicitudJpaRepository;
    private ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository;
    private UnidadJpaRepository unidadJpaRepository;
    private ProcesoJpaRepository procesoJpaRepository;
    private MovimientoJpaRepository movimientoJpaRepository;
    private EquipoJpaRepository equipoJpaRepository;

    public SolicitudService(SolicitudJpaRepository solicitudJpaRepository,
        ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository,
        UnidadJpaRepository unidadJpaRepository,
        ProcesoJpaRepository procesoJpaRepository,
        MovimientoJpaRepository movimientoJpaRepository,
        EquipoJpaRepository equipoJpaRepository) {
        this.solicitudJpaRepository = solicitudJpaRepository;
        this.clasificacionEquipoJpaRepository = clasificacionEquipoJpaRepository;
        this.unidadJpaRepository = unidadJpaRepository;
        this.procesoJpaRepository = procesoJpaRepository;
        this.movimientoJpaRepository = movimientoJpaRepository;
        this.equipoJpaRepository = equipoJpaRepository;
    }
    
    public Iterable<Solicitud> getAll() {
        return solicitudJpaRepository.findAll();
    }

    public Solicitud getById(Long id) {
        return solicitudJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Solicitud #%d no encontrada".formatted(id)));
    }

    public Solicitud create(SolicitudRequestDto solicitud) {
        Solicitud newSolicitud = new Solicitud();
        Unidad unidad = unidadJpaRepository.findById(solicitud.unidadId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Unidad #%d no encontrada".formatted(solicitud.unidadId())));
        Proceso proceso = procesoJpaRepository.findById(solicitud.procesoId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Proceso #%d no encontrado".formatted(solicitud.procesoId())));

        newSolicitud.setUnidad(unidad);
        newSolicitud.setProceso(proceso);

        solicitud.detalleSolicitud().forEach(detalle -> {
            ClasificacionEquipo clasificacionEquipo = clasificacionEquipoJpaRepository
                .findById(detalle.clasificacionEquipoId())
                .orElse(null);

            DetalleSolicitud newDetalle = new DetalleSolicitud();
            newDetalle.setCantidadAprobada(detalle.cantidadAprobada());
            newDetalle.setCantidadSolicitada(detalle.cantidadSolicitada());
            newDetalle.setClasificacionEquipo(clasificacionEquipo);

            newSolicitud.getDetalleSolicitud().add(newDetalle);
        });

        return solicitudJpaRepository.save(newSolicitud);
    }

    public Iterable<DisponibilidadResponseDto> verificarDisponibilidad(Long id) {
        // si solicitud esta atendida return BadRequest

        Solicitud solicitud = solicitudJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Solicitud #%d no encontrada".formatted(id)));
        List<DisponibilidadResponseDto> response = new ArrayList<>();

        solicitud.getDetalleSolicitud().forEach(detalle -> {
            List<DisponibilidadEquipoResponseDto> disponibilidadEquipo = new ArrayList<>();
            Long clasificacionId = detalle.getClasificacionEquipo().getId();
            List<Movimiento> movimiento = movimientoJpaRepository
                .findFirstByProcesoIdAndClasificaionEquipoId(solicitud.getProceso().getId(), clasificacionId);

            Long total = 0L;
            for (Movimiento m : movimiento) {
                Equipo e = m.getEquipo();
                DisponibilidadEquipoResponseDto d = new DisponibilidadEquipoResponseDto(
                    e.getId(), e.getDescripcion(),
                    e.getMarca().getNombre(), e.getModelo(), m.getStockActual());
                total += m.getStockActual();
                disponibilidadEquipo.add(d);
            }

            String nombre = detalle.getClasificacionEquipo().getNombre();

            response.add(new DisponibilidadResponseDto(clasificacionId, nombre,
                detalle.getCantidadAprobada(), total, disponibilidadEquipo));
        });

        return response;
    }

    public void atenderSolicitud(Long solicitudId, List<AtenderSolicitudRequestDto> atender) {
        // acta
        Solicitud solicitud = solicitudJpaRepository.findById(solicitudId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Solicitud #%d no encontrada.".formatted(solicitudId)));

        atender.forEach(atenderSolicitud -> {
            Proceso proceso = solicitud.getProceso();
            Long clasificacionId = atenderSolicitud.clasificacionEquipoId();
            Long total = 0L;

            for (var equipoSolicitud : atenderSolicitud.equipos()) {
                ClasificacionEquipo clasificacionEquipo = clasificacionEquipoJpaRepository.findById(clasificacionId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Clasificacion Equipo #%d no encontrada.".formatted(clasificacionId)));

                Equipo equipo = equipoJpaRepository.findByIdAndClasificacionEquipo(equipoSolicitud.id(), clasificacionEquipo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Equipo #%d con Clasificacion #%d no encontrado.".formatted(equipoSolicitud.id(), clasificacionId)));

                Movimiento oldMovimiento = movimientoJpaRepository.findByProcesoAndEquipo(proceso, equipo)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Equipo #%d no posee movimiento de entrada para el proceso #%d"
                            .formatted(proceso.getId(), equipo.getId())));

                Movimiento movimiento = new Movimiento();
                movimiento.setStockAnterior(oldMovimiento.getStockActual());
                movimiento.setStockActual(oldMovimiento.getStockActual() - equipoSolicitud.cantidad());
                movimiento.setEquipo(equipo);
                movimiento.setProceso(proceso);
                movimiento.setCreatedAt(LocalDateTime.now());

                movimientoJpaRepository.save(movimiento);

                total += equipoSolicitud.cantidad();
            }

            /*DetalleSolicitud detalle = solicitudJpaRepository.
                findDetalleBySolicitudAndClasificacion(solicitudId, clasificacionId)
                .orElseThrow(() -> new ResourceNotFoundException("no encontrado"));

            if (total == detalle.getCantidadAprobada()) {
                throw new ResourceNotFoundException("Cantidad Aprobada es mayor que la solicitada.");
            }*/
        });
    }
}
