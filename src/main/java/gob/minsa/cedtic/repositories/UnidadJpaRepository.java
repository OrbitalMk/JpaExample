package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Unidad;

@Repository
public interface UnidadJpaRepository extends JpaRepository<Unidad, Long> {
}
