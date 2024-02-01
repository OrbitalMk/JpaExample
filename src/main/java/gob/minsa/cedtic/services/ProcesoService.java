package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.ProcesoRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.Proceso;
import gob.minsa.cedtic.repositories.ProcesoJpaRepository;

@Service
public class ProcesoService {
    
    private ProcesoJpaRepository procesoJpaRepository;

    public ProcesoService(ProcesoJpaRepository procesoJpaRepository) {
        this.procesoJpaRepository = procesoJpaRepository;
    }

    public Iterable<Proceso> getAll() {
        return procesoJpaRepository.findAll();
    }

    public Proceso getById(Long id) {
        return procesoJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Proceso #%d no encontrado".formatted(id)));
    }

    public Proceso create(ProcesoRequestDto proceso) {
        Proceso newProceso = new Proceso();
        newProceso.setNombre(proceso.nombre());

        return procesoJpaRepository.save(newProceso);
    }
}
