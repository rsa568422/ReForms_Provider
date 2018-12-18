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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id")
    , @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cliente.findByApellido1", query = "SELECT c FROM Cliente c WHERE c.apellido1 = :apellido1")
    , @NamedQuery(name = "Cliente.findByApellido2", query = "SELECT c FROM Cliente c WHERE c.apellido2 = :apellido2")
    , @NamedQuery(name = "Cliente.findByTelefono1", query = "SELECT c FROM Cliente c WHERE c.telefono1 = :telefono1")
    , @NamedQuery(name = "Cliente.findByTelefono2", query = "SELECT c FROM Cliente c WHERE c.telefono2 = :telefono2")
    , @NamedQuery(name = "Cliente.findByTipo", query = "SELECT c FROM Cliente c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Cliente.findByObservaciones", query = "SELECT c FROM Cliente c WHERE c.observaciones = :observaciones")
    // Consultas diseñadas
    , @NamedQuery(name = "Cliente.buscarClientePorTelefono", query = "SELECT c FROM Cliente c WHERE (c.telefono1 = :telefono OR c.telefono2 = :telefono) ORDER BY c.nombre, c.apellido1, c.apellido2")
    , @NamedQuery(name = "Cliente.buscarClientePorTelefonoA", query = "SELECT c FROM Cliente c WHERE (c.aseguradora.id = :aseguradoraId AND (c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY c.nombre, c.apellido1, c.apellido2")
    , @NamedQuery(name = "Cliente.buscarClientePorNombreCompleto", query = "SELECT c FROM Cliente c WHERE (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2)) ORDER BY c.nombre, c.apellido1, c.apellido2")
    , @NamedQuery(name = "Cliente.buscarClientePorNombreCompletoA", query = "SELECT c FROM Cliente c WHERE (c.aseguradora.id = :aseguradoraId AND c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2)) ORDER BY c.nombre, c.apellido1, c.apellido2")
    , @NamedQuery(name = "Cliente.buscarClientePorNombreMasTelefono", query = "SELECT c FROM Cliente c WHERE (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2) AND (c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY c.nombre, c.apellido1, c.apellido2")
    , @NamedQuery(name = "Cliente.buscarClientePorNombreMasTelefonoA", query = "SELECT c FROM Cliente c WHERE (c.aseguradora.id = :aseguradoraId AND c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2) AND (c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY c.nombre, c.apellido1, c.apellido2")})
public class Cliente implements Serializable {

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
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "observaciones", length = 250)
    private String observaciones;
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Llamada> llamadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Poliza> polizas;
    @JoinColumn(name = "aseguradora", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aseguradora aseguradora;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Cliente(Integer id, String nombre, String apellido1, String telefono1) {
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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

    @XmlTransient
    public List<Poliza> getPolizas() {
        return polizas;
    }

    public void setPolizas(List<Poliza> polizas) {
        this.polizas = polizas;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Cliente[ id=" + id + " ]";
    }
    
}
