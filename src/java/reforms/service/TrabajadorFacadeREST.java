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
import reforms.jpa.Localidad;
import reforms.jpa.Propiedad;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("trabajador")
public class TrabajadorFacadeREST extends AbstractFacade<Trabajador> {

    @EJB
    private PropiedadFacadeREST propiedadFacadeREST;

    @EJB
    private LocalidadFacadeREST localidadFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public TrabajadorFacadeREST() {
        super(Trabajador.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Trabajador entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Trabajador entity) {
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
    public Trabajador find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajador> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajador> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerTrabajadores")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajador> obtenerTrabajadores() {
        Query q = em.createNamedQuery("Trabajador.obtenerTrabajadores");
        List<Trabajador> lt = q.getResultList();
        for (Trabajador t : lt) {
            t.setOperario(null);
            t.setOperador(null);
            t.setNominas(null);
            t.setEmail(null);
            t.setPassword(null);
        }
        return lt.isEmpty() ? null : lt;
    }
    
    @GET
    @Path("obtenerOperadores")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajador> obtenerOperadores() {
        Query q = em.createNamedQuery("Trabajador.obtenerOperadores");
        List<Trabajador> lt = q.getResultList();
        for (Trabajador t : lt) {
            t.setOperario(null);
            t.setOperador(null);
            t.setNominas(null);
            t.setEmail(null);
            t.setPassword(null);
        }
        return lt.isEmpty() ? null : lt;
    }
    
    @GET
    @Path("obtenerOperarios")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabajador> obtenerOperarios() {
        Query q = em.createNamedQuery("Trabajador.obtenerOperarios");
        List<Trabajador> lt = q.getResultList();
        for (Trabajador t : lt) {
            t.setOperario(null);
            t.setOperador(null);
            t.setNominas(null);
            t.setEmail(null);
            t.setPassword(null);
        }
        return lt.isEmpty() ? null : lt;
    }
    
    @GET
    @Path("comprobarDni/{dni}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Trabajador comprobarDni(@PathParam("dni") String dni) {
        Query q = em.createNamedQuery("Trabajador.buscarTrabajadorPorDni");
        q.setParameter("dni", dni);
        List<Trabajador> lt = q.getResultList();
        Trabajador t = new Trabajador();
        if (lt.size() > 0) {
            t = null;
        }
        return t;
    }
    
    @POST
    @Path("registrarTrabajador")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void registrarTrabajador(Trabajador entity) {
        if (entity.getPropiedad().getId() == null) {
            Propiedad p = entity.getPropiedad();
            System.err.println(p.getLocalidad().getNombre());
            if (p.getLocalidad().getId() == null) {
                Localidad l = localidadFacadeREST.buscarLocalidadPorCodigoPostal(p.getLocalidad().getCp());
                if (l == null) {
                    l = p.getLocalidad();
                    localidadFacadeREST.create(l);
                    Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Integer id = q.getFirstResult();
                    l.setId(id);
                }
                p.setLocalidad(l);
            }
            propiedadFacadeREST.create(p);
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Integer id = q.getFirstResult();
            p.setId(id);
            entity.setPropiedad(p);
        }
        super.create(entity);
    }
}
