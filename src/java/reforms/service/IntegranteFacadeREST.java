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
import reforms.jpa.Grupo;
import reforms.jpa.Integrante;
import reforms.jpa.Operario;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("integrante")
public class IntegranteFacadeREST extends AbstractFacade<Integrante> {

    @EJB
    private GrupoFacadeREST grupoFacadeREST;

    @EJB
    private OperarioFacadeREST operarioFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public IntegranteFacadeREST() {
        super(Integrante.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Integrante entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Integrante entity) {
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
    public Integrante find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Integrante> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Integrante> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerIntegrantePorGrupo/{idGrupo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Integrante> obtenerIntegrantePorGrupo(@PathParam("idGrupo") Integer idGrupo) {
        Query q = em.createNamedQuery("Integrante.obtenerIntegrantePorGrupo");
        q.setParameter("idGrupo", idGrupo);
        List<Integrante> li = q.getResultList(),
                         res = new ArrayList<>();
        for (Integrante i : li) {
            Integrante aux = new Integrante();
            aux.setId(i.getId());
            Grupo g = new Grupo();
            g.setId(i.getGrupo().getId());
            aux.setGrupo(g);
            Operario o = new Operario();
            o.setId(i.getOperario().getId());
            o.setCarnet(i.getOperario().getCarnet());
            Trabajador t = new Trabajador();
            t.setId(i.getOperario().getTrabajador().getId());
            t.setNombre(i.getOperario().getTrabajador().getNombre());
            t.setApellido1(i.getOperario().getTrabajador().getApellido1());
            t.setApellido2(i.getOperario().getTrabajador().getApellido2());
            o.setTrabajador(t);
            aux.setOperario(o);
            res.add(aux);
        }
        return res;
    }
    
    @POST
    @Path("agregarIntegrante")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Integrante agregarIntegrante(Integrante entity) {
        if (entity.getGrupo() != null && entity.getGrupo().getId() != null && entity.getOperario()!= null && entity.getOperario().getId() != null) {
            Grupo gaux = entity.getGrupo(),
                  g = grupoFacadeREST.find(entity.getGrupo().getId());
            Operario oaux = entity.getOperario(),
                     o = operarioFacadeREST.find(entity.getOperario().getId());
            if (g != null && o!= null) {
                entity.setGrupo(g);
                entity.setOperario(o);
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
                g.getIntegrantes().add(entity);
                grupoFacadeREST.edit(g);
                o.getIntegrantes().add(entity);
                operarioFacadeREST.edit(o);
                entity.setGrupo(gaux);
                entity.setOperario(oaux);
            } else {
                entity = null;
            }
        } else {
            entity = null;
        }
        return entity;
    }
}
