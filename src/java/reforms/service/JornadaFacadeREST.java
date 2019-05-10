/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.Calendar;
import java.util.Date;
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
import reforms.jpa.Jornada;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("jornada")
public class JornadaFacadeREST extends AbstractFacade<Jornada> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public JornadaFacadeREST() {
        super(Jornada.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Jornada entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Jornada entity) {
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
    public Jornada find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Jornada> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Jornada> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("infoMes/{a}/{m}")
    @Produces(MediaType.TEXT_PLAIN)
    public String infoMes(@PathParam("a") Integer a, @PathParam("m") Integer m) {
        String salida = "0/0";
        if ((m > 0) && (m < 13)) {
            Calendar fecha = Calendar.getInstance();
            fecha.set(a, m - 1 , 1);
            int inicio = fecha.get(Calendar.DAY_OF_WEEK) == 1 ? 6 : fecha.get(Calendar.DAY_OF_WEEK) - 2;
            salida = String.valueOf(inicio) + "/" + String.valueOf(fecha.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return salida;
    }
    
    @GET
    @Path("contarJornadaPorMes/{a}/{m}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarJornadaPorMes(@PathParam("a") Integer a, @PathParam("m") Integer m) {
        Query q;
        q = em.createNamedQuery("Jornada.contarJornadaPorMes");
        Calendar inicio = Calendar.getInstance(),
                 fin = Calendar.getInstance();
        inicio.set(a, m - 1 , 1);
        fin.set(a, m - 1 , inicio.getActualMaximum(Calendar.DAY_OF_MONTH));
        q.setParameter("inicio", new Date(inicio.getTimeInMillis()));
        q.setParameter("fin", new Date(fin.getTimeInMillis()));
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarJornadaPorMes/{a}/{m}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Jornada> buscarJornadaPorMes(@PathParam("pagina") Integer pagina, @PathParam("a") Integer a, @PathParam("m") Integer m) {
        Query q;
        q = em.createNamedQuery("Jornada.buscarJornadaPorMes");
        Calendar inicio = Calendar.getInstance(),
                 fin = Calendar.getInstance();
        inicio.set(a, m - 1 , 1);
        fin.set(a, m - 1 , inicio.getActualMaximum(Calendar.DAY_OF_MONTH));
        q.setParameter("inicio", new Date(inicio.getTimeInMillis()));
        q.setParameter("fin", new Date(fin.getTimeInMillis()));
        List<Jornada> lj = q.getResultList();
        return lj.isEmpty() ? null : lj;
    }
    
    @POST
    @Path("registrarJornada")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void Jornada(Jornada entity) {
        if (entity.getGerente().getGerente() == 1) {
            if (entity.getObservaciones() != null && entity.getObservaciones().isEmpty()) {
                entity.setObservaciones(null);
            }
            super.create(entity);
        }
    }

    @PUT
    @Path("actualizarJornada/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarJornada(@PathParam("id") Integer id, Jornada entity) {
        Jornada j = super.find(id);
        if (entity.getObservaciones() != null && !entity.getObservaciones().isEmpty()) {
            j.setObservaciones(entity.getObservaciones());
        } else if (j.getObservaciones() != null) {
            j.setObservaciones(null);
        }
        super.edit(j);
    }
}
