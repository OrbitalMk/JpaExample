package gob.minsa.cedtic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.minsa.cedtic.models.Marca;

@Repository
public interface MarcaJpaRepository extends JpaRepository<Marca, Long> {
}
