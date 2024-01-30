package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.SolicitudRequestDto;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.models.DetalleSolicitud;
import gob.minsa.cedtic.models.Solicitud;
import gob.minsa.cedtic.repositories.ClasificacionEquipoJpaRepository;
import gob.minsa.cedtic.repositories.DetalleSolicitudJpaRepository;
import gob.minsa.cedtic.repositories.SolicitudJpaRepository;

@Service
public class SolicitudService {

    private SolicitudJpaRepository solicitudJpaRepository;
    private ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository;
    private DetalleSolicitudJpaRepository detalleSolicitudJpaRepository;

    public SolicitudService(SolicitudJpaRepository solicitudJpaRepository,
        ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository,
        DetalleSolicitudJpaRepository detalleSolicitudJpaRepository) {
        this.solicitudJpaRepository = solicitudJpaRepository;
        this.clasificacionEquipoJpaRepository = clasificacionEquipoJpaRepository;
        this.detalleSolicitudJpaRepository = detalleSolicitudJpaRepository;
    }
    
    public Iterable<Solicitud> getAll() {
        return solicitudJpaRepository.findAll();
    }

    public Solicitud getById(Long id) {
        return solicitudJpaRepository.findById(id).orElse(null);
    }

    public Solicitud create(SolicitudRequestDto solicitud) {
        Solicitud newSolicitud = new Solicitud();

        solicitud.detalleSolicitud().forEach(detalle -> {
            ClasificacionEquipo clasificacionEquipo = clasificacionEquipoJpaRepository
                .findById(detalle.clasificacionEquipoId())
                .orElse(null);

            DetalleSolicitud newDetalle = new DetalleSolicitud();
            newDetalle.setCantidadAprobada(detalle.cantidadAprobada());
            newDetalle.setCantidadSolicitada(detalle.cantidadSolicitada());
            newDetalle.setClasificacionEquipo(clasificacionEquipo);

            newDetalle = detalleSolicitudJpaRepository.save(newDetalle);

            newSolicitud.getDetalleSolicitud().add(newDetalle);
        });

        return solicitudJpaRepository.save(newSolicitud);
    }
}
