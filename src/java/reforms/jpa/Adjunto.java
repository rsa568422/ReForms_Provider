/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "adjunto")
@XmlRootElement
@NamedQueries({
    // Consultas por defecto
      @NamedQuery(name = "Adjunto.findAll", query = "SELECT a FROM Adjunto a")
    , @NamedQuery(name = "Adjunto.findById", query = "SELECT a FROM Adjunto a WHERE a.id = :id")
    // Consultas diseñadas
    , @NamedQuery(name = "Adjunto.buscarAdjuntoPorRecurso", query = "SELECT a FROM Adjunto a WHERE a.recurso.id = :idRecurso")})
public class Adjunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "recurso", referencedColumnName = "id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Recurso recurso;
    @JoinColumn(name = "siniestro", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Siniestro siniestro;

    public Adjunto() {
    }

    public Adjunto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Siniestro getSiniestro() {
        return siniestro;
    }

    public void setSiniestro(Siniestro siniestro) {
        this.siniestro = siniestro;
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
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Adjunto[ id=" + id + " ]";
    }
    
}
