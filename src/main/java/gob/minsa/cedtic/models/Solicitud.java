package gob.minsa.cedtic.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
    private List<DetalleSolicitud> detalleSolicitud = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DetalleSolicitud> getDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(List<DetalleSolicitud> detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }
}
