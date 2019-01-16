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
    
    @GET
    @Path("contarSiniestros/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestros(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestros");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestrosAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestrosNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("obtenerSiniestros/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> obtenerSiniestros(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.obtenerSiniestros");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.obtenerSiniestrosAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.obtenerSiniestrosNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("contarSiniestroPorNumeroSiniestro/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{numero}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestroPorNumeroSiniestro(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("numero") String numero) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroSiniestro");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroSiniestroAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroSiniestroNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarSiniestroPorNumeroSiniestro/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroSiniestro(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("numero") String numero) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroSiniestro");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroSiniestroAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroSiniestroNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("contarSiniestroPorNumeroPoliza/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{numero}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestroPorNumeroPoliza(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("numero") String numero) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroPoliza");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroPolizaAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNumeroPolizaNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarSiniestroPorNumeroPoliza/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{numero}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNumeroPoliza(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("numero") String numero) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroPoliza");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroPolizaAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNumeroPolizaNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("numero", numero);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("contarSiniestroPorNombre/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestroPorNombre(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNombre");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNombreAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorNombreNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarSiniestroPorNombre/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{nombre}/{apellido1}/{apellido2:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorNombre(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("nombre") String nombre, @PathParam("apellido1") String apellido1, @PathParam("apellido2") String apellido2) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNombre");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNombreAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorNombreNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("nombre", nombre);
        q.setParameter("apellido1", apellido1);
        q.setParameter("apellido2", apellido2);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("contarSiniestroPorTelefono/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{telefono}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestroPorTelefono(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("telefono") String telefono) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorTelefono");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorTelefonoAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorTelefonoNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("telefono", telefono);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarSiniestroPorTelefono/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{telefono}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorTelefono(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("telefono") String telefono) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorTelefono");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorTelefonoAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorTelefonoNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("telefono", telefono);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
    
    @GET
    @Path("contarSiniestroPorDireccion/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{cp}/{direccion}/{numero}/{piso:.*}")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarSiniestroPorDireccion(@PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("cp") String cp, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorDireccion");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorDireccionAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.contarSiniestroPorDireccionNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("cp", cp);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", "".equals(piso) ? null : piso);
        return String.valueOf(q.getSingleResult());
    }
    
    @GET
    @Path("buscarSiniestroPorDireccion/{pagina}/{aseguradoraId:.*}/{estado:.*}/{subestados:.*}/{cp}/{direccion}/{numero}/{piso:.*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Siniestro> buscarSiniestroPorDireccion(@PathParam("pagina") Integer pagina, @PathParam("aseguradoraId") Integer aseguradoraId, @PathParam("estado") Integer estado, @PathParam("subestados") String subestados, @PathParam("cp") String cp, @PathParam("direccion") String direccion, @PathParam("numero") Integer numero, @PathParam("piso") String piso) {
        Query q;
        List<Integer> listaSubestados = new ArrayList<>();
        if (estado == null) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorDireccion");
        } else if (estado == 0) {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorDireccionAbiertos");
            for (int i = 0; i < 4; i++) {
                if (subestados.charAt(i) == '1') {
                    listaSubestados.add(i);
                }
            }
            if (listaSubestados.isEmpty()) {
                listaSubestados.add(-1);
            }
            q.setParameter("subestados", listaSubestados);
        } else {
            q = em.createNamedQuery("Siniestro.buscarSiniestroPorDireccionNoAbiertos");
            q.setParameter("estado", estado);
        }
        q.setParameter("aseguradoraId", aseguradoraId);
        q.setParameter("cp", cp);
        q.setParameter("direccion", direccion);
        q.setParameter("numero", numero);
        q.setParameter("piso", "".equals(piso) ? null : piso);
        q.setFirstResult(10 * pagina);
        q.setMaxResults(10);
        List<Siniestro> ls = q.getResultList();
        return ls.isEmpty() ? null : ls;
    }
}
