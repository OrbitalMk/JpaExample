package gob.minsa.cedtic.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private LocalDateTime createdAt;

    @ManyToOne
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.EAGER)
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
        return stockAnterior;
    }

    public void setStockAnterior(Long stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public Long getCantidad() {
        return Math.abs(stockActual - stockAnterior);
    }

    public String getTipo() {
        if (stockActual > stockAnterior)
            return "Entrada";

        return "Salida";
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
