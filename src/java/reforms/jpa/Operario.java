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
@Table(name = "operario")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Operario.findAll", query = "SELECT o FROM Operario o")
    , @NamedQuery(name = "Operario.findById", query = "SELECT o FROM Operario o WHERE o.id = :id")
    , @NamedQuery(name = "Operario.findByCarnet", query = "SELECT o FROM Operario o WHERE o.carnet = :carnet")
    , @NamedQuery(name = "Operario.findByDispositivo", query = "SELECT o FROM Operario o WHERE o.dispositivo = :dispositivo")
    , @NamedQuery(name = "Operario.findByTelefono", query = "SELECT o FROM Operario o WHERE o.telefono = :telefono")
    , @NamedQuery(name = "Operario.findByEmail", query = "SELECT o FROM Operario o WHERE o.email = :email")
    , @NamedQuery(name = "Operario.findByPass", query = "SELECT o FROM Operario o WHERE o.pass = :pass")
    , @NamedQuery(name = "Operario.buscarOperarioPorTrabajador", query = "SELECT o FROM Operario o WHERE o.trabajador.id = :trabajadorId")})
public class Operario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "carnet")
    private Integer carnet;
    @Column(name = "dispositivo", length = 100)
    private String dispositivo;
    @Column(name = "telefono", length = 9)
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "pass", length = 100)
    private String pass;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Localidadesvisitadas> localidadesvisitadas;
    @JoinColumn(name = "trabajador", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador trabajador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Capacidad> capacidades;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<Conductor> conductores;

    public Operario() {
    }

    public Operario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarnet() {
        return carnet;
    }

    public void setCarnet(Integer carnet) {
        this.carnet = carnet;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @XmlTransient
    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    @XmlTransient
    public List<Localidadesvisitadas> getLocalidadesvisitadas() {
        return localidadesvisitadas;
    }

    public void setLocalidadesvisitadas(List<Localidadesvisitadas> localidadesvisitadas) {
        this.localidadesvisitadas = localidadesvisitadas;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @XmlTransient
    public List<Capacidad> getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(List<Capacidad> capacidades) {
        this.capacidades = capacidades;
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
        if (!(object instanceof Operario)) {
            return false;
        }
        Operario other = (Operario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Operario[ id=" + id + " ]";
    }
    
}
