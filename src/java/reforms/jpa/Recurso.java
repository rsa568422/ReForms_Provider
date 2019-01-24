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
import javax.persistence.Lob;
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
@Table(name = "recurso")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r")
    , @NamedQuery(name = "Recurso.findById", query = "SELECT r FROM Recurso r WHERE r.id = :id")
    , @NamedQuery(name = "Recurso.findByTipo", query = "SELECT r FROM Recurso r WHERE r.tipo = :tipo")
    , @NamedQuery(name = "Recurso.findByNombre", query = "SELECT r FROM Recurso r WHERE r.nombre = :nombre")
    , @NamedQuery(name = "Recurso.findByDescripcion", query = "SELECT r FROM Recurso r WHERE r.descripcion = :descripcion")
    // Consultas diseñadas
    , @NamedQuery(name = "Recurso.obtenerRecursos", query = "SELECT r FROM Recurso r JOIN r.adjunto.siniestro s WHERE s.id = :siniestroId")})
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private Integer tipo;
    @Lob
    @Column(name = "fichero")
    private byte[] fichero;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 250)
    private String nombre;
    @Column(name = "descripcion",  length = 250)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private List<Factura> facturas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private Adjunto adjunto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "original", fetch = FetchType.LAZY)
    private List<Siniestro> siniestros;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private Material material;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private List<Recursossubcontrata> recursossubcontratas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private Subcontrata subcontrata;

    public Recurso() {
    }

    public Recurso(Integer id) {
        this.id = id;
    }

    public Recurso(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
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
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    @XmlTransient
    public List<Siniestro> getSiniestros() {
        return siniestros;
    }

    public void setSiniestros(List<Siniestro> siniestros) {
        this.siniestros = siniestros;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @XmlTransient
    public List<Recursossubcontrata> getRecursossubcontratas() {
        return recursossubcontratas;
    }

    public void setRecursossubcontratas(List<Recursossubcontrata> recursossubcontratas) {
        this.recursossubcontratas = recursossubcontratas;
    }

    public Subcontrata getSubcontrata() {
        return subcontrata;
    }

    public void setSubcontrata(Subcontrata subcontrata) {
        this.subcontrata = subcontrata;
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
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Recurso[ id=" + id + " ]";
    }
    
}
