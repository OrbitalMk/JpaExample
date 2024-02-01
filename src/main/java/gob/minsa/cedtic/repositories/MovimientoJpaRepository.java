package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Movimiento;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<Movimiento, Long> {
}
