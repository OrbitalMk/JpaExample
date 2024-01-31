package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.ClasificacionEquipoRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.repositories.ClasificacionEquipoJpaRepository;

@Service
public class ClasificacionEquipoService {
    
    private ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository;

    public ClasificacionEquipoService(ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository) {
        this.clasificacionEquipoJpaRepository = clasificacionEquipoJpaRepository;
    }

    public Iterable<ClasificacionEquipo> getAll() {
        return clasificacionEquipoJpaRepository.findAll();
    }

    public ClasificacionEquipo getById(Long id) {
        return clasificacionEquipoJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Clasificacion equipo #%d no encontrada".formatted(id)));
    }

    public ClasificacionEquipo create(ClasificacionEquipoRequestDto clasificacionEquipo) {
        ClasificacionEquipo newClasificacionEquipo = new ClasificacionEquipo();
        newClasificacionEquipo.setNombre(clasificacionEquipo.nombre());

        return clasificacionEquipoJpaRepository.save(newClasificacionEquipo);
    }
}
