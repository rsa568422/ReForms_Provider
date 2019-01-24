/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
import java.util.List;
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
import reforms.jpa.Perito;
import reforms.jpa.Reasignacion;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("reasignacion")
public class ReasignacionFacadeREST extends AbstractFacade<Reasignacion> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ReasignacionFacadeREST() {
        super(Reasignacion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Reasignacion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Reasignacion entity) {
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
    public Reasignacion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Reasignacion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Reasignacion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerUltimaReasignacion/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Reasignacion obtenerUltimaReasignacion(@PathParam("siniestroId") Integer siniestroId) {
        Query q = em.createNamedQuery("Reasignacion.obtenerReasignaciones");
        q.setParameter("siniestroId", siniestroId);
        q.setMaxResults(1);
        List<Reasignacion> lr = q.getResultList();
        Reasignacion r, res = null;
        if (!lr.isEmpty()) {
            r = lr.get(0);
            res = new Reasignacion();
            res.setId(r.getId());
            res.setFecha(r.getFecha());
            Perito p = new Perito();
            p.setId(r.getPerito().getId());
            p.setNombre(r.getPerito().getNombre());
            p.setApellido1(r.getPerito().getApellido1());
            p.setApellido2(r.getPerito().getApellido2());
            res.setPerito(p);
            Siniestro s = new Siniestro();
            s.setId(r.getSiniestro().getId());
            res.setSiniestro(s);
        }
        return res;
    }
    
    @GET
    @Path("obtenerReasignaciones/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Reasignacion> obtenerReasignaciones(@PathParam("siniestroId") Integer siniestroId) {
        Query q = em.createNamedQuery("Reasignacion.obtenerReasignaciones");
        q.setParameter("siniestroId", siniestroId);
        List<Reasignacion> lr = q.getResultList(),
                           res = new ArrayList<>();
        for (Reasignacion r : lr) {
            Reasignacion aux = new Reasignacion();
            aux.setId(r.getId());
            aux.setFecha(r.getFecha());
            Perito p = new Perito();
            p.setId(r.getPerito().getId());
            p.setNombre(r.getPerito().getNombre());
            p.setApellido1(r.getPerito().getApellido1());
            p.setApellido2(r.getPerito().getApellido2());
            aux.setPerito(p);
            Siniestro s = new Siniestro();
            s.setId(r.getSiniestro().getId());
            aux.setSiniestro(s);
            res.add(aux);
        }
        return res;
    }
}
