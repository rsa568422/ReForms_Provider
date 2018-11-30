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
import reforms.jpa.Nomina;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("nomina")
public class NominaFacadeREST extends AbstractFacade<Nomina> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public NominaFacadeREST() {
        super(Nomina.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Nomina entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Nomina entity) {
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
    public Nomina find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Nomina> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Nomina> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarNominaPorTrabajador/{trabajadorId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Nomina> buscarNominaPorTrabajador(@PathParam("trabajadorId") Integer trabajadorId) {
        Query q = em.createNamedQuery("Nomina.buscarNominaPorTrabajador");
        q.setParameter("trabajadorId", trabajadorId);
        List<Nomina> ln = q.getResultList();
        for (Nomina n : ln) {
            Trabajador t = new Trabajador();
            t.setId(n.getTrabajador().getId());
            n.setTrabajador(t);
        }
        return ln.size() > 0 ? ln : null;
    }
    
    @POST
    @Path("agregarNomina")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarNomina(Nomina entity) {
        super.create(entity);
    }
}
