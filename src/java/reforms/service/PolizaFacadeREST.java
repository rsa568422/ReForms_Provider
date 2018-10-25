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
import reforms.jpa.Poliza;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("poliza")
public class PolizaFacadeREST extends AbstractFacade<Poliza> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public PolizaFacadeREST() {
        super(Poliza.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Poliza entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Poliza entity) {
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
    public Poliza find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poliza> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poliza> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarPolizaPorNumeroPoliza/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poliza> buscarPolizaPorNumeroPoliza(@PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Poliza.buscarPolizaPorNumeroPoliza");
        q.setParameter("numero", numero);
        List<Poliza> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }
    
    @GET
    @Path("buscarPolizaPorNumeroPolizaA/{aseguradoraId}/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Poliza buscarPolizaPorNumeroPolizaA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Poliza.buscarPolizaPorNumeroPolizaA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        List<Poliza> lp = q.getResultList();
        return lp.isEmpty() ? null : lp.get(0);
    }
    
    @GET
    @Path("buscarPolizaPorCliente/{clienteId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poliza> buscarPolizaPorCliente(@PathParam("clienteId") Integer clienteId) {
        Query q = em.createNamedQuery("Poliza.buscarPolizaPorCliente");
        q.setParameter("clienteId", clienteId);
        List<Poliza> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }
    
    @GET
    @Path("buscarPolizaPorPropiedad/{propiedadId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poliza> buscarPolizaPorPropiedad(@PathParam("propiedadId") Integer propiedadId) {
        Query q = em.createNamedQuery("Poliza.buscarPolizaPorPropiedad");
        q.setParameter("propiedadId", propiedadId);
        List<Poliza> lp = q.getResultList();
        return lp.isEmpty() ? null : lp;
    }
}
