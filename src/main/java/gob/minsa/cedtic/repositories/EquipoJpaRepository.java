package gob.minsa.cedtic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.models.Equipo;

@Repository
public interface EquipoJpaRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByIdAndClasificacionEquipo(Long id, ClasificacionEquipo clasificacionEquipo);
}
