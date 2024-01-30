package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.DetalleSolicitud;

@Repository
public interface DetalleSolicitudJpaRepository extends JpaRepository<DetalleSolicitud, Long> {
}
