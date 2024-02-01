package gob.minsa.cedtic.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Unidad unidad;

    @ManyToOne
    private Proceso proceso;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitud_id", nullable = false, referencedColumnName = "id")
    private List<DetalleSolicitud> detalleSolicitud = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public List<DetalleSolicitud> getDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(List<DetalleSolicitud> detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }
}
