/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reforms.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Roberto
 */
@javax.ws.rs.ApplicationPath("wr")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(reforms.service.AdjuntoFacadeREST.class);
        resources.add(reforms.service.AseguradoraFacadeREST.class);
        resources.add(reforms.service.CapacidadFacadeREST.class);
        resources.add(reforms.service.CitaFacadeREST.class);
        resources.add(reforms.service.ClienteFacadeREST.class);
        resources.add(reforms.service.ConductorFacadeREST.class);
        resources.add(reforms.service.ContactoFacadeREST.class);
        resources.add(reforms.service.EventoFacadeREST.class);
        resources.add(reforms.service.FacturaFacadeREST.class);
        resources.add(reforms.service.GremioFacadeREST.class);
        resources.add(reforms.service.GrupoFacadeREST.class);
        resources.add(reforms.service.IntegranteFacadeREST.class);
        resources.add(reforms.service.JornadaFacadeREST.class);
        resources.add(reforms.service.LlamadaFacadeREST.class);
        resources.add(reforms.service.LocalidadFacadeREST.class);
        resources.add(reforms.service.LocalidadesvisitadasFacadeREST.class);
        resources.add(reforms.service.MantenimientoFacadeREST.class);
        resources.add(reforms.service.MaterialFacadeREST.class);
        resources.add(reforms.service.MultiserviciosFacadeREST.class);
        resources.add(reforms.service.NominaFacadeREST.class);
        resources.add(reforms.service.OperadorFacadeREST.class);
        resources.add(reforms.service.OperarioFacadeREST.class);
        resources.add(reforms.service.ParticipanteFacadeREST.class);
        resources.add(reforms.service.PeritoFacadeREST.class);
        resources.add(reforms.service.PolizaFacadeREST.class);
        resources.add(reforms.service.PropiedadFacadeREST.class);
        resources.add(reforms.service.ReasignacionFacadeREST.class);
        resources.add(reforms.service.RecursoFacadeREST.class);
        resources.add(reforms.service.RecursossubcontrataFacadeREST.class);
        resources.add(reforms.service.ReplanificacionFacadeREST.class);
        resources.add(reforms.service.SiniestroFacadeREST.class);
        resources.add(reforms.service.SubcontrataFacadeREST.class);
        resources.add(reforms.service.TareaFacadeREST.class);
        resources.add(reforms.service.TareascitaFacadeREST.class);
        resources.add(reforms.service.TrabajadorFacadeREST.class);
        resources.add(reforms.service.TrabajoFacadeREST.class);
        resources.add(reforms.service.VehiculoFacadeREST.class);
    }
    
}
