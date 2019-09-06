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
import reforms.jpa.Cita;
import reforms.jpa.Grupo;
import reforms.jpa.Tarea;
import reforms.jpa.Tareascita;
import reforms.jpa.Trabajo;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("tareascita")
public class TareascitaFacadeREST extends AbstractFacade<Tareascita> {

    @EJB
    private SiniestroFacadeREST siniestroFacadeREST;

    @EJB
    private CitaFacadeREST citaFacadeREST;

    @EJB
    private TareaFacadeREST tareaFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public TareascitaFacadeREST() {
        super(Tareascita.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Tareascita entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Tareascita entity) {
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
    public Tareascita find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tareascita> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tareascita> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerTareasPorCita/{idCita}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tareascita> obtenerTareasPorCita(@PathParam("idCita") Integer idCita) {
        Query q = em.createNamedQuery("Tareascita.obtenerTareasPorCita");
        q.setParameter("idCita", idCita);
        List<Tareascita> ltc = q.getResultList(),
                         res = new ArrayList<>();
        for (Tareascita tc : ltc) {
            Tareascita aux = new Tareascita();
            aux.setId(tc.getId());
            Tarea t = new Tarea();
            t.setId(tc.getTarea().getId());
            t.setCantidad(tc.getTarea().getCantidad());
            Trabajo tr = new Trabajo();
            tr.setId(tc.getTarea().getTrabajo().getId());
            tr.setCodigo(tc.getTarea().getTrabajo().getCodigo());
            tr.setMedida(tc.getTarea().getTrabajo().getMedida());
            tr.setDescripcion(tc.getTarea().getTrabajo().getDescripcion());
            t.setTrabajo(tr);
            aux.setTarea(t);
            Cita c = new Cita();
            c.setId(tc.getCita().getId());
            c.setHora(tc.getCita().getHora());
            c.setMinuto(tc.getCita().getMinuto());
            Grupo g = new Grupo();
            g.setId(tc.getCita().getGrupo().getId());
            c.setGrupo(g);
            aux.setCita(c);
            res.add(aux);
        }
        return res;
    }
    
    @POST
    @Path("agregarTarea")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Tareascita agregarTarea(Tareascita entity) {
        if (entity.getCita()!= null && entity.getCita().getId() != null && entity.getTarea() != null && entity.getTarea().getId() != null) {
            Cita caux = entity.getCita(),
                 c = citaFacadeREST.find(entity.getCita().getId());
            Tarea taux = entity.getTarea(),
                  t = tareaFacadeREST.find(entity.getTarea().getId());
            if (c != null && t!= null) {
                entity.setCita(c);
                entity.setTarea(t);
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
                
                c.getTareascitas().add(entity);
                citaFacadeREST.edit(c);
                t.getTareascitas().add(entity);
                if (t.getEstado() == 0) {
                    t.setEstado(1);
                    if (t.getSiniestro().getEstado() == 0) {
                        t.getSiniestro().setEstado(1);
                        siniestroFacadeREST.edit(t.getSiniestro());
                    }
                }
                tareaFacadeREST.edit(t);
                entity.setCita(caux);
                entity.setTarea(taux);
            } else {
                entity = null;
            }
        } else {
            entity = null;
        }
        return entity;
    }
}
