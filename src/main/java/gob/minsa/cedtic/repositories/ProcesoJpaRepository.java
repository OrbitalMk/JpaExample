package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Proceso;

@Repository
public interface ProcesoJpaRepository extends JpaRepository<Proceso, Long> {
}
