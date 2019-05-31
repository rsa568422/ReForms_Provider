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
import reforms.jpa.Localidad;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("localidad")
public class LocalidadFacadeREST extends AbstractFacade<Localidad> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public LocalidadFacadeREST() {
        super(Localidad.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Localidad entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Localidad entity) {
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
    public Localidad find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Localidad> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Localidad> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarLocalidadPorCodigoPostal/{cp}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Localidad buscarLocalidadPorCodigoPostal(@PathParam("cp") String cp) {
        Query q = em.createNamedQuery("Localidad.buscarLocalidadPorCodigoPostal");
        q.setParameter("cp", cp);
        List<Localidad> ll = q.getResultList();
        return ll.isEmpty() ? null : ll.get(0);
    }
    
    public List<Localidad> obtenerLocalidadPorGrupo(@PathParam("idGrupo") Integer idGrupo) {
        Query q = em.createNamedQuery("Localidad.obtenerLocalidadPorGrupo");
        q.setParameter("idGrupo", idGrupo);
        List<Localidad> ll = q.getResultList();
        return ll;
    }
}
