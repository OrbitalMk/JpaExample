package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.ClasificacionEquipo;

@Repository
public interface ClasificacionEquipoJpaRepository extends JpaRepository<ClasificacionEquipo, Long> {
}
