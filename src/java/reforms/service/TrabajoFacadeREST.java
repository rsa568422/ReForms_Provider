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
import reforms.jpa.Aseguradora;
import reforms.jpa.Gremio;
import reforms.jpa.Trabajo;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("trabajo")
public class TrabajoFacadeREST extends AbstractFacade<Trabajo> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public TrabajoFacadeREST() {
        super(Trabajo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Trabajo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Trabajo entity) {
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
    public Trabajo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerTrabajosPorGremio/{idAseguradora}/{idGremio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajo> obtenerTrabajosPorGremio(@PathParam("idAseguradora") Integer idAseguradora, @PathParam("idGremio") Integer idGremio) {
        Query q = em.createNamedQuery("Trabajo.obtenerTrabajosPorGremio");
        q.setParameter("idAseguradora", idAseguradora);
        q.setParameter("idGremio", idGremio);
        List<Trabajo> lt = q.getResultList(),
                      res = new ArrayList<>();
        for (Trabajo t : lt) {
            Trabajo aux = new Trabajo();
            aux.setId(t.getId());
            aux.setCodigo(t.getCodigo());
            aux.setCantidadMed(t.getCantidadMed());
            aux.setPrecioMed(t.getPrecioMed());
            aux.setCantidadMin(t.getCantidadMin());
            aux.setPrecioMin(t.getPrecioMin());
            aux.setPrecioExtra(t.getPrecioExtra());
            aux.setDificultad(t.getDificultad());
            aux.setDescripcion(t.getDescripcion());
            aux.setMedida(t.getMedida());
            Aseguradora aaux = new Aseguradora();
            aaux.setId(t.getAseguradora().getId());
            aux.setAseguradora(aaux);
            Gremio gaux = new Gremio();
            gaux.setId(t.getGremio().getId());
            aux.setGremio(gaux);
            res.add(aux);
        }
        return res;
    }
    
    @POST
    @Path("agregarTrabajo")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarTrabajo(Trabajo entity) {
        super.create(entity);
    }
}
