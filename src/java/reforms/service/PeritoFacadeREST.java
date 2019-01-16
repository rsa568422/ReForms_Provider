/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

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

/**
 *
 * @author Roberto
 */
@Stateless
@Path("perito")
public class PeritoFacadeREST extends AbstractFacade<Perito> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public PeritoFacadeREST() {
        super(Perito.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Perito entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Perito entity) {
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
    public Perito find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Perito> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Perito> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarPeritoPorAseguradora/{aseguradoraId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Perito> buscarPeritoPorAseguradora(@PathParam("aseguradoraId") Integer aseguradoraId) {
        Query q = em.createNamedQuery("Perito.buscarPeritoPorAseguradora");
        q.setParameter("aseguradoraId", aseguradoraId);
        List<Perito> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }
    
    @POST
    @Path("agregarPerito")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarPerito(Perito entity) {
        super.create(entity);
    }
    
    @GET
    @Path("buscarPeritoReasignadoPorSiniestro/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Perito buscarPeritoReasignadoPorSiniestro(@PathParam("siniestroId") Integer siniestroId) {
        Query q = em.createNamedQuery("Perito.buscarPeritoReasignadoPorSiniestro");
        q.setParameter("siniestroId", siniestroId);
        q.setMaxResults(1);
        List<Perito> lp = q.getResultList();
        return lp.isEmpty() ? null : lp.get(0);
    }
}
