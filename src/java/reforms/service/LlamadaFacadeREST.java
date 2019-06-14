/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import reforms.jpa.Aseguradora;
import reforms.jpa.Cliente;
import reforms.jpa.Contacto;
import reforms.jpa.Evento;
import reforms.jpa.Grupo;
import reforms.jpa.Llamada;
import reforms.jpa.Operador;
import reforms.jpa.Perito;
import reforms.jpa.Siniestro;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("llamada")
public class LlamadaFacadeREST extends AbstractFacade<Llamada> {

    @EJB
    private GrupoFacadeREST grupoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public LlamadaFacadeREST() {
        super(Llamada.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Llamada entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Llamada entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Llamada find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("obtenerLlamadas/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> obtenerLlamadas(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Llamada.obtenerLlamadas");
        q.setParameter("idSiniestro", idSiniestro);
        List<Llamada> ll = q.getResultList(),
                      res = new ArrayList<>();
        for (Llamada l : ll) {
            Llamada aux = new Llamada();
            aux.setId(l.getId());
            aux.setTipo(l.getTipo());
            Evento e = new Evento();
            e.setId(l.getEvento().getId());
            e.setFecha(l.getEvento().getFecha());
            e.setDescripcion(l.getEvento().getDescripcion());
            Operador o = new Operador();
            o.setId(l.getEvento().getOperador().getId());
            o.setGerente(l.getEvento().getOperador().getGerente());
            Trabajador t = new Trabajador();
            t.setId(l.getEvento().getOperador().getTrabajador().getId());
            t.setNombre(l.getEvento().getOperador().getTrabajador().getNombre());
            t.setApellido1(l.getEvento().getOperador().getTrabajador().getApellido1());
            t.setApellido2(l.getEvento().getOperador().getTrabajador().getApellido2());
            o.setTrabajador(t);
            e.setOperador(o);
            Siniestro s = new Siniestro();
            s.setId(l.getEvento().getSiniestro().getId());
            e.setSiniestro(s);
            aux.setEvento(e);
            if (l.getCliente() != null) {
                Cliente c = new Cliente();
                c.setId(l.getCliente().getId());
                c.setNombre(l.getCliente().getNombre());
                c.setApellido1(l.getCliente().getApellido1());
                c.setApellido2(l.getCliente().getApellido2());
                c.setTelefono1(l.getCliente().getTelefono1());
                c.setTelefono2(l.getCliente().getTelefono2());
                c.setTipo(l.getCliente().getTipo());
                c.setObservaciones(l.getCliente().getObservaciones());
                Aseguradora a = new Aseguradora();
                a.setId(l.getCliente().getAseguradora().getId());
                c.setAseguradora(a);
                aux.setCliente(c);
            }
            if (l.getContacto()!= null) {
                Contacto c = new Contacto();
                c.setId(l.getContacto().getId());
                c.setSiniestro(s);
                c.setNombre(l.getContacto().getNombre());
                c.setApellido1(l.getContacto().getApellido1());
                c.setApellido2(l.getContacto().getApellido2());
                c.setTelefono1(l.getContacto().getTelefono1());
                c.setTelefono2(l.getContacto().getTelefono2());
                c.setObservaciones(l.getContacto().getObservaciones());
                aux.setContacto(c);
            }
            if (l.getPerito()!= null) {
                Perito p = new Perito();
                p.setId(l.getPerito().getId());
                p.setNombre(l.getPerito().getNombre());
                p.setApellido1(l.getPerito().getApellido1());
                p.setApellido2(l.getPerito().getApellido2());
                p.setTelefono1(l.getPerito().getTelefono1());
                p.setTelefono2(l.getPerito().getTelefono2());
                Aseguradora a = new Aseguradora();
                a.setId(l.getPerito().getAseguradora().getId());
                p.setAseguradora(a);
                aux.setPerito(p);
            }
            if (l.getGrupo()!= null) {
                Grupo g = new Grupo();
                g.setId(l.getGrupo().getId());
                String texto = l.getGrupo().getJornada().getFecha().toString().subSequence(0, l.getGrupo().getJornada().getFecha().toString().indexOf('T')).toString();
                texto = "[Jornada " + texto + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
                g.setObservaciones(texto);
                aux.setGrupo(g);
            }
            res.add(aux);
        }
        return res;
    }
    
    private Evento normalizar_evento(Evento e) {
        Evento eaux = new Evento();
        eaux.setId(e.getId());
        eaux.setFecha(e.getFecha());
        eaux.setDescripcion(e.getDescripcion());
        Operador o = new Operador();
        o.setId(e.getOperador().getId());
        o.setGerente(e.getOperador().getGerente());
        Trabajador t = new Trabajador();
        t.setId(e.getOperador().getTrabajador().getId());
        t.setNombre(e.getOperador().getTrabajador().getNombre());
        t.setApellido1(e.getOperador().getTrabajador().getApellido1());
        t.setApellido2(e.getOperador().getTrabajador().getApellido2());
        o.setTrabajador(t);
        eaux.setOperador(o);
        Siniestro s = new Siniestro();
        s.setId(e.getSiniestro().getId());
        eaux.setSiniestro(s);
        return eaux;
    }
    
    private Llamada normalizar_llamada(Llamada l) {
        Llamada laux = new Llamada();
        laux.setId(l.getId());
        laux.setTipo(l.getTipo());
        laux.setEvento(normalizar_evento(l.getEvento()));
        if (l.getCliente() != null) {
            Cliente c = new Cliente();
            c.setId(l.getCliente().getId());
            c.setNombre(l.getCliente().getNombre());
            c.setApellido1(l.getCliente().getApellido1());
            c.setApellido2(l.getCliente().getApellido2());
            c.setTelefono1(l.getCliente().getTelefono1());
            c.setTelefono2(l.getCliente().getTelefono2());
            c.setTipo(l.getCliente().getTipo());
            c.setObservaciones(l.getCliente().getObservaciones());
            Aseguradora a = new Aseguradora();
            a.setId(l.getCliente().getAseguradora().getId());
            c.setAseguradora(a);
            laux.setCliente(c);
        }
        if (l.getContacto() != null) {
            Contacto c = new Contacto();
            c.setId(l.getContacto().getId());
            Siniestro s = new Siniestro();
            s.setId(l.getEvento().getSiniestro().getId());
            c.setSiniestro(s);
            c.setNombre(l.getContacto().getNombre());
            c.setApellido1(l.getContacto().getApellido1());
            c.setApellido2(l.getContacto().getApellido2());
            c.setTelefono1(l.getContacto().getTelefono1());
            c.setTelefono2(l.getContacto().getTelefono2());
            c.setObservaciones(l.getContacto().getObservaciones());
            laux.setContacto(c);
        }
        if (l.getPerito() != null) {
            Perito p = new Perito();
            p.setId(l.getPerito().getId());
            p.setNombre(l.getPerito().getNombre());
            p.setApellido1(l.getPerito().getApellido1());
            p.setApellido2(l.getPerito().getApellido2());
            p.setTelefono1(l.getPerito().getTelefono1());
            p.setTelefono2(l.getPerito().getTelefono2());
            Aseguradora a = new Aseguradora();
            a.setId(l.getPerito().getAseguradora().getId());
            p.setAseguradora(a);
            laux.setPerito(p);
        }
        if (l.getGrupo() != null) {
            Grupo g = new Grupo();
            g.setId(l.getGrupo().getId());
            String texto = "[" + l.getGrupo().getJornada().getFecha().getTime() + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
            g.setObservaciones(texto);
            laux.setGrupo(g);
        }
        return laux;
    }
    
    @GET
    @Path("obtenerEventos/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> obtenerEventos(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Evento.obtenerEventos");
        q.setParameter("idSiniestro", idSiniestro);
        List<Evento> le = q.getResultList();
        List<Llamada> res = new ArrayList<>();
        for (Evento e : le) {
            Llamada aux;
            if (e.getLlamadas() != null && !e.getLlamadas().isEmpty()) {
                for (Llamada l : e.getLlamadas()) {
                    res.add(normalizar_llamada(l));
                }
            } else {
                aux = new Llamada();
                aux.setId(-1);
                aux.setEvento(normalizar_evento(e));
                res.add(aux);
            }
        }
        return res;
    }
}
