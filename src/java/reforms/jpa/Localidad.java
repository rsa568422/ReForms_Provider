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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "localidad")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Localidad.findAll", query = "SELECT l FROM Localidad l")
    , @NamedQuery(name = "Localidad.findById", query = "SELECT l FROM Localidad l WHERE l.id = :id")
    , @NamedQuery(name = "Localidad.findByNombre", query = "SELECT l FROM Localidad l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "Localidad.findByCp", query = "SELECT l FROM Localidad l WHERE l.cp = :cp")})
public class Localidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "cp", nullable = false, length = 5)
    private String cp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localidad", fetch = FetchType.LAZY)
    private List<Localidadesvisitadas> localidadesvisitadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localidad", fetch = FetchType.LAZY)
    private List<Propiedad> propiedades;

    public Localidad() {
    }

    public Localidad(Integer id) {
        this.id = id;
    }

    public Localidad(Integer id, String nombre, String cp) {
        this.id = id;
        this.nombre = nombre;
        this.cp = cp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @XmlTransient
    public List<Localidadesvisitadas> getLocalidadesvisitadas() {
        return localidadesvisitadas;
    }

    public void setLocalidadesvisitadas(List<Localidadesvisitadas> localidadesvisitadas) {
        this.localidadesvisitadas = localidadesvisitadas;
    }

    @XmlTransient
    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
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
        if (!(object instanceof Localidad)) {
            return false;
        }
        Localidad other = (Localidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Localidad[ id=" + id + " ]";
    }
    
}
