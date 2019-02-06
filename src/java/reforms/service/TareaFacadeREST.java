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
import reforms.jpa.Siniestro;
import reforms.jpa.Tarea;
import reforms.jpa.Trabajo;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("tarea")
public class TareaFacadeREST extends AbstractFacade<Tarea> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public TareaFacadeREST() {
        super(Tarea.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Tarea entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Tarea entity) {
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
    public Tarea find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tarea> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tarea> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerTareas/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Tarea> obtenerTareas(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Tarea.obtenerTareas");
        q.setParameter("idSiniestro", idSiniestro);
        List<Tarea> lt = q.getResultList(),
                    res = new ArrayList<>();
        for (Tarea t : lt) {
            Tarea aux = new Tarea();
            aux.setId(t.getId());
            Siniestro s = new Siniestro();
            s.setId(t.getSiniestro().getId());
            aux.setSiniestro(s);
            aux.setEstado(t.getEstado());
            aux.setObservaciones(t.getObservaciones());
            Trabajo taux = new Trabajo();
            Aseguradora aaux = new Aseguradora();
            aaux.setId(t.getTrabajo().getAseguradora().getId());
            taux.setAseguradora(aaux);
            taux.setCantidadMed(t.getTrabajo().getCantidadMed());
            taux.setCantidadMin(t.getTrabajo().getCantidadMin());
            taux.setPrecioMed(t.getTrabajo().getPrecioMed());
            taux.setPrecioMin(t.getTrabajo().getPrecioMin());
            taux.setPrecioExtra(t.getTrabajo().getPrecioExtra());
            taux.setCodigo(t.getTrabajo().getCodigo());
            taux.setDescripcion(t.getTrabajo().getDescripcion());
            taux.setDificultad(t.getTrabajo().getDificultad());
            taux.setGremio(t.getTrabajo().getGremio());
            taux.setMedida(t.getTrabajo().getMedida());
            aux.setTrabajo(taux);
            Integer c = t.getCantidad();
            aux.setCantidad(c);
            if (t.getImporte() == null || t.getImporte().equals(new Float(0.0))) {
                if (c <= taux.getCantidadMin()) {
                    aux.setImporte(taux.getPrecioMin());
                } else if (c <= taux.getCantidadMed()) {
                    Float caux = c - taux.getCantidadMin();
                    aux.setImporte(taux.getPrecioMin() + (caux * taux.getPrecioMed()));
                } else {
                    Float caux1 = taux.getCantidadMed() - taux.getCantidadMin(),
                          caux2 = c - taux.getCantidadMed();
                    aux.setImporte(taux.getPrecioMin() + (caux1 * taux.getPrecioMed()) + (caux2 * taux.getPrecioExtra()));
                }
            } else {
                aux.setImporte(t.getImporte());
            }
            aux.setFechaAmpliacion(t.getFechaAmpliacion());
            res.add(aux);
        }
        return res;
    }
}
