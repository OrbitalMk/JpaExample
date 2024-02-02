package gob.minsa.cedtic.dtos.response;

import java.util.List;

public record DisponibilidadResponseDto(Long clasificacionEquipoId, String clasificacionEquipo,
    boolean disponible, List<DisponibilidadEquipoResponseDto> equipo) {
}
