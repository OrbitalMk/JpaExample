package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.MarcaRequestDto;
import gob.minsa.cedtic.exceptions.ResourceNotFoundException;
import gob.minsa.cedtic.models.Marca;
import gob.minsa.cedtic.repositories.MarcaJpaRepository;

@Service
public class MarcaService {
    
    private MarcaJpaRepository marcaJpaRepository;

    public MarcaService(MarcaJpaRepository marcaJpaRepository) {
        this.marcaJpaRepository = marcaJpaRepository;
    }

    public Iterable<Marca> getAll() {
        return marcaJpaRepository.findAll();
    }

    public Marca getById(Long id) {
        return marcaJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Marca #%d no encontrada".formatted(id)));
    }

    public Marca create(MarcaRequestDto marca) {
        Marca newMarca = new Marca();
        newMarca.setNombre(marca.nombre());

        return marcaJpaRepository.save(newMarca);
    }
}
