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
@Table(name = "trabajo")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Trabajo.findAll", query = "SELECT t FROM Trabajo t")
    , @NamedQuery(name = "Trabajo.findById", query = "SELECT t FROM Trabajo t WHERE t.id = :id")
    , @NamedQuery(name = "Trabajo.findByCodigo", query = "SELECT t FROM Trabajo t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "Trabajo.findByDescripcion", query = "SELECT t FROM Trabajo t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Trabajo.findByCantidadMin", query = "SELECT t FROM Trabajo t WHERE t.cantidadMin = :cantidadMin")
    , @NamedQuery(name = "Trabajo.findByPrecioMin", query = "SELECT t FROM Trabajo t WHERE t.precioMin = :precioMin")
    , @NamedQuery(name = "Trabajo.findByCantidadMed", query = "SELECT t FROM Trabajo t WHERE t.cantidadMed = :cantidadMed")
    , @NamedQuery(name = "Trabajo.findByPrecioMed", query = "SELECT t FROM Trabajo t WHERE t.precioMed = :precioMed")
    , @NamedQuery(name = "Trabajo.findByPrecioExtra", query = "SELECT t FROM Trabajo t WHERE t.precioExtra = :precioExtra")
    , @NamedQuery(name = "Trabajo.findByMedida", query = "SELECT t FROM Trabajo t WHERE t.medida = :medida")
    , @NamedQuery(name = "Trabajo.findByDificultad", query = "SELECT t FROM Trabajo t WHERE t.dificultad = :dificultad")
    // Consultas diseñadas
    , @NamedQuery(name = "Trabajo.buscarTrabajoPorAseguradoraGremio", query = "SELECT t FROM Trabajo t WHERE (t.aseguradora.id = :idAseguradora AND t.gremio.id = :idGremio) ORDER BY t.codigo")})
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false, length = 5)
    private String codigo;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidadMin")
    private Float cantidadMin;
    @Column(name = "precioMin")
    private Float precioMin;
    @Column(name = "cantidadMed")
    private Float cantidadMed;
    @Column(name = "precioMed")
    private Float precioMed;
    @Column(name = "precioExtra")
    private Float precioExtra;
    @Column(name = "medida")
    private Integer medida;
    @Column(name = "dificultad")
    private Integer dificultad;
    @JoinColumn(name = "aseguradora", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aseguradora aseguradora;
    @JoinColumn(name = "gremio", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gremio gremio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajo", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    public Trabajo() {
    }

    public Trabajo(Integer id) {
        this.id = id;
    }

    public Trabajo(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getCantidadMin() {
        return cantidadMin;
    }

    public void setCantidadMin(Float cantidadMin) {
        this.cantidadMin = cantidadMin;
    }

    public Float getPrecioMin() {
        return precioMin;
    }

    public void setPrecioMin(Float precioMin) {
        this.precioMin = precioMin;
    }

    public Float getCantidadMed() {
        return cantidadMed;
    }

    public void setCantidadMed(Float cantidadMed) {
        this.cantidadMed = cantidadMed;
    }

    public Float getPrecioMed() {
        return precioMed;
    }

    public void setPrecioMed(Float precioMed) {
        this.precioMed = precioMed;
    }

    public Float getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(Float precioExtra) {
        this.precioExtra = precioExtra;
    }

    public Integer getMedida() {
        return medida;
    }

    public void setMedida(Integer medida) {
        this.medida = medida;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Gremio getGremio() {
        return gremio;
    }

    public void setGremio(Gremio gremio) {
        this.gremio = gremio;
    }

    @XmlTransient
    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
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
        if (!(object instanceof Trabajo)) {
            return false;
        }
        Trabajo other = (Trabajo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Trabajo[ id=" + id + " ]";
    }
    
}
