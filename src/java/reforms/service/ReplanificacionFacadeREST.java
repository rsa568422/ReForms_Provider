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
import reforms.jpa.Replanificacion;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("replanificacion")
public class ReplanificacionFacadeREST extends AbstractFacade<Replanificacion> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ReplanificacionFacadeREST() {
        super(Replanificacion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Replanificacion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Replanificacion entity) {
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
    public Replanificacion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Replanificacion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Replanificacion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerReplanificaciones/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Replanificacion> obtenerReplanificaciones(@PathParam("siniestroId") Integer siniestroId) {
        Query q = em.createNamedQuery("Replanificacion.obtenerReplanificaciones");
        q.setParameter("siniestroId", siniestroId);
        List<Replanificacion> lr = q.getResultList(),
                              res = new ArrayList<>();
        for (Replanificacion r : lr) {
            Replanificacion aux = new Replanificacion();
            Siniestro s = new Siniestro();
            s.setId(r.getSiniestro().getId());
            aux.setId(r.getId());
            aux.setFecha(r.getFecha());
            aux.setSiniestro(s);
            res.add(aux);
        }
        return res;
    }
}
