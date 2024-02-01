package gob.minsa.cedtic.dtos.request;

public record MovimientoRequestDto(Long stockActual, Long stockAnterior,
    Long procesoId, Long equipoId) {
}
