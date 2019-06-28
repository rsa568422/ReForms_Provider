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
import reforms.jpa.Contacto;
import reforms.jpa.Evento;
import reforms.jpa.Llamada;
import reforms.jpa.Siniestro;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("contacto")
public class ContactoFacadeREST extends AbstractFacade<Contacto> {
    
    @EJB
    private SiniestroFacadeREST siniestroFacadeREST;
    
    @EJB
    private EventoFacadeREST eventoFacadeREST;
    
    @EJB
    private LlamadaFacadeREST llamadaFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ContactoFacadeREST() {
        super(Contacto.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Contacto entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Contacto entity) {
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
    public Contacto find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contacto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contacto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("agregarContacto")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void agregarContacto(Contacto entity) {
        super.create(entity);
    }

    @PUT
    @Path("actualizarContacto/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarContacto(@PathParam("id") Integer id, Contacto entity) {
        Contacto t = find(id);
        t.setNombre(entity.getNombre());
        t.setApellido1(entity.getApellido1());
        t.setApellido2(entity.getApellido2());
        t.setTelefono1(entity.getTelefono1());
        t.setTelefono2(entity.getTelefono2());
        t.setObservaciones(entity.getObservaciones());
        super.edit(t);
    }
    
    @GET
    @Path("obtenerContactos/{siniestroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contacto> obtenerContactos(@PathParam("siniestroId") Integer siniestroId) {
        Query q;
        q = em.createNamedQuery("Contacto.obtenerContactos");
        q.setParameter("siniestroId", siniestroId);
        List<Contacto> lc = q.getResultList(),
                       res = new ArrayList<>();
        for (Contacto c : lc) {
            Contacto aux = new Contacto();
            aux.setId(c.getId());
            aux.setNombre(c.getNombre());
            aux.setApellido1(c.getApellido1());
            aux.setApellido2(c.getApellido2());
            aux.setTelefono1(c.getTelefono1());
            aux.setTelefono2(c.getTelefono2());
            aux.setObservaciones(c.getObservaciones());
            res.add(aux);
        }
        return res;
    }
    
    @DELETE
    @Path("borrarContacto/{id}")
    public void borrarContacto(@PathParam("id") Integer id) {
        Contacto c = super.find(id);
        Siniestro s = c.getSiniestro();
        for (Llamada l : c.getLlamadas()) {
            Evento e = l.getEvento();
            String tipo;
            switch (l.getTipo()) {
                case 0: tipo = "saliente"; break;
                case 1: tipo = "entrante"; break;
                case 2: tipo = "sin respuesta"; break;
                case 3: tipo = "perdida"; break;
                default:  tipo = "";
            }
            e.setDescripcion("Llamada " + tipo + " (" + l.getContacto().getTelefono1() + ")" + (e.getDescripcion() != null ? ("\n\n" + e.getDescripcion()) : ""));
            e.getLlamadas().remove(l);
            eventoFacadeREST.edit(e);
        }
        s.getContactos().remove(c);
        siniestroFacadeREST.edit(s);
        super.remove(super.find(id));
    }
}
