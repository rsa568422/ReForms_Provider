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
@Table(name = "gremio")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Gremio.findAll", query = "SELECT g FROM Gremio g")
    , @NamedQuery(name = "Gremio.findById", query = "SELECT g FROM Gremio g WHERE g.id = :id")
    , @NamedQuery(name = "Gremio.findByNombre", query = "SELECT g FROM Gremio g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "Gremio.findByDescripcion", query = "SELECT g FROM Gremio g WHERE g.descripcion = :descripcion")})
public class Gremio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gremio", fetch = FetchType.LAZY)
    private List<Trabajo> trabajos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gremio", fetch = FetchType.LAZY)
    private List<Capacidad> capacidades;

    public Gremio() {
    }

    public Gremio(Integer id) {
        this.id = id;
    }

    public Gremio(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    @XmlTransient
    public List<Capacidad> getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(List<Capacidad> capacidades) {
        this.capacidades = capacidades;
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
        if (!(object instanceof Gremio)) {
            return false;
        }
        Gremio other = (Gremio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Gremio[ id=" + id + " ]";
    }
    
}
