package gob.minsa.cedtic.dtos.request;

import java.util.List;

public record SolicitudRequestDto(Long unidadId, Long procesoId,
    List<DetalleSolicitudRequestDto> detalleSolicitud) {
}
