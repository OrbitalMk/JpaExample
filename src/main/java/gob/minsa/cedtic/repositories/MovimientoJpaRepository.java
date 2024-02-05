package gob.minsa.cedtic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Equipo;
import gob.minsa.cedtic.models.Movimiento;
import gob.minsa.cedtic.models.Proceso;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m " +
        "LEFT JOIN FETCH m.proceso p " +
        "LEFT JOIN FETCH m.equipo e " +
        "WHERE m.proceso.id = :procesoId " +
        "AND e.clasificacionEquipo.id = :clasificacionEquipoId " +
        "AND m.createdAt = (SELECT MAX(mc.createdAt) FROM Movimiento mc " +
        "INNER JOIN mc.proceso pc " +
        "WHERE mc.equipo.id = e.id AND pc.id = p.id )")
    List<Movimiento> findFirstByProcesoIdAndClasificaionEquipoId(
        @Param("procesoId") Long procesoId,
        @Param("clasificacionEquipoId") Long clasificacionEquipoId);

    Optional<Movimiento> findByProcesoAndEquipo(Proceso proceso, Equipo equipo);
}
