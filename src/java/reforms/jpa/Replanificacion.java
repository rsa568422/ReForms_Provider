/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "replanificacion")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Replanificacion.findAll", query = "SELECT r FROM Replanificacion r")
    , @NamedQuery(name = "Replanificacion.findById", query = "SELECT r FROM Replanificacion r WHERE r.id = :id")
    , @NamedQuery(name = "Replanificacion.findByFecha", query = "SELECT r FROM Replanificacion r WHERE r.fecha = :fecha")
    // Consultas diseñadas
    , @NamedQuery(name = "Replanificacion.obtenerReplanificaciones", query = "SELECT r FROM Replanificacion r WHERE r.siniestro.id = :siniestroId ORDER BY r.fecha DESC")})
public class Replanificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "siniestro", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Siniestro siniestro;

    public Replanificacion() {
    }

    public Replanificacion(Integer id) {
        this.id = id;
    }

    public Replanificacion(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Siniestro getSiniestro() {
        return siniestro;
    }

    public void setSiniestro(Siniestro siniestro) {
        this.siniestro = siniestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Replanificacion)) {
            return false;
        }
        Replanificacion other = (Replanificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Replanificacion[ id=" + id + " ]";
    }
    
}
