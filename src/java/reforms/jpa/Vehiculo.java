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
@Table(name = "vehiculo")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
    , @NamedQuery(name = "Vehiculo.findById", query = "SELECT v FROM Vehiculo v WHERE v.id = :id")
    , @NamedQuery(name = "Vehiculo.findByMatricula", query = "SELECT v FROM Vehiculo v WHERE v.matricula = :matricula")
    , @NamedQuery(name = "Vehiculo.findByMarca", query = "SELECT v FROM Vehiculo v WHERE v.marca = :marca")
    , @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo")
    , @NamedQuery(name = "Vehiculo.findByMatriculacion", query = "SELECT v FROM Vehiculo v WHERE v.matriculacion = :matriculacion")
    , @NamedQuery(name = "Vehiculo.findByAdquisicion", query = "SELECT v FROM Vehiculo v WHERE v.adquisicion = :adquisicion")
    , @NamedQuery(name = "Vehiculo.findByKm", query = "SELECT v FROM Vehiculo v WHERE v.km = :km")
    , @NamedQuery(name = "Vehiculo.findByObservaciones", query = "SELECT v FROM Vehiculo v WHERE v.observaciones = :observaciones")
    // Consultas diseñadas
    , @NamedQuery(name = "Vehiculo.obtenerVehiculos", query = "SELECT v FROM Vehiculo v ORDER BY v.marca, v.modelo, v.matricula")
    , @NamedQuery(name = "Vehiculo.obtenerVehiculoDisponiblePorJornada", query = "SELECT v FROM Vehiculo v WHERE (v NOT IN (SELECT v2 FROM Jornada j JOIN j.grupos g JOIN g.conductor c JOIN c.vehiculo v2 WHERE (j.id = :idJornada))) ORDER BY v.matricula")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "matricula", nullable = false, length = 11)
    private String matricula;
    @Column(name = "marca", length = 10)
    private String marca;
    @Column(name = "modelo", length = 20)
    private String modelo;
    @Column(name = "matriculacion")
    @Temporal(TemporalType.DATE)
    private Date matriculacion;
    @Column(name = "adquisicion")
    @Temporal(TemporalType.DATE)
    private Date adquisicion;
    @Column(name = "km")
    private Integer km;
    @Column(name = "observaciones", length = 250)
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimientos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<Conductor> conductores;

    public Vehiculo() {
    }

    public Vehiculo(Integer id) {
        this.id = id;
    }

    public Vehiculo(Integer id, String matricula) {
        this.id = id;
        this.matricula = matricula;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getMatriculacion() {
        return matriculacion;
    }

    public void setMatriculacion(Date matriculacion) {
        this.matriculacion = matriculacion;
    }

    public Date getAdquisicion() {
        return adquisicion;
    }

    public void setAdquisicion(Date adquisicion) {
        this.adquisicion = adquisicion;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    @XmlTransient
    public List<Conductor> getConductores() {
        return conductores;
    }

    public void setConductores(List<Conductor> conductores) {
        this.conductores = conductores;
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
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Vehiculo[ id=" + id + " ]";
    }
}
