package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.UnidadRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.Unidad;
import gob.minsa.cedtic.repositories.UnidadJpaRepository;

@Service
public class UnidadService {
    
    private UnidadJpaRepository unidadJpaRepository;

    public UnidadService(UnidadJpaRepository unidadJpaRepository) {
        this.unidadJpaRepository = unidadJpaRepository;
    }

    public Iterable<Unidad> getAll() {
        return unidadJpaRepository.findAll();
    }

    public Unidad getById(Long id) {
        return unidadJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Unidad #%d no encontrada".formatted(id)));
    }

    public Unidad create(UnidadRequestDto unidad) {
        Unidad newUnidad = new Unidad();
        newUnidad.setNombre(unidad.nombre());

        return unidadJpaRepository.save(newUnidad);
    }

    public void delete(Long id) {
        Unidad unidad = unidadJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Unidad #%d no encontrada".formatted(id)));

        unidadJpaRepository.delete(unidad);
    }
}
