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
import reforms.jpa.Grupo;
import reforms.jpa.Jornada;
import reforms.jpa.Localidad;
import reforms.jpa.Operador;
import reforms.jpa.Operario;
import reforms.jpa.Propiedad;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("grupo")
public class GrupoFacadeREST extends AbstractFacade<Grupo> {

    @EJB
    private JornadaFacadeREST jornadaFacadeREST;

    @EJB
    private OperarioFacadeREST operarioFacadeREST;

    @EJB
    private LocalidadFacadeREST localidadFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public GrupoFacadeREST() {
        super(Grupo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Grupo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Grupo entity) {
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
    public Grupo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grupo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grupo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("buscarGrupoPorJornada/{idJornada}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grupo> buscarGrupoPorJornada(@PathParam("idJornada") Integer idJornada) {
        Query q = em.createNamedQuery("Grupo.buscarGrupoPorJornada");
        q.setParameter("idJornada", idJornada);
        List<Grupo> lg = q.getResultList(),
                    res = new ArrayList<>();
        for (Grupo g : lg) {
            Grupo aux = new Grupo();
            aux.setId(g.getId());
            aux.setObservaciones(g.getObservaciones());
            aux.setSubcontrata(g.getSubcontrata());
            Jornada j = new Jornada();
            j.setId(g.getJornada().getId());
            j.setFecha(g.getJornada().getFecha());
            j.setObservaciones(g.getJornada().getObservaciones());
            Operador ger = new Operador();
            ger.setId(g.getJornada().getGerente().getId());
            Trabajador t = new Trabajador();
            t.setId(g.getJornada().getGerente().getTrabajador().getId());
            t.setNombre(g.getJornada().getGerente().getTrabajador().getNombre());
            t.setApellido1(g.getJornada().getGerente().getTrabajador().getApellido1());
            t.setApellido2(g.getJornada().getGerente().getTrabajador().getApellido2());
            t.setDni("");
            t.setTelefono1("");
            t.setTelefono2("");
            t.setEmail("");
            t.setPassword("");
            Propiedad p = new Propiedad();
            p.setId(0);
            p.setDireccion("");
            p.setNumero(0);
            Localidad l = new Localidad();
            l.setId(0);
            l.setNombre("");
            l.setCp("");
            p.setLocalidad(l);
            t.setPropiedad(p);
            ger.setTrabajador(t);
            j.setGerente(ger);
            aux.setJornada(j);
            res.add(aux);
        }
        return res;
    }
    
    public String obtenerNombreGrupo(Integer idGrupo) {
        String salida = "Nuevo grupo";
        Grupo g = super.find(idGrupo);
        if (g != null && g.getIntegrantes() != null && !g.getIntegrantes().isEmpty()) {
            salida = g.getIntegrantes().get(0).getOperario().getTrabajador().getNombre();
            if (g.getIntegrantes().size() > 1) {
                for (int i = 1; i < g.getIntegrantes().size() - 1; i++) {
                    salida += ", " + g.getIntegrantes().get(i).getOperario().getTrabajador().getNombre();
                }
                salida += " y " + g.getIntegrantes().get(g.getIntegrantes().size() - 1).getOperario().getTrabajador().getNombre();
            }
        }
        return salida;
    }
    
    public String obtenerZonaGrupo(Integer idGrupo) {
        String salida = "libre";
        Grupo g = super.find(idGrupo);
        if (g != null && g.getCitas() != null && !g.getCitas().isEmpty()) {
            List<Localidad> ll = localidadFacadeREST.obtenerLocalidadPorGrupo(g.getId());
            int actual = 0, max = 0;
            String strA = ll.get(0).getNombre(), strM = ll.get(0).getNombre();
            for (Localidad l : ll) {
                if (l.getNombre().equals(strA)) {
                    actual++;
                } else {
                    if (actual > max) {
                        max = actual;
                        strM = strA;
                    }
                    actual = 1;
                    strA = l.getNombre();
                }
            }
            salida = strM;
        }
        return salida;
    }
    
    @GET
    @Path("obtenerInfoGrupoPorJornada/{idJornada}")
    @Produces(MediaType.TEXT_PLAIN)
    public String obtenerInfoGrupoPorJornada(@PathParam("idJornada") Integer idJornada) {
        String salida = "";
        Query q = em.createNamedQuery("Grupo.buscarGrupoPorJornada");
        q.setParameter("idJornada", idJornada);
        List<Grupo> lg = q.getResultList();
        for (int i = 0; i < lg.size() - 1; i++) {
            salida += "{\"nombre\":\"" + obtenerNombreGrupo(lg.get(i).getId()) + "\", \"zona\":\"" + obtenerZonaGrupo(lg.get(i).getId()) + "\"}, ";
        }
        if (lg.size() > 0) {
            salida += "{\"nombre\":\"" + obtenerNombreGrupo(lg.get(lg.size() - 1).getId()) + "\", \"zona\":\"" + obtenerZonaGrupo(lg.get(lg.size() - 1).getId()) + "\"}";
        }
        return "[" + salida + "]";
    }
    
    @POST
    @Path("agregarGrupo")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Grupo agregarGrupo(Grupo entity) {
        if (entity.getJornada() != null && entity.getJornada().getId() != null) {
            List<Operario> disponibles = operarioFacadeREST.obtenerOperarioDisponiblePorJornada(entity.getJornada().getId());
            if (!disponibles.isEmpty()) {
                entity.setJornada(jornadaFacadeREST.find(entity.getJornada().getId()));
                if (entity.getObservaciones() != null && entity.getObservaciones().isEmpty()) {
                    entity.setObservaciones(null);
                }
                super.create(entity);
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                entity.setId(q.getFirstResult());
            }
        }
        return entity;
    }
}
