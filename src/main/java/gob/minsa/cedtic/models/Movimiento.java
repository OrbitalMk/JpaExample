package gob.minsa.cedtic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stockActual;

    private Long stockAnterior;

    @ManyToOne
    private Proceso proceso;

    @ManyToOne
    private Equipo equipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockActual() {
        return stockActual;
    }

    public void setStockActual(Long stockActual) {
        this.stockActual = stockActual;
    }

    public Long getStockAnterior() {
        return stockActual;
    }

    public void setStockAnterior(Long stockActual) {
        this.stockActual = stockActual;
    }

    public Long getCantidad() {
        return Math.abs(stockActual - stockAnterior);
    }

    public String getTipo() {
        if (stockActual > stockAnterior)
            return "Entrada";

        return "Salida";
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Equipo getEquippo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
