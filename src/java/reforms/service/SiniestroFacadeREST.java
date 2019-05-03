/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.ArrayList;
import java.util.Date;
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
import reforms.jpa.Localidad;
import reforms.jpa.Perito;
import reforms.jpa.Poliza;
import reforms.jpa.Propiedad;
import reforms.jpa.Recurso;
import reforms.jpa.Siniestro;
import reforms.jpa.Tarea;

/**
 *
 * @author Roberto
 */
@Stateless
@Path("siniestro")
public class SiniestroFacadeREST extends AbstractFacade<Siniestro> {

    @EJB
    private AseguradoraFacadeREST aseguradoraFacadeREST;

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

    @EJB
    private TareaFacadeREST tareaFacadeREST;

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
    
    private boolean test_info_cliente(Cliente c) {
        boolean ok = c != null;
        if (c.getId() == null) {
            ok &= c.getNombre() != null && !c.getNombre().isEmpty();
            ok &= c.getApellido1() != null && !c.getApellido1().isEmpty();
            ok &= c.getTelefono1()!= null && Pattern.matches("^[69]\\d{8}$", c.getTelefono1());
            ok &= !(c.getTelefono2() != null && !c.getTelefono2().isEmpty() && !Pattern.matches("^[69]\\d{8}$", c.getTelefono2()));
            ok &= c.getTipo() != null && c.getTipo() >= 0 && c.getTipo() <= 2;
        }
        ok &= c.getAseguradora() != null && c.getAseguradora().getId() != null;
        return ok;
    }
    
    private boolean test_info_localidad(Localidad l) {
        boolean ok = l != null;
        if (l.getId() == null) {
            ok &= l != null && l.getCp() != null && Pattern.matches("^\\d{5}$", l.getCp());
            ok &= l.getNombre() != null && !l.getNombre().isEmpty();
        }
        return ok;
    }
    
    private boolean test_info_propiedad(Propiedad p) {
        boolean ok = p != null;
        if (p.getId() == null) {
            ok &= p.getDireccion() != null && !p.getDireccion().isEmpty();
            ok &= p.getNumero() != null && p.getGeolat() != null && p.getGeolong() != null;
        }
        ok &= test_info_localidad(p.getLocalidad());
        return ok;
    }
    
    private boolean test_info_poliza(Poliza p) {
        return p != null && test_info_cliente(p.getCliente()) && test_info_propiedad(p.getPropiedad());
    }
    
    private boolean test_info_original(Recurso r) {
        boolean ok = r != null;
        ok &= r.getNombre() != null && !r.getNombre().isEmpty();
        ok &= r.getTipo() != null && r.getTipo().equals(0);
        ok &= r.getFichero() != null;
        return ok;
    }
    
    private boolean test_info_siniestro(Siniestro s) {
        boolean ok = s != null;
        ok &= s.getNumero() != null && !s.getNumero().isEmpty();
        ok &= test_info_poliza(s.getPoliza());
        ok &= test_info_original(s.getOriginal());
        ok &= s.getAfectado() == null || test_info_propiedad(s.getAfectado());
        ok &= s.getPeritoOriginal() != null && s.getPeritoOriginal().getId() != null;
        ok &= s.getPeritoOriginal().getAseguradora() != null && s.getPeritoOriginal().getAseguradora().getId() != null;
        return ok;
    }
    
    private boolean misma_direccion(Propiedad p1, Propiedad p2) {
        boolean res = true;
        res &= p1.getDireccion().equals(p2.getDireccion());
        res &= p1.getNumero().equals(p2.getNumero());
        res &= p1.getGeolat().equals(p2.getGeolat());
        res &= p1.getGeolong().equals(p2.getGeolong());
        if (p1.getPiso() != null && !p1.getPiso().isEmpty()) {
            res &= p1.getPiso().equals(p2.getPiso());
        } else if (p2.getPiso() != null && !p2.getPiso().isEmpty()) {
            res &= p2.getPiso().equals(p1.getPiso());
        }
        return res;
    }
    
    private boolean misma_propiedad(Propiedad a, Propiedad p) {
        boolean res = true;
        if (a == null) {
            res = false;
        } else if (a.getId() != null && p.getId() != null && !a.getId().equals(p.getId())) {
            res = false;
        } else if (!a.getLocalidad().getCp().equals(p.getLocalidad().getCp())) {
            res = false;
        } else {
            res = misma_direccion(a, p);
        }
        return res;
    }
    
    private boolean validar_info_siniestro(Siniestro s) {
        boolean ok = true;
        if (!s.getPeritoOriginal().getAseguradora().getId().equals(s.getPoliza().getCliente().getAseguradora().getId())) {
            ok = false;
        } else if (buscarSiniestroPorNumeroSiniestro(0, s.getPeritoOriginal().getAseguradora().getId(), null, null, s.getNumero()) != null) {
            ok = false;
        } else if (s.getPoliza().getId() == null && !polizaFacadeREST.buscarPolizaPorNumeroPoliza(s.getPoliza().getCliente().getAseguradora().getId(), s.getPoliza().getNumero()).isEmpty()) {
            ok = false;
        } else if (s.getAfectado() != null && misma_propiedad(s.getAfectado(), s.getPoliza().getPropiedad())) {
            ok = false;
        }
        return ok;
    }
    
    @POST
    @Path("registrarSiniestro")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void registrarSiniestro(Siniestro entity) {
        if (test_info_siniestro(entity) && validar_info_siniestro(entity)) {
            entity.setAlbaran(null);
            entity.setEstado(0);
            entity.setFechaCierre(null);
            if (entity.getObservaciones() != null && entity.getObservaciones().isEmpty()) {
                entity.setObservaciones(null);
            }
            if (entity.getPoliza().getCliente().getApellido2() != null && entity.getPoliza().getCliente().getApellido2().isEmpty()) {
                entity.getPoliza().getCliente().setApellido2(null);
            }
            if (entity.getPoliza().getCliente().getTelefono2()!= null && entity.getPoliza().getCliente().getTelefono2().isEmpty()) {
                entity.getPoliza().getCliente().setTelefono2(null);
            }
            if (entity.getPoliza().getCliente().getObservaciones()!= null && entity.getPoliza().getCliente().getObservaciones().isEmpty()) {
                entity.getPoliza().getCliente().setObservaciones(null);
            }
            if (entity.getPoliza().getPropiedad().getPiso() != null && entity.getPoliza().getPropiedad().getPiso().isEmpty()) {
                entity.getPoliza().getPropiedad().setPiso(null);
            }
            if (entity.getPoliza().getPropiedad().getObservaciones()!= null && entity.getPoliza().getPropiedad().getObservaciones().isEmpty()) {
                entity.getPoliza().getPropiedad().setObservaciones(null);
            }
            if (entity.getOriginal().getDescripcion() != null && entity.getOriginal().getDescripcion().isEmpty()) {
                entity.getOriginal().setDescripcion(null);
            }
            Aseguradora a = aseguradoraFacadeREST.find(entity.getPeritoOriginal().getAseguradora().getId());
            entity.getOriginal().setNombre("[" + a.getNombre() + "] " + entity.getNumero() + ".pdf");
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            recursoFacadeREST.create(entity.getOriginal());
            entity.getOriginal().setId(q.getFirstResult());
            if (entity.getPoliza().getId() == null) {
                if (entity.getPoliza().getCliente().getId() == null) {
                    clienteFacadeREST.create(entity.getPoliza().getCliente());
                    entity.getPoliza().getCliente().setId(q.getFirstResult());
                }
                if (entity.getPoliza().getPropiedad().getId() == null) {
                    if (entity.getPoliza().getPropiedad().getLocalidad().getId() == null) {
                        localidadFacadeREST.create(entity.getPoliza().getPropiedad().getLocalidad());
                        entity.getPoliza().getPropiedad().getLocalidad().setId(q.getFirstResult());
                    }
                    propiedadFacadeREST.create(entity.getPoliza().getPropiedad());
                    entity.getPoliza().getPropiedad().setId(q.getFirstResult());
                }
                polizaFacadeREST.create(entity.getPoliza());
                entity.getPoliza().setId(q.getFirstResult());
            }
            if (entity.getAfectado() != null && entity.getAfectado().getId() == null) {
                if (entity.getAfectado().getLocalidad().getId() == null) {
                    Localidad aux = localidadFacadeREST.buscarLocalidadPorCodigoPostal(entity.getAfectado().getLocalidad().getCp());
                    if (aux == null) {
                        localidadFacadeREST.create(entity.getAfectado().getLocalidad());
                        entity.getAfectado().getLocalidad().setId(q.getFirstResult());
                    } else {
                        entity.getAfectado().setLocalidad(aux);
                    }
                }
                propiedadFacadeREST.create(entity.getAfectado());
                entity.getAfectado().setId(q.getFirstResult());
            }
            super.create(entity);
        }
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

    @GET
    @Path("consultarEstado/{idSiniestro}")
    @Produces(MediaType.TEXT_PLAIN)
    public String consultarEstado(@PathParam("idSiniestro") Integer idSiniestro) {
        Siniestro s = find(idSiniestro);
        return s != null ? s.getEstado().toString() : null;
    }
    
    public Integer calcularEstado(Integer idSiniestro) {
        Siniestro s = find(idSiniestro);
        Query qmin = em.createNamedQuery("Siniestro.estadoMinimo"),
              qmax = em.createNamedQuery("Siniestro.estadoMaximo");
        qmin.setParameter("idSiniestro", idSiniestro);
        qmax.setParameter("idSiniestro", idSiniestro);
        qmin.setFirstResult(0);
        qmax.setFirstResult(0);
        qmin.setMaxResults(1);
        qmax.setMaxResults(1);
        Integer min = qmin.getResultList().isEmpty() ? null : (Integer) qmin.getResultList().get(0),
                max = qmax.getResultList().isEmpty() ? null : (Integer) qmax.getResultList().get(0),
                res;
        if (s != null && min != null && max != null) {
            if (s.getEstado() < 4) {
                if (max.equals(0)) {
                    res = 0;
                } else if (min < 2) {
                    res = 1;
                } else if (max.equals(2)) {
                    res = 2;
                } else {
                    res = 3;
                }
            } else {
                if (min.equals(3) && s.getEstado() <= 5) {
                    res = s.getEstado();
                } else if (min.equals(2) && !s.getEstado().equals(5)) {
                    res = s.getEstado();
                } else {
                    res = -1;
                }
            }
        } else if (s != null) {
            res = 0;
        } else {
            res = null;
        }
        return res;
    }
    
    @PUT
    @Path("cerrarSiniestro/{idSiniestro}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void cerrarSiniestro(@PathParam("idSiniestro") Integer idSiniestro, Siniestro entity) {
        Siniestro s = find(idSiniestro);
        Query qmin = em.createNamedQuery("Siniestro.estadoMinimo");
        qmin.setParameter("idSiniestro", idSiniestro);
        qmin.setFirstResult(0);
        qmin.setMaxResults(1);
        Integer min = qmin.getResultList().isEmpty() ? null : (Integer) qmin.getResultList().get(0);
        if (s.getEstado() > 1 && s.getEstado() < 4) {
            if (min.equals(2)) {
                s.setEstado(4);
            } else if (min.equals(3)) {
                s.setEstado(5);
            }
            s.setFechaCierre(new Date());
            edit(idSiniestro, s);
        }
    }
    
    @PUT
    @Path("facturarSiniestro/{idSiniestro}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void facturarSiniestro(@PathParam("idSiniestro") Integer idSiniestro, Siniestro entity) {
        Siniestro s = find(idSiniestro);
        Query qmin = em.createNamedQuery("Siniestro.estadoMinimo");
        qmin.setParameter("idSiniestro", idSiniestro);
        qmin.setFirstResult(0);
        qmin.setMaxResults(1);
        Integer min = qmin.getResultList().isEmpty() ? null : (Integer) qmin.getResultList().get(0);
        if (s.getEstado().equals(4) && min.equals(2)) {
            List<Tarea> lt = tareaFacadeREST.obtenerTareas(idSiniestro);
            for (Tarea t : lt) {
                Tarea aux = tareaFacadeREST.find(t.getId());
                if (aux.getEstado().equals(2)) {
                    aux.setImporte(t.getImporte());
                } else {
                    aux.setImporte(new Float(0.0));
                }
                tareaFacadeREST.edit(aux);
            }
            s.setEstado(6);
            s.setAlbaran(entity.getAlbaran());
            edit(idSiniestro, s);
        }
    }
    
    @PUT
    @Path("cobrarSiniestro/{idSiniestro}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void cobrarSiniestro(@PathParam("idSiniestro") Integer idSiniestro, Siniestro entity) {
        Siniestro s = find(idSiniestro);
        Query qmin = em.createNamedQuery("Siniestro.estadoMinimo");
        qmin.setParameter("idSiniestro", idSiniestro);
        qmin.setFirstResult(0);
        qmin.setMaxResults(1);
        Integer min = qmin.getResultList().isEmpty() ? null : (Integer) qmin.getResultList().get(0);
        if (s.getEstado().equals(6) && min.equals(2)) {
            s.setEstado(7);
            edit(idSiniestro, s);
        }
    }
}
