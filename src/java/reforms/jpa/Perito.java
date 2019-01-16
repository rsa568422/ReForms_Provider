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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "perito")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Perito.findAll", query = "SELECT p FROM Perito p")
    , @NamedQuery(name = "Perito.findById", query = "SELECT p FROM Perito p WHERE p.id = :id")
    , @NamedQuery(name = "Perito.findByNombre", query = "SELECT p FROM Perito p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Perito.findByApellido1", query = "SELECT p FROM Perito p WHERE p.apellido1 = :apellido1")
    , @NamedQuery(name = "Perito.findByApellido2", query = "SELECT p FROM Perito p WHERE p.apellido2 = :apellido2")
    , @NamedQuery(name = "Perito.findByTelefono1", query = "SELECT p FROM Perito p WHERE p.telefono1 = :telefono1")
    , @NamedQuery(name = "Perito.findByTelefono2", query = "SELECT p FROM Perito p WHERE p.telefono2 = :telefono2")
    , @NamedQuery(name = "Perito.findByFax", query = "SELECT p FROM Perito p WHERE p.fax = :fax")
    , @NamedQuery(name = "Perito.findByEmail", query = "SELECT p FROM Perito p WHERE p.email = :email")
    // Consultas diseñadas
    , @NamedQuery(name = "Perito.buscarPeritoPorAseguradora", query = "SELECT p FROM Perito p WHERE (p.aseguradora.id = :aseguradoraId) ORDER BY p.nombre, p.apellido1, p.apellido2")
    , @NamedQuery(name = "Perito.buscarPeritoReasignadoPorSiniestro", query = "SELECT p FROM Perito p JOIN p.reasignaciones r WHERE (r.siniestro.id = :siniestroId) ORDER BY r.fecha DESC")})
public class Perito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido1", nullable = false, length = 50)
    private String apellido1;
    @Column(name = "apellido2", length = 50)
    private String apellido2;
    @Basic(optional = false)
    @Column(name = "telefono1", nullable = false, length = 9)
    private String telefono1;
    @Column(name = "telefono2", length = 9)
    private String telefono2;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Column(name = "fax", length = 9)
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 100)
    private String email;
    @OneToMany(mappedBy = "perito", fetch = FetchType.LAZY)
    private List<Llamada> llamadas;
    @JoinColumn(name = "aseguradora", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aseguradora aseguradora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perito", fetch = FetchType.LAZY)
    private List<Reasignacion> reasignaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "peritoOriginal", fetch = FetchType.LAZY)
    private List<Siniestro> siniestros;

    public Perito() {
    }

    public Perito(Integer id) {
        this.id = id;
    }

    public Perito(Integer id, String nombre, String apellido1, String telefono1) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Llamada> getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(List<Llamada> llamadas) {
        this.llamadas = llamadas;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    @XmlTransient
    public List<Reasignacion> getReasignaciones() {
        return reasignaciones;
    }

    public void setReasignaciones(List<Reasignacion> reasignaciones) {
        this.reasignaciones = reasignaciones;
    }

    @XmlTransient
    public List<Siniestro> getSiniestros() {
        return siniestros;
    }

    public void setSiniestros(List<Siniestro> siniestros) {
        this.siniestros = siniestros;
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
        if (!(object instanceof Perito)) {
            return false;
        }
        Perito other = (Perito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Perito[ id=" + id + " ]";
    }
    
}
