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
import reforms.jpa.Evento;
import reforms.jpa.Operador;
import reforms.jpa.Siniestro;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("evento")
public class EventoFacadeREST extends AbstractFacade<Evento> {

    @EJB
    private SiniestroFacadeREST siniestroFacadeREST;

    @EJB
    private OperadorFacadeREST operadorFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public EventoFacadeREST() {
        super(Evento.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Evento entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Evento entity) {
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
    public Evento find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("obtenerEventos/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> obtenerEventos(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Evento.obtenerEventos");
        q.setParameter("idSiniestro", idSiniestro);
        List<Evento> le = q.getResultList(),
                      res = new ArrayList<>();
        for (Evento e : le) {
            res.add(normalizar_evento(e));
        }
        return res;
    }
    
    private boolean test_info_evento(Evento e) {
        boolean test = e.getFecha() != null && e.getSiniestro()!= null && e.getSiniestro().getId() != null && e.getOperador() != null && e.getOperador().getId() != null;
        if (test) {
            Siniestro s = siniestroFacadeREST.find(e.getSiniestro().getId());
            Operador o = operadorFacadeREST.find(e.getOperador().getId());
            test = s != null && o != null;
        }
        return test;
    }
    
    @POST
    @Path("agregarEvento")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Evento agregarEvento(Evento entity) {
        if (test_info_evento(entity)) {
            Siniestro s = siniestroFacadeREST.find(entity.getSiniestro().getId());
            Operador o = operadorFacadeREST.find(entity.getOperador().getId());
            if (s != null && o != null) {
                entity.setSiniestro(s);
                entity.setOperador(o);
                if (entity.getDescripcion() != null && entity.getDescripcion().isEmpty()) {
                    entity.setDescripcion(null);
                }
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
                /*s.getEventos().add(entity);
                siniestroFacadeREST.edit(s);
                o.getEventos().add(entity);
                operadorFacadeREST.edit(o);*/
            }
        }
        //return normalizar_evento(entity);
        return entity;
    }
}
