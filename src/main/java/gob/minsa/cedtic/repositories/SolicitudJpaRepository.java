package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Solicitud;

@Repository
public interface SolicitudJpaRepository extends JpaRepository<Solicitud, Long> {
}
