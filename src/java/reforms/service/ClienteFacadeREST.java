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
import reforms.jpa.Cliente;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("cliente")
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cliente entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cliente entity) {
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
    public Cliente find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarCoincidenciasCliente/{idAseguradora}/{nombre}/{apellido1}/{apellido2:.*}/{telefono1}/{telefono2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorNombreMasTelefonoA(@PathParam("idAseguradora") Integer idAseguradora, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2, @PathParam("telefono1") String telefono1, @PathParam("telefono2") String telefono2) {
        Query q = em.createNamedQuery("Cliente.buscarCoincidenciasCliente");
        q.setParameter("idAseguradora", idAseguradora);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        q.setParameter("telefono1", telefono1);
        q.setParameter("telefono2", telefono2);
        List<Cliente> lc = q.getResultList(),
                      res = new ArrayList<>();
        for (Cliente c : lc) {
            Cliente aux = new Cliente();
            aux.setId(c.getId());
            aux.setNombre(c.getNombre());
            aux.setApellido1(c.getApellido1());
            aux.setApellido2(c.getApellido2());
            aux.setTelefono1(c.getTelefono1());
            aux.setTelefono2(c.getTelefono2());
            aux.setTipo(c.getTipo());
            Aseguradora a = new Aseguradora();
            a.setId(c.getAseguradora().getId());
            a.setNombre(c.getAseguradora().getNombre());
            aux.setAseguradora(a);
            res.add(aux);
        }
        return res;
    }
    
    // PARA BORRAR
    @GET
    @Path("buscarClientePorTelefono/{telefono}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorTelefono(@PathParam("telefono") String telefono) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorTelefono");
        q.setParameter("telefono", telefono);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
    
    @GET
    @Path("buscarClientePorTelefonoA/{aseguradoraId}/{telefono}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorTelefonoA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("telefono") String telefono) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorTelefonoA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("telefono", telefono);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
    
    @GET
    @Path("buscarClientePorNombreCompleto/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorNombreCompleto(@PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorNombreCompleto");
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
    
    @GET
    @Path("buscarClientePorNombreCompletoA/{aseguradoraId}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorNombreCompletoA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorNombreCompletoA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
    
    @GET
    @Path("buscarClientePorNombreMasTelefono/{telefono}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorNombreMasTelefono(@PathParam("telefono") String telefono, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorNombreMasTelefono");
        q.setParameter("telefono", telefono);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
    
    @GET
    @Path("buscarClientePorNombreMasTelefonoA/{aseguradoraId}/{telefono}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> buscarClientePorNombreMasTelefonoA(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("telefono") String telefono, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q = em.createNamedQuery("Cliente.buscarClientePorNombreMasTelefonoA");
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("telefono", telefono);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        List<Cliente> lc = q.getResultList();
        return lc.isEmpty() ? null : lc;
    }
}
