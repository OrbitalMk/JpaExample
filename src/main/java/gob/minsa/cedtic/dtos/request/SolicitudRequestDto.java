package gob.minsa.cedtic.dtos.request;

import java.util.List;

public record SolicitudRequestDto(List<DetalleSolicitudRequestDto> detalleSolicitud) {
}
