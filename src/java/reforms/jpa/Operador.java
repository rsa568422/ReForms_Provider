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
@Table(name = "operador")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Operador.findAll", query = "SELECT o FROM Operador o")
    , @NamedQuery(name = "Operador.findById", query = "SELECT o FROM Operador o WHERE o.id = :id")
    , @NamedQuery(name = "Operador.findByGerente", query = "SELECT o FROM Operador o WHERE o.gerente = :gerente")
    // Consultas diseñadas
    , @NamedQuery(name = "Operador.buscarOperadorPorTrabajador", query = "SELECT o FROM Operador o WHERE o.trabajador.id = :trabajadorId")
    // --------------------------- PARA BORRAR ---------------------------------
    , @NamedQuery(name = "Operador.prueba", query = "SELECT o FROM Operador o ORDER BY o.trabajador.nombre, o.trabajador.apellido1, o.trabajador.apellido2")})
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "gerente")
    private Integer gerente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gerente", fetch = FetchType.LAZY)
    private List<Jornada> jornadas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operador", fetch = FetchType.LAZY)
    private List<Evento> eventos;
    @JoinColumn(name = "trabajador", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Trabajador trabajador;

    public Operador() {
    }

    public Operador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGerente() {
        return gerente;
    }

    public void setGerente(Integer gerente) {
        this.gerente = gerente;
    }

    @XmlTransient
    public List<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    @XmlTransient
    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
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
        if (!(object instanceof Operador)) {
            return false;
        }
        Operador other = (Operador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Operador[ id=" + id + " ]";
    }
    
}
