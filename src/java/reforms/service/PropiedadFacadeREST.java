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
import reforms.jpa.Propiedad;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("propiedad")
public class PropiedadFacadeREST extends AbstractFacade<Propiedad> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public PropiedadFacadeREST() {
        super(Propiedad.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Propiedad entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Propiedad entity) {
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
    public Propiedad find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Propiedad> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Propiedad> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarPropiedadPorDireccionCompleta/{cp}/{direccion}/{numero}/{piso:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Propiedad> buscarPropiedadPorDireccionCompleta(@PathParam("cp") String cp, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q = em.createNamedQuery("Propiedad.buscarPropiedadPorDireccionCompleta");
        q.setParameter("cp", cp);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", piso);
        List<Propiedad> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }
    
    @GET
    @Path("buscarPropiedadPorDireccionCompletaA/{aseguradoraId}/{cp}/{direccion}/{numero}/{piso:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Propiedad> buscarPropiedadPorDireccionCompletaA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("cp") String cp, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q = em.createNamedQuery("Propiedad.buscarPropiedadPorDireccionCompletaA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("cp", cp);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", piso);
        List<Propiedad> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }

    @POST
    @Path("registrarPropiedad")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void registrarPropiedad(Propiedad entity) {
        super.create(entity);
    }
}