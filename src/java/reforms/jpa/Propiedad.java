/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "propiedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propiedad.findAll", query = "SELECT p FROM Propiedad p")
    , @NamedQuery(name = "Propiedad.findById", query = "SELECT p FROM Propiedad p WHERE p.id = :id")
    , @NamedQuery(name = "Propiedad.findByDireccion", query = "SELECT p FROM Propiedad p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Propiedad.findByNumero", query = "SELECT p FROM Propiedad p WHERE p.numero = :numero")
    , @NamedQuery(name = "Propiedad.findByPiso", query = "SELECT p FROM Propiedad p WHERE p.piso = :piso")
    , @NamedQuery(name = "Propiedad.findByObservaciones", query = "SELECT p FROM Propiedad p WHERE p.observaciones = :observaciones")
    , @NamedQuery(name = "Propiedad.findByGeolat", query = "SELECT p FROM Propiedad p WHERE p.geolat = :geolat")
    , @NamedQuery(name = "Propiedad.findByGeolong", query = "SELECT p FROM Propiedad p WHERE p.geolong = :geolong")})
public class Propiedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 20)
    @Column(name = "piso")
    private String piso;
    @Size(max = 250)
    @Column(name = "observaciones")
    private String observaciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "geolat")
    private Float geolat;
    @Column(name = "geolong")
    private Float geolong;
    @JoinColumn(name = "localidad", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Localidad localidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad", fetch = FetchType.LAZY)
    private List<Poliza> polizaList;
    @OneToMany(mappedBy = "afectado", fetch = FetchType.LAZY)
    private List<Siniestro> siniestroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad", fetch = FetchType.LAZY)
    private List<Trabajador> trabajadorList;

    public Propiedad() {
    }

    public Propiedad(Integer id) {
        this.id = id;
    }

    public Propiedad(Integer id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Float getGeolat() {
        return geolat;
    }

    public void setGeolat(Float geolat) {
        this.geolat = geolat;
    }

    public Float getGeolong() {
        return geolong;
    }

    public void setGeolong(Float geolong) {
        this.geolong = geolong;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    @XmlTransient
    public List<Poliza> getPolizaList() {
        return polizaList;
    }

    public void setPolizaList(List<Poliza> polizaList) {
        this.polizaList = polizaList;
    }

    @XmlTransient
    public List<Siniestro> getSiniestroList() {
        return siniestroList;
    }

    public void setSiniestroList(List<Siniestro> siniestroList) {
        this.siniestroList = siniestroList;
    }

    @XmlTransient
    public List<Trabajador> getTrabajadorList() {
        return trabajadorList;
    }

    public void setTrabajadorList(List<Trabajador> trabajadorList) {
        this.trabajadorList = trabajadorList;
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
        if (!(object instanceof Propiedad)) {
            return false;
        }
        Propiedad other = (Propiedad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Propiedad[ id=" + id + " ]";
    }
    
}