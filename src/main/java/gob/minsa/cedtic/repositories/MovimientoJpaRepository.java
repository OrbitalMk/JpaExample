package gob.minsa.cedtic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Movimiento;

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

    /**
     * "SELECT m.* FROM Movimiento m " +
        "INNER JOIN Proceso p ON m.proceso_id = :procesoId " +
        "INNER JOIN Equipo e ON m.equipo_id = e.id " +
        "WHERE e.clasificacion_equipo_id = :clasificacionEquipoId " +
        "AND m.created_at = (SELECT MAX(m.created_at) FROM MOVIMIENTO mc " +
        "INNER JOIN Proceso pc ON mc.proceso_id = m.proceso_id " +
        "WHERE mc.equipo_id = e.id ))"

        """
        SELECT m.id, m.stock_actual, m.stock_anterior, MAX(m.created_at) created_at, e.*
        FROM Movimiento m
        INNER JOIN Proceso p ON m.proceso_id = :procesoId
        INNER JOIN Equipo e ON m.equipo_id = e.id
        WHERE e.clasificacion_equipo_id = :clasificacionEquipoId
        GROUP BY m.id, m.stock_actual, m.stock_anterior, e.*
            """
     */
}
