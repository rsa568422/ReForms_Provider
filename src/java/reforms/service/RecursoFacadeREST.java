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
import reforms.jpa.Recurso;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("recurso")
public class RecursoFacadeREST extends AbstractFacade<Recurso> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public RecursoFacadeREST() {
        super(Recurso.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Recurso entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Recurso entity) {
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
    public Recurso find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Recurso> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Recurso> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerRecursos/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Recurso> obtenerRecursos(@PathParam("siniestroId") Integer siniestroId) {
        Query q = em.createNamedQuery("Recurso.obtenerRecursos");
        q.setParameter("siniestroId", siniestroId);
        List<Recurso> lr = q.getResultList(),
                      res = new ArrayList<>();
        for (Recurso r : lr) {
            Recurso aux = new Recurso();
            aux.setId(r.getId());
            aux.setTipo(r.getTipo());
            aux.setNombre(r.getNombre());
            aux.setDescripcion(r.getDescripcion());
            aux.setFichero(r.getFichero());
            res.add(aux);
        }
        return res;
    }

    @PUT
    @Path("actualizarRecurso/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarRecurso(@PathParam("id") Integer id, Recurso entity) {
        Recurso r = find(id);
        r.setDescripcion(entity.getDescripcion());
        super.edit(r);
    }
}
