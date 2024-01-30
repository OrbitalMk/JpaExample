package gob.minsa.cedtic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private String modelo;

    @ManyToOne
    private Marca marca;

    @ManyToOne
    private ClasificacionEquipo clasificacionEquipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public ClasificacionEquipo getClasificacionEquipo() {
        return clasificacionEquipo;
    }

    public void setClasificacionEquipo(ClasificacionEquipo clasificacionEquipo) {
        this.clasificacionEquipo = clasificacionEquipo;
    }
}
