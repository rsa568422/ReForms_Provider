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
import reforms.jpa.Vehiculo;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("vehiculo")
public class VehiculoFacadeREST extends AbstractFacade<Vehiculo> {

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public VehiculoFacadeREST() {
        super(Vehiculo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Vehiculo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Vehiculo entity) {
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
    public Vehiculo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vehiculo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vehiculo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("obtenerVehiculos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vehiculo> obtenerVehiculos() {
        Query q = em.createNamedQuery("Vehiculo.obtenerVehiculos");
        List<Vehiculo> lv = q.getResultList();
        return lv.isEmpty() ? null : lv;
    }
    
    @POST
    @Path("registrarVehiculo")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void registrarVehiculo(Vehiculo entity) {
        super.create(entity);
    }

    @PUT
    @Path("actualizarVehiculo/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void actualizarVehiculo(@PathParam("id") Integer id, Vehiculo entity) {
        Vehiculo v = find(id);
        v.setMarca(entity.getMarca());
        v.setModelo(entity.getModelo());
        v.setAdquisicion(entity.getAdquisicion());
        v.setMatriculacion(entity.getMatriculacion());
        v.setKm(entity.getKm());
        v.setObservaciones(entity.getObservaciones());
        super.edit(v);
    }
    
    @GET
    @Path("obtenerVehiculoDisponiblePorJornada/{idJornada}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vehiculo> obtenerVehiculoDisponiblePorJornada(@PathParam("idJornada") Integer idJornada) {
        Query q = em.createNamedQuery("Vehiculo.obtenerVehiculoDisponiblePorJornada");
        q.setParameter("idJornada", idJornada);
        List<Vehiculo> lv = q.getResultList(),
                       res = new ArrayList<>();
        for (Vehiculo v : lv) {
            Vehiculo aux = new Vehiculo();
            aux.setId(v.getId());
            aux.setMatricula(v.getMatricula());
            aux.setMarca(v.getMarca());
            aux.setModelo(v.getModelo());
            aux.setObservaciones(v.getObservaciones());
            res.add(aux);
        }
        return res;
    }
}
