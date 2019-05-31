/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
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
import reforms.jpa.Conductor;
import reforms.jpa.Grupo;
import reforms.jpa.Operario;
import reforms.jpa.Vehiculo;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("conductor")
public class ConductorFacadeREST extends AbstractFacade<Conductor> {

    @EJB
    private GrupoFacadeREST grupoFacadeREST;

    @EJB
    private OperarioFacadeREST operarioFacadeREST;

    @EJB
    private VehiculoFacadeREST vehiculoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ConductorFacadeREST() {
        super(Conductor.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Conductor entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Conductor entity) {
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
    public Conductor find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Conductor> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Conductor> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerConductorPorGrupo/{idGrupo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Conductor> obtenerConductorPorGrupo(@PathParam("idGrupo") Integer idGrupo) {
        Query q = em.createNamedQuery("Conductor.obtenerConductorPorGrupo");
        q.setParameter("idGrupo", idGrupo);
        List<Conductor> lc = q.getResultList(),
                        res = new ArrayList<>();
        if (!lc.isEmpty()) {
            Conductor aux = new Conductor();
            aux.setId(lc.get(0).getId());
            Grupo g = new Grupo();
            g.setId(lc.get(0).getGrupo().getId());
            aux.setGrupo(g);
            Operario o = new Operario();
            o.setId(lc.get(0).getConductor().getId());
            aux.setConductor(o);
            Vehiculo v = new Vehiculo();
            v.setId(lc.get(0).getVehiculo().getId());
            v.setMatricula(lc.get(0).getVehiculo().getMatricula());
            v.setMarca(lc.get(0).getVehiculo().getMarca());
            v.setModelo(lc.get(0).getVehiculo().getModelo());
            aux.setVehiculo(v);
            res.add(aux);
        }
        return res;
    }
    
    @POST
    @Path("agregarConductor")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Conductor agregarConductor(Conductor entity) {
        if (entity.getGrupo() != null && entity.getGrupo().getId() != null && entity.getConductor()!= null && entity.getConductor().getId() != null && entity.getVehiculo()!= null && entity.getVehiculo().getId() != null) {
            Grupo gaux = entity.getGrupo(),
                  g = grupoFacadeREST.find(entity.getGrupo().getId());
            Operario oaux = entity.getConductor(),
                     o = operarioFacadeREST.find(entity.getConductor().getId());
            Vehiculo vaux = entity.getVehiculo(),
                     v = vehiculoFacadeREST.find(entity.getVehiculo().getId());
            if (g != null && o != null && v != null) {
                entity.setGrupo(g);
                entity.setConductor(o);
                entity.setVehiculo(v);
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
                entity.setGrupo(gaux);
                entity.setConductor(oaux);
                entity.setVehiculo(vaux);
            } else {
                entity = null;
            }
        } else {
            entity = null;
        }
        return entity;
    }
}
