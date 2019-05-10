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

    @EJB
    private SiniestroFacadeREST siniestroFacadeREST;

    @EJB
    private TrabajoFacadeREST trabajoFacadeREST;

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
        Siniestro siniestro = siniestroFacadeREST.find(idSiniestro);
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
            taux.setId(t.getTrabajo().getId());
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
            if (siniestro.getEstado() < 5 && taux.getCantidadMin()!=  null) {
                if (c <= taux.getCantidadMin()) {
                    aux.setImporte(taux.getPrecioMin());
                } else if (c <= taux.getCantidadMed()) {
                    aux.setImporte(taux.getPrecioMed());
                } else {
                    Float caux = c - taux.getCantidadMed();
                    aux.setImporte(taux.getPrecioMed() + (caux * taux.getPrecioExtra()));
                }
            } else {
                aux.setImporte(t.getImporte());
            }
            aux.setFechaAmpliacion(t.getFechaAmpliacion());
            res.add(aux);
        }
        return res;
    }
    
    @POST
    @Path("agregarTarea")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarTarea(Tarea entity) {
        Tarea nueva = new Tarea();
        nueva.setSiniestro(siniestroFacadeREST.find(entity.getSiniestro().getId()));
        nueva.setTrabajo(trabajoFacadeREST.find(entity.getTrabajo().getId()));
        nueva.setCantidad(entity.getCantidad());
        nueva.setObservaciones(entity.getObservaciones());
        if (nueva.getCantidad() > 0 && nueva.getSiniestro().getEstado() < 4) {
            if (nueva.getSiniestro().getEstado() > 1) {
                nueva.getSiniestro().setEstado(1);
                siniestroFacadeREST.edit(nueva.getSiniestro());
            }
            if (nueva.getTrabajo().getCantidadMin() == null) {
                nueva.setImporte(entity.getImporte());
            }
            super.create(entity);
        }
    }

    @DELETE
    @Path("borrarTarea/{id}")
    public void borrarTarea(@PathParam("id") Integer id) {
        Tarea t = super.find(id);
        if (t != null && t.getSiniestro() != null && t.getSiniestro().getEstado() < 4) {
            Siniestro s = t.getSiniestro();
            if (t.getTareascitas() != null && !t.getTareascitas().isEmpty()) {
                // borrar citas
            }
            if (t.getAmpliacion() != null || (t.getTareas() != null && !t.getTareas().isEmpty())) {
                // borrar ampliaciones
            }
            super.remove(t);
            Integer e = siniestroFacadeREST.calcularEstado(s.getId());
            if (!s.getEstado().equals(e)) {
                s = siniestroFacadeREST.find(t.getSiniestro().getId());
                s.setEstado(e);
                siniestroFacadeREST.edit(s);
            }
        }
    }

    @PUT
    @Path("actualizarTarea/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarTarea(@PathParam("id") Integer id, Tarea entity) {
        Tarea t = super.find(id);
        if (t != null && t.getSiniestro().getEstado() < 4) {
            boolean estado = !t.getEstado().equals(entity.getEstado());
            t.setObservaciones(entity.getObservaciones());
            t.setEstado(entity.getEstado());
            if (t.getTrabajo().getCantidadMin() == null) {
                t.setCantidad(entity.getCantidad());
                t.setImporte(entity.getImporte());
            }
            super.edit(t);
            if (estado) {
                Integer e = siniestroFacadeREST.calcularEstado(t.getSiniestro().getId());
                if (!t.getSiniestro().getEstado().equals(e)) {
                    Siniestro s = siniestroFacadeREST.find(t.getSiniestro().getId());
                    s.setEstado(e);
                    siniestroFacadeREST.edit(s);
                }
            }
        }
    }
}
