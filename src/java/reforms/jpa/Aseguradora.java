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
@Table(name = "aseguradora")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Aseguradora.findAll", query = "SELECT a FROM Aseguradora a")
    , @NamedQuery(name = "Aseguradora.findById", query = "SELECT a FROM Aseguradora a WHERE a.id = :id")
    , @NamedQuery(name = "Aseguradora.findByNombre", query = "SELECT a FROM Aseguradora a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Aseguradora.findByTelefono1", query = "SELECT a FROM Aseguradora a WHERE a.telefono1 = :telefono1")
    , @NamedQuery(name = "Aseguradora.findByTelefono2", query = "SELECT a FROM Aseguradora a WHERE a.telefono2 = :telefono2")
    , @NamedQuery(name = "Aseguradora.findByFax", query = "SELECT a FROM Aseguradora a WHERE a.fax = :fax")
    , @NamedQuery(name = "Aseguradora.findByEmail", query = "SELECT a FROM Aseguradora a WHERE a.email = :email")
    , @NamedQuery(name = "Aseguradora.obtenerAseguradoras", query = "SELECT a FROM Aseguradora a ORDER BY a.nombre")})
public class Aseguradora implements Serializable {

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
    @Column(name = "telefono1", nullable = false, length = 9)
    private String telefono1;
    @Column(name = "telefono2", length = 9)
    private String telefono2;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Column(name = "fax", length = 9)
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.LAZY)
    private List<Perito> peritos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.LAZY)
    private List<Trabajo> trabajos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.LAZY)
    private List<Cliente> clientes;

    public Aseguradora() {
    }

    public Aseguradora(Integer id) {
        this.id = id;
    }

    public Aseguradora(Integer id, String nombre, String telefono1, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono1 = telefono1;
        this.email = email;
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
    public List<Perito> getPeritos() {
        return peritos;
    }

    public void setPeritos(List<Perito> peritos) {
        this.peritos = peritos;
    }

    @XmlTransient
    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    @XmlTransient
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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
        if (!(object instanceof Aseguradora)) {
            return false;
        }
        Aseguradora other = (Aseguradora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Aseguradora[ id=" + id + " ]";
    }
    
}
