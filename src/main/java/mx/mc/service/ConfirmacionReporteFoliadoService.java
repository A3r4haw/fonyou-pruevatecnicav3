/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.ConfirmacionReporteFoliado;
import mx.mc.model.Folios;

/**
 *
 * @author gcruz
 */
public interface ConfirmacionReporteFoliadoService extends GenericCrudService<ConfirmacionReporteFoliado, String> {
    
   boolean registrarFolioReporteDiario(Folios folio, ConfirmacionReporteFoliado reporteFoliado) throws Exception;
   
}
