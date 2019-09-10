/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
import java.util.List;
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
import reforms.jpa.Cita;
import reforms.jpa.Evento;
import reforms.jpa.Grupo;
import reforms.jpa.Operador;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("cita")
public class CitaFacadeREST extends AbstractFacade<Cita> {

    @EJB
    private EventoFacadeREST eventoFacadeREST;

    @EJB
    private GrupoFacadeREST grupoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public CitaFacadeREST() {
        super(Cita.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cita entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cita entity) {
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
    public Cita find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cita> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cita> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerCitaPorGrupo/{idGrupo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cita> obtenerCitaPorGrupo(@PathParam("idGrupo") Integer idGrupo) {
        Query q = em.createNamedQuery("Cita.obtenerCitaPorGrupo");
        q.setParameter("idGrupo", idGrupo);
        List<Cita> lc = q.getResultList(),
                        res = new ArrayList<>();
        for (Cita c : lc) {
            Cita aux = new Cita();
            aux.setId(c.getId());
            aux.setHora(c.getHora());
            aux.setMinuto(c.getMinuto());
            Grupo g = new Grupo();
            g.setId(c.getGrupo().getId());
            String texto = "[" + c.getGrupo().getJornada().getFecha().getTime() + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
            g.setObservaciones(texto);
            aux.setGrupo(g);
            aux.setGrupo(g);
            Evento e = new Evento();
            e.setId(c.getEvento().getId());
            Siniestro s = new Siniestro();
            s.setId(c.getEvento().getSiniestro().getId());
            s.setAfectado(c.getEvento().getSiniestro().getAfectado());
            s.setFechaRegistro(c.getEvento().getSiniestro().getFechaRegistro());
            s.setNumero(c.getEvento().getSiniestro().getNumero());
            s.setObservaciones(c.getEvento().getSiniestro().getObservaciones());
            s.setPoliza(c.getEvento().getSiniestro().getPoliza());
            s.setPeritoOriginal(c.getEvento().getSiniestro().getPeritoOriginal());
            e.setSiniestro(s);
            texto = c.getEvento().getSiniestro().getPoliza().getCliente().getAseguradora().getNombre() + "[" + c.getEvento().getSiniestro().getNumero() + "] " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getDireccion() + " " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getNumero();
            if (c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso() != null && !c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso().isEmpty()) {
                texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso();
            }
            texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getNombre() + " [" + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getCp() + "]";
            e.setDescripcion(texto);
            aux.setEvento(e);
            res.add(aux);
        }
        return res;
    }
    
    @GET
    @Path("obtenerCitaPorEvento/{idEvento}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cita> obtenerCitaPorEvento(@PathParam("idEvento") Integer idEvento) {
        Query q = em.createNamedQuery("Cita.obtenerCitaPorEvento");
        q.setParameter("idEvento", idEvento);
        List<Cita> lc = q.getResultList(),
                        res = new ArrayList<>();
        for (Cita c : lc) {
            Cita aux = new Cita();
            aux.setId(c.getId());
            aux.setHora(c.getHora());
            aux.setMinuto(c.getMinuto());
            Grupo g = new Grupo();
            g.setId(c.getGrupo().getId());
            String texto = "[" + c.getGrupo().getJornada().getFecha().getTime() + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
            g.setObservaciones(texto);
            aux.setGrupo(g);
            Evento e = new Evento();
            e.setId(c.getEvento().getId());
            texto = "[" + c.getEvento().getSiniestro().getNumero() + "] " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getDireccion() + " " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getNumero();
            if (c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso() != null && !c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso().isEmpty()) {
                texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso();
            }
            texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getNombre() + " [" + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getCp() + "]";
            e.setDescripcion(texto);
            aux.setEvento(e);
            res.add(aux);
        }
        return res;
    }
    
    @GET
    @Path("obtenerCitas/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cita> obtenerCitas(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Cita.obtenerCitas");
        q.setParameter("idSiniestro", idSiniestro);
        List<Cita> lc = q.getResultList(),
                        res = new ArrayList<>();
        for (Cita c : lc) {
            Cita aux = new Cita();
            aux.setId(c.getId());
            aux.setHora(c.getHora());
            aux.setMinuto(c.getMinuto());
            Grupo g = new Grupo();
            g.setId(c.getGrupo().getId());
            String texto = "[" + c.getGrupo().getJornada().getFecha().getTime() + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
            g.setObservaciones(texto);
            aux.setGrupo(g);
            Evento e = new Evento();
            e.setId(c.getEvento().getId());
            texto = c.getEvento().getSiniestro().getPoliza().getCliente().getAseguradora().getNombre() + "[" + c.getEvento().getSiniestro().getNumero() + "] " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getDireccion() + " " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getNumero();
            if (c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso() != null && !c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso().isEmpty()) {
                texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getPiso();
            }
            texto += ", " + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getNombre() + " [" + c.getEvento().getSiniestro().getPoliza().getPropiedad().getLocalidad().getCp() + "]";
            e.setDescripcion(texto);
            aux.setEvento(e);
            res.add(aux);
        }
        return res;
    }
    
    private boolean test_info_cita(Cita c) {
        return c != null && c.getHora() >= 0 && c.getHora() < 24 && c.getMinuto() >= 0 && c.getMinuto() < 60 && c.getEvento() != null && c.getEvento().getId() != null && c.getGrupo() != null && c.getGrupo().getId() != null;
    }
    
    @POST
    @Path("agregarCita")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cita agregarCita(Cita entity) {
        System.out.println("test_info_cita(entity) = " + test_info_cita(entity));
        if (test_info_cita(entity)) {
            Evento eaux = entity.getEvento(),
                   e = eventoFacadeREST.find(eaux.getId());
            Grupo gaux = entity.getGrupo(),
                  g = grupoFacadeREST.find(gaux.getId());
            if (e != null && g != null) {
                entity.setEvento(e);
                entity.setGrupo(g);
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
                /*e.getCitas().add(entity);
                eventoFacadeREST.edit(e);
                g.getCitas().add(entity);
                grupoFacadeREST.edit(g);*/
                entity.setEvento(eaux);
                entity.setGrupo(gaux);
            } else {
                entity = null;
            }
        } else {
            entity = null;
        }
        return entity;
    }
}
