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
@Table(name = "subcontrata")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Subcontrata.findAll", query = "SELECT s FROM Subcontrata s")
    , @NamedQuery(name = "Subcontrata.findById", query = "SELECT s FROM Subcontrata s WHERE s.id = :id")
    , @NamedQuery(name = "Subcontrata.findByNombre", query = "SELECT s FROM Subcontrata s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "Subcontrata.findByTelefono1", query = "SELECT s FROM Subcontrata s WHERE s.telefono1 = :telefono1")
    , @NamedQuery(name = "Subcontrata.findByTelefono2", query = "SELECT s FROM Subcontrata s WHERE s.telefono2 = :telefono2")
    , @NamedQuery(name = "Subcontrata.findByFax", query = "SELECT s FROM Subcontrata s WHERE s.fax = :fax")
    , @NamedQuery(name = "Subcontrata.findByEmail", query = "SELECT s FROM Subcontrata s WHERE s.email = :email")})
public class Subcontrata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "telefono1", length = 9)
    private String telefono1;
    @Column(name = "telefono2", length = 9)
    private String telefono2;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Column(name = "fax", length = 9)
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 100)
    private String email;
    @OneToMany(mappedBy = "subcontrata", fetch = FetchType.LAZY)
    private List<Grupo> grupos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcontrata", fetch = FetchType.LAZY)
    private List<Recursossubcontrata> recursossubcontratas;
    @JoinColumn(name = "recurso", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recurso recurso;

    public Subcontrata() {
    }

    public Subcontrata(Integer id) {
        this.id = id;
    }

    public Subcontrata(Integer id, String nombre) {
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
    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @XmlTransient
    public List<Recursossubcontrata> getRecursossubcontratas() {
        return recursossubcontratas;
    }

    public void setRecursossubcontratas(List<Recursossubcontrata> recursossubcontratas) {
        this.recursossubcontratas = recursossubcontratas;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
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
        if (!(object instanceof Subcontrata)) {
            return false;
        }
        Subcontrata other = (Subcontrata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Subcontrata[ id=" + id + " ]";
    }
    
}
