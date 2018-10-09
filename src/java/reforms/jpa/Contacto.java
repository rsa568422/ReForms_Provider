/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "contacto")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Contacto.findAll", query = "SELECT c FROM Contacto c")
    , @NamedQuery(name = "Contacto.findById", query = "SELECT c FROM Contacto c WHERE c.id = :id")
    , @NamedQuery(name = "Contacto.findByNombre", query = "SELECT c FROM Contacto c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Contacto.findByApellido1", query = "SELECT c FROM Contacto c WHERE c.apellido1 = :apellido1")
    , @NamedQuery(name = "Contacto.findByApellido2", query = "SELECT c FROM Contacto c WHERE c.apellido2 = :apellido2")
    , @NamedQuery(name = "Contacto.findByTelefono1", query = "SELECT c FROM Contacto c WHERE c.telefono1 = :telefono1")
    , @NamedQuery(name = "Contacto.findByTelefono2", query = "SELECT c FROM Contacto c WHERE c.telefono2 = :telefono2")
    , @NamedQuery(name = "Contacto.findByObservaciones", query = "SELECT c FROM Contacto c WHERE c.observaciones = :observaciones")})
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "apellido1", length = 50)
    private String apellido1;
    @Column(name = "apellido2", length = 50)
    private String apellido2;
    @Basic(optional = false)
    @Column(name = "telefono1", nullable = false, length = 9)
    private String telefono1;
    @Column(name = "telefono2", length = 9)
    private String telefono2;
    @Column(name = "observaciones", length = 250)
    private String observaciones;
    @OneToMany(mappedBy = "contacto", fetch = FetchType.LAZY)
    private List<Llamada> llamadas;
    @JoinColumn(name = "siniestro", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Siniestro siniestro;

    public Contacto() {
    }

    public Contacto(Integer id) {
        this.id = id;
    }

    public Contacto(Integer id, String telefono1) {
        this.id = id;
        this.telefono1 = telefono1;
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Llamada> getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(List<Llamada> llamadas) {
        this.llamadas = llamadas;
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
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Contacto[ id=" + id + " ]";
    }
    
}
