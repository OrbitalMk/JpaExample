package gob.minsa.cedtic.services;

import org.springframework.stereotype.Service;

import gob.minsa.cedtic.dtos.request.EquipoRequestDto;
import gob.minsa.cedtic.models.ClasificacionEquipo;
import gob.minsa.cedtic.models.Equipo;
import gob.minsa.cedtic.models.Marca;
import gob.minsa.cedtic.repositories.ClasificacionEquipoJpaRepository;
import gob.minsa.cedtic.repositories.EquipoJpaRepository;
import gob.minsa.cedtic.repositories.MarcaJpaRepository;

@Service
public class EquipoService {

    private EquipoJpaRepository equipoJpaRepository;
    private MarcaJpaRepository marcaJpaRepository;
    private ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository;

    public EquipoService(EquipoJpaRepository equipoJpaRepository,
        MarcaJpaRepository marcaJpaRepository,
        ClasificacionEquipoJpaRepository clasificacionEquipoJpaRepository) {
        this.equipoJpaRepository = equipoJpaRepository;
        this.marcaJpaRepository = marcaJpaRepository;
        this.clasificacionEquipoJpaRepository = clasificacionEquipoJpaRepository;
    }

    public Iterable<Equipo> getAll() {
        return equipoJpaRepository.findAll();
    }

    public Equipo getById(Long id) {
        return equipoJpaRepository.findById(id).orElse(null);
    }

    public Equipo create(EquipoRequestDto equipo) {
        Marca marca = marcaJpaRepository.findById(equipo.marcaId()).orElse(null);
        ClasificacionEquipo clasificacionEquipo = clasificacionEquipoJpaRepository.findById(equipo.clasificacionEquipoId()).orElse(null);
        
        Equipo newEquipo = new Equipo();
        newEquipo.setDescripcion(equipo.descripcion());
        newEquipo.setModelo(equipo.modelo());
        newEquipo.setMarca(marca);
        newEquipo.setClasificacionEquipo(clasificacionEquipo);

        return equipoJpaRepository.save(newEquipo);
    }
}
