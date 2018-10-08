/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.jpa;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Roberto
 */
@Entity
@Table(name = "siniestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Siniestro.findAll", query = "SELECT s FROM Siniestro s")
    , @NamedQuery(name = "Siniestro.findById", query = "SELECT s FROM Siniestro s WHERE s.id = :id")
    , @NamedQuery(name = "Siniestro.findByNumero", query = "SELECT s FROM Siniestro s WHERE s.numero = :numero")
    , @NamedQuery(name = "Siniestro.findByFechaRegistro", query = "SELECT s FROM Siniestro s WHERE s.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Siniestro.findByObservaciones", query = "SELECT s FROM Siniestro s WHERE s.observaciones = :observaciones")
    , @NamedQuery(name = "Siniestro.findByAlbaran", query = "SELECT s FROM Siniestro s WHERE s.albaran = :albaran")})
public class Siniestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Size(max = 250)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "albaran")
    private Integer albaran;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Tarea> tareaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Evento> eventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Adjunto> adjuntoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Contacto> contactoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Reasignacion> reasignacionList;
    @JoinColumn(name = "afectado", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Propiedad afectado;
    @JoinColumn(name = "original", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Recurso original;
    @JoinColumn(name = "peritoOriginal", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Perito peritoOriginal;
    @JoinColumn(name = "poliza", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poliza poliza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Participante> participanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Replanificacion> replanificacionList;

    public Siniestro() {
    }

    public Siniestro(Integer id) {
        this.id = id;
    }

    public Siniestro(Integer id, int numero, Date fechaRegistro) {
        this.id = id;
        this.numero = numero;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Integer albaran) {
        this.albaran = albaran;
    }

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @XmlTransient
    public List<Adjunto> getAdjuntoList() {
        return adjuntoList;
    }

    public void setAdjuntoList(List<Adjunto> adjuntoList) {
        this.adjuntoList = adjuntoList;
    }

    @XmlTransient
    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @XmlTransient
    public List<Reasignacion> getReasignacionList() {
        return reasignacionList;
    }

    public void setReasignacionList(List<Reasignacion> reasignacionList) {
        this.reasignacionList = reasignacionList;
    }

    public Propiedad getAfectado() {
        return afectado;
    }

    public void setAfectado(Propiedad afectado) {
        this.afectado = afectado;
    }

    public Recurso getOriginal() {
        return original;
    }

    public void setOriginal(Recurso original) {
        this.original = original;
    }

    public Perito getPeritoOriginal() {
        return peritoOriginal;
    }

    public void setPeritoOriginal(Perito peritoOriginal) {
        this.peritoOriginal = peritoOriginal;
    }

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    @XmlTransient
    public List<Participante> getParticipanteList() {
        return participanteList;
    }

    public void setParticipanteList(List<Participante> participanteList) {
        this.participanteList = participanteList;
    }

    @XmlTransient
    public List<Replanificacion> getReplanificacionList() {
        return replanificacionList;
    }

    public void setReplanificacionList(List<Replanificacion> replanificacionList) {
        this.replanificacionList = replanificacionList;
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
        if (!(object instanceof Siniestro)) {
            return false;
        }
        Siniestro other = (Siniestro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reforms.jpa.Siniestro[ id=" + id + " ]";
    }
    
}
