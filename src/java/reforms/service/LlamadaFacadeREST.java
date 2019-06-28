/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
import reforms.jpa.Cliente;
import reforms.jpa.Contacto;
import reforms.jpa.Evento;
import reforms.jpa.Grupo;
import reforms.jpa.Llamada;
import reforms.jpa.Operador;
import reforms.jpa.Perito;
import reforms.jpa.Siniestro;
import reforms.jpa.Trabajador;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("llamada")
public class LlamadaFacadeREST extends AbstractFacade<Llamada> {

    @EJB
    private EventoFacadeREST eventoFacadeREST;
    
    @EJB
    private OperadorFacadeREST operadorFacadeREST;

    @EJB
    private SiniestroFacadeREST siniestroFacadeREST;

    @EJB
    private ClienteFacadeREST clienteFacadeREST;

    @EJB
    private ContactoFacadeREST contactoFacadeREST;

    @EJB
    private PeritoFacadeREST peritoFacadeREST;

    @EJB
    private GrupoFacadeREST grupoFacadeREST;

    @PersistenceContext(unitName = "ReForms_ProviderPU")
    private EntityManager em;

    public LlamadaFacadeREST() {
        super(Llamada.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Llamada entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Llamada entity) {
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
    public Llamada find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    private Evento normalizar_evento(Evento e) {
        Evento eaux = new Evento();
        eaux.setId(e.getId());
        eaux.setFecha(e.getFecha());
        eaux.setDescripcion(e.getDescripcion());
        Operador o = new Operador();
        o.setId(e.getOperador().getId());
        o.setGerente(e.getOperador().getGerente());
        Trabajador t = new Trabajador();
        t.setId(e.getOperador().getTrabajador().getId());
        t.setNombre(e.getOperador().getTrabajador().getNombre());
        t.setApellido1(e.getOperador().getTrabajador().getApellido1());
        t.setApellido2(e.getOperador().getTrabajador().getApellido2());
        o.setTrabajador(t);
        eaux.setOperador(o);
        Siniestro s = new Siniestro();
        s.setId(e.getSiniestro().getId());
        eaux.setSiniestro(s);
        return eaux;
    }
    
    private Llamada normalizar_llamada(Llamada l) {
        Llamada laux = new Llamada();
        laux.setId(l.getId());
        laux.setTipo(l.getTipo());
        laux.setEvento(normalizar_evento(l.getEvento()));
        if (l.getCliente() != null) {
            Cliente c = new Cliente();
            c.setId(l.getCliente().getId());
            c.setNombre(l.getCliente().getNombre());
            c.setApellido1(l.getCliente().getApellido1());
            c.setApellido2(l.getCliente().getApellido2());
            c.setTelefono1(l.getCliente().getTelefono1());
            c.setTelefono2(l.getCliente().getTelefono2());
            c.setTipo(l.getCliente().getTipo());
            c.setObservaciones(l.getCliente().getObservaciones());
            Aseguradora a = new Aseguradora();
            a.setId(l.getCliente().getAseguradora().getId());
            c.setAseguradora(a);
            laux.setCliente(c);
        }
        if (l.getContacto() != null) {
            Contacto c = new Contacto();
            c.setId(l.getContacto().getId());
            Siniestro s = new Siniestro();
            s.setId(l.getEvento().getSiniestro().getId());
            c.setSiniestro(s);
            c.setNombre(l.getContacto().getNombre());
            c.setApellido1(l.getContacto().getApellido1());
            c.setApellido2(l.getContacto().getApellido2());
            c.setTelefono1(l.getContacto().getTelefono1());
            c.setTelefono2(l.getContacto().getTelefono2());
            c.setObservaciones(l.getContacto().getObservaciones());
            laux.setContacto(c);
        }
        if (l.getPerito() != null) {
            Perito p = new Perito();
            p.setId(l.getPerito().getId());
            p.setNombre(l.getPerito().getNombre());
            p.setApellido1(l.getPerito().getApellido1());
            p.setApellido2(l.getPerito().getApellido2());
            p.setTelefono1(l.getPerito().getTelefono1());
            p.setTelefono2(l.getPerito().getTelefono2());
            Aseguradora a = new Aseguradora();
            a.setId(l.getPerito().getAseguradora().getId());
            p.setAseguradora(a);
            laux.setPerito(p);
        }
        if (l.getGrupo() != null) {
            Grupo g = new Grupo();
            g.setId(l.getGrupo().getId());
            String texto = "[" + l.getGrupo().getJornada().getFecha().getTime() + "] " + grupoFacadeREST.obtenerNombreGrupo(g.getId());
            g.setObservaciones(texto);
            laux.setGrupo(g);
        }
        return laux;
    }
    
    @GET
    @Path("obtenerLlamadas/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> obtenerLlamadas(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Llamada.obtenerLlamadas");
        q.setParameter("idSiniestro", idSiniestro);
        List<Llamada> ll = q.getResultList(),
                      res = new ArrayList<>();
        for (Llamada l : ll) {
            res.add(normalizar_llamada(l));
        }
        return res;
    }
    
    @GET
    @Path("obtenerEventos/{idSiniestro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Llamada> obtenerEventos(@PathParam("idSiniestro") Integer idSiniestro) {
        Query q = em.createNamedQuery("Evento.obtenerEventos");
        q.setParameter("idSiniestro", idSiniestro);
        List<Evento> le = q.getResultList();
        List<Llamada> res = new ArrayList<>();
        for (Evento e : le) {
            Llamada aux;
            if (e.getLlamadas() != null && !e.getLlamadas().isEmpty()) {
                for (Llamada l : e.getLlamadas()) {
                    res.add(normalizar_llamada(l));
                }
            } else {
                aux = new Llamada();
                aux.setId(-1);
                aux.setEvento(normalizar_evento(e));
                res.add(aux);
            }
        }
        return res;
    }
    
    private boolean test_info_evento(Evento e) {
        return e != null && e.getFecha() != null && e.getOperador() != null && e.getOperador().getId() != null && e.getSiniestro()!= null && e.getSiniestro().getId() != null;
    }
    
    private boolean test_info_llamada(Llamada l) {
        boolean test = l != null && (l.getId() == null || l.getId() == -1) && l.getTipo() >= 0 && l.getTipo() < 4;
        if (test && test_info_evento(l.getEvento())) {
            if (l.getCliente() != null) {
                test = l.getCliente().getId() != null;
            } else if (l.getContacto() != null) {
                if (l.getContacto().getId() != null) {
                    test = true;
                } else {
                    test = Pattern.matches("^[69]\\d{8}$", l.getContacto().getTelefono1());
                    if (l.getContacto().getTelefono2() != null && !l.getContacto().getTelefono2().isEmpty()) {
                        test &= Pattern.matches("^[69]\\d{8}$", l.getContacto().getTelefono2());
                    }
                }
            } else if (l.getPerito() != null) {
                test = l.getPerito().getId() != null;
            } else if (l.getGrupo() != null) {
                test = l.getGrupo().getId() != null;
            } else {
                test = false;
            }
        } else {
            test = false;
        }
        return test;
    }
    
    @POST
    @Path("agregarEvento")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Llamada agregarEvento(Llamada entity) {
        if (test_info_llamada(entity)) {
            Operador o = operadorFacadeREST.find(entity.getEvento().getOperador().getId());
            Siniestro s = siniestroFacadeREST.find(entity.getEvento().getSiniestro().getId());
            entity.getEvento().setOperador(o);
            entity.getEvento().setSiniestro(s);
            if (entity.getEvento().getDescripcion() != null && entity.getEvento().getDescripcion().isEmpty()) {
                entity.getEvento().setDescripcion(null);
            }
            eventoFacadeREST.create(entity.getEvento());
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            entity.getEvento().setId(q.getFirstResult());
            o.getEventos().add(entity.getEvento());
            operadorFacadeREST.edit(o);
            s.getEventos().add(entity.getEvento());
            siniestroFacadeREST.edit(s);
            if (entity.getId() == null) {
                if (entity.getCliente() != null) {
                    Cliente c = clienteFacadeREST.find(entity.getCliente().getId());
                    entity.setCliente(c);
                    super.create(entity);
                    entity.setId(q.getFirstResult());
                    c.getLlamadas().add(entity);
                    clienteFacadeREST.edit(c);
                } else if (entity.getContacto() != null) {
                    if (entity.getContacto().getId() != null) {
                        Contacto c = contactoFacadeREST.find(entity.getContacto().getId());
                        entity.setContacto(c);
                        super.create(entity);
                        entity.setId(q.getFirstResult());
                        c.getLlamadas().add(entity);
                        contactoFacadeREST.edit(c);
                    } else {
                        Contacto c = new Contacto();
                        c.setTelefono1(entity.getContacto().getTelefono1());
                        if (entity.getContacto().getNombre() != null && !entity.getContacto().getNombre().isEmpty()) {
                            c.setNombre(entity.getContacto().getNombre());
                        }
                        if (entity.getContacto().getApellido1()!= null && !entity.getContacto().getApellido1().isEmpty()) {
                            c.setApellido1(entity.getContacto().getApellido1());
                        }
                        if (entity.getContacto().getApellido2()!= null && !entity.getContacto().getApellido2().isEmpty()) {
                            c.setApellido2(entity.getContacto().getApellido2());
                        }
                        if (entity.getContacto().getTelefono2()!= null && !entity.getContacto().getTelefono2().isEmpty()) {
                            c.setTelefono2(entity.getContacto().getTelefono2());
                        }
                        c.setSiniestro(s);
                        contactoFacadeREST.create(c);
                        c.setId(q.getFirstResult());
                        s.getContactos().add(c);
                        siniestroFacadeREST.edit(s);
                        entity.setContacto(c);
                        super.create(entity);
                        entity.setId(q.getFirstResult());
                        c.getLlamadas().add(entity);
                        contactoFacadeREST.edit(c);
                    }
                } else if (entity.getPerito() != null) {
                    Perito p = peritoFacadeREST.find(entity.getPerito().getId());
                    entity.setPerito(p);
                    super.create(entity);
                    entity.setId(q.getFirstResult());
                    p.getLlamadas().add(entity);
                    peritoFacadeREST.edit(p);
                } else if (entity.getGrupo() != null) {
                    Grupo g = grupoFacadeREST.find(entity.getGrupo().getId());
                    entity.setGrupo(g);
                    super.create(entity);
                    entity.setId(q.getFirstResult());
                    g.getLlamadas().add(entity);
                    grupoFacadeREST.edit(g);
                }
                entity.getEvento().getLlamadas().add(entity);
                eventoFacadeREST.edit(entity.getEvento());
            }
        } else {
            entity = null;
        }
        return normalizar_llamada(entity);
    }
}
