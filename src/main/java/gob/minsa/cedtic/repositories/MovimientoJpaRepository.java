package gob.minsa.cedtic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Movimiento;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<Movimiento, Long> {
    @Query(value = "SELECT m.* FROM Movimiento m " +
        "INNER JOIN Proceso p ON m.proceso_id = p.id " +
        "INNER JOIN Equipo e ON m.equipo_id = e.id " +
        "WHERE e.clasificacion_equipo_id = :clasificacionEquipoId " +
        "ORDER BY m.created_at DESC LIMIT 1", nativeQuery = true)
    Optional<Movimiento> findFirstByProcesoIdAndClasificaionEquipoId(@Param("clasificacionEquipoId") Long clasificacionEquipoId);
}
