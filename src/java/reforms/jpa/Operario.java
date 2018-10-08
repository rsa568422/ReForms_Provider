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
import javax.validation.constraints.Size;
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
    , @NamedQuery(name = "Operario.findByPass", query = "SELECT o FROM Operario o WHERE o.pass = :pass")})
public class Operario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "carnet")
    private Integer carnet;
    @Size(max = 100)
    @Column(name = "dispositivo")
    private String dispositivo;
    @Size(max = 9)
    @Column(name = "telefono")
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 100)
    @Column(name = "pass")
    private String pass;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Integrante> integranteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Localidadesvisitadas> localidadesvisitadasList;
    @JoinColumn(name = "trabajador", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador trabajador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operario", fetch = FetchType.LAZY)
    private List<Capacidad> capacidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<Conductor> conductorList;

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
    public List<Integrante> getIntegranteList() {
        return integranteList;
    }

    public void setIntegranteList(List<Integrante> integranteList) {
        this.integranteList = integranteList;
    }

    @XmlTransient
    public List<Localidadesvisitadas> getLocalidadesvisitadasList() {
        return localidadesvisitadasList;
    }

    public void setLocalidadesvisitadasList(List<Localidadesvisitadas> localidadesvisitadasList) {
        this.localidadesvisitadasList = localidadesvisitadasList;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @XmlTransient
    public List<Capacidad> getCapacidadList() {
        return capacidadList;
    }

    public void setCapacidadList(List<Capacidad> capacidadList) {
        this.capacidadList = capacidadList;
    }

    @XmlTransient
    public List<Conductor> getConductorList() {
        return conductorList;
    }

    public void setConductorList(List<Conductor> conductorList) {
        this.conductorList = conductorList;
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
