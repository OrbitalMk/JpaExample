package gob.minsa.cedtic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class DetalleSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cantidadAprobada;

    private Long cantidadSolicitada;

    @ManyToOne
    private ClasificacionEquipo clasificacionEquipo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(Long cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }

    public Long getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Long cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public ClasificacionEquipo getClasificacionEquipo() {
        return clasificacionEquipo;
    }

    public void setClasificacionEquipo(ClasificacionEquipo clasificacionEquipo) {
        this.clasificacionEquipo = clasificacionEquipo;
    }
}
