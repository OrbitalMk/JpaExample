package gob.minsa.cedtic.dtos.request;

import java.util.List;

public record AtenderSolicitudRequestDto(Long clasificacionEquipoId,
    List<AntenderEquipoRequestDto> equipos) {
}
