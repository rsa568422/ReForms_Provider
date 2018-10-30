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
import reforms.jpa.Cliente;
import reforms.jpa.Localidad;
import reforms.jpa.Poliza;
import reforms.jpa.Propiedad;
import reforms.jpa.Recurso;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("siniestro")
public class SiniestroFacadeREST extends AbstractFacade<Siniestro> {

    @EJB
    private PolizaFacadeREST polizaFacadeREST;

    @EJB
    private ClienteFacadeREST clienteFacadeREST;

    @EJB
    private PropiedadFacadeREST propiedadFacadeREST;

    @EJB
    private LocalidadFacadeREST localidadFacadeREST;

    @EJB
    private RecursoFacadeREST recursoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public SiniestroFacadeREST() {
        super(Siniestro.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Siniestro entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Siniestro entity) {
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
    public Siniestro find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarSiniestroPorNumeroSiniestro/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroSiniestro(@PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroSiniestro");
        q.setParameter("numero", numero);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorNumeroSiniestroA/{aseguradoraId}/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroSiniestroA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroSiniestroA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorNumeroPoliza/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroPoliza(@PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroPoliza");
        q.setParameter("numero", numero);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorNumeroPolizaA/{aseguradoraId}/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroPolizaA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("numero") String numero) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroPolizaA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorTelefono/{telefono}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorTelefono(@PathParam("telefono") String telefono) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorTelefono");
        q.setParameter("telefono", telefono);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorTelefonoA/{aseguradoraId}/{telefono}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorTelefonoA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("telefono") String telefono) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorTelefonoA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("telefono", telefono);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorNombre/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNombre(@PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNombre");
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorNombreA/{aseguradoraId}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNombreA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorNombreA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorDireccion/{localidadId}/{direccion}/{numero}/{piso:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorDireccion(@PathParam("localidadId") Integer localidadId, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorDireccion");
        q.setParameter("localidadId", localidadId);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", piso);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("buscarSiniestroPorDireccionA/{aseguradoraId}/{localidadId}/{direccion}/{numero}/{piso:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorDireccionA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("localidadId") Integer localidadId, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q = em.createNamedQuery("Siniestro.buscarSiniestroPorDireccionA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("localidadId", localidadId);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", piso);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }

    @POST
    @Path("registrarSiniestro")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void registrarSiniestro(Siniestro entity) {
        if (entity.getPoliza().getId() == null) {
            Poliza p = entity.getPoliza();
            if (entity.getPoliza().getCliente().getId() == null) {
                Cliente c = entity.getPoliza().getCliente();
                clienteFacadeREST.create(c);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                Integer id = q.getFirstResult();
                c.setId(id);
                p.setCliente(c);
            }
            if (entity.getPoliza().getPropiedad().getId() == null) {
                Propiedad pr = entity.getPoliza().getPropiedad();
                if (pr.getLocalidad().getId() == null) {
                    Localidad l = pr.getLocalidad();
                    localidadFacadeREST.create(l);
                    Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Integer id = q.getFirstResult();
                    l.setId(id);
                    pr.setLocalidad(l);
                }
                propiedadFacadeREST.create(pr);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                Integer id = q.getFirstResult();
                pr.setId(id);
                p.setPropiedad(pr);
            }
            polizaFacadeREST.create(p);
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Integer id = q.getFirstResult();
            p.setId(id);
            entity.setPoliza(p);
        }
        Recurso r = entity.getOriginal();
        recursoFacadeREST.create(r);
        Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
        Integer id = q.getFirstResult();
        r.setId(id);
        entity.setOriginal(r);
        super.create(entity);
    }
}
