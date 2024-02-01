package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.SolicitudRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.models.DetalleSolicitud;
import gob.minsa.cedtic.models.Proceso;
import gob.minsa.cedtic.models.Solicitud;
import gob.minsa.cedtic.models.Unidad;
import gob.minsa.cedtic.repositories.ClasificacionEquipoJpaRepository;
import gob.minsa.cedtic.repositories.ProcesoJpaRepository;
import gob.minsa.cedtic.repositories.SolicitudJpaRepository;
import gob.minsa.cedtic.repositories.UnidadJpaRepository;

@Service
public class SolicitudService {

    private SolicitudJpaRepository solicitudJpaRepository;
    private ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository;
    private UnidadJpaRepository unidadJpaRepository;
    private ProcesoJpaRepository procesoJpaRepository;

    public SolicitudService(SolicitudJpaRepository solicitudJpaRepository,
        ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository,
        UnidadJpaRepository unidadJpaRepository,
        ProcesoJpaRepository procesoJpaRepository) {
        this.solicitudJpaRepository = solicitudJpaRepository;
        this.clasificacionEquipoJpaRepository = clasificacionEquipoJpaRepository;
        this.unidadJpaRepository = unidadJpaRepository;
        this.procesoJpaRepository = procesoJpaRepository;
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
}
