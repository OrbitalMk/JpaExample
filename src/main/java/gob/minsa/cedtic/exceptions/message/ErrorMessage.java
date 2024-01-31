package gob.minsa.cedtic.exceptions.message;

import java.time.LocalDateTime;

public record ErrorMessage(String error, int status, LocalDateTime date) {
}
