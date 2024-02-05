package gob.minsa.cedtic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.DetalleSolicitud;
import gob.minsa.cedtic.models.Solicitud;

@Repository
public interface SolicitudJpaRepository extends JpaRepository<Solicitud, Long> {
    /*@Query("SELECT ds FROM DetalleSolicitud ds " +
        "WHERE ds.solicitud.id = :solicitudId AND ds.clasificacionEquipo.id = :clasificacionId")
    Optional<DetalleSolicitud> findDetalleBySolicitudAndClasificacion(
        Long solicitudId, Long clasificacionId);*/
}
