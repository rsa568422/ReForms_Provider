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
import reforms.jpa.Adjunto;
import reforms.jpa.Recurso;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("adjunto")
public class AdjuntoFacadeREST extends AbstractFacade<Adjunto> {

    @EJB
    private RecursoFacadeREST recursoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public AdjuntoFacadeREST() {
        super(Adjunto.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Adjunto entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Adjunto entity) {
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
    public Adjunto find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adjunto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adjunto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @POST
    @Path("agregarAdjunto")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarAdjunto(Adjunto entity) {
        if (entity.getRecurso().getId() == null) {
            Recurso r = entity.getRecurso();
            String nombre = r.getNombre(),
                   extension = nombre;
            while (extension.indexOf('.') != -1) {
                extension = extension.substring(extension.indexOf('.') + 1, extension.length());
            }
            if (extension.toLowerCase().equals("pdf")) {
                r.setTipo(0);
            } else if (extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg")) {
                r.setTipo(1);
            } else {
                r.setTipo(2);
            }
            recursoFacadeREST.create(r);
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Integer id = q.getFirstResult();
            r.setId(id);
            entity.setRecurso(r);
        }
        super.create(entity);
    }
    
    @DELETE
    @Path("borrarAdjunto/{idRecurso}")
    public void Participante(@PathParam("idRecurso") Integer idRecurso) {
        Recurso r = recursoFacadeREST.find(idRecurso);
        Query q = em.createNamedQuery("Adjunto.buscarAdjuntoPorRecurso");
        q.setParameter("idRecurso", idRecurso);
        List<Adjunto> la = q.getResultList();
        if (la.size() > 0 && la.size() < 2) {
            super.remove(la.get(0));
            recursoFacadeREST.remove(idRecurso);
        } else {
            System.err.println("Hay mas de un adjunto asociado al mismo recurso");
        }
    }
}
