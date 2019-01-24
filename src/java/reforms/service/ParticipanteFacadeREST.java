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
import reforms.jpa.Multiservicios;
import reforms.jpa.Participante;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("participante")
public class ParticipanteFacadeREST extends AbstractFacade<Participante> {

    @EJB
    private MultiserviciosFacadeREST multiserviciosFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ParticipanteFacadeREST() {
        super(Participante.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Participante entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Participante entity) {
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
    public Participante find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Participante> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Participante> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("agregarParticipante")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarParticipante(Participante entity) {
        if (entity.getMultiservicios().getId() == null) {
            Multiservicios m = entity.getMultiservicios();
            multiserviciosFacadeREST.create(m);
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Integer id = q.getFirstResult();
            m.setId(id);
            entity.setMultiservicios(m);
        }
        super.create(entity);
    }
    
    @GET
    @Path("obtenerParticipantes/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Participante> obtenerParticipantes(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Participante.obtenerParticipantes");
        q.setParameter("idSiniestro", idSiniestro);
        List<Participante> lp = q.getResultList(),
                           res = new ArrayList<>();
        for (Participante p : lp) {
            Participante aux = new Participante();
            aux.setId(p.getId());
            Multiservicios m = new Multiservicios();
            m.setId(p.getMultiservicios().getId());
            m.setNombre(p.getMultiservicios().getNombre());
            m.setEmail(p.getMultiservicios().getEmail());
            m.setTelefono1(p.getMultiservicios().getTelefono1());
            m.setTelefono2(p.getMultiservicios().getTelefono2());
            m.setFax(p.getMultiservicios().getFax());
            aux.setMultiservicios(m);
            Siniestro s = new Siniestro();
            s.setId(p.getSiniestro().getId());
            aux.setSiniestro(s);
            res.add(aux);
        }
        return res;
    }
    
    @DELETE
    @Path("borrarParticipante/{id}")
    public void Participante(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
}
