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
    // Consultas por defecto
      @NamedQuery(name = "Siniestro.findAll", query = "SELECT s FROM Siniestro s")
    , @NamedQuery(name = "Siniestro.findById", query = "SELECT s FROM Siniestro s WHERE s.id = :id")
    , @NamedQuery(name = "Siniestro.findByNumero", query = "SELECT s FROM Siniestro s WHERE s.numero = :numero")
    , @NamedQuery(name = "Siniestro.findByFechaRegistro", query = "SELECT s FROM Siniestro s WHERE s.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Siniestro.findByObservaciones", query = "SELECT s FROM Siniestro s WHERE s.observaciones = :observaciones")
    , @NamedQuery(name = "Siniestro.findByAlbaran", query = "SELECT s FROM Siniestro s WHERE s.albaran = :albaran")
    // Consultas diseñadas
    , @NamedQuery(name = "Siniestro.contarSiniestros", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE (:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id)")
    , @NamedQuery(name = "Siniestro.obtenerSiniestros", query = "SELECT s FROM Siniestro s WHERE (:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestrosAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados))")
    , @NamedQuery(name = "Siniestro.obtenerSiniestrosAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestrosNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado))")
    , @NamedQuery(name = "Siniestro.obtenerSiniestrosNoAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado)) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroSiniestro", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroSiniestro", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.estado, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroSiniestroAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroSiniestroAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroSiniestroNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroSiniestroNoAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroPoliza", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.poliza.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroPoliza", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.poliza.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroPolizaAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.poliza.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroPolizaAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.poliza.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNumeroPolizaNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.poliza.numero LIKE :numero))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNumeroPolizaNoAbiertos", query = "SELECT s FROM Siniestro s WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.poliza.numero LIKE :numero)) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNombre", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNombre", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2)))) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNombreAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNombreAbiertos", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2)))) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorNombreNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorNombreNoAbiertos", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND ((s.poliza.cliente.nombre LIKE :nombre AND s.poliza.cliente.apellido1 LIKE :apellido1 AND (s.poliza.cliente.apellido2 IS NULL OR s.poliza.cliente.apellido2 LIKE :apellido2)) OR (c.nombre LIKE :nombre AND c.apellido1 LIKE :apellido1 AND (c.apellido2 IS NULL OR c.apellido2 LIKE :apellido2)))) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorTelefono", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorTelefono", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorTelefonoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorTelefonoAbiertos", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorTelefonoNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorTelefonoNoAbiertos", query = "SELECT s FROM Siniestro s LEFT JOIN s.contactos c WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (s.poliza.cliente.telefono1 = :telefono OR s.poliza.cliente.telefono2 = :telefono OR c.telefono1 = :telefono OR c.telefono2 = :telefono)) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorDireccion", query = "SELECT COUNT(s.id) FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorDireccion", query = "SELECT s FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp)))) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorDireccionAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorDireccionAbiertos", query = "SELECT s FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado IN :subestados) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp)))) ORDER BY s.fechaRegistro DESC, s.estado, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.contarSiniestroPorDireccionNoAbiertos", query = "SELECT COUNT(s.id) FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp))))")
    , @NamedQuery(name = "Siniestro.buscarSiniestroPorDireccionNoAbiertos", query = "SELECT s FROM Siniestro s JOIN s.poliza.propiedad p LEFT JOIN s.afectado a WHERE ((:aseguradoraId is NULL OR :aseguradoraId = s.poliza.cliente.aseguradora.id) AND (s.estado = :estado) AND (((p.direccion = :direccion) AND (p.numero = :numero) AND (p.piso is NULL OR :piso is NULL OR p.piso LIKE :piso) AND (p.localidad.cp = :cp)) OR (a is NOT NULL AND (a.direccion = :direccion) AND (a.numero = :numero) AND (a.piso is NULL OR :piso is NULL OR a.piso LIKE :piso) AND (p.localidad.cp = :cp)))) ORDER BY s.fechaRegistro DESC, s.numero, s.poliza.numero")
    , @NamedQuery(name = "Siniestro.estadoMinimo", query = "SELECT t.estado FROM Siniestro s JOIN s.tareas t WHERE (s.id = :idSiniestro) ORDER BY t.estado ASC")
    , @NamedQuery(name = "Siniestro.estadoMaximo", query = "SELECT t.estado FROM Siniestro s JOIN s.tareas t WHERE (s.id = :idSiniestro) ORDER BY t.estado DESC")})
public class Siniestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;
    @Basic(optional = false)
    @Column(name = "fechaRegistro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "fechaCierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "observaciones", length = 250)
    private String observaciones;
    @Column(name = "albaran")
    private Integer albaran;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Tarea> tareas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Evento> eventos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Adjunto> adjuntos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Contacto> contactos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Reasignacion> reasignaciones;
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
    private List<Participante> participantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siniestro", fetch = FetchType.LAZY)
    private List<Replanificacion> replanificaciones;

    public Siniestro() {
    }

    public Siniestro(Integer id) {
        this.id = id;
    }

    public Siniestro(Integer id, String numero, Date fechaRegistro) {
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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
    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    @XmlTransient
    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @XmlTransient
    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    @XmlTransient
    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    @XmlTransient
    public List<Reasignacion> getReasignaciones() {
        return reasignaciones;
    }

    public void setReasignaciones(List<Reasignacion> reasignaciones) {
        this.reasignaciones = reasignaciones;
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
    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    @XmlTransient
    public List<Replanificacion> getReplanificaciones() {
        return replanificaciones;
    }

    public void setReplanificaciones(List<Replanificacion> replanificaciones) {
        this.replanificaciones = replanificaciones;
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
