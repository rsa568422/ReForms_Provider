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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "trabajador")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Trabajador.findAll", query = "SELECT t FROM Trabajador t")
    , @NamedQuery(name = "Trabajador.findById", query = "SELECT t FROM Trabajador t WHERE t.id = :id")
    , @NamedQuery(name = "Trabajador.findByDni", query = "SELECT t FROM Trabajador t WHERE t.dni = :dni")
    , @NamedQuery(name = "Trabajador.findByNombre", query = "SELECT t FROM Trabajador t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Trabajador.findByApellido1", query = "SELECT t FROM Trabajador t WHERE t.apellido1 = :apellido1")
    , @NamedQuery(name = "Trabajador.findByApellido2", query = "SELECT t FROM Trabajador t WHERE t.apellido2 = :apellido2")
    , @NamedQuery(name = "Trabajador.findByTelefono1", query = "SELECT t FROM Trabajador t WHERE t.telefono1 = :telefono1")
    , @NamedQuery(name = "Trabajador.findByTelefono2", query = "SELECT t FROM Trabajador t WHERE t.telefono2 = :telefono2")
    , @NamedQuery(name = "Trabajador.findByEmail", query = "SELECT t FROM Trabajador t WHERE t.email = :email")
    , @NamedQuery(name = "Trabajador.obtenerTrabajadores", query = "SELECT t FROM Trabajador t ORDER BY t.nombre, t.apellido1, t.apellido2")
    , @NamedQuery(name = "Trabajador.obtenerOperadores", query = "SELECT t FROM Operador o LEFT JOIN o.trabajador t ORDER BY t.nombre, t.apellido1, t.apellido2")
    , @NamedQuery(name = "Trabajador.obtenerOperarios", query = "SELECT t FROM Operario o LEFT JOIN o.trabajador t ORDER BY t.nombre, t.apellido1, t.apellido2")
    , @NamedQuery(name = "Trabajador.buscarTrabajadorPorDni", query = "SELECT t FROM Trabajador t WHERE t.dni = :dni")})
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido1", nullable = false, length = 50)
    private String apellido1;
    @Column(name = "apellido2", length = 50)
    private String apellido2;
    @Column(name = "telefono1", length = 9)
    private String telefono1;
    @Column(name = "telefono2", length = 9)
    private String telefono2;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "password", length = 130)
    private String password;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajador", fetch = FetchType.LAZY)
    private Operario operario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajador", fetch = FetchType.LAZY)
    private List<Nomina> nominas;
    @JoinColumn(name = "propiedad", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Propiedad propiedad;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajador", fetch = FetchType.LAZY)
    private Operador operador;

    public Trabajador() {
    }

    public Trabajador(Integer id) {
        this.id = id;
    }

    public Trabajador(Integer id, String dni, String nombre, String apellido1) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    @XmlTransient
    public List<Nomina> getNominas() {
        return nominas;
    }

    public void setNominas(List<Nomina> nominas) {
        this.nominas = nominas;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
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
        if (!(object instanceof Trabajador)) {
            return false;
        }
        Trabajador other = (Trabajador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Trabajador[ id=" + id + " ]";
    }
    
}
