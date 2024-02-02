package gob.minsa.cedtic.dtos.response;

public record DisponibilidadEquipoResponseDto(Long id, String nombre,
    String marca, String modelo, Long cantidadDisponible) {
}
