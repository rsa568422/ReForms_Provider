/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "tarea")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t")
    , @NamedQuery(name = "Tarea.findById", query = "SELECT t FROM Tarea t WHERE t.id = :id")
    , @NamedQuery(name = "Tarea.findByCantidad", query = "SELECT t FROM Tarea t WHERE t.cantidad = :cantidad")
    , @NamedQuery(name = "Tarea.findByEstado", query = "SELECT t FROM Tarea t WHERE t.estado = :estado")
    , @NamedQuery(name = "Tarea.findByObservaciones", query = "SELECT t FROM Tarea t WHERE t.observaciones = :observaciones")
    , @NamedQuery(name = "Tarea.findByFechaAmpliacion", query = "SELECT t FROM Tarea t WHERE t.fechaAmpliacion = :fechaAmpliacion")
    , @NamedQuery(name = "Tarea.buscarTareaPorSiniestro", query = "SELECT t FROM Tarea t WHERE t.siniestro.id = :idSiniestro ORDER BY t.trabajo.gremio.nombre, t.trabajo.codigo")})
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "observaciones", length = 250)
    private String observaciones;
    @Column(name = "fechaAmpliacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAmpliacion;
    @OneToMany(mappedBy = "ampliacion", fetch = FetchType.LAZY)
    private List<Tarea> tareas;
    @JoinColumn(name = "ampliacion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tarea ampliacion;
    @JoinColumn(name = "siniestro", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Siniestro siniestro;
    @JoinColumn(name = "trabajo", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajo trabajo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarea", fetch = FetchType.LAZY)
    private List<Tareascita> tareascitas;

    public Tarea() {
    }

    public Tarea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaAmpliacion() {
        return fechaAmpliacion;
    }

    public void setFechaAmpliacion(Date fechaAmpliacion) {
        this.fechaAmpliacion = fechaAmpliacion;
    }

    @XmlTransient
    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Tarea getAmpliacion() {
        return ampliacion;
    }

    public void setAmpliacion(Tarea ampliacion) {
        this.ampliacion = ampliacion;
    }

    public Siniestro getSiniestro() {
        return siniestro;
    }

    public void setSiniestro(Siniestro siniestro) {
        this.siniestro = siniestro;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    @XmlTransient
    public List<Tareascita> getTareascitas() {
        return tareascitas;
    }

    public void setTareascitas(List<Tareascita> tareascitas) {
        this.tareascitas = tareascitas;
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
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Tarea[ id=" + id + " ]";
    }
    
}
