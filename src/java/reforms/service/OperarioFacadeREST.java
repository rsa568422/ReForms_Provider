/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

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
import reforms.jpa.Operario;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("operario")
public class OperarioFacadeREST extends AbstractFacade<Operario> {

    @EJB
    private TrabajadorFacadeREST trabajadorFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public OperarioFacadeREST() {
        super(Operario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Operario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Operario entity) {
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
    public Operario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Operario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Operario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarOperarioPorTrabajador/{trabajadorId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Operario buscarOperarioPorTrabajador(@PathParam("trabajadorId") Integer trabajadorId) {
        Query q = em.createNamedQuery("Operario.buscarOperarioPorTrabajador");
        q.setParameter("trabajadorId", trabajadorId);
        List<Operario> lo = q.getResultList();
        Operario o = lo.size() > 0 ? lo.get(0) : null;
        if (o != null) {
            Trabajador t = new Trabajador();
            t.setId(o.getTrabajador().getId());
            o.setTrabajador(t);
            o.setCapacidades(null);
            o.setConductores(null);
            o.setIntegrantes(null);
            o.setLocalidadesvisitadas(null);
        }
        return o;
    }
    
    @POST
    @Path("agregarOperario")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarOperario(Operario entity) {
        entity.setTrabajador(trabajadorFacadeREST.find(entity.getTrabajador().getId()));
        super.create(entity);
    }
}
